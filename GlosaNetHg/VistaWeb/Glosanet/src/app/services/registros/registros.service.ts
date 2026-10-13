import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {SessionUser} from './../session/session.service';
import {Parametros} from '../../model/parametros.class';
import {Registro} from '../../model/registro.class';
import {MediaContent} from "../../interfaces/mediacontent";
/* Dev Tools */
import {DEBUG} from "../../settings/app.settings";
import {EventDuration} from "../../interfaces/eventduration";

@Injectable()
export class RegistrosService {

  private urlRegistros = SessionUser.getInstance().getIP() + "registros/";
  private customUrl = SessionUser.getInstance().getIP() + "controller/registros/";

  constructor(private http: HttpClient) {
  }

  obtenerRegistrosEmpresa(parametros: Parametros) {
    let body = JSON.stringify(parametros);

    return this.http.post<any>(`${this.urlRegistros}obtenerRegistrosEmpresa`, body);
  }

  guardarRegistro(registro) {
    let body = JSON.stringify(registro);
    DEBUG && console.log(body);

    return this.http.post<any>(`${this.urlRegistros}guardarRegistro`, body);
  }

  obtenerRegistroPorId(parametro: Parametros) {
    let body = JSON.stringify(parametro);

    return this.http.post<any>(`${this.urlRegistros}obtenerRegistroPorId`, body);
  }

  obtenerTiempoRealizacion(parametro: Parametros) {
    let body = JSON.stringify(parametro);

    return this.http.post<EventDuration>(`${this.urlRegistros}obtenerTiempoRealizacion`, body);
  }

  obtenerProductosDataStagePorEmpresa(parametro: Parametros) {
    let body = JSON.stringify(parametro);

    return this.http.post<any>(`${this.urlRegistros}obtenerProductosDataStagePorEmpresa`, body);
  }

  guardarHistorialEvento(parametro: Parametros) {
    let body = JSON.stringify(parametro);
    DEBUG && console.log(body);

    return this.http.post<any>(`${this.urlRegistros}guardarHistorialEvento`, body);
  }

  obtenerHistorialEvento(parametro: Parametros) {
    let body = JSON.stringify(parametro);

    return this.http.post<any>(`${this.urlRegistros}obtenerHistorialEvento`, body);
  }

  realizarEvento(parametro: Parametros) {
    let body = JSON.stringify(parametro);

    return this.http.post<any>(`${this.urlRegistros}realizarEvento`, body);
  }

  actualizaNotificaciones(parametro: Parametros) {
    let body = JSON.stringify(parametro);
    DEBUG && console.log(body);

    return this.http.post<any>(`${this.urlRegistros}actualizaNotificaciones`, body);
  }

  guardarElementoMonitoreoRegistro(parametro: Parametros) {
    let body = JSON.stringify(parametro);

    return this.http.post<any>(`${this.urlRegistros}guardarElementoMonitoreoRegistro`, body);
  }

  obtenerStatusTramiteCofepris(parametro: Parametros) {
    let body = JSON.stringify(parametro);

    return this.http.post<MediaContent>(`${this.urlRegistros}obtenerStatusTramiteCofepris`, body);
  }

  eliminarHistorialEvento(parametro: Parametros) {
    let body = JSON.stringify(parametro);

    return this.http.post<any>(`${this.urlRegistros}eliminarHistorialEvento`, body);
  }

  eliminarNotificaciones(parametro: Parametros) {
    let body = JSON.stringify(parametro);

    return this.http.post<any>(`${this.urlRegistros}eliminarNotificaciones`, body);
  }

  eliminarCorreos(parametro: Parametros) {
    let body = JSON.stringify(parametro);

    return this.http.post<any>(`${this.urlRegistros}eliminarCorreos`, body);
  }


  loadFile(files: FileList, idEmpresa: any, idRegistro: any, idEvento: any, idHistorial: any) {
    if (files === undefined) {
      DEBUG && console.log("0 archivos enviados");
    } else {
      if (files.length > 0) {
        let file: File = files[0];
        DEBUG && console.log(file);
        let formData: FormData = new FormData();
        formData.append("file", file, file.name);
        DEBUG && console.log(formData);

        const headers = new HttpHeaders()
          .set('x-auth-token', SessionUser.getInstance().getToken());

        return this.http
          .post<MediaContent>(
            `${this.urlRegistros}subirArchivoHistorial/${idEmpresa}/${idRegistro}/${idEvento}/${idHistorial}`, formData, {headers});
      }
    }
  }

  getPdf(idEmpresa: any, idRegistro: any, idEvento: any, idHistorial: any) {
    const headers = new HttpHeaders()
      .set('Content-Type', 'text/plain; charset=utf-8')
      .set('x-auth-token', SessionUser.getInstance().getToken());

    return this.http.get<any>(`${this.urlRegistros}obtenerrArchivoHistorial/${idEmpresa}/${idRegistro}/${idEvento}/${idHistorial}`, {headers, responseType: "text" as "json"});
  }


  obtenerFeriadosOficiales() {
    let body = JSON.stringify('');

    return this.http.post<any>(`${this.urlRegistros}obtenerFeriadosOficiales`, body);
  }


  actualizarRegistro(registro: Registro) {
    let body = JSON.stringify(registro);
    DEBUG && console.log(body);

    return this.http.post<any>(`${this.urlRegistros}actualizarRegistro`, body);
  }

  getPaginatedRecords(url) {
    return this.http.get<any>(`${this.customUrl}obtenerRegistrosEmpresa?${url}`);
  }

  getManagers(parameter: Parametros) {
    const body = JSON.stringify(parameter);

    return this.http.post<any>(`${this.customUrl}getManagers`, body);
  }

  /* TODO: Utilizando la definición de spring boot
  guardarHistorialProducto(parameter: Parametros) {
    const body = JSON.stringify(parameter);
    return this.http.post<any>(`${this.customUrl}guardarHistorialProducto`, body);
  }*/

  guardarHistorialProducto(parameter: Parametros) {
    const body = JSON.stringify(parameter);

    return this.http.post<any>(`${this.urlRegistros}guardarHistorialProducto`, body);
  }

  uploadFileProduct(files: FileList, idEmpresa: any, idProducto: any, idRegistro: any, idHistorialProducto: any, fileName: any) {
    if (!files || files.length === 0) return;

    const headers = new HttpHeaders()
      .set('x-auth-token', SessionUser.getInstance().getToken());

    const file: File = files[0];
    const formData: FormData = new FormData();
    formData.append("file", file, file.name);

    return this.http
      .post<MediaContent>(
        `${this.urlRegistros}subirArchivoHistorialProducto/${idEmpresa}/${idProducto}/${idRegistro}/${idHistorialProducto}/${fileName}`,
        formData, {headers});
  }

  getHistorialProductoList(parameter: Parametros) {
    const body = JSON.stringify(parameter);

    return this.http.post<any>(`${this.urlRegistros}obtenerArchivosPorRegistro`, body);
  }

  getHistorialProductoPDF(idEmpresa: any, idProducto: any, idRegistro: any, fileName: any) {
    const headers = new HttpHeaders()
      .set('Content-Type', 'text/plain; charset=utf-8')
      .set('x-auth-token', SessionUser.getInstance().getToken());

    return this.http.get<any>(`${this.urlRegistros}obtenerHistorialProductoPDF/${idEmpresa}/${idProducto}/${idRegistro}/${fileName}`, {headers, responseType: "text" as "json"});
  }

  getRegistrosPorProducto(parameter: Parametros) {
    const body = JSON.stringify(parameter);

    return this.http.post<any>(`${this.urlRegistros}obtenerRegistrosPorProducto`, body);
  }

  deleteHistorialProducto(parameter: Parametros) {
    const body = JSON.stringify(parameter);

    return this.http.post<any>(`${this.urlRegistros}eliminarHistorialProducto`, body);
  }
}
