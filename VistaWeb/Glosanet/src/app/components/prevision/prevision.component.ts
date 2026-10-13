import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {FormGroup, FormControl, Validators} from '@angular/forms';
import {Notificacion} from '../../model/notificacion.class';
import {CorreoEvento} from '../../model/correoEvento.class';
import {Eventos} from '../../model/eventos.class';
import {Prevision} from '../../model/prevision.class';
import {Parametros} from '../../model/parametros.class';
import {SessionUser} from '../../services/session/session.service';
import {UserService} from '../../services/user/user.service';
import {PrevisionesService} from '../../services/previsiones/previsiones.service';
import {DiasHabilesService} from '../../services/general/diasHabiles';
import {START_DATE_PREVISION_CURRENT, END_DATE_PREVISION_CURRENT} from '../../util/common.protocols';
import * as _ from 'lodash';

/* Dev Tools */
import {DEBUG} from '../../settings/app.settings';

@Component({
  selector: 'gn-prevision',
  templateUrl: './prevision.component.html',
  styleUrls: ['./prevision.component.scss']
})
export class PrevisionComponent implements OnInit {

  users: any = [];
  prevision: Prevision = new Prevision();

  selectUser: any = {nombre: ''};

  gEventosForm1: FormGroup;
  gEventosForm2: FormGroup;

  public dropListPeriodoTiempo: any[] = [{nombre: 'Días'}, {nombre: 'Semanas'}, {nombre: 'Meses'}, {nombre: 'Años'}];

  public dropListNotificacion: any[] = [{nombre: 'Cada Día'}, {nombre: 'Cada Semana'}, {nombre: 'Cada Mes'}, {nombre: 'Cada Año'}];

  /* Efectos de los 3 eventos por default */
  itemEventEffect: any = new Array(1).fill('body-itemE-close');


  /* reset droplistNuevo */
  resetNuevo: Array<any> = new Array<any>(3);


  listNotificacionesNuevo: Array<Notificacion> = new Array<Notificacion>();
  listCorreosNuevo: Array<CorreoEvento> = new Array<CorreoEvento>();

  generarEventEffect: string = 'body-generarE-close';

  elaboracion: Eventos = new Eventos();

  /* Lista de eventos nuevos */
  listEventos: Array<Eventos> = new Array<Eventos>();

  /* **********************************************+ Elaboracion **********************************************+ */

  dia1Elaboracion: number = 0;
  correoElaboracion: string = '';
  listNotificacionesElaboracion: Array<Notificacion> = new Array<Notificacion>();
  listCorreosElaboracion: Array<CorreoEvento> = new Array<CorreoEvento>();
  resetElaboracion: Array<any> = new Array<any>(3);

  /* Efectos de los nuevos eventos creados */
  itemNewEventEffect: any = new Array(); /* .fill('body-itemE-close'); */

  evento: Eventos = new Eventos();
  notificaciones: Notificacion = new Notificacion();
  correos: CorreoEvento = new CorreoEvento();

  validEmailElaboracion = false;
  validarDias2Elaboracion = false;
  validarDias1Elaboracion = false;

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
  listCorreoT: any[] = [];

  check: boolean = false;

  fullData: boolean = true;

  nombreConfirmEliminar: string = '';
  indexEventEliminar: number = 0;

  indexModal: number = 0;

  date = new Date();
  yearActual: number = 0;
  nextYear: number = 0;

  /* Variable para setear valores de droplist */
  clear: boolean = false;
  resetValidate: boolean = false;

  validarDias2Nuevo: boolean = false;
  validEmailNuevo: boolean = false;

  newEventInvalidDates: boolean = false;
  invalidDateCofeprisEdit: boolean = false;
  eventInvalidDates: any = [];

  startDatePrevision = START_DATE_PREVISION_CURRENT;
  endDatePrevision = END_DATE_PREVISION_CURRENT;

  /* Lista de notificaciones que se encuentra fuera de tiempo */
  listNotificationError: any = [];

  constructor(
    private router: Router,
    private userService: UserService,
    private previsionService: PrevisionesService,
    private diasHabilesService: DiasHabilesService) {
  }

  ngOnInit() {
    this.startDatePrevision.setHours(0,0,0,0);
    this.endDatePrevision.setHours(23, 59, 59, 999);

    this.yearActual = this.date.getFullYear();
    this.nextYear = this.yearActual + 1;

    this.gEventosForm1 = new FormGroup({
      nombreEvent: new FormControl('', [Validators.required, Validators.pattern('^([a-zñáéíóú, A-ZÁÉÍÓÚ]{1}[a-zñáéíóú,A-ZÁÉÍÓÚ]+[\s]*)+$')]),
      dias1: new FormControl('', [Validators.required, Validators.pattern('^(([0-9])+(-){0,1})+$')]),
      dias2: new FormControl('', [Validators.required, Validators.pattern('^(([0-9])+(-){0,1})+$')]),
      dias3: new FormControl('', [Validators.required, Validators.pattern('^(([0-9])+(-){0,1})+$')])
    });

    this.gEventosForm2 = new FormGroup({
      correoN: new FormControl('', [Validators.required, Validators.pattern('^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$')]),
    });

    this.prevision.citaCofepris = new Date((this.prevision.vigencia - 1), 4, 30);

    this.elaboracion.nombre = 'Elaboración';
    this.elaboracion.definido = true;

    this.buscarUsuarios();
  }


  ngDoCheck() {
    if (this.listNotificacionesElaboracion.length > 0
      && this.listCorreosElaboracion.length > 0 && this.prevision.usuario !== undefined) {

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

  irPermisos() {
    SessionUser.getInstance().setConfirm(false);
    this.router.navigate(['protected/permisos']);
  }


  resetDroplistNuevo(seccion: string) {
    if (seccion === 'Nombre') {
      this.resetNuevo[0] = {nombre: 'Días'};
    } else {
      this.resetNuevo[1] = {nombre: ''};
      this.resetNuevo[2] = {nombre: ''};
    }
  }

  checkF($event) {
    $event.stopPropagation();
    this.check = !this.check;
  }

  confirm(open: boolean, index: number) {
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

  setConfirmFalse() {
    setTimeout(() => {
      SessionUser.getInstance().setConfirm(false);
    }, 198);
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
        // this.modalForm.patchValue({
        // });
        break;
      default:
        break;
    }
  }

  resetDroplistElaboracion(seccion: string) {
    if (seccion === 'Nombre') {
      this.resetElaboracion[0] = {nombre: 'Días'};
    } else {
      this.resetElaboracion[1] = {nombre: ''};
      this.resetElaboracion[2] = {nombre: ''};
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

  dropListTiempoR($event, i) {
    this.listEventos[i].tipoPeriodo = $event.nombre;
    // this.calculateFRealizacionEvento(i);
  }


  dropListElaboracion($event, index) {
    switch (index) {
      case 1: // Nombre del evento nuevo
        this.elaboracion.tipoPeriodo = $event.nombre;
        // this.calculateFRealizacion(1);
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
    date = this.diasHabilesService.getDate(!customDate ? this.endDatePrevision : customDate, periodo, cantidad);

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

  listCorreosTotal(item) {
    this.listCorreoT.push(item);
    let i;
    const len = this.listCorreoT.length;
    const out = [];
    const obj = {};

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


  fecha($event) {
    DEBUG && console.log($event);
  }

  confirmarEliminar(i, nombre) {
    this.nombreConfirmEliminar = nombre;
    this.indexEventEliminar = i;
    this.classAnimation4 = 'w3-animate-zoom';
    document.getElementById('idEliminarEv').style.display = 'block';
  }


  /* ****************************** Nuevo evento llenar y borrar ******************************  */
  agregarNuevoNotificacion() {
    if (this.gEventosForm1.get('dias2').value !== 0 && this.notificaciones.tipoPeriodo !== '' && this.notificaciones.recurrencia !== '') {
      this.notificaciones.cantPeriodo = this.gEventosForm1.get('dias2').value;
      this.notificaciones.startDate = this.calculateFInicioNuevo(this.evento.frealizacion ? this.evento.frealizacion : this.endDatePrevision, this.notificaciones.tipoPeriodo, this.notificaciones.cantPeriodo);
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
    DEBUG && console.log('******************+ lista nuevo notificaciones ***************');
    DEBUG && console.log(this.listNotificacionesNuevo);
  }

  borrarNuevoCorreo(data: number) {
    this.listCorreosNuevo.splice(data, 1);
    DEBUG && console.log('******************+ lista nuevo correos ***************');
    DEBUG && console.log(this.listCorreosNuevo);
  }

  /* ****************************** Eventos crear y borrar ******************************  */
  generarNuevoEvento() {
    // TODO: El calculo de fecha de inicio del evento se realiza en el guardado final,
    // ya que no tiene caso realizar el calculo ya que el evento se puede remover
    if (this.evento.nombre !== '' && this.evento.frealizacion !== null &&
        this.listNotificacionesNuevo.length > 0 && this.listCorreosNuevo.length > 0) {
      let auxCorreo = Object.assign([], this.listCorreosNuevo);
      let auxNotificaciones = Object.assign([], this.listNotificacionesNuevo);
      this.evento.correos = auxCorreo;
      this.evento.notificaciones = auxNotificaciones;
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

      this.agregarCamposNuevoevento();
      this.generarEventEffect = 'body-generarE-close';

      this.validarDias2Nuevo = false;
    }
    DEBUG && console.log('******************+ lista eventos ***************');
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
    // this.dropListPeriodoTiempo.splice(index, 1);

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

      this.resetDefault[index] = {nombre: ''};
      this.eventInvalidDates[index] = false;

      DEBUG && console.log(this.listEventos);
    }
  }

  borrarNotificacion(ev: any, item: any) {
    this.listEventos[ev].notificaciones.splice(item, 1);
    DEBUG && console.log('******************+ lista Notificaciones ***************');
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
        let aux = Object.assign([], this.correos);
        this.listEventos[index].correos.push(aux);

        this.listCorreosTotal(this.correoInput[index]);
      }
      this.correos.reset();

      this.correoInput[index] = '';


      DEBUG && console.log(this.listEventos);
    }
  }

  borrarCorreo(ev: any, item: any) {
    this.listEventos[ev].correos.splice(item, 1);
    DEBUG && console.log('******************+ lista Correo ***************');
    //this.borrarCorreosTotal();
    DEBUG && console.log(this.listEventos);
  }

  /*  ****************************** Elaboracion Agregar y borrar  ****************************** */
  agregarNotificacionElaboracion() {
    if (this.dia1Elaboracion !== 0 && this.notificaciones.tipoPeriodo !== '' && this.notificaciones.recurrencia !== '') {
      this.notificaciones.cantPeriodo = this.dia1Elaboracion;
      this.notificaciones.startDate = this.calculateFInicioNuevo(this.prevision.citaCofepris, this.notificaciones.tipoPeriodo, this.notificaciones.cantPeriodo);
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
    DEBUG && console.log('******************+ lista Elaboracion notificaciones ***************');
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
    DEBUG && console.log('******************+ lista Elaboracion correos ***************');
    //this.borrarCorreosTotal();
    DEBUG && console.log(this.listCorreosElaboracion);
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
            u.push({nombre: item.nombre + ' ' + item.apellido + ' - ' + item.email, user: item});
          }
          Array.prototype.push.apply(this.users, u);
          DEBUG && console.log(this.users);
        },
        error => {
          DEBUG && console.log('Error al buscar usuarios: ' + error);
        });
  }

  guardarPrevision() {
    const prevision = JSON.parse(JSON.stringify(this.prevision));

    const listNotificationError = [];
    this.listNotificationError = [];

    prevision.impLstProductos = this.check;
    this.elaboracion.frealizacion = this.prevision.citaCofepris;

    this.elaboracion.notificaciones = this.listNotificacionesElaboracion;
    for (let item of this.elaboracion.notificaciones) {
      item.finicio = this.calculateFInicioNuevo(this.elaboracion.frealizacion, item.tipoPeriodo, item.cantPeriodo);
      if (this.startDatePrevision.getTime() > item.finicio.getTime()) {
        this.findInvalidDate(listNotificationError, this.elaboracion.nombre, item.finicio, item.tipoPeriodo, item.cantPeriodo, item.recurrencia);
      }
    }

    this.elaboracion.correos = this.listCorreosElaboracion;
    let auxElaboracion = Object.assign({}, this.elaboracion);

    prevision.eventos.push(auxElaboracion);

    for (let item of this.listEventos) {
      prevision.eventos.push(item);
      _.forEach(item.notificaciones, notification => {
        notification.finicio = this.calculateFRealizacionNuevo(notification.tipoPeriodo, notification.cantPeriodo, false, item.frealizacion);
        if (this.startDatePrevision.getTime() > notification.finicio.getTime()) {
          this.findInvalidDate(listNotificationError, item.nombre, notification.finicio, notification.tipoPeriodo, notification.cantPeriodo, notification.recurrencia);
        }
      });
    }

    prevision.empresa = SessionUser.getInstance().getCliente().empresas[0];
    if (listNotificationError.length > 0) {
      /* Hay al menos un registro fuera de rango, mostramos mensaje en UI*/
      this.listNotificationError = listNotificationError.slice();
      return;
    }

    SessionUser.getInstance().setLoading(true);

    this.agregarPrevision(prevision);
  }

  agregarPrevision(prevision) {
    this.previsionService.agregarPrevision(prevision)
      .subscribe(
        data => {
          DEBUG && console.log(data);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageOk('Has guardado');
          document.getElementById('idOk').style.display = 'block';
          this.irPermisos();
        },
        error => {
          DEBUG && console.log(error);
          SessionUser.getInstance().setLoading(false);
          this.irPermisos();
        });
  }

  handleValidDates(date, option, index = 0) {
    setTimeout(() => {
      const currentDate = new Date(date);
      currentDate.setHours(9, 0, 0, 0);

      if (option === 2 && this.listNotificacionesNuevo.length > 0) {
        const result = [];
        _.forEach(this.listNotificacionesNuevo, o => {
          const startDate = this.calculateFInicioNuevo(this.evento.frealizacion, o.tipoPeriodo, o.cantPeriodo);
          result.push({...o, startDate: startDate, startDateText: `Inicia el ${this.getCustomDate(startDate)}`});
        });
        this.listNotificacionesNuevo = result.slice();
      }

      if (this.startDatePrevision.getTime() < currentDate.getTime() && currentDate.getTime() < this.endDatePrevision.getTime()) {
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

    const eventInvalidDates = [];
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
        if (this.startDatePrevision.getTime() > o.startDate.getTime()) {
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

}
