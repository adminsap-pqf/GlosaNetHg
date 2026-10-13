package org.mx.modelo.datastage.bo;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "ContribucionesPartidaBO")
public class ContribucionesPartidaBO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1609877850000144432L;
	private Integer id;
	private String patente;
	private String pedimento;
	private String seccionAduanera;
	private String fraccion;
	private Integer secuenciaFraccion;
	private Integer claveContribucion;
	private Integer formaPago;
	private Double importePago;
	private Date fechaPagoReal;
	private DataStageBO dataStage;
	
	private PartidaBO partida;
	
	public ContribucionesPartidaBO() {
		super();
	}


	public ContribucionesPartidaBO(String patente, String pedimento,
			String seccionAduanera, String fraccion, Integer secuenciaFraccion,
			Integer claveContribucion, Integer formaPago, Double importePago,
			Date fechaPagoReal) {
		super();
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
		this.fraccion = fraccion;
		this.secuenciaFraccion = secuenciaFraccion;
		this.claveContribucion = claveContribucion;
		this.formaPago = formaPago;
		this.importePago = importePago;
		this.fechaPagoReal = fechaPagoReal;
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
	 * @return the formaPago
	 */
	public Integer getFormaPago() {
		return formaPago;
	}


	/**
	 * @param formaPago the formaPago to set
	 */
	public void setFormaPago(Integer formaPago) {
		this.formaPago = formaPago;
	}


	/**
	 * @return the importePago
	 */
	public Double getImportePago() {
		return importePago;
	}


	/**
	 * @param importePago the importePago to set
	 */
	public void setImportePago(Double importePago) {
		this.importePago = importePago;
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


	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}


	/**
	 * @return the partida
	 */
	public PartidaBO getPartida() {
		return partida;
	}


	/**
	 * @param partida the partida to set
	 */
	public void setPartida(PartidaBO partida) {
		this.partida = partida;
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
}
