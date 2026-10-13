import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { SessionUser } from '../session/session.service';
import { Parametros } from "../../model/parametros.class";


@Injectable({
  providedIn: 'root'
})
export class ProductsService {
  private api = SessionUser.getInstance().getIP() + 'controller/registros/';

  constructor(private http: HttpClient) { }

  fetchProducts(parametro: Parametros) {
    let body = JSON.stringify(parametro);

    return this.http.post<any>(`${this.api}obtenerProductosDataStagePorEmpresa`, body);
  }


}
