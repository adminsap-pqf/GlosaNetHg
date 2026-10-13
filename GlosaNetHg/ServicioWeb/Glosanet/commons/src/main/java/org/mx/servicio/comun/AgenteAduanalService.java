package org.mx.servicio.comun;

import java.util.List;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.AgenciaAduanalBO;
import org.mx.modelo.comun.bo.AgenteAduanalBO;

public interface AgenteAduanalService {

	/***
	 * 
	 * @param bo
	 * @return
	 */
	public boolean agregarAgenciaAduanal(AgenciaAduanalBO bo);
	
	/***
	 * 
	 * @param bo
	 * @return
	 */
	public boolean actualizarAgenteAduanal(AgenteAduanalBO bo);
	
	/***
	 * 
	 * @param bo
	 * @return
	 */
	public boolean actualizarDatosAgenteAduanal(AgenteAduanalBO bo);
	
	/***
	 * 
	 * @param bo
	 * @return
	 */
	public boolean actualizarAgenciaAduanal(AgenciaAduanalBO bo);
	
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
	 * @throws GlosaNetException
	 */
	public List<AgenteAduanalBO> obtenerAgentesYAgencias(int idCliente,boolean informacion) throws GlosaNetException;

}
