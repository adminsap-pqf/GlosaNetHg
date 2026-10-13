import {Component, ElementRef, OnInit, ViewChild, AfterViewInit, DoCheck, OnDestroy} from '@angular/core';
import {animate, style, transition, trigger} from '@angular/animations';
import {Router} from '@angular/router';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Eventos} from '../../model/eventos.class';
import {Notificacion} from '../../model/notificacion.class';
import {CorreoEvento} from '../../model/correoEvento.class';
import {Registro} from '../../model/registro.class';
import {HistorialProducto} from '../../model/historialProducto.class';
import {SessionUser} from '../../services/session/session.service';
import {RegistrosService} from '../../services/registros/registros.service';
import {ProductsService} from '../../services/products/products.service';
import {UserBarComponent} from '../core-container/user-bar/user-bar.component';
import {Producto} from '../../model/producto.class';
import {Parametros} from '../../model/parametros.class';
import {GlosarioService} from '../../services/glosario/glosario.service';
import {Usuario} from '../../model/usuario.class';
import {UserService} from '../../services/user/user.service';
import {IPageInfo, VirtualScrollerComponent} from 'ngx-virtual-scroller';
import {DiasHabilesService} from '../../services/general/diasHabiles';
import {UploadFile} from '../../interfaces/uploadFile';
import * as moment from 'moment';
import * as _ from 'lodash';
import {PAGING_LIMIT} from '../../util/common.protocols';

/* Dev Tools */
import {DEBUG} from '../../settings/app.settings';

@Component({
  selector: 'gn-nuevo-registro',
  templateUrl: './nuevo-registro.component.html',
  styleUrls: ['./nuevo-registro.component.scss'],
  animations: [
    trigger('fadeInOut', [
      transition(':enter', [
        style({opacity: 0}),
        animate(400, style({opacity: 1}))
      ]),
      transition(':leave', [
        animate(400, style({opacity: 0}))
      ])
    ])
  ]
})
export class NuevoRegistroComponent implements OnInit, AfterViewInit, DoCheck, OnDestroy {

  @ViewChild("body1", {static: false}) body1: ElementRef;

  @ViewChild(VirtualScrollerComponent, {static: false})
  private virtualScroller: VirtualScrollerComponent;

  @ViewChild('modalUpload', {static: false}) modalUpload: ElementRef;
  @ViewChild('fileInput', {static: false}) fileInput: any;


  autor: string = SessionUser.getInstance().getNombreCompleto();

  public users: any = [{nombre: 'Cada Dia'}];

  validEmailDossier = false;
  validarDias2Dossier = false;
  validarDias1Dossier = false;

  validEmailTecno = false;
  validarDias2Tecno = false;
  validarDias1Tecno = false;

  validEmailSom = false;
  validarDias2Som = false;
  validarDias1Som = false;

  arrayValidEmail = [];
  arrayValidDias2 = [];
  arrayValidDias1 = [];

  validEmailPro = false;
  validarDias2Pro = false;
  validarDias1Pro = false;

  validEmailNuevo = false;
  validarDias2Nuevo = false;
  validarDias1Nuevo = false;

  gEventosForm1: FormGroup;
  gEventosForm2: FormGroup;
  modalForm: FormGroup;

  placeholder: any = 'Buscar';
  searchTerm = '';
  resetSearch: boolean;
  arrayAux: any = [];
  search: any = [];

  /* DropList */
  public itemsDropList: any[] = [{nombre: 'Ejemplo1'}, {nombre: 'Ejemplo2'}, {nombre: 'Ejemplo3'}];
  defaultSelected: any;

  public itemsFiltros: any[] = [
    {nombre: 'Nombre (A-Z)', id: 0},
    {nombre: 'Código', id: 2}
  ];
  defaultSelectedFiltros: any = {nombre: 'Nombre (A-Z)', id: 0};
  // 0 = Nombre - Ascendente
  // 1 = Nombre - Descendente
  // 2 = Código - Ascendente
  // 3 = Código - Descendente

  public itemsDropList1: any[] = [{nombre: 'Ejemplo1'}, {nombre: 'Ejemplo2'}, {nombre: 'Ejemplo3'}];
  defaultSelected1: any;

  public dropListPeriodoTiempo: any[] = [{nombre: 'Días'}, {nombre: 'Semanas'}, {nombre: 'Meses'}, {nombre: 'Años'}];

  public dropListNotificacion: any[] = [{nombre: 'Cada Día'}, {nombre: 'Cada Semana'}, {nombre: 'Cada Mes'}, {nombre: 'Cada Año'}];

  /* reset droplistNuevo */
  resetNuevo: Array<any> = new Array<any>(3);

  cuadricula: boolean = false;

  idProducto: number = 0;

  lst: any[] = [];

  classItem: any[];
  classItem1: any[];

  generarEventEffect: string = 'body-generarE-close';


  lstItems: string [] = ['Seleccionar Producto', 'Publicar Registro'];
  pWizard: any = 0; // TODO: Testing iniciar con el paso 2: pWizard: any = 1;
  pWizard2: any = 0;

  nombreProducto: string = '';
  defaultOrigen: any = '';

  ttProducto: any = 0;

  /* Efectos de los 3 eventos por default */
  itemEventEffect: any = new Array(3).fill('body-itemE-close');

  /* Efectos de los nuevos eventos creados */
  itemNewEventEffect: any = new Array();/*.fill('body-itemE-close'); */

  items: Array<Eventos> = new Array<Eventos>();

  check: boolean = false;

  classProrroga: string = 'body-prorroga-close';

  classAnimation = 'w3-animate-zoom-out';
  classAnimation2 = 'w3-animate-zoom-out';
  classAnimation3 = 'w3-animate-zoom-out';
  classAnimation4 = 'w3-animate-zoom-out';

  public paises: any[] = [{nombre: 'Cada Dia'}];


  productoSelect: boolean = false;

  nombreConfirmEliminar: string = '';
  indexEventEliminar: number = 0;

  listCorreoT: any[] = [];


  /* Datepicker */
  date: Date = null;

  /* Lista de eventos nuevos */
  listEventos: Array<Eventos> = new Array<Eventos>();

  /* Objetos pirncipales para guardar los nuevos eventos y sus listas, reset cada que se aplica un agregar o generar */
  registro: Registro = new Registro();
  evento: Eventos = new Eventos();
  notificaciones: Notificacion = new Notificacion();
  correos: CorreoEvento = new CorreoEvento();
  producto: Producto = new Producto();


  listNotificacionesNuevo: Array<Notificacion> = new Array<Notificacion>();
  listCorreosNuevo: Array<CorreoEvento> = new Array<CorreoEvento>();


  dropListTipoPeriodo: Array<any> = new Array<any>();
  dropListRecurrencia: Array<any> = new Array<any>();
  diasInput: Array<number> = new Array<number>();
  correoInput: Array<string> = new Array<string>();

  dossier: Eventos = new Eventos();
  tecnovigilancia: Eventos = new Eventos();
  sometimiento: Eventos = new Eventos();
  prorroga: Eventos = new Eventos();

  /* **********************************************+ dossier **********************************************+ */

  dia1Dossier: number = 0;
  correoDossier: string = '';
  listNotificacionesDossier: Array<Notificacion> = new Array<Notificacion>();
  listCorreosDossier: Array<CorreoEvento> = new Array<CorreoEvento>();
  resetDossier: Array<any> = new Array<any>(3);

  /* **********************************************+ Tecnovigilancia **********************************************+ */
  dia1Tecno: number = 0;
  correoTecno: string = '';
  listNotificacionesTecno: Array<Notificacion> = new Array<Notificacion>();
  listCorreosTecno: Array<CorreoEvento> = new Array<CorreoEvento>();
  resetTecno: Array<any> = new Array<any>(3);

  /* **********************************************+ Sometimiento **********************************************+ */
  dia1Som: number = 0;
  correoSom: string = '';
  listNotificacionesSom: Array<Notificacion> = new Array<Notificacion>();
  listCorreosSom: Array<CorreoEvento> = new Array<CorreoEvento>();
  resetSom: Array<any> = new Array<any>(3);

  /* **********************************************+ Prorroga **********************************************+ */
  dia1Prorro: number = 0;
  correoProrro: string = '';
  listNotificacionesProrro: Array<Notificacion> = new Array<Notificacion>();
  listCorreosProrro: Array<CorreoEvento> = new Array<CorreoEvento>();
  resetProrro: Array<any> = new Array<any>(3);

  indexModal: number = 0;
  fullData: boolean = false;

  loading = true;
  scrollItems: any = [];
  buffer: any[] = [];
  timer;

  indices: any;

  /* Bandera para habilitar Eventos */
  enableDossier: boolean = false;
  enableTecnovigilancia: boolean = false;
  enableSometimiento: boolean = false;

  /* Fecha de inicio y fin del evento */
  startDate: Date = null;
  endDate: Date = null;

  /* Valid data */
  activeInvalidData: any = {
    name: false,
    tramiteCofepris: false,
    folioRegistro: false,
    description: false,
    codeProduct: false,
    origin: false,
    user: false,
    startDate: false,
    endDate: false
  };

  /* Variable para setear valores de droplist */
  clear: boolean = false;
  resetValidate: boolean = false;

  /* Lista de notificaciones que se encuentra fuera de tiempo */
  listNotificationError: any = [];

  /* Respaldo de configuración (tipo de periodo) para restablecer
  *  los valores cuando se cancela la selección del producto
  *
  * */
  backupDossier: Eventos = new Eventos();
  backupTecnovigilancia: Eventos = new Eventos();
  backupSometimiento: Eventos = new Eventos();

  today: Date = new Date();
  lodash = _;

  contentIsOpenUpload = false;
  modalIsOpenUpload = false;
  fileUpload: UploadFile;
  listFilesToUpload = [];
  listFiles = [];

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
    private userBar: UserBarComponent,
    private glosarioService: GlosarioService,
    private userService: UserService,
    private diasHabilesService: DiasHabilesService) { }

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

    this.modalForm = new FormGroup({
      folioRegistro: new FormControl('', [Validators.required]),
      tramiteCofrepis: new FormControl('', [Validators.required]),
    });

    this.dossier.nombre = 'Dossier';
    this.dossier.definido = true;
    this.tecnovigilancia.nombre = 'Tecnovigilancia';
    this.tecnovigilancia.definido = true;
    this.sometimiento.nombre = 'Sometimiento';
    this.sometimiento.definido = true;
    this.prorroga.nombre = 'Prorroga';
    this.prorroga.definido = true;

    this.registro.usuario = new Usuario();
    /*
     * TODO: Testing iniciar con el paso 2
     *
    this.registro.descripcion = 'Testing';
    this.registro.finicio = new Date();
    this.registro.ffin = new Date(new Date().getTime() + 100000000000);
    */

    //this.registro.usuario.idUsuario = SessionUser.getInstance().getUserID();

    this.gettiempoRealizacion();
    // this.getProductos(); TODO: Versión anterior del endpoint
    this.obtenerPaises();
    this.defaultValuesEvent();
    this.buscarUsuarios();

    this.today = new Date(this.today.getFullYear(), this.today.getMonth(), this.today.getDate() + 1, 0, 0, 0);

    this.fileUpload = {
      file: null,
      fileName: '',
      description: '',
      preview: ''
    };
  }

  async ngAfterViewInit() {
    // TODO: Mover el elemento al final de la página (antes de</body>), para que se pueda visualizar sobre todo lo demás
    document.body.appendChild(this.modalUpload.nativeElement);

    this._isMounted = true; // TODO: Evitamos llamadas innecesarias de los droplist cuando aun el componente no esta montado
    this.loading = true;
    const data:any = await this.getData(); // Vamos por la primer página
    this.buffer = data.slice();
    this.loading = false;
  }

  ngDoCheck() {

    if (this.pWizard === 1) {
      this.validateCompleteData();
    }
  }

  ngOnDestroy() {
    // TODO: Eliminar los elementos que fueron  movidos al final
    if (this.modalIsOpenUpload) {
      this.modalUpload.nativeElement.remove();
    }
  }

  /* Busqueda de search bar */
  changeSearchTerm($event) {
    this.searchTerm = $event;
    this.currentPage = 0;
    this.reset();
  }


  /* ------------------------------------------------------------Reset Forms-------------------------------------------------------------- */

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
        this.modalForm.patchValue({});
        break;
      default:
        break;
    }
  }


  resetAll() {
    this.resetForms(1);
    this.resetForms(2);
    this.resetForms(3);
    this.resetForms(4);
    this.resetForms(5);
    this.dossier.reset();
    this.tecnovigilancia.reset();
    this.sometimiento.reset();
    this.prorroga.reset();
    this.check = false;
    this.registro.reset();
    this.evento.reset();
    this.notificaciones.reset();
    this.correos.reset();
    this.producto.reset();


    this.listNotificacionesNuevo = [];
    this.listCorreosNuevo = [];

    this.resetDroplistNuevo('Nombre');
    this.resetDroplistNuevo('');


    this.resetDroplistDossier('Nombre');
    this.resetDroplistDossier('');

    this.resetDroplistTecno('Nombre');
    this.resetDroplistTecno('');

    this.resetDroplistSom('Nombre');
    this.resetDroplistSom('');

    this.resetDroplistProrro('Nombre');
    this.resetDroplistProrro('');
  }

  resetCustom() {
    this.resetForms(1);
    this.resetForms(2);
    this.resetForms(3);
    this.resetForms(4);
    this.resetForms(5);

    this.dossier.reset();
    this.dossier.nombre = 'Dossier';
    this.dossier.definido = true;
    this.dossier.cantPeriodo = this.backupDossier.cantPeriodo;
    this.dossier.tipoPeriodo = this.backupDossier.tipoPeriodo;
    this.listNotificacionesDossier = [];
    this.listCorreosDossier = [];

    this.tecnovigilancia.reset();
    this.tecnovigilancia.nombre = 'Tecnovigilancia';
    this.tecnovigilancia.definido = true;
    this.tecnovigilancia.cantPeriodo = this.backupTecnovigilancia.cantPeriodo;
    this.tecnovigilancia.tipoPeriodo = this.backupTecnovigilancia.tipoPeriodo;
    this.listNotificacionesTecno = [];
    this.listCorreosTecno = [];

    this.sometimiento.reset();
    this.sometimiento.nombre = 'Sometimiento';
    this.sometimiento.definido = true;
    this.sometimiento.cantPeriodo = this.backupSometimiento.cantPeriodo;
    this.sometimiento.tipoPeriodo = this.backupSometimiento.tipoPeriodo;
    this.listNotificacionesSom = [];
    this.listCorreosSom = [];

    this.prorroga.reset();
    this.prorroga.nombre = 'Prorroga';
    this.prorroga.definido = true;

    this.listEventos = [];

    this.listNotificacionesProrro = [];
    this.listCorreosProrro = [];
    this.classProrroga = 'body-prorroga-close';

    this.check = false;
    this.registro.reset();
    this.registro.usuario = new Usuario();
    this.evento.reset();
    this.notificaciones.reset();
    this.correos.reset();
    this.producto.reset();
    this.listNotificacionesNuevo = [];
    this.listCorreosNuevo = [];

    /* Nuevos valores */
    this.enableDossier = false;
    this.enableSometimiento = false;
    this.enableTecnovigilancia = false;
    this.listNotificationError = [];
    this.startDate = null;
    this.endDate = null;
    this.activeInvalidData = {
      name: false,
      tramiteCofepris: false,
      folioRegistro: false,
      description: false,
      codeProduct: false,
      origin: false,
      user: false,
      startDate: false,
      endDate: false
    };

    this.generarEventEffect = 'body-generarE-close';
    this.itemEventEffect = new Array(3).fill('body-itemE-close');

    this.clear = true;
    this.resetValidate = true;
    setTimeout(() => {
      this.clear = false;
      this.resetValidate = false;
    }, 500);
  }

  /* ------------------------------------------------------------ /Reset Forms-------------------------------------------------------------- */

  /* ------------------------------------------------------------Drop list-------------------------------------------------------------- */
  dropListPeriodo($event, index) {
    this.dropListTipoPeriodo[index] = $event;
  }

  dropListRecu($event, index) {
    this.dropListRecurrencia[index] = $event;
  }

  droplistNuevo(index, $event) {
    switch (index) {
      case 1: // Nombre del evento nuevo
        this.evento.tipoPeriodo = $event.nombre;
        this.evento.frealizacion = this.calculateFRealizacionNuevo(this.evento.tipoPeriodo, this.evento.cantPeriodo);
        if (this.evento.cantPeriodo > 0 && this.evento.tipoPeriodo !== '' && this.listNotificacionesNuevo.length > 0) {
          const result = [];
          _.forEach(this.listNotificacionesNuevo, o => {
            const startDate = this.calculateFInicioNuevo(this.evento.frealizacion, o.tipoPeriodo, o.cantPeriodo);
            result.push({...o, startDate: startDate, startDateText: `Inicia el ${this.getCustomDate(startDate)}`});
          });
          this.listNotificacionesNuevo = result.slice();
        }
        break;
      case 2: // Periodo de Tiempo dias, semanas, meses, años nuevo evento
        this.notificaciones.tipoPeriodo = $event.nombre;
        this.notificaciones.finicio = this.gEventosForm1.get('dias2').value > 0 ? this.calculateFRealizacionNuevo(this.notificaciones.tipoPeriodo, this.notificaciones.cantPeriodo) : null;
        break;
      case 3: // Notificar nuevo evento
        this.notificaciones.recurrencia = $event.nombre;
        break;
      default:
        break;
    }
  }


  resetDroplistNuevo(seccion: string) {
    if (seccion == 'Nombre') {
      this.resetNuevo[0] = {nombre: ''}
    } else {
      this.resetNuevo[1] = {nombre: ''};
      this.resetNuevo[2] = {nombre: ''};
    }
  }

  resetDroplistDossier(seccion: string) {
    if (seccion == 'Nombre') {
      this.resetDossier[0] = {nombre: 'Días'}
    } else {
      this.resetDossier[1] = {nombre: ''};
      this.resetDossier[2] = {nombre: ''};
    }
  }

  resetDroplistTecno(seccion: string) {
    if (seccion == 'Nombre') {
      this.resetTecno[0] = {nombre: 'Días'}
    } else {
      this.resetTecno[1] = {nombre: ''};
      this.resetTecno[2] = {nombre: ''};
    }
  }

  resetDroplistSom(seccion: string) {
    if (seccion == 'Nombre') {
      this.resetSom[0] = {nombre: 'Días'}
    } else {
      this.resetSom[1] = {nombre: ''};
      this.resetSom[2] = {nombre: ''};
    }
  }

  resetDroplistProrro(seccion: string) {
    if (seccion == 'Nombre') {
      this.resetProrro[0] = {nombre: 'Días'}
    } else {
      this.resetProrro[1] = {nombre: ''};
      this.resetProrro[2] = {nombre: ''};
    }
  }

  dropListDossier($event, index) {
    switch (index) {
      case 1: // Nombre del evento nuevo
        this.dossier.tipoPeriodo = $event.nombre;
        this.resetDossier[0] = $event;
        this.calculateFRealizacion(1);
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

  dropListTecno($event, index) {
    switch (index) {
      case 1: // Nombre del evento nuevo
        this.tecnovigilancia.tipoPeriodo = $event.nombre;
        this.resetTecno[0] = $event;
        this.calculateFRealizacion(2);
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

  dropListSom($event, index) {
    switch (index) {
      case 1: // Nombre del evento nuevo
        this.sometimiento.tipoPeriodo = $event.nombre;
        this.resetSom[0] = $event;
        this.calculateFRealizacion(3);
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

  dropListProrro($event, index) {
    switch (index) {
      case 1: // Nombre del evento nuevo
        this.prorroga.tipoPeriodo = $event.nombre;
        this.resetProrro[0] = $event;
        this.calculateFRealizacion(4);
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

  dropListFiltro($event) {
    this.defaultSelectedFiltros = $event;
    this.currentPage = 0;
    this.reset();
  }


  dropListTiempoR($event, i) {
    this.listEventos[i].tipoPeriodo = $event.nombre;
    this.calculateFRealizacionEvento(i);
  }

  /* ------------------------------------------------------------ /Drop list-------------------------------------------------------------- */

  defaultValuesEvent() {
    this.resetNuevo.fill({nombre: ''});
    this.resetDossier.fill({nombre: ''});
    this.resetDossier[1] = {nombre: ''};
    this.resetDossier[2] = {nombre: ''};
    this.resetTecno.fill({nombre: ''});
    this.resetTecno[1] = {nombre: ''};
    this.resetTecno[2] = {nombre: ''};
    this.resetSom.fill({nombre: ''});
    this.resetSom[1] = {nombre: ''};
    this.resetSom[2] = {nombre: ''};
    this.resetProrro.fill({nombre: ''});
    this.resetProrro[1] = {nombre: ''};
    this.resetProrro[2] = {nombre: ''};
  }

  irLibreriaRegistros() {
    this.confirm(false, 0);
    SessionUser.getInstance().setConfirm(false);
    this.router.navigate(["protected/libreriaRegistros"]);
  }

  confirm(open: boolean, index: number) {
    SessionUser.getInstance().setConfirm(true);
    this.indexModal = index;
    if (open) {
      this.classAnimation3 = 'w3-animate-zoom';
      document.getElementById('idConfirm').style.display = 'block'
    } else {
      this.classAnimation3 = 'w3-animate-zoom-out';
      document.getElementById('idConfirm').style.display = 'none';
    }
  }


  confirmarEliminar(i, nombre) {
    this.nombreConfirmEliminar = nombre;
    this.indexEventEliminar = i;
    this.classAnimation4 = 'w3-animate-zoom';
    document.getElementById('idEliminarEv').style.display = 'block';
  }


  back() {
    this.confirm(false, 1);
    SessionUser.getInstance().setConfirm(false);
    this.resetCustom();
    if (this.pWizard > 0) {
      this.pWizard--;
    }
    DEBUG && console.log(this.pWizard);
  }

  next() {
    if (this.pWizard < (this.lstItems.length - 1)) {
      this.pWizard++;
    }
    this.producto.idEmpresa = SessionUser.getInstance().getidEmpresaActual();
    this.producto.idProducto = this.idProducto;
    DEBUG && console.log(this.producto);
    DEBUG && console.log(this.pWizard);
  }

  selectItem(item) {
    this.idProducto = item.idProducto !== undefined ? item.idProducto : 0;
    this.producto.nombre = item.nombre;
    this.producto.codigo = item.codigo;
    this.producto.origen = item.origen;
    this.defaultOrigen = {nombre: this.producto.origen};
    this.buffer = _.map(this.buffer, o => {
      return {
        ...o,
        selected: o.idProducto === item.idProducto
      }
    });
    this.seleccionarProducto();
  }

  checkF($event) {
    $event.stopPropagation();
    this.check = !this.check;
  }


  nuevoProducto() {
    this.classAnimation = 'w3-animate-zoom';
    this.producto.nombre = '';
    this.producto.codigo = '';
    this.producto.origen = '';
    this.defaultOrigen = {nombre: ''};

    this.registro.finicio = new Date();
    this.registro.ffin = null;
    this.registro.tramiteCofepris = '';
    this.registro.folioRegistro = '';
    this.registro.descripcion = '';
  }

  cerrarProducto(removeUser) {
    this.classAnimation2 = 'w3-animate-zoom-out';
    document.getElementById('id02').style.display = 'none';

    if (removeUser)
      this.registro.usuario = new Usuario();

    this.activeInvalidData = {
      name: false,
      tramiteCofepris: false,
      folioRegistro: false,
      description: false,
      codeProduct: false,
      origin: false,
      user: false,
      startDate: false,
      endDate: false
    };
    this.clear = true;
    this.resetValidate = true;
    setTimeout(() => {
      this.clear = false;
      this.resetValidate = false;
    }, 500);
  }

  seleccionarProducto() {
    this.classAnimation2 = 'w3-animate-zoom';
    document.getElementById('id02').style.display = 'block';


    this.registro.finicio = null;
    this.registro.ffin = null;
    this.registro.tramiteCofepris = '';
    this.registro.folioRegistro = '';
    this.registro.descripcion = '';
  }

  cerrarSeleccionarProducto(removeUser) {
    this.classAnimation2 = 'w3-animate-zoom-out';
    document.getElementById('id02').style.display = 'none';

    if (removeUser)
      this.registro.usuario = new Usuario();

    /* Setear Validaciones */
    this.startDate = null;
    this.endDate = null;
    this.activeInvalidData = {
      name: false,
      tramiteCofepris: false,
      folioRegistro: false,
      description: false,
      codeProduct: false,
      origin: false,
      user: false,
      startDate: false,
      endDate: false
    };
    this.clear = true;
    this.resetValidate = true;
    setTimeout(() => {
      this.clear = false;
      this.resetValidate = false;
    }, 500);
  }

  getFechaImpl($event) {
    DEBUG && console.log($event);
  }

  calculateFRealizacion(index) {

    switch (index) {
      case 1:
        this.dossier.frealizacion = this.diasHabilesService.getDate(this.registro.ffin, this.dossier.tipoPeriodo, this.dossier.cantPeriodo);
        if (this.dossier.cantPeriodo > 0 && this.dossier.tipoPeriodo !== '' && this.listNotificacionesDossier.length > 0) {
          const result = [];
          _.forEach(this.listNotificacionesDossier, o => {
            const startDate = this.calculateFInicioNuevo(this.dossier.frealizacion, o.tipoPeriodo, o.cantPeriodo);
            result.push({...o, startDate: startDate, startDateText: `Inicia el ${this.getCustomDate(startDate)}`});
          });
          this.listNotificacionesDossier = result.slice();
        }
        break;

      case 2:
        this.tecnovigilancia.frealizacion = this.diasHabilesService.getDate(this.registro.ffin, this.tecnovigilancia.tipoPeriodo, this.tecnovigilancia.cantPeriodo);
        if (this.tecnovigilancia.cantPeriodo > 0 && this.tecnovigilancia.tipoPeriodo !== '' && this.listNotificacionesTecno.length > 0) {
          const result = [];
          _.forEach(this.listNotificacionesTecno, o => {
            const startDate = this.calculateFInicioNuevo(this.tecnovigilancia.frealizacion, o.tipoPeriodo, o.cantPeriodo);
            result.push({...o, startDate: startDate, startDateText: `Inicia el ${this.getCustomDate(startDate)}`});
          });
          this.listNotificacionesTecno = result.slice();
        }
        break;

      case 3:
        this.sometimiento.frealizacion = this.diasHabilesService.getDate(this.registro.ffin, this.sometimiento.tipoPeriodo, this.sometimiento.cantPeriodo);
        if (this.sometimiento.cantPeriodo > 0 && this.sometimiento.tipoPeriodo !== '' && this.listNotificacionesSom.length > 0) {
          const result = [];
          _.forEach(this.listNotificacionesSom, o => {
            const startDate = this.calculateFInicioNuevo(this.sometimiento.frealizacion, o.tipoPeriodo, o.cantPeriodo);
            result.push({...o, startDate: startDate, startDateText: `Inicia el ${this.getCustomDate(startDate)}`});
          });
          this.listNotificacionesSom = result.slice();
        }
        break;

      case 4:
        this.prorroga.frealizacion = this.diasHabilesService.getDate(this.registro.ffin, this.prorroga.tipoPeriodo, this.prorroga.cantPeriodo);
        if (this.prorroga.cantPeriodo > 0 && this.prorroga.tipoPeriodo !== '' && this.listNotificacionesProrro.length > 0) {
          const result = [];
          _.forEach(this.listNotificacionesProrro, o => {
            const startDate = this.calculateFInicioNuevo(this.prorroga.frealizacion, o.tipoPeriodo, o.cantPeriodo);
            result.push({...o, startDate: startDate, startDateText: `Inicia el ${this.getCustomDate(startDate)}`});
          });
          this.listNotificacionesProrro = result.slice();
        }
        break;

      default:
        break;
    }
  }

  calculateFRealizacionEvento(index) {
    this.listEventos[index].frealizacion = this.diasHabilesService.getDate(this.registro.ffin, this.listEventos[index].tipoPeriodo, this.listEventos[index].cantPeriodo);

    if (this.listEventos[index].cantPeriodo > 0 && this.listEventos[index].tipoPeriodo !== '' && this.listEventos[index].notificaciones.length > 0) {
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
    date = this.diasHabilesService.getDate(!customDate ? this.registro.ffin : customDate, periodo, cantidad);

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

  /* ******************************************************  Botones agregar default  ****************************************************** */
  agregarNotificacion(index: any) {
    if (this.diasInput[index] !== 0 && this.dropListTipoPeriodo[index].nombre !== '' && this.dropListRecurrencia[index].nombre !== '') {
      this.notificaciones.tipoPeriodo = this.dropListTipoPeriodo[index].nombre;
      this.notificaciones.cantPeriodo = this.diasInput[index];
      this.notificaciones.recurrencia = this.dropListRecurrencia[index].nombre;
      this.notificaciones.startDate = this.calculateFInicioNuevo(this.listEventos[index].frealizacion, this.notificaciones.tipoPeriodo, this.notificaciones.cantPeriodo);
      this.notificaciones.startDateText = `Inicia el ${this.getCustomDate(this.notificaciones.startDate)}`;
      let aux = Object.assign({}, this.notificaciones);
      this.listEventos[index].notificaciones.push(aux);
      this.notificaciones.reset();

      this.arrayValidDias2[index] = false;
      this.dropListRecurrencia[index] = {nombre: ''};
      this.dropListTipoPeriodo[index] = {nombre: ''};

      this.diasInput[index] = 0;
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
      for (let item of this.listEventos[index].correos) {
        if (item.correo === this.correoInput[index]) {
          flag = true;
          break;
        }
      }

      if (!flag) {
        this.correos.correo = this.correoInput[index];
        let aux = Object.assign({}, this.correos);
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
    this.borrarCorreosTotal();
    DEBUG && console.log(this.listEventos);
  }

  /* ******************************************************  Botones agregar default  ****************************************************** */

  /* ****************************** Nuevo evento llenar y borrar ******************************  */
  agregarNuevoNotificacion() {
    if (this.gEventosForm1.get('dias2').value !== 0 && this.notificaciones.tipoPeriodo !== '' && this.notificaciones.recurrencia !== '') {
      this.notificaciones.cantPeriodo = this.gEventosForm1.get('dias2').value;
      this.notificaciones.startDate = this.calculateFInicioNuevo(this.evento.frealizacion, this.notificaciones.tipoPeriodo, this.notificaciones.cantPeriodo);
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
      for (let item of this.listCorreosNuevo) {
        if (item.correo === this.gEventosForm2.get('correoN').value) {
          flag = true;
          break;
        }
      }
      if (!flag) {
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
    if (this.evento.cantPeriodo !== 0 && this.evento.nombre !== '' && this.evento.tipoPeriodo !== '' && this.listNotificacionesNuevo.length > 0 && this.listCorreosNuevo.length > 0) {
      let auxCorreo = Object.assign([], this.listCorreosNuevo);
      let auxNotificaciones = Object.assign([], this.listNotificacionesNuevo);
      this.evento.correos = auxCorreo;
      this.evento.notificaciones = auxNotificaciones;
      this.evento.frealizacion = this.calculateFRealizacionNuevo(this.evento.tipoPeriodo, this.evento.cantPeriodo);

      for (let item of this.evento.notificaciones) {
        item.finicio = this.calculateFInicioNuevo(this.evento.frealizacion, item.tipoPeriodo, item.cantPeriodo);
      }

      for (let item of this.evento.correos) {
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
      this.validarDias1Nuevo = false;

      this.agregarCamposNuevoevento();
      this.generarEventEffect = 'body-generarE-close';
    }
    DEBUG && console.log("******************+ lista eventos ***************");
    DEBUG && console.log(this.listEventos);
  }


  agregarCamposNuevoevento() {
    this.dropListTipoPeriodo.push({nombre: ''});
    this.dropListRecurrencia.push({nombre: ''});

    this.diasInput.push(0);
    this.correoInput.push('');

    this.arrayValidEmail.push(false);
    this.arrayValidDias2.push(false);
    this.arrayValidDias1.push(false);
  }

  borrarEvento(index: number) {
    this.listEventos.splice(index, 1);

    this.diasInput.splice(index, 1);

    /* TODO: Esta linea elimina directamente opciones del droplist */
    // this.dropListPeriodoTiempo.splice(index, 1);

    this.dropListTipoPeriodo.splice(index, 1);
    this.dropListRecurrencia.splice(index, 1);

    this.classAnimation4 = 'w3-animate-zoom-out';
    document.getElementById('idEliminarEv').style.display = 'none';
    DEBUG && console.log(this.listEventos);
  }

  /*  ****************************** Dossier Agregar y borrar  ****************************** */
  agregarNotificacionDossier() {
    if (this.dia1Dossier !== 0 && this.notificaciones.tipoPeriodo !== '' && this.notificaciones.recurrencia !== '') {
      this.notificaciones.cantPeriodo = this.dia1Dossier;
      this.notificaciones.startDate = this.calculateFInicioNuevo(this.dossier.frealizacion, this.notificaciones.tipoPeriodo, this.notificaciones.cantPeriodo);
      this.notificaciones.startDateText = `Inicia el ${this.getCustomDate(this.notificaciones.startDate)}`;
      let aux = Object.assign({}, this.notificaciones);
      this.listNotificacionesDossier.push(aux);
      this.notificaciones.reset();
      this.resetDroplistDossier('Notificacion');
      this.dia1Dossier = 0;
      this.validarDias2Dossier = false;
    }
    DEBUG && console.log(this.listNotificacionesDossier);
  }

  borrarDossierNotificacion(data: number) {
    this.listNotificacionesDossier.splice(data, 1);
    DEBUG && console.log("******************+ lista Dossier notificaciones ***************");
    DEBUG && console.log(this.listNotificacionesDossier);
  }

  agregarDossierCorreo() {
    if (this.correoDossier !== '') {
      let flag = false;
      for (let item of this.listCorreosDossier) {
        if (item.correo === this.correoDossier) {
          flag = true;
          break;
        }
      }
      if (!flag) {
        this.correos.correo = this.correoDossier;
        let aux = Object.assign({}, this.correos);
        this.listCorreosDossier.push(aux);

        this.listCorreosTotal(this.correoDossier);
      }
      this.correos.reset();
      this.correoDossier = '';
      this.validEmailDossier = false;
    }
    DEBUG && console.log(this.listCorreosDossier);
  }

  borrarDossierCorreo(data: number) {
    this.listCorreosDossier.splice(data, 1);
    DEBUG && console.log("******************+ lista Dossier correos ***************");
    this.borrarCorreosTotal();
    DEBUG && console.log(this.listCorreosDossier);
  }

  /*  ****************************** Tecnovigilancia Agregar y borrar  ****************************** */
  agregarNotificacionTecno() {
    if (this.dia1Tecno !== 0 && this.notificaciones.tipoPeriodo !== '' && this.notificaciones.recurrencia !== '') {
      this.notificaciones.cantPeriodo = this.dia1Tecno;
      this.notificaciones.startDate = this.calculateFInicioNuevo(this.tecnovigilancia.frealizacion, this.notificaciones.tipoPeriodo, this.notificaciones.cantPeriodo);
      this.notificaciones.startDateText = `Inicia el ${this.getCustomDate(this.notificaciones.startDate)}`;
      let aux = Object.assign({}, this.notificaciones);
      this.listNotificacionesTecno.push(aux);
      this.notificaciones.reset();
      this.resetDroplistTecno('Notificacion');
      this.dia1Tecno = 0;
      this.validarDias2Tecno = false;
    }
    DEBUG && console.log(this.listNotificacionesTecno);
  }

  borrarTecnoNotificacion(data: number) {
    this.listNotificacionesTecno.splice(data, 1);
    DEBUG && console.log("******************+ lista Tecnovigilancia notificaciones ***************");
    DEBUG && console.log(this.listNotificacionesTecno);
  }

  agregarTecnoCorreo() {
    if (this.correoTecno !== '') {
      let flag = false;
      for (let item of this.listCorreosTecno) {
        if (item.correo === this.correoTecno) {
          flag = true;
          break;
        }
      }
      if (!flag) {
        this.correos.correo = this.correoTecno;
        let aux = Object.assign({}, this.correos);
        this.listCorreosTecno.push(aux);

        this.listCorreosTotal(this.correoTecno);
      }
      this.correos.reset();
      this.correoTecno = '';
      this.validEmailTecno = false;
    }
    DEBUG && console.log(this.listCorreosTecno);
  }

  borrarTecnoCorreo(data: number) {
    this.listCorreosTecno.splice(data, 1);
    DEBUG && console.log("******************+ lista Tecnovigilancia correos ***************");
    this.borrarCorreosTotal();
    DEBUG && console.log(this.listCorreosTecno);
  }

  /*  ****************************** Sometimiento Agregar y borrar  ****************************** */
  agregarNotificacionSom() {
    if (this.dia1Som !== 0 && this.notificaciones.tipoPeriodo !== '' && this.notificaciones.recurrencia !== '') {
      this.notificaciones.cantPeriodo = this.dia1Som;
      this.notificaciones.startDate = this.calculateFInicioNuevo(this.sometimiento.frealizacion, this.notificaciones.tipoPeriodo, this.notificaciones.cantPeriodo);
      this.notificaciones.startDateText = `Inicia el ${this.getCustomDate(this.notificaciones.startDate)}`;
      let aux = Object.assign({}, this.notificaciones);
      this.listNotificacionesSom.push(aux);
      this.notificaciones.reset();
      this.resetDroplistSom('Notificacion');
      this.dia1Som = 0;
      this.validarDias2Som = false;
    }
    DEBUG && console.log(this.listNotificacionesSom);
  }

  borrarSomNotificacion(data: number) {
    this.listNotificacionesSom.splice(data, 1);
    DEBUG && console.log("******************+ lista Sometimiento notificaciones ***************");
    DEBUG && console.log(this.listNotificacionesSom);
  }

  agregarSomCorreo() {
    if (this.correoSom !== '') {
      let flag = false;
      for (let item of this.listCorreosSom) {
        if (item.correo === this.correoSom) {
          flag = true;
          break;
        }
      }
      if (!flag) {
        this.correos.correo = this.correoSom;
        let aux = Object.assign({}, this.correos);
        this.listCorreosSom.push(aux);

        this.listCorreosTotal(this.correoSom);
      }
      this.correos.reset();
      this.correoSom = '';
      this.validEmailSom = false;
    }
    DEBUG && console.log(this.listCorreosSom);
  }

  borrarSomCorreo(data: number) {
    this.listCorreosSom.splice(data, 1);
    DEBUG && console.log("******************+ lista Sometimiento correos ***************");
    this.borrarCorreosTotal();
    DEBUG && console.log(this.listCorreosSom);
  }

  /*  ****************************** Prorroga Agregar y borrar  ****************************** */
  agregarNotificacionProrro() {
    if (this.dia1Prorro !== 0 && this.notificaciones.tipoPeriodo !== '' && this.notificaciones.recurrencia !== '') {
      this.notificaciones.cantPeriodo = this.dia1Prorro;
      this.notificaciones.startDate = this.calculateFInicioNuevo(this.prorroga.frealizacion, this.notificaciones.tipoPeriodo, this.notificaciones.cantPeriodo);
      this.notificaciones.startDateText = `Inicia el ${this.getCustomDate(this.notificaciones.startDate)}`;
      let aux = Object.assign({}, this.notificaciones);
      this.listNotificacionesProrro.push(aux);
      this.notificaciones.reset();
      this.resetDroplistProrro('Notificacion');
      this.dia1Prorro = 0;
      this.validarDias2Pro = false;
    }
    DEBUG && console.log(this.listNotificacionesProrro);
  }

  borrarProrroNotificacion(data: number) {
    this.listNotificacionesProrro.splice(data, 1);
    DEBUG && console.log("******************+ lista Prorroga notificaciones ***************");
    DEBUG && console.log(this.listNotificacionesProrro);
  }

  agregarProrroCorreo() {
    if (this.correoProrro !== '') {
      let flag = false;
      for (let item of this.listCorreosProrro) {
        if (item.correo === this.correoProrro) {
          flag = true;
          break;
        }
      }
      if (!flag) {
        this.correos.correo = this.correoProrro;
        let aux = Object.assign({}, this.correos);
        this.listCorreosProrro.push(aux);

        //this.listCorreosTotal(this.correoProrro);
      }
      this.correos.reset();
      this.correoProrro = '';
      this.validEmailPro = false;
    }
    DEBUG && console.log(this.listCorreosProrro);
  }

  borrarProrroCorreo(data: number) {
    this.listCorreosProrro.splice(data, 1);
    DEBUG && console.log("******************+ lista Prorroga correos ***************");
    DEBUG && console.log(this.listCorreosProrro);
  }


  /* ********************************************************* Guadar Registro ********************************************************* */

  guardarRegistro() {

    /* Clonamos objetos para parchar las propiedades faltantes, que son requeridas en servicio */
    let register = JSON.parse(JSON.stringify(this.registro));
    let auxProducto = Object.assign({}, this.producto);
    register.producto = auxProducto; //En duro por que no hay lista de productos
    register.idEmpresa = SessionUser.getInstance().getidEmpresaActual();
    register.prorroga = false;

    let listNotificationError = [];
    this.listNotificationError = [];

    this.dossier.notificaciones = this.listNotificacionesDossier;
    for (let item of this.dossier.notificaciones) {
      item.finicio = this.calculateFInicioNuevo(this.dossier.frealizacion, item.tipoPeriodo, item.cantPeriodo);
      if (new Date(register.finicio).getTime() > item.finicio.getTime()) {
        this.findInvalidDate(listNotificationError, this.dossier.nombre, item.finicio, item.tipoPeriodo, item.cantPeriodo, item.recurrencia);
      }
    }
    this.dossier.correos = this.listCorreosDossier;
    let auxDossier = Object.assign({}, this.dossier);

    this.tecnovigilancia.notificaciones = this.listNotificacionesTecno;
    for (let item of this.tecnovigilancia.notificaciones) {
      item.finicio = this.calculateFInicioNuevo(this.tecnovigilancia.frealizacion, item.tipoPeriodo, item.cantPeriodo);
      if (new Date(register.finicio).getTime() > item.finicio.getTime()) {
        this.findInvalidDate(listNotificationError, this.tecnovigilancia.nombre, item.finicio, item.tipoPeriodo, item.cantPeriodo, item.recurrencia);
      }
    }
    this.tecnovigilancia.correos = this.listCorreosTecno;
    let auxTecno = Object.assign({}, this.tecnovigilancia);

    this.sometimiento.notificaciones = this.listNotificacionesSom;
    for (let item of this.sometimiento.notificaciones) {
      item.finicio = this.calculateFInicioNuevo(this.sometimiento.frealizacion, item.tipoPeriodo, item.cantPeriodo);
      if (new Date(register.finicio).getTime() > item.finicio.getTime()) {
        this.findInvalidDate(listNotificationError, this.sometimiento.nombre, item.finicio, item.tipoPeriodo, item.cantPeriodo, item.recurrencia);
      }
    }
    this.sometimiento.correos = this.listCorreosSom;
    let auxSom = Object.assign({}, this.sometimiento);

    this.prorroga.notificaciones = this.listNotificacionesProrro;
    for (let item of this.prorroga.notificaciones) {
      item.finicio = this.calculateFInicioNuevo(this.prorroga.frealizacion, item.tipoPeriodo, item.cantPeriodo);
      if (new Date(register.finicio).getTime() > item.finicio.getTime()) {
        this.findInvalidDate(listNotificationError, this.prorroga.nombre, item.finicio, item.tipoPeriodo, item.cantPeriodo, item.recurrencia);
      }
    }
    this.prorroga.correos = this.listCorreosProrro;
    let auxPro = Object.assign({}, this.prorroga);

    /* Guardar solo los eventos activos */
    if (this.enableDossier)
      register.eventos.push(auxDossier);
    if (this.enableTecnovigilancia)
      register.eventos.push(auxTecno);
    if (this.enableSometimiento)
      register.eventos.push(auxSom);
    if (this.check) {
      register.eventos.push(auxPro);
      register.prorroga = true;
    }

    for (let item of this.listEventos) {
      register.eventos.push(item);
      _.forEach(item.notificaciones, notification => {
        /* Recalculamos la fecha de inicio para el caso que el usuario realizo
        * modificaciones al evento personalizado
        *
        * */
        notification.finicio = this.calculateFRealizacionNuevo(notification.tipoPeriodo, notification.cantPeriodo, false, item.frealizacion);
        if (new Date(register.finicio).getTime() > notification.finicio.getTime()) {
          this.findInvalidDate(listNotificationError, item.nombre, notification.finicio, notification.tipoPeriodo, notification.cantPeriodo, notification.recurrencia);
        }
      });
    }

    if (listNotificationError.length > 0) {
      this.listNotificationError = listNotificationError.slice();
      return;
    }

    /* Una vez que toda la información esta valida se procede a pedir el cargador */
    SessionUser.getInstance().setLoading(true);

    this.registrosService.guardarRegistro(register)
      .subscribe(
        response => {
          DEBUG && console.log(response);

          // TODO: Verificar si se cargaron archivos al producto
          if (this.listFiles.length > 0) {
            // Guardar los archivos del producto
            this.saveFileHistory(response.idRegistro, response.idproducto);
            this
          } else {
            // Notificar que las inserción fue corrrecta y redireccionar
            SessionUser.getInstance().setLoading(false);
            SessionUser.getInstance().setMessageOk('Has guardado');
            document.getElementById('idOk').style.display = 'block';
            this.router.navigate(["protected/libreriaRegistros"]);
          }

        },
        error => {
          DEBUG && console.log('Error al guardar el registro: ' + error);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al guardar el registro');
          document.getElementById('requestFailed').style.display = 'block';
        });
  }

  gettiempoRealizacion() {
    let parametro = new Parametros();
    parametro.id = SessionUser.getInstance().getidEmpresaActual();

    this.registrosService.obtenerTiempoRealizacion(parametro)
      .subscribe(
        data => {
          DEBUG && console.log(data);

          /* TODO: Omitir la configuración por default para los eventos definidos
           *
           *
           */

          this.dossier.cantPeriodo = 0;   //data.ev1_cantidad;
          this.dossier.tipoPeriodo = '';    //data.ev1_tipo;
          this.tecnovigilancia.cantPeriodo = 0;   //data.ev2_cantidad;
          this.tecnovigilancia.tipoPeriodo = '';    //data.ev2_tipo;
          this.sometimiento.cantPeriodo = 0;    //data.ev3_cantidad;
          this.sometimiento.tipoPeriodo = '';   //data.ev3_tipo;

          /* Respaldo de la configuración inicial del evento */
          this.backupDossier.cantPeriodo = 0;   //data.ev1_cantidad;
          this.backupDossier.tipoPeriodo = '';    //data.ev1_tipo;
          this.backupTecnovigilancia.cantPeriodo = 0;   //data.ev2_cantidad;
          this.backupTecnovigilancia.tipoPeriodo = '';    //data.ev2_tipo;
          this.backupSometimiento.cantPeriodo = 0;    //data.ev3_cantidad;
          this.backupSometimiento.tipoPeriodo = ''; //data.ev3_tipo;

          this.resetDossier[0] = {nombre: ''/*this.dossier.tipoPeriodo*/};
          this.resetTecno[0] = {nombre: ''/*this.tecnovigilancia.tipoPeriodo*/};
          this.resetSom[0] = {nombre: ''/*this.sometimiento.tipoPeriodo*/};
        },
        error => {
          DEBUG && console.log('Error al traer los tiempos ' + error);
          SessionUser.getInstance().setMessageFailed('al obtener las fechas por default');
          document.getElementById('requestFailed').style.display = 'block';
        });
  }

  /*getProductos() {
    SessionUser.getInstance().setLoading(true);
    let parametros = new Parametros();
    parametros.condicion = SessionUser.getInstance().getRfc();
    parametros.id = SessionUser.getInstance().getidEmpresaActual();

    this.registrosService.obtenerProductosDataStagePorEmpresa(parametros)
      .subscribe(
        data => {
          DEBUG && console.log(data);
          this.lst = Object.assign([], data);
          this.arrayAux = Object.assign([], data);
          this.arrayAux = this.arrayAux.sort((a, b) => (a.nombre < b.nombre) ? -1 : 1);
          let i = 0;
          for (let item of this.arrayAux) {
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
          DEBUG && console.log('error al traer los productos ' + error);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al obtener los productos');
          document.getElementById('requestFailed').style.display = 'block';
        });
  }*/

  saveProducto(index) {
    DEBUG && console.log(this.registro);
    DEBUG && console.log(this.producto);

    if (index === 1 && (!this.producto.nombre || !this.registro.descripcion || !this.producto.codigo || !this.producto.origen ||
      !this.registro.usuario.idUsuario || !this.registro.finicio || !this.registro.ffin) ) {
      this.activeInvalidData = {
        name: !this.producto.nombre,
        tramiteCofepris: false,
        folioRegistro: false,
        description: !this.registro.descripcion,
        codeProduct: !this.producto.codigo,
        origin: !this.producto.origen,
        user: !this.registro.usuario.idUsuario,
        startDate: !this.registro.finicio,
        endDate: !this.registro.ffin
      };
      return;
    }

    if (index === 2 && ( !this.producto.nombre || !this.registro.tramiteCofepris || !this.registro.folioRegistro
      || !this.registro.descripcion || !this.producto.codigo || !this.producto.origen || !this.registro.usuario.idUsuario
      || !this.registro.finicio || !this.registro.ffin || this.registro.finicio && this.registro.finicio.getTime() >= this.registro.ffin.getTime())) {
      this.activeInvalidData = {
        name: !this.producto.nombre,
        tramiteCofepris: !this.registro.tramiteCofepris,
        folioRegistro: !this.registro.folioRegistro,
        description: !this.registro.descripcion,
        codeProduct: !this.producto.codigo,
        origin: !this.producto.origen,
        user: !this.registro.usuario.idUsuario,
        startDate: !this.registro.finicio,
        endDate: !this.registro.ffin || (this.registro.finicio && this.registro.finicio.getTime() >= this.registro.ffin.getTime())
      };
      return;
    }

    this.next();
    if (index == 1) {
      this.cerrarProducto(false);
      this.productoSelect = false;
      setTimeout(() => { document.getElementById('id01').style.display='none'; }, 200);
    } else {
      this.cerrarSeleccionarProducto(false);
      this.productoSelect = true;

      setTimeout(() => { document.getElementById('id02').style.display='none'; }, 200);
    }

    this.checkDates();
  }

  /* Revisamos la última configuración del evento pasado */
  checkDates() {

    if (this.registro.finicio && this.registro.ffin) {
      let startDate = moment(this.registro.finicio);
      let endDate = moment(this.registro.ffin);

      /* Obtenemos la cantidad de días disponibles entre la fecha inicial y fecha final */
      let totalOfDays = this.diasHabilesService.calculateDays(startDate, endDate);

      /* Obtener la fecha de realización de los eventos principales */
      let dateDossier = this.diasHabilesService.getDate(this.registro.ffin, this.dossier.tipoPeriodo, this.dossier.cantPeriodo);
      let dateTecnovigilancia = this.diasHabilesService.getDate(this.registro.ffin, this.tecnovigilancia.tipoPeriodo, this.tecnovigilancia.cantPeriodo);
      let dateSometimiento = this.diasHabilesService.getDate(this.registro.ffin, this.sometimiento.tipoPeriodo, this.sometimiento.cantPeriodo);

      /*
      *  Verificamos que la fecha de realización del evento, se encuentre dentro del rango valido;
      *  en caso contrario se asigna un periodo valido
      *
      * */
      if (this.registro.finicio.getTime() > dateDossier.getTime()) {
        this.dossier.tipoPeriodo = 'Días';
        this.dossier.cantPeriodo = totalOfDays > 1 ? totalOfDays - 1 : 1;
      }

      if (this.registro.finicio.getTime() > dateTecnovigilancia.getTime()) {
        this.tecnovigilancia.tipoPeriodo = 'Días';
        this.tecnovigilancia.cantPeriodo = totalOfDays > 1 ? totalOfDays - 1 : 1;
      }

      if (this.registro.finicio.getTime() > dateSometimiento.getTime()) {
        this.sometimiento.tipoPeriodo = 'Días';
        this.sometimiento.cantPeriodo = totalOfDays > 1 ? totalOfDays - 1 : 1;
      }

    }
  }

  obtenerPaises() {
    let parametro = new Parametros();
    this.glosarioService.obtenerPaises(parametro)
      .subscribe(
        data => {
          DEBUG && console.log(data);
          this.paises = new Array();
          let d = data.current;
          let pais = d.split(' ');
          DEBUG && console.log(pais);
          let pa = [];
          for (let item of pais) {
            if (item !== '') {
              pa.push({nombre: item});
            }
          }

          Array.prototype.push.apply(this.paises, pa);
          DEBUG && console.log(this.paises);
        },
        error => {
          SessionUser.getInstance().setLoading(false);
          DEBUG && console.log('Error al traer la lista de paises: ' + error);
          SessionUser.getInstance().setMessageFailed('al obtener los paises');
          document.getElementById('requestFailed').style.display = 'block';
        });
  }


  listCorreosTotal(item) {

    this.listCorreoT.push(item);

    let i;
    let len = this.listCorreoT.length;
    let out = [];
    let obj = {};

    for (i = 0; i < len; i++) {
      obj[this.listCorreoT[i]] = 0;
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
    for (let item of this.listCorreosDossier) {
      this.listCorreosTotal(item.correo);
    }
    for (let item of this.listCorreosTecno) {
      this.listCorreosTotal(item.correo);
    }
    for (let item of this.listCorreosSom) {
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
        data => {
          DEBUG && console.log(data);
          this.users = new Array();
          let u = [];
          for (let item of data) {
            u.push({nombre: item.nombre + " " + item.apellido + " - " + item.email, user: item});
          }
          Array.prototype.push.apply(this.users, u);
          DEBUG && console.log(this.users);
        },
        error => {
          DEBUG && console.log('Error al buscar usuarios: ' + error);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al obtener los usuarios');
          document.getElementById('requestFailed').style.display = 'block';
        });
  }


  validarCorrreo(email) {
    let re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    DEBUG && console.log(re.test(String(email).toLowerCase()));
    return re.test(String(email).toLowerCase());
  }


  validarDias(dias) {
    if (dias > 0) {
      let re = /^[0-9]+$/;
      DEBUG && console.log(re.test(String(dias).toLowerCase()));
      return re.test(String(dias).toLowerCase());
    } else {
      return false;
    }
  }


  reset_() {
    let numItems = Math.floor((window.screen.width - 110) / 225) * 4;
    /* if(this.arrayAux.length > 0 ){ */
    this.loading = true;
    /* } */
    if (this.arrayAux.length > 0) {
      this.fetchNextChunk(0, numItems).then(chunk => this.buffer = chunk);
    }
  }

  async reset() {
    this.loading = true;
    const data: any = await this.getData();
    this.buffer = data.slice();
    this.loading = false;
  }

  fetchMore(event: IPageInfo) {
    DEBUG && console.log(`@fetchMore => END = ${event.endIndex} || BUFFER = ${this.buffer.length} || AUX = ${this.arrayAux.length}`);
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
    DEBUG && console.log("@fetchNextChunk: Entre");

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

  handlerEvents($event, field) {
    $event.stopPropagation();
    this[field] = !this[field];
  }

  /*
   * Para publicar el registro solo bastara tener al menos un evento activo
   *
   */
  validateCompleteData() {
    const isValidDossier = this.validateDossier();
    const isValidTecnovigilancia = this.validateTecnovigilancia();
    const isValidSometimiento = this.validateSometimiento();
    const isValidEvents = this.validateCustomEvents();

    let currentDossier = this.enableDossier ? isValidDossier : true;
    let currentTecnovigilancia = this.enableTecnovigilancia ? isValidTecnovigilancia : true;
    let currentSometimiento = this.enableSometimiento ? isValidSometimiento : true;
    let currentEvents = this.listEventos.length > 0 ? isValidEvents : true;

    if (currentDossier && currentTecnovigilancia && currentSometimiento && currentEvents
      && ( isValidDossier || isValidTecnovigilancia || isValidSometimiento || isValidEvents )) { /* TODO: Validamos que hay al menos un evento valido */

      if (this.check) {
        if (this.validateProrroga()) {
          this.fullData = true;
        } else {
          this.fullData = false;
        }
      } else {
        this.fullData = true;
      }
    } else {
      this.fullData = false;
    }
  }

  validateDossier() {

    if (!this.enableDossier) return false;

    if (this.listNotificacionesDossier.length > 0 && this.listCorreosDossier.length > 0 && this.dossier.cantPeriodo > 0
      && this.registro.finicio.getTime() < this.dossier.frealizacion.getTime() && this.resetDossier[0].nombre)  {
      return true
    }

    return false;
  }

  validateTecnovigilancia() {

    if (!this.enableTecnovigilancia) return false;

    if (this.listNotificacionesTecno.length > 0 && this.listCorreosTecno.length > 0 && this.tecnovigilancia.cantPeriodo > 0
      && this.registro.finicio.getTime() < this.tecnovigilancia.frealizacion.getTime() && this.resetTecno[0].nombre) {
      return true;
    }

    return false;
  }

  validateSometimiento() {
    if (!this.enableSometimiento) return false;

    if (this.listNotificacionesSom.length > 0 && this.listCorreosSom.length > 0 && this.sometimiento.cantPeriodo > 0
      && this.registro.finicio.getTime() < this.sometimiento.frealizacion.getTime() && this.resetSom[0].nombre) {
      return true;
    }

    return false
  }

  validateProrroga() {

    if (this.listNotificacionesProrro.length > 0 && this.listCorreosProrro.length > 0 && this.prorroga.cantPeriodo > 0 && this.prorroga.tipoPeriodo !== ''
      && this.registro.finicio.getTime() < this.prorroga.frealizacion.getTime() && this.resetProrro[0].nombre) {
      return true
    }

    return false;
  }

  validateCustomEvents() {
    if (this.listEventos.length > 0) {
      let isValid = true;
      _.forEach(this.listEventos, o => {
        if (o.notificaciones.length === 0 || o.correos.length === 0 || o.cantPeriodo === 0 || o.tipoPeriodo === ''
          || this.registro.finicio.getTime() > o.frealizacion.getTime()) {
          isValid = false;
          return false;
        }
      });
      return isValid;
    }
    return false;
  }

  handleOfDaySelected($event, label) {
    if (label === 'startDate') {
      this.startDate = new Date($event);
      this.activeInvalidData = {
        ...this.activeInvalidData,
        startDate: false
      };
    } else if (label === 'endDate') {
      this.endDate = new Date($event);
      this.activeInvalidData = {
        ...this.activeInvalidData,
        endDate: this.registro.finicio && this.registro.finicio.getTime() >= this.endDate.getTime()
      };
    }
  }

  getOptionsEvents() {
    let startDate = moment(this.registro.finicio);
    let endDate = moment(this.registro.ffin);
    let options = [];

    /* Obtenemos la cantidad de días disponibles entre la fecha inicial y fecha final */
    let totalOfDays = this.diasHabilesService.calculateDays(startDate, endDate);

    if (totalOfDays > 0) {
      options.push({nombre: 'Días'});
    }

    if (totalOfDays > 7) {
      options.push({nombre: 'Semanas'});
    }

    if (totalOfDays > 30) {
      options.push({nombre: 'Meses'});
    }

    if (totalOfDays > 365) {
      options.push({nombre: 'Años'});
    }

    return options;
  }

  findInvalidDate(collection, eventName, date, periodType, periodQty, recurrence) {
    let eventIndex = _.findIndex(collection, o => {
      return o.name === eventName
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

  getCustomDate(dateToFormat) {
    let now: Date = new Date(dateToFormat);
    let date: String;
    let month: number = now.getMonth();
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
    if (this.evento.cantPeriodo !== 0 && this.evento.nombre !== '' && this.evento.tipoPeriodo !== '' && this.listNotificacionesNuevo.length > 0 && this.listCorreosNuevo.length > 0) {
      let invalid = false;
      _.forEach(this.listNotificacionesNuevo, o => {
        if (this.registro.finicio.getTime() > o.startDate.getTime()) {
          invalid = true;
          return false;
        }
      });
      return invalid;
    }
    return true;
  }


  handleOpenContent() {
    this.contentIsOpenUpload = !this.contentIsOpenUpload;
  }

  openModalUpload() {
    this.modalIsOpenUpload = true;
  }

  closeModalUpload() {
    this.modalIsOpenUpload = false;
    this.listFilesToUpload = [];
    this.resetFileUpload();
  }

  handleFileUpload( $event) {
    if ($event.target.files.length > 0) {
      this.fileUpload.file = $event.target.files;
      this.fileUpload.fileName = $event.target.files[0].name;
      const reader:any = new FileReader();
      reader.readAsBinaryString($event.target.files[0]);
      reader.onload = () => {
        this.fileUpload.preview = btoa(reader.result);
      };
      reader.onerror = () => {
        DEBUG && console.log('Error al obtener el base64');
      };

    }
  }

  resetFileUpload() {
    this.fileUpload.file = null;
    this.fileUpload.fileName = '';
    this.fileUpload.description = '';
    this.fileUpload.preview = '';
  }

  addFileToList() {
    this.listFilesToUpload.push({
      file: this.fileUpload.file,
      fileName: this.fileUpload.fileName,
      description: this.fileUpload.description,
      preview: this.fileUpload.preview,
      date: new Date(),
    });
    this.resetFileUpload();
  }

  deleteFileToList(index) {
    this.listFilesToUpload.splice(index, 1);
  }

  deleteFile(index) {
    this.listFiles.splice(index, 1);
  }

  saveFiles() {
    this.listFiles = [
      ...this.listFiles,
      ...this.listFilesToUpload
    ];
    this.listFilesToUpload.length = 0;
    this.closeModalUpload();
  }

  saveFileHistory(idRegistro: number, idProducto) {
    const result = [];
    _.forEach(this.listFiles, o => {
      const data = new HistorialProducto();
      data.idRegistro = idRegistro;
      data.nombre = o.fileName;
      data.comentarios = o.description;
      result.push(data);
    });

    const parameter = new Parametros();
    parameter.historialProducto = result;

    this.registrosService.guardarHistorialProducto(parameter)
      .subscribe(async response => {
        if (response.historialProducto.length > 0) {
          let index = 0;
          let countError = 0;
          for (let item of response.historialProducto) {
            const fileName = item.nombre.split('.').slice(0, -1).join('.') + '_' + item.idHistorialProducto + '.pdf';
            const result = await this.uploadFile(
              this.listFiles[index].file,
              SessionUser.getInstance().getidEmpresaActual(),
              idProducto,
              item.idRegistro,
              item.idHistorialProducto,
              fileName
            );
            countError = result ? countError : countError + 1;
            index++;
          }

          if (countError === 0) {
            // Notificar que las inserción fue corrrecta y redireccionar
            SessionUser.getInstance().setLoading(false);
            SessionUser.getInstance().setMessageOk('Has guardado');
            document.getElementById('idOk').style.display = 'block';
            this.router.navigate(["protected/libreriaRegistros"]);
          } else {
            SessionUser.getInstance().setLoading(false);
            SessionUser.getInstance().setMessageFailed('al guardar el registro');
            document.getElementById('requestFailed').style.display = 'block';
          }
        }
      }, error => {
        DEBUG && console.log('Error al guardar el registro: ' + error);
        SessionUser.getInstance().setLoading(false);
        SessionUser.getInstance().setMessageFailed('al guardar el registro');
        document.getElementById('requestFailed').style.display = 'block';
      });
  }

  uploadFile(file, idEmpresa, idProducto, idRegistro, idHistorialProducto, fileName) {
    return new Promise(resolve => {
      this.registrosService.uploadFileProduct(file, idEmpresa, idProducto, idRegistro, idHistorialProducto, fileName)
        .subscribe(
          data => {
            DEBUG && console.log(data);
            resolve(true);
          },
          error => {
            DEBUG && console.log('error al subir el pdf: ' + error);
            resolve(false);
          });
    });
  }

  getPDF(base64, fileName) {
    const pdfFile = 'data:application/pdf;base64,' + base64;
    const element = document.createElement('a');
    element.setAttribute('href', pdfFile);
    element.setAttribute('download', fileName);
    element.style.display = 'none';
    document.body.appendChild(element);
    element.click();
    document.body.removeChild(element);
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
            }

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
