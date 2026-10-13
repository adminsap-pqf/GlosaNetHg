import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { SearchBarComponent } from "./search-bar.component";


@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
  ],
  declarations: [
    SearchBarComponent
  ],
  exports:[
    SearchBarComponent
  ]
}) 
export class SearchBarModule { }