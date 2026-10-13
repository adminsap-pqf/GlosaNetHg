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
 * Contenedores504 entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Contenedores_504")
public class ContenedoresPO implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -6568466603586002426L;
	private Integer pkContenedores;
	private DataStagePO dataStage;
	private String patente;
	private String pedimento;
	private String seccionAduanera;
	private String numContenedor;
	private String tipoContenedor;
	private Timestamp fechaPagoReal;

	// Constructors

	/** default constructor */
	public ContenedoresPO() {
	}

	/** minimal constructor */
	public ContenedoresPO(Integer pkContenedores, DataStagePO dataStage,
			String patente, String pedimento, String seccionAduanera) {
		this.pkContenedores = pkContenedores;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
	}

	/** full constructor */
	public ContenedoresPO(Integer pkContenedores, DataStagePO dataStage,
			String patente, String pedimento, String seccionAduanera,
			String numContenedor, String tipoContenedor, Timestamp fechaPagoReal) {
		this.pkContenedores = pkContenedores;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
		this.numContenedor = numContenedor;
		this.tipoContenedor = tipoContenedor;
		this.fechaPagoReal = fechaPagoReal;
	}

	// Property accessors
	@Id 
	@GeneratedValue
	@Column(name = "PK_Contenedores", unique = true, nullable = false)
	public Integer getPkContenedores() {
		return this.pkContenedores;
	}

	public void setPkContenedores(Integer pkContenedores) {
		this.pkContenedores = pkContenedores;
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

	@Column(name = "NumContenedor", length = 20)
	public String getNumContenedor() {
		return this.numContenedor;
	}

	public void setNumContenedor(String numContenedor) {
		this.numContenedor = numContenedor;
	}

	@Column(name = "TipoContenedor", length = 1)
	public String getTipoContenedor() {
		return this.tipoContenedor;
	}

	public void setTipoContenedor(String tipoContenedor) {
		this.tipoContenedor = tipoContenedor;
	}

	@Column(name = "FechaPagoReal", precision = 15, scale = 0)
	public Timestamp getFechaPagoReal() {
		return this.fechaPagoReal;
	}

	public void setFechaPagoReal(Timestamp fechaPagoReal) {
		this.fechaPagoReal = fechaPagoReal;
	}

}