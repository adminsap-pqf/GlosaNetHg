import { TestBed, inject } from '@angular/core/testing';

import { CatalogoProductosService } from './catalogo-productos.service';

describe('CatalogoProductosService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [CatalogoProductosService]
    });
  });

  it('should be created', inject([CatalogoProductosService], (service: CatalogoProductosService) => {
    expect(service).toBeTruthy();
  }));
});
