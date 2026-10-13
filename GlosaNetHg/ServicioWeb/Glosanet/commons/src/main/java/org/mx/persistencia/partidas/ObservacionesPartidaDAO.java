package org.mx.persistencia.partidas;

import java.io.File;
import java.util.List;

import org.mx.modelo.datastage.bo.ObservacionesPartidaBO;

public interface ObservacionesPartidaDAO {

	/**
	 * 
	 * @param list
	 * @return
	 */
	public boolean guardar(List<ObservacionesPartidaBO> list);

	/**
	 * 
	 * @param file
	 * @return
	 */
	public boolean loadDataLocal(File file);

}
