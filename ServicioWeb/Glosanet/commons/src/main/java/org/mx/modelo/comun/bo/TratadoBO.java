package org.mx.modelo.comun.bo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "TratadoBO")
public class TratadoBO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6245449864752235273L;
	private int idTratado;
	private int numTratado;
	private String nombreTratado;
	private int subTitulo;
	
	public int getIdTratado() {
		return idTratado;
	}
	public void setIdTratado(int idTratado) {
		this.idTratado = idTratado;
	}
	public int getNumTratado() {
		return numTratado;
	}
	public void setNumTratado(int numTratado) {
		this.numTratado = numTratado;
	}
	public String getNombreTratado() {
		return nombreTratado;
	}
	public void setNombreTratado(String nombreTratado) {
		this.nombreTratado = nombreTratado;
	}
	public int getSubTitulo() {
		return subTitulo;
	}
	public void setSubTitulo(int subTitulo) {
		this.subTitulo = subTitulo;
	}

}
