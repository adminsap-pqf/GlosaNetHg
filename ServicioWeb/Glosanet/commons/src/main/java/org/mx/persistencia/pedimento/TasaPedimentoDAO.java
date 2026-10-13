package org.mx.persistencia.pedimento;

import java.io.File;
import java.util.List;

import org.mx.modelo.datastage.bo.TasasPedimentoBO;

public interface TasaPedimentoDAO {

	/**
	 * 
	 * @param list
	 * @return
	 */
	public boolean guardar(List<TasasPedimentoBO> list);

	/**
	 * 
	 * @param file
	 * @return
	 */
	public boolean loadDataLocal(File file);

}
