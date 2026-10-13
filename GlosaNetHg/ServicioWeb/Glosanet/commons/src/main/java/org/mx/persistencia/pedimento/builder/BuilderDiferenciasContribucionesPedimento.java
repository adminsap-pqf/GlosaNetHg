package org.mx.persistencia.pedimento.builder;

import java.sql.Timestamp;

import org.mx.modelo.comun.po.DataStagePO;
import org.mx.modelo.datastage.bo.DiferenciasContribucionesPedimentoBO;
import org.mx.modelo.datastage.po.DiferenciasContribucionesPedimentoPO;
import org.mx.persistencia.comun.builder.BuilderDataStage;

public class BuilderDiferenciasContribucionesPedimento {

	public static DiferenciasContribucionesPedimentoPO builderPO(DiferenciasContribucionesPedimentoBO bo){

		DiferenciasContribucionesPedimentoPO po = new DiferenciasContribucionesPedimentoPO();
		po.setClaveContribucion(bo.getClaveContribucion());
		if (bo.getFechaPagoReal() != null && !bo.getFechaPagoReal().equals(""))
			po.setFechaPagoReal(new Timestamp(bo.getFechaPagoReal().getTime()));
		po.setFormaPago(bo.getFormaPago());
		po.setImportePago(bo.getImportePago());
		po.setPatente(bo.getPatente());
		po.setPedimento(bo.getPedimento());
		po.setPkDiferenciasContribucionesPedimento(bo.getIdDiferenciasContribucionesPedimento());
		po.setSeccionAduanera(bo.getSeccionAduanera());
		po.setTipoPedimento(bo.getTipoPedimento());

		DataStagePO dataStage = BuilderDataStage.buildPO(bo.getDataStage());
		po.setDataStage(dataStage);

		return po;
	}
}
