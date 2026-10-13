import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {SessionUser} from './../session/session.service';
import {Parametros} from '../../model/parametros.class';
import {AgenteAduanal} from '../../model/agenteAduanal.class';
import {AgenciaAduanal} from '../../model/agenciaAduanal.class';
/* Dev Tools */
import {DEBUG} from "../../settings/app.settings";

@Injectable()
export class AgenteAduanalHttpService {
  private url: string = SessionUser.getInstance().getIP() + 'agenteAduanal/';

  constructor(private http: HttpClient) {
  }

  obtenerAgentesYAgencias(parametros: Parametros) {
    let body = JSON.stringify(parametros);
    DEBUG && console.log(body);

    return this.http.post<any>(`${this.url}obtenerAgentesYAgencias`, body);
  }

  actualizarAgenteAduanal(agente: AgenteAduanal) {
    let body = JSON.stringify(agente);
    DEBUG && console.log(body);

    return this.http.post<any>(`${this.url}actualizarAgenteAduanal`, body);
  }

  agregarAgenciaAduanal(agencia: AgenciaAduanal) {
    let body = JSON.stringify(agencia);
    DEBUG && console.log(body);

    return this.http.post<any>(`${this.url}agregarAgenciaAduanal`, body);
  }


  actualizarAgenciaAduanal(agencia: AgenciaAduanal) {
    let body = JSON.stringify(agencia);
    DEBUG && console.log(body);

    return this.http.post<any>(`${this.url}actualizarAgenciaAduanal`, body);
  }
}
