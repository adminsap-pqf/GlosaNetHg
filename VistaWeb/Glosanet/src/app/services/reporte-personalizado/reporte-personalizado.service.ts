import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {SessionUser} from './../session/session.service';
import {Parametros} from '../../model/parametros.class';
import {ReportePersonalizado} from '../../model/reportePersonalizado.class';
import {MediaContent} from "../../interfaces/mediacontent";

@Injectable()
export class ReportePersonalizadoService {
  private urlobtenerTablas: string = SessionUser.getInstance().getIP() + 'reportePersonalizado/obtenerTablasDataStage';
  private urlPeticionRRP: string = SessionUser.getInstance().getIP() + 'reportePersonalizado/peticionResultadosDeReportePersonalizado';
  private urlObtenerLiga: string = SessionUser.getInstance().getIP() + 'reportePersonalizado/obtenerLigasDescargasReportes';
  private urlGuardarReporte: string = SessionUser.getInstance().getIP() + 'reportePersonalizado/guardarReporte';
  private urlObtenerReportes: string = SessionUser.getInstance().getIP() + 'reportePersonalizado/obtenerReportes';
  private urlObtenerReportePorId: string = SessionUser.getInstance().getIP() + 'reportePersonalizado/obtenerReportePorId';
  private urlBorrarreporte: string = SessionUser.getInstance().getIP() + 'reportePersonalizado/borrarReporte';

  constructor(private http: HttpClient) {
  }

  obtenerTablas(parametros: Parametros) {
    let body = JSON.stringify(parametros);

    return this.http.post<any[]>(this.urlobtenerTablas, body);
  }

  peticionResultadosDeReportePersonalizado(parametros: Parametros) {
    let body = JSON.stringify(parametros);

    return this.http.post<any>(this.urlPeticionRRP, body);
  }

  obtenerLigasDescargasReportes(parametros: Parametros) {
    let body = JSON.stringify(parametros);

    return this.http.post<MediaContent>(this.urlObtenerLiga, body);
  }

  guardarReporte(reportePersonalizado: ReportePersonalizado) {
    let body = JSON.stringify(reportePersonalizado);

    return this.http.post<any>(this.urlGuardarReporte, body);
  }

  obtenerReportes() {
    return this.http.post<any>(this.urlObtenerReportes, {});
  }

  obtenerReportePorId(reportePersonalizado: ReportePersonalizado) {
    let body = JSON.stringify(reportePersonalizado);

    return this.http.post<any>(this.urlObtenerReportePorId, body);
  }

  borrarReporte(reportePersonalizado: ReportePersonalizado) {
    let body = JSON.stringify(reportePersonalizado);

    return this.http.post<any>(this.urlBorrarreporte, body);
  }


}
