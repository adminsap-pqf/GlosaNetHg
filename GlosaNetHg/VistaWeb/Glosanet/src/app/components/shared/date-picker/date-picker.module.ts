import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { DatePickerComponent } from "./date-picker.component";


@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule
  ],
  declarations: [
    DatePickerComponent
  ],
  exports:[
    DatePickerComponent
  ]
}) 
export class DatePickerModule { }