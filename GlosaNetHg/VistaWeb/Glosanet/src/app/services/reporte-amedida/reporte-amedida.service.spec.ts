import { TestBed, inject } from '@angular/core/testing';

import { ReporteAmedidaService } from './reporte-amedida.service';

describe('ReporteAmedidaService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ReporteAmedidaService]
    });
  });

  it('should be created', inject([ReporteAmedidaService], (service: ReporteAmedidaService) => {
    expect(service).toBeTruthy();
  }));
});
