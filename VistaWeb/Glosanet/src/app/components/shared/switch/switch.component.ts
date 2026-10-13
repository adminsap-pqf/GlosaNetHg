import { Component, OnInit, Input, Output, EventEmitter, SimpleChanges } from '@angular/core';

/* Dev Tools */
import { DEBUG } from "../../../settings/app.settings";

@Component({
  selector: 'gn-switch',
  templateUrl: './switch.component.html',
  styleUrls: ['./switch.component.scss']
})
export class SwitchComponent implements OnInit {

  @Input() isActive: boolean;
  @Input() color: string;
  @Input() confirmActive: boolean = false;
  @Output() valueChanged: EventEmitter<boolean> = new EventEmitter<boolean>();

  public switchState = 'inactive';
  private firstOnInit = 0;
  constructor() { }

  ngOnInit() {
    if (this.color === 'green') {
      this.switchState = this.isActive ? 'activeGreen' : 'inactive';
    }
    if (this.color === 'gray') {
      this.switchState = this.isActive ? 'activeGreen' : 'inactive';
    }
  }

  ngOnChanges(changes: SimpleChanges) {
    if (this.firstOnInit === 0) {
      this.ngOnInit();
    }
    //this.switchState = this.isActive ? 'activeGreen' : 'inactive';
    if(changes.isActive !== undefined){
      if(changes.isActive.currentValue){
        this.switchState = 'activeGreen';
      }else{
        this.switchState = 'inactive';
      }
    }
    this.firstOnInit++;
  }

  changeStatus() {
    if (!this.confirmActive) {
      this.isActive = !this.isActive;
      this.valueChanged.emit(this.isActive);
      if (this.color === 'green') {
        this.switchState = this.isActive ? 'activeGreen' : 'inactive';
      }
      if (this.color === 'gray') {
        this.switchState = this.isActive ? 'activeGreen' : 'inactive';
      }
    } else {
      this.valueChanged.emit(this.isActive);
    }

    DEBUG && console.log(this.switchState);
  }

  changeEvent($event) {

  }
}
