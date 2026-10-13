import { CoreContainerComponent } from './components/core-container/core-container.component';
import { ModuleWithProviders } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LibreriaDatastageComponent } from './components/libreria-datastage/libreria-datastage.component';
import { VistaCalendarioComponent } from './components/libreria-datastage/vista-calendario/vista-calendario.component';
import { ReportesPersonalizadosComponent } from './components/reportes-personalizados/reportes-personalizados.component';
import { CatalogoReportesComponent } from './components/reportes-personalizados/catalogo-reportes/catalogo-reportes.component';
import { LoginComponent } from './components/login/login.component';
import { CatalogoProductosComponent } from './components/catalogo-productos/catalogo-productos.component';

import { AuthGuard } from './guards/auth.guard';

const APP_ROUTES: Routes = [
  { path: '', component: LoginComponent },
  {
    path: 'protected', component: CoreContainerComponent, canActivate: [AuthGuard],
    children: [
      // { path: '', component: MainComponent },
      { path: '', component: LibreriaDatastageComponent },
      { path: 'datastagelibrary', component: LibreriaDatastageComponent },
      { path: 'datastageCalendar', component: VistaCalendarioComponent },
      {
        path: 'pedimentos/:id',
        loadChildren: () => import('../app/components/libreria-datastage/pedimento/pedimento.module').then(m => m.PedimentoModule)
      },
      {
        path: 'detallePedimento',
        loadChildren: () => import('../app/components/libreria-datastage/detalle-pedimento/detalle-pedimento.module').then(m => m.DetallePedimentoModule)
      },
      { path: 'reportesPersonalizados/:id', component: ReportesPersonalizadosComponent },
      { path: 'catalogoReportes', component: CatalogoReportesComponent },
      {
        path: 'preauditoria',
        loadChildren: () => import('../app/components/preauditoria/preauditoria.module').then(m => m.PreauditoriaModule)
      },
      {
        path: 'reportesAmedida',
        loadChildren: () => import('../app/components/reportes-amedida/reportes-amedida.module').then(m => m.ReportesAmedidaModule)
      },
      {
        path: 'glosario',
        loadChildren: () => import('../app/components/glosario/glosario.module').then(m => m.GlosarioModule)
      },
      {
        path: 'tablero',
        loadChildren: () => import('../app/components/tablero/tablero.module').then(m => m.TableroModule)
      },
      {
        path: 'agenteAduanal',
        loadChildren: () => import('../app/components/agente-aduanal/agente-aduanal.module').then(m => m.AgenteAduanalModule)
      },
      {
        path: 'fileMonitoring',
        loadChildren: () => import('../app/components/file-monitoring/file-monitoring.module').then(m => m.FileMonitoringModule)
      },
      {
        path: 'buscarPedimentos',
        loadChildren: () => import('../app/components/search-pedimento/search-pedimento.module').then(m => m.SearchPedimentoModule)
      },
      {
        path: 'buscarFacturas',
        loadChildren: () => import('../app/components/search-factura/search-factura.module').then(m => m.SearchFacturaModule)
      },
      {
        path: 'buscarE_doc',
        loadChildren: () => import('../app/components/search-edoc/search-edoc.module').then(m => m.SearchEdocModule)
      },
      {
        path: 'libreriaRegistros',
        loadChildren: () => import('../app/components/libreria-registros/libreria-registros.module').then(m => m.LibreriaRegistrosModule)
      },
      { path: 'catalogoProductos', component: CatalogoProductosComponent },
      {
        path: 'permisos',
        loadChildren: () => import('../app/components/permisos/permisos.module').then(m => m.PermisosModule)
      },
      {
        path: 'libreriaPermisos',
        loadChildren: () => import('../app/components/libreria-permisos/libreria-permisos.module').then(m => m.LibreriaPermisosModule)
      },
      {
        path: 'configuraciones',
        loadChildren: () => import('../app/components/configuraciones/configuraciones.module').then(m => m.ConfiguracionesModule)
      },
      {
        path: 'token',
        loadChildren: () => import('../app/components/vucem-token/vucem-token.module').then(m => m.VucemTokenModule)
      },
      {
        path: 'productos',
        loadChildren: () => import('./components/products/products.module').then(m => m.ProductsModule)
      },
      { path: '**', redirectTo: 'protected' }
    ]
  },
  { path: '**', redirectTo: '' }
];

export const routing: ModuleWithProviders = RouterModule.forRoot(APP_ROUTES);
