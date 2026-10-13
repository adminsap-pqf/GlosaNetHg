import {Injectable} from '@angular/core';
import {Parametros} from '../../model/parametros.class';
import {Previsiones} from '../../model/previsiones.class';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {SessionUser} from '../session/session.service';
import {Prevision} from '../../model/prevision.class';
import {Producto} from '../../model/producto.class';
import {MediaContent} from "../../interfaces/mediacontent";
/* Dev Tools */
import {DEBUG} from "../../settings/app.settings";
import {IPrevision} from "../../interfaces/prevision";

@Injectable()
export class PrevisionesService {

  private url: string = SessionUser.getInstance().getIP() + 'previsiones/';

  constructor(private http: HttpClient) {
  }

  obtenerPrevisiones(previsiones: Previsiones) {
    let body = JSON.stringify(previsiones);

    return this.http.post<any>(`${this.url}obtenerPrevisiones`, body);
  }

  obtenerProductos(parametro: Parametros) {
    let body = JSON.stringify(parametro);

    return this.http.post<any>(`${this.url}obtenerProductos`, body);
  }

  guardarPrevision(previsiones: Previsiones) {
    let body = JSON.stringify(previsiones);

    return this.http.post<any>(`${this.url}guardarPrevision`, body);
  }


  agregarPrevision(previsiones: Prevision) {
    let body = JSON.stringify(previsiones);
    DEBUG && console.log(body);

    return this.http.post<any>(`${this.url}crearPrevision`, body);
  }

  getPrevision(parametro: Parametros) {
    let body = JSON.stringify(parametro);
    DEBUG && console.log(body);

    return this.http.post<IPrevision>(`${this.url}obtenerPrevision`, body);
  }

  obtenerProductosPorPrevision(parametro: Parametros) {
    let body = JSON.stringify(parametro);

    return this.http.post<any>(`${this.url}obtenerProductosPorPrevision`, body);
  }


  eliminarNotificaciones(parametro: Parametros) {
    let body = JSON.stringify(parametro);

    return this.http.post<any>(`${this.url}eliminarNotificaciones`, body);
  }

  eliminarCorreos(parametro: Parametros) {
    let body = JSON.stringify(parametro);

    return this.http.post<any>(`${this.url}eliminarCorreos`, body);
  }


  guardarProducto(lstProductos: Array<Producto>) {
    let body = JSON.stringify(lstProductos);

    return this.http.post<any>(`${this.url}guardarListadoProducto`, body);
  }


  eliminarPrevisionProductos(parametro: Parametros) {
    let body = JSON.stringify(parametro);

    return this.http.post<any>(`${this.url}eliminarPrevisionProductos`, body);
  }


  guardarHistorialEvento(parametro: Parametros) {
    let body = JSON.stringify(parametro);
    DEBUG && console.log(body);

    return this.http.post<any>(`${this.url}guardarHistorialEvento`, body);
  }


  obtenerHistorialEvento(parametro: Parametros) {
    let body = JSON.stringify(parametro);

    return this.http.post<any>(`${this.url}obtenerHistorialEvento`, body);
  }


  eliminarHistorialEvento(parametro: Parametros) {
    let body = JSON.stringify(parametro);

    return this.http.post<any>(`${this.url}eliminarHistorialEvento`, body);
  }


  loadFile(files: FileList, idEmpresa: any, idPrevision: any, idEvento: any, idHistorial: any) {
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
            `${this.url}subirArchivoHistorial/${idEmpresa}/${idPrevision}/${idEvento}/${idHistorial}`, formData, {headers});
      }
    }
  }

  getPdf(idEmpresa: any, idPrevision: any, idEvento: any, idHistorial: any) {
    const headers = new HttpHeaders()
      .set('Content-Type', 'text/plain; charset=utf-8')
      .set('x-auth-token', SessionUser.getInstance().getToken());

    return this.http
      .get<any>(`${this.url}obtenerrArchivoHistorial/${idEmpresa}/${idPrevision}/${idEvento}/${idHistorial}`, {headers, responseType: "text" as "json"});
  }


  realizarEvento(parametro: Parametros) {
    let body = JSON.stringify(parametro);

    return this.http.post<any>(`${this.url}realizarEvento`, body);
  }


  actualizarFechaCita(parametro: Parametros) {
    let body = JSON.stringify(parametro);
    DEBUG && console.log(body);

    return this.http.post<any>(`${this.url}actualizarFechaCita`, body);
  }


  actualizarCitaCofeprisEvento(parametro: Parametros) {
    let body = JSON.stringify(parametro);
    DEBUG && console.log(body);

    return this.http.post<any>(`${this.url}actualizarCitaCofeprisEvento`, body);
  }


  getPrevisionActual(parametro: Parametros) {
    let body = JSON.stringify(parametro);
    DEBUG && console.log(body);

    return this.http.post<MediaContent>(`${this.url}obtenerPrevisionActual`, body);
  }


  obtenerProductosCompletosPrevisionActual(parametro: Parametros) {
    let body = JSON.stringify(parametro);
    DEBUG && console.log(body);

    return this.http.post<any>(`${this.url}obtenerProductosCompletosPrevisionActual`, body);
  }


  guardarElementoMonitoreoPrevision(parametro: Parametros) {
    let body = JSON.stringify(parametro);

    return this.http.post<any>(`${this.url}guardarElementoMonitoreoPrevision`, body);
  }


  obtenerProductosLibreriaPrevision(parametro: Parametros) {
    let body = JSON.stringify(parametro);

    return this.http.post<any>(`${this.url}obtenerProductosLibreriaPrevision`, body);
  }


}
