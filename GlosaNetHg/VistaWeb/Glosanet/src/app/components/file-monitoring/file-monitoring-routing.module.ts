import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { FileMonitoringComponent } from './file-monitoring.component';


@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: '',
                component: FileMonitoringComponent
            }
        ])
    ],
    exports: [
        RouterModule
    ]
})
export class FileMonitoringRoutingModule { }
