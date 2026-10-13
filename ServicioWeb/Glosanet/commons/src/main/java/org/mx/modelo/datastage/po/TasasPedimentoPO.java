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
 * TasasPedimento509 entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Tasas_Pedimento_509")
public class TasasPedimentoPO implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -6636841080202269385L;
	private Integer pkTasasPedimento;
	private DataStagePO dataStage;
	private String patente;
	private String pedimento;
	private String seccionAduanera;
	private Integer claveContribucion;
	private Double tasaContribucion;
	private Integer tipoTasa;
	private Integer tipoPedimento;
	private Timestamp fechaPagoReal;

	// Constructors

	/** default constructor */
	public TasasPedimentoPO() {
	}

	/** minimal constructor */
	public TasasPedimentoPO(Integer pkTasasPedimento, DataStagePO dataStage,
			String patente, String pedimento, String seccionAduanera) {
		this.pkTasasPedimento = pkTasasPedimento;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
	}

	/** full constructor */
	public TasasPedimentoPO(Integer pkTasasPedimento, DataStagePO dataStage,
			String patente, String pedimento, String seccionAduanera,
			Integer claveContribucion, Double tasaContribucion,
			Integer tipoTasa, Integer tipoPedimento, Timestamp fechaPagoReal) {
		this.pkTasasPedimento = pkTasasPedimento;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
		this.claveContribucion = claveContribucion;
		this.tasaContribucion = tasaContribucion;
		this.tipoTasa = tipoTasa;
		this.tipoPedimento = tipoPedimento;
		this.fechaPagoReal = fechaPagoReal;
	}

	// Property accessors
	@Id 
	@GeneratedValue
	@Column(name = "PK_Tasas_Pedimento", unique = true, nullable = false)
	public Integer getPkTasasPedimento() {
		return this.pkTasasPedimento;
	}

	public void setPkTasasPedimento(Integer pkTasasPedimento) {
		this.pkTasasPedimento = pkTasasPedimento;
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

	@Column(name = "ClaveContribucion")
	public Integer getClaveContribucion() {
		return this.claveContribucion;
	}

	public void setClaveContribucion(Integer claveContribucion) {
		this.claveContribucion = claveContribucion;
	}

	@Column(name = "TasaContribucion", precision = 10, scale = 5)
	public Double getTasaContribucion() {
		return this.tasaContribucion;
	}

	public void setTasaContribucion(Double tasaContribucion) {
		this.tasaContribucion = tasaContribucion;
	}

	@Column(name = "TipoTasa")
	public Integer getTipoTasa() {
		return this.tipoTasa;
	}

	public void setTipoTasa(Integer tipoTasa) {
		this.tipoTasa = tipoTasa;
	}

	@Column(name = "TipoPedimento")
	public Integer getTipoPedimento() {
		return this.tipoPedimento;
	}

	public void setTipoPedimento(Integer tipoPedimento) {
		this.tipoPedimento = tipoPedimento;
	}

	@Column(name = "FechaPagoReal", length = 23)
	public Timestamp getFechaPagoReal() {
		return this.fechaPagoReal;
	}

	public void setFechaPagoReal(Timestamp fechaPagoReal) {
		this.fechaPagoReal = fechaPagoReal;
	}

}