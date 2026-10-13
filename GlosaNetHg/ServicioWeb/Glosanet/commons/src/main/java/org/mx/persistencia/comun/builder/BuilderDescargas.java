package org.mx.persistencia.comun.builder;

import org.mx.modelo.comun.bo.DescargaBO;
import org.mx.modelo.comun.po.DescargaPO;

public class BuilderDescargas {

	public static DescargaBO builderBO(DescargaPO po){
		DescargaBO bo = new DescargaBO();
		bo.setIdDescarga(po.getIdDescarga());
		bo.setIdUsuario(po.getIdUsuario());
		bo.setEncabezados(po.getEncabezados());
		bo.setTipo(po.getTipo());
		bo.setDescargado(po.getDescargado());
		bo.setSqlConsulta(po.getSqlConsulta());
		bo.setLocacion(po.getLocacionTemp());
		
		return bo;
	}
	
}
