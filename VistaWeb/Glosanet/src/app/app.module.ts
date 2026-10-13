import {NavBarService} from './services/nav-bar/nav-bar.service';
import {BrowserDetectionService} from './services/browser-detection/browser-detection.service';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppComponent} from './app.component';
import {LoginComponent} from './components/login/login.component';
import {CatalogoProductosComponent} from './components/catalogo-productos/catalogo-productos.component';
import {CoreContainerComponent} from './components/core-container/core-container.component';
import {NavBarComponent} from './components/core-container/nav-bar/nav-bar.component';
import {NavItemComponent} from './components/core-container/nav-bar/nav-item/nav-item.component';
import {SubMenuComponent} from './components/core-container/nav-bar/sub-menu/sub-menu.component';
import {FooterComponent} from './components/core-container/footer/footer.component';
import {UserBarComponent} from './components/core-container/user-bar/user-bar.component';
import {SubMenuItemComponent} from './components/core-container/nav-bar/sub-menu/sub-menu-item/sub-menu-item.component';
import {FilterListComponent} from './components/shared/filter-list/filter-list.component';
import {CheckBoxComponent} from './components/shared/check-box/check-box.component';
import {OptionListComponent} from './components/shared/option-list/option-list.component';
import {routing} from './app.routing';
import {HeaderBarService} from './services/header-bar/header-bar.service';
import {MenuUserComponent} from './components/shared/menu-user/menu-user.component';
import {LibreriaDatastageComponent} from './components/libreria-datastage/libreria-datastage.component';
import {CardDatastageComponent} from './components/shared/card-datastage/card-datastage.component';
import {GridCardContainerComponent} from './components/shared/grid-card-container/grid-card-container.component';
import {LibreriaDatastageService} from './services/libreria-datastage/libreria-datastage.service';
import {SessionUser} from './services/session/session.service';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {LoggingService} from './services/logging/logging.service';
import {CalendarComponent} from './components/shared/calendar/calendar.component';
import {GridCalendarComponent} from './components/shared/grid-calendar/grid-calendar.component';
import {VistaCalendarioComponent} from './components/libreria-datastage/vista-calendario/vista-calendario.component';
import {CardBitacoraComponent} from './components/shared/card-bitacora/card-bitacora.component';
import {DragulaModule} from 'ng2-dragula';
import {ReportesPersonalizadosComponent} from './components/reportes-personalizados/reportes-personalizados.component';
import {ReportePersonalizadoService} from './services/reporte-personalizado/reporte-personalizado.service';
import {CatalogoReportesComponent} from './components/reportes-personalizados/catalogo-reportes/catalogo-reportes.component';
import {CardCatalogoComponent} from './components/shared/card-catalogo/card-catalogo.component';
import {UserService} from './services/user/user.service';
import {AuthGuard} from './guards/auth.guard';
import {TableroHttpService} from './services/tablero/tableroHttp.service';
import {DropListModule} from './components/shared/drop-list/drop-list.module';
import {SearchBarModule} from './components/shared/search-bar/search-bar.module';
import {DatePickerModule} from './components/shared/date-picker/date-picker.module';
import {PipeModule} from './pipes/accounting/accounting.module';
import {PreauditoriaHttpService} from './services/preauditoria/preauditoriaHttp.service';
import {ReporteAmedidaService} from './services/reporte-amedida/reporte-amedida.service';
import {FiltroTiempoModule} from './components/shared/filtro-tiempo/filtro-tiempo.module';
import {WizardModule} from './components/shared/wizard/wizard.module';
import {AgenteAduanalHttpService} from './services/agente-aduanal/agente-aduanalHttp.service';
import {GlosarioService} from './services/glosario/glosario.service';
import {DownloadHistoryModule} from './components/shared/download-history/download-history.module';
import {RegistrosService} from './services/registros/registros.service';
import {RadioButtonModule} from './components/shared/radio-button/radio-button.module';
import {CatalogoProductosService} from './services/catalogo-productos/catalogo-productos.service';
import {PermisosService} from './services/permisos/permisos.service';
import {PrevisionesService} from './services/previsiones/previsiones.service';
import {ClienteService} from './services/cliente/cliente.service';
import {EmpresaService} from './services/empresa/empresa.service';
import {ForecastService} from './services/forecast/forecast.service';
import {ProductoGeneralService} from './services/general/productos';
import {DiasHabilesService} from './services/general/diasHabiles';
import {AuthenticationService} from './services/authentication/authentication.service';
import {AuthInterceptor} from './services/helpers/auth.interceptor';
import {ErrorInterceptor} from './services/helpers/error.interceptor';
import {FileMonitoringService} from './services/file-monitoring/file-monitoring.service';
import {CatalogoService} from './services/catalogo/catalogo.service';
import { ModalComponent } from './components/shared/modal/modal.component';
import {MatDialogModule} from '@angular/material/dialog';
import {MatTableModule} from '@angular/material/table';
import {MatPaginatorModule} from '@angular/material/paginator';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    CoreContainerComponent,
    NavBarComponent,
    NavItemComponent,
    SubMenuComponent,
    FooterComponent,
    UserBarComponent,
    SubMenuItemComponent,
    FilterListComponent,
    CheckBoxComponent,
    OptionListComponent,
    MenuUserComponent,
    LibreriaDatastageComponent,
    CardDatastageComponent,
    GridCardContainerComponent,
    CalendarComponent,
    GridCalendarComponent,
    VistaCalendarioComponent,
    CardBitacoraComponent,
    ReportesPersonalizadosComponent,
    CatalogoReportesComponent,
    CardCatalogoComponent,
    CatalogoProductosComponent,
    ModalComponent
  ],
  imports: [
    BrowserModule,
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    routing,
    HttpClientModule,
    DragulaModule.forRoot(),
    DropListModule,
    DownloadHistoryModule,
    SearchBarModule,
    DatePickerModule,
    PipeModule,
    FiltroTiempoModule,
    WizardModule,
    RadioButtonModule,
    MatDialogModule,
    MatTableModule,
    MatPaginatorModule
  ],
  providers: [
    HeaderBarService,
    LoggingService,
    BrowserDetectionService,
    NavBarService,
    SessionUser,
    LibreriaDatastageService,
    ReportePersonalizadoService,
    UserService,
    AuthGuard,
    TableroHttpService,
    PreauditoriaHttpService,
    GlosarioService,
    ReporteAmedidaService,
    AgenteAduanalHttpService,
    UserBarComponent,
    RegistrosService,
    CatalogoProductosService,
    PermisosService,
    PrevisionesService,
    ClienteService,
    EmpresaService,
    PrevisionesService,
    PrevisionesService,
    ForecastService,
    ProductoGeneralService,
    DiasHabilesService,
    AuthenticationService,
    FileMonitoringService,
    CatalogoService,
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
  ],
  entryComponents: [
    SubMenuComponent,
    ModalComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
