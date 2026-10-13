/**
 * 
 */
package org.mx.modelo.comun.bo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "DatosContactoBO")
public class DatosContactoBO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 621941850200543338L;
	public Integer idDatos;
	public String nombre;
	public String aPaterno;
	public String aMaterno;
	public String correo;
	public String telefono;
	public String celular;
	/**
	 * 
	 */
	public DatosContactoBO() {
		super();
	}
	/**
	 * @return the idDatos
	 */
	public Integer getIdDatos() {
		return idDatos;
	}
	/**
	 * @param idDatos the idDatos to set
	 */
	public void setIdDatos(Integer idDatos) {
		this.idDatos = idDatos;
	}
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return the aPaterno
	 */
	public String getaPaterno() {
		return aPaterno;
	}
	/**
	 * @param aPaterno the aPaterno to set
	 */
	public void setaPaterno(String aPaterno) {
		this.aPaterno = aPaterno;
	}
	/**
	 * @return the aMaterno
	 */
	public String getaMaterno() {
		return aMaterno;
	}
	/**
	 * @param aMaterno the aMaterno to set
	 */
	public void setaMaterno(String aMaterno) {
		this.aMaterno = aMaterno;
	}
	/**
	 * @return the lada
	 */
	public String getCorreo() {
		return correo;
	}
	/**
	 * @param lada the lada to set
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	/**
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}
	/**
	 * @param telefono the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	/**
	 * @return the celular
	 */
	public String getCelular() {
		return celular;
	}
	/**
	 * @param celular the celular to set
	 */
	public void setCelular(String celular) {
		this.celular = celular;
	}
}
