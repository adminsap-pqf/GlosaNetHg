package org.mx.modelo.permisos.org.mx.bo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "PermisoCorreoEventoBO")
public class PermisoCorreoEventoBO {
	private Integer idCorreo;
	private Integer idEvento;
	private String correo;
	
	
	
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
