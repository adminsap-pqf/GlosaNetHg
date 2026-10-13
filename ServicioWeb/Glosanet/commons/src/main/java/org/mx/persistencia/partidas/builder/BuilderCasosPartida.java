package org.mx.persistencia.partidas.builder;

import java.sql.Timestamp;

import org.mx.modelo.comun.po.DataStagePO;
import org.mx.modelo.datastage.bo.CasosPartidaBO;
import org.mx.modelo.datastage.po.CasosPartidaPO;
import org.mx.persistencia.comun.builder.BuilderDataStage;

public class BuilderCasosPartida {

	public static CasosPartidaPO builderPO(CasosPartidaBO bo){

		CasosPartidaPO po = new CasosPartidaPO();
		po.setClaveCaso(bo.getClaveCaso());
		po.setComplementoCaso(bo.getComplementoCaso());
		if (bo.getFechaPagoReal() != null)
			po.setFechaPagoReal(new Timestamp(bo.getFechaPagoReal().getTime()));
		po.setFraccion(bo.getFraccion());
		po.setIdentificadorCaso(bo.getIdentificadorCaso());
		po.setPatente(bo.getPatente());
		po.setPedimento(bo.getPedimento());
		po.setPkCasosPartida(bo.getIdCasosPartida());
		po.setSeccionAduanera(bo.getSeccionAduanera());
		po.setSecuenciaFraccion(bo.getSecuenciaFraccion());

		DataStagePO dataStage = BuilderDataStage.buildPO(bo.getDataStage());
		po.setDataStage(dataStage);

		return po;
	}

}
