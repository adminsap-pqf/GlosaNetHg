package org.mx.persistencia.comun.builder;

import org.mx.modelo.comun.bo.RolBO;
import org.mx.modelo.comun.po.RolPO;

public class BuilderRol {
	
	public static RolBO builderBO(RolPO po){
		RolBO bo = new RolBO();
		bo.setIdRol(po.getIdRol());
		bo.setNombreES(po.getNombreES());
		bo.setNombreEN(po.getNombreEN());
		bo.setDescripcionEN(po.getDescripcionEN());
		bo.setDescripcionES(po.getDescripcionES());
		bo.setActivo(po.isActivo());
		bo.setNivelAcceso(po.getNivelAcceso());
		return bo;
	}

}
