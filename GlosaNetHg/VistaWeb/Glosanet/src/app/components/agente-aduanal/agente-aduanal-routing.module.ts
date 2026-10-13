import {NgModule}     from '@angular/core';
import {RouterModule} from '@angular/router';
import { AgenteAduanalComponent } from './agente-aduanal.component';



@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: '', 
                component: AgenteAduanalComponent
            }
        ])
    ],
    exports: [
        RouterModule
    ]
})
export class AgenteAduanalRoutingModule {
}