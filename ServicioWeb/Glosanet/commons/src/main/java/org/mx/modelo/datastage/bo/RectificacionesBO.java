/**
 * 
 */
package org.mx.modelo.datastage.bo;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "RectificacionesBO")
public class RectificacionesBO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6215789252656714395L;
	private Integer idRectificaciones;
	private DataStageBO dataStage;
	private String patente;
	private String pedimento;
	private String seccionAduanera;
	private String claveDocumento;
	private Date fechaPago;
	private String pedimentoAnterior;
	private String patenteAnterior;
	private String seccionAduaneraAnterior;
	private String documentoAnterior;
	private Date fechaOperacionAnterior;
	private String pedimentoOriginal;
	private String patenteAduanalOrig;
	private String seccionAduaneraDespOrig;
	private Date fechaPagoReal;
	
	/**
	 * 
	 */
	public RectificacionesBO() {
		super();
	}

	/**
	 * @param idRectificaciones
	 * @param dataStage
	 * @param patente
	 * @param pedimento
	 * @param seccionAduanera
	 * @param claveDocumento
	 * @param fechaPago
	 * @param pedimentoAnterior
	 * @param patenteAnterior
	 * @param seccionAduaneraAnterior
	 * @param documentoAnterior
	 * @param fechaOperacionAnterior
	 * @param pedimentoOriginal
	 * @param patenteAduanalOrig
	 * @param seccionAduaneraDespOrig
	 * @param fechaPagoReal
	 */
	public RectificacionesBO(Integer idRectificaciones, DataStageBO dataStage,
			String patente, String pedimento, String seccionAduanera,
			String claveDocumento, Date fechaPago, String pedimentoAnterior,
			String patenteAnterior, String seccionAduaneraAnterior,
			String documentoAnterior, Date fechaOperacionAnterior,
			String pedimentoOriginal, String patenteAduanalOrig,
			String seccionAduaneraDespOrig, Date fechaPagoReal) {
		super();
		this.idRectificaciones = idRectificaciones;
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

	/**
	 * @return the idRectificaciones
	 */
	public Integer getIdRectificaciones() {
		return idRectificaciones;
	}

	/**
	 * @param idRectificaciones the idRectificaciones to set
	 */
	public void setIdRectificaciones(Integer idRectificaciones) {
		this.idRectificaciones = idRectificaciones;
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
	 * @return the claveDocumento
	 */
	public String getClaveDocumento() {
		return claveDocumento;
	}

	/**
	 * @param claveDocumento the claveDocumento to set
	 */
	public void setClaveDocumento(String claveDocumento) {
		this.claveDocumento = claveDocumento;
	}

	/**
	 * @return the fechaPago
	 */
	public Date getFechaPago() {
		return fechaPago;
	}

	/**
	 * @param fechaPago the fechaPago to set
	 */
	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	/**
	 * @return the pedimentoAnterior
	 */
	public String getPedimentoAnterior() {
		return pedimentoAnterior;
	}

	/**
	 * @param pedimentoAnterior the pedimentoAnterior to set
	 */
	public void setPedimentoAnterior(String pedimentoAnterior) {
		this.pedimentoAnterior = pedimentoAnterior;
	}

	/**
	 * @return the patenteAnterior
	 */
	public String getPatenteAnterior() {
		return patenteAnterior;
	}

	/**
	 * @param patenteAnterior the patenteAnterior to set
	 */
	public void setPatenteAnterior(String patenteAnterior) {
		this.patenteAnterior = patenteAnterior;
	}

	/**
	 * @return the seccionAduaneraAnterior
	 */
	public String getSeccionAduaneraAnterior() {
		return seccionAduaneraAnterior;
	}

	/**
	 * @param seccionAduaneraAnterior the seccionAduaneraAnterior to set
	 */
	public void setSeccionAduaneraAnterior(String seccionAduaneraAnterior) {
		this.seccionAduaneraAnterior = seccionAduaneraAnterior;
	}

	/**
	 * @return the documentoAnterior
	 */
	public String getDocumentoAnterior() {
		return documentoAnterior;
	}

	/**
	 * @param documentoAnterior the documentoAnterior to set
	 */
	public void setDocumentoAnterior(String documentoAnterior) {
		this.documentoAnterior = documentoAnterior;
	}

	/**
	 * @return the fechaOperacionAnterior
	 */
	public Date getFechaOperacionAnterior() {
		return fechaOperacionAnterior;
	}

	/**
	 * @param fechaOperacionAnterior the fechaOperacionAnterior to set
	 */
	public void setFechaOperacionAnterior(Date fechaOperacionAnterior) {
		this.fechaOperacionAnterior = fechaOperacionAnterior;
	}

	/**
	 * @return the pedimentoOriginal
	 */
	public String getPedimentoOriginal() {
		return pedimentoOriginal;
	}

	/**
	 * @param pedimentoOriginal the pedimentoOriginal to set
	 */
	public void setPedimentoOriginal(String pedimentoOriginal) {
		this.pedimentoOriginal = pedimentoOriginal;
	}

	/**
	 * @return the patenteAduanalOrig
	 */
	public String getPatenteAduanalOrig() {
		return patenteAduanalOrig;
	}

	/**
	 * @param patenteAduanalOrig the patenteAduanalOrig to set
	 */
	public void setPatenteAduanalOrig(String patenteAduanalOrig) {
		this.patenteAduanalOrig = patenteAduanalOrig;
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
