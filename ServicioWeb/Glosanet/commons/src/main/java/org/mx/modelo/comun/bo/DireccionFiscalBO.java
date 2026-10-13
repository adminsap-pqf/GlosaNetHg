/**
 * 
 */
package org.mx.modelo.comun.bo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "DireccionFiscalBO")
public class DireccionFiscalBO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1556937272348410834L;
	private Integer idDireccion;
	private String calle;
	private String noInterior;
	private String noExterior;
	private String colonia;
	private String municipio;
	private String estado;
	private String pais;
	private String codigoPostal;
	/**
	 * @param idDireccion
	 * @param calle
	 * @param noInterior
	 * @param noExterior
	 * @param colonia
	 * @param municipio
	 * @param estado
	 * @param pais
	 * @param codigoPostal
	 */
	public DireccionFiscalBO(Integer idDireccion, String calle,
			String noInterior, String noExterior, String colonia,
			String municipio, String estado, String pais, String codigoPostal) {
		super();
		this.idDireccion = idDireccion;
		this.calle = calle;
		this.noInterior = noInterior;
		this.noExterior = noExterior;
		this.colonia = colonia;
		this.municipio = municipio;
		this.estado = estado;
		this.pais = pais;
		this.codigoPostal = codigoPostal;
	}
	
	public DireccionFiscalBO(){
		
	}
	/**
	 * @return the idDireccion
	 */
	public Integer getIdDireccion() {
		return idDireccion;
	}
	/**
	 * @param idDireccion the idDireccion to set
	 */
	public void setIdDireccion(Integer idDireccion) {
		this.idDireccion = idDireccion;
	}
	/**
	 * @return the calle
	 */
	public String getCalle() {
		return calle;
	}
	/**
	 * @param calle the calle to set
	 */
	public void setCalle(String calle) {
		this.calle = calle;
	}
	/**
	 * @return the noInterior
	 */
	public String getNoInterior() {
		return noInterior;
	}
	/**
	 * @param noInterior the noInterior to set
	 */
	public void setNoInterior(String noInterior) {
		this.noInterior = noInterior;
	}
	/**
	 * @return the noExterior
	 */
	public String getNoExterior() {
		return noExterior;
	}
	/**
	 * @param noExterior the noExterior to set
	 */
	public void setNoExterior(String noExterior) {
		this.noExterior = noExterior;
	}
	/**
	 * @return the colonia
	 */
	public String getColonia() {
		return colonia;
	}
	/**
	 * @param colonia the colonia to set
	 */
	public void setColonia(String colonia) {
		this.colonia = colonia;
	}
	/**
	 * @return the municipio
	 */
	public String getMunicipio() {
		return municipio;
	}
	/**
	 * @param municipio the municipio to set
	 */
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	/**
	 * @return the pais
	 */
	public String getPais() {
		return pais;
	}
	/**
	 * @param pais the pais to set
	 */
	public void setPais(String pais) {
		this.pais = pais;
	}
	/**
	 * @return the codigoPostal
	 */
	public String getCodigoPostal() {
		return codigoPostal;
	}
	/**
	 * @param codigoPostal the codigoPostal to set
	 */
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	
	
	

}
