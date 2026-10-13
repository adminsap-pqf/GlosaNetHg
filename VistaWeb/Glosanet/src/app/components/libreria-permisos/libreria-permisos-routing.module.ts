import { NgModule } from "@angular/core";
import { RouterModule } from "@angular/router";
import { LibreriaPermisosComponent } from "./libreria-permisos.component";
import { MonitoreoPermisosComponent } from "../monitoreo-permisos/monitoreo-permisos.component";
import { NuevoPermisoComponent } from "../nuevo-permiso/nuevo-permiso.component";



@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: '',
                component: LibreriaPermisosComponent
            },
            {
                path:'monitoreoPermisos/:id',
                component: MonitoreoPermisosComponent
            },
            
            {
                path:'nuevoPermiso',
                component: NuevoPermisoComponent
            },

        ])
    ],
    exports: [
        RouterModule
    ]
})
export class LibreriaPermisosRoutingModule {
}