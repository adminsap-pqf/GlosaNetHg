import { Component, OnInit, Output, Input, OnChanges, SimpleChanges, EventEmitter} from '@angular/core';
import * as _ from 'lodash';

/* Dev Tools */
import { DEBUG } from '../../../settings/app.settings';

@Component({
  selector: 'gn-filtro-tiempo',
  templateUrl: './filtro-tiempo.component.html',
  styleUrls: ['./filtro-tiempo.component.scss']
})
export class FiltroTiempoComponent implements OnInit, OnChanges {
  @Input() lstYears: string [] = [];
  @Input() reset: Boolean;
  @Input() activeTab = '';

  @Output() emitYear: EventEmitter<string> = new EventEmitter<string>();

  lstEstados: boolean[];

  constructor() { }

  ngOnInit() {
  }

  ngOnChanges(simple: SimpleChanges) {
    DEBUG && console.log(simple);
    if (this.lstYears !== undefined && this.lstYears.length > 0) {
      this.lstEstados = new Array(this.lstYears.length);
      this.lstEstados.fill(false);
      this.lstEstados[0] = true;
    }
    if (this.reset) {
      this.lstEstados.fill(false);
      this.lstEstados[0] = true;
    }

    if (simple.activeTab && simple.activeTab.currentValue && (this.activeTab === 'Partidas' || this.activeTab === 'E-doc')) {
      const index = _.findIndex(this.lstYears, o => {
        return o === this.activeTab;
      });
      if (index !== -1) {
        setTimeout(() => {
          this.lstEstados.fill(false);
          this.lstEstados[index] = true;
        }, 200);
      }

    }
  }

  activeItem(opc) {
    this.lstEstados.fill(false);
    this.lstEstados[opc] = true;
    this.emitYear.emit(this.lstYears[opc]);
  }
}
