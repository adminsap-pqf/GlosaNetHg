import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FileMonitoringComponent } from './file-monitoring.component';

describe('FileMonitoringComponent', () => {
  let component: FileMonitoringComponent;
  let fixture: ComponentFixture<FileMonitoringComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FileMonitoringComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FileMonitoringComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
