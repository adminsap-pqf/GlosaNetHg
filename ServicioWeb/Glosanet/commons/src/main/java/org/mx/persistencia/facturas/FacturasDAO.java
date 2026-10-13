package org.mx.persistencia.facturas;

import java.io.File;
import java.util.List;

import org.mx.modelo.comun.bo.Parametros;
import org.mx.modelo.datastage.bo.FacturasBO;

public interface FacturasDAO {

	/**
	 * 
	 * @param list
	 * @return
	 */
	public boolean guardar(List<FacturasBO> list);
	
	/**
	 * 
	 * @param folio
	 * @return
	 */
	public List<FacturasBO> findFactuasXDataStage(String folio);

	/**
	 * 
	 * @param parametros
	 * @return
	 */
	public List<FacturasBO> findFacturas(Parametros parametros);

	/**
	 * 
	 * @param file
	 * @return
	 */
	public boolean loadDataLocal(File file);

}
