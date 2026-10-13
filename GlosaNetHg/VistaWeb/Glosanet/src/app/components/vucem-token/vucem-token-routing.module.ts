import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { VucemTokenComponent } from './vucem-token.component';


@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: '',
                component: VucemTokenComponent
            }
        ])
    ],
    exports: [
        RouterModule
    ]
})
export class VucemTokenRoutingModule {
}
