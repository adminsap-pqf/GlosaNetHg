package org.mx.modelo.registros.bo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CorreoEventoBO")
public class CorreoEventoBO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5006971342174063860L;

	private Integer idCorreo;
	private Integer idEvento;
	private String correo;
	
	
	public CorreoEventoBO() {
		super();
	}


	public CorreoEventoBO(Integer idCorreo, Integer idEvento,
			String correo) {
		super();
		this.idCorreo = idCorreo;
		this.idEvento = idEvento;
		this.correo = correo;
	}


	public Integer getIdCorreo() {
		return idCorreo;
	}


	public void setIdCorreo(Integer idCorreo) {
		this.idCorreo = idCorreo;
	}


	public Integer getIdEvento() {
		return idEvento;
	}


	public void setIdEvento(Integer idEvento) {
		this.idEvento = idEvento;
	}


	public String getCorreo() {
		return correo;
	}


	public void setCorreo(String correo) {
		this.correo = correo;
	}






}
