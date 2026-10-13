package org.mx.persistencia.permisos.builder;

import org.mx.modelo.permisos.org.mx.bo.PermisoHistorialEventoBO;
import org.mx.modelo.permisos.org.mx.po.PermisoHistorialEventoPO;

public class BuilderPermisoHistorialEvento {

	public static PermisoHistorialEventoBO buildBO(PermisoHistorialEventoPO po){
		PermisoHistorialEventoBO bo = new PermisoHistorialEventoBO();
		bo.setIdHistorial(po.getIdHistorial());
		bo.setIdEvento(po.getIdEvento());
		bo.setIdUsuario(po.getIdUsuario());
		bo.setComentarios(po.getComentarios());
		bo.setFecha(po.getFecha());
		return bo;
	}
}
