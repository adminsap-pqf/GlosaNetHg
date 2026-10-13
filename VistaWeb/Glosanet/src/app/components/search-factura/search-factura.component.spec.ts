import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchFacturaComponent } from './search-factura.component';

describe('SearchFacturaComponent', () => {
  let component: SearchFacturaComponent;
  let fixture: ComponentFixture<SearchFacturaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SearchFacturaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchFacturaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
