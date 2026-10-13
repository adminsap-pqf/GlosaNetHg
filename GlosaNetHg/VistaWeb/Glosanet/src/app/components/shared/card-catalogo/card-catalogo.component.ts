import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { ReportePersonalizado } from '../../../model/reportePersonalizado.class';
import { Router } from '@angular/router';
import { ReportePersonalizadoService } from '../../../services/reporte-personalizado/reporte-personalizado.service';
import { SessionUser } from "../../../services/session/session.service";

/* Dev Tools */
import { DEBUG } from "../../../settings/app.settings";

@Component({
  selector: 'gn-card-catalogo',
  templateUrl: './card-catalogo.component.html',
  styleUrls: ['./card-catalogo.component.scss']
})
export class CardCatalogoComponent implements OnInit {
  @Input() data: ReportePersonalizado;
  @Output() reload: EventEmitter<any> = new EventEmitter<any>();

  classAnimation: String = 'w3-animate-zoom';

  constructor(private reportePersonalizado: ReportePersonalizadoService, private router: Router) { }

  ngOnInit() {
  }

  editReport() {
    this.router.navigate(['protected/reportesPersonalizados/' + this.data.idReportePersonalizado]);
  }

  closeModal() {
    this.classAnimation = 'w3-animate-zoom-out';
    setTimeout(() => {
        document.getElementById('id' + this.data.idReportePersonalizado).style.display = 'none'
      }, 198);
  }

  abrir() {
    this.classAnimation = 'w3-animate-zoom';
    document.getElementById('id' + this.data.idReportePersonalizado).style.display = 'block';
  }

  eliminar() {
    SessionUser.getInstance().setLoading(true);
    this.reportePersonalizado.borrarReporte(this.data).subscribe(
      data => {
        DEBUG && console.log('@eliminar: success', data);
        setTimeout(() => {
          let element = document.getElementById('id' + this.data.idReportePersonalizado);
          if (element)
            element.style.display = 'none';
        }, 198);
        setTimeout(() => this.reload.emit(true), 200);
        SessionUser.getInstance().setLoading(false);
      },
      error => {
        DEBUG && console.log('@eliminar: failed', error);
        SessionUser.getInstance().setLoading(false);
        SessionUser.getInstance().setMessageFailed('al eliminar el catálogo');
        document.getElementById('requestFailed').style.display = 'block';
      }
    );
  }
}
