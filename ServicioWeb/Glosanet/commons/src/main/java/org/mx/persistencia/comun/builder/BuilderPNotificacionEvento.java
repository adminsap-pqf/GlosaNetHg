package org.mx.persistencia.comun.builder;

import org.mx.modelo.comun.bo.PNotificacionBO;
import org.mx.modelo.comun.po.PNotificacionPO;

public class BuilderPNotificacionEvento {

	public static PNotificacionBO builderBO(PNotificacionPO po){
		PNotificacionBO bo = new PNotificacionBO();
		bo.setIdNotificacion(po.getIdNotificacion());
		bo.setCantPeriodo(po.getCantPeriodo());
		bo.setTipoPeriodo(po.getTipoPeriodo());
		bo.setRecurrencia(po.getRecurrencia());
		bo.setFinicio(po.getFinicio());
		bo.setTerminado(po.isTerminado());
		bo.setIdEvento(po.getPevento().getIdEvento());
		return bo;
	}
}
