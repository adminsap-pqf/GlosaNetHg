package org.mx.persistencia.aduaneras;

import java.io.File;
import java.util.List;

import org.mx.modelo.datastage.bo.CuentasAduanerasGarantiaPedimentoBO;

public interface CuentasAduanerasGarantiaPedimentoDAO {

	/**
	 * 
	 * @param list
	 * @return
	 */
	public boolean guardar(List<CuentasAduanerasGarantiaPedimentoBO> list);

	/**
	 * 
	 * @param file
	 * @return
	 */
	public boolean loadDataLocal(File file);

}
