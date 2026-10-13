import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PipeModule } from '../../pipes/accounting/accounting.module';
import { SearchBarModule } from '../shared/search-bar/search-bar.module';
import { VirtualScrollerModule } from 'ngx-virtual-scroller';
import { ProductsComponent } from './products.component';
import { ProductsRoutingModule } from './products-routing.module';
import { ProductListModule } from './product-list/product-list.module';



@NgModule({
  declarations: [
    ProductsComponent,
  ],
  imports: [
    CommonModule,
    PipeModule,
    SearchBarModule,
    VirtualScrollerModule,
    ProductListModule,
    ProductsRoutingModule
  ]
})
export class ProductsModule { }
