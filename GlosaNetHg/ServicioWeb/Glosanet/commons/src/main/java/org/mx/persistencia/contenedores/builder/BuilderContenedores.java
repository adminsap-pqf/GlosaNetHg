package org.mx.persistencia.contenedores.builder;

import java.sql.Timestamp;

import org.mx.modelo.comun.po.DataStagePO;
import org.mx.modelo.datastage.bo.ContenedoresBO;
import org.mx.modelo.datastage.po.ContenedoresPO;
import org.mx.persistencia.comun.builder.BuilderDataStage;

public class BuilderContenedores {

	public static ContenedoresPO builderPO(ContenedoresBO bo){

		ContenedoresPO po = new ContenedoresPO();
		if (bo.getFechaPagoReal() != null)
			po.setFechaPagoReal(new Timestamp(bo.getFechaPagoReal().getTime()));
		po.setNumContenedor(bo.getNumContenedor());
		po.setPatente(bo.getPatente());
		po.setPedimento(bo.getPedimento());
		po.setPkContenedores(bo.getIdContenedores());
		po.setSeccionAduanera(bo.getSeccionAduanera());
		po.setTipoContenedor(bo.getTipoContenedor());

		DataStagePO dataStage = BuilderDataStage.buildPO(bo.getDataStage());
		po.setDataStage(dataStage);

		return po;
	}
}
