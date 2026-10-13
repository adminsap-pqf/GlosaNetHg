package org.mx.persistencia.comun.builder;

import java.sql.Timestamp;

import org.mx.modelo.datastage.bo.PartidaBO;
import org.mx.modelo.datastage.po.PartidaPO;

public class BuilderPartida {

public static PartidaBO builderBO(PartidaPO po){
		
		PartidaBO bo = new PartidaBO();
		bo.setCantidadUmcomercial(po.getCantidadUmcomercial());
		bo.setCantidadUmtarifa(po.getCantidadUmtarifa());
		bo.setClaveDocumento(po.getClaveDocumento());
		bo.setClaveVinculacion(po.getClaveVinculacion());
		bo.setCodigoMercanciaProducto(po.getCodigoMercanciaProducto());
		bo.setDescripcionMercancia(po.getDescripcionMercancia());
		bo.setEntidadFedComprador(po.getEntidadFedComprador());
		bo.setEntidadFedDestino(po.getEntidadFedDestino());
		bo.setEntidadFedOrigen(po.getEntidadFedOrigen());
		bo.setEntidadFedVendedor(po.getEntidadFedVendedor());
		bo.setFechaPagoReal(po.getFechaPagoReal());
		bo.setFraccion(po.getId().getFraccion());
		bo.setMarcaMercanciaProducto(po.getMarcaMercanciaProducto());
		bo.setMetodoValorizacion(po.getMetodoValorizacion());
		bo.setModeloMercanciaProducto(po.getModeloMercanciaProducto());
		bo.setPaisCompradorVendedor(po.getPaisCompradorVendedor());
		bo.setPaisOrigenDestino(po.getPaisOrigenDestino());
		bo.setPatente(po.getId().getPatente());
		bo.setPedimento(po.getId().getPedimento());
		bo.setPrecioUnitario(po.getPrecioUnitario());
		bo.setSeccionAduanera(po.getId().getSeccionAduanera());
		bo.setSecuenciaFraccion(po.getId().getSecuenciaFraccion());
		bo.setSubdivisionFraccion(po.getSubdivisionFraccion());
		bo.setTipoOperacion(po.getTipoOperacion());
		bo.setUnidadMedidaComercial(po.getUnidadMedidaComercial());
		bo.setUnidadMedidaTarifa(po.getUnidadMedidaTarifa());
		bo.setValorAduana(po.getValorAduana());
		bo.setValorAgregado(po.getValorAgregado());
		bo.setValorComercial(po.getValorComercial());
		bo.setValorDolares(po.getValorDolares());
		
		return bo;
	}
	
	public static PartidaPO builderPO(PartidaBO bo){
		
		PartidaPO po = new PartidaPO();
		po.setCantidadUmcomercial(bo.getCantidadUmcomercial());
		po.setCantidadUmtarifa(bo.getCantidadUmtarifa());
		po.setClaveDocumento(bo.getClaveDocumento());
		po.setClaveVinculacion(bo.getClaveVinculacion());
		po.setCodigoMercanciaProducto(bo.getCodigoMercanciaProducto());
		po.setDescripcionMercancia(bo.getDescripcionMercancia());
		po.setEntidadFedComprador(bo.getEntidadFedComprador());
		po.setEntidadFedDestino(bo.getEntidadFedDestino());
		po.setEntidadFedOrigen(bo.getEntidadFedOrigen());
		po.setEntidadFedVendedor(bo.getEntidadFedVendedor());
		if (bo.getFechaPagoReal() != null)
			po.setFechaPagoReal(new Timestamp(bo.getFechaPagoReal().getTime()));
		po.getId().setFraccion(bo.getFraccion());
		po.getId().setPatente(bo.getPatente());
		po.getId().setPedimento(bo.getPedimento());
		po.getId().setSeccionAduanera(bo.getSeccionAduanera());
		po.getId().setSecuenciaFraccion(bo.getSecuenciaFraccion());
		po.setMarcaMercanciaProducto(bo.getMarcaMercanciaProducto());
		po.setMetodoValorizacion(bo.getMetodoValorizacion());
		po.setModeloMercanciaProducto(bo.getModeloMercanciaProducto());
		po.setPaisCompradorVendedor(bo.getPaisCompradorVendedor());
		po.setPaisOrigenDestino(bo.getPaisOrigenDestino());
		po.setPrecioUnitario(bo.getPrecioUnitario());
		po.setSubdivisionFraccion(bo.getSubdivisionFraccion());
		po.setValorAduana(bo.getValorAduana());
		po.setValorAgregado(bo.getValorAgregado());
		po.setValorComercial(bo.getValorComercial());
		po.setValorDolares(bo.getValorDolares());
		po.setTipoOperacion(bo.getTipoOperacion());
		po.setUnidadMedidaComercial(bo.getUnidadMedidaComercial());
		po.setUnidadMedidaTarifa(bo.getUnidadMedidaTarifa());
		
		return po;
	}
	
}
