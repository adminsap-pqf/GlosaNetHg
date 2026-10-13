import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ReportesAmedidaComponent } from './reportes-amedida.component';



@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: '',
        component: ReportesAmedidaComponent
      }
    ])
  ],
  exports: [
    RouterModule
  ]
})
export class ReportesAmedidaRoutingModule {
}