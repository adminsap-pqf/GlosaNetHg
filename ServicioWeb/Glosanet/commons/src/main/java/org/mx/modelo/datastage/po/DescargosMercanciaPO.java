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
 * DescargosMercancia512 entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Descargos_Mercancia_512")
public class DescargosMercanciaPO implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1013151302304049149L;
	private Integer pkDescargosMercancia;
	private DataStagePO dataStage;
	private String patente;
	private String pedimento;
	private String seccionAduanera;
	private Integer patenteAduanalOrig;
	private Integer pedimentoOriginal;
	private String seccionAduaneraDespOrig;
	private String documentoOriginal;
	private Timestamp fechaOperacionOrig;
	private String fraccionOriginal;
	private Integer unidadMedida;
	private Double mercanciaDescargada;
	private Integer tipoPedimento;
	private Timestamp fechaPagoReal;

	// Constructors

	/** default constructor */
	public DescargosMercanciaPO() {
	}

	/** minimal constructor */
	public DescargosMercanciaPO(Integer pkDescargosMercancia,
			DataStagePO dataStage, String patente, String pedimento,
			String seccionAduanera) {
		this.pkDescargosMercancia = pkDescargosMercancia;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
	}

	/** full constructor */
	public DescargosMercanciaPO(Integer pkDescargosMercancia,
			DataStagePO dataStage, String patente, String pedimento,
			String seccionAduanera, Integer patenteAduanalOrig,
			Integer pedimentoOriginal, String seccionAduaneraDespOrig,
			String documentoOriginal, Timestamp fechaOperacionOrig,
			String fraccionOriginal, Integer unidadMedida,
			Double mercanciaDescargada, Integer tipoPedimento,
			Timestamp fechaPagoReal) {
		this.pkDescargosMercancia = pkDescargosMercancia;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
		this.patenteAduanalOrig = patenteAduanalOrig;
		this.pedimentoOriginal = pedimentoOriginal;
		this.seccionAduaneraDespOrig = seccionAduaneraDespOrig;
		this.documentoOriginal = documentoOriginal;
		this.fechaOperacionOrig = fechaOperacionOrig;
		this.fraccionOriginal = fraccionOriginal;
		this.unidadMedida = unidadMedida;
		this.mercanciaDescargada = mercanciaDescargada;
		this.tipoPedimento = tipoPedimento;
		this.fechaPagoReal = fechaPagoReal;
	}

	// Property accessors
	@Id 
	@GeneratedValue
	@Column(name = "PK_Descargos_Mercancia", unique = true, nullable = false)
	public Integer getPkDescargosMercancia() {
		return this.pkDescargosMercancia;
	}

	public void setPkDescargosMercancia(Integer pkDescargosMercancia) {
		this.pkDescargosMercancia = pkDescargosMercancia;
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

	@Column(name = "PatenteAduanalOrig")
	public Integer getPatenteAduanalOrig() {
		return this.patenteAduanalOrig;
	}

	public void setPatenteAduanalOrig(Integer patenteAduanalOrig) {
		this.patenteAduanalOrig = patenteAduanalOrig;
	}

	@Column(name = "PedimentoOriginal")
	public Integer getPedimentoOriginal() {
		return this.pedimentoOriginal;
	}

	public void setPedimentoOriginal(Integer pedimentoOriginal) {
		this.pedimentoOriginal = pedimentoOriginal;
	}

	@Column(name = "SeccionAduaneraDespOrig", length = 3)
	public String getSeccionAduaneraDespOrig() {
		return this.seccionAduaneraDespOrig;
	}

	public void setSeccionAduaneraDespOrig(String seccionAduaneraDespOrig) {
		this.seccionAduaneraDespOrig = seccionAduaneraDespOrig;
	}

	@Column(name = "DocumentoOriginal", length = 3)
	public String getDocumentoOriginal() {
		return this.documentoOriginal;
	}

	public void setDocumentoOriginal(String documentoOriginal) {
		this.documentoOriginal = documentoOriginal;
	}

	@Column(name = "FechaOperacionOrig", length = 23)
	public Timestamp getFechaOperacionOrig() {
		return this.fechaOperacionOrig;
	}

	public void setFechaOperacionOrig(Timestamp fechaOperacionOrig) {
		this.fechaOperacionOrig = fechaOperacionOrig;
	}

	@Column(name = "FraccionOriginal", length = 8)
	public String getFraccionOriginal() {
		return this.fraccionOriginal;
	}

	public void setFraccionOriginal(String fraccionOriginal) {
		this.fraccionOriginal = fraccionOriginal;
	}

	@Column(name = "UnidadMedida")
	public Integer getUnidadMedida() {
		return this.unidadMedida;
	}

	public void setUnidadMedida(Integer unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	@Column(name = "MercanciaDescargada", precision = 15, scale = 3)
	public Double getMercanciaDescargada() {
		return this.mercanciaDescargada;
	}

	public void setMercanciaDescargada(Double mercanciaDescargada) {
		this.mercanciaDescargada = mercanciaDescargada;
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