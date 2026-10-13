import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {SessionUser} from './../session/session.service';
import {Parametros} from '../../model/parametros.class';
/* Dev Tools */
import {DEBUG} from "../../settings/app.settings";

@Injectable()
export class PreauditoriaHttpService {
  private url: string = SessionUser.getInstance().getIP() + 'reportePredefinido/';
  private urlBase: string = SessionUser.getInstance().getIP();

  constructor(private http: HttpClient) {
  }

  obtenerResultadosReportesPredefinidos(parametros: Parametros) {
    let body = JSON.stringify(parametros);
    DEBUG && console.log(body);

    return this.http.post<any>(`${this.url}obtenerResultadosReportesPredefinidos`, body);
  }


  generarReporte(map) {
    const headers = new HttpHeaders()
      .set('Content-Type', 'text/plain; charset=utf-8')
      .set('x-auth-token', SessionUser.getInstance().getToken());

    return this.http.post<any>(`${this.url}generarReporte`, map, {headers, responseType: "text" as "json"});
  }

  getDataStep5(params: Parametros) {
    const body = JSON.stringify(params);
    return this.http.post<any>(`${this.url}reporteContribuciones`, body);
  }

  getDataStep6(params: Parametros) {
    const body = JSON.stringify(params);
    return this.http.post<any>(`${this.urlBase}controller/reportes/reportePartidasDetallado`, body);
  }

  getFileExportStep5(params: Parametros) {
    const headers = new HttpHeaders()
      .set('Content-Type', 'text/plain; charset=utf-8');
    const body = JSON.stringify(params);
    return this.http.post<any>(`${this.url}reporteContribucionesDescarga`, body, {headers, observe: 'response', responseType: "text" as "json"});
  }

  getFileExportStep6(params: Parametros) {
    const headers = new HttpHeaders()
      .set('Content-Type', 'text/plain; charset=utf-8');
    const body = JSON.stringify(params);
    return this.http.post<any>(`${this.url}reportePartidasDescarga`, body, {headers, observe: 'response', responseType: "text" as "json"});
  }

  getPartidas(data) {
    const body = JSON.stringify(data);
    return this.http.post<any>(`${this.url}reportePartidas`, body);
  }
}
