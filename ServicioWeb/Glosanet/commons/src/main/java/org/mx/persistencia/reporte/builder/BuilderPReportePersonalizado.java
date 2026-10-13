package org.mx.persistencia.reporte.builder;

import org.mx.modelo.reportes.bo.PReportesPersonalizadosBO;
import org.mx.modelo.reportes.po.PReportesPersonalizadosPO;

public class BuilderPReportePersonalizado {

	public static PReportesPersonalizadosBO builderBO(PReportesPersonalizadosPO po){
		PReportesPersonalizadosBO bo = new PReportesPersonalizadosBO();
		bo.setIdRPersonalizado_ColumnaTDS(po.getIdPReportesPersonalizados());
		if(po.getCampoTabla() != null){
			bo.setCampoTabla(BuilderCampoTabla.builderBO(po.getCampoTabla(), true));
		}
		bo.setReportePersonalizado(BuilderReportePersonalizado.builderBO(po.getReportePersonalizado(), true));

		return bo;
	}
}
