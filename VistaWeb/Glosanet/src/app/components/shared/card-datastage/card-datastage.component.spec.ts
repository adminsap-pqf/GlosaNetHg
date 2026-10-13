import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CardDatastageComponent } from './card-datastage.component';

describe('CardDatastageComponent', () => {
  let component: CardDatastageComponent;
  let fixture: ComponentFixture<CardDatastageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CardDatastageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CardDatastageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
