import { Component, OnInit, Input} from '@angular/core';
import { DatosGenerales } from '../../../model/datosGenerales.class';
import {ROUTE_DATASTAGES} from "../../../util/common.protocols";

@Component({
  selector: 'gn-card-bitacora',
  templateUrl: './card-bitacora.component.html',
  styleUrls: ['./card-bitacora.component.scss']
})
export class CardBitacoraComponent implements OnInit {
  @Input() data: DatosGenerales;
  @Input() idCliente: String;
  @Input() rfc: String;

  rutaGlosaDocs: string = ROUTE_DATASTAGES;

  constructor() { }

  ngOnInit() {
  }

}
