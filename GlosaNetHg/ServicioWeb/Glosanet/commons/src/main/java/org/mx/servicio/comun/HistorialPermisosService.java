package org.mx.servicio.comun;

import java.util.List;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.permisos.org.mx.bo.HistorialPermisosBO;
import org.mx.modelo.permisos.org.mx.bo.PermisoBO;


public interface HistorialPermisosService {

	public List<PermisoBO> obtenerPermisos() throws GlosaNetException;
	
	/**
	 * 
	 * @param numPermiso
	 * @return
	 */
	public int obtenerPermiso(int numPermiso);
	
	/**
	 * 
	 * @param bo
	 * @param flagProductoPartida
	 * @return
	 * @throws Exception
	 */
	public boolean insertarPermisos(HistorialPermisosBO bo) throws Exception;
		
}
