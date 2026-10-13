import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { SwitchComponent } from "./switch.component";


@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
  ],
  declarations: [
    SwitchComponent
  ],
  exports:[
    SwitchComponent
  ]
}) 
export class SwitchModule { }