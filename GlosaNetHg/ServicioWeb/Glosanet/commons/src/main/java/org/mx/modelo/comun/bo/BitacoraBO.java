/**
 * 
 */
package org.mx.modelo.comun.bo;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "BitacoraBO")
public class BitacoraBO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2346014040140493795L;
	Integer idBitacora;
	String accion;
	String seccion;
	UsuarioBO usuario;
	Date fechaAccion;
	/**
	 * 
	 */
	public BitacoraBO() {
		super();
	}
	/**
	 * @return the idBitacora
	 */
	public Integer getIdBitacora() {
		return idBitacora;
	}
	/**
	 * @param idBitacora the idBitacora to set
	 */
	public void setIdBitacora(Integer idBitacora) {
		this.idBitacora = idBitacora;
	}
	/**
	 * @return the accion
	 */
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
	public UsuarioBO getUsuario() {
		return usuario;
	}
	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(UsuarioBO usuario) {
		this.usuario = usuario;
	}
	/**
	 * @return the fechaAccion
	 */
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
