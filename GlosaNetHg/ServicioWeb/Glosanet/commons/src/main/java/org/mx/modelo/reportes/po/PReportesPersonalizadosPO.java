package org.mx.modelo.reportes.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "GN_PReportes_Personalizados")
public class PReportesPersonalizadosPO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7573169673123920097L;
	private Integer idPReportesPersonalizados;
	private CampoTablaPO campoTabla;
	private ReportePersonalizadoPO reportePersonalizado;
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="PK_PReporte_Personalizado")
	public Integer getIdPReportesPersonalizados() {
		return idPReportesPersonalizados;
	}
	
	public void setIdPReportesPersonalizados(Integer pK_RPersonalizado_ColumnaTDS) {
		idPReportesPersonalizados = pK_RPersonalizado_ColumnaTDS;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FK01_Columna_TablaDataStage", nullable = false)
	public CampoTablaPO getCampoTabla() {
		return campoTabla;
	}
	
	public void setCampoTabla(CampoTablaPO campoTabla) {
		this.campoTabla = campoTabla;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FK02_Reporte_Personalizado", nullable = false)	
	public ReportePersonalizadoPO getReportePersonalizado() {
		return reportePersonalizado;
	}
	
	public void setReportePersonalizado(ReportePersonalizadoPO reportePersonalizado) {
		this.reportePersonalizado = reportePersonalizado;
	}
	
}
