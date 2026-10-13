package org.mx.persistencia.reporte;

import java.util.List;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.reportes.ReporteDICIPA_565;
import org.mx.persistencia.comun.DataBaseDAO;

public interface AmedidaDAO extends DataBaseDAO<ReporteDICIPA_565>{

	/***
	 * 
	 * @param fecha
	 * @return
	 * @throws GlosaNetException
	 */
	public List<ReporteDICIPA_565> findReporteMedidaMensualDICIPA_565(String fecha)
			throws GlosaNetException;

	/***
	 * 
	 * @param fecha
	 * @return
	 * @throws GlosaNetException
	 */
	public List<ReporteDICIPA_565> findReporteMedidaMensualDICIPA_565_Generales(
			String fecha) throws GlosaNetException;

}
