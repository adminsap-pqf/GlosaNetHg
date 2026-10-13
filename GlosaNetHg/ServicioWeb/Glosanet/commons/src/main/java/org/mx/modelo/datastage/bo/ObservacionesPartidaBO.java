/**
 * 
 */
package org.mx.modelo.datastage.bo;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ObservacionesPartidaBO")
public class ObservacionesPartidaBO implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5612208266144976106L;
	private Integer idObservacionesPartida;
	private DataStageBO dataStage;
	private String patente;
	private String pedimento;
	private String seccionAduanera;
	private String fraccion;
	private Integer secuenciaFraccion;
	private Integer secuenciaObservacion;
	private String observaciones;
	private Date fechaPagoReal;
	
	/**
	 * 
	 */
	public ObservacionesPartidaBO() {
		super();
	}

	/**
	 * @param idObservacionesPartida
	 * @param dataStage
	 * @param patente
	 * @param pedimento
	 * @param seccionAduanera
	 * @param fraccion
	 * @param secuenciaFraccion
	 * @param secuenciaObservacion
	 * @param observaciones
	 * @param fechaPagoReal
	 */
	public ObservacionesPartidaBO(Integer idObservacionesPartida,
			DataStageBO dataStage, String patente, String pedimento,
			String seccionAduanera, String fraccion, Integer secuenciaFraccion,
			Integer secuenciaObservacion, String observaciones,
			Date fechaPagoReal) {
		super();
		this.idObservacionesPartida = idObservacionesPartida;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
		this.fraccion = fraccion;
		this.secuenciaFraccion = secuenciaFraccion;
		this.secuenciaObservacion = secuenciaObservacion;
		this.observaciones = observaciones;
		this.fechaPagoReal = fechaPagoReal;
	}

	/**
	 * @return the idObservacionesPartida
	 */
	public Integer getIdObservacionesPartida() {
		return idObservacionesPartida;
	}

	/**
	 * @param idObservacionesPartida the idObservacionesPartida to set
	 */
	public void setIdObservacionesPartida(Integer idObservacionesPartida) {
		this.idObservacionesPartida = idObservacionesPartida;
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
	 * @return the fraccion
	 */
	public String getFraccion() {
		return fraccion;
	}

	/**
	 * @param fraccion the fraccion to set
	 */
	public void setFraccion(String fraccion) {
		this.fraccion = fraccion;
	}

	/**
	 * @return the secuenciaFraccion
	 */
	public Integer getSecuenciaFraccion() {
		return secuenciaFraccion;
	}

	/**
	 * @param secuenciaFraccion the secuenciaFraccion to set
	 */
	public void setSecuenciaFraccion(Integer secuenciaFraccion) {
		this.secuenciaFraccion = secuenciaFraccion;
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
