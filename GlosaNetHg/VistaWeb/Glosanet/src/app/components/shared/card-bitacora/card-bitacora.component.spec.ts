import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CardBitacoraComponent } from './card-bitacora.component';

describe('CardBitacoraComponent', () => {
  let component: CardBitacoraComponent;
  let fixture: ComponentFixture<CardBitacoraComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CardBitacoraComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CardBitacoraComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
