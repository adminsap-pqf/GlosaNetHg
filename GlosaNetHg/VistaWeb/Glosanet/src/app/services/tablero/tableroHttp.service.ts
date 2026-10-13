import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {SessionUser} from './../session/session.service';
import {Parametros} from '../../model/parametros.class';
/* Dev Tools */
import {DEBUG} from "../../settings/app.settings";
import {MediaContent} from "../../interfaces/mediacontent";

@Injectable()
export class TableroHttpService {
  private url: string = SessionUser.getInstance().getIP() + 'dashBoard/';

  constructor(private http: HttpClient) {
  }

  t501_obtenerOperacionesDelPeriodo(parametros: Parametros) {
    let body = JSON.stringify(parametros);
    DEBUG && console.log(body);

    return this.http.post<any>(`${this.url}t501_obtenerOperacionesDelPeriodo`, body);
  }

  t501_obtenerTipoCambioPeriodo(parametros: Parametros) {
    let body = JSON.stringify(parametros);
    DEBUG && console.log(body);

    return this.http.post<any>(`${this.url}t501_obtenerTipoCambioPeriodo`, body);
  }

  t501_obtenerOperacionesPorAduana(parametros: Parametros) {
    let body = JSON.stringify(parametros);
    DEBUG && console.log(body);

    return this.http.post<any>(`${this.url}t501_obtenerOperacionesPorAduana`, body);
  }

  t501_obtenerOperacionesPorMedioTransporteArribo(parametros: Parametros) {
    let body = JSON.stringify(parametros);
    DEBUG && console.log(body);

    return this.http.post<any>(`${this.url}t501_obtenerOperacionesPorMedioTransporteArribo`, body);
  }

  t501_obtenerPesoBrutoPorMedioTransporteArribo(parametros: Parametros) {
    let body = JSON.stringify(parametros);
    DEBUG && console.log(body);

    return this.http.post<any>(`${this.url}t501_obtenerPesoBrutoPorMedioTransporteArribo`, body);
  }

  t501_obtenerOperacionesPorTipoPedimento(parametros: Parametros) {
    let body = JSON.stringify(parametros);
    DEBUG && console.log(body);

    return this.http.post<any>(`${this.url}t501_obtenerOperacionesPorTipoPedimento`, body);
  }

  t501_obtenerOperacionesPorClavePedimento(parametros: Parametros) {
    let body = JSON.stringify(parametros);
    DEBUG && console.log(body);

    return this.http.post<any>(`${this.url}t501_obtenerOperacionesPorClavePedimento`, body);
  }

  t501_ObtenerOperacionesMedioTransporteSalida(parametros: Parametros) {
    let body = JSON.stringify(parametros);
    DEBUG && console.log(body);

    return this.http.post<any>(`${this.url}t501_ObtenerOperacionesMedioTransporteSalida`, body);
  }


  t501_ObtenerOperacionesMedioTransporteArriboSalida(parametros: Parametros) {
    let body = JSON.stringify(parametros);
    DEBUG && console.log(body);

    return this.http.post<any>(`${this.url}t501_ObtenerOperacionesMedioTransporteArriboSalida`, body);
  }

  t501_ObtenerOperacionesPorIncrementablesYDeducibles(parametros: Parametros) {
    let body = JSON.stringify(parametros);
    DEBUG && console.log(body);

    return this.http.post<any>(`${this.url}t501_ObtenerOperacionesPorIncrementablesYDeducibles`, body);
  }

  t501_ObtenerOperacionesDestinoMercancia(parametros: Parametros) {
    let body = JSON.stringify(parametros);
    DEBUG && console.log(body);

    return this.http.post<any>(`${this.url}t501_ObtenerOperacionesDestinoMercancia`, body);
  }

  t501_ObtenerOperacionesPorPatente(parametros: Parametros) {
    let body = JSON.stringify(parametros);
    DEBUG && console.log(body);

    return this.http.post<any>(`${this.url}t501_ObtenerOperacionesPorPatente`, body);
  }

  t501_ObtenerOperacionesPorTipoPedimento(parametros: Parametros) {
    let body = JSON.stringify(parametros);
    DEBUG && console.log(body);

    return this.http.post<any>(`${this.url}t501_ObtenerOperacionesPorTipoPedimento`, body);
  }


  t501_obtenerDatos(parametros: Parametros) {
    let body = JSON.stringify(parametros);
    DEBUG && console.log(body);

    return this.http.post<any>(`${this.url}t501_obtenerDatos`, body);
  }

  t502_obtenerDatos(parametros: Parametros) {
    let body = JSON.stringify(parametros);
    DEBUG && console.log(body);

    return this.http.post<any>(`${this.url}t502_obtenerDatos`, body);
  }

  t503_obtenerDatos(parametros: Parametros) {
    let body = JSON.stringify(parametros);
    DEBUG && console.log(body);

    return this.http.post<any>(`${this.url}t503_obtenerDatos`, body);
  }

  t504_obtenerDatos(parametros: Parametros) {
    let body = JSON.stringify(parametros);
    DEBUG && console.log(body);

    return this.http.post<any>(`${this.url}t504_obtenerDatos`, body);
  }

  t505_obtenerDatos(parametros: Parametros) {
    let body = JSON.stringify(parametros);
    DEBUG && console.log(body);

    return this.http.post<any>(`${this.url}t505_obtenerDatos`, body);
  }

  t506_obtenerDatos(parametros: Parametros) {
    let body = JSON.stringify(parametros);
    DEBUG && console.log(body);

    return this.http.post<any>(`${this.url}t506_obtenerDatos`, body);
  }

  t507_obtenerDatos(parametros: Parametros) {
    let body = JSON.stringify(parametros);
    DEBUG && console.log(body);

    return this.http.post<any>(`${this.url}t507_obtenerDatos`, body);
  }

  t509_obtenerDatos(parametros: Parametros) {
    let body = JSON.stringify(parametros);
    DEBUG && console.log(body);

    return this.http.post<any>(`${this.url}t509_obtenerDatos`, body);
  }

  t510_obtenerDatos(parametros: Parametros) {
    let body = JSON.stringify(parametros);
    DEBUG && console.log(body);

    return this.http.post<any>(`${this.url}t510_obtenerDatos`, body);
  }

  t701_obtenerDatos(parametros: Parametros) {
    let body = JSON.stringify(parametros);
    DEBUG && console.log(body);

    return this.http.post<any>(`${this.url}t701_obtenerDatos`, body);
  }

  t702_obtenerDatos(parametros: Parametros) {
    let body = JSON.stringify(parametros);
    DEBUG && console.log(body);

    return this.http.post<any>(`${this.url}t702_obtenerDatos`, body);
  }

  t554_obtenerDatos(parametros: Parametros) {
    let body = JSON.stringify(parametros);
    DEBUG && console.log(body);

    return this.http.post<any>(`${this.url}t554_obtenerDatos`, body);
  }

  t556_obtenerDatos(parametros: Parametros) {
    let body = JSON.stringify(parametros);
    DEBUG && console.log(body);

    return this.http.post<any>(`${this.url}t556_obtenerDatos`, body);
  }

  t557_obtenerDatos(parametros: Parametros) {
    let body = JSON.stringify(parametros);
    DEBUG && console.log(body);

    return this.http.post<any>(`${this.url}t557_obtenerDatos`, body);
  }


  generarReporte(mapa) {
    const headers = new HttpHeaders()
      .set('Content-Type', 'text/plain; charset=utf-8')
      .set('x-auth-token', SessionUser.getInstance().getToken());

    return this.http.post<any>(`${this.url}generarReporte`, mapa, {headers, responseType: "text" as "json"});
  }


  getIconosTransportes(arrayClaves: any[]) {
    let arrayAux = [];
    for (let item of arrayClaves) {
      switch (item) {
        case 1:
          /* maritimo */
          arrayAux.push(" \ue907");
          break;
        case 2:
          /* ferroviario doble */
          arrayAux.push(" \ue905");
          break;
        case 3:
          /* carretero ferroviario */
          arrayAux.push(" \ue906");
          break;
        case 4:
          /* aereo */
          arrayAux.push(" \ue900");
          break;
        case 5:
          /* postal */
          arrayAux.push(" \ue90a");
          break;
        case 6:
          /* ferroviario */
          arrayAux.push(" \ue907");
          break;
        case 7:
          /* carretero */
          arrayAux.push(" \ue903");
          break;
        case 8:
          /* tuberia */
          arrayAux.push(" \ue90b");
          break;
        case 10:
          /* cables */
          arrayAux.push(" \ue901");
          break;
        case 11:
          /* ductos */
          arrayAux.push(" \ue904");
          break;
        case 12:
          /* peatonal */
          arrayAux.push(" \ue909");
          break;
        case 98:
          /* no se declara medio de transporte */
          arrayAux.push(" \ue908");
          break;
        case 99:
          /* otros */
          arrayAux.push(" \ue908");
          break;
        default:
          arrayAux.push(" \ue908");
          break;
      }
    }
    return arrayAux;
  }

  getNombreTransportes(arrayClaves: any[]) {
    let arrayAux = [];
    for (let item of arrayClaves) {
      switch (item) {
        case 1:
          /* maritimo */
          arrayAux.push("MARÍTIMO");
          break;
        case 2:
          /* ferroviario doble */
          arrayAux.push("FERROVIARIO DE DOBLE ESTIBA");
          break;
        case 3:
          /* carretero ferroviario */
          arrayAux.push("CARRETERO - FERROVIARIO");
          break;
        case 4:
          /* aereo */
          arrayAux.push("AÉREO");
          break;
        case 5:
          /* postal */
          arrayAux.push("POSTAL");
          break;
        case 6:
          /* ferroviario */
          arrayAux.push("FERROVIARIO");
          break;
        case 7:
          /* carretero */
          arrayAux.push("CARRETERO");
          break;
        case 8:
          /* tuberia */
          arrayAux.push("TUBERÍA");
          break;
        case 10:
          /* cables */
          arrayAux.push("CABLES");
          break;
        case 11:
          /* ductos */
          arrayAux.push("DUCTOS");
          break;
        case 12:
          /* peatonal */
          arrayAux.push("PEATONAL");
          break;
        case 98:
          /* no se declara medio de transporte */
          arrayAux.push("NO SE DECLARA MEDIO DE TRANSPORTE POR NO HABER");
          break;
        case 99:
          /* otros */
          arrayAux.push("OTROS");
          break;
        default:
          arrayAux.push("OTROS");
          break;
      }
    }
    return arrayAux;
  }

}
