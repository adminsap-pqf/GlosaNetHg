package org.mx.persistencia.registros.builder;

import org.mx.modelo.registros.bo.CorreoEventoBO;
import org.mx.modelo.registros.po.CorreoEventoPO;

public class BuilderCorreoEvento {
	
	public static CorreoEventoBO builderBO(CorreoEventoPO po){
		CorreoEventoBO bo = new CorreoEventoBO();
		bo.setCorreo(po.getCorreo());
		bo.setIdCorreo(po.getIdCorreo());
		bo.setIdEvento(po.getEvento().getIdEvento());
		return bo;
	}

}
