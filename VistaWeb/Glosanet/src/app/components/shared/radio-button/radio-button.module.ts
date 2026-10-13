import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { HttpClientModule } from "@angular/common/http";
import { RadioButtonComponent } from "./radio-button.component";


@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
  ],
  declarations: [
    RadioButtonComponent
  ],
  exports:[
    RadioButtonComponent
  ]
})
export class RadioButtonModule { }
