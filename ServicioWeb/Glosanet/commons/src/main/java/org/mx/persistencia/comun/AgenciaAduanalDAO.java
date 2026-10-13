package org.mx.persistencia.comun;

import org.mx.modelo.comun.bo.AgenciaAduanalBO;

public interface AgenciaAduanalDAO {
	
	/***
	 * 
	 * @param bo
	 * @return
	 */
	public boolean insertarAgenciaAduanal(AgenciaAduanalBO bo);
	
	/***
	 * 
	 * @param bo
	 * @return
	 */
	public boolean actualizarAgenciaAduanal(AgenciaAduanalBO bo);

}
