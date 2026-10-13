import {Usuario} from "../model/usuario.class";

export interface IPrevision {
  idProxPrevision: number;
  usuario: Usuario;
  citaCofepris: Date;
}
