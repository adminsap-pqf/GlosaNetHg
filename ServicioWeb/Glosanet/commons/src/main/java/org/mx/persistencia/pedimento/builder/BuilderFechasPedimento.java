package org.mx.persistencia.pedimento.builder;

import java.sql.Timestamp;

import org.mx.modelo.comun.po.DataStagePO;
import org.mx.modelo.datastage.bo.FechasPedimentoBO;
import org.mx.modelo.datastage.po.FechasPedimentoPO;
import org.mx.persistencia.comun.builder.BuilderDataStage;

public class BuilderFechasPedimento {

	public static FechasPedimentoPO builderPO(FechasPedimentoBO bo){

		FechasPedimentoPO po = new FechasPedimentoPO();
		if (bo.getFechaOperacion() != null && !bo.getFechaOperacion().equals(""))
			po.setFechaOperacion(new Timestamp(bo.getFechaOperacion().getTime()));
		if (bo.getFechaValidacionPagoR() != null && !bo.getFechaValidacionPagoR().equals(""))
			po.setFechaValidacionPagoR(new Timestamp(bo.getFechaValidacionPagoR().getTime()));
		po.setPatente(bo.getPatente());
		po.setPedimento(bo.getPedimento());
		po.setPkFechasPedimento(bo.getIdFechasPedimento());
		po.setSeccionAduanera(bo.getSeccionAduanera());
		po.setTipoFecha(bo.getTipoFecha());

		DataStagePO dataStage = BuilderDataStage.buildPO(bo.getDataStage());
		po.setDataStage(dataStage);

		return po;
	}
}
