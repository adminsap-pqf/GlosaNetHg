import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SearchFacturaComponent } from './search-factura.component';


@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: '',
                component: SearchFacturaComponent
            }
        ])
    ],
    exports: [
        RouterModule
    ]
})
export class SearchFacturaRoutingModule { }
