package org.mx.modelo.reportes.bo;


public class PReportesPersonalizadosBO {
	
	private Integer idRPersonalizado_ColumnaTDS;
	private CampoTablaBO campoTabla;
	private ReportePersonalizadoBO reportePersonalizado;
	
	public Integer getIdRPersonalizado_ColumnaTDS() {
		return idRPersonalizado_ColumnaTDS;
	}
	
	public void setIdRPersonalizado_ColumnaTDS(Integer idRPersonalizado_ColumnaTDS) {
		this.idRPersonalizado_ColumnaTDS = idRPersonalizado_ColumnaTDS;
	}
	
	public CampoTablaBO getCampoTabla() {
		return campoTabla;
	}
	
	public void setCampoTabla(CampoTablaBO campoTabla) {
		this.campoTabla = campoTabla;
	}
	
	public ReportePersonalizadoBO getReportePersonalizado() {
		return reportePersonalizado;
	}
	
	public void setReportePersonalizado(ReportePersonalizadoBO reportePersonalizado) {
		this.reportePersonalizado = reportePersonalizado;
	}
	
}
