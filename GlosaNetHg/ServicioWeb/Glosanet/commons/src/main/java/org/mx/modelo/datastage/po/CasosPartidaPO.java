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
 * CasosPartida554 entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Casos_Partida_554")
public class CasosPartidaPO implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5052579498580088026L;
	private Integer pkCasosPartida;
	private DataStagePO dataStage;
	private String patente;
	private String pedimento;
	private String seccionAduanera;
	private String fraccion;
	private Integer secuenciaFraccion;
	private String claveCaso;
	private String identificadorCaso;
	private String complementoCaso;
	private Timestamp fechaPagoReal;

	// Constructors

	/** default constructor */
	public CasosPartidaPO() {
	}

	/** minimal constructor */
	public CasosPartidaPO(Integer pkCasosPartida, DataStagePO dataStage,
			String patente, String pedimento, String seccionAduanera) {
		this.pkCasosPartida = pkCasosPartida;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
	}

	/** full constructor */
	public CasosPartidaPO(Integer pkCasosPartida, DataStagePO dataStage,
			String patente, String pedimento, String seccionAduanera,
			String fraccion, Integer secuenciaFraccion, String claveCaso,
			String identificadorCaso, String complementoCaso,
			Timestamp fechaPagoReal) {
		this.pkCasosPartida = pkCasosPartida;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
		this.fraccion = fraccion;
		this.secuenciaFraccion = secuenciaFraccion;
		this.claveCaso = claveCaso;
		this.identificadorCaso = identificadorCaso;
		this.complementoCaso = complementoCaso;
		this.fechaPagoReal = fechaPagoReal;
	}

	// Property accessors
	@Id 
	@GeneratedValue
	@Column(name = "PK_Casos_Partida", unique = true, nullable = false)
	public Integer getPkCasosPartida() {
		return this.pkCasosPartida;
	}

	public void setPkCasosPartida(Integer pkCasosPartida) {
		this.pkCasosPartida = pkCasosPartida;
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

	@Column(name = "ClaveCaso", length = 2)
	public String getClaveCaso() {
		return this.claveCaso;
	}

	public void setClaveCaso(String claveCaso) {
		this.claveCaso = claveCaso;
	}

	@Column(name = "IdentificadorCaso", length = 20)
	public String getIdentificadorCaso() {
		return this.identificadorCaso;
	}

	public void setIdentificadorCaso(String identificadorCaso) {
		this.identificadorCaso = identificadorCaso;
	}

	@Column(name = "ComplementoCaso", length = 30)
	public String getComplementoCaso() {
		return this.complementoCaso;
	}

	public void setComplementoCaso(String complementoCaso) {
		this.complementoCaso = complementoCaso;
	}

	@Column(name = "FechaPagoReal", length = 23)
	public Timestamp getFechaPagoReal() {
		return this.fechaPagoReal;
	}

	public void setFechaPagoReal(Timestamp fechaPagoReal) {
		this.fechaPagoReal = fechaPagoReal;
	}

}