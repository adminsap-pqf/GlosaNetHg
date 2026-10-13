package org.mx.persistencia.permisos.builder;

import org.mx.modelo.permisos.org.mx.bo.PermisoCorreoEventoBO;
import org.mx.modelo.permisos.org.mx.po.PermisoCorreoEventoPO;


public class BuilderPermisoCorreoEvento {

	
	public static PermisoCorreoEventoBO builderBO(PermisoCorreoEventoPO po){
		PermisoCorreoEventoBO bo = new PermisoCorreoEventoBO();
		bo.setCorreo(po.getCorreo());
		bo.setIdCorreo(po.getIdcorreo());
		bo.setIdEvento(po.getPermiso().getIdEvento());
		return bo;
	}
}
