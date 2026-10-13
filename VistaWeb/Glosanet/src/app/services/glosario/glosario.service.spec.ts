import { TestBed, inject } from '@angular/core/testing';

import { GlosarioService } from './glosario.service';

describe('GlosarioService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [GlosarioService]
    });
  });

  it('should be created', inject([GlosarioService], (service: GlosarioService) => {
    expect(service).toBeTruthy();
  }));
});
