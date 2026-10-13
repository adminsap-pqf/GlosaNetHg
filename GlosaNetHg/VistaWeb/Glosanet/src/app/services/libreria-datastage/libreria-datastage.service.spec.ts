import { TestBed, inject } from '@angular/core/testing';

import { LibreriaDatastageService } from './libreria-datastage.service';

describe('LibreriaDatastageService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [LibreriaDatastageService]
    });
  });

  it('should be created', inject([LibreriaDatastageService], (service: LibreriaDatastageService) => {
    expect(service).toBeTruthy();
  }));
});
