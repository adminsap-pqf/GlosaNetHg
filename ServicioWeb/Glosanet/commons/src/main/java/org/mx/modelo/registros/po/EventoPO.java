package org.mx.modelo.registros.po;

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
@Table(name = "Registro_Evento")
public class EventoPO implements Serializable {
	private static final long serialVersionUID = 6544185105389229842L;
	
	private Integer idEvento;
	private String nombre;
	private boolean definido;
	private int cantPeriodo;
	private String tipoPeriodo;
	
	private Date frealizacion;
	private RegistroPO registro;
	private String tramiteCofepris; 
	private Set<NotificacionPO> notificaciones;
	private Set<CorreoEventoPO> correos;
	
	private Date fterminado;
	private boolean terminado;
	
	private boolean tramite;
	private Date fechaTramite;

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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FK01_Registro")
	public RegistroPO getRegistro() {
		return registro;
	}
	public void setRegistro(RegistroPO registro) {
		this.registro = registro;
	}

	@OneToMany(fetch = FetchType.EAGER,  mappedBy = "evento")
	@OrderBy("FechaInicio Desc")
	public Set<NotificacionPO> getNotificaciones() {
		return notificaciones;
	}
	public void setNotificaciones(Set<NotificacionPO> notificaciones) {
		this.notificaciones = notificaciones;
	}

	@OneToMany(fetch = FetchType.EAGER,  mappedBy = "evento")
	public Set<CorreoEventoPO> getCorreos() {
		return correos;
	}
	public void setCorreos(Set<CorreoEventoPO> correos) {
		this.correos = correos;
	}

	@Column(name = "FechaTerminado")
	public Date getFterminado() {
		return fterminado;
	}
	public void setFterminado(Date fterminado) {
		this.fterminado = fterminado;
	}

	@Column(name = "TramiteCofepris")
	public String getTramiteCofepris() {
		return tramiteCofepris;
	}
	public void setTramiteCofepris(String tramiteCofepris) {
		this.tramiteCofepris = tramiteCofepris;
	}

	@Column(name = "Tramite")
	public boolean isTramite() {
		return tramite;
	}
	public void setTramite(boolean tramite) {
		this.tramite = tramite;
	}

	@Column(name = "FechaTramite")
	public Date getFechaTramite() {
		return fechaTramite;
	}
	public void setFechaTramite(Date fechaTramite) {
		this.fechaTramite = fechaTramite;
	}
}
