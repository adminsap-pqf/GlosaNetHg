package org.mx.modelo.permisos.org.mx.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Permiso_Notificacion")
public class PermisoNotificacionPO implements Serializable {
	 /**
	 * 
	 */
	 private static final long serialVersionUID = 1111856550485739124L;
	 private Integer idNotificacion;
	 private Date fechaInicio;
	 private Integer cantPeriodo;
	 private String tipoPerido;
	 private boolean terminado;
	 private String recurrencia;
	 private DupPermisoEventoPO permiso;
	 
	 
	 
	@Id 
	@GeneratedValue
	@Column(name = "PK_Notificacion", unique = true, nullable = false)
	public Integer getIdNotificacion() {
		return idNotificacion;
	}
	public void setIdNotificacion(Integer idNotificacion) {
		this.idNotificacion = idNotificacion;
	}
	@Column(name = "FechaInicio")
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	@Column(name = "CantPeriodo")
	public Integer getCantPeriodo() {
		return cantPeriodo;
	}
	public void setCantPeriodo(Integer cantPeriodo) {
		this.cantPeriodo = cantPeriodo;
	}
	@Column(name = "TipoPeriodo")
	public String getTipoPerido() {
		return tipoPerido;
	}
	public void setTipoPerido(String tipoPerido) {
		this.tipoPerido = tipoPerido;
	}
	@Column(name = "Terminado")
	public boolean isTerminado() {
		return terminado;
	}
	public void setTerminado(boolean terminado) {
		this.terminado = terminado;
	}
	@Column(name = "Recurrencia")
	public String getRecurrencia() {
		return recurrencia;
	}
	public void setRecurrencia(String recurrencia) {
		this.recurrencia = recurrencia;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FK01_Evento")
	public DupPermisoEventoPO getPermiso() {
		return permiso;
	}
	public void setPermiso(DupPermisoEventoPO permiso) {
		this.permiso = permiso;
	}
	 
	 
	 
}
