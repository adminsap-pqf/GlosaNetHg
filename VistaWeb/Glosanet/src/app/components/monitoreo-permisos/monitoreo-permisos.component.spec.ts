import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MonitoreoPermisosComponent } from './monitoreo-permisos.component';

describe('MonitoreoPermisosComponent', () => {
  let component: MonitoreoPermisosComponent;
  let fixture: ComponentFixture<MonitoreoPermisosComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MonitoreoPermisosComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MonitoreoPermisosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
