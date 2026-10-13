import {Empresa} from "./empresa.class";
import {Usuario} from "./usuario.class";
import {Eventos} from "./eventos.class";

export class Prevision {

  idPrevision: number;
  vigencia: number;
  tipoPrevision: string;
  folioPrevision: string;
  usuario: Usuario;
  empresa: Empresa;
  publicado: number;
  eventos: Array<Eventos>;
  citaCofepris: Date;
  citaCofeprisAux: Date;


  impLstProductos: boolean;

  proxPrevision: boolean;
  idProxPrevision: number;

  fechaCita: Date;

  constructor() {
    let today = new Date();

    this.idPrevision = 0;
    this.vigencia = today.getFullYear() + 1;
    this.tipoPrevision = 'Normal';
    this.folioPrevision = '';
    this.usuario = null;
    this.empresa = null;
    this.publicado = 0;
    this.eventos = [];
    this.citaCofepris = null;
    this.citaCofeprisAux = null;


    this.impLstProductos = false;

    this.proxPrevision = false;
    this.idProxPrevision = 0;

    this.fechaCita = null;
  }

  reset() {
    this.idPrevision = 0;
    this.tipoPrevision = 'Normal';
    this.folioPrevision = '';
    this.usuario = null;
    this.empresa = null;
    this.publicado = 0;
    this.eventos = [];
    this.citaCofepris = null;
    this.citaCofeprisAux = null;

    this.impLstProductos = false;

    this.proxPrevision = false;
    this.idProxPrevision = 0;

    this.fechaCita = null;
  }
}
