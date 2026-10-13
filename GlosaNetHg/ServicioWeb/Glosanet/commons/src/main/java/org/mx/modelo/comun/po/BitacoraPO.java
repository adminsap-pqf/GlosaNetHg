/**
 * 
 */
package org.mx.modelo.comun.po;

import java.util.Date;

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
@Table(name = "GN_Bitacora")
public class BitacoraPO implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7896103946617921275L;
	Integer pkBitacora;
	String accion;
	String seccion;
	UsuariosPO usuario;
	Date fechaAccion;
	/**
	 * 
	 */
	public BitacoraPO() {
		super();
	}
	/**
	 * @return the pkBitacora
	 */
	
	@Id 
	@GeneratedValue
	@Column(name = "PK_Bitacora", unique = true, nullable = false)
	public Integer getPkBitacora() {
		return pkBitacora;
	}
	/**
	 * @param pkBitacora the pkBitacora to set
	 */
	public void setPkBitacora(Integer pkBitacora) {
		this.pkBitacora = pkBitacora;
	}
	/**
	 * @return the accion
	 */
	@Column(name = "Accion")
	public String getAccion() {
		return accion;
	}
	/**
	 * @param accion the accion to set
	 */
	public void setAccion(String accion) {
		this.accion = accion;
	}
	/**
	 * @return the seccion
	 */
	@Column(name = "Seccion")
	public String getSeccion() {
		return seccion;
	}
	/**
	 * @param seccion the seccion to set
	 */
	public void setSeccion(String seccion) {
		this.seccion = seccion;
	}
	/**
	 * @return the usuario
	 */
	@Column(name = "FK01_Usuario")
	public UsuariosPO getUsuario() {
		return usuario;
	}
	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(UsuariosPO usuario) {
		this.usuario = usuario;
	}
	/**
	 * @return the fechaAccion
	 */
	@Column(name = "Fecha_Accion")
	public Date getFechaAccion() {
		return fechaAccion;
	}
	/**
	 * @param fechaAccion the fechaAccion to set
	 */
	public void setFechaAccion(Date fechaAccion) {
		this.fechaAccion = fechaAccion;
	}
	
	

}
