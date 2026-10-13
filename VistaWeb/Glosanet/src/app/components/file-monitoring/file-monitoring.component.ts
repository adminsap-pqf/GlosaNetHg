import { Component, OnInit, AfterViewInit, OnDestroy, ViewChild, HostListener, ElementRef } from '@angular/core';
import { FileMonitoringService } from "../../services/file-monitoring/file-monitoring.service";
import { SessionUser } from "../../services/session/session.service";
import { IPageInfo, VirtualScrollerComponent } from 'ngx-virtual-scroller';
import { PAGING_LIMIT } from '../../util/common.protocols';
import * as _ from 'lodash';
import { Subscription } from "rxjs";

/* Dev Tools */
import { DEBUG } from "../../settings/app.settings";

const TEXT = 'Se produjo un error inesperado...';

const EXCEPTION_ORDER_BY_DATE = 'EXCEPTION_ORDER_BY_DATE';
const EXCEPTION_FILTER_BY_STATUS = 'EXCEPTION_FILTER_BY_STATUS';
const EXCEPTION_START_DATE = 'EXCEPTION_START_DATE';
const EXCEPTION_END_DATE = 'EXCEPTION_END_DATE';

@Component({
  selector: 'gn-file-monitoring',
  templateUrl: './file-monitoring.component.html',
  styleUrls: ['./file-monitoring.component.scss']
})
export class FileMonitoringComponent implements OnInit, AfterViewInit, OnDestroy {

  @ViewChild('lightbox', {static: false}) lightboxDOM: ElementRef;
  @ViewChild('containerLightbox', {static: false}) containerLightboxDOM: ElementRef;
  @ViewChild(VirtualScrollerComponent, {static: false})
  private virtualScroller: VirtualScrollerComponent;

  readonly statusInicio: number = 0;
  readonly statusEnProceso: number = 1;
  readonly statusFallo: number = 2;
  readonly statusExito: number = 3;

  realTime: any;
  searchTerm:string = null;

  loading:boolean = false;
  disableLoading:boolean = false;
  scrollItems:any = [];
  buffer:any = [];
  timer;

  totalFiles:number = 0;
  currentTotal:number = 0;
  currentPath:string = '&orderBySymbol=-';
  currentPage:number = 0;
  currentClient:Number = 0;

  startDate:string = null;
  startDateFormat:string = '';
  invalidStartDate:boolean = false;
  endDate:string = null;
  endDateFormat:string = '';
  invalidEndDate:boolean = false;

  firstItem:any = {};

  _isMounted:boolean = false;

  optionsOrderBy:any[] = [
    {nombre: 'Más Recientes'},
    {nombre: 'Más Antiguos'},
  ];
  selectedOrderBy:any = {nombre: 'Más Recientes'};

  optionsFilterByStatus:any[] = [
    {nombre: 'Todos', id: -1},
    {nombre: 'Encolado', id: 0},
    {nombre: 'Descargando ', id :1},
    {nombre: 'Fallo ', id: 2},
    {nombre: 'Exito ', id: 3},
  ];
  selectedFilterByStatus:any = {nombre: 'Todos', id:1};

  classAnimation = 'w3-animate-zoom-out';
  currentStackTrace:string = '';
  subs: Subscription;

  constructor(private fileMonitoringService: FileMonitoringService) { }

  ngOnInit() {
    SessionUser.getInstance().setLoading(true);
    this.subs = this.fileMonitoringService.reloadFileMonitoring.subscribe(URL => {
      if (URL === '/protected/fileMonitoring') {
        this.loadData();
      }
    });
  }

  ngAfterViewInit() {
    this.loadData();
  }

  async loadData() {
    this._isMounted = true; /* TODO: evitamos llamadas innecesarias de los droplist cuando aun el componente no esta montado */
    this.currentClient = SessionUser.getInstance().getCliente().idCliente;
    const data:any = await this.getData(); // Vamos por la primer página
    this.buffer = data.slice();
    /* Verificamos si en la llamada hay al menos un registro con estado diferente de terminado */
    this.handleCheckInRealTime(this.buffer.slice());
  }

  handleKeySearch($event) {
    DEBUG && console.log('@handleKeySearch: Entre ', $event);
  }

  orderByDate($event) {
    DEBUG && console.log('@handleOrderBy: Entre ', $event);
    let path = this.getOrderByDate();
    this.selectedOrderBy = $event;
    this.processFilesForPresentation(path, [EXCEPTION_ORDER_BY_DATE]);
  }

  getOrderByDate() {
    if (this.selectedOrderBy.nombre === 'Más Antiguos') {
      return `&orderBySymbol=+`;
    } else if (this.selectedOrderBy.nombre === 'Más Recientes') {
      return `&orderBySymbol=-`;
    }
  }

  filterByStatus($event) {
    DEBUG && console.log('@filterByStatus: Entre', $event);
    this.selectedFilterByStatus = $event;
    let path = this.getFilterByStatus();
    this.processFilesForPresentation(path, [EXCEPTION_FILTER_BY_STATUS]);
  }

  getFilterByStatus() {
    if (this.selectedFilterByStatus.nombre === 'Todos') return '';
    return `&filterStatus=${this.selectedFilterByStatus.id}`;
  }

  async handleStartDate(date) {
    DEBUG && console.log('@handleStartDate: Entre');
    const invalid = await this.validateDates(date, 1);
    if (invalid || this.invalidEndDate) {
      if (!invalid) {
        this.startDateFormat = `${this.formatDate(date)} 00:00:00`;
      } else {
        this.startDateFormat = '';
      }
      return;
    }
    this.startDateFormat = `${this.formatDate(date)} 00:00:00`;
    let path = this.getStartDate();
    this.processFilesForPresentation(path, [EXCEPTION_START_DATE]);
  }

  getStartDate() {
    if (this.startDateFormat === '') return '';
    return `&filterInitialDate=${this.startDateFormat}`;
  }

  resetStartDate() {
    this.startDate = null;
    this.startDateFormat = '';
    this.invalidStartDate = false;
    this.processFilesForPresentation('', [EXCEPTION_START_DATE]);
  }

  async handleEndDate(date) {
    DEBUG && console.log('@handleEndDate: Entre');
    const invalid = await this.validateDates(date, 2);
    if (invalid || this.invalidStartDate) {
      if (!invalid) {
        this.endDateFormat = `${this.formatDate(date)} 23:59:59`;
      } else {
        this.endDateFormat = '';
      }
      return;
    }
    this.endDateFormat = `${this.formatDate(date)} 23:59:59`;
    let path = this.getEndDate();
    this.processFilesForPresentation(path, [EXCEPTION_END_DATE]);
  }

  getEndDate() {
    if (this.endDateFormat === '') return '';
    return `&filterFinalDate=${this.endDateFormat}`;
  }

  resetEndDate() {
    this.endDate = null;
    this.endDateFormat = '';
    this.invalidEndDate = null;
    this.processFilesForPresentation('', [EXCEPTION_END_DATE]);
  }

  validateDates(date, option) {
    return new Promise(resolve => {
      setTimeout(() => {

        if (option === 1 && this.endDate === null) {
          return resolve(false);
        }

        if (option === 2 && this.startDate === null) {
          return resolve(false);
        }

        let startTime = new Date(this.startDate);
        startTime.setHours(0, 0, 0, 0);
        let endTime = new Date(this.endDate);
        endTime.setHours(23, 59, 59, 999);
        let currentDate = new Date(date);
        let valid = true;
        // startTime.getTime() < currentDate.getTime() && (currentDate.getTime() < endTime.getTime() || option === 1)
        if ( (option === 1 && currentDate.getTime() <= endTime.getTime()) || (option === 2 && startTime.getTime() <= currentDate.getTime())) {
          if (option === 1) {
            this.invalidStartDate = false;
          } else if (option === 2) {
            this.invalidEndDate = false;
          }
          valid = false;
        } else {
          if (option === 1) {
            this.invalidStartDate = true;
          } else if (option === 2) {
            this.invalidEndDate = true;
          }
          valid = true;
        }
        resolve(valid);
      });
    });
  }

  processFilesForPresentation(filter = '', filterExceptionList = []) {

    if (!this._isMounted) {
      return;
    }

    if (_.indexOf(filterExceptionList, EXCEPTION_ORDER_BY_DATE) === -1) {
      filter += this.getOrderByDate();
    }

    if (_.indexOf(filterExceptionList, EXCEPTION_FILTER_BY_STATUS) === -1) {
      filter += this.getFilterByStatus();
    }

    if (_.indexOf(filterExceptionList, EXCEPTION_START_DATE) === -1) {
      filter += this.getStartDate();
    }

    if (_.indexOf(filterExceptionList, EXCEPTION_END_DATE) === -1) {
      filter += this.getEndDate();
    }

    this.currentPage = 0;
    this.currentPath = `${filter}`;
    this.reset();
  }

  update() {
    this.reset();
  }

  async reset() {
    let numItems = Math.floor((window.screen.width - 110) / 225) * 4;
    const size = PAGING_LIMIT < numItems ? numItems : PAGING_LIMIT;
    this.loading = true;
    if (this.totalFiles > 0) {
      this.fetchNextChunk(0, size)
        .then(chunk => {
          this.buffer = chunk.slice();
          this.handleCheckInRealTime(this.buffer.slice());
        });
    } else {
      // TODO: Cuando totalFiles esta en 0, hay diferentes situaciones, por lo cual siempre pedimos la primera pagina
      const data:any = await this.getData(); // Vamos por la primer página
      this.buffer = data.slice();
      this.loading = false;
      this.handleCheckInRealTime(this.buffer.slice());
    }
  }

  fetchMore(event: IPageInfo) {
    DEBUG && console.log(`@fetchMore => END = ${event.endIndex}`);
    if (event.endIndex !== this.buffer.length - 1) return;
    const numItems = Math.floor((window.screen.width - 110) / 225) * 4;
    const size = PAGING_LIMIT < numItems ? numItems : PAGING_LIMIT;
    if (event.endIndex !== this.currentTotal - 1 && this.currentTotal > 0) {
      const totalPages = this.currentTotal >= size ?  Math.ceil(this.currentTotal/size) : 0;
      if ( this.currentPage > totalPages) {
        this.currentPage = totalPages;
        return;
      }
      if (event.startIndex !== 0) this.loading = true;
      this.currentPage = this.currentPage + 1;
      this.fetchNextChunk(this.buffer.length, size)
        .then(chunk => {
          this.buffer = this.buffer.concat(chunk);
          this.loading = false;
          this.handleCheckInRealTime(this.buffer.slice());
        }, () => {
          this.loading = false;
        });
    }
  }

  fetchNextChunk(skip: number, limit: number): Promise<any[]> {
    DEBUG && console.log('@fetchNextChunk: Entre, skip => ' + skip + '  ||  limit => ' + limit);
    return new Promise( (resolve, reject) => {
      clearTimeout(this.timer);
      this.timer = setTimeout( async() => {
        if (skip < this.totalFiles) {
          const data: any = await this.getData();
          this.loading = false;
          return resolve(data);
        }
        this.loading = false;
        reject();
      }, 200);
    });
  }

  scrollTo() {
    this.virtualScroller.scrollInto(this.firstItem);
  }

  getData() {
    return new Promise((resolve, reject) => {
      const numItems = Math.floor((window.screen.width - 110) / 225) * 4;
      const size = PAGING_LIMIT < numItems ? numItems : PAGING_LIMIT;
      const url = `page=${this.currentPage}&size=${size}&filterCompany=${this.currentClient}${this.currentPath}`;
      DEBUG && console.log('@getData: URL ' + url);
      this.fileMonitoringService.getDataStagePhase1(url).subscribe(
        data => {
          DEBUG && console.log('@getData: Entre ', data);
          SessionUser.getInstance().setLoading(false);
          const { results, total } = data;
          let result = [];

          _.forEach(results, o => {
            result.push({
              index: o.recordIndex,
              fileName: o.nombreArchivo,
              user: `${o.nombre} ${o.apellido}`,
              date: new Date(o.creationDate),
              status: o.status,
              statusText: this.getStatus(o.status),
              comments: _.truncate(o.error ? this.getError(o.error) : o.error, {'length': 102}),
              activeTooltip: false,
              id: o.pk_Fase1RMQMensaje,
              stackTrace: o.stackTrace === 'true',
              guid: o.guid,
            });
          });

          this.totalFiles = this.currentTotal === 0 && total > 0 ? total : this.totalFiles;
          this.firstItem = result.length > 0 ? result[0] : {};
          this.currentTotal = total;
          resolve(result.slice());
        }, err => {
          DEBUG && console.log('@getData: Error ', err);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al obtener los registros');
          resolve([]);
        });
    });
  }

  getStatus(status) {
    let text = '';
    switch (status) {
      case this.statusInicio:
        text = 'Encolado';
        break;

      case this.statusEnProceso:
        text = 'Descargando';
        break;

      case this.statusFallo:
        text = 'Fallo';
        break;

      case this.statusExito:
        text = 'Exito';
        break;
    }
    return text;
  }

  formatDate(date) {
    let d = new Date(date),
      month = '' + (d.getMonth() + 1),
      day = '' + d.getDate(),
      year = d.getFullYear();

    if (month.length < 2)
      month = '0' + month;
    if (day.length < 2)
      day = '0' + day;

    return [year, month, day].join('-');
  }

  getError(error) {
    let stringAux = error.replace('{', '');
    stringAux = stringAux.replace('=', ': ');
    stringAux = stringAux.replace('}', '');
    return stringAux;
  }

  openLightbox(file) {
    if (!file.stackTrace) return;

    SessionUser.getInstance().setLoading(true);

    this.fileMonitoringService.getStackTrace(file.guid).subscribe(
      data => {
        SessionUser.getInstance().setLoading(false);
        if (data) {
          this.classAnimation = 'w3-animate-zoom';
          this.lightboxDOM.nativeElement.style.display = 'block';
          this.currentStackTrace = data;
        }
      },
      err => {
        SessionUser.getInstance().setLoading(false);
        SessionUser.getInstance().setMessageFailed('al obtener el StackTrace');
        document.getElementById('requestFailed').style.display = 'block';
      }
    );
  }

  closeLightbox() {
    this.classAnimation = 'w3-animate-zoom-out';
    this.lightboxDOM.nativeElement.style.display = 'none';
  }

  handleCheckInRealTime(collection: any) {
    const index = _.findIndex(collection, o => ( o.status === this.statusInicio || o.status === this.statusEnProceso ));
    if (index !== -1) {
      if (this.realTime) { clearInterval(this.realTime); }
      this.realTime = setInterval(() => this.checkInRealTime(), 30000);
      this.disableLoading = true;
    } else {
      clearInterval(this.realTime);
      this.disableLoading = false;
    }
  }

  checkInRealTime() {
    this.reset();
  }

  ngOnDestroy() {
    clearInterval(this.realTime);
  }

}
