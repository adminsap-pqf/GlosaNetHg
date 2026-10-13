package org.mx.modelo.permisos.org.mx.bo;

import java.io.Serializable;
import java.util.Date;

public class PermisoHistorialEventoBO implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3240767156295351153L;
	private Integer idHistorial;
	private Integer idUsuario;
	private Integer idEvento;
	private Date fecha;
	private String comentarios;
	
	public Integer getIdHistorial() {
		return idHistorial;
	}
	public void setIdHistorial(Integer idHistorial) {
		this.idHistorial = idHistorial;
	}
	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	public Integer getIdEvento() {
		return idEvento;
	}
	public void setIdEvento(Integer idEvento) {
		this.idEvento = idEvento;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getComentarios() {
		return comentarios;
	}
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
	
	
}
