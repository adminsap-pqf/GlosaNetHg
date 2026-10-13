package org.mx.modelo.comun.bo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "IndustriaBO")
public class IndustriaBO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8475819632402066131L;
	private int idIndustria;
	private String nombreEs;
	private String nombreEn;
	private String descripcionEs;
	private String descripcionEn;
	private boolean activo;
	
	public IndustriaBO(){}

	public int getIdIndustria() {
		return idIndustria;
	}

	public void setIdIndustria(int idIndustria) {
		this.idIndustria = idIndustria;
	}

	public String getNombreEs() {
		return nombreEs;
	}

	public void setNombreEs(String nombreEs) {
		this.nombreEs = nombreEs;
	}

	public String getNombreEn() {
		return nombreEn;
	}

	public void setNombreEn(String nombreEn) {
		this.nombreEn = nombreEn;
	}

	public String getDescripcionEs() {
		return descripcionEs;
	}

	public void setDescripcionEs(String descripcionEs) {
		this.descripcionEs = descripcionEs;
	}

	public String getDescripcionEn() {
		return descripcionEn;
	}

	public void setDescripcionEn(String descripcionEn) {
		this.descripcionEn = descripcionEn;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	
}
