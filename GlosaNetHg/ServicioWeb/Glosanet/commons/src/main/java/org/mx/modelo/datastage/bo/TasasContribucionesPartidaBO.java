/**
 * 
 */
package org.mx.modelo.datastage.bo;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "TasasContribucionesPartidaBO")
public class TasasContribucionesPartidaBO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7578088431359679807L;
	private Integer pkTasasContribucionesPartida;
	private DataStageBO dataStage;
	private String patente;
	private String pedimento;
	private String seccionAduanera;
	private String fraccion;
	private Integer secuenciaFraccion;
	private Integer claveContribucion;
	private Double tasaContribucion;
	private Double tipoTasa;
	private Date fechaPagoReal;
	
	/**
	 * 
	 */
	public TasasContribucionesPartidaBO() {
		super();
	}

	/**
	 * @param pkTasasContribucionesPartida
	 * @param dataStage
	 * @param patente
	 * @param pedimento
	 * @param seccionAduanera
	 * @param fraccion
	 * @param secuenciaFraccion
	 * @param claveContribucion
	 * @param tasaContribucion
	 * @param tipoTasa
	 * @param fechaPagoReal
	 */
	public TasasContribucionesPartidaBO(Integer pkTasasContribucionesPartida,
			DataStageBO dataStage, String patente, String pedimento,
			String seccionAduanera, String fraccion, Integer secuenciaFraccion,
			Integer claveContribucion, Double tasaContribucion,
			Double tipoTasa, Date fechaPagoReal) {
		super();
		this.pkTasasContribucionesPartida = pkTasasContribucionesPartida;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
		this.fraccion = fraccion;
		this.secuenciaFraccion = secuenciaFraccion;
		this.claveContribucion = claveContribucion;
		this.tasaContribucion = tasaContribucion;
		this.tipoTasa = tipoTasa;
		this.fechaPagoReal = fechaPagoReal;
	}

	/**
	 * @return the pkTasasContribucionesPartida
	 */
	public Integer getPkTasasContribucionesPartida() {
		return pkTasasContribucionesPartida;
	}

	/**
	 * @param pkTasasContribucionesPartida the pkTasasContribucionesPartida to set
	 */
	public void setPkTasasContribucionesPartida(Integer pkTasasContribucionesPartida) {
		this.pkTasasContribucionesPartida = pkTasasContribucionesPartida;
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
	 * @return the claveContribucion
	 */
	public Integer getClaveContribucion() {
		return claveContribucion;
	}

	/**
	 * @param claveContribucion the claveContribucion to set
	 */
	public void setClaveContribucion(Integer claveContribucion) {
		this.claveContribucion = claveContribucion;
	}

	/**
	 * @return the tasaContribucion
	 */
	public Double getTasaContribucion() {
		return tasaContribucion;
	}

	/**
	 * @param tasaContribucion the tasaContribucion to set
	 */
	public void setTasaContribucion(Double tasaContribucion) {
		this.tasaContribucion = tasaContribucion;
	}

	/**
	 * @return the tipoTasa
	 */
	public Double getTipoTasa() {
		return tipoTasa;
	}

	/**
	 * @param tipoTasa the tipoTasa to set
	 */
	public void setTipoTasa(Double tipoTasa) {
		this.tipoTasa = tipoTasa;
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
