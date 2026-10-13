package org.mx.modelo.comun.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PF_Roles")
public class RolPO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2801590412024270614L;
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="PK_Rol")
	private int idRol;
	@Column(name="NombreES")
	private String nombreES;
	@Column(name="NombreEN")
	private String nombreEN;
	@Column(name="DescripcionES")
	private String descripcionES;
	@Column(name="DescripcionEN")
	private String descripcionEN;
	@Column(name="Activo")
	private boolean activo;
	@Column(name="FK01_NivelAcceso")
	private int nivelAcceso;
	
	
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
	public int getIdRol() {
		return idRol;
	}
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
	

}
