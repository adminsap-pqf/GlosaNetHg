package org.mx.modelo.registros.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Feriados_Oficiales")
public class FeriadosOficialesPO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1302885527791445019L;
	private Integer idFeriados;
	private String nombre;
	private Date fecha;
	private Integer periodo;
	
	@Id 
	@GeneratedValue
	@Column(name = "PK_Feriados", unique = true, nullable = false)
	public Integer getIdFeriados() {
		return idFeriados;
	}
	public void setIdFeriados(Integer idFeriados) {
		this.idFeriados = idFeriados;
	}
	
	@Column(name = "Nombre")
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Column(name = "Fecha")
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	@Column(name = "Periodo")
	public Integer getPeriodo() {
		return periodo;
	}
	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}
	
	
}
