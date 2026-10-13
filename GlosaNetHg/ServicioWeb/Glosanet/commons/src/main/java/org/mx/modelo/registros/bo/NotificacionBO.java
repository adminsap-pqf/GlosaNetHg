package org.mx.modelo.registros.bo;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "NotificacionBO")
public class NotificacionBO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1828097721540250121L;
	private Integer idNotificacion;
	private Integer idEvento;
	private Date finicio;
	private int cantPeriodo;
	private String tipoPeriodo;
	private String recurrencia;
	private boolean terminado;
	
	
	public NotificacionBO() {
		super();
	}
	
	public NotificacionBO(Integer idNotificacion, Integer idEvento,
			Date finicio, int cantPeriodo, String tipoPeriodo, String recurrencia, boolean terminado) {
		super();
		this.idNotificacion = idNotificacion;
		this.idEvento = idEvento;
		this.finicio = finicio;
		this.cantPeriodo = cantPeriodo;
		this.tipoPeriodo = tipoPeriodo;
		this.recurrencia = recurrencia;
		this.terminado = terminado;
	}
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
