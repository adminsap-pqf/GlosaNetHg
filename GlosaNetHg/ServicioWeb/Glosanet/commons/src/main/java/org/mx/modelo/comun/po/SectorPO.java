package org.mx.modelo.comun.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Sectores")
public class SectorPO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2943437445007143790L;

	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="PK_Sector")
	private int idSector;
	
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
	
	public SectorPO(){}

	public int getIdSector() {
		return idSector;
	}

	public void setIdSector(int idSector) {
		this.idSector = idSector;
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
