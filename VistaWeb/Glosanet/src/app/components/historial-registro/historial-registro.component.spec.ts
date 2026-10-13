import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HistorialRegistroComponent } from './historial-registro.component';

describe('HistorialRegistroComponent', () => {
  let component: HistorialRegistroComponent;
  let fixture: ComponentFixture<HistorialRegistroComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HistorialRegistroComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HistorialRegistroComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
