import { Component, OnInit, ViewChild } from '@angular/core';
import { RegistrosService } from '../../services/registros/registros.service';
import { ProductsService } from '../../services/products/products.service';
import { SessionUser } from '../../services/session/session.service';
import { Parametros } from '../../model/parametros.class';
import { Router } from '@angular/router';
import { IPageInfo, VirtualScrollerComponent } from 'ngx-virtual-scroller';
import _ from 'lodash';
import { PAGING_LIMIT } from '../../util/common.protocols';

/* Dev Tools */
import { DEBUG } from '../../settings/app.settings';

@Component({
  selector: 'gn-cat-productos',
  templateUrl: './cat-productos.component.html',
  styleUrls: ['./cat-productos.component.scss']
})
export class CatProductosComponent implements OnInit {

  @ViewChild(VirtualScrollerComponent, {static: false})
  private virtualScroller: VirtualScrollerComponent;

  lstItems: string [] = ['Todos', 'Con Registro', 'Con Permiso'];
  selectedTab = 'Todos';
  pWizard: any = 0;

  placeholder: any = 'Buscar';
  searchTerm = '';
  resetSearch: boolean;
  arrayAux: any = [];
  search: any = [];

  public itemsFiltros: any[] = [
    {nombre: 'Nombre (A-Z)', id: 0},
    {nombre: 'Código', id: 2}
  ];
  defaultSelectedFiltros: any = {nombre:'Nombre (A-Z)', id: 0};
  // 0 = Nombre - Ascendente
  // 1 = Nombre - Descendente
  // 2 = Código - Ascendente
  // 3 = Código - Descendente

  cuadricula:boolean = false;

  lst: any[] = [];

  classItem: any[];
  classItem1: any[];

  loading = true;
  scrollItems: any = [];
  buffer: any[] = [];
  timer;

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
    private registrosService: RegistrosService,
    private productsService: ProductsService
    ) { }

  ngOnInit() {
    // this.getProductos();
  }

  async ngAfterViewInit() {
    this._isMounted = true; // TODO: Evitamos llamadas innecesarias de los droplist cuando aun el componente no esta montado
    const data:any = await this.getData(); // Vamos por la primer página
    this.buffer = data.slice();
    this.loading = false;
  }

  tab($event) {
    DEBUG && console.log($event);
    this.selectedTab = $event;

    this.buffer = [];
    this.loading = false;
    this.currentPage = 0;
    this.reset();
    return;

    this.arrayAux = [];
    if($event == 'Todos'){
      this.arrayAux = Object.assign([], this.lst);
      this.reset();
    }else if($event == 'Con Registro'){
      for(let item of this.lst){
        if(item.numRegistros > 0){
          this.arrayAux.push(item);
        }
      }
      this.reset();
    }else if($event == 'Con Permiso'){
      for(let item of this.lst){
        if(item.numPermisos){
          this.arrayAux.push(item);
        }
      }
      this.reset();
    }
  }

  /* Busqueda de search bar */
  changeSearchTerm($event) {
    this.searchTerm = $event;
    this.currentPage = 0;
    this.reset();
    return;
  }

  dropListFiltro($event){
    DEBUG && console.log($event);
    this.defaultSelectedFiltros = $event;
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

  /*reset_() {
    let numItems = 0;
    numItems = Math.floor((window.screen.width - 110)/ 225) * 4;
    if(this.arrayAux.length > 0 ){
      this.loading = true;
    }
    if(this.arrayAux.length > 0){
      this.fetchNextChunk(0, numItems).then(chunk => this.buffer = chunk);

    }
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
    let numItems = Math.floor((window.screen.width - 110) / 225) * 4;

    if (event.endIndex !== this.arrayAux.length - 1 && this.arrayAux.length > 0) {
      if (event.startIndex !== 0) {
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
      }, 200);
    });
  }

  scrollTo() {
    this.virtualScroller.scrollInto(this.arrayAux[0]);
  }

  irLibreria(){
    this.router.navigate(["protected/libreriaRegistros"]);
  }

  selectItem(item) {
    this.buffer = _.map(this.buffer, o => {
      return {
        ...o,
        selected: o.idProducto === item.idProducto
      }
    });
  }

  handleLoading(value) {
    this.loading = value;
  }

  handleCurrentPage(value) {
    this.currentPage = value;
  }

  getData = (updateData = false) => {
    return new Promise((resolve, reject) => {
      const numItems = Math.floor((window.screen.width - 110) / 225) * 4;
      const size = PAGING_LIMIT < numItems ? numItems : PAGING_LIMIT;

      let params = new Parametros();
      params.condicion = this.selectedTab === 'Con Registro'
        ? 'registro'
        : this.selectedTab === 'Con Permiso'
          ? 'permiso'
          : '';
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
              _.forEach(this.buffer, (o, index) => {
                this.buffer[index].indexxx = index + 1;
              });
            }
            _.forEach(result, (o, index) => {
              result[index].indexxx = index + 1;
            });
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
