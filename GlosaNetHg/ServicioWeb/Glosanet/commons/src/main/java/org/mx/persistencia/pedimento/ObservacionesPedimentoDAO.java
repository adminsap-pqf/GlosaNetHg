package org.mx.persistencia.pedimento;

import java.io.File;
import java.util.List;

import org.mx.modelo.datastage.bo.ObservacionesPedimentoBO;

public interface ObservacionesPedimentoDAO {

	/**
	 * 
	 * @param list
	 * @return
	 */
	public boolean guardar(List<ObservacionesPedimentoBO> list);

	/**
	 * 
	 * @param file
	 * @return
	 */
	public boolean loadDataLocal(File file);

}
