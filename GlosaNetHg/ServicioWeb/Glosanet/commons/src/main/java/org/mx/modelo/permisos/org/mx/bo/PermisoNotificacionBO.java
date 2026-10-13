package org.mx.modelo.permisos.org.mx.bo;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "PermisoNotificacionBO")
public class PermisoNotificacionBO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4181926297764533567L;
	private Integer idNotificacion;
	private Integer idEvento;
	private Date finicio;
	private int cantPeriodo;
	private String tipoPeriodo;	
	private boolean terminado;
	private String recurrencia;
	
	
	
	
	public Integer getIdNotificacion() {
		return idNotificacion;
	}
	public void setIdNotificacion(Integer idNotificacion) {
		this.idNotificacion = idNotificacion;
	}
	public Integer getIdEvento() {
		return idEvento;
	}
	public void setIdEvento(Integer idEvento) {
		this.idEvento = idEvento;
	}
	public Date getFinicio() {
		return finicio;
	}
	public void setFinicio(Date finicio) {
		this.finicio = finicio;
	}
	public int getCantPeriodo() {
		return cantPeriodo;
	}
	public void setCantPeriodo(int cantPeriodo) {
		this.cantPeriodo = cantPeriodo;
	}
	public String getTipoPeriodo() {
		return tipoPeriodo;
	}
	public void setTipoPeriodo(String tipoPeriodo) {
		this.tipoPeriodo = tipoPeriodo;
	}
	public boolean isTerminado() {
		return terminado;
	}
	public void setTerminado(boolean terminado) {
		this.terminado = terminado;
	}
	public String getRecurrencia() {
		return recurrencia;
	}
	public void setRecurrencia(String recurrencia) {
		this.recurrencia = recurrencia;
	}
	
	
	
	
	
	
	
}
