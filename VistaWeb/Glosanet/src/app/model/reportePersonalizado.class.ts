import { Usuario } from "./usuario.class";

export class ReportePersonalizado{
  idReportePersonalizado: any;
  nombre: String;
  descripcion: String;
  fechaCreacion: Date;
  autor: Usuario;
  lstPReportesPersonalizados: any[];
}
