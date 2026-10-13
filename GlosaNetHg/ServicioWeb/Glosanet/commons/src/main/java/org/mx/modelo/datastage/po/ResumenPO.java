package org.mx.modelo.datastage.po;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.mx.modelo.comun.po.DataStagePO;

/**
 * Resumen entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Resumen")
public class ResumenPO implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 3730854310372392396L;
	private Integer pkResumen;
	private String folio;
	private String rfcoPatenteAduanal;
	private Timestamp fechaInicial;
	private Timestamp fechaFinal;
	private Timestamp fechaEjecucion;
	private String totalFracciones;
	private String totalContribuciones;
	private DataStagePO dataStage;

	// Constructors

	/** default constructor */
	public ResumenPO() {
	}

	// Property accessors

	@Id 
	@GeneratedValue
	@Column(name = "PK_Resumen", nullable = false)
	public Integer getPkResumen() {
		return this.pkResumen;
	}

	public void setPkResumen(Integer pkResumen) {
		this.pkResumen = pkResumen;
	}

	@Column(name = "Folio", length = 17)
	public String getFolio() {
		return this.folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	@Column(name = "RFCoPatenteAduanal", length = 13)
	public String getRfcoPatenteAduanal() {
		return this.rfcoPatenteAduanal;
	}

	public void setRfcoPatenteAduanal(String rfcoPatenteAduanal) {
		this.rfcoPatenteAduanal = rfcoPatenteAduanal;
	}

	@Column(name = "Fecha_Inicial", length = 23)
	public Timestamp getFechaInicial() {
		return this.fechaInicial;
	}

	public void setFechaInicial(Timestamp fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	@Column(name = "Fecha_Final", length = 23)
	public Timestamp getFechaFinal() {
		return this.fechaFinal;
	}

	public void setFechaFinal(Timestamp fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	@Column(name = "Fecha_Ejecucion", length = 23)
	public Timestamp getFechaEjecucion() {
		return this.fechaEjecucion;
	}

	public void setFechaEjecucion(Timestamp fechaEjecucion) {
		this.fechaEjecucion = fechaEjecucion;
	}

	@Column(name = "Total_Fracciones", length = 13)
	public String getTotalFracciones() {
		return this.totalFracciones;
	}

	public void setTotalFracciones(String totalFracciones) {
		this.totalFracciones = totalFracciones;
	}

	@Column(name = "Total_Contribuciones", length = 13)
	public String getTotalContribuciones() {
		return this.totalContribuciones;
	}

	public void setTotalContribuciones(String totalContribuciones) {
		this.totalContribuciones = totalContribuciones;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_DataStage", nullable = false)
	public DataStagePO getDataStage() {
		return this.dataStage;
	}

	public void setDataStage(DataStagePO dataStage) {
		this.dataStage = dataStage;
	}

}