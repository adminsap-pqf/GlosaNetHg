package org.mx.persistencia.comun.builder;

import org.mx.modelo.comun.bo.DireccionFiscalBO;
import org.mx.modelo.comun.po.DireccionFiscalPO;

public class BuilderDireccionFiscal {

	public static DireccionFiscalPO builderPO(DireccionFiscalBO bo){
		DireccionFiscalPO po = new DireccionFiscalPO();
		po.setCalle(bo.getCalle());
		po.setCodigoPostal(bo.getCodigoPostal());
		po.setColonia(bo.getColonia());
		po.setEstado(bo.getEstado());
		po.setMunicipio(bo.getMunicipio());
		po.setNoExterior(bo.getNoExterior());
		po.setNoInterior(bo.getNoInterior());
		po.setPais(bo.getPais());
		po.setPkDireccion(bo.getIdDireccion());
		return po;
	}
	
	public static DireccionFiscalBO builderBO(DireccionFiscalPO po){
		DireccionFiscalBO bo = new DireccionFiscalBO();
		bo.setCalle(po.getCalle());
		bo.setCodigoPostal(po.getCodigoPostal());
		bo.setColonia(po.getColonia());
		bo.setEstado(po.getEstado());
		bo.setMunicipio(po.getMunicipio());
		bo.setNoExterior(po.getNoExterior());
		bo.setNoInterior(po.getNoInterior());
		bo.setPais(po.getPais());
		bo.setIdDireccion(po.getPkDireccion());
		return bo;
	}
	
}
