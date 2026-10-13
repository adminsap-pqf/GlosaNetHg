package org.mx.servicio.reporte;

import java.util.List;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.Parametros;
import org.mx.modelo.reportes.ReporteDICIPA_565;

public interface AmedidaService {

	/***
	 * 
	 * @param p
	 * @return
	 * @throws GlosaNetException
	 */
	public List<ReporteDICIPA_565> obtenerReporteMedidaMensualDICIPA_565(Parametros p)
			throws GlosaNetException;

	/***
	 * 
	 * @param p
	 * @return
	 * @throws GlosaNetException
	 */
	public Integer peticionReporteMedidaMensualDICIPA_565(Parametros p)
			throws GlosaNetException;

}
