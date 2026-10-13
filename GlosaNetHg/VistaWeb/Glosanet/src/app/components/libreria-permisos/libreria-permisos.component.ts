import { Component, OnInit, ViewChild } from '@angular/core';
import { PermisosService } from '../../services/permisos/permisos.service';
import { SessionUser } from '../../services/session/session.service';
import { Parametros } from '../../model/parametros.class';
import { CoreContainerComponent } from '../core-container/core-container.component';
import _ from 'lodash';

import * as moment from 'moment';
import { Router } from '@angular/router';

/* Dev Tools */
import { DEBUG } from "../../settings/app.settings";

@Component({
  selector: 'gn-libreria-permisos',
  templateUrl: './libreria-permisos.component.html',
  styleUrls: ['./libreria-permisos.component.scss']
})
export class LibreriaPermisosComponent implements OnInit {

  @ViewChild('wizard', {static: false}) wizard: any;
  @ViewChild('drop', {static: false}) drop: any;

  public itemsFiltrar: any[] = [{nombre: 'Eventos', tipo: "cat", id:0},
    {nombre: 'Dossier', tipo: "", id:1, seccion:1},{nombre: 'Tecnovigilancia', tipo: "", id:2, seccion:1},
    {nombre: 'Sometimiento', tipo: "", seccion:1, id:3},{nombre: 'Todos los Eventos', tipo: "", id:4,seccion:1},
    {nombre: 'Fecha Realización', tipo: "cat", id:0},
    {nombre: 'Todos', tipo: "", id:5, seccion:2},
    {nombre: 'En Tiempo', tipo: "", id:6, seccion:2},
    {nombre: 'Fuera de Tiempo', tipo: "", id:7, seccion:2}];
  defaultFiltrar: any ;

  public itemsRegistros: any[] = [{nombre: 'Todos', tipo: "", id:1, seccion:1},
    {nombre: 'Con Folio', tipo: "", id:2, seccion:1},{nombre: 'Sin Folio', tipo: "", id:3, seccion:1},
    {nombre: 'En Trámite', tipo: "cat", id:0},
    {nombre: 'Trámite en Evaluación', tipo: "", id:4, seccion:2},{nombre: 'Trámite Disponible', tipo: "", id:5, seccion:2},
    {nombre: 'Trámite Entregado', tipo: "", id:6, seccion:2}];
  defaultRegistros: any = {nombre: 'Todos', tipo: "", id:1};

  public itemsResponsables: any[] = [];
  defaultResponsables: any = {nombre: 'Todos', tipo: "", seccion:1};

  resetSearch: boolean;
  searchTerm: string = null;
  placeholder: any = 'Buscar';
  searchTerm2: string= '';

  lst:any[] = [];
  arrayAux: any = [];
  tooltipV:any[] = [];
  today:Date = new Date();

  eventos_filtro: string = 'Todos los Eventos';
  frealizacion_filtro: string = 'Todos';
  registro_filtro: string =  'Todos';
  registro_filtro2: string = 'Todos';
  responsable_filtro: string = 'Todos';
  responsable_filtro2: string = 'Todos';


  lstItems: string [] = ['Activos', 'Historial'];

  date = new Date();
  yearActual: number = 0;
  nextYear: number = 0;

  constructor(private router: Router, private permisoService: PermisosService) { }

  ngOnInit() {
    this.yearActual = this.date.getFullYear();
    this.nextYear = this.yearActual + 1;
    this.obtenerPermisos();
  }


  irNuevoPermiso(){
    this.router.navigate(['protected/libreriaPermisos/nuevoPermiso']);
  }

  irMonitoreoPermisos(id){
    this.router.navigate(["protected/libreriaPermisos/monitoreoPermisos", id]);
  }
  /* ------------------------------------------------------------ SERVICIO OBTENER PERMISOS -------------------------------------------------------------- */

  obtenerPermisos(){
    SessionUser.getInstance().setLoading(true);
    let parametros = new Parametros();
    parametros.id=SessionUser.getInstance().getidEmpresaActual();
    this.permisoService.obtenerPermisosEmpresa(parametros)
      .subscribe(
        data => {
          for (let item of data) {
            if (item.estadoCofepris != undefined) {
              let array = item.estadoCofepris.split("|");
              item.estadoCofepris = array[0];
              item.fecha = array[1] != undefined ? array[1] : null;
            }
          }
          this.lst = data;
          DEBUG && console.log(this.lst);
          this.arrayAux = data;
          this.tooltipV = new Array(this.lst.length).fill(false);
          this.arrayAux = this.arrayAux.sort((a, b) => (a.nombre < b.nombre) ? -1 : 1);
          DEBUG && console.log(data);
          this.getCorreos();
          // this.resetArraysFiltros();
          SessionUser.getInstance().setLoading(false);
          DEBUG && console.log('Termina obtener registros');
        },
        error => {
          DEBUG && console.log('Error al obtener los registros', error);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al obtener los permisos');
          document.getElementById('requestFailed').style.display = 'block';
        });
  }


  /* ------------------------------------------------------------ Filtros -------------------------------------------------------------- */


  procesamientoFiltros(collection, option ) {

    if (option !==  'search'){
      collection = this.handlerSearch(collection);
    }
    if(option !== 'evento1'){
      collection = this.handlerFiltersEvent(collection);
    }
    if (option !== 'evento2'){
      collection = this.handlerFiltersTimes(collection);
    }
    if(option !== 'folio'){
      collection = this.handlerFiltersEvaluation(collection);
    }
    if(option !== 'tramite'){
      collection = this.handlerFiltersProcedure(collection);
    }
    if(option !== 'responsableGestion'){
      collection = this.handlerFiltersResponsableManagement(collection);
    }
    if(option !== 'responsableEvento'){
      collection = this.handlerFiltersResponsableEvent(collection);
    }

    this.arrayAux = collection;
  }

  /* ------------------------------------------------------------ FILTRO DE LA BARRA DE BUSCAR  -------------------------------------------------------------- */

  changeSearchTerm(nombre) {
    this.searchTerm2 = nombre;

    let dataSearch = this.handlerSearch(this.lst);

    this.procesamientoFiltros(dataSearch, 'search')
  }

  handlerSearch(collection) {
    if(this.searchTerm2 === ''){
      return collection;
    }
    let data = [];
    data= collection.filter((item) => {
      return item.producto.nombre.toLowerCase().indexOf(this.searchTerm2.toLowerCase()) !== -1;
    });

    return data;
  }



  /* ------------------------------------------------------------ PRIMER FILTRO DROP LIST  -------------------------------------------------------------- */


  dropFiltrar(value) {
    let data =[];
    let opc = '';
    if(value.seccion == 1){
      this.eventos_filtro = value.nombre;
      data =  this.handlerFiltersEvent(this.lst);
      opc = 'evento1';
    } else if(value.seccion == 2){
      this.frealizacion_filtro = value.nombre;
      data = this.handlerFiltersTimes(this.lst);
      opc ='evento2';
    }
    this.procesamientoFiltros(data,opc);
  }


  handlerFiltersEvent(collection) {
    if(this.eventos_filtro === 'Todos los Eventos') {
      return collection;
    }

    collection = _.filter(collection, o => {
      return o.eventoActual === this.eventos_filtro;
    })
    return collection;
  }

  handlerFiltersTimes(collection) {

    if(this.frealizacion_filtro === 'Todos') {
      return collection;
    }
    collection = _.filter(collection, o =>{
      if(this.frealizacion_filtro === 'En Tiempo' && o.eventos.length > 0){
        DEBUG && console.log(moment(o.eventos[0].frealizacion).diff(moment(this.today), 'days'));
        return moment(o.eventos[0].frealizacion).diff(moment(this.today), 'days') > 0 ;
      }else if(o.eventos.length > 0){ //fuera de tiempo
        DEBUG && console.log(moment(this.today).diff(moment(o.eventos[0].frealizacion), 'days'));
        return moment(this.today).diff(moment(o.eventos[0].frealizacion), 'days') > 0;
      }
    })
    return collection;
  }

  /* ------------------------------------------------------------ SEGUNDO FILTRO  DROP LIST  -------------------------------------------------------------- */


  dropRegistros(value) {
    let array = [];
    var opc = '';
    DEBUG && console.log(value);
    if (value.nombre === "Todos")
      this.registro_filtro2 = "Todos"
    if(value.seccion === 1){
      this.registro_filtro = value.nombre;
      array = this.handlerFiltersEvaluation(this.lst);
      opc ='folio'
    }else if(value.seccion === 2){
      this.registro_filtro2 = value.nombre;
      array = this.handlerFiltersProcedure(this.lst);
      opc = 'tramite'
    }
    this.procesamientoFiltros(array,opc);
  }

  handlerFiltersEvaluation(collection){
    if(this.registro_filtro === 'Todos'){
      return collection;
    }

    return _.filter(collection, o => {
      if(this.registro_filtro === 'Con Folio'){
        return o.folioRegistro !== '';
      }else{
        return o.folioRegistro === '';
      }
    })
  }

  handlerFiltersProcedure(collection){
    if(this.registro_filtro2 === 'Todos'){
      return collection;
    }
    return  _.filter(collection, o => (
        o.estadoCofepris.toLowerCase()  === `${this.registro_filtro2.toLowerCase()}.`
      )
    )
  }

  /* ------------------------------------------------------------ TERCERO FILTRO  DROP LIST  -------------------------------------------------------------- */




  dropResponsables(value){
    let array = [];
    var opc = '';
    DEBUG && console.log(value.nombre);
    if(value.seccion === 1){
      this.responsable_filtro = value.nombre;
      DEBUG && console.log(this.responsable_filtro);
      array = this.handlerFiltersResponsableManagement(this.lst);
      opc = 'responsableGestion';
      this.procesamientoFiltros(array,opc);
    }else if(value.seccion === 2){
      this.responsable_filtro2 = value.nombre;
      DEBUG && console.log(this.responsable_filtro2)
      array = this.handlerFiltersResponsableEvent(this.lst);
      opc = 'responsableEvento';
      this.procesamientoFiltros(array, opc);
    }
  }


  handlerFiltersResponsableManagement(collection){
    if(this.responsable_filtro === 'Todos'){
      return collection;
    }
    let responsable = _.filter(collection, responsable => {
      return this.responsable_filtro === responsable.usuario.email;
    })

    DEBUG && console.log(responsable);
    return responsable;
  }

  handlerFiltersResponsableEvent(collection){
    if(this.responsable_filtro2 === 'Todos'){
      return collection;
    }
    let evento = _.filter(collection, coleccion =>
      (_.filter(coleccion.eventos, evento => (
          _.filter(evento.correos, correo => (
            this.responsable_filtro2  === correo.correo
          )).length > 0
        )).length > 0
      ))
    DEBUG && console.log(evento);
    return evento;
  }

  /* ------------------------------------------------------------ METODO PARA OBTENER CORREOS   -------------------------------------------------------------- */

  getCorreos(){
    DEBUG && console.log ("dentro del metodo get correos ")
    let arrayEmail = [];
    let arrayGestion = [];
    let arrayEve = [];
    this.lst.length > 0 ? arrayGestion.push(this.lst[0].usuario.email) : null;

    for(let item of this.lst){
      let flag = false;
      for(let item2 of arrayGestion){
        if(item.usuario.email == item2){
          flag = true;
        }
      }
      if(!flag){
        arrayGestion.push(item.usuario.email);
      }
    }

    DEBUG && console.log(arrayEmail)
    _.forEach( this.lst, o =>{
      if(o.eventos.length > 0  && o.eventos !== undefined){
        if(o.eventos[0].correos !== undefined && o.eventos[0].correos.length > 0){
          for (var  indexEvento in o.eventos) {
            for (var indexCorreo in o.eventos[indexEvento]['correos']){
              arrayEve.push(o.eventos[indexEvento]['correos'][indexCorreo]['correo'])
            }
          }
        }
      }
    })
    let lst2 = this.itemsResponsables.slice(0);
    lst2.push({nombre: 'Responsables Gestión', tipo: "cat"});
    lst2.push({nombre: 'Todos', tipo: "", seccion:1});
    for(let email of arrayGestion){
      lst2.push({nombre: email, tipo: "", seccion:1});
    }
    this.itemsResponsables = lst2;
    this.itemsResponsables.push({nombre: 'Responsables Evento', tipo: "cat"});
    this.itemsResponsables.push({nombre: 'Todos', tipo: "", seccion:2});
    for(let item of arrayEve){
      this.itemsResponsables.push({nombre: item, tipo: "", seccion:2});
    }
  }

  actualizar(){
    this.obtenerPermisos();
  }



  tab($event) {
    DEBUG && console.log($event);
    //this.pWizard2 = $event;
    /* let array = [];
    if($event == 'Activos'){
      for(let item of this.lst){
        if(item.prorroga && item.eventoActual == 'Prorroga'){
          moment(item.eventos[0].frealizacion).diff(moment(this.today), 'days') > 0 ? array.push(item): null;
        }else{
          moment(item.ffin).diff(moment(this.today), 'days') > 0 ? array.push(item): null;
        }
      }

    }else if($event == 'Caducados'){
      for(let item of this.lst){
        if(item.prorroga && item.eventoActual == 'Prorroga'){
          moment(this.today).diff(moment(item.eventos[0].frealizacion), 'days') > 0 ? array.push(item): null;
        }else{
          moment(this.today).diff(moment(item.ffin), 'days') > 0 ? array.push(item): null;
        }
      }
    }else{
      for(let item of this.lst){
        array.push(item);
      }
    }

    DEBUG && console.log(array);

    this.arrayTab = Object.assign([], array);

    this.filtroGeneral(); */
  }
}
