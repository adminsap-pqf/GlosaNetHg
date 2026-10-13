import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {SessionUser} from "../session/session.service";
import {Cliente} from "../../model/cliente.class";
import {MediaContent} from "../../interfaces/mediacontent";
/* Dev Tools */
import {DEBUG} from "../../settings/app.settings";

@Injectable()
export class EmpresaService {

  private apiURL: String = SessionUser.getInstance().getIP() + "empresa";

  constructor(private http: HttpClient) {
  }

  actualizarVigencia(client: Cliente) {
    let body = JSON.stringify(client);

    return this.http.post<any>(`${this.apiURL}/actualizarVigencia`, body);
  }

  guardarMatriz(files, rfc, idCliente) {
    if (files === undefined) {
      DEBUG && console.log("0 archivos enviados");
    } else {
      if (files.length > 0) {
        let file: File = files[0];
        DEBUG && console.log(file);
        let formData: FormData = new FormData();
        formData.append("file", file, file.name);
        DEBUG && console.log(formData);

        const headers = new HttpHeaders()
          .set('x-auth-token', SessionUser.getInstance().getToken());

        return this.http
          .post<MediaContent>(
            `${this.apiURL}/guardarMatriz/${rfc}/${idCliente}/`, formData, {headers});
      }
    }
  }

  downloadMatriz(rfc: any, idCliente: any) {
    const headers = new HttpHeaders()
      .set('Content-Type', 'text/plain; charset=utf-8')
      .set('x-auth-token', SessionUser.getInstance().getToken());

    return this.http.get<any>(`${this.apiURL}/obtenerMatriz/${rfc}/${idCliente}/`, {headers, responseType: "text" as "json"});
  }

  guardarImagenEmpresa(files, idEmpresa, idCliente) {
    if (files === undefined) {
      DEBUG && console.log("0 archivos enviados");
    } else {
      if (files.length > 0) {
        let file: File = files[0];
        DEBUG && console.log(file);
        let formData: FormData = new FormData();
        formData.append("file", file, file.name);
        DEBUG && console.log(formData);

        const headers = new HttpHeaders()
          .set('x-auth-token', SessionUser.getInstance().getToken());

        return this.http
          .post<MediaContent>(
            `${this.apiURL}/guardarImagenEmpresa/${idCliente}/${idEmpresa}/`, formData, {headers});
      }
    }
  }
}
