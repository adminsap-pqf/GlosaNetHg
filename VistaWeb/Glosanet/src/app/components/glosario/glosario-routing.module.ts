import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { GlosarioComponent } from './glosario.component';



@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: '',
        component: GlosarioComponent
      }
    ])
  ],
  exports: [
    RouterModule
  ]
})
export class GlosarioRoutingModule {
}