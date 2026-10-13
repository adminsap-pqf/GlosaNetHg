package org.mx.modelo.reportes.bo;

import java.util.Date;
import java.util.List;

import org.mx.modelo.comun.bo.UsuarioBO;

public class ReportePersonalizadoBO {

	private Integer idReportePersonalizado;
	private String nombre;
	private String descripcion;
	private Date fechaCreacion;
	private UsuarioBO autor;
	private List<PReportesPersonalizadosBO> lstPReportesPersonalizados;
	
	public Integer getIdReportePersonalizado() {
		return idReportePersonalizado;
	}
	
	public void setIdReportePersonalizado(Integer idReportePersonalizado) {
		this.idReportePersonalizado = idReportePersonalizado;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	public UsuarioBO getAutor() {
		return autor;
	}
	
	public void setAutor(UsuarioBO autor) {
		this.autor = autor;
	}
	
	public List<PReportesPersonalizadosBO> getLstPReportesPersonalizados() {
		return lstPReportesPersonalizados;
	}
	
	public void setLstPReportesPersonalizados(
			List<PReportesPersonalizadosBO> lstPReportesPersonalizados) {
		this.lstPReportesPersonalizados = lstPReportesPersonalizados;
	}
	
}
