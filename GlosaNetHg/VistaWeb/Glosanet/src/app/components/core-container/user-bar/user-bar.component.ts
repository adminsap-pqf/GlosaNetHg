import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {HeaderBarService} from './../../../services/header-bar/header-bar.service';
import {UserService} from '../../../services/user/user.service';
import {SessionUser} from '../../../services/session/session.service';
import { FileMonitoringService } from "../../../services/file-monitoring/file-monitoring.service";
import {Parametros} from '../../../model/parametros.class';
import {ReportePersonalizadoService} from '../../../services/reporte-personalizado/reporte-personalizado.service';
import {getFileNameFromResponseContentDisposition} from './file-download-helper';
import {Subscription} from 'rxjs';
/* Dev Tools */
import {DEBUG} from "../../../settings/app.settings";
import {ROUTE_FILES} from "../../../util/common.protocols";
import {HttpEvent, HttpEventType} from "@angular/common/http";

@Component({
  selector: 'gn-user-bar',
  templateUrl: './user-bar.component.html',
  styleUrls: ['./user-bar.component.scss']
})

export class UserBarComponent implements OnInit {

  realTime: any;
  realTime2: any;
  items: any = [{nombre: 'nombre1'}, {nombre: 'nombre2'}, {nombre: 'nombre3'}, {nombre: 'nombre4'}, {nombre: 'nombre5'}];
  user: any = {
    usuario: '', nombre: '', rol: '',
    rfc: ['']
  };
  cargando: Boolean = false;
  divSession = 'NoShowCloseSession';
  messageOk: String = "";
  tError: String = "";
  sError: String = "";
  listPermisos: any = [];
  subscription: Subscription;
  noDisponible: string = '';
  testing: string = '¡Hay un error <span style="color: #3B8EDE; font-size: 21px; font-weight: 400;">Exitosamente!</span>';
  progress:number = 0;

  constructor(
    private headerService: HeaderBarService,
    private router: Router,
    private userService: UserService,
    private reportePersonalizado: ReportePersonalizadoService,
    private fileMonitoringService: FileMonitoringService) { }

  ngOnInit() {

    this.obtenerPermisos();
    let rfcEmpresas = [];
    DEBUG && console.log(SessionUser.getInstance().getCliente().empresas);
    for (let item of SessionUser.getInstance().getCliente().empresas) {
      rfcEmpresas.push(item.rfc);
    }

    let userAux = {
      usuario: SessionUser.getInstance().getUsuario().usuario,
      nombre: SessionUser.getInstance().getUsuario().nombre + ' ' + SessionUser.getInstance().getUsuario().apellido,
      rol: SessionUser.getInstance().getUsuario().permiso,
      empresas: SessionUser.getInstance().getCliente().empresas,
      rfc: rfcEmpresas,
      dof: SessionUser.getInstance().getDof()
    };

    this.user = userAux;


    this.subscription = this.userService.sinDatos.subscribe(
      (data) => {
        DEBUG && console.log('Subscription');
        this.noDisponible = data;
      }
    );


  }

  obtenerUsuarioActual() {
    this.userService.obtenerUsuarioActual()
      .subscribe(
        data => {
          DEBUG && console.log(data);
          SessionUser.getInstance().setNombreCompleto(data.usuario.nombre + " " + data.usuario.apellido);
          SessionUser.getInstance().setIdClient(data.cliente.idCliente);
          SessionUser.getInstance().setRfc(data.cliente.empresas[0].rfc);
          SessionUser.getInstance().setUsuario(data.usuario);
          SessionUser.getInstance().setCliente(data.cliente);
          SessionUser.getInstance().setDof(data.dof);
          SessionUser.getInstance().setidEmpresaActual(data.usuario.cliente.idEmpresaActual);
          SessionUser.getInstance().setNumDataStage(data.cliente.empresas[0].numDataStage);

          let rfcEmpresas = [];
          DEBUG && console.log(SessionUser.getInstance().getCliente().empresas);
          for (let item of SessionUser.getInstance().getCliente().empresas) {
            rfcEmpresas.push(item.rfc);
          }

          let userAux = {
            usuario: SessionUser.getInstance().getUsuario().usuario,
            nombre: SessionUser.getInstance().getUsuario().nombre + ' ' + SessionUser.getInstance().getUsuario().apellido,
            rol: SessionUser.getInstance().getUsuario().permiso.permiso,
            empresas: SessionUser.getInstance().getCliente().empresas,
            rfc: rfcEmpresas,
            dof: SessionUser.getInstance().getDof()
          }


          this.user = Object.assign({}, userAux);
        },
        error => {

        }
      );
  }

  ngDoCheck() {
    if (SessionUser.getInstance().getLoading()) {
      document.getElementById('idLoading').style.display = 'block'
    } else {
      document.getElementById('idLoading').style.display = 'none';

    }


  }

  toggleDivSession() {
    if (this.divSession === 'NoShowCloseSession') {
      this.divSession = 'ShowCloseSession';
    } else {
      this.divSession = 'NoShowCloseSession';
    }
  }

  closeSession() {
    window.location.href = window.location.origin + '/petroflex';
  }

  hostClick(event) {
    if (this.divSession === 'ShowCloseSession' && !(event.target.id === 'cerrar')) {
      this.divSession = 'NoShowCloseSession';
    }
  }

  filterSelected($event) {
    DEBUG && console.log($event);
  }

  iniciarCheckInRealTime(idPeticion: any) {
    if (idPeticion > 0) {
      SessionUser.getInstance().getOpenDescargas().visible = true;
      this.realTime = setInterval(() => this.checkInRealTime(idPeticion), 5000);
    }
  }

  checkInRealTime(idPeticion: any) {
    let parametros: Parametros = new Parametros();
    parametros.id = idPeticion;
    this.reportePersonalizado.obtenerLigasDescargasReportes(parametros).subscribe(
      data => {
        DEBUG && console.log(data);
        if (data && data.mediaValue) {
          clearInterval(this.realTime);
          const fileName = getFileNameFromResponseContentDisposition(data.mediaValue);
          SessionUser.getInstance().getListDescargas()[SessionUser.getInstance().getListDescargas().length - 1].porcentaje = "100%";
          SessionUser.getInstance().getListDescargas()[SessionUser.getInstance().getListDescargas().length - 1].fileName = fileName;
          SessionUser.getInstance().getListDescargas()[SessionUser.getInstance().getListDescargas().length - 1].subText = SessionUser.getInstance().getOriegnDescarga();
          SessionUser.getInstance().getListDescargas()[SessionUser.getInstance().getListDescargas().length - 1].class = "";
          let element = document.createElement('a');
          element.setAttribute('href', ROUTE_FILES + data.mediaValue);

          element.setAttribute('download', 'Reporte.xls');//se hace cambio para poder descargar reporte a medida.
          //element.setAttribute('target', '_blank');
          element.style.display = 'none';
          document.body.appendChild(element);
          element.click();
          SessionUser.getInstance().getOpenDescargas().visible = true;
          document.body.removeChild(element);
          SessionUser.getInstance().getOpenDescargas().descargando = false;
        }
      },
      error => {
        DEBUG && console.log("error");
        DEBUG && console.log(error);
        SessionUser.getInstance().getListDescargas()[SessionUser.getInstance().getListDescargas().length - 1].class = "error";
        SessionUser.getInstance().setMessageFailed('al descargar el reporte');
        document.getElementById('requestFailed').style.display = 'block';
      }
    );
  }

  ngOnDestroy() {
    clearInterval(this.realTime);
    clearInterval(this.realTime2);
  }

  fileChange1($event) {
    DEBUG && console.log($event);

    if (Number(SessionUser.getInstance().getCliente().numeroPedimentos) < SessionUser.getInstance().getCliente().paquete.pedimentos) {

      var file: File = $event.target.files[0];
      let parametros: Parametros = new Parametros();
      parametros.file = file;
      parametros.tipo = file.name;
      parametros.usuario = SessionUser.getInstance().getUsuario();

      this.cargando = true;
      this.headerService.subirArchivoDataStage(
        file,
        file.name, SessionUser.getInstance().getUsuario().idUsuario,
        SessionUser.getInstance().getUsuario().cliente.idCliente,
        SessionUser.getInstance().getUsuario().cliente.rfcActual
      ).subscribe(
        (event: HttpEvent<any>) => {
          /*if (data && data.mediaValue) {
            this.cargando = true;
            this.realTime2 = setInterval(() => this.checkInRealTimeDataStge(data.mediaValue), 5000);
          }*/
          switch (event.type) {
            case HttpEventType.Sent:
              DEBUG && console.log('Request has been made!');
              break;
            case HttpEventType.ResponseHeader:
              DEBUG && console.log('Response header has been received!');
              break;
            case HttpEventType.UploadProgress:
              this.progress = Math.round(event.loaded / event.total * 100);
              DEBUG && console.log(`Uploaded! ${this.progress}%`);
              break;
            case HttpEventType.Response:
              DEBUG && console.log('Successfully!', event.body);

              if (event.body && event.body.mediaValue) {
                this.messageOk = "El Datastage se subio";
                SessionUser.getInstance().setMessageOk(this.messageOk);
                document.getElementById('idOk').style.display = 'block';
                setTimeout(() => {
                  this.progress = 0;
                  this.cargando = false;
                  this.obtenerUsuarioActual();
                }, 1000);

              } else {
                this.getError();
              }
              break;
          }
        },
        error => {
          DEBUG && console.log("Error Subir archivos");
          this.getError();
        }
      );
    } else {
      this.getError();
    }
    DEBUG && console.log(file);
  }

  checkInRealTimeDataStge(idPeticion: any) {
    let parametros: Parametros = new Parametros();
    parametros.id = idPeticion;

    this.headerService.obtenerDataStageRespuesta(parametros).subscribe(
      data => {
        DEBUG && console.log(data);
        if (data && data.mediaValue) {
          clearInterval(this.realTime2);
          this.cargando = false;
          let auxArray = data.mediaValue.split("|");
          DEBUG && console.log(auxArray[1]);
          if (auxArray[1] == "") {
            this.messageOk = "Datastage cargado";
            SessionUser.getInstance().setMessageOk(this.messageOk);
            document.getElementById('idOk').style.display = 'block';

            setTimeout(() => this.obtenerUsuarioActual(), 3000);
          } else {
            this.tError = "Error al cargar el Datastage";
            let stringAux = auxArray[1].replace("{Resumen=, ", "");
            stringAux = stringAux.replace("}", "");
            this.sError = stringAux;
            document.getElementById('idError').style.display = 'block'
          }
        }
      },
      error => {
        DEBUG && console.log("error");
        DEBUG && console.log(error);
        this.cargando = false;
      }
    );
  }

  confirmRes(res: boolean) {

  }


  getMessage() {
    return SessionUser.getInstance().getMessageOk();
  }

  obtenerPermisos() {

    for (let item of SessionUser.getInstance().getAdministrador()) {
      if (item.idUsuario == SessionUser.getInstance().getUsuario().idUsuario) {
        this.listPermisos = item.permisos;
        break;
      }
    }

    this.listPermisos = this.listPermisos.sort((a, b) => (a.permiso.idPermiso < b.permiso.idPermiso) ? -1 : 1);
    DEBUG && console.log(this.listPermisos);
  }


  informacionNoDisponible() {
    this.tError = 'No hay información disponible';
  }

  getMessageFailed() {
    return SessionUser.getInstance().getMessageFailed();
  }

  redirectToMonitoreo(){
    document.getElementById('idOk').style.display = 'none';
    this.router.navigate(['protected/fileMonitoring']).then( () => {
      this.fileMonitoringService.handleRealoadFileMonitoring(this.router.url);
    });
  }

  getError() {
    this.tError = "Error al subir el Datastage";
    this.sError = "El servidor no puede procesar esta solicitud.";
    document.getElementById('idError').style.display = 'block';
    this.cargando = false;
    this.progress = 0;
  }

}
