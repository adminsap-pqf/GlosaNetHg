package org.mx.persistencia.registros.builder;

import org.mx.modelo.registros.bo.TiempoRealizacionBO;
import org.mx.modelo.registros.po.TiempoRealizacionPO;

public class BuilderTiempoRealizacion {
	
	public static TiempoRealizacionBO builderBO(TiempoRealizacionPO po){
		TiempoRealizacionBO bo = new TiempoRealizacionBO();
		bo.setActivo(po.isActivo());
		bo.setEv1_cantidad(po.getEv1_cantidad());
		bo.setEv1_tipo(po.getEv1_tipo());
		bo.setEv2_cantidad(po.getEv2_cantidad());
		bo.setEv2_tipo(po.getEv2_tipo());
		bo.setEv3_cantidad(po.getEv3_cantidad());
		bo.setEv3_tipo(po.getEv3_tipo());
		bo.setFecha(po.getFecha());
		bo.setIdEmpresa(po.getIdEmpresa());
		bo.setIdTiempo(po.getIdTiempo());
		return bo;
	}

}
