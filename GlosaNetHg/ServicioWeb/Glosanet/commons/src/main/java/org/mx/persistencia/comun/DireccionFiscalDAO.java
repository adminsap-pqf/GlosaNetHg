package org.mx.persistencia.comun;

import org.mx.modelo.comun.bo.DireccionFiscalBO;

public interface DireccionFiscalDAO {
	
	/***
	 * 
	 * @param bo
	 * @return
	 */
	public DireccionFiscalBO insertarDireccion(DireccionFiscalBO bo);
	
	/***
	 * 
	 * @param bo
	 * @return
	 */
	public boolean  modificarDireccion(DireccionFiscalBO bo);

}
