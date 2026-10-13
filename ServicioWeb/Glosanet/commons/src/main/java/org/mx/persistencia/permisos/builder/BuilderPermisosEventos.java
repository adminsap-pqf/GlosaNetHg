package org.mx.persistencia.permisos.builder;

import org.mx.modelo.permisos.org.mx.bo.PermisoEventoBO;
import org.mx.modelo.permisos.org.mx.po.DupPermisoEventoPO;
import org.mx.modelo.permisos.org.mx.po.PermisoCorreoEventoPO;
import org.mx.modelo.permisos.org.mx.po.PermisoNotificacionPO;


public class BuilderPermisosEventos {

	
	
	
	public static PermisoEventoBO builder2BO(DupPermisoEventoPO po){
		PermisoEventoBO bo = new PermisoEventoBO();
		
		bo.setIdEvento(po.getIdEvento());
//		bo.setIdRegistro(po.getRegistro().getIdRegistro());
		//bo.setRegistro(BuilderRegistros.builderBOSimple(po.getRegistro()));
		bo.setNombre(po.getNombre());
		bo.setCantPeriodo(Integer.parseInt(po.getCantPeriodo()));
		bo.setDefinido(po.isDefinido());
		bo.setFrealizacion(po.getFechaRealizada());
		bo.setTerminado(po.isTerminado());
		 bo.setTipoPeriodo(po.getTipoPeriodo());
		for(PermisoCorreoEventoPO co : po.getCorreos()){
			bo.getCorreos().add(BuilderPermisoCorreoEvento.builderBO(co));
		}	
		
		for(PermisoNotificacionPO no : po.getNotificaciones()){
			bo.getNotificaciones().add(BuilderPermisoNotificacionEvento.builderBO(no));
		}

		bo.setFterminado(po.getFechaTerminada());
		
		//System.out.println("builderPermisoEvento");
		//System.out.println(bo);
		return bo;
	}

	
	
	
}
