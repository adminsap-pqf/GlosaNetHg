import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import Chart from 'chart.js';
import * as accounting from 'accounting';
import {Parametros} from '../../model/parametros.class';
import {Usuario} from '../../model/usuario.class';
import {Cliente} from '../../model/cliente.class';
import {SessionUser} from '../../services/session/session.service';
import {PreauditoriaHttpService} from '../../services/preauditoria/preauditoriaHttp.service';
import {FormGroup, FormBuilder} from '@angular/forms';
import * as _ from 'lodash';

import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table';
import {MatDialog} from '@angular/material/dialog';
import {ModalComponent} from '../shared/modal/modal.component';

/* Dev Tools */
import {DEBUG} from '../../settings/app.settings';

import * as html2canvas from 'html2canvas';
import {IPageInfo} from 'ngx-virtual-scroller';
import * as protocols from '../../util/common.protocols';

@Component({
  selector: 'gn-preauditoria',
  templateUrl: './preauditoria.component.html',
  styleUrls: ['./preauditoria.component.scss']
})
export class PreauditoriaComponent implements OnInit, AfterViewInit {
  displayedColumns = ['#',
    'fechaPagoReal',
    'folio',
    'patente',
    'pedimento',
    'seccionAduanera',
    'tipoOperacion',
    'tipoPedimento',
    'claveDocumento',
    'dta',
    'prv',
    'cnt',
    'recargos',
    'otros',
    'iva',
    'igi',
    'total'];
  displayedColumnsPedimento = [
    '#',
    'Fecha Pago Real',
    'Folio',
    'Patente',
    'Pedimento',
    'Sección Aduanera',
    'Secuencia Fracción',
    'Identificación Fiscal',
    'Proveedor de mercancía',
    'Pais Comprador',
    'Valor Aduana',
    'IVA',
    'IGI/IGE',
  ];
  dataNamesColumnsPedimento = [
    'rowIndex',
    'fechaPagoReal',
    'folio',
    'patente',
    'pedimento',
    'seccionAduanera',
    'secuenciaFraccion',
    'indentFiscalProveedor',
    'proveedorMercancia',
    'paisCompradorVendedor',
    'valorAduana',
    'iva',
    'igi',
  ];
  widthStyles = [
    {width: '3%', minWidth: ''},
    {width: '8%'},
    {width: '5%'},
    {width: '6%'},
    {width: '6%'},
    {width: '7%'},
    {width: '8%'},
    {width: '10%'},
    {width: '24%'},
    {width: '8%'}, 
    {width: '5%'},
    {width: '5%'},
    {width: '5%'}
  ];
  dataSource: any = new MatTableDataSource<any>([]);
  @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;
  searchTerm: string = null;
  handleKeySearch = _.debounce($event => this.apply($event), 500);
  loadingDataStep5 = false;
  loadingDataStep6 = false;

  itemsPedimento = [];
  itemsUpdatePedimento = [];
  sizeItemsForPage = 24;
  currentPage = 0;
  totalItems =  0;
  timer;

  public myForm: FormGroup;
  defaultSelected: any;
  labelGlobal: any = '';
  valueGlobal: any = '';

  changeGraph: any = '0';
  opacity1: any = '1';
  opacity2: any = '0';

  data: any;

  parametros: Parametros;
  idReporte: any = 0;
  totalGrafica: any = 0;
  dataGraph: any;
  datosGrafica: any[] = [];
  titleGraph: any = '';
  titleLegend: any = '';
  subtitle1: any = '';
  subtitle2: any = '';
  subtitle3: any = '';
  subtitle4: any = '';
  step5Selected = false;
  step6Selected = false;

  totalGrafica2: any = 0;
  totalPorcentaje: any = 0;
  totalMonto: any = 0;
  titleLabel2: any = '';

  datosGraficaPais: any[] = [];
  subtitlePais1: any = '';
  subtitlePais2: any = '';
  subtitlePais3: any = '';
  subtitlePais4: any = '';
  columnExtra: any = false;

  /* DropList */
  public itemsDropList: any = [];
  public itemsDropList2: any = [
    {
      nombre: 'Todo el Año',
      tipo: '',
      periodo: 'Anual',
      data: 1
    },
    {
      nombre: 'Semestres',
      tipo: 'cat'
    },
    {
      nombre: 'Enero - Junio',
      tipo: '',
      periodo: 'Semestre',
      data: 1
    },
    {
      nombre: 'Julio - Diciembre',
      tipo: '',
      periodo: 'Semestre',
      data: 2
    },
    {
      nombre: 'Trimestres',
      tipo: 'cat'
    },
    {
      nombre: 'Enero - Marzo',
      tipo: '',
      periodo: 'Trimestre',
      data: 1
    },
    {
      nombre: 'Abril - Junio',
      tipo: '',
      periodo: 'Trimestre',
      data: 2
    },
    {
      nombre: 'Juilo - Septiembre',
      tipo: '',
      periodo: 'Trimestre',
      data: 3
    },
    {
      nombre: 'Octubre - Diciembre',
      tipo: '',
      periodo: 'Trimestre',
      data: 4
    },
    {
      nombre: 'Mensual',
      tipo: 'cat'
    },
    {
      nombre: 'Enero',
      tipo: '',
      periodo: 'Mes',
      data: 'Enero'
    },
    {
      nombre: 'Febrero',
      tipo: '',
      periodo: 'Mes',
      data: 'Febrero'
    },
    {
      nombre: 'Marzo',
      tipo: '',
      periodo: 'Mes',
      data: 'Marzo'
    },
    {
      nombre: 'Abril',
      tipo: '',
      periodo: 'Mes',
      data: 'Abril'
    },
    {
      nombre: 'Mayo',
      tipo: '',
      periodo: 'Mes',
      data: 'Mayo'
    },
    {
      nombre: 'Junio',
      tipo: '',
      periodo: 'Mes',
      data: 'Junio'
    },
    {
      nombre: 'Julio',
      tipo: '',
      periodo: 'Mes',
      data: 'Julio'
    },
    {
      nombre: 'Agosto',
      tipo: '',
      periodo: 'Mes',
      data: 'Agosto'
    },
    {
      nombre: 'Septiembre',
      tipo: '',
      periodo: 'Mes',
      data: 'Septiembre'
    },
    {
      nombre: 'Octubre',
      tipo: '',
      periodo: 'Mes',
      data: 'Octubre'
    },
    {
      nombre: 'Noviembre',
      tipo: '',
      periodo: 'Mes',
      data: 'Noviembre'
    },
    {
      nombre: 'Diciembre',
      tipo: '',
      periodo: 'Mes',
      data: 'Diciembre'
    }
  ];

  public defaultdroplist2 = {nombre: 'Todo el Año', tipo: '', periodo: 'Anual', data: 1};
  anio: any = '';
  periodo: any = '';

  /* Datepicker */
  date: Date;
  date2: Date;
  dateInicio: Date;
  dateFin: Date;


  label: any[] = new Array<any>(7).fill('labelDiv');
  items: any[] = [{nombre: 'Resumen de Importaciones', reporte: '0'},
    {nombre: 'Resumen de Exportaciones', reporte: '1'},
    {nombre: 'Resumen de Contribuciones', reporte: '2'},
    {nombre: 'Valores en Aduana por región - Importaciones Temporales', reporte: '3'},
    {nombre: 'Valores en Aduana por región - Importaciones Definitivas', reporte: '4'},
    {nombre: 'Reporte de Pedimentos con contribuciones', reporte: '5'},
    {nombre: 'Reporte de Partidas con contribuciones', reporte: '6'}
    ];
  /* { nombre: "Relación entre impuesto de importación y valor en aduana", reporte: "5" },
  { nombre: "Pago pendiente de impuesto de Importación", reporte: "6" }]; */


  colorsDefault: any[] = [
    '#3498DB',
    '#16A085',
    '#2ECC71',
    '#F39C12',
    '#D35400',
    '#C0392B',
    '#9B59B6',
    '#2980B9',
    '#1ABC9C',
    '#27AE60',
    '#F1C40F',
    '#E67E22',
    '#CB4335',
    '#7D3C98',
    '#2E86C1',
    '#138D75',
    '#28B463',
    '#D68910',
    '#BA4A00',
    '#A93226',
    '#884EA0',
    '#2471A3',
    '#17A589',
    '#229954',
    '#D4AC0D',
    '#CA6F1E',
    '#E74C3C',
    '#8E44AD',
  ];


  /* colorsDefault:any[] = [
    '#5A95F0',
    '#DB4F7A',
    '#BB3C49',
    '#DB4648',
    '#F69B45',
    '#FDC340',
    '#D4DD64',
    '#577AC0',
  ]; */


  constructor(private _fb: FormBuilder, private preauditoria: PreauditoriaHttpService, private dialog: MatDialog) {
  }

  ngAfterViewInit() {
    this.paginator._intl.itemsPerPageLabel = 'items por pagina';
    this.paginator._intl.nextPageLabel = 'Siguiente';
    this.paginator._intl.previousPageLabel = 'Anterior';
    this.dataSource.paginator = this.paginator;
  }

  ngOnInit() {
    this.myForm = this._fb.group({
      label: [''],
    });

    this.myForm.valueChanges.subscribe(() => {
      DEBUG && console.log('value change');
    });
    this.loadOptions();
    this.dropList(1, this.itemsDropList[0]);
    this.dropList(2, this.itemsDropList2[0]);
    this.selectItem(0, this.items[0]);
    //this.apply();
  }

  ngOnChanges() {

  }

  ngDoCheck() {

  }

  loadOptions() {
    const startYear = new Date().getFullYear();
    const endYear = 2012;
    let count = startYear;
    const options = [];

    for (let i = startYear; i >= endYear; i--) {
      options.push({nombre: `${count}`});
      count--;
    }

    this.itemsDropList = options.slice();
  }

  /* labels */
  selectItem(index: any, item: any) {
    this.resetGrafica();
    this.label.fill('labelDiv');
    this.label[index] = 'labelDivSelect';
    this.idReporte = index;
    this.titleGraph = item.nombre;
    this.totalGrafica = 0;
    this.datosGrafica = [];
    switch (index) {
      case 0:
        this.titleLegend = 'Importaciones';
        this.subtitle1 = 'Clave';
        this.subtitle2 = 'Valor Aduana (Pesos)';
        this.columnExtra = false;
        this.step5Selected = false;
        this.step6Selected = false;
        break;
      case 1:
        this.titleLegend = 'Exportaciones';
        this.subtitle1 = 'Clave';
        this.subtitle2 = 'Valor Aduana (Pesos)';
        this.columnExtra = false;
        this.step5Selected = false;
        this.step6Selected = false;
        break;
      case 2:
        this.titleLegend = 'Contribuciones';
        this.subtitle1 = 'Clave';
        this.subtitle2 = 'Monto (Pesos)';
        this.columnExtra = false;
        this.step5Selected = false;
        this.step6Selected = false;
        break;
      case 3:
        this.titleLegend = 'Importaciones Temporales';
        this.subtitle1 = 'Región';
        this.subtitle2 = 'Valor en Aduanas (Pesos)';

        this.subtitlePais1 = 'País';
        this.subtitlePais2 = 'Valor en Aduanas (Pesos)';
        this.columnExtra = false;
        this.step5Selected = false;
        this.step6Selected = false;
        break;
      case 4:
        this.titleLegend = 'Importaciones Definitivas';
        this.subtitle1 = 'Región';
        this.subtitle2 = 'Valor en Aduanas (Pesos)';

        this.subtitlePais1 = 'País';
        this.subtitlePais2 = 'Valor en Aduanas (Pesos)';
        this.columnExtra = false;
        this.step5Selected = false;
        this.step6Selected = false;
        break;
      case 5:
        this.step5Selected = true;
        this.step6Selected = false;
        break;
      case 6:
        this.step6Selected = true;
        this.step5Selected = false;
        break;
      /*      case 5:
              this.titleLegend = 'Relación entre impuestos de importación y valor en aduana';
              this.subtitle1 = 'Región';
              this.subtitle2 = 'Valor en Aduanas (Pesos)';
              this.subtitle3 = 'Impuesto de importación (Pesos)';
              this.subtitle4 = 'Porcentaje (Pesos)';


              this.subtitlePais1 = 'País';
              this.subtitlePais2 = 'Valor en Aduanas (Pesos)';
              this.subtitlePais3 = 'Impuesto de importación (Pesos)';
              this.subtitlePais4 = 'Porcentaje (Pesos)';
              this.columnExtra = true;
              break;
            case 6:
              this.titleLegend = 'Pago pendiente de impuesto de importación';
              this.subtitle1 = 'Región';
              this.subtitle2 = 'Precio de importación general';
              this.subtitle3 = 'Precio Prosec';
              this.subtitle4 = 'Importe Total';

              this.subtitlePais1 = 'País';
              this.subtitlePais2 = 'Precio de importación general';
              this.columnExtra = false;
              break;*/
      default:
        break;
    }
    /*    if (!this.step5Selected) {
          this.apply();
        }*/
    this.apply();
  }

  /* droplist */
  dropList(index: any, $event) {
    DEBUG && console.log($event);
    switch (index) {
      case 1:
        this.anio = $event;
        break;
      case 2:
        this.periodo = $event;
        break;
      default:
        break;
    }
  }

  /* boton aplicar */
  apply(searchTerm?: string, download?: boolean) {
    this.parametros = new Parametros();
    const usuario: Usuario = new Usuario();
    const cliente: Cliente = new Cliente();
    this.parametros.tipo = 'Periodo';
    cliente.idCliente = SessionUser.getInstance().getIdClient();
    cliente.rfcActual = SessionUser.getInstance().getRfc();
    usuario.cliente = cliente;
    usuario.moneda = 'Peso';
    usuario.idioma = 'espanol';
    this.parametros.usuario = usuario;
    this.parametros.reporte = this.idReporte;
    this.parametros.year = this.anio.nombre;

    if (searchTerm) {
      this.parametros.busqueda = searchTerm;
    }

    switch (this.periodo.periodo) {
      case 'Semestre':
        this.parametros.semestre = this.periodo.data;
        break;
      case 'Trimestre':
        this.parametros.trimestre = this.periodo.data;
        break;
      case 'Mes':
        this.parametros.mes = this.periodo.data;
        break;

      default:
        break;
    }
    if (this.step5Selected) {
      if (download) {
        this.downLoadHandler('getFileExportStep5', 'reporte_contribuciones_detallado.xls');
      } else {
        this.getDataStep5();
      }
    } else {
      if (this.step6Selected) {
        if (download) {
          this.downLoadHandler('getFileExportStep6', 'reporte_partidas_detallado.xls');
        } else {
          this.currentPage = 0;
          this.itemsPedimento = [];
          this.parametros.size = this.sizeItemsForPage;
          this.parametros.page = this.currentPage;
          this.getDataStep6();
          this.currentPage = 1;
        }
      } else {
        this.obtenerResultadosReportesPredefinidos();
      }
    }
  }

  /* resetGrafica */

  resetGrafica() {
    this.returnGlobal();
    try {
      const canvasDiv = document.querySelector('#doughnut1Div');
      canvasDiv.removeChild(document.querySelector('#doughnut1'));
      document.querySelector('#doughnut1Div').insertAdjacentHTML('afterbegin', '<canvas id="doughnut1"></canvas>');

    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    try {
      const canvasDiv = document.querySelector('#doughnut2Div');
      canvasDiv.removeChild(document.querySelector('#doughnut2'));
      document.querySelector('#doughnut2Div').insertAdjacentHTML('afterbegin', '<canvas id="doughnut2" #doughnut2></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }
  }

  /***** Obtenemos los datos para la tabla ******/
  getDataStep5() {
    this.loadingDataStep5 = true;
    this.preauditoria.getDataStep5(this.parametros).subscribe(response => {
      this.dataSource.data = response;
      this.loadingDataStep5 = false;
    }, error => {
      this.loadingDataStep5 = false;
    });
  }

  /***** Obtenemos los datos para la tabla de pedimentos ******/
  getDataStep6() {
    this.loadingDataStep6 = true;
    this.preauditoria.getDataStep6(this.parametros).subscribe(response => {
      this.loadingDataStep6 = false;
      this.itemsPedimento = this.itemsPedimento.concat(response.results);
      this.totalItems = response.total;
    }, error => {
      this.loadingDataStep6 = false;
    });
  }

  /* metodo grafica */

  obtenerResultadosReportesPredefinidos() {
    SessionUser.getInstance().setLoading(true);
    this.preauditoria.obtenerResultadosReportesPredefinidos(this.parametros)
      .subscribe(
        data => {
          DEBUG && console.log(data);
          this.dataGraph = data;
          this.datosGrafica = [];
          let etiquetas = [];
          let valores = [];
          const colores = [];
          this.totalGrafica = 0;
          const claveNombreCompleto = [];

          if (data.length > 0) {

            const etiquetasUnique = [];
            const idEtiquetasUnique = new Set(etiquetasUnique);

            if (data.length !== 0) {
              for (const item of data) {
                if (this.idReporte >= 0 && this.idReporte <= 2) {
                  etiquetas.push(item.clave);
                  valores.push(item.montoTotal);
                  claveNombreCompleto.push(item.nombreClave !== undefined ? '- ' + item.nombreClave : ' ');
                } else {
                  idEtiquetasUnique.add(item.region);
                }
                this.totalGrafica += Number(item.montoTotal);
              }

              DEBUG && console.log(claveNombreCompleto);

              idEtiquetasUnique.forEach((value) => {
                DEBUG && console.log(value);
                etiquetas.push(value);
              });

              DEBUG && console.log(etiquetas);
              let index = 0;

              if (this.idReporte >= 3 && this.idReporte <= 6) {
                valores = new Array<any>(etiquetas.length).fill(0);
                for (const item of etiquetas) {
                  for (const item2 of data) {
                    if (item === item2.region) {
                      valores[index] += Number(item2.montoTotal);
                    }
                  }
                  index++;
                }
                DEBUG && console.log(etiquetas);
                DEBUG && console.log(valores);
              }
              let indexColor = 0;
              for (const item of etiquetas) {
                /* colores.push("#" + Math.floor(Math.random()*16777215).toString(16)); */
                colores.push(this.colorsDefault[indexColor]);
                indexColor++;
              }
              DEBUG && console.log(colores);

              let indexGraph = 0;
              for (const item of etiquetas) {
                const data = {
                  labels: etiquetas[indexGraph],
                  labels2: claveNombreCompleto.length > 0 ? claveNombreCompleto[indexGraph] : ' ',
                  data: valores[indexGraph],
                  colors: colores[indexGraph],
                };
                this.datosGrafica.push(data);
                indexGraph++;
              }
              DEBUG && console.log(this.datosGrafica);
              DEBUG && console.log(this.dataGraph);
            } else {
              etiquetas = [];
              valores = [0, 0, 0];
            }
            try {
              const canvasDiv = document.querySelector('#doughnut1Div');
              canvasDiv.removeChild(document.querySelector('#doughnut1'));
              document.querySelector('#doughnut1Div').insertAdjacentHTML('afterbegin', '<canvas id="doughnut1"></canvas>');
            } catch (error) {
              DEBUG && console.log('No hay elementos hijos, error: ' + error);
            }

            const tooltipData = false;
            this.createDoughnut('doughnut1', etiquetas, valores, colores, tooltipData);
          } else {
            try {
              const canvasDiv = document.querySelector('#doughnut1Div');
              canvasDiv.removeChild(document.querySelector('#doughnut1'));
              document.querySelector('#doughnut1Div').insertAdjacentHTML('afterbegin', '<canvas id="doughnut1"></canvas>');
            } catch (error) {
              DEBUG && console.log('No hay elementos hijos, error: ' + error);
            }
            const tooltipData = true;
            this.createDoughnut('doughnut1', [''], [1], ['#D5DEE2'], tooltipData, false);
          }
          SessionUser.getInstance().setLoading(false);
        },
        error => {
          DEBUG && console.log('Error: t501_obtenerOperacionesDelPeriodo - tablero.service.ts - ' + error);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al obtener el reporte');
          document.getElementById('requestFailed').style.display = 'block';
        }
      );
  }

  /* metodo grafica por regiones */

  createGraficaRegiones(region: any) {
    this.changeGraph = '-100%';
    this.opacity1 = '0';
    this.opacity2 = '1';
    const etiquetas = [];
    const valores = [];
    const colores = [];
    this.datosGraficaPais = [];


    this.titleLabel2 = region;
    this.totalGrafica2 = 0;

    const montoContribuciones = [];
    const porcentajeContribuciones = [];

    for (const item of this.dataGraph) {
      if (item.region == region) {
        etiquetas.push(item.pais);
        valores.push(item.montoTotal);
        montoContribuciones.push(item.montoContribuciones);
        porcentajeContribuciones.push(item.porcentajeContribuciones);

        this.totalGrafica2 += Number(item.montoTotal);
        this.totalMonto += Number(item.montoContribuciones);
        this.totalPorcentaje += Number(item.porcentajeContribuciones);
      }
    }

    DEBUG && console.log(etiquetas);
    DEBUG && console.log(valores);


    let indexColor = 0;
    for (const item of etiquetas) {
      /* colores.push("#" + Math.floor(Math.random()*16777215).toString(16)); */
      colores.push(this.colorsDefault[indexColor]);
      indexColor++;
    }
    DEBUG && console.log(colores);


    let indexGraph = 0;
    for (const item of etiquetas) {
      const data = {
        labels: etiquetas[indexGraph],
        data: valores[indexGraph],
        colors: colores[indexGraph],
        porcentaje: porcentajeContribuciones[indexGraph],
        monto: montoContribuciones[indexGraph]
      };
      this.datosGraficaPais.push(data);
      indexGraph++;
    }
    DEBUG && console.log(this.datosGraficaPais);


    try {
      const canvasDiv = document.querySelector('#doughnut2Div');
      canvasDiv.removeChild(document.querySelector('#doughnut2'));
      document.querySelector('#doughnut2Div').insertAdjacentHTML('afterbegin', '<canvas id="doughnut2" #doughnut2></canvas>');


    } catch (error) {
      DEBUG && console.log('No hay elementos hijos, error: ' + error);
    }

    const tooltipData = false;

    this.createDoughnut('doughnut2', etiquetas, valores, colores, tooltipData);
  }


  /* ************************** VALORES DE LA GRAFICA ****************************
  let element = 'doughnut1';
  let etiquetas = ['Importaciones', 'Exportaciones', 'Rectificaciones'];
  let valores = [100, 200, 150];
  let colores = ["#5792F3", "#FEC42D", "#DD4444",]
  let callback = true = formatNumber | false = formatMoney
  let tooltipView = true : display visible | false : display hide

  ************************************************************************************
  */

  createDoughnut(element: any, etiquetas: any[], valores: any[], colores: any[], callback: any, tooltipView = true) {

    const oilCanvas = document.getElementById(element);

    Chart.defaults.global.defaultFontFamily = 'Roboto Cn';
    Chart.defaults.global.defaultFontSize = 12;

    Chart.defaults.global.animation.duration = 2000;
    Chart.defaults.global.animation.easing = 'easeOutBounce';


    const oilData = {
      labels: etiquetas,
      datasets: [
        {
          data: valores,
          backgroundColor: colores,
          borderColor: 'black',
          borderWidth: 0,
          hoverBorderWidth: 5,
          hoverBorderColor: colores,
          hoverBackgroundColor: colores
        }],

    };

    const chartOptions = {
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
        enabled: tooltipView,
        backgroundColor: '#000',
        callbacks: {
          label: function (tooltipItem, data) {
            return callback !== true ? data.labels[tooltipItem.index] + ': ' + accounting.formatMoney(data.datasets[0].data[tooltipItem.index]) : tooltipItem.yLabel + ' ' + accounting.formatNumber(data.datasets[0].data[tooltipItem.index]);
          }
        }
      },
      onClick: this.chartClicked
    };

    const pieChart = new Chart(oilCanvas, {
      type: 'doughnut',
      data: oilData,
      options: chartOptions,
    });

  }


  chartClicked($e: Event, array: any[]): any {
    DEBUG && console.log($e);
    if (array[0]._view !== undefined) {
      DEBUG && console.log(array[0]._view.label);
      (<HTMLInputElement>document.getElementById('labelInput')).value = array[0]._view.label;
    }
  }

  chartHovered(e: any): void {
    DEBUG && console.log(e);
  }

  changeDetection($event) {
    DEBUG && console.log($event);
    const input = (<HTMLInputElement>document.getElementById('labelInput')).value;
    DEBUG && console.log(input);
    switch (input) {
      case 'Norte América':
        this.createGraficaRegiones(input);
        break;
      case 'Asia':
        this.createGraficaRegiones(input);
        break;
      case 'Europa':
        this.createGraficaRegiones(input);
        break;
      case 'África':
        this.createGraficaRegiones(input);
        break;
      case 'Oceania':
        this.createGraficaRegiones(input);
        break;
      case 'Antártida':
        this.createGraficaRegiones(input);
        break;
      case 'Sur América':
        this.createGraficaRegiones(input);
        break;

      default:
        break;
    }
  }


  clickLabelRegion(input: any) {
    (<HTMLInputElement>document.getElementById('labelInput')).value = input;
    DEBUG && console.log(input);
    switch (input) {
      case 'Norte América':
        this.createGraficaRegiones(input);
        break;
      case 'Asia':
        this.createGraficaRegiones(input);
        break;
      case 'Europa':
        this.createGraficaRegiones(input);
        break;
      case 'África':
        this.createGraficaRegiones(input);
        break;
      case 'Oceania':
        this.createGraficaRegiones(input);
        break;
      case 'Antártida':
        this.createGraficaRegiones(input);
        break;
      case 'Sur América':
        this.createGraficaRegiones(input);
        break;

      default:
        break;
    }
  }

  returnGlobal() {
    this.opacity1 = '1';
    this.opacity2 = '0';
    this.changeGraph = '0';
  }

  pdfDownload() {
    SessionUser.getInstance().setLoading(true);
    this.getDataTable().then(
      res => {
        this.generarReporte(res);
      });
  }

  getDataTable() {
    return new Promise((resolve, reject) => {

      let data;

      switch (this.idReporte) {
        case 0:
          const regionesAux = [];
          const montosAux = [];
          for (const item of this.datosGrafica) {
            regionesAux.push({nombreRegion: item.labels, montos: item.data});
          }
          data = {
            tipoGrafica: 'Contenido2Columnas',
            regiones: regionesAux,
            fechaReporte: '16/Julio/2018',
            email: 'emailprueba',
            total: '10000',
            logo: SessionUser.getInstance().getCliente().empresas[0].imagenEmpresa,
            usuario: SessionUser.getInstance().getUserID(),
            pdf: this.getImgTablero('doughnut1Div').then(d => {
              data.pdf = d;
              resolve(data);
            })
          };
          break;
        case 1:

          break;
        case 2:

          break;
        case 3:

          break;
        case 4:

          break;
        case 5:

          break;
        case 6:

          break;
        default:
          break;
      }
    });
  }

  getImgTablero(element) {
    return new Promise((resolve, reject) => {

      // /*let options = {
      //   async: false
      // }*/
      (html2canvas as any)(document.getElementById(element)).then(canvas => {
        let imgData = canvas.toDataURL('image/png');

        /* let map = new Map();
        map.set('dashboard', imgData); */
        const array = imgData.split(',');
        imgData = array[1];
        DEBUG && console.log(imgData);

        resolve(imgData);

      });
    });
  }

  generarReporte(data) {
    this.preauditoria.generarReporte(data)
      .subscribe(
        data => {
          if (data) {
            DEBUG && console.log('@generarReporte: success', data);
            const element = document.createElement('a');
            element.setAttribute('href', 'data:application/pdf;base64,' + data);
            element.setAttribute('download', 'Reporte');
            element.setAttribute('target', '_blank');
            element.style.display = 'none';
            document.body.appendChild(element);
            element.click();
          } else {
            SessionUser.getInstance().setMessageFailed('al descargar el reporte');
            document.getElementById('requestFailed').style.display = 'block';
          }
        },
        error => {
          DEBUG && console.log('@generarReporte: failed', error);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al descargar el reporte');
          document.getElementById('requestFailed').style.display = 'block';
        }, () => {
          SessionUser.getInstance().setLoading(false);
        });
  }

  downLoadHandler(serviceName, fileName) {
    this.preauditoria[serviceName](this.parametros).subscribe(response => {
      const binary_string = this.base64ToArrayBuffer(response.body);
      const url = window.URL.createObjectURL(new Blob([binary_string], {type: 'application/pdf'}));
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', fileName);
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    });
  }

  base64ToArrayBuffer(base64) {
    const binary_string = window.atob(base64);
    const len = binary_string.length;
    const bytes = new Uint8Array(len);
    for (let i = 0; i < len; i++) {
      bytes[i] = binary_string.charCodeAt(i);
    }
    return bytes.buffer;
  }

  getPediment(item) {
    const body = {
      idDatastage : item.idDatastage,
      patente : item.patente,
      pedimento : item.pedimento,
      seccionAduanera: item.seccionAduanera
    };

    this.preauditoria.getPartidas(body).subscribe(response => {
      const dialogRef = this.dialog.open(ModalComponent, {data: response});
    }, () => {
      SessionUser.getInstance().setMessageFailed('al obtener el reporte');
    });
  }

  fetchMore(event: IPageInfo) {
    if (event.endIndex !== this.itemsPedimento.length - 1) { return; }
    if (event.endIndex !== this.totalItems - 1 && this.totalItems > 0) {
      const totalPages = this.totalItems >= this.sizeItemsForPage ?
        Math.ceil(this.totalItems / this.sizeItemsForPage)
        : 0;
      if (this.currentPage > this.totalItems || this.itemsPedimento.length > this.totalItems) {
        return;
      }
      this.fetchNextChunk().then(() => {
      });
    }
  }

  fetchNextChunk(): Promise<any[]> {
    this.parametros.page = this.currentPage;
    return new Promise((resolve, reject) => {
      clearTimeout(this.timer);
      this.timer = setTimeout( async() => {
        await this.getDataStep6();
        this.currentPage ++;
        return resolve([]);
      }, 200);
    });
  }
}
