import { Component, OnInit } from '@angular/core';

/* Dev Tools */
import { DEBUG } from "./settings/app.settings";

@Component({
  selector: 'gn-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  /* /// dropList
  droplist1: any;
  droplist2: any;
  items: any = [{nombre: 'nombre1'}, {nombre: 'nombre2'}, {nombre: 'nombre3'}, {nombre: 'nombre4'}, {nombre: 'nombre5'}];

  title = 'jungle \\m/';

  lstYears: string [] = ['2017', '2016', '2015', '2014', '2013', '2012'];
  lstItems: string [] = ['Descripción', 'Familia', 'Imágenes', 'Permisos'];
  user: any = {usuario: 'NombreUsuario001' , nombre: 'Nombre ApellidoP ApellidoM', rol: 'Administrador' ,
                rfc: ['CUPU800825569', 'GUCH6411244W0', 'DIHM711110PC7', 'CUPU800825569']};

  pWizard: any = 0;

  emitYear($event) {
    DEBUG && console.log($event);
  } */
  constructor() {}

  ngOnInit() { }
 /*
  ngDoCheck() {
    // DEBUG && console.log(this.droplist1);
    // DEBUG && console.log(this.droplist2);
  }

  dropList($event) {
    DEBUG && console.log($event);
  }
  getFechaImpl($event) {
    DEBUG && console.log($event);
  }



  emitItem($event) {
    DEBUG && console.log($event);
  }

  back() {
    if (this.pWizard > 0) {
      this.pWizard --;
    }
    DEBUG && console.log(this.pWizard);
  }

  next() {
    if (this.pWizard < (this.lstItems.length - 1)) {
      this.pWizard ++;
    }
    DEBUG && console.log(this.pWizard);
  }

  filterSelected($event) {
    DEBUG && console.log($event);
  }
 */

}
