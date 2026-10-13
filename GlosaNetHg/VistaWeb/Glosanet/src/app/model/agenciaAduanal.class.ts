import {DireccionFiscal} from "./direccionFiscal.class";
import {AgenteAduanal} from "./agenteAduanal.class";

export class AgenciaAduanal {

  idAgenciaAduanal: number;
  rfc: string;
  razonSocial: string;
  nombre: string;
  telefono: string;
  correoElectronico: string;
  direccion: DireccionFiscal;
  agente: AgenteAduanal;

  constructor() {
    this.idAgenciaAduanal = 0;
    this.rfc = '';
    this.razonSocial = '';
    this.nombre = '';
    this.telefono = '';
    this.correoElectronico = '';
    this.direccion = null;
    this.agente = null;
  }

}
