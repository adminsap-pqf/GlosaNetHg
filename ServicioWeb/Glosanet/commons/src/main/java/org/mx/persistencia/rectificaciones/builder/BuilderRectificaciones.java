package org.mx.persistencia.rectificaciones.builder;

import java.sql.Timestamp;

import org.mx.modelo.comun.po.DataStagePO;
import org.mx.modelo.datastage.bo.RectificacionesBO;
import org.mx.modelo.datastage.po.RectificacionesPO;
import org.mx.persistencia.comun.builder.BuilderDataStage;

public class BuilderRectificaciones {

	public static RectificacionesPO builderPO(RectificacionesBO bo){

		RectificacionesPO po = new RectificacionesPO();
		po.setClaveDocumento(bo.getClaveDocumento());
		po.setDocumentoAnterior(bo.getDocumentoAnterior());
		if (bo.getFechaOperacionAnterior() != null)
			po.setFechaOperacionAnterior(new Timestamp(bo.getFechaOperacionAnterior().getTime()));
		if (bo.getFechaPago() != null)
			po.setFechaPago(new Timestamp(bo.getFechaPago().getTime()));
		if (bo.getFechaPagoReal() != null)
			po.setFechaPagoReal(new Timestamp(bo.getFechaPagoReal().getTime()));
		po.setPatente(bo.getPatente());
		po.setPatenteAduanalOrig(bo.getPatenteAduanalOrig());
		po.setPatenteAnterior(bo.getPatenteAnterior());
		po.setPedimento(bo.getPedimento());
		po.setPedimentoAnterior(bo.getPedimentoAnterior());
		po.setPedimentoOriginal(bo.getPedimentoOriginal());
		po.setPkRectificaciones(bo.getIdRectificaciones());
		po.setSeccionAduanera(bo.getSeccionAduanera());
		po.setSeccionAduaneraAnterior(bo.getSeccionAduaneraAnterior());
		po.setSeccionAduaneraDespOrig(bo.getSeccionAduaneraDespOrig());

		DataStagePO dataStage = BuilderDataStage.buildPO(bo.getDataStage());
		po.setDataStage(dataStage);

		return po;
	}
	
}
