import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NuevoPermisoComponent } from './nuevo-permiso.component';

describe('NuevoPermisoComponent', () => {
  let component: NuevoPermisoComponent;
  let fixture: ComponentFixture<NuevoPermisoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NuevoPermisoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NuevoPermisoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
