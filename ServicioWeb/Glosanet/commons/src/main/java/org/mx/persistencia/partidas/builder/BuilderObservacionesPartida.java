package org.mx.persistencia.partidas.builder;

import java.sql.Timestamp;

import org.mx.modelo.comun.po.DataStagePO;
import org.mx.modelo.datastage.bo.ObservacionesPartidaBO;
import org.mx.modelo.datastage.po.ObservacionesPartidaPO;
import org.mx.persistencia.comun.builder.BuilderDataStage;

public class BuilderObservacionesPartida {

	public static ObservacionesPartidaPO builderPO(ObservacionesPartidaBO bo){

		ObservacionesPartidaPO po = new ObservacionesPartidaPO();
		if (bo.getFechaPagoReal() != null)
			po.setFechaPagoReal(new Timestamp(bo.getFechaPagoReal().getTime()));
		po.setFraccion(bo.getFraccion());
		po.setObservaciones(bo.getObservaciones());
		po.setPatente(bo.getPatente());
		po.setPedimento(bo.getPedimento());
		po.setPkObservacionesPartida(bo.getIdObservacionesPartida());
		po.setSeccionAduanera(bo.getSeccionAduanera());
		po.setSecuenciaFraccion(bo.getSecuenciaFraccion());
		po.setSecuenciaObservacion(bo.getSecuenciaObservacion());

		DataStagePO dataStage = BuilderDataStage.buildPO(bo.getDataStage());
		po.setDataStage(dataStage);

		return po;
	}
}
