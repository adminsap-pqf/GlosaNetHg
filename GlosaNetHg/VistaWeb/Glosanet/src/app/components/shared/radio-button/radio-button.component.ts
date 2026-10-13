import { Component, OnInit, Output, Input, OnChanges, SimpleChanges, EventEmitter } from '@angular/core';

/* Dev Tools */
import { DEBUG } from "../../../settings/app.settings";

@Component({
  selector: 'gn-radio-button',
  templateUrl: './radio-button.component.html',
  styleUrls: ['./radio-button.component.scss']
})
export class RadioButtonComponent implements OnInit, OnChanges {
  @Input() lstItems: string [] = [];
  @Input() disble: Boolean = true;
  @Input() direction: String;
  @Input() imgSize: String = '26.5px';
  @Input() borderBottom: Boolean = false;
  @Input() widthTotal: String = '120px';
  @Input() colorText: String = '#818192';
  @Input() colorTextSelected: String = '#818192';
  @Input() colorTextHover: String = '#818192';
  @Input() fontWeight: String = 'normal';
  @Input() hover: String = '';
  @Input() subFijo: String = '';

  @Output() emitItem: EventEmitter<any> = new EventEmitter<any>();

  lstEstados: boolean[];
  colorTextAux: String = '';
  colorTextSelectedAux: String = '';
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
      this.lstEstados.fill(false);
      this.lstEstados[opc] = true;
      this.emitItem.emit(opc);
    }
  }

}
