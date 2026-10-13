/**
 * 
 */
package org.mx.modelo.comun.bo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "PaqueteBO")
public class PaqueteBO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2828524821868086244L;
	private Integer idPaquete;
	private String nombre;
	private String tipo;
	private Integer pedimentos;
	private Integer usuarios;
	private Double costoAnual;
	private Double costoSemestral;
	private Double costoMensual;
	private Double costoConfiguracion;
	/**
	 * @return the idPaquete
	 */
	public Integer getIdPaquete() {
		return idPaquete;
	}
	/**
	 * @param idPaquete the idPaquete to set
	 */
	public void setIdPaquete(Integer idPaquete) {
		this.idPaquete = idPaquete;
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
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	/**
	 * @return the pedimentos
	 */
	public Integer getPedimentos() {
		return pedimentos;
	}
	/**
	 * @param pedimentos the pedimentos to set
	 */
	public void setPedimentos(Integer pedimentos) {
		this.pedimentos = pedimentos;
	}
	/**
	 * @return the usuarios
	 */
	public Integer getUsuarios() {
		return usuarios;
	}
	/**
	 * @param usuarios the usuarios to set
	 */
	public void setUsuarios(Integer usuarios) {
		this.usuarios = usuarios;
	}
	/**
	 * @return the costoAnual
	 */
	public Double getCostoAnual() {
		return costoAnual;
	}
	/**
	 * @param costoAnual the costoAnual to set
	 */
	public void setCostoAnual(Double costoAnual) {
		this.costoAnual = costoAnual;
	}
	/**
	 * @return the costoSemestral
	 */
	public Double getCostoSemestral() {
		return costoSemestral;
	}
	/**
	 * @param costoSemestral the costoSemestral to set
	 */
	public void setCostoSemestral(Double costoSemestral) {
		this.costoSemestral = costoSemestral;
	}
	/**
	 * @return the costoMensual
	 */
	public Double getCostoMensual() {
		return costoMensual;
	}
	/**
	 * @param costoMensual the costoMensual to set
	 */
	public void setCostoMensual(Double costoMensual) {
		this.costoMensual = costoMensual;
	}
	/**
	 * @return the costoConfiguracion
	 */
	public Double getCostoConfiguracion() {
		return costoConfiguracion;
	}
	/**
	 * @param costoConfiguracion the costoConfiguracion to set
	 */
	public void setCostoConfiguracion(Double costoConfiguracion) {
		this.costoConfiguracion = costoConfiguracion;
	}
	
	
	

}
