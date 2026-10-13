export class HistorialProducto {

  idHistorialProducto:number;
  idRegistro:number;
  nombre:string;
  comentarios:string;
  fecha:Date;

  constructor() {
    this.idHistorialProducto = 0;
    this.idRegistro = 0;
    this.nombre = '';
    this.comentarios = '';
    this.fecha = null;
  }

  reset() {
    this.idHistorialProducto = 0;
    this.idRegistro = 0;
    this.nombre = '';
    this.comentarios = '';
    this.fecha = null;
  }

}
