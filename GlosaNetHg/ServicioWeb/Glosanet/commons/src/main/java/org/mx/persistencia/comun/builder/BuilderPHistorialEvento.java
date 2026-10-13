package org.mx.persistencia.comun.builder;

import org.mx.modelo.comun.bo.PHistorialEventoBO;
import org.mx.modelo.comun.po.PHistorialEventoPO;

public class BuilderPHistorialEvento {
	
	public static PHistorialEventoBO buildBO(PHistorialEventoPO po){
		PHistorialEventoBO bo = new PHistorialEventoBO();
		bo.setComentarios(po.getComentarios());
		bo.setFecha(po.getFecha());
		bo.setIdEvento(po.getIdEvento());
		bo.setIdHistorial(po.getIdHistorial());
		bo.setIdUsuario(po.getIdUsuario());
		return bo;
	}

}
