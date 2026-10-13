import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LibreriaPermisosComponent } from './libreria-permisos.component';

describe('LibreriaPermisosComponent', () => {
  let component: LibreriaPermisosComponent;
  let fixture: ComponentFixture<LibreriaPermisosComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LibreriaPermisosComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LibreriaPermisosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
