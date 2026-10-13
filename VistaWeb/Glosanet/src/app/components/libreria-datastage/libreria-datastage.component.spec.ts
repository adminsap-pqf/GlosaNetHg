import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LibreriaDatastageComponent } from './libreria-datastage.component';

describe('LibreriaDatastageComponent', () => {
  let component: LibreriaDatastageComponent;
  let fixture: ComponentFixture<LibreriaDatastageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LibreriaDatastageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LibreriaDatastageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
