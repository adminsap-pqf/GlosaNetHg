import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { HttpClientModule } from "@angular/common/http";
import { TableroRoutingModule } from "./tablero-routing.module";
import { TableroComponent } from "./tablero.component";
import { DropListModule } from "../shared/drop-list/drop-list.module";
import { DatePickerModule } from "../shared/date-picker/date-picker.module";
import { PipeModule } from "../../pipes/accounting/accounting.module";
import { MesesService } from "../../services/general/meses";
import { LegendsModule } from "../shared/legends/legends.module";


@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    HttpClientModule,
    TableroRoutingModule,
    DropListModule,
    DatePickerModule,
    PipeModule,
    LegendsModule
  ],
  declarations: [
    TableroComponent
  ],
  providers:[
    MesesService
  ]
})
export class TableroModule { }
