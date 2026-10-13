import {Injectable} from '@angular/core';
import {SessionUser} from '../session/session.service';
import {Parametros} from '../../model/parametros.class';
import {Forecast} from '../../model/forecast';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class ForecastService {

  private url: string = SessionUser.getInstance().getIP() + 'previsiones/';

  constructor(private http: HttpClient) {
  }

  getForecast(forecast: Forecast) {
    let body = JSON.stringify(forecast);

    return this.http.post<any>(`${this.url}obtenerPrevisiones`, body);
  }

  getProducts(parametro: Parametros) {
    let body = JSON.stringify(parametro);

    return this.http.post<any>(`${this.url}obtenerProductos`, body);
  }

  saveForecast(forecast: Forecast) {
    let body = JSON.stringify(forecast);

    return this.http.post<any>(`${this.url}guardarPrevision`, body);
  }


}
