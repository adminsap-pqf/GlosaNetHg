import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GridCalendarComponent } from './grid-calendar.component';

describe('GridCalendarComponent', () => {
  let component: GridCalendarComponent;
  let fixture: ComponentFixture<GridCalendarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GridCalendarComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GridCalendarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
