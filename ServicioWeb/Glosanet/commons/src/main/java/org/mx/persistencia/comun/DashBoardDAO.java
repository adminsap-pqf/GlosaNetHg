package org.mx.persistencia.comun;

import java.util.List;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.DashBoard;

public interface DashBoardDAO {

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t501_ObtenerOperacionesPorTipoPedimento(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t501_ObtenerOperacionesPorPatente(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t501_ObtenerOperacionesDestinoMercancia(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t501_ObtenerOperacionesPorIncrementablesYDeducibles(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t501_ObtenerOperacionesMedioTransporteArriboSalida(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t501_ObtenerOperacionesMedioTransporteSalida(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t702_ObtenerTotalPorFormaPago(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t702_ObtenerOperacionesPorAduana(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t702_ObtenerOperacionesPorPatente(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t701_ObtenerOperacionesPorAduana(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t701_ObtenerOperacionesPorPatente(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t701_ObtenerOperacionesPorClaveDocumento(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t556_ObtenerOperacionesTipoTasa(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t556_ObtenerOperacionesPorAgente(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t556_ObtenerOperacionesPorAduana(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t556_ObtenerOperacionesTasaContribucion(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t556_ObtenerOperacionesPorClave(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t554_ObtenerOperacionesPorAgente(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t554_ObtenerOperacionesPorClave(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t554_ObtenerOperacionesPorAduana(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t510_ObtenerOperacionesPorTipoPedimento(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t510_ObtenerOperacionesPorAgente(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t510_ObtenerOperacionesPorClave(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t510_ObtenerOperacionesPorContribucion(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t510_ObtenerOperacionesPorAduana(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t509_ObtenerPedimentoPorAgente(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t509_ObtenerPedimentoPorImporteTasa(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t509_ObtenerPedimentoPorTipoTasa(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t509_ObtenerPedimentoPorAduana(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t507_ObtenerPedimentoPorClave(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t507_ObtenerPedimentoPorAduana(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t506_ObtenerFechaPorAgente(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t506_ObtenerFechaPorAduana(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t505_getFacturaPorMoneda(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t505_getFacturaPorPais(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t504_getContenedorPorAgente(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t504_getContenedorPorTipo(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t504_getContenedorPorAduana(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t503_getGuiasPorTipo(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t503_getGuiasPorAgente(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t503_getGuiasPorAduana(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t501_findOperacionesDelPeriodo(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t501_findOperacionesPorAduana(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t501_findTipoCambioPeriodo(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t501_findOperacionesPorMedioTransporteArribo(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t501_findPesoBrutoPorMedioTransporteArribo(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t501_getOperacionesPorTipoPedimento(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t501_getOperacionesPorClavePedimento(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t502_findOperacionesPorAduana(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t502_findOperacionesPorAgenteAduanal(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t502_findOperacionesPorPais(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t557_findOperacionesPorFormaDePago(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t557_findImportePagoPorTipoContribucion(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t557_findOperacionesPorAgenteAduanal(String restricciones) throws GlosaNetException;

	/***
	 * 
	 * @param restricciones
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DashBoard> t557_findOperacionesPorAduana(String restricciones) throws GlosaNetException;

}
