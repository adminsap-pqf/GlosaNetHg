import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {DropListModule} from "../shared/drop-list/drop-list.module";
import {PipeModule} from "../../pipes/accounting/accounting.module";
import {AgenteAduanalRoutingModule} from "./agente-aduanal-routing.module";
import {AgenteAduanalComponent} from "./agente-aduanal.component";
import {WizardModule} from "../shared/wizard/wizard.module";


@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    HttpClientModule,
    DropListModule,
    AgenteAduanalRoutingModule,
    PipeModule,
    FormsModule,
    WizardModule
  ],
  declarations: [
    AgenteAduanalComponent
  ],
})
export class AgenteAduanalModule { }
