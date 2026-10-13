package org.mx.persistencia.mercancia.builder;

import java.sql.Timestamp;

import org.mx.modelo.comun.po.DataStagePO;
import org.mx.modelo.datastage.bo.DescargosMercanciaBO;
import org.mx.modelo.datastage.po.DescargosMercanciaPO;
import org.mx.persistencia.comun.builder.BuilderDataStage;

public class BuilderDescargosMercancias {

	public static DescargosMercanciaPO builderPO(DescargosMercanciaBO bo){

		DescargosMercanciaPO po = new DescargosMercanciaPO();
		po.setDocumentoOriginal(bo.getDocumentoOriginal());
		if (bo.getFechaOperacionOrig() != null)
			po.setFechaOperacionOrig(new Timestamp(bo.getFechaOperacionOrig().getTime()));
		if (bo.getFechaPagoReal() != null)
			po.setFechaPagoReal(new Timestamp(bo.getFechaPagoReal().getTime()));
		po.setFraccionOriginal(bo.getFraccionOriginal());
		po.setMercanciaDescargada(bo.getMercanciaDescargada());
		po.setPatente(bo.getPatente());
		po.setPedimento(bo.getPedimento());
		po.setPedimentoOriginal(bo.getPedimentoOriginal());
		po.setPatenteAduanalOrig(bo.getPatenteAduanalOrig());
		po.setPkDescargosMercancia(bo.getPkDescargosMercancia());
		po.setSeccionAduanera(bo.getSeccionAduanera());
		po.setSeccionAduaneraDespOrig(bo.getSeccionAduaneraDespOrig());
		po.setTipoPedimento(bo.getTipoPedimento());
		po.setUnidadMedida(bo.getUnidadMedida());

		DataStagePO dataStage = BuilderDataStage.buildPO(bo.getDataStage());
		po.setDataStage(dataStage);

		return po;
	}
	
}
