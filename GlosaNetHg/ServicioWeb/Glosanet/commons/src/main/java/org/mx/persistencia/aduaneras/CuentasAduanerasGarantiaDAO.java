package org.mx.persistencia.aduaneras;

import java.io.File;
import java.util.List;

import org.mx.modelo.datastage.bo.CuentasAduanerasGarantiaBO;

public interface CuentasAduanerasGarantiaDAO {

	/**
	 * 
	 * @param list
	 * @return
	 */
	public boolean guardar(List<CuentasAduanerasGarantiaBO> list);

	/**
	 * 
	 * @param file
	 * @return
	 */
	public boolean loadDataLocal(File file);

}
