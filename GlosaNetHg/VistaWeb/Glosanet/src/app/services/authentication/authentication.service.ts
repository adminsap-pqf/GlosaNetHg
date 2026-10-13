import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import {DEBUG} from "../../settings/app.settings";
import {SessionUser} from "../session/session.service";
import {AuthenticatedUser} from "../../model/authenticatedUser.class";

@Injectable()
export class AuthenticationService {

  private apiURL: string = SessionUser.getInstance().getIP() + "inicio";
  private currentUserSubject: BehaviorSubject<AuthenticatedUser>;
  public currentUser: Observable<AuthenticatedUser>;

  constructor(private http: HttpClient) {
    this.currentUserSubject = (typeof (Storage) !== "undefined")
      ? new BehaviorSubject<AuthenticatedUser>(JSON.parse(localStorage.getItem('currentUser')))
      : new BehaviorSubject<AuthenticatedUser>(null);
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): AuthenticatedUser {
    return this.currentUserSubject.value;
  }

  public set currentUserValue(userData) {
    // Establecemos información de usuario autenticado como currentUserSubject
    this.currentUserSubject.next(userData);
  }

  login(username: String, password: String) {
    DEBUG && console.log("@login: inicio");
    const sha1 = require('sha1');
    const hashedPassword = sha1(password);
    DEBUG && console.log("@login: username =>" + username);
    DEBUG && console.log("@login: hashedPassword =>" + hashedPassword);
    const body = {
      'usuario': username,
      'password': hashedPassword
    };
    DEBUG && console.log("@login: body =>" + body);
    const headers = new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set('Accept', 'application/json');
    return this.http.post<any>(this.apiURL + '/authenticate', body, {headers});
  }

  logout() {
    DEBUG && console.log("@logout: inicio");
    // remove user from local storage and set current user to null
    if (typeof (Storage) !== "undefined") {
      localStorage.removeItem('currentUser');
    }
    this.currentUserSubject.next(null);
  }

  packUserSessionData(userData: { loginData, userInfo, adminData, catalogVucem }) {
    DEBUG && console.log("@packUserSessionData: inicio");

    // Establecemos informacion de usuario autenticado en singleton de sessionUser
    SessionUser.getInstance().setToken(userData.loginData.token);
    SessionUser.getInstance().setUserName(userData.userInfo.usuario.toString());
    SessionUser.getInstance().setUserID(userData.loginData.userId);
    SessionUser.getInstance().setNombreCompleto(
      `${userData.userInfo.usuario.nombre} ${userData.userInfo.usuario.apellido}`);
    SessionUser.getInstance().setIdClient(userData.userInfo.cliente.idCliente);
    SessionUser.getInstance().setRfc(userData.userInfo.cliente.empresas[0].rfc);
    SessionUser.getInstance().setUsuario(userData.userInfo.usuario);
    SessionUser.getInstance().setCliente(userData.userInfo.cliente);
    SessionUser.getInstance().setDof(userData.userInfo.dof);
    SessionUser.getInstance().setidEmpresaActual(userData.userInfo.usuario.cliente.idEmpresaActual);
    SessionUser.getInstance().setNumDataStage(userData.userInfo.cliente.empresas[0].numDataStage);
    SessionUser.getInstance().setNumDataStageAux(userData.userInfo.cliente.empresas[0].numDataStage);
    SessionUser.getInstance().setAdministrador(userData.adminData);
    SessionUser.getInstance().setEndTime(new Date(userData.loginData.endTime));
    SessionUser.getInstance().setCatalogVucem(userData.catalogVucem);
  }
}
