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
 * ObservacionesPedimento511 entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Observaciones_Pedimento_511")
public class ObservacionesPedimentoPO implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -7416754099213812143L;
	private Integer pkObservacionesPedimento;
	private DataStagePO dataStage;
	private String patente;
	private String pedimento;
	private String seccionAduanera;
	private Integer secuenciaObservacion;
	private String observaciones;
	private Integer tipoPedimento;
	private Timestamp fechaValidacionPagoR;

	// Constructors

	/** default constructor */
	public ObservacionesPedimentoPO() {
	}

	/** minimal constructor */
	public ObservacionesPedimentoPO(Integer pkObservacionesPedimento,
			DataStagePO dataStage, String patente, String pedimento,
			String seccionAduanera) {
		this.pkObservacionesPedimento = pkObservacionesPedimento;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
	}

	/** full constructor */
	public ObservacionesPedimentoPO(Integer pkObservacionesPedimento,
			DataStagePO dataStage, String patente, String pedimento,
			String seccionAduanera, Integer secuenciaObservacion,
			String observaciones, Integer tipoPedimento,
			Timestamp fechaValidacionPagoR) {
		this.pkObservacionesPedimento = pkObservacionesPedimento;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
		this.secuenciaObservacion = secuenciaObservacion;
		this.observaciones = observaciones;
		this.tipoPedimento = tipoPedimento;
		this.fechaValidacionPagoR = fechaValidacionPagoR;
	}

	// Property accessors
	@Id 
	@GeneratedValue
	@Column(name = "PK_Observaciones_Pedimento", unique = true, nullable = false)
	public Integer getPkObservacionesPedimento() {
		return this.pkObservacionesPedimento;
	}

	public void setPkObservacionesPedimento(Integer pkObservacionesPedimento) {
		this.pkObservacionesPedimento = pkObservacionesPedimento;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_DataStage", nullable = false)
	public DataStagePO getDataStage() {
		return this.dataStage;
	}

	public void setDataStage(DataStagePO dataStage) {
		this.dataStage = dataStage;
	}

	@Column(name = "Patente", nullable = false, length = 4)
	public String getPatente() {
		return this.patente;
	}

	public void setPatente(String patente) {
		this.patente = patente;
	}

	@Column(name = "Pedimento", nullable = false, length = 7)
	public String getPedimento() {
		return this.pedimento;
	}

	public void setPedimento(String pedimento) {
		this.pedimento = pedimento;
	}

	@Column(name = "SeccionAduanera", nullable = false, length = 3)
	public String getSeccionAduanera() {
		return this.seccionAduanera;
	}

	public void setSeccionAduanera(String seccionAduanera) {
		this.seccionAduanera = seccionAduanera;
	}

	@Column(name = "SecuenciaObservacion")
	public Integer getSecuenciaObservacion() {
		return this.secuenciaObservacion;
	}

	public void setSecuenciaObservacion(Integer secuenciaObservacion) {
		this.secuenciaObservacion = secuenciaObservacion;
	}

	@Column(name = "Observaciones")
	public String getObservaciones() {
		return this.observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	@Column(name = "TipoPedimento")
	public Integer getTipoPedimento() {
		return this.tipoPedimento;
	}

	public void setTipoPedimento(Integer tipoPedimento) {
		this.tipoPedimento = tipoPedimento;
	}

	@Column(name = "FechaValidacionPagoR", length = 23)
	public Timestamp getFechaValidacionPagoR() {
		return this.fechaValidacionPagoR;
	}

	public void setFechaValidacionPagoR(Timestamp fechaValidacionPagoR) {
		this.fechaValidacionPagoR = fechaValidacionPagoR;
	}

}