package org.mx.persistencia.reporte.builder;

import org.mx.modelo.reportes.bo.ReportePersonalizadoBO;
import org.mx.modelo.reportes.po.ReportePersonalizadoPO;
import org.mx.persistencia.comun.builder.BuilderUsuario;

public class BuilderReportePersonalizado {

	public static ReportePersonalizadoBO builderBO(ReportePersonalizadoPO po){
		ReportePersonalizadoBO bo = new ReportePersonalizadoBO();
		bo.setIdReportePersonalizado(po.getIdReportePersonalizado());
		bo.setNombre(po.getNombre());
		bo.setDescripcion(po.getDescripcion());
		bo.setFechaCreacion(po.getFechaCreacion());
		bo.setAutor(BuilderUsuario.builderBO(po.getAutor()));
		
//		for(PReportesPersonalizadosPO partida : po.getLstPReportesPersonalizados()){
//			bo.getLstPReportesPersonalizados().add(BuilderPReportePersonalizado.builderBO(partida));
//		}
		
		return bo;
	}
	
	public static ReportePersonalizadoBO builderBO(ReportePersonalizadoPO po, boolean oneToMany){
		ReportePersonalizadoBO bo = new ReportePersonalizadoBO();
		bo.setIdReportePersonalizado(po.getIdReportePersonalizado());
		bo.setNombre(po.getNombre());
		bo.setDescripcion(po.getDescripcion());
		bo.setFechaCreacion(po.getFechaCreacion());
		bo.setAutor(BuilderUsuario.builderBO(po.getAutor()));
		
		return bo;
	}
}
