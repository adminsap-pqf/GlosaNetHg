import { Component, OnInit } from '@angular/core';

import Chart from 'chart.js';
import { ReportePersonalizadoService } from '../../services/reporte-personalizado/reporte-personalizado.service';
import { Parametros } from '../../model/parametros.class';
import { Usuario } from '../../model/usuario.class';
import { Cliente } from '../../model/cliente.class';
import { SessionUser } from '../../services/session/session.service';

import * as accounting from 'accounting';
import { TableroHttpService } from '../../services/tablero/tableroHttp.service';
import { UserBarComponent } from '../core-container/user-bar/user-bar.component';

import * as html2canvas from 'html2canvas';
import { MesesService } from '../../services/general/meses';
import { Legend } from '../../model/legend.class';

/* Dev Tools */
import { DEBUG } from "../../settings/app.settings";

@Component({
  selector: 'gn-tablero',
  templateUrl: './tablero.component.html',
  styleUrls: ['./tablero.component.scss']
})
export class TableroComponent implements OnInit {

  defaultSelected: any;

  /* Parametros */
  parametros: any;

  /* DropList */
  public itemsDropList: any[] = [];
  public itemsDropList2: any[] = [];

  /* Datepicker */
  date: Date;
  date2: Date;
  dateInicio: Date;
  dateFin: Date;

  /* Secciones tablero */
  seccion: any = 0;

  /* Variables de cada grafica */

  /*  ********************* 501 ********************** */
  operacionesPeriodo: any;
  total_OperacionesPeriodo: any = 0;
  importaciones_OperacionesPeriodo: any = 0;
  exportaciones_OperacionesPeriodo: any = 0;
  rectificaciones_OperacionesPeriodo: any = 0;

  minimo_tipoCambioPeriodo: any = 0;
  promedio_tipoCambioPeriodo: any = 0;
  maximo_tipoCambioPeriodo: any = 0;

  imp: any = [];
  impAux: any = [];
  exp: any = [];
  expAux: any = [];
  clave: any = [];


  imp_TransporteArribo: any = [];
  impAux_TransporteArribo: any = [];
  exp_TransporteArribo: any = [];
  expAux_TransporteArribo: any = [];
  clave_TransporteArribo: any = [];


  imp_PesoBrutoTransporteArribo: any = [];
  impAux_PesoBrutoTransporteArribo: any = [];
  exp_PesoBrutoTransporteArribo: any = [];
  expAux_PesoBrutoTransporteArribo: any = [];
  clave_PesoBrutoTransporteArribo: any = [];


  expAux_TipoPedimento: any = [];
  impAux_TipoPedimento: any = [];
  imp_TipoPedimento: any = [];
  exp_TipoPedimento: any = [];
  clave_TipoPedimento: any = [];
  total_TipoPedimento: any = 0;



  expAux_ClavePedimento: any = [];
  impAux_ClavePedimento: any = [];
  imp_ClavePedimento: any = [];
  exp_ClavePedimento: any = [];
  clave_ClavePedimento: any = [];


  impAux_TransporteSalida: any = [];
  imp_TransporteSalida: any = [];
  clave_TransporteSalida: any = [];


  impAux_ArriboSalida: any = [];
  imp_ArriboSalida: any = [];
  clave_ArriboSalida: any = [];


  total_IncrementablesYDeducibles: any = 0;
  totalAux_IncrementablesYDeducibles: any = [];
  fletes: any = 0;
  seguros: any = 0;
  embalajes: any = 0;
  incrementables: any = 0;
  deducibles: any = 0;


  impAux_DestinoMercancia: any = [];
  imp_DestinoMercancia: any = [];
  clave_DestinoMercancia: any = [];

  impAux_PorPatente: any = [];
  imp_PorPatente: any = [];
  clave_PorPatente: any = [];

  impAux_PorTipoPedimento: any = [];
  imp_PorTipoPedimento: any = [];
  clave_PorTipoPedimento: any = [];

  /*  ********************* 502 ********************** */
  impAux_502: any[] = [];
  imp_502: any[] = [];
  clave_502: any[] = [];


  /*  ********************* 503 ********************** */

  guiasPorTipo: any[] = [];
  total_GuiasPorTipo: any = 0;
  m_GuiasPorTipo: any = 0;
  h_GuiasPorTipo: any = 0;

  impAux_503: any[] = [];
  imp_503: any[] = [];
  clave_503: any[] = [];

  /*  ********************* 504 ********************** */

  impAux_504: any = [];
  imp_504: any = [];
  clave_504: any = [];

  /*  ********************* 505 ********************** */

  impAux_505: any = [];
  imp_505: any = [];
  clave_505: any = [];


  /*  ********************* 506 ********************** */
  impAux_506: any = [];
  imp_506: any = [];
  clave_506: any = [];

  /*  ********************* 507 ********************** */

  impAux_507: any = [];
  imp_507: any = [];
  clave_507: any = [];

  expAux_507PedimentoPorClave: any = [];
  impAux_507PedimentoPorClave: any = [];
  imp_507PedimentoPorClave: any = [];
  exp_507PedimentoPorClave: any = [];
  clave_507PedimentoPorClave: any = [];

  /*  ********************* 509 ********************** */
  impAux_509: any = [];
  imp_509: any = [];
  clave_509: any = [];

  expAux_PorImporteTasa: any = [];
  impAux_PorImporteTasa: any = [];
  imp_PorImporteTasa: any = [];
  exp_PorImporteTasa: any = [];
  clave_PorImporteTasa: any = [];

  /*  ********************* 510 ********************** */
  TipoPedimento: any;
  total_510TipoPedimento: any = 0;
  e_510TipoPedimento: any = 0;
  i_510TipoPedimento: any = 0;

  impAux_510: any = [];
  imp_510: any = [];
  clave_510: any = [];

  /*  ********************* 701 ********************** */
  impAux_701: any = [];
  imp_701: any = [];
  clave_701: any = [];

  /*  ********************* 702 ********************** */
  impAux_702: any = [];
  imp_702: any = [];
  clave_702: any = [];

  /*  ********************* 554 ********************** */
  impAux_554: any = [];
  imp_554: any = [];
  clave_554: any = [];

  /*  ********************* 556 ********************** */
  impAux_556: any = [];
  imp_556: any = [];
  clave_556: any = [];

  igieAux_556: any = [];
  ivaAux_556: any = [];
  iva_556: any = [];
  igie_556: any = [];
  claveStacked_556: any = [];

  /*  ********************* 557 ********************** */
  impAux_557: any = [];
  imp_557: any = [];
  clave_557: any = [];

  resolutionPdf: boolean = false;

  data1:any;
  data2:any;
  data3:any;
  data4:any;
  data5:any;
  data6:any;
  data7:any;
  data8:any;
  data9:any;
  data10:any;

  data1_502:any;
  data2_502:any;
  data3_502:any;

  data1_503:any;
  data2_503:any;

  data1_504:any;
  data2_504:any;
  data3_504:any;

  data1_505:any;
  data2_505:any;

  data1_506:any;
  data2_506:any;

  data1_507:any;
  data2_507:any;

  data1_509:any;
  data2_509:any;
  data3_509:any;
  data4_509:any;

  data1_510:any;
  data2_510:any;
  data3_510:any;
  data4_510:any;

  data1_701:any;
  data2_701:any;
  data3_701:any;

  data1_702:any;
  data2_702:any;
  data3_702:any;

  data1_554:any;
  data2_554:any;
  data3_554:any;

  data1_556:any;
  data2_556:any;
  data3_556:any;
  data4_556:any;
  data5_556:any;

  data1_557:any;
  data2_557:any;
  data3_557:any;
  data4_557:any;

  invalidDates: boolean = false;
  calculateActive: boolean = false;

  constructor(
    private reportePersonalizado: ReportePersonalizadoService,
    private tableroHttp: TableroHttpService,
    private userBarComponent: UserBarComponent,
    private month: MesesService
  ) {

  }

  ngOnInit() {
    const currentYear = new Date();
    this.date = new Date(currentYear.getFullYear(), 0, 1);
    this.date2 = new Date(currentYear.getFullYear(), 11, 31);
    this.getTablas();
    this.apply();
  }

  ngOnchanges() {

  }

  ngDoCheck() {

  }

  ngAfterViewInit(){

  }

  pdfDownload() {
    DEBUG && console.log('@pdfDownload: Entre');
    this.changeSize1385().then( () => {
      this.getDataPdf().then(
        res => {
          DEBUG && console.log(res);
          this.generarReporte(res);
          this.changeSizeNormal();
        });
    });
  }

  changeSize1385() {
    DEBUG && console.log('@changeSize1385: Entre');
    return new Promise((resolve, reject)=>{
      SessionUser.getInstance().setLoading(true);
      this.resolutionPdf = true;
      DEBUG && console.log('Cambio de tamaño');
      setTimeout(()=>{resolve();}, 1000);
    })
  }

  changeSizeNormal() {
    DEBUG && console.log('@changeSizeNormal: Entre');
    this.resolutionPdf = false;
  }

  getDataPdf() {
    DEBUG && console.log('@getDataPdf: Entre');
    return new Promise((resolve, reject) => {
      let data;
      switch (this.seccion) {
        case 0:
          data = {
            dashboard: 'D501_2',
            titulo:'Datos Generales',
            img1_501:this.getImgTablero('img1_501').then(d =>{ data.img1_501 = d }),
            img2_501:this.getImgTablero('img2_501').then(d =>{ data.img2_501 = d }),
            img3_501:this.getImgTablero('img3_501').then(d =>{ data.img3_501 = d }),
            img4_501:this.getImgTablero('img4_501').then(d =>{ data.img4_501 = d }),
            img5_501: this.getImgTablero('img5_501').then(d =>{ data.img5_501 = d }),
            img6_501: this.getImgTablero('img6_501').then(d =>{ data.img6_501 = d }),
            img7_501: this.getImgTablero('img7_501').then(d =>{ data.img7_501 = d }),
            img8_501: this.getImgTablero('img8_501').then(d =>{ data.img8_501 = d }),
            img9_501: this.getImgTablero('img9_501').then(d =>{ data.img9_501 = d }),
            img10_501: this.getImgTablero('img10_501').then(d =>{ data.img10_501 = d }),
            img11_501: this.getImgTablero('img11_501').then(d =>{ data.img11_501 = d }),
            img12_501: this.getImgTablero('img12_501').then(d =>{ data.img12_501 = d }),
            img13_501: this.getImgTablero('img13_501').then(d =>{ data.img13_501 = d; resolve(data); }),


            logo:SessionUser.getInstance().getCliente().empresas[0].imagenEmpresa,
            usuario:SessionUser.getInstance().getUserID(),
            nombreCompleto:SessionUser.getInstance().getNombreCompleto(),
            email:SessionUser.getInstance().getUsuario().email,
            fechaReporte: 'Del '+ this.date.getDate() + ' de '+ this.month.getMesString(this.date.getMonth()) + ' de '+ this.date.getFullYear() + ' al '+ this.date2.getDate() + ' de '+ this.month.getMesString(this.date2.getMonth()) + ' de '+ this.date2.getFullYear()
          };
          break;
        case 1:
          data = {
            dashboard: 'D502_2',
            titulo:'Transporte Mercancias',
            img1_502:this.getImgTablero('img1_502').then(d =>{ data.img1_502 = d }),
            img2_502:this.getImgTablero('img2_502').then(d =>{ data.img2_502 = d }),
            img3_502:this.getImgTablero('img3_502').then(d =>{ data.img3_502 = d; resolve(data); }),
            logo:SessionUser.getInstance().getCliente().empresas[0].imagenEmpresa,
            usuario:SessionUser.getInstance().getUserID(),
            nombreCompleto:SessionUser.getInstance().getNombreCompleto(),
            email:SessionUser.getInstance().getUsuario().email,
            fechaReporte: 'Del '+ this.date.getDate() + ' de '+ this.month.getMesString(this.date.getMonth()) + ' de '+ this.date.getFullYear() + ' al '+ this.date2.getDate() + ' de '+ this.month.getMesString(this.date2.getMonth()) + ' de '+ this.date2.getFullYear()
          };
          break;
        case 2:
          data = {
            dashboard: 'D503_2',
            titulo:'Guías',
            img1_503:this.getImgTablero('img1_503').then(d =>{ data.img1_503 = d }),
            img2_503:this.getImgTablero('img2_503').then(d =>{ data.img2_503 = d }),
            img3_503:this.getImgTablero('img3_503').then(d =>{ data.img3_503 = d; resolve(data); }),
            logo:SessionUser.getInstance().getCliente().empresas[0].imagenEmpresa,
            usuario:SessionUser.getInstance().getUserID(),
            nombreCompleto:SessionUser.getInstance().getNombreCompleto(),
            email:SessionUser.getInstance().getUsuario().email,
            fechaReporte: 'Del '+ this.date.getDate() + ' de '+ this.month.getMesString(this.date.getMonth()) + ' de '+ this.date.getFullYear() + ' al '+ this.date2.getDate() + ' de '+ this.month.getMesString(this.date2.getMonth()) + ' de '+ this.date2.getFullYear()
          };
          break;
        case 3:
          data = {
            dashboard: 'D504_2',
            titulo:'Contenedores',
            img1_504:this.getImgTablero('img1_504').then(d =>{ data.img1_504 = d }),
            img2_504:this.getImgTablero('img2_504').then(d =>{ data.img2_504 = d }),
            img3_504:this.getImgTablero('img3_504').then(d =>{ data.img3_504 = d; resolve(data); }),
            logo:SessionUser.getInstance().getCliente().empresas[0].imagenEmpresa,
            usuario:SessionUser.getInstance().getUserID(),
            nombreCompleto:SessionUser.getInstance().getNombreCompleto(),
            email:SessionUser.getInstance().getUsuario().email,
            fechaReporte: 'Del '+ this.date.getDate() + ' de '+ this.month.getMesString(this.date.getMonth()) + ' de '+ this.date.getFullYear() + ' al '+ this.date2.getDate() + ' de '+ this.month.getMesString(this.date2.getMonth()) + ' de '+ this.date2.getFullYear()
          };
          break;
        case 4:
          data = {
            dashboard: 'D505_2',
            titulo: 'Facturas',
            img1_505:this.getImgTablero('img1_505').then(d =>{ data.img1_505 = d }),
            img2_505:this.getImgTablero('img2_505').then(d =>{ data.img2_505 = d; resolve(data); }),
            logo:SessionUser.getInstance().getCliente().empresas[0].imagenEmpresa,
            usuario:SessionUser.getInstance().getUserID(),
            nombreCompleto:SessionUser.getInstance().getNombreCompleto(),
            email:SessionUser.getInstance().getUsuario().email,
            fechaReporte: 'Del '+ this.date.getDate() + ' de '+ this.month.getMesString(this.date.getMonth()) + ' de '+ this.date.getFullYear() + ' al '+ this.date2.getDate() + ' de '+ this.month.getMesString(this.date2.getMonth()) + ' de '+ this.date2.getFullYear()
          };

          break;

        case 5:
          data = {
            dashboard: 'D506_2',
            titulo: 'Fechas Pedimento',
            img1_506:this.getImgTablero('img1_506').then(d =>{ data.img1_506 = d }),
            img2_506:this.getImgTablero('img2_506').then(d =>{ data.img2_506 = d; resolve(data); }),
            logo:SessionUser.getInstance().getCliente().empresas[0].imagenEmpresa,
            usuario:SessionUser.getInstance().getUserID(),
            nombreCompleto:SessionUser.getInstance().getNombreCompleto(),
            email:SessionUser.getInstance().getUsuario().email,
            fechaReporte: 'Del '+ this.date.getDate() + ' de '+ this.month.getMesString(this.date.getMonth()) + ' de '+ this.date.getFullYear() + ' al '+ this.date2.getDate() + ' de '+ this.month.getMesString(this.date2.getMonth()) + ' de '+ this.date2.getFullYear()

          };
          break;
        case 6:
          data = {
            dashboard: 'D507_2',
            titulo: 'Casos Pedimento',
            img1_507:this.getImgTablero('img1_507').then(d =>{ data.img1_507 = d }),
            img2_507:this.getImgTablero('img2_507').then(d =>{ data.img2_507 = d; resolve(data); }),
            logo:SessionUser.getInstance().getCliente().empresas[0].imagenEmpresa,
            usuario:SessionUser.getInstance().getUserID(),
            nombreCompleto:SessionUser.getInstance().getNombreCompleto(),
            email:SessionUser.getInstance().getUsuario().email,
            fechaReporte: 'Del '+ this.date.getDate() + ' de '+ this.month.getMesString(this.date.getMonth()) + ' de '+ this.date.getFullYear() + ' al '+ this.date2.getDate() + ' de '+ this.month.getMesString(this.date2.getMonth()) + ' de '+ this.date2.getFullYear()
          };
          break;
        case 7:
          data = {
            dashboard: 'D509_2',
            titulo: 'Tasas Pedimento',
            img1_509:this.getImgTablero('img1_509').then(d =>{ data.img1_509 = d }),
            img2_509:this.getImgTablero('img2_509').then(d =>{ data.img2_509 = d }),
            img3_509:this.getImgTablero('img3_509').then(d =>{ data.img3_509 = d }),
            img4_509:this.getImgTablero('img4_509').then(d =>{ data.img4_509 = d; resolve(data); }),
            logo:SessionUser.getInstance().getCliente().empresas[0].imagenEmpresa,
            usuario:SessionUser.getInstance().getUserID(),
            nombreCompleto:SessionUser.getInstance().getNombreCompleto(),
            email:SessionUser.getInstance().getUsuario().email,
            fechaReporte: 'Del '+ this.date.getDate() + ' de '+ this.month.getMesString(this.date.getMonth()) + ' de '+ this.date.getFullYear() + ' al '+ this.date2.getDate() + ' de '+ this.month.getMesString(this.date2.getMonth()) + ' de '+ this.date2.getFullYear()
          };
          break;
        case 8:
          data = {
            dashboard: 'D510_2',
            titulo:'Contribuciones Pedimento',
            img1_510:this.getImgTablero('img1_510').then(d =>{ data.img1_510 = d }),
            img2_510:this.getImgTablero('img2_510').then(d =>{ data.img2_510 = d }),
            img3_510:this.getImgTablero('img3_510').then(d =>{ data.img3_510 = d }),
            img4_510:this.getImgTablero('img4_510').then(d =>{ data.img4_510 = d }),
            img5_510:this.getImgTablero('img5_510').then(d =>{ data.img5_510 = d; resolve(data); }),
            logo:SessionUser.getInstance().getCliente().empresas[0].imagenEmpresa,
            usuario:SessionUser.getInstance().getUserID(),
            nombreCompleto:SessionUser.getInstance().getNombreCompleto(),
            email:SessionUser.getInstance().getUsuario().email,
            fechaReporte: 'Del '+ this.date.getDate() + ' de '+ this.month.getMesString(this.date.getMonth()) + ' de '+ this.date.getFullYear() + ' al '+ this.date2.getDate() + ' de '+ this.month.getMesString(this.date2.getMonth()) + ' de '+ this.date2.getFullYear()
          };
          break;
        case 9:
          data = {
            dashboard: 'D701_2',
            titulo:'Rectificaciones',
            img1_701:this.getImgTablero('img1_701').then(d =>{ data.img1_701 = d }),
            img2_701:this.getImgTablero('img2_701').then(d =>{ data.img2_701 = d }),
            img3_701:this.getImgTablero('img3_701').then(d =>{ data.img3_701 = d; resolve(data); }),
            logo:SessionUser.getInstance().getCliente().empresas[0].imagenEmpresa,
            usuario:SessionUser.getInstance().getUserID(),
            nombreCompleto:SessionUser.getInstance().getNombreCompleto(),
            email:SessionUser.getInstance().getUsuario().email,
            fechaReporte: 'Del '+ this.date.getDate() + ' de '+ this.month.getMesString(this.date.getMonth()) + ' de '+ this.date.getFullYear() + ' al '+ this.date2.getDate() + ' de '+ this.month.getMesString(this.date2.getMonth()) + ' de '+ this.date2.getFullYear()

          };

          break;

        case 10:
          data = {
            dashboard: 'D702_2',
            titulo:'Diferencias Contribuciones Pedimento',
            img1_702:this.getImgTablero('img1_702').then(d =>{ data.img1_702 = d }),
            img2_702:this.getImgTablero('img2_702').then(d =>{ data.img2_702 = d }),
            img3_702:this.getImgTablero('img3_702').then(d =>{ data.img3_702 = d; resolve(data); }),
            logo:SessionUser.getInstance().getCliente().empresas[0].imagenEmpresa,
            usuario:SessionUser.getInstance().getUserID(),
            nombreCompleto:SessionUser.getInstance().getNombreCompleto(),
            email:SessionUser.getInstance().getUsuario().email,
            fechaReporte: 'Del '+ this.date.getDate() + ' de '+ this.month.getMesString(this.date.getMonth()) + ' de '+ this.date.getFullYear() + ' al '+ this.date2.getDate() + ' de '+ this.month.getMesString(this.date2.getMonth()) + ' de '+ this.date2.getFullYear()

          };

          break;
        case 11:
          data = {
            dashboard: 'D554_2',
            titulo:'Casos Partida',
            img1_554:this.getImgTablero('img1_554').then(d =>{ data.img1_554 = d }),
            img2_554:this.getImgTablero('img2_554').then(d =>{ data.img2_554 = d }),
            img3_554:this.getImgTablero('img3_554').then(d =>{ data.img3_554 = d; resolve(data); }),
            logo:SessionUser.getInstance().getCliente().empresas[0].imagenEmpresa,
            usuario:SessionUser.getInstance().getUserID(),
            nombreCompleto:SessionUser.getInstance().getNombreCompleto(),
            email:SessionUser.getInstance().getUsuario().email,
            fechaReporte: 'Del '+ this.date.getDate() + ' de '+ this.month.getMesString(this.date.getMonth()) + ' de '+ this.date.getFullYear() + ' al '+ this.date2.getDate() + ' de '+ this.month.getMesString(this.date2.getMonth()) + ' de '+ this.date2.getFullYear()

          };
          break;
        case 12:
          data = {
            dashboard: 'D556_2',
            titulo:'Tasas Contribuciones Partida',
            img1_556:this.getImgTablero('img1_556').then(d =>{ data.img1_556 = d }),
            img2_556:this.getImgTablero('img2_556').then(d =>{ data.img2_556 = d }),
            img3_556:this.getImgTablero('img3_556').then(d =>{ data.img3_556 = d }),
            img4_556:this.getImgTablero('img4_556').then(d =>{ data.img4_556 = d }),
            img5_556:this.getImgTablero('img5_556').then(d =>{ data.img5_556 = d; resolve(data); }),
            logo:SessionUser.getInstance().getCliente().empresas[0].imagenEmpresa,
            usuario:SessionUser.getInstance().getUserID(),
            nombreCompleto:SessionUser.getInstance().getNombreCompleto(),
            email:SessionUser.getInstance().getUsuario().email,
            fechaReporte: 'Del '+ this.date.getDate() + ' de '+ this.month.getMesString(this.date.getMonth()) + ' de '+ this.date.getFullYear() + ' al '+ this.date2.getDate() + ' de '+ this.month.getMesString(this.date2.getMonth()) + ' de '+ this.date2.getFullYear()

          };

          break;

        case 13:
          data = {
            dashboard: 'D557_2',
            titulo:'Contribuciones Partida',
            img1_557:this.getImgTablero('img1_557').then(d =>{ data.img1_557 = d }),
            img2_557:this.getImgTablero('img2_557').then(d =>{ data.img2_557 = d }),
            img3_557:this.getImgTablero('img3_557').then(d =>{ data.img3_557 = d }),
            img4_557:this.getImgTablero('img4_557').then(d =>{ data.img4_557 = d; resolve(data); }),
            logo:SessionUser.getInstance().getCliente().empresas[0].imagenEmpresa,
            usuario:SessionUser.getInstance().getUserID(),
            nombreCompleto:SessionUser.getInstance().getNombreCompleto(),
            email:SessionUser.getInstance().getUsuario().email,
            fechaReporte: 'Del '+ this.date.getDate() + ' de '+ this.month.getMesString(this.date.getMonth()) + ' de '+ this.date.getFullYear() + ' al '+ this.date2.getDate() + ' de '+ this.month.getMesString(this.date2.getMonth()) + ' de '+ this.date2.getFullYear()

          };

          break;

        default:
          break;
      }

    });

  }

  getImgTablero(element) {
    DEBUG && console.log('@getImgTablero: Entre');
    return new Promise((resolve, reject) => {
      /*let options = {
        async: false
      }*/
      (html2canvas as any)(document.getElementById(element)).then(canvas => {
        let imgData = canvas.toDataURL("image/png");

        /* let map = new Map();
        map.set('dashboard', imgData); */
        let array = imgData.split(",");
        imgData = array[1];
        DEBUG && console.log(imgData);

        resolve(imgData);

      });
    });
  }

  generarReporte(data) {
    DEBUG && console.log('@generarReporte: Entre');
    this.tableroHttp.generarReporte(data)
      .subscribe(
        data => {
          DEBUG && console.log(data);
          if (data) {
            DEBUG && console.log("@generarReporte: contenido recibido correctamente");
            let element = document.createElement('a');
            element.setAttribute('href', "data:application/pdf;base64," + data);
            element.setAttribute('download', 'Reporte');
            element.setAttribute('target', '_blank');
            element.style.display = 'none';
            document.body.appendChild(element);
            element.click();
          } else {
            SessionUser.getInstance().setMessageFailed('al descargar reporte');
            document.getElementById('requestFailed').style.display = 'block';
          }
        },
        error => {
          DEBUG && console.log("@generarReporte: Error -> " + error);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al descargar reporte');
          document.getElementById('requestFailed').style.display = 'block';
        },
        () => {
          DEBUG && console.log("@generarReporte: finalizado");
          SessionUser.getInstance().setLoading(false);
        });
  }

  /* ------------------------------------------------------------Servicios-------------------------------------------------------------- */

  t501_obtenerDatos() {
    DEBUG && console.log('@t501_obtenerDatos: Entre');
    SessionUser.getInstance().setLoading(true);
    this.tableroHttp.t501_obtenerDatos(this.parametros)
      .subscribe(
        data => {
          DEBUG && console.log("-------------------------------1 solo resource--------------------------");
          DEBUG && console.log(data);
          SessionUser.getInstance().setLoading(false);
          this.getOperacionesPeriodo(data[0].list);
          this.getTipoCambioPeriodo(data[1].list);
          this.getOperacionesPorAduana(data[2].list);
          this.t501_obtenerOperacionesPorMedioTransporteArribo(data[3].list);
          this.t501_obtenerPesoBrutoPorMedioTransporteArribo(data[4].list);
          this.t501_obtenerOperacionesPorTipoPedimento(data[5].list);
          this.t501_obtenerOperacionesPorClavePedimento(data[6].list);

          this.t501_ObtenerOperacionesMedioTransporteSalida(data[7].list);
          this.t501_ObtenerOperacionesMedioTransporteArriboSalida(data[8].list);
          this.t501_ObtenerOperacionesPorIncrementablesYDeducibles(data[9].list);
          this.t501_ObtenerOperacionesDestinoMercancia(data[10].list);
          this.t501_ObtenerOperacionesPorPatente(data[11].list);
          this.t501_ObtenerOperacionesPorTipoPedimento(data[12].list);

        },
        error => {
          DEBUG && console.log('@t501_obtenerDatos: Error - >', error);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al obtener los datos 501');
          document.getElementById('requestFailed').style.display = 'block';
        }
      );
  }

  t502_obtenerDatos() {
    DEBUG && console.log('@t502_obtenerDatos: Entre');
    SessionUser.getInstance().setLoading(true);
    this.tableroHttp.t502_obtenerDatos(this.parametros)
      .subscribe(
        data => {
          DEBUG && console.log("-------------------------------2 solo resource--------------------------");
          DEBUG && console.log(data);
          SessionUser.getInstance().setLoading(false);
          this.t502_obtenerOperacionesPorAduana(data[0].list);
          this.t502_obtenerOperacionesPorAgenteAduanal(data[1].list);
          this.t502_obtenerOperacionesPorPais(data[2].list);
        },
        error => {
          DEBUG && console.log('@t502_obtenerDatos: Error -> ', error);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al obtener los datos 502');
          document.getElementById('requestFailed').style.display = 'block';
        }
      );
  }

  t503_obtenerDatos() {
    DEBUG && console.log('@t503_obtenerDatos: Entre');
    SessionUser.getInstance().setLoading(true);
    this.tableroHttp.t503_obtenerDatos(this.parametros)
      .subscribe(
        data => {
          DEBUG && console.log("-------------------------------3 solo resource--------------------------");
          DEBUG && console.log(data);
          SessionUser.getInstance().setLoading(false);
          this.t503_ObtenerGuiasPorTipo(data[0].list);
          this.t503_ObtenerGuiasPorAduana(data[1].list);
          this.t503_ObtenerGuiasPorAgente(data[2].list);
        },
        error => {
          DEBUG && console.log('@t503_obtenerDatos: Error -> ', error);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al obtener los datos 503');
          document.getElementById('requestFailed').style.display = 'block';
        }
      );
  }

  t504_obtenerDatos() {
    DEBUG && console.log('@t504_obtenerDatos: Entre');
    SessionUser.getInstance().setLoading(true);
    this.tableroHttp.t504_obtenerDatos(this.parametros)
      .subscribe(
        data => {
          DEBUG && console.log("-------------------------------4 solo resource--------------------------");
          DEBUG && console.log(data);
          SessionUser.getInstance().setLoading(false);
          this.t504_ObtenerContenedorPorAduana(data[0].list);
          this.t504_ObtenerContenedorPorTipo(data[1].list);
          this.t504_ObtenerContenedorPorAgente(data[2].list);
        },
        error => {
          DEBUG && console.log('@t504_obtenerDatos: Error -> ', error);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al obtener los datos 504');
          document.getElementById('requestFailed').style.display = 'block';
        }
      );
  }

  t505_obtenerDatos() {
    DEBUG && console.log('@t505_obtenerDatos: Entre');
    SessionUser.getInstance().setLoading(true);
    this.tableroHttp.t505_obtenerDatos(this.parametros)
      .subscribe(
        data => {
          DEBUG && console.log("-------------------------------5 solo resource--------------------------");
          DEBUG && console.log(data);
          SessionUser.getInstance().setLoading(false);
          this.t505_ObtenerFacturaPorPais(data[0].list);
          this.t505_ObtenerFacturaPorMoneda(data[1].list);
        },
        error => {
          DEBUG && console.log('@t505_obtenerDatos: Error -> ', error);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al obtener los datos 505');
          document.getElementById('requestFailed').style.display = 'block';
        }
      );
  }

  t506_obtenerDatos() {
    DEBUG && console.log('@t506_obtenerDatos: Entre');
    SessionUser.getInstance().setLoading(true);
    this.tableroHttp.t506_obtenerDatos(this.parametros)
      .subscribe(
        data => {
          DEBUG && console.log("-------------------------------6 solo resource--------------------------");
          DEBUG && console.log(data);
          SessionUser.getInstance().setLoading(false);
          this.t506_ObtenerFechaPorAduana(data[0].list);
          this.t506_ObtenerFechaPorAgente(data[1].list);
        },
        error => {
          DEBUG && console.log('@t506_obtenerDatos: Error -> ', error);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al obtener los datos 506');
          document.getElementById('requestFailed').style.display = 'block';
        }
      );
  }

  t507_obtenerDatos() {
    DEBUG && console.log('@t507_obtenerDatos: Entre');
    SessionUser.getInstance().setLoading(true);
    this.tableroHttp.t507_obtenerDatos(this.parametros)
      .subscribe(
        data => {
          DEBUG && console.log("-------------------------------7 solo resource--------------------------");
          DEBUG && console.log(data);
          SessionUser.getInstance().setLoading(false);
          this.t507_ObtenerPedimentoPorAduana(data[0].list);
          this.t507_ObtenerPedimentoPorClave(data[1].list);
        },
        error => {
          DEBUG && console.log('@t507_obtenerDatos: Error -> ', error);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al obtener los datos 507');
          document.getElementById('requestFailed').style.display = 'block';
        }
      );
  }

  t509_obtenerDatos() {
    DEBUG && console.log('@t509_obtenerDatos: Entre');
    SessionUser.getInstance().setLoading(true);
    this.tableroHttp.t509_obtenerDatos(this.parametros)
      .subscribe(
        data => {
          DEBUG && console.log("-------------------------------509 solo resource--------------------------");
          DEBUG && console.log(data);
          SessionUser.getInstance().setLoading(false);
          this.t509_ObtenerPedimentoPorTipoTasa(data[0].list);
          this.t509_ObtenerPedimentoPorAduana(data[1].list);
          this.t509_ObtenerPedimentoPorImporteTasa(data[2].list);
          this.t509_ObtenerPedimentoPorAgente(data[3].list);
        },
        error => {
          DEBUG && console.log('@t509_obtenerDatos: Error -> ', error);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al obtener los datos 509');
          document.getElementById('requestFailed').style.display = 'block';
        }
      );
  }

  t510_obtenerDatos() {
    DEBUG && console.log('@t510_obtenerDatos: Entre');
    SessionUser.getInstance().setLoading(true);
    this.tableroHttp.t510_obtenerDatos(this.parametros)
      .subscribe(
        data => {
          DEBUG && console.log("-------------------------------510 solo resource--------------------------");
          DEBUG && console.log(data);
          SessionUser.getInstance().setLoading(false);
          this.t510_ObtenerOperacionesPorTipoPedimento(data[0].list);
          this.t510_ObtenerOperacionesPorAduana(data[1].list);
          this.t510_ObtenerOperacionesPorContribucion(data[2].list);
          this.t510_ObtenerOperacionesPorClave(data[3].list);
          this.t510_ObtenerOperacionesPorAgente(data[4].list);
        },
        error => {
          DEBUG && console.log('@t510_obtenerDatos: Error -> ', error);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al obtener los datos 510');
          document.getElementById('requestFailed').style.display = 'block';
        }
      );
  }

  t701_obtenerDatos() {
    DEBUG && console.log('@t701_obtenerDatos: Entre');
    SessionUser.getInstance().setLoading(true);
    this.tableroHttp.t701_obtenerDatos(this.parametros)
      .subscribe(
        data => {
          DEBUG && console.log("-------------------------------701 solo resource--------------------------");
          DEBUG && console.log(data);
          SessionUser.getInstance().setLoading(false);
          this.t701_ObtenerOperacionesPorAduana(data[0].list);
          this.t701_ObtenerOperacionesPorPatente(data[1].list);
          this.t701_ObtenerOperacionesPorClaveDocumento(data[2].list);
        },
        error => {
          DEBUG && console.log('@t701_obtenerDatos: Error -> ', error);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al obtener los datos 701');
          document.getElementById('requestFailed').style.display = 'block';
        }
      );
  }

  t702_obtenerDatos() {
    DEBUG && console.log('@t702_obtenerDatos: Entre');
    SessionUser.getInstance().setLoading(true);
    this.tableroHttp.t702_obtenerDatos(this.parametros)
      .subscribe(
        data => {
          DEBUG && console.log("-------------------------------702 solo resource--------------------------");
          DEBUG && console.log(data);
          SessionUser.getInstance().setLoading(false);
          this.t702_ObtenerOperacionesPorAduana(data[0].list);
          this.t702_ObtenerOperacionesPorPatente(data[1].list);
          this.t702_ObtenerTotalPorFormaPago(data[2].list);
        },
        error => {
          DEBUG && console.log('@t702_obtenerDatos: Error -> ', error);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al obtener los datos 702');
          document.getElementById('requestFailed').style.display = 'block';
        }
      );
  }

  t554_obtenerDatos() {
    DEBUG && console.log('@t554_obtenerDatos: Entre');
    SessionUser.getInstance().setLoading(true);
    this.tableroHttp.t554_obtenerDatos(this.parametros)
      .subscribe(
        data => {
          DEBUG && console.log("-------------------------------554 solo resource--------------------------");
          DEBUG && console.log(data);
          SessionUser.getInstance().setLoading(false);
          this.t554_ObtenerOperacionesPorAduana(data[0].list);
          this.t554_ObtenerOperacionesPorAgente(data[1].list);
          this.t554_ObtenerOperacionesPorClave(data[2].list);
        },
        error => {
          DEBUG && console.log('@t554_obtenerDatos: Error -> ', error);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al obtener los datos 554');
          document.getElementById('requestFailed').style.display = 'block';
        }
      );
  }

  t556_obtenerDatos() {
    DEBUG && console.log('@t556_obtenerDatos: Entre');
    SessionUser.getInstance().setLoading(true);
    this.tableroHttp.t556_obtenerDatos(this.parametros)
      .subscribe(
        data => {
          DEBUG && console.log("-------------------------------556 solo resource--------------------------");
          DEBUG && console.log(data);
          SessionUser.getInstance().setLoading(false);
          this.t556_ObtenerOperacionesPorAduana(data[0].list);
          this.t556_ObtenerOperacionesPorAgente(data[1].list);
          this.t556_ObtenerOperacionesPorClave(data[2].list);
          this.t556_ObtenerOperacionesTasaContribucion(data[3].list);
          this.t556_ObtenerOperacionesTipoTasa(data[4].list);
        },
        error => {
          DEBUG && console.log('@t556_obtenerDatos: Error -> ', error);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al obtener los datos 556');
          document.getElementById('requestFailed').style.display = 'block';
        }
      );
  }

  t557_obtenerDatos() {
    DEBUG && console.log('@t557_obtenerDatos: Entre');
    SessionUser.getInstance().setLoading(true);
    this.tableroHttp.t557_obtenerDatos(this.parametros)
      .subscribe(
        data => {

          DEBUG && console.log("-------------------------------557 solo resource--------------------------");
          DEBUG && console.log(data);
          SessionUser.getInstance().setLoading(false);
          this.t557_obtenerOperacionesPorAduana(data[0].list);
          this.t557_obtenerOperacionesPorAgenteAduanal(data[1].list);
          this.t557_obtenerImportePagoPorTipoContribucion(data[2].list);
          this.t557_obtenerOperacionesPorFormaDePago(data[3].list);
        },
        error => {
          DEBUG && console.log('@t557_obtenerDatos: Error -> ', error);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al obtener los datos 557');
          document.getElementById('requestFailed').style.display = 'block';
        }
      );
  }

  getTablas() {
    DEBUG && console.log('@getTablas: Entre');
    let parametros: Parametros = new Parametros();
    parametros.idioma = "Espanol";
    parametros.condicion = "tablero";
    this.reportePersonalizado.obtenerTablas(parametros)
      .subscribe(
        data => {
          data.forEach((item, index) => {
            if (item.numero != 0) {
              this.itemsDropList.push({ nombre: item.numero + " - " + item.descripcion, id: index });
            } else {
              this.itemsDropList.push({ nombre: item.descripcion, id: index });
            }
          });
          this.itemsDropList2 = [];
          Array.prototype.push.apply(this.itemsDropList2, this.itemsDropList);
          this.defaultSelected = this.itemsDropList2[0];
          DEBUG && console.log(this.itemsDropList2[0]);
        }, error => {
          DEBUG && console.log('@getTablas: Error -> ', error);
          SessionUser.getInstance().setMessageFailed('al descargar el archivo');
          document.getElementById('requestFailed').style.display = 'block';
        });
  }

  getOperacionesPeriodo(data: any) {
    DEBUG && console.log('@getOperacionesPeriodo: Entre');
    DEBUG && console.log(data);
    this.operacionesPeriodo = data;
    this.total_OperacionesPeriodo = this.operacionesPeriodo[0].totalI + this.operacionesPeriodo[1].totalI + this.operacionesPeriodo[2].totalI;
    this.importaciones_OperacionesPeriodo = this.operacionesPeriodo[2].totalI;
    this.exportaciones_OperacionesPeriodo = this.operacionesPeriodo[1].totalI;
    this.rectificaciones_OperacionesPeriodo = this.operacionesPeriodo[0].totalI;
    let etiquetas;
    let valores;
    let colores = ["#DD4444", "#FEC42D", "#5792F3"];
    if (data.length !== 0) {
      etiquetas = [data[0].tipo, data[1].tipo, data[2].tipo];
      valores = [data[0].totalI, data[1].totalI, data[2].totalI];
    } else {
      etiquetas = [];
      valores = [0, 0, 0];
    }
    try {
      let canvasDiv = document.querySelector('#doughnut1Div');
      canvasDiv.removeChild(document.querySelector('#doughnut1'));
      document.querySelector('#doughnut1Div').insertAdjacentHTML('afterbegin', '<canvas id="doughnut1"></canvas>');
    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    let tooltipData = true;
    this.createDoughnut('doughnut1', etiquetas, valores, colores, tooltipData);
  }

  getTipoCambioPeriodo(data: any) {
    DEBUG && console.log('@getTipoCambioPeriodo: Entre');
    DEBUG && console.log(data);
    this.minimo_tipoCambioPeriodo = data[0].totalD;
    this.promedio_tipoCambioPeriodo = data[1].totalD;
    this.maximo_tipoCambioPeriodo = data[2].totalD;
    let etiquetaLine = ['Mínimo', 'Promedio', 'Máximo'];
    let valoresLine = [this.minimo_tipoCambioPeriodo, this.promedio_tipoCambioPeriodo, this.maximo_tipoCambioPeriodo];

    try {
      let canvasDiv = document.querySelector('#lineChart1Div');
      canvasDiv.removeChild(document.querySelector('#lineChart1'));
      document.querySelector('#lineChart1Div').insertAdjacentHTML('afterbegin', '<canvas id="lineChart1"  width="100%"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }
    this.createLineBar('lineChart1', etiquetaLine, valoresLine);
    /* },
    error => {

    }); */
  }

  getOperacionesPorAduana(data: any) {
    DEBUG && console.log('@getOperacionesPorAduana: Entre');
    DEBUG && console.log(data);
    this.expAux = [];
    this.impAux = [];
    this.imp = [];
    this.exp = [];
    this.clave = [];

    for (let item of data) {
      if (item.tipo == 'Importacion') {
        this.imp.push(item);
        this.impAux.push(item.totalI);
      } else {
        this.exp.push(item);
      }
    }
    this.expAux = new Array(this.imp.length).fill(0);
    let i = 0;
    for (let item of this.imp) {
      for (let item2 of this.exp) {
        if (item.clave == item2.clave) {
          this.expAux[i] = item2.totalI;
        }
      }
      i++;
    }
    for (let item of this.imp) {
      this.clave.push(item.clave);
    }

    let color1 = new Array(this.imp.length).fill('#5A95F0');
    let color2 = new Array(this.imp.length).fill('#FDC340');

    try {
      let canvasDiv = document.querySelector('#barStackedChart1Div');
      canvasDiv.removeChild(document.querySelector('#barStackedChart1'));
      document.querySelector('#barStackedChart1Div').insertAdjacentHTML('afterbegin', '<canvas id="barStackedChart1" width="100%"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    let widthBar = 0;
    if (this.impAux.length > 1) {
      widthBar = 0.7;
    } else {
      widthBar = 0.25;
    }

    ///////////////////////////////////// legend //////////////////////////////////////
    let index = 0;
    let array = [];
    let dat = new Legend();
    for(let item of this.impAux){
      dat.reset();
      dat.titulo = this.clave[index],
        dat.dataDefault = item,
        dat.dataDouble = this.expAux[index],
        dat.colorDefault = color1[0],
        dat.nameDefault = 'Imp:',
        dat.nameDouble = 'Exp:',
        dat.colorDouble = color2[0],
        dat.double = true

      array.push(Object.assign(new Legend, dat));
      index++;
    }

    this.data1 = Object.assign(new Array, array);
    DEBUG && console.log(this.data1);
    ///////////////////////////////////// legend //////////////////////////////////////


    this.createBarStacked('barStackedChart1', ['Importaciones', 'Exportaciones'], this.impAux, this.expAux, color1, color2, '#5A95F0', 'rgba(0, 0, 0, 0.1)', 1, widthBar, this.clave, this.clave);

  }


  t501_obtenerOperacionesPorMedioTransporteArribo(data: any) {
    DEBUG && console.log('@t501_obtenerOperacionesPorMedioTransporteArribo: Entre');
    DEBUG && console.log(data);
    this.expAux_TransporteArribo = [];
    this.impAux_TransporteArribo = [];
    this.imp_TransporteArribo = [];
    this.exp_TransporteArribo = [];
    this.clave_TransporteArribo = [];

    for (let item of data) {
      if (item.tipo == 'Importacion') {
        this.imp_TransporteArribo.push(item);
        this.impAux_TransporteArribo.push(item.totalI);
      } else {
        this.exp_TransporteArribo.push(item);
      }
    }
    this.expAux_TransporteArribo = new Array(this.imp_TransporteArribo.length).fill(0);
    let i = 0;
    for (let item of this.imp_TransporteArribo) {
      for (let item2 of this.exp_TransporteArribo) {
        if (item.clave == item2.clave) {
          this.expAux_TransporteArribo[i] = item2.totalI;
        }
      }
      i++;
    }
    let claveAux = [];
    for (let item of this.imp_TransporteArribo) {
      this.clave_TransporteArribo.push(item.clave);
      claveAux.push(item.clave);
    }

    let color1 = new Array(this.imp_TransporteArribo.length).fill('#5A95F0');
    let color2 = new Array(this.imp_TransporteArribo.length).fill('#FDC340');

    try {
      let canvasDiv = document.querySelector('#barStackedChart2Div');
      canvasDiv.removeChild(document.querySelector('#barStackedChart2'));
      document.querySelector('#barStackedChart2Div').insertAdjacentHTML('afterbegin', '<canvas id="barStackedChart2" width="100%"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    let widthBar = 0;
    if (this.impAux_TransporteArribo.length > 1) {
      widthBar = 0.7;
    } else {
      widthBar = 0.25;
    }
    claveAux = this.tableroHttp.getNombreTransportes(claveAux);
    this.clave_TransporteArribo = this.tableroHttp.getIconosTransportes(this.clave_TransporteArribo);
    DEBUG && console.log(this.clave_TransporteArribo);
    DEBUG && console.log(claveAux);

    ///////////////////////////////////// legend //////////////////////////////////////
    let index = 0;
    let array = [];
    let dat = new Legend();
    for(let item of this.impAux_TransporteArribo){
      dat.reset();
      dat.titulo = claveAux[index] == "NO SE DECLARA MEDIO DE TRANSPORTE POR NO HABER" ? claveAux[index] + " PRESENTACIÓN FÍSICA DE MERCANCÍAS ANTE LA ADUANA" : claveAux[index];
      dat.dataDefault = item;
      dat.dataDouble = this.expAux_TransporteArribo[index];
      dat.colorDefault = color1[0];
      dat.nameDefault = 'Imp:';
      dat.nameDouble = 'Exp:';
      dat.colorDouble = color2[0];
      dat.double = true

      array.push(Object.assign(new Legend, dat));
      index++;
    }

    this.data2 = Object.assign(new Array, array);
    DEBUG && console.log(this.data2);
    ///////////////////////////////////// legend //////////////////////////////////////

    this.createBarStacked('barStackedChart2', ['Importaciones', 'Exportaciones'], this.impAux_TransporteArribo, this.expAux_TransporteArribo, color1, color2, '#5A95F0', 'rgba(0, 0, 0, 0.1)', 1, widthBar, this.clave_TransporteArribo, claveAux);
  }


  t501_obtenerPesoBrutoPorMedioTransporteArribo(data: any) {
    DEBUG && console.log('@t501_obtenerPesoBrutoPorMedioTransporteArribo: Entre');
    DEBUG && console.log(data);
    this.expAux_PesoBrutoTransporteArribo = [];
    this.impAux_PesoBrutoTransporteArribo = [];
    this.imp_PesoBrutoTransporteArribo = [];
    this.exp_PesoBrutoTransporteArribo = [];
    this.clave_PesoBrutoTransporteArribo = [];

    for (let item of data) {
      if (item.tipo == 'Importacion') {
        this.imp_PesoBrutoTransporteArribo.push(item);
        this.impAux_PesoBrutoTransporteArribo.push(item.totalD);
      } else {
        this.exp_PesoBrutoTransporteArribo.push(item);
      }
    }
    this.expAux_PesoBrutoTransporteArribo = new Array(this.imp_PesoBrutoTransporteArribo.length).fill(0);
    let i = 0;
    for (let item of this.imp_PesoBrutoTransporteArribo) {
      for (let item2 of this.exp_PesoBrutoTransporteArribo) {
        if (item.clave == item2.clave) {
          this.expAux_PesoBrutoTransporteArribo[i] = item2.totalD;
        }
      }
      i++;
    }
    let claveAux = [];
    for (let item of this.imp_PesoBrutoTransporteArribo) {
      this.clave_PesoBrutoTransporteArribo.push(item.clave);
      claveAux.push(item.clave);
    }

    let color1 = new Array(this.imp_PesoBrutoTransporteArribo.length).fill('#5A95F0');
    let color2 = new Array(this.imp_PesoBrutoTransporteArribo.length).fill('#FDC340');

    try {
      let canvasDiv = document.querySelector('#barStackedChart3Div');
      canvasDiv.removeChild(document.querySelector('#barStackedChart3'));
      document.querySelector('#barStackedChart3Div').insertAdjacentHTML('afterbegin', '<canvas id="barStackedChart3" width="100%"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    let widthBar = 0;
    if (this.impAux_PesoBrutoTransporteArribo.length > 1) {
      widthBar = 0.7;
    } else {
      widthBar = 0.25;
    }

    claveAux = this.tableroHttp.getNombreTransportes(claveAux);
    this.clave_PesoBrutoTransporteArribo = this.tableroHttp.getIconosTransportes(this.clave_PesoBrutoTransporteArribo);
    DEBUG && console.log(this.clave_PesoBrutoTransporteArribo);
    DEBUG && console.log(claveAux);


    ///////////////////////////////////// legend //////////////////////////////////////
    let index = 0;
    let array = [];
    let dat = new Legend();
    for(let item of this.impAux_PesoBrutoTransporteArribo){
      dat.reset();
      dat.titulo = claveAux[index] == "NO SE DECLARA MEDIO DE TRANSPORTE POR NO HABER" ? claveAux[index] + " PRESENTACIÓN FÍSICA DE MERCANCÍAS ANTE LA ADUANA" : claveAux[index],
        dat.dataDefault = item,
        dat.dataDouble = this.expAux_PesoBrutoTransporteArribo[index],
        dat.colorDefault = color1[0],
        dat.nameDefault = 'Imp:',
        dat.nameDouble = 'Exp:',
        dat.colorDouble = color2[0],
        dat.double = true,


        array.push(Object.assign(new Legend, dat));
      index++;
    }

    this.data3 = Object.assign(new Array, array);
    DEBUG && console.log(this.data3);
    ///////////////////////////////////// legend //////////////////////////////////////


    this.createBarStacked('barStackedChart3', ['Importaciones', 'Exportaciones'], this.impAux_PesoBrutoTransporteArribo, this.expAux_PesoBrutoTransporteArribo, color1, color2, '#5A95F0', 'rgba(0, 0, 0, 0.1)', 1, widthBar, this.clave_PesoBrutoTransporteArribo, claveAux, true);

  }

  t501_obtenerOperacionesPorTipoPedimento(data: any) {
    DEBUG && console.log('@t501_obtenerOperacionesPorTipoPedimento: Entre');
    DEBUG && console.log(data);
    this.expAux_TipoPedimento = [];
    this.impAux_TipoPedimento = [];
    this.imp_TipoPedimento = [];
    this.exp_TipoPedimento = [];
    this.clave_TipoPedimento = [];

    for (let item of data) {
      if (item.tipo == 'Importacion') {
        this.imp_TipoPedimento.push(item);
        this.impAux_TipoPedimento.push(item.totalI);
      } else {
        this.exp_TipoPedimento.push(item);
      }
    }
    this.expAux_TipoPedimento = new Array(this.imp_TipoPedimento.length).fill(0);
    let i = 0;
    for (let item of this.imp_TipoPedimento) {
      for (let item2 of this.exp_TipoPedimento) {
        if (item.clave == item2.clave) {
          this.expAux_TipoPedimento[i] = item2.totalI;
        }
      }
      i++;
    }
    for (let item of this.imp_TipoPedimento) {
      this.clave_TipoPedimento.push(item.clave);
    }

    let color1 = new Array(this.imp_TipoPedimento.length).fill('#5A95F0');
    let color2 = new Array(this.imp_TipoPedimento.length).fill('#FDC340');

    try {
      let canvasDiv = document.querySelector('#barStackedChartVertical1Div');
      canvasDiv.removeChild(document.querySelector('#barStackedChartVertical1'));
      document.querySelector('#barStackedChartVertical1Div').insertAdjacentHTML('afterbegin', '<canvas id="barStackedChartVertical1" width="100%"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }
    let clave = ['Pedimento Normal', 'Pedimento de Rectificación'];

    ///////////////////////////////////// legend //////////////////////////////////////
    let index = 0;
    let array = [];
    let dat = new Legend();
    for(let item of this.impAux_TipoPedimento){
      dat.reset();
      dat.titulo = clave[index];
      dat.dataDefault = item,
        dat.dataDouble = this.expAux_TipoPedimento[index],
        dat.colorDefault = color1[0],
        dat.nameDefault = 'Imp:',
        dat.nameDouble = 'Exp:',
        dat.colorDouble = color2[0],
        dat.double = true,


        array.push(Object.assign(new Legend, dat));
      index++;
    }

    this.data10 = Object.assign(new Array, array);
    DEBUG && console.log(this.data10);
    ///////////////////////////////////// legend //////////////////////////////////////


    this.createBarStackedVertical('barStackedChartVertical1', ['Importaciones', 'Exportaciones'], this.impAux_TipoPedimento, this.expAux_TipoPedimento, color1, color2, 'transparent', 'rgba(0, 0, 0, 0.1)', 1, '0.5', clave);


    this.total_TipoPedimento = 0;
    this.total_TipoPedimento = (this.expAux_TipoPedimento[0] + this.impAux_TipoPedimento[0]) + (this.expAux_TipoPedimento[1] + this.impAux_TipoPedimento[1]);
    let etiquetas;
    let valores;
    let colores = ['#F89C39', '#BA1768'];
    if (data.length !== 0) {
      etiquetas = ['Pedimento Normal', 'Pedimento de Rectificación'];
      valores = [(this.expAux_TipoPedimento[0] + this.impAux_TipoPedimento[0]), (this.expAux_TipoPedimento[1] + this.impAux_TipoPedimento[1])];
    } else {
      etiquetas = [];
      valores = [0, 0, 0];
    }
    try {
      let canvasDiv = document.querySelector('#doughnut2Div');
      canvasDiv.removeChild(document.querySelector('#doughnut2'));
      document.querySelector('#doughnut2Div').insertAdjacentHTML('afterbegin', '<canvas id="doughnut2"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    let tooltipData = true;

    this.createDoughnut('doughnut2', etiquetas, valores, colores, tooltipData);

  }

  t501_obtenerOperacionesPorClavePedimento(data: any) {
    DEBUG && console.log('@t501_obtenerOperacionesPorClavePedimento: Entre');
    DEBUG && console.log(data);
    this.expAux_ClavePedimento = [];
    this.impAux_ClavePedimento = [];
    this.imp_ClavePedimento = [];
    this.exp_ClavePedimento = [];
    this.clave_ClavePedimento = [];

    for (let item of data) {
      if (item.tipo == 'Importacion') {
        this.imp_ClavePedimento.push(item);
        this.impAux_ClavePedimento.push(item.totalI);
      } else {
        this.exp_ClavePedimento.push(item);
      }
    }
    this.expAux_ClavePedimento = new Array(this.imp_ClavePedimento.length).fill(0);
    let i = 0;
    for (let item of this.imp_ClavePedimento) {
      for (let item2 of this.exp_ClavePedimento) {
        if (item.claveStr == item2.claveStr) {
          this.expAux_ClavePedimento[i] = item2.totalI;
        }
      }
      i++;
    }
    for (let item of this.imp_ClavePedimento) {
      this.clave_ClavePedimento.push(item.claveStr);
    }

    let color1 = new Array(this.imp_ClavePedimento.length).fill('#E06188');
    let color2 = new Array(this.imp_ClavePedimento.length).fill('#FDC340');

    try {
      let canvasDiv = document.querySelector('#barStackedChart4Div');
      canvasDiv.removeChild(document.querySelector('#barStackedChart4'));
      document.querySelector('#barStackedChart4Div').insertAdjacentHTML('afterbegin', '<canvas id="barStackedChart4" width="100%"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }
    let widthBar = 0;
    if (this.impAux_ClavePedimento.length > 1) {
      widthBar = 0.75;
    } else {
      widthBar = 0.25;
    }


    ///////////////////////////////////// legend //////////////////////////////////////
    let index = 0;
    let array = [];
    let dat = new Legend();
    for(let item of this.impAux_ClavePedimento){
      dat.reset();
      dat.titulo = this.clave_ClavePedimento[index];
      dat.dataDefault = item;
      dat.dataDouble = this.expAux_ClavePedimento[index];
      dat.colorDefault = color1[0];
      dat.nameDefault = 'Imp:';
      dat.nameDouble = 'Exp:';
      dat.colorDouble = color2[0];
      dat.double = true;
      dat.colorLabel = '#FFFFFF';

      array.push(Object.assign(new Legend, dat));
      index++;
    }

    this.data4 = Object.assign(new Array, array);
    DEBUG && console.log(this.data4);
    ///////////////////////////////////// legend //////////////////////////////////////


    this.createBarStacked('barStackedChart4', ['Importaciones', 'Exportaciones'], this.impAux_ClavePedimento, this.expAux_ClavePedimento, color1, color2, '#fff', '#5A95F0', 2, widthBar, this.clave_ClavePedimento);

  }

  t501_ObtenerOperacionesMedioTransporteSalida(data: any) {
    DEBUG && console.log('@t501_ObtenerOperacionesMedioTransporteSalida: Entre');
    DEBUG && console.log(data);
    this.impAux_TransporteSalida = [];
    this.imp_TransporteSalida = [];
    this.clave_TransporteSalida = [];

    for (let item of data) {
      this.imp_TransporteSalida.push(item);
      this.impAux_TransporteSalida.push(item.totalI);
    }
    let claveAux = [];
    for (let item of this.imp_TransporteSalida) {
      this.clave_TransporteSalida.push(item.clave);
      claveAux.push(item.clave);
    }

    let color1 = new Array(this.imp_TransporteSalida.length).fill('#5793F3');

    try {
      let canvasDiv = document.querySelector('#barChart4Div');
      canvasDiv.removeChild(document.querySelector('#barChart4'));
      document.querySelector('#barChart4Div').insertAdjacentHTML('afterbegin', '<canvas id="barChart4" width="100%"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    let widthBar = 0;
    if (this.impAux_TransporteSalida.length > 1) {
      widthBar = 0.7;
    } else {
      widthBar = 0.25;
    }

    claveAux = this.tableroHttp.getNombreTransportes(claveAux);
    this.clave_TransporteSalida = this.tableroHttp.getIconosTransportes(this.clave_TransporteSalida);
    DEBUG && console.log(this.clave_TransporteSalida);
    DEBUG && console.log(claveAux);

    ///////////////////////////////////// legend //////////////////////////////////////
    let index = 0;
    let array = [];
    let dat = new Legend();
    for(let item of this.impAux_TransporteSalida){
      dat.reset();
      dat.titulo = claveAux[index] == "NO SE DECLARA MEDIO DE TRANSPORTE POR NO HABER" ? claveAux[index] + " PRESENTACIÓN FÍSICA DE MERCANCÍAS ANTE LA ADUANA" : claveAux[index];
      dat.dataDefault = item;
      dat.colorDefault = color1[0];
      dat.nameDefault = 'Op:';
      dat.double = false;

      array.push(Object.assign(new Legend, dat));
      index++;
    }

    this.data5 = Object.assign(new Array, array);
    DEBUG && console.log(this.data5);
    ///////////////////////////////////// legend //////////////////////////////////////

    this.createBar('barChart4', 'Operaciones', this.impAux_TransporteSalida, color1, '#5A95F0', 'rgba(0, 0, 0, 0.1)', 1, widthBar, this.clave_TransporteSalida, claveAux);

  }

  t501_ObtenerOperacionesMedioTransporteArriboSalida(data: any) {
    DEBUG && console.log('@t501_ObtenerOperacionesMedioTransporteArriboSalida: Entre');
    DEBUG && console.log(data);
    this.impAux_ArriboSalida = [];
    this.imp_ArriboSalida = [];
    this.clave_ArriboSalida = [];

    for (let item of data) {
      this.imp_ArriboSalida.push(item);
      this.impAux_ArriboSalida.push(item.totalI);
    }
    let claveAux = [];
    for (let item of this.imp_ArriboSalida) {
      this.clave_ArriboSalida.push(item.clave);
      claveAux.push(item.clave);
    }

    let color1 = new Array(this.imp_ArriboSalida.length).fill('#E06188');

    try {
      let canvasDiv = document.querySelector('#barChart5Div');
      canvasDiv.removeChild(document.querySelector('#barChart5'));
      document.querySelector('#barChart5Div').insertAdjacentHTML('afterbegin', '<canvas id="barChart5" width="100%"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    let widthBar = 0;
    if (this.impAux_ArriboSalida.length > 1) {
      widthBar = 0.7;
    } else {
      widthBar = 0.25;
    }

    claveAux = this.tableroHttp.getNombreTransportes(claveAux);
    this.clave_ArriboSalida = this.tableroHttp.getIconosTransportes(this.clave_ArriboSalida);
    DEBUG && console.log(this.clave_ArriboSalida);
    DEBUG && console.log(claveAux);

    ///////////////////////////////////// legend //////////////////////////////////////
    let index = 0;
    let array = [];
    let dat = new Legend();
    for(let item of this.impAux_ArriboSalida){
      dat.reset();
      dat.titulo = claveAux[index] == "NO SE DECLARA MEDIO DE TRANSPORTE POR NO HABER" ? claveAux[index] + " PRESENTACIÓN FÍSICA DE MERCANCÍAS ANTE LA ADUANA" : claveAux[index];
      dat.dataDefault = item;
      dat.colorDefault = color1[0];
      dat.nameDefault = 'Op:';
      dat.double = false;

      array.push(Object.assign(new Legend, dat));
      index++;
    }

    this.data6 = Object.assign(new Array, array);
    DEBUG && console.log(this.data6);
    ///////////////////////////////////// legend //////////////////////////////////////

    this.createBar('barChart5', 'Operaciones', this.impAux_ArriboSalida, color1, '#5A95F0', 'rgba(0, 0, 0, 0.1)', 1, widthBar, this.clave_ArriboSalida, claveAux);

  }

  t501_ObtenerOperacionesPorIncrementablesYDeducibles(data: any) {
    DEBUG && console.log('@t501_ObtenerOperacionesPorIncrementablesYDeducibles: Entre');
    DEBUG && console.log(data);

    this.totalAux_IncrementablesYDeducibles = [];
    for (let item of data) {
      this.totalAux_IncrementablesYDeducibles.push(item.totalD);
    }

    this.fletes = this.totalAux_IncrementablesYDeducibles[0];
    this.seguros = this.totalAux_IncrementablesYDeducibles[1];
    this.embalajes = this.totalAux_IncrementablesYDeducibles[2];
    this.incrementables = this.totalAux_IncrementablesYDeducibles[3];
    this.deducibles = this.totalAux_IncrementablesYDeducibles[4];

    this.total_IncrementablesYDeducibles = 0;
    this.total_IncrementablesYDeducibles = this.fletes + this.seguros + this.embalajes + this.incrementables + this.deducibles;
    let etiquetas;
    let valores;
    let colores = ['#A667D0', '#EAE833', '#53DFA8', '#3FBEF0', '#EA0D3E'];
    if (data.length !== 0) {
      etiquetas = ['Fletes', 'Seguros', 'Embalajes', 'Incrementables', 'Deducibles'];
      valores = this.totalAux_IncrementablesYDeducibles;
    } else {
      etiquetas = [];
      valores = [0, 0, 0];
    }
    try {
      let canvasDiv = document.querySelector('#doughnut3Div');
      canvasDiv.removeChild(document.querySelector('#doughnut3'));
      document.querySelector('#doughnut3Div').insertAdjacentHTML('afterbegin', '<canvas id="doughnut3"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }


    let tooltipData = false;

    this.createDoughnut('doughnut3', etiquetas, valores, colores, tooltipData);

  }

  t501_ObtenerOperacionesDestinoMercancia(data: any) {
    DEBUG && console.log('@t501_ObtenerOperacionesDestinoMercancia: Entre');
    DEBUG && console.log(data);
    this.impAux_DestinoMercancia = [];
    this.imp_DestinoMercancia = [];
    this.clave_DestinoMercancia = [];

    for (let item of data) {
      this.imp_DestinoMercancia.push(item);
      this.impAux_DestinoMercancia.push(item.totalI);
    }
    let claveAux = [];
    for (let item of this.imp_DestinoMercancia) {
      this.clave_DestinoMercancia.push(item.clave);
      claveAux.push(item.clave);
    }

    let color1 = new Array(this.imp_DestinoMercancia.length).fill('#5793F3');

    try {
      let canvasDiv = document.querySelector('#barChart1Div');
      canvasDiv.removeChild(document.querySelector('#barChart1'));
      document.querySelector('#barChart1Div').insertAdjacentHTML('afterbegin', '<canvas id="barChart1" width="100%"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    let widthBar = 0;
    if (this.impAux_DestinoMercancia.length > 1) {
      widthBar = 0.7;
    } else {
      widthBar = 0.25;
    }

    claveAux = this.tableroHttp.getNombreTransportes(claveAux);
    this.clave_DestinoMercancia = this.tableroHttp.getIconosTransportes(this.clave_DestinoMercancia);
    DEBUG && console.log(this.clave_DestinoMercancia);
    DEBUG && console.log(claveAux);

    ///////////////////////////////////// legend //////////////////////////////////////
    let index = 0;
    let array = [];
    let dat = new Legend();
    for(let item of this.impAux_DestinoMercancia){
      dat.reset();
      dat.titulo = claveAux[index] == "NO SE DECLARA MEDIO DE TRANSPORTE POR NO HABER" ? claveAux[index] + " PRESENTACIÓN FÍSICA DE MERCANCÍAS ANTE LA ADUANA" : claveAux[index];
      dat.dataDefault = item;
      dat.colorDefault = color1[0];
      dat.nameDefault = 'Op:';
      dat.double = false;

      array.push(Object.assign(new Legend, dat));
      index++;
    }

    this.data7 = Object.assign(new Array, array);
    DEBUG && console.log(this.data7);
    ///////////////////////////////////// legend //////////////////////////////////////

    this.createBar('barChart1', 'Operaciones', this.impAux_DestinoMercancia, color1, '#5A95F0', 'rgba(0, 0, 0, 0.1)', 1, widthBar, this.clave_DestinoMercancia, claveAux);


  }

  t501_ObtenerOperacionesPorPatente(data: any) {
    DEBUG && console.log('@t501_ObtenerOperacionesPorPatente: Entre');
    DEBUG && console.log(data);
    this.impAux_PorPatente = [];
    this.imp_PorPatente = [];
    this.clave_PorPatente = [];

    for (let item of data) {
      this.imp_PorPatente.push(item);
      this.impAux_PorPatente.push(item.totalI);
    }
    for (let item of this.imp_PorPatente) {
      this.clave_PorPatente.push(item.clave);
    }

    let color1 = new Array(this.imp_PorPatente.length).fill('#E06188');

    try {
      let canvasDiv = document.querySelector('#barChart2Div');
      canvasDiv.removeChild(document.querySelector('#barChart2'));
      document.querySelector('#barChart2Div').insertAdjacentHTML('afterbegin', '<canvas id="barChart2" width="100%"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    let widthBar = 0;
    if (this.impAux_PorPatente.length > 1) {
      widthBar = 0.7;
    } else {
      widthBar = 0.25;
    }

    ///////////////////////////////////// legend //////////////////////////////////////
    let index = 0;
    let array = [];
    let dat = new Legend();
    for(let item of this.impAux_PorPatente){
      dat.reset();
      dat.titulo = this.clave_PorPatente[index];
      dat.dataDefault = item;
      dat.colorDefault = color1[0];
      dat.nameDefault = 'Op:';
      dat.double = false;

      array.push(Object.assign(new Legend, dat));
      index++;
    }

    this.data8 = Object.assign(new Array, array);
    DEBUG && console.log(this.data8);
    ///////////////////////////////////// legend //////////////////////////////////////

    this.createBar('barChart2', 'Operaciones', this.impAux_PorPatente, color1, '#5A95F0', 'rgba(0, 0, 0, 0.1)', 1, widthBar, this.clave_PorPatente, this.clave_PorPatente);

  }

  t501_ObtenerOperacionesPorTipoPedimento(data: any) {
    DEBUG && console.log('@t501_ObtenerOperacionesPorTipoPedimento: Entre');
    DEBUG && console.log(data);
    this.impAux_PorTipoPedimento = [];
    this.imp_PorTipoPedimento = [];
    this.clave_PorTipoPedimento = [];

    for (let item of data) {
      this.imp_PorTipoPedimento.push(item);
      this.impAux_PorTipoPedimento.push(item.totalI);
    }
    for (let item of this.imp_PorTipoPedimento) {
      this.clave_PorTipoPedimento.push(item.clave);
    }

    let color1 = new Array(this.imp_PorTipoPedimento.length).fill('#5793F3');

    try {
      let canvasDiv = document.querySelector('#barChart3Div');
      canvasDiv.removeChild(document.querySelector('#barChart3'));
      document.querySelector('#barChart3Div').insertAdjacentHTML('afterbegin', '<canvas id="barChart3" width="100%"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    let widthBar = 0;
    if (this.impAux_PorTipoPedimento.length > 1) {
      widthBar = 0.7;
    } else {
      widthBar = 0.25;
    }


    ///////////////////////////////////// legend //////////////////////////////////////
    let index = 0;
    let array = [];
    let dat = new Legend();
    for(let item of this.impAux_PorTipoPedimento){
      dat.reset();
      dat.titulo = this.clave_PorTipoPedimento[index];
      dat.dataDefault = item;
      dat.colorDefault = color1[0];
      dat.nameDefault = 'Op:';
      dat.double = false;

      array.push(Object.assign(new Legend, dat));
      index++;
    }

    this.data9 = Object.assign(new Array, array);
    DEBUG && console.log(this.data9);
    ///////////////////////////////////// legend //////////////////////////////////////

    this.createBar('barChart3', 'Operaciones', this.impAux_PorTipoPedimento, color1, '#5A95F0', 'rgba(0, 0, 0, 0.1)', 1, widthBar, this.clave_PorTipoPedimento, this.clave_PorTipoPedimento);

  }

  /* ------------------------------------------------------------ 502 graficas ------------------------------------------------------------ */

  t502_obtenerOperacionesPorAduana(data: any) {
    DEBUG && console.log('@t502_obtenerOperacionesPorAduana: Entre');
    DEBUG && console.log(data);
    this.impAux_502 = [];
    this.imp_502 = [];
    this.clave_502 = [];

    for (let item of data) {
      this.imp_502.push(item);
      this.impAux_502.push(item.totalI);
    }
    for (let item of this.imp_502) {
      this.clave_502.push(item.clave);
    }

    let color1 = new Array(this.imp_502.length).fill('#5793F3');

    try {
      let canvasDiv = document.querySelector('#barChart4Div');
      canvasDiv.removeChild(document.querySelector('#barChart4'));
      document.querySelector('#barChart4Div').insertAdjacentHTML('afterbegin', '<canvas id="barChart4" width="100%"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    let widthBar = 0;
    if (this.impAux_502.length > 1) {
      widthBar = 0.7;
    } else {
      widthBar = 0.25;
    }

    ///////////////////////////////////// legend //////////////////////////////////////
    let index = 0;
    let array = [];
    let dat = new Legend();
    for(let item of this.impAux_502){
      dat.reset();
      dat.titulo = this.clave_502[index];
      dat.dataDefault = item;
      dat.colorDefault = color1[0];
      dat.nameDefault = 'Op:';
      dat.double = false;

      array.push(Object.assign(new Legend, dat));
      index++;
    }

    this.data1_502 = Object.assign(new Array, array);
    DEBUG && console.log(this.data1_502);
    ///////////////////////////////////// legend //////////////////////////////////////

    this.createBar('barChart4', 'Operaciones', this.impAux_502, color1, '#5A95F0', 'rgba(0, 0, 0, 0.1)', 1, widthBar, this.clave_502, this.clave_502);
  }

  t502_obtenerOperacionesPorAgenteAduanal(data: any) {
    DEBUG && console.log('@t502_obtenerOperacionesPorAgenteAduanal: Entre');
    DEBUG && console.log(data);
    this.impAux_502 = [];
    this.imp_502 = [];
    this.clave_502 = [];

    for (let item of data) {
      this.imp_502.push(item);
      this.impAux_502.push(item.totalI);
    }
    for (let item of this.imp_502) {
      this.clave_502.push(item.clave);
    }

    let color1 = new Array(this.imp_502.length).fill('#E06188');

    try {
      let canvasDiv = document.querySelector('#barChart5Div');
      canvasDiv.removeChild(document.querySelector('#barChart5'));
      document.querySelector('#barChart5Div').insertAdjacentHTML('afterbegin', '<canvas id="barChart5" width="100%"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    let widthBar = 0;
    if (this.impAux_502.length > 1) {
      widthBar = 0.7;
    } else {
      widthBar = 0.25;
    }

    ///////////////////////////////////// legend //////////////////////////////////////
    let index = 0;
    let array = [];
    let dat = new Legend();
    for(let item of this.impAux_502){
      dat.reset();
      dat.titulo = this.clave_502[index];
      dat.dataDefault = item;
      dat.colorDefault = color1[0];
      dat.nameDefault = 'Op:';
      dat.double = false;

      array.push(Object.assign(new Legend, dat));
      index++;
    }

    this.data2_502 = Object.assign(new Array, array);
    DEBUG && console.log(this.data2_502);
    ///////////////////////////////////// legend //////////////////////////////////////

    this.createBar('barChart5', 'Operaciones', this.impAux_502, color1, '#5A95F0', 'rgba(0, 0, 0, 0.1)', 1, widthBar, this.clave_502, this.clave_502);
  }

  t502_obtenerOperacionesPorPais(data: any) {
    DEBUG && console.log('@t502_obtenerOperacionesPorPais: Entre');
    DEBUG && console.log(data);
    this.impAux_502 = [];
    this.imp_502 = [];
    this.clave_502 = [];

    for (let item of data) {
      this.imp_502.push(item);
      this.impAux_502.push(item.totalI);
    }
    for (let item of this.imp_502) {
      this.clave_502.push(item.claveStr);
    }

    let color1 = new Array(this.imp_502.length).fill('#5793F3');

    try {
      let canvasDiv = document.querySelector('#barChart6Div');
      canvasDiv.removeChild(document.querySelector('#barChart6'));
      document.querySelector('#barChart6Div').insertAdjacentHTML('afterbegin', '<canvas id="barChart6" width="100%"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    let widthBar = 0;
    if (this.impAux_502.length > 1) {
      widthBar = 0.7;
    } else {
      widthBar = 0.25;
    }

    ///////////////////////////////////// legend //////////////////////////////////////
    let index = 0;
    let array = [];
    let dat = new Legend();
    for(let item of this.impAux_502){
      dat.reset();
      dat.titulo = this.clave_502[index];
      dat.dataDefault = item;
      dat.colorDefault = color1[0];
      dat.nameDefault = 'Op:';
      dat.double = false;

      array.push(Object.assign(new Legend, dat));
      index++;
    }

    this.data3_502 = Object.assign(new Array, array);
    DEBUG && console.log(this.data3_502);
    ///////////////////////////////////// legend //////////////////////////////////////

    this.createBar('barChart6', 'Operaciones', this.impAux_502, color1, '#5A95F0', 'rgba(0, 0, 0, 0.1)', 1, widthBar, this.clave_502, this.clave_502);
  }

  /* ------------------------------------------------------------ /502 graficas ------------------------------------------------------------ */

  /* ------------------------------------------------------------ 503 graficas ------------------------------------------------------------ */

  t503_ObtenerGuiasPorTipo(data: any) {
    DEBUG && console.log('@t503_ObtenerGuiasPorTipo: Entre');
    DEBUG && console.log(data);
    this.guiasPorTipo = data;
    try {
      this.total_GuiasPorTipo = this.guiasPorTipo[0].totalI + this.guiasPorTipo[1].totalI;
      this.m_GuiasPorTipo = this.guiasPorTipo[1].totalI;
      this.h_GuiasPorTipo = this.guiasPorTipo[0].totalI;
    } catch (error) {
      DEBUG && console.log(error);
    }
    let etiquetas;
    let valores;
    let colores = ["#DD4444", "#FEC42D"];
    if (data.length !== 0) {
      etiquetas =  data.length > 1 ? [data[0].claveStr, data[1].claveStr] : [data[0].claveStr];
      valores = data.length > 1 ? [data[0].totalI, data[1].totalI] : [data[0].totalI];
    } else {
      etiquetas = [];
      valores = [0, 0];
    }
    try {
      let canvasDiv = document.querySelector('#doughnut4Div');
      canvasDiv.removeChild(document.querySelector('#doughnut4'));
      document.querySelector('#doughnut4Div').insertAdjacentHTML('afterbegin', '<canvas id="doughnut4"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    let tooltipData = true;

    this.createDoughnut('doughnut4', etiquetas, valores, colores, tooltipData);
  }

  t503_ObtenerGuiasPorAduana(data: any) {
    DEBUG && console.log('@t503_ObtenerGuiasPorAduana: Entre');
    DEBUG && console.log(data);
    this.impAux_503 = [];
    this.imp_503 = [];
    this.clave_503 = [];

    for (let item of data) {
      this.imp_503.push(item);
      this.impAux_503.push(item.totalI);
    }
    for (let item of this.imp_503) {
      this.clave_503.push(item.clave);
    }

    let color1 = new Array(this.imp_503.length).fill('#E06188');

    try {
      let canvasDiv = document.querySelector('#barChart7Div');
      canvasDiv.removeChild(document.querySelector('#barChart7'));
      document.querySelector('#barChart7Div').insertAdjacentHTML('afterbegin', '<canvas id="barChart7" width="100%"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    let widthBar = 0;
    if (this.impAux_503.length > 1) {
      widthBar = 0.7;
    } else {
      widthBar = 0.25;
    }

    ///////////////////////////////////// legend //////////////////////////////////////
    let index = 0;
    let array = [];
    let dat = new Legend();
    for(let item of this.impAux_503){
      dat.reset();
      dat.titulo = this.clave_503[index];
      dat.dataDefault = item;
      dat.colorDefault = color1[0];
      dat.nameDefault = 'Op:';
      dat.double = false;

      array.push(Object.assign(new Legend, dat));
      index++;
    }

    this.data1_503 = Object.assign(new Array, array);
    DEBUG && console.log(this.data1_503);
    ///////////////////////////////////// legend //////////////////////////////////////

    this.createBar('barChart7', 'Operaciones', this.impAux_503, color1, '#5A95F0', 'rgba(0, 0, 0, 0.1)', 1, widthBar, this.clave_503, this.clave_503);
  }

  t503_ObtenerGuiasPorAgente(data: any) {
    DEBUG && console.log('@t503_ObtenerGuiasPorAgente: Entre');
    DEBUG && console.log(data);
    this.impAux_503 = [];
    this.imp_503 = [];
    this.clave_503 = [];

    for (let item of data) {
      this.imp_503.push(item);
      this.impAux_503.push(item.totalI);
    }
    for (let item of this.imp_503) {
      this.clave_503.push(item.clave);
    }

    let color1 = new Array(this.imp_503.length).fill('#5793F3');

    try {
      let canvasDiv = document.querySelector('#barChart8Div');
      canvasDiv.removeChild(document.querySelector('#barChart8'));
      document.querySelector('#barChart8Div').insertAdjacentHTML('afterbegin', '<canvas id="barChart8" width="100%"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    let widthBar = 0;
    if (this.impAux_503.length > 1) {
      widthBar = 0.7;
    } else {
      widthBar = 0.25;
    }

    ///////////////////////////////////// legend //////////////////////////////////////
    let index = 0;
    let array = [];
    let dat = new Legend();
    for(let item of this.impAux_503){
      dat.reset();
      dat.titulo = this.clave_503[index];
      dat.dataDefault = item;
      dat.colorDefault = color1[0];
      dat.nameDefault = 'Op:';
      dat.double = false;

      array.push(Object.assign(new Legend, dat));
      index++;
    }

    this.data2_503 = Object.assign(new Array, array);
    DEBUG && console.log(this.data2_503);
    ///////////////////////////////////// legend //////////////////////////////////////

    this.createBar('barChart8', 'Operaciones', this.impAux_503, color1, '#5A95F0', 'rgba(0, 0, 0, 0.1)', 1, widthBar, this.clave_503, this.clave_503);
  }
  /* ------------------------------------------------------------ /503 graficas ------------------------------------------------------------ */

  /* ------------------------------------------------------------ 504 graficas ------------------------------------------------------------ */

  t504_ObtenerContenedorPorAduana(data: any) {
    DEBUG && console.log('@t504_ObtenerContenedorPorAduana: Entre');
    DEBUG && console.log(data);
    this.impAux_504 = [];
    this.imp_504 = [];
    this.clave_504 = [];

    for (let item of data) {
      this.imp_504.push(item);
      this.impAux_504.push(item.totalI);
    }
    for (let item of this.imp_504) {
      this.clave_504.push(item.clave);
    }

    let color1 = new Array(this.imp_504.length).fill('#5793F3');

    try {
      let canvasDiv = document.querySelector('#barChart9Div');
      canvasDiv.removeChild(document.querySelector('#barChart9'));
      document.querySelector('#barChart9Div').insertAdjacentHTML('afterbegin', '<canvas id="barChart9" width="100%"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    let widthBar = 0;
    if (this.impAux_504.length > 1) {
      widthBar = 0.7;
    } else {
      widthBar = 0.25;
    }


    ///////////////////////////////////// legend //////////////////////////////////////
    let index = 0;
    let array = [];
    let dat = new Legend();
    for(let item of this.impAux_504){
      dat.reset();
      dat.titulo = this.clave_504[index];
      dat.dataDefault = item;
      dat.colorDefault = color1[0];
      dat.nameDefault = 'Con:';
      dat.double = false;

      array.push(Object.assign(new Legend, dat));
      index++;
    }

    this.data1_504 = Object.assign(new Array, array);
    DEBUG && console.log(this.data1_504);
    ///////////////////////////////////// legend //////////////////////////////////////

    this.createBar('barChart9', 'Contenedores', this.impAux_504, color1, '#5A95F0', 'rgba(0, 0, 0, 0.1)', 1, widthBar, this.clave_504, this.clave_504);
  }

  t504_ObtenerContenedorPorTipo(data: any) {
    DEBUG && console.log('@t504_ObtenerContenedorPorTipo: Entre');
    DEBUG && console.log(data);
    this.impAux_504 = [];
    this.imp_504 = [];
    this.clave_504 = [];

    for (let item of data) {
      this.imp_504.push(item);
      this.impAux_504.push(item.totalI);
    }
    for (let item of this.imp_504) {
      this.clave_504.push(item.clave);
    }

    let color1 = new Array(this.imp_504.length).fill('#E06188');

    try {
      let canvasDiv = document.querySelector('#barChart10Div');
      canvasDiv.removeChild(document.querySelector('#barChart10'));
      document.querySelector('#barChart10Div').insertAdjacentHTML('afterbegin', '<canvas id="barChart10" width="100%"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    let widthBar = 0;
    if (this.impAux_504.length > 1) {
      widthBar = 0.7;
    } else {
      widthBar = 0.25;
    }

    ///////////////////////////////////// legend //////////////////////////////////////
    let index = 0;
    let array = [];
    let dat = new Legend();
    for(let item of this.impAux_504){
      dat.reset();
      dat.titulo = this.clave_504[index];
      dat.dataDefault = item;
      dat.colorDefault = color1[0];
      dat.nameDefault = 'Op:';
      dat.double = false;

      array.push(Object.assign(new Legend, dat));
      index++;
    }

    this.data2_504 = Object.assign(new Array, array);
    DEBUG && console.log(this.data2_504);
    ///////////////////////////////////// legend //////////////////////////////////////

    this.createBar('barChart10', 'Operaciones', this.impAux_504, color1, '#5A95F0', 'rgba(0, 0, 0, 0.1)', 1, widthBar, this.clave_504, this.clave_504);
  }

  t504_ObtenerContenedorPorAgente(data: any) {
    DEBUG && console.log('@t504_ObtenerContenedorPorAgente: Entre');
    DEBUG && console.log(data);
    this.impAux_504 = [];
    this.imp_504 = [];
    this.clave_504 = [];

    for (let item of data) {
      this.imp_504.push(item);
      this.impAux_504.push(item.totalI);
    }
    for (let item of this.imp_504) {
      this.clave_504.push(item.clave);
    }

    let color1 = new Array(this.imp_504.length).fill('#5793F3');

    try {
      let canvasDiv = document.querySelector('#barChart11Div');
      canvasDiv.removeChild(document.querySelector('#barChart11'));
      document.querySelector('#barChart11Div').insertAdjacentHTML('afterbegin', '<canvas id="barChart11" width="100%"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    let widthBar = 0;
    if (this.impAux_504.length > 1) {
      widthBar = 0.7;
    } else {
      widthBar = 0.25;
    }

    ///////////////////////////////////// legend //////////////////////////////////////
    let index = 0;
    let array = [];
    let dat = new Legend();
    for(let item of this.impAux_504){
      dat.reset();
      dat.titulo = this.clave_504[index];
      dat.dataDefault = item;
      dat.colorDefault = color1[0];
      dat.nameDefault = 'Op:';
      dat.double = false;

      array.push(Object.assign(new Legend, dat));
      index++;
    }

    this.data3_504 = Object.assign(new Array, array);
    DEBUG && console.log(this.data3_504);
    ///////////////////////////////////// legend //////////////////////////////////////

    this.createBar('barChart11', 'Operaciones', this.impAux_504, color1, '#5A95F0', 'rgba(0, 0, 0, 0.1)', 1, widthBar, this.clave_504, this.clave_504);
  }

  /* ------------------------------------------------------------ /504 graficas ------------------------------------------------------------ */

  /* ------------------------------------------------------------ 505 graficas ------------------------------------------------------------ */

  t505_ObtenerFacturaPorPais(data: any) {
    DEBUG && console.log('@t505_ObtenerFacturaPorPais: Entre');
    DEBUG && console.log(data);
    this.impAux_505 = [];
    this.imp_505 = [];
    this.clave_505 = [];

    for (let item of data) {
      this.imp_505.push(item);
      this.impAux_505.push(item.totalI);
    }
    for (let item of this.imp_505) {
      this.clave_505.push(item.claveStr == ' ' ? 'N/A' : item.claveStr);
    }

    let color1 = new Array(this.imp_505.length).fill('#5793F3');

    try {
      let canvasDiv = document.querySelector('#barChart12Div');
      canvasDiv.removeChild(document.querySelector('#barChart12'));
      document.querySelector('#barChart12Div').insertAdjacentHTML('afterbegin', '<canvas id="barChart12" width="100%"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    let widthBar = 0;
    if (this.impAux_505.length > 1) {
      widthBar = 0.7;
    } else {
      widthBar = 0.25;
    }

    ///////////////////////////////////// legend //////////////////////////////////////
    let index = 0;
    let array = [];
    let dat = new Legend();
    for(let item of this.impAux_505){
      dat.reset();
      dat.titulo = this.clave_505[index];
      dat.dataDefault = item;
      dat.colorDefault = color1[0];
      dat.nameDefault = 'Op:';
      dat.double = false;

      array.push(Object.assign(new Legend, dat));
      index++;
    }

    this.data1_505 = Object.assign(new Array, array);
    DEBUG && console.log(this.data1_505);
    ///////////////////////////////////// legend //////////////////////////////////////

    this.createBar('barChart12', 'Operaciones', this.impAux_505, color1, '#5A95F0', 'rgba(0, 0, 0, 0.1)', 1, widthBar, this.clave_505, this.clave_505);
  }

  t505_ObtenerFacturaPorMoneda(data: any) {
    DEBUG && console.log('@t505_ObtenerFacturaPorMoneda: Entre');
    DEBUG && console.log(data);
    this.impAux_505 = [];
    this.imp_505 = [];
    this.clave_505 = [];

    for (let item of data) {
      this.imp_505.push(item);
      this.impAux_505.push(item.totalI);
    }
    for (let item of this.imp_505) {
      this.clave_505.push(item.claveStr == ' ' ? 'N/A' : item.claveStr);
    }

    let color1 = new Array(this.imp_505.length).fill('#E06188');

    try {
      let canvasDiv = document.querySelector('#barChart13Div');
      canvasDiv.removeChild(document.querySelector('#barChart13'));
      document.querySelector('#barChart13Div').insertAdjacentHTML('afterbegin', '<canvas id="barChart13" width="100%"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    let widthBar = 0;
    if (this.impAux_505.length > 1) {
      widthBar = 0.7;
    } else {
      widthBar = 0.25;
    }

    ///////////////////////////////////// legend //////////////////////////////////////
    let index = 0;
    let array = [];
    let dat = new Legend();
    for(let item of this.impAux_505){
      dat.reset();
      dat.titulo = this.clave_505[index];
      dat.dataDefault = item;
      dat.colorDefault = color1[0];
      dat.nameDefault = 'Op:';
      dat.double = false;

      array.push(Object.assign(new Legend, dat));
      index++;
    }

    this.data2_505 = Object.assign(new Array, array);
    DEBUG && console.log(this.data2_505);
    ///////////////////////////////////// legend //////////////////////////////////////

    this.createBar('barChart13', 'Operaciones', this.impAux_505, color1, '#5A95F0', 'rgba(0, 0, 0, 0.1)', 1, widthBar, this.clave_505, this.clave_505);
  }

  /* ------------------------------------------------------------ /505 graficas ------------------------------------------------------------ */

  /* ------------------------------------------------------------ 506 graficas ------------------------------------------------------------ */

  t506_ObtenerFechaPorAduana(data: any) {
    DEBUG && console.log('@t506_ObtenerFechaPorAduana: Entre');
    DEBUG && console.log(data);
    this.impAux_506 = [];
    this.imp_506 = [];
    this.clave_506 = [];

    for (let item of data) {
      this.imp_506.push(item);
      this.impAux_506.push(item.totalI);
    }
    for (let item of this.imp_506) {
      this.clave_506.push(item.clave);
    }

    let color1 = new Array(this.imp_506.length).fill('#5793F3');

    try {
      let canvasDiv = document.querySelector('#barChart14Div');
      canvasDiv.removeChild(document.querySelector('#barChart14'));
      document.querySelector('#barChart14Div').insertAdjacentHTML('afterbegin', '<canvas id="barChart14" width="100%"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    let widthBar = 0;
    if (this.impAux_506.length > 1) {
      widthBar = 0.7;
    } else {
      widthBar = 0.25;
    }

    ///////////////////////////////////// legend //////////////////////////////////////
    let index = 0;
    let array = [];
    let dat = new Legend();
    for(let item of this.impAux_506){
      dat.reset();
      dat.titulo = this.clave_506[index];
      dat.dataDefault = item;
      dat.colorDefault = color1[0];
      dat.nameDefault = 'Op:';
      dat.double = false;

      array.push(Object.assign(new Legend, dat));
      index++;
    }

    this.data1_506 = Object.assign(new Array, array);
    DEBUG && console.log(this.data1_506);
    ///////////////////////////////////// legend //////////////////////////////////////

    this.createBar('barChart14', 'Operaciones', this.impAux_506, color1, '#5A95F0', 'rgba(0, 0, 0, 0.1)', 1, widthBar, this.clave_506, this.clave_506);
  }

  t506_ObtenerFechaPorAgente(data: any) {
    DEBUG && console.log('@t506_ObtenerFechaPorAgente: Entre');
    DEBUG && console.log(data);
    this.impAux_506 = [];
    this.imp_506 = [];
    this.clave_506 = [];

    for (let item of data) {
      this.imp_506.push(item);
      this.impAux_506.push(item.totalI);
    }
    for (let item of this.imp_506) {
      this.clave_506.push(item.clave);
    }

    let color1 = new Array(this.imp_506.length).fill('#E06188');

    try {
      let canvasDiv = document.querySelector('#barChart15Div');
      canvasDiv.removeChild(document.querySelector('#barChart15'));
      document.querySelector('#barChart15Div').insertAdjacentHTML('afterbegin', '<canvas id="barChart15" width="100%"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    let widthBar = 0;
    if (this.impAux_506.length > 1) {
      widthBar = 0.7;
    } else {
      widthBar = 0.25;
    }

    ///////////////////////////////////// legend //////////////////////////////////////
    let index = 0;
    let array = [];
    let dat = new Legend();
    for(let item of this.impAux_506){
      dat.reset();
      dat.titulo = this.clave_506[index];
      dat.dataDefault = item;
      dat.colorDefault = color1[0];
      dat.nameDefault = 'Op:';
      dat.double = false;

      array.push(Object.assign(new Legend, dat));
      index++;
    }

    this.data2_506 = Object.assign(new Array, array);
    DEBUG && console.log(this.data2_506);
    ///////////////////////////////////// legend //////////////////////////////////////

    this.createBar('barChart15', 'Operaciones', this.impAux_506, color1, '#5A95F0', 'rgba(0, 0, 0, 0.1)', 1, widthBar, this.clave_506, this.clave_506);
  }

  /* ------------------------------------------------------------ /506 graficas ------------------------------------------------------------ */

  /* ------------------------------------------------------------ 507 graficas ------------------------------------------------------------ */

  t507_ObtenerPedimentoPorAduana(data: any) {
    DEBUG && console.log(data);
    this.impAux_507 = [];
    this.imp_507 = [];
    this.clave_507 = [];

    for (let item of data) {
      this.imp_507.push(item);
      this.impAux_507.push(item.totalI);
    }
    for (let item of this.imp_507) {
      this.clave_507.push(item.clave);
    }

    let color1 = new Array(this.imp_507.length).fill('#5793F3');

    try {
      let canvasDiv = document.querySelector('#barChart16Div');
      canvasDiv.removeChild(document.querySelector('#barChart16'));
      document.querySelector('#barChart16Div').insertAdjacentHTML('afterbegin', '<canvas id="barChart16" width="100%"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    let widthBar = 0;
    if (this.impAux_507.length > 1) {
      widthBar = 0.7;
    } else {
      widthBar = 0.25;
    }

    ///////////////////////////////////// legend //////////////////////////////////////
    let index = 0;
    let array = [];
    let dat = new Legend();
    for(let item of this.impAux_507){
      dat.reset();
      dat.titulo = this.clave_507[index];
      dat.dataDefault = item;
      dat.colorDefault = color1[0];
      dat.nameDefault = 'Ped:';
      dat.double = false;

      array.push(Object.assign(new Legend, dat));
      index++;
    }

    this.data1_507 = Object.assign(new Array, array);
    DEBUG && console.log(this.data1_507);
    ///////////////////////////////////// legend //////////////////////////////////////

    this.createBar('barChart16', 'Pedimentos', this.impAux_507, color1, '#5A95F0', 'rgba(0, 0, 0, 0.1)', 1, widthBar, this.clave_507, this.clave_507);
  }

  t507_ObtenerPedimentoPorClave(data: any) {
    DEBUG && console.log(data);
    this.expAux_507PedimentoPorClave = [];
    this.impAux_507PedimentoPorClave = [];
    this.imp_507PedimentoPorClave = [];
    this.exp_507PedimentoPorClave = [];
    this.clave_507PedimentoPorClave = [];

    for (let item of data) {
      if (item.tipo == 'Importacion') {
        this.imp_507PedimentoPorClave.push(item);
        this.impAux_507PedimentoPorClave.push(item.totalI);
      } else {
        this.exp_507PedimentoPorClave.push(item);
      }
    }
    this.expAux_507PedimentoPorClave = new Array(this.imp_507PedimentoPorClave.length).fill(0);
    let i = 0;
    for (let item of this.imp_507PedimentoPorClave) {
      for (let item2 of this.exp_507PedimentoPorClave) {
        if (item.claveStr == item2.claveStr) {
          this.expAux_507PedimentoPorClave[i] = item2.totalI;
        }
      }
      i++;
    }
    let claveAux = [];
    for (let item of this.imp_507PedimentoPorClave) {
      this.clave_507PedimentoPorClave.push(item.claveStr);
      claveAux.push(item.claveStr);
    }

    let color1 = new Array(this.imp_507PedimentoPorClave.length).fill('#5A95F0');
    let color2 = new Array(this.imp_507PedimentoPorClave.length).fill('#FDC340');

    try {
      let canvasDiv = document.querySelector('#barStackedChart5Div');
      canvasDiv.removeChild(document.querySelector('#barStackedChart5'));
      document.querySelector('#barStackedChart5Div').insertAdjacentHTML('afterbegin', '<canvas id="barStackedChart5" width="100%"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    let widthBar = 0;
    if (this.impAux_507PedimentoPorClave.length > 1) {
      widthBar = 0.7;
    } else {
      widthBar = 0.25;
    }

    //claveAux = this.tableroHttp.getNombreTransportes(claveAux);
    //this.clave_507PedimentoPorClave = this.tableroHttp.getIconosTransportes(this.clave_507PedimentoPorClave);
    DEBUG && console.log(this.clave_507PedimentoPorClave);
    DEBUG && console.log(claveAux);

    ///////////////////////////////////// legend //////////////////////////////////////
    let index = 0;
    let array = [];
    let dat = new Legend();
    for(let item of this.impAux_507PedimentoPorClave){
      dat.reset();
      dat.titulo = this.clave_507PedimentoPorClave[index];
      dat.dataDefault = item;
      dat.dataDouble = this.expAux_507PedimentoPorClave[index];
      dat.colorDefault = color1[0];
      dat.nameDefault = 'Imp:';
      dat.nameDouble = 'Exp:';
      dat.colorDouble = color2[0];
      dat.double = true;

      array.push(Object.assign(new Legend, dat));
      index++;
    }

    this.data2_507 = Object.assign(new Array, array);
    DEBUG && console.log(this.data2_507);
    ///////////////////////////////////// legend //////////////////////////////////////


    this.createBarStacked('barStackedChart5', ['Importaciones', 'Exportaciones'], this.impAux_507PedimentoPorClave, this.expAux_507PedimentoPorClave, color1, color2, '#5A95F0', 'rgba(0, 0, 0, 0.1)', 1, widthBar, this.clave_507PedimentoPorClave, claveAux, false);

  }



  /* ------------------------------------------------------------ /507 graficas ------------------------------------------------------------ */

  /* ------------------------------------------------------------ 509 graficas ------------------------------------------------------------ */

  t509_ObtenerPedimentoPorTipoTasa(data: any) {
    DEBUG && console.log(data);
    this.impAux_509 = [];
    this.imp_509 = [];
    this.clave_509 = [];

    for (let item of data) {
      this.imp_509.push(item);
      this.impAux_509.push(item.totalI);
    }
    for (let item of this.imp_509) {
      this.clave_509.push(item.clave);
    }

    let color1 = new Array(this.imp_509.length).fill('#5793F3');

    try {
      let canvasDiv = document.querySelector('#barChart17Div');
      canvasDiv.removeChild(document.querySelector('#barChart17'));
      document.querySelector('#barChart17Div').insertAdjacentHTML('afterbegin', '<canvas id="barChart17" width="100%"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    let widthBar = 0;
    if (this.impAux_509.length > 1) {
      widthBar = 0.7;
    } else {
      widthBar = 0.25;
    }

    ///////////////////////////////////// legend //////////////////////////////////////
    let index = 0;
    let array = [];
    let dat = new Legend();
    for(let item of this.impAux_509){
      dat.reset();
      dat.titulo = this.clave_509[index];
      dat.dataDefault = item;
      dat.colorDefault = color1[0];
      dat.nameDefault = 'Op:';
      dat.double = false;

      array.push(Object.assign(new Legend, dat));
      index++;
    }

    this.data1_509 = Object.assign(new Array, array);
    DEBUG && console.log(this.data1_509);
    ///////////////////////////////////// legend //////////////////////////////////////

    this.createBar('barChart17', 'Operaciones', this.impAux_509, color1, '#5A95F0', 'rgba(0, 0, 0, 0.1)', 1, widthBar, this.clave_509, this.clave_509);
  }

  t509_ObtenerPedimentoPorAduana(data: any) {
    DEBUG && console.log(data);
    this.impAux_509 = [];
    this.imp_509 = [];
    this.clave_509 = [];

    for (let item of data) {
      this.imp_509.push(item);
      this.impAux_509.push(item.totalI);
    }
    for (let item of this.imp_509) {
      this.clave_509.push(item.clave);
    }

    let color1 = new Array(this.imp_509.length).fill('#E06188');

    try {
      let canvasDiv = document.querySelector('#barChart18Div');
      canvasDiv.removeChild(document.querySelector('#barChart18'));
      document.querySelector('#barChart18Div').insertAdjacentHTML('afterbegin', '<canvas id="barChart18" width="100%"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    let widthBar = 0;
    if (this.impAux_509.length > 1) {
      widthBar = 0.7;
    } else {
      widthBar = 0.25;
    }

    ///////////////////////////////////// legend //////////////////////////////////////
    let index = 0;
    let array = [];
    let dat = new Legend();
    for(let item of this.impAux_509){
      dat.reset();
      dat.titulo = this.clave_509[index];
      dat.dataDefault = item;
      dat.colorDefault = color1[0];
      dat.nameDefault = 'Op:';
      dat.double = false;

      array.push(Object.assign(new Legend, dat));
      index++;
    }

    this.data2_509 = Object.assign(new Array, array);
    DEBUG && console.log(this.data2_509);
    ///////////////////////////////////// legend //////////////////////////////////////

    this.createBar('barChart18', 'Operaciones', this.impAux_509, color1, '#5A95F0', 'rgba(0, 0, 0, 0.1)', 1, widthBar, this.clave_509, this.clave_509);
  }

  t509_ObtenerPedimentoPorImporteTasa(data: any) {
    DEBUG && console.log(data);
    this.expAux_PorImporteTasa = [];
    this.impAux_PorImporteTasa = [];
    this.imp_PorImporteTasa = [];
    this.exp_PorImporteTasa = [];
    this.clave_PorImporteTasa = [];

    for (let item of data) {
      if (item.tipo == 'Importacion') {
        this.imp_PorImporteTasa.push(item);
        this.impAux_PorImporteTasa.push(item.totalI);
      } else {
        this.exp_PorImporteTasa.push(item);
      }
    }
    this.expAux_PorImporteTasa = new Array(this.imp_PorImporteTasa.length).fill(0);
    let i = 0;
    for (let item of this.imp_PorImporteTasa) {
      for (let item2 of this.exp_PorImporteTasa) {
        if (item.clave == item2.clave) {
          this.expAux_PorImporteTasa[i] = item2.totalI;
        }
      }
      i++;
    }
    let claveAux = [];
    for (let item of this.imp_PorImporteTasa) {
      this.clave_PorImporteTasa.push(item.clave);
      claveAux.push(item.clave);
    }

    let color1 = new Array(this.imp_PorImporteTasa.length).fill('#5A95F0');
    let color2 = new Array(this.imp_PorImporteTasa.length).fill('#FDC340');

    try {
      let canvasDiv = document.querySelector('#barStackedChart6Div');
      canvasDiv.removeChild(document.querySelector('#barStackedChart6'));
      document.querySelector('#barStackedChart6Div').insertAdjacentHTML('afterbegin', '<canvas id="barStackedChart6" width="100%"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    let widthBar = 0;
    if (this.impAux_PorImporteTasa.length > 1) {
      widthBar = 0.7;
    } else {
      widthBar = 0.25;
    }

    //claveAux = this.tableroHttp.getNombreTransportes(claveAux);
    //this.clave_507PedimentoPorClave = this.tableroHttp.getIconosTransportes(this.clave_507PedimentoPorClave);
    DEBUG && console.log(this.clave_PorImporteTasa);
    DEBUG && console.log(claveAux);

    ///////////////////////////////////// legend //////////////////////////////////////
    let index = 0;
    let array = [];
    let dat = new Legend();
    for(let item of this.impAux_PorImporteTasa){
      dat.reset();
      dat.titulo = this.clave_PorImporteTasa[index];
      dat.dataDefault = item;
      dat.dataDouble = this.expAux_PorImporteTasa[index];
      dat.colorDefault = color1[0];
      dat.nameDefault = 'Imp:';
      dat.nameDouble = 'Exp:';
      dat.colorDouble = color2[0];
      dat.double = true;

      array.push(Object.assign(new Legend, dat));
      index++;
    }

    this.data3_509 = Object.assign(new Array, array);
    DEBUG && console.log(this.data3_509);
    ///////////////////////////////////// legend //////////////////////////////////////



    this.createBarStacked('barStackedChart6', ['Importaciones', 'Exportaciones'], this.impAux_PorImporteTasa, this.expAux_PorImporteTasa, color1, color2, '#5A95F0', 'rgba(0, 0, 0, 0.1)', 1, widthBar, this.clave_PorImporteTasa, claveAux, false);

  }

  t509_ObtenerPedimentoPorAgente(data: any) {
    DEBUG && console.log(data);
    this.impAux_509 = [];
    this.imp_509 = [];
    this.clave_509 = [];

    for (let item of data) {
      this.imp_509.push(item);
      this.impAux_509.push(item.totalI);
    }
    for (let item of this.imp_509) {
      this.clave_509.push(item.clave);
    }

    let color1 = new Array(this.imp_509.length).fill('#5793F3');

    try {
      let canvasDiv = document.querySelector('#barChart19Div');
      canvasDiv.removeChild(document.querySelector('#barChart19'));
      document.querySelector('#barChart19Div').insertAdjacentHTML('afterbegin', '<canvas id="barChart19" width="100%"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    let widthBar = 0;
    if (this.impAux_509.length > 1) {
      widthBar = 0.7;
    } else {
      widthBar = 0.25;
    }

    ///////////////////////////////////// legend //////////////////////////////////////
    let index = 0;
    let array = [];
    let dat = new Legend();
    for(let item of this.impAux_509){
      dat.reset();
      dat.titulo = this.clave_509[index];
      dat.dataDefault = item;
      dat.colorDefault = color1[0];
      dat.nameDefault = 'Op:';
      dat.double = false;

      array.push(Object.assign(new Legend, dat));
      index++;
    }

    this.data4_509 = Object.assign(new Array, array);
    DEBUG && console.log(this.data4_509);
    ///////////////////////////////////// legend //////////////////////////////////////

    this.createBar('barChart19', 'Operaciones', this.impAux_509, color1, '#5A95F0', 'rgba(0, 0, 0, 0.1)', 1, widthBar, this.clave_509, this.clave_509);
  }



  /* ------------------------------------------------------------ /509 graficas ------------------------------------------------------------ */

  /* ------------------------------------------------------------ 510 graficas ------------------------------------------------------------ */

  t510_ObtenerOperacionesPorTipoPedimento(data: any) {
    DEBUG && console.log(data);
    this.TipoPedimento = data;
    try {
      this.total_510TipoPedimento = this.TipoPedimento.length == 2 ? this.TipoPedimento[0].totalI + this.TipoPedimento[1].totalI : this.TipoPedimento[0].totalI;
      this.e_510TipoPedimento = this.TipoPedimento[1] !== undefined ? this.TipoPedimento[1].totalI : 0;
      this.i_510TipoPedimento = this.TipoPedimento[0].totalI;
    } catch (error) {
      DEBUG && console.log(error);
    }
    let etiquetas;
    let valores;
    let colores = ["#DD4444", "#FEC42D"];
    if (data.length !== 0 && data.length == 2) {
      etiquetas = [data[0].clave, data[1].clave];
      valores = [data[0].totalI, data[1].totalI];
    } else if (data.length == 1) {
      etiquetas = [data[0].clave, 'Exportaciones'];
      valores = [data[0].totalI, 0];
    } else {
      etiquetas = [];
      valores = [0, 0];
    }
    try {
      let canvasDiv = document.querySelector('#doughnut5Div');
      canvasDiv.removeChild(document.querySelector('#doughnut5'));
      document.querySelector('#doughnut5Div').insertAdjacentHTML('afterbegin', '<canvas id="doughnut5"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    let tooltipData = true;

    this.createDoughnut('doughnut5', etiquetas, valores, colores, tooltipData);
  }

  t510_ObtenerOperacionesPorAduana(data: any) {
    DEBUG && console.log(data);
    this.impAux_510 = [];
    this.imp_510 = [];
    this.clave_510 = [];

    for (let item of data) {
      this.imp_510.push(item);
      this.impAux_510.push(item.totalI);
    }
    for (let item of this.imp_510) {
      this.clave_510.push(item.clave);
    }

    let color1 = new Array(this.imp_510.length).fill('#5793F3');

    try {
      let canvasDiv = document.querySelector('#barChart20Div');
      canvasDiv.removeChild(document.querySelector('#barChart20'));
      document.querySelector('#barChart20Div').insertAdjacentHTML('afterbegin', '<canvas id="barChart20" width="100%"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    let widthBar = 0;
    if (this.impAux_510.length > 1) {
      widthBar = 0.7;
    } else {
      widthBar = 0.25;
    }

    ///////////////////////////////////// legend //////////////////////////////////////
    let index = 0;
    let array = [];
    let dat = new Legend();
    for(let item of this.impAux_510){
      dat.reset();
      dat.titulo = this.clave_510[index];
      dat.dataDefault = item;
      dat.colorDefault = color1[0];
      dat.nameDefault = 'Op:';
      dat.double = false;

      array.push(Object.assign(new Legend, dat));
      index++;
    }

    this.data1_510 = Object.assign(new Array, array);
    DEBUG && console.log(this.data1_510);
    ///////////////////////////////////// legend //////////////////////////////////////

    this.createBar('barChart20', 'Operaciones', this.impAux_510, color1, '#5A95F0', 'rgba(0, 0, 0, 0.1)', 1, widthBar, this.clave_510, this.clave_510);
  }

  t510_ObtenerOperacionesPorContribucion(data: any) {
    DEBUG && console.log(data);
    this.impAux_510 = [];
    this.imp_510 = [];
    this.clave_510 = [];

    for (let item of data) {
      this.imp_510.push(item);
      this.impAux_510.push(item.totalI);
    }
    for (let item of this.imp_510) {
      this.clave_510.push(item.clave);
    }

    let color1 = new Array(this.imp_510.length).fill('#E06188');

    try {
      let canvasDiv = document.querySelector('#barChart21Div');
      canvasDiv.removeChild(document.querySelector('#barChart21'));
      document.querySelector('#barChart21Div').insertAdjacentHTML('afterbegin', '<canvas id="barChart21" width="100%"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    let widthBar = 0;
    if (this.impAux_510.length > 1) {
      widthBar = 0.7;
    } else {
      widthBar = 0.25;
    }

    ///////////////////////////////////// legend //////////////////////////////////////
    let index = 0;
    let array = [];
    let dat = new Legend();
    for(let item of this.impAux_510){
      dat.reset();
      dat.titulo = this.clave_510[index];
      dat.dataDefault = item;
      dat.colorDefault = color1[0];
      dat.nameDefault = 'Op:';
      dat.double = false;

      array.push(Object.assign(new Legend, dat));
      index++;
    }

    this.data2_510 = Object.assign(new Array, array);
    DEBUG && console.log(this.data2_510);
    ///////////////////////////////////// legend //////////////////////////////////////

    this.createBar('barChart21', 'Operaciones', this.impAux_510, color1, '#5A95F0', 'rgba(0, 0, 0, 0.1)', 1, widthBar, this.clave_510, this.clave_510);
  }

  t510_ObtenerOperacionesPorClave(data: any) {
    DEBUG && console.log(data);
    this.impAux_510 = [];
    this.imp_510 = [];
    this.clave_510 = [];

    for (let item of data) {
      this.imp_510.push(item);
      this.impAux_510.push(item.totalD);
    }
    for (let item of this.imp_510) {
      this.clave_510.push(item.clave);
    }

    let color1 = new Array(this.imp_510.length).fill('#5793F3');

    try {
      let canvasDiv = document.querySelector('#barChart22Div');
      canvasDiv.removeChild(document.querySelector('#barChart22'));
      document.querySelector('#barChart22Div').insertAdjacentHTML('afterbegin', '<canvas id="barChart22" width="100%"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    let widthBar = 0;
    if (this.impAux_510.length > 1) {
      widthBar = 0.7;
    } else {
      widthBar = 0.25;
    }

    ///////////////////////////////////// legend //////////////////////////////////////
    let index = 0;
    let array = [];
    let dat = new Legend();
    for(let item of this.impAux_510){
      dat.reset();
      dat.titulo = this.clave_510[index];
      dat.dataDefault = item;
      dat.colorDefault = color1[0];
      dat.nameDefault = 'Op:';
      dat.double = false;

      array.push(Object.assign(new Legend, dat));
      index++;
    }

    this.data3_510 = Object.assign(new Array, array);
    DEBUG && console.log(this.data3_510);
    ///////////////////////////////////// legend //////////////////////////////////////

    this.createBar('barChart22', 'Operaciones', this.impAux_510, color1, '#5A95F0', 'rgba(0, 0, 0, 0.1)', 1, widthBar, this.clave_510, this.clave_510);
  }

  t510_ObtenerOperacionesPorAgente(data: any) {
    DEBUG && console.log(data);
    this.impAux_510 = [];
    this.imp_510 = [];
    this.clave_510 = [];

    for (let item of data) {
      this.imp_510.push(item);
      this.impAux_510.push(item.totalI);
    }
    for (let item of this.imp_510) {
      this.clave_510.push(item.clave);
    }

    let color1 = new Array(this.imp_510.length).fill('#E06188');

    try {
      let canvasDiv = document.querySelector('#barChart23Div');
      canvasDiv.removeChild(document.querySelector('#barChart23'));
      document.querySelector('#barChart23Div').insertAdjacentHTML('afterbegin', '<canvas id="barChart23" width="100%"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    let widthBar = 0;
    if (this.impAux_510.length > 1) {
      widthBar = 0.7;
    } else {
      widthBar = 0.25;
    }

    ///////////////////////////////////// legend //////////////////////////////////////
    let index = 0;
    let array = [];
    let dat = new Legend();
    for(let item of this.impAux_510){
      dat.reset();
      dat.titulo = this.clave_510[index];
      dat.dataDefault = item;
      dat.colorDefault = color1[0];
      dat.nameDefault = 'Op:';
      dat.double = false;

      array.push(Object.assign(new Legend, dat));
      index++;
    }

    this.data4_510 = Object.assign(new Array, array);
    DEBUG && console.log(this.data4_510);
    ///////////////////////////////////// legend //////////////////////////////////////

    this.createBar('barChart23', 'Operaciones', this.impAux_510, color1, '#5A95F0', 'rgba(0, 0, 0, 0.1)', 1, widthBar, this.clave_510, this.clave_510);
  }

  /* ------------------------------------------------------------ /510 graficas ------------------------------------------------------------ */


  /* ------------------------------------------------------------ 701 graficas ------------------------------------------------------------ */

  t701_ObtenerOperacionesPorAduana(data: any) {
    DEBUG && console.log(data);
    this.impAux_701 = [];
    this.imp_701 = [];
    this.clave_701 = [];

    for (let item of data) {
      this.imp_701.push(item);
      this.impAux_701.push(item.totalI);
    }
    for (let item of this.imp_701) {
      this.clave_701.push(item.clave);
    }

    let color1 = new Array(this.imp_701.length).fill('#5793F3');

    try {
      let canvasDiv = document.querySelector('#barChart24Div');
      canvasDiv.removeChild(document.querySelector('#barChart24'));
      document.querySelector('#barChart24Div').insertAdjacentHTML('afterbegin', '<canvas id="barChart24" width="100%"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    let widthBar = 0;
    if (this.impAux_701.length > 1) {
      widthBar = 0.7;
    } else {
      widthBar = 0.25;
    }

    ///////////////////////////////////// legend //////////////////////////////////////
    let index = 0;
    let array = [];
    let dat = new Legend();
    for(let item of this.impAux_701){
      dat.reset();
      dat.titulo = this.clave_701[index];
      dat.dataDefault = item;
      dat.colorDefault = color1[0];
      dat.nameDefault = 'Op:';
      dat.double = false;

      array.push(Object.assign(new Legend, dat));
      index++;
    }

    this.data1_701 = Object.assign(new Array, array);
    DEBUG && console.log(this.data1_701);
    ///////////////////////////////////// legend //////////////////////////////////////

    this.createBar('barChart24', 'Operaciones', this.impAux_701, color1, '#5A95F0', 'rgba(0, 0, 0, 0.1)', 1, widthBar, this.clave_701, this.clave_701);
  }
  t701_ObtenerOperacionesPorPatente(data: any) {
    DEBUG && console.log(data);
    this.impAux_701 = [];
    this.imp_701 = [];
    this.clave_701 = [];

    for (let item of data) {
      this.imp_701.push(item);
      this.impAux_701.push(item.totalI);
    }
    for (let item of this.imp_701) {
      this.clave_701.push(item.clave);
    }

    let color1 = new Array(this.imp_701.length).fill('#E06188');

    try {
      let canvasDiv = document.querySelector('#barChart25Div');
      canvasDiv.removeChild(document.querySelector('#barChart25'));
      document.querySelector('#barChart25Div').insertAdjacentHTML('afterbegin', '<canvas id="barChart25" width="100%"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    let widthBar = 0;
    if (this.impAux_701.length > 1) {
      widthBar = 0.7;
    } else {
      widthBar = 0.25;
    }

    ///////////////////////////////////// legend //////////////////////////////////////
    let index = 0;
    let array = [];
    let dat = new Legend();
    for(let item of this.impAux_701){
      dat.reset();
      dat.titulo = this.clave_701[index];
      dat.dataDefault = item;
      dat.colorDefault = color1[0];
      dat.nameDefault = 'Op:';
      dat.double = false;

      array.push(Object.assign(new Legend, dat));
      index++;
    }

    this.data2_701 = Object.assign(new Array, array);
    DEBUG && console.log(this.data2_701);
    ///////////////////////////////////// legend //////////////////////////////////////

    this.createBar('barChart25', 'Operaciones', this.impAux_701, color1, '#5A95F0', 'rgba(0, 0, 0, 0.1)', 1, widthBar, this.clave_701, this.clave_701);
  }
  t701_ObtenerOperacionesPorClaveDocumento(data: any) {
    DEBUG && console.log(data);
    this.impAux_701 = [];
    this.imp_701 = [];
    this.clave_701 = [];

    for (let item of data) {
      this.imp_701.push(item);
      this.impAux_701.push(item.totalI);
    }
    for (let item of this.imp_701) {
      this.clave_701.push(item.claveStr);
    }

    let color1 = new Array(this.imp_701.length).fill('#5793F3');

    try {
      let canvasDiv = document.querySelector('#barChart26Div');
      canvasDiv.removeChild(document.querySelector('#barChart26'));
      document.querySelector('#barChart26Div').insertAdjacentHTML('afterbegin', '<canvas id="barChart26" width="100%"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    let widthBar = 0;
    if (this.impAux_701.length > 1) {
      widthBar = 0.7;
    } else {
      widthBar = 0.25;
    }

    ///////////////////////////////////// legend //////////////////////////////////////
    let index = 0;
    let array = [];
    let dat = new Legend();
    for(let item of this.impAux_701){
      dat.reset();
      dat.titulo = this.clave_701[index];
      dat.dataDefault = item;
      dat.colorDefault = color1[0];
      dat.nameDefault = 'Op:';
      dat.double = false;

      array.push(Object.assign(new Legend, dat));
      index++;
    }

    this.data3_701 = Object.assign(new Array, array);
    DEBUG && console.log(this.data3_701);
    ///////////////////////////////////// legend //////////////////////////////////////

    this.createBar('barChart26', 'Operaciones', this.impAux_701, color1, '#5A95F0', 'rgba(0, 0, 0, 0.1)', 1, widthBar, this.clave_701, this.clave_701);
  }
  /* ------------------------------------------------------------ /701 graficas ------------------------------------------------------------ */


  /* ------------------------------------------------------------ 702 graficas ------------------------------------------------------------ */

  t702_ObtenerOperacionesPorAduana(data: any) {
    DEBUG && console.log(data);
    this.impAux_702 = [];
    this.imp_702 = [];
    this.clave_702 = [];

    for (let item of data) {
      this.imp_702.push(item);
      this.impAux_702.push(item.totalI);
    }
    for (let item of this.imp_702) {
      this.clave_702.push(item.clave);
    }

    let color1 = new Array(this.imp_702.length).fill('#5793F3');

    try {
      let canvasDiv = document.querySelector('#barChart27Div');
      canvasDiv.removeChild(document.querySelector('#barChart27'));
      document.querySelector('#barChart27Div').insertAdjacentHTML('afterbegin', '<canvas id="barChart27" width="100%"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    let widthBar = 0;
    if (this.impAux_702.length > 1) {
      widthBar = 0.7;
    } else {
      widthBar = 0.25;
    }

    ///////////////////////////////////// legend //////////////////////////////////////
    let index = 0;
    let array = [];
    let dat = new Legend();
    for(let item of this.impAux_702){
      dat.reset();
      dat.titulo = this.clave_702[index];
      dat.dataDefault = item;
      dat.colorDefault = color1[0];
      dat.nameDefault = 'Op:';
      dat.double = false;

      array.push(Object.assign(new Legend, dat));
      index++;
    }

    this.data1_702 = Object.assign(new Array, array);
    DEBUG && console.log(this.data1_702);
    ///////////////////////////////////// legend //////////////////////////////////////

    this.createBar('barChart27', 'Operaciones', this.impAux_702, color1, '#5A95F0', 'rgba(0, 0, 0, 0.1)', 1, widthBar, this.clave_702, this.clave_702);
  }
  t702_ObtenerOperacionesPorPatente(data: any) {
    DEBUG && console.log(data);
    this.impAux_702 = [];
    this.imp_702 = [];
    this.clave_702 = [];

    for (let item of data) {
      this.imp_702.push(item);
      this.impAux_702.push(item.totalI);
    }
    for (let item of this.imp_702) {
      this.clave_702.push(item.clave);
    }

    let color1 = new Array(this.imp_702.length).fill('#E06188');

    try {
      let canvasDiv = document.querySelector('#barChart28Div');
      canvasDiv.removeChild(document.querySelector('#barChart28'));
      document.querySelector('#barChart28Div').insertAdjacentHTML('afterbegin', '<canvas id="barChart28" width="100%"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    let widthBar = 0;
    if (this.impAux_702.length > 1) {
      widthBar = 0.7;
    } else {
      widthBar = 0.25;
    }

    ///////////////////////////////////// legend //////////////////////////////////////
    let index = 0;
    let array = [];
    let dat = new Legend();
    for(let item of this.impAux_702){
      dat.reset();
      dat.titulo = this.clave_702[index];
      dat.dataDefault = item;
      dat.colorDefault = color1[0];
      dat.nameDefault = 'Op:';
      dat.double = false;

      array.push(Object.assign(new Legend, dat));
      index++;
    }

    this.data2_702 = Object.assign(new Array, array);
    DEBUG && console.log(this.data2_702);
    ///////////////////////////////////// legend //////////////////////////////////////

    this.createBar('barChart28', 'Operaciones', this.impAux_702, color1, '#5A95F0', 'rgba(0, 0, 0, 0.1)', 1, widthBar, this.clave_702, this.clave_702);
  }
  t702_ObtenerTotalPorFormaPago(data: any) {
    DEBUG && console.log(data);
    this.impAux_702 = [];
    this.imp_702 = [];
    this.clave_702 = [];

    for (let item of data) {
      this.imp_702.push(item);
      this.impAux_702.push(item.totalI);
    }
    for (let item of this.imp_702) {
      this.clave_702.push(item.clave);
    }

    let color1 = new Array(this.imp_702.length).fill('#2ABCF4');

    try {
      let canvasDiv = document.querySelector('#barChart29Div');
      canvasDiv.removeChild(document.querySelector('#barChart29'));
      document.querySelector('#barChart29Div').insertAdjacentHTML('afterbegin', '<canvas id="barChart29" width="100%"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    let widthBar = 0;
    if (this.impAux_702.length > 1) {
      widthBar = 0.7;
    } else {
      widthBar = 0.25;
    }

    ///////////////////////////////////// legend //////////////////////////////////////
    let index = 0;
    let array = [];
    let dat = new Legend();
    for(let item of this.impAux_702){
      dat.reset();
      dat.titulo = this.clave_702[index];
      dat.dataDefault = item;
      dat.colorDefault = color1[0];
      dat.nameDefault = 'Ef:';
      dat.double = false;

      array.push(Object.assign(new Legend, dat));
      index++;
    }

    this.data3_702 = Object.assign(new Array, array);
    DEBUG && console.log(this.data3_702);
    ///////////////////////////////////// legend //////////////////////////////////////

    this.createBar('barChart29', 'Efectivo', this.impAux_702, color1, '#5A95F0', 'rgba(0, 0, 0, 0.1)', 1, widthBar, this.clave_702, this.clave_702);
  }
  /* ------------------------------------------------------------ /702 graficas ------------------------------------------------------------ */

  /* ------------------------------------------------------------ 554 graficas ------------------------------------------------------------ */

  t554_ObtenerOperacionesPorAduana(data: any) {
    DEBUG && console.log(data);
    this.impAux_554 = [];
    this.imp_554 = [];
    this.clave_554 = [];

    for (let item of data) {
      this.imp_554.push(item);
      this.impAux_554.push(item.totalI);
    }
    for (let item of this.imp_554) {
      this.clave_554.push(item.clave);
    }

    let color1 = new Array(this.imp_554.length).fill('#5793F3');

    try {
      let canvasDiv = document.querySelector('#barChart30Div');
      canvasDiv.removeChild(document.querySelector('#barChart30'));
      document.querySelector('#barChart30Div').insertAdjacentHTML('afterbegin', '<canvas id="barChart30" width="100%"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    let widthBar = 0;
    if (this.impAux_554.length > 1) {
      widthBar = 0.7;
    } else {
      widthBar = 0.25;
    }

    ///////////////////////////////////// legend //////////////////////////////////////
    let index = 0;
    let array = [];
    let dat = new Legend();
    for(let item of this.impAux_554){
      dat.reset();
      dat.titulo = this.clave_554[index];
      dat.dataDefault = item;
      dat.colorDefault = color1[0];
      dat.nameDefault = 'Op:';
      dat.double = false;

      array.push(Object.assign(new Legend, dat));
      index++;
    }

    this.data1_554 = Object.assign(new Array, array);
    DEBUG && console.log(this.data1_554);
    ///////////////////////////////////// legend //////////////////////////////////////

    this.createBar('barChart30', 'Operaciones', this.impAux_554, color1, '#5A95F0', 'rgba(0, 0, 0, 0.1)', 1, widthBar, this.clave_554, this.clave_554);
  }
  t554_ObtenerOperacionesPorAgente(data: any) {
    DEBUG && console.log(data);
    this.impAux_554 = [];
    this.imp_554 = [];
    this.clave_554 = [];

    for (let item of data) {
      this.imp_554.push(item);
      this.impAux_554.push(item.totalI);
    }
    for (let item of this.imp_554) {
      this.clave_554.push(item.clave);
    }

    let color1 = new Array(this.imp_554.length).fill('#E06188');

    try {
      let canvasDiv = document.querySelector('#barChart31Div');
      canvasDiv.removeChild(document.querySelector('#barChart31'));
      document.querySelector('#barChart31Div').insertAdjacentHTML('afterbegin', '<canvas id="barChart31" width="100%"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    let widthBar = 0;
    if (this.impAux_554.length > 1) {
      widthBar = 0.7;
    } else {
      widthBar = 0.25;
    }

    ///////////////////////////////////// legend //////////////////////////////////////
    let index = 0;
    let array = [];
    let dat = new Legend();
    for(let item of this.impAux_554){
      dat.reset();
      dat.titulo = this.clave_554[index];
      dat.dataDefault = item;
      dat.colorDefault = color1[0];
      dat.nameDefault = 'Op:';
      dat.double = false;

      array.push(Object.assign(new Legend, dat));
      index++;
    }

    this.data2_554 = Object.assign(new Array, array);
    DEBUG && console.log(this.data2_554);
    ///////////////////////////////////// legend //////////////////////////////////////

    this.createBar('barChart31', 'Operaciones', this.impAux_554, color1, '#5A95F0', 'rgba(0, 0, 0, 0.1)', 1, widthBar, this.clave_554, this.clave_554);
  }
  t554_ObtenerOperacionesPorClave(data: any) {
    DEBUG && console.log(data);
    this.impAux_554 = [];
    this.imp_554 = [];
    this.clave_554 = [];

    for (let item of data) {
      this.imp_554.push(item);
      this.impAux_554.push(item.totalI);
    }
    for (let item of this.imp_554) {
      this.clave_554.push(item.claveStr);
    }

    let color1 = new Array(this.imp_554.length).fill('#5793F3');

    try {
      let canvasDiv = document.querySelector('#barChart32Div');
      canvasDiv.removeChild(document.querySelector('#barChart32'));
      document.querySelector('#barChart32Div').insertAdjacentHTML('afterbegin', '<canvas id="barChart32" width="100%"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    let widthBar = 0;
    if (this.impAux_554.length > 1) {
      widthBar = 0.7;
    } else {
      widthBar = 0.25;
    }

    ///////////////////////////////////// legend //////////////////////////////////////
    let index = 0;
    let array = [];
    let dat = new Legend();
    for(let item of this.impAux_554){
      dat.reset();
      dat.titulo = this.clave_554[index];
      dat.dataDefault = item;
      dat.colorDefault = color1[0];
      dat.nameDefault = 'Op:';
      dat.double = false;

      array.push(Object.assign(new Legend, dat));
      index++;
    }

    this.data3_554 = Object.assign(new Array, array);
    DEBUG && console.log(this.data3_554);
    ///////////////////////////////////// legend //////////////////////////////////////

    this.createBar('barChart32', 'Operaciones', this.impAux_554, color1, '#5A95F0', 'rgba(0, 0, 0, 0.1)', 1, widthBar, this.clave_554, this.clave_554);
  }
  /* ------------------------------------------------------------ /554 graficas ------------------------------------------------------------ */

  /* ------------------------------------------------------------ 556 graficas ------------------------------------------------------------ */

  t556_ObtenerOperacionesPorAduana(data: any) {
    DEBUG && console.log(data);
    this.impAux_556 = [];
    this.imp_556 = [];
    this.clave_556 = [];

    for (let item of data) {
      this.imp_556.push(item);
      this.impAux_556.push(item.totalI);
    }
    for (let item of this.imp_556) {
      this.clave_556.push(item.clave);
    }

    let color1 = new Array(this.imp_556.length).fill('#5793F3');

    try {
      let canvasDiv = document.querySelector('#barChart33Div');
      canvasDiv.removeChild(document.querySelector('#barChart33'));
      document.querySelector('#barChart33Div').insertAdjacentHTML('afterbegin', '<canvas id="barChart33" width="100%"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    let widthBar = 0;
    if (this.impAux_556.length > 1) {
      widthBar = 0.7;
    } else {
      widthBar = 0.25;
    }

    ///////////////////////////////////// legend //////////////////////////////////////
    let index = 0;
    let array = [];
    let dat = new Legend();
    for(let item of this.impAux_556){
      dat.reset();
      dat.titulo = this.clave_556[index];
      dat.dataDefault = item;
      dat.colorDefault = color1[0];
      dat.nameDefault = 'Op:';
      dat.double = false;

      array.push(Object.assign(new Legend, dat));
      index++;
    }

    this.data1_556 = Object.assign(new Array, array);
    DEBUG && console.log(this.data1_556);
    ///////////////////////////////////// legend //////////////////////////////////////

    this.createBar('barChart33', 'Operaciones', this.impAux_556, color1, '#5A95F0', 'rgba(0, 0, 0, 0.1)', 1, widthBar, this.clave_556, this.clave_556);
  }
  t556_ObtenerOperacionesPorAgente(data: any) {
    DEBUG && console.log(data);
    this.impAux_556 = [];
    this.imp_556 = [];
    this.clave_556 = [];

    for (let item of data) {
      this.imp_556.push(item);
      this.impAux_556.push(item.totalI);
    }
    for (let item of this.imp_556) {
      this.clave_556.push(item.clave);
    }

    let color1 = new Array(this.imp_556.length).fill('#E06188');

    try {
      let canvasDiv = document.querySelector('#barChart34Div');
      canvasDiv.removeChild(document.querySelector('#barChart34'));
      document.querySelector('#barChart34Div').insertAdjacentHTML('afterbegin', '<canvas id="barChart34" width="100%"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    let widthBar = 0;
    if (this.impAux_556.length > 1) {
      widthBar = 0.7;
    } else {
      widthBar = 0.25;
    }

    ///////////////////////////////////// legend //////////////////////////////////////
    let index = 0;
    let array = [];
    let dat = new Legend();
    for(let item of this.impAux_556){
      dat.reset();
      dat.titulo = this.clave_556[index];
      dat.dataDefault = item;
      dat.colorDefault = color1[0];
      dat.nameDefault = 'Op:';
      dat.double = false;

      array.push(Object.assign(new Legend, dat));
      index++;
    }

    this.data2_556 = Object.assign(new Array, array);
    DEBUG && console.log(this.data2_556);
    ///////////////////////////////////// legend //////////////////////////////////////

    this.createBar('barChart34', 'Operaciones', this.impAux_556, color1, '#5A95F0', 'rgba(0, 0, 0, 0.1)', 1, widthBar, this.clave_556, this.clave_556);
  }
  t556_ObtenerOperacionesPorClave(data: any) {
    DEBUG && console.log(data);
    this.impAux_556 = [];
    this.imp_556 = [];
    this.clave_556 = [];

    for (let item of data) {
      this.imp_556.push(item);
      this.impAux_556.push(item.totalI);
    }
    for (let item of this.imp_556) {
      this.clave_556.push(item.clave);
    }

    let color1 = new Array(this.imp_556.length).fill('#5793F3');

    try {
      let canvasDiv = document.querySelector('#barChart35Div');
      canvasDiv.removeChild(document.querySelector('#barChart35'));
      document.querySelector('#barChart35Div').insertAdjacentHTML('afterbegin', '<canvas id="barChart35" width="100%"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    let widthBar = 0;
    if (this.impAux_556.length > 1) {
      widthBar = 0.7;
    } else {
      widthBar = 0.25;
    }

    ///////////////////////////////////// legend //////////////////////////////////////
    let index = 0;
    let array = [];
    let dat = new Legend();
    for(let item of this.impAux_556){
      dat.reset();
      dat.titulo = this.clave_556[index];
      dat.dataDefault = item;
      dat.colorDefault = color1[0];
      dat.nameDefault = 'Op:';
      dat.double = false;

      array.push(Object.assign(new Legend, dat));
      index++;
    }

    this.data3_556 = Object.assign(new Array, array);
    DEBUG && console.log(this.data3_556);
    ///////////////////////////////////// legend //////////////////////////////////////

    this.createBar('barChart35', 'Operaciones', this.impAux_556, color1, '#5A95F0', 'rgba(0, 0, 0, 0.1)', 1, widthBar, this.clave_556, this.clave_556);
  }

  t556_ObtenerOperacionesTasaContribucion(data: any) {
    DEBUG && console.log(data);
    this.impAux_556 = [];
    this.imp_556 = [];
    this.clave_556 = [];

    for (let item of data) {
      this.imp_556.push(item);
      this.impAux_556.push(item.totalI);
    }
    for (let item of this.imp_556) {
      this.clave_556.push(item.claveStr);
    }

    let color1 = [];
    for (let item of this.imp_556) {
      if (item.tipo == '3') {
        color1.push('#72D34F');
      } else {
        color1.push('#A85FD6');
      }
    }

    try {
      let canvasDiv = document.querySelector('#barChart36Div');
      canvasDiv.removeChild(document.querySelector('#barChart36'));
      document.querySelector('#barChart36Div').insertAdjacentHTML('afterbegin', '<canvas id="barChart36" width="100%"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    let widthBar = 0;
    if (this.impAux_556.length > 1) {
      widthBar = 0.7;
    } else {
      widthBar = 0.25;
    }

    ///////////////////////////////////// legend //////////////////////////////////////
    let index = 0;
    let array = [];
    let dat = new Legend();
    for(let item of this.impAux_556){
      dat.reset();
      dat.titulo = this.clave_556[index];
      dat.dataDefault = item;
      dat.colorDefault = color1[index];
      dat.nameDefault = color1[index] == '#72D34F' ? 'IVA:' : 'IGIE:';
      dat.double = false;

      array.push(Object.assign(new Legend, dat));
      index++;
    }

    this.data4_556 = Object.assign(new Array, array);
    DEBUG && console.log(this.data4_556);
    ///////////////////////////////////// legend //////////////////////////////////////

    this.createBar('barChart36', '', this.impAux_556, color1, '#5A95F0', 'rgba(0, 0, 0, 0.1)', 1, widthBar, this.clave_556, this.clave_556);

  }

  t556_ObtenerOperacionesTipoTasa(data: any) {
    DEBUG && console.log(data);
    this.igieAux_556 = [];
    this.ivaAux_556 = [];
    this.iva_556 = [];
    this.igie_556 = [];
    this.claveStacked_556 = [];

    for (let item of data) {
      if (item.tipo == '3') {
        this.iva_556.push(item);
        this.ivaAux_556.push(item.totalI);
      } else {
        this.igie_556.push(item);
      }
    }
    this.igieAux_556 = new Array(this.iva_556.length).fill(0);
    let i = 0;
    for (let item of this.iva_556) {
      for (let item2 of this.igie_556) {
        if (item.clave == item2.clave) {
          this.igieAux_556[i] = item2.totalI;
        }
      }
      i++;
    }
    let claveAux = [];
    for (let item of this.iva_556) {
      this.claveStacked_556.push(item.clave);
      claveAux.push(item.clave);
    }

    let color1 = new Array(this.iva_556.length).fill('#72D34F');
    let color2 = new Array(this.iva_556.length).fill('#A85FD6');

    try {
      let canvasDiv = document.querySelector('#barStackedChart8Div');
      canvasDiv.removeChild(document.querySelector('#barStackedChart8'));
      document.querySelector('#barStackedChart8Div').insertAdjacentHTML('afterbegin', '<canvas id="barStackedChart8" width="100%"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    let widthBar = 0;
    if (this.ivaAux_556.length > 1) {
      widthBar = 0.7;
    } else {
      widthBar = 0.25;
    }

    //claveAux = this.tableroHttp.getNombreTransportes(claveAux);
    //this.clave_507PedimentoPorClave = this.tableroHttp.getIconosTransportes(this.clave_507PedimentoPorClave);
    DEBUG && console.log(this.claveStacked_556);
    DEBUG && console.log(claveAux);

    ///////////////////////////////////// legend //////////////////////////////////////
    let index = 0;
    let array = [];
    let dat = new Legend();
    for(let item of this.ivaAux_556){
      dat.reset();
      dat.titulo = this.claveStacked_556[index];
      dat.dataDefault = item;
      dat.dataDouble = this.igieAux_556[index];
      dat.colorDefault = color1[0];
      dat.nameDefault = 'IVA:';
      dat.nameDouble = 'IGIE:';
      dat.colorDouble = color2[0];
      dat.double = true;

      array.push(Object.assign(new Legend, dat));
      index++;
    }

    this.data5_556 = Object.assign(new Array, array);
    DEBUG && console.log(this.data5_556);
    ///////////////////////////////////// legend //////////////////////////////////////

    this.createBarStacked('barStackedChart8', ['IVA', 'IGIE'], this.ivaAux_556, this.igieAux_556, color1, color2, '#5A95F0', 'rgba(0, 0, 0, 0.1)', 1, widthBar, this.claveStacked_556, claveAux, false);

  }

  /* ------------------------------------------------------------ /556 graficas ------------------------------------------------------------ */

  /* ------------------------------------------------------------ 557 graficas ------------------------------------------------------------ */

  t557_obtenerOperacionesPorAduana(data: any) {
    DEBUG && console.log(data);
    this.impAux_557 = [];
    this.imp_557 = [];
    this.clave_557 = [];

    for (let item of data) {
      this.imp_557.push(item);
      this.impAux_557.push(item.totalI);
    }
    for (let item of this.imp_557) {
      this.clave_557.push(item.tipo);
    }

    let color1 = new Array(this.imp_557.length).fill('#5793F3');

    try {
      let canvasDiv = document.querySelector('#barChart37Div');
      canvasDiv.removeChild(document.querySelector('#barChart37'));
      document.querySelector('#barChart37Div').insertAdjacentHTML('afterbegin', '<canvas id="barChart37" width="100%"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    let widthBar = 0;
    if (this.impAux_557.length > 1) {
      widthBar = 0.7;
    } else {
      widthBar = 0.25;
    }

    ///////////////////////////////////// legend //////////////////////////////////////
    let index = 0;
    let array = [];
    let dat = new Legend();
    for(let item of this.impAux_557){
      dat.reset();
      dat.titulo = this.clave_557[index];
      dat.dataDefault = item;
      dat.colorDefault = color1[index];
      dat.nameDefault = 'Op:';
      dat.double = false;

      array.push(Object.assign(new Legend, dat));
      index++;
    }

    this.data1_557 = Object.assign(new Array, array);
    DEBUG && console.log(this.data1_557);
    ///////////////////////////////////// legend //////////////////////////////////////

    this.createBar('barChart37', 'Operaciones', this.impAux_557, color1, '#5A95F0', 'rgba(0, 0, 0, 0.1)', 1, widthBar, this.clave_557, this.clave_557);
  }

  t557_obtenerOperacionesPorAgenteAduanal(data: any) {
    DEBUG && console.log(data);
    this.impAux_557 = [];
    this.imp_557 = [];
    this.clave_557 = [];

    for (let item of data) {
      this.imp_557.push(item);
      this.impAux_557.push(item.totalI);
    }
    for (let item of this.imp_557) {
      this.clave_557.push(item.tipo);
    }

    let color1 = new Array(this.imp_557.length).fill('#E06188');

    try {
      let canvasDiv = document.querySelector('#barChart38Div');
      canvasDiv.removeChild(document.querySelector('#barChart38'));
      document.querySelector('#barChart38Div').insertAdjacentHTML('afterbegin', '<canvas id="barChart38" width="100%"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    let widthBar = 0;
    if (this.impAux_557.length > 1) {
      widthBar = 0.7;
    } else {
      widthBar = 0.25;
    }

    ///////////////////////////////////// legend //////////////////////////////////////
    let index = 0;
    let array = [];
    let dat = new Legend();
    for(let item of this.impAux_557){
      dat.reset();
      dat.titulo = this.clave_557[index];
      dat.dataDefault = item;
      dat.colorDefault = color1[index];
      dat.nameDefault = 'Op:';
      dat.double = false;

      array.push(Object.assign(new Legend, dat));
      index++;
    }

    this.data2_557 = Object.assign(new Array, array);
    DEBUG && console.log(this.data2_557);
    ///////////////////////////////////// legend //////////////////////////////////////

    this.createBar('barChart38', 'Operaciones', this.impAux_557, color1, '#5A95F0', 'rgba(0, 0, 0, 0.1)', 1, widthBar, this.clave_557, this.clave_557);
  }

  t557_obtenerImportePagoPorTipoContribucion(data: any) {
    DEBUG && console.log('@t557_obtenerImportePagoPorTipoContribucion: Entre');
    DEBUG && console.log(data);
    this.impAux_557 = [];
    this.imp_557 = [];
    this.clave_557 = [];

    for (let item of data) {
      this.imp_557.push(item);
      this.impAux_557.push(item.totalD);
    }
    for (let item of this.imp_557) {
      this.clave_557.push(item.clave);
    }

    let color1 = new Array(this.imp_557.length).fill('#2ABCF4');

    try {
      let canvasDiv = document.querySelector('#barChart39Div');
      canvasDiv.removeChild(document.querySelector('#barChart39'));
      document.querySelector('#barChart39Div').insertAdjacentHTML('afterbegin', '<canvas id="barChart39" width="100%"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    let widthBar = 0;
    if (this.impAux_557.length > 1) {
      widthBar = 0.7;
    } else {
      widthBar = 0.25;
    }

    ///////////////////////////////////// legend //////////////////////////////////////
    let index = 0;
    let array = [];
    let dat = new Legend();
    for(let item of this.impAux_557){
      dat.reset();
      dat.titulo = this.clave_557[index];
      dat.dataDefault = item;
      dat.colorDefault = color1[index];
      dat.nameDefault = 'Ef:';
      dat.double = false;

      array.push(Object.assign(new Legend, dat));
      index++;
    }

    this.data3_557 = Object.assign(new Array, array);
    DEBUG && console.log(this.data3_557);
    ///////////////////////////////////// legend //////////////////////////////////////

    this.createBar('barChart39', 'Efectivo', this.impAux_557, color1, '#5A95F0', 'rgba(0, 0, 0, 0.1)', 1, widthBar, this.clave_557, this.clave_557);
  }

  t557_obtenerOperacionesPorFormaDePago(data: any) {
    DEBUG && console.log('@t557_obtenerOperacionesPorFormaDePago: Entre');
    DEBUG && console.log(data);
    this.impAux_557 = [];
    this.imp_557 = [];
    this.clave_557 = [];

    for (let item of data) {
      this.imp_557.push(item);
      this.impAux_557.push(item.totalI);
    }
    for (let item of this.imp_557) {
      this.clave_557.push(item.clave);
    }

    let color1 = [];
    for (let item of this.imp_557) {
      if (item.clave == '3') {
        color1.push('#72D34F');
      } else {
        color1.push('#A85FD6');
      }
    }

    //let color1 = new Array(this.imp_557.length).fill('#75D157');

    try {
      let canvasDiv = document.querySelector('#barChart40Div');
      canvasDiv.removeChild(document.querySelector('#barChart40'));
      document.querySelector('#barChart40Div').insertAdjacentHTML('afterbegin', '<canvas id="barChart40" width="100%"></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    let widthBar = 0;
    if (this.impAux_557.length > 1) {
      widthBar = 0.7;
    } else {
      widthBar = 0.25;
    }

    ///////////////////////////////////// legend //////////////////////////////////////
    let index = 0;
    let array = [];
    let dat = new Legend();
    for(let item of this.impAux_557){
      dat.reset();
      dat.titulo = this.clave_557[index];
      dat.dataDefault = item;
      dat.colorDefault = color1[index];
      dat.nameDefault = color1[index] == '#72D34F' ? 'IVA:' : 'IGIE:';
      dat.double = false;

      array.push(Object.assign(new Legend, dat));
      index++;
    }

    this.data4_557 = Object.assign(new Array, array);
    DEBUG && console.log(this.data4_557);
    ///////////////////////////////////// legend //////////////////////////////////////

    this.createBar('barChart40', '', this.impAux_557, color1, '#5A95F0', 'rgba(0, 0, 0, 0.1)', 1, widthBar, this.clave_557, this.clave_557);
  }

  /* ------------------------------------------------------------ /557 graficas ------------------------------------------------------------ */


  /* ------------------------------------------------------------ /Servicios-------------------------------------------------------------- */


  /* ------------------------------------------------------------ Boton aplicar ------------------------------------------------------------ */
  async  apply() {
    if (await this.handleValidDates()) {
      DEBUG && console.log('@apply: Entre');
      this.parametros = new Parametros();
      let usuario: Usuario = new Usuario();
      let cliente: Cliente = new Cliente();
      this.parametros.tipo = 'Personalizado';
      this.parametros.finicio = this.date;/* '20180101'; */
      this.parametros.ffin = this.date2; /* '20181231'; */
      cliente.idCliente = SessionUser.getInstance().getIdClient();
      cliente.rfcActual = SessionUser.getInstance().getRfc();
      usuario.cliente = cliente;
      this.parametros.usuario = usuario;

      switch (this.seccion) {
        case 0:
          this.t501_obtenerDatos();
          break;
        case 1:
          this.t502_obtenerDatos();
          break;
        case 2:
          this.t503_obtenerDatos();
          break;
        case 3:
          this.t504_obtenerDatos();
          break;
        case 4:
          this.t505_obtenerDatos();
          break;
        case 5:
          this.t506_obtenerDatos();
          break;
        case 6:
          this.t507_obtenerDatos();
          break;
        case 7:
          this.t509_obtenerDatos();
          break;
        case 8:
          this.t510_obtenerDatos();
          break;
        case 9:
          this.t701_obtenerDatos();
          break;
        case 10:
          this.t702_obtenerDatos();
          break;
        case 11:
          this.t554_obtenerDatos();
          break;
        case 12:
          this.t556_obtenerDatos();
          break;
        case 13:
          this.t557_obtenerDatos();
          break;
      }

    }
  }

  /* ------------------------------------------------------------ /Boton aplicar ------------------------------------------------------------ */



  /* ------------------------------------------------------------Drop list-------------------------------------------------------------- */
  dropList($event) {
    DEBUG && console.log($event);
    this.seccion = $event.id;
    this.reset501();
    this.reset502();
    this.reset503();
    this.reset504();
    this.reset505();
    this.reset506();
    this.reset507();
    this.reset509();
    this.reset510();
    this.reset701();
    this.reset702();
    this.reset554();
    this.reset556();
    this.reset557();
    this.apply();
  }

  /* ------------------------------------------------------------ /Drop list-------------------------------------------------------------- */




  /* ------------------------------------------------------------ DatePicker-------------------------------------------------------------- */
  getFechaImpl($event) {
    //DEBUG && console.log($event);

  }

  /* ------------------------------------------------------------ /DatePicker-------------------------------------------------------------- */





  /*   ------------------------------------------------------------ resets ------------------------------------------------------------

    ------------------------------------------------------------ reset 501 ------------------------------------------------------------  */

  reset501() {
    this.operacionesPeriodo = '';
    this.total_OperacionesPeriodo = 0;
    this.importaciones_OperacionesPeriodo = 0;
    this.exportaciones_OperacionesPeriodo = 0;
    this.rectificaciones_OperacionesPeriodo = 0;

    this.minimo_tipoCambioPeriodo = 0;
    this.promedio_tipoCambioPeriodo = 0;
    this.maximo_tipoCambioPeriodo = 0;

    this.imp = [];
    this.impAux = [];
    this.exp = [];
    this.expAux = [];
    this.clave = [];


    this.imp_TransporteArribo = [];
    this.impAux_TransporteArribo = [];
    this.exp_TransporteArribo = [];
    this.expAux_TransporteArribo = [];
    this.clave_TransporteArribo = [];


    this.imp_PesoBrutoTransporteArribo = [];
    this.impAux_PesoBrutoTransporteArribo = [];
    this.exp_PesoBrutoTransporteArribo = [];
    this.expAux_PesoBrutoTransporteArribo = [];
    this.clave_PesoBrutoTransporteArribo = [];


    this.expAux_TipoPedimento = [];
    this.impAux_TipoPedimento = [];
    this.imp_TipoPedimento = [];
    this.exp_TipoPedimento = [];
    this.clave_TipoPedimento = [];
    this.total_TipoPedimento = 0;



    this.expAux_ClavePedimento = [];
    this.impAux_ClavePedimento = [];
    this.imp_ClavePedimento = [];
    this.exp_ClavePedimento = [];
    this.clave_ClavePedimento = [];


    this.impAux_TransporteSalida = [];
    this.imp_TransporteSalida = [];
    this.clave_TransporteSalida = [];


    this.impAux_ArriboSalida = [];
    this.imp_ArriboSalida = [];
    this.clave_ArriboSalida = [];


    this.total_IncrementablesYDeducibles = 0;
    this.totalAux_IncrementablesYDeducibles = [];
    this.fletes = 0;
    this.seguros = 0;
    this.embalajes = 0;
    this.incrementables = 0;
    this.deducibles = 0;


    this.impAux_DestinoMercancia = [];
    this.imp_DestinoMercancia = [];
    this.clave_DestinoMercancia = [];

    this.impAux_PorPatente = [];
    this.imp_PorPatente = [];
    this.clave_PorPatente = [];

    this.impAux_PorTipoPedimento = [];
    this.imp_PorTipoPedimento = [];
    this.clave_PorTipoPedimento = [];
  }

  /*   ------------------------------------------------------------ reset 502 ------------------------------------------------------------ */

  reset502() {
    this.impAux_502 = [];
    this.imp_502 = [];
    this.clave_502 = [];
  }

  /*   ------------------------------------------------------------ reset 503 ------------------------------------------------------------ */

  reset503() {
    this.guiasPorTipo = [];
    this.total_GuiasPorTipo = 0
    this.m_GuiasPorTipo = 0;
    this.h_GuiasPorTipo = 0;

    this.impAux_503 = [];
    this.imp_503 = [];
    this.clave_503 = [];
  }

  /*   ------------------------------------------------------------ reset 504 ------------------------------------------------------------ */

  reset504() {
    this.impAux_504 = [];
    this.imp_504 = [];
    this.clave_504 = [];
  }

  /*   ------------------------------------------------------------ reset 505 ------------------------------------------------------------ */

  reset505() {
    this.impAux_505 = [];
    this.imp_505 = [];
    this.clave_505 = [];
  }

  /*   ------------------------------------------------------------ reset 506 ------------------------------------------------------------ */
  reset506() {
    this.impAux_506 = [];
    this.imp_506 = [];
    this.clave_506 = [];
  }

  /*   ------------------------------------------------------------ reset 507 ------------------------------------------------------------ */
  reset507() {
    this.impAux_507 = [];
    this.imp_507 = [];
    this.clave_507 = [];

    this.expAux_507PedimentoPorClave = [];
    this.impAux_507PedimentoPorClave = [];
    this.imp_507PedimentoPorClave = [];
    this.exp_507PedimentoPorClave = [];
    this.clave_507PedimentoPorClave = [];
  }

  /*   ------------------------------------------------------------ reset 509 ------------------------------------------------------------ */
  reset509() {
    this.impAux_509 = [];
    this.imp_509 = [];
    this.clave_509 = [];

    this.expAux_PorImporteTasa = [];
    this.impAux_PorImporteTasa = [];
    this.imp_PorImporteTasa = [];
    this.exp_PorImporteTasa = [];
    this.clave_PorImporteTasa = [];
  }
  /*   ------------------------------------------------------------ reset 509 ------------------------------------------------------------ */
  reset510() {
    this.TipoPedimento = null;
    this.total_510TipoPedimento = 0;
    this.e_510TipoPedimento = 0;
    this.i_510TipoPedimento = 0;

    this.impAux_510 = [];
    this.imp_510 = [];
    this.clave_510 = [];
  }

  /*   ------------------------------------------------------------ reset 701 ------------------------------------------------------------ */
  reset701() {
    this.impAux_701 = [];
    this.imp_701 = [];
    this.clave_701 = [];
  }

  /*   ------------------------------------------------------------ reset 702 ------------------------------------------------------------ */
  reset702() {
    this.impAux_702 = [];
    this.imp_702 = [];
    this.clave_702 = [];
  }

  /*   ------------------------------------------------------------ reset 554 ------------------------------------------------------------ */
  reset554() {
    this.impAux_554 = [];
    this.imp_554 = [];
    this.clave_554 = [];
  }

  /*   ------------------------------------------------------------ reset 556 ------------------------------------------------------------ */
  reset556() {
    this.impAux_556 = [];
    this.imp_556 = [];
    this.clave_556 = [];

    this.igieAux_556 = [];
    this.ivaAux_556 = [];
    this.iva_556 = [];
    this.igie_556 = [];
    this.claveStacked_556 = [];
  }

  /*   ------------------------------------------------------------ reset 557 ------------------------------------------------------------ */
  reset557() {
    this.impAux_557 = [];
    this.imp_557 = [];
    this.clave_557 = [];
  }



  /* ************************** VALORES DE LA GRAFICA ****************************
  let element = 'doughnut1';
  let etiquetas = ['Importaciones', 'Exportaciones', 'Rectificaciones'];
  let valores = [100, 200, 150];
  let colores = ["#5792F3", "#FEC42D", "#DD4444",]

  ************************************************************************************
  */

  createDoughnut(element: any, etiquetas: any[], valores: any[], colores: any[], callback: any) {
    let oilCanvas = document.getElementById(element);

    Chart.defaults.global.defaultFontFamily = "Roboto Cn";
    Chart.defaults.global.defaultFontSize = 12;
    Chart.defaults.global.animation.duration = 3000;
    Chart.defaults.global.animation.easing = 'easeOutBounce';

    let oilData = {
      labels: etiquetas,
      datasets: [
        {
          data: valores,
          backgroundColor: colores,
          borderColor: "black",
          borderWidth: 0,
          hoverBorderWidth: 5,
          hoverBorderColor: colores,
          hoverBackgroundColor: colores
        }]
    };

    let chartOptions = {
      maintainAspectRatio: false,
      cutoutPercentage: 75,
      circumference: 2 * Math.PI,
      legend: {
        display: false,
        position: 'right',
        fontFamily: 'Roboto Cn',
        fontSize: '12px',
        padding: 20
      },
      tooltips: {
        display: true,
        backgroundColor: '#000',
        callbacks: {
          label: function (tooltipItem, data) {
            return callback !== true ? accounting.formatMoney(data.datasets[0].data[tooltipItem.index]) : accounting.formatNumber(data.datasets[0].data[tooltipItem.index]);
          }
        }
      }
    };

    let pieChart = new Chart(oilCanvas, {
      type: 'doughnut',
      data: oilData,
      options: chartOptions
    });
  }

  /* ************************** VALORES DE LA GRAFICA ****************************
    let element = 'lineChart1'
    let etiquetas = ['Mínimo', 'Promedio', 'Máximo'];
    let valores = [120, 190, 240];

    ************************************************************************************
  */

  createLineBar(element: any, etiquetas: any[], valores: any[]) {
    Chart.defaults.global.animation.duration = 3000;
    Chart.defaults.global.animation.easing = 'easeOutBounce';
    let ctx = document.getElementById(element);

    let myChart = new Chart(ctx, {
      type: 'line',
      data: {
        labels: etiquetas,
        datasets: [{
          label: '',
          data: valores,
          backgroundColor: 'transparent',
          borderColor: 'white',
          borderWidth: '0.75px',
          lineTension: 0,
          pointBackgroundColor: ['white', 'white', 'white'],
          borderDash: [0, 0, 0]
        }]
      },

      options: {
        maintainAspectRatio: false,
        tooltips: {
          mode: 'label',
          callbacks: {
            label: function (tooltipItem, data) {
              return accounting.formatMoney(tooltipItem.yLabel);
            }
          }
        },
        legend: {
          position: "none"
        },
        scales: {
          yAxes: [{
            ticks: {
              fontColor: "transparent",
              fontStyle: "bold",
              beginAtZero: true,
              padding: 20,
              //display:false
            },
            gridLines: {
              zeroLineColor: "#3B8EDE",
              color: "#3B8EDE",
              lineWidth: 2,
              drawBorder: false,
              zeroLineWidth: 2
            }
          }],
          xAxes: [{
            gridLines: {
              zeroLineColor: '#3B8EDE',
              color: '#3B8EDE',
              lineWidth: 2,
              drawTicks: true,
              display: false,
              drawBorder: true
            },
            ticks: {
              padding: 20,
              fontColor: "#3B8EDE",
              fontSize: 12
              //fontStyle: "bold",
              //display:false
            }
          }]
        }
      }
    });
  }


  /* ************************** VALORES DE LA GRAFICA ****************************
    let element = 'barStackedChart1'
    let etiquetas = ['Importaciones', 'Exportaciones'];
    let valores1 = [5427, 5243, 5014, 3933, 2326, 1687, 1271, 1038];
    let valores2 = [2000, 1443, 1514, 1933, 326, 487, 271, 638];
    let color1 = ['#5A95F0','#5A95F0','#5A95F0','#5A95F0','#5A95F0','#5A95F0','#5A95F0','#5A95F0',];
    let color2 = ['#FDC340','#FDC340','#FDC340','#FDC340','#FDC340','#FDC340','#FDC340','#FDC340',]
    let ylabelColor = #5A95F0 || transparent;
    let colorGrid = 'rgba(0, 0, 0, 0.1)' ||
    let lineWidthGrid = 1 || 2
    let barPercentage = 0.75 || 1.5
    let labelsY = ["800", "705", "096", "126", "050", "051", "105", "991"];
    let kg = format number .000
    ************************************************************************************
  */

  createBarStacked(element: any, etiquetas: any[], valores1: any[], valores2: any[], color1: any[], color2: any[], ylabelColor: any, colorGrid: any, lineWidthGrid: any, barPercentage: any, labelsY: any[], titleTooltip: any[] = [], kg = false) {
    let densityCanvas = document.getElementById(element);

    //Chart.defaults.global.defaultFontFamily = "icomoon";
    Chart.defaults.global.defaultFontSize = 10;
    Chart.defaults.global.animation.duration = 3000;
    Chart.defaults.global.animation.easing = 'easeOutBounce';

    let densityData = [{
      label: etiquetas[0],
      data: valores1,
      backgroundColor: color1,
      borderWidth: 0,
      hoverBorderWidth: 0
    },
      {
        label: etiquetas[1],
        data: valores2,
        backgroundColor: color2,
        borderWidth: 0,
        hoverBorderWidth: 0
      }];

    let chartOptions = {
      maintainAspectRatio: false,
      tooltips: {
        borderColor: '#FFF',
        mode: 'label',
        //tooltipFontFamily:"'icomoon', 'Roboto Cn'",
        titleFontSize: 10,
        bodyFontSize: 10,
        callbacks: {
          label: function (tooltipItem, data) {
            if (kg) {
              return data.datasets[tooltipItem.datasetIndex].label + ": " + accounting.formatNumber(tooltipItem.xLabel, 3, " ");
            } else {
              return data.datasets[tooltipItem.datasetIndex].label + ": " + accounting.formatNumber(tooltipItem.xLabel);
            }
          },
          title: function (tooltipItem, data) {
            return titleTooltip[tooltipItem[0].index];
          },
          afterTitle: function (tooltipItem, data) {
            return titleTooltip[tooltipItem[0].index] == "NO SE DECLARA MEDIO DE TRANSPORTE POR NO HABER" ? " PRESENTACIÓN FÍSICA DE MERCANCÍAS ANTE LA ADUANA" : "";
          }
        }
      },
      legend: {
        position: "none"
      },
      scales: {
        yAxes: [{
          barPercentage: barPercentage,
          stacked: true,
          ticks: {
            fontColor: ylabelColor,
            fontFamily: "'icomoon', 'Roboto Cn'",
            fontSize: 12,
          },
          gridLines: {
            display: false,
            drawBorder: true,
            zeroLineColor: colorGrid,
            zeroLineWidth: 2
          }
        }],
        xAxes: [{
          stacked: true,
          gridLines: {
            drawBorder: false,
            color: colorGrid,
            lineWidth: lineWidthGrid,
            zeroLineColor: colorGrid,
            zeroLineWidth: lineWidthGrid
          },
          ticks: {
            fontColor: "transparent",
          }
        }]
      },
    };

    let barChart = new Chart(densityCanvas, {
      type: 'horizontalBar',
      data: {
        labels: labelsY,
        datasets: densityData,
      },
      options: chartOptions
    });
  }

  /* ************************** VALORES DE LA GRAFICA ****************************
    let element = 'barStackedChartVertical1'
    let etiquetas = ['Importaciones', 'Exportaciones'];
    let valores1 = [5427, 5243, 5014, 3933, 2326, 1687, 1271, 1038];
    let valores2 = [2000, 1443, 1514, 1933, 326, 487, 271, 638];
    let color1 = ['#5A95F0','#5A95F0','#5A95F0','#5A95F0','#5A95F0','#5A95F0','#5A95F0','#5A95F0',];
    let color2 = ['#FDC340','#FDC340','#FDC340','#FDC340','#FDC340','#FDC340','#FDC340','#FDC340',]
    let ylabelColor = #5A95F0 || transparent;
    let colorGrid = 'rgba(0, 0, 0, 0.1)' ||
    let lineWidthGrid = 1 || 2
    let barPercentage = 0.75 || 1.5
    let labelsY = ["800", "705", "096", "126", "050", "051", "105", "991"];

    ************************************************************************************
  */

  createBarStackedVertical(element: any, etiquetas: any[], valores1: any[], valores2: any[], color1: any[], color2: any[], xlabelColor: any, colorGrid: any, lineWidthGrid: any, barPercentage: any, labelsX: any[]) {

    let densityCanvas = document.getElementById(element);

    Chart.defaults.global.defaultFontFamily = "Roboto Cn";
    Chart.defaults.global.defaultFontSize = 12;
    Chart.defaults.global.animation.duration = 3000;
    Chart.defaults.global.animation.easing = 'easeOutBounce';

    let densityData = [{
      label: etiquetas[0],
      data: valores1,
      backgroundColor: color1,
      borderWidth: 0,
      hoverBorderWidth: 0
    },
      {
        label: etiquetas[1],
        data: valores2,
        backgroundColor: color2,
        borderWidth: 0,
        hoverBorderWidth: 0
      }];

    let chartOptions = {
      maintainAspectRatio: false,
      tooltips: {
        borderColor: '#FFF',
        mode: 'label',
        callbacks: {
          label: function (tooltipItem, data) {
            return data.datasets[tooltipItem.datasetIndex].label + ": " + accounting.formatNumber(tooltipItem.yLabel);
          }
        }
      },
      legend: {
        position: "none"
      },
      scales: {
        yAxes: [{

          stacked: true,
          ticks: {
            fontColor: 'transparent',
            //display:false
          },
          gridLines: {
            drawBorder: false,
            color: colorGrid,
            lineWidth: lineWidthGrid

          }
        }],
        xAxes: [{
          barPercentage: barPercentage,
          stacked: true,
          gridLines: {
            display: false,
            drawBorder: false
          },
          ticks: {
            display: false,
            fontColor: xlabelColor,
          }
        }]
      },
    };

    let barChart = new Chart(densityCanvas, {
      type: 'bar',
      data: {
        labels: labelsX,
        datasets: densityData,
      },
      options: chartOptions
    });
  }

  /* ************************** VALORES DE LA GRAFICA ****************************
    let element = 'barStackedChart1'
    let etiqueta = 'Importaciones';
    let valores1 = [5427, 5243, 5014, 3933, 2326, 1687, 1271, 1038];
    let color1 = ['#5A95F0','#5A95F0','#5A95F0','#5A95F0','#5A95F0','#5A95F0','#5A95F0','#5A95F0',];
    let ylabelColor = #5A95F0 || transparent;
    let colorGrid = 'rgba(0, 0, 0, 0.1)' ||
    let lineWidthGrid = 1 || 2
    let barPercentage = 0.75 || 1.5
    let labelsY = ["800", "705", "096", "126", "050", "051", "105", "991"];

    ************************************************************************************
  */

  createBar(element: any, etiqueta: any, valores: any[], color: any[], ylabelColor: any, colorGrid: any, lineWidthGrid: any, barPercentage: any, labelsY: any[], titleTooltip: any[] = []) {
    let densityCanvas = document.getElementById(element);

    //Chart.defaults.global.defaultFontFamily = "Roboto Cn";
    Chart.defaults.global.defaultFontSize = 10;
    Chart.defaults.global.animation.duration = 3000;
    Chart.defaults.global.animation.easing = 'easeOutBounce';

    let densityData = [{
      label: etiqueta,
      data: valores,
      backgroundColor: color,
      borderWidth: 0,
      hoverBorderWidth: 0
    }];

    let chartOptions = {
      maintainAspectRatio: false,
      tooltips: {
        borderColor: '#FFF',
        mode: 'label',
        //tooltipFontFamily:"'icomoon', 'Roboto Cn'",
        titleFontSize: 10,
        bodyFontSize: 10,
        callbacks: {
          label: function (tooltipItem, data) {
            return data.datasets[tooltipItem.datasetIndex].label + ": " + accounting.formatNumber(tooltipItem.xLabel);
          },
          title: function (tooltipItem, data) {
            return titleTooltip[tooltipItem[0].index];
          },
          afterTitle: function (tooltipItem, data) {
            return titleTooltip[tooltipItem[0].index] == "NO SE DECLARA MEDIO DE TRANSPORTE POR NO HABER" ? " PRESENTACIÓN FÍSICA DE MERCANCÍAS ANTE LA ADUANA" : "";
          }
        }
      },
      legend: {
        position: "none"
      },
      scales: {
        yAxes: [{
          barPercentage: barPercentage,
          stacked: true,
          ticks: {
            fontColor: ylabelColor,
            fontFamily: "'icomoon', 'Roboto Cn'",
            fontSize: 12,
          },
          gridLines: {
            display: false,
            drawBorder: true,
            zeroLineColor: colorGrid,
            zeroLineWidth: 2
          }
        }],
        xAxes: [{
          stacked: true,
          gridLines: {
            drawBorder: false,
            color: colorGrid,
            lineWidth: lineWidthGrid,

          },
          ticks: {
            fontColor: "transparent",
          }
        }]
      },
    };

    let barChart = new Chart(densityCanvas, {
      type: 'horizontalBar',
      data: {
        labels: labelsY,
        datasets: densityData,
      },
      options: chartOptions
    });
  }

  /* --------------------------------------------------/charts----------------------------------------- */

  handleValidDates() {
    DEBUG && console.log('@handleValidDates: Entre');

    return new Promise(resolve => {
      setTimeout(() => {
        let startTime = new Date(this.date);
        let endTime = new Date(this.date2);
        startTime.setHours(0, 0, 0, 0);
        endTime.setHours(23, 59, 59, 999);

        if (startTime.getTime() < endTime.getTime()) {
          this.invalidDates = false;
          resolve(true);
        } else {
          this.invalidDates = true;
          resolve(false);
        }
      })
    });
  }

  handleCalculateDate(value) {
    DEBUG && console.log('@handleCalculateDate: Entre');
    this.calculateActive = value;
  }

  handleDate(data, option) {
    DEBUG && console.log('@handleDate: Entre', this.calculateActive);

    if (!this.calculateActive) return;

    if (option === "start") {
      this.date2 = this.selectDate(data, option);
    } else if (option === "end") {
      this.date = this.selectDate(data, option);
    }
  }

  selectDate(date, option) {
    DEBUG && console.log('@selectDate: Entre', date, option);

    let year = date.getFullYear();
    let month = date.getMonth();

    let day = option === "start" ? new Date(year, month + 1, 0).getDate() : 1 ;

    return new Date(year, month, day);
  }

}

