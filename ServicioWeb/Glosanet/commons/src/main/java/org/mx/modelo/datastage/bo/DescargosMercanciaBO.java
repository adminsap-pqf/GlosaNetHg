/**
 * 
 */
package org.mx.modelo.datastage.bo;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "DescargosMercanciaBO")
public class DescargosMercanciaBO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2781968131160113309L;
	private Integer pkDescargosMercancia;
	private DataStageBO dataStage;
	private String patente;
	private String pedimento;
	private String seccionAduanera;
	private Integer patenteAduanalOrig;
	private Integer pedimentoOriginal;
	private String seccionAduaneraDespOrig;
	private String documentoOriginal;
	private Date fechaOperacionOrig;
	private String fraccionOriginal;
	private Integer unidadMedida;
	private Double mercanciaDescargada;
	private Integer tipoPedimento;
	private Date fechaPagoReal;
	
	/**
	 * 
	 */
	public DescargosMercanciaBO() {
		super();
	}

	/**
	 * @param pkDescargosMercancia
	 * @param dataStage
	 * @param patente
	 * @param pedimento
	 * @param seccionAduanera
	 * @param patenteAduanalOrig
	 * @param pedimentoOriginal
	 * @param seccionAduaneraDespOrig
	 * @param documentoOriginal
	 * @param fechaOperacionOrig
	 * @param fraccionOriginal
	 * @param unidadMedida
	 * @param mercanciaDescargada
	 * @param tipoPedimento
	 * @param fechaPagoReal
	 */
	public DescargosMercanciaBO(Integer pkDescargosMercancia,
			DataStageBO dataStage, String patente, String pedimento,
			String seccionAduanera, Integer patenteAduanalOrig,
			Integer pedimentoOriginal, String seccionAduaneraDespOrig,
			String documentoOriginal, Date fechaOperacionOrig,
			String fraccionOriginal, Integer unidadMedida,
			Double mercanciaDescargada, Integer tipoPedimento,
			Date fechaPagoReal) {
		super();
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

	/**
	 * @return the pkDescargosMercancia
	 */
	public Integer getPkDescargosMercancia() {
		return pkDescargosMercancia;
	}

	/**
	 * @param pkDescargosMercancia the pkDescargosMercancia to set
	 */
	public void setPkDescargosMercancia(Integer pkDescargosMercancia) {
		this.pkDescargosMercancia = pkDescargosMercancia;
	}

	/**
	 * @return the dataStage
	 */
	public DataStageBO getDataStage() {
		return dataStage;
	}

	/**
	 * @param dataStage the dataStage to set
	 */
	public void setDataStage(DataStageBO dataStage) {
		this.dataStage = dataStage;
	}

	/**
	 * @return the patente
	 */
	public String getPatente() {
		return patente;
	}

	/**
	 * @param patente the patente to set
	 */
	public void setPatente(String patente) {
		this.patente = patente;
	}

	/**
	 * @return the pedimento
	 */
	public String getPedimento() {
		return pedimento;
	}

	/**
	 * @param pedimento the pedimento to set
	 */
	public void setPedimento(String pedimento) {
		this.pedimento = pedimento;
	}

	/**
	 * @return the seccionAduanera
	 */
	public String getSeccionAduanera() {
		return seccionAduanera;
	}

	/**
	 * @param seccionAduanera the seccionAduanera to set
	 */
	public void setSeccionAduanera(String seccionAduanera) {
		this.seccionAduanera = seccionAduanera;
	}

	/**
	 * @return the patenteAduanalOrig
	 */
	public Integer getPatenteAduanalOrig() {
		return patenteAduanalOrig;
	}

	/**
	 * @param patenteAduanalOrig the patenteAduanalOrig to set
	 */
	public void setPatenteAduanalOrig(Integer patenteAduanalOrig) {
		this.patenteAduanalOrig = patenteAduanalOrig;
	}

	/**
	 * @return the pedimentoOriginal
	 */
	public Integer getPedimentoOriginal() {
		return pedimentoOriginal;
	}

	/**
	 * @param pedimentoOriginal the pedimentoOriginal to set
	 */
	public void setPedimentoOriginal(Integer pedimentoOriginal) {
		this.pedimentoOriginal = pedimentoOriginal;
	}

	/**
	 * @return the seccionAduaneraDespOrig
	 */
	public String getSeccionAduaneraDespOrig() {
		return seccionAduaneraDespOrig;
	}

	/**
	 * @param seccionAduaneraDespOrig the seccionAduaneraDespOrig to set
	 */
	public void setSeccionAduaneraDespOrig(String seccionAduaneraDespOrig) {
		this.seccionAduaneraDespOrig = seccionAduaneraDespOrig;
	}

	/**
	 * @return the documentoOriginal
	 */
	public String getDocumentoOriginal() {
		return documentoOriginal;
	}

	/**
	 * @param documentoOriginal the documentoOriginal to set
	 */
	public void setDocumentoOriginal(String documentoOriginal) {
		this.documentoOriginal = documentoOriginal;
	}

	/**
	 * @return the fechaOperacionOrig
	 */
	public Date getFechaOperacionOrig() {
		return fechaOperacionOrig;
	}

	/**
	 * @param fechaOperacionOrig the fechaOperacionOrig to set
	 */
	public void setFechaOperacionOrig(Date fechaOperacionOrig) {
		this.fechaOperacionOrig = fechaOperacionOrig;
	}

	/**
	 * @return the fraccionOriginal
	 */
	public String getFraccionOriginal() {
		return fraccionOriginal;
	}

	/**
	 * @param fraccionOriginal the fraccionOriginal to set
	 */
	public void setFraccionOriginal(String fraccionOriginal) {
		this.fraccionOriginal = fraccionOriginal;
	}

	/**
	 * @return the unidadMedida
	 */
	public Integer getUnidadMedida() {
		return unidadMedida;
	}

	/**
	 * @param unidadMedida the unidadMedida to set
	 */
	public void setUnidadMedida(Integer unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	/**
	 * @return the mercanciaDescargada
	 */
	public Double getMercanciaDescargada() {
		return mercanciaDescargada;
	}

	/**
	 * @param mercanciaDescargada the mercanciaDescargada to set
	 */
	public void setMercanciaDescargada(Double mercanciaDescargada) {
		this.mercanciaDescargada = mercanciaDescargada;
	}

	/**
	 * @return the tipoPedimento
	 */
	public Integer getTipoPedimento() {
		return tipoPedimento;
	}

	/**
	 * @param tipoPedimento the tipoPedimento to set
	 */
	public void setTipoPedimento(Integer tipoPedimento) {
		this.tipoPedimento = tipoPedimento;
	}

	/**
	 * @return the fechaPagoReal
	 */
	public Date getFechaPagoReal() {
		return fechaPagoReal;
	}

	/**
	 * @param fechaPagoReal the fechaPagoReal to set
	 */
	public void setFechaPagoReal(Date fechaPagoReal) {
		this.fechaPagoReal = fechaPagoReal;
	}
}
