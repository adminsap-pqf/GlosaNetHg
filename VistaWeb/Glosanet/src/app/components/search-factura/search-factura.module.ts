import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DatePickerModule } from '../shared/date-picker/date-picker.module';
import { DropListModule } from '../shared/drop-list/drop-list.module';
import { DatastageTableModule } from '../shared/datastage-table/datastage-table.module';
import { DatastageService } from '../../services/datastage/datastage.service';
import { VucemTokenService } from '../../services/vucem-token/vucem-token.service';
import { SearchFacturaComponent } from './search-factura.component';
import { SearchFacturaRoutingModule } from './search-factura-routing.module';


@NgModule({
    declarations: [
        SearchFacturaComponent
    ],
    imports: [
        CommonModule,
        FormsModule,
        DatePickerModule,
        DropListModule,
        DatastageTableModule,
        SearchFacturaRoutingModule
    ],
    providers: [
        DatastageService,
        VucemTokenService
    ]
})
export class SearchFacturaModule { }
