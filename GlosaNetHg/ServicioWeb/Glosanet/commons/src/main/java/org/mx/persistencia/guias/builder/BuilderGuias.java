package org.mx.persistencia.guias.builder;

import java.sql.Timestamp;

import org.mx.modelo.comun.po.DataStagePO;
import org.mx.modelo.datastage.bo.GuiasBO;
import org.mx.modelo.datastage.po.GuiasPO;
import org.mx.persistencia.comun.builder.BuilderDataStage;

public class BuilderGuias {

	public static GuiasPO builderPO(GuiasBO bo){

		GuiasPO po = new GuiasPO();
		if (bo.getFechaPagoReal() != null)
			po.setFechaPagoReal(new Timestamp(bo.getFechaPagoReal().getTime()));
		po.setNumeroGuia(bo.getNumeroGuia());
		po.setPatente(bo.getPatente());
		po.setPedimento(bo.getPedimento());
		po.setPkGuias(bo.getIdGuias());
		po.setSeccionAduanera(bo.getSeccionAduanera());
		po.setTipoGuia(bo.getTipoGuia());

		DataStagePO dataStage = BuilderDataStage.buildPO(bo.getDataStage());
		po.setDataStage(dataStage);

		return po;
	}
}
