import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ForecastNoticeComponent } from './forecast-notice.component';

describe('ForecastNoticeComponent', () => {
  let component: ForecastNoticeComponent;
  let fixture: ComponentFixture<ForecastNoticeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ForecastNoticeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ForecastNoticeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
