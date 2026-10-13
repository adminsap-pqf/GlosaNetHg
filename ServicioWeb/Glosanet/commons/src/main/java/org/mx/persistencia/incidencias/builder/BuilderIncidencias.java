package org.mx.persistencia.incidencias.builder;

import java.sql.Timestamp;

import org.mx.modelo.comun.po.DataStagePO;
import org.mx.modelo.datastage.bo.IncidenciasReconocimientoAduaneroBO;
import org.mx.modelo.datastage.po.IncidenciasReconocimientoAduaneroPO;
import org.mx.persistencia.comun.builder.BuilderDataStage;

public class BuilderIncidencias {

	public static IncidenciasReconocimientoAduaneroPO builderPO (IncidenciasReconocimientoAduaneroBO bo){

		IncidenciasReconocimientoAduaneroPO po = new IncidenciasReconocimientoAduaneroPO();
		po.setClaveDocumento(bo.getClaveDocumento());
		po.setConsecutivoRemesa(bo.getConsecutivoRemesa());
		if (bo.getFechaFinReconocimiento() != null)
			po.setFechaFinReconocimiento(new Timestamp(bo.getFechaFinReconocimiento().getTime()));
		if (bo.getFechaInicioReconocimiento() != null)
			po.setFechaInicioReconocimiento(new Timestamp(bo.getFechaInicioReconocimiento().getTime()));
		if (bo.getFechaSeleccion() != null)
			po.setFechaSeleccion(new Timestamp(bo.getFechaSeleccion().getTime()));
		po.setFraccion(bo.getFraccion());
		po.setGradoIncidencia(bo.getGradoIncidencia());
		po.setHoraFinReconocimiento(bo.getHoraFinReconocimiento());
		po.setHoraInicioReconocimiento(bo.getHoraInicioReconocimiento());
		po.setNumeroSeleccion(bo.getNumeroSeleccion());
		po.setPatente(bo.getPatente());
		po.setPedimento(bo.getPedimento());
		po.setPkIncidenciasReconocimientoAduanero(bo.getIdIncidenciasReconocimientoAduanero());
		po.setSeccionAduanera(bo.getSeccionAduanera());
		po.setSecuenciaFraccion(bo.getSecuenciaFraccion());
		po.setTipoOperacion(bo.getTipoOperacion());

		DataStagePO dataStage = BuilderDataStage.buildPO(bo.getDataStage());
		po.setDataStage(dataStage);

		return po;
	}
}
