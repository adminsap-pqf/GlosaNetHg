import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { CatalogoProductosService } from '../../services/catalogo-productos/catalogo-productos.service';
import { Parametros } from '../../model/parametros.class';
import { SessionUser } from '../../services/session/session.service';
import { HistorialImportaciones } from '../../model/historialImportaciones.class';
import { HistorialPermisos } from '../../model/historialPermisos.class';
import { Producto } from '../../model/producto.class';
import { GlosarioService } from '../../services/glosario/glosario.service';

/* Dev Tools */
import { DEBUG } from "../../settings/app.settings";

@Component({
  selector: 'gn-catalogo-productos',
  templateUrl: './catalogo-productos.component.html',
  styleUrls: ['./catalogo-productos.component.scss']
})
export class CatalogoProductosComponent implements OnInit {
  classAnimation = 'w3-animate-zoom-out';
  placeholder: any = 'Buscar';
  searchTerm: string = '';
  resetSearch: boolean;
  item = {};
  cuadricula:boolean = false;
  classAnimation2 = 'w3-animate-zoom-out';

//variables importacion permiso
  unidad: any;
  permiso: any;
  cantidad:any;
  arrayPermisos: any [] = [];
  cform: FormGroup;
  registro: boolean ;
  arrayList: any []= [];
  lstPermiso: any [] = [];
  fecha: any = new Date();
  arrayInsersion: boolean [] = [];
  resultado: boolean = false;

  // variables producto
  origen: any;
  vigencia: any;
  tipo: any;
  arrayPaises: any [] =[];

  productsData:any[] = [
    {
      nombre: 'Diluyente p/Dynastat Iny Pitex1',
      permisos: '5',
      codigo: '00001',
      origen: 'Alemania'
    },
    {
      nombre: 'Diluyente p/Dynastat Iny Pitex2',
      permisos: '10',
      codigo: '00002',
      origen: 'Italia'

    },
    {
      nombre: 'Diluyente p/Dynastat Iny Pitex3',
      permisos: '15',
      codigo: '00003',
      origen: 'Estados Unidos de América'
    },
    {
      nombre: 'Diluyente p/Dynastat Iny Pitex4',
      permisos: '20',
      codigo: '00004',
      origen: 'Andorra'
    },
    {
      nombre: 'Diluyente p/Dynastat Iny Pitex5',
      permisos: '25',
      codigo: '00005',
      origen: 'Bélgica'
    },
    {
      nombre: 'Diluyente p/Dynastat Iny Pitex6',
      permisos: '30',
      codigo: '00006',
      origen: 'Suiza'
    },
    {
      nombre: 'Diluyente p/Dynastat Iny Pitex7',
      permisos: '35',
      codigo: '00007',
      origen: 'Portugal'
    },
    {
      nombre: 'Diluyente p/Dynastat Iny Pitex8',
      permisos: '40',
      codigo: '00008',
      origen: 'Francia'
    },
    {
      nombre: 'Diluyente p/Dynastat Iny Pitex9',
      permisos: '45',
      codigo: '00009',
      origen: 'Turquía'
    },
    {
      nombre: 'Diluyente p/Dynastat Iny Pitex10',
      permisos: '50',
      codigo: '00010',
      origen: 'Ghana'
    }
  ];

  /* variables importacion */
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

  itemsDropList2:any[] = [
    {
      nombre:'Controlado'
    },
    {
      nombre:'No Controlado'
    }
  ];

  unidadSelected: any;
  permisosSelected: any;
  isAvailable: boolean = true;



  productsToShow = [];
  classItem:any = new Array(11).fill('item');


  countValid: any = 0;
  countValidAux: any[] = new Array(2).fill(false);
  datosGeneralesValid:boolean = false;

  nombreForm: boolean  = false;
  agregarPrevProduct: boolean = false;
  unidadForm: boolean = true;
  cantidadForm: boolean = true;
  validarCantidad2Dossier = false;
  cantidad1Dossier: number = 0;
  validCantidad: boolean = false;


  constructor(private catalogoProductos: CatalogoProductosService, private glosarioService:GlosarioService ) {
    this.productsToShow = this.productsData.slice();

  }


  ngOnInit() {


    this.cform = new FormGroup({
      permiso: new FormControl('', [Validators.required ]),
      cantidad: new FormControl('', [Validators.required ]),
      unidad: new FormControl('', [Validators.required ]),
      codigo: new FormControl('', [Validators.required ]),
      descripcion: new FormControl('', [Validators.required ])
    });

    this.cform.valueChanges.subscribe(
      () => {
        this.countValid = 0;
        this.countValidAux.fill(false);
        this.countValidAux[0] = this.cform.get('permiso').valid;
        this.countValidAux[1] = this.cform.get('cantidad').valid;
        this.countValidAux[2] = this.cform.get('unidad').valid;
        for (let i of this.countValidAux) {
          if (i) {
            this.countValid++;
          }
          if (this.countValid == 5) {
            this.datosGeneralesValid = true;
          } else {
            this.datosGeneralesValid = false;
          }
        }
      });

    this.obtenerPaises();
    this.crearImportacion();
  }

  searchTermChanged($event) {
    this.searchTerm = $event;
    this.productsToShow = this.productsData.filter((item) => {

      return item.nombre.toString().toLowerCase().indexOf($event.toLowerCase()) !== -1 ||
        item.codigo.toString().toLowerCase().indexOf($event.toLowerCase()) !== -1;
    });
  }

  selectItem(index){
    this.classItem.fill('item');
    this.classItem[index]='itemSelected';
    this.seleccionarProducto();
  }




  seleccionarProducto() {
    this.classAnimation2 = 'w3-animate-zoom';
    document.getElementById('id04').style.display = 'block';


  }


// registrar un producto
  registrarProducto( item?: any){
    SessionUser.getInstance().setLoading(true);
    let nom = SessionUser.getInstance().getidEmpresaActual();
    DEBUG && console.log("dentro del metodo registar");
    let input:any = document.getElementById('nombre');
    var nombre = input.value;
    let producto  = new Producto();
    producto.idEmpresa =nom;
    DEBUG && console.log("este es el nombre " + nombre );
    producto.nombre = nombre;
    producto.origen = this.origen;
    this.tipo === 'Controlado' ?  producto.tipoProducto = 1 : producto.tipoProducto = 2;
    producto.codigo = item.codigo;
    producto.descripcion = item.descripcion;
    DEBUG && console.log("el permiso vigente es "+ this.permiso);
    producto.permiso2 = this.permiso;
    producto.vigencia = this.fecha;
    this.catalogoProductos.agregarProducto(producto).subscribe(
      data =>{
        DEBUG && console.log(data);
        if(data){
          document.getElementById('idConfirm').style.display='block'
        }
        SessionUser.getInstance().setLoading(false);
      },
      error => {
        SessionUser.getInstance().setLoading(false);
      });
    this.limpiarModal();
  }

  obtenerPaises() {
    let parametro = new Parametros();
    this.glosarioService.obtenerPaises(parametro)
      .subscribe(
        data =>{
          DEBUG && console.log(data);
          let d = data.current;
          let pais = d.split(' ');
          DEBUG && console.log(pais);
          let pa = [];
          for(let item of pais){
            if(item !== ''){
              pa.push({ nombre: item });
            }
          }

          this.arrayPaises = pa;
          DEBUG && console.log(this.arrayPaises);
        },
        error =>{
          DEBUG && console.log('Error al traer la lista de paises: '+error);
        });
  }

  tipoProducto(tipo: any ){
    this.tipo = tipo.nombre;
    DEBUG && console.log("tipo de producto "+ this.tipo)
  }

  dropListOrigen(origen: any){
    this.origen = origen.nombre;
    DEBUG && console.log("origen "+ this.origen)
  }

  dropListPermiso(permiso: any){
    this.permiso = permiso.nombre;
    DEBUG && console.log("permiso "+ this.permiso)
  }


  // datosVaciosRegistroProducto(){
  //   let input:any = document.getElementById('nombre');
  //     this.agregarPrevProduct = true;
  //     if (!input.value || input.value === '' ){
  //       this.agregarPrevProduct = false;
  //     }
  //     let input2:any = document.getElementById('codigo');
  //     if (!input2.value || input2.value === '' || input2.value === null) {
  //       this.agregarPrevProduct = false;
  //     }
  //     if (!this.tipo|| this.tipo === '' ){
  //         this.agregarPrevProduct = false;
  //     }
  //     if (!this.origen || this.origen === '' ){
  //         this.agregarPrevProduct = false;
  //     }
  //     if (!this.permiso|| this.permiso === '' ){
  //         this.agregarPrevProduct = false;
  //     }
  //     if (!this.fecha|| this.fecha === '' ){
  //         this.agregarPrevProduct = false;
  //     }
  //     let input3:any = document.getElementById('descripcion');
  //     if (!input3.value || input3.value === '' || input3.value === null) {
  //       this.agregarPrevProduct = false;
  //     }
  // }



// crear una importacion
  crearImportacion(){
    SessionUser.getInstance().setLoading(true);
    this.arrayPermisos = [];
    let array2 =[];
    let parametros = new Parametros();
    parametros.idproducto=151402;
    parametros.flagProductoPartidas=false;
    parametros.id =SessionUser.getInstance().getIdClient();
    let nom = SessionUser.getInstance().getidEmpresaActual();
    this.catalogoProductos.obtenerPermisos(parametros).subscribe(
      data =>{
        DEBUG && console.log(data);
        for(let item of data){
          let permisos = {
            nombre: item.permiso,
            idPermiso: item.pkHistorialPermisos
          }
          array2.push(permisos);
        }
        this.arrayPermisos= array2;
        SessionUser.getInstance().setLoading(false);
      },
      error => {
        SessionUser.getInstance().setLoading(false);
      });
    this.classAnimation = 'w3-animate-zoom';
  }

  agregarImportacion(item?: any){
    this.datosVaciosImportacion();
    if(this.agregarPrevProduct){
      this.cantidad= item.cantidad
      DEBUG && console.log("valores del modal " + this.cantidad + " " + this.permiso + " " + this.unidad )
      let historial = new HistorialImportaciones();
      historial.cantidad = this.cantidad;
      historial.unidad = this.unidad;
      historial.idpermiso=this.permiso.idPermiso;
      DEBUG && console.log("id del permiso selecionado " + this.permiso.nombre + " "+ historial.idpermiso)
      historial.fk02partidad = 151402;
      this.catalogoProductos.agregarHistorialImportacion(historial).subscribe(
        data => {
          if(data){
            document.getElementById('idConfirm').style.display='block'
          }
          SessionUser.getInstance().setLoading(false);
        }
      );
    }

    DEBUG && console.log("registro se logro? " + this.registro);
    this.limpiarModal();

  }

  dropPermiso(permiso: any){
    this.permiso = permiso;
    DEBUG && console.log("este es el num permiso "+ this.permiso)
    setTimeout( () => {
      this.datosVaciosImportacion();
    }, 200);
  }


  dropList(unidad: any){
    this.unidad= unidad.nombre;
    DEBUG && console.log("este es la unidad " + this.unidad)
    setTimeout( () => {
      this.datosVaciosImportacion();
    }, 200);
  }

  input(cantidad){
    this.cantidad = cantidad;
  }


  datosVaciosImportacion(){
    this.agregarPrevProduct = true;
    if (!this.permiso || !this.permiso.nombre || this.permiso.nombre === '' ){
      this.agregarPrevProduct = false;
    }
    if (this.unidad === undefined || this.unidad === '' ){
      this.agregarPrevProduct = false;
    }
    if (this.cform.get('cantidad').value === undefined || this.cform.get('cantidad').value === '' || this.cform.get('cantidad').value === null) {
      this.agregarPrevProduct = false;
    }
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


  limpiarModal(){
    this.permisosSelected = {nombre:''};
    this.unidadSelected = {nombre:''};
    let input:any = document.getElementById('nombre');
    input.value= '';
    this.cform.patchValue({
      cantidad:'',
      permiso:'',
      unidad: '',
      codigo: '',
      descripcion:''
    });
    this.agregarPrevProduct = false;
  }


// crear un permiso
  crearPermiso(){
    document.getElementById('id02').style.display='block';
    this.catalogoProductos.obtenerPermisosGenerales().subscribe(
      data => {
        DEBUG && console.log(data);
        for(let item of data){
          let permisos ={
            permiso: item.permiso
          };
          this.arrayList.push(permisos);
        }
        SessionUser.getInstance().setLoading(false);
      },
      error => {
        SessionUser.getInstance().setLoading(false);
      }
    );
  }

  agregarPermiso( item?: any){
    this.datosVaciosPermisos();
    if(this.agregarPrevProduct){
      this.permiso = item.permiso;
      DEBUG && console.log("numero de permiso "+this.permiso +" "+this.fecha);
      let permiso =  this.arrayList.filter(h => {
        if(this.permiso !== ''){
          return h.permiso === this.permiso;
        }
      });
      if(permiso.length > 0){
        DEBUG && console.log("el permiso existe")
      }else{
        DEBUG && console.log("el permiso no existe");
        let permiso = new  HistorialPermisos();
        permiso.vigenciaPermiso = this.fecha;
        permiso.permiso= this.permiso;
        permiso.fkPartidas = 151402;
        permiso.flagProductoPartidas = false;
        this.catalogoProductos.agregarHistorialPermiso(permiso).subscribe(
          data => {
            DEBUG && console.log(data);
            if(data){
              document.getElementById('idConfirm').style.display='block'
            }
            SessionUser.getInstance().setLoading(false);
          });
        this.limpiarModal();
      }
    }
  }

  getFechaImpl(fecha: any ){
    this.fecha = fecha;
    DEBUG && console.log(this.fecha)
  }


  datosVaciosPermisos(){
    this.agregarPrevProduct = true;
    if (!this.fecha ||  this.fecha === ''){
      this.agregarPrevProduct = false;
    }
    else
      this.nombreForm = true;
    if (!this.cform.get('permiso').value === undefined || this.cform.get('permiso').value === '' || this.cform.get('permiso').value === null) {
      this.agregarPrevProduct = false;
    }
  }

}
