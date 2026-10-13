package org.mx.persistencia.mercancia;

import java.io.File;
import java.util.List;

import org.mx.modelo.datastage.bo.DescargosMercanciaBO;

public interface DescargosMercanciaDAO {

	/**
	 * 
	 * @param list
	 * @return
	 */
	public boolean guardar(List<DescargosMercanciaBO> list);

	/**
	 * 
	 * @param file
	 * @return
	 */
	public boolean loadDataLocal(File file);

}
