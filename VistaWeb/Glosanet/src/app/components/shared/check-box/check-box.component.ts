import { Component, OnInit, Output, Input, OnChanges, SimpleChanges, EventEmitter } from '@angular/core';

/* Dev Tools */
import { DEBUG } from "../../../settings/app.settings";

@Component({
  selector: 'gn-check-box',
  templateUrl: './check-box.component.html',
  styleUrls: ['./check-box.component.scss']
})
export class CheckBoxComponent implements OnInit, OnChanges {
  @Input() lstItems: string [] = [];
  @Input() disble: Boolean = true;
  @Input() direction: String;

  @Output() emitItem: EventEmitter<any> = new EventEmitter<any>();

  lstEstados: boolean[];
  lstSeleccionados: any [] = [];

  constructor() { }

  ngOnInit() {
  }

  ngOnChanges(simple: SimpleChanges) {
    DEBUG && console.log(this.lstItems);
    if (this.lstItems !== undefined && this.lstItems.length > 0) {
      this.lstEstados = new Array(this.lstItems.length);
      this.lstEstados.fill(false);
    }
  }

  activeItem(opc) {
    if (this.disble) {
      this.lstEstados[opc] = !this.lstEstados[opc];
      this.lstSeleccionados = [];
      let cont: any = 0;
      for (const item of  this.lstEstados) {
        if (item) {
          this.lstSeleccionados.push(cont);
        }
        cont++;
      }
      this.emitItem.emit(this.lstSeleccionados);
    }
  }

}
