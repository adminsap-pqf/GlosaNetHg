package org.mx.persistencia.comun.builder;

import org.mx.modelo.comun.bo.CambioBitacoraBO;
import org.mx.modelo.comun.po.CambioBitacoraPO;

public class BuilderCambioBitacora {
	
	public static CambioBitacoraBO builderBO(CambioBitacoraPO po){
		CambioBitacoraBO bo = new CambioBitacoraBO();
		bo.setIdCambioBitacora(po.getIdCambioBitacora());
		bo.setDescripcion(po.getDescripcion());
		bo.setFecha(po.getFecha());
		bo.setUsuario(po.getUsuario());
		
		return bo;
	}

}
