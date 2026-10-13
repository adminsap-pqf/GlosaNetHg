/**
 * 
 */
package org.mx.modelo.comun.po;

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
@Table(name = "GN_Datos_Contacto")
public class DatosContactoPO implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2362082345441387721L;
	public Integer pkDatos;
	public String nombre;
	public String aPaterno;
	public String aMaterno;
	public String correo;
	public String telefono;
	public String celular;
	/**
	 * 
	 */
	public DatosContactoPO() {
		super();
	}
	/**
	 * @return the pkDatos
	 */
	
	
	@Id 
	@GeneratedValue
	@Column(name = "PK_Datos", unique = true, nullable = false)
	public Integer getPkDatos() {
		return pkDatos;
	}
	/**
	 * @param pkDatos the pkDatos to set
	 */
	public void setPkDatos(Integer pkDatos) {
		this.pkDatos = pkDatos;
	}
	/**
	 * @return the nombre
	 */
	@Column(name = "Nombre", nullable = false)
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
	@Column(name = "Apellido_Paterno", nullable = false)
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
	@Column(name = "Apellido_Materno", nullable = false)
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
	@Column(name = "Correo", nullable = false)
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
	@Column(name = "Telefono", nullable = false)
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
	@Column(name = "Celular", nullable = false)
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
