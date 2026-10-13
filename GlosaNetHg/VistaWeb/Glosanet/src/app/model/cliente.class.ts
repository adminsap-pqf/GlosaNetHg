export class Cliente {

  idCliente: Number;
  nombre: String;
  representante: String;
  rfcActual: String;
  idEmpresaActual: Number;
  empresas: any[];//List<EmpresaBO>
  usuarios: any[];//List<UsuarioBO>
  fcreacion: Date;
  fua: Date;
  habilitado: Boolean;
  prospecto: Boolean;
  frecuenciaFacturacion: Number;
  inicioCobranza: Number;
  paquete: any;//PaqueteBO
  direccion: any;//DireccionFiscalBO
  direccionFisica: any;//DireccionFiscalBO
  // String rfc;
  rfc: String;
  razonSocial: String;
  datosContacto: any;//DatosContactoBO
  datosContactoCobro: any;//DatosContactoBO
  pedimentosAnio: any[];//List<PedimentoBO>
  pedimentosTotales: Number;


  telefono: string;
  fauditable: Date;
  regimenFiscal: string;
  usoCFDI: string;
  claveProducto: string;

  numeroPedimentos: number;

}
