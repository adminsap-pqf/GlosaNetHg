package org.mx.modelo.comun.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "GN_Regiones")
public class RegionesPO implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4327685756506604582L;
	private int idRegion;
	private int numRegion;
	private String nombreRegion;
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="PK_Region")
	public int getIdRegion() {
		return idRegion;
	}
	public void setIdRegion(int idRegion) {
		this.idRegion = idRegion;
	}
	
	@Column(name = "NumRegion")
	public int getNumRegion() {
		return numRegion;
	}
	public void setNumRegion(int numRegion) {
		this.numRegion = numRegion;
	}
	
	@Column(name = "NombreRegion")
	public String getNombreRegion() {
		return nombreRegion;
	}
	public void setNombreRegion(String nombreRegion) {
		this.nombreRegion = nombreRegion;
	}
}
