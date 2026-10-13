package org.mx.persistencia.partidas.builder;

import java.sql.Timestamp;

import org.mx.modelo.comun.po.DataStagePO;
import org.mx.modelo.datastage.bo.TasasContribucionesPartidaBO;
import org.mx.modelo.datastage.po.TasasContribucionesPartidaPO;
import org.mx.persistencia.comun.builder.BuilderDataStage;

public class BuilderTasaContribucionesPartida {

	public static TasasContribucionesPartidaPO builderPO(TasasContribucionesPartidaBO bo){
		TasasContribucionesPartidaPO po = new TasasContribucionesPartidaPO();
		
		po.setPkTasasContribucionesPartida(bo.getPkTasasContribucionesPartida());
		po.setPatente(bo.getPatente());
		po.setPedimento(bo.getPedimento());
		po.setSeccionAduanera(bo.getSeccionAduanera());
		po.setFraccion(bo.getFraccion());
		po.setSecuenciaFraccion(bo.getSecuenciaFraccion());
		po.setClaveContribucion(bo.getClaveContribucion());
		po.setTasaContribucion(bo.getTasaContribucion());
		po.setTipoTasa(bo.getTipoTasa());
		
		if (bo.getFechaPagoReal() != null)
			po.setFechaPagoReal(new Timestamp(bo.getFechaPagoReal().getTime()));
		
		DataStagePO dataStage = BuilderDataStage.buildPO(bo.getDataStage());
		po.setDataStage(dataStage);
		
		
		
		return po;
	}

}
