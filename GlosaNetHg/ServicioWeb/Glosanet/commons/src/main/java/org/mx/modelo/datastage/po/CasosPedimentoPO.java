package org.mx.modelo.datastage.po;

import java.io.Serializable;
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
 * CasosPedimento507 entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Casos_Pedimento_507")
public class CasosPedimentoPO implements Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -62689898383760846L;
	private Integer pkCasosPedimento;
	private DataStagePO dataStage;
	private String patente;
	private String pedimento;
	private String seccionAduanera;
	private String claveCaso;
	private String identificadorCaso;
	private Integer tipoPedimento;
	private String complementoCaso;
	private Timestamp fechaValidacionPagoR;

	// Constructors

	/** default constructor */
	public CasosPedimentoPO() {
	}

	/** minimal constructor */
	public CasosPedimentoPO(Integer pkCasosPedimento, DataStagePO dataStage,
			String patente, String pedimento, String seccionAduanera) {
		this.pkCasosPedimento = pkCasosPedimento;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
	}

	/** full constructor */
	public CasosPedimentoPO(Integer pkCasosPedimento, DataStagePO dataStage,
			String patente, String pedimento, String seccionAduanera,
			String claveCaso, String identificadorCaso, Integer tipoPedimento,
			String complementoCaso, Timestamp fechaValidacionPagoR) {
		this.pkCasosPedimento = pkCasosPedimento;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
		this.claveCaso = claveCaso;
		this.identificadorCaso = identificadorCaso;
		this.tipoPedimento = tipoPedimento;
		this.complementoCaso = complementoCaso;
		this.fechaValidacionPagoR = fechaValidacionPagoR;
	}

	// Property accessors
	@Id 
	@GeneratedValue
	@Column(name = "PK_Casos_Pedimento", unique = true, nullable = false)
	public Integer getPkCasosPedimento() {
		return this.pkCasosPedimento;
	}

	public void setPkCasosPedimento(Integer pkCasosPedimento) {
		this.pkCasosPedimento = pkCasosPedimento;
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

	@Column(name = "TipoPedimento")
	public Integer getTipoPedimento() {
		return this.tipoPedimento;
	}

	public void setTipoPedimento(Integer tipoPedimento) {
		this.tipoPedimento = tipoPedimento;
	}

	@Column(name = "ComplementoCaso", length = 30)
	public String getComplementoCaso() {
		return this.complementoCaso;
	}

	public void setComplementoCaso(String complementoCaso) {
		this.complementoCaso = complementoCaso;
	}

	@Column(name = "FechaValidacionPagoR", length = 23)
	public Timestamp getFechaValidacionPagoR() {
		return this.fechaValidacionPagoR;
	}

	public void setFechaValidacionPagoR(Timestamp fechaValidacionPagoR) {
		this.fechaValidacionPagoR = fechaValidacionPagoR;
	}

}