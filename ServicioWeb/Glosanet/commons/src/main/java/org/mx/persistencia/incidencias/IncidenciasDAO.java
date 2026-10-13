package org.mx.persistencia.incidencias;

import java.io.File;
import java.util.List;

import org.mx.modelo.datastage.bo.IncidenciasReconocimientoAduaneroBO;

public interface IncidenciasDAO {

	/**
	 * 
	 * @param list
	 * @return
	 */
	public boolean guardar(List<IncidenciasReconocimientoAduaneroBO> list);

	/**
	 * 
	 * @param file
	 * @return
	 */
	public boolean loadDataLocal(File file);

}
