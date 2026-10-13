package org.mx.persistencia.reporte.builder;

import org.mx.modelo.reportes.bo.TablaDataStageBO;
import org.mx.modelo.reportes.po.CampoTablaPO;
import org.mx.modelo.reportes.po.TablaDataStagePO;

public class BuilderTablaDataStage {

	public static TablaDataStageBO builderBO(TablaDataStagePO po){

		TablaDataStageBO bo = new TablaDataStageBO();
		bo.setIdTabla(po.getPk_Tabla());
		bo.setNombre(po.getNombre());
		if(po.getIdioma().equalsIgnoreCase("ingles")){
			bo.setDescripcion(po.getDescripcionEn());
		}else{
			bo.setDescripcion(po.getDescripcion());
		}
		bo.setNumero(po.getNumero());
		bo.setNivelPartida(po.getNivelPartida());
		
		for(CampoTablaPO campo : po.getCampos()){
			campo.setIdioma(po.getIdioma());
			bo.getCampos().add(BuilderCampoTabla.builderBO(campo,true));
		}
		return bo;
		
	}
	public static TablaDataStageBO builderBO(TablaDataStagePO po, Boolean oneToMany){
		TablaDataStageBO bo = new TablaDataStageBO();
		bo.setIdTabla(po.getPk_Tabla());
		bo.setNombre(po.getNombre());
		if(po.getIdioma() != null && po.getIdioma().equalsIgnoreCase("ingles")){
			bo.setDescripcion(po.getDescripcionEn());
		}else{
			bo.setDescripcion(po.getDescripcion());
		}
		bo.setNumero(po.getNumero());
		bo.setNivelPartida(po.getNivelPartida());
		
		return bo;
		
	}
	
}
