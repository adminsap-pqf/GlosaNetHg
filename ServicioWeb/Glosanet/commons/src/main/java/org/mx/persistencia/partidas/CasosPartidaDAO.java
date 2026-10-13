package org.mx.persistencia.partidas;

import java.io.File;
import java.util.List;

import org.mx.modelo.datastage.bo.CasosPartidaBO;


public interface CasosPartidaDAO {

	/**
	 * 
	 * @param lst
	 * @return
	 */
	public boolean guardar(List<CasosPartidaBO> lst);

	/**
	 * 
	 * @param file
	 * @return
	 */
	boolean loadDataLocal(File file);

}
