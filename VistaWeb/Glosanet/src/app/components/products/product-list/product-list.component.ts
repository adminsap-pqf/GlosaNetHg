import {
  Component,
  EventEmitter,
  Input,
  OnDestroy,
  OnInit,
  Output,
  SimpleChange,
  ViewChild
} from '@angular/core';
import { animate, style, transition, trigger } from '@angular/animations';
import { IPageInfo, VirtualScrollerComponent } from 'ngx-virtual-scroller';
import * as _ from 'lodash';

/* Dev Tools */
import { DEBUG } from '../../../settings/app.settings';
import * as protocols from "../../../util/common.protocols";
const objectID = 'product-list';

@Component({
  selector: 'gn-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.scss'],
  animations: [
    trigger('fadeInOut', [
      transition(':enter', [
        style({opacity: 0}),
        animate(400, style({opacity: 0.7}))
      ]),
      transition(':leave', [
        animate(400, style({opacity: 0}))
      ])
    ])
  ]
})
export class ProductListComponent implements OnInit {

  @ViewChild(VirtualScrollerComponent, { static: false })
  private virtualScroller: VirtualScrollerComponent;

  /* Data Controller */
  @Input() buffer: any[] = [];
  @Input() firstItem = {};
  @Input() grandTotal = 0;
  @Input() currentTotal = 0;
  @Input() currentPage = 0;
  @Input() loading = false;
  @Input() isList = true;
  @Input() showRecords = true;
  @Input() showPermission = false;
  /* TODO: Función para obtener las siguientes páginas */
  @Input() getData = null;

  @Input() showTotal = true;
  @Input() withoutMessage = 'No hay información que mostrar';

  @Output() emitCurrentPage: EventEmitter<number> = new EventEmitter<number>();
  @Output() emitLoading: EventEmitter<boolean> = new EventEmitter<boolean>();
  @Output() emitSelectItem: EventEmitter<any> = new EventEmitter<any>();

  scrollItems: any[] = [];
  timer;

  constructor() { }

  ngOnInit() { }

  ngOnChanges(simpleChanges: SimpleChange) {
    // tslint:disable-next-line:no-unused-expression
    DEBUG && console.log(objectID + '@ngOnChanges', simpleChanges);
  }

  fetchMore(event: IPageInfo) {
    if (event.endIndex !== this.buffer.length - 1) { return; }
    const numItems = Math.floor((window.screen.width - 110) / 225) * 4;
    const size = protocols.PAGING_LIMIT < numItems ? numItems : protocols.PAGING_LIMIT;
    if (event.endIndex !== this.currentTotal - 1 && this.currentTotal > 0) {
      const totalPages = this.currentTotal >= size ?  Math.ceil(this.currentTotal / size) : 0;
      if ( this.currentPage > totalPages) {
        this.emitCurrentPage.emit(totalPages);
        return;
      }
      if (event.startIndex !== 0) { this.emitLoading.emit(true); }
      this.emitCurrentPage.emit(this.currentPage + 1);
      this.fetchNextChunk(this.buffer.length, size)
        .then(chunk => {
          this.buffer = this.buffer.concat(chunk);
          this.emitLoading.emit(false);
        }, () => {
          this.emitLoading.emit(false);
        });
    }
  }

  fetchNextChunk(skip: number, limit: number): Promise<any[]> {
    return new Promise((resolve, reject) => {
      clearTimeout(this.timer);
      this.timer = setTimeout( async() => {
        if (skip < this.grandTotal) {
          const data: any = await this.getData(true);
          this.emitLoading.emit(false);
          return resolve(data);
        }
        this.emitLoading.emit(false);
        reject();
      }, 200);
    });
  }

  scrollTo() {
    this.virtualScroller.scrollInto(this.firstItem);
  }

  selectItem(item: any) {
    this.emitSelectItem.emit(item);
  }

}
