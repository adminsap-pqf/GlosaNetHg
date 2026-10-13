import {Usuario} from '../../model/usuario.class';
import {Cliente} from '../../model/cliente.class';
import {VucemTokenClass} from '../../model/vucem-token.class';
import {Catalog} from "../../model/catalog.class";

export class SessionUser {

  private static _instance: SessionUser = new SessionUser();

  /* --------- Token ---------- */
  private token = '';

  /* --------------- Authentication ----------- */
  private userName = 'Usuario Registrado';
  private idUser = 0;
  private idCliente = 0;
  private rfc = '';
  private usuario: Usuario;
  private cliente: Cliente;
  private administrador: Array<Usuario>;
  private dof: any;
  private descargando: Boolean = false;
  private listDescargas: any[] = new Array();
  private openDescargas: any = {visible: true};
  private origenDescarga: string;
  private idEmpresaActual: number;
  private numDataStage: number;
  private numDataStageAux: number;
  private nombreCompleto: string;
  private loading = false;
  private confirm = false;
  private messageOk = '';
  private diasFeriados: any[];
  private messageFailed = '';
  private vucemToken: VucemTokenClass;
  private catalogVucem: Catalog;

  /* ------------ Fin de sesión ------------- */
  private endTime: Date = null;

  // LOCAL
  // private ip: string = "http://localhost:8080/GlosaNet/";
  // PRUEBAS
 // private ip = 'http://172.24.32.19:8080/GlosaNet/';
 // private ip = 'http://192.168.100.6:8181/GlosaNet/';
  //private ip = 'http://172.24.42.46:8181/GlosaNet/';
  // PRODUCCION
  private ip: string = "https://glosanet.ryndem.mx/GlosaNet/";

  constructor() {
    if (SessionUser._instance) {
      throw new Error('SessionUser is a singleton class and cannot be created!');
    }

    SessionUser._instance = this;
  }

  public static getInstance(): SessionUser {
    return SessionUser._instance;
  }

  public setToken(token: any) {
    this.token = token;
  }

  public getToken() {
    return this.token;
  }

  public getIP() {
    return this.ip;
  }

  public getUserName() {
    return this.userName;
  }

  public setUserName(user: string) {
    this.userName = user;
  }

  public getUserID() {
    return this.idUser;
  }

  public setUserID(id: number) {
    this.idUser = id;
  }

  public getIdClient() {
    return this.idCliente;
  }

  public setIdClient(idClient: number) {
    this.idCliente = idClient;
  }

  public getRfc() {
    return this.rfc;
  }

  public setRfc(rfc: string) {
    this.rfc = rfc;
  }

  public getUsuario() {
    return this.usuario;
  }

  public setUsuario(usuario: Usuario) {
    this.usuario = usuario;
  }

  public getCliente() {
    return this.cliente;
  }

  public setCliente(cliente: Cliente) {
    this.cliente = cliente;
  }

  public getDof() {
    return this.dof;
  }

  public setDof(dof: any) {
    this.dof = dof;
  }

  public getDescargando() {
    return this.descargando;
  }

  public setDescargando(descargando: Boolean) {
    this.descargando = descargando;
  }

  public getListDescargas() {
    return this.listDescargas;
  }

  public setListDescargas(listDescargas: any[]) {
    this.listDescargas = listDescargas;
  }

  public getOpenDescargas() {
    return this.openDescargas;
  }

  public setOpenDescargas(openDescargas: any) {
    this.openDescargas = openDescargas;
  }

  public getOriegnDescarga() {
    return this.origenDescarga;
  }

  public setOrigenDescarga(origenDescarga: string) {
    this.origenDescarga = origenDescarga;
  }

  public getidEmpresaActual() {
    return this.idEmpresaActual;
  }

  public setidEmpresaActual(idEmpresaActual: number) {
    this.idEmpresaActual = idEmpresaActual;
  }

  public getLoading() {
    return this.loading;
  }

  public setLoading(loading: boolean) {
    this.loading = loading;
  }

  public getConfirm() {
    return this.confirm;
  }

  public setConfirm(confirm: boolean) {
    this.confirm = confirm;
  }

  public setMessageOk(messageOk: any) {
    this.messageOk = messageOk;
  }

  public getMessageOk() {
    return this.messageOk;
  }

  public setNombreCompleto(nombreCompleto) {
    this.nombreCompleto = nombreCompleto;
  }

  public getNombreCompleto() {
    return this.nombreCompleto;
  }

  public setNumDataStage(numDataStage) {
    this.numDataStage = numDataStage;
  }

  public getNumDataStage() {
    return this.numDataStage;
  }

  public setNumDataStageAux(numDataStageAux) {
    this.numDataStageAux = numDataStageAux;
  }

  public getNumDataStageAux() {
    return this.numDataStageAux;
  }

  public setDiasFeriados(diasFeriados) {
    this.diasFeriados = Object.assign([], diasFeriados);
  }

  public getDiasFeriados() {
    return this.diasFeriados;
  }

  public setAdministrador(administrador) {
    this.administrador = Object.assign([], administrador);
  }

  public getAdministrador() {
    return this.administrador ;
  }

  public setMessageFailed(detail: string = 'al guardar datos', error: string = 'Ocurrió un error', ) {
    this.messageFailed = `¡${error} <span style="color: #3B8EDE; font-size: 21px; font-weight: 400;">${detail}!</span>`;
  }

  public getMessageFailed() {
    return this.messageFailed;
  }

  public setEndTime(date: Date) {
    this.endTime = date;
  }

  public getEndTime() {
    return this.endTime;
  }

  public getVucemToken() {
    return this.vucemToken;
  }

  public setVucemToken(vucemToken: VucemTokenClass) {
    this.vucemToken = vucemToken;
  }

  public getCatalogVucem() {
    return this.catalogVucem;
  }

  public setCatalogVucem(catalogVucem: Catalog) {
    this.catalogVucem = catalogVucem;
  }
}
