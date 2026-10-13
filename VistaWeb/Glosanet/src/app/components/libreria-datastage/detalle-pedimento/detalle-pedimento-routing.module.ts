import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { DetallePedimentoComponent } from './detalle-pedimento.component';


@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: '',
                component: DetallePedimentoComponent
            }
        ])
    ],
    exports: [
        RouterModule
    ]
})
export class DetallePedimentoRoutingModule { }
