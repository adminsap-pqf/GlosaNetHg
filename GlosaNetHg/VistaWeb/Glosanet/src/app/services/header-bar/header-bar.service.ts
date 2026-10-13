import {Injectable} from '@angular/core';
import {SessionUser} from './../session/session.service';
import {Parametros} from '../../model/parametros.class';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {MediaContent} from "../../interfaces/mediacontent";

@Injectable()
export class HeaderBarService {
  private urlSubirArchivo: string = SessionUser.getInstance().getIP() + "descomprimir/subirArchivoDataStage";
  private urlObtenerDataStage: string = SessionUser.getInstance().getIP() + "descomprimir/obtenerDataStageRespuesta";

  constructor(private http: HttpClient) {
  }

  subirArchivoDataStage(file: File, nombreFile: String, idUsuario: any, idCliente: any, rfcActual: String) {
    let formData: FormData = new FormData();
    formData.append("file", file, file.name);

    const headers = new HttpHeaders()
      .set('x-auth-token', SessionUser.getInstance().getToken());

    return this.http.post<MediaContent>(
      `${this.urlSubirArchivo}/${nombreFile}/${idUsuario}/${idCliente}/${rfcActual}`, formData, {headers, reportProgress: true, observe: 'events'});
  }

  obtenerDataStageRespuesta(parametros: Parametros) {
    let body = JSON.stringify(parametros);

    /* mediaValue ++ */
    return this.http.post<MediaContent>(this.urlObtenerDataStage, body);
  }

}
