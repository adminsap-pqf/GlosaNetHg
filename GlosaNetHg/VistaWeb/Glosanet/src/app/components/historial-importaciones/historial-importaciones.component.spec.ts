import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HistorialImportacionesComponent } from './historial-importaciones.component';

describe('HistorialImportacionesComponent', () => {
  let component: HistorialImportacionesComponent;
  let fixture: ComponentFixture<HistorialImportacionesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HistorialImportacionesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HistorialImportacionesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
