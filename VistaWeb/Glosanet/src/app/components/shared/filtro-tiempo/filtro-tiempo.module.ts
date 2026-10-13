import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { HttpClientModule } from "@angular/common/http";
import { FiltroTiempoComponent } from "./filtro-tiempo.component";


@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
  ],
  declarations: [
    FiltroTiempoComponent
  ],
  exports:[
    FiltroTiempoComponent
  ]
})
export class FiltroTiempoModule { }
