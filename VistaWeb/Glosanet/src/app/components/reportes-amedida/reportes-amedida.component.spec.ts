import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportesAmedidaComponent } from './reportes-amedida.component';

describe('ReportesAmedidaComponent', () => {
  let component: ReportesAmedidaComponent;
  let fixture: ComponentFixture<ReportesAmedidaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReportesAmedidaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportesAmedidaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
