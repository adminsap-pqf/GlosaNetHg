import {Injectable} from "@angular/core";
import {HttpClient} from '@angular/common/http';
import {SessionUser} from "../session/session.service";
import {Cliente} from "../../model/cliente.class";

@Injectable()
export class ClienteService {

  private apiURL: String = SessionUser.getInstance().getIP() + "cliente";

  constructor(private http: HttpClient) {
  }

  actualizarCliente(cliente: Cliente) {
    let body = JSON.stringify(cliente);

    return this.http.post<any>(`${this.apiURL}/actualizarCliente`, body);
  }
}
