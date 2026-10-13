export class DatosGenerales {

  pkDatosGenerales: Number;
  dataStage: any;//DataStageBO
  patente: String;
  pedimento: String;
  seccionAduanera: String;
  tipoOperacion: String;
  claveDocumento: String;
  seccionAduaneraEntrada: String;
  curpContribuyente: String;
  rfc: String;
  curpAgenteA: String;
  tipoCambio: Number;//Double
  totalFletes: Number;//Double
  totalSeguros: Number;//Double
  totalEmbalajes: Number;//Double
  totalIncrementables: Number;//Double
  totalDeducibles: Number;//Double
  pesoBrutoMercancia: Number;//Double
  medioTransporteSalida: Number;
  medioTransporteArribo: Number;
  medioTransporteEntradaSalida: Number;
  destinoMercancia: Number;
  nombreContribuyente: String;
  calleContribuyente: String;
  numInteriorContribuyente: String;
  numExteriorContribuyente: String;
  cpcontribuyente: String;
  municipioContribuyente: String;
  entidadFedContribuyente: String;
  paisContribuyente: String;
  tipoPedimento: Number;
  fechaRecepcionPedimento: Date;
  fechaPagoReal: Date;

  finicio: Date;
  ffin: Date;
  resumen: any;//ResumenBO

  fk01Empresa: number;
}