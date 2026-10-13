import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PreauditoriaComponent } from './preauditoria.component';

describe('PreauditoriaComponent', () => {
  let component: PreauditoriaComponent;
  let fixture: ComponentFixture<PreauditoriaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PreauditoriaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PreauditoriaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
