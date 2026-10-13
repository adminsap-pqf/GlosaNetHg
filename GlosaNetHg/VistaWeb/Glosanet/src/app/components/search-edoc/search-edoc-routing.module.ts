import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SearchEdocComponent } from './search-edoc.component';


@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: '',
                component: SearchEdocComponent
            }
        ])
    ],
    exports: [
        RouterModule
    ]
})
export class SearchEdocRoutingModule {
}
