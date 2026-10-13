package org.mx.modelo.registros.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "EventoBO")
public class EventoBO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2920414180973312203L;
	
	private Integer idEvento;
//	private Integer idRegistro;
	private String nombre;
	private boolean definido;
	private int cantPeriodo;
	private String tipoPeriodo;
	private boolean terminado;
	private Date frealizacion;
	private String tramiteCofepris;
	private List<NotificacionBO> notificaciones;
	private List<CorreoEventoBO> correos;
	private RegistroBO registro;
	private Date fterminado;
	
	private int totalHistorial;
	private List<HistorialEventoBO> historialEvento;
	private boolean tramite;
	private Date fechaTramite;
	
	
	public EventoBO(){
		notificaciones = new ArrayList<NotificacionBO>();
		correos = new ArrayList<CorreoEventoBO>();
		
	}
	
	public Integer getIdEvento() {
		return idEvento;
	}
	public void setIdEvento(Integer idEvento) {
		this.idEvento = idEvento;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public boolean isDefinido() {
		return definido;
	}
	public void setDefinido(boolean definido) {
		this.definido = definido;
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
	public List<NotificacionBO> getNotificaciones() {
		return notificaciones;
	}
	public void setNotificaciones(List<NotificacionBO> notificaciones) {
		this.notificaciones = notificaciones;
	}
	public List<CorreoEventoBO> getCorreos() {
		return correos;
	}
	public void setCorreos(List<CorreoEventoBO> correos) {
		this.correos = correos;
	}
	public boolean isTerminado() {
		return terminado;
	}
	public void setTerminado(boolean terminado) {
		this.terminado = terminado;
	}
	public Date getFrealizacion() {
		return frealizacion;
	}
	public void setFrealizacion(Date frealizacion) {
		this.frealizacion = frealizacion;
	}

	public RegistroBO getRegistro() {
		return registro;
	}

	public void setRegistro(RegistroBO registro) {
		this.registro = registro;
	}


	public Date getFterminado() {
		return fterminado;
	}

	public void setFterminado(Date fterminado) {
		this.fterminado = fterminado;
	}

	public int getTotalHistorial() {
		return totalHistorial;
	}

	public void setTotalHistorial(int totalHistorial) {
		this.totalHistorial = totalHistorial;
	}

	public String getTramiteCofepris() {
		return tramiteCofepris;
	}

	public void setTramiteCofepris(String tramiteCofepris) {
		this.tramiteCofepris = tramiteCofepris;
	}

	public boolean isTramite() {
		return tramite;
	}

	public void setTramite(boolean tramite) {
		this.tramite = tramite;
	}

	public Date getFechaTramite() {
		return fechaTramite;
	}

	public void setFechaTramite(Date fechaTramite) {
		this.fechaTramite = fechaTramite;
	}

	public List<HistorialEventoBO> getHistorialEvento() {
		return historialEvento;
	}

	public void setHistorialEvento(List<HistorialEventoBO> historialEvento) {
		this.historialEvento = historialEvento;
	}
}
