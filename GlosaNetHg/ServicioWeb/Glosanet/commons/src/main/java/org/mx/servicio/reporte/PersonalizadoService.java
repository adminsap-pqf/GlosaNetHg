package org.mx.servicio.reporte;

import java.util.List;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.Parametros;
import org.mx.modelo.reportes.bo.CampoTablaBO;
import org.mx.modelo.reportes.bo.ReportePersonalizadoBO;
import org.mx.modelo.reportes.bo.TablaDataStageBO;

public interface PersonalizadoService {

	/***
	 * Obtiene las tablas se obtienen de un DataStage
	 * @return
	 * @throws GlosaNetException
	 */
	public List<TablaDataStageBO> obtenerTablasDataStage(String idioma, String condicion) throws GlosaNetException;

	/***
	 * Obtiene la informacon de las diferentes tablas y columnas indicadas 
	 * @return
	 * @throws GlosaNetException
	 */
	public List<String>  obtenerResultadosDeReportePersonalizado(List<CampoTablaBO> campos, Parametros p) throws GlosaNetException;
	
	/***
	 * Obtiene la informacion de las diferentes tablas y columnas indicadas, primer paso de flujo 'Descargas'
	 * @return
	 * @throws GlosaNetException
	 */
	public Integer  peticionResultadosDeReportePersonalizado(List<CampoTablaBO> campos, Parametros p) throws GlosaNetException;

	/***
	 * Obtiene un listado de ligas para descargar los archivos creados para una peticion de descarga
	 * @param idPeticion
	 * @return
	 * @throws GlosaNetException
	 */
	public List<String> obtenerLigasArchivosReportes(Integer idPeticion) throws GlosaNetException;
	
	

	/***
	 * 
	 * @param idPeticion
	 * @return
	 * @throws GlosaNetException
	 */
	public String obtenerLigasDescargasReportes(Integer idPeticion)
			throws GlosaNetException;

	/***
	 * 
	 * @param reportePersonalizado
	 * @return
	 * @throws GlosaNetException
	 */
	public Integer guardarReporte(ReportePersonalizadoBO reportePersonalizado)
			throws GlosaNetException;

	/***
	 * 
	 * @param idAutor
	 * @return
	 * @throws Exception
	 */
	public List<ReportePersonalizadoBO> obtenerReportes(int idAutor) throws Exception;

	/***
	 * 
	 * @param reportePersonalizado
	 * @return
	 * @throws GlosaNetException
	 */
	public boolean borrarReporte(ReportePersonalizadoBO reportePersonalizado)
			throws GlosaNetException;

	/***
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ReportePersonalizadoBO obtenerReportePorId(int id) throws Exception;
}
