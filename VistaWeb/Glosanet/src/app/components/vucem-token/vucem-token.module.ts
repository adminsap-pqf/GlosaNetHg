import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PipeModule } from '../../pipes/accounting/accounting.module';
import { VucemTokenService } from '../../services/vucem-token/vucem-token.service';
import { VucemTokenComponent } from './vucem-token.component';
import { VucemTokenRoutingModule } from './vucem-token-routing.module';


@NgModule({
    declarations: [
        VucemTokenComponent
    ],
    imports: [
        CommonModule,
        FormsModule,
        PipeModule,
        VucemTokenRoutingModule
    ],
    providers: [
        VucemTokenService
    ]
})
export class VucemTokenModule { }
