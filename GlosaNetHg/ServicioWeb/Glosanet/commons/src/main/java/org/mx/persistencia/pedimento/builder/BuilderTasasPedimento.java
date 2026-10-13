package org.mx.persistencia.pedimento.builder;

import java.sql.Timestamp;

import org.mx.modelo.comun.po.DataStagePO;
import org.mx.modelo.datastage.bo.TasasPedimentoBO;
import org.mx.modelo.datastage.po.TasasPedimentoPO;
import org.mx.persistencia.comun.builder.BuilderDataStage;

public class BuilderTasasPedimento {
	
	public static TasasPedimentoPO builderPO(TasasPedimentoBO bo){
		TasasPedimentoPO po = new TasasPedimentoPO();
		if (bo.getFechaPagoReal() != null && !bo.getFechaPagoReal().equals(""))
			po.setFechaPagoReal(new Timestamp(bo.getFechaPagoReal().getTime()));
		
		po.setPkTasasPedimento(bo.getIdTasasPedimento());
		po.setPatente(bo.getPatente());
		po.setPedimento(bo.getPedimento());
		po.setSeccionAduanera(bo.getSeccionAduanera());
		po.setClaveContribucion(bo.getClaveContribucion());
		po.setTasaContribucion(bo.getTasaContribucion());
		po.setTipoTasa(bo.getTipoTasa());
		po.setTipoPedimento(bo.getTipoPedimento());
		
		
		DataStagePO dataStage = BuilderDataStage.buildPO(bo.getDataStage());
		po.setDataStage(dataStage);
		
		return po;
	}

}
