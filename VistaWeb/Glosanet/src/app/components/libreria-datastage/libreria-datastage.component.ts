import { Component, OnInit } from '@angular/core';
import { LibreriaDatastageService } from '../../services/libreria-datastage/libreria-datastage.service';
import { Parametros } from '../../model/parametros.class';
import { Usuario } from '../../model/usuario.class';
import { Cliente } from '../../model/cliente.class';
import { DatosGenerales } from '../../model/datosGenerales.class';
import { Router } from '@angular/router';
import { SessionUser } from '../../services/session/session.service';
import * as _ from 'lodash';

/* Dev Tools */
import { DEBUG } from "../../settings/app.settings";
import {ROUTE_FILES} from "../../util/common.protocols";

@Component({
  selector: 'gn-libreria-datastage',
  templateUrl: './libreria-datastage.component.html',
  styleUrls: ['./libreria-datastage.component.scss']
})
export class LibreriaDatastageComponent implements OnInit {

  today: Date = new Date();
  currentYear: number = 0;
  currentMonth: number = 0;
  currentDay: number = 0;

  parametros: Parametros = new Parametros();
  lstYears: string[] = [];
  date: Date = new Date(2018, 0, 1);
  date2: Date = new Date(2018, 0, 1);

  dateInicio:Date;
  dateFin: Date;
  lstData: DatosGenerales [];
  lstDataAux: DatosGenerales[];
  idCliente: any = SessionUser.getInstance().getCliente().idCliente;
  rfc: any = SessionUser.getInstance().getCliente().rfcActual;
  /*lstData: any = [{ fInicio: '01 · Octubre · 201', fFin: '31 · Octubre · 2014', identificador: '#210810', solicitud: '04·Marzo·2015', inclusion: '12·Oct·2015', total: 7284.39},
  { fInicio: '01 · Octubre · 201', fFin: '31 · Octubre · 2014', identificador: '#210810', solicitud: '04·Marzo·2015', inclusion: '12·Oct·2015', total: 7284.39 },
  { fInicio: '01 · Octubre · 201', fFin: '31 · Octubre · 2014', identificador: '#210810', solicitud: '04·Marzo·2015', inclusion: '12·Oct·2015', total: 7284.39 },
  { fInicio: '01 · Octubre · 201', fFin: '31 · Octubre · 2014', identificador: '#210810', solicitud: '04·Marzo·2015', inclusion: '12·Oct·2015', total: 7284.39 },
  { fInicio: '01 · Octubre · 201', fFin: '31 · Octubre · 2014', identificador: '#210810', solicitud: '04·Marzo·2015', inclusion: '12·Oct·2015', total: 7284.39 },
  { fInicio: '01 · Octubre · 201', fFin: '31 · Octubre · 2014', identificador: '#210810', solicitud: '04·Marzo·2015', inclusion: '12·Oct·2015', total: 7284.39 }];
  lstData2: any = [{ fInicio: '01 · Octubre · 201', fFin: '31 · Octubre · 2014', identificador: '#210810', solicitud: '04·Marzo·2015', inclusion: '12·Oct·2015', total: 7284.39 }];*/

  invalidDates: boolean = false;
  calculateActive: boolean = false;

  constructor(private libreriaDSS: LibreriaDatastageService, private router: Router) { }

  ngOnInit() {
    this.currentYear = this.today.getFullYear();
    this.currentMonth = this.today.getMonth() + 1;
    this.currentDay = this.today.getDate();

    this.dateInicio = new Date(this.currentYear,0,1);
    this.dateFin = new Date(this.currentYear, 11, 31);
    this.date = new Date(this.currentYear,0,1);
    this.date2 = new Date(this.currentYear, 11, 31);
    /* let usuario: Usuario = new Usuario();
    let cliente: Cliente = new Cliente(); */
    this.parametros.year = this.currentYear;
    this.parametros.tipo = "Periodo";
  /*   cliente.idCliente = 4;
    cliente.rfcActual = "PQF910416FB3";
    usuario.cliente = cliente; */
    this.parametros.usuario = SessionUser.getInstance().getUsuario();
    this.getYearsList();
    this.navegacionDataStage();
  }

  ngDoCheck(){
    if(SessionUser.getInstance().getNumDataStage() != SessionUser.getInstance().getNumDataStageAux()){
      this.navegacionDataStage();
      SessionUser.getInstance().setNumDataStageAux(SessionUser.getInstance().getNumDataStage());
    }
  }


  getYearsList(){
    let yearActual = this.currentYear;
    while(yearActual > 2012) {
      this.lstYears.push(yearActual+'');
      yearActual--;
    }
    DEBUG && console.log(this.lstYears);
  }

  emitYear($event) {
    DEBUG && console.log($event);
    this.dateInicio = new Date($event, 0, 1);
    this.dateFin = new Date($event, 11, 31);

    this.date = new Date($event, 0, 1);
    this.date2 = new Date($event, 11, 31);
    this.parametros.year = $event;
    this.navegacionDataStage();
    this.handleValidDates();
  }

  getFechaImpl($event) {
    DEBUG && console.log($event);
  }

  navegacionDataStage() {
    SessionUser.getInstance().setLoading(true);
    this.libreriaDSS.navegacionDataStage(this.parametros).subscribe(
      data => {
        DEBUG && console.log(data);
        this.lstData = data;
        this.lstDataAux = data;
        SessionUser.getInstance().setLoading(false);
      },
      error => {
        DEBUG && console.log('@navegacionDataStage:', error);
        SessionUser.getInstance().setLoading(false);
        SessionUser.getInstance().setMessageFailed('al obtener la Librería DataStages');
        document.getElementById('requestFailed').style.display = 'block';
      }
    );
  }

  async filtrar() {
    if (await this.handleValidDates()) {
      SessionUser.getInstance().setLoading(true);

      let parameter : Parametros = new Parametros();
      parameter.finicio = this.date;
      parameter.ffin = this.date2;
      parameter.tipo = "Range";
      parameter.usuario = SessionUser.getInstance().getUsuario();
      this.lstData = [];

      this.libreriaDSS.navegacionDataStage(parameter).subscribe(
        data => {
          DEBUG && console.log('@filtrar:', data);
          SessionUser.getInstance().setLoading(false);

          if (_.isArray(data)) this.lstData = data.slice();
        },
        error => {
          DEBUG && console.log('@filtrar:', error);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al obtener la Librería DataStages');
          document.getElementById('requestFailed').style.display = 'block';
        }
      );
    }
  }

  //The function is called
  compressed_files() {
    SessionUser.getInstance().setLoading(true);
    let parametros : Parametros = new Parametros();
    parametros.archivos = [];
    parametros.nombres = [];
    for (let data of this.lstData) {
      parametros.archivos.push("DataStages/" + this.parametros.usuario.cliente.idCliente + "/" + this.parametros.usuario.cliente.rfcActual + "/" + data.dataStage.folio + ".zip");
      parametros.nombres.push(data.dataStage.folio + ".zip");
    }
    parametros.salida = "DataStages/" + this.parametros.usuario.cliente.idCliente + "/" + this.parametros.usuario.cliente.rfcActual+"/";
    DEBUG && console.log(parametros);
    this.libreriaDSS.generarZip(parametros).subscribe(
      data => {
        DEBUG && console.log(data);
        SessionUser.getInstance().setLoading(false);
        if (data && data.mediaValue) {
          let element = document.createElement('a');
          element.setAttribute('href', ROUTE_FILES + "/" + data.mediaValue + "Archivos.zip");
          element.setAttribute('download', 'Archivos.zip');
          element.style.display = 'none';
          document.body.appendChild(element);
          element.click();
          document.body.removeChild(element);
        } else {
          SessionUser.getInstance().setMessageFailed('al descargar los archivos');
          document.getElementById('requestFailed').style.display = 'block';
        }
      },
      error => {
        DEBUG && console.log('@compressed_files: ', error);
        SessionUser.getInstance().setLoading(false);
        SessionUser.getInstance().setMessageFailed('al descargar los archivos');
        document.getElementById('requestFailed').style.display = 'block';
      }
    );

  }

  redirectCalendar(){
    this.router.navigate(["protected/datastageCalendar"]);
  }

  handleValidDates() {
    DEBUG && console.log('@handleValidDates: Entre');

    return new Promise(resolve => {
      setTimeout(() => {
        let startTime = new Date(this.date);
        let endTime = new Date(this.date2);
        startTime.setHours(0, 0, 0, 0);
        endTime.setHours(23, 59, 59, 999);

        if (startTime.getTime() < endTime.getTime()) {
          this.invalidDates = false;
          resolve(true);
        } else {
          this.invalidDates = true;
          resolve(false);
        }
      })
    });
  }

  handleCalculateDate(value) {
    this.calculateActive = value;
  }

  handleDate(data, option) {
    DEBUG && console.log('@handleDate: Entre');

    if (!this.calculateActive) return;

    if (option === "start") {
      this.date2 = this.selectDate(data, option);
    } else if (option === "end") {
      this.date = this.selectDate(data, option);
    }
  }

  selectDate(date, option) {

    let year = date.getFullYear();
    let month = date.getMonth();

    let day = option === "start" ? new Date(year, month + 1, 0).getDate() : 1 ;

    return new Date(year, month, day);
  }

}
