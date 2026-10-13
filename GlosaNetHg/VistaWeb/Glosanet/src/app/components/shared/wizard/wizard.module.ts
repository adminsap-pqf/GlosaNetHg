import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { WizardComponent } from "./wizard.component";


@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
  ],
  declarations: [
    WizardComponent
  ],
  exports:[
    WizardComponent
  ]
}) 
export class WizardModule { }