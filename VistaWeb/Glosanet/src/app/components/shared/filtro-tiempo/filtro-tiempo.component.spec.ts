import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FiltroTiempoComponent } from './filtro-tiempo.component';

describe('FiltroTiempoComponent', () => {
  let component: FiltroTiempoComponent;
  let fixture: ComponentFixture<FiltroTiempoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FiltroTiempoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FiltroTiempoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
