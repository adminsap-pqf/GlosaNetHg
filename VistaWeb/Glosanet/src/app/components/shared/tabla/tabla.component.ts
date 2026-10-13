import { Component, OnInit, Input, SimpleChanges} from '@angular/core';

/* Dev Tools */
import { DEBUG } from "../../../settings/app.settings";

@Component({
  selector: 'gn-tabla',
  templateUrl: './tabla.component.html',
  styleUrls: ['./tabla.component.scss']
})
export class TablaComponent implements OnInit {
  @Input() lstCabeceras: any [] = [];
  @Input() lstData:any [] = [];
  @Input() lstSecciones:any [] = [];
  @Input() scroll: boolean = false;
  @Input() minWidth: String;
  @Input() nota: String;
  @Input() selectedOption: number = 0;

  @Input() searchVar:string = '';

  lstColumn:any[]=[];
  lstColumn3:any[]=[];

  constructor() { }

  ngOnInit() {
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes.lstData != undefined) {
      DEBUG && console.log(changes.lstData);
      //this.lstColumn2 = lstData.
    }
  }

  splitColumn(text:String):any{
    if(text != undefined){
      return text.split("|");
    }
    return this.lstColumn;
  }

}
