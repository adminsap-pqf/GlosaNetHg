package org.mx.persistencia.pedimento;

import java.io.File;
import java.util.List;

import org.mx.modelo.datastage.bo.ContribucionesPedimentoBO;

/**
 * 
 * @author miguel.torres
 *
 */
public interface ContribucionesPedimentoDAO {

	/***
	 * 
	 * @param list
	 * @return
	 */
	public boolean guardar(List<ContribucionesPedimentoBO> list);

	/**
	 * 
	 * @param file
	 * @return
	 */
	public boolean loadDataLocal(File file);

}
