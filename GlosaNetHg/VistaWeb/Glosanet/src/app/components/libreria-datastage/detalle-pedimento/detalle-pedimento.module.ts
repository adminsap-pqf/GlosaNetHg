import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DatePickerModule } from '../../shared/date-picker/date-picker.module';
import { DropListModule } from '../../shared/drop-list/drop-list.module';
import { FiltroTiempoModule } from '../../shared/filtro-tiempo/filtro-tiempo.module';
import { PipeModule } from '../../../pipes/accounting/accounting.module';
import { SearchBarModule } from '../../shared/search-bar/search-bar.module';
import { DatastageTableModule } from '../../shared/datastage-table/datastage-table.module';
import { DatastageService } from '../../../services/datastage/datastage.service';
import { VucemTokenService } from '../../../services/vucem-token/vucem-token.service';
import { DetallePedimentoComponent } from './detalle-pedimento.component';
import { DetallePedimentoRoutingModule } from './detalle-pedimento-routing.module';


@NgModule({
    declarations: [
        DetallePedimentoComponent
    ],
    imports: [
        CommonModule,
        DatePickerModule,
        DropListModule,
        FiltroTiempoModule,
        PipeModule,
        SearchBarModule,
        DatastageTableModule,
        DetallePedimentoRoutingModule
    ],
    providers: [
        DatastageService,
        VucemTokenService
    ]
})
export class DetallePedimentoModule {
}
