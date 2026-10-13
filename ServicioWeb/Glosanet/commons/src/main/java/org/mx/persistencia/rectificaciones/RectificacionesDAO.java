package org.mx.persistencia.rectificaciones;

import java.io.File;
import java.util.List;

import org.mx.modelo.datastage.bo.RectificacionesBO;

public interface RectificacionesDAO {

	/**
	 * 
	 * @param list
	 * @return
	 */
	public boolean guardar(List<RectificacionesBO> list);

	/**
	 * 
	 * @param file
	 * @return
	 */
	public boolean loadDataLocal(File file);

}
