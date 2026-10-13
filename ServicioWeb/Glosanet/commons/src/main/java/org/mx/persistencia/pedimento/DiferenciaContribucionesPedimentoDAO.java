package org.mx.persistencia.pedimento;

import java.io.File;
import java.util.List;

import org.mx.modelo.datastage.bo.DiferenciasContribucionesPedimentoBO;

public interface DiferenciaContribucionesPedimentoDAO {

	/**
	 * 
	 * @param list
	 * @return
	 */
	public boolean guardar(List<DiferenciasContribucionesPedimentoBO> list);

	/**
	 * 
	 * @param file
	 * @return
	 */
	public boolean loadDataLocal(File file);

}
