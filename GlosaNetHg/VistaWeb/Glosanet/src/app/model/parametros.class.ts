import { Usuario } from "./usuario.class";
import { FileZip } from "./FileZip.class";
import { HistorialEvento } from "./historialEvento.class";
import { HistorialProducto } from './historialProducto.class';
import { Notificacion } from "./notificacion.class";

export class Parametros{

  usuario: Usuario;
  file:File;
  //Fechas
  year: Number;
  semestre: Number;
  trimestre: Number;
  mes: String;
  finicio: any;
  ffin: any;
  tipo: String;

  //Reportes
  reporte: Number;
  importacionDefinitiva: Boolean;

  //Direccion
  pais: String;
  cp: String;

  //Listas
  lstObjects: any[];

  //Objeto
  object: any;

  //consultas
  fechaInicio: any;
  fechaFin: any;
  id: Number;
  auxBoolean: Boolean;
  // orden: Number;

  //Campos
  idioma: String;
  condicion: String;

  archivos: String[];
  nombres: String[];
  salida: String;


  /* Registros */

  idEmpresa: number;
  idRegistro: number;
  idEvento: number;
  idHistorial: number;
  historial:Array<HistorialEvento>;
  historialProducto:Array<HistorialProducto>;
  path: String;

  /*Historial-Importaciones*/
  idPrevision: any;
  empresa: any;

  flagProductoPartidas: boolean;
  idproducto: any;


  map:any;
  lstRegiones: any;

  /* Notificaciones */

  notificaciones: Array<Notificacion>;

  /* previsiones */

  pHistorial:Array<HistorialEvento>;

  permisoHistorial:Array<HistorialEvento>;

  /* Paginación */
  page: number;
  size: number;
  orden: number; /* 0 = Ascendente / 1 = Descendente */
  busqueda: string; /* Nombre o código */

}
