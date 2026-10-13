import {
    Component,
    ElementRef,
    EventEmitter,
    Input,
    OnChanges,
    OnInit,
    Output,
    Renderer,
    SimpleChanges,
} from '@angular/core';

/* Dev Tools */
import { DEBUG } from "../../../settings/app.settings";

@Component({
  selector: 'gn-filter-list',
  templateUrl: './filter-list.component.html',
  styleUrls: ['./filter-list.component.scss']
})
export class FilterListComponent implements OnInit, OnChanges {
  @Output() itemSelected: EventEmitter<any> = new EventEmitter<any>();
  @Input() items: any;

  clickListener: Function;
  open: boolean;
  classActive = new Array();

  constructor(private renderer: Renderer, private elementRef: ElementRef) {
    this.clickListener = renderer.listenGlobal(
      'document',
      'click',
      (event: MouseEvent) => this.handleGlobalClick(event)
    );
   }

  ngOnInit() {
    this.open = false;
  }

  ngOnChanges(changes: SimpleChanges) {
    if (this.items !== undefined) {
      this.classActive.fill(false);
    }
  }
  /* Escucha el evento click globalmente */
  handleGlobalClick(event: MouseEvent): void {
    const withinElement = this.elementRef.nativeElement.contains(event.target);
    if (!this.elementRef.nativeElement.contains(event.target)) {
      this.close();
    }
  }
  close(): void {
    this.open = false;
  }

  reset() {
    this.classActive.fill(false);
  }

  select(item: any, i: any ): void {
    DEBUG && console.log(item);
    DEBUG && console.log(i);
    this.reset();
    this.classActive[i] = true;
    this.itemSelected.emit(i);
    this.close();
  }
  ngOnDestroy() {
    this.clickListener();
  }

}
