import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {SessionUser} from '../session/session.service';

@Injectable()
export class VucemTokenService {

    private url = SessionUser.getInstance().getIP() + 'controller/vucem_token/';

    constructor(private http: HttpClient) {
    }

    addToken(term: any) {
        return this.http.post<any>(`${this.url}addToken`,
            {token: term});
    }

    getToken(idClient: number) {
        return this.http.post<any>(`${this.url}getToken`,
            {idCliente: idClient});
    }

    updateToken(term: any) {
        return this.http.post<any>(`${this.url}updateToken`,
            {token: term});
    }

    deleteToken(idToken: number) {
        return this.http.post<any>(`${this.url}deleteToken`,
            {idToken: idToken});
    }
}
