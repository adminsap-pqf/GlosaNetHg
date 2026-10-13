package org.mx.servicio.comun;

import java.util.List;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.HistorialImportacionesBO;

public interface HistorialImportacionesService {

	
	
	/**
	 * 
	 * @param idproducto
	 * @param flagProductoPartida
	 * @return
	 * @throws GlosaNetException
	 */
	public List<HistorialImportacionesBO> obtenerHistorialImportaciones(int idproducto, boolean  flagProductoPartida) throws GlosaNetException;
	/**
	 * 
	 * @param bo
	 * @return
	 * @throws Exception
	 */
	public boolean insertarHistorialImportacion(HistorialImportacionesBO bo) throws Exception;
	/**
	 * 
	 * @param bo
	 * @return
	 */
//	public List<HistorialPermisosBO> obtenerPermisos(Parametros bo);
	
	
}
