import {DireccionFiscal} from "./direccionFiscal.class";
import {AgenciaAduanal} from "./agenciaAduanal.class";

export class AgenteAduanal {

  idAgente: number;
  numeroPatente: string;
  nombre: string;
  apellido: string;
  rfc: string;
  telefono: string;
  correoElectronico: string;
  direccion: DireccionFiscal;
  idCliente: number;
  agencias: AgenciaAduanal[];

  constructor() {
    this.idAgente = 0;
    this.numeroPatente = '';
    this.nombre = '';
    this.apellido = '';
    this.rfc = '';
    this.telefono = '';
    this.correoElectronico = '';
    this.direccion = null;
    this.idCliente = 0;
    this.agencias = [];
  }

}
