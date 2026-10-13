import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Parametros} from '../../model/parametros.class';
import {SessionUser} from '../../services/session/session.service';
import {Producto} from '../../model/producto.class';
import {PermisosService} from '../../services/permisos/permisos.service';
import {Permiso} from '../../model/permiso.class';
import {Usuario} from '../../model/usuario.class';

import * as moment from 'moment';
import {Prevision} from '../../model/prevision.class';
import {PrevisionesService} from '../../services/previsiones/previsiones.service';
/* Dev Tools */
import {DEBUG} from "../../settings/app.settings";

@Component({
  selector: 'gn-historial-importaciones',
  templateUrl: './historial-importaciones.component.html',
  styleUrls: ['./historial-importaciones.component.scss']
})
export class HistorialImportacionesComponent implements OnInit {

  @ViewChild('containerBar', {static: false}) container: ElementRef;

  ///////////////////////////////////////////////////////////////////////////////////////////////////
  placeholder: any = 'Buscar Nombre de usuario, Estado';
  searchTerm: string = null;
  resetSearch: boolean;
  arrayAux: any = [];
  search: any = [];

  widthContainer: number;

  /* modal producto */
  itemsTipoProducto: any = [{nombre: 'Controlado', tipo: 1}, {nombre: 'No Controlado', tipo: 2}];
  itemsUnidad: any = [{nombre: 't'}, {nombre: 'Kg'}, {nombre: 'g'}, {nombre: 'l'}, {nombre: 'ml'}, {nombre: 'pza'}];

  prevision: Prevision = new Prevision();
  idProxPrevision: number = 0;
  idPrevisionActual: number = 0;
  producto: Producto = new Producto();
  usuario: Usuario = new Usuario();

  lstProductos: any = [];

  folio: string = '';

  lstPermisos: Array<Permiso> = new Array<Permiso>();

  classAnimation = 'w3-animate-zoom-out';

  defaultTipoProducto = {nombre: '', tipo: 0};
  defaultUnidad = {nombre: ''};

  date = new Date();
  yearActual: number = 0;
  nextYear: number = 0;

  sub: any;
  idProducto: number = 0;

  unidad = '';

  totalProducto: number = 0;
  unidadProducto: string = '';
  totalImportaciones: number = 0;

  totalGrafica: number = 0;

  whidtImp: number = 0;
  widthTotalP: number = 0;

  totalPermisosProducto: number = 0;

  barraProducto: number = 0;

  productoExist: boolean = false;

  permisovigente: number = 0;

  constructor(private permisoService: PermisosService, private router: Router, private route: ActivatedRoute, private previsionService: PrevisionesService) {
  }

  ngOnInit() {
    this.yearActual = this.date.getFullYear();
    this.nextYear = this.yearActual + 1;
    this.sub = this.route.params.subscribe(params => {
      this.idProducto = params['id'];

    });
    this.getPrevisionActual(SessionUser.getInstance().getidEmpresaActual()).then(() => {
      this.getPrevision(this.idPrevisionActual).then(() => {
        if (this.idProxPrevision !== 0) {
          this.getPrevision(this.idProxPrevision).then(() => {
            this.getProductos(this.prevision.idPrevision).then(() => {
              //SessionUser.getInstance().setLoading(false);

              this.obtenerProductosCompletosPrevisionActual(SessionUser.getInstance().getidEmpresaActual()).then(() => {
                DEBUG && console.log(this.idProducto);
                this.obtenerPermisosActual(this.idProducto).then(() => {
                  if (this.lstPermisos.length > 0) {
                    let parametro = new Parametros();
                    parametro.condicion = this.lstPermisos[0].folioRegistro;
                    if (parametro.condicion !== '' && parametro.condicion !== undefined) {
                      this.obtenerImportaciones(parametro);
                    }
                  } else {
                    DEBUG && console.log('No hay permisos');
                    SessionUser.getInstance().setLoading(false);
                  }
                });
              });
            });
          });
        } else {
          this.obtenerProductosCompletosPrevisionActual(SessionUser.getInstance().getidEmpresaActual()).then(() => {
            this.obtenerPermisosActual(this.idProducto).then(() => {
              if (this.lstPermisos.length > 0) {
                let parametro = new Parametros();
                parametro.condicion = this.lstPermisos[0].folioRegistro;
                if (parametro.condicion !== '' && parametro.condicion !== undefined) {
                  this.obtenerImportaciones(parametro);
                }
              } else {
                DEBUG && console.log('No hay permisos');
                SessionUser.getInstance().setLoading(false);
              }
            });
          });
        }
      });
    });
  }

  ngAfterViewInit() {
    this.widthContainer = this.container.nativeElement.offsetWidth;
    DEBUG && console.log(this.container.nativeElement.offsetWidth);
  }

  /* Busqueda de search bar */
  changeSearchTerm($event) {
    this.arrayAux = [];
    for (let item of this.lstPermisos) {
      this.arrayAux.push(item);
    }
    if ($event !== '') {
      this.search = [];
      for (let item of this.arrayAux) {
        if (item.producto.nombre.toLowerCase().indexOf($event.toLowerCase()) !== -1) {
          this.search.push(item);
        }
      }
      if (this.search.length > 0) {
        this.arrayAux = [];
        for (let item of this.search) {
          this.arrayAux.push(Object.assign(new Permiso, item));
        }
      } else {
        this.arrayAux = [];
      }
    } else {
      this.arrayAux = [];
      for (let item of this.lstPermisos) {
        this.arrayAux.push(Object.assign(new Permiso, item));
      }
    }
  }

  irPermisos() {
    this.router.navigate(["protected/permisos"]);
  }

  /* modal producto */
  nuevoProducto() {
    this.classAnimation = 'w3-animate-zoom';
    if (this.producto.tipoProducto == 1) {
      this.defaultTipoProducto = {nombre: 'Controlado', tipo: 1};
    } else {
      this.defaultTipoProducto = {nombre: 'No Controlado', tipo: 2};
    }
  }

  cerrarProducto() {
    this.classAnimation = 'w3-animate-zoom-out';
    document.getElementById('id01').style.display = 'none';
  }

  /* modal producto */

  obtenerPermisosActual(id) {
    return new Promise((resolve, reject) => {
      let parametros = new Parametros();
      parametros.id = id;
      this.permisoService.obtenerPermisosActual(parametros)
        .subscribe(
          data => {
            DEBUG && console.log(data);
            this.lstPermisos = new Array<Permiso>();
            this.unidad = data[0] !== undefined ? this.unidadString(data[0].unidad) : '';
            for (let item of data) {
              let permiso = Object.assign(new Permiso, item);

              permiso.diasNaturales = this.diasNaturales(new Date(item.finicio), new Date(item.ffin));
              permiso.vigente = this.permisoVigente(new Date(item.finicio), new Date(item.ffin));
              let estadoCofepris = permiso.estadoCofepris.split("|");
              permiso.estadoCofepris = estadoCofepris[0];
              this.lstPermisos.push(permiso);
              this.arrayAux.push(permiso);

              this.totalPermisosProducto += Number(item.cantidad);
            }
            if (this.lstPermisos.length > 0) {
              this.producto = Object.assign(new Producto, this.lstPermisos[0].producto);
              this.usuario = Object.assign(new Usuario, this.lstPermisos[0].usuario);
              this.folio = this.lstPermisos[0].folioRegistro;
              for (let item of this.lstPermisos) {
                if (item.vigente) {
                  this.permisovigente = item.idpermiso;
                  break;
                }
              }
            }
            DEBUG && console.log(this.lstPermisos);
            DEBUG && console.log(this.producto);
            resolve();
          },
          error => {
            DEBUG && console.log(error);
            SessionUser.getInstance().setLoading(false);
            SessionUser.getInstance().setMessageFailed('al obtener los permisos');
            document.getElementById('requestFailed').style.display = 'block';
          });
    });
  }

  obtenerImportaciones(parametro: Parametros) {
    this.permisoService.obtenerImportaciones(parametro)
      .subscribe(
        data => {
          DEBUG && console.log(data);
          for (let item of data) {
            this.totalImportaciones += Number(item.salida);
          }
          this.getBar();
          SessionUser.getInstance().setLoading(false);
        },
        error => {
          DEBUG && console.log(error);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al obtener las importaciones');
          document.getElementById('requestFailed').style.display = 'block';
        });
  }

  unidadString(opc) {
    let unidad = '';
    switch (opc) {
      case 't':
        unidad = 'Toneladas';
        break;
      case 'Kg':
        unidad = 'Kilogramos';
        break;
      case 'g':
        unidad = 'Gramos';
        break;
      case 'l':
        unidad = 'Litros';
        break;
      case 'ml':
        unidad = 'Mililitros';
        break;
      case 'pza':
        unidad = 'Piezas';
        break;
      default:
        break;
    }

    return unidad;
  }


  diasNaturales(finicio: Date, ffin: Date) {
    let days = moment(ffin, "DD-MM-YYYY").diff(moment(finicio, "DD-MM-YYYY"), 'days');
    DEBUG && console.log(days);
    return days;
  }

  permisoVigente(finicio: Date, ffin: Date) {
    if (moment(this.date).isBetween(finicio, ffin)) {
      return true;
    } else {
      return false;
    }
  }

  getPrevisionActual(id: number) {
    return new Promise((resolve, reject) => {
      SessionUser.getInstance().setLoading(true);
      let parametro = new Parametros();
      parametro.id = id;
      this.previsionService.getPrevisionActual(parametro)
        .subscribe(
          data => {
            DEBUG && console.log(data);

            if (data && data.mediaValue) {
              this.idPrevisionActual = Number(data.mediaValue);
              resolve();
            } else {
              reject();
            }
          },
          error => {
            DEBUG && console.log(error);
            SessionUser.getInstance().setLoading(false);
            SessionUser.getInstance().setMessageFailed('al obtener las previsiones');
            document.getElementById('requestFailed').style.display = 'block';
          });
    });
  }

  getPrevision(id: number) {
    return new Promise((resolve, reject) => {
      let parametro = new Parametros();
      parametro.id = id;
      this.previsionService.getPrevision(parametro)
        .subscribe(
          data => {
            DEBUG && console.log(data);
            this.idProxPrevision = data.idProxPrevision;
            this.prevision = Object.assign(new Prevision, data);
            DEBUG && console.log(this.prevision);
            resolve();
          },
          error => {
            DEBUG && console.log(error);
            SessionUser.getInstance().setLoading(false);
            SessionUser.getInstance().setMessageFailed('al obtener las previsión');
            document.getElementById('requestFailed').style.display = 'block';
          });
    });
  }


  obtenerProductosCompletosPrevisionActual(id: number) {
    SessionUser.getInstance().setLoading(true);
    return new Promise((resolve, reject) => {
      let parametro = new Parametros();
      parametro.id = id;
      this.previsionService.obtenerProductosCompletosPrevisionActual(parametro)
        .subscribe(
          data => {
            DEBUG && console.log(data);
            /* this.idProxPrevision = data.idProxPrevision;
            this.prevision = Object.assign(new Prevision, data); */
            DEBUG && console.log(this.prevision);
            for (let item of data) {
              if (this.idProducto == item.idProducto) {
                this.totalProducto = Number(item.cantidad);
                this.unidadProducto = item.unidad;
                this.producto = Object.assign(new Producto, item);
              }
            }
            resolve();
          },
          error => {
            DEBUG && console.log(error);
            SessionUser.getInstance().setLoading(false);
            SessionUser.getInstance().setMessageFailed('al obtener los productos');
            document.getElementById('requestFailed').style.display = 'block';
          });
    });
  }


  getBar() {
    DEBUG && console.log(this.totalGrafica);
    DEBUG && console.log(this.totalImportaciones);
    DEBUG && console.log(this.totalProducto);
    DEBUG && console.log(this.whidtImp);
    DEBUG && console.log(this.widthTotalP);
    //this.totalImportaciones = 1000; Prueba barra azul.
    this.totalGrafica = this.totalImportaciones + this.totalProducto;

    let perc = this.totalGrafica / 100;

    this.whidtImp = this.totalImportaciones / perc;
    this.widthTotalP = this.totalProducto / perc;

    let perc2 = this.totalProducto / 100;

    this.barraProducto = this.totalPermisosProducto / perc2;
    //this.barraProducto = 94 ; Prueba barra verde.

  }


  agregarProductoPrevision() {
    SessionUser.getInstance().setLoading(true);
    this.producto.idPrevision = this.prevision.idPrevision;
    let array = new Array<Producto>();
    DEBUG && console.log(array);
    array.push(this.producto);
    this.previsionService.guardarProducto(array)
      .subscribe(
        data => {
          DEBUG && console.log(data);
          this.irPermisos();
          SessionUser.getInstance().setLoading(false);
        },
        error => {
          DEBUG && console.log(error);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al guardar');
          document.getElementById('requestFailed').style.display = 'block';
        });
  }

  getProductos(id: number) {
    return new Promise((resolve, reject) => {
      let parametros = new Parametros();
      parametros.id = id;
      this.previsionService.obtenerProductosPorPrevision(parametros)
        .subscribe(
          data => {
            DEBUG && console.log(data);
            this.lstProductos = Object.assign([], data);
            this.productoExist = false;
            if (this.lstProductos.length > 0) {
              for (let item of this.lstProductos) {
                if (item.idProducto == this.idProducto) {
                  this.productoExist = true;
                  break;
                }
              }
            }
            resolve();
          },
          error => {
            DEBUG && console.log('error al traer los productos ' + error);
            SessionUser.getInstance().setLoading(false);
            SessionUser.getInstance().setMessageFailed('al obtener los productos');
            document.getElementById('requestFailed').style.display = 'block';
          });
    });
  }


}
