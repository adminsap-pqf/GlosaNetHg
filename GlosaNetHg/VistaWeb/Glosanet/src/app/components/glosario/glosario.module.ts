import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {DropListModule} from "../shared/drop-list/drop-list.module";
import {DatePickerModule} from "../shared/date-picker/date-picker.module";
import {PipeModule} from "../../pipes/accounting/accounting.module";
import {GlosarioComponent} from "./glosario.component";
import {GlosarioRoutingModule} from "./glosario-routing.module";
import {FiltroTiempoModule} from "../shared/filtro-tiempo/filtro-tiempo.module";
import {TablaModule} from "../shared/tabla/tabla.module";
import {SearchBarModule} from "../shared/search-bar/search-bar.module";

@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    HttpClientModule,
    DropListModule,
    DatePickerModule,
    PipeModule,
    GlosarioRoutingModule,
    FiltroTiempoModule,
    TablaModule,
    SearchBarModule,
  ],
  declarations: [
    GlosarioComponent
  ],
})
export class GlosarioModule {
}
