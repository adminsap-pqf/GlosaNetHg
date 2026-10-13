package org.mx.modelo.comun.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "GN_Apendices")
public class ApendicePO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1979255394900702482L;
	private int idApendice;
	private int numApendice;
	private String nombreApendice;
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="PK_Apendice")
	public int getIdApendice() {
		return idApendice;
	}
	public void setIdApendice(int idApendice) {
		this.idApendice = idApendice;
	}
	
	@Column(name = "NumApendice")
	public int getNumApendice() {
		return numApendice;
	}
	public void setNumApendice(int numApendice) {
		this.numApendice = numApendice;
	}
	
	@Column(name = "NombreApendice")
	public String getNombreApendice() {
		return nombreApendice;
	}
	public void setNombreApendice(String nombreApendice) {
		this.nombreApendice = nombreApendice;
	}
}
