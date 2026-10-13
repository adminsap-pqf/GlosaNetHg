package org.mx.persistencia.pedimento;

import java.io.File;
import java.util.List;

import org.mx.modelo.datastage.bo.CasosPedimentoBO;

public interface CasosPedimentoDAO{

	/**
	 * 
	 * @param list
	 * @return
	 */
	public boolean guardar(List<CasosPedimentoBO> list);

	/**
	 * 
	 * @param file
	 * @return
	 */
	boolean loadDataLocal(File file);

}
