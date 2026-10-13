import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductListComponent } from './product-list.component';
import { PipeModule } from '../../../pipes/accounting/accounting.module';
import { VirtualScrollerModule } from 'ngx-virtual-scroller';



@NgModule({
  declarations: [
    ProductListComponent
  ],
  imports: [
    CommonModule,
    PipeModule,
    VirtualScrollerModule
  ],
  exports: [
    ProductListComponent
  ]
})
export class ProductListModule { }
