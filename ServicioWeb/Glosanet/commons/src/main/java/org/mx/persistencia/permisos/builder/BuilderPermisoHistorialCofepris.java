package org.mx.persistencia.permisos.builder;

import org.mx.modelo.permisos.org.mx.bo.PermisoHistorialCofeprisBO;
import org.mx.modelo.permisos.org.mx.po.PermisoHistorialCofeprisPO;

public class BuilderPermisoHistorialCofepris {

		
	public static PermisoHistorialCofeprisBO buildBO(PermisoHistorialCofeprisPO po){
		PermisoHistorialCofeprisBO bo = new PermisoHistorialCofeprisBO();
		bo.setIdHistorial(po.getIdHistorial());
		bo.setTramite(po.getTramite());
		bo.setFecha(po.getFecha());
		bo.setEstado(po.getEstado());
		return bo;
	}
		
		
	
		
}
