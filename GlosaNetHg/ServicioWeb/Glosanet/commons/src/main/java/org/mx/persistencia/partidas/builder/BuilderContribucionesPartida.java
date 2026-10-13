package org.mx.persistencia.partidas.builder;

import java.sql.Timestamp;

import org.mx.modelo.comun.po.DataStagePO;
import org.mx.modelo.datastage.bo.ContribucionesPartidaBO;
import org.mx.modelo.datastage.bo.PartidaBO;
import org.mx.modelo.datastage.po.ContribucionesPartidaPO;
import org.mx.modelo.datastage.po.PartidaPO;
import org.mx.persistencia.comun.builder.BuilderDataStage;
import org.mx.persistencia.comun.builder.BuilderPartida;

public class BuilderContribucionesPartida {

	public static ContribucionesPartidaPO builderPO(ContribucionesPartidaBO bo){

		ContribucionesPartidaPO po = new ContribucionesPartidaPO();
		po.setClaveContribucion(bo.getClaveContribucion());
		if (bo.getFechaPagoReal() != null)
			po.setFechaPagoReal(new Timestamp(bo.getFechaPagoReal().getTime()));
		po.setFormaPago(bo.getFormaPago());
		po.setImportePago(bo.getImportePago());
		po.setPkContribucionesPartida(bo.getId());

		DataStagePO dataStage = BuilderDataStage.buildPO(bo.getDataStage());
		po.setDataStage(dataStage);
		PartidaPO partida = BuilderPartida.builderPO(bo.getPartida());
		po.setPartidas(partida);

		return po;
	}

	public static ContribucionesPartidaBO builderBO(ContribucionesPartidaPO po){

		ContribucionesPartidaBO bo = new ContribucionesPartidaBO();
		bo.setId(po.getPkContribucionesPartida());
		bo.setClaveContribucion(po.getClaveContribucion());
		bo.setFechaPagoReal(po.getFechaPagoReal());
		bo.setFormaPago(po.getFormaPago());
		bo.setFraccion(po.getPartidas().getId().getFraccion());
		bo.setImportePago(po.getImportePago());
		bo.setPatente(po.getPartidas().getId().getPatente());
		bo.setPedimento(po.getPartidas().getId().getPedimento());
		bo.setSeccionAduanera(po.getPartidas().getId().getSeccionAduanera());
		bo.setSecuenciaFraccion(po.getPartidas().getId().getSecuenciaFraccion());

		PartidaBO partida = BuilderPartida.builderBO(po.getPartidas());
		bo.setPartida(partida);

		return bo;
	}
}
