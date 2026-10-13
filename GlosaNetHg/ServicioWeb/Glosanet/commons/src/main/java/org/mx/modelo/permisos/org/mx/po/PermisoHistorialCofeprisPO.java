package org.mx.modelo.permisos.org.mx.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Permiso_HistorialCofepris")
public class PermisoHistorialCofeprisPO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8596677853374372571L;
	private Integer idHistorial;
	private String tramite;
	private Date fecha;
	private String estado;
	
	
	@Id 
	@GeneratedValue
	@Column(name = "PK_HistorialCofepris", unique = true, nullable = false)
	public Integer getIdHistorial() {
		return idHistorial;
	}
	public void setIdHistorial(Integer idHistorial) {
		this.idHistorial = idHistorial;
	}
	@Column(name = "Tramite")
	public String getTramite() {
		return tramite;
	}
	public void setTramite(String tramite) {
		this.tramite = tramite;
	}
	
	@Column(name = "Fecha")
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	@Column(name = "Estado")
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
	
	
}
