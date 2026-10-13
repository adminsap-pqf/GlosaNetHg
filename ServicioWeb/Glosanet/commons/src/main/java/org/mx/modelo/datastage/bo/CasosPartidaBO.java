package org.mx.modelo.datastage.bo;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CasosPartidaBO")
public class CasosPartidaBO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2459500078983559325L;
	private Integer idCasosPartida;
	private DataStageBO dataStage;
	private String patente;
	private String pedimento;
	private String seccionAduanera;
	private String fraccion;
	private Integer secuenciaFraccion;
	private String claveCaso;
	private String identificadorCaso;
	private String complementoCaso;
	private Date fechaPagoReal;
	
	/**
	 * 
	 */
	public CasosPartidaBO() {
		super();
	}

	/**
	 * @param idCasosPartida
	 * @param dataStage
	 * @param patente
	 * @param pedimento
	 * @param seccionAduanera
	 * @param fraccion
	 * @param secuenciaFraccion
	 * @param claveCaso
	 * @param identificadorCaso
	 * @param complementoCaso
	 * @param fechaPagoReal
	 */
	public CasosPartidaBO(Integer idCasosPartida, DataStageBO dataStage,
			String patente, String pedimento, String seccionAduanera,
			String fraccion, Integer secuenciaFraccion, String claveCaso,
			String identificadorCaso, String complementoCaso, Date fechaPagoReal) {
		super();
		this.idCasosPartida = idCasosPartida;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
		this.fraccion = fraccion;
		this.secuenciaFraccion = secuenciaFraccion;
		this.claveCaso = claveCaso;
		this.identificadorCaso = identificadorCaso;
		this.complementoCaso = complementoCaso;
		this.fechaPagoReal = fechaPagoReal;
	}

	/**
	 * @return the idCasosPartida
	 */
	public Integer getIdCasosPartida() {
		return idCasosPartida;
	}

	/**
	 * @param idCasosPartida the idCasosPartida to set
	 */
	public void setIdCasosPartida(Integer idCasosPartida) {
		this.idCasosPartida = idCasosPartida;
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
	 * @return the claveCaso
	 */
	public String getClaveCaso() {
		return claveCaso;
	}

	/**
	 * @param claveCaso the claveCaso to set
	 */
	public void setClaveCaso(String claveCaso) {
		this.claveCaso = claveCaso;
	}

	/**
	 * @return the identificadorCaso
	 */
	public String getIdentificadorCaso() {
		return identificadorCaso;
	}

	/**
	 * @param identificadorCaso the identificadorCaso to set
	 */
	public void setIdentificadorCaso(String identificadorCaso) {
		this.identificadorCaso = identificadorCaso;
	}

	/**
	 * @return the complementoCaso
	 */
	public String getComplementoCaso() {
		return complementoCaso;
	}

	/**
	 * @param complementoCaso the complementoCaso to set
	 */
	public void setComplementoCaso(String complementoCaso) {
		this.complementoCaso = complementoCaso;
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
