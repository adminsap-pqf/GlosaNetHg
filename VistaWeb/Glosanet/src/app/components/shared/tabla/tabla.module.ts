import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { TablaComponent } from "./tabla.component";
import { PipeModule } from "../../../pipes/accounting/accounting.module";


@NgModule({
  imports: [
    CommonModule,
    PipeModule
  ],
  declarations: [
    TablaComponent
  ],
  exports: [
    TablaComponent
  ]
})
export class TablaModule { }