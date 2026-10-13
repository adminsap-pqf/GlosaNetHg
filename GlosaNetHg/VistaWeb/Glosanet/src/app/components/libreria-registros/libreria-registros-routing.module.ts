import {NgModule}     from '@angular/core';
import {RouterModule} from '@angular/router';
import { LibreriaRegistrosComponent } from './libreria-registros.component';
import { NuevoRegistroComponent } from '../nuevo-registro/nuevo-registro.component';
import { MonitoreoTramiteComponent } from '../monitoreo-tramite/monitoreo-tramite.component';
import { CatProductosComponent } from '../cat-productos/cat-productos.component';



@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: '', 
                component: LibreriaRegistrosComponent
            },
            {
                path:'nuevoRegistro',
                component: NuevoRegistroComponent
            },
            {
                path:'monitorearTramite/:id',
                component: MonitoreoTramiteComponent,
            },
            {
                path: 'catalogoProductos',
                component: CatProductosComponent
            }
        ])
    ],
    exports: [
        RouterModule
    ]
})
export class LibreriaRegistrosRoutingModule {
}