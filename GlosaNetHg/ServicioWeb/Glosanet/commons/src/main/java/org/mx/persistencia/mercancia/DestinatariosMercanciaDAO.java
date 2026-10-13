package org.mx.persistencia.mercancia;

import java.io.File;
import java.util.List;

import org.mx.modelo.datastage.bo.DestinatariosMercanciaBO;

public interface DestinatariosMercanciaDAO {

	/**
	 * 
	 * @param list
	 * @return
	 */
	public boolean guardar(List<DestinatariosMercanciaBO> list);

	/**
	 * 
	 * @param file
	 * @return
	 */
	public boolean loadDataLocal(File file);

}
