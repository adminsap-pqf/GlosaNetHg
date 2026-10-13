package org.mx.modelo.registros.bo;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "FeriadosOficialesBO")
public class FeriadosOficialesBO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -649954277044649771L;
	private Integer idFeriados;
	private String nombre;
	private Date fecha;
	private Integer periodo;
	
	
	public Integer getIdFeriados() {
		return idFeriados;
	}
	public void setIdFeriados(Integer idFeriados) {
		this.idFeriados = idFeriados;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Integer getPeriodo() {
		return periodo;
	}
	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}
	
	
}
