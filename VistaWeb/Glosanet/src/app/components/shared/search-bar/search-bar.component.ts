import { FormBuilder, FormGroup } from '@angular/forms';
import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';

/* Dev Tools */
import { DEBUG } from "../../../settings/app.settings";

@Component({
  selector: 'gn-search-bar',
  templateUrl: './search-bar.component.html',
  styleUrls: ['./search-bar.component.scss'],
})
export class SearchBarComponent implements OnInit, OnChanges {

  searchForm: FormGroup;
  searchTerm: string;
  imgClase: string;

  @Output() searchTermChanged: EventEmitter<string> = new EventEmitter<string>();
  @Input() placeholder: string;
  @Input() searchValue: string;
  @Input() imageSerch: boolean;
  @Input() reset: boolean;

  @Input() color: string = '';

  private imagen: string = './assets/images/lupa.svg';

  constructor(private _fb: FormBuilder) { }

  ngOnInit() {
    this.searchForm = this._fb.group({
      searchTerm: ['']
    });
    this.searchTerm = this.searchValue;
    this.searchForm.valueChanges.subscribe( () => {
      this.searchTerm = this.searchForm.get('searchTerm').value;
      this.returnSearchTerm();
    });
    if (!this.imageSerch) {
      this.imagen = './assets/images/lupa.svg';
      this.imgClase = '';
    }else {
      this.imagen = './assets/images/lupa.svg';
      this.imgClase = 'imagen2';
    }
  }
  ngOnChanges(changes: SimpleChanges) {
    if (changes.reset !== undefined && this.searchForm !== undefined) {
      DEBUG && console.log(changes);
      this.searchForm.patchValue({
        searchTerm: ''
      });
    }
  }

  changeSearchTerm() {
    this.searchTerm = this.searchForm.controls['searchTerm'].value;

  }

  returnSearchTerm() {
    this.searchTermChanged.emit(this.searchTerm);
  }


}
