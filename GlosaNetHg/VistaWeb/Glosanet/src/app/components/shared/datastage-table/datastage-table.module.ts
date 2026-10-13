import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DatastageTableComponent } from './datastage-table.component';
import { PipeModule } from '../../../pipes/accounting/accounting.module';
import { VirtualScrollerModule } from 'ngx-virtual-scroller';



@NgModule({
  declarations: [
      DatastageTableComponent
  ],
  imports: [
      CommonModule,
      PipeModule,
      VirtualScrollerModule
  ],
  exports: [
      DatastageTableComponent
  ]
})
export class DatastageTableModule { }
