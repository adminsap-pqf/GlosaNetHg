package org.mx.persistencia.pedimento;

import java.io.File;
import java.util.List;

import org.mx.modelo.datastage.bo.FechasPedimentoBO;

public interface FechasPedimentoDAO {

	/**
	 * 
	 * @param list
	 * @return
	 */
	public boolean guardar(List<FechasPedimentoBO> list);

	/**
	 * 
	 * @param file
	 * @return
	 */
	public boolean loadDataLocal(File file);

}
