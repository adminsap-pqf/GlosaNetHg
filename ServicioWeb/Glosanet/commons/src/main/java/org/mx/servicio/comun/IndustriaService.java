package org.mx.servicio.comun;

import java.util.List;

import org.mx.modelo.comun.bo.IndustriaBO;

public interface IndustriaService {

	public IndustriaBO agregarIndustria(IndustriaBO industria) throws Exception;

	public boolean actualizarIndustria(IndustriaBO industria) throws Exception;

	public List<IndustriaBO> buscarIndustrias();

}
