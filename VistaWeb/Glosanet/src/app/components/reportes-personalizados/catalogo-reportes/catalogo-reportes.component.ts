import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ReportePersonalizadoService } from '../../../services/reporte-personalizado/reporte-personalizado.service';
import { SessionUser } from '../../../services/session/session.service';

/* Dev Tools */
import { DEBUG } from "../../../settings/app.settings";

@Component({
  selector: 'gn-catalogo-reportes',
  templateUrl: './catalogo-reportes.component.html',
  styleUrls: ['./catalogo-reportes.component.scss']
})
export class CatalogoReportesComponent implements OnInit {
  lstData: any [] = [];
  lstReport: any[] = [];
  lstReportSearch: any[] = [];
  reportsLength = 0;
  searchTerm = '';
  lstFiltro: any[] = [{ nombre: "Nombre (A-Z)", id: 1 }, { nombre: "Nombre (Z-A)", id: 2 }, { nombre: "Reciente", id: 3 }, { nombre: "Más Antigüo", id: 4 }];
  selectFiltro = {nombre: "Más Antigüo", id: 4 };

  constructor(private reportePersonalizado: ReportePersonalizadoService, private router: Router) { }

  ngOnInit() {
    this.obtenerReportes();
  }

  irNuevoReporte() {
    this.router.navigate(["protected/reportesPersonalizados/"+0]);
  }

  obtenerReportes() {
    SessionUser.getInstance().setLoading(true);
    this.lstData = [];
    this.lstReport = [];
    this.reportePersonalizado.obtenerReportes().subscribe(
      data => {
        this.lstData = data;
        this.lstReport = this.lstReport.concat(this.lstData);
        DEBUG && console.log(this.lstReport);
        SessionUser.getInstance().setLoading(false);
      },
      error => {
        DEBUG && console.log('@obtenerReportes: failed', error);
        SessionUser.getInstance().setLoading(false);
        SessionUser.getInstance().setMessageFailed('al obtener el catálogo');
        document.getElementById('requestFailed').style.display = 'block';
      }
    );
  }

  filtrar($event) {
    DEBUG && console.log($event);
    if ($event.id === 1) {
      this.lstReport.sort(function (a, b) {
        return (a.nombre.toUpperCase() > b.nombre.toUpperCase()) ? 1 : ((b.nombre.toUpperCase() > a.nombre.toUpperCase()) ? -1 : 0);
      });
    } else if($event.id === 2) {
      this.lstReport.sort(function (a, b) {
        return (a.nombre.toUpperCase() < b.nombre.toUpperCase()) ? 1 : ((b.nombre.toUpperCase() < a.nombre.toUpperCase()) ? -1 : 0);
      });
    } else if ($event.id === 3) {
      this.lstReport.sort(function (a, b) {
        return (a.fechaCreacion < b.fechaCreacion) ? 1 : ((b.fechaCreacion < a.fechaCreacion) ? -1 : 0);
      });
    } else if ($event.id === 4) {
      this.lstReport.sort(function (a, b) {
        return (a.fechaCreacion > b.fechaCreacion) ? 1 : ((b.fechaCreacion > a.fechaCreacion) ? -1 : 0);
      });
    }
  }

  changeSearchTerm($event) {
    this.lstReport = [];
    for (let p of this.lstData) {
      this.lstReport.push(p);
    }
    if ($event !== '') {
      this.lstReportSearch = [];
      for (let ps of this.lstReport) {
        if (ps.nombre !== undefined) {
          if (ps.nombre.toLowerCase().indexOf($event.toLowerCase()) !== -1) {
            this.lstReportSearch.push(ps);
          }
        }
      }
      if (this.lstReportSearch.length > 0) {
        this.lstReport = [];
        for (let p of this.lstReportSearch) {
          this.lstReport.push(p);
        }
        this.reportsLength = this.lstReport.length;
      } else {
        this.lstReport = [];
        this.reportsLength = this.lstReport.length;
      }
    } else {
      this.reportsLength = this.lstReport.length;
    }
  }
}
