package org.mx.persistencia.comun.builder;

import org.mx.modelo.comun.bo.PCorreoEventoBO;
import org.mx.modelo.comun.po.PCorreoEventoPO;

public class BuilderPCorreoEvento {

	public static PCorreoEventoBO builderBO(PCorreoEventoPO po){
		PCorreoEventoBO bo = new PCorreoEventoBO();
		bo.setCorreo(po.getCorreo());
		bo.setIdCorreo(po.getIdCorreo());
		bo.setIdEvento(po.getEvento().getIdEvento());
		return bo;
	}
}
