import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { DatosGenerales } from '../../../model/datosGenerales.class';
import { Parametros } from '../../../model/parametros.class';
import { LibreriaDatastageService } from '../../../services/libreria-datastage/libreria-datastage.service';
import { SessionUser } from '../../../services/session/session.service';
import * as _ from 'lodash';

/* Dev Tools */
import { DEBUG } from '../../../settings/app.settings';

@Component({
  selector: 'gn-card-datastage',
  templateUrl: './card-datastage.component.html',
  styleUrls: ['./card-datastage.component.scss']
})
export class CardDatastageComponent implements OnInit {
  @Input() data: DatosGenerales;
  @Input() idCliente: String;
  @Input() rfc: String;

  hover:Boolean = false;

  listPermisos:any = [];
  constructor(
    private libreriaDSS: LibreriaDatastageService,
    private router: Router
  ) { }

  ngOnInit() {
    this.obtenerPermisos();
  }

  voltearTarjeta(){
    DEBUG && console.log(this.hover);
    this.hover = !this.hover;
  }

  obtenerArchivo() {
    SessionUser.getInstance().setLoading(true);
    let parametros: Parametros = new Parametros();
    parametros.archivos = [];
    parametros.nombres = [];
    parametros.archivos.push("Datastages/" + this.idCliente + "/" + this.rfc + "/" + this.data.dataStage.folio + ".zip");
    parametros.nombres.push(this.data.dataStage.folio + ".zip");

    DEBUG && console.log(parametros.archivos);
    DEBUG && console.log(parametros.nombres);
    this.libreriaDSS.obtenerArchivo(parametros).subscribe(
      data => {
        DEBUG && console.log(data);
        SessionUser.getInstance().setLoading(false);
        if (data) {
          let blob = this.b64toBlob(data, 'zip','');
          let blobUrl = URL.createObjectURL(blob);
          let element = document.createElement('a');
          element.setAttribute('href', blobUrl);
          element.setAttribute('download', this.data.dataStage.folio + ".zip");
          element.style.display = 'none';
          document.body.appendChild(element);
          element.click();
          document.body.removeChild(element);
        } else {
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al descargar el archivo');
          document.getElementById('requestFailed').style.display = 'block';
        }
      },
      error => {
        DEBUG && console.log('@obtenerArchivo: ', error);
        SessionUser.getInstance().setLoading(false);
        SessionUser.getInstance().setMessageFailed('al descargar el archivo');
        document.getElementById('requestFailed').style.display = 'block';
      }
    );
  }

  b64toBlob(b64Data, contentType, sliceSize) {
    contentType = contentType || '';
    sliceSize = sliceSize || 512;

    var byteCharacters = atob(b64Data);
    var byteArrays = [];

    for (var offset = 0; offset < byteCharacters.length; offset += sliceSize) {
      var slice = byteCharacters.slice(offset, offset + sliceSize);

      var byteNumbers = new Array(slice.length);
      for (var i = 0; i < slice.length; i++) {
        byteNumbers[i] = slice.charCodeAt(i);
      }

      var byteArray = new Uint8Array(byteNumbers);

      byteArrays.push(byteArray);
    }

    var blob = new Blob(byteArrays, { type: contentType });
    return blob;
  }

  obtenerPermisos(){

    for(let item of SessionUser.getInstance().getAdministrador()){
      if(item.idUsuario == SessionUser.getInstance().getUsuario().idUsuario){
        this.listPermisos = item.permisos;
        break;
      }
    }

    this.listPermisos = this.listPermisos.sort((a, b) => (a.permiso.idPermiso < b.permiso.idPermiso) ? -1 : 1);
    DEBUG && console.log(this.listPermisos);
  }

  redirectTo() {
    this.saveCurrentDataStage();
    this.router.navigate([`protected/pedimentos/${this.data.dataStage.idDataStage}`]);
  }

  saveCurrentDataStage() {
    let packedData = localStorage.getItem('currentUser');
    if (packedData) {
      packedData = JSON.parse(packedData);
      _.update(packedData, 'currentDataStage', () => {
        return {
          idDataStage: this.data.dataStage.idDataStage,
          startDate: this.data.finicio,
          endDate: this.data.ffin,
          folio: this.data.dataStage.folio,
          totalValue: this.data.totalDeducibles
        };
      });
      localStorage.setItem('currentUser', JSON.stringify(packedData));
    }
  }

}
