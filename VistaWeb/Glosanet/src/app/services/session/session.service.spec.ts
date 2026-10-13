import { TestBed, inject } from '@angular/core/testing';

import { SessionUser } from './session.service';

describe('SessionService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SessionUser]
    });
  });

  it('should be created', inject([SessionUser], (service: SessionUser) => {
    expect(service).toBeTruthy();
  }));
});
