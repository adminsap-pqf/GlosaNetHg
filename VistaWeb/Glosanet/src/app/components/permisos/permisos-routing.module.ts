import {NgModule}     from '@angular/core';
import {RouterModule} from '@angular/router';
import {PermisosComponent} from "./permisos.component";
import {HistorialImportacionesComponent} from "../historial-importaciones/historial-importaciones.component";
import { PrevisionComponent } from '../prevision/prevision.component';
import { MonitoreoPrevisionComponent } from '../monitoreo-prevision/monitoreo-prevision.component';
import { ListadoProductosComponent } from '../listado-productos/listado-productos.component';
import { AgregarProductoComponent } from '../agregar-producto/agregar-producto.component';


@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: '',
                component: PermisosComponent
            },
            {
                path:'historialImportacion/:id',
                component: HistorialImportacionesComponent
            },
            
            {
                path:'prevision',
                component: PrevisionComponent
            },

            {
                path:'monitoreo/:id',
                component: MonitoreoPrevisionComponent
            },
            {
                path:'listadoProductos/:id',
                component: ListadoProductosComponent
            },
            {
                path:'agregarProducto/:id',
                component: AgregarProductoComponent
            },
            
            /* {
                path:'NuevaPrevision',
                component: NuevaPrevisionComponent
            }, */
            /* {
              path: 'newForecast',
              component: NewForecastComponent
            },
            {
              path: 'forecastNotice',
              component: ForecastNoticeComponent
            } */

        ])
    ],
    exports: [
        RouterModule
    ]
})
export class PermisosRoutingModule {
}
