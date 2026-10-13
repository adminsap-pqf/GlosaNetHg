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
 * Rectificaciones701 entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Rectificaciones_701")
public class RectificacionesPO implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 6525901475619977312L;
	private Integer pkRectificaciones;
	private DataStagePO dataStage;
	private String patente;
	private String pedimento;
	private String seccionAduanera;
	private String claveDocumento;
	private Timestamp fechaPago;
	private String pedimentoAnterior;
	private String patenteAnterior;
	private String seccionAduaneraAnterior;
	private String documentoAnterior;
	private Timestamp fechaOperacionAnterior;
	private String pedimentoOriginal;
	private String patenteAduanalOrig;
	private String seccionAduaneraDespOrig;
	private Timestamp fechaPagoReal;

	// Constructors

	/** default constructor */
	public RectificacionesPO() {
	}

	/** minimal constructor */
	public RectificacionesPO(Integer pkRectificaciones, DataStagePO dataStage,
			String patente, String pedimento, String seccionAduanera) {
		this.pkRectificaciones = pkRectificaciones;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
	}

	/** full constructor */
	public RectificacionesPO(Integer pkRectificaciones, DataStagePO dataStage,
			String patente, String pedimento, String seccionAduanera,
			String claveDocumento, Timestamp fechaPago,
			String pedimentoAnterior, String patenteAnterior,
			String seccionAduaneraAnterior, String documentoAnterior,
			Timestamp fechaOperacionAnterior, String pedimentoOriginal,
			String patenteAduanalOrig, String seccionAduaneraDespOrig,
			Timestamp fechaPagoReal) {
		this.pkRectificaciones = pkRectificaciones;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
		this.claveDocumento = claveDocumento;
		this.fechaPago = fechaPago;
		this.pedimentoAnterior = pedimentoAnterior;
		this.patenteAnterior = patenteAnterior;
		this.seccionAduaneraAnterior = seccionAduaneraAnterior;
		this.documentoAnterior = documentoAnterior;
		this.fechaOperacionAnterior = fechaOperacionAnterior;
		this.pedimentoOriginal = pedimentoOriginal;
		this.patenteAduanalOrig = patenteAduanalOrig;
		this.seccionAduaneraDespOrig = seccionAduaneraDespOrig;
		this.fechaPagoReal = fechaPagoReal;
	}

	// Property accessors
	@Id 
	@GeneratedValue
	@Column(name = "PK_Rectificaciones", unique = true, nullable = false)
	public Integer getPkRectificaciones() {
		return this.pkRectificaciones;
	}

	public void setPkRectificaciones(Integer pkRectificaciones) {
		this.pkRectificaciones = pkRectificaciones;
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

	@Column(name = "ClaveDocumento", length = 3)
	public String getClaveDocumento() {
		return this.claveDocumento;
	}

	public void setClaveDocumento(String claveDocumento) {
		this.claveDocumento = claveDocumento;
	}

	@Column(name = "FechaPago", length = 23)
	public Timestamp getFechaPago() {
		return this.fechaPago;
	}

	public void setFechaPago(Timestamp fechaPago) {
		this.fechaPago = fechaPago;
	}

	@Column(name = "PedimentoAnterior", length = 7)
	public String getPedimentoAnterior() {
		return this.pedimentoAnterior;
	}

	public void setPedimentoAnterior(String pedimentoAnterior) {
		this.pedimentoAnterior = pedimentoAnterior;
	}

	@Column(name = "PatenteAnterior", length = 4)
	public String getPatenteAnterior() {
		return this.patenteAnterior;
	}

	public void setPatenteAnterior(String patenteAnterior) {
		this.patenteAnterior = patenteAnterior;
	}

	@Column(name = "SeccionAduaneraAnterior", length = 3)
	public String getSeccionAduaneraAnterior() {
		return this.seccionAduaneraAnterior;
	}

	public void setSeccionAduaneraAnterior(String seccionAduaneraAnterior) {
		this.seccionAduaneraAnterior = seccionAduaneraAnterior;
	}

	@Column(name = "DocumentoAnterior", length = 3)
	public String getDocumentoAnterior() {
		return this.documentoAnterior;
	}

	public void setDocumentoAnterior(String documentoAnterior) {
		this.documentoAnterior = documentoAnterior;
	}

	@Column(name = "FechaOperacionAnterior", length = 23)
	public Timestamp getFechaOperacionAnterior() {
		return this.fechaOperacionAnterior;
	}

	public void setFechaOperacionAnterior(Timestamp fechaOperacionAnterior) {
		this.fechaOperacionAnterior = fechaOperacionAnterior;
	}

	@Column(name = "PedimentoOriginal", length = 7)
	public String getPedimentoOriginal() {
		return this.pedimentoOriginal;
	}

	public void setPedimentoOriginal(String pedimentoOriginal) {
		this.pedimentoOriginal = pedimentoOriginal;
	}

	@Column(name = "PatenteAduanalOrig", length = 4)
	public String getPatenteAduanalOrig() {
		return this.patenteAduanalOrig;
	}

	public void setPatenteAduanalOrig(String patenteAduanalOrig) {
		this.patenteAduanalOrig = patenteAduanalOrig;
	}

	@Column(name = "SeccionAduaneraDespOrig", length = 3)
	public String getSeccionAduaneraDespOrig() {
		return this.seccionAduaneraDespOrig;
	}

	public void setSeccionAduaneraDespOrig(String seccionAduaneraDespOrig) {
		this.seccionAduaneraDespOrig = seccionAduaneraDespOrig;
	}

	@Column(name = "FechaPagoReal", length = 23)
	public Timestamp getFechaPagoReal() {
		return this.fechaPagoReal;
	}

	public void setFechaPagoReal(Timestamp fechaPagoReal) {
		this.fechaPagoReal = fechaPagoReal;
	}

}