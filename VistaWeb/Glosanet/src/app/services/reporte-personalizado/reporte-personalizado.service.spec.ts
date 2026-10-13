import { TestBed, inject } from '@angular/core/testing';

import { ReportePersonalizadoService } from './reporte-personalizado.service';

describe('ReportePersonalizadoService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ReportePersonalizadoService]
    });
  });

  it('should be created', inject([ReportePersonalizadoService], (service: ReportePersonalizadoService) => {
    expect(service).toBeTruthy();
  }));
});
