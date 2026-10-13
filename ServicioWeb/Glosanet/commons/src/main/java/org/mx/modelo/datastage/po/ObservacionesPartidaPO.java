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
 * ObservacionesPartida558 entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Observaciones_Partida_558")
public class ObservacionesPartidaPO implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -3479413536622529331L;
	private Integer pkObservacionesPartida;
	private DataStagePO dataStage;
	private String patente;
	private String pedimento;
	private String seccionAduanera;
	private String fraccion;
	private Integer secuenciaFraccion;
	private Integer secuenciaObservacion;
	private String observaciones;
	private Timestamp fechaPagoReal;

	// Constructors

	/** default constructor */
	public ObservacionesPartidaPO() {
	}

	/** minimal constructor */
	public ObservacionesPartidaPO(Integer pkObservacionesPartida,
			DataStagePO dataStage, String patente, String pedimento,
			String seccionAduanera) {
		this.pkObservacionesPartida = pkObservacionesPartida;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
	}

	/** full constructor */
	public ObservacionesPartidaPO(Integer pkObservacionesPartida,
			DataStagePO dataStage, String patente, String pedimento,
			String seccionAduanera, String fraccion, Integer secuenciaFraccion,
			Integer secuenciaObservacion, String observaciones,
			Timestamp fechaPagoReal) {
		this.pkObservacionesPartida = pkObservacionesPartida;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
		this.fraccion = fraccion;
		this.secuenciaFraccion = secuenciaFraccion;
		this.secuenciaObservacion = secuenciaObservacion;
		this.observaciones = observaciones;
		this.fechaPagoReal = fechaPagoReal;
	}

	// Property accessors
	@Id 
	@GeneratedValue
	@Column(name = "PK_Observaciones_Partida", unique = true, nullable = false)
	public Integer getPkObservacionesPartida() {
		return this.pkObservacionesPartida;
	}

	public void setPkObservacionesPartida(Integer pkObservacionesPartida) {
		this.pkObservacionesPartida = pkObservacionesPartida;
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

	@Column(name = "Fraccion", length = 8)
	public String getFraccion() {
		return this.fraccion;
	}

	public void setFraccion(String fraccion) {
		this.fraccion = fraccion;
	}

	@Column(name = "SecuenciaFraccion")
	public Integer getSecuenciaFraccion() {
		return this.secuenciaFraccion;
	}

	public void setSecuenciaFraccion(Integer secuenciaFraccion) {
		this.secuenciaFraccion = secuenciaFraccion;
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

	@Column(name = "FechaPagoReal", length = 23)
	public Timestamp getFechaPagoReal() {
		return this.fechaPagoReal;
	}

	public void setFechaPagoReal(Timestamp fechaPagoReal) {
		this.fechaPagoReal = fechaPagoReal;
	}

}