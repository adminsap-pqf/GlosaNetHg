package org.mx.persistencia.mercancia.builder;

import java.sql.Timestamp;

import org.mx.modelo.comun.po.DataStagePO;
import org.mx.modelo.datastage.bo.TransporteMercanciasBO;
import org.mx.modelo.datastage.po.TransporteMercanciasPO;
import org.mx.persistencia.comun.builder.BuilderDataStage;

public class BuilderTransporteMercancias {

	public static TransporteMercanciasPO builderPO(TransporteMercanciasBO bo){
		TransporteMercanciasPO po = new TransporteMercanciasPO();
		
		if (bo.getFechaPagoReal() != null)
			po.setFechaPagoReal(new Timestamp(bo.getFechaPagoReal().getTime()));
		
		po.setPkTransporteMercancias(bo.getIdTransporteMercancias());
		po.setPatente(bo.getPatente());
		po.setPedimento(bo.getPedimento());
		po.setSeccionAduanera(bo.getSeccionAduanera());
		po.setRfcTransportista(bo.getRfcTransportista());
		po.setCurpTransportista(bo.getCurpTransportista());
		po.setNombreTransportista(bo.getNombreTransportista());
		po.setPaisTransporte(bo.getPaisTransporte());
		po.setIdentificadorTransporte(bo.getIdentificadorTransporte());
	
		DataStagePO dataStage = BuilderDataStage.buildPO(bo.getDataStage());
		po.setDataStage(dataStage);
		
		return po;
	}
}
