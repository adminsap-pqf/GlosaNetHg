/**
 * 
 */
package org.mx.modelo.comun.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author yahir.nunez
 *
 */

@Entity
@Table(name = "GN_Direccion_Fiscal")
public class DireccionFiscalPO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2907051215696985262L;
	private Integer pkDireccion;
	private String calle;
	private String noExterior;
	private String noInterior;
	private String colonia;
	private String municipio;
	private String estado;
	private String pais;
	private String codigoPostal;
	/**
	 * @param pkDireccion
	 * @param calle
	 * @param noExterior
	 * @param noInterior
	 * @param colonia
	 * @param municipio
	 * @param estado
	 * @param pais
	 * @param codigoPostal
	 */
	public DireccionFiscalPO(Integer pkDireccion, String calle,
			String noExterior, String noInterior, String colonia,
			String municipio, String estado, String pais, String codigoPostal) {
		super();
		this.pkDireccion = pkDireccion;
		this.calle = calle;
		this.noExterior = noExterior;
		this.noInterior = noInterior;
		this.colonia = colonia;
		this.municipio = municipio;
		this.estado = estado;
		this.pais = pais;
		this.codigoPostal = codigoPostal;
	}
	
	public DireccionFiscalPO(){
		
	}
	/**
	 * @return the pkDireccion
	 */
	@Id
	@GeneratedValue
	@Column(name = "PK_Direccion", unique = true, nullable = false)
	public Integer getPkDireccion() {
		return pkDireccion;
	}
	/**
	 * @param pkDireccion the pkDireccion to set
	 */
	public void setPkDireccion(Integer pkDireccion) {
		this.pkDireccion = pkDireccion;
	}
	/**
	 * @return the calle
	 */
	@Column(name = "Calle")
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
	 * @return the noExterior
	 */
	@Column(name = "No_Exterior")
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
	 * @return the noInterior
	 */
	@Column(name = "No_Interior")
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
	 * @return the colonia
	 */
	@Column(name = "Colonia")
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
	@Column(name = "Municipio")
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
	@Column(name = "Estado")
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
	@Column(name = "Pais")
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
	@Column(name = "Codigo_Postal")
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
