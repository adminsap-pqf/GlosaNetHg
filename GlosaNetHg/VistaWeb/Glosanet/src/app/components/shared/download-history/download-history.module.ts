import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { HttpClientModule } from "@angular/common/http";
import { DownloadHistoryComponent } from "./download-history.component";

@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule
  ],
  declarations: [
    DownloadHistoryComponent
  ],
  exports: [
    DownloadHistoryComponent
  ]
})
export class DownloadHistoryModule { }
