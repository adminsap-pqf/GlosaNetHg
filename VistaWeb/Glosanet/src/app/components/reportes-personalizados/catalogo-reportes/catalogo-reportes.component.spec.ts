import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CatalogoReportesComponent } from './catalogo-reportes.component';

describe('CatalogoReportesComponent', () => {
  let component: CatalogoReportesComponent;
  let fixture: ComponentFixture<CatalogoReportesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CatalogoReportesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CatalogoReportesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
