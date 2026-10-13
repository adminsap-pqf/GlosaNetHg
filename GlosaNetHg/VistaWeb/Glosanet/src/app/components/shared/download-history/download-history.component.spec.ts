import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DownloadHistoryComponent } from './download-history.component';

describe('DownloadHistoryComponent', () => {
  let component: DownloadHistoryComponent;
  let fixture: ComponentFixture<DownloadHistoryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DownloadHistoryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DownloadHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
