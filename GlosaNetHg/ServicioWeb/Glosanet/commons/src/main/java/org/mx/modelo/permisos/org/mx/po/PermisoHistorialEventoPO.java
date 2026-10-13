package org.mx.modelo.permisos.org.mx.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "Permiso_HistorialEvento")
public class PermisoHistorialEventoPO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8811285840368074570L;
	private Integer idHistorial;
	private Integer idUsuario;
	private Integer idEvento;
	private Date fecha;
	private String comentarios;
	
	
	@Id 
	@GeneratedValue
	@Column(name = "PK_Historial", unique = true, nullable = false)
	public Integer getIdHistorial() {
		return idHistorial;
	}
	public void setIdHistorial(Integer idHistorial) {
		this.idHistorial = idHistorial;
	}
	
	@Column(name = "FK01Usuario")
	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	@Column(name = "FK02Evento")
	public Integer getIdEvento() {
		return idEvento;
	}
	public void setIdEvento(Integer idEvento) {
		this.idEvento = idEvento;
	}
	
	@Column(name = "Fecha")
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	@Column(name = "Comentarios")
	public String getComentarios() {
		return comentarios;
	}
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
	
	
	
}
