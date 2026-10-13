import {Component, ElementRef, EventEmitter, Input, OnInit, Output, Renderer, SimpleChanges} from '@angular/core';
/* Dev Tools */
import {DEBUG} from "../../../settings/app.settings";

@Component({
  selector: 'gn-drop-list',
  templateUrl: './drop-list.component.html',
  styleUrls: ['./drop-list.component.scss']
})
export class DropListComponent implements OnInit {

  @Output() valueDropList: EventEmitter<any> = new EventEmitter();
  @Input() items: any;
  @Input() isSearch: boolean = true;
  @Input() isCategory: boolean = false;
  @Input() size: String = '';
  @Input() align: String = '';
  @Input() itemSelect: any;
  @Input() widthContent: any = '100%';
  @Input() marginLeftContent: any = '0px';
  @Input() tooltip: boolean = false;

  @Input() lineaColor: string = '';

  @Input() color: String = '';
  @Input() fontSize: String = '';

  @Input() fontWeight: string = '';
  @Input() fontFamily: string = '';

  @Input() radio: boolean = false;

  @Input() flecha: boolean = true;

  @Input() clear: boolean;

  @Input() activeValidData: boolean = false;
  @Input() activeFirst: boolean = false;
  @Input() resetValidate: boolean = false;

  //@Input() placeH: string = '';

  activeValid: boolean = false;
  isOpen : boolean = false;

  clickListener: Function;
  showDropList: boolean;
  dropListClass: string = 'dropList';
  title: string;
  placeholder: any = 'Search';
  searchTerm: string = null;
  arrayAux: any = [];
  search: any = [];
  resetSearch: boolean;

  radioArray: boolean[];

  title1: string = 'Todos los Eventos';
  title2: string = 'Todos';

  constructor(private renderer: Renderer, private elementRef: ElementRef) {
    this.clickListener = renderer.listenGlobal(
      'document',
      'click',
      (event: MouseEvent) => this.handleGlobalClick(event)
    );
  }

  ngOnInit() {
    this.showDropList = false;
    //this.title = this.placeH;
    //this.dropListClass = 'dropList';
    //this.fillArrayAux();
    this.activeValid = this.activeValidData && this.activeFirst;
  }

  ngOnChanges(changes: SimpleChanges) {
    DEBUG && console.log(changes);
    if (changes.items != undefined) {
      DEBUG && console.log('entra');
      this.fillArrayAux();
    }
    if (changes.clear !== undefined) {
      if (changes.clear.currentValue !== undefined && !changes.firstChange && changes.clear.currentValue) {
        this.title = '';
        this.title1 = '';
        this.title2 = '';
      }
    }
    if (changes.itemSelect !== undefined) {
      DEBUG && console.log(changes.itemSelect);
      this.dropListClass = 'dropListSelect';
      if (changes.itemSelect.currentValue !== undefined && !changes.firstChange) {
        this.selectItem(this.itemSelect, 0);
      } else {
        this.title1 = '';
      }
    }
    if (changes.resetValidate) {
      if (changes.resetValidate.currentValue !== undefined && !changes.firstChange && changes.resetValidate.currentValue) {
        this.activeValid = false;
        this.isOpen = false;
      }
    }
    if (changes.activeValidData) {
      if (changes.activeValidData.currentValue !== undefined && !changes.firstChange && changes.activeValidData.currentValue) {
        this.activeValid = this.activeValidData && this.activeFirst;
      }
      if (changes.activeValidData.currentValue !== undefined && !changes.firstChange && !changes.activeValidData.currentValue) {
        this.activeValid = false;
      }
    }
  }

  /* Escucha el evento click globalmente */
  handleGlobalClick(event: MouseEvent): void {
    const withinElement = this.elementRef.nativeElement.contains(event.target);
    if (!this.elementRef.nativeElement.contains(event.target)) {
      this.closeDropList();
    }
  }

  /* Cierra el drop list */
  closeDropList(): void {
    if (this.activeValidData && this.isOpen) {
      this.activeValid = this.title ? false : true;
    }
    this.showDropList = false;
  }

  /* Abre el drop list */
  openDropList($event): void {
    $event.stopPropagation();
    this.isOpen = true;
    this.showDropList = !this.showDropList;
  }

  /* Selecciona opcion del drop list */
  selectItem(item: any, index, $event?): void {
    $event !== undefined ? $event.stopPropagation() : null;
    DEBUG && console.log(item);
    if (!this.radio) {

      if (this.activeValidData && this.isOpen) {
        this.activeValid = this.title ? false : true;
      }

      this.title = item.nombre;
    } else {
      if (item.seccion == 1) {
        this.title1 = item.nombre;
      } else {
        this.title2 = item.nombre
      }
    }

    DEBUG && console.log(this.title);
    DEBUG && console.log(this.title1);
    DEBUG && console.log(this.title2);

    if (!this.radio) {
      this.closeDropList();
    }
    this.resetSearch = !this.resetSearch;
    this.dropListClass = 'dropListSelect';
    this.valueDropList.emit(item);

    DEBUG && console.log(index);
    let seccion = item.seccion;
    let i = 0;
    if (this.radio) {
      for (let item of this.arrayAux) {
        if (seccion == item.seccion) {
          this.radioArray[i] = false;
        }
        i++;
      }
      this.radioArray[index] = true;
      DEBUG && console.log(this.radioArray);
    }
    DEBUG && console.log(this.title);
    DEBUG && console.log(this.radio)

    //this.fillArrayAux();
  }

  /* Busqueda de search bar */
  changeSearchTerm($event) {
    this.arrayAux = [];
    for (let item of this.items) {
      this.arrayAux.push(item);
    }
    if ($event !== '') {
      this.search = [];
      for (let item of this.arrayAux) {
        if (item.nombre.toLowerCase().indexOf($event.toLowerCase()) !== -1) {
          this.search.push(item);
        }
      }
      if (this.search.length > 0) {
        this.arrayAux = [];
        for (let item of this.search) {
          this.arrayAux.push(item);
        }
      } else {
        this.arrayAux = [];
      }
    } else {
      this.arrayAux = [];
      for (let item of this.items) {
        this.arrayAux.push(item);
      }
    }
  }

  /* Llenar el arreglo auxiliar */
  fillArrayAux() {
    this.arrayAux = [];
    for (let item of this.items) {
      this.arrayAux.push(item);
    }

    this.radioArray = new Array<boolean>(this.arrayAux.length).fill(false);
    DEBUG && console.log(this.radioArray);
    this.radioArray[4] = true;
    this.radioArray[6] = true;
  }

  ngOnDestroy() {
    this.clickListener();
  }

}
