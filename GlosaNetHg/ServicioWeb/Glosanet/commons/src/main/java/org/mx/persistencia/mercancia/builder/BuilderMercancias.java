package org.mx.persistencia.mercancia.builder;

import java.sql.Timestamp;

import org.mx.modelo.comun.po.DataStagePO;
import org.mx.modelo.datastage.bo.MercanciasBO;
import org.mx.modelo.datastage.po.MercanciasPO;
import org.mx.persistencia.comun.builder.BuilderDataStage;

public class BuilderMercancias {

	public static MercanciasPO builderPO(MercanciasBO bo){

		MercanciasPO po = new MercanciasPO();
		if (bo.getFechaPagoReal() != null)
			po.setFechaPagoReal(new Timestamp(bo.getFechaPagoReal().getTime()));
		po.setFraccion(bo.getFraccion());
		po.setKilometrajeVehiculo(bo.getKilometrajeVehiculo());
		po.setPatente(bo.getPatente());
		po.setPedimento(bo.getPedimento());
		po.setPkMercancias(bo.getIdMercancias());
		po.setSeccionAduanera(bo.getSeccionAduanera());
		po.setSecuenciaFraccion(bo.getSecuenciaFraccion());
		po.setVinNumeroSerie(bo.getVinNumeroSerie());

		DataStagePO dataStage = BuilderDataStage.buildPO(bo.getDataStage());
		po.setDataStage(dataStage);

		return po;
	}
}
