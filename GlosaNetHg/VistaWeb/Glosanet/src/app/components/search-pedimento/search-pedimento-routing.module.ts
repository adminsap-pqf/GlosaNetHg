import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SearchPedimentoComponent } from './search-pedimento.component';


@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: '',
                component: SearchPedimentoComponent
            }
        ])
    ],
    exports: [
        RouterModule
    ]
})
export class SearchPedimentoRoutingModule { }
