package org.mx.persistencia.reporte;

import java.util.List;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.reportes.bo.PReportesPersonalizadosBO;
import org.mx.modelo.reportes.bo.ReportePersonalizadoBO;
import org.mx.modelo.reportes.bo.TablaDataStageBO;
import org.mx.persistencia.comun.DataBaseDAO;

public interface PersonalizadoDAO extends DataBaseDAO<TablaDataStageBO> {

	/***
	 * 
	 * @return
	 * @throws GlosaNetException
	 */
	public List<TablaDataStageBO> findTablasDataStage(String idioma, String condicion) throws GlosaNetException;
	
	/***
	 * 
	 * @param nombreTablas
	 * @param nombreColumnas
	 * @param filtros
	 * @return
	 * @throws GlosaNetException
	 */
	public List<String[]> findResultadosDeReportePersonalizado(String sql) throws GlosaNetException;
	
	/***
	 * Obtiene el numero de registros que trae una consulta en reportes personalizados
	 * @param sql
	 * @return
	 * @throws GlosaNetException
	 */
	public int getCantidadResultadosDeReportePersonalizado(String sql) throws GlosaNetException;

	/***
	 * 
	 * @param reportePersonalizado
	 * @return
	 * @throws GlosaNetException
	 */
	public Integer saveReport(ReportePersonalizadoBO reportePersonalizado)
			throws GlosaNetException;

	/***
	 * 
	 * @param pReportePersonalizado
	 * @return
	 * @throws GlosaNetException
	 */
	public Integer savePReport(PReportesPersonalizadosBO pReportePersonalizado)
			throws GlosaNetException;

	/***
	 * 
	 * @param idAutor
	 * @return
	 */
	public List<ReportePersonalizadoBO> getReports(int idAutor);

	/***
	 * 
	 * @param reportePersonalizado
	 * @return
	 * @throws GlosaNetException
	 */
	public boolean deleteReport(ReportePersonalizadoBO reportePersonalizado)
			throws GlosaNetException;

	/***
	 * 
	 * @param reportePersonalizado
	 * @return
	 * @throws GlosaNetException
	 */
	public boolean deletePReports(ReportePersonalizadoBO reportePersonalizado)
			throws GlosaNetException;

	/***
	 * 
	 * @param id
	 * @return
	 */
	public ReportePersonalizadoBO getReportById(int id);

	/***
	 * 
	 * @param id
	 * @return
	 */
	public List<PReportesPersonalizadosBO> getPReporstById(int id);

	/***
	 * 
	 * @param reportePersonalizado
	 * @return
	 * @throws GlosaNetException
	 */
	public Integer editReport(ReportePersonalizadoBO reportePersonalizado)
			throws GlosaNetException;
}
