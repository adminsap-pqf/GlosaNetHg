import { Component, OnInit } from '@angular/core';
import { LibreriaDatastageService } from '../../../services/libreria-datastage/libreria-datastage.service';
import { Parametros } from '../../../model/parametros.class';
import { Usuario } from '../../../model/usuario.class';
import { Cliente } from '../../../model/cliente.class';
import { DatosGenerales } from '../../../model/datosGenerales.class';
import { Router } from '@angular/router';
import { SessionUser } from '../../../services/session/session.service';

/* Dev Tools */
import { DEBUG } from "../../../settings/app.settings";

@Component({
  selector: 'gn-vista-calendario',
  templateUrl: './vista-calendario.component.html',
  styleUrls: ['./vista-calendario.component.scss']
})
export class VistaCalendarioComponent implements OnInit {
  parametros: Parametros = new Parametros();
  idCliente: Number = 0;
  rfc: String = "";
  lstYears: string[] = ['2018','2017', '2016', '2015', '2014', '2013'];
  year:any = "";
  lstData: DatosGenerales[] = [];

  constructor(private libreriaDSS: LibreriaDatastageService,private router: Router) { }

  ngOnInit() {
    const currentYear = new Date().getFullYear();

    /* let usuario: Usuario = new Usuario();
    let cliente: Cliente = new Cliente(); */
    this.parametros.year = currentYear;
    this.parametros.tipo = "Periodo";
    /* cliente.idCliente = 4;
    cliente.rfcActual = "PQF910416FB3";
    usuario.cliente = cliente; */
    this.parametros.usuario = SessionUser.getInstance().getUsuario();
    this.navegacionDataStage(currentYear);

    //this.idCliente = 4;
    this.idCliente = SessionUser.getInstance().getIdClient();
    //this.rfc = "PQF910416FB3";
    this.rfc = SessionUser.getInstance().getRfc();
    this.loadOptions();
  }


  loadOptions() {
    let startYear = new Date().getFullYear();
    let endYear = 2013;
    let count = startYear;
    let options = [];

    for (let i = startYear ; i >= endYear ; i--) {
      options.push(`${count}`);
      count--;
    }
    this.lstYears = options.slice();
  }


  emitYear($event) {
    DEBUG && console.log($event);
    //this.year = $event;
    this.parametros.year = $event;
    this.navegacionDataStage($event);
  }

  redirectLibrary() {
    this.router.navigate(["protected/datastagelibrary"]);
  }

  navegacionDataStage(year:any) {
    SessionUser.getInstance().setLoading(true);
    this.libreriaDSS.navegacionDataStage(this.parametros).subscribe(
      data => {
        DEBUG && console.log(data);
        this.lstData = data;
        this.year = year;
        SessionUser.getInstance().setLoading(false);
      },
      error => {
        DEBUG && console.log(error);
        SessionUser.getInstance().setLoading(false);
      }
    );
  }

}
