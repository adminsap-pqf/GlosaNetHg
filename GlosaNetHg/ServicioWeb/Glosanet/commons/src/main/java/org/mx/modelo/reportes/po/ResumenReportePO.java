package org.mx.modelo.reportes.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Datos_Generales_501")
public class ResumenReportePO implements Serializable {
	
	private static final long serialVersionUID = 1403419090182951177L;
	
	
	private Integer pkResumenReportesPO;
	private String pais;
	private Double montoTotal;
	private Double montoContibuciones;
	
	/**
	 * @return the pkResumenReportesPO
	 */
	public ResumenReportePO(){
		super();
	}
	
	
	
	
	/**
	 * @param pkResumenReportesPO
	 * @param pais
	 * @param montoTotal
	 * @param montoContibuciones
	 * @param porcentajeContribuciones
	 */
	public ResumenReportePO(Integer pkResumenReportesPO,
			String pais, Double montoTotal,
			Double montoContibuciones) {
		super();
		this.pkResumenReportesPO = pkResumenReportesPO;
		this.pais = pais;
		this.montoTotal = montoTotal;
		this.montoContibuciones = montoContibuciones;
		
	}

	@Id 
	@GeneratedValue()
	@Column(name = "PK_ResumenReportesPO")
	public Integer getPkResumenReportesPO() {
		return pkResumenReportesPO;
	}


	/**
	 * @param pkResumenReportesPO the pkResumenReportesPO to set
	 */
	public void setPkResumenReportesPO(Integer pkResumenReportesPO) {
		this.pkResumenReportesPO = pkResumenReportesPO;
	}
	
	/**
	 * @return the region
	 */
	@Column(name = "Pais")
	public String getPais() {
		return pais;
	}
	/**
	 * @param region the region to set
	 */
	public void setPais(String pais) {
		this.pais = pais;
	}
	/**
	 * @return the montoTotal
	 */
	@Column(name = "Monto", precision = 14)
	public Double getMontoTotal() {
		return montoTotal;
	}
	/**
	 * @param montoTotal the montoTotal to set
	 */
	public void setMontoTotal(Double montoTotal) {
		this.montoTotal = montoTotal;
	}


	/**
	 * @return the montoContibuciones
	 */
	@Column(name = "MontoContibuciones", precision = 14)
	public Double getMontoContibuciones() {
		return montoContibuciones;
	}


	/**
	 * @param montoContibuciones the montoContibuciones to set
	 */
	public void setMontoContibuciones(Double montoContibuciones) {
		this.montoContibuciones = montoContibuciones;
	}



	
}
