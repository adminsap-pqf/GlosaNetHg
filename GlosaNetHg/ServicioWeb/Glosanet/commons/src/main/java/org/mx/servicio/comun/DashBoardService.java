package org.mx.servicio.comun;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.DashBoard;
import org.mx.modelo.comun.bo.Parametros;

import java.util.List;
import java.util.Map;

public interface DashBoardService {
    /***
     * Obtiene la cantidad total de operaciones por patente
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t501_ObtenerOperacionesPorTipoPedimento(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene la cantidad total de operaciones por patente
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t501_ObtenerOperacionesPorPatente(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene la cantidad total de operaciones por destino de la mercancia
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t501_ObtenerOperacionesDestinoMercancia(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene la cantidad total de operaciones por incrementables y deducibles
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t501_ObtenerOperacionesPorIncrementablesYDeducibles(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene la cantidad total de operaciones por medio de transporte de arribo y salida
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t501_ObtenerOperacionesMedioTransporteArriboSalida(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene la cantidad total de operaciones por medio de transporte de salida
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t501_ObtenerOperacionesMedioTransporteSalida(Parametros parametros) throws GlosaNetException;

    /**
     * Genera un pdf.  de la tabla Datos_Generales_501
     *
     * @param arguments
     * @return
     * @throws GlosaNetException
     */
    public byte[] generaReporteDG501PDF(Map<String, Object> arguments) throws GlosaNetException;

    /***
     * Obtiene  total de forma de pago
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t702_ObtenerTotalPorFormaPago(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene la cantidad total de operaciones por aduana
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t702_ObtenerOperacionesPorAduana(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene la cantidad total de operaciones por patente
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t702_ObtenerOperacionesPorPatente(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene la cantidad total de operaciones por aduana
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t701_ObtenerOperacionesPorAduana(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene la cantidad total de operaciones por patente
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t701_ObtenerOperacionesPorPatente(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene la cantidad total de operaciones por tipo tasa
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t701_ObtenerOperacionesPorClaveDocumento(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene la cantidad total de operaciones por tipo tasa
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t556_ObtenerOperacionesTipoTasa(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene la cantidad total de operaciones por agente
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t556_ObtenerOperacionesPorAgente(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene la cantidad total de operaciones por aduana
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t556_ObtenerOperacionesPorAduana(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene la cantidad total de operaciones por tasa de contribucion
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t556_ObtenerOperacionesTasaContribucion(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene la cantidad total de operaciones por clave
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t556_ObtenerOperacionesPorClave(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene la cantidad total de operaciones por agente
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t554_ObtenerOperacionesPorAgente(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene la cantidad total de operaciones por clave contribucion
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t554_ObtenerOperacionesPorClave(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene la cantidad total de operaciones por aduana
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t554_ObtenerOperacionesPorAduana(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene la cantidad total de operaciones por tipo pedimento
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t510_ObtenerOperacionesPorTipoPedimento(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene la cantidad total de operaciones por agente
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t510_ObtenerOperacionesPorAgente(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene la cantidad total de operaciones por clave contribucion
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t510_ObtenerOperacionesPorClave(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene la cantidad total de operaciones por contribucion
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t510_ObtenerOperacionesPorContribucion(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene la cantidad total de operaciones por aduana
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t510_ObtenerOperacionesPorAduana(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene la cantidad total de pedimentos por clave contribucion
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t509_ObtenerPedimentoPorImporteTasa(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene la cantidad total de pedimentos por agente
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t509_ObtenerPedimentoPorAgente(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene la cantidad total de pedimentos por tipo de tasa
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t509_ObtenerPedimentoPorTipoTasa(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene la cantidad total de pedimentos por aduana
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t509_ObtenerPedimentoPorAduana(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene la cantidad total de pedimentos por clave
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t507_ObtenerPedimentoPorClave(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene la cantidad total de pedimentos por aduana
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t507_ObtenerPedimentoPorAduana(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene la cantidad total de fechas por agente aduanal
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t506_ObtenerFechaPorAgente(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene la cantidad total de fechas por aduana
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t506_ObtenerFechaPorAduana(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene la cantidad total de obtener factura por moneda
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t505_ObtenerFacturaPorMoneda(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene la cantidad total de obtener factura por pais
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t505_ObtenerFacturaPorPais(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene la cantidad total de contenedores por Agente
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t504_ObtenerContenedorPorAgente(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene la cantidad total de contenedores por tipo de contenedor
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t504_ObtenerContenedorPorTipo(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene la cantidad total de contenedores por Aduana
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t504_ObtenerContenedorPorAduana(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene la cantidad total de guias por Tipo de guia
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t503_ObtenerGuiasPorTipo(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene la cantidad total de guias por Agente Aduanal
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t503_ObtenerGuiasPorAgente(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene la cantidad total de guias por Aduana
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t503_ObtenerGuiasPorAduana(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene la cantidad total de operaciones agrupadas por importaciones/exprtaciones/rectificaciones
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t501_obtenerOperacionesDelPeriodo(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene la cantidad total de operaciones agrupadas por aduana y tipo operacion de la tabla Datos_Generales_501
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t501_obtenerOperacionesPorAduana(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene el maximo, minimo y promedio del tipo de cambio de la tabla Datos_Generales_501
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t501_obtenerTipoCambioPeriodo(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene la cantidad total de operaciones agrupadas por medio de transporte de arribo de la tabla Datos_Generales_501
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t501_obtenerOperacionesPorMedioTransporteArribo(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene la suma de peso bruto agrupado por Medio Transporte Arribo de la tabla Datos_Generales_501
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t501_obtenerPesoBrutoPorMedioTransporteArribo(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene la cantidad de operaciones agrupadas por tipo de pedimento y de operacion de la tabla Datos_Generales_501
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t501_obtenerOperacionesPorTipoPedimento(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene la cantidad de operaciones agrupadas por clave pedimento y tipo de operacion de la tabla Datos_Generales_501
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t501_obtenerOperacionesPorClavePedimento(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene la cantidad de operaciones hechas por Aduana de la tabla Transporte_Mercancias_502
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t502_obtenerOperacionesPorAduana(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene la cantidad de operaciones hechas por Agente Aduana de la tabla Transporte_Mercancias_502
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t502_obtenerOperacionesPorAgenteAduanal(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene la cantidad de operaciones por pais de la tabla Transporte_Mercancias_502
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t502_obtenerOperacionesPorPais(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene la cantidad de operaciones por forma de pago de la tabla Contribuciones_Partida_557
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t557_obtenerOperacionesPorFormaDePago(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene el importe de pago por el tipo de contibucion de la tabla Contribuciones_Partida_557
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t557_obtenerImportePagoPorTipoContribucion(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene la cantidad de operaciones por Agente Aduanal de la tabla Contribuciones_Partida_557
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t557_obtenerOperacionesPorAgenteAduanal(Parametros parametros) throws GlosaNetException;

    /***
     * Obtiene la cantidad de operaciones por Aduana de la tabla Contribuciones_Partida_557
     * @param parametros
     * @return
     * @throws GlosaNetException
     */
    public List<DashBoard> t557_obtenerOperacionesPorAduana(Parametros parametros) throws GlosaNetException;

}
