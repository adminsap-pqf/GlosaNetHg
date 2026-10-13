import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MonitoreoTramiteComponent } from './monitoreo-tramite.component';

describe('MonitoreoTramiteComponent', () => {
  let component: MonitoreoTramiteComponent;
  let fixture: ComponentFixture<MonitoreoTramiteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MonitoreoTramiteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MonitoreoTramiteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
