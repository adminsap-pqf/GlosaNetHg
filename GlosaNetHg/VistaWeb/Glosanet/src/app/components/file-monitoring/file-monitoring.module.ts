import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DatePickerModule } from '../shared/date-picker/date-picker.module';
import { DropListModule } from '../shared/drop-list/drop-list.module';
import { PipeModule } from '../../pipes/accounting/accounting.module';
import { SearchBarModule } from '../shared/search-bar/search-bar.module';
import { VirtualScrollerModule } from 'ngx-virtual-scroller';
import { FileMonitoringComponent } from './file-monitoring.component';
import { FileMonitoringRoutingModule } from './file-monitoring-routing.module';


@NgModule({
    declarations: [
        FileMonitoringComponent
    ],
    imports: [
        CommonModule,
        DatePickerModule,
        DropListModule,
        FileMonitoringRoutingModule,
        PipeModule,
        SearchBarModule,
        VirtualScrollerModule,
    ]
})
export class FileMonitoringModule { }
