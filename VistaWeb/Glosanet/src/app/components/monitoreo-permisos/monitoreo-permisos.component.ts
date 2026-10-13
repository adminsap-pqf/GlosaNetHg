import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Eventos} from '../../model/eventos.class';
import {Parametros} from '../../model/parametros.class';
import {HistorialEvento} from '../../model/historialEvento.class';
import {Notificacion} from '../../model/notificacion.class';
import {CorreoEvento} from '../../model/correoEvento.class';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {SessionUser} from '../../services/session/session.service';
import {UserService} from '../../services/user/user.service';
import {PrevisionesService} from '../../services/previsiones/previsiones.service';
import {Usuario} from '../../model/usuario.class';
import {dateFormatSlashFull} from '../../pipes/accounting/accounting.pipe';

import * as moment from 'moment';
import * as _ from 'lodash';
import {PermisosService} from '../../services/permisos/permisos.service';
import {Permiso} from '../../model/permiso.class';
import {DiasHabilesService} from '../../services/general/diasHabiles';
import {Producto} from '../../model/producto.class';

/* Dev Tools */
import { DEBUG } from '../../settings/app.settings';

@Component({
  selector: 'gn-monitoreo-permisos',
  templateUrl: './monitoreo-permisos.component.html',
  styleUrls: ['./monitoreo-permisos.component.scss']
})
export class MonitoreoPermisosComponent implements OnInit {

  @ViewChild('fileInput', {static: false}) fileInput: any;

  @ViewChild('containerBar', {static: false}) container: ElementRef;

  widthContainer: number;

  positionToday: any = 0;

  sub: any;
  idPermiso: number = 0;

  today = new Date();

  users: any = [];
  permiso: Permiso = new Permiso();
  producto: Producto = new Producto();

  evento: Eventos = new Eventos();
  notificaciones: Notificacion = new Notificacion();
  correos: CorreoEvento = new CorreoEvento();
  switchArray: any = [];
  open = [];

  gEventosForm1: FormGroup;
  gEventosForm2: FormGroup;

  classAnimation2 = 'w3-animate-zoom-out';
  idEventoActualCarga: number = 0;

  indexActualCarga: number = 0;

  fileName_light: string = '';
  file_light: FileList = null;

  arrayHistorialNuevos: any = [];
  arrayFileHistorialNuevos: any = [];

  textArea_light: string = '';
  section: number = 1;

  selectUser: any;

  indexLine: any = 0;
  showTooltip: any = [];
  contentTooltip: any = [];

  arrayValidEmail = [];
  arrayValidDias2 = [];
  arrayValidDias1 = [];

  fullData: boolean = true;

  rTerminado: number = 0;

  switchFol: boolean = false;

  /* file */

  arrayFile: Array<FileList>;

  textArea: Array<string>;

  fileName: Array<string>;

  items: any [] = [1];


  widthBar: number = 0;

  eventoActual: string = '';

  diasPorevento: any = [];

  responsableGestion: String = '';

  /* Lista de eventos nuevos */
  listEventos: Array<Eventos> = new Array<Eventos>();

  /* variables donde se encadenan los correos y notificaciones a eliminar */
  notifEliminar: string = '';
  correoEliminar: string = '';

  fRealizacionArray: any[] = [];
  etiquetasBar: any = [];
  etiquetasBar2: any = [];

  public dropListPeriodoTiempo: any[] = [{nombre: 'Días'}, {nombre: 'Semanas'}, {nombre: 'Meses'}, {nombre: 'Años'}];

  public dropListNotificacion: any[] = [{nombre: 'Cada Día'}, {nombre: 'Cada Semana'}, {nombre: 'Cada Mes'}, {nombre: 'Cada Año'}];

  dateFolio1: Date;
  dateFolio2: Date;
  openFolio: boolean = false;

  /* reset droplistNuevo */
  resetNuevo: Array<any> = new Array<any>(3);

  resetDefault: Array<any> = new Array<any>();

  itemEventEffect: any = new Array(2).fill('body-itemE-close');

  generarEventEffect: string = 'body-generarE-close';

  /* Efectos de los nuevos eventos creados */
  itemNewEventEffect: any;  /* = new Array(3).fill('body-itemE-close'); */

  diasInput: Array<number> = new Array<number>();

  dropListTipoPeriodo: Array<any> = new Array<any>();
  dropListRecurrencia: Array<any> = new Array<any>();
  correoInput: Array<string> = new Array<string>();

  /* **********************************************+ Elaboracion **********************************************+ */

  elaboracion: Eventos = new Eventos();

  dia1Elaboracion: number = 0;
  correoElaboracion: string = '';
  listNotificacionesElaboracion: Array<Notificacion> = new Array<Notificacion>();
  listCorreosElaboracion: Array<CorreoEvento> = new Array<CorreoEvento>();
  resetElaboracion: Array<any> = new Array<any>(3);

  resetelaboracion: Array<any> = new Array<any>(3);

  /* **********************************************+ Sometimiento **********************************************+ */

  sometimiento: Eventos = new Eventos();

  dia1Sometimiento: number = 0;
  correoSometimiento: string = '';
  listNotificacionesSometimiento: Array<Notificacion> = new Array<Notificacion>();
  listCorreosSometimiento: Array<CorreoEvento> = new Array<CorreoEvento>();
  resetSometimiento: Array<any> = new Array<any>(3);

  resetsometimiento: Array<any> = new Array<any>(3);

  listNotificacionesNuevo: Array<Notificacion> = new Array<Notificacion>();
  listCorreosNuevo: Array<CorreoEvento> = new Array<CorreoEvento>();

  folio: Eventos = new Eventos();

  date = new Date();
  yearActual: number = 0;
  nextYear: number = 0;

  fInicio: Date;
  fFin: Date;

  sinCita: number = 0;
  conCita: number = 0;

  eventosTerminados: number = 0;

  tiempoResp: number = 0;

  testRangeDate: Date;
  testRangeDateInit: Date;

  /*   **************************************************************** barra **************************************************************** */

  lines: any = new Array(this.items.length + 2).fill(1);


  /* Validaciones */
  validEmailNuevo:boolean = false;
  validarDias2Nuevo:boolean = false;
  validEmailElaboracion:boolean = false;
  validarDias2Elaboracion:boolean = false;
  validEmailSometimiento:boolean = false;
  validarDias2Sometimiento:boolean = false;

  newEventInvalidDates: boolean = false;
  invalidDateCofeprisCurrent: boolean = false;
  eventInvalidDates: any = [];

  listNotificationError: any = [];
  validDate: any[] = new Array<boolean>(5).fill(false);

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private userService: UserService,
    private permisoService: PermisosService,
    private previsionService: PrevisionesService,
    private diasHabilesService: DiasHabilesService
  ) { }

  ngOnInit() {

    this.yearActual = this.date.getFullYear();
    this.nextYear = this.yearActual + 1;
    this.fInicio = new Date(this.yearActual, 0, 1);
    this.fFin = new Date(this.yearActual, 4, 30);
    this.sub = this.route.params.subscribe(params => {
      this.idPermiso = params['id'];

    });
    //this.idPrevision = 57;
    this.getPermiso();
    this.gEventosForm1 = new FormGroup({
      nombreEvent: new FormControl('', [Validators.required]),
      dias1: new FormControl('', [Validators.required]),
      dias2: new FormControl('', [Validators.required]),
      dias3: new FormControl('', [Validators.required])
    });

    this.gEventosForm2 = new FormGroup({
      correoN: new FormControl('', [Validators.required]),
    });

    //this.prevision.eventos = [new Eventos];
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

  ngAfterViewInit() {
    this.widthContainer = this.container.nativeElement.offsetWidth;
    DEBUG && console.log(this.container.nativeElement.offsetWidth);
  }


  irPermisos() {
    this.router.navigate(["protected/libreriaPermisos"]);
  }

  getFechaImpl($event) {
    DEBUG && console.log($event);
    DEBUG && console.log(this.switchArray[this.permiso.eventos.length - 1]);
    DEBUG && console.log(this.switchFol);
    DEBUG && console.log(this.permiso.finicio);
    DEBUG && console.log(this.permiso.ffin);
    DEBUG && console.log(this.folio.terminado);

    if (this.permiso.finicio !== null) {
      let parametro = new Parametros();
      parametro.id = this.permiso.idpermiso;
      parametro.tipo = '3';
      parametro.finicio = this.permiso.finicio;
      this.guardarElementoMonitoreoRegistro(parametro);
    }
    if (this.permiso.ffin !== null) {
      let parametro = new Parametros();
      parametro.id = this.permiso.idpermiso;
      parametro.tipo = '4';
      parametro.finicio = this.permiso.ffin;
      this.guardarElementoMonitoreoRegistro(parametro);
    }
  }


  cargarReferencia(id, index) {
    this.classAnimation2 = 'w3-animate-zoom';
    document.getElementById('id01').style.display = 'block';
    this.idEventoActualCarga = id;
    this.indexActualCarga = index;
    DEBUG && console.log(id);
  }

  salir_light() {
    this.file_light = null;
    this.arrayFileHistorialNuevos = [];
    this.arrayHistorialNuevos = [];
    this.fileName_light = '';
    this.textArea_light = '';

    //this.fileInput.nativeElement.value = '';
  }

  loadFile(file, idEmpresa, idPrevision, idEvento, idHistorial) {
    return new Promise(resolve => {
      this.permisoService.loadFile(file, idEmpresa, idPrevision, idEvento, idHistorial)
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

  guardarHistorial() {
    SessionUser.getInstance().setLoading(true);
    this.classAnimation2 = 'w3-animate-zoom-out';
    document.getElementById('id01').style.display = 'none';
    let parametros = new Parametros();
    parametros.permisoHistorial = this.arrayHistorialNuevos;
    DEBUG && console.log(parametros);
    this.permisoService.guardarHistorialEvento(parametros)
      .subscribe(
        async data => {
          DEBUG && console.log(data);
          let i = 0;
          let countError = 0;
          for (let item of data.permisoHistorial) {
            const result = await this.loadFile(this.arrayFileHistorialNuevos[i], this.permiso.idpermiso, this.permiso.idEmpresa, item.idEvento, item.idHistorial);
            countError = result ? countError : countError + 1;
            i++;
          }
          if (this.indexActualCarga > -1) {
            this.obtenerHistorial(this.idEventoActualCarga, this.indexActualCarga);
          }
          if (this.indexActualCarga == -1) {
            this.obtenerHistorialFol(this.idEventoActualCarga);
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
        error => {
          DEBUG && console.log('@guardarHistorial: Error al guardar historial ' + error);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al cargar la referencia');
          document.getElementById('requestFailed').style.display = 'block';
        });
  }

  fecha($event) {
    DEBUG && console.log($event);
  }

  fechaCofepris($event, index) {
    if (index == 1 && $event !== new Date(this.yearActual, 4, 30) && $event !== this.permiso.citaCofepris) {

      this.permiso.fechaCita = new Date();

    }
  }

  async fechaCofeprisMonitoreo($event, index) {
    let evento = new Date($event.slice(0, 4), Number($event.slice(4, 6)) - 1, $event.slice(6, 8));

    if (await this.handleValidDates(evento, 5)) {
      return;
    }

    if (index == 2 && moment(this.permiso.citaCofepris, "DD-MM-YYYY").diff(moment(new Date(this.yearActual, 4, 30), "DD-MM-YYYY"), 'days') != 0 && moment(this.permiso.citaCofeprisAux, "DD-MM-YYYY").diff(moment(evento, "DD-MM-YYYY"), 'days') != 0) {
      SessionUser.getInstance().setLoading(true);
      if (this.tiempoResp == 0) {
        this.tiempoResp = 90;
      }
      this.getTiempoRespuesta();

      DEBUG && console.log("entra actualizar fecha");

      let parametro = new Parametros();
      parametro.id = this.permiso.idpermiso;
      parametro.finicio = new Date();
      parametro.ffin = this.permiso.citaCofepris;
      const statusParameter1 = await this.actualizarFecha(parametro);

      let parametro2 = new Parametros();
      parametro2.id = this.elaboracion.idEvento;
      parametro2.ffin = this.permiso.citaCofepris;
      const statusParameter2 = await this.actualizarFechaevento(parametro2);

      let parametro3 = new Parametros();
      parametro3.id = this.sometimiento.idEvento;
      parametro3.ffin = this.sometimiento.frealizacion;
      const statusParameter3 = await this.actualizarFechaevento(parametro3);

      if (statusParameter1 && statusParameter2 && statusParameter3) {
        this.getPermiso();
      } else {
        SessionUser.getInstance().setLoading(false);
        SessionUser.getInstance().setMessageFailed('al actualizar la fecha');
        document.getElementById('requestFailed').style.display = 'block';
      }
    }
  }

  actualizarFecha(parametro: Parametros) {
    return new Promise(resolve => {
      this.permisoService.actualizarFechaCita(parametro)
        .subscribe(
          data => {
            DEBUG && console.log(data);
            resolve(true);
          },
          error => {
            resolve(false);
            DEBUG && console.log(error);
          });
    });
  }

  actualizarFechaevento(parametro: Parametros) {
    return new Promise(resolve => {
      this.permisoService.actualizarCitaCofeprisEvento(parametro)
        .subscribe(
          data => {
            DEBUG && console.log(data);
            resolve(true);
          },
          error => {
            DEBUG && console.log(error);
            resolve(false);
          });
    });
  }

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

  dropListElaboracion($event, index) {
    switch (index) {
      case 1:
        this.elaboracion.tipoPeriodo = $event.nombre;

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

  dropListSometimiento($event, index) {
    switch (index) {
      case 1:
        this.sometimiento.tipoPeriodo = $event.nombre;

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

  async uStatus($event, index, item) {
    DEBUG && console.log($event);
    SessionUser.getInstance().setLoading(true);

    this.permiso.eventos[index].terminado = true;

    const result = await this.realizarEvento(item);

    SessionUser.getInstance().setLoading(false);
    if (result) {
      this.switchArray[index] = $event;
      this.permiso.eventos[index].aTiempo = this.today > new Date(this.permiso.eventos[index].frealizacion) ? true : false;

      if ($event) {
        this.widthBar = this.widthBar + (100 / (this.lines.length - 1));
      } else {
        this.widthBar = this.widthBar - (100 / (this.lines.length - 1));
      }
      this.diasPorEventos();
      this.ngOnInit();
    } else {
      SessionUser.getInstance().setMessageFailed('al conciliar etapa');
      document.getElementById('requestFailed').style.display = 'block';
      this.permiso.eventos[index].terminado = false;
    }
  }

  async uStatusFol($event, item) {
    DEBUG && console.log($event);
    SessionUser.getInstance().setLoading(true);

    this.folio.terminado = true;

    const result = await this.realizarEvento(item);

    SessionUser.getInstance().setLoading(false);
    if (result) {
      this.switchFol = $event;
      this.ngOnInit();
    } else {
      SessionUser.getInstance().setMessageFailed('al conciliar etapa');
      document.getElementById('requestFailed').style.display = 'block';
      this.folio.terminado = false;
    }
  }

  confirm(open: boolean, index: number) {
    this.section = 1;
    this.clearValidators();
    this.ngOnInit();
  }

  fileChange2($event) {
    DEBUG && console.log($event);
    if ($event.target.files.length > 0) {
      this.file_light = $event.target.files;
      this.fileName_light = $event.target.files[0].name;
    }
  }

  historial(i, item) {
    DEBUG && console.log(item);
    DEBUG && console.log(i);
    this.obtenerHistorial(item.idEvento, i);
  }

  obtenerHistorialFol(id) {
    let parametros = new Parametros();
    parametros.id = id;
    this.permisoService.obtenerHistorialEvento(parametros)
      .subscribe(
        data => {
          DEBUG && console.log(data);
          this.folio.historial = data;
        },
        error => {
          DEBUG && console.log('@obtenerHistorialFol: Error al traer el historial: ' + error);
          SessionUser.getInstance().setMessageFailed('al obtener el historial');
          document.getElementById('requestFailed').style.display = 'block';
          return [];
        });
  }

  historialFol(id) {
    this.obtenerHistorialFol(id);
  }

  eliminarHistorialEvento_light(index) {
    this.arrayHistorialNuevos.splice(index, 1);
    this.arrayFileHistorialNuevos.splice(index, 1);
  }

  obtenerHistorial(id, i) {
    let parametros = new Parametros();
    parametros.id = id;
    this.permisoService.obtenerHistorialEvento(parametros)
      .subscribe(
        data => {
          DEBUG && console.log(data);
          this.permiso.eventos[i].historial = data;
        },
        error => {
          DEBUG && console.log('@obtenerHistorial: Error al traer el historial: ' + error);
          SessionUser.getInstance().setMessageFailed('al obtener el historial');
          document.getElementById('requestFailed').style.display = 'block';
          return [];
        });
  }


  eliminarHistorialEvento(index, item) {
    SessionUser.getInstance().setLoading(true);
    let parametro = new Parametros();
    parametro.idHistorial = item.idHistorial;
    this.permisoService.eliminarHistorialEvento(parametro)
      .subscribe(
        data => {
          DEBUG && console.log(data);
          SessionUser.getInstance().setLoading(false);
          this.obtenerHistorial(item.idEvento, index);
        },
        error => {
          DEBUG && console.log('@eliminarHistorialEvento: Error al eliminar historial: ' + error);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al eliminar el documento');
          document.getElementById('requestFailed').style.display = 'block';
        });
  }

  eliminarHistorialEventoFol(item) {
    SessionUser.getInstance().setLoading(true);
    let parametro = new Parametros();
    parametro.idHistorial = item.idHistorial;
    this.permisoService.eliminarHistorialEvento(parametro)
      .subscribe(
        data => {
          DEBUG && console.log(data);
          SessionUser.getInstance().setLoading(false);
          this.obtenerHistorialFol(this.folio.idEvento);
        },
        error => {
          DEBUG && console.log('@eliminarHistorialEventoFol: Error al eliminar historial: ' + error);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al eliminar el documento');
          document.getElementById('requestFailed').style.display = 'block';
        });
  }


  agregarHistorial_light() {
    DEBUG && console.log(this.file_light);

    this.arrayFileHistorialNuevos.push(this.file_light);
    let historial = new HistorialEvento();
    historial.comentarios = this.textArea_light;
    historial.idEvento = this.idEventoActualCarga;
    historial.idUsuario = this.permiso.usuario.idUsuario;

    this.arrayHistorialNuevos.push(historial);

    DEBUG && console.log(this.arrayFileHistorialNuevos);

    this.file_light = null;
    this.fileName_light = '';
    this.textArea_light = '';
    //this.fileInput.nativeElement.value = '';

    DEBUG && console.log(this.arrayFileHistorialNuevos);
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
        //this.modalForm.patchValue({

        //});
        break;
      default:
        break;
    }
  }

  resetDroplistNuevo(seccion: string) {
    if (seccion == 'Nombre') {
      this.resetNuevo[0] = {nombre: 'Días'}
    } else {
      this.resetNuevo[1] = {nombre: ''};
      this.resetNuevo[2] = {nombre: ''};
    }
  }

  resetDroplistElaboracion(seccion: string) {
    if (seccion == 'Nombre') {
      this.resetElaboracion[0] = {nombre: 'Días'}
    } else {
      this.resetElaboracion[1] = {nombre: ''};
      this.resetElaboracion[2] = {nombre: ''};
    }
  }

  resetDroplistSometimiento(seccion: string) {
    if (seccion == 'Nombre') {
      this.resetSometimiento[0] = {nombre: 'Días'}
    } else {
      this.resetSometimiento[1] = {nombre: ''};
      this.resetSometimiento[2] = {nombre: ''};
    }
  }


  /* ******************************************************  Botones agregar default  ****************************************************** */

  /* ****************************** Nuevo evento llenar y borrar ******************************  */
  agregarNuevoNotificacion() {
    if (this.gEventosForm1.get('dias2').value !== 0 && this.notificaciones.tipoPeriodo !== '' && this.notificaciones.recurrencia !== '') {
      this.notificaciones.cantPeriodo = this.gEventosForm1.get('dias2').value;
      this.notificaciones.startDate = this.calculateFInicioNuevo(this.evento.frealizacion ? this.evento.frealizacion : new Date(this.permiso.ffin), this.notificaciones.tipoPeriodo, this.notificaciones.cantPeriodo);
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
    if (this.evento.nombre !== '' && this.evento.frealizacion !== null && this.listNotificacionesNuevo.length > 0 && this.listCorreosNuevo.length > 0) {
      let auxCorreo = Object.assign([], this.listCorreosNuevo);
      let auxNotificaciones = Object.assign([], this.listNotificacionesNuevo);
      this.evento.correos = auxCorreo;
      this.evento.notificaciones = auxNotificaciones;

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
    }
    DEBUG && console.log("******************+ lista eventos ***************");
    DEBUG && console.log(this.listEventos);
  }


  agregarCamposNuevoevento() {
    this.dropListTipoPeriodo.push({nombre: ''});
    this.dropListRecurrencia.push({nombre: ''});
    this.diasInput.push(0);
    this.correoInput.push('');
    this.resetDefault.push({nombre: ''});

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

  borrarElaboracionNotificacion(data: number, item) {
    DEBUG && console.log(item);
    this.addEliminarNotificacion(item.idNotificacion);
    this.listNotificacionesElaboracion.splice(data, 1);
    DEBUG && console.log("******************+ lista Dossier notificaciones ***************");
    DEBUG && console.log(this.listNotificacionesElaboracion);
  }

  agregarElaboracionCorreo() {
    if (this.correoElaboracion !== '') {
      let flag = false;
      for (let item of this.listCorreosElaboracion) {
        if (item.correo === this.correoElaboracion) {
          flag = true;
          break;
        }
      }
      if (!flag) {
        this.correos.correo = this.correoElaboracion;
        let aux = Object.assign({}, this.correos);
        this.listCorreosElaboracion.push(aux);

        //this.listCorreosTotal(this.correoDos);
      }
      this.correos.reset();
      this.correoElaboracion = '';
      this.validEmailElaboracion = false;
    }
    DEBUG && console.log(this.listCorreosElaboracion);
  }

  borrarElaboracionCorreo(data: number, item) {
    this.addEliminarCorreo(item.idCorreo);
    this.listCorreosElaboracion.splice(data, 1);
    DEBUG && console.log("******************+ lista Dossier correos ***************");
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

  borrarSometimientoNotificacion(data: number, item) {
    DEBUG && console.log(item);
    this.addEliminarNotificacion(item.idNotificacion);
    this.listNotificacionesSometimiento.splice(data, 1);
    DEBUG && console.log("******************+ lista Dossier notificaciones ***************");
    DEBUG && console.log(this.listNotificacionesSometimiento);
  }

  agregarSometimientoCorreo() {
    if (this.correoSometimiento !== '') {
      let flag = false;
      for (let item of this.listCorreosSometimiento) {
        if (item.correo === this.correoSometimiento) {
          flag = true;
          break;
        }
      }
      if (!flag) {
        this.correos.correo = this.correoSometimiento;
        let aux = Object.assign({}, this.correos);
        this.listCorreosSometimiento.push(aux);

        //this.listCorreosTotal(this.correoDos);
      }
      this.correos.reset();
      this.correoSometimiento = '';
      this.validEmailSometimiento = false;
    }
    DEBUG && console.log(this.listCorreosSometimiento);
  }

  borrarSometimientoCorreo(data: number, item) {
    this.addEliminarCorreo(item.idCorreo);
    this.listCorreosSometimiento.splice(data, 1);
    DEBUG && console.log("******************+ lista Dossier correos ***************");
    //this.borrarCorreosTotal();
    DEBUG && console.log(this.listCorreosSometimiento);
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

      this.resetDefault[index] = {nombre: ''};
      this.eventInvalidDates[index] = false;

      DEBUG && console.log(this.listEventos);
    }
  }

  borrarNotificacion(ev: any, item: any, data) {
    this.addEliminarNotificacion(data.idNotificacion);
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

        //this.listCorreosTotal(this.correoInput[index]);
      }
      this.correos.reset();
      this.correoInput[index] = '';
      this.arrayValidEmail[index] = false;


      DEBUG && console.log(this.listEventos);
    }
  }

  borrarCorreo(ev: any, item: any, data) {
    this.addEliminarCorreo(data.idCorreo);
    this.listEventos[ev].correos.splice(item, 1);
    DEBUG && console.log("******************+ lista Correo ***************");
    //this.borrarCorreosTotal();
    DEBUG && console.log(this.listEventos);
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
          for (let it of this.users) {
            if (it.user.idUsuario == this.permiso.usuario.idUsuario) {
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

  addEliminarNotificacion(item) {
    this.notifEliminar += item + ',';
    DEBUG && console.log(this.notifEliminar);
  }

  addEliminarCorreo(item) {
    this.correoEliminar += item + ',';
    DEBUG && console.log(this.correoEliminar);
  }


  setEventosDefault() {
    this.listEventos = [];
    for (let item of this.permiso.eventos) {
      if (item.nombre == 'Elaboración' && item.definido) {
        item.frealizacion = new Date(item.frealizacion);
        this.elaboracion = Object.assign(new Eventos, item);
        //this.resetElaboracion[0] = {nombre:this.prevision.tipoPeriodo};
      }

      if (item.nombre == 'Sometimiento' && item.definido) {
        item.frealizacion = new Date(item.frealizacion);
        this.sometimiento = Object.assign(new Eventos, item);
        //this.resetElaboracion[0] = {nombre:this.prevision.tipoPeriodo};
      }

      if (item.definido == false) {
        item.frealizacion = new Date(item.frealizacion);
        let result = [];
        _.forEach(item.notificaciones, o => {
          const startDate = this.calculateFInicioNuevo(item.frealizacion, o.tipoPeriodo, o.cantPeriodo);
          result.push({
            ...o,
            finicio: new Date(o.finicio),
            startDate: startDate,
            startDateText: `Inicia el ${this.getCustomDate(startDate)}`
          });
        });
        item.notificaciones = result.slice();

        this.listEventos.push(Object.assign(new Eventos, item));
      }
    }

    for (let item of this.elaboracion.correos) {
      this.listCorreosElaboracion.push(item);
    }
    for (let item of this.elaboracion.notificaciones) {
      const startDate = this.calculateFInicioNuevo(this.elaboracion.frealizacion, item.tipoPeriodo, item.cantPeriodo);
      item.finicio = new Date(item.finicio);
      item.startDate = startDate;
      item.startDateText = `Inicia el ${this.getCustomDate(startDate)}`;
      this.listNotificacionesElaboracion.push(item);
    }

    for (let item of this.sometimiento.correos) {
      this.listCorreosSometimiento.push(item);
    }
    for (let item of this.sometimiento.notificaciones) {
      const startDate = this.calculateFInicioNuevo(this.sometimiento.frealizacion, item.tipoPeriodo, item.cantPeriodo);
      item.finicio = new Date(item.finicio);
      item.startDate = startDate;
      item.startDateText = `Inicia el ${this.getCustomDate(startDate)}`;
      this.listNotificacionesSometimiento.push(item);
    }

    this.testRangeDate = this.sometimiento.frealizacion;


    DEBUG && console.log(this.elaboracion);
    DEBUG && console.log(this.sometimiento);
    DEBUG && console.log(this.listEventos);
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

  saveTramiteCofepris() {
    let parametro = new Parametros();
    parametro.id = this.permiso.idpermiso;
    parametro.tipo = '1';
    parametro.condicion = this.permiso.tramiteCofepris;
    this.guardarElementoMonitoreoRegistro(parametro);

  }

  saveFolioRegistro() {
    let parametro = new Parametros();
    parametro.id = this.permiso.idpermiso;
    parametro.tipo = '2';
    parametro.condicion = this.permiso.folioRegistro;
    this.guardarElementoMonitoreoRegistro(parametro);
  }

  guardarElementoMonitoreoRegistro(parametro: Parametros) {
    this.permisoService.guardarElementoMonitoreoPermiso(parametro)
      .subscribe(
        data => {
          DEBUG && console.log(data);
        },
        error => {
          DEBUG && console.log('Error al guardar elemento monitoreo: ' + error);
          SessionUser.getInstance().setMessageFailed('al guardar la fecha');
          document.getElementById('requestFailed').style.display = 'block';
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
    this.sometimiento.frealizacion = this.diasHabilesService.diasHabilesProx(this.tiempoResp, fRealizacion, this.permiso.citaCofepris);
    DEBUG && console.log(this.sometimiento.frealizacion);
    this.permiso.fRealizacionFolio = new Date(this.sometimiento.frealizacion);
    //this.sometimiento.frealizacionAux = new Date(this.sometimiento.frealizacion);
    this.fFin = new Date(this.sometimiento.frealizacion);
  }


  diasPorEventos() {
    let index = 0;
    for (let item of this.permiso.eventos) {
      DEBUG && console.log(item.frealizacionAux);
      if (item.terminado) {
        DEBUG && console.log('Terminado');
        if (new Date(item.fterminado) > new Date(item.frealizacionAux)) {
          DEBUG && console.log('fuera de tiempo');
          let d1 = moment(item.fterminado);
          let d2 = moment(item.frealizacionAux);
          let days = d1.diff(d2, 'days');

          this.diasPorevento[index] = days == 1 ? days + ' Día de Diferencia · Fuera de Tiempo' : days + ' Días de Diferencia · Fuera de Tiempo';
        }
        if (new Date(item.fterminado) <= new Date(item.frealizacionAux)) {
          DEBUG && console.log('a tiempo');
          let d1 = moment(item.fterminado);
          let d2 = moment(item.frealizacionAux);
          let days = d2.diff(d1, 'days');

          this.diasPorevento[index] = days == 1 ? days + ' Día de Diferencia · A Tiempo' : days + ' Días de Diferencia · A Tiempo';
        }
      } else {
        DEBUG && console.log('no Terminado');
        if (this.today > new Date(item.frealizacionAux)) {
          DEBUG && console.log('fuera de tiempo');
          let d1 = moment(this.today);
          let d2 = moment(item.frealizacionAux);
          let days = d1.diff(d2, 'days');
          //days += 1;
          this.diasPorevento[index] = days == 1 ? days + ' Día Atrasado · Fuera de Tiempo' : days + ' Días Atrasado · Fuera de Tiempo';
        }
        if (this.today <= new Date(item.frealizacionAux)) {
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


  getPermiso() {
    this.openFolio = false;
    SessionUser.getInstance().setLoading(true);
    let parametro = new Parametros();
    parametro.id = this.idPermiso;
    this.permisoService.obtenerPermiso(parametro)
      .subscribe(
        data => {
          DEBUG && console.log(data);

          this.listNotificacionesElaboracion = new Array<Notificacion>();
          this.listCorreosElaboracion = new Array<CorreoEvento>();
          this.listNotificacionesSometimiento = new Array<Notificacion>();
          this.listCorreosSometimiento = new Array<CorreoEvento>();

          this.notifEliminar = '';
          this.correoEliminar = '';

          //this.widthBar = 0;

          this.fRealizacionArray = [];

          this.permiso = null;
          this.producto = Object.assign({}, data.producto);
          this.permiso = Object.assign(new Permiso, data);

          DEBUG && console.log(this.permiso);
          let u = Object.assign(new Usuario, data.usuario);

          DEBUG && console.log(u);
          this.permiso.usuario = new Usuario();
          this.permiso.usuario = u;

          this.permiso.fechaCita !== null ? this.permiso.citaCofepris = new Date(this.permiso.citaCofepris) : this.permiso.citaCofepris = null;
          this.permiso.citaCofeprisAux = new Date(data.citaCofepris);

          this.permiso.finicio !== null ? this.permiso.finicio = new Date(this.permiso.finicio) : this.permiso.finicio = new Date();
          this.permiso.ffin !== null ? this.permiso.ffin = new Date(this.permiso.ffin) : null;

          this.permiso.fecha = new Date(this.permiso.fecha);

          this.permiso.fechaCita !== null ? this.permiso.fechaCita = new Date(this.permiso.fechaCita) : null;

          this.testRangeDateInit = this.permiso.fecha;


          for (let item of this.permiso.eventos) {
            item.historial = [];
            DEBUG && console.log(item.totalHistorial);
            if (item.terminado) {
              this.fRealizacionArray.push(new Date(item.frealizacion));

            }
            item.frealizacion = new Date(item.frealizacion);
          }

          this.fRealizacionArray = this.fRealizacionArray.sort((a, b) => (a > b) ? -1 : 1);
          DEBUG && console.log(this.fRealizacionArray);

          DEBUG && console.log(this.permiso);

          let index2: number = -1;
          let flag2 = false;
          for (let item of this.permiso.eventos) {
            if (item.nombre == 'Folio de Permiso' && item.definido) {
              this.folio = Object.assign(new Eventos, item);
              this.folio.historial = [];
              index2++;
              flag2 = true;
              break;
            }
            index2++;
          }
          if (index2 !== -1 && flag2) {
            this.permiso.eventos.splice(index2, 1);
          }
          DEBUG && console.log(this.folio);

          this.switchArray = new Array<boolean>(this.permiso.eventos.length).fill(false);

          if (this.permiso.eventos[this.permiso.eventos.length - 1].terminado == true) {
            this.switchArray[this.permiso.eventos.length - 1] = true;
          }

          DEBUG && console.log(this.switchArray);
          DEBUG && console.log(this.switchArray[this.permiso.eventos.length - 1]);
          DEBUG && console.log(this.switchFol);
          DEBUG && console.log(this.permiso.finicio);
          DEBUG && console.log(this.permiso.ffin);
          DEBUG && console.log(this.folio.terminado);

          this.open = new Array<boolean>().fill(false);

          this.etiquetasBar = new Array<string>();
          this.etiquetasBar2 = new Array<string>();
          let pipe = new dateFormatSlashFull();

          for (let item of this.permiso.eventos) {
            item.frealizacionAux = new Date(item.frealizacion);
            if (item.terminado) {
              item.frealizacion = item.fterminado;
            }
          }

          //this.etiquetasBar.push(pipe.transform(this.registro.finicio));
          this.arrayFile = new Array<FileList>(this.permiso.eventos.length).fill(null);
          this.textArea = new Array<string>(this.permiso.eventos.length).fill('');
          this.fileName = new Array<string>(this.permiso.eventos.length).fill('');
          this.itemNewEventEffect = new Array<string>(this.permiso.eventos.length).fill('body-itemE-close');
          this.diasInput = new Array<number>(this.permiso.eventos.length).fill(0);
          for (let item of this.permiso.eventos) {
            if (item.nombre == 'Elaboración' && this.permiso.fechaCita == null) {
              this.etiquetasBar.push('N/D');
            } else {
              this.etiquetasBar.push(pipe.transform(item.frealizacion));
            }
            if (item.nombre == 'Elaboración') {
              this.etiquetasBar2.push('Cita COFEPRIS');
              this.contentTooltip.push('Cita COFEPRIS');
            } else {
              this.etiquetasBar2.push(item.nombre);
              this.contentTooltip.push(item.nombre);
            }
          }

          //this.etiquetasBar.push(pipe.transform(this.registro.ffin));
          DEBUG && console.log(this.etiquetasBar);
          DEBUG && console.log(this.etiquetasBar2);
          this.lines = new Array(this.etiquetasBar.length - 1).fill(1);

          for (let item of this.permiso.eventos) {
            if (item.terminado) {
              DEBUG && console.log('Entra');
              DEBUG && console.log(this.widthBar);
              this.widthBar = this.widthBar + (100 / (this.lines.length - 1));
            }
          }

          for (let item of this.permiso.eventos) {
            if (item.terminado == false) {
              this.eventoActual = item.nombre;
              break;
            }
          }
          DEBUG && console.log(this.eventoActual);

          this.setEventosDefault();

          this.validarFRealizacion(2);
          this.validarFRealizacion(3);

          // this.borrarCorreosTotal();

          this.showTooltip = new Array<string>(this.permiso.eventos.length).fill('0');
          this.diasPorevento = new Array<string>(this.permiso.eventos.length).fill('');

          this.arrayValidDias1 = new Array<boolean>(this.permiso.eventos.length).fill(false);
          this.arrayValidDias2 = new Array<boolean>(this.permiso.eventos.length).fill(false);
          this.arrayValidEmail = new Array<boolean>(this.permiso.eventos.length).fill(false);
          this.eventInvalidDates = new Array<boolean>(this.permiso.eventos.length).fill(false);

          this.switchArray = new Array<boolean>(this.permiso.eventos.length).fill(false);
          this.open = new Array<boolean>(this.permiso.eventos.length).fill(false);


          for (let item of this.permiso.eventos) {
            DEBUG && console.log(item.frealizacionAux);
            DEBUG && console.log(item.fterminado);
            if (item.fterminado !== undefined) {
              let d = new Date(item.fterminado);
              /* d.setDate(d.getDate() - 1); */
              if (new Date(item.frealizacionAux) < d) {
                item.aTiempo = true;
              } else {
                item.aTiempo = false;
              }
            }
          }

          this.fInicio = new Date(this.permiso.fecha);
          if (this.permiso.citaCofepris !== null) {
            this.fFin = new Date(this.sometimiento.frealizacion)
          } else {
            this.fFin = new Date(this.fInicio);
            this.fFin.setDate(this.fFin.getDate() + 90);
          }

          this.responsableGestion = this.permiso.usuario.email;

          this.buscarUsuarios();
          this.diasPorEventos();

          this.lineTodayPosition();

          this.getBar();

          SessionUser.getInstance().setLoading(false);

          DEBUG && console.log(this.permiso);
        },
        error => {
          DEBUG && console.log('Error al obtener registro: ' + error);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al obtener el trámite');
          document.getElementById('requestFailed').style.display = 'block';
        });
  }


  guardarPermiso() {
    const permiso = JSON.parse(JSON.stringify(this.permiso));

    const listNotificationError = [];
    this.listNotificationError = [];

    this.elaboracion.frealizacion = this.permiso.citaCofepris;

    //this.elaboracion.frealizacion = this.elaboracion.frealizacionAux;
    this.elaboracion.notificaciones = this.listNotificacionesElaboracion;
    for (let item of this.elaboracion.notificaciones) {
      item.finicio = this.calculateFInicioNuevo(this.elaboracion.frealizacion, item.tipoPeriodo, item.cantPeriodo);
      if (new Date(permiso.finicio).getTime() > item.finicio.getTime()) {
        this.findInvalidDate(listNotificationError, this.elaboracion.nombre, item.finicio, item.tipoPeriodo, item.cantPeriodo, item.recurrencia);
      }
    }

    this.elaboracion.correos = this.listCorreosElaboracion;
    //this.sometimiento.frealizacion = this.sometimiento.frealizacionAux;
    this.sometimiento.notificaciones = this.listNotificacionesSometimiento;
    for (let item of this.sometimiento.notificaciones) {
      item.finicio = this.calculateFInicioNuevo(this.sometimiento.frealizacion, item.tipoPeriodo, item.cantPeriodo);
      if (new Date(permiso.finicio).getTime() > item.finicio.getTime()) {
        this.findInvalidDate(listNotificationError, this.sometimiento.nombre, item.finicio, item.tipoPeriodo, item.cantPeriodo, item.recurrencia);
      }
    }

    this.sometimiento.correos = this.listCorreosSometimiento;
    let auxElaboracion = Object.assign({}, this.elaboracion);
    let auxSometimiento = Object.assign({}, this.sometimiento);

    permiso.eventos = [];
    permiso.eventos.push(auxElaboracion);
    permiso.eventos.push(auxSometimiento);

    for (let item of this.listEventos) {
      // item.frealizacion = item.frealizacionAux;
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

    // this.permiso.empresa = SessionUser.getInstance().getCliente().empresas[0];
    DEBUG && console.log(this.elaboracion);
    DEBUG && console.log(this.sometimiento);
    DEBUG && console.log(this.listEventos);

    permiso.terminado = this.rTerminado;
    DEBUG && console.log(this.permiso);
    SessionUser.getInstance().setLoading(true);
    this.agregarPermiso(permiso);
  }


  agregarPermiso(permiso) {
    this.permisoService.crearPermiso(permiso)
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
            this.irPermisos();
          } else {
            SessionUser.getInstance().setMessageFailed(`al guardar el trámite ${!statusNotifications ? '- Notificación ' : ''} ${!statusEmail ? '- Correo' : ''}`);
            document.getElementById('requestFailed').style.display = 'block';
          }
        },
        error => {
          DEBUG && console.log('@agregarPermiso: failed', error);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al guardar el trámite');
          document.getElementById('requestFailed').style.display = 'block';
        });
  }

  eliminarNotificaciones() {
    return new Promise(resolve => {
      let parametro = new Parametros();
      parametro.condicion = this.notifEliminar;
      this.permisoService.eliminarNotificaciones(parametro)
        .subscribe(
          data => {
            DEBUG && console.log('@eliminarNotificaciones: success', data);
            resolve(true);
          },
          error => {
            DEBUG && console.log('@eliminarNotificaciones: failure', error);
            resolve(false);
          });
    });
  }

  eliminarCorreos() {
    return new Promise(resolve => {
      let parametro = new Parametros();
      parametro.condicion = this.correoEliminar;
      this.permisoService.eliminarCorreos(parametro)
        .subscribe(
          data => {
            DEBUG && console.log('@eliminarCorreos: success', data);
            resolve(true);
          },
          error => {
            DEBUG && console.log('@eliminarCorreos: failure', error);
            resolve(false);
          });
    });
  }

  getPdf(item) {
    SessionUser.getInstance().setLoading(true);
    this.permisoService.getPdf(this.permiso.idEmpresa, this.permiso.idpermiso, item.idEvento, item.idHistorial)
      .subscribe(
        data => {
          DEBUG && console.log(data);
          if (data) {
            let pdfFile = 'data:application/pdf;base64,' + data;
            let element = document.createElement('a');
            element.setAttribute('href', pdfFile);
            element.setAttribute('download', 'Doc_' + item.idHistorial + '.pdf');
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
        error => {
          DEBUG && console.log('Error al traer el pdf: ' + error);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al descargar el archivo');
          document.getElementById('requestFailed').style.display = 'block';
        });
  }


  realizarEvento(item) {
    return new Promise(resolve => {
      let parametro = new Parametros();
      parametro.id = item.idEvento;
      parametro.orden = 1;
      this.permisoService.realizarEvento(parametro)
        .subscribe(
          data => {
            DEBUG && console.log('@realizarEvento: ', data);
            resolve(true);
          },
          error => {
            DEBUG && console.log('@realizarEvento: Error al terminar evento: ' + error);
            resolve(false);
          });
    });
  }

  finalizarPermiso() {
    this.rTerminado = 1;
    this.guardarPermiso();
  }

  /***********************************************************+ barra **********************************************************+*/

  clickBar(index) {

    let v = 'itemId' + (index + 1) + '';
    DEBUG && console.log(v);
    this.open.fill(false);
    //this.openFolio = false;
    this.open[index] = true;


    setTimeout(() => {    //<<<---    using ()=> syntax

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
    }, 200)


  }


  getBar() {
    DEBUG && console.log(this.fInicio);
    DEBUG && console.log(this.fFin);

    let days = moment(this.fFin).diff(moment(this.fInicio), 'days');
    DEBUG && console.log(days);
    let perc = days / 100;

    if (this.permiso.fechaCita !== null) {
      DEBUG && console.log(this.permiso.fechaCita);
      let cita = moment(this.permiso.fechaCita, "DD-MM-YYYY").diff(moment(this.fInicio, "DD-MM-YYYY"), 'days');
      DEBUG && console.log(cita);
      this.sinCita = (cita - 1) / perc;
      this.conCita = 100;
      DEBUG && console.log(this.sinCita);
      DEBUG && console.log(this.conCita);
    } else {
      let cita = moment(this.today).diff(moment(this.fInicio), 'days');
      this.sinCita = cita / perc;
      this.conCita = 100 - this.sinCita;
    }

    for (let item of this.permiso.eventos) {
      item.width = (moment(item.frealizacion).diff(moment(this.fInicio), 'days')) / perc;
    }
    DEBUG && console.log(this.permiso.eventos);

    this.positionToday = (moment(this.today).diff(moment(this.fInicio), 'days')) / perc;

    for (let item of this.permiso.eventos) {
      DEBUG && console.log((moment(item.fterminado).diff(moment(this.permiso.fechaCita), 'days')));
      //if((moment(item.fterminado, "DD-MM-YYYY").diff(moment(this.prevision.fechaCita , "DD-MM-YYYY"), 'days' )) > 0 ){
      if (item.fterminado !== null && item.fterminado !== undefined) {
        let cita = moment(item.fterminado).diff(moment(this.fInicio), 'days');
        this.eventosTerminados = cita / perc;
      }
      //}
    }

    DEBUG && console.log(this.eventosTerminados);

  }

  lineTodayPosition() {
    DEBUG && console.log(this.permiso.eventos);
    //let today = new Date();
    let index = -1;
    let igual = false;
    DEBUG && console.log(this.today);
    DEBUG && console.log(moment(this.today, "DD-MM-YYYY"));

    /* if(moment(this.registro.finicio).diff(moment(this.today), 'days') > 0 || moment(this.registro.finicio, "DD-MM-YYYY").diff(moment(this.today, "DD-MM-YYYY"), 'days') == 0){
    } */
    DEBUG && console.log(this.positionToday);
  }

  handleValidDates(date, option, index = 0) {
    return new Promise(resolve => {
      setTimeout(() => {
        let startTime = new Date(this.testRangeDateInit);
        startTime.setHours(0, 0, 0, 0);
        let endTime = new Date(this.testRangeDate);
        endTime.setHours(23, 59, 59, 999);
        let currentDate = new Date(date);

        if (option === 2 && this.listNotificacionesNuevo.length > 0) {
          const result = [];
          _.forEach(this.listNotificacionesNuevo, o => {
            const startDate = this.calculateFInicioNuevo(this.evento.frealizacion, o.tipoPeriodo, o.cantPeriodo);
            result.push({...o, startDate: startDate, startDateText: `Inicia el ${this.getCustomDate(startDate)}`});
          });
          this.listNotificacionesNuevo = result.slice();
        }

        let valid = true;
        if (startTime.getTime() < currentDate.getTime() && (currentDate.getTime() < endTime.getTime() || option === 1)) {

          if (option === 1) {
            this.invalidDateCofeprisCurrent = false;
          } else if (option === 2) {
            this.newEventInvalidDates = false;
          } else if (option === 3) {
            this.eventInvalidDates[index] = false;
            this.calculateFRealizacionEvento(index);
          }

          valid = false;

        } else {

          if (option === 1) {
            this.invalidDateCofeprisCurrent = true;
          } else if (option === 2) {
            this.newEventInvalidDates = true;
          } else if (option === 3) {
            this.eventInvalidDates[index] = true;
            this.calculateFRealizacionEvento(index);
          }

          valid = true;
        }

        resolve(valid);
      });
    });
  }

  clearValidators() {
    this.invalidDateCofeprisCurrent = false;
    this.newEventInvalidDates = false;

    let eventInvalidDates = [];
    _.forEach(this.eventInvalidDates, () => {
      eventInvalidDates.push(false);
    });

    this.eventInvalidDates = eventInvalidDates;
  }

  validFullData() {
    let validate = this.fullData;

    _.forEach(this.eventInvalidDates, o => {
      if (o) {
        validate = false;
        return false;
      }
    });

    this.fullData = validate;
  }

  calculateFInicioNuevo(frealizacion, periodo, cantidad) {
    let date: Date;
    date = this.diasHabilesService.getDate(frealizacion, periodo, cantidad);
    return date;
  }

  calculateFRealizacionNuevo(periodo, cantidad, validateDate = false, customDate = null) {
    let date: Date;
    date = this.diasHabilesService.getDate(!customDate ? this.permiso.ffin : customDate, periodo, cantidad);

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

  validarFRealizacion(index) {
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
        if(this.elaboracion.tipoPeriodo !== '' && this.elaboracion.cantPeriodo !== 0){
          DEBUG && console.log('Entra');
          let da = this.calculateFRealizacionNuevo(this.elaboracion.tipoPeriodo, this.elaboracion.cantPeriodo);
          da.setDate(da.getDate() - 1);
          this.validDate[1] = this.fRealizacionArray[0] > da ? false : true;
        }
        break;
      case 3:
        if(this.sometimiento.tipoPeriodo !== '' && this.sometimiento.cantPeriodo !== 0){
          DEBUG && console.log('Entra');
          let da = this.calculateFRealizacionNuevo(this.sometimiento.tipoPeriodo, this.sometimiento.cantPeriodo);
          da.setDate(da.getDate() - 1);
          this.validDate[3] = this.fRealizacionArray[0] > da ? false : true;
        }
        break;

      default:
        break;
    }
    DEBUG && console.log(this.validDate);
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

}
