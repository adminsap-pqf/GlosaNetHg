import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {ReactiveFormsModule} from "@angular/forms";
import {DropListComponent} from "./drop-list.component";
import {SearchBarModule} from "../search-bar/search-bar.module";
import {RadioButtonModule} from "../radio-button/radio-button.module";


@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    SearchBarModule,
    RadioButtonModule
  ],
  declarations: [
    DropListComponent,
  ],
  exports: [
    DropListComponent
  ]
})
export class DropListModule {
}
