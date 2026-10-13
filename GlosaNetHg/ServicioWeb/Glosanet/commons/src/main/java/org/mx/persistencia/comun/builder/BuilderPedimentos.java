package org.mx.persistencia.comun.builder;

import org.mx.modelo.comun.bo.PedimentosBO;
import org.mx.modelo.comun.po.PedimentosPO;

public class BuilderPedimentos {

	public static PedimentosBO builderBO(PedimentosPO po){
		PedimentosBO bo = new PedimentosBO();
		bo.setIdPedimentos(po.getIdPedimentos());
		bo.setIdCliente(po.getIdCliente());
		bo.setNumeroPedimentos(po.getNumeroPedimentos());
		bo.setPeriodo(po.getPeriodo());
		
		return bo;
	}
}
