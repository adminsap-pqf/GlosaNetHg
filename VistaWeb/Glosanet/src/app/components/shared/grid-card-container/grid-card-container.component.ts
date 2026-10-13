import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { DatosGenerales } from '../../../model/datosGenerales.class';
import { Router } from '@angular/router';

@Component({
  selector: 'gn-grid-card-container',
  templateUrl: './grid-card-container.component.html',
  styleUrls: ['./grid-card-container.component.scss']
})
export class GridCardContainerComponent implements OnInit {
  @Input() lstData: DatosGenerales[];
  @Input() idCliente: String;
  @Input() rfc: String;
  @Input() tipo: Boolean;

  @Output() reload2: EventEmitter<any> = new EventEmitter<any>();

  constructor(private router: Router) { }

  ngOnInit() {
  }

  editReport(i: any) {
    this.router.navigate(['reportesPersonalizados/' + i]);
  }

  reload($event){
    this.reload2.emit(true);
  }

}
