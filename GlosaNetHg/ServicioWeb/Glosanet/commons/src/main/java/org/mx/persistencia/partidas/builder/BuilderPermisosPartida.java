package org.mx.persistencia.partidas.builder;

import java.sql.Timestamp;

import org.mx.modelo.comun.po.DataStagePO;
import org.mx.modelo.datastage.bo.PermisosPartidaBO;
import org.mx.modelo.datastage.po.PermisosPartidaPO;
import org.mx.persistencia.comun.builder.BuilderDataStage;

public class BuilderPermisosPartida {

	public static PermisosPartidaPO builderPO(PermisosPartidaBO bo){

		PermisosPartidaPO po = new PermisosPartidaPO();
		po.setCantidadMumtarifa(bo.getCantidadMumtarifa());
		po.setClavePermiso(bo.getClavePermiso());
		if (bo.getFechaPagoReal() != null)
			po.setFechaPagoReal(new Timestamp(bo.getFechaPagoReal().getTime()));
		po.setFirmaDescargo(bo.getFirmaDescargo());
		po.setFraccion(bo.getFraccion());
		po.setNumeroPermiso(bo.getNumeroPermiso());
		po.setPatente(bo.getPatente());
		po.setPedimento(bo.getPedimento());
		po.setPkPermisosPartida(bo.getIdPermisosPartida());
		po.setSeccionAduanera(bo.getSeccionAduanera());
		po.setSecuenciaFraccion(bo.getSecuenciaFraccion());
		po.setValorComercialDolares(bo.getValorComercialDolares());

		DataStagePO dataStage = BuilderDataStage.buildPO(bo.getDataStage());
		po.setDataStage(dataStage);

		return po;
	}
}
