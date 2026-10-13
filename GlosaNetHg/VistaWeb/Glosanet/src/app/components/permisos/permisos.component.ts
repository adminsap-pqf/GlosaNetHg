import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { PrevisionesService } from '../../services/previsiones/previsiones.service';
import { SessionUser } from '../../services/session/session.service';
import { Parametros } from '../../model/parametros.class';
import { Producto } from '../../model/producto.class';
import { PermisosService } from '../../services/permisos/permisos.service';
import { Previsiones } from '../../model/previsiones.class';
import { Prevision } from '../../model/prevision.class';
import { Session } from 'protractor';

/* Dev Tools */
import { DEBUG } from "../../settings/app.settings";

@Component({
  selector: 'gn-permisos',
  templateUrl: './permisos.component.html',
  styleUrls: ['./permisos.component.scss']
})
export class PermisosComponent implements OnInit {

  idPrevision:number;

  prevision: Prevision = new Prevision();

  modalForm: FormGroup;

  /* DropList */
  public itemsDropList: any[] = [{ nombre: 'Kg'}, { nombre: 'g'}, { nombre: 'mg'}, { nombre: 'L'}, { nombre: 'mL'}, { nombre: 'units'}, { nombre: 'Tn'}];
  defaultSelected: any;

  placeholder: any = 'Buscar';
  searchTerm: string = '';
  resetSearch: boolean;
  item = {};
  estado: boolean;
  array = [{
    codigo:       '0001',
    concepto:     '(010086620078 HATSUNE MIKU PROJECT DIVA X PSV) CARTUCHOIs',
    presentacion: '8',
    unidad:       'mg',
    total:        '1000',
    consumido:    '700',
    show: false
  },{
    codigo:       '0001',
    concepto:     '(STREET FIGHTER V COLLECTORS EDITION): KIT D/DISCO COMPACTO GRABADO P/VIDEO JUEGO QUE INCLUYE LIBRO D/ARTE, 2 TARJETAS C/CODIGO P/DESCARGAS, FIGURA DECORATIVA DEL VIDEO JUEGO. MCA.CAPCOM, MOD.56025B,2100835, P/N.013388560257, REF.56025B',
    presentacion: '8',
    unidad:       'mg',
    total:        '62000',
    consumido:    '60700',
    show: false
  },{
    codigo:       '0001',
    concepto:     'ACCERORIOS PARA CONSOLAS DE VIDEOJUEGOS,ACCESORIOS PARA CONSOLA DE VIDEOJUEGOS,ACCESORIOS PARA CONSOLAS DE VIDEOJUEGO,CONTROLES PARA CONSOLA DE VIDEOJUEGOS,CONTROLES PARA CONSOLAS DE VIDEOJUEGO037-010-NA-AS-BLD Partidas: 16,24,29,32,33,36,7,12,17,2',
    presentacion: '8',
    unidad:       'mg',
    total:        '62000',
    consumido:    '5700',
    show: false
  },{
    codigo:       '0001',
    concepto:     '50 ACCESORIOS(PORTAL P/ACTIVAR PERSONAJES VIRTUALES),P/CONSOLAS D/VIDEOJUEGO,INCLUYE:(3)FIGURAS,1POSTER,(3)CALCOMANIAS,(3)TARJETAS D/PERSONAJES',
    presentacion: '8',
    unidad:       'mg',
    total:        '62000',
    consumido:    '44700',
    show: false
  },];
  array2: any [] = [];

  search = [];

  classAnimation = 'w3-animate-zoom-out';

  lstItems: string [] = ['Activos', 'Historial'];

  date = new Date();
  yearActual: number = 0;
  nextYear: number = 0;

  constructor(private router: Router, private permiso: PermisosService, private previsionService: PrevisionesService ) {
    this.search = this.array;
  }

  ngOnInit() {
    this.yearActual = this.date.getFullYear();
    this.nextYear = this.yearActual + 1;

    window.addEventListener('resize', () => {
      this.estado = !this.estado;
    });

    this.modalForm = new FormGroup({
    });

    this.obtenerProductosLibreriaPrevision().then(
      (res) =>{
        if (res !== 0) {
          this.getPrevision(Number(res));
        } else {
          SessionUser.getInstance().setLoading(false);
        }
      });
  }


  searchTermChanged($event) {
    DEBUG && console.log($event);
    this.searchTerm = $event;
    this.array2 = this.search.filter((item) => {
      return item.nombre.toLowerCase().indexOf($event.toLowerCase()) !== -1 ||
      item.codigo.toLowerCase().indexOf($event.toLowerCase()) !== -1;
    });
  }

  /* Esta función no esta utilizando en ninguna sección */
  consultaProductosGeneralesPrevision() {
    SessionUser.getInstance().setLoading(true);
    return new Promise((resolve, reject) => {
      SessionUser.getInstance().getIdClient();
      // let nom = SessionUser.getInstance().getidEmpresaActual();
      let parametros = new Parametros();
      parametros.idEmpresa = SessionUser.getInstance().getidEmpresaActual();
      this.permiso.obtenerProductosPrevision(parametros).subscribe(
        data => {
          DEBUG && console.log(data);
          for (let item of data) {
            let productos = {
              nombre: item.nombre ? item.nombre : item.descripcion ? item.descripcion : null,
              cantidad: item.cantidad,
              unidad: item.unidad,
              vigencia: item.vigencia,
              total: item.total,
              codigo: item.codigo,
              idProducto: item.productos
            };
            this.array2.push(productos);
            this.search = this.array2;
          }
          DEBUG && console.log(this.array2);
          SessionUser.getInstance().setLoading(false);
          resolve(data[0].fkPrevision);
        }, error =>{
          DEBUG && console.log(error);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al obtener los productos');
          document.getElementById('requestFailed').style.display = 'block';
        });
      });

  }

  obtenerProductosLibreriaPrevision() {
    SessionUser.getInstance().setLoading(true);
    return new Promise(resolve => {
    let parametro = new Parametros();
    parametro.id = SessionUser.getInstance().getidEmpresaActual();
    this.previsionService.obtenerProductosLibreriaPrevision(parametro)
    .subscribe(
      data => {
        DEBUG && console.log(data);
        this.array2 = Object.assign([],data);
        this.search = Object.assign([],data);
        if(this.array2.length > 0){
          resolve(this.array2[0].idPrevision)
        } else {
          resolve(0);
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
      let parametro = new Parametros();
      parametro.id = id;
      this.previsionService.getPrevision(parametro)
        .subscribe(
          data => {
            DEBUG && console.log(data);
            this.idPrevision = data.idProxPrevision;
            this.prevision = Object.assign(new Prevision, data);
            DEBUG && console.log(this.prevision);
            SessionUser.getInstance().setLoading(false);
          },
          error => {
            DEBUG && console.log(error);
            SessionUser.getInstance().setLoading(false);
            SessionUser.getInstance().setMessageFailed('al obtener las previsiones');
            document.getElementById('requestFailed').style.display = 'block';
          });
  }


  truncateText($text) {
    const canvas = document.createElement('canvas');
    const cavas2D = canvas.getContext('2d');
    cavas2D.font = '16px Roboto';
    const size = cavas2D.measureText($text).width;
    let text = 'none';
    const realSize = document.getElementsByClassName('concepto')[0];
    if (size > (realSize['offsetWidth'] * 2) ) {
      text = 'block';
    }
    return text;
  }

  showText($item) {
    $item.show = !$item.show;
  }

  previsionExtemporanea(){
    this.classAnimation = 'w3-animate-zoom';
  }

  cerrarPrvision(){
    this.classAnimation = 'w3-animate-zoom';
  }

  irNuevaPrevision(){
    this.router.navigate(['protected/permisos/NuevaPrevision']);
  }

  historialImportaciones(id){
    this.router.navigate(["protected/permisos/historialImportacion", id]);
  }




  redirectToForecastNotice() {
    this.router.navigate(['protected/permisos/prevision']);
  }

  redirectToMonitoreo(){
    this.router.navigate(['protected/permisos/monitoreo', this.idPrevision]);
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
