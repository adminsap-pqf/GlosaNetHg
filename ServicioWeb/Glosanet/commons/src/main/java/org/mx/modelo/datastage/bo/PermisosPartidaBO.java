/**
 * 
 */
package org.mx.modelo.datastage.bo;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "PermisosPartidaBO")
public class PermisosPartidaBO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4658164227600645425L;
	private Integer idPermisosPartida;
	private DataStageBO dataStage;
	private String patente;
	private String pedimento;
	private String seccionAduanera;
	private String fraccion;
	private Integer secuenciaFraccion;
	private String clavePermiso;
	private String firmaDescargo;
	private String numeroPermiso;
	private Double valorComercialDolares;
	private Double cantidadMumtarifa;
	private Date fechaPagoReal;
	
	/**
	 * 
	 */
	public PermisosPartidaBO() {
		super();
	}

	/**
	 * @param idPermisosPartida
	 * @param dataStage
	 * @param patente
	 * @param pedimento
	 * @param seccionAduanera
	 * @param fraccion
	 * @param secuenciaFraccion
	 * @param clavePermiso
	 * @param firmaDescargo
	 * @param numeroPermiso
	 * @param valorComercialDolares
	 * @param cantidadMumtarifa
	 * @param fechaPagoReal
	 */
	public PermisosPartidaBO(Integer idPermisosPartida, DataStageBO dataStage,
			String patente, String pedimento, String seccionAduanera,
			String fraccion, Integer secuenciaFraccion, String clavePermiso,
			String firmaDescargo, String numeroPermiso,
			Double valorComercialDolares, Double cantidadMumtarifa,
			Date fechaPagoReal) {
		super();
		this.idPermisosPartida = idPermisosPartida;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
		this.fraccion = fraccion;
		this.secuenciaFraccion = secuenciaFraccion;
		this.clavePermiso = clavePermiso;
		this.firmaDescargo = firmaDescargo;
		this.numeroPermiso = numeroPermiso;
		this.valorComercialDolares = valorComercialDolares;
		this.cantidadMumtarifa = cantidadMumtarifa;
		this.fechaPagoReal = fechaPagoReal;
	}

	/**
	 * @return the idPermisosPartida
	 */
	public Integer getIdPermisosPartida() {
		return idPermisosPartida;
	}

	/**
	 * @param idPermisosPartida the idPermisosPartida to set
	 */
	public void setIdPermisosPartida(Integer idPermisosPartida) {
		this.idPermisosPartida = idPermisosPartida;
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
	 * @return the clavePermiso
	 */
	public String getClavePermiso() {
		return clavePermiso;
	}

	/**
	 * @param clavePermiso the clavePermiso to set
	 */
	public void setClavePermiso(String clavePermiso) {
		this.clavePermiso = clavePermiso;
	}

	/**
	 * @return the firmaDescargo
	 */
	public String getFirmaDescargo() {
		return firmaDescargo;
	}

	/**
	 * @param firmaDescargo the firmaDescargo to set
	 */
	public void setFirmaDescargo(String firmaDescargo) {
		this.firmaDescargo = firmaDescargo;
	}

	/**
	 * @return the numeroPermiso
	 */
	public String getNumeroPermiso() {
		return numeroPermiso;
	}

	/**
	 * @param numeroPermiso the numeroPermiso to set
	 */
	public void setNumeroPermiso(String numeroPermiso) {
		this.numeroPermiso = numeroPermiso;
	}

	/**
	 * @return the valorComercialDolares
	 */
	public Double getValorComercialDolares() {
		return valorComercialDolares;
	}

	/**
	 * @param valorComercialDolares the valorComercialDolares to set
	 */
	public void setValorComercialDolares(Double valorComercialDolares) {
		this.valorComercialDolares = valorComercialDolares;
	}

	/**
	 * @return the cantidadMumtarifa
	 */
	public Double getCantidadMumtarifa() {
		return cantidadMumtarifa;
	}

	/**
	 * @param cantidadMumtarifa the cantidadMumtarifa to set
	 */
	public void setCantidadMumtarifa(Double cantidadMumtarifa) {
		this.cantidadMumtarifa = cantidadMumtarifa;
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
