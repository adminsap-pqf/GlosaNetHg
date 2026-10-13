import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VistaCalendarioComponent } from './vista-calendario.component';

describe('VistaCalendarioComponent', () => {
  let component: VistaCalendarioComponent;
  let fixture: ComponentFixture<VistaCalendarioComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VistaCalendarioComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VistaCalendarioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
