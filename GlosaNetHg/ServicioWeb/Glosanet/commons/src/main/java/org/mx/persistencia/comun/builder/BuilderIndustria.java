package org.mx.persistencia.comun.builder;

import org.mx.modelo.comun.bo.IndustriaBO;
import org.mx.modelo.comun.po.IndustriaPO;

public class BuilderIndustria {

	public static IndustriaBO builderBO(IndustriaPO po){
		IndustriaBO bo = new IndustriaBO();
		bo.setIdIndustria(po.getIdIndustria());
		bo.setNombreEs(po.getNombreEs());
		bo.setNombreEn(po.getNombreEn());
		bo.setDescripcionEs(po.getDescripcionEs());
		bo.setDescripcionEn(po.getDescripcionEn());
		bo.setActivo(po.isActivo());
		
		return bo;
	}
}
