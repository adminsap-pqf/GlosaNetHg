import {Cliente} from "./cliente.class";

export class Usuario {

  idUsuario: number;
  usuario: String;
  password: String;
  cliente: Cliente;
  permiso: any;//PermisosBO
  habilitado: Boolean;
  fcreacion: Date;
  fechaNacimiento: Date;
  nombre: String;
  apellido: String;
  nacionalidad: String;
  titulo: String;
  email: String;
  telefono: String;
  ext: String;
  permisos: any[];//List<PermisosBO>
  imagenUsuario: any[];//byte[]
  extImagenUsuario: String;
  admin: Boolean;
  sinImagen: Boolean;
  idioma: String;
  moneda: String;
  consola: Boolean;

  contrasenaNueva: string;
}
