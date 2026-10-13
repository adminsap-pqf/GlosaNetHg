import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchPedimentoComponent } from './search-pedimento.component';

describe('SearchPedimentoComponent', () => {
  let component: SearchPedimentoComponent;
  let fixture: ComponentFixture<SearchPedimentoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SearchPedimentoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchPedimentoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
