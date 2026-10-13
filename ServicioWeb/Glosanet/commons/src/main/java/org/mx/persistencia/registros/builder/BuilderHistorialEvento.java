package org.mx.persistencia.registros.builder;

import org.mx.modelo.registros.bo.HistorialEventoBO;
import org.mx.modelo.registros.po.HistorialEventoPO;

public class BuilderHistorialEvento {
	
	public static HistorialEventoBO buildBO(HistorialEventoPO po){
		HistorialEventoBO bo = new HistorialEventoBO();
		bo.setComentarios(po.getComentarios());
		bo.setFecha(po.getFecha());
		bo.setIdEvento(po.getIdEvento());
		bo.setIdHistorial(po.getIdHistorial());
		bo.setIdUsuario(po.getIdUsuario());
		return bo;
	}

}
