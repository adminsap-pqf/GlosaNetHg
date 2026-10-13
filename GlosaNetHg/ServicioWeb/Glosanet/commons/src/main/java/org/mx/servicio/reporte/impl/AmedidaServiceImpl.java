package org.mx.servicio.reporte.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.DescargaBO;
import org.mx.modelo.comun.bo.Parametros;
import org.mx.modelo.reportes.ReporteDICIPA_565;
import org.mx.modelo.util.UtilFechas;
import org.mx.persistencia.comun.DescargaDAO;
import org.mx.persistencia.reporte.AmedidaDAO;
import org.mx.servicio.reporte.AmedidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AmedidaServiceImpl implements AmedidaService{
	private final static Logger logger = Logger.getLogger(AmedidaServiceImpl.class);
	
	UtilFechas u;
	
	@Autowired
	AmedidaDAO amedidaDAO;
	@Autowired
	DescargaDAO descargaDAO;

	@Override
	public List<ReporteDICIPA_565> obtenerReporteMedidaMensualDICIPA_565(Parametros p)
			throws GlosaNetException {
		
		u = new UtilFechas();
		String 	rGeneral = u.construccionDeFechas(p, "DG.FechaPagoReal", "DG.FechaPagoReal");

		return amedidaDAO.findReporteMedidaMensualDICIPA_565(rGeneral);
	}
	
	@Override
	@Transactional(rollbackFor={Exception.class})
	public Integer peticionReporteMedidaMensualDICIPA_565(Parametros p) throws GlosaNetException{
		try{
			String 	fecha = u.construccionDeFechas(p, "DG.FechaPagoReal", "DG.FechaPagoReal");
			DescargaBO peticion = new DescargaBO(fecha, " ", p.getUsuario().getIdUsuario(), "DICIPA", null);
			return descargaDAO.insertPeticionDescarga(peticion);
		}catch(Exception e){
			logger.error(e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}
}
