package org.mx.persistencia.permisos.builder;

import org.mx.modelo.permisos.org.mx.bo.PermisoNotificacionBO;
import org.mx.modelo.permisos.org.mx.po.PermisoNotificacionPO;


public class BuilderPermisoNotificacionEvento {
		
	public static PermisoNotificacionBO builderBO(PermisoNotificacionPO po){
		PermisoNotificacionBO bo = new PermisoNotificacionBO();
		bo.setIdNotificacion(po.getIdNotificacion());
		bo.setCantPeriodo(po.getCantPeriodo());
		bo.setTipoPeriodo(po.getTipoPerido());
		bo.setRecurrencia(po.getRecurrencia());
		bo.setFinicio(po.getFechaInicio());
		bo.setTerminado(po.isTerminado());
		bo.setIdEvento(po.getPermiso().getIdEvento());
		return bo;
	}
}
