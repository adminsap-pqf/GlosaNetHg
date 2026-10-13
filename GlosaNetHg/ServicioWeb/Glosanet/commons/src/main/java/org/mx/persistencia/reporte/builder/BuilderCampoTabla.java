package org.mx.persistencia.reporte.builder;

import org.mx.modelo.reportes.bo.CampoTablaBO;
import org.mx.modelo.reportes.bo.TablaDataStageBO;
import org.mx.modelo.reportes.po.CampoTablaPO;

public class BuilderCampoTabla {
	
	public static CampoTablaBO builderBO(CampoTablaPO po){
		CampoTablaBO bo = new CampoTablaBO();
		bo.setIdCampo(po.getPk_campo());
		bo.setNombre(po.getNombre());
		if(po.getIdioma().equalsIgnoreCase("ingles")){
			bo.setDescripcion(po.getDescripcionEn());
		}else{
			bo.setDescripcion(po.getDescripcion());
		}
		
		return bo;
	}
	
	public static CampoTablaBO builderBO(CampoTablaPO po, Boolean oneToMany){
		CampoTablaBO bo = new CampoTablaBO();
		bo.setIdCampo(po.getPk_campo());
		bo.setNombre(po.getNombre());
		if(po.getIdioma() != null && po.getIdioma().equalsIgnoreCase("ingles")){
			bo.setDescripcion(po.getDescripcionEn());
		}else{
			bo.setDescripcion(po.getDescripcion());
		}
		po.getTabla().setIdioma(po.getIdioma());
		TablaDataStageBO tabla = BuilderTablaDataStage.builderBO(po.getTabla(), true);
		bo.setTabla(tabla);
		
		return bo;
	}

}
