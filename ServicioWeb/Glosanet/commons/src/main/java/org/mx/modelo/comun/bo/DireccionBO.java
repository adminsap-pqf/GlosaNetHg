/**
 * 
 */
package org.mx.modelo.comun.bo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "DireccionBO")
public class DireccionBO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5506204893821091119L;
	private String colonia;
	private String estado;
	private String abrevEstado; 
	private String municipio;
	private String pais;
	private String abrevPais; 
	private String codigoPostal;
	
	
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
	 * @return the abrevEstado
	 */
	public String getAbrevEstado() {
		return abrevEstado;
	}
	/**
	 * @param abrevEstado the abrevEstado to set
	 */
	public void setAbrevEstado(String abrevEstado) {
		this.abrevEstado = abrevEstado;
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
	 * @return the abrevPais
	 */
	public String getAbrevPais() {
		return abrevPais;
	}
	/**
	 * @param abrevPais the abrevPais to set
	 */
	public void setAbrevPais(String abrevPais) {
		this.abrevPais = abrevPais;
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
