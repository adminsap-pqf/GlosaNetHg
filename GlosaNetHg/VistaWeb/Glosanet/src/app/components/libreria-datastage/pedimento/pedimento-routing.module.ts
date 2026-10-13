import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { PedimentoComponent } from './pedimento.component';


@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: '',
                component: PedimentoComponent
            }
        ])
    ],
    exports: [
        RouterModule
    ]
})
export class PedimentoRoutingModule { }
