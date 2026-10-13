package org.mx.persistencia.partidas;

import java.io.File;
import java.util.List;

import org.mx.modelo.datastage.bo.PermisosPartidaBO;

public interface PermisosPartidaDAO {

	/**
	 * 
	 * @param list
	 * @return
	 */
	public boolean guardar(List<PermisosPartidaBO> list);

	/**
	 * 
	 * @param file
	 * @return
	 */
	public boolean loadDataLocal(File file);

}
