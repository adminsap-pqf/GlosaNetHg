import {UserService} from './../../services/user/user.service';
import {Component, Inject, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {DragulaService} from 'ng2-dragula';
import {Parametros} from '../../model/parametros.class';
import {ReportePersonalizadoService} from '../../services/reporte-personalizado/reporte-personalizado.service';
import {Usuario} from '../../model/usuario.class';
import {ReportePersonalizado} from '../../model/reportePersonalizado.class';
import {PReportePersonalizados} from '../../model/pReportePersonalizado.class';
import {ActivatedRoute, Router} from '@angular/router';
import {Subscription} from 'rxjs';
import {UserBarComponent} from '../core-container/user-bar/user-bar.component';
import {SessionUser} from '../../services/session/session.service';
/* Dev Tools */
import {DEBUG} from "../../settings/app.settings";


//private userService: UserService,
  //private reportePersonalizado: ReportePersonalizadoService,
  //private routeParams: ActivatedRoute,
  //private dragulaService: DragulaService,

@Component({
  selector: 'gn-reportes-personalizados',
  templateUrl: './reportes-personalizados.component.html',
  styleUrls: ['./reportes-personalizados.component.scss'],
})
export class ReportesPersonalizadosComponent implements OnInit {

  idReport: any;
  subs: Subscription;
  years: any = [{nombre: '2022'}, {nombre: '2021'}, {nombre: '2020'}, {nombre: '2019'}, {nombre: '2018'}, {nombre: '2017'}, {nombre: '2016'}, {nombre: '2015'}, {nombre: '2014'}, {nombre: '2013'}];
  periodos: any = [
    {
      nombre: 'Todo el Año',
      tipo: "",
      periodo: "Anual",
      data: 1
    },
    {
      nombre: 'Semestres',
      tipo: "cat"
    },
    {
      nombre: 'Enero - Junio',
      tipo: "",
      periodo: "Semestre",
      data: 1
    },
    {
      nombre: 'Julio - Diciembre',
      tipo: "",
      periodo: "Semestre",
      data: 2
    },
    {
      nombre: 'Trimestres',
      tipo: "cat"
    },
    {
      nombre: 'Enero - Marzo',
      tipo: "",
      periodo: "Trimestre",
      data: 1
    },
    {nombre: 'Abril - Junio',
      tipo: "",
      periodo: "Trimestre",
      data: 2
    },
    {
      nombre: 'Juilo - Septiembre',
      tipo: "",
      periodo: "Trimestre",
      data: 3
    },
    {
      nombre: 'Octubre - Diciembre',
      tipo: "",
      periodo: "Trimestre",
      data: 4
    },
    {
      nombre: 'Mensual',
      tipo: "cat"
    },
    {
      nombre: 'Enero',
      tipo: "",
      periodo: "Mes",
      data: 1
    },
    {
      nombre: 'Febrero',
      tipo: "",
      periodo: "Mes",
      data: 2
    },
    {
      nombre: 'Marzo',
      tipo: "",
      periodo: "Mes",
      data: 3
    },
    {
      nombre: 'Abril',
      tipo: "",
      periodo: "Mes",
      data: 4
    },
    {
      nombre: 'Mayo',
      tipo: "",
      periodo: "Mes",
      data: 5
    },
    {
      nombre: 'Junio',
      tipo: "",
      periodo: "Mes",
      data: 6
    },
    {
      nombre: 'Julio',
      tipo: "",
      periodo: "Mes",
      data: 7
    },
    {
      nombre: 'Agosto',
      tipo: "",
      periodo: "Mes",
      data: 8
    },
    {
      nombre: 'Septiembre',
      tipo: "",
      periodo: "Mes",
      data: 9
    },
    {
      nombre: 'Octubre',
      tipo: "",
      periodo: "Mes",
      data: 10
    },
    {
      nombre: 'Noviembre',
      tipo: "",
      periodo: "Mes",
      data: 11
    },
    {
      nombre: 'Diciembre',
      tipo: "",
      periodo: "Mes",
      data: 12
    }
  ];
  itemsDD: any[] = [];
  itemsDD2: any[] = [];
  documentos: any [] = [];
  documentosAux: any[] = [];
  documentosList: any[] = [];
  periodo: String = "";
  dataPeriodo: any;
  year: any;
  lstOpciones: String[] = ["Campos", "Datos Generales"];
  visibleCampos: Boolean = true;
  resetTabs: Boolean = false;
  rpForm: FormGroup;
  countValid: number = 0;
  countValidAux: Array<boolean> = new Array(2).fill(false);
  dataValid: string = 'invalidData';
  classAnimation: String = 'w3-animate-zoom';
  reporte: ReportePersonalizado;
  periodoDefault: any = {nombre: 'Todo el Año', tipo: "", periodo: "Anual", data: 1};


  currentYear: number = 0;
  today: Date = new Date();

  nameUser: string = '';
  indexYear: number = 0;

  constructor(
    private userService: UserService,
    private reportePersonalizado: ReportePersonalizadoService,
    private routeParams: ActivatedRoute,
    private dragulaService: DragulaService,
    private router: Router,
    @Inject(UserBarComponent) private userBAR) {
    DEBUG && console.log("@constructor: dragulaService.find('another-bag')");
    if (dragulaService.find('another-bag') == undefined) {
      dragulaService.createGroup('another-bag', {
        accepts: (el, source, handle, sibling) => !source.classList.contains('no-drop')
      });
    }
  }

  ngOnInit() {
    this.subs = this.routeParams.params.subscribe(
      (params: any) => {
        this.idReport = params['id'];

        this.currentYear = this.today.getFullYear();
        this.yearItemSelected();

        this.nameUser = SessionUser.getInstance().getUsuario().nombre + ' ' + SessionUser.getInstance().getUsuario().apellido;

        this.obtenerTablas();
        this.rpForm = new FormGroup({
          rpNombre: new FormControl('', [Validators.required]),
          rpDescripcion: new FormControl('', [Validators.required])
        });
        this.rpForm.valueChanges.subscribe(
          () => {
            this.countValid = 0;
            this.countValidAux.fill(false);
            this.countValidAux[0] = this.rpForm.get('rpNombre').valid;
            this.countValidAux[1] = this.rpForm.get('rpDescripcion').valid;

            for (let i of this.countValidAux) {
              if (i) {
                this.countValid++;
              }
              if (this.countValid == 2) {
                DEBUG && console.log("Valido");
                this.dataValid = "validData";
              } else {
                this.dataValid = "invalidData";
              }
            }
          });
      }
    );
  }

  yearItemSelected() {
    let i = 0;
    for (let item of this.years) {
      if (this.currentYear == Number(item.nombre)) {
        this.indexYear = i;
      }
      i++;
    }
  }

  dropYear($event) {
    this.year = $event.nombre;
    DEBUG && console.log($event);
  }

  dropPeriodo($event) {
    this.periodo = $event.periodo;
    this.dataPeriodo = $event.data;
    DEBUG && console.log($event);
  }

  dropDocument($event) {
    this.itemsDD = [];
    this.itemsDD = this.documentos[$event.id].campos;
  }

  obtenerTablas() {
    SessionUser.getInstance().setLoading(true);
    let parametros: Parametros = new Parametros();
    parametros.idioma = "Espanol";
    parametros.condicion = "";

    this.reportePersonalizado.obtenerTablas(parametros).subscribe(
      data => {
        this.documentos = data;
        data.forEach((item, index) => {
          if (item.numero != 0) {
            this.documentosAux.push({nombre: item.numero + " - " + item.descripcion, id: index});
          } else {
            this.documentosAux.push({nombre: item.descripcion, id: index});
          }
        });
        this.documentosList = [];
        Array.prototype.push.apply(this.documentosList, this.documentosAux);

        if (this.idReport != undefined && this.idReport != 0) {
          this.reporte = new ReportePersonalizado();
          this.reporte.idReportePersonalizado = this.idReport;
          this.obtenerReportePorId(this.reporte);
        } else {
          SessionUser.getInstance().setLoading(false);
        }
      },
      error => {
        DEBUG && console.log('@obtenerTablas: failed', error);
        SessionUser.getInstance().setLoading(false);
        SessionUser.getInstance().setMessageFailed('al obtener las opciones');
        document.getElementById('requestFailed').style.display = 'block';
      }
    );
  }

  peticionResultadosDeReportePersonalizado() {
    SessionUser.getInstance().setLoading(true);
    let parametros: Parametros = new Parametros();
    parametros.lstObjects = this.itemsDD2;
    parametros.usuario = SessionUser.getInstance().getUsuario();
    parametros.year = this.year;
    parametros.tipo = "Periodo";

    if (this.periodo == "Semestre") {
      parametros.semestre = this.dataPeriodo;
    } else if (this.periodo == "Trimestre") {
      parametros.trimestre = this.dataPeriodo;
    } else if (this.periodo == "Mes") {
      parametros.mes = this.dataPeriodo;
    }

    DEBUG && console.log(parametros);
    this.reportePersonalizado.peticionResultadosDeReportePersonalizado(parametros).subscribe(
      data => {
        DEBUG && console.log(data);
        if (data !== -1) {
          SessionUser.getInstance().getListDescargas().push({
            fileName: "reporte.xls", //se hace cambio para poder descargar reporte a medida.
            porcentaje: "0%",
            subText: "Preparando Descarga...",
            class: "barra"
          });
          this.userBAR.iniciarCheckInRealTime(data);
          SessionUser.getInstance().setLoading(false);
        } else {
          this.userService.noDisponible('No hay información disponible');
          document.getElementById('idNoDisponible').style.display = 'block';
          DEBUG && console.log("Sin datos");
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().getOpenDescargas().descargando = false;
        }
      },
      error => {
        DEBUG && console.log('@peticionResultadosDeReportePersonalizado: failed', error);
        SessionUser.getInstance().setLoading(false);
        SessionUser.getInstance().setMessageFailed('al exportar el reporte');
        document.getElementById('requestFailed').style.display = 'block';
      }
    );
  }

  deleteItem(i: number) {
    DEBUG && console.log(this.itemsDD2[i]);
    this.documentos.forEach((item, index) => {
      if (item.idTabla == this.itemsDD2[i].tabla.idTabla) {
        this.documentos[index].campos.push(this.itemsDD2[i]);
      }
    });
    this.itemsDD2.splice(i, 1);
  }

  exportar() {
    DEBUG && console.log(this.year);
    DEBUG && console.log(this.dataPeriodo);
    DEBUG && console.log(SessionUser.getInstance().getOpenDescargas().descargando);
    if (!SessionUser.getInstance().getOpenDescargas().descargando) {
      //SessionUser.getInstance().getOpenDescargas().descargando = true;
      SessionUser.getInstance().setOrigenDescarga("Reportes Personalizados");
      DEBUG && console.log('Primer if');
      if (this.year != undefined && this.dataPeriodo != undefined && this.itemsDD2.length > 0) {
        DEBUG && console.log("se ejecuta el reporte");
        SessionUser.getInstance().getOpenDescargas().descargando = true;
        this.peticionResultadosDeReportePersonalizado();
      }
    }
  }

  closeModal() {
    this.resetTabs = true;
    this.classAnimation = 'w3-animate-zoom-out';
  }

  guardar() {
    this.resetTabs = false;
    this.visibleCampos = true;
    DEBUG && console.log(this.itemsDD2);
    this.classAnimation = 'w3-animate-zoom';
  }

  guardarReport() {
    if (this.dataValid === "validData") {
      SessionUser.getInstance().setLoading(true);

      let reportePersonalizado: ReportePersonalizado = new ReportePersonalizado();
      reportePersonalizado.nombre = this.rpForm.get('rpNombre').value;
      reportePersonalizado.descripcion = this.rpForm.get('rpDescripcion').value;

      let usuario: Usuario = new Usuario();
      usuario.idUsuario = SessionUser.getInstance().getUserID();
      reportePersonalizado.autor = usuario;
      reportePersonalizado.fechaCreacion = new Date();
      reportePersonalizado.idReportePersonalizado = (this.idReport == undefined || this.idReport == 0) ? 0 : this.idReport;

      let lstPReportePersonalizado: any[] = [];
      this.itemsDD2.forEach((item, index) => {
        let pReportePersonalizado: PReportePersonalizados = new PReportePersonalizados();
        pReportePersonalizado.campoTabla = item;
        lstPReportePersonalizado.push(pReportePersonalizado);
      });

      reportePersonalizado.lstPReportesPersonalizados = lstPReportePersonalizado;

      DEBUG && console.log(reportePersonalizado);
      this.reportePersonalizado.guardarReporte(reportePersonalizado).subscribe(
        data => {
          DEBUG && console.log(data);

          /* Reset a tabs, para que cada opción se encuentre en el lugar correcto */
          this.resetTabs = true;
          setTimeout(() => {
            this.resetTabs = false;
            SessionUser.getInstance().setLoading(false);
          }, 100);
        },
        error => {
          DEBUG && console.log('@guardarReport: failed', error);
          SessionUser.getInstance().setLoading(false);
          SessionUser.getInstance().setMessageFailed('al guardar el reporte');
          document.getElementById('requestFailed').style.display = 'block';
        }
      );
    }
  }

  tabOption($event) {
    DEBUG && console.log($event);
    this.visibleCampos = !this.visibleCampos;
  }

  irCatalogo() {
    this.router.navigate(["protected/catalogoReportes"]);
  }

  obtenerReportePorId(reporte: ReportePersonalizado) {
    SessionUser.getInstance().setLoading(true);
    this.reportePersonalizado.obtenerReportePorId(reporte).subscribe(
      data => {
        this.reporte = data;
        this.rpForm.patchValue({
          rpNombre: this.reporte.nombre,
          rpDescripcion: this.reporte.descripcion
        });
        this.reporte.lstPReportesPersonalizados.forEach((item, index) => {
          this.itemsDD2.push(item.campoTabla);
          this.documentos.forEach((item2, index2) => {
            if (item2.idTabla == item.campoTabla.tabla.idTabla) {
              this.documentos[index2].campos.forEach((item3, index3) => {
                if (item3.idCampo == item.campoTabla.idCampo) {
                  this.documentos[index2].campos.splice(index3, 1);
                }
              });
            }
          });
        });
        DEBUG && console.log(this.reporte);
        SessionUser.getInstance().setLoading(false);
      },
      error => {
        DEBUG && console.log('@obtenerReportePorId: failed', error);
        SessionUser.getInstance().setLoading(false);
        SessionUser.getInstance().setMessageFailed('al obtener el reporte');
        document.getElementById('requestFailed').style.display = 'block';
      }
    );
  }

}
