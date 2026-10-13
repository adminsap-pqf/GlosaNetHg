import { Component, OnInit, Output, Input, OnChanges, SimpleChanges, EventEmitter } from '@angular/core';

/* Dev Tools */
import { DEBUG } from "../../../settings/app.settings";

@Component({
  selector: 'gn-wizard',
  templateUrl: './wizard.component.html',
  styleUrls: ['./wizard.component.scss']
})
export class WizardComponent implements OnInit, OnChanges {
  @Input() lstItems: string [] = [];
  @Input() pWizard: any = 0;
  @Input() check: boolean;    /* false = se esconde la palomita | true = se muestra la palomita */
  @Input() tabs: boolean;  /* false = Actua como wizard | true = actua como tab */

  @Output() tabSelected: EventEmitter<number> = new EventEmitter<number>();

  lstEstados: string[]= new Array;
  lstOk: boolean[] = new Array;

  constructor() { }

  ngOnInit() {
  }

  ngOnChanges(simple: SimpleChanges) {
    DEBUG && console.log(this.lstItems);
    if (this.lstItems !== undefined && this.lstItems.length > 0) {
      if(this.tabs == false){
        this.lstEstados[this.pWizard] = 'itemSelected';
        if (this.pWizard > 0) {
          this.lstEstados[this.pWizard - 1] = 'itemOk';
          this.lstOk[this.pWizard - 1] = true;
        }
        if ((this.lstEstados[this.pWizard + 1] === 'itemSelected' || this.lstEstados[this.pWizard + 1] === 'itemOk')
            && this.lstOk[this.pWizard + 1] === true) {
          this.lstEstados[this.pWizard + 1] = 'itemOk';
        } else {
          this.lstEstados[this.pWizard + 1] = 'itemGray';
        }

      }else{
        this.lstEstados.fill('itemGray');
        this.lstOk.fill(false);
        this.lstEstados[this.pWizard] = 'itemSelected';
      }
    }
  }

  activeItem(opc) {
    if(this.tabs){
      this.tabSelected.emit(opc);
      this.lstEstados.fill('itemGray');
      this.lstEstados[opc] = 'itemSelected';
    }
  }
}
