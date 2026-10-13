import { TestBed } from '@angular/core/testing';

import { FileMonitoringService } from './file-monitoring.service';

describe('FileMonitoringService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: FileMonitoringService = TestBed.get(FileMonitoringService);
    expect(service).toBeTruthy();
  });
});
