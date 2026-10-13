package org.mx.persistencia.reporte;

import java.util.List;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.reportes.bo.TablaDataStageBO;

public interface TablaDataStageDAO {

	/***
	 * 
	 * @param idioma
	 * @param condicion
	 * @return
	 * @throws GlosaNetException
	 */
	public List<TablaDataStageBO> findTablasDataStage(String idioma, String condicion)
			throws GlosaNetException;

	/***
	 * 
	 * @param sql
	 * @return
	 * @throws GlosaNetException
	 */
	public List<String[]> findResultadosDeReportePersonalizado(String sql)
			throws GlosaNetException;

	/***
	 * 
	 * @param sql
	 * @return
	 * @throws GlosaNetException
	 */
	public int getCantidadResultadosDeReportePersonalizado(String sql)
			throws GlosaNetException;

}
