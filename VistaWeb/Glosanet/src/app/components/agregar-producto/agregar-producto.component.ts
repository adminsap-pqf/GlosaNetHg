import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {SessionUser} from '../../services/session/session.service';
import {Parametros} from '../../model/parametros.class';
import {RegistrosService} from '../../services/registros/registros.service';
import {IPageInfo, VirtualScrollerComponent} from 'ngx-virtual-scroller';
import {Producto} from '../../model/producto.class';
import {ProductoGeneralService} from '../../services/general/productos';
import {ProductsService} from '../../services/products/products.service';
import * as _ from 'lodash';
import {PAGING_LIMIT} from '../../util/common.protocols';

/* Dev Tools */
import {DEBUG} from "../../settings/app.settings";

@Component({
  selector: 'gn-agregar-producto',
  templateUrl: './agregar-producto.component.html',
  styleUrls: ['./agregar-producto.component.scss']
})
export class AgregarProductoComponent implements OnInit {

  @ViewChild(VirtualScrollerComponent, {static: false})
  private virtualScroller: VirtualScrollerComponent;

  placeholder: any = 'Buscar';
  searchTerm = '';
  resetSearch: boolean;
  arrayAux: any = [];
  search: any = [];

  sub: any;
  idPrevision: number = 0;

  lst: any[] = [];


  loading = true;
  scrollItems: any = [];
  buffer: any[] = [];
  timer;

  itemsFiltros: any[] = [
    {nombre: 'Nombre (A-Z)', id: 0},
    {nombre: 'Código', id: 2}
  ];
  defaultSelectedFiltros: any = {nombre: 'Nombre (A-Z)', id: 0};
  // 0 = Nombre - Ascendente
  // 1 = Nombre - Descendente
  // 2 = Código - Ascendente
  // 3 = Código - Descendente

  classItem: any[];
  classItem1: any[];

  /* modal producto */
  itemsTipoProducto:any = [{nombre:'Controlado', tipo: 1},{nombre:'No Controlado',tipo:2}];
  itemsUnidad:any = [{nombre:'t'},{nombre:'Kg'},{nombre:'g'},{nombre:'l'},{nombre:'ml'}, {nombre:'pza'}];

  producto: Producto = new Producto();

  classAnimation = 'w3-animate-zoom-out';

  defaultTipoProducto = {nombre: ''};
  defaultUnidad = {nombre: ''};
  titleModal: string = '';

  ttProducto:any = 0;

  idProducto:number = 0;

  date = new Date();
  yearActual: number = 0;
  nextYear: number = 0;

  totalProducts  = 0;
  isList = true;
  grandTotal = 0;
  currentTotal = 0;
  currentPage = 0;
  _isMounted = false;
  firstItem = {};

  private handleKeySearch = _.debounce(value => this.changeSearchTerm(value), 500);

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private registrosService: RegistrosService,
    private productoGeneral: ProductoGeneralService,
    private productsService: ProductsService,
  ) { }

  ngOnInit() {

    this.yearActual = this.date.getFullYear();
    this.nextYear = this.yearActual + 1;
    this.sub = this.route.params.subscribe(params => {
      this.idPrevision = params['id'];

    });

    // this.getProductos(); TODO: Versión anterior del endpoint
  }

  async ngAfterViewInit() {
    this._isMounted = true; // TODO: Evitamos llamadas innecesarias de los droplist cuando aun el componente no esta montado
    this.loading = true;
    const data:any = await this.getData(); // Vamos por la primer página
    this.buffer = data.slice();
    this.loading = false;
  }


  irListadoProducto(){
    this.router.navigate(["protected/permisos/listadoProductos", this.idPrevision]);
  }




  dropListFiltro($event) {
    this.defaultSelectedFiltros = $event;
    this.currentPage = 0;
    this.reset();
  }



  /* Busqueda de search bar */
  changeSearchTerm($event) {
    this.searchTerm = $event;
    this.currentPage = 0;
    this.reset();
  }


  /*getProductos(){
    SessionUser.getInstance().setLoading(true);
    let parametros = new Parametros();
    parametros.condicion = SessionUser.getInstance().getRfc();
    parametros.id = SessionUser.getInstance().getidEmpresaActual();
    this.registrosService.obtenerProductosDataStagePorEmpresa(parametros)
      .subscribe(
        data => {
          DEBUG && console.log(data);
          this.lst = Object.assign([], data);
          this.arrayAux = Object.assign([], data);

          this.arrayAux = this.arrayAux.sort((a, b) => (a.nombre < b.nombre) ? -1 : 1);
          let i = 0;
          for(let item of this.arrayAux){
            item.index = i + 1;
            i++;
          }
          //this.lst.push(data[0]);
          this.classItem = new Array(this.lst.length).fill('item');
          this.classItem1 = new Array(this.lst.length).fill('item1');
          SessionUser.getInstance().setLoading(false);
          this.reset();
        },
        error => {
          SessionUser.getInstance().setLoading(false);
          DEBUG && console.log('error al traer los productos '+error);
        });
  }*/

  async reset() {
    this.loading = true;
    const data: any = await this.getData();
    this.buffer = data.slice();
    this.loading = false;
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
    DEBUG && console.log("fetchNextChunk");

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
  nuevoProducto() {
    this.titleModal = 'Nuevo Producto / Previsión 2019';
    this.classAnimation = 'w3-animate-zoom';
    this.producto.nombre = '';
    this.producto.codigo = '';
    this.producto.origen = '';
    this.defaultTipoProducto = {nombre: ''};
    this.defaultUnidad = {nombre: ''};

  }

  agregarProducto(){
    DEBUG && console.log(this.producto);
    this.findIdProducto(this.producto) ? null : this.productoGeneral.addProducto(this.producto);
    this.irListadoProducto();
  }

  cerrarProducto() {
    this.classAnimation = 'w3-animate-zoom-out';
    document.getElementById('id01').style.display = 'none';
  }


  findIdProducto(product){
    let flag = false;
    let productos = this.productoGeneral.getProducto();
    for(let item of productos){
      if(item.idProducto == product.idProducto && item.idProducto !== 0){
        flag = true;
        break;
      }
    }

    return flag;
  }
  /* modal producto */



  selectItem(item) {
    DEBUG && console.log(item);
    /* this.idProducto = item.idProducto !== undefined ? item.idProducto : 0;
    this.producto.nombre = item.nombre;
    this.producto.codigo = item.codigo;
    this.producto.origen = item.origen; */
    this.producto = Object.assign(new Producto(), item);

    this.buffer = _.map(this.buffer, o => {
      return {
        ...o,
        selected: o.idProducto === item.idProducto
      }
    });

    DEBUG && console.log(this.producto);
    this.seleccionarProducto();
  }

  seleccionarProducto() {
    this.titleModal = 'Agregar Producto / Previsión 2019';
    this.classAnimation = 'w3-animate-zoom';
    document.getElementById('id01').style.display = 'block';
  }

  handleLoading(value) {
    this.loading = value;
  }

  handleCurrentPage(value) {
    this.currentPage = value;
  }

  getData = (updateData = false) => {
    //SessionUser.getInstance().setLoading(true);
    return new Promise((resolve, reject) => {
      const numItems = Math.floor((window.screen.width - 110) / 225) * 4;
      const size = PAGING_LIMIT < numItems ? numItems : PAGING_LIMIT;
      let params = new Parametros();
      params.condicion = SessionUser.getInstance().getRfc();
      params.id = SessionUser.getInstance().getidEmpresaActual();
      params.page = this.currentPage;
      params.size = size;
      params.orden = this.defaultSelectedFiltros.id;
      params.busqueda = this.searchTerm;
      this.productsService.fetchProducts(params).subscribe(
        data => {
          SessionUser.getInstance().setLoading(false);
          if (data) {
            const { results, total } = data;
            const result: any[] = this.patchObject(results.slice());
            this.grandTotal = this.currentTotal === 0 && this.grandTotal === 0 && total > 0 ? total : this.grandTotal;
            this.firstItem = result.length > 0 && this.currentPage === 0 ? result[0] : this.firstItem;
            this.currentTotal = total;
            this.loading = false;
            if (updateData) {
              this.buffer = this.buffer.concat(result);
            }
            resolve(result.slice());
          } else {
            resolve([]);
          }
        }, err => {
          DEBUG && console.log('@getData: Error ', err);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al obtener los productos');
          resolve([]);
          this.loading = false;
        });
    });
  }

  patchObject(collection): any[] {
    return _.map(collection, o => {
      return {
        ...o,
        selected: false
      };
    })
  }

}
