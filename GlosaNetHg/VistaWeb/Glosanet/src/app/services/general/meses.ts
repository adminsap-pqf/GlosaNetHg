import {Injectable} from "@angular/core";

@Injectable()
export class MesesService {

  constructor() {

  }

  getMesString(mes) {
    let mesString = '';
    switch (mes) {
      case 0:
        mesString = 'Enero';
        break;
      case 1:
        mesString = 'Febrero';
        break;
      case 2:
        mesString = 'Marzo';
        break;
      case 3:
        mesString = 'Abril';
        break;
      case 4:
        mesString = 'Mayo';
        break;
      case 5:
        mesString = 'Junio';
        break;
      case 6:
        mesString = 'Julio';
        break;
      case 7:
        mesString = 'Agosto';
        break;
      case 8:
        mesString = 'Septiembre';
        break;
      case 9:
        mesString = 'Octubre';
        break;
      case 10:
        mesString = 'Noviembre';
        break;
      case 11:
        mesString = 'Diciembre';
        break;
      default:
        break;
    }

    return mesString
  }

}
