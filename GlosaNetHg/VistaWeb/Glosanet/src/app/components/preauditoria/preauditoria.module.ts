import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {DropListModule} from "../shared/drop-list/drop-list.module";
import {DatePickerModule} from "../shared/date-picker/date-picker.module";
import {PipeModule} from "../../pipes/accounting/accounting.module";
import {PreauditoriaRoutingModule} from "./preauditoria-routing.module";
import {PreauditoriaComponent} from "./preauditoria.component";
import {VirtualScrollerModule} from 'ngx-virtual-scroller';
import {MatTableModule} from '@angular/material/table';
import {MatPaginatorModule} from '@angular/material/paginator';
import {SearchBarModule} from '../shared/search-bar/search-bar.module';


@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    HttpClientModule,
    PreauditoriaRoutingModule,
    DropListModule,
    DatePickerModule,
    PipeModule,
    VirtualScrollerModule,
    MatTableModule,
    MatPaginatorModule,
    SearchBarModule
  ],
  declarations: [
    PreauditoriaComponent
  ],
})
export class PreauditoriaModule {
}
