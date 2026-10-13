import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { SessionUser } from '../session/session.service';
import _ from 'lodash';

@Injectable()
export class DatastageService {

    private url = SessionUser.getInstance().getIP() + 'controller/datastage/';

    constructor(private http: HttpClient) { }

    getPedimentos(path) {
        return this.http.get<any>(`${this.url}pedimentos?${path}`);
    }

    getDetails(params: any) {
        if (params.option === 1) {
            return this.getFacturas(params.path);
        } else if (params.option === 2) {
            return this.getE_Doc(params.path);
        } else if (params.option === 3) {
            return this.getPartidas(params.path);
        }
    }

    getFacturas(path) {
        return this.http.get<any>(`${this.url}facturas?${path}`);
    }

    getE_Doc(path) {
        return this.http.get<any>(`${this.url}eDoc?${path}`);
    }

    getPartidas(path) {
        return this.http.get<any>(`${this.url}partidas?${path}`);
    }

    requestFactura(param) {
        const body = JSON.stringify(param);
        return this.http.post<any>(`${this.url}request-factura`, body);
    }

    requestEdoc(param) {
        const body = JSON.stringify(param);
        return this.http.post<any>(`${this.url}request-edoc`, body);
    }

    requestAcuseEdoc(param) {
        const body = JSON.stringify(param);
        return this.http.post<any>(`${this.url}request-acuseedoc`, body);
    }

    getStatusAcuseCove(ids: number[]) {
        return this.http.get<any>(`${this.url}acuseCove/getStatus?ids=${_.toString(ids)}`);
    }

    getStatusAcuseEdoc(ids: number[]) {
        return this.http.get<any>(`${this.url}acuseEdoc/getStatus?ids=${_.toString(ids)}`);
    }
}
