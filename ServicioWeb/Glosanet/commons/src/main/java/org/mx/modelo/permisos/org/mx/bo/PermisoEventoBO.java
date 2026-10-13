package org.mx.modelo.permisos.org.mx.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.mx.modelo.registros.bo.RegistroBO;

public class PermisoEventoBO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6297610721262537272L;
	private Integer idEvento;
//	private Integer idRegistro;
	private String nombre;
	private boolean definido;
	private int cantPeriodo;
	private String tipoPeriodo;
	private boolean terminado;
	private Date frealizacion;
	private List<PermisoNotificacionBO> notificaciones;
	private List<PermisoCorreoEventoBO> correos;
	private RegistroBO registro;
	private Date fterminado;
	
	private PermisoBO permiso;
	
	private int totalHistorial;
	
	public PermisoEventoBO(){
		notificaciones = new ArrayList<PermisoNotificacionBO>();
		correos = new ArrayList<PermisoCorreoEventoBO>();
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

	public List<PermisoNotificacionBO> getNotificaciones() {
		return notificaciones;
	}

	public void setNotificaciones(List<PermisoNotificacionBO> notificaciones) {
		this.notificaciones = notificaciones;
	}

	public List<PermisoCorreoEventoBO> getCorreos() {
		return correos;
	}

	public void setCorreos(List<PermisoCorreoEventoBO> correos) {
		this.correos = correos;
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




	public PermisoBO getPermiso() {
		return permiso;
	}




	public void setPermiso(PermisoBO permiso) {
		this.permiso = permiso;
	}
	
	
	
	
	
	
}
