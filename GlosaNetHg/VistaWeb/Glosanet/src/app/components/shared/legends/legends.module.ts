import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { LegendsComponent } from "./legends.component";



@NgModule({
  imports: [
    CommonModule,
  ],
  declarations: [
    LegendsComponent
  ],
  exports:[
    LegendsComponent
  ]
}) 
export class LegendsModule { }