package org.mx.persistencia.registros.builder;

import org.mx.modelo.registros.bo.FeriadosOficialesBO;
import org.mx.modelo.registros.po.FeriadosOficialesPO;

public class BuilderFeriadosOficiales {
	
	public static FeriadosOficialesBO builderBO(FeriadosOficialesPO po){
		FeriadosOficialesBO bo = new FeriadosOficialesBO();
		bo.setIdFeriados(po.getIdFeriados());
		bo.setNombre(po.getNombre());
		bo.setFecha(po.getFecha());
		bo.setPeriodo(po.getPeriodo());
		
		return bo;
	}

}
