package org.mx.persistencia.comun;

import java.util.List;

import org.mx.modelo.comun.bo.HistorialImportacionesBO;
import org.mx.modelo.comun.bo.Parametros;
import org.mx.modelo.permisos.org.mx.bo.HistorialPermisosBO;

public interface HistorialImportacionesDAO {
	
	/**
	 * 
	 * @param idproducto
	 * @param informacion
	 * @return
	 */
	public List<HistorialImportacionesBO> obteneHistorialImportaciones (int idproducto, boolean informacion);
	/**
	 * 
	 * @param bo
	 * @return
	 * @throws Exception
	 */
	public boolean  insertarHistorialImportacion(HistorialImportacionesBO  bo) throws Exception;
	/**
	 * 
	 * @param bo
	 * @return
	 */
	public List<HistorialPermisosBO> obtenerPermisos(Parametros bo);
	
}
