import {Subject} from 'rxjs';
import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {SessionUser} from './../session/session.service';
import {Parametros} from '../../model/parametros.class';
import {Usuario} from '../../model/usuario.class';
/* Dev Tools */
import {UserInfo} from "../../interfaces/userinfo";

@Injectable()
export class UserService {
  private apiUser: String = SessionUser.getInstance().getIP() + "usuario";

  public message = new Subject<any>();
  public sinDatos = new Subject<any>();

  constructor(private http: HttpClient) {
  }

  obtenerUsuarioActual() {
    return this.http.post<UserInfo>(`${this.apiUser}/obtenerUsuarioActual`, {});
  }

  obtenerUsuarioActualConToken(token: string) {
    const headers = new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set('Accept', 'application/json')
      .set('x-auth-token', token);
    return this.http.post<UserInfo>(`${this.apiUser}/obtenerUsuarioActual`, {}, {headers});
  }

  buscarUsuariosPorEmpresa(parametro: Parametros) {
    const body = JSON.stringify(parametro);
    return this.http.post<any>(`${this.apiUser}/buscarUsuariosPorEmpresa`, body);
  }

  buscarUsuariosPorEmpresaConToken(token: string, parametro: Parametros) {
    const headers = new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set('Accept', 'application/json')
      .set('x-auth-token', token);
    const body = JSON.stringify(parametro);
    return this.http.post<any>(`${this.apiUser}/buscarUsuariosPorEmpresa`, body, {headers});
  }

  buscarUsuarios(parametros: Parametros) {
    let body = JSON.stringify(parametros);

    return this.http.post<any>(`${this.apiUser}/buscarUsuarios`, body);
  }

  actualizarUsuarios(usuario: Usuario) {
    let body = JSON.stringify(usuario);

    return this.http.post<any>(`${this.apiUser}/actualizarUsuario`, body);
  }

  actualizarPermisos(respuesta) {
    let body = JSON.stringify(respuesta);

    return this.http.post<any>(`${this.apiUser}/actualizarPermisos`, body);
  }


  downloadLogo(idEmpresa: any, idCliente: any) {
    const headers = new HttpHeaders()
      .set('Content-Type', 'text/plain; charset=utf-8')
      .set('x-auth-token', SessionUser.getInstance().getToken());

    return this.http.get<any>(`${this.apiUser}/obtenerLogo/${idCliente}/${idEmpresa}/`, {headers, responseType: "text" as "json"});
  }


  refreshLogoEmpresa(value: any) {
    this.message.next(value);
  }


  noDisponible(value: any) {
    this.sinDatos.next(value);
  }

}
