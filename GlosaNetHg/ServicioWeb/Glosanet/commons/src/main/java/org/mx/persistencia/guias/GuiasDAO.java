package org.mx.persistencia.guias;

import java.io.File;
import java.util.List;

import org.mx.modelo.datastage.bo.GuiasBO;

public interface GuiasDAO {

	/**
	 * 
	 * @param list
	 * @return
	 */
	public boolean guardar(List<GuiasBO> list);

	/**
	 * 
	 * @param file
	 * @return
	 */
	public boolean loadDataLocal(File file);

}
