package org.mx.persistencia.pedimento.builder;

import java.sql.Timestamp;

import org.mx.modelo.comun.po.DataStagePO;
import org.mx.modelo.datastage.bo.CasosPedimentoBO;
import org.mx.modelo.datastage.po.CasosPedimentoPO;
import org.mx.persistencia.comun.builder.BuilderDataStage;

public class BuilderCasosPedimento {

	public static CasosPedimentoPO builderPO(CasosPedimentoBO bo){

		CasosPedimentoPO po = new CasosPedimentoPO();
		po.setClaveCaso(bo.getClaveCaso());
		po.setComplementoCaso(bo.getComplementoCaso());
		if (bo.getFechaValidacionPagoR() != null)
			po.setFechaValidacionPagoR(new Timestamp(bo.getFechaValidacionPagoR().getTime()));
		po.setIdentificadorCaso(bo.getIdentificadorCaso());
		po.setPatente(bo.getPatente());
		po.setPedimento(bo.getPedimento());
		po.setPkCasosPedimento(bo.getIdCasosPedimento());
		po.setSeccionAduanera(bo.getSeccionAduanera());
		po.setTipoPedimento(bo.getTipoPedimento());

		DataStagePO dataStage = BuilderDataStage.buildPO(bo.getDataStage());
		po.setDataStage(dataStage);

		return po;
	}
}
