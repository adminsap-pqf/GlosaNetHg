import {NgModule}     from '@angular/core';
import {RouterModule} from '@angular/router';
import { ConfiguracionesComponent } from './configuraciones.component';



@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: '', 
                component: ConfiguracionesComponent
            }
        ])
    ],
    exports: [
        RouterModule
    ]
})
export class ConfiguracionesRoutingModule {
}