import {Injectable} from "@angular/core";
import {Producto} from "../../model/producto.class";

@Injectable()
export class ProductoGeneralService {

  private producto: Array<Producto> = new Array<Producto>();
  private firstChange: boolean = false;

  constructor() {

  }

  resetProducto() {
    this.producto = [];
    this.firstChange = false;
  }

  addProducto(producto: Producto) {
    this.producto.push(producto);
  }

  setProducto(producto: Array<Producto>) {
    this.producto = Object.assign([], producto);
    this.firstChange = true;
  }

  getProducto() {
    return this.producto;
  }

  getFirstChange() {
    return this.firstChange;
  }


}
