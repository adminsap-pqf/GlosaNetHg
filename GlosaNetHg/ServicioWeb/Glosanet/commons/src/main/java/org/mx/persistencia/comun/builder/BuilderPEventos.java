package org.mx.persistencia.comun.builder;

import org.mx.modelo.comun.po.PCorreoEventoPO;
import org.mx.modelo.comun.bo.PEventoBO;
import org.mx.modelo.comun.po.PEventoPO;
import org.mx.modelo.comun.po.PNotificacionPO;


public class BuilderPEventos {

	public static PEventoBO builderBO(PEventoPO po){
		PEventoBO bo = new PEventoBO();
		
		bo.setIdEvento(po.getIdEvento());
//		bo.setIdRegistro(po.getRegistro().getIdRegistro());
		//bo.setPrevision(BuilderPrevisiones.builderBO(po.getPrevision()));
		bo.setNombre(po.getNombre());
		bo.setCantPeriodo(po.getCantPeriodo());
		bo.setTipoPeriodo(po.getTipoPeriodo());
		bo.setDefinido(po.isDefinido());
		bo.setFrealizacion(po.getFrealizacion());
		bo.setTerminado(po.isTerminado());
		
		for(PCorreoEventoPO co : po.getCorreos()){
			bo.getCorreos().add(BuilderPCorreoEvento.builderBO(co));
		}	
		
		for(PNotificacionPO no : po.getNotificaciones()){
			bo.getNotificaciones().add(BuilderPNotificacionEvento.builderBO(no));
		}

		bo.setFterminado(po.getFterminado());
		return bo;
	}
}
