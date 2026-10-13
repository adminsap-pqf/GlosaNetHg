package org.mx.modelo.comun.bo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ApendiceBO")
public class ApendiceBO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5533592223147986310L;
	private int idApendice;
	private int numApendice;
	private String nombreApendice;
	
	public int getIdApendice() {
		return idApendice;
	}
	public void setIdApendice(int idApendice) {
		this.idApendice = idApendice;
	}
	public int getNumApendice() {
		return numApendice;
	}
	public void setNumApendice(int numApendice) {
		this.numApendice = numApendice;
	}
	public String getNombreApendice() {
		return nombreApendice;
	}
	public void setNombreApendice(String nombreApendice) {
		this.nombreApendice = nombreApendice;
	}
}
