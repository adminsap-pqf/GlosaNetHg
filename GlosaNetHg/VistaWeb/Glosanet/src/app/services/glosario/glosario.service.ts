import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {SessionUser} from './../session/session.service';
import {Parametros} from '../../model/parametros.class';
import {Countries} from "../../interfaces/countries";
import {Appendix} from "../../interfaces/appendix";

@Injectable()
export class GlosarioService {
  private urlobtenerApendices: string = SessionUser.getInstance().getIP() + 'glosario/obtenerApendices';
  private urlobtenerTratados: string = SessionUser.getInstance().getIP() + 'glosario/obtenerTratados';
  private urlobtenerRegiones: string = SessionUser.getInstance().getIP() + 'glosario/obtenerRegiones';
  private urlobtenerApendiceContenido: string = SessionUser.getInstance().getIP() + 'glosario/obtenerApendiceContenido';
  private urlobtenerTratadoContenido: string = SessionUser.getInstance().getIP() + 'glosario/obtenerTratadoContenido';
  private urlobtenerRegionContenido: string = SessionUser.getInstance().getIP() + 'glosario/obtenerRegionContenido';

  private urlobtenerPaises: string = SessionUser.getInstance().getIP() + 'glosario/obtenerPaises';

  constructor(private http: HttpClient) {
  }

  obtenerApendices() {
    return this.http.post<Appendix[]>(this.urlobtenerApendices, {});
  }

  obtenerTratados() {
    return this.http.post<any>(this.urlobtenerTratados, {});
  }

  obtenerRegiones() {
    return this.http.post<any>(this.urlobtenerRegiones, {});
  }

  obtenerApendiceContenido(parametros: Parametros) {
    let body = JSON.stringify(parametros);
    return this.http.post<any>(this.urlobtenerApendiceContenido, body);
  }

  obtenerTratadoContenido(parametros: Parametros) {
    let body = JSON.stringify(parametros);
    return this.http.post<any>(this.urlobtenerTratadoContenido, body);
  }

  obtenerRegionContenido(parametros: Parametros) {
    let body = JSON.stringify(parametros);
    return this.http.post<any>(this.urlobtenerRegionContenido, body);
  }

  obtenerPaises(parametros: Parametros) {
    let body = JSON.stringify(parametros);
    return this.http.post<Countries>(this.urlobtenerPaises, body);
  }


}
