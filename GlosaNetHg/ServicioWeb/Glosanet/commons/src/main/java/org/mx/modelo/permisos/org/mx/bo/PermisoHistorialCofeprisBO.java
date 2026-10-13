package org.mx.modelo.permisos.org.mx.bo;

import java.io.Serializable;
import java.util.Date;

public class PermisoHistorialCofeprisBO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3200287024979836289L;
	private Integer idHistorial;
	private String tramite;
	private Date fecha;
	private String estado;
	
	
	
	
	public Integer getIdHistorial() {
		return idHistorial;
	}
	public void setIdHistorial(Integer idHistorial) {
		this.idHistorial = idHistorial;
	}
	public String getTramite() {
		return tramite;
	}
	public void setTramite(String tramite) {
		this.tramite = tramite;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
}
