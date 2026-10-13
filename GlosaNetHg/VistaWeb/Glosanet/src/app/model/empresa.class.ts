import {Cliente} from "./cliente.class";

export class Empresa {

  idEmpresa: number;
  razonSocial: string;
  rfc: string;
  pais: string;
  estado: string;
  calle: string;
  delegacion: string;
  municipio: string;
  cp: string;
  empresarial: string;
  email: string;
  telefono: string;
  mseguridad: boolean;
  vigencia: Date;
  cliente: Cliente;
  numDataStage: number;
  finicioDataStage: Date;
  ffinDataStage: Date;
  vigenciaMatriz: Date;
  vigenciaMatrizFin: Date;
  auditable: Date;
  imagenEmpresa: string;
  representanteLegal: string;

  constructor() {
    this.idEmpresa = 0;
    this.razonSocial = '';
    this.rfc = '';
    this.pais = '';
    this.estado = '';
    this.calle = '';
    this.delegacion = '';
    this.municipio = '';
    this.cp = '';
    this.empresarial = '';
    this.email = '';
    this.telefono = '';
    this.mseguridad = false;
    this.vigencia = null;
    this.cliente = null;
    this.numDataStage = 0;
    this.finicioDataStage = null;
    this.ffinDataStage = null;
    this.vigenciaMatriz = null;
    this.vigenciaMatrizFin = null;
    this.auditable = null;
    this.imagenEmpresa = '';
    this.representanteLegal = '';
  }

  reset() {
    this.idEmpresa = 0;
    this.razonSocial = '';
    this.rfc = '';
    this.pais = '';
    this.estado = '';
    this.calle = '';
    this.delegacion = '';
    this.municipio = '';
    this.cp = '';
    this.empresarial = '';
    this.email = '';
    this.telefono = '';
    this.mseguridad = false;
    this.vigencia = null;
    this.cliente = null;
    this.numDataStage = 0;
    this.finicioDataStage = null;
    this.ffinDataStage = null;
    this.vigenciaMatriz = null;
    this.vigenciaMatrizFin = null;
    this.auditable = null;
    this.imagenEmpresa = '';
    this.representanteLegal = '';
  }
}

