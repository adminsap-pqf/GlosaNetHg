package org.mx.modelo.permisos.org.mx.po;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;




@Entity
@Table(name = "Permiso_Evento")
public class DupPermisoEventoPO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6643680762808339342L;
	private PermisoPO permiso;  
	private  Integer idEvento;
	private String nombre;
	private boolean definido;
	private  String cantPeriodo;
	private String tipoPeriodo;
	private boolean terminado;
	private Date fechaRealizada;
	private  Date fechaTerminada;
	private Set<PermisoNotificacionPO> notificaciones;
	private Set<PermisoCorreoEventoPO> correos;
	
	
	
	
	
	
	@Id 
	@GeneratedValue
	@Column(name = "PK_Evento", unique = true, nullable = false)
	public Integer getIdEvento() {
		return idEvento;
	}
	public void setIdEvento(Integer idEvento) {
		this.idEvento = idEvento;
	}
	
	@Column(name = "Nombre")
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	@Column(name = "Definido")
	public boolean isDefinido() {
		return definido;
	}
	public void setDefinido(boolean definido) {
		this.definido = definido;
	}
	@Column(name = "CantPeriodo")
	public String getCantPeriodo() {
		return cantPeriodo;
	}
	public void setCantPeriodo(String cantPeriodo) {
		this.cantPeriodo = cantPeriodo;
	}
	@Column(name = "TipoPeriodo")
	public String getTipoPeriodo() {
		return tipoPeriodo;
	}
	public void setTipoPeriodo(String tipoPeriodo) {
		this.tipoPeriodo = tipoPeriodo;
	}
	@Column(name = "Terminado")
	public boolean isTerminado() {
		return terminado;
	}
	public void setTerminado(boolean terminado) {
		this.terminado = terminado;
	}
	@Column(name = "FechaRealizacion")
	public Date getFechaRealizada() {
		return fechaRealizada;
	}
	public void setFechaRealizada(Date fechaRealizada) {
		this.fechaRealizada = fechaRealizada;
	}
	@Column(name = "FechaTerminado")
	public Date getFechaTerminada() {
		return fechaTerminada;
	}
	public void setFechaTerminada(Date fechaTerminada) {
		this.fechaTerminada = fechaTerminada;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FK01_Permiso")
	public PermisoPO getPermiso() {
		return permiso;
	}

	public void setPermiso(PermisoPO permiso) {
		this.permiso = permiso;
	}
	
	@OneToMany(fetch = FetchType.EAGER,  mappedBy = "permiso")
	@OrderBy("FechaInicio Desc")
	public Set<PermisoNotificacionPO> getNotificaciones() {
		return notificaciones;
	}
	
	public void setNotificaciones(Set<PermisoNotificacionPO> notificaciones) {
		this.notificaciones = notificaciones;
	}
	
	@OneToMany(fetch = FetchType.EAGER,  mappedBy = "permiso")
	public Set<PermisoCorreoEventoPO> getCorreos() {
		return correos;
	}
	
	public void setCorreos(Set<PermisoCorreoEventoPO> correos) {
		this.correos = correos;
	}
	
	
	
	
	
	
}
