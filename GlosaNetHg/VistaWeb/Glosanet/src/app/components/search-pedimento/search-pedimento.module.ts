import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DatePickerModule } from '../shared/date-picker/date-picker.module';
import { DropListModule } from '../shared/drop-list/drop-list.module';
import { DatastageTableModule } from '../shared/datastage-table/datastage-table.module';
import { DatastageService } from '../../services/datastage/datastage.service';
import { SearchPedimentoComponent } from './search-pedimento.component';
import { SearchPedimentoRoutingModule } from './search-pedimento-routing.module';


@NgModule({
    declarations: [
        SearchPedimentoComponent
    ],
    imports: [
        CommonModule,
        FormsModule,
        DatePickerModule,
        DropListModule,
        DatastageTableModule,
        SearchPedimentoRoutingModule
    ],
    providers: [
        DatastageService
    ]
})
export class SearchPedimentoModule { }
