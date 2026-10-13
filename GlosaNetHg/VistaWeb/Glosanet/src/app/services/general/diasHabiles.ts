import {Injectable} from "@angular/core";
import * as moment from 'moment';
import {SessionUser} from "../session/session.service";
import * as _ from 'lodash';
/* Dev Tools */
import {DEBUG} from "../../settings/app.settings";

@Injectable()
export class DiasHabilesService {

  holidays = [ /* TODO: Agregar todos los días festivos con el formato DD/MM */
    "01/01",
    "05/02",
    "21/03",
    "01/05",
    "16/09",
    "20/11",
    "25/12"
  ];

  constructor() {
  }

  diasHabiles(cantPeriodo: number, frealizacion: Date, ffin: Date) {

    let menosDias = cantPeriodo;
    let calendar = new Date(ffin);// fecha de partida
    let aux = new Date(ffin);

    calendar.setDate(frealizacion.getDate() - (menosDias)); // se le quitan los dias indicados

    while (menosDias > 0) {
      // se calcula el numero de dias festivos y fines de semana entre dos fechas y se calcula una nueva fecha
      menosDias = this.finSemanaDiasFestivoscount(calendar, aux);
      aux = new Date(calendar);
      calendar.setDate(calendar.getDate() - (menosDias));

    }

    return calendar;
  }

  finSemanaDiasFestivoscount(d1: Date, c2: Date) {
    let dias = 0;
    let c1 = new Date(d1);
    while (c1.getTime() < c2.getTime()) {

      dias += this.isWeekendOrHolyday(c1);
      c1.setDate(c1.getDate() + 1);
    }

    return dias;
  }

  isWeekendOrHolyday(d1: Date) {

    let c1 = moment(d1, 'DD/MM/YYY');

    if ((d1.getDay() === 6) || (d1.getDay() === 0)) {
      return 1;
    } else if (c1.day() === 25 && (c1.month() === 11)) {
      //Navidad
      return 1;
    } else if (c1.day() === 1 && (c1.month() === 0)) {
      //año nuevo
      return 1;
    } else if (c1.day() === 5 && (c1.month() === 1)) {
      //5 de febrero
      return 1;
    } else if (c1.day() === 21 && (c1.month() === 2)) {
      //21 de marzo
      return 1;
    } else if (c1.day() === 1 && (c1.month() === 4)) {
      //1 de mayo
      return 1;
    } else if (c1.day() === 16 && (c1.month() === 8)) {
      //16 de septiembre
      return 1;
    } else if (c1.day() === 20 && (c1.month() === 10)) {
      //20 de noviembre
      return 1;
    }

    return 0;
  }

  diasHabilesProx(cantPeriodo: number, fRealizacion: Date, finicio: Date) {
    let days = 0;
    while (days != cantPeriodo && days > -1) {
      days = this.workingDays(finicio, fRealizacion);

      fRealizacion.setDate(fRealizacion.getDate() + (cantPeriodo - days));

    }

    return fRealizacion;
  }

  /* diasHabiles(cantPeriodo: number, frealizacion: Date, ffin: Date) {
     let days = 0;
     while (days != cantPeriodo) {
       days = this.workingDays(frealizacion, ffin);
       DEBUG && console.log("workingDays: " + days);
       frealizacion.setDate(frealizacion.getDate() - (cantPeriodo - days));
     }
     //frealizacion.setDate(frealizacion.getDate() - 1 );
     return frealizacion;
   }*/

  workingDays(dateFrom, dateTo) {
    let from = moment(dateFrom, 'DD/MM/YYY'),
      to = moment(dateTo, 'DD/MM/YYY'),
      days = 0,
      init = moment(dateFrom, 'DD/MM/YYY'),
      end = moment(dateTo, 'DD/MM/YYY');

    while (!from.isAfter(to)) {

      if (from.isoWeekday() !== 6 && from.isoWeekday() !== 7) {
        days++;// Si no es sabado ni domingo
      }
      from.add(1, 'days');
    }

    DEBUG && console.log(SessionUser.getInstance().getDiasFeriados());
    for (let item of SessionUser.getInstance().getDiasFeriados()) {
      if (moment(new Date(item.fecha), 'DD/MM/YYY').isBetween(init, end)) {
        days--;
      }
    }
    return days - 1;
  }

  calculateDays(startDate, endDate) {

    let start = new Date(startDate);
    let end = new Date(endDate);

    let totalDays = 0;

    start.setHours(0, 0, 0, 0);
    end.setHours(0, 0, 0, 0);

    let current = new Date(start);
    current.setDate(current.getDate() + 1);

    let day;
    let holidayFound = false;

    while (current <= end) {

      const date = this.changeDateFormat(current);

      if (_.indexOf(this.holidays, date) !== -1) {
        holidayFound = true;
      }

      day = current.getDay();
      if (day >= 1 && day <= 5 && !holidayFound) {
        ++totalDays;
      }

      /* Si es una día festivo que cae en fin de semana se debe de restar,
       * ya que esta día se asignara a un lunes o viernes dependiendo el día que es.
       * */
      if (holidayFound && (day === 0 || day === 6)) {
        --totalDays;
      }

      current.setDate(current.getDate() + 1);
      holidayFound = false;
    }

    return totalDays;
  }

  getDate(endDate, periodType, periodQty) {

    let total = this.convertDays(periodType, periodQty);

    let end = new Date(endDate);
    end.setHours(0, 0, 0, 0);
    let startDate = end;

    let count = 0;

    let day;
    let holidayFound = false;

    while (count < total) {

      startDate = new Date(end.setDate(end.getDate() - 1));
      const date = this.changeDateFormat(startDate);

      if (_.indexOf(this.holidays, date) !== -1) {
        holidayFound = true;
      }

      day = startDate.getDay();
      if (day >= 1 && day <= 5 && !holidayFound) {
        count++;
      }

      /* Si es una día festivo que cae en fin de semana se debe de restar de la variable count,
       * ya que esta día se asignara a un lunes o viernes dependiendo el día que es.
       * */
      if (holidayFound && (day === 0 || day === 6)) {
        count--;
      }

      holidayFound = false;
    }

    return startDate;
  }

  changeDateFormat(date) {

    let day = date.getDate();
    if (day < 10) {
      day = '0' + day;
    }

    let month = date.getMonth() + 1;
    if (month < 10) {
      month = '0' + month;
    }

    return day + '/' + month;
  }

  convertDays(periodType, periodQty) {

    if (periodType === 'Días') return periodQty;

    let total = 0;

    switch (periodType) {
      case 'Semanas':
        total = periodQty * 7;
        break;
      case 'Meses':
        total = periodQty * 30;
        break;
      case 'Años':
        total = periodQty * 365;
        break;
    }

    return total;
  }
}
