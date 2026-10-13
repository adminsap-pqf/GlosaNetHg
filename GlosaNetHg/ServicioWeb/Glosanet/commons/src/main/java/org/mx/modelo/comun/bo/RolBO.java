package org.mx.modelo.comun.bo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "RolBO")
public class RolBO implements Serializable {
	
	private static final long serialVersionUID = -8077095690359183054L;
	private int idRol;
	private String nombreES;
	private String nombreEN;
	private String descripcionES;
	private String descripcionEN;
	private boolean activo;
	private int nivelAcceso;
	
	public RolBO(){
		
	}

	/**
	 * @return the idRol
	 */
	public int getIdRol() {
		return idRol;
	}

	/**
	 * @param idRol the idRol to set
	 */
	public void setIdRol(int idRol) {
		this.idRol = idRol;
	}

	/**
	 * @return the nombreES
	 */
	public String getNombreES() {
		return nombreES;
	}

	/**
	 * @param nombreES the nombreES to set
	 */
	public void setNombreES(String nombreES) {
		this.nombreES = nombreES;
	}

	/**
	 * @return the nombreEN
	 */
	public String getNombreEN() {
		return nombreEN;
	}

	/**
	 * @param nombreEN the nombreEN to set
	 */
	public void setNombreEN(String nombreEN) {
		this.nombreEN = nombreEN;
	}

	/**
	 * @return the descripcionES
	 */
	public String getDescripcionES() {
		return descripcionES;
	}

	/**
	 * @param descripcionES the descripcionES to set
	 */
	public void setDescripcionES(String descripcionES) {
		this.descripcionES = descripcionES;
	}

	/**
	 * @return the descripcionEN
	 */
	public String getDescripcionEN() {
		return descripcionEN;
	}

	/**
	 * @param descripcionEN the descripcionEN to set
	 */
	public void setDescripcionEN(String descripcionEN) {
		this.descripcionEN = descripcionEN;
	}

	/**
	 * @return the activo
	 */
	public boolean isActivo() {
		return activo;
	}

	/**
	 * @param activo the activo to set
	 */
	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	/**
	 * @return the nivelAcceso
	 */
	public int getNivelAcceso() {
		return nivelAcceso;
	}

	/**
	 * @param nivelAcceso the nivelAcceso to set
	 */
	public void setNivelAcceso(int nivelAcceso) {
		this.nivelAcceso = nivelAcceso;
	}
	
	

}
