import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {SessionUser} from './../session/session.service';
import {Parametros} from '../../model/parametros.class';
import {MediaContent} from '../../interfaces/mediacontent';

@Injectable()
export class LibreriaDatastageService {
  private urlnavegacionDS: string = SessionUser.getInstance().getIP() + 'libreriaDataStage/navegacionDataStage';
  private urlgenerarZip: string = SessionUser.getInstance().getIP() + 'libreriaDataStage/generarZip';
  private urlObtenerArchivo: string = SessionUser.getInstance().getIP() + 'libreriaDataStage/obtenerArchivo';

  constructor(private http: HttpClient) {
  }

  navegacionDataStage(parametros: Parametros) {
    const body = JSON.stringify(parametros);
    return this.http.post<any>(this.urlnavegacionDS, body);
  }

  generarZip(parametros: Parametros) {
    const body = JSON.stringify(parametros);

    return this.http.post<MediaContent>(this.urlgenerarZip, body);
  }

  obtenerArchivo(parametros: Parametros) {
    //let body = JSON.stringify(parametros);

    const headers = new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set('x-auth-token', SessionUser.getInstance().getToken());

    const body = {...parametros};

    return this.http.post<any>(this.urlObtenerArchivo, body, {headers, responseType: "text" as "json"});
    //return this.http.post<any>(this.urlObtenerArchivo, body);
  }
}
