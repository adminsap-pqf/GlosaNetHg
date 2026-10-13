import { Component, OnInit, ViewChild } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import {IPageInfo, VirtualScrollerComponent} from 'ngx-virtual-scroller';
import { SessionUser } from '../../services/session/session.service';
import { RegistrosService } from '../../services/registros/registros.service';
import { Parametros } from '../../model/parametros.class';
import { Producto } from '../../model/producto.class';
import { PrevisionesService } from '../../services/previsiones/previsiones.service';
import { ProductoGeneralService } from '../../services/general/productos';
import { Prevision } from '../../model/prevision.class';

/* Dev Tools */
import { DEBUG } from "../../settings/app.settings";

@Component({
  selector: 'gn-listado-productos',
  templateUrl: './listado-productos.component.html',
  styleUrls: ['./listado-productos.component.scss']
})
export class ListadoProductosComponent implements OnInit {

  @ViewChild(VirtualScrollerComponent, {static: false})
  private virtualScroller: VirtualScrollerComponent;

  placeholder: any = 'Buscar por producto, tipo de producto';
  searchTerm: string = null;
  resetSearch: boolean;
  arrayAux: any = [];
  search: any = [];

  sub: any;
  idPrevision:number;

  prevision: Prevision = new Prevision();

  lst: any[] = [];
  loading: boolean;
  scrollItems: any = [];
  buffer: any[] = [];
  timer;

  classItem: any[];
  classItem1: any[];

  /* modal producto */
  itemsTipoProducto:any = [{nombre:'Controlado', tipo: 1},{nombre:'No Controlado',tipo:2}];
  itemsUnidad:any = [{nombre:'t'},{nombre:'Kg'},{nombre:'g'},{nombre:'l'},{nombre:'ml'}, {nombre:'pza'}];

  producto: Producto = new Producto();

  productoEdit: Producto = new Producto();

  classAnimation = 'w3-animate-zoom-out';

  defaultTipoProducto = {nombre: '', tipo:0};
  defaultUnidad = {nombre: ''};


  lstKG:number = 0;
  lstPZA:number = 0;
  lstL:number = 0;

  ttProducto:any = 0;

  lstProductos:Array<Producto>;

  lstProductosFinal = new Array<Producto>();

  listProductosEliminar:string = '';

  date = new Date();
  yearActual: number = 0;
  nextYear: number = 0;

  constructor(private previsionService: PrevisionesService, private route: ActivatedRoute, private router: Router, private productoGeneral:ProductoGeneralService) { }

  ngOnInit() {
    this.yearActual = this.date.getFullYear();
    this.nextYear = this.yearActual + 1;
    this.sub = this.route.params.subscribe(params => {
      this.idPrevision = params['id'];

    });
    this.getPrevision();
    this.getProductos();
  }


  irMonitoreo() {
    this.router.navigate(['protected/permisos/monitoreo', this.idPrevision]);
  }

  irAgregarProducto() {
    this.router.navigate(['protected/permisos/agregarProducto', this.idPrevision]);
  }

  irPermisos(){
    this.router.navigate(['protected/permisos',]);
  }

  /* Busqueda de search bar */
  changeSearchTerm($event) {
    this.arrayAux = [];
    this.buffer = [];
    this.loading = false;
    let i = 0;
    for (let item of this.lst) {
      item.index = i + 1;
      this.arrayAux.push(item);
      i++;
    }
    DEBUG && console.log($event);
    if ($event !== '') {
      this.search = [];
      for (let item of this.arrayAux) {
        if(item.nombre !== undefined){

          if (item.nombre.toLowerCase().indexOf($event.toLowerCase()) !== -1 || item.codigo.indexOf($event) !== -1) {
            this.search.push(item);
          }
        }
      }
      if (this.search.length > 0) {
        this.arrayAux = [];
        let j = 0;
        for (let item of this.search) {
          item.index = j + 1;
          this.arrayAux.push(item);
          j++;
        }
        this.reset();

      }else {
        this.arrayAux = [];
        //this.loading = false;
      }
    }else {
      this.arrayAux = [];
      let l = 0;
      for (let item of this.lst) {
        item.index = l + 1;
        this.arrayAux.push(item);
        l++;
      }
      this.reset();
    }
  }


  reset() {
    let numItems = Math.floor((window.screen.width - 110)/ 225) * 4;
      /* if(this.arrayAux.length > 0 ){ */
        this.loading = true;
      /* } */
      if(this.arrayAux.length > 0){
        this.fetchNextChunk(0, numItems).then(chunk => this.buffer = chunk);
      }
  }


  getProductos(){
    SessionUser.getInstance().setLoading(true);
    let parametros = new Parametros();
    parametros.id = this.idPrevision;
    this.previsionService.obtenerProductosPorPrevision(parametros)
    .subscribe(
      data => {
        DEBUG && console.log(data);
        DEBUG && console.log(this.productoGeneral.getFirstChange());
        if(!this.productoGeneral.getFirstChange()){
          this.productoGeneral.setProducto(Object.assign([],data));
        }
        this.lst = Object.assign([],this.productoGeneral.getProducto());
        this.arrayAux = Object.assign([],this.productoGeneral.getProducto());

        //this.lst = Object.assign([], data);
        //this.arrayAux = Object.assign([], data);

        this.arrayAux = this.arrayAux.sort((a, b) => (a.nombre < b.nombre) ? -1 : 1);
        let i = 0;
        for(let item of this.arrayAux){
          item.index = i + 1;
          i++;
          this.sumarUnidadesProducto(item.unidad, item.cantidad);
        }
        DEBUG && console.log(this.lstKG);
        DEBUG && console.log(this.lstL);
        DEBUG && console.log(this.lstPZA);
        //this.lst.push(data[0]);
        this.classItem = new Array(this.lst.length).fill('item');
        this.classItem1 = new Array(this.lst.length).fill('item1');
        SessionUser.getInstance().setLoading(false);
        DEBUG && console.log(this.arrayAux);
        this.reset();
      },
      error => {
        DEBUG && console.log('error al traer los productos '+error);
      });
  }

  fetchMore(event: IPageInfo) {
    DEBUG && console.log(event);
    DEBUG && console.log(this.scrollItems);

    if (event.endIndex !== this.buffer.length - 1) return;
    const numItems = Math.floor((window.screen.width - 110) / 225) * 4;
      /* if(event.start == 0){
        this.loading = false;
      } */

    if (event.endIndex !== this.arrayAux.length - 1 && this.arrayAux.length > 0) {
      if(event.startIndex !== 0){
        this.loading = true;
      }
      this.fetchNextChunk(this.buffer.length, numItems).then(chunk => {
        this.buffer = this.buffer.concat(chunk);
        this.loading = false;
      }, () => this.loading = false);
    }
}

fetchNextChunk(skip: number, limit: number): Promise<any[]> {
  //DEBUG && console.log("fetchNextChunk");
    return new Promise((resolve, reject) => {
      clearTimeout(this.timer);
      this.timer = setTimeout(() => {
        this.loading = false;
        if (skip < this.arrayAux.length) {
          return resolve(this.arrayAux.slice(skip, skip + limit));
        }
        reject();
      }, 500);
    });
}

scrollTo() {
  this.virtualScroller.scrollInto(this.arrayAux[0]);
}


/* modal producto */
nuevoProducto(item) {
  this.classAnimation = 'w3-animate-zoom';
  this.productoEdit = Object.assign(new Producto(), item);
  if(this.productoEdit.tipoProducto == 1){
    this.defaultTipoProducto = {nombre:'Controlado', tipo: 1};
  }else{
    this.defaultTipoProducto = {nombre:'No Controlado', tipo: 2};
  }
  this.defaultUnidad = {nombre: this.productoEdit.unidad};

}

editarProducto(){
  DEBUG && console.log(this.productoEdit);
  let i = 0;
  for(let item of this.arrayAux){
    DEBUG && console.log(item);
    if(item.idProducto == this.productoEdit.idProducto){
      DEBUG && console.log('Entra');
      break;
    }
    i++;
  }
  let j = 0;
  for(let item of this.lst){
    if(item.idProducto == this.productoEdit.idProducto){
      break;
    }
    j++;
  }
  this.lst[j] = Object.assign(new Producto, this.productoEdit);
  this.arrayAux[i] = Object.assign(new Producto(), this.productoEdit);
  this.reset();
  DEBUG && console.log(this.arrayAux);
  this.lstKG = 0;
  this.lstL = 0;
  this.lstPZA = 0;
  for(let item of this.arrayAux){
    this.sumarUnidadesProducto(item.unidad, item.cantidad);
  }
}

eliminarProducto(producto){
  if(!this.prevision.publicado){
    let i = 0;
    for(let item of this.lst){
      if(item.index == producto.index){
        break;
      }
      i++;
    }

    let j = 0;
    for(let item of this.arrayAux){
      if(item.index == producto.index){
        break;
      }
      j++;
    }

    this.lst.splice(i,1);
    this.arrayAux.splice(j,1);
    this.productoGeneral.setProducto(Object.assign([],this.lst));

    DEBUG && console.log(this.lst);
    DEBUG && console.log(this.arrayAux);

    this.reset();
    this.lstKG = 0;
    this.lstL = 0;
    this.lstPZA = 0;
    for(let item of this.arrayAux){
      this.sumarUnidadesProducto(item.unidad, item.cantidad);
    }

    if(producto.idProducto !== 0){
      this.listProductosEliminar += producto.idPrevisionProducto + ',';
    }

  }
}

cerrarProducto() {
  this.classAnimation = 'w3-animate-zoom-out';
  document.getElementById('id01').style.display = 'none';
}
/* modal producto */


sumarUnidadesProducto(unidad, cantidad){
  switch (unidad) {
    case 'Kg':
      this.lstKG += Number(cantidad);
      break;
    case 'pza':
      this.lstPZA += Number(cantidad);
      break;
    case 'g':
      this.lstKG += (Number(cantidad)/1000);
      break;
    case 't':
      this.lstKG += (Number(cantidad) * 1000);
      break;
    case 'l':
      this.lstL += Number(cantidad);
      break;
    case 'ml':
      this.lstL += (Number(cantidad)/1000);
      break;
    default:
      break;
  }
}


guardarProductos(){
  //this.productoGeneral.resetProducto();
  SessionUser.getInstance().setLoading(true);
  for(let item of this.lst){
      item.idEmpresa = SessionUser.getInstance().getidEmpresaActual();
      item.idPrevision = this.idPrevision;
  }


    this.saveNuevosProductos(this.lst).then(
      (data) =>{
        DEBUG && console.log(data);
        this.productoGeneral.resetProducto();
        if(this.listProductosEliminar !== ''){
          this.eliminarPrevisionProducto();
        }
        this.irMonitoreo();
        SessionUser.getInstance().setLoading(false);
    });

}

saveNuevosProductos(arrayNuevosProductos){
  return new Promise((resolve, reject) =>{
  this.previsionService.guardarProducto(arrayNuevosProductos)
  .subscribe(
    data =>{
      DEBUG && console.log(data);
      resolve(data);
    },
    error =>{
      DEBUG && console.log(error);
      SessionUser.getInstance().setLoading(false);
    });
  });
}

eliminarPrevisionProducto(){
  let parametro = new Parametros();
  parametro.condicion = this.listProductosEliminar;
  this.previsionService.eliminarPrevisionProductos(parametro)
    .subscribe(
      data =>{
        DEBUG && console.log(data);
        SessionUser.getInstance().setLoading(false);
      },
      error =>{
        DEBUG && console.log(error);
        SessionUser.getInstance().setLoading(false);
      });
}


getPrevision(){
  //SessionUser.getInstance().setLoading(true);
  let parametro = new Parametros();
  parametro.id = this.idPrevision;
  this.previsionService.getPrevision(parametro)
  .subscribe(
    data =>{
      DEBUG && console.log(data);
      this.prevision = Object.assign(new Prevision, data);
    });
  }

}
