import { TestBed, inject } from '@angular/core/testing';

import { PrevisionesService } from './previsiones.service';

describe('PrevisionesService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PrevisionesService]
    });
  });

  it('should be created', inject([PrevisionesService], (service: PrevisionesService) => {
    expect(service).toBeTruthy();
  }));
});
