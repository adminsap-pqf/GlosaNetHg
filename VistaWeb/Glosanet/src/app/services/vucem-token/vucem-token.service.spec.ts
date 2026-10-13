import { TestBed } from '@angular/core/testing';

import { VucemTokenService } from './vucem-token.service';

describe('VucemTokenService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: VucemTokenService = TestBed.get(VucemTokenService);
    expect(service).toBeTruthy();
  });
});
