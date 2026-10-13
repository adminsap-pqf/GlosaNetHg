package org.mx.persistencia.comun.builder;

import org.mx.modelo.comun.bo.PaqueteBO;
import org.mx.modelo.comun.po.PaquetePO;

public class BuilderPaquete {

	public static PaqueteBO builderBO(PaquetePO po){

		PaqueteBO bo = new PaqueteBO();
		bo.setCostoAnual(po.getCostoAnual());
		bo.setCostoConfiguracion(po.getCostoConfiguracion());
		bo.setCostoMensual(po.getCostoMensual());
		bo.setCostoSemestral(po.getCostoSemestral());
		bo.setIdPaquete(po.getPkPaquete());
		bo.setNombre(po.getNombre());
		bo.setPedimentos(po.getPedimentos());
		bo.setTipo(po.getTipo());
		bo.setUsuarios(po.getUsuarios());
		
		return bo;
	}
}
