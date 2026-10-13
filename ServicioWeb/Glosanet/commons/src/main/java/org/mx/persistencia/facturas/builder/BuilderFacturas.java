package org.mx.persistencia.facturas.builder;

import java.sql.Timestamp;

import org.mx.modelo.comun.po.DataStagePO;
import org.mx.modelo.datastage.bo.DataStageBO;
import org.mx.modelo.datastage.bo.FacturasBO;
import org.mx.modelo.datastage.po.FacturasPO;
import org.mx.modelo.gaia.Country;
import org.mx.persistencia.comun.builder.BuilderDataStage;

public class BuilderFacturas {

	public static FacturasPO builderPO(FacturasBO bo){

		FacturasPO po = new FacturasPO();
		po.setCalleProveedor(bo.getCalleProveedor());
		po.setCpProveedor(bo.getCpProveedor());
		po.setEntidadFedFacturacion(bo.getEntidadFedFacturacion());
		if (bo.getFechaFacturacion() != null)
			po.setFechaFacturacion(new Timestamp(bo.getFechaFacturacion().getTime()));
		if (bo.getFechaPagoReal() != null)
			po.setFechaPagoReal(new Timestamp(bo.getFechaPagoReal().getTime()));
		po.setIndentFiscalProveedor(bo.getIndentFiscalProveedor());
		po.setMonedaFacturacion(bo.getMonedaFacturacion());
		po.setMunicipioProveedor(bo.getMunicipioProveedor());
		po.setNumeroFactura(bo.getNumeroFactura());
		po.setNumExteriorProveedor(bo.getNumExteriorProveedor());
		po.setNumInteriorProveedor(bo.getNumInteriorProveedor());
		po.setPaisFacturacion(bo.getPaisFacturacion());
		po.setPatente(bo.getPatente());
		po.setPedimento(bo.getPedimento());
		po.setPkFacturas(bo.getIdFacturas());
		po.setProveedorMercancia(bo.getProveedorMercancia());
		po.setSeccionAduanera(bo.getSeccionAduanera());
		po.setTerminoFacturacion(bo.getTerminoFacturacion());
		po.setValorDolares(bo.getValorDolares());
		po.setValorMonedaExtranjera(bo.getValorMonedaExtranjera());

		DataStagePO dataStage = BuilderDataStage.buildPO(bo.getDataStage());
		po.setDataStage(dataStage);

		return po;
	}
	
	public static FacturasBO builderBO(FacturasPO po)
	{
		
		FacturasBO bo = new FacturasBO();
		bo.setCalleProveedor(po.getCalleProveedor());
		bo.setCpProveedor(po.getCpProveedor());
		bo.setEntidadFedFacturacion(po.getEntidadFedFacturacion());
		bo.setFechaFacturacion(po.getFechaFacturacion());
		bo.setFechaPagoReal(po.getFechaPagoReal());
		bo.setIdFacturas(po.getPkFacturas());
		bo.setIndentFiscalProveedor(po.getIndentFiscalProveedor());
		bo.setMonedaFacturacion(po.getMonedaFacturacion());
		bo.setMunicipioProveedor(po.getMunicipioProveedor());
		bo.setNumeroFactura(po.getNumeroFactura());
		bo.setNumExteriorProveedor(po.getNumExteriorProveedor());
		bo.setNumInteriorProveedor(po.getNumInteriorProveedor());
		bo.setPaisFacturacion(po.getPaisFacturacion());
		bo.setPatente(po.getPatente());
		bo.setPedimento(po.getPedimento());
		bo.setProveedorMercancia(po.getProveedorMercancia());
		bo.setSeccionAduanera(po.getSeccionAduanera());
		bo.setTerminoFacturacion(po.getTerminoFacturacion());
		bo.setValorDolares(po.getValorDolares());
		bo.setValorMonedaExtranjera(po.getValorMonedaExtranjera());
		
		if (po.getPaisFacturacion() != null && 
				!po.getPaisFacturacion().equals(""))
			try {
				bo.setRegion(Country.getInstance(po.getPaisFacturacion()).getContinent().getNombre());
			} catch (Exception e) {
				//e.printStackTrace();
			}
		
		DataStageBO dataStage = BuilderDataStage.buildBO(po.getDataStage());
		bo.setDataStage(dataStage);
		
		return bo;
	}
}
