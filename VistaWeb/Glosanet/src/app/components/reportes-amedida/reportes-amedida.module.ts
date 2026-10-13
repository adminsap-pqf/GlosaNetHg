import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {DropListModule} from "../shared/drop-list/drop-list.module";
import {DatePickerModule} from "../shared/date-picker/date-picker.module";
import {PipeModule} from "../../pipes/accounting/accounting.module";
import {ReportesAmedidaRoutingModule} from "./reportes-amedida-routing.module";
import {ReportesAmedidaComponent} from "./reportes-amedida.component";


@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    HttpClientModule,
    DropListModule,
    DatePickerModule,
    PipeModule,
    ReportesAmedidaRoutingModule
  ],
  declarations: [
    ReportesAmedidaComponent
  ],
})
export class ReportesAmedidaModule {
}
