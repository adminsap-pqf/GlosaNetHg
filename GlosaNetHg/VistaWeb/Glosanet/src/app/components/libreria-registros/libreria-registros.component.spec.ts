import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LibreriaRegistrosComponent } from './libreria-registros.component';

describe('LibreriaRegistrosComponent', () => {
  let component: LibreriaRegistrosComponent;
  let fixture: ComponentFixture<LibreriaRegistrosComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LibreriaRegistrosComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LibreriaRegistrosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
