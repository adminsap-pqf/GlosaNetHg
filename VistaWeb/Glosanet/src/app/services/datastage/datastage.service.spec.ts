import { TestBed } from '@angular/core/testing';

import { DatastageService } from './datastage.service';

describe('DatastageService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: DatastageService = TestBed.get(DatastageService);
    expect(service).toBeTruthy();
  });
});
