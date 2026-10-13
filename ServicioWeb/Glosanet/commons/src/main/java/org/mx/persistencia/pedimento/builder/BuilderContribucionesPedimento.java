package org.mx.persistencia.pedimento.builder;

import java.sql.Timestamp;

import org.mx.modelo.comun.po.DataStagePO;
import org.mx.modelo.datastage.bo.ContribucionesPedimentoBO;
import org.mx.modelo.datastage.po.ContribucionesPedimentoPO;
import org.mx.persistencia.comun.builder.BuilderDataStage;

public class BuilderContribucionesPedimento {

	public static ContribucionesPedimentoPO builderPO(ContribucionesPedimentoBO bo){

		ContribucionesPedimentoPO po = new ContribucionesPedimentoPO();
		po.setClaveContribucion(bo.getClaveContribucion());
		if (bo.getFechaPagoReal() != null)
			po.setFechaPagoReal(new Timestamp(bo.getFechaPagoReal().getTime()));
		po.setFormaPago(bo.getFormaPago());
		po.setImportePago(bo.getImportePago());
		po.setPatente(bo.getPatente());
		po.setPedimento(bo.getPedimento());
		po.setPkContribucionesPedimento(bo.getIdContribucionesPedimento());
		po.setSeccionAduanera(bo.getSeccionAduanera());
		po.setTipoPedimento(bo.getTipoPedimento());

		DataStagePO dataStage = BuilderDataStage.buildPO(bo.getDataStage());
		po.setDataStage(dataStage);

		return po;
	}

}
