import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import * as moment from 'moment';
import { Forecast } from "../../model/forecast";
import { SessionUser } from "../../services/session/session.service";
import { ForecastService } from "../../services/forecast/forecast.service";
import * as _ from "lodash";

@Component({
  selector: 'gn-forecast-notice',
  templateUrl: './forecast-notice.component.html',
  styleUrls: ['./forecast-notice.component.scss']
})
export class ForecastNoticeComponent implements OnInit {

  year: any = '';
  remainingDays: any = '';
  deadline: any = '';
  dataForecast: any = [] = [];

  constructor(private forecast: ForecastService, private router: Router) { }

  ngOnInit() {
    this.loadDate();
    this.getForecasts();
  }

  loadDate() {

    //let today = moment('02-04-2018', 'DD/MM/YYYY'); //TODO: pruebas
    let today = moment();
    today.hours(0).valueOf();
    today.minutes(0).valueOf();
    today.seconds(0).valueOf();
    today.milliseconds(0).valueOf();
    let year = today.year();

    if (today.date() > 1 && today.month() > 2 ) {
      year += 1;
    }

    let date = moment([year, 3, 1, 0, 0, 0, 0]);
    let remainingDays = date.diff(today, 'days');


    this.year = year;
    this.remainingDays = `Quedan ${remainingDays} ${remainingDays <= 1 ? 'día' : 'días'}.`;
    this.deadline = this.getDate(date);

  }

  getDate(deadline) {

    let months = ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'];

    return `${deadline.date() < 10 ? `0${deadline.date()}` : deadline.date() } de ${months[deadline.month()]} de ${deadline.year()}`;
  }

  getForecasts() {
    let forecasts = new Forecast();
    forecasts.empresa = SessionUser.getInstance().getidEmpresaActual();
    SessionUser.getInstance().setLoading(true);

    this.forecast.getForecast(forecasts)
      .subscribe(
        data => {
          for (let item of data) {
            let forecast = {
              nombre: item.nombre ? item.nombre : '',
              cantidad: item.cantidad ? item.cantidad : '',
              unidad: item.unidad ? item.unidad : '',
              idPrevision : item.idPrevision  ? item.idPrevision : '',
              vigencia : item.vigencia ? item.vigencia : '',
              fechaCreacion : item.fechaCreacion ? item.fechaCreacion : '',
              estado : item.estado ? item.estado : '',
              tipoPrevision : item.tipoPrevision ? item.tipoPrevision : '',
              descripcion : item.descripcion ? item.descripcion : '',
              fechaLimite : item.fechaLimite ? item.fechaLimite : '',
              folioPrevision : item.folioPrevision ? item.folioPrevision : '',
              productos : item.productos ? item.productos : '',
              previsionProducto : item.previsionProducto ? item.previsionProducto : '',
              partida : item.partida ? item.partida : '',
              empresa : item.empresa ? item.empresa : '',
              publicado : item.publicado ? item.publicado : '',
            };

            this.dataForecast.push(forecast);
          }

          let result =

          this.dataForecast = this.dataForecast.sort((a, b) => (a.nombre < b.nombre) ? -1 : 1);
          SessionUser.getInstance().setLoading(false);
        },
        error => {
          console.error('Error: getForecasts');
        });

  }

  redirectToNewForecast() {
    this.router.navigate(['protected/permisos/newForecast']);
  }

}
