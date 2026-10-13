package org.mx.modelo.comun.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Industrias")
public class IndustriaPO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3076640941719351385L;

	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="PK_Industria")
	private int idIndustria;
	
	@Column(name="NombreES")
	private String nombreEs;
	
	@Column(name="NombreEN")
	private String nombreEn;
	
	@Column(name="DescripcionES")
	private String descripcionEs;
	
	@Column(name="DescripcionEN")
	private String descripcionEn;
	
	@Column(name="Activo")
	private boolean activo;
	
	public IndustriaPO(){}

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
