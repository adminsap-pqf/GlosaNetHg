import {Notificacion} from "./notificacion.class";
import {CorreoEvento} from "./correoEvento.class";
import {HistorialEvento} from "./historialEvento.class";

export class Eventos {

  idEvento: number;
  idRegistro: number;
  nombre: string;
  definido: boolean;
  cantPeriodo: number
  tipoPeriodo: any;
  frealizacion: Date;
  terminado: boolean;
  notificaciones: Array<Notificacion>;
  correos: Array<CorreoEvento>;

  fterminado: Date;


  historial: Array<HistorialEvento>
  aTiempo: boolean;
  totalHistorial: number;
  tramite: boolean;
  fechaTramite: Date;


  frealizacionAux: Date; //Variable de apoyo para guardar la fecha de realizacion a la hora de guardar la fecha de terminado en la barra de monitoreo


  idPrevision: number;

  idPermiso

  width: number;

  tramiteCofepris: string = '';
  estadoCofepris: string = '';

  constructor() {
    this.idEvento = 0;
    this.idRegistro = 0;
    this.nombre = '';
    this.definido = false;
    this.cantPeriodo = 0;
    this.tipoPeriodo = '';
    this.frealizacion = null;
    this.terminado = false;
    this.notificaciones = [];
    this.correos = [];

    this.fterminado = null;
    this.historial = [];
    this.aTiempo = true;
    this.totalHistorial = 0;
    this.tramite = false;
    this.fechaTramite = null;

    this.frealizacionAux = null;

    this.idPrevision = 0;

    this.width = 0;

    this.tramiteCofepris = '';
    this.estadoCofepris = '';
  }

  reset() {
    this.idEvento = 0;
    this.idRegistro = 0;
    this.nombre = '';
    this.definido = false;
    this.cantPeriodo = 0;
    this.tipoPeriodo = '';
    this.frealizacion = null;
    this.terminado = false;
    this.notificaciones = [];
    this.correos = [];
    this.fterminado = null;
    this.aTiempo = true;
    this.totalHistorial = 0;

    this.frealizacionAux = null;

    this.idPrevision = 0;

    this.width = 0;
    this.tramiteCofepris = '';
    this.estadoCofepris = '';
  }


  resetHistorial() {
    this.historial = [];
  }
}
