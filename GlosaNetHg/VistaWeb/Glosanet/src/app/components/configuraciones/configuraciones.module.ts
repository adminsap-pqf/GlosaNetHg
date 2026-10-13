import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { ConfiguracionesComponent } from "./configuraciones.component";
import { ConfiguracionesRoutingModule } from "./configuraciones.routing.module";
import { DatePickerModule } from "../shared/date-picker/date-picker.module";
import { PipeModule } from "../../pipes/accounting/accounting.module";
import { DropListModule } from "../shared/drop-list/drop-list.module";


@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    ConfiguracionesRoutingModule,
    DatePickerModule,
    PipeModule,
    DropListModule,
  ],
  declarations: [
    ConfiguracionesComponent
  ], 

}) 
export class ConfiguracionesModule { }