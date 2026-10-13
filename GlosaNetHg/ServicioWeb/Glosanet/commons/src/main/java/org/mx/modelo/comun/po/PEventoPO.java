package org.mx.modelo.comun.po;

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
import javax.persistence.Table;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.mx.modelo.comun.po.PNotificacionPO;

@Entity
@Table(name = "Prevision_Evento")
public class PEventoPO implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 788398738463423976L;
	private Integer idEvento;
	private String nombre;
	private boolean definido;
	private int cantPeriodo;
	private String tipoPeriodo;
	
	private Date frealizacion;
	private PrevisionesPO prevision;
	private Set<PNotificacionPO> notificaciones;
	private Set<PCorreoEventoPO> correos;
	
	private Date fterminado;
	private boolean terminado;

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

	@Column(name = "Definido") //default
	public boolean isDefinido() {
		return definido;
	}


	public void setDefinido(boolean definido) {
		this.definido = definido;
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

	@Column(name = "FechaRealizacion")
	public Date getFrealizacion() {
		return frealizacion;
	}


	public void setFrealizacion(Date frealizacion) {
		this.frealizacion = frealizacion;
	}

	

	@OneToMany(fetch = FetchType.EAGER,  mappedBy = "pevento")
	@OrderBy("FechaInicio Desc")
	public Set<PNotificacionPO> getNotificaciones() {
		return notificaciones;
	}


	public void setNotificaciones(Set<PNotificacionPO> notificaciones) {
		this.notificaciones = notificaciones;
	}


	@OneToMany(fetch = FetchType.EAGER,  mappedBy = "evento")
	public Set<PCorreoEventoPO> getCorreos() {
		return correos;
	}


	public void setCorreos(Set<PCorreoEventoPO> correos) {
		this.correos = correos;
	}

	
	@Column(name = "FechaTerminado")
	public Date getFterminado() {
		return fterminado;
	}


	public void setFterminado(Date fterminado) {
		this.fterminado = fterminado;
	}


	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FK01_Prevision")
	public PrevisionesPO getPrevision() {
		return prevision;
	}


	public void setPrevision(PrevisionesPO prevision) {
		this.prevision = prevision;
	}
}
