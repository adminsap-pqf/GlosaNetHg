import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { HttpClientModule } from "@angular/common/http";
import { DropListModule } from "../shared/drop-list/drop-list.module";
import { DatePickerModule } from "../shared/date-picker/date-picker.module";
import { PipeModule } from "../../pipes/accounting/accounting.module";
import { PermisosComponent } from "./permisos.component";
import { PermisosRoutingModule } from "./permisos-routing.module";
import { SearchBarModule } from "../shared/search-bar/search-bar.module";
import { NuevaPrevisionComponent } from "../nueva-prevision/nueva-prevision.component";
import { WizardModule } from "../shared/wizard/wizard.module";
import { SwitchModule } from "../shared/switch/switch.module";
import { HistorialImportacionesComponent } from "../historial-importaciones/historial-importaciones.component";
import { NewForecastComponent } from "../new-forecast/new-forecast.component";
import { ForecastNoticeComponent } from "../forecast-notice/forecast-notice.component";
import { FiltroTiempoModule } from "../shared/filtro-tiempo/filtro-tiempo.module";
import { PrevisionComponent } from "../prevision/prevision.component";
import { MonitoreoPrevisionComponent } from "../monitoreo-prevision/monitoreo-prevision.component";
import { ListadoProductosComponent } from "../listado-productos/listado-productos.component";
import { VirtualScrollerModule } from "ngx-virtual-scroller";
import { AgregarProductoComponent } from "../agregar-producto/agregar-producto.component";
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
    PermisosRoutingModule,
    SearchBarModule,
    WizardModule,
    SwitchModule,
    FiltroTiempoModule,
    VirtualScrollerModule,
    ProductListModule
  ],
  declarations: [
    PermisosComponent,
    NuevaPrevisionComponent,
    HistorialImportacionesComponent,
    NewForecastComponent,
    ForecastNoticeComponent,
    PrevisionComponent,
    MonitoreoPrevisionComponent,
    ListadoProductosComponent,
    AgregarProductoComponent,
  ]
})
export class PermisosModule { }
