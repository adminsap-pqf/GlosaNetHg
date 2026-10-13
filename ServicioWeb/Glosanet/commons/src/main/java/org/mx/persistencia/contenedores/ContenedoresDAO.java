package org.mx.persistencia.contenedores;

import java.io.File;
import java.util.List;

import org.mx.modelo.datastage.bo.ContenedoresBO;

public interface ContenedoresDAO {

	/***
	 * 
	 * @param list
	 * @return
	 */
	public boolean guardar(List<ContenedoresBO> list);

	/**
	 * 
	 * @param file
	 * @return
	 */
	public boolean loadDataLocal(File file);

}
