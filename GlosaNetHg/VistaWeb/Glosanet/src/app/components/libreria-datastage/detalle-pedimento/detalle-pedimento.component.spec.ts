import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DetallePedimentoComponent } from './detalle-pedimento.component';

describe('DetallePedimentoComponent', () => {
  let component: DetallePedimentoComponent;
  let fixture: ComponentFixture<DetallePedimentoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DetallePedimentoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DetallePedimentoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
