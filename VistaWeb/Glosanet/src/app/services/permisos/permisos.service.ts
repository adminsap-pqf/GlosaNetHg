import {throwError as observableThrowError} from 'rxjs';
import {Injectable} from '@angular/core';
import {SessionUser} from './../session/session.service';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Parametros} from '../../model/parametros.class';
import {Permiso} from '../../model/permiso.class';
import {MediaContent} from "../../interfaces/mediacontent";
/* Dev Tools */
import {DEBUG} from "../../settings/app.settings";
import {catchError} from "rxjs/operators";

@Injectable()
export class PermisosService {

  private urlPrevisiones = SessionUser.getInstance().getIP() + "previsiones/";
  private urlPermisos = SessionUser.getInstance().getIP() + "historialPermisos/";

  constructor(private http: HttpClient) {
  }


  obtenerProductosPrevision(parametros: Parametros) {
    let body = JSON.stringify(parametros);

    return this.http.post<any>(`${this.urlPrevisiones}obtenerProductosGenerales`, body);
  }


  obtenerPermisosEmpresa(parametros: Parametros) {
    let body = JSON.stringify(parametros);

    return this.http.post<any>(`${this.urlPermisos}obtenerPermisosEmpresa`, body);
  }


  crearPermiso(permiso: Permiso) {
    let body = JSON.stringify(permiso);
    DEBUG && console.log(body);

    return this.http.post<Number>(`${this.urlPermisos}crearPermiso`, body);
  }


  obtenerPermiso(parametro: Parametros) {
    let body = JSON.stringify(parametro);
    DEBUG && console.log(body);

    return this.http.post<any>(`${this.urlPermisos}obtenerPermiso`, body);
  }

  eliminarNotificaciones(parametro: Parametros) {
    let body = JSON.stringify(parametro);

    return this.http.post<any>(`${this.urlPermisos}eliminarNotificaciones`, body);
  }

  eliminarCorreos(parametro: Parametros) {
    let body = JSON.stringify(parametro);

    return this.http.post<any>(`${this.urlPermisos}eliminarCorreos`, body);
  }


  guardarHistorialEvento(parametro: Parametros) {
    let body = JSON.stringify(parametro);
    DEBUG && console.log(body);

    return this.http.post<any>(`${this.urlPermisos}guardarHistorialEvento`, body);
  }


  obtenerHistorialEvento(parametro: Parametros) {
    let body = JSON.stringify(parametro);

    return this.http.post<any>(`${this.urlPermisos}obtenerHistorialEvento`, body);
  }


  eliminarHistorialEvento(parametro: Parametros) {
    let body = JSON.stringify(parametro);

    return this.http.post<any>(`${this.urlPermisos}eliminarHistorialEvento`, body);
  }


  loadFile(files: FileList, idEmpresa: any, idPermiso: any, idEvento: any, idHistorial: any) {
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
            `${this.urlPermisos}subirArchivoHistorial/${idEmpresa}/${idPermiso}/${idEvento}/${idHistorial}`, formData, {headers})
          .pipe(catchError((error: any) =>
            observableThrowError(error.json().error || "Server error")
          ));
      }
    }
  }

  getPdf(idEmpresa: any, idPermiso: any, idEvento: any, idHistorial: any) {
    const headers = new HttpHeaders()
      .set('Content-Type', 'text/plain; charset=utf-8')
      .set('x-auth-token', SessionUser.getInstance().getToken());

    return this.http
      .get<any>(`${this.urlPermisos}obtenerrArchivoHistorial/${idEmpresa}/${idPermiso}/${idEvento}/${idHistorial}`, {headers, responseType: "text" as "json"})
      .pipe(catchError((error: any) => observableThrowError(error.json().error || "Server error")));
  }


  realizarEvento(parametro: Parametros) {
    let body = JSON.stringify(parametro);

    return this.http.post<any>(`${this.urlPermisos}realizarEvento`, body);
  }


  guardarElementoMonitoreoPermiso(parametro: Parametros) {
    let body = JSON.stringify(parametro);

    return this.http.post<any>(`${this.urlPermisos}guardarElementoMonitoreoPermiso`, body);
  }


  actualizarFechaCita(parametro: Parametros) {
    let body = JSON.stringify(parametro);
    DEBUG && console.log(body);

    return this.http.post<any>(`${this.urlPermisos}actualizarFechaCita`, body);
  }


  actualizarCitaCofeprisEvento(parametro: Parametros) {
    let body = JSON.stringify(parametro);
    DEBUG && console.log(body);

    return this.http.post<any>(`${this.urlPermisos}actualizarCitaCofeprisEvento`, body);
  }


  obtenerPermisosActual(parametro: Parametros) {
    let body = JSON.stringify(parametro);

    return this.http.post<any>(`${this.urlPermisos}obtenerPermisosActual`, body);
  }


  obtenerImportaciones(parametro: Parametros) {
    let body = JSON.stringify(parametro);

    return this.http.post<any>(`${this.urlPermisos}obtenerImportaciones`, body);
  }
}
