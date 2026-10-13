import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { RegistrosService } from '../../services/registros/registros.service';
import { SessionUser } from '../../services/session/session.service';
import { Parametros } from '../../model/parametros.class';
import { CoreContainerComponent } from '../core-container/core-container.component';
import {IPageInfo, VirtualScrollerComponent} from 'ngx-virtual-scroller';
import { PAGING_LIMIT } from "../../util/common.protocols";
import * as moment from 'moment';
import * as _ from 'lodash';

/* Dev Tools */
import { DEBUG } from "../../settings/app.settings";

const EXCEPTION_TAB = 'EXCEPTION_TAB';
const EXCEPTION_SEARCH = "EXCEPTION_SEARCH";
const EXCEPTION_FILTERS_EVENTS_NAME = "EXCEPTION_FILTERS_EVENTS_NAME";
const EXCEPTION_FILTERS_EVENTS_DATE = "EXCEPTION_FILTERS_EVENTS_DATE";
const EXCEPTION_FILTERS_RECORDS = "EXCEPTION_FILTERS_RECORDS";
const EXCEPTION_FILTERS_USERS = 'EXCEPTION_FILTERS_USERS';

@Component({
  selector: 'gn-libreria-registros',
  templateUrl: './libreria-registros.component.html',
  styleUrls: ['./libreria-registros.component.scss']
})
export class LibreriaRegistrosComponent implements OnInit {


  @ViewChild('wizard', {static: false}) wizard: any;
  @ViewChild('drop', {static: false}) drop: any;

  @ViewChild(VirtualScrollerComponent, {static: false})
  private virtualScroller: VirtualScrollerComponent;

  placeholder: any = 'Buscar';
  searchTerm: string = null;
  resetSearch: boolean;
  arrayAux: any = [];
  search: any = [];

  /* DropList */
  public itemsDropList: any[] = [];
  defaultSelected:any;


  cuadricula:boolean = false;

  public itemsFiltros: any[] = [{nombre: 'Nombre (A-Z)', id:1}];
  defaultSelectedFiltros: any = {nombre:'Nombre (A-Z)', id: 1};

  public itemsFiltrar: any[] = [{nombre: 'Eventos', tipo: "cat", id:0},
    {nombre: 'Dossier', tipo: "", id:1, seccion:1},{nombre: 'Tecnovigilancia', tipo: "", id:2, seccion:1},
    {nombre: 'Sometimiento', tipo: "", seccion:1, id:3},{nombre: 'Todos los Eventos', tipo: "", id:4,seccion:1},
    {nombre: 'Fecha Realización', tipo: "cat", id:0},
    {nombre: 'Todos', tipo: "", id:5, seccion:2},
    {nombre: 'En Tiempo', tipo: "", id:6, seccion:2},
    {nombre: 'Fuera de Tiempo', tipo: "", id:7, seccion:2}];
  defaultFiltrar: any ;


  public itemsRegistros: any[] = [{nombre: 'Todos', tipo: "", id:1},
    {nombre: 'Con Folio', tipo: "", id:2, seccion:1},{nombre: 'Sin Folio', tipo: "", id:3, seccion:1},
    {nombre: 'En Trámite', tipo: "cat", id:0},
    {nombre: 'Trámite en Evaluación', tipo: "", id:4, seccion:2},{nombre: 'Trámite Disponible', tipo: "", id:5, seccion:2},
    {nombre: 'Trámite Entregado', tipo: "", id:6, seccion:2}];
  defaultRegistros: any = {nombre: 'Todos', tipo: "", id:1};

  public itemsResponsables: any[] = [{nombre: 'Responsables Gestión', tipo: "cat", id:0},
    {nombre: 'Todos', tipo: "", id:1},{nombre: 'correo@ryndem.mx', tipo: "", id:2},{nombre: 'correo2@ryndem.mx', tipo: "", id:3},
    {nombre: 'Responsables Evento', tipo: "cat", id:0},
    {nombre: 'Todos', tipo: "", id:4},{nombre: 'correo@ryndem.mx', tipo: "", id:5},{nombre: 'correo2@ryndem.mx', tipo: "", id:6}];
  defaultResponsables: any = {nombre: 'Todos', tipo: "", seccion:1};


  lst:any[] = [];
  tooltipV:any[] = [];

  today:Date = new Date();

  lstItems: string [] = ['Todos', 'Activos', 'Caducados'];
  pWizard: any = 0;
  pWizard2: any = 0;


  arrayFiltro = [];
  eventos_filtro: string = 'Todos los Eventos';
  frealizacion_filtro: string = 'Todos';
  registro_filtro: any = {nombre: 'Todos', tipo: "", id:1};
  resp_filtro: any = {nombre: 'Todos', tipo: "", id:1};


  arrayCorreoEve:any = [];

  arrayResp:any = [];
  arrayFiltrar:any = [];
  arrayRegistros:any = [];
  arrayTab:any = [];

  arrayFiltroGeneral:any = [];

  activeTab:string = 'all';
  currentPath:string = '';
  currentPage:number = 0;

  loading: boolean = false;
  scrollItems: any = [];
  buffer: any[] = [];
  timer;
  searchKey:string = "";
  firstItem:any = {};

  totalRecords:number = 0; //Total de todos los registros del cliente
  currentTotal:number = 0; // Total de los registros ya filtrados

  _isMounted:boolean = false;

  private handleKeySearch = _.debounce($event => this.changeSearchTerm($event), 500);

  constructor(private router: Router, private registrosService: RegistrosService, private coreContainer: CoreContainerComponent) { }

  ngOnInit() {
    SessionUser.getInstance().setLoading(true);
    this.getManager();
  }

  async ngAfterViewInit() {
    this._isMounted = true; /* TODO: evitamos llamadas innecesarias de los droplist cuando aun el componente no esta montado */
    const records:any = await this.getRecords(); // Vamos por la primer página
    this.buffer = records;
  }

  /* ngDoCheck(){
    if(SessionUser.getInstance().getScrollTop() == (SessionUser.getInstance().getScrollPosition() - 794) ){
      DEBUG && console.log('bottom');
    }
  } */

  /* Busqueda de search bar */
  changeSearchTerm($event) {
    this.searchKey = $event;
    let result = this.searchLabels();
    this.processRecordsForPresentation(result, [EXCEPTION_SEARCH]);
  }

  searchLabels() {
    if (!this.searchKey) return '';
    return `&keyword=${this.searchKey}`;
  };

  /* ------------------------------------------------------------Drop list-------------------------------------------------------------- */
  dropList($event) {
    DEBUG && console.log($event);
  }

  dropListFiltro($event) {
    if($event.id == 1){

      this.arrayAux = this.arrayAux.sort((a, b) => (a.nombre < b.nombre) ? -1 : 1);
      DEBUG && console.log(this.arrayAux);

    }
    /* if($event.id == 2){
        this.arrayAux = this.arrayAux.sort((a, b) => (a.codigo < b.codigo) ? -1 : 1);
        DEBUG && console.log(this.arrayAux);
    } */
  }


  dropFiltrar($event) {
    DEBUG && console.log($event);

    if($event.seccion == 1){
      this.eventos_filtro = $event.nombre;
      this.handlerEventsName();
    }else if($event.seccion == 2){
      this.frealizacion_filtro = $event.nombre;
      this.handlerEventsDate();
    }
  }

  dropRegistros($event) {
    DEBUG && console.log($event);
    this.registro_filtro = $event;
    this.handlerRecords();
  }

  dropResponsables($event) {
    DEBUG && console.log($event);
    this.resp_filtro = $event;
    this.handlerFiltersUsers();
  }

  /* ------------------------------------------------------------ /Drop list-------------------------------------------------------------- */

  irNuevoRegistro() {
    this.router.navigate(["protected/libreriaRegistros/nuevoRegistro"]);
  }

  monitorear(id: number, data: any) {
    this.router.navigate(["protected/libreriaRegistros/monitorearTramite", id]);
  }

  irCatalogoProductos() {
    this.router.navigate(["protected/libreriaRegistros/catalogoProductos"]);
  }

  top() {
    this.coreContainer.scrollToBottom();
  }

  tab($event) {
    DEBUG && console.log($event);
    this.handlerTab($event);
  }

  actualizar() {
    this.processRecordsForPresentation('');
  }

  resetArraysFiltros() {
    this.eventos_filtro= 'Todos los Eventos';
    this.frealizacion_filtro = 'Todos';
    this.registro_filtro = {nombre: 'Todos', tipo: "", id:1};
    this.resp_filtro = {nombre: 'Todos', tipo: "", id:1};

    this.wizard.activeItem(0);

    this.drop.title1 = 'Todos los Eventos';
    this.drop.title2 = 'Todos';

    this.defaultRegistros = {nombre: 'Todos', tipo: "", id:1};
    this.defaultResponsables = {nombre: 'Todos', tipo: "", seccion:1};

    this.resetSearch = !this.resetSearch;
  }

  handlerTab($event) {

    switch ($event) {
      case 'Todos':
        this.activeTab = 'all';
        break;

      case 'Activos':
        this.activeTab = 'active';
        break;

      case 'Caducados':
        this.activeTab = 'expired';
        break;
    }

    this.processRecordsForPresentation('');
  }

  handlerFiltersUsers() {
    let path = this.getFiltersUsers();
    this.processRecordsForPresentation(path, [EXCEPTION_FILTERS_USERS]);
  }

  getFiltersUsers() {
    if (this.resp_filtro.nombre === 'Todos') return '';
    if (this.resp_filtro.seccion === 1) {
      return `&manager=${this.resp_filtro.nombre}`;
    } else if (this.resp_filtro.seccion === 2) {
      return `&managerEvent=${this.resp_filtro.nombre}`;
    }
  }

  handlerRecords() {
    let path = this.getFiltersRecords();
    this.processRecordsForPresentation(path, [EXCEPTION_FILTERS_RECORDS]);
  }

  getFiltersRecords() {
    if (this.registro_filtro.nombre === 'Todos') return '';

    let path = '';

    if (this.registro_filtro.seccion === 1) {
      if (this.registro_filtro.nombre === "Con Folio") path = '&withFolio=true';
      if (this.registro_filtro.nombre === "Sin Folio") path = '&withoutFolio=true';
    } else if (this.registro_filtro.seccion === 2) {
      switch (this.registro_filtro.nombre) {
        case 'Trámite en Evaluación':
          path = '&statusCofepris=Trámite en evaluación.';
          break;
        case 'Trámite Disponible':
          path = '&statusCofepris=Trámite disponible.';
          break;
        case 'Trámite Entregado':
          path = '&statusCofepris=Trámite entregado.';
          break;
      }
    }
    return path;
  }

  handlerEventsName() {
    let path = this.getFiltersEventsName();
    this.processRecordsForPresentation(path, [EXCEPTION_FILTERS_EVENTS_NAME]);
  }

  getFiltersEventsName() {
    if (this.eventos_filtro === 'Todos los Eventos') return '';
    return `&filterEventName=${this.eventos_filtro}`;
  }

  handlerEventsDate() {
    let path = this.getFiltersEventsDate();
    this.processRecordsForPresentation(path, [EXCEPTION_FILTERS_EVENTS_DATE]);
  }

  getFiltersEventsDate() {
    if (this.frealizacion_filtro === 'Todos') return '';
    const field = this.frealizacion_filtro === 'En Tiempo' ? 'inTime' : 'timeout';
    return `&filterEventDate=${field}`;
  }

  processRecordsForPresentation(filter = '', filterExceptionList = []) {
    if (!this._isMounted) return;

    if ( _.indexOf(filterExceptionList, EXCEPTION_SEARCH) === -1 ) {
      filter += this.searchLabels();
    }

    if ( _.indexOf(filterExceptionList, EXCEPTION_FILTERS_EVENTS_NAME) === -1 ) {
      filter += this.getFiltersEventsName();
    }

    if ( _.indexOf(filterExceptionList, EXCEPTION_FILTERS_EVENTS_DATE) === -1 ) {
      filter += this.getFiltersEventsDate();
    }

    if ( _.indexOf(filterExceptionList, EXCEPTION_FILTERS_RECORDS) === -1 ) {
      filter += this.getFiltersRecords();
    }

    if ( _.indexOf(filterExceptionList, EXCEPTION_FILTERS_USERS) === -1 ) {
      filter += this.getFiltersUsers();
    }

    this.currentPage = 0;
    this.currentPath = `&tab=${this.activeTab}${filter}`;
    this.reset();
  }

  async reset() {
    const numItems = Math.floor((window.screen.width - 110) / 225) * 4;
    const size = PAGING_LIMIT < numItems ? numItems : PAGING_LIMIT;
    this.loading = true;
    if (this.totalRecords > 0) {
      this.fetchNextChunk(0, size).then(chunk => this.buffer = chunk);
    } else {
      // TODO: Cuando totalRecords esta en 0, hay diferentes situaciones, por lo cual siempre pedimos la primera pagina
      const records:any = await this.getRecords(); // Vamos por la primer página
      this.buffer = records;
      this.loading = false;
    }
  }

  fetchMore(event: IPageInfo) {
    DEBUG && console.log(`@fetchMore => END = ${event.endIndex} || BUFFER = ${this.buffer.length} || TOTAL = ${this.currentTotal}`);
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
      this.fetchNextChunk(this.buffer.length, size).then(chunk => {
        this.buffer = this.buffer.concat(chunk);
        this.loading = false;
      }, () => this.loading = false);
    }
  }

  fetchNextChunk(skip: number, limit: number): Promise<any[]> {
    DEBUG && console.log("@fetchNextChunk: Entre");
    return new Promise((resolve, reject) => {
      clearTimeout(this.timer);
      this.timer = setTimeout(async () => {
        if (skip < this.totalRecords) {
          const data:any = await this.getRecords();
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

  getRecords() {
    return new Promise((resolve, reject) => {
      const numItems = Math.floor((window.screen.width - 110) / 225) * 4;
      const size = PAGING_LIMIT < numItems ? numItems : PAGING_LIMIT;
      const url = `page=${this.currentPage}&size=${size}&idCompany=${SessionUser.getInstance().getidEmpresaActual()}${this.currentPath}`;
      DEBUG && console.log('@getRecords: URL ' + url);

      this.registrosService.getPaginatedRecords(url).subscribe(
        data => {
          DEBUG && console.log('@getRecords: Entre ', data);
          SessionUser.getInstance().setLoading(false);
          let result = [];
          _.forEach(data.results, o => {
            if (o.estadoCofepris != undefined) {
              let array = o.estadoCofepris.split("|");
              o.estadoCofepris = array[0];
              o.fecha = array[1] !== undefined ? array[1] : null;
            }
            if (o.producto && o.producto.codigo && typeof o.producto.codigo === 'string')
              o.producto.codigo = o.producto.codigo.trim();

            result.push({...o});
          });
          this.arrayAux = result.slice();
          this.totalRecords = this.currentTotal === 0 && data.total > 0 ? data.total : this.totalRecords;
          this.firstItem = result.length > 0 ? result[0] : {};
          this.currentTotal = data.total;
          this.tooltipV = new Array(this.currentTotal).fill(false);
          resolve(result.slice());
        }, err => {
          DEBUG && console.log('@getRecords: Error ', err);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al obtener los registros');
          resolve([]);
        });
    });
  }

  getManager() {
    let parameter = new Parametros();
    parameter.id = SessionUser.getInstance().getidEmpresaActual();

    this.registrosService.getManagers(parameter).subscribe(
      data => {
        DEBUG && console.log("@getManager: Entre ", data);
        this.itemsResponsables = [];

        if (data.manager && data.manager.length > 0) {
          this.itemsResponsables.push({ nombre: 'Responsables Gestión', tipo: "cat" });
          this.itemsResponsables.push({ nombre: 'Todos', tipo: "", seccion:1 });
          _.forEach(data.manager, o => {
            this.itemsResponsables.push({ nombre: o, tipo: "", seccion:1 });
          });
        }

        if (data.managerEvent  && data.managerEvent.length > 0) {
          this.itemsResponsables.push({ nombre: 'Responsables Evento', tipo: "cat" });
          this.itemsResponsables.push({ nombre: 'Todos', tipo: "", seccion:2 });
          _.forEach(data.managerEvent, o => {
            this.itemsResponsables.push({ nombre: o, tipo: "", seccion:2 });
          });
        }
      }, err => {
        DEBUG && console.log('@getManager: Error ', err);
      }
    )
  }
}
