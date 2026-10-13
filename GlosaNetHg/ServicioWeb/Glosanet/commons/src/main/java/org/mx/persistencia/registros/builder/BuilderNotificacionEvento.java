package org.mx.persistencia.registros.builder;

import org.mx.modelo.registros.bo.NotificacionBO;
import org.mx.modelo.registros.po.NotificacionPO;

public class BuilderNotificacionEvento {
	
	public static NotificacionBO builderBO(NotificacionPO po){
		NotificacionBO bo = new NotificacionBO();
		bo.setIdNotificacion(po.getIdNotificacion());
		bo.setCantPeriodo(po.getCantPeriodo());
		bo.setTipoPeriodo(po.getTipoPeriodo());
		bo.setRecurrencia(po.getRecurrencia());
		bo.setFinicio(po.getFinicio());
		bo.setTerminado(po.isTerminado());
		bo.setIdEvento(po.getEvento().getIdEvento());
		return bo;
	}

}
