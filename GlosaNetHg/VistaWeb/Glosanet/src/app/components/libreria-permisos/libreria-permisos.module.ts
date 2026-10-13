import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { HttpClientModule } from "@angular/common/http";
import { DropListModule } from "../shared/drop-list/drop-list.module";
import { DatePickerModule } from "../shared/date-picker/date-picker.module";
import { PipeModule } from "../../pipes/accounting/accounting.module";
import { SearchBarModule } from "../shared/search-bar/search-bar.module";
import { WizardModule } from "../shared/wizard/wizard.module";
import { SwitchModule } from "../shared/switch/switch.module";
import { FiltroTiempoModule } from "../shared/filtro-tiempo/filtro-tiempo.module";
import { VirtualScrollerModule } from "ngx-virtual-scroller";
import { LibreriaPermisosComponent } from "./libreria-permisos.component";
import { LibreriaPermisosRoutingModule } from "./libreria-permisos-routing.module";
import { MonitoreoPermisosComponent } from "../monitoreo-permisos/monitoreo-permisos.component";
import { NuevoPermisoComponent } from "../nuevo-permiso/nuevo-permiso.component";
import { ProductListModule } from '../products/product-list/product-list.module';


@NgModule({
    imports: [
        CommonModule,
        ReactiveFormsModule,
        FormsModule,
        HttpClientModule,
        DropListModule,
        DatePickerModule,
        PipeModule,
        SearchBarModule,
        WizardModule,
        SwitchModule,
        FiltroTiempoModule,
        VirtualScrollerModule,
        LibreriaPermisosRoutingModule,
        ProductListModule
    ],
    declarations: [
        LibreriaPermisosComponent,
        MonitoreoPermisosComponent,
        NuevoPermisoComponent
    ]
})

export class LibreriaPermisosModule { }
