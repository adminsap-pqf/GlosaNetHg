import { Component, ElementRef, OnInit, ViewChild, AfterViewInit, OnDestroy, DoCheck } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Notificacion } from '../../model/notificacion.class';
import { CorreoEvento } from '../../model/correoEvento.class';
import { Eventos } from '../../model/eventos.class';
import { Registro } from '../../model/registro.class';
import { Producto } from '../../model/producto.class';
import { RegistrosService } from '../../services/registros/registros.service';
import { SessionUser } from '../../services/session/session.service';
import { Parametros } from '../../model/parametros.class';
import { dateFormatNumber } from '../../pipes/accounting/accounting.pipe';
import { HistorialEvento } from '../../model/historialEvento.class';
import { trigger, style, animate, transition } from '@angular/animations';

import * as moment from 'moment';
import * as _ from 'lodash';

import { UserService } from '../../services/user/user.service';
import { Usuario } from '../../model/usuario.class';
import { DiasHabilesService } from '../../services/general/diasHabiles';
import { UploadFile } from '../../interfaces/uploadFile';

/* Dev Tools */
import { DEBUG } from '../../settings/app.settings';
import {HistorialProducto} from '../../model/historialProducto.class';

interface ISwitch {
  typeSwitch: number;
  index: number;
  status: boolean;
  object: any;
}
// typeSwitch
// 1 - Eventos
// 2 - Folio
// 3 - Prorroga

@Component({
  selector: 'gn-monitoreo-tramite',
  templateUrl: './monitoreo-tramite.component.html',
  styleUrls: ['./monitoreo-tramite.component.scss'],
  animations: [
    trigger('fadeInOut', [
      transition(':enter', [
        style({ opacity: 0 }),
        animate(400, style({ opacity: 0.7 }))
      ]),
      transition(':leave', [
        animate(400, style({ opacity: 0 }))
      ])
    ])
  ]
})
export class MonitoreoTramiteComponent implements OnInit, AfterViewInit, DoCheck, OnDestroy {

  @ViewChild('fileInput', {static: false}) fileInput: any;
  @ViewChild('modalUploadedFiles', {static: false}) modalUploadedFiles: ElementRef;
  @ViewChild('lightBox', {static: false}) lightBoxDOM: ElementRef;

  positionToday:any = 0;
  today = new Date();


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
  arrayValidDias1 = [];
  arrayValidDias2 = [];

  validEmailPro = false;
  validarDias2Pro = false;
  validarDias1Pro = false;

  validEmailNuevo = false;
  validarDias2Nuevo = false;
  validarDias1Nuevo = false;


  selectUser:any;

  users:any = [];

  sub: any;
  idRegistro: number = 0;

  open = [];

  openFolio:boolean = false;
  openProrro: boolean = false;

  gEventosForm1: FormGroup;
  gEventosForm2: FormGroup;
  modalForm: FormGroup;
  /* Datepicker */
  date: Date = null;

  /* DropList */
  public itemsDropList1: any[] = [{ nombre: 'Ejemplo1' }, { nombre: 'Ejemplo2' }, { nombre: 'Ejemplo3' }];
  defaultSelected1:any;

  public dropListPeriodoTiempo: any[] = [{ nombre: 'Días' }, { nombre: 'Semanas' }, { nombre: 'Meses' }, {nombre: 'Años'}];

  public dropListNotificacion: any[] = [{ nombre: 'Cada Día' }, { nombre: 'Cada Semana' }, { nombre: 'Cada Mes' }, {nombre: 'Cada Año'}];

  /* check prorroga */
  check: boolean = false;

  /* reset droplistNuevo */
  resetNuevo:Array<any> = new Array<any>(3);

  /* file */

  arrayFile:Array<FileList>;

  textArea:Array<string>;

  fileName:Array<string>;


  classProrroga: string = 'body-prorroga-close';

  generarEventEffect:string = 'body-generarE-close';

  classAnimation = 'w3-animate-zoom-out';
  classAnimation2 = 'w3-animate-zoom-out';

  items:any [] = [1,1,1 ];

  section:number = 1;

  lines:any = new Array(this.items.length + 2).fill(1);
  etiquetasBar:any = [];

  widthBar:number = 0;

  switchArray:any = [];

  indexLine:any = 0;
  showTooltip:any = [];
  contentTooltip:any = [];

  switchPro:boolean = false;
  fileNamePro:string = '';
  textAreaPro:string = '';

  filePro:FileList = null;

  switchFol:boolean = false;
  fileNameFolio:string = '';
  textAreaFol:string = '';

  fileFol:FileList = null;

  fileName_light:string = '';
  file_light:FileList = null;

  datePro1:Date = new Date();
  datePro2:Date = new Date();
  dateFolio1:Date;
  dateFolio2:Date;

  rTerminado: boolean = false;

  fRealizacionArray:any[] = [];

  validDate: any[] = new Array<boolean>(5).fill(false);

  listCorreoT:any[] = [];

  fechaAtiempo:Date;

  fechaLimiteNueva:number = 0;

  showTooltipFolio:string = '0';

  diasPorevento:any = [];

  textArea_light:string = '';


  arrayHistorialNuevos:any = [];
  arrayFileHistorialNuevos:any = [];

  idEventoActualCarga:number = 0;

  indexActualCarga:number = 0;

  eventoActual:string='';

  responsableGestion: String = '';


  /* variables donde se encadenan los correos y notificaciones a eliminar */
  notifEliminar: string = '';
  correoEliminar: string = '';


  /* Efectos de los 3 eventos por default */
  itemEventEffect:any = new Array(3).fill('body-itemE-close');

  /* Efectos de los nuevos eventos creados */
  itemNewEventEffect: any;  /* = new Array(3).fill('body-itemE-close'); */


  /* Lista de eventos nuevos */
  listEventos: Array<Eventos> = new Array<Eventos>();

  /* Objetos pirncipales para guardar los nuevos eventos y sus listas, reset cada que se aplica un agregar o generar */
  registro: Registro = new Registro();
  evento: Eventos = new Eventos();
  notificaciones: Notificacion =  new Notificacion();
  correos : CorreoEvento = new CorreoEvento();
  producto: Producto = new Producto();

  folio: Eventos = new Eventos();


  registroUpdate: Registro = new Registro();


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
  resetDossier:Array<any> = new Array<any>(3);

  /* **********************************************+ Tecnovigilancia **********************************************+ */
  dia1Tecno: number = 0;
  correoTecno: string = '';
  listNotificacionesTecno: Array<Notificacion> = new Array<Notificacion>();
  listCorreosTecno: Array<CorreoEvento> = new Array<CorreoEvento>();
  resetTecno:Array<any> = new Array<any>(3);

  /* **********************************************+ Sometimiento **********************************************+ */
  dia1Som: number = 0;
  correoSom: string = '';
  listNotificacionesSom: Array<Notificacion> = new Array<Notificacion>();
  listCorreosSom: Array<CorreoEvento> = new Array<CorreoEvento>();
  resetSom:Array<any> = new Array<any>(3);


  /* **********************************************+ Prorroga **********************************************+ */
  dia1Prorro: number = 0;
  correoProrro: string = '';
  listNotificacionesProrro: Array<Notificacion> = new Array<Notificacion>();
  listCorreosProrro: Array<CorreoEvento> = new Array<CorreoEvento>();
  resetProrro: Array<any> = new Array<any>(3);

  clear: boolean = false;

  /* Arreglo con el proposito de recalcular las fechas para los eventos y notificaciones*/
  backupEvents: Array<Eventos> = new Array<Eventos>();

  /* Manejo de errores al modificar la fecha anterior */
  showError:boolean = false;
  messageError:string = 'Fuera de rango';
  hasChange:boolean = false;

  /* Valid data */
  activeInValidData: any = {
    name: false,
    idTramite: false,
    numeberTramite: false
  };

  /* Variable para setear valores de droplist */
  resetValidate: boolean = false;

  /* Contador de eventos definidos previamente agregados */
  countEventsDefined: number = 0;

  /* Validar que todos las fechas de eventos sean mayores a la fecha de inicio del registro */
  fullData: boolean = false;

  /* Lista de notificaciones que se encuentra fuera de tiempo */
  listNotificationError: any = [];

  numberDossier: number = 0;
  numberTecno: number = 0;
  numberSom: number = 0;

  invalidDates: boolean = false;

  modalIsOpen = false;
  historialProductoStatus = 0; // 0 = Lanzar petición, 1 = fallo, 2 = exito
  historialProductoLoading = false;
  currentFilesInRecords = [];

  historialRegistros = [];
  historialRegistrosStatus = 0; // 0 = Lanzar petición, 1 = fallo, 2 = exito
  historialRegistrosLoading = false;

  activeTab = 'Evento';
  optionsTab: string [] = ['Evento', 'Historial Registros'];
  // optionsTab: string [] = ['Historial Registros', 'Evento']; // TODO: TESTING
  fileUpload: UploadFile;
  listFilesToUpload = [];

  selectedSwitch = {} as ISwitch;

  constructor(
    private router: Router,
    private registrosService: RegistrosService,
    private route: ActivatedRoute,
    private userService: UserService,
    private diasHabilesService: DiasHabilesService) { }

  ngOnInit() {

    this.sub = this.route.params.subscribe(params => {
      this.idRegistro = params['id'];

    });

    this.registroUpdate.usuario = new Usuario();
    this.registroUpdate.producto = new Producto();

    this.obtenerRegistro();
    this.gEventosForm1 = new FormGroup({
      nombreEvent: new FormControl('', [Validators.required]),
      dias1: new FormControl('', [Validators.required]),
      dias2: new FormControl('', [Validators.required]),
      dias3: new FormControl('', [Validators.required])
    });

    this.gEventosForm2 = new FormGroup({
      correoN: new FormControl('', [Validators.required]),
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

    this.defaultValuesEvent();

    this.fileUpload = {
      file: null,
      fileName: '',
      description: '',
      preview: ''
    };
  }

  ngAfterViewInit() {
    // TODO: Mover el elemento al final de la página (antes de</body>), para que se pueda visualizar sobre todo lo demás
    document.body.appendChild(this.modalUploadedFiles.nativeElement);

    // TODO: TESTING
    // this.activeTab = 'Historial Registros';
    setTimeout(() => {
      this.getRegistrosPorProducto();
    }, 5000);
  }

  ngDoCheck() {
    if (this.section === 2) {
      this.validateCompleteData();
    }
  }

  ngOnDestroy() {
    // TODO: Eliminar los elementos que fueron  movidos al final
    if (this.modalUploadedFiles) {
      this.modalUploadedFiles.nativeElement.remove();
    }
  }

  irLibreriaRegistros() {
    SessionUser.getInstance().setConfirm(false);
    this.router.navigate(["protected/libreriaRegistros"]);
  }

  async uStatus($event, index, item) {
    DEBUG && console.log($event);

    SessionUser.getInstance().setLoading(true);

    this.registro.eventos[index].terminado = true;

    const result = await this.realizarEvento(item);

    SessionUser.getInstance().setLoading(false);
    if (result) {
      this.switchArray[index] = $event;
      this.registro.eventos[index].aTiempo = this.today > new Date(this.registro.eventos[index].frealizacion) ? true : false;

      if ($event) {
        this.widthBar = this.widthBar + ( 100 / (this.lines.length - 1) );
      } else {
        this.widthBar = this.widthBar - ( 100 / (this.lines.length - 1) );
      }
      this.diasPorEventos();
      this.ngOnInit();
    } else {
      SessionUser.getInstance().setMessageFailed('al conciliar etapa');
      document.getElementById('requestFailed').style.display = 'block';
      this.registro.eventos[index].terminado = false;
    }
  }

  async uStatusPro($event, item){
    DEBUG && console.log($event);

    SessionUser.getInstance().setLoading(true);

    this.prorroga.terminado = true;

    const result = await this.realizarEvento(item);

    SessionUser.getInstance().setLoading(false);
    if (result) {
      this.switchPro = $event;
      this.ngOnInit();
    } else {
      SessionUser.getInstance().setMessageFailed('al conciliar etapa');
      document.getElementById('requestFailed').style.display = 'block';
      this.prorroga.terminado = false;
    }
  }

  async uStatusFol($event,item) {
    DEBUG && console.log($event);

    SessionUser.getInstance().setLoading(true);

    this.folio.terminado = true;

    const result = await this.realizarEvento(item);

    SessionUser.getInstance().setLoading(false);
    if (result) {
      if ($event) {
        this.widthBar = this.widthBar + ( 100 / (this.lines.length - 1) );
      } else{
        this.widthBar = this.widthBar - ( 100 / (this.lines.length - 1) );
      }
      this.switchFol = $event;
      this.ngOnInit();
    } else {
      SessionUser.getInstance().setMessageFailed('al conciliar etapa');
      document.getElementById('requestFailed').style.display = 'block';
      this.folio.terminado = false;
    }
  }

  getFechaImpl($event) {
    DEBUG && console.log($event);
  }

  saveTramiteCofepris(index, id) {
    if (this.registro.eventos[index].tramiteCofepris !== '') {
      let parametro = new Parametros();
      parametro.id = id;
      parametro.tipo = '1';
      parametro.condicion = this.registro.eventos[index].tramiteCofepris;
      this.guardarElementoMonitoreoRegistro(parametro);

      this.obtenerStatusTramiteCofepris(index);
    } else {
      DEBUG && console.log('Campo vacio!!!');
    }


    if (this.registro.eventos[index].fechaTramite == undefined || this.registro.eventos[index].fechaTramite == null) {

      let parametro = new Parametros();
      parametro.id = id;
      parametro.tipo = '7';
      parametro.finicio = new Date();
      this.guardarElementoMonitoreoRegistro(parametro);

      this.registro.eventos[index].fechaTramite = new Date();
    }
  }

  saveTramiteCofeprisProrro() {
    if(this.prorroga.tramiteCofepris !== '') {
      let parametro = new Parametros();
      parametro.id = this.prorroga.idEvento;
      parametro.tipo = '1';
      parametro.condicion = this.prorroga.tramiteCofepris;
      this.guardarElementoMonitoreoRegistro(parametro);

      this.obtenerStatusTramiteCofeprisProrro();
    } else {
      DEBUG && console.log('Campo vacio!!!');
    }

    if (this.prorroga.fechaTramite == undefined || this.prorroga.fechaTramite == null) {

      let parametro = new Parametros();
      parametro.id = this.prorroga.idEvento;
      parametro.tipo = '7';
      parametro.finicio = new Date();
      this.guardarElementoMonitoreoRegistro(parametro);

      this.prorroga.fechaTramite = new Date();
    }
  }

  saveFolioRegistro() {
    let parametro = new Parametros();
    parametro.id = this.registro.idRegistro;
    parametro.tipo = '2';
    parametro.condicion = this.registro.folioRegistro;
    this.guardarElementoMonitoreoRegistro(parametro);
  }

  saveFInicioPro() {
    let parametro = new Parametros();
    parametro.id = this.registro.idRegistro;
    parametro.tipo = '5';
    parametro.finicio = this.datePro1;
    this.registro.finicioProrroga = this.datePro1;
    this.guardarElementoMonitoreoRegistro(parametro);
  }

  saveFFinPro() {
    let parametro = new Parametros();
    parametro.id = this.registro.idRegistro;
    parametro.tipo = '6';
    parametro.finicio = this.datePro2;
    this.registro.ffinProrroga = this.datePro2;
    this.guardarElementoMonitoreoRegistro(parametro);
  }

  calcularFechaLimite() {
    if (this.today > this.registro.ffin && this.registro.eventos[this.registro.eventos.length - 1].terminado) {
      let d1 = moment(this.today);
      let d2 = moment(this.registro.ffin);
      let days = d1.diff(d2, 'days');
      DEBUG && console.log(days);
      this.fechaLimiteNueva = days;
    } else {

      for(let item of this.registro.eventos) {
        if(this.today > new Date(item.frealizacion) && !item.terminado) {
          let d1 = moment(this.today);
          let d2 = moment(item.frealizacion);
          let days = d1.diff(d2, 'days');
          DEBUG && console.log(days);
          this.fechaLimiteNueva = days;
          break;
        }
        if (this.today <= item.frealizacion && !item.terminado) {
          this.fechaLimiteNueva = 0;
          break;
        }
      }

      if (this.folio.fterminado) {
        this.fechaAtiempo = new Date(this.folio.fterminado);
      }
    }
  }


  diasPorEventos(){
    let index = 0;
    for(let item of this.registro.eventos){
      DEBUG && console.log(item.frealizacionAux);
      if(item.terminado){
        DEBUG && console.log('Terminado');
        if(new Date(item.fterminado) > new Date(item.frealizacionAux)){
          DEBUG && console.log('fuera de tiempo');
          let d1 = moment(item.fterminado);
          let d2 = moment(item.frealizacionAux);
          let days = d1.diff(d2, 'days');

          this.diasPorevento[index] = days == 1 ? days + ' Día de Diferencia · Fuera de Tiempo' :days + ' Días de Diferencia · Fuera de Tiempo';
        }
        if(new Date(item.fterminado) <= new Date(item.frealizacionAux)){
          DEBUG && console.log('a tiempo');
          let d1 = moment(item.fterminado);
          let d2 = moment(item.frealizacionAux);
          let days = d2.diff(d1, 'days');

          this.diasPorevento[index] = days == 1 ? days + ' Día de Diferencia · A Tiempo' : days + ' Días de Diferencia · A Tiempo';
        }
      }else{
        DEBUG && console.log('no Terminado');
        if(this.today > new Date(item.frealizacionAux)){
          DEBUG && console.log('fuera de tiempo');
          let d1 = moment(this.today);
          let d2 = moment(item.frealizacionAux);
          let days = d1.diff(d2, 'days');
          //days += 1;
          this.diasPorevento[index] = days == 1 ? days + ' Día Atrasado · Fuera de Tiempo' : days + ' Días Atrasado · Fuera de Tiempo';
        }
        if(this.today <= new Date(item.frealizacionAux)){
          DEBUG && console.log('a tiempo');
          let d1 = moment(this.today);
          let d2 = moment(item.frealizacionAux);
          let days = d2.diff(d1, 'days');
          //days += 1;
          this.diasPorevento[index] = days == 1 ? days + ' Día Faltante · A Tiempo' : days + ' Días Faltantes · A Tiempo';
        }
      }
      index++
    }
    DEBUG && console.log(this.diasPorevento);
  }


  /* ------------------------------------------------------------Reset Forms-------------------------------------------------------------- */

  resetForms(form:number){
    switch (form) {
      case 1:
        this.gEventosForm1.patchValue({
          nombreEvent:'',
          dias1:'',
        });
        break;
      case 2:
        this.gEventosForm1.patchValue({
          dias2:''
        });
        break;
      case 3:
        this.gEventosForm1.patchValue({
          dias3:''
        });
        break;
      case 4:
        this.gEventosForm2.patchValue({
          correoN:''
        });
        break;
      case 5:
        this.modalForm.patchValue({

        });
        break;
      default:
        break;
    }
  }

  /* ------------------------------------------------------------ /Reset Forms-------------------------------------------------------------- */

  /* ------------------------------------------------------------Drop list-------------------------------------------------------------- */
  dropListPeriodo($event, index) {
    this.dropListTipoPeriodo[index] = $event;
  }

  dropListRecu($event, index){
    this.dropListRecurrencia[index] = $event;
  }

  droplistNuevo(index, $event){
    switch (index) {
      case 1: // Nombre del evento nuevo
        this.evento.tipoPeriodo = $event.nombre;
        this.evento.frealizacion = this.calculateFRealizacionNuevo(this.evento.tipoPeriodo, this.evento.cantPeriodo);
        this.validarFRealizacion(1);
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
        break;
      case 3: // Notificar nuevo evento
        this.notificaciones.recurrencia = $event.nombre;
        break;
      default:
        break;
    }
  }


  resetDroplistNuevo(seccion:string) {
    if (seccion == 'Nombre') {
      this.resetNuevo[0] = {nombre: ''}
    }
    else {
      this.resetNuevo[1] = {nombre: ''};
      this.resetNuevo[2] = {nombre: ''};
    }
  }

  resetDroplistDossier(seccion:string) {
    if (seccion == 'Nombre') {
      this.resetDossier[0] = {nombre: 'Días'}
    }
    else {
      this.resetDossier[1] = {nombre: ''};
      this.resetDossier[2] = {nombre: ''};
    }
  }

  resetDroplistTecno(seccion:string) {
    if (seccion == 'Nombre') {
      this.resetTecno[0] = {nombre: 'Días'}
    }
    else {
      this.resetTecno[1] = {nombre: ''};
      this.resetTecno[2] = {nombre: ''};
    }
  }

  resetDroplistSom(seccion:string) {
    if (seccion == 'Nombre') {
      this.resetSom[0] = {nombre:'Días'}
    }
    else {
      this.resetSom[1] = {nombre: ''};
      this.resetSom[2] = {nombre: ''};
    }
  }

  resetDroplistProrro(seccion:string){
    if (seccion == 'Nombre') {
      this.resetProrro[0] = {nombre:'Días'}
    }
    else {
      this.resetProrro[1] = {nombre: ''};
      this.resetProrro[2] = {nombre: ''};
    }
  }


  dropListDossier($event, index){
    switch (index) {
      case 1:
        this.dossier.tipoPeriodo = $event.nombre;
        this.calculateFRealizacion(1);
        this.validarFRealizacion(2);
        break;
      case 2:
        this.notificaciones.tipoPeriodo = $event.nombre;
        break;
      case 3:
        this.notificaciones.recurrencia = $event.nombre;
        break;
      default:
        break;
    }
  }

  dropListTecno($event, index){
    switch (index) {
      case 1:
        this.tecnovigilancia.tipoPeriodo = $event.nombre;
        this.calculateFRealizacion(2);
        this.validarFRealizacion(3);
        break;
      case 2:
        this.notificaciones.tipoPeriodo = $event.nombre;
        break;
      case 3:
        this.notificaciones.recurrencia = $event.nombre;
        break;
      default:
        break;
    }
  }

  dropListSom($event, index){
    switch (index) {
      case 1:
        this.sometimiento.tipoPeriodo = $event.nombre;
        this.calculateFRealizacion(3);
        this.validarFRealizacion(4);
        break;
      case 2:
        this.notificaciones.tipoPeriodo = $event.nombre;
        break;
      case 3:
        this.notificaciones.recurrencia = $event.nombre;
        break;
      default:
        break;
    }
  }

  dropListProrro($event, index){
    switch (index) {
      case 1:
        this.prorroga.tipoPeriodo = $event.nombre;
        this.calculateFRealizacion(4);
        this.validarFRealizacion(5);
        break;
      case 2:
        this.notificaciones.tipoPeriodo = $event.nombre;
        break;
      case 3:
        this.notificaciones.recurrencia = $event.nombre;
        break;
      default:
        break;
    }
  }


  dropListTiempoR($event, i){
    this.listEventos[i].tipoPeriodo = $event.nombre;
    this.calculateFRealizacionEvento(i);
    this.validarFRealizacionEvento(i);
  }

  /* ------------------------------------------------------------ /Drop list-------------------------------------------------------------- */

  defaultValuesEvent(){
    this.resetNuevo.fill({nombre: ''});
    this.resetDossier.fill({nombre: 'Días'});
    this.resetDossier[1] = {nombre: ''};
    this.resetDossier[2] = {nombre: ''};
    this.resetTecno.fill({nombre: 'Días'});
    this.resetTecno[1] = {nombre: ''};
    this.resetTecno[2] = {nombre: ''};
    this.resetSom.fill({nombre:'Días'});
    this.resetSom[1] = {nombre: ''};
    this.resetSom[2] = {nombre: ''};
    this.resetProrro.fill({nombre: 'Días'});
    this.resetProrro[1] = {nombre: ''};
    this.resetProrro[2] = {nombre: ''};
  }

  tooltip(index){
    this.indexLine = index;
    this.showTooltip[index] ='1';
    //this.contentTooltip = this.etiquetasBar[index];
  }

  tooltipHide(index){
    this.showTooltip[index] = '0';
  }

  confirm(open: boolean, index) {
    SessionUser.getInstance().setConfirm(true);
    if (open) {
      this.classAnimation = 'w3-animate-zoom';
      document.getElementById('idConfirm').style.display = 'block'
    } else {
      this.classAnimation = 'w3-animate-zoom-out';
      document.getElementById('idConfirm').style.display = 'none';
      SessionUser.getInstance().setConfirm(false);
    }

    if(index == 1){
      this.section = 1;
    }

  }

  checkF($event) {
    $event.stopPropagation();
    this.check = !this.check;
  }

  calculateFRealizacion(index){
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

  addEliminarNotificacion(item){
    this.notifEliminar += item +',';
    DEBUG && console.log(this.notifEliminar);
  }

  addEliminarCorreo(item){
    this.correoEliminar += item +',';
    DEBUG && console.log(this.correoEliminar);
  }


  validarFRealizacion(index){
    switch (index) {
      case 1:
        if(this.evento.tipoPeriodo !== '' && this.evento.cantPeriodo !== 0){
          DEBUG && console.log('Entra');
          let da = this.calculateFRealizacionNuevo(this.evento.tipoPeriodo, this.evento.cantPeriodo);
          da.setDate(da.getDate() - 1);
          this.validDate[0] = this.fRealizacionArray[0] > da ? false : true;
        }
        break;
      case 2:
        if(this.dossier.tipoPeriodo !== '' && this.dossier.cantPeriodo !== 0){
          DEBUG && console.log('Entra');
          let da = this.calculateFRealizacionNuevo(this.dossier.tipoPeriodo, this.dossier.cantPeriodo);
          da.setDate(da.getDate() - 1);
          this.validDate[1] = this.fRealizacionArray[0] > da ? false : true;
        }
        break;
      case 3:
        if(this.tecnovigilancia.tipoPeriodo !== '' && this.tecnovigilancia.cantPeriodo !== 0){
          DEBUG && console.log('Entra');
          let da = this.calculateFRealizacionNuevo(this.tecnovigilancia.tipoPeriodo, this.tecnovigilancia.cantPeriodo);
          da.setDate(da.getDate() - 1);
          this.validDate[2] = this.fRealizacionArray[0] > da ? false : true;
        }
        break;
      case 4:
        if(this.sometimiento.tipoPeriodo !== '' && this.sometimiento.cantPeriodo !== 0){
          DEBUG && console.log('Entra');
          let da = this.calculateFRealizacionNuevo(this.sometimiento.tipoPeriodo, this.sometimiento.cantPeriodo);
          da.setDate(da.getDate() - 1);
          this.validDate[3] = this.fRealizacionArray[0] > da ? false : true;
        }
        break;
      case 5:
        if(this.prorroga.tipoPeriodo !== '' && this.prorroga.cantPeriodo !== 0){
          DEBUG && console.log('Entra' + this.prorroga.tipoPeriodo + " - " + this.prorroga.cantPeriodo);
          let da = this.calculateFRealizacionNuevo(this.prorroga.tipoPeriodo, this.prorroga.cantPeriodo);
          da.setDate(da.getDate() - 1);
          this.validDate[4] = this.fRealizacionArray[0] > da ? false : true;
        }
        break;

      default:
        break;
    }
    DEBUG && console.log(this.validDate);
  }

  validarFRealizacionEvento(index){
    if(this.listEventos[index].tipoPeriodo !== '' && this.listEventos[index].cantPeriodo !== 0){
      DEBUG && console.log('Entra');
      let da = this.calculateFRealizacionNuevo(this.listEventos[index].tipoPeriodo, this.listEventos[index].cantPeriodo);
      da.setDate(da.getDate() - 1);
      //this.validDate[0] = this.fRealizacionArray[0] > da ? false : true;
    }
  }

  /* ******************************************************  Botones agregar default  ****************************************************** */
  agregarNotificacion(index:any){
    if(this.diasInput[index] !== 0 && this.dropListTipoPeriodo[index].nombre !== '' && this.dropListRecurrencia[index].nombre !== ''){
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

  borrarNotificacion(ev:any, item:any, data){
    this.addEliminarNotificacion(data.idNotificacion);
    this.listEventos[ev].notificaciones.splice(item, 1);
    DEBUG && console.log("******************+ lista Notificaciones ***************");
    DEBUG && console.log(this.listEventos);


  }

  agregarCorreo(index:any){
    if(this.correoInput[index] !== ''){
      let flag = false;
      for(let item of this.listEventos[index].correos){
        if(item.correo === this.correoInput[index]){
          flag = true;
          break;
        }
      }
      if(!flag){
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

  borrarCorreo(ev:any, item:any, data){
    this.addEliminarCorreo(data.idCorreo);
    this.listEventos[ev].correos.splice(item, 1);
    DEBUG && console.log("******************+ lista Correo ***************");
    this.borrarCorreosTotal();
    DEBUG && console.log(this.listEventos);
  }

  /* ******************************************************  Botones agregar default  ****************************************************** */

  /* ****************************** Nuevo evento llenar y borrar ******************************  */
  agregarNuevoNotificacion(){
    if(this.gEventosForm1.get('dias2').value !== 0 && this.notificaciones.tipoPeriodo !== '' && this.notificaciones.recurrencia !== ''){
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

  agregarNuevoCorreo(){
    if(this.gEventosForm2.get('correoN').value !== ''){
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

  borrarNuevoNotificacion(data: number){
    this.listNotificacionesNuevo.splice(data, 1);
    DEBUG && console.log("******************+ lista nuevo notificaciones ***************");
    DEBUG && console.log(this.listNotificacionesNuevo);
  }

  borrarNuevoCorreo(data: number){
    this.listCorreosNuevo.splice(data, 1);
    DEBUG && console.log("******************+ lista nuevo correos ***************");
    DEBUG && console.log(this.listCorreosNuevo);
  }
  /* ****************************** Eventos crear y borrar ******************************  */
  generarNuevoEvento(){
    if (this.evento.cantPeriodo !== 0 && this.evento.nombre !== '' && this.evento.tipoPeriodo !== '' && this.listNotificacionesNuevo.length > 0 && this.listCorreosNuevo.length > 0) {
      let auxCorreo = Object.assign([], this.listCorreosNuevo);
      let auxNotificaciones = Object.assign([], this.listNotificacionesNuevo);
      this.evento.correos = auxCorreo;
      this.evento.notificaciones = auxNotificaciones;
      this.evento.frealizacion = this.calculateFRealizacionNuevo(this.evento.tipoPeriodo, this.evento.cantPeriodo);

      for(let item of this.evento.notificaciones){
        item.finicio = this.calculateFInicioNuevo(this.evento.frealizacion,item.tipoPeriodo, item.cantPeriodo);
      }

      for(let item of this.evento.correos){
        this.listCorreosTotal(item.correo);
      }

      let aux = Object.assign({},this.evento);
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
    this.arrayValidDias1.push(false);
    this.arrayValidDias2.push(false);
  }

  borrarEvento(index: number){
    this.listEventos.splice(index, 1);

    this.diasInput.splice(index, 1);

    /* TODO: Esta linea elimina directamente opciones del droplist */
    // this.dropListPeriodoTiempo.splice(index, 1);

    this.dropListTipoPeriodo.splice(index, 1);
    this.dropListRecurrencia.splice(index, 1);

    DEBUG && console.log(this.listEventos);
  }

  /*  ****************************** Dossier Agregar y borrar  ****************************** */
  agregarNotificacionDossier(){
    if(this.dia1Dossier !== 0 && this.notificaciones.tipoPeriodo !== '' && this.notificaciones.recurrencia !== ''){
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
  borrarDossierNotificacion(data: number, item){
    DEBUG && console.log(item);
    this.addEliminarNotificacion(item.idNotificacion);
    this.listNotificacionesDossier.splice(data, 1);
    DEBUG && console.log("******************+ lista Dossier notificaciones ***************");
    DEBUG && console.log(this.listNotificacionesDossier);
  }

  agregarDossierCorreo(){
    if(this.correoDossier !== ''){
      let flag = false;
      for(let item of this.listCorreosDossier){
        if(item.correo === this.correoDossier){
          flag = true;
          break;
        }
      }
      if(!flag){
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

  borrarDossierCorreo(data: number, item){
    this.addEliminarCorreo(item.idCorreo);
    this.listCorreosDossier.splice(data, 1);
    DEBUG && console.log("******************+ lista Dossier correos ***************");
    this.borrarCorreosTotal();
    DEBUG && console.log(this.listCorreosDossier);
  }

  /*  ****************************** Tecnovigilancia Agregar y borrar  ****************************** */
  agregarNotificacionTecno() {
    if(this.dia1Tecno !== 0 && this.notificaciones.tipoPeriodo !== '' && this.notificaciones.recurrencia !== '') {
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
  borrarTecnoNotificacion(data: number, item){
    this.addEliminarNotificacion(item.idNotificacion);
    this.listNotificacionesTecno.splice(data, 1);
    DEBUG && console.log("******************+ lista Tecnovigilancia notificaciones ***************");
    DEBUG && console.log(this.listNotificacionesTecno);
  }

  agregarTecnoCorreo() {
    if(this.correoTecno !== '') {
      let flag = false;
      for(let item of this.listCorreosTecno){
        if(item.correo === this.correoTecno){
          flag = true;
          break;
        }
      }
      if(!flag){
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

  borrarTecnoCorreo(data: number, item){
    this.addEliminarCorreo(item.idCorreo);
    this.listCorreosTecno.splice(data, 1);
    DEBUG && console.log("******************+ lista Tecnovigilancia correos ***************");
    this.borrarCorreosTotal();
    DEBUG && console.log(this.listCorreosTecno);
  }

  /*  ****************************** Sometimiento Agregar y borrar  ****************************** */
  agregarNotificacionSom(){
    if(this.dia1Som !== 0 && this.notificaciones.tipoPeriodo !== '' && this.notificaciones.recurrencia !== ''){
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
  borrarSomNotificacion(data: number, item){
    this.addEliminarNotificacion(item.idNotificacion);
    this.listNotificacionesSom.splice(data, 1);
    DEBUG && console.log("******************+ lista Sometimiento notificaciones ***************");
    DEBUG && console.log(this.listNotificacionesSom);
  }

  agregarSomCorreo(){
    if(this.correoSom !== ''){
      let flag = false;
      for(let item of this.listCorreosSom){
        if(item.correo === this.correoSom){
          flag = true;
          break;
        }
      }
      if(!flag){
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

  borrarSomCorreo(data: number, item){
    this.addEliminarCorreo(item.idCorreo);
    this.listCorreosSom.splice(data, 1);
    DEBUG && console.log("******************+ lista Sometimiento correos ***************");
    this.borrarCorreosTotal();
    DEBUG && console.log(this.listCorreosSom);
  }

  /*  ****************************** Prorroga Agregar y borrar  ****************************** */
  agregarNotificacionProrro() {
    if(this.dia1Prorro !== 0 && this.notificaciones.tipoPeriodo !== '' && this.notificaciones.recurrencia !== '') {
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
  borrarProrroNotificacion(data: number, item){
    this.addEliminarNotificacion(item.idNotificacion);
    this.listNotificacionesProrro.splice(data, 1);
    DEBUG && console.log("******************+ lista Prorroga notificaciones ***************");
    DEBUG && console.log(this.listNotificacionesProrro);
  }

  agregarProrroCorreo(){
    if(this.correoProrro !== ''){
      let flag = false;
      for(let item of this.listCorreosProrro){
        if(item.correo === this.correoProrro){
          flag = true;
          break;
        }
      }
      if(!flag){
        this.correos.correo = this.correoProrro;
        let aux = Object.assign({}, this.correos);
        this.listCorreosProrro.push(aux);

      }
      this.correos.reset();
      this.correoProrro = '';
      this.validEmailPro = false;
    }
    DEBUG && console.log(this.listCorreosProrro);
  }

  borrarProrroCorreo(data: number, item){
    this.addEliminarCorreo(item.idCorreo);
    this.listCorreosProrro.splice(data, 1);
    DEBUG && console.log("******************+ lista Prorroga correos ***************");
    DEBUG && console.log(this.listCorreosProrro);
  }

  /* ********************************************************* Finalizar Registro ********************************************************* */

  finalizarRegistro() {
    this.rTerminado = true;
    this.guardarRegistro();
  }


  /* ********************************************************* Guadar Registro ********************************************************* */

  guardarRegistro() {
    DEBUG && console.log(this.registro);
    this.registro.eventos = [];

    let register = {...this.registro };
    register.producto =  Object.assign({}, this.producto);

    /* Nos aseguramos que el tipo de dato sea Date */
    register.finicio = new Date(register.finicio);

    register.idRegistro = this.idRegistro;
    register.idEmpresa = SessionUser.getInstance().getidEmpresaActual();

    let listNotificationError = [];
    this.listNotificationError = [];

    /* Revisar que se encuentren habilitados y aun en proceso los eventos definidos */

    if (this.dossier.idEvento > 0 && !this.dossier.terminado) {
      this.dossier.notificaciones = this.listNotificacionesDossier;

      for (let item of this.dossier.notificaciones) {
        DEBUG && console.log(this.dossier.frealizacion);
        item.finicio = this.calculateFInicioNuevo(new Date(this.dossier.frealizacion),item.tipoPeriodo, item.cantPeriodo);
        if (new Date(register.finicio).getTime() > new Date(item.finicio).getTime()) {
          this.findInvalidDate(listNotificationError, this.dossier.nombre, item.finicio, item.tipoPeriodo, item.cantPeriodo, item.recurrencia);
        }
      }

      this.dossier.correos = this.listCorreosDossier;
      register.eventos.push(Object.assign({}, this.dossier));
    }

    if (this.tecnovigilancia.idEvento > 0 && !this.tecnovigilancia.terminado) {
      this.tecnovigilancia.notificaciones = this.listNotificacionesTecno;

      for (let item of this.tecnovigilancia.notificaciones) {
        item.finicio = this.calculateFInicioNuevo(new Date(this.tecnovigilancia.frealizacion),item.tipoPeriodo, item.cantPeriodo);
        if (new Date(register.finicio).getTime() > new Date(item.finicio).getTime()) {
          this.findInvalidDate(listNotificationError, this.tecnovigilancia.nombre, item.finicio, item.tipoPeriodo, item.cantPeriodo, item.recurrencia);
        }
      }

      this.tecnovigilancia.correos = this.listCorreosTecno;
      register.eventos.push(Object.assign({}, this.tecnovigilancia));
    }

    if (this.sometimiento.idEvento > 0 && !this.sometimiento) {
      this.sometimiento.notificaciones = this.listNotificacionesSom;

      for (let item of this.sometimiento.notificaciones) {
        item.finicio = this.calculateFInicioNuevo(new Date(this.sometimiento.frealizacion),item.tipoPeriodo, item.cantPeriodo);
        if (new Date(register.finicio).getTime() > new Date(item.finicio).getTime()) {
          this.findInvalidDate(listNotificationError, this.sometimiento.nombre, item.finicio, item.tipoPeriodo, item.cantPeriodo, item.recurrencia);
        }
      }

      this.sometimiento.correos = this.listCorreosSom;
      register.eventos.push(Object.assign({}, this.sometimiento));
    }

    if (!this.prorroga.terminado) {
      this.prorroga.notificaciones = this.listNotificacionesProrro;
      for (let item of this.prorroga.notificaciones) {
        item.finicio = this.calculateFInicioNuevo(new Date(this.prorroga.frealizacion),item.tipoPeriodo, item.cantPeriodo);
        if (new Date(register.finicio).getTime() > new Date(item.finicio).getTime()) {
          this.findInvalidDate(listNotificationError, this.prorroga.nombre, item.finicio, item.tipoPeriodo, item.cantPeriodo, item.recurrencia);
        }
      }

      this.prorroga.correos = this.listCorreosProrro;
      let auxPro = Object.assign({}, this.prorroga);

      if (this.check) {
        register.eventos.push(auxPro);
        register.prorroga = true;
      }
    }

    for (let item of this.listEventos) {
      if (!item.terminado) {
        register.eventos.push(item);
        _.forEach(item.notificaciones, notification => {
          notification.finicio = this.calculateFRealizacionNuevo(notification.tipoPeriodo, notification.cantPeriodo, false, item.frealizacion);
          if (new Date(register.finicio).getTime() > new Date(notification.finicio).getTime()) {
            this.findInvalidDate(listNotificationError, item.nombre, notification.finicio, notification.tipoPeriodo, notification.cantPeriodo, notification.recurrencia);
          }
        });
      }
    }

    register.terminado = this.rTerminado;

    if (listNotificationError.length > 0) {
      this.listNotificationError = listNotificationError.slice();
      return;
    }

    this.itemEventEffect = new Array(3).fill('body-itemE-close');

    DEBUG && console.log(register);
    SessionUser.getInstance().setLoading(true);

    this.registrosService.guardarRegistro(register)
      .subscribe(
        async data => {
          DEBUG && console.log(data);

          let statusNotifications: any = true;
          let statusEmail:any = true;

          if (this.notifEliminar !== '') {
            statusNotifications = await this.eliminarNotificaciones();
          }
          if (this.correoEliminar !== '') {
            statusEmail = await this.eliminarCorreos();
          }
          SessionUser.getInstance().setLoading(false);
          if (statusNotifications && statusEmail) {
            SessionUser.getInstance().setMessageOk('Has guardado');
            document.getElementById('idOk').style.display = 'block';
            if (this.rTerminado) {
              this.irLibreriaRegistros();
            }
            this.registro.reset();
            this.obtenerRegistro();
            this.section = 1;
            //this.ngOnInit();
          } else {
            SessionUser.getInstance().setMessageFailed(`al guardar el trámite ${!statusNotifications ? '- Notificación ' : ''} ${!statusEmail ? '- Correo' : ''}`);
            document.getElementById('requestFailed').style.display = 'block';
          }
        },
        error => {
          DEBUG && console.log('Error al guardar el registro: '+error);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al guardar el trámite');
          document.getElementById('requestFailed').style.display = 'block';
        });

    /* DEBUG && console.log(this.registro);
    DEBUG && console.log(JSON.stringify(this.registro)); */

  }


  /* **************************************************+ Obtener rgistro **************************************************+ */

  obtenerRegistro() {
    SessionUser.getInstance().setLoading(true);
    this.openFolio = false;
    this.openProrro = false;
    let parametro = new Parametros();
    parametro.id = this.idRegistro;
    this.registrosService.obtenerRegistroPorId(parametro)
      .subscribe(
        data => {
          DEBUG && console.log(data);

          this.listNotificacionesDossier = new Array<Notificacion>();
          this.listCorreosDossier = new Array<CorreoEvento>();
          this.listNotificacionesTecno = new Array<Notificacion>();
          this.listCorreosTecno = new Array<CorreoEvento>();
          this.listNotificacionesSom = new Array<Notificacion>();
          this.listCorreosSom = new Array<CorreoEvento>();
          this.listNotificacionesProrro = new Array<Notificacion>();
          this.listCorreosProrro = new Array<CorreoEvento>();

          this.notifEliminar = '';
          this.correoEliminar = '';

          this.widthBar = 0;

          this.fRealizacionArray = [];

          this.registro = null;
          this.producto = Object.assign({}, data.producto);
          this.registro = Object.assign(new Registro, data);

          let u = Object.assign(new Usuario, data.usuario);

          this.registro.ffin = new Date(this.registro.ffin);
          this.registro.finicio = new Date(this.registro.finicio);

          DEBUG && console.log(u);
          this.registro.usuario = new Usuario();
          this.registro.usuario = u;
          DEBUG && console.log(this.registro.finicioProrroga);

          let array = this.registro.estadoCofepris.split("|");
          this.registro.estadoCofepris = array[0];
          this.registro.fecha = array[1] !== undefined ? new Date(array[1]) : null;

          if(this.registro.ffinProrroga !== null && this.registro.finicioProrroga !== null){
            this.registro.ffinProrroga = new Date(this.registro.ffinProrroga);
            this.registro.finicioProrroga = new Date(this.registro.finicioProrroga);

            this.datePro1 = this.registro.finicioProrroga !== undefined ? this.registro.finicioProrroga : new Date() ;
            this.datePro2 = this.registro.ffinProrroga !== undefined ? this.registro.ffinProrroga : new Date();
          }

          for (let item of this.registro.eventos) {
            item.historial = [];
            DEBUG && console.log(item.totalHistorial);
            if(item.terminado){
              this.fRealizacionArray.push(new Date(item.frealizacion));
            }
          }

          this.fRealizacionArray = this.fRealizacionArray.sort((a, b) => (a > b) ? -1 : 1);
          DEBUG && console.log(this.fRealizacionArray);

          DEBUG && console.log(this.registro);
          if (data.prorroga) {
            this.registro.prorroga = true;
            this.check = true;
            let index: number = -1;
            let flag = false;
            for (let item of this.registro.eventos) {
              if(item.nombre === 'Prorroga' && item.definido){
                DEBUG && console.log(item);
                this.prorroga = Object.assign(new Eventos, item);
                /*
                 *  Formatear la fecha a objeto del tipo DATE
                 *
                 */
                this.prorroga.frealizacion = new Date(item.frealizacion);
                this.prorroga.historial = [];
                DEBUG && console.log(this.prorroga);
                this.resetProrro[0] = {nombre:this.prorroga.tipoPeriodo};
                index++;
                flag = true;
                break;
              }
              index++;
            }
            this.listCorreosProrro = [];
            this.listNotificacionesProrro = [];
            for(let item of this.prorroga.correos){
              this.listCorreosProrro.push(item);
            }
            for (let item of this.prorroga.notificaciones) {
              this.listNotificacionesProrro.push(item);
            }
            DEBUG && console.log(index);
            if (index !== -1 && flag) {
              this.registro.eventos.splice(index, 1)
            }
          }
          let index2: number = -1;
          let flag2 = false;
          for (let item of this.registro.eventos) {
            if (item.nombre == 'Folio Registro' && item.definido) {
              this.folio = Object.assign(new Eventos, item);
              this.folio.historial = [];
              index2++;
              flag2 = true;
              break;
            }
            index2++;
          }
          if (index2 !== -1 && flag2) {
            this.registro.eventos.splice(index2, 1);
          }
          DEBUG && console.log(this.folio);

          this.switchArray = new Array<boolean>(this.registro.eventos.length).fill(false);

          if (this.registro.eventos.length > 0) {
            if (this.registro.eventos[this.registro.eventos.length - 1].terminado == true){
              this.switchArray[this.registro.eventos.length - 1] = true;
            }
          }

          this.open = new Array<boolean>().fill(false);
          this.etiquetasBar = new Array<string>();
          let pipe = new dateFormatNumber();

          for(let item of this.registro.eventos){
            item.frealizacionAux = new Date(item.frealizacion);
            if(item.terminado){
              item.frealizacion = item.fterminado;
            }
          }

          //this.etiquetasBar.push(pipe.transform(this.registro.finicio));
          this.arrayFile = new Array<FileList>(this.registro.eventos.length).fill(null);
          this.textArea = new Array<string>(this.registro.eventos.length).fill('');
          this.fileName = new Array<string>(this.registro.eventos.length).fill('');
          this.itemNewEventEffect = new Array<string>(this.registro.eventos.length).fill('body-itemE-close');
          //this.diasInput = new Array<number>(this.registro.eventos.length).fill(0);
          this.contentTooltip = [];
          for (let item of this.registro.eventos) {
            this.etiquetasBar.push(pipe.transform(item.frealizacion));
            this.contentTooltip.push(item.nombre);

          }

          //this.etiquetasBar.push(pipe.transform(this.registro.ffin));
          DEBUG && console.log(this.etiquetasBar);
          this.lines = new Array(this.etiquetasBar.length).fill(1);

          for (let item of this.registro.eventos) {

            if (item.terminado) {
              DEBUG && console.log('Entra');
              DEBUG && console.log(this.widthBar);
              this.widthBar = this.widthBar + ( 100 / (this.lines.length - 1) );
            }
          }

          if (this.folio.terminado) {
            this.widthBar = this.widthBar + ( 100 / (this.lines.length - 1) );
          }

          for (let item of this.registro.eventos) {
            if(item.terminado == false){
              this.eventoActual = item.nombre;
              break;
            }
          }
          DEBUG && console.log(this.eventoActual);
          DEBUG && console.log(this.folio);

          this.setEventosDefault();

          this.validarFRealizacion(2);
          this.validarFRealizacion(3);
          this.validarFRealizacion(4);
          this.validarFRealizacion(5);

          this.borrarCorreosTotal();

          this.showTooltip = new Array<string>(this.registro.eventos.length).fill('0');
          this.diasPorevento = new Array<string>(this.registro.eventos.length).fill('');

          this.fechaAtiempo = new Date(this.registro.ffin);
          DEBUG && console.log(this.fechaAtiempo);

          for (let item of this.registro.eventos) {
            DEBUG && console.log(item.frealizacionAux);
            DEBUG && console.log(item.fterminado);
            if(item.fterminado !== undefined){
              let d =  new Date(item.fterminado);
              /* d.setDate(d.getDate() - 1); */
              if(new Date(item.frealizacionAux) < d){
                item.aTiempo = true;
              }else{
                item.aTiempo = false;
              }
            }
          }
          this.diasPorEventos();

          if (this.folio.fterminado !== undefined) {
            let d =  new Date(this.folio.fterminado);
            /* d.setDate(d.getDate() - 1); */
            if(new Date(this.folio.frealizacion) < d) {
              this.folio.aTiempo = true;
            } else {
              this.folio.aTiempo = false;
            }
          }

          this.responsableGestion = this.registro.usuario.email;

          if (this.registro.eventos.length > 0) {
            this.calcularFechaLimite();
            this.lineTodayPosition();
          } else if (this.registro.eventos.length === 0) {
            this.lineTodayPositionWithoutEvents();
          }

          this.buscarUsuarios();

          SessionUser.getInstance().setLoading(false);
          DEBUG && console.log(this.registro);

          /* Se guardan los eventos para recalcular las fechas de las notificaciones en caso de ser requerido */
          this.backupEvents = [...this.registro.eventos];

          /* Reset de las notificaciones fuera de tiempo */
          this.listNotificationError = [];

        },
        error => {
          DEBUG && console.log('Error al obtener registro: '+error);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al obtener el registro');
          document.getElementById('requestFailed').style.display = 'block';
        });
  }


  setEventosDefault(){
    this.listEventos = [];
    let countEventsDefined = 0;
    let enableDossier = false;
    let enableTecnovigilancia = false;
    let enableSometimiento = false;

    _.forEach(this.registro.eventos, o => {
      /**
       * Convertir la fecha en un objeto valido
       *
       */
      o.frealizacion = new Date(o.frealizacion);
      if (o.nombre == 'Dossier' && o.definido) {
        this.dossier = Object.assign(new Eventos, o);
        this.resetDossier[0] = {nombre:this.dossier.tipoPeriodo};
        countEventsDefined++;
        enableDossier = true;
      }
      if (o.nombre == 'Tecnovigilancia' && o.definido) {
        this.tecnovigilancia = Object.assign(new Eventos, o);
        this.resetTecno[0] = {nombre:this.tecnovigilancia.tipoPeriodo};
        countEventsDefined++;
        enableTecnovigilancia = true;
      }
      if (o.nombre == 'Sometimiento' && o.definido) {

        this.sometimiento = Object.assign(new Eventos, o);
        this.resetSom[0] = {nombre:this.sometimiento.tipoPeriodo};
        countEventsDefined++;
        enableSometimiento = true;
      }
      if(o.definido == false){
        this.listEventos.push(Object.assign(new Eventos, o));
      }
    });

    this.numberDossier = enableDossier ? 1 : 0;
    this.numberTecno = enableTecnovigilancia ? 1 + this.numberDossier : this.numberDossier;
    this.numberSom = enableSometimiento ? 1 + this.numberTecno : 0;

    this.listCorreosDossier = [...this.dossier.correos];
    this.listNotificacionesDossier = [...this.dossier.notificaciones];

    this.listCorreosTecno = [...this.tecnovigilancia.correos];
    this.listNotificacionesTecno = [...this.tecnovigilancia.notificaciones];

    this.listCorreosSom = [...this.sometimiento.correos];
    this.listNotificacionesSom = [...this.sometimiento.notificaciones];

    this.arrayValidDias1 = new Array<boolean>(this.listEventos.length).fill(false);
    this.arrayValidDias2 = new Array<boolean>(this.listEventos.length).fill(false);
    this.diasInput = new Array<number>(this.listEventos.length).fill(0);

    this.dropListTipoPeriodo = new Array<any>(this.listEventos.length).fill({nombre: ''});
    this.dropListRecurrencia = new Array<any>(this.listEventos.length).fill({nombre: ''});

    this.arrayValidEmail = new Array<boolean>(this.listEventos.length).fill(false);

    this.countEventsDefined = countEventsDefined;

    DEBUG && console.log(this.dossier);
    DEBUG && console.log(this.tecnovigilancia);
    DEBUG && console.log(this.sometimiento);
    DEBUG && console.log(this.listEventos);
  }

  /* ***************************************************************** file ***************************************************************** */


  cargarReferencia(id, index){
    this.classAnimation2 = 'w3-animate-zoom';
    document.getElementById('id01').style.display = 'block';
    this.idEventoActualCarga = id;
    this.indexActualCarga = index;
    DEBUG && console.log(id);
  }

  salir_light(){
    this.file_light = null;
    this.arrayFileHistorialNuevos = [];
    this.arrayHistorialNuevos = [];
    this.fileName_light = '';
    this.textArea_light = '';

    //this.fileInput.nativeElement.value = '';
  }

  fileChange(index, $event) {
    this.cancelFile(index);
    DEBUG && console.log(index);
    DEBUG && console.log($event);
    this.arrayFile[index] = $event.target.files;
    this.fileName[index] = this.arrayFile[index][0].name;
    DEBUG && console.log(this.arrayFile[index]);
    DEBUG && console.log(this.fileName);
    DEBUG && console.log(this.arrayFile[index][0].name);
  }

  fileChange2( $event) {
    DEBUG && console.log($event);
    if($event.target.files.length > 0){
      this.file_light = $event.target.files;
      this.fileName_light = $event.target.files[0].name;
    }
  }

  fileChangePro($event) {
    this.cancelFilePro();
    DEBUG && console.log($event);
    this.filePro = $event.target.files;
    this.fileNamePro = this.filePro[0].name;
  }

  fileChangeFol($event) {
    this.cancelFileFol();
    DEBUG && console.log($event);
    this.fileFol = $event.target.files;
    this.fileNameFolio = this.fileFol[0].name;
  }

  cancelFile(index){
    this.arrayFile[index] = null;
    this.fileName[index] = '';
  }

  cancelFile2(){
    this.file_light = null;
    this.fileName_light = '';
    //this.fileInput.nativeElement.value = '';
  }

  cancelFilePro(){
    this.filePro = null;
    this.fileNamePro = '';
  }

  cancelFileFol(){
    this.fileFol = null;
    this.fileNameFolio = '';
  }

  eliminarHistorialEvento_light(index){
    this.arrayHistorialNuevos.splice(index, 1);
    this.arrayFileHistorialNuevos.splice(index, 1);
  }


  eliminarHistorialEvento(index, item) {
    SessionUser.getInstance().setLoading(true);
    let parametro = new Parametros();
    parametro.idHistorial = item.idHistorial;
    this.registrosService.eliminarHistorialEvento(parametro)
      .subscribe(
        data =>{
          DEBUG && console.log(data);
          SessionUser.getInstance().setLoading(false);
          this.obtenerHistorial(item.idEvento, index);
        },
        error =>{
          DEBUG && console.log('Error al eliminar historial: '+error);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al eliminar el documento');
          document.getElementById('requestFailed').style.display = 'block';
        });
  }

  eliminarHistorialEventoPro(item) {
    SessionUser.getInstance().setLoading(true);
    let parametro = new Parametros();
    parametro.idHistorial = item.idHistorial;
    this.registrosService.eliminarHistorialEvento(parametro)
      .subscribe(
        data => {
          DEBUG && console.log(data);
          SessionUser.getInstance().setLoading(false);
          this.obtenerHistorialPro( this.prorroga.idEvento);
        },
        error => {
          DEBUG && console.log('Error al eliminar historial: '+ error);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al eliminar el documento');
          document.getElementById('requestFailed').style.display = 'block';
        });
  }

  eliminarHistorialEventoFol(item) {
    SessionUser.getInstance().setLoading(true);
    let parametro = new Parametros();
    parametro.idHistorial = item.idHistorial;
    this.registrosService.eliminarHistorialEvento(parametro)
      .subscribe(
        data => {
          DEBUG && console.log(data);
          SessionUser.getInstance().setLoading(false);
          this.obtenerHistorialFol( this.folio.idEvento);
        },
        error => {
          DEBUG && console.log('Error al eliminar historial: '+error);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al eliminar el documento');
          document.getElementById('requestFailed').style.display = 'block';
        });
  }

  guardarHistorial() {
    SessionUser.getInstance().setLoading(true);
    this.classAnimation2 = 'w3-animate-zoom-out';
    document.getElementById('id01').style.display = 'none';
    let parametros = new Parametros();
    parametros.historial = this.arrayHistorialNuevos;

    DEBUG && console.log(parametros);

    this.registrosService.guardarHistorialEvento(parametros)
      .subscribe(
        async data => {
          DEBUG && console.log(data);
          let i = 0;
          let countError = 0;
          for (let item of data.historial) {
            const result = await this.loadFile(this.arrayFileHistorialNuevos[i],this.registro.idRegistro, this.registro.idEmpresa, item.idEvento, item.idHistorial);
            countError = result ? countError : countError + 1;
            i++;
          }
          if (this.indexActualCarga == -1) {
            this.obtenerHistorialFol( this.folio.idEvento);
          } if (this.indexActualCarga == -2) {
            this.obtenerHistorialPro( this.prorroga.idEvento);
          } if (this.indexActualCarga > -1) {
            this.obtenerHistorial(this.idEventoActualCarga, this.indexActualCarga);
          }
          this.idEventoActualCarga = 0;
          this.indexActualCarga = 0;
          this.arrayFileHistorialNuevos = [];
          this.arrayHistorialNuevos = [];
          this.fileInput.nativeElement.value = '';
          SessionUser.getInstance().setLoading(false);

          if (countError === 0) {
            SessionUser.getInstance().setMessageOk('Has guardado');
            document.getElementById('idOk').style.display = 'block';
          } else {
            SessionUser.getInstance().setMessageFailed('al cargar uno o más archivos');
            document.getElementById('requestFailed').style.display = 'block';
          }
        },
        error =>{
          DEBUG && console.log('Error al guardar historial '+error);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al cargar la referencia');
          document.getElementById('requestFailed').style.display = 'block';
        });


  }


  agregarHistorial_light(){
    DEBUG && console.log(this.file_light);

    this.arrayFileHistorialNuevos.push(this.file_light);
    let historial = new HistorialEvento();
    historial.comentarios = this.textArea_light;
    historial.idEvento = this.idEventoActualCarga;
    historial.idUsuario = this.registro.usuario.idUsuario;

    this.arrayHistorialNuevos.push(historial);

    DEBUG && console.log(this.arrayFileHistorialNuevos);

    this.file_light = null;
    this.fileName_light = '';
    this.textArea_light = '';
    //this.fileInput.nativeElement.value = '';

    DEBUG && console.log(this.arrayFileHistorialNuevos);
  }

  agregarHistorial(index, item){
    SessionUser.getInstance().setLoading(true);
    DEBUG && console.log(item);
    let historial = new HistorialEvento();
    historial.comentarios = this.textArea[index];
    historial.idEvento = item.idEvento;
    historial.idUsuario = this.registro.usuario.idUsuario;

    DEBUG && console.log(JSON.stringify(historial));

    /* this.registrosService.guardarHistorialEvento(historial)
    .subscribe(
      data => {
        DEBUG && console.log(data);
        this.obtenerHistorial(item.idEvento, index);
        this.loadFile(this.arrayFile[index],this.registro.idRegistro, this.registro.idEmpresa, item.idEvento, data);
        this.cancelFile(index);
      },
      error => {
        DEBUG && console.log('Error al guardar historial: '+error);
      }); */
  }


  agregarHistorialPro(){
    SessionUser.getInstance().setLoading(true);
    let historial = new HistorialEvento();
    historial.comentarios = this.textAreaPro;
    historial.idEvento = this.prorroga.idEvento;
    historial.idUsuario = this.registro.usuario.idUsuario;

    DEBUG && console.log(JSON.stringify(historial));

    /* this.registrosService.guardarHistorialEvento(historial)
    .subscribe(
      data => {
        DEBUG && console.log(data);
        this.obtenerHistorialPro( this.prorroga.idEvento);
        this.loadFile(this.filePro,this.registro.idRegistro, this.registro.idEmpresa, this.prorroga.idEvento, data);
        this.cancelFilePro();
      },
      error => {
        DEBUG && console.log('Error al guardar historial: '+error);
      }); */
  }

  agregarHistorialFol(){
    SessionUser.getInstance().setLoading(true);
    let historial = new HistorialEvento();
    historial.comentarios = this.textAreaFol;
    historial.idEvento = this.folio.idEvento;
    historial.idUsuario = this.registro.usuario.idUsuario;

    DEBUG && console.log(JSON.stringify(historial));

    /* this.registrosService.guardarHistorialEvento(historial)
    .subscribe(
      data => {
        DEBUG && console.log(data);
        this.obtenerHistorialFol( this.folio.idEvento);
        this.loadFile(this.fileFol,this.registro.idRegistro, this.registro.idEmpresa, this.folio.idEvento, data);
        this.cancelFileFol();
      },
      error => {
        DEBUG && console.log('Error al guardar historial: '+error);
      }); */
  }

  obtenerHistorial(id, i) {
    let parametros = new Parametros();
    parametros.id = id;
    this.registrosService.obtenerHistorialEvento(parametros)
      .subscribe(
        data =>{
          DEBUG && console.log(data);
          this.registro.eventos[i].historial = data;
        },
        error =>{
          DEBUG && console.log('Error al traer el historial: ' + error);
          SessionUser.getInstance().setMessageFailed('al obtener el historial');
          document.getElementById('requestFailed').style.display = 'block';
          return [];
        });
  }

  obtenerHistorialPro(id){
    let parametros = new Parametros();
    parametros.id = id;
    this.registrosService.obtenerHistorialEvento(parametros)
      .subscribe(
        data => {
          DEBUG && console.log(data);
          this.prorroga.historial = data;
        },
        error => {
          DEBUG && console.log('Error al traer el historial: ' + error);
          SessionUser.getInstance().setMessageFailed('al obtener el historial');
          document.getElementById('requestFailed').style.display = 'block';
          return [];
        });
  }

  obtenerHistorialFol(id){
    let parametros = new Parametros();
    parametros.id = id;
    this.registrosService.obtenerHistorialEvento(parametros)
      .subscribe(
        data => {
          DEBUG && console.log(data);
          this.folio.historial = data;
        },
        error =>{
          DEBUG && console.log('Error al traer el historial: '+ error);
          SessionUser.getInstance().setMessageFailed('al obtener el historial');
          document.getElementById('requestFailed').style.display = 'block';
          return [];
        });
  }

  historial(i, item){
    DEBUG && console.log(item);
    DEBUG && console.log(i);
    this.obtenerHistorial(item.idEvento, i);
  }

  historialFol(id){
    this.obtenerHistorialFol(id);
  }

  historialPro(id){
    this.obtenerHistorialPro(id);
  }

  loadFile(file,idEmpresa, idRegistro, idEvento, idHistorial) {
    return new Promise(resolve => {
      this.registrosService.loadFile(file, idEmpresa, idRegistro, idEvento, idHistorial)
        .subscribe(
          data => {
            DEBUG && console.log(data);
            resolve(true);
          },
          error => {
            DEBUG && console.log('error al subir el pdf: '+error);
            resolve(false);
          });
    });
  }

  getPdf(item) {
    SessionUser.getInstance().setLoading(true);
    this.registrosService.getPdf(this.registro.idEmpresa, this.registro.idRegistro, item.idEvento, item.idHistorial)
      .subscribe(
        data =>
        {
          DEBUG && console.log(data);
          if (data) {
            let pdfFile = 'data:application/pdf;base64,' + data;
            let element = document.createElement('a');
            element.setAttribute('href', pdfFile);
            element.setAttribute('download', 'Doc_'+item.idHistorial+'.pdf');
            element.style.display = 'none';
            document.body.appendChild(element);
            element.click();
            document.body.removeChild(element);
          } else {
            SessionUser.getInstance().setMessageFailed('al descargar el archivo');
            document.getElementById('requestFailed').style.display = 'block';
          }
          SessionUser.getInstance().setLoading(false);
        },
        error =>{
          DEBUG && console.log('Error al traer el pdf: '+ error);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al descargar el archivo');
          document.getElementById('requestFailed').style.display = 'block';
        });
  }

  /**
   *
   * Cierra las notificaciones del evento
   * @param notificaciones las notificaciones del evento
   */
  terminaNotificacionesEvento(notificaciones) {
    return new Promise(resolve => {
      let parametros = new Parametros();
      parametros.notificaciones = _.cloneDeep(notificaciones);
      _.forEach(parametros.notificaciones, notificacion => {
        notificacion.terminado = true;
      });

      this.registrosService.actualizaNotificaciones(parametros)
        .subscribe(
          data => {
            DEBUG && console.log(data);
            resolve(true);
          },
          error => {
            DEBUG && console.log('Error al terminar evento: ' + error);
            resolve(false);
          });
    });
  }

  realizarEvento(item) {
    return new Promise(resolve => {
      let parametro = new Parametros();
      parametro.id = item.idEvento;
      parametro.orden = 1;

      this.registrosService.realizarEvento(parametro)
        .subscribe(
          async data => {
            DEBUG && console.log(data);
            const result = await this.terminaNotificacionesEvento(item.notificaciones);
            resolve(result);
          },
          error => {
            DEBUG && console.log('Error al terminar evento: ' + error);
            resolve(false);
          });
    });
  }

  guardarElementoMonitoreoRegistro(parametro:Parametros){
    this.registrosService.guardarElementoMonitoreoRegistro(parametro)
      .subscribe(
        data => {
          DEBUG && console.log(data);
        },
        error => {
          DEBUG && console.log('Error al guardar elemento monitoreo: ' + error);
          SessionUser.getInstance().setMessageFailed('al guardar la información');
          document.getElementById('requestFailed').style.display = 'block';
        });
  }

  obtenerStatusTramiteCofepris(index) {
    let parametros = new Parametros();
    parametros.condicion = this.registro.eventos[index].tramiteCofepris;

    this.registrosService.obtenerStatusTramiteCofepris(parametros)
      .subscribe(
        data => {
          DEBUG && console.log(data);
          if (data && data.mediaValue) {
            let state = data.mediaValue.split('|');
            this.registro.eventos[index].estadoCofepris = state[0];
          }
        },
        error => {
          DEBUG && console.log('Error al obtener estado de cofepris: ' + error);
          SessionUser.getInstance().setMessageFailed('al obtener el estatus');
          document.getElementById('requestFailed').style.display = 'block';
        });
  }

  obtenerStatusTramiteCofeprisProrro() {
    let parametros = new Parametros();
    parametros.condicion = this.prorroga.tramiteCofepris;

    this.registrosService.obtenerStatusTramiteCofepris(parametros)
      .subscribe(
        data => {
          DEBUG && console.log(data);
          if (data && data.mediaValue) {
            let state = data.mediaValue.split('|');
            this.prorroga.estadoCofepris = state[0];
          }
        },
        error => {
          DEBUG && console.log('Error al obtener estado de cofepris: ' + error);
          SessionUser.getInstance().setMessageFailed('al obtener el estatus');
          document.getElementById('requestFailed').style.display = 'block';
        });
  }

  eliminarNotificaciones() {
    return new Promise(resolve => {
      let parametro = new Parametros();
      parametro.condicion = this.notifEliminar;
      this.registrosService.eliminarNotificaciones(parametro)
        .subscribe(
          data => {
            DEBUG && console.log(data);
            resolve(true);
          },
          error => {
            DEBUG && console.log('Error al eliminar las notificaciones: ' + error);
            resolve(false);
          });
    });
  }

  eliminarCorreos() {
    return new Promise(resolve => {
      let parametro = new Parametros();
      parametro.condicion = this.correoEliminar;
      this.registrosService.eliminarCorreos(parametro)
        .subscribe(
          data => {
            DEBUG && console.log(data);
            resolve(true);
          },
          error => {
            DEBUG && console.log('Error al eliminar los correos: ' + error);
            resolve(true);
          });
    });
  }

  listCorreosTotal(item){

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

  borrarCorreosTotal(){
    this.listCorreoT = [];
    for(let item of this.listCorreosDossier){
      this.listCorreosTotal(item.correo);
    }
    for(let item of this.listCorreosTecno){
      this.listCorreosTotal(item.correo);
    }
    for(let item of this.listCorreosSom){
      this.listCorreosTotal(item.correo);
    }
    for(let item of this.listEventos){
      for(let c of item.correos){
        this.listCorreosTotal(c.correo);
      }
    }

    DEBUG && console.log(this.listCorreoT);
  }

  clickBar(index){

    let v = 'itemId'+(index + 1)+'';
    DEBUG && console.log(v);
    this.open.fill(false);
    this.openFolio = false;
    this.open[index] = true;


    setTimeout(()=>{    //<<<---    using ()=> syntax

      let el = document.getElementById('scrollPage');

      try {
        el.scrollTop = 0;
        let itemHtml = document.getElementById(v);

        let cords = itemHtml.getBoundingClientRect();

        DEBUG && console.log(itemHtml.getBoundingClientRect());

        DEBUG && console.log(el.scrollLeft, el.scrollTop);

        DEBUG && console.log(scrollY);

        el.scrollLeft = 0;
        el.scrollTop = ((cords.top - el.scrollTop) - 80 - 100) - scrollY;
      } catch (error) {

      }
    },200)



  }

  clickBarFolio(){
    let v = 'itemIdFolio';
    DEBUG && console.log(v);
    this.open.fill(false);
    this.openFolio = true;


    setTimeout(()=> {    //<<<---    using ()=> syntax

      let el = document.getElementById('scrollPage');

      try {
        el.scrollTop = 0;
        let itemHtml = document.getElementById(v);

        let cords = itemHtml.getBoundingClientRect();

        DEBUG && console.log(itemHtml.getBoundingClientRect());

        DEBUG && console.log(el.scrollLeft, el.scrollTop);

        DEBUG && console.log(scrollY);

        el.scrollLeft = 0;
        el.scrollTop = ((cords.top - el.scrollTop) - 80 - 100) - scrollY;
      } catch (error) {

      }
    },200)
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
          for(let it of this.users){
            if(it.user.idUsuario == this.registro.usuario.idUsuario){
              this.selectUser = it;
            }
          }
        },
        error => {
          DEBUG && console.log('Error al buscar usuarios: ' + error);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al obtener usuarios');
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

  lineTodayPosition() {
    DEBUG && console.log(this.registro.eventos);
    //let today = new Date();
    let index = -1;
    let igual = false;
    DEBUG && console.log(this.today);
    DEBUG && console.log(moment(this.today, "DD-MM-YYYY"));
    DEBUG && console.log(moment(this.registro.eventos[0].frealizacion).diff(moment(this.today), 'days'));



    /* if(moment(this.registro.finicio).diff(moment(this.today), 'days') > 0 || moment(this.registro.finicio, "DD-MM-YYYY").diff(moment(this.today, "DD-MM-YYYY"), 'days') == 0){

    } */

    if(moment(this.registro.eventos[0].frealizacion).diff(moment(this.today), 'days') > 0 ){ //menor que la primer fecha del primer evento.
      if(moment(this.registro.finicio, "DD-MM-YYYY").diff(moment(this.today, "DD-MM-YYYY"), 'days') >= 0){ //dia igual al inicio del registro.
        this.positionToday = '0';
      }else{   //dias entre la fecha inicio de registro y la primer fecha de realizacion del primer evento.
        let days = moment(this.registro.eventos[0].frealizacion).diff(moment(this.registro.finicio), 'days');
        let days2 = moment(this.today).diff(moment(this.registro.finicio), 'days');
        DEBUG && console.log(days);
        this.positionToday = ( ( ( (100 / (this.registro.eventos.length + 1) )/days) * (days2) ) + (0) );
        DEBUG && console.log(this.positionToday);
      }
    }else if(moment(this.registro.eventos[this.registro.eventos.length - 1].frealizacion).diff(moment(this.today), 'days') < 0){ //mayor al la fecha de reralizacion del ultimo evento
      if(moment(this.registro.ffin).diff(moment(this.today), 'days') <= 0){ //dia igual al final del registro.
        this.positionToday = '100';
      }else{   //dias entre la fecha final de registro y la ultima fecha de realizacion del ultimo evento.
        let days = moment(this.registro.ffin).diff(moment(this.registro.eventos[this.registro.eventos.length - 1].frealizacion), 'days');
        let days2 = moment(this.today).diff(moment(this.registro.eventos[this.registro.eventos.length - 1].frealizacion), 'days');
        DEBUG && console.log(days);
        this.positionToday = ( ( ( (100 / (this.registro.eventos.length + 1) )/days) * (days2) ) + ((100 / (this.registro.eventos.length + 1) ) * this.registro.eventos.length ) );
        DEBUG && console.log(this.positionToday);
      }
    }else{ // Dia entre las fechas de los eventos creados y por default
      for(let item of this.registro.eventos){
        index++;
        DEBUG && console.log(moment(this.today, "DD-MM-YYYY").diff(moment(item.frealizacion, "DD-MM-YYYY"), 'days'));
        if(moment(this.today, "DD-MM-YYYY").diff(moment(item.frealizacion, "DD-MM-YYYY"), 'days') == 0 ){
          DEBUG && console.log('misma fecha');
          igual = true;
          break;
        }else if(moment(this.today).diff(moment(item.frealizacion), 'days') < 0){
          break;
        }

      }

      if(igual){
        DEBUG && console.log(index);
        DEBUG && console.log(this.registro.eventos.length);
        this.positionToday = (((100 / (this.registro.eventos.length + 1) )) * (index + 1));
      }else if(this.registro.eventos.length > 1){
        DEBUG && console.log(this.registro.eventos[index].frealizacion);
        DEBUG && console.log(this.registro.eventos[index-1].frealizacion);
        let days = moment(this.registro.eventos[index].frealizacion).diff(moment(this.registro.eventos[index-1].frealizacion), 'days');
        let days2 = moment(this.today).diff(moment(this.registro.eventos[index - 1].frealizacion), 'days');
        DEBUG && console.log(days);
        this.positionToday = ( ( ( (100 / (this.registro.eventos.length + 1) )/days) * (days2) ) + ((100 / (this.registro.eventos.length + 1) ) * index ) );
        DEBUG && console.log(this.positionToday);
      }else{
        DEBUG && console.log(this.registro.eventos[index].frealizacion);
        let days = moment(this.registro.eventos[index].frealizacion).diff(moment(this.registro.ffin), 'days');
        let days2 = moment(this.today).diff(moment(this.registro.ffin), 'days');
        DEBUG && console.log(days);
        this.positionToday = ( ( ( (100 / (this.registro.eventos.length + 1) )/days) * (days2) ) + ((100 / (this.registro.eventos.length + 1) ) * index ) );
        DEBUG && console.log(this.positionToday);
      }
    }


    DEBUG && console.log(this.positionToday);

  }

  lineTodayPositionWithoutEvents() {
    const totalDays = moment(this.registro.ffin).diff(moment(this.registro.finicio), 'days');
    const today = moment(this.today).diff(moment(this.registro.finicio), 'days');
    this.positionToday = totalDays > 0 && today > 0 ? (today * 100) / totalDays : 0;
  }


  /* diasHabiles(cantPeriodo: number, frealizacion: Date, ffin: Date){
    let days = 0;
    while (days != cantPeriodo){
      days = this.workingDays( frealizacion, ffin);
      DEBUG && console.log(days);
        frealizacion.setDate(frealizacion.getDate() - (cantPeriodo - days ) );
    }
    return frealizacion;
  }

  workingDays(dateFrom, dateTo) {
    var from = moment(dateFrom, 'DD/MM/YYY'),
      to = moment(dateTo, 'DD/MM/YYY'),
      days = 0;

    while (!from.isAfter(to)) {
      // Si no es sabado ni domingo
      if (from.isoWeekday() !== 6 && from.isoWeekday() !== 7) {
        days++;
      }
      from.add(1, 'days');
    }
    return days;
  } */



  openModalUpdateRegister() {
    this.classAnimation2 = 'w3-animate-zoom';
    document.getElementById('id02').style.display = 'block';
    this.clear = true;
  }

  closeModalUpdateRegister() {
    this.resetValidate = true;
    setTimeout(() => {
      document.getElementById('id02').style.display='none';
      this.resetValidate = false;
    }, 198);
  }

  cancelUpdateRegistro() {
    this.clear = false;
    this.showError = false;
    this.registroUpdate = new Registro();
    this.registroUpdate.usuario = new Usuario();
    this.registroUpdate.producto = new Producto();
    this.closeModalUpdateRegister();
    this.activeInValidData = {
      name: false,
      idTramite: false,
      numberTramite: false
    }
  }

  updateRegistro() {

    let registroUpdate = {...this.registroUpdate};

    let endDate = registroUpdate.ffin ? registroUpdate.ffin : this.registro.ffin;

    /* Se realiza la conversión para evitar problemas de que las fechas no sean del tipo Date */
    let currentDate = new Date(this.registro.ffin);
    let updateDate = new Date(endDate);

    let successRecalculate = true;
    let messageError = 'Fuera de rango';

    /* Se cambio la fecha de finalización, recalculamos las fechas en notificaciones para todos los eventos que no han sido conciliados */
    if (currentDate.getTime() !== updateDate.getTime()) {

      let { status, data, message } = this.recalculateDates(updateDate);
      registroUpdate.eventos = data;
      successRecalculate = !status;
      messageError = message;
    }

    if (successRecalculate) {

      if (registroUpdate.producto.nombre == '') {
        registroUpdate.producto.nombre = this.producto.nombre;
      }
      if (registroUpdate.producto.codigo == '') {
        registroUpdate.producto.codigo = this.producto.codigo;
      }
      if (registroUpdate.tramiteCofepris == '') {
        registroUpdate.tramiteCofepris = this.registro.tramiteCofepris;
      }
      if (registroUpdate.usuario.idUsuario == undefined) {
        registroUpdate.usuario.idUsuario = this.registro.usuario.idUsuario;
      }

      if (registroUpdate.ffin == null) {
        registroUpdate.ffin = this.registro.ffin;
      }

      registroUpdate.producto.idProducto = this.producto.idProducto;
      registroUpdate.idRegistro = this.registro.idRegistro;

      this.showError = false;
      this.messageError = '';
      this.actualizarRegistro(registroUpdate)
        .then(() => {
          this.cancelUpdateRegistro();
          this.registro.reset();
          this.obtenerRegistro();
          this.section = 1;
          this.showError = false;
          this.messageError = 'Fuera de rango';
        })
        .catch(err => {
          this.showError = true;
          this.messageError = '¡Ocurrió un error!';
        });
    } else {
      this.showError = true;
      this.messageError = messageError;
    }
  }

  actualizarRegistro(registro) {
    return new Promise((resolve, reject) => {
      this.registrosService.actualizarRegistro(registro)
        .subscribe(
          data => {
            DEBUG && console.log(data);
            resolve();
          },
          error => {
            DEBUG && console.log(error);
            reject(error);
          });
    });
  }

  recalculateDates(date) {
    let events = [];
    let statusError = false;
    let eventName = '';

    /*
    *   Obtenemos la cantidad de días disponibles que hay entre la fecha de inicio
    *   del registro y la nueva fecha fin del evento
    *
    * */
    let totalDays = this.diasHabilesService.calculateDays(this.registro.finicio, date);

    _.forEach(this.backupEvents, o => {
      if (!o.terminado && o.nombre !== 'Folio Registro') { /*TODO: Testing para eventos en especificos => && o.nombre === 'Tecnovigilancia' && o.nombre === 'Dossier'*/
        let eventData = JSON.parse(JSON.stringify(o));

        /*
        *   Guardamos el nombre del evento, para dado el caso que se requiera mostrar un mensaje de error
        *
        * */
        eventName = eventData.nombre;

        /*
        *   Convertimos el perido del evento a días
        *
        * */
        let currentPeriodQty = this.diasHabilesService.convertDays(eventData.tipoPeriodo, eventData.cantPeriodo);

        /*
        *   Verificamos si la cantidad de días se encuentra dentro del rago de la fecha
        *   de inicio y fin del registro,  en caso de que no se encuentre
        *   se detinene el proceso y se notifica al usuario
        *
        * */
        if (currentPeriodQty > totalDays) {
          /* Existe al menos un evento que se encuentra fuera de rango,
           * detenemos ciclo y notificamos al usuario
           *
           * */
          statusError = true;
          return false;
        }

        /*
        * Recalcular la fecha de realización del evento
        *
        * */
        eventData.frealizacion = this.diasHabilesService.getDate(date, eventData.tipoPeriodo, eventData.cantPeriodo);

        /* Recalcular la fecha de inicio de las notificaciones */
        if (_.isArray(eventData.notificaciones) && eventData.notificaciones.length > 0) {
          let notifications = [];
          _.forEach(eventData.notificaciones, n => {
            let notification = JSON.parse(JSON.stringify(n));

            /*
            *   Obtenemos la cantidad de días disponibles que hay entre la fecha de inicio
            *   del registro y la fecha de realización del evento, para posteriormente
            *   verificar si se require modificar el periodo y la cantidad
            *
            * */
            let totalDays = this.diasHabilesService.calculateDays(this.registro.finicio, eventData.frealizacion);

            /*
            *   Convertimos el perido de la notificación a días
            *
             */
            let currentPeriodQty = this.diasHabilesService.convertDays(notification.tipoPeriodo, notification.cantPeriodo);

            /*
            *   Verificamos si la cantidad de días se encuentra dentro del rago de la fecha
            *   de inicio del registro y la fecha de realización, en caso de que no se encuentre
            *   se detinene el proceso y se notifica al usuario
            *
            * */
            if (currentPeriodQty > totalDays) {
              /* Existe al menos un evento que se encuentra fuera de rango,
               * detenemos ciclo y notificamos al usuario
               *
               * */
              statusError = true;
              return false;
            }

            /*
            *   Calculamos la nueva fecha del inicio de la notificación apartir de la nueva
            *   fecha de realización del evento
            *
            * */
            notification.finicioAux = notification.finicio;
            notification.finicio = this.diasHabilesService.getDate(eventData.frealizacion, notification.tipoPeriodo, notification.cantPeriodo);

            notifications.push(notification);
          });
          eventData.notificaciones = [...notifications];
        }

        /*
        * Verificamos si hay al menos una notificación se encuentra fuera del rango de las fechas de inicio y fin
        *
        * */
        if (statusError) {
          return false;
        }

        events.push(eventData);
      }
    });

    DEBUG && console.log('@recalculateDates: ', events);

    return { status: statusError, data: _.slice(events), message: `Las notificaciones del evento "${eventName}", se encuentran fuera del rango de la nueva fecha límite de registro.` };
  }

  handleChange(data) {
    const { producto, tramiteCofepris, usuario, ffin } = this.registroUpdate;

    if ( (producto.nombre && producto.nombre .toString().toLowerCase().trim() !== this.producto.nombre .toString().toLowerCase().trim()) ||
      (producto.codigo && producto.codigo .toString().toLowerCase().trim() !== this.producto.codigo .toString().toLowerCase().trim()) ||
      (tramiteCofepris && tramiteCofepris .toString().toLowerCase().trim() !== this.registro.tramiteCofepris .toString().toLowerCase().trim()) ||
      (usuario.idUsuario && usuario.idUsuario !== this.registro.usuario.idUsuario) ||
      (ffin && ffin.getTime() !== this.registro.ffin.getTime()) ) {
      this.hasChange = true;
    } else {
      this.hasChange = false;
    }
  }

   /*
   * Para publicar el registro solo bastara tener al menos un evento activo
   *
   */
  validateCompleteData() {
    let isValidDossier = this.dossier.idEvento > 0 ? this.validateDossier() : true;
    let isValidTecnovigilancia = this.tecnovigilancia.idEvento > 0 ? this.validateTecnovigilancia() : true;
    let isValidSometimiento = this.sometimiento.idEvento ? this.validateSometimiento() : true;
    let isValidEvents = this.listEventos.length > 0 ? this.validateCustomEvents() : true;

    if (isValidDossier && isValidTecnovigilancia && isValidSometimiento && isValidEvents) {

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
    if (this.dossier.idEvento === 0) return true;
    if (this.dossier.terminado) return true;

    if (this.listNotificacionesDossier.length > 0 && this.listCorreosDossier.length > 0  && this.dossier.cantPeriodo > 0
      && this.registro.finicio.getTime() < this.dossier.frealizacion.getTime()) {
      return true
    }

    return false;
  }

  validateTecnovigilancia() {
    if (this.tecnovigilancia.idEvento === 0) return true;
    if (this.tecnovigilancia.terminado) return true;

    if (this.listNotificacionesTecno.length > 0 && this.listCorreosTecno.length > 0 && this.tecnovigilancia.cantPeriodo > 0
      && this.registro.finicio.getTime() < this.tecnovigilancia.frealizacion.getTime()) {
      return true;
    }

    return false;
  }

  validateSometimiento() {
    if (this.sometimiento.idEvento === 0) return true;
    if (this.sometimiento.terminado) return true;

    if (this.listNotificacionesSom.length > 0 && this.listCorreosSom.length > 0 && this.sometimiento.cantPeriodo > 0
      && this.registro.finicio.getTime() < this.sometimiento.frealizacion.getTime()) {
      return true;
    }

    return false
  }

  validateProrroga() {
    if (this.listNotificacionesProrro.length > 0 && this.listCorreosProrro.length > 0 && this.prorroga.cantPeriodo > 0 && this.prorroga.tipoPeriodo !== ''
      && this.registro.finicio.getTime() < this.prorroga.frealizacion.getTime()) {
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

  handleValidDates() {
    if (this.registro.finicio && this.registro.ffin) {
      let startTime = new Date(this.registro.finicio);
      startTime.setHours(0, 0, 0, 0);
      let endTime = new Date(this.registroUpdate.ffin);
      endTime.setHours(0, 0, 0, 0);
      if (startTime.getTime() < endTime.getTime()) {
        this.invalidDates = false;
      } else {
        this.invalidDates = true;
      }
    }
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

  openModal() {
    this.modalIsOpen = true;

    this.historialProductoLoading = true;
    const parameter = new Parametros();
    parameter.id = this.registro.idRegistro;
    this.registrosService.getHistorialProductoList(parameter)
      .subscribe(response => {
        if (response) {
          this.currentFilesInRecords = response.historialProducto.slice();
          this.historialProductoStatus = 2;
        }
        this.historialProductoLoading = false;
      },
      error => {
        DEBUG && console.log('@openModal: ', error);
        this.historialProductoLoading = false;
        this.historialProductoStatus = 1;
      });
  }

  closeModal() {
    this.modalIsOpen = false;
  }

  setActiveTab($event) {
    this.activeTab = $event;
    if (this.activeTab === 'Historial Registros' /*&& this.historialRegistrosStatus !== 2*/) {
      this.getRegistrosPorProducto();
    }
  }

  getHistorialProoductoPDF(filename) {
    SessionUser.getInstance().setLoading(true);
    this.modalIsOpen = false;
    this.registrosService.getHistorialProductoPDF(this.registro.idEmpresa, this.producto.idProducto, this.registro.idRegistro, filename)
      .subscribe(
        data => {
          if (data) {
            const pdfFile = 'data:application/pdf;base64,' + data;
            const element = document.createElement('a');
            element.setAttribute('href', pdfFile);
            element.setAttribute('download', filename);
            element.style.display = 'none';
            document.body.appendChild(element);
            element.click();
            document.body.removeChild(element);
          } else {
            SessionUser.getInstance().setMessageFailed('al descargar el archivo');
            document.getElementById('requestFailed').style.display = 'block';
          }
          SessionUser.getInstance().setLoading(false);
          this.modalIsOpen = true;
        },
        error => {
          DEBUG && console.log('Error al traer el pdf: ', error);
          SessionUser.getInstance().setLoading(false);
          this.modalIsOpen = true;
        });
  }

  getRegistrosPorProducto() {
    const parameter = new Parametros();
    parameter.idEmpresa = this.registro.idEmpresa;
    parameter.idproducto = this.producto.idProducto;
    this.historialRegistrosLoading = true;
    this.registrosService.getRegistrosPorProducto(parameter)
      .subscribe(response => {
        if (response) {
          if (response) {
            this.historialRegistros = _.filter(response, o => { return o.idRegistro !== this.registro.idRegistro; });
          }
        }
        this.historialRegistrosLoading = false;
      }, error => {
        DEBUG && console.log('@getRegistrosPorProducto: entre ', error);
        this.historialRegistrosLoading = false;
      });
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

  saveFileHistory() {
    if (this.listFilesToUpload.length === 0) return;

    SessionUser.getInstance().setLoading(true);
    this.closeModal();

    const result = [];
    _.forEach(this.listFilesToUpload, o => {
      const data = new HistorialProducto();
      data.idRegistro = this.registro.idRegistro;
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
              this.listFilesToUpload[index].file,
              SessionUser.getInstance().getidEmpresaActual(),
              this.producto.idProducto,
              item.idRegistro,
              item.idHistorialProducto,
              fileName
            );
            countError = result ? countError : countError + 1;
            index++;
          }
          this.listFilesToUpload.length = 0;
          if (countError === 0) {
            // Notificar que las inserción fue corrrecta y redireccionar
            SessionUser.getInstance().setLoading(false);
            SessionUser.getInstance().setMessageOk('Has guardado');
            document.getElementById('idOk').style.display = 'block';
          } else {
            SessionUser.getInstance().setLoading(false);
            SessionUser.getInstance().setMessageFailed('al cargar los archivos');
            document.getElementById('requestFailed').style.display = 'block';
          }
        }
      }, error => {
        DEBUG && console.log('Error al guardar el registro: ' + error);
        SessionUser.getInstance().setLoading(false);
        SessionUser.getInstance().setMessageFailed('al cargar los archivos el registro');
        document.getElementById('requestFailed').style.display = 'block';
        this.listFilesToUpload.length = 0;
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

  deleteFileToList(index) {
    this.listFilesToUpload.splice(index, 1);
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

  deleteFile(file, index) {
    const parameter = new Parametros();
    parameter.idHistorial = file.idHistorialProducto;
    parameter.path = `${this.registro.idEmpresa}/${this.producto.idProducto}/${this.registro.idRegistro}/${file.nombre}`;
    this.registrosService.deleteHistorialProducto(parameter)
      .subscribe(
        data => {
          if (data) {
            this.currentFilesInRecords.splice(index, 1);
          }
        },
        error => {
          console.log('@deleteFile: error ', error);
        }
      );
  }

  openLightBox(typeSwitch, object, index = -1) {
    const selectedSwitch = {
      status: true,
      typeSwitch,
      object,
      index,
    };
    this.selectedSwitch = selectedSwitch;
    this.classAnimation = 'w3-animate-zoom';
    this.lightBoxDOM.nativeElement.style.display = 'block';
  }

  closeLightBox() {
    this.classAnimation = 'w3-animate-zoom-out';
    this.lightBoxDOM.nativeElement.style.display = 'none';
  }

  handelAccept() {
    this.closeLightBox();
    switch (this.selectedSwitch.typeSwitch) {
      case 1:
        this.uStatus(
          this.selectedSwitch.status,
          this.selectedSwitch.index,
          this.selectedSwitch.object
        );
        break;
      case 2:
        this.uStatusFol(
          this.selectedSwitch.status,
          this.selectedSwitch.object
        );
        break;
      case 3:
        this.uStatusPro(
          this.selectedSwitch.status,
          this.selectedSwitch.object
        );
        break;
    }
  }

}
