import {Component, OnInit} from "@angular/core";
import { Router } from '@angular/router';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { PrevisionesService } from '../../services/previsiones/previsiones.service';
import { SessionUser } from '../../services/session/session.service';
import { Parametros } from '../../model/parametros.class';
import { Producto } from '../../model/producto.class';
import { Previsiones } from '../../model/previsiones.class';

/* Dev Tools */
import { DEBUG } from "../../settings/app.settings";

@Component({
  selector: 'gn-nueva-prevision',
  templateUrl: './nueva-prevision.component.html',
  styleUrls: ['./nueva-prevision.component.scss']
})
export class NuevaPrevisionComponent implements OnInit{

  validarCantidad2Dossier = false;

  cantidad1Dossier: number = 0;
  classAnimation = 'w3-animate-zoom-out';
  classAnimation3 = 'w3-animate-zoom-out';
  placeholder: any = 'Buscar';
  searchTerm: string = null;
  resetSearch: boolean;
  arrayAux: any = [];
  search: any = [];
  arrayPrevisiones: any = [] = [];
  arrayProductos: any = [] = [];
  model: any = {};
  itemPrevisiones: any = [] = [];
  lst:any[] = [];
  tooltipV:any[] = [];
  resetTabs: boolean = false;
  visibleCampos: boolean = false;
  typePopUp:number = 0;
  tabs: boolean = false;    /* false = Actua como wizard | true = actua como tab */
  check: boolean = true;    /* false = se esconde la palomita | true = se muestra la palomita */
  dForm: FormGroup;
  formF: FormGroup;
  nombre: any = '';
  partida: any = '';
  vigencia: any = '';
  folioPrevision: any = '';
  productos: any = '';
  fechaLimite: any = '';
  fechaCreacion: any = '';
  estado: any = '';
  tipoPrevision: any = '';
  cantidad: any = '';
  descripcion: any = '';
  empresa: any = '';
  unidad: any = '';
  previsionProducto: any;
  publicado: any = '';
  countValid: any = 0;
  productos2: any;
  idProductos: any;
  idProductos2: any;
  countValidAux: any[] = new Array(5).fill(false);
  datosGeneralesValid:boolean = false;
  indexProductoAEditar: number = -1;
  unidadAsignada: string = '';
  nombreAsignado: string = '';
  arrayPRevisiones2: any=[] = [];
  idPrevision: any;
  validName: boolean = false;
  validCantidad: boolean = false;
  validUnidad: boolean = false;
  nombreForm: boolean = true;
  unidadForm: boolean = true;
  cantidadForm: boolean = true;
  agregarPrevProduct: boolean = false;
  indexModal: number = 0;
  pWizard: any = 0;

  public isAvailable: boolean = true;

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

  unidadSelected: any;
  defaultSelected1: any;
  modalSelect: any;


  constructor(private previsiones: PrevisionesService, private router: Router){

  }
  ngOnInit() {

    this.dForm = new FormGroup({
      nombre: new FormControl('', [Validators.required, Validators.pattern('^([a-zñáéíóú, A-ZÁÉÍÓÚ]{1}[a-zñáéíóú,A-ZÁÉÍÓÚ]+[\s]*)+$')]),
      cantidad: new FormControl('', [Validators.required, Validators.pattern('^(([0-9])+(-){0,1})+$')]),
      unidad: new FormControl('', [Validators.required, Validators.pattern('^([a-zñáéíóú, A-ZÁÉÍÓÚ]{1}[a-zñáéíóú,A-ZÁÉÍÓÚ]+[\s]*)+$')]),
    });

    this.formF = new FormGroup({
      nombre: new FormControl('', [Validators.required, Validators.pattern('^([a-zñáéíóú, A-ZÁÉÍÓÚ]{1}[a-zñáéíóú,A-ZÁÉÍÓÚ]+[\s]*)+$')]),
      cantidad: new FormControl('', [Validators.required, Validators.pattern('^(([0-9])+(-){0,1})+$')]),
      unidad: new FormControl('', [Validators.required, Validators.pattern('^([a-zñáéíóú, A-ZÁÉÍÓÚ]{1}[a-zñáéíóú,A-ZÁÉÍÓÚ]+[\s]*)+$')]),
      productos: new FormControl('', [Validators.required, Validators.pattern('^([a-zñáéíóú, A-ZÁÉÍÓÚ]{1}[a-zñáéíóú,A-ZÁÉÍÓÚ]+[\s]*)+$')]),
    });

    this.formF.valueChanges.subscribe(
    () => {
      this.countValid = 0;
      this.countValidAux.fill(false);
      this.countValidAux[0] = this.formF.get('nombre').valid;
      this.countValidAux[1] = this.formF.get('cantidad').valid;
      this.countValidAux[2] = this.formF.get('unidad').valid;
      this.countValidAux[3] = this.formF.get('productos').valid;

      for (let i of this.countValidAux) {
        if (i) {
          this.countValid++;
        }
        if (this.countValid == 4) {
          DEBUG && console.log("Valido");
          this.datosGeneralesValid = true;
        } else {
          this.datosGeneralesValid = false;
        }
      }
    });

    this.obtenerPrevisiones();
    this.obtenerProductos();
  }

  irPrevisiones() {
    this.confirm(false, 0);
    SessionUser.getInstance().setConfirm(false);
    this.router.navigate(["protected/permisos"]);
  }

  confirm(open: boolean, index: number) {
    SessionUser.getInstance().setConfirm(true);
    this.indexModal = index;
    if (open) {
      this.classAnimation3 = 'w3-animate-zoom';
      document.getElementById('idConfirm').style.display = 'block'
    } else {
      this.classAnimation3 = 'w3-animate-zoom-out';
      document.getElementById('idConfirm').style.display = 'none';

    }
  }

  back() {
    this.confirm(false, 1);
    SessionUser.getInstance().setConfirm(false);
    //this.resetAll();
    if (this.pWizard > 0) {
      this.pWizard--;
    }

    DEBUG && console.log(this.pWizard);0

  }

  validarForm() {
    this.datosGeneralesValid = true;

    if (this.dForm.get('nombre').value === undefined || this.dForm.get('nombre').value === '' ){
      this.datosGeneralesValid = false;
      this.validName = false;
    }
    else
      this.validName = true;


    if (this.dForm.get('unidad').value === undefined || this.dForm.get('unidad').value === '' ){
        this.datosGeneralesValid = false;
        this.validUnidad = false;
    }
    else
      this.validUnidad = true;

    if (this.dForm.get('cantidad').value === undefined || this.dForm.get('cantidad').value === '' ) {
      this.datosGeneralesValid = false;
      this.validCantidad = false;
    }
    else
      this.validCantidad = true;

  }

  dropList($event) {
    //this.unidadSelected = {nombre: $event.nombre};
    DEBUG && console.log($event);
    DEBUG && console.log(this.indexProductoAEditar);
    DEBUG && console.log(this.arrayPrevisiones);
    this.unidadAsignada = $event.nombre;
    setTimeout( () => {
        this.datosVacios();
    }, 200);

  }

  /* Busqueda */
  dropProducts($event, nombre: any, productos: any) {
    DEBUG && console.log($event);
    this.arrayProductos= [];
    for (let item of this.lst) {
      this.arrayProductos.push(item);
    }
    if ($event !== '') {
      this.search = [];
      for (let item of this.arrayProductos) {
        if (item.nombre !== -1) {
          this.search.push(item);
        }
      }
      }else {
        this.arrayProductos= [];
        for (let item of this.lst) {
          this.arrayProductos.push(item);
        }
      }
      this.productos2 = $event;
      DEBUG && console.log(this.productos2);
      setTimeout( () => {
          this.datosVacios();
      }, 200);
  }

  validarCantidad(cantidad){
    if(cantidad > 0){
      let re = /^[0-9]+$/;
      DEBUG && console.log(re.test(String(cantidad).toLowerCase()));
      return re.test(String(cantidad).toLowerCase());
    }else{
      return false;
    }
  }

  agregarProductos(item:any){
    DEBUG && console.log(item);
    this.datosVacios();
    if (this.agregarPrevProduct) {
      let previsiones = {
        nombre: this.productos2.nombre,
        cantidad: item.cantidad,
        unidad: this.unidadAsignada,
        productos : this.productos2.productos,
        idPrevision : this.idPrevision !== undefined ? this.idPrevision : '',
        vigenciaString : this.vigencia !== '' ? this.vigencia : '2019-04-02',
        fechaCreacionString : this.fechaCreacion !== undefined ? this.fechaCreacion : '',
        estado : this.estado !== '' ? this.estado : 'Por Revisar',
        tipoPrevision : this.tipoPrevision !== '' ? this.tipoPrevision : 'Normal',
        fechaLimiteString : this.fechaLimite !== undefined ? this.fechaLimite : '2018-05-22 00:00:00',
        folioPrevision : this.folioPrevision !== undefined ? this.folioPrevision : '648OSI984SB',
        previsionProducto : null,
        partida : this.partida !== '' ? this.partida : null,
        empresa : this.empresa !== '' ? this.empresa : SessionUser.getInstance().getidEmpresaActual(),
        publicado : this.publicado !== undefined ? this.publicado : '',
      };

      this.arrayPrevisiones.push(previsiones);
      DEBUG && console.log(this.arrayPrevisiones);
      this.resetForm();
    }
  }

  eliminarProducto(i){
    this.arrayPrevisiones.splice(i,1);
    DEBUG && console.log(this.arrayPrevisiones);
  }

  actualizarDatos($event, type:number, index: number, item?:any) {
    $event.stopPropagation();
    DEBUG && console.log(item);
    this.resetTabs = false;
    this.visibleCampos = true;
    this.classAnimation = 'w3-animate-zoom';
    this.typePopUp = type;
    this.dForm.patchValue({
      nombre: item.nombre,
      cantidad: item.cantidad,
      unidad: item.unidad,
        });
        this.indexProductoAEditar = index;
        this.modalSelect = {nombre:item.unidad};
        this.validarForm();
  }

  closeModal() {
    this.resetTabs = true;
    this.classAnimation = 'w3-animate-zoom-out';
    this.resetForms();
  }

  resetForms(){
    this.dForm.patchValue({
      nombre: '',
      cantidad: '',
      unidad: '',
    });
  }

  resetForm(){
    this.unidadSelected = {nombre:''};
    this.defaultSelected1 = {nombre:''};
    this.formF.patchValue({
      cantidad: '',
    });
    this.nombreForm = true;
    this.unidadForm = true;
    this.cantidadForm = true;
    this.agregarPrevProduct = false;
  }

  datosVacios(){
      this.agregarPrevProduct = true;

      let nombre = document.getElementsByName('nombre')[0].children[0].children[0].children[0].children[0].innerHTML;
      let unidad = document.getElementsByName('unidad')[0].children[0].children[0].children[0].children[0].innerHTML;
      if (nombre === undefined || nombre === '' ){
        this.agregarPrevProduct = false;
        this.nombreForm = false;
      }
      else
        this.nombreForm = true;


      if (unidad === undefined || unidad === '' ){
          this.agregarPrevProduct = false;
          this.unidadForm = false;
      }
      else
        this.unidadForm = true;

      if (this.formF.get('cantidad').value === undefined || this.formF.get('cantidad').value === '' || this.formF.get('cantidad').value === null) {
        this.agregarPrevProduct = false;
        this.cantidadForm = false;
      }
      else

        this.cantidadForm = true;
  }

  fillDatosGenerales(item: any){
    this.nombre = item.nombre;
    this.cantidad = item.cantidad;
    this.unidad = item.unidad;
    this.vigencia = item.vigencia;
    this.fechaLimite = item.fechaLimite;
    this.fechaCreacion = item.fechaCreacion;
    this.estado = item.estado;
    this.tipoPrevision = item.tipoPrevision;
    this.folioPrevision = item.folioPrevision;
    this.descripcion = item.descripcion;
    this.empresa = item.empresa;
    this.publicado = item.publicado;
    this.partida = item.partida;
  }

  resetDatosGenerales(){
    this.nombre = "";
    this.cantidad = "";
    this.unidad = "";
    this.partida = "";
    this.vigencia = "";
    this.fechaLimite = "";
    this.fechaCreacion = "";
    this.estado = "";
    this.tipoPrevision = "";
    this.folioPrevision = "";
    this.descripcion = "";
    this.empresa = "";
    this.publicado = "";
    this.productos = "";
  }

  actualizar(datosGenerales:any){
    DEBUG && console.log(datosGenerales);
    this.nombre = datosGenerales.nombre;
    this.cantidad = datosGenerales.cantidad;
    this.unidad = datosGenerales.unidad;
    this.arrayPrevisiones = this.arrayPrevisiones.map((p, index) => {
      if(index === this.indexProductoAEditar){
        DEBUG && console.log('Enucentra el indicado');
        DEBUG && console.log(p);
        let nuevoProducto = Object.assign({},p, {
          nombre : datosGenerales.nombre,
          cantidad : datosGenerales.cantidad,
          unidad : this.unidadAsignada
        });
        DEBUG && console.log(nuevoProducto);
        return nuevoProducto
      }
      return p;
    })
  }

/* servicios */
obtenerProductos() {
  let parametros = new Parametros();
  parametros.id = SessionUser.getInstance().getIdClient();
  parametros.empresa = SessionUser.getInstance().getidEmpresaActual();
  this.previsiones.obtenerProductos(parametros)
    .subscribe(
      data => {
        this.lst = data;
        this.arrayProductos = data;
        this.arrayProductos= this.arrayProductos.sort((a, b) => (a.nombre < b.nombre) ? -1 : 1);
        DEBUG && console.log(data);
        SessionUser.getInstance().setLoading(false);
      },
      error => {
        DEBUG && console.log('Error');
      });
  }

  obtenerPrevisiones() {
    let previsiones = new Previsiones();
    previsiones.empresa = SessionUser.getInstance().getidEmpresaActual();
    DEBUG && console.log(previsiones.empresa);
    this.previsiones.obtenerPrevisiones(previsiones)
      .subscribe(
        data => {
          for (let item of data) {
            let previsiones = {
              nombre: item.nombre !== undefined ? item.nombre : '',
              cantidad: item.cantidad !== undefined ? item.cantidad : '',
              unidad: item.unidad !== undefined ? item.unidad : '',
              idPrevision : item.idPrevision !== undefined ? item.idPrevision : '',
              vigencia : item.vigencia !== undefined ? item.vigencia : '',
              fechaCreacion : item.fechaCreacion !== undefined ? item.fechaCreacion : '',
              estado : item.estado !== undefined ? item.estado : '',
              tipoPrevision : item.tipoPrevision !== undefined ? item.tipoPrevision : '',
              descripcion : item.descripcion !== undefined ? item.descripcion : '',
              fechaLimite : item.fechaLimite !== undefined ? item.fechaLimite : '',
              folioPrevision : item.folioPrevision !== undefined ? item.folioPrevision : '',
              productos : item.productos !== undefined ? item.productos : '',
              previsionProducto : item.previsionProducto !== undefined ? item.previsionProducto : '',
              partida : item.partida !== undefined ? item.partida : '',
              empresa : item.empresa !== undefined ? item.empresa : '',
              publicado : item.publicado !== undefined ? item.publicado : '',
            };

            this.idPrevision = item.idPrevision !== undefined ? item.idPrevision : '';
            this.cantidad = item.cantidad !== undefined ? item.cantidad : '';
            this.unidad = item.unidad !== undefined ? item.unidad : '';
            this.vigencia = item.vigencia !== undefined ? item.vigencia : '';
            this.fechaCreacion = item.fechaCreacion !== undefined ? item.fechaCreacion : '';
            this.estado = item.estado !== undefined ? item.estado : '';
            this.tipoPrevision = item.tipoPrevision !== undefined ? item.tipoPrevision : '';
            this.descripcion = item.descripcion !== undefined ? item.descripcion : '';
            this.fechaLimite = item.fechaLimite !== undefined ? item.fechaLimite : '';
            this.folioPrevision = item.folioPrevision !== undefined ? item.folioPrevision : '';
            this.productos = item.productos !== undefined ? item.productos : '';
            this.previsionProducto = item.previsionProducto !== undefined ? item.previsionProducto : '';
            this.partida = item.partida !== undefined ? item.partida : '';
            this.empresa = item.empresa !== undefined ? item.empresa : '';
            this.publicado = item.publicado !== undefined ? item.publicado : '';

            this.arrayPrevisiones.push(previsiones);
          }
          this.arrayPrevisiones = this.arrayPrevisiones.sort((a, b) => (a.nombre < b.nombre) ? -1 : 1);
          this.itemPrevisiones = new Array(this.arrayPrevisiones.length).fill('itemsDiv');
          DEBUG && console.log(data);
          SessionUser.getInstance().setLoading(false);
        },
        error => {
          DEBUG && console.log('Error');
        });
  }

  guardarPrevision() {
    let previsiones = new Previsiones();
    SessionUser.getInstance().getIdClient();
    previsiones.empresa = SessionUser.getInstance().getidEmpresaActual();

    this.previsiones.guardarPrevision(this.arrayPrevisiones)
      .subscribe(
        data => {
          this.arrayPrevisiones = data;
          // this.arrayPrevisiones = this.arrayPrevisiones.sort((a, b) => (a.nombre < b.nombre) ? -1 : 1);
          this.idPrevision = data[0].idPrevision;
          DEBUG && console.log(data);
          SessionUser.getInstance().setLoading(false);
        },
        error => {
          DEBUG && console.log('Error');
        });

    }

}
