package org.mx.modelo.comun.po;

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

import org.mx.modelo.comun.po.PEventoPO;


@Entity
@Table(name = "Prevision_Notificacion")
public class PNotificacionPO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -87552605375437182L;
	private Integer idNotificacion;
//	private Integer idEvento;
	private Date finicio;
	private int cantPeriodo;
	private String tipoPeriodo;
	private String recurrencia;
	private boolean terminado;
	private PEventoPO pevento;
	
	

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
	public Date getFinicio() {
		return finicio;
	}

	public void setFinicio(Date finicio) {
		this.finicio = finicio;
	}

	@Column(name = "CantPeriodo")
	public int getCantPeriodo() {
		return cantPeriodo;
	}

	public void setCantPeriodo(int cantPeriodo) {
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

	@Column(name = "Recurrencia")
	public String getRecurrencia() {
		return recurrencia;
	}

	public void setRecurrencia(String recurrencia) {
		this.recurrencia = recurrencia;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FK01_Evento")
	public PEventoPO getPevento() {
		return pevento;
	}

	public void setPevento(PEventoPO pevento) {
		this.pevento = pevento;
	}
}
