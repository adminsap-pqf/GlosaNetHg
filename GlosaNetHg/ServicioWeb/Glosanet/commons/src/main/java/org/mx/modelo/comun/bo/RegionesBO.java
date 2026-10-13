package org.mx.modelo.comun.bo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "RegionesBO")
public class RegionesBO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 185493273066754771L;
	private int idRegion;
	private int numRegion;
	private String nombreRegion;
	private int idEmpresa;
	private String montos;
	
	
	public int getIdRegion() {
		return idRegion;
	}
	public void setIdRegion(int idRegion) {
		this.idRegion = idRegion;
	}
	public int getNumRegion() {
		return numRegion;
	}
	public void setNumRegion(int numRegion) {
		this.numRegion = numRegion;
	}
	public String getNombreRegion() {
		return nombreRegion;
	}
	public void setNombreRegion(String nombreRegion) {
		this.nombreRegion = nombreRegion;
	}
	public int getIdEmpresa() {
		return idEmpresa;
	}
	public void setIdEmpresa(int idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	public String getMontos() {
		return montos;
	}
	public void setMontos(String montos) {
		this.montos = montos;
	}
	
	
	
	
	

}
