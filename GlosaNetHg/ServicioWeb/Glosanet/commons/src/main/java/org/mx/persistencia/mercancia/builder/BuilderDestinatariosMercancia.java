package org.mx.persistencia.mercancia.builder;

import java.sql.Timestamp;

import org.mx.modelo.comun.po.DataStagePO;
import org.mx.modelo.datastage.bo.DestinatariosMercanciaBO;
import org.mx.modelo.datastage.po.DestinatariosMercanciaPO;
import org.mx.persistencia.comun.builder.BuilderDataStage;

public class BuilderDestinatariosMercancia {

	public static DestinatariosMercanciaPO builderPO(DestinatariosMercanciaBO bo){

		DestinatariosMercanciaPO po = new DestinatariosMercanciaPO();
		po.setCalleDestinatario(bo.getCalleDestinatario());
		po.setCpDestinatario(bo.getCpDestinatario());
		if (bo.getFechaPagoReal() != null)
			po.setFechaPagoReal(new Timestamp(bo.getFechaPagoReal().getTime()));
		po.setIndentFiscalDestinatario(bo.getIndentFiscalDestinatario());
		po.setMunicpioDestinatario(bo.getMunicpioDestinatario());
		po.setNombreDestinatarioMercancia(bo.getNombreDestinatarioMercancia());
		po.setNumExteriorDestinatario(bo.getNumExteriorDestinatario());
		po.setNumInteriorDestinatario(bo.getNumInteriorDestinatario());
		po.setPaisDestinatario(bo.getPaisDestinatario());
		po.setPatente(bo.getPatente());
		po.setPedimento(bo.getPedimento());
		po.setPkDestinatariosMercancia(bo.getIdDestinatariosMercancia());
		po.setSeccionAduanera(bo.getSeccionAduanera());

		DataStagePO dataStage = BuilderDataStage.buildPO(bo.getDataStage());
		po.setDataStage(dataStage);

		return po;
	}
}
