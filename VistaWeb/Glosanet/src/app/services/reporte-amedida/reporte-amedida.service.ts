import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {SessionUser} from './../session/session.service';
import {Parametros} from '../../model/parametros.class';

@Injectable()
export class ReporteAmedidaService {
  private urlobtenerReporte565: string = SessionUser.getInstance().getIP() + 'reporteAmedida/obtenerReporteMedidaMensualDICIPA_565';
  private urlPeticionReporte565: string = SessionUser.getInstance().getIP() + 'reporteAmedida/peticionReporteMedidaMensualDICIPA_565';

  constructor(private http: HttpClient) {
  }

  obtenerReporte565(parametros: Parametros) {
    let body = JSON.stringify(parametros);

    return this.http.post<any>(this.urlobtenerReporte565, body);
  }

  peticionReporte565(parametros: Parametros) {
    let body = JSON.stringify(parametros);

    return this.http.post<any>(this.urlPeticionReporte565, body);
  }
}
