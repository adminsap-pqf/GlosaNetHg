package org.mx.persistencia.comun.builder;

import org.mx.modelo.comun.bo.ProductoBO;
import org.mx.modelo.comun.po.ProductoPO;

public class BuilderProducto {

	public static ProductoBO builderBOSimple(ProductoPO po){
		ProductoBO bo = new ProductoBO();
		bo.setIdProducto(po.getIdProducto());
		bo.setIdEmpresa(po.getIdEmpresa());
		bo.setNombre(po.getNombre());
		bo.setOrigen(po.getOrigen());
		bo.setRegistro(po.isRegistro());
		bo.setPermiso(po.isPermiso());
		bo.setCodigo(po.getCodigo());
		if(po.getTipoProducto() != null){
			bo.setTipoProducto(po.getTipoProducto());			
		}
		bo.setPresentacion(po.getPresentacion());
		
		bo.setNumRegistros(0);
		return bo;
	}

	public static ProductoBO builderObjeto(Object[] po ){
		ProductoBO bo = new ProductoBO();
		String codigo = "";
		System.out.println(po[0]);
		if(po[0] != null){ //Fraccion
			codigo += po[0].toString();
		}else{
			codigo += "000000000";
		}
		if(po[1] != null){//SecuenciaFraccion
			codigo += po[1].toString();
		}else{
			codigo += "-00";
		}
		if(po[2] != null){//SubdivisionFraccion
			codigo += po[2].toString();
		}else{
			codigo += "-00";
		}
		bo.setCodigo(codigo);


		if(po[3] != null){//DescripcionMercancia
			bo.setNombre(po[3].toString());
		}
		if(po[4] != null){//PaisCompradorVendedor
			bo.setOrigen(po[4].toString());
		}
		
		bo.setNumRegistros(0);
		bo.setIdProducto((Integer)po[0]);

		return bo;
	}
	
	public static ProductoBO builderBOObject(Object[] po ){
		ProductoBO bo = new ProductoBO();
		bo.setIdProducto((Integer)po[0]);
		bo.setIdEmpresa((Integer)po[1]);
		bo.setNombre(po[2].toString());
		bo.setOrigen(po[3].toString());
		bo.setRegistro((boolean)po[4]);
		bo.setPermiso((boolean)po[5]);
		if(po[6] != null){
			bo.setTipoProducto((Integer)po[6]);
		}
		bo.setCodigo(po[7].toString());
		if(po[8] != null){
			bo.setDescripcion(po[8].toString());			
		}
		if(po[9] != null){
			bo.setPresentacion(po[9].toString());			
		}
		bo.setIdPrevision((Integer)po[11]);
		bo.setCantidad(po[12].toString());
		bo.setUnidad(po[13].toString());
		bo.setIdPrevisionProducto((Integer)po[14]);
		return bo;
	}
	
	
	
	public static ProductoBO builderListaProductos(Object[] po ){
		ProductoBO bo = new ProductoBO();
		bo.setIdProducto((Integer)po[0]);
		bo.setIdEmpresa((Integer)po[1]);
		bo.setNombre(po[2].toString());
		bo.setOrigen(po[3].toString());
		if(po[4] != null){
			bo.setTipoProducto((Integer)po[4]);			
		}
		bo.setCodigo(po[5].toString());
		if(po[6] != null){
			bo.setPresentacion(po[6].toString());			
		}
		bo.setCantidad(po[7].toString());
		bo.setUnidad(po[8].toString());
		bo.setIdPrevision((Integer)po[9]);
		return bo;
	}

}
