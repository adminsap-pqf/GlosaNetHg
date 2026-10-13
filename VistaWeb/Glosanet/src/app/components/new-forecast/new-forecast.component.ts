import { Component, OnInit } from '@angular/core';
import * as moment from 'moment';
import { Forecast } from "../../model/forecast";
import { SessionUser } from "../../services/session/session.service";
import { ForecastService } from "../../services/forecast/forecast.service";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import _ from 'lodash';
import { Parametros } from "../../model/parametros.class";

/* Dev Tools */
import { DEBUG } from "../../settings/app.settings";

@Component({
  selector: 'gn-new-forecast',
  templateUrl: './new-forecast.component.html',
  styleUrls: ['./new-forecast.component.scss']
})
export class NewForecastComponent implements OnInit {

  year: any = '';
  remainingDays: any = '';
  deadline: any = '';
  classAnimation: string = '';
  indexForecastToEdit: number = -1;
  validity: string = '';

  productSelected: any;
  quantitySelected: string = '';
  unitySelected: any = { nombre: '' };

  isValidAddProduct: boolean = false;
  isValidFormForecast: boolean = false;

  dataForecast: any = [] = [];
  dataForecastOriginal: any = [] = [];
  dataProducts: any = [] = [];
  dataUniverseProducts: any = [] = [];

  empresa: string = '';
  publicado: string = '';

  searchBarForm: FormGroup;
  updateForecastForm: FormGroup;

  nameSearchBar: string = '';
  quantitySearchBar: string = '';
  unitySearchBar: string = '';
  productsSearchBar: string = '';

  nameForecast: string = '';
  quantityForecast: string = '';
  unityForecast: any = { nombre: '' };

  /* DropList */
  itemsDropList:any[] = [
    {
      nombre: 'Kg'
    },
    {
      nombre: 'g'
    },
    {
      nombre: 'pza'
    },
    {
      nombre: 'm'
    },
    {
      nombre: 'm²'
    },
    {
      nombre:'m³'
    },
    {
      nombre:'Cabz'
    },
    {
      nombre:'l'
    },
    {
      nombre:'KW'
    },
    {
      nombre:'kWH'
    },
    {
      nombre:'mil'
    },
    {
      nombre:'jgo'
    },
    {
      nombre:'t'
    },
    {
      nombre:'bbl'
    },
    {
      nombre:'G net'
    },
    {
      nombre:'dec'
    },
    {
      nombre:'c'
    },
    {
      nombre:'doc'
    },
    {
      nombre:'caj'
    },
    {
      nombre:'btll'
    }
  ];

  constructor(private forecastService: ForecastService) { }

  ngOnInit() {

    this.updateForecastForm = new FormGroup({
      nameForecast: new FormControl('', [Validators.required, Validators.pattern('^([a-zñáéíóú, A-ZÁÉÍÓÚ]{1}[a-zñáéíóú,A-ZÁÉÍÓÚ]+[\s]*)+$')]),
      quantityForecast: new FormControl('', [Validators.required, Validators.pattern('^(([0-9])+(-){0,1})+$')]),
      unityForecast: new FormControl('', [Validators.required, Validators.pattern('^([a-zñáéíóú, A-ZÁÉÍÓÚ]{1}[a-zñáéíóú,A-ZÁÉÍÓÚ]+[\s]*)+$')]),
    });

    this.searchBarForm = new FormGroup({
      nameSearchBar: new FormControl('', [Validators.required, Validators.pattern('^([a-zñáéíóú, A-ZÁÉÍÓÚ]{1}[a-zñáéíóú,A-ZÁÉÍÓÚ]+[\s]*)+$')]),
      quantitySearchBar: new FormControl('', [Validators.required, Validators.pattern('^(([0-9])+(-){0,1})+$')]),
      unitySearchBar: new FormControl('', [Validators.required, Validators.pattern('^([a-zñáéíóú, A-ZÁÉÍÓÚ]{1}[a-zñáéíóú,A-ZÁÉÍÓÚ]+[\s]*)+$')]),
      productsSearchBar: new FormControl('', [Validators.required, Validators.pattern('^([a-zñáéíóú, A-ZÁÉÍÓÚ]{1}[a-zñáéíóú,A-ZÁÉÍÓÚ]+[\s]*)+$')]),
    });

    this.loadDate();
    this.getProducts();

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
    this.validity = date.format('YYYY-MM-DD');

  }

  getDate(deadline) {

    let months = ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'];

    return `${deadline.date() < 10 ? `0${deadline.date()}` : deadline.date() } de ${months[deadline.month()]} de ${deadline.year()}`;
  }

  getProducts() {
    let term = new Parametros();
    term.id = SessionUser.getInstance().getIdClient();
    term.empresa = SessionUser.getInstance().getidEmpresaActual();
    this.forecastService.getProducts(term)
      .subscribe(
        data => {
          this.dataUniverseProducts = _.slice(data);
          this.dataProducts = _.slice(data);
          this.dataProducts = this.dataProducts.sort((a, b) => (a.nombre < b.nombre) ? -1 : 1);
          SessionUser.getInstance().setLoading(false);
          this.getForecasts();
        },
        error => {
          console.error('Error: getProducts');
        });
  }

  getForecasts() {
    let forecasts = new Forecast();
    forecasts.empresa = SessionUser.getInstance().getidEmpresaActual();
    SessionUser.getInstance().setLoading(true);

    this.forecastService.getForecast(forecasts)
      .subscribe(
        data => {
          _.forEach(data, o => {
            let forecast = {
              nombre: o.nombre ? o.nombre : '',
              cantidad: o.cantidad ? o.cantidad : '',
              unidad: o.unidad ? o.unidad : '',
              idPrevision : o.idPrevision  ? o.idPrevision : '',
              vigencia : o.vigencia ? o.vigencia : '',
              fechaCreacion : o.fechaCreacion ? o.fechaCreacion : '',
              estado : o.estado ? o.estado : '',
              tipoPrevision : o.tipoPrevision ? o.tipoPrevision : '',
              descripcion : o.descripcion ? o.descripcion : '',
              fechaLimite : o.fechaLimite ? o.fechaLimite : '',
              folioPrevision : o.folioPrevision ? o.folioPrevision : '',
              productos : o.productos ? o.productos : '',
              previsionProducto : o.previsionProducto ? o.previsionProducto : '',
              partida : o.partida ? o.partida : '',
              empresa : o.empresa ? o.empresa : '',
              publicado : o.publicado ? o.publicado : '',
            };

            this.dataForecast.push(forecast);
            DEBUG && console.log(this.dataForecast)

          });

          this.dataForecast = this.dataForecast.sort((a, b) => (a.nombre < b.nombre) ? -1 : 1);
          this.dataForecastOriginal = _.slice(this.dataForecast);
          SessionUser.getInstance().setLoading(false);
        },
        error => {
          console.error('Error: getForecasts');
        });
  }

  saveForecast() {
    let forecast = new Forecast();
    SessionUser.getInstance().getIdClient();
    forecast.empresa = SessionUser.getInstance().getidEmpresaActual();
    SessionUser.getInstance().setLoading(true);

    this.forecastService.saveForecast(this.dataForecast)
      .subscribe(
        data => {
          this.dataForecast = data;
          SessionUser.getInstance().setLoading(false);
          document.getElementById('confirmForecastSave').style.display='block'
        },
        error => {
          DEBUG && console.log('Error');
        });
  }

  isEmptyData() {
    let isValidAddProduct = false;

    if (typeof this.productSelected !== 'undefined' && Object.keys(this.productSelected).length > 0 && this.productSelected.nombre !== '' &&
      this.searchBarForm.get('quantitySearchBar').value !== '' && this.searchBarForm.get('quantitySearchBar').value > 0 &&
      typeof this.unitySelected !== 'undefined' && Object.keys(this.unitySelected).length > 0 && this.unitySelected.nombre !== '') {

      isValidAddProduct = true;

    }

    this.isValidAddProduct = isValidAddProduct;
  }

  isEmptyForecastForm() {
    let isValidFormForecast = false;

    if (this.updateForecastForm.get('nameForecast').value !== '' &&
        this.updateForecastForm.get('quantityForecast').value !== '' &&
        this.updateForecastForm.get('unityForecast').value !== '') {

      isValidFormForecast = true;

    }

    this.isValidFormForecast = isValidFormForecast;

  }

  validateQty(value){
    if(value > 0){
      let validate = /^[0-9]+$/;
      return validate.test(String(value).toLowerCase());
    }else{
      return false;
    }
  }

  handlerListProduct(value) {
    this.productSelected = value;

    setTimeout( () => {
      this.isEmptyData();
    }, 200);
  }

  handlerQuantity(qty) {

    DEBUG && console.log(String(qty).toLowerCase());

    if (qty > 0) {
      DEBUG && console.log(String(qty).toLowerCase());
    } else {
      return false;
    }
  }

  handlerListUnity(value, option) {

    if (option) {
      this.unitySelected = value;
    } else {
      this.unityForecast = value;
    }

    setTimeout( () => {

      if (option) {
        this.isEmptyData();
      } else {
        this.isEmptyForecastForm();
      }

    }, 200);

  }

  addProducts(form) {
    this.isEmptyData();

    if (this.isValidAddProduct) {

      let forecastIndex = _.findIndex(this.dataForecast, o => {
        return o.nombre === this.productSelected.nombre
      });

      let forecastSelected = forecastIndex !== -1 ? JSON.parse(JSON.stringify(this.dataForecast[forecastIndex])) : {};
      let createDate = moment(new Date()).format('YYYY-MM-DD');

      let forecast = {
        nombre: this.productSelected.nombre,
        cantidad: form.quantitySearchBar,
        unidad: this.unitySelected.nombre,
        productos : this.productSelected.productos,
        idPrevision :  forecastIndex !== -1 ? forecastSelected.idPrevision : '',
        vigenciaString : forecastIndex !== -1 ?  forecastSelected.vigenciaString : '',
        fechaCreacionString : forecastIndex !== -1 ? forecastSelected.fechaCreacion : '',
        estado : forecastIndex !== -1 ? forecastSelected.estado : 'Por Revisar',
        tipoPrevision : forecastIndex !== -1 ? forecastSelected.tipoPrevision : 'Normal',
        fechaLimiteString : forecastIndex !== -1 ? forecastSelected.fechaLimite : `${this.validity} 00:00:00`,
        folioPrevision : forecastIndex !== -1 ? forecastSelected.folioPrevision : '',
        previsionProducto : null,
        partida : forecastIndex !== -1 ? forecastSelected.partida : null,
        empresa : forecastIndex !== -1 ? forecastSelected.empresa : SessionUser.getInstance().getidEmpresaActual(),
        publicado : forecastIndex !== -1 ? forecastSelected.publicado : '',
      };

      if (forecastIndex !== -1) {

        if (forecastSelected.unidad === this.unitySelected.nombre) {
          forecastSelected.cantidad = parseFloat(forecastSelected.cantidad) + form.quantitySearchBar;
          this.dataForecast[forecastIndex] = forecastSelected;
        } else {
          this.dataForecast.push(forecast);
        }

      } else {
        this.dataForecast.push(forecast);
      }

      this.resetFormSearchBar();

    }

  }

  change() {

    let change = false;

    if (!_.isEqual(this.dataForecastOriginal, this.dataForecast)) {
      change = true;
    }

    return change;
  }

  updateItem($event, index: number, item ? : any) {
    $event.stopPropagation();
    this.classAnimation = 'w3-animate-zoom';

    this.updateForecastForm.patchValue({
      nameForecast: item.nombre,
      quantityForecast: item.cantidad,
      unityForecast: item.unidad,
    });

    this.indexForecastToEdit = index;
    this.unityForecast = { nombre: item.unidad };
    this.isEmptyForecastForm();
  }

  updateForecast(data: any) {

    this.nameForecast = data.nameForecast;
    this.quantityForecast = data.quantityForecast;
    this.unityForecast = { nombre: data.unityForecast };

    let forecast = JSON.parse(JSON.stringify(this.dataForecast[this.indexForecastToEdit]));
    forecast.nombre = data.nameForecast;
    forecast.cantidad = data.quantityForecast;
    forecast.unidad = data.unityForecast;

    this.dataForecast[this.indexForecastToEdit] = forecast;
  }

  deleteProduct(index) {
    this.dataForecast.splice(index, 1);
  }

  closeModal() {
    this.classAnimation = 'w3-animate-zoom-out';
    this.resetFormUpdateForecast();
  }

  resetFormSearchBar() {
    this.productSelected = { nombre: '' };

    this.searchBarForm.patchValue({
      quantitySearchBar: ''
    });

    this.unitySelected = { nombre: '' };

    this.isValidAddProduct = false;
  }

  resetFormUpdateForecast() {
    this.updateForecastForm.patchValue({
      nombre: '',
      cantidad: '',
      unidad: '',
    });
  }

}
