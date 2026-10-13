package org.mx.modelo.reportes.po;

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

import org.mx.modelo.comun.po.UsuariosPO;

@Entity
@Table(name = "GN_Reportes_Personalizados")
public class ReportePersonalizadoPO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2254503873175314091L;
	private Integer idReportePersonalizado;
	private String nombre;
	private String descripcion;
	private Date fechaCreacion;
	private UsuariosPO autor;
//	private Set<PReportesPersonalizadosPO> lstPReportesPersonalizados;
	
	public ReportePersonalizadoPO() {
		super();
	}

	@Id 
	@GeneratedValue
	@Column(name="PK_ReportePersonalizado")	
	public Integer getIdReportePersonalizado() {
		return idReportePersonalizado;
	}
	
	public void setIdReportePersonalizado(Integer pk_ReportePersonalizado) {
		this.idReportePersonalizado = pk_ReportePersonalizado;
	}
	
	@Column(name = "Nombre")
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Column(name = "Descripcion")
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@Column(name = "Fecha_Creacion")
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FK01_Autor")
	public UsuariosPO getAutor() {
		return autor;
	}
	
	public void setAutor(UsuariosPO autor) {
		this.autor = autor;
	}

//	@OneToMany(fetch = FetchType.EAGER, mappedBy = "tabla")
//	@OrderBy("idRPReportesPersonalizados ASC")
//	public Set<PReportesPersonalizadosPO> getLstPReportesPersonalizados() {
//		return lstPReportesPersonalizados;
//	}
//
//	public void setLstPReportesPersonalizados(
//			Set<PReportesPersonalizadosPO> lstPReportesPersonalizados) {
//		this.lstPReportesPersonalizados = lstPReportesPersonalizados;
//	}
	
}
