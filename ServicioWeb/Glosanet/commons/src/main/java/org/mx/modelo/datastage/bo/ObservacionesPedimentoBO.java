/**
 * 
 */
package org.mx.modelo.datastage.bo;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ObservacionesPedimentoBO")
public class ObservacionesPedimentoBO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9173362264400456306L;
	private Integer idObservacionesPedimento;
	private DataStageBO dataStage;
	private String patente;
	private String pedimento;
	private String seccionAduanera;
	private Integer secuenciaObservacion;
	private String observaciones;
	private Integer tipoPedimento;
	private Date fechaValidacionPagoR;
	
	/**
	 * 
	 */
	public ObservacionesPedimentoBO() {
		super();
	}

	/**
	 * @param idObservacionesPedimento
	 * @param dataStage
	 * @param patente
	 * @param pedimento
	 * @param seccionAduanera
	 * @param secuenciaObservacion
	 * @param observaciones
	 * @param tipoPedimento
	 * @param fechaValidacionPagoR
	 */
	public ObservacionesPedimentoBO(Integer idObservacionesPedimento,
			DataStageBO dataStage, String patente, String pedimento,
			String seccionAduanera, Integer secuenciaObservacion,
			String observaciones, Integer tipoPedimento,
			Date fechaValidacionPagoR) {
		super();
		this.idObservacionesPedimento = idObservacionesPedimento;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
		this.secuenciaObservacion = secuenciaObservacion;
		this.observaciones = observaciones;
		this.tipoPedimento = tipoPedimento;
		this.fechaValidacionPagoR = fechaValidacionPagoR;
	}

	/**
	 * @return the idObservacionesPedimento
	 */
	public Integer getIdObservacionesPedimento() {
		return idObservacionesPedimento;
	}

	/**
	 * @param idObservacionesPedimento the idObservacionesPedimento to set
	 */
	public void setIdObservacionesPedimento(Integer idObservacionesPedimento) {
		this.idObservacionesPedimento = idObservacionesPedimento;
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
	 * @return the secuenciaObservacion
	 */
	public Integer getSecuenciaObservacion() {
		return secuenciaObservacion;
	}

	/**
	 * @param secuenciaObservacion the secuenciaObservacion to set
	 */
	public void setSecuenciaObservacion(Integer secuenciaObservacion) {
		this.secuenciaObservacion = secuenciaObservacion;
	}

	/**
	 * @return the observaciones
	 */
	public String getObservaciones() {
		return observaciones;
	}

	/**
	 * @param observaciones the observaciones to set
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
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
	 * @return the fechaValidacionPagoR
	 */
	public Date getFechaValidacionPagoR() {
		return fechaValidacionPagoR;
	}

	/**
	 * @param fechaValidacionPagoR the fechaValidacionPagoR to set
	 */
	public void setFechaValidacionPagoR(Date fechaValidacionPagoR) {
		this.fechaValidacionPagoR = fechaValidacionPagoR;
	}
}
