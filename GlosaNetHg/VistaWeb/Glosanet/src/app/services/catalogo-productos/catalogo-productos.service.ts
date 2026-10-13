import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {SessionUser} from './../session/session.service';
import {Parametros} from '../../model/parametros.class';
import {HistorialImportaciones} from '../../model/historialImportaciones.class';
import {HistorialPermisos} from '../../model/historialPermisos.class';
import {Producto} from '../../model/producto.class';
/* Dev Tools */
import {DEBUG} from "../../settings/app.settings";

@Injectable()
export class CatalogoProductosService {

  private url: string = SessionUser.getInstance().getIP() + 'historialImportaciones/';
  private url2: string = SessionUser.getInstance().getIP() + 'historialPermisos/';
  private url3: string = SessionUser.getInstance().getIP() + 'previsiones/';

  constructor(private http: HttpClient) {
  }

  obtenerPermisos(parametros: Parametros) {
    let body = JSON.stringify(parametros);
    DEBUG && console.log(body);

    return this.http.post<any>(`${this.url}obtenerPermisos`, body);
  }

  agregarHistorialImportacion(historial: HistorialImportaciones) {
    let body = JSON.stringify(historial);
    DEBUG && console.log(body);

    return this.http.post<any>(`${this.url}registrarImportacion`, body);
  }

  obtenerPermisosGenerales() {

    return this.http.post<any>(`${this.url2}obtenerPermisos`, null);
  }

  agregarHistorialPermiso(permiso: HistorialPermisos) {
    let body = JSON.stringify(permiso);
    DEBUG && console.log(body);

    return this.http.post<any>(`${this.url2}registrarPermisos`, body);
  }

  agregarProducto(Producto: Producto) {
    let body = JSON.stringify(Producto);

    return this.http.post<any>(`${this.url3}registrarProducto`, body);
  }


}
