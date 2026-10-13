package org.mx.persistencia.mercancia;

import java.io.File;
import java.util.List;

import org.mx.modelo.datastage.bo.TransporteMercanciasBO;

public interface TransporteMercanciasDAO {

	/**
	 * 
	 * @param list
	 * @return
	 */
	public boolean guardar(List<TransporteMercanciasBO> list);

	/**
	 * 
	 * @param file
	 * @return
	 */
	public boolean loadDataLocal(File file);

}
