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
 * Guias503 entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Guias_503")
public class GuiasPO implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 8410183479861526710L;
	private Integer pkGuias;
	private DataStagePO dataStage;
	private String patente;
	private String pedimento;
	private String seccionAduanera;
	private String numeroGuia;
	private String tipoGuia;
	private Timestamp fechaPagoReal;

	// Constructors

	/** default constructor */
	public GuiasPO() {
	}

	/** minimal constructor */
	public GuiasPO(Integer pkGuias, DataStagePO dataStage, String patente,
			String pedimento, String seccionAduanera) {
		this.pkGuias = pkGuias;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
	}

	/** full constructor */
	public GuiasPO(Integer pkGuias, DataStagePO dataStage, String patente,
			String pedimento, String seccionAduanera, String numeroGuia,
			String tipoGuia, Timestamp fechaPagoReal) {
		this.pkGuias = pkGuias;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
		this.numeroGuia = numeroGuia;
		this.tipoGuia = tipoGuia;
		this.fechaPagoReal = fechaPagoReal;
	}

	// Property accessors
	@Id 
	@GeneratedValue
	@Column(name = "PK_Guias", unique = true, nullable = false)
	public Integer getPkGuias() {
		return this.pkGuias;
	}

	public void setPkGuias(Integer pkGuias) {
		this.pkGuias = pkGuias;
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

	@Column(name = "NumeroGuia", length = 20)
	public String getNumeroGuia() {
		return this.numeroGuia;
	}

	public void setNumeroGuia(String numeroGuia) {
		this.numeroGuia = numeroGuia;
	}

	@Column(name = "TipoGuia", length = 1)
	public String getTipoGuia() {
		return this.tipoGuia;
	}

	public void setTipoGuia(String tipoGuia) {
		this.tipoGuia = tipoGuia;
	}

	@Column(name = "FechaPagoReal", length = 23)
	public Timestamp getFechaPagoReal() {
		return this.fechaPagoReal;
	}

	public void setFechaPagoReal(Timestamp fechaPagoReal) {
		this.fechaPagoReal = fechaPagoReal;
	}

}