import { Component, OnInit } from '@angular/core';
import _ from 'lodash';
import { SessionUser } from '../../services/session/session.service';
import { ProductsService } from '../../services/products/products.service';
import { Parametros } from '../../model/parametros.class';
import { PAGING_LIMIT } from '../../util/common.protocols';
import { DEBUG } from '../../settings/app.settings';


@Component({
  selector: 'gn-productos',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.scss']
})
export class ProductsComponent implements OnInit {
  totalProducts  = 0;
  searchTerm = '';
  isList = true;

  grandTotal = 0;
  currentTotal = 0;
  currentPage = 0;
  _isMounted = false;

  firstItem = {};
  loading = true;
  buffer: any[] = [];

  constructor(private productsService: ProductsService) { }

  ngOnInit() { }

  async ngAfterViewInit() {
    this._isMounted = true; // TODO: Evitamos llamadas innecesarias de los droplist cuando aun el componente no esta montado
    this.loading = true;
    const data:any = await this.getData(); // Vamos por la primer página
    this.buffer = data.slice();
    this.loading = false;
  }

  changeSearchTerm($event: string) {

  }

  async reset() {
    this.loading = true;
    const data: any = await this.getData();
    this.buffer = data.slice();
    this.loading = false;
  }

  handleLoading(value) {
    this.loading = value;
  }

  handleCurrentPage(value) {
    this.currentPage = value;
  }

  selectItem(item) {
    this.buffer = _.map(this.buffer, o => {
      return {
        ...o,
        selected: o.idProducto === item.idProducto
      }
    });
  }

  getData = (updateData = false) => {
    return new Promise((resolve, reject) => {
      const numItems = Math.floor((window.screen.width - 110) / 225) * 4;
      const size = PAGING_LIMIT < numItems ? numItems : PAGING_LIMIT;

      let params = new Parametros();
      params.condicion = '';
      params.id = SessionUser.getInstance().getidEmpresaActual();
      params.page = this.currentPage;
      params.size = size;

      this.productsService.fetchProducts(params).subscribe(
        data => {
          SessionUser.getInstance().setLoading(false);
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
