package org.mx.persistencia.partidas;

import java.io.File;
import java.util.List;

import org.mx.modelo.datastage.bo.PartidaBO;

public interface PartidasDAO {

	/**
	 * 
	 * @param list
	 * @return
	 */
	public boolean guardar(List<PartidaBO> list);

	/**
	 * 
	 * @param file
	 * @return
	 */
	public boolean loadDataLocal(File file);

}
