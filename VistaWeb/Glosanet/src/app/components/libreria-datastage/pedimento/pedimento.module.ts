import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DatePickerModule } from '../../shared/date-picker/date-picker.module';
import { DropListModule } from '../../shared/drop-list/drop-list.module';
import { PipeModule } from '../../../pipes/accounting/accounting.module';
import { SearchBarModule } from '../../shared/search-bar/search-bar.module';
import { DatastageTableModule } from '../../shared/datastage-table/datastage-table.module';
import { DatastageService } from '../../../services/datastage/datastage.service';
import { PedimentoComponent } from './pedimento.component';
import { PedimentoRoutingModule } from './pedimento-routing.module';


@NgModule({
    declarations: [
        PedimentoComponent
    ],
    imports: [
        CommonModule,
        DatePickerModule,
        DropListModule,
        PipeModule,
        SearchBarModule,
        DatastageTableModule,
        PedimentoRoutingModule
    ],
    providers: [
        DatastageService
    ]
})
export class PedimentoModule { }
