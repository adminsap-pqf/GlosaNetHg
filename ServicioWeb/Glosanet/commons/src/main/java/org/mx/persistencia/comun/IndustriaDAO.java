package org.mx.persistencia.comun;

import java.util.List;

import org.mx.modelo.comun.bo.IndustriaBO;

public interface IndustriaDAO {

	public int addIndustria(IndustriaBO industria) throws Exception;

	public boolean updateIndustria(IndustriaBO industria) throws Exception;

	public List<IndustriaBO> findIndustria();

}
