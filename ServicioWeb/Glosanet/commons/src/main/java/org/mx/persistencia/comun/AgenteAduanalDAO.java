package org.mx.persistencia.comun;

import java.util.List;

import org.mx.modelo.comun.bo.AgenteAduanalBO;

public interface AgenteAduanalDAO {
	
	/***
	 * 
	 * @param bo
	 * @return
	 */
	public boolean modificarAgente(AgenteAduanalBO bo);
	
	/***
	 * 
	 * @param bo
	 * @return
	 */
	public boolean actualizarAgente(AgenteAduanalBO bo);
	
	/***
	 * 
	 * @param idCliente
	 * @return
	 */
	public int obtenerCantidadAgentesSinInformacion(int idCliente);
	
	/***
	 * 
	 * @param idCliente
	 * @param informacion
	 * @return
	 */
	public List<AgenteAduanalBO> obtenerAgentesYAgencias(int idCliente,boolean informacion);

}
