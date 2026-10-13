import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportesPersonalizadosComponent } from './reportes-personalizados.component';

describe('ReportesPersonalizadosComponent', () => {
  let component: ReportesPersonalizadosComponent;
  let fixture: ComponentFixture<ReportesPersonalizadosComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReportesPersonalizadosComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportesPersonalizadosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
