import { Component, OnInit, ViewChild } from '@angular/core';
import { IPageInfo, VirtualScrollerComponent } from 'ngx-virtual-scroller';
import { Producto } from '../../model/producto.class';
import { Router, ActivatedRoute } from '@angular/router';
import { RegistrosService } from '../../services/registros/registros.service';
import { ProductoGeneralService } from '../../services/general/productos';
import { SessionUser } from '../../services/session/session.service';
import { Parametros } from '../../model/parametros.class';
import { UserService } from '../../services/user/user.service';
import { PrevisionesService } from '../../services/previsiones/previsiones.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Notificacion } from '../../model/notificacion.class';
import { CorreoEvento } from '../../model/correoEvento.class';
import { Eventos } from '../../model/eventos.class';
import { Prevision } from '../../model/prevision.class';
import { Permiso } from '../../model/permiso.class';
import { PermisosService } from '../../services/permisos/permisos.service';
import { DiasHabilesService } from '../../services/general/diasHabiles';
import { ProductsService } from '../../services/products/products.service';
import * as _ from 'lodash';
import { PAGING_LIMIT } from '../../util/common.protocols';

/* Dev Tools */
import { DEBUG } from '../../settings/app.settings';
import {$e} from "codelyzer/angular/styles/chars";

@Component({
  selector: 'gn-nuevo-permiso',
  templateUrl: './nuevo-permiso.component.html',
  styleUrls: ['./nuevo-permiso.component.scss']
})
export class NuevoPermisoComponent implements OnInit {

  /* agregar producto */

  @ViewChild(VirtualScrollerComponent, {static: false})
  private virtualScroller: VirtualScrollerComponent;

  section: boolean = false;

  placeholder: any = 'Buscar';
  searchTerm = '';
  resetSearch: boolean;
  arrayAux: any = [];
  search: any = [];

  lstItems: string [] = ['Productos Prevision 2018', 'Productos de DataStage'];
  pWizard: string = 'Productos Prevision 2018';

  lst: any[] = [];

  idPevisionActual: number = 0;

  loading: boolean = false;
  scrollItems: any = [];
  buffer: any[] = [];
  timer;

  public itemsFiltros: any[] = [
    {nombre: 'Nombre (A-Z)', id: 0},
    {nombre: 'Código', id: 2}
  ];
  defaultSelectedFiltros: any = {nombre: 'Nombre (A-Z)', id: 0};
  // 0 = Nombre - Ascendente
  // 1 = Nombre - Descendente
  // 2 = Código - Ascendente
  // 3 = Código - Descendente

  classItem: any[];
  classItem1: any[];


  /* modal producto */
  itemsTipoProducto: any = [{nombre:'Controlado', tipo: 1},{nombre:'No Controlado',tipo:2}];
  itemsUnidad: any = [{nombre:'t'},{nombre:'Kg'},{nombre:'g'},{nombre:'l'},{nombre:'ml'}, {nombre:'pza'}];

  producto: Producto = new Producto();

  classAnimation = 'w3-animate-zoom-out';

  defaultTipoProducto = {nombre:'Controlado', tipo: 1};
  defaultUnidad = {nombre: ''};
  titleModal: string = '';

  ttProducto:any = 0;

  idProducto:number = 0;

  date = new Date();
  yearActual: number = 0;
  nextYear: number = 0;

  /* saber si lleva check o no */
  formModal: number = 0;

  checkModal:boolean = false;


  /* agregar eventos *********************************************************************************/

  permiso: Permiso = new Permiso();

  users:any = [];
  //prevision:Prevision = new Prevision();

  selectUser: any = {nombre: ''};

  gEventosForm1: FormGroup;
  gEventosForm2: FormGroup;

  public dropListPeriodoTiempo: any[] = [{ nombre: 'Días' }, { nombre: 'Semanas' }, { nombre: 'Meses' }, { nombre: 'Años' }];

  public dropListNotificacion: any[] = [{ nombre: 'Cada Día' }, { nombre: 'Cada Semana' }, { nombre: 'Cada Mes' }, { nombre: 'Cada Año' }];

  /* Efectos de los 3 eventos por default */
  itemEventEffect: any = new Array(2).fill('body-itemE-close');

  /* reset droplistNuevo */
  resetNuevo: Array<any> = new Array<any>(3);


  listNotificacionesNuevo: Array<Notificacion> = new Array<Notificacion>();
  listCorreosNuevo: Array<CorreoEvento> = new Array<CorreoEvento>();

  generarEventEffect: string = 'body-generarE-close';

  elaboracion: Eventos = new Eventos();

  sometimiento: Eventos = new Eventos();

  /* Lista de eventos nuevos */
  listEventos: Array<Eventos> = new Array<Eventos>();

  /* **********************************************+ Elaboracion **********************************************+ */

  dia1Elaboracion: number = 0;
  correoElaboracion: string = '';
  listNotificacionesElaboracion: Array<Notificacion> = new Array<Notificacion>();
  listCorreosElaboracion: Array<CorreoEvento> = new Array<CorreoEvento>();
  resetElaboracion: Array<any> = new Array<any>(3);

  /* **********************************************+ Sometimiento **********************************************+ */

  dia1Sometimiento: number = 0;
  correoSometimiento: string = '';
  listNotificacionesSometimiento: Array<Notificacion> = new Array<Notificacion>();
  listCorreosSometimiento: Array<CorreoEvento> = new Array<CorreoEvento>();
  resetSometimiento: Array<any> = new Array<any>(3);

  /* Efectos de los nuevos eventos creados */
  itemNewEventEffect: any  = new Array();/*.fill('body-itemE-close'); */

  evento: Eventos = new Eventos();
  notificaciones: Notificacion = new Notificacion();
  correos: CorreoEvento = new CorreoEvento();

  validEmailElaboracion = false;
  validarDias2Elaboracion = false;
  validarDias1Elaboracion = false;

  validEmailSometimiento = false;
  validarDias2Sometimiento = false;
  validarDias1Sometimiento = false;

  dropListTipoPeriodo: Array<any> = new Array<any>();
  dropListRecurrencia: Array<any> = new Array<any>();
  diasInput: Array<number> = new Array<number>();
  correoInput: Array<string> = new Array<string>();

  resetDefault: Array<any> = new Array<any>();

  classAnimation4 = 'w3-animate-zoom-out';
  classAnimation3 = 'w3-animate-zoom-out';

  arrayValidEmail = [];
  arrayValidDias2 = [];
  arrayValidDias1 = [];
  listCorreoT:any[] = [];

  check: boolean = false;

  fullData:boolean = true;

  nombreConfirmEliminar:string = '';
  indexEventEliminar:number = 0;

  indexModal: number = 0;

  tiempoResp: number = 0;

  /* Valid data */
  activeInValidData: any = {
    name: false,
    presentation: false,
    fraction: false,
    quantity: false
  };

  /* Variable para setear valores de droplist */
  clear: boolean = false;
  resetValidate: boolean = false;

  validEmailNuevo: boolean = false;
  validarDias2Nuevo: boolean = false;

  newEventInvalidDates: boolean = false;
  invalidDateCofeprisEdit:boolean = false;
  eventInvalidDates: any = [];

  listNotificationError: any = [];

  totalProducts  = 0;
  isList = true;
  grandTotal = 0;
  currentTotal = 0;
  currentPage = 0;
  _isMounted = false;

  firstItem = {};

  private handleKeySearch = _.debounce(value => this.changeSearchTerm(value), 500);

  constructor(
    private router: Router,
    private registrosService: RegistrosService,
    private productsService: ProductsService,
    private userService: UserService,
    private previsionService: PrevisionesService,
    private permisoService: PermisosService,
    private diasHabilesService: DiasHabilesService
  ) { }

  ngOnInit() {
    this.gEventosForm1 = new FormGroup({
      nombreEvent: new FormControl('', [Validators.required, Validators.pattern('^([a-zñáéíóú, A-ZÁÉÍÓÚ]{1}[a-zñáéíóú,A-ZÁÉÍÓÚ]+[\s]*)+$')]),
      dias1: new FormControl('', [Validators.required, Validators.pattern('^(([0-9])+(-){0,1})+$')]),
      dias2: new FormControl('', [Validators.required, Validators.pattern('^(([0-9])+(-){0,1})+$')]),
      dias3: new FormControl('', [Validators.required, Validators.pattern('^(([0-9])+(-){0,1})+$')])
    });

    this.gEventosForm2 = new FormGroup({
      correoN: new FormControl('', [Validators.required, Validators.pattern('^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$')]),
    });

    this.permiso.citaCofepris = new Date();
    this.permiso.finicio = new Date();
    this.permiso.citaCofepris.setDate(this.permiso.citaCofepris.getDate() + 90);
    this.sometimiento.frealizacion = new Date(this.permiso.citaCofepris);

    this.elaboracion.nombre = 'Elaboración';
    this.elaboracion.definido = true;
    this.sometimiento.nombre = 'Sometimiento';
    this.sometimiento.definido = true;

    this.buscarUsuarios();

    /* agregar producto */

    this.yearActual = this.date.getFullYear();
    this.nextYear = this.yearActual + 1;

    //this.permiso.citaCofepris = new Date((this.yearActual) , 4, 30);

    this.lstItems = ['Productos Prevision '+this.yearActual, 'Productos de DataStage'];
    this.pWizard = 'Productos Prevision '+this.yearActual;

    this.getProductos();
  }

  ngDoCheck() {
    if (this.listNotificacionesElaboracion.length > 0
      && this.listCorreosElaboracion.length > 0 && this.permiso.usuario !== undefined && this.listNotificacionesSometimiento.length > 0
      && this.listCorreosSometimiento.length > 0) {

      if (this.listEventos.length > 0) {
        let flag = false;
        for (let item of this.listEventos) {
          if (item.notificaciones.length > 0 && item.correos.length > 0) {
            flag = true;
          } else {
            flag = false;
            break;
          }
        }
        if (flag) {
          this.fullData = true;
          this.validFullData();
        } else {
          this.fullData = false;
        }
      } else {
        this.fullData = true;
        this.validFullData();
      }
    } else {
      this.fullData = false;
    }
  }

  setConfirmFalse() {
    setTimeout(() => {
      SessionUser.getInstance().setConfirm(false);
    }, 198);
  }

  /* -------------------------------- Agregar Producto -------------------------------- */

  irListadoProducto() {
    this.router.navigate(["protected/libreriaPermisos"]);
  }

  dropListFiltro($event) {
    if (this.pWizard === 'Productos de DataStage') {
      this.defaultSelectedFiltros = $event;
      this.currentPage = 0;
      this.resetAsync();
    } else {
      if ($event.id == 0) {
        this.arrayAux = this.arrayAux.sort((a, b) => (a.nombre < b.nombre) ? -1 : 1);
        DEBUG && console.log(this.arrayAux);
        this.reset();
      }
      if ($event.id == 2) {
        this.arrayAux = this.arrayAux.sort((a, b) => (a.codigo < b.codigo) ? -1 : 1);
        DEBUG && console.log(this.arrayAux);
        this.reset();
      }
    }
  }

  /* Busqueda de search bar */
  changeSearchTerm($event) {
    this.searchTerm = $event;

    if (this.pWizard === 'Productos de DataStage') {
      this.currentPage = 0;
      this.resetAsync();
    } else {
      this.arrayAux = [];
      this.buffer = [];
      this.loading = false;
      let i = 0;
      for (let item of this.lst) {
        item.index = i + 1;
        this.arrayAux.push(item);
        i++;
      }
      DEBUG && console.log($event);
      if ($event !== '') {
        this.search = [];
        for (let item of this.arrayAux) {
          if(item.nombre !== undefined){

            if (item.nombre.toLowerCase().indexOf($event.toLowerCase()) !== -1 || item.codigo.indexOf($event) !== -1) {
              this.search.push(item);
            }
          }
        }
        if (this.search.length > 0) {
          this.arrayAux = [];
          let j = 0;
          for (let item of this.search) {
            item.index = j + 1;
            this.arrayAux.push(item);
            j++;
          }
          this.reset();

        }else {
          this.arrayAux = [];
          //this.loading = false;
        }
      }else {
        this.arrayAux = [];
        let l = 0;
        for (let item of this.lst) {
          item.index = l + 1;
          this.arrayAux.push(item);
          l++;
        }
        this.reset();
      }
    }
  }

  async getProductos() {
    SessionUser.getInstance().setLoading(true);

    if (this.pWizard == 'Productos de DataStage') {
      let parametros = new Parametros();
      parametros.condicion = SessionUser.getInstance().getRfc();
      parametros.id = SessionUser.getInstance().getidEmpresaActual();

      this._isMounted = true; // TODO: Evitamos llamadas innecesarias de los droplist cuando aun el componente no esta montado
      this.loading = true;
      this.currentPage = 0;
      const data:any = await this.getData(); // Vamos por la primer página
      this.buffer = data.slice();
      this.loading = false;

      /*this.registrosService.obtenerProductosDataStagePorEmpresa(parametros)
        .subscribe(
          data => {
            DEBUG && console.log(data);
            //this.scrollItems = [];
            this.reset();
            this.lst = Object.assign([], data);
            this.arrayAux = Object.assign([], data);

            this.arrayAux = this.arrayAux.sort((a, b) => (a.nombre < b.nombre) ? -1 : 1);
            let i = 0;
            for(let item of this.arrayAux){
              item.index = i + 1;
              i++;
            }
            //this.lst.push(data[0]);
            this.classItem = new Array(this.lst.length).fill('item');
            this.classItem1 = new Array(this.lst.length).fill('item1');
            SessionUser.getInstance().setLoading(false);
            this.reset();
          },
          error => {
            DEBUG && console.log('error al traer los productos '+error);
            SessionUser.getInstance().setLoading(false);
          });*/
    } else {
      let parametros = new Parametros();
      parametros.id = SessionUser.getInstance().getidEmpresaActual();

      this.previsionService.obtenerProductosCompletosPrevisionActual(parametros)
        .subscribe(
          data => {
            DEBUG && console.log(data);
            this.scrollItems = [];
            this.reset();
            this.lst = Object.assign([], data);
            this.arrayAux = Object.assign([], data);

            this.arrayAux = this.arrayAux.sort((a, b) => (a.nombre < b.nombre) ? -1 : 1);
            let i = 0;
            for(let item of this.arrayAux){
              item.index = i + 1;
              i++;
            }
            //this.lst.push(data[0]);
            this.classItem = new Array(this.lst.length).fill('item');
            this.classItem1 = new Array(this.lst.length).fill('item1');
            SessionUser.getInstance().setLoading(false);
            this.reset();
          },
          error =>{
            DEBUG && console.log(error);
            SessionUser.getInstance().setLoading(false);
          });
    }
  }

  reset() {
    let numItems = Math.floor((window.screen.width - 110)/ 225) * 4;
    /* if(this.arrayAux.length > 0 ){ */
    this.loading = this.arrayAux.length > 0;
    /* } */
    if (this.arrayAux.length > 0) {
      this.fetchNextChunk(0, numItems).then(chunk => this.buffer = chunk);
    }
  }

  fetchMore(event: IPageInfo) {
    DEBUG && console.log(event);
    DEBUG && console.log(this.scrollItems);
    if (event.endIndex !== this.buffer.length - 1) return;
    const numItems = Math.floor((window.screen.width - 110) / 225) * 4;
    if (event.endIndex !== this.arrayAux.length - 1 && this.arrayAux.length > 0) {
      if (event.startIndex !== 0) {
        this.loading = true;
      }
      this.fetchNextChunk(this.buffer.length, numItems).then(chunk => {
        this.buffer = this.buffer.concat(chunk);
        this.loading = false;
      }, () => this.loading = false);
    }
  }

  fetchNextChunk(skip: number, limit: number): Promise<any[]> {
    DEBUG && console.log("fetchNextChunk");
    return new Promise((resolve, reject) => {
      clearTimeout(this.timer);
      this.timer = setTimeout(() => {
        this.loading = false;
        if (skip < this.arrayAux.length) {
          return resolve(this.arrayAux.slice(skip, skip + limit));
        }
        reject();
      }, 500);
    });
  }

  scrollTo() {
    this.virtualScroller.scrollInto(this.arrayAux[0]);
  }

  /* modal producto */
  nuevoProducto() {
    this.formModal = 2;
    this.titleModal = 'Nuevo Producto / Previsión '+ this.nextYear;
    this.classAnimation = 'w3-animate-zoom';
    this.producto.nombre = '';
    this.producto.codigo = '';
    this.producto.origen = '';
    this.defaultTipoProducto = {nombre:'Controlado', tipo: 1};
    this.defaultUnidad = {nombre: ''};
  }

  agregarProducto() {
    DEBUG && console.log(this.producto);
    this.permiso.cantidad = this.producto.cantidad;
    this.permiso.unidad = this.producto.unidad;
    this.section = true;

    this.activeInValidData = {
      name: false,
      presentation: false,
      code: false,
      quantity: false
    };
    this.clear = true;
    this.resetValidate = true;
    setTimeout(() => {
      this.clear = false;
      this.resetValidate = false;
    }, 500);
  }

  cerrarProducto() {
    this.producto.reset();
    this.classAnimation = 'w3-animate-zoom-out';
    document.getElementById('id01').style.display = 'none';
    this.activeInValidData = {
      name: false,
      presentation: false,
      code: false,
      quantity: false
    };
    this.clear = true;
    this.resetValidate = true;
    setTimeout(() => {
      this.clear = false;
      this.resetValidate = false;
    }, 500);
  }

  /* modal producto */

  /*selectItem_(index, item, form) {
    this.formModal = form;
    // this.idProducto = item.idProducto !== undefined ? item.idProducto : 0;
    // this.producto.nombre = item.nombre;
    // this.producto.codigo = item.codigo;
    // this.producto.origen = item.origen;
    this.producto = Object.assign(new Producto(), item);
    if (this.producto.tipoProducto == 1) {
      this.defaultTipoProducto = {nombre:'Controlado', tipo: 1};
    } else {
      this.defaultTipoProducto = {nombre:'No Controlado', tipo: 2};
    }
    this.defaultUnidad = {nombre: this.producto.unidad};
    this.classItem.fill('item');
    //this.classItem[index] = 'itemSelected';

    this.classItem1.fill('item1');
    //this.classItem1[index] = 'itemSelected1';
    DEBUG && console.log(this.producto);
    this.seleccionarProducto();
  }*/

  selectItem(item, form) {
    this.formModal = form;
    /* this.idProducto = item.idProducto !== undefined ? item.idProducto : 0;
    this.producto.nombre = item.nombre;
    this.producto.codigo = item.codigo;
    this.producto.origen = item.origen; */
    this.producto = Object.assign(new Producto(), item);
    if (this.producto.tipoProducto == 1) {
      this.defaultTipoProducto = {nombre:'Controlado', tipo: 1};
    } else {
      this.defaultTipoProducto = {nombre:'No Controlado', tipo: 2};
    }
    this.defaultUnidad = {nombre: this.producto.unidad};

    this.buffer = _.map(this.buffer, o => {
      return {
        ...o,
        selected: o.idProducto === item.idProducto
      }
    });
    this.seleccionarProducto();
  }

  seleccionarProducto() {
    this.titleModal = 'Agregar Producto / Previsión '+this.nextYear;
    this.classAnimation = 'w3-animate-zoom';
    document.getElementById('id01').style.display = 'block';
  }

  tab($event) {
    DEBUG && console.log($event);
    this.pWizard = $event;

    this.searchTerm = '';
    this.defaultSelectedFiltros = {nombre: 'Nombre (A-Z)', id: 0};

    this.getProductos();

    /* let array = [];
    if($event == 'Activos'){
      for(let item of this.lst){
        if(item.prorroga && item.eventoActual == 'Prorroga'){
          moment(item.eventos[0].frealizacion).diff(moment(this.today), 'days') > 0 ? array.push(item): null;
        }else{
          moment(item.ffin).diff(moment(this.today), 'days') > 0 ? array.push(item): null;
        }
      }

    }else if($event == 'Caducados'){
      for(let item of this.lst){
        if(item.prorroga && item.eventoActual == 'Prorroga'){
          moment(this.today).diff(moment(item.eventos[0].frealizacion), 'days') > 0 ? array.push(item): null;
        }else{
          moment(this.today).diff(moment(item.ffin), 'days') > 0 ? array.push(item): null;
        }
      }
    }else{
      for(let item of this.lst){
        array.push(item);
      }
    }

    DEBUG && console.log(array);

    this.arrayTab = Object.assign([], array);

    this.filtroGeneral(); */
  }
  /* ---------------------------------------------------------------- */

  irPermisos() {
    SessionUser.getInstance().setConfirm(false);
    this.router.navigate(["protected/libreriaPermisos"]);
  }

  resetDroplistNuevo(seccion: string) {
    if (seccion == 'Nombre') {
      this.resetNuevo[0] = { nombre: 'Días' }
    }
    else {
      this.resetNuevo[1] = { nombre: '' };
      this.resetNuevo[2] = { nombre: '' };
    }
  }

  checkF($event) {
    $event.stopPropagation();
    this.check = !this.check;
  }

  confirm(open: boolean, index = 0) {
    SessionUser.getInstance().setConfirm(true);
    this.indexModal = index;
    if (open) {
      this.classAnimation3 = 'w3-animate-zoom';
      document.getElementById('idConfirm').style.display = 'block';
      this.clearValidators();
    } else {
      this.classAnimation3 = 'w3-animate-zoom-out';
      document.getElementById('idConfirm').style.display = 'none';

    }
  }

  back() {
    this.confirm(false, 1);
    SessionUser.getInstance().setConfirm(false);

  }

  resetForms(form: number) {
    switch (form) {
      case 1:
        this.gEventosForm1.patchValue({
          nombreEvent: '',
          dias1: '',
        });
        break;
      case 2:
        this.gEventosForm1.patchValue({
          dias2: ''
        });
        break;
      case 3:
        this.gEventosForm1.patchValue({
          dias3: ''
        });
        break;
      case 4:
        this.gEventosForm2.patchValue({
          correoN: ''
        });
        break;
      case 5:
        //this.modalForm.patchValue({
        //});
        break;
      default:
        break;
    }
  }

  resetDroplistElaboracion(seccion: string) {
    if (seccion == 'Nombre') {
      this.resetElaboracion[0] = { nombre: 'Días' }
    }
    else {
      this.resetElaboracion[1] = { nombre: '' };
      this.resetElaboracion[2] = { nombre: '' };
    }
  }

  resetDroplistSometimiento(seccion: string) {
    if (seccion == 'Nombre') {
      this.resetSometimiento[0] = { nombre: 'Días' }
    }
    else {
      this.resetSometimiento[1] = { nombre: '' };
      this.resetSometimiento[2] = { nombre: '' };
    }
  }

  droplistNuevo(index, $event) {
    switch (index) {
      case 1: // Nombre del evento nuevo
        this.evento.tipoPeriodo = $event.nombre;
        break;
      case 2: // Periodo de Tiempo dias, semanas, meses, años nuevo evento
        this.notificaciones.tipoPeriodo = $event.nombre;
        break;
      case 3: // Notificar nuevo evento
        this.notificaciones.recurrencia = $event.nombre;
        break;
      default:
        break;
    }
  }

  dropListPeriodo($event, index) {
    this.dropListTipoPeriodo[index] = $event;
  }

  dropListRecu($event, index) {
    this.dropListRecurrencia[index] = $event;
  }

  dropListTiempoR($event, i){
    this.listEventos[i].tipoPeriodo = $event.nombre;
    //this.calculateFRealizacionEvento(i);
  }

  dropListElaboracion($event, index) {
    switch (index) {
      case 1: // Nombre del evento nuevo
        this.elaboracion.tipoPeriodo = $event.nombre;
        //this.calculateFRealizacion(1);
        break;
      case 2: // Periodo de Tiempo dias, semanas, meses, años nuevo evento
        this.notificaciones.tipoPeriodo = $event.nombre;
        break;
      case 3: // Notificar nuevo evento
        this.notificaciones.recurrencia = $event.nombre;
        break;
      default:
        break;
    }
  }

  dropListSometimiento($event, index) {
    switch (index) {
      case 1: // Nombre del evento nuevo
        this.sometimiento.tipoPeriodo = $event.nombre;
        //this.calculateFRealizacion(1);
        break;
      case 2: // Periodo de Tiempo dias, semanas, meses, años nuevo evento
        this.notificaciones.tipoPeriodo = $event.nombre;
        break;
      case 3: // Notificar nuevo evento
        this.notificaciones.recurrencia = $event.nombre;
        break;
      default:
        break;
    }
  }

  calculateFRealizacionEvento_(index){
    /* let year = this.registro.ffin.getFullYear();
    let month = this.registro.ffin.getMonth();
    let day = this.registro.ffin.getDate();
    if(this.listEventos[index].tipoPeriodo == 'Días'){
      this.listEventos[index].frealizacion = new Date(year,month, Number(day - this.listEventos[index].cantPeriodo) );
    }
    if(this.listEventos[index].tipoPeriodo == 'Semanas'){
      this.listEventos[index].frealizacion = new Date(year,month, Number(day - (this.listEventos[index].cantPeriodo * 7)));
    }
    if(this.listEventos[index].tipoPeriodo == 'Meses'){
      this.listEventos[index].frealizacion = new Date(year,Number(month - this.listEventos[index].cantPeriodo),day  );
    }
    if(this.listEventos[index].tipoPeriodo == 'Años'){
      this.listEventos[index].frealizacion = new Date(Number(year - this.listEventos[index].cantPeriodo),month, day );
    } */
  }

  calculateFRealizacionEvento(index) {
    if (this.listEventos[index].notificaciones.length > 0) {
      const result = [];
      _.forEach(this.listEventos[index].notificaciones, o => {
        const startDate = this.calculateFInicioNuevo(this.listEventos[index].frealizacion, o.tipoPeriodo, o.cantPeriodo);
        result.push({...o, startDate: startDate, startDateText: `Inicia el ${this.getCustomDate(startDate)}`});
      });
      this.listEventos[index].notificaciones = result.slice();
    }
  }

  calculateFRealizacionNuevo(periodo, cantidad, validateDate = false, customDate = null) {
    let date: Date;
    date = this.diasHabilesService.getDate(!customDate ? this.permiso.citaCofepris : customDate, periodo, cantidad);

    if (validateDate && this.evento.cantPeriodo > 0 && this.evento.tipoPeriodo !== '' && this.listNotificacionesNuevo.length > 0) {
      const result = [];
      _.forEach(this.listNotificacionesNuevo, o => {
        const startDate = this.calculateFInicioNuevo(date, o.tipoPeriodo, o.cantPeriodo);
        result.push({...o, startDate: startDate, startDateText: `Inicia el ${this.getCustomDate(startDate)}`});
      });
      this.listNotificacionesNuevo = result.slice();
    }

    return date;
  }

  calculateFInicioNuevo(frealizacion, periodo, cantidad) {
    let date: Date;
    date = this.diasHabilesService.getDate(frealizacion, periodo, cantidad);
    return date;
  }

  fecha($event){
    //DEBUG && console.log($event);
  }

  confirmarEliminar(i, nombre){
    this.nombreConfirmEliminar = nombre;
    this.indexEventEliminar = i;
    this.classAnimation4 = 'w3-animate-zoom';
    document.getElementById('idEliminarEv').style.display = 'block';
  }

  /* ****************************** Nuevo evento llenar y borrar ******************************  */
  agregarNuevoNotificacion() {
    if (this.gEventosForm1.get('dias2').value !== 0 && this.notificaciones.tipoPeriodo !== '' && this.notificaciones.recurrencia !== '') {
      this.notificaciones.cantPeriodo = this.gEventosForm1.get('dias2').value;
      this.notificaciones.startDate = this.calculateFInicioNuevo(this.evento.frealizacion ? this.evento.frealizacion : this.permiso.citaCofepris, this.notificaciones.tipoPeriodo, this.notificaciones.cantPeriodo);
      this.notificaciones.startDateText = `Inicia el ${this.getCustomDate(this.notificaciones.startDate)}`;
      let aux = Object.assign({}, this.notificaciones);
      this.listNotificacionesNuevo.push(aux);
      this.notificaciones.reset();
      this.resetDroplistNuevo('Notificacion');
      this.resetForms(2);
      this.validarDias2Nuevo = false;
    }
  }

  agregarNuevoCorreo() {
    if (this.gEventosForm2.get('correoN').value !== '') {
      let flag = false;
      for(let item of this.listCorreosNuevo){
        if(item.correo === this.gEventosForm2.get('correoN').value){
          flag = true;
          break;
        }
      }
      if(!flag){
        this.correos.correo = this.gEventosForm2.get('correoN').value;
        let aux = Object.assign({}, this.correos);
        this.listCorreosNuevo.push(aux);
      }
      this.correos.reset();
      this.resetForms(4);
      this.validEmailNuevo = false;
    }
  }

  borrarNuevoNotificacion(data: number) {
    this.listNotificacionesNuevo.splice(data, 1);
    DEBUG && console.log("******************+ lista nuevo notificaciones ***************");
    DEBUG && console.log(this.listNotificacionesNuevo);
  }

  borrarNuevoCorreo(data: number) {
    this.listCorreosNuevo.splice(data, 1);
    DEBUG && console.log("******************+ lista nuevo correos ***************");
    DEBUG && console.log(this.listCorreosNuevo);
  }
  /* ****************************** Eventos crear y borrar ******************************  */
  generarNuevoEvento() {
    if (this.evento.nombre !== '' && this.evento.frealizacion !== null && this.listNotificacionesNuevo.length > 0 && this.listCorreosNuevo.length > 0) {
      let auxCorreo = Object.assign([], this.listCorreosNuevo);
      let auxNotificaciones = Object.assign([], this.listNotificacionesNuevo);
      this.evento.correos = auxCorreo;
      this.evento.notificaciones = auxNotificaciones;
      //this.evento.frealizacion = this.calculateFRealizacionNuevo(this.evento.tipoPeriodo, this.evento.cantPeriodo);
      for(let item of this.evento.notificaciones){
        //item.finicio = this.calculateFInicioNuevo(this.evento.frealizacion,item.tipoPeriodo, item.cantPeriodo);
      }

      for(let item of this.evento.correos){
        this.listCorreosTotal(item.correo);
      }
      let aux = Object.assign({}, this.evento);
      this.listEventos.push(aux);
      this.itemNewEventEffect.push('body-itemE-close');
      this.evento.reset();
      this.resetForms(1);
      this.resetDroplistNuevo('Nombre');
      this.listCorreosNuevo = [];
      this.listNotificacionesNuevo = [];

      this.agregarCamposNuevoevento();
      this.generarEventEffect = 'body-generarE-close';

      this.validarDias2Nuevo = false;
    }
    DEBUG && console.log("******************+ lista eventos ***************");
    DEBUG && console.log(this.listEventos);
  }

  agregarCamposNuevoevento() {
    this.dropListTipoPeriodo.push({nombre: ''});
    this.dropListRecurrencia.push({nombre: ''});
    this.diasInput.push(0);
    this.correoInput.push('');
    this.resetDefault.push({ nombre: '' });

    this.arrayValidEmail.push(false);
    this.arrayValidDias2.push(false);
    this.arrayValidDias1.push(false);
    this.eventInvalidDates.push(false);
  }

  borrarEvento(index: number) {
    this.listEventos.splice(index, 1);

    this.diasInput.splice(index, 1);
    /* TODO: Esta linea elimina directamente opciones del droplist */
    //this.dropListPeriodoTiempo.splice(index, 1);

    this.dropListTipoPeriodo.splice(index, 1);
    this.dropListRecurrencia.splice(index, 1);

    this.classAnimation4 = 'w3-animate-zoom-out';
    document.getElementById('idEliminarEv').style.display = 'none';
    DEBUG && console.log(this.listEventos);
  }

  /* ******************************************************  Botones agregar default  ****************************************************** */
  agregarNotificacion(index: any) {
    if (this.diasInput[index] !== 0 && this.dropListTipoPeriodo[index].nombre !== '' && this.dropListRecurrencia[index].nombre !== '') {
      this.notificaciones.tipoPeriodo = this.dropListTipoPeriodo[index].nombre;
      this.notificaciones.cantPeriodo = this.diasInput[index];
      this.notificaciones.recurrencia = this.dropListRecurrencia[index].nombre;
      this.notificaciones.startDate = this.calculateFInicioNuevo(this.listEventos[index].frealizacion, this.notificaciones.tipoPeriodo, this.notificaciones.cantPeriodo);
      this.notificaciones.startDateText = `Inicia el ${this.getCustomDate(this.notificaciones.startDate)}`;
      let aux = Object.assign([], this.notificaciones);
      this.listEventos[index].notificaciones.push(aux);
      this.notificaciones.reset();

      this.arrayValidDias2[index] = false;
      this.dropListRecurrencia[index] = {nombre: ''};
      this.dropListTipoPeriodo[index] = {nombre: ''};
      this.diasInput[index] = 0;

      this.resetDefault[index] = { nombre: '' };
      this.eventInvalidDates[index] = false;

      DEBUG && console.log(this.listEventos);
    }
  }

  borrarNotificacion(ev: any, item: any) {
    this.listEventos[ev].notificaciones.splice(item, 1);
    DEBUG && console.log("******************+ lista Notificaciones ***************");
    DEBUG && console.log(this.listEventos);


  }

  agregarCorreo(index: any) {
    if (this.correoInput[index] !== '') {
      let flag = false;
      for(let item of this.listEventos[index].correos){
        if(item.correo === this.correoInput[index]){
          flag = true;
          break;
        }
      }
      if(!flag){
        this.correos.correo = this.correoInput[index];
        let aux = Object.assign([], this.correos);
        this.listEventos[index].correos.push(aux);

        this.listCorreosTotal(this.correoInput[index]);
      }

      this.correos.reset();
      this.correoInput[index] = '';
      this.arrayValidEmail[index] = false;

      DEBUG && console.log(this.listEventos);
    }
  }

  borrarCorreo(ev: any, item: any) {
    this.listEventos[ev].correos.splice(item, 1);
    DEBUG && console.log("******************+ lista Correo ***************");
    //this.borrarCorreosTotal();
    DEBUG && console.log(this.listEventos);
  }

  /*  ****************************** Elaboracion Agregar y borrar  ****************************** */
  agregarNotificacionElaboracion() {
    if (this.dia1Elaboracion !== 0 && this.notificaciones.tipoPeriodo !== '' && this.notificaciones.recurrencia !== '') {
      this.notificaciones.cantPeriodo = this.dia1Elaboracion;
      this.notificaciones.startDate = this.calculateFInicioNuevo(this.elaboracion.frealizacion, this.notificaciones.tipoPeriodo, this.notificaciones.cantPeriodo);
      this.notificaciones.startDateText = `Inicia el ${this.getCustomDate(this.notificaciones.startDate)}`;
      let aux = Object.assign({}, this.notificaciones);
      this.listNotificacionesElaboracion.push(aux);
      this.notificaciones.reset();
      this.resetDroplistElaboracion('Notificacion');
      this.dia1Elaboracion = 0;
      this.validarDias2Elaboracion = false;
    }
    DEBUG && console.log(this.listNotificacionesElaboracion);
  }
  borrarElaboracionNotificacion(data: number) {
    this.listNotificacionesElaboracion.splice(data, 1);
    DEBUG && console.log("******************+ lista Elaboracion notificaciones ***************");
    DEBUG && console.log(this.listNotificacionesElaboracion);
  }

  agregarElaboracionCorreo() {
    if (this.correoElaboracion !== '') {
      let flag = false;
      for(let item of this.listCorreosElaboracion){
        if(item.correo === this.correoElaboracion){
          flag = true;
          break;
        }
      }
      if(!flag){
        this.correos.correo = this.correoElaboracion;
        let aux = Object.assign({}, this.correos);
        this.listCorreosElaboracion.push(aux);

        this.listCorreosTotal(this.correoElaboracion);
      }

      this.correos.reset();
      this.correoElaboracion = '';
      this.validEmailElaboracion = false;

    }
    DEBUG && console.log(this.listCorreosElaboracion);
  }

  borrarElaboracionCorreo(data: number) {
    this.listCorreosElaboracion.splice(data, 1);
    DEBUG && console.log("******************+ lista Elaboracion correos ***************");
    //this.borrarCorreosTotal();
    DEBUG && console.log(this.listCorreosElaboracion);
  }


  /*  ****************************** Sometimiento Agregar y borrar  ****************************** */
  agregarNotificacionSometimiento() {
    if (this.dia1Sometimiento !== 0 && this.notificaciones.tipoPeriodo !== '' && this.notificaciones.recurrencia !== '') {
      this.notificaciones.cantPeriodo = this.dia1Sometimiento;
      this.notificaciones.startDate = this.calculateFInicioNuevo(this.sometimiento.frealizacion, this.notificaciones.tipoPeriodo, this.notificaciones.cantPeriodo);
      this.notificaciones.startDateText = `Inicia el ${this.getCustomDate(this.notificaciones.startDate)}`;
      let aux = Object.assign({}, this.notificaciones);
      this.listNotificacionesSometimiento.push(aux);
      this.notificaciones.reset();
      this.resetDroplistSometimiento('Notificacion');
      this.dia1Sometimiento = 0;
      this.validarDias2Sometimiento = false;
    }
    DEBUG && console.log(this.listNotificacionesSometimiento);
  }
  borrarSometimientoNotificacion(data: number) {
    this.listNotificacionesSometimiento.splice(data, 1);
    DEBUG && console.log("******************+ lista Sometimiento notificaciones ***************");
    DEBUG && console.log(this.listNotificacionesSometimiento);
  }

  agregarSometimientoCorreo() {
    if (this.correoSometimiento !== '') {
      let flag = false;
      for(let item of this.listCorreosSometimiento){
        if(item.correo === this.correoSometimiento){
          flag = true;
          break;
        }
      }
      if(!flag){
        this.correos.correo = this.correoSometimiento;
        let aux = Object.assign({}, this.correos);
        this.listCorreosSometimiento.push(aux);

        this.listCorreosTotal(this.correoSometimiento);
      }

      this.correos.reset();
      this.correoSometimiento = '';
      this.validEmailSometimiento = false;

    }
    DEBUG && console.log(this.listCorreosSometimiento);
  }

  borrarSometimientoCorreo(data: number) {
    this.listCorreosSometimiento.splice(data, 1);
    DEBUG && console.log("******************+ lista Sometimiento correos ***************");
    //this.borrarCorreosTotal();
    DEBUG && console.log(this.listCorreosSometimiento);
  }

  validarCorrreo(email) {
    let re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    DEBUG && console.log(re.test(String(email).toLowerCase()));
    return re.test(String(email).toLowerCase());
  }

  validarDias(dias){
    if(dias > 0){
      let re = /^[0-9]+$/;
      DEBUG && console.log(re.test(String(dias).toLowerCase()));
      return re.test(String(dias).toLowerCase());
    }else{
      return false;
    }
  }

  listCorreosTotal(item) {
    this.listCorreoT.push(item);
    let i;
    let len = this.listCorreoT.length;
    let out=[];
    let obj={};

    for (i=0;i<len;i++) {
      obj[this.listCorreoT[i]]=0;
    }
    DEBUG && console.log(obj);
    for (i in obj) {
      out.push(i);
    }

    this.listCorreoT = out;
    DEBUG && console.log(this.listCorreoT);
  }


  borrarCorreosTotal() {
    this.listCorreoT = [];
    for (let item of this.listCorreosElaboracion) {
      this.listCorreosTotal(item.correo);
    }
    for (let item of this.listCorreosSometimiento) {
      this.listCorreosTotal(item.correo);
    }
    for (let item of this.listEventos) {
      for (let c of item.correos) {
        this.listCorreosTotal(c.correo);
      }
    }
    DEBUG && console.log(this.listCorreoT);
  }

  buscarUsuarios() {
    //this.users = [];
    let parametros = new Parametros();
    parametros.tipo = 'FK01_Cliente';
    parametros.year = SessionUser.getInstance().getCliente().idCliente;

    this.userService.buscarUsuarios(parametros)
      .subscribe(
        data =>{
          DEBUG && console.log(data);
          this.users = new Array();
          let u = [];
          for(let item of data){
            u.push({ nombre: item.nombre +" "+item.apellido+" - "+item.email, user: item });
          }
          Array.prototype.push.apply(this.users, u);
          DEBUG && console.log(this.users);
        },
        error =>{
          DEBUG && console.log('Error al buscar usuarios: '+error);
        });
  }

  getTiempoRespuesta() {
    DEBUG && console.log(this.permiso.citaCofepris);
    DEBUG && console.log(this.tiempoResp);
    let fRealizacion = new Date(this.permiso.citaCofepris);
    DEBUG && console.log(fRealizacion);
    fRealizacion.setDate(fRealizacion.getDate() + this.tiempoResp);
    DEBUG && console.log(this.permiso.citaCofepris);
    DEBUG && console.log(fRealizacion);
    if (this.permiso.citaCofepris !== null) {
      this.sometimiento.frealizacion = this.diasHabilesService.diasHabilesProx(this.tiempoResp, fRealizacion, this.permiso.citaCofepris);
      DEBUG && console.log(this.sometimiento.frealizacion);
      this.permiso.fRealizacionFolio = this.sometimiento.frealizacion;
    }
  }

  actualizarFecha(parametro:Parametros) {
    return new Promise((resolve, reject) => {
      this.permisoService.actualizarFechaCita(parametro)
        .subscribe(
          data =>{
            DEBUG && console.log(data);
            resolve();
          },
          error =>{
            DEBUG && console.log(error);
          });
    });
  }

  guardarPrevision() {
    const permiso = JSON.parse(JSON.stringify(this.permiso));

    const listNotificationError = [];
    this.listNotificationError = [];

    this.elaboracion.frealizacion = this.permiso.citaCofepris;

    this.elaboracion.notificaciones = this.listNotificacionesElaboracion;
    for (let item of this.elaboracion.notificaciones) {
      item.finicio = this.calculateFInicioNuevo(this.elaboracion.frealizacion, item.tipoPeriodo, item.cantPeriodo);
      if (new Date(permiso.finicio).getTime() > item.finicio.getTime()) {
        this.findInvalidDate(listNotificationError, this.elaboracion.nombre, item.finicio, item.tipoPeriodo, item.cantPeriodo, item.recurrencia);
      }
    }
    this.producto.permiso = true;
    this.permiso.previsionExt = this.checkModal;
    this.producto.idEmpresa == 0 ? this.producto.idEmpresa = SessionUser.getInstance().getidEmpresaActual() : null;
    this.permiso.producto = this.producto;
    this.elaboracion.correos = this.listCorreosElaboracion;
    let auxElaboracion = Object.assign({}, this.elaboracion);

    this.sometimiento.notificaciones = this.listNotificacionesSometimiento;
    for (let item of this.sometimiento.notificaciones) {
      item.finicio = this.calculateFInicioNuevo(this.sometimiento.frealizacion, item.tipoPeriodo, item.cantPeriodo);
      if (new Date(permiso.finicio).getTime() > item.finicio.getTime()) {
        this.findInvalidDate(listNotificationError, this.sometimiento.nombre, item.finicio, item.tipoPeriodo, item.cantPeriodo, item.recurrencia);
      }
    }

    this.sometimiento.correos = this.listCorreosSometimiento;
    let auxSometimiento = Object.assign({}, this.sometimiento);

    this.permiso.eventos.push(auxElaboracion);
    this.permiso.eventos.push(auxSometimiento);

    for (let item of this.listEventos) {
      if (new Date(item.frealizacion) > new Date(this.sometimiento.frealizacion)){
        item.frealizacion = new Date(this.sometimiento.frealizacion);
      }
      permiso.eventos.push(item);
      _.forEach(item.notificaciones, notification => {
        notification.finicio = this.calculateFRealizacionNuevo(notification.tipoPeriodo, notification.cantPeriodo, false, item.frealizacion);
        if (new Date(permiso.finicio).getTime() > notification.finicio.getTime()) {
          this.findInvalidDate(listNotificationError, item.nombre, notification.finicio, notification.tipoPeriodo, notification.cantPeriodo, notification.recurrencia);
        }
      });
    }

    permiso.idEmpresa = SessionUser.getInstance().getidEmpresaActual();
    if (listNotificationError.length > 0) {
      /* Hay al menos un registro fuera de rango, mostramos mensaje en UI*/
      this.listNotificationError = listNotificationError.slice();
      return;
    }

    SessionUser.getInstance().setLoading(true);

    DEBUG && console.log(this.elaboracion);
    DEBUG && console.log(this.sometimiento);
    DEBUG && console.log(this.listEventos);
    DEBUG && console.log(permiso);

    // this.agregarPermiso(this.permiso);
  }

  agregarPermiso(permiso) {
    this.permisoService.crearPermiso(permiso)
      .subscribe(
        data =>{
          DEBUG && console.log(data);
          let parametro = new Parametros();
          parametro.id = data;
          parametro.finicio = new Date();
          parametro.ffin = this.permiso.citaCofepris;
          this.actualizarFecha(parametro).then( ()=>{});
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageOk('Has guardado');
          document.getElementById('idOk').style.display = 'block';
          this.irPermisos();
        },
        error =>{
          DEBUG && console.log(error);
          SessionUser.getInstance().setLoading(false);
          this.irPermisos();
        });
  }

  handleValidDates(date, option, index = 0) {
    setTimeout(() => {
      let startTime = this.permiso.finicio;
      startTime.setHours(0, 0, 0, 0);

      let currentDate = new Date(date);
      currentDate.setHours(9, 0, 0, 0);

      if (option === 2 && this.listNotificacionesNuevo.length > 0) {
        const result = [];
        _.forEach(this.listNotificacionesNuevo, o => {
          const startDate = this.calculateFInicioNuevo(this.evento.frealizacion, o.tipoPeriodo, o.cantPeriodo);
          result.push({...o, startDate: startDate, startDateText: `Inicia el ${this.getCustomDate(startDate)}`});
        });
        this.listNotificacionesNuevo = result.slice();
      }

      if (startTime.getTime() <  currentDate.getTime()) {
        switch (option) {
          case 1:
            this.invalidDateCofeprisEdit = false;
            break;
          case 2:
            this.newEventInvalidDates = false;
            break;
          case 3:
            this.eventInvalidDates[index] = false;
            this.calculateFRealizacionEvento(index);
            break;
        }

        return false;

      } else {
        switch (option) {
          case 1:
            this.invalidDateCofeprisEdit = true;
            break;
          case 2:
            this.newEventInvalidDates = true;
            break;
          case 3:
            this.eventInvalidDates[index] = true;
            this.calculateFRealizacionEvento(index);
            break;
        }

        return true;
      }
    });
  }

  clearValidators() {
    this.invalidDateCofeprisEdit = false;
    this.newEventInvalidDates = false;

    let eventInvalidDates = [];
    _.forEach(this.eventInvalidDates, () => {
      eventInvalidDates.push(false);
    });

    this.eventInvalidDates = eventInvalidDates;
  }

  validFullData() {
    this.fullData = !this.invalidDateCofeprisEdit;
    let validate = this.fullData;
    _.forEach(this.eventInvalidDates, o => {
      if (o) {
        validate = false;
        return false;
      }
    });
    this.fullData = validate;
  }

  getCustomDate(dateToFormat) {
    const now: Date = new Date(dateToFormat);
    let date: String;
    const month: number = now.getMonth();
    switch (month) {
      case 0:
        date = now.getDate() + '/Ene/' + now.getFullYear();
        break;
      case 1:
        date = now.getDate() + '/Feb/' + now.getFullYear();
        break;
      case 2:
        date = now.getDate() + '/Mar/' + now.getFullYear();
        break;
      case 3:
        date = now.getDate() + '/Abr/' + now.getFullYear();
        break;
      case 4:
        date = now.getDate() + '/May/' + now.getFullYear();
        break;
      case 5:
        date = now.getDate() + '/Jun/' + now.getFullYear();
        break;
      case 6:
        date = now.getDate() + '/Jul/' + now.getFullYear();
        break;
      case 7:
        date = now.getDate() + '/Ago/' + now.getFullYear();
        break;
      case 8:
        date = now.getDate() + '/Sep/' + now.getFullYear();
        break;
      case 9:
        date = now.getDate() + '/Oct/' + now.getFullYear();
        break;
      case 10:
        date = now.getDate() + '/Nov/' + now.getFullYear();
        break;
      case 11:
        date = now.getDate() + '/Dic/' + now.getFullYear();
        break;
      default:
        break;
    }
    return date;
  }

  isNewEventInvalid() {
    if (this.evento.nombre !== '' && this.listNotificacionesNuevo.length > 0 && this.listCorreosNuevo.length > 0) {
      let invalid = false;
      _.forEach(this.listNotificacionesNuevo, o => {
        if (this.permiso.finicio.getTime() > o.startDate.getTime()) {
          invalid = true;
          return false;
        }
      });
      return invalid;
    }
    return true;
  }

  findInvalidDate(collection, eventName, date, periodType, periodQty, recurrence) {
    const eventIndex = _.findIndex(collection, o => {
      return o.name === eventName;
    });
    if (eventIndex === -1) {
      collection.push({
        name: eventName,
        notifications: [{
          recurrence: `${periodQty} ${periodType} antes / ${recurrence}`,
          date: `Inicia el ${this.getCustomDate(date)}`
        }]
      });
    } else {
      collection[eventIndex].notifications.push({
        recurrence: `${periodQty} ${periodType} antes / ${recurrence}`,
        date: `Inicia el ${this.getCustomDate(date)}`
      });
    }
  }

  async resetAsync() {
    this.loading = true;
    const data: any = await this.getData();
    this.buffer = data.slice();
    this.loading = false;
  }

  handleLoading(value) {
    this.loading = value;
  }


  handleCurrentPage(value) {
    this.currentPage = value;
  }

  getData = (updateData = false) => {
    //SessionUser.getInstance().setLoading(true);
    return new Promise((resolve, reject) => {
      const numItems = Math.floor((window.screen.width - 110) / 225) * 4;
      const size = PAGING_LIMIT < numItems ? numItems : PAGING_LIMIT;

      let params = new Parametros();
      params.condicion = '';
      params.id = SessionUser.getInstance().getidEmpresaActual();
      params.page = this.currentPage;
      params.size = size;

      params.orden = this.defaultSelectedFiltros.id;
      params.busqueda = this.searchTerm;

      this.productsService.fetchProducts(params).subscribe(
        data => {
          SessionUser.getInstance().setLoading(false);
          if (data) {
            const { results, total } = data;
            const result: any[] = this.patchObject(results.slice());
            this.grandTotal = this.currentTotal === 0 && this.grandTotal === 0 && total > 0 ? total : this.grandTotal;
            this.firstItem = result.length > 0 && this.currentPage === 0 ? result[0] : this.firstItem;
            this.currentTotal = total;
            this.loading = false;

            if (updateData) {
              this.buffer = this.buffer.concat(result);
              _.forEach(this.buffer, (o, index) => {
                this.buffer[index].indexxx = index + 1;
              });
            }

            _.forEach(result, (o, index) => {
              result[index].indexxx = index + 1;
            });
            resolve(result.slice());
          } else {
            resolve([]);
          }
        }, err => {
          DEBUG && console.log('@getData: Error ', err);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al obtener los productos');
          resolve([]);
          this.loading = false;
        });
    });
  }

  patchObject(collection): any[] {
    return _.map(collection, o => {
      return {
        ...o,
        selected: false
      };
    })
  }

}
