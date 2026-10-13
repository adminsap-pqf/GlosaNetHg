package org.mx.persistencia.reporte;

import java.util.List;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.reportes.ParametrosPartida;
import org.mx.modelo.reportes.bo.DetalleContribucionesBO;
import org.mx.modelo.reportes.bo.DetallePartidasBO;
import org.mx.modelo.reportes.bo.ResumenReporteBO;
import org.mx.persistencia.Page;
import org.mx.persistencia.comun.DataBaseDAO;

public interface PredefinidoDAO extends DataBaseDAO<ResumenReporteBO> {

	/***
	 * Montos totales en aduana por Tipo de Operacion agrupada por Clave
	 * @param restricciones
	 * @param empresa
	 * @param tipoMoneda
	 * @return
	 * @throws GlosaNetException
	 */
	public List<ResumenReporteBO> getMontosTotalesPorTipoOperacionClave(String restricciones, String empresa, String tipoMoneda) throws GlosaNetException;
	
	/***
	 * Montos totales de contribucion por clave 
	 * @param restricciones
	 * @param empresa
	 * @param tipoMoneda
	 * @return
	 * @throws GlosaNetException
	 */
	public List<ResumenReporteBO> getMontosTotalesClaveContribucion(String restricciones, String empresa, String tipoMoneda, String rectificacionFecha) throws GlosaNetException;
	
	/***
	 * Obtiene el Valor de Aduana por las importaciones Definitivas o Temporales
	 * @param restricciones
	 * @param tipoImportacion
	 * @param idCliente
	 * @return
	 * @throws GlosaNetException
	 */
	public List<ResumenReporteBO> getMontosTotalesImportacion(String restricciones, String empresa, String tipoMoneda,String idioma) throws GlosaNetException;
	
	/***
	 * Obtiene el Valor de Aduana y la suma de contribuciones por (impuesto)Importacion relacionadas por patente, pedimento, seccionAduanera, SecuenciaFraccion
	 * agrupadas por pais
	 * @param restriccion
	 * @return
	 * @throws GlosaNetException
	 */
	public List<ResumenReporteBO> getMontoValorAduanaYContribucionImportacion(String restriccion, String tipoMoneda,String idioma) throws GlosaNetException;

	/***
	 * Obtiene el Valor de Aduana y la suma de contribuciones por (impuesto)Importacion por Patente, Pedimento, SeccionAduanera, SecuenciaFraccion agrupadas por Pais
	 * @param restriccion
	 * @return
	 * @throws GlosaNetException
	 */
	public List<ResumenReporteBO> getMontoPendienteValorAduanaYContribucionImportacion(String restriccion, String tipoMoneda,String idioma) throws GlosaNetException;

	/***
	 * Obtiene la lista de pedimentos con el detalle de contribuciones filtrados por un rango de fecha de pago final e identificador de cliente
	 * @param filtro
	 * @return
	 */
    List<DetalleContribucionesBO> getReporteContribuciones(String filtro);

	/**
	 *
	 * @param parametros
	 * @return
	 */
	List<DetallePartidasBO> getReportePartidas(ParametrosPartida parametros);

	/**
	 *
	 * @param filtro
	 * @return
	 */
	Page<DetallePartidasBO> getReportePartidas(String filtro, Integer page, Integer size);

	/**
	 *
	 * @param filtro
	 * @return
	 */
	public List<DetallePartidasBO> getReportePartidas(String filtro);
}
