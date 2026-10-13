package org.mx.persistencia.registros.builder;

import org.mx.modelo.registros.bo.HistorialCofeprisBO;
import org.mx.modelo.registros.po.HistorialCofeprisPO;

public class BuilderHistorialCofepris {
	
	public static HistorialCofeprisBO buildBO(HistorialCofeprisPO po){
		HistorialCofeprisBO bo = new HistorialCofeprisBO();
		bo.setIdHistorial(po.getIdHistorial());
		bo.setTramite(po.getTramite());
		bo.setFecha(po.getFecha());
		bo.setEstado(po.getEstado());
		return bo;
	}

}
