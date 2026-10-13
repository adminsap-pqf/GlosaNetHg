package org.mx.persistencia.mercancia;

import java.io.File;
import java.util.List;

import org.mx.modelo.datastage.bo.MercanciasBO;

public interface MercanciasDAO {

	/**
	 * 
	 * @param list
	 * @return
	 */
	public boolean guardar(List<MercanciasBO> list);

	/**
	 * 
	 * @param file
	 * @return
	 */
	public boolean loadDataLocal(File file);

}
