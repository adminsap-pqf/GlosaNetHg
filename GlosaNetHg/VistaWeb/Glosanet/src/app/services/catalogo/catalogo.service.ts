import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {SessionUser} from '../session/session.service';
import * as _ from 'lodash';

@Injectable()
export class CatalogoService {

  private url = SessionUser.getInstance().getIP() + 'controller/catalogo/';

  constructor(private http: HttpClient) {
  }

  getCatalog(token: string, ids: number[]) {
    const headers = new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set('Accept', 'application/json')
      .set('x-auth-token', token);
    return this.http.get<any>(`${this.url}getcatalogs?ids=${_.toString(ids)}`, {headers});
  }
}
