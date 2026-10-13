package org.mx.persistencia.comun.builder;

import org.mx.modelo.comun.bo.PrevisionProductoBO;
import org.mx.modelo.comun.po.PrevisionProductoPO;

public class BuilderPrevisionProducto {
	
	public static PrevisionProductoBO builderBO(PrevisionProductoPO po){

        PrevisionProductoBO bo = new PrevisionProductoBO();
        bo.setIdPrevisionProducto(po.getIdPrevisionProducto());
//        if(po.getPrevision() != null){
//        	bo.setPrevision(BuilderPrevisiones.builderBO(po.getPrevision()));
//        }
        
        bo.setPrevision(po.getPrevision());
        bo.setIdProducto(po.getIdProducto());
        bo.setCantidad(po.getCantidad());
        bo.setUnidad(po.getUnidad());
        
        return bo;
	}

}
