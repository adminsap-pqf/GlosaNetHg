import { Component, OnInit } from '@angular/core';
import { ReporteAmedidaService } from '../../services/reporte-amedida/reporte-amedida.service';
import { Parametros } from '../../model/parametros.class';
import { Usuario } from '../../model/usuario.class';
import { Cliente } from '../../model/cliente.class';
import { UserBarComponent } from '../core-container/user-bar/user-bar.component';
import { SessionUser } from '../../services/session/session.service';

/* Dev Tools */
import { DEBUG } from "../../settings/app.settings";

@Component({
  selector: 'gn-reportes-amedida',
  templateUrl: './reportes-amedida.component.html',
  styleUrls: ['./reportes-amedida.component.scss']
})
export class ReportesAmedidaComponent implements OnInit {

  today: Date = new Date();
  currentYear: number = 0;
  currentMonth: number = 0;
  currentDay: number = 0;

  indexYear: number = 0;

  lstData: any[] = [];
  lstColores: any[] = new Array(12).fill("");

  meses: any[] = ["Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"];
  year: any;
  periodo: String = "";
  dataPeriodo: any;
  itemsDropList: any = [{ nombre: '2021' },{ nombre: '2020' }, { nombre: '2019' }, { nombre: '2018' }, { nombre: '2017' }, { nombre: '2016' }, { nombre: '2015' }, { nombre: '2014' }, { nombre: '2013' }, { nombre: '2012' }];
  itemsDropList2: any = [{ nombre: 'Todo el Año', tipo: "", periodo: "Anual", data: 1 }, { nombre: 'Semestres', tipo: "cat" }, { nombre: 'Enero - Junio', tipo: "", periodo: "Semestre", data: 1 },
  { nombre: 'Julio - Diciembre', tipo: "", periodo: "Semestre", data: 2 }, { nombre: 'Trimestres', tipo: "cat" }, { nombre: 'Enero - Marzo', tipo: "", periodo: "Trimestre", data: 1 },
  { nombre: 'Abril - Junio', tipo: "", periodo: "Trimestre", data: 2 }, { nombre: 'Juilo - Septiembre', tipo: "", periodo: "Trimestre", data: 3 }, { nombre: 'Octubre - Diciembre', tipo: "", periodo: "Trimestre", data: 4 },
  { nombre: 'Mensual', tipo: "cat" }, { nombre: 'Enero', tipo: "", periodo: "Mes", data: 'Enero' }, { nombre: 'Febrero', tipo: "", periodo: "Mes", data: 'Febrero' }, { nombre: 'Marzo', tipo: "", periodo: "Mes", data: 'Marzo' },
  { nombre: 'Abril', tipo: "", periodo: "Mes", data: 'Abril' }, { nombre: 'Mayo', tipo: "", periodo: "Mes", data: 'Mayo' }, { nombre: 'Junio', tipo: "", periodo: "Mes", data: 'Junio' }, { nombre: 'Julio', tipo: "", periodo: "Mes", data: 'Julio' },
  { nombre: 'Agosto', tipo: "", periodo: "Mes", data: 'Agosto' }, { nombre: 'Septiembre', tipo: "", periodo: "Mes", data: 'Septiembre' }, { nombre: 'Octubre', tipo: "", periodo: "Mes", data: 'Octubre' },
  { nombre: 'Noviembre', tipo: "", periodo: "Mes", data: 'Noviembre' }, { nombre: 'Diciembre', tipo: "", periodo: "Mes", data: 'Diciembre' }];


  constructor(private reprteAmedida: ReporteAmedidaService, private userBAR: UserBarComponent) { }

  ngOnInit() {
    this.currentYear = this.today.getFullYear();
    this.currentMonth = this.today.getMonth() + 1;
    this.currentDay = this.today.getDate();

    let parametros: Parametros = new Parametros();
    let usuario: Usuario = new Usuario();
    let cliente: Cliente = new Cliente();
    cliente.idCliente = SessionUser.getInstance().getIdClient();
    cliente.rfcActual = SessionUser.getInstance().getRfc();
    usuario.cliente = cliente;
    usuario.idUsuario = SessionUser.getInstance().getUserID();
    parametros.usuario = usuario;
    parametros.year = this.currentYear;
    parametros.tipo = "Periodo";
    parametros.mes = this.getMonthString(this.currentMonth);
    this.lstColores[this.currentMonth - 1] = "rgba(6, 188, 82, 0.2)";

    this.yearItemSelected();

    DEBUG && console.log(parametros);
    SessionUser.getInstance().setLoading(true);
    this.reprteAmedida.obtenerReporte565(parametros).subscribe(
      data => {
        DEBUG && console.log(data);
        this.lstData = data;
        SessionUser.getInstance().setLoading(false);
      },
      error => {
        DEBUG && console.log('@ngOnInit: failed', error);
        SessionUser.getInstance().setLoading(false);
        SessionUser.getInstance().setMessageFailed('al obtener el reporte');
        document.getElementById('requestFailed').style.display = 'block';
      }
    );
  }

  yearItemSelected(){
    let i = 0;
    for(let item of this.itemsDropList){
      if(this.currentYear == Number(item.nombre)){
        this.indexYear = i;
      }
      i++;
    }
  }

  dropList(index: any, $event) {
  }

  filtroMes(index: any){
    SessionUser.getInstance().setLoading(true);
    this.lstColores = new Array(12).fill("");
    this.lstColores[index] = "rgba(6, 188, 82, 0.2)";
    let parametros: Parametros = new Parametros();
    let usuario: Usuario = new Usuario();
    let cliente: Cliente = new Cliente();
    cliente.idCliente = SessionUser.getInstance().getIdClient();
    cliente.rfcActual = SessionUser.getInstance().getRfc();
    usuario.cliente = cliente;
    usuario.idUsuario = SessionUser.getInstance().getUserID();
    parametros.usuario = usuario;
    parametros.year = this.year;
    parametros.tipo = "Periodo";
    parametros.mes = this.meses[(index)];
    this.lstData = [] ;

    this.reprteAmedida.obtenerReporte565(parametros).subscribe(
      data => {
        DEBUG && console.log(data);
        this.lstData = data;
        SessionUser.getInstance().setLoading(false);
      },
      error => {
        DEBUG && console.log('@filtroMes: failed', error);
        SessionUser.getInstance().setLoading(false);
        SessionUser.getInstance().setMessageFailed('al obtener el mes seleccionado');
        document.getElementById('requestFailed').style.display = 'block';
      }
    );
  }

  dropYear($event) {
    this.year = $event.nombre;
    DEBUG && console.log($event);
  }

  dropPeriodo($event) {
    this.periodo = $event.periodo;
    this.dataPeriodo = $event.data;
    DEBUG && console.log($event);
  }

  exportar() {
    DEBUG && console.log(this.year);
    DEBUG && console.log(this.dataPeriodo);
    if (!SessionUser.getInstance().getOpenDescargas().descargando) {
      SessionUser.getInstance().getOpenDescargas().descargando = true;
      SessionUser.getInstance().setOrigenDescarga("Reportes a la Medida");
      if (this.year != undefined && this.dataPeriodo != undefined) {
        this.peticionResultadosDeReporte565();
      }
    }
  }

  peticionResultadosDeReporte565(){
    SessionUser.getInstance().setLoading(true);
    let parametros: Parametros = new Parametros();
    let usuario: Usuario = new Usuario();
    let cliente: Cliente = new Cliente();
    cliente.idCliente = SessionUser.getInstance().getIdClient();
    cliente.rfcActual = SessionUser.getInstance().getRfc();
    usuario.cliente = cliente;
    usuario.idUsuario = SessionUser.getInstance().getUserID();
    parametros.usuario = usuario;
    parametros.year = this.year;
    parametros.tipo = "Periodo";

    if (this.periodo == "Semestre") {
      parametros.semestre = this.dataPeriodo;
    } else if (this.periodo == "Trimestre") {
      parametros.trimestre = this.dataPeriodo;
    } else if (this.periodo == "Mes") {
      parametros.mes = this.dataPeriodo;
    }

    DEBUG && console.log(parametros);
    this.reprteAmedida.peticionReporte565(parametros).subscribe(
      data => {
        debugger;
        DEBUG && console.log(data);
        SessionUser.getInstance().getListDescargas().push({ fileName: "reporte.xls", porcentaje: "0%", subText: "Preparando Descarga...", class: "barra" });//se hace cambio para poder descargar reporte a medida.
        this.userBAR.iniciarCheckInRealTime(data);
        SessionUser.getInstance().setLoading(false);
      },
      error => {
        DEBUG && console.log('@filtroMes: failed', error);
        SessionUser.getInstance().setLoading(false);
        SessionUser.getInstance().setMessageFailed('al exportar el reporte');
        document.getElementById('requestFailed').style.display = 'block';
      }
    );
  }

  getMonthString(month){
    let result = '';
    switch (month) {
      case 1:
        result = 'Enero';
        break;
      case 2:
      result = 'Febrero';
        break;
      case 3:
      result = 'Marzo';
        break;
      case 4:
      result = 'Abril';
        break;
      case 5:
      result = 'Mayo';
        break;
      case 6:
      result = 'Junio';
        break;
      case 7:
      result = 'Julio';
        break;
      case 8:
      result = 'Agosto';
        break;
      case 9:
      result = 'Septiembre';
        break;
      case 10:
      result = 'Octubre';
        break;
      case 11:
      result = 'Noviembre';
        break;
      case 12:
      result = 'Diciembre';
        break;

      default:
        break;
    }
    return result;
  }

}
