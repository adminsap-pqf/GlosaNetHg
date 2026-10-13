package org.mx.persistencia.registros.builder;

import org.mx.modelo.registros.bo.EventoBO;
import org.mx.modelo.registros.po.CorreoEventoPO;
import org.mx.modelo.registros.po.EventoPO;
import org.mx.modelo.registros.po.NotificacionPO;

public class BuilderEventos {
	
	public static EventoBO builderBO(EventoPO po){
		EventoBO bo = new EventoBO();
		
		bo.setIdEvento(po.getIdEvento());
//		bo.setIdRegistro(po.getRegistro().getIdRegistro());
		bo.setRegistro(BuilderRegistros.builderBOSimple(po.getRegistro()));
		bo.setNombre(po.getNombre());
		bo.setCantPeriodo(po.getCantPeriodo());
		bo.setTipoPeriodo(po.getTipoPeriodo());
		bo.setDefinido(po.isDefinido());
		bo.setFrealizacion(po.getFrealizacion());
		bo.setTerminado(po.isTerminado());
		bo.setTramiteCofepris(po.getTramiteCofepris());
		bo.setTramite(po.isTramite());
		bo.setFechaTramite(po.getFechaTramite());
		for(CorreoEventoPO co : po.getCorreos()){
			bo.getCorreos().add(BuilderCorreoEvento.builderBO(co));
		}	
		
		for(NotificacionPO no : po.getNotificaciones()){
			bo.getNotificaciones().add(BuilderNotificacionEvento.builderBO(no));
		}

		bo.setFterminado(po.getFterminado());
		return bo;
	}
	
	public static EventoBO builder2BO(EventoPO po){
		EventoBO bo = new EventoBO();
		
		bo.setIdEvento(po.getIdEvento());
//		bo.setIdRegistro(po.getRegistro().getIdRegistro());
		//bo.setRegistro(BuilderRegistros.builderBOSimple(po.getRegistro()));
		bo.setNombre(po.getNombre());
		bo.setCantPeriodo(po.getCantPeriodo());
		bo.setTipoPeriodo(po.getTipoPeriodo());
		bo.setDefinido(po.isDefinido());
		bo.setFrealizacion(po.getFrealizacion());
		bo.setTerminado(po.isTerminado());
		bo.setTramite(po.isTramite());
		bo.setFechaTramite(po.getFechaTramite());
		bo.setTramiteCofepris(po.getTramiteCofepris());
		for(CorreoEventoPO co : po.getCorreos()){
			bo.getCorreos().add(BuilderCorreoEvento.builderBO(co));
		}	
		
		for(NotificacionPO no : po.getNotificaciones()){
			bo.getNotificaciones().add(BuilderNotificacionEvento.builderBO(no));
		}

		bo.setFterminado(po.getFterminado());
		return bo;
	}

}
