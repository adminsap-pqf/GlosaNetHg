package org.mx.persistencia.comun;

import org.mx.modelo.comun.bo.EmpresaBO;

public interface EmpresaDAO {

	public boolean updateVigencia(EmpresaBO empresa) throws Exception;
	
}
