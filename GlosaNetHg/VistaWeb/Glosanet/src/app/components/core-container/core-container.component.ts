import {Component, ElementRef, OnInit, OnDestroy, ViewChild} from '@angular/core';
import {NavItem} from './nav-bar/';
import {Event as NavigationEvent, NavigationStart, Router} from '@angular/router';
import {SessionUser} from '../../services/session/session.service';
import {RegistrosService} from '../../services/registros/registros.service';
import {UserService} from '../../services/user/user.service';
import {AuthenticationService} from '../../services/authentication/authentication.service';
import * as accounting from 'accounting';

/* Dev Tools */
import {DEBUG} from '../../settings/app.settings';
import {SearchPedimentoComponent} from '../search-pedimento/search-pedimento.component';
import {SearchFacturaComponent} from '../search-factura/search-factura.component';

/* import { ScrollEvent } from 'ngx-scroll-event'; */

@Component({
  selector: 'gn-core-container',
  templateUrl: './core-container.component.html',
  styleUrls: ['./core-container.component.scss']
})
export class CoreContainerComponent implements OnInit, OnDestroy {

  @ViewChild('scrollMe', {static: false}) private myScrollContainer: ElementRef;

  /* Clases dinámicas */
  componentTranslateAnimation = 'originPosition';
  /* Items del menú*/
  navItems: Array<NavItem>;

  listPermisos: any[] = [];

  intervalFunc: any = null;
  showWarningModal: boolean = false;
  message: string = 'La sesión ha expirado';
  remainingTime: number = 0;
  minutes: string = '00';
  seconds: string = '00';
  warnTimeout: any;

  constructor(
    public router: Router,
    private registroService: RegistrosService,
    private userService: UserService,
    private authService: AuthenticationService) {
    router.events.forEach((event: NavigationEvent) => {
      if (event instanceof NavigationStart) {
        DEBUG && console.log('Deteccion de cambios de ruta!!!');
        this.scrollToBottom();
      }
    });
  }

  ngOnInit() {
    this.obtenerPermisos();
    this.scrollToBottom();
    this.createNavItems();
    this.obtenerDiasFeriados();

    if (SessionUser.getInstance().getEndTime()) {
      this.createTimeouts(SessionUser.getInstance().getEndTime());
    }

  }

  fillSubMenu() {
    let aux = [];
    if (this.listPermisos[2].activo) {
      aux.push({
        section: true,
        key: 'protected/datastagelibrary',
        iconPath: './assets/images/asset_2.svg',
        translatedName: 'Librería DataStages',
        itemsCount: 0
      });
      aux.push({
        section: true,
        key: 'protected/fileMonitoring',
        iconPath: './assets/images/asset_2.svg',
        translatedName: 'Monitor de Archivos',
        itemsCount: 0
      });
    }
    if (this.listPermisos[3].activo) {
      aux.push({
        section: true,
        key: 'protected/tablero',
        iconPath: './assets/images/asset_1.svg',
        translatedName: 'Tableros',
        itemsCount: 0
      });
    }
    if (this.listPermisos[4].activo) {
      aux.push({
        section: true,
        key: 'protected/preauditoria',
        iconPath: './assets/images/asset_3.svg',
        translatedName: 'Reportes Prediseñados',
        itemsCount: 0
      });
    }
    if (this.listPermisos[5].activo) {
      aux.push({
        section: true,
        key: 'protected/agenteAduanal',
        iconPath: './assets/images/asset_4.svg',
        translatedName: 'Agente Aduanal',
        itemsCount: 0
      });
    }
    if (this.listPermisos[6].activo) {
      aux.push({
        section: true,
        key: 'protected/reportesPersonalizados/0',
        iconPath: './assets/images/asset_5.svg',
        translatedName: 'Reportes Personalizados',
        itemsCount: 0
      });
    }
    if (this.listPermisos[7].activo) {
      aux.push({
        section: true,
        key: 'protected/glosario',
        iconPath: './assets/images/asset_6.svg',
        translatedName: 'Glosario',
        itemsCount: 0
      });
    }
    if (this.listPermisos[8].activo) {
      aux.push({
        section: SessionUser.getInstance().getCliente().idCliente == 5 ? true : false,
        key: 'protected/reportesAmedida',
        iconPath: './assets/images/asset_7.svg',
        translatedName: 'Reportes A Medida',
        itemsCount: 0
      });
    }

    if (this.listPermisos[11].activo) {
      aux.push({
        section: true,
        key: 'protected/buscarPedimentos',
        iconPath: './assets/images/asset_2.svg',
        translatedName: 'Buscar Pedimento',
        itemsCount: 0
      });
      aux.push({
        section: true,
        key: 'protected/buscarFacturas',
        iconPath: './assets/images/asset_2.svg',
        translatedName: 'Buscar Factura',
        itemsCount: 0
      });
      aux.push({
        section: true,
        key: 'protected/buscarE_doc',
        iconPath: './assets/images/asset_2.svg',
        translatedName: 'Buscar E-doc',
        itemsCount: 0
      });
    }

    return aux;
  }

  createNavItems() {

    this.navItems = [
      {
        section: true,
        key: 'DataStage',
        iconPath: './assets/images/1_datastages.svg',
        translatedName: 'DataStage',
        subMenuComponent: 'menu',
        subMenuTitle: 'DataStage',
        subMenuIcon: './assets/images/1_datastages.svg',
        subMenuItems: this.fillSubMenu()

      },
      {
        section: this.listPermisos[9].activo,
        key: 'Registros',
        iconPath: './assets/images/3_registros.svg',
        translatedName: 'Registros',
        subMenuComponent: 'menu',
        subMenuTitle: 'Registros',
        subMenuIcon: './assets/images/3_registros.svg',
        subMenuItems: [
          {
            section: true,
            key: 'protected/libreriaRegistros',
            iconPath: './assets/images/asset_7.svg',
            translatedName: 'Librería Registros',
            itemsCount: 0
          }
        ]
      },

      {
        section: this.listPermisos[10].activo,
        key: 'Permisos',
        iconPath: './assets/images/4_permisos.svg',
        translatedName: 'Permisos',
        subMenuComponent: 'menu',
        subMenuTitle: 'Permisos',
        subMenuIcon: './assets/images/4_permisos.svg',
        subMenuItems: [

          {
            section: true,
            key: 'protected/permisos',
            iconPath: './assets/images/asset_7.svg',
            translatedName: 'Librería Previsión',
            itemsCount: ''
          },
          {
            section: true,
            key: 'protected/libreriaPermisos',
            iconPath: './assets/images/asset_7.svg',
            translatedName: 'Librería Permisos',
            itemsCount: ''
          }
        ]
      },

      {
        section: this.listPermisos[2].activo,
        key: 'Token',
        iconPath: './assets/images/token/key_menu.svg',
        translatedName: 'Token',
        subMenuComponent: 'menu',
        subMenuTitle: 'Token',
        subMenuIcon: './assets/images/token/key_menu.svg',
        subMenuItems: [
          {
            section: true,
            key: 'protected/token',
            iconPath: './assets/images/asset_7.svg',
            translatedName: 'Administración de Token',
            itemsCount: ''
          }
        ]
      }
    ];
  }

  listenNavBarChanges($event) {
    if ($event.menuEvent === 'open') {
      if ($event.state === 'peek') {
        this.componentTranslateAnimation = 'originPosition';
      } else if ($event.state === 'opened') {
        this.componentTranslateAnimation = 'openedPosition';
      }
    } else if ($event.menuEvent === 'close') {
      this.componentTranslateAnimation = 'originPosition';
    }
  }

  scrollToBottom(): void {
    try {

      this.myScrollContainer.nativeElement.scrollTop = 0;
    } catch (err) {
      DEBUG && console.log(err);
    }
  }


  obtenerDiasFeriados() {
    this.registroService.obtenerFeriadosOficiales()
      .subscribe(
        data => {
          DEBUG && console.log(data);
          SessionUser.getInstance().setDiasFeriados(data);
          DEBUG && console.log(SessionUser.getInstance().getDiasFeriados());
        },
        error => {
          DEBUG && console.log('@obtenerDiasFeriados: failed', error);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al obtener dias festivos');
          document.getElementById('requestFailed').style.display = 'block';
        });
  }

  obtenerPermisos() {

    DEBUG && console.log("@obtenerPermisos: inicio");

    for (let item of SessionUser.getInstance().getAdministrador()) {
      if (item.idUsuario == SessionUser.getInstance().getUsuario().idUsuario) {
        this.listPermisos = item.permisos;
        break;
      }
    }

    this.listPermisos = this.listPermisos.sort((a, b) => (a.permiso.idPermiso < b.permiso.idPermiso) ? -1 : 1);
    DEBUG && console.log("@obtenerPermisos: this.listPermisos => " + this.listPermisos);
  }


  createTimeouts(endTime) {
    let startHour = new Date();
    endTime = new Date(endTime);

    let elapsedTime = (endTime.getTime() - startHour.getTime());

    if ( elapsedTime < 0 ) {
      this.remainingTime = 60000;
      this.warn('La sesión ha expirado');

    } else {
      this.remainingTime = 180000;
      this.warnTimeout = setTimeout(() => { this.warn(); }, elapsedTime);
    }
  }

  warn(message = 'La sesión ha expirado. Se cerrará automáticamente en') {
    this.showWarningModal = true;
    this.message = message;
    this.intervalFunc = setInterval(() => { this.doCountDown(); }, 1000);
  }

  doCountDown() {
    const remainingTime = this.remainingTime - 1000;
    if (remainingTime < 0) {
      this.closeSession();
      return;
    }
    this.remainingTime = remainingTime;
    let minutes = accounting.formatNumber(Math.floor(this.remainingTime / 60000), 0);
    minutes = minutes < 10 ? `0${minutes}` : minutes;
    let seconds = accounting.formatNumber(Math.floor((this.remainingTime % 60000) / 1000), 0);
    seconds = seconds < 10 ? `0${seconds}` : seconds;
    this.minutes = minutes;
    this.seconds = seconds;
  }

  closeSession() {
    clearInterval(this.intervalFunc);
    this.showWarningModal = false;
    this.authService.logout();
    this.router.navigate(['']);
    location.reload();
  }

  ngOnDestroy() {
    if (this.warnTimeout) {
      clearTimeout(this.warnTimeout);
    }
  }

}
