package org.mx.persistencia.partidas;

import java.io.File;
import java.util.List;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.datastage.bo.ContribucionesPartidaBO;

public interface ContribucionPartidaDAO{

	/**
	 * 
	 * @param list
	 * @return
	 */
	public boolean guardar(List<ContribucionesPartidaBO> list);

	/**
	 * 
	 * @return
	 * @throws GlosaNetException
	 */
	public ContribucionesPartidaBO findTop1() throws GlosaNetException;

	/**
	 * 
	 * @param file
	 * @return
	 */
	public boolean loadDataLocal(File file);

}
