package org.mx.persistencia.pedimento.builder;

import java.sql.Timestamp;

import org.mx.modelo.comun.po.DataStagePO;
import org.mx.modelo.datastage.bo.ObservacionesPedimentoBO;
import org.mx.modelo.datastage.po.ObservacionesPedimentoPO;
import org.mx.persistencia.comun.builder.BuilderDataStage;

public class BuilderObservacionesPedimento {

	public static ObservacionesPedimentoPO builderPO (ObservacionesPedimentoBO bo){

		ObservacionesPedimentoPO po = new ObservacionesPedimentoPO();
		if (bo.getFechaValidacionPagoR() != null && !bo.getFechaValidacionPagoR().equals(""))
			po.setFechaValidacionPagoR(new Timestamp(bo.getFechaValidacionPagoR().getTime()));
		po.setObservaciones(bo.getObservaciones());
		po.setPatente(bo.getPatente());
		po.setPedimento(bo.getPedimento());
		po.setPkObservacionesPedimento(bo.getIdObservacionesPedimento());
		po.setSeccionAduanera(bo.getSeccionAduanera());
		po.setSecuenciaObservacion(bo.getSecuenciaObservacion());
		po.setTipoPedimento(bo.getTipoPedimento());

		DataStagePO dataStage = BuilderDataStage.buildPO(bo.getDataStage());
		po.setDataStage(dataStage);

		return po;
	}
}
