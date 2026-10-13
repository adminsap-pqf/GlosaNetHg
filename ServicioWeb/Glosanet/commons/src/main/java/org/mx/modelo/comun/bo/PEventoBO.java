package org.mx.modelo.comun.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import org.mx.modelo.comun.bo.PNotificacionBO;
import org.mx.modelo.comun.bo.PrevisionesBO;

public class PEventoBO {

	
	private Integer idEvento;
//	private Integer idRegistro;
	private String nombre;
	private boolean definido;
	private int cantPeriodo;
	private String tipoPeriodo;
	private boolean terminado;
	private Date frealizacion;
	private List<PNotificacionBO> notificaciones;
	private List<PCorreoEventoBO> correos;
	private PrevisionesBO prevision;
	private Date fterminado;
	
	private int totalHistorial;
	
	
	public PEventoBO(){
		notificaciones = new ArrayList<PNotificacionBO>();
		correos = new ArrayList<PCorreoEventoBO>();
		//correos = new ArrayList<CorreoEventoBO>();
		
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
	public List<PNotificacionBO> getNotificaciones() {
		return notificaciones;
	}
	public void setNotificaciones(List<PNotificacionBO> notificaciones) {
		this.notificaciones = notificaciones;
	}
	public List<PCorreoEventoBO> getCorreos() {
		return correos;
	}
	public void setCorreos(List<PCorreoEventoBO> correos) {
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

	public PrevisionesBO getPrevision() {
		return prevision;
	}

	public void setPrevision(PrevisionesBO prevision) {
		this.prevision = prevision;
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
}
