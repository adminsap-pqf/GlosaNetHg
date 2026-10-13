import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CatalogoProductosComponent } from './catalogo-productos.component';

describe('CatalogoProductosComponent', () => {
  let component: CatalogoProductosComponent;
  let fixture: ComponentFixture<CatalogoProductosComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CatalogoProductosComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CatalogoProductosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
