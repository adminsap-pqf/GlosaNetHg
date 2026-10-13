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
 * DiferenciasContribucionesPedimento702 entity. @author MyEclipse Persistence
 * Tools
 */
@Entity
@Table(name = "Diferencias_Contribuciones_Pedimento_702")
public class DiferenciasContribucionesPedimentoPO implements
		java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 8154648649696006654L;
	private Integer pkDiferenciasContribucionesPedimento;
	private DataStagePO dataStage;
	private String patente;
	private String pedimento;
	private String seccionAduanera;
	private Double claveContribucion;
	private Integer formaPago;
	private Integer importePago;
	private Integer tipoPedimento;
	private Timestamp fechaPagoReal;

	// Constructors

	/** default constructor */
	public DiferenciasContribucionesPedimentoPO() {
	}

	/** minimal constructor */
	public DiferenciasContribucionesPedimentoPO(
			Integer pkDiferenciasContribucionesPedimento, DataStagePO dataStage,
			String patente, String pedimento, String seccionAduanera) {
		this.pkDiferenciasContribucionesPedimento = pkDiferenciasContribucionesPedimento;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
	}

	/** full constructor */
	public DiferenciasContribucionesPedimentoPO(
			Integer pkDiferenciasContribucionesPedimento, DataStagePO dataStage,
			String patente, String pedimento, String seccionAduanera,
			Double claveContribucion, Integer formaPago, Integer importePago,
			Integer tipoPedimento, Timestamp fechaPagoReal) {
		this.pkDiferenciasContribucionesPedimento = pkDiferenciasContribucionesPedimento;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
		this.claveContribucion = claveContribucion;
		this.formaPago = formaPago;
		this.importePago = importePago;
		this.tipoPedimento = tipoPedimento;
		this.fechaPagoReal = fechaPagoReal;
	}

	// Property accessors
	@Id 
	@GeneratedValue
	@Column(name = "PK_Diferencias_Contribuciones_Pedimento", unique = true, nullable = false)
	public Integer getPkDiferenciasContribucionesPedimento() {
		return this.pkDiferenciasContribucionesPedimento;
	}

	public void setPkDiferenciasContribucionesPedimento(
			Integer pkDiferenciasContribucionesPedimento) {
		this.pkDiferenciasContribucionesPedimento = pkDiferenciasContribucionesPedimento;
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

	@Column(name = "ClaveContribucion", precision = 15, scale = 0)
	public Double getClaveContribucion() {
		return this.claveContribucion;
	}

	public void setClaveContribucion(Double claveContribucion) {
		this.claveContribucion = claveContribucion;
	}

	@Column(name = "FormaPago")
	public Integer getFormaPago() {
		return this.formaPago;
	}

	public void setFormaPago(Integer formaPago) {
		this.formaPago = formaPago;
	}

	@Column(name = "ImportePago")
	public Integer getImportePago() {
		return this.importePago;
	}

	public void setImportePago(Integer importePago) {
		this.importePago = importePago;
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