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
 * TasasContribucionesPartida556 entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Tasas_Contribuciones_Partida_556")
public class TasasContribucionesPartidaPO implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1351870554848183019L;
	private Integer pkTasasContribucionesPartida;
	private DataStagePO dataStage;
	private String patente;
	private String pedimento;
	private String seccionAduanera;
	private String fraccion;
	private Integer secuenciaFraccion;
	private Integer claveContribucion;
	private Double tasaContribucion;
	private Double tipoTasa;
	private Timestamp fechaPagoReal;

	// Constructors

	/** default constructor */
	public TasasContribucionesPartidaPO() {
	}

	/** minimal constructor */
	public TasasContribucionesPartidaPO(Integer pkTasasContribucionesPartida,
			DataStagePO dataStage, String patente, String pedimento,
			String seccionAduanera) {
		this.pkTasasContribucionesPartida = pkTasasContribucionesPartida;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
	}

	/** full constructor */
	public TasasContribucionesPartidaPO(Integer pkTasasContribucionesPartida,
			DataStagePO dataStage, String patente, String pedimento,
			String seccionAduanera, String fraccion, Integer secuenciaFraccion,
			Integer claveContribucion, Double tasaContribucion,
			Double tipoTasa, Timestamp fechaPagoReal) {
		this.pkTasasContribucionesPartida = pkTasasContribucionesPartida;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
		this.fraccion = fraccion;
		this.secuenciaFraccion = secuenciaFraccion;
		this.claveContribucion = claveContribucion;
		this.tasaContribucion = tasaContribucion;
		this.tipoTasa = tipoTasa;
		this.fechaPagoReal = fechaPagoReal;
	}

	// Property accessors
	@Id 
	@GeneratedValue
	@Column(name = "PK_Tasas_Contribuciones_Partida", unique = true, nullable = false)
	public Integer getPkTasasContribucionesPartida() {
		return this.pkTasasContribucionesPartida;
	}

	public void setPkTasasContribucionesPartida(
			Integer pkTasasContribucionesPartida) {
		this.pkTasasContribucionesPartida = pkTasasContribucionesPartida;
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

	@Column(name = "ClaveContribucion")
	public Integer getClaveContribucion() {
		return this.claveContribucion;
	}

	public void setClaveContribucion(Integer claveContribucion) {
		this.claveContribucion = claveContribucion;
	}

	@Column(name = "TasaContribucion")
	public Double getTasaContribucion() {
		return this.tasaContribucion;
	}

	public void setTasaContribucion(Double tasaContribucion) {
		this.tasaContribucion = tasaContribucion;
	}

	@Column(name = "TipoTasa", precision = 12)
	public Double getTipoTasa() {
		return this.tipoTasa;
	}

	public void setTipoTasa(Double tipoTasa) {
		this.tipoTasa = tipoTasa;
	}

	@Column(name = "FechaPagoReal", length = 23)
	public Timestamp getFechaPagoReal() {
		return this.fechaPagoReal;
	}

	public void setFechaPagoReal(Timestamp fechaPagoReal) {
		this.fechaPagoReal = fechaPagoReal;
	}

}