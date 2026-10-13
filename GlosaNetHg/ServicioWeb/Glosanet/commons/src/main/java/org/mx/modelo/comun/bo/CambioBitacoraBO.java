package org.mx.modelo.comun.bo;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CambioBitacoraBO")
public class CambioBitacoraBO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5076755225136903054L;
	private int idCambioBitacora;
	private Date fecha;
	private String descripcion;
	private int usuario;
	
	public CambioBitacoraBO(){}

	public int getIdCambioBitacora() {
		return idCambioBitacora;
	}

	public void setIdCambioBitacora(int idCambioBitacora) {
		this.idCambioBitacora = idCambioBitacora;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getUsuario() {
		return usuario;
	}

	public void setUsuario(int usuario) {
		this.usuario = usuario;
	}

}
