/**
 * 
 */
package org.mx.modelo.datastage.bo;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "MercanciasBO")
public class MercanciasBO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2050353925433000330L;
	private Integer idMercancias;
	private DataStageBO dataStage;
	private String patente;
	private String pedimento;
	private String seccionAduanera;
	private String fraccion;
	private Integer secuenciaFraccion;
	private String vinNumeroSerie;
	private Integer kilometrajeVehiculo;
	private Date fechaPagoReal;
	
	/**
	 * 
	 */
	public MercanciasBO() {
		super();
	}

	/**
	 * @param idMercancias
	 * @param dataStage
	 * @param patente
	 * @param pedimento
	 * @param seccionAduanera
	 * @param fraccion
	 * @param secuenciaFraccion
	 * @param vinNumeroSerie
	 * @param kilometrajeVehiculo
	 * @param fechaPagoReal
	 */
	public MercanciasBO(Integer idMercancias, DataStageBO dataStage,
			String patente, String pedimento, String seccionAduanera,
			String fraccion, Integer secuenciaFraccion, String vinNumeroSerie,
			Integer kilometrajeVehiculo, Date fechaPagoReal) {
		super();
		this.idMercancias = idMercancias;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
		this.fraccion = fraccion;
		this.secuenciaFraccion = secuenciaFraccion;
		this.vinNumeroSerie = vinNumeroSerie;
		this.kilometrajeVehiculo = kilometrajeVehiculo;
		this.fechaPagoReal = fechaPagoReal;
	}

	/**
	 * @return the idMercancias
	 */
	public Integer getIdMercancias() {
		return idMercancias;
	}

	/**
	 * @param idMercancias the idMercancias to set
	 */
	public void setIdMercancias(Integer idMercancias) {
		this.idMercancias = idMercancias;
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
	 * @return the vinNumeroSerie
	 */
	public String getVinNumeroSerie() {
		return vinNumeroSerie;
	}

	/**
	 * @param vinNumeroSerie the vinNumeroSerie to set
	 */
	public void setVinNumeroSerie(String vinNumeroSerie) {
		this.vinNumeroSerie = vinNumeroSerie;
	}

	/**
	 * @return the kilometrajeVehiculo
	 */
	public Integer getKilometrajeVehiculo() {
		return kilometrajeVehiculo;
	}

	/**
	 * @param kilometrajeVehiculo the kilometrajeVehiculo to set
	 */
	public void setKilometrajeVehiculo(Integer kilometrajeVehiculo) {
		this.kilometrajeVehiculo = kilometrajeVehiculo;
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
