import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VucemTokenComponent } from './vucem-token.component';

describe('VucemTokenComponent', () => {
  let component: VucemTokenComponent;
  let fixture: ComponentFixture<VucemTokenComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VucemTokenComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VucemTokenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
