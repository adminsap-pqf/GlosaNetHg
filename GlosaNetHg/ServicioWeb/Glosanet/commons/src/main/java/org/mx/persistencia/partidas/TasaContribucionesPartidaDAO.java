package org.mx.persistencia.partidas;

import java.io.File;
import java.util.List;

import org.mx.modelo.datastage.bo.TasasContribucionesPartidaBO;

public interface TasaContribucionesPartidaDAO {

	/**
	 * 
	 * @param list
	 * @return
	 */
	public boolean guardar(List<TasasContribucionesPartidaBO> list);

	/**
	 * 
	 * @param file
	 * @return
	 */
	public boolean loadDataLocal(File file);

}
