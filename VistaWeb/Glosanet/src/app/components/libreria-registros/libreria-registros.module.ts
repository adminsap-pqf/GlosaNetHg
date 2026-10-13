import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {DropListModule} from "../shared/drop-list/drop-list.module";
import {DatePickerModule} from "../shared/date-picker/date-picker.module";
import {PipeModule} from "../../pipes/accounting/accounting.module";
import {LibreriaRegistrosComponent} from "./libreria-registros.component";
import {LibreriaRegistrosRoutingModule} from "./libreria-registros-routing.module";
import {SearchBarModule} from "../shared/search-bar/search-bar.module";
import {NuevoRegistroComponent} from "../nuevo-registro/nuevo-registro.component";
import {WizardModule} from "../shared/wizard/wizard.module";
import {MonitoreoTramiteComponent} from "../monitoreo-tramite/monitoreo-tramite.component";
import {SwitchModule} from "../shared/switch/switch.module";
import {VirtualScrollerModule} from 'ngx-virtual-scroller';
import {DiasHabilesService} from "../../services/general/diasHabiles";
import {CatProductosComponent} from "../cat-productos/cat-productos.component";
import {FiltroTiempoModule} from "../shared/filtro-tiempo/filtro-tiempo.module";
import {HistorialRegistroComponent} from '../historial-registro/historial-registro.component';
import {ProductListModule} from '../products/product-list/product-list.module';


@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
    DropListModule,
    DatePickerModule,
    PipeModule,
    LibreriaRegistrosRoutingModule,
    SearchBarModule,
    WizardModule,
    SwitchModule,
    VirtualScrollerModule,
    FiltroTiempoModule,
    ProductListModule
  ],
  declarations: [
    LibreriaRegistrosComponent,
    NuevoRegistroComponent,
    MonitoreoTramiteComponent,
    CatProductosComponent,
    HistorialRegistroComponent,
  ],
  providers: [
    DiasHabilesService
  ]
})
export class LibreriaRegistrosModule {
}
