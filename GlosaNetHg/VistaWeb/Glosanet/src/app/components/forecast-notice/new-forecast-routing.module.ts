import { NgModule } from '@angular/core';
import { RouterModule } from "@angular/router";
import { ForecastNoticeComponent } from "./forecast-notice.component";
import { NewForecastComponent } from "../new-forecast/new-forecast.component";

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: '',
        component: ForecastNoticeComponent
      },
      {
        path: 'newForecast',
        component: NewForecastComponent
      }
    ])
  ],
  declarations: [
    RouterModule
  ]
})
export class NewForecastRoutingModule { }
