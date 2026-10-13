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
 * FechasPedimento506 entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Fechas_Pedimento_506")
public class FechasPedimentoPO implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -4430461801926722902L;
	private Integer pkFechasPedimento;
	private DataStagePO dataStage;
	private String patente;
	private String pedimento;
	private String seccionAduanera;
	private Integer tipoFecha;
	private Timestamp fechaOperacion;
	private Timestamp fechaValidacionPagoR;

	// Constructors

	/** default constructor */
	public FechasPedimentoPO() {
	}

	/** minimal constructor */
	public FechasPedimentoPO(Integer pkFechasPedimento, DataStagePO dataStage,
			String patente, String pedimento, String seccionAduanera) {
		this.pkFechasPedimento = pkFechasPedimento;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
	}

	/** full constructor */
	public FechasPedimentoPO(Integer pkFechasPedimento, DataStagePO dataStage,
			String patente, String pedimento, String seccionAduanera,
			Integer tipoFecha, Timestamp fechaOperacion,
			Timestamp fechaValidacionPagoR) {
		this.pkFechasPedimento = pkFechasPedimento;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
		this.tipoFecha = tipoFecha;
		this.fechaOperacion = fechaOperacion;
		this.fechaValidacionPagoR = fechaValidacionPagoR;
	}

	// Property accessors
	@Id 
	@GeneratedValue
	@Column(name = "PK_Fechas_Pedimento", unique = true, nullable = false)
	public Integer getPkFechasPedimento() {
		return this.pkFechasPedimento;
	}

	public void setPkFechasPedimento(Integer pkFechasPedimento) {
		this.pkFechasPedimento = pkFechasPedimento;
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

	@Column(name = "TipoFecha")
	public Integer getTipoFecha() {
		return this.tipoFecha;
	}

	public void setTipoFecha(Integer tipoFecha) {
		this.tipoFecha = tipoFecha;
	}

	@Column(name = "FechaOperacion", length = 23)
	public Timestamp getFechaOperacion() {
		return this.fechaOperacion;
	}

	public void setFechaOperacion(Timestamp fechaOperacion) {
		this.fechaOperacion = fechaOperacion;
	}

	@Column(name = "FechaValidacionPagoR", length = 23)
	public Timestamp getFechaValidacionPagoR() {
		return this.fechaValidacionPagoR;
	}

	public void setFechaValidacionPagoR(Timestamp fechaValidacionPagoR) {
		this.fechaValidacionPagoR = fechaValidacionPagoR;
	}

}