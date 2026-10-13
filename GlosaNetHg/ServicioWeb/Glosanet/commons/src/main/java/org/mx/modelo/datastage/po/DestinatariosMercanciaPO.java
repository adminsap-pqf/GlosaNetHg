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
 * DestinatariosMercancia520 entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Destinatarios_Mercancia_520")
public class DestinatariosMercanciaPO implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -3948110856120394337L;
	private Integer pkDestinatariosMercancia;
	private DataStagePO dataStage;
	private String patente;
	private String pedimento;
	private String seccionAduanera;
	private String indentFiscalDestinatario;
	private String nombreDestinatarioMercancia;
	private String calleDestinatario;
	private String numInteriorDestinatario;
	private String numExteriorDestinatario;
	private String cpDestinatario;
	private String municpioDestinatario;
	private String paisDestinatario;
	private Timestamp fechaPagoReal;

	// Constructors

	/** default constructor */
	public DestinatariosMercanciaPO() {
	}

	/** minimal constructor */
	public DestinatariosMercanciaPO(Integer pkDestinatariosMercancia,
			DataStagePO dataStage, String patente, String pedimento,
			String seccionAduanera) {
		this.pkDestinatariosMercancia = pkDestinatariosMercancia;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
	}

	/** full constructor */
	public DestinatariosMercanciaPO(Integer pkDestinatariosMercancia,
			DataStagePO dataStage, String patente, String pedimento,
			String seccionAduanera, String indentFiscalDestinatario,
			String nombreDestinatarioMercancia, String calleDestinatario,
			String numInteriorDestinatario, String numExteriorDestinatario,
			String cpDestinatario, String municpioDestinatario,
			String paisDestinatario, Timestamp fechaPagoReal) {
		this.pkDestinatariosMercancia = pkDestinatariosMercancia;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
		this.indentFiscalDestinatario = indentFiscalDestinatario;
		this.nombreDestinatarioMercancia = nombreDestinatarioMercancia;
		this.calleDestinatario = calleDestinatario;
		this.numInteriorDestinatario = numInteriorDestinatario;
		this.numExteriorDestinatario = numExteriorDestinatario;
		this.cpDestinatario = cpDestinatario;
		this.municpioDestinatario = municpioDestinatario;
		this.paisDestinatario = paisDestinatario;
		this.fechaPagoReal = fechaPagoReal;
	}

	// Property accessors
	@Id 
	@GeneratedValue
	@Column(name = "PK_Destinatarios_Mercancia", unique = true, nullable = false)
	public Integer getPkDestinatariosMercancia() {
		return this.pkDestinatariosMercancia;
	}

	public void setPkDestinatariosMercancia(Integer pkDestinatariosMercancia) {
		this.pkDestinatariosMercancia = pkDestinatariosMercancia;
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

	@Column(name = "IndentFiscalDestinatario", length = 17)
	public String getIndentFiscalDestinatario() {
		return this.indentFiscalDestinatario;
	}

	public void setIndentFiscalDestinatario(String indentFiscalDestinatario) {
		this.indentFiscalDestinatario = indentFiscalDestinatario;
	}

	@Column(name = "NombreDestinatarioMercancia")
	public String getNombreDestinatarioMercancia() {
		return this.nombreDestinatarioMercancia;
	}

	public void setNombreDestinatarioMercancia(
			String nombreDestinatarioMercancia) {
		this.nombreDestinatarioMercancia = nombreDestinatarioMercancia;
	}

	@Column(name = "CalleDestinatario", length = 80)
	public String getCalleDestinatario() {
		return this.calleDestinatario;
	}

	public void setCalleDestinatario(String calleDestinatario) {
		this.calleDestinatario = calleDestinatario;
	}

	@Column(name = "NumInteriorDestinatario", length = 10)
	public String getNumInteriorDestinatario() {
		return this.numInteriorDestinatario;
	}

	public void setNumInteriorDestinatario(String numInteriorDestinatario) {
		this.numInteriorDestinatario = numInteriorDestinatario;
	}

	@Column(name = "NumExteriorDestinatario", length = 10)
	public String getNumExteriorDestinatario() {
		return this.numExteriorDestinatario;
	}

	public void setNumExteriorDestinatario(String numExteriorDestinatario) {
		this.numExteriorDestinatario = numExteriorDestinatario;
	}

	@Column(name = "CpDestinatario", length = 10)
	public String getCpDestinatario() {
		return this.cpDestinatario;
	}

	public void setCpDestinatario(String cpDestinatario) {
		this.cpDestinatario = cpDestinatario;
	}

	@Column(name = "MunicpioDestinatario", length = 80)
	public String getMunicpioDestinatario() {
		return this.municpioDestinatario;
	}

	public void setMunicpioDestinatario(String municpioDestinatario) {
		this.municpioDestinatario = municpioDestinatario;
	}

	@Column(name = "PaisDestinatario", length = 3)
	public String getPaisDestinatario() {
		return this.paisDestinatario;
	}

	public void setPaisDestinatario(String paisDestinatario) {
		this.paisDestinatario = paisDestinatario;
	}

	@Column(name = "FechaPagoReal", length = 23)
	public Timestamp getFechaPagoReal() {
		return this.fechaPagoReal;
	}

	public void setFechaPagoReal(Timestamp fechaPagoReal) {
		this.fechaPagoReal = fechaPagoReal;
	}

}