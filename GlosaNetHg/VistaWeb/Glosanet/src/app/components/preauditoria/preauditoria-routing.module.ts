import {NgModule}     from '@angular/core';
import {RouterModule} from '@angular/router';
import { PreauditoriaComponent } from './preauditoria.component';



@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: '', 
                component: PreauditoriaComponent
            }
        ])
    ],
    exports: [
        RouterModule
    ]
})
export class PreauditoriaRoutingModule {
}