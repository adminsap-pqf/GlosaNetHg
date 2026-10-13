import { Component, OnInit, Input, SimpleChanges } from '@angular/core';
import { Legend } from '../../../model/legend.class';

/* Dev Tools */
import { DEBUG } from "../../../settings/app.settings";

@Component({
  selector: 'gn-legends',
  templateUrl: './legends.component.html',
  styleUrls: ['./legends.component.scss']
})
export class LegendsComponent implements OnInit {

  @Input() items: Array<Legend>;

  constructor() { }

  ngOnInit() {
  }

  ngOnChanges(changes: SimpleChanges){
    DEBUG && console.log(changes);
  }

}
