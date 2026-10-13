package org.mx.modelo.registros.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Registro_TiempoRealizacion")
public class TiempoRealizacionPO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5320746353244132263L;
	
	private Integer idTiempo;
	private Integer Ev1_cantidad;
	private String Ev1_tipo;
	private Integer Ev2_cantidad;
	private String Ev2_tipo;
	private Integer Ev3_cantidad;
	private String Ev3_tipo;
	private Integer idEmpresa;
	
	private Date fecha;
	private boolean activo;
	
	@Id 
	@GeneratedValue
	@Column(name = "PK_Tiempo", unique = true, nullable = false)
	public Integer getIdTiempo() {
		return idTiempo;
	}
	public void setIdTiempo(Integer idTiempo) {
		this.idTiempo = idTiempo;
	}
	
	@Column(name = "Ev1_Cant")
	public Integer getEv1_cantidad() {
		return Ev1_cantidad;
	}
	public void setEv1_cantidad(Integer ev1_cantidad) {
		Ev1_cantidad = ev1_cantidad;
	}
	
	@Column(name = "Ev1_Tipo")
	public String getEv1_tipo() {
		return Ev1_tipo;
	}
	public void setEv1_tipo(String ev1_tipo) {
		Ev1_tipo = ev1_tipo;
	}
	
	@Column(name = "Ev2_Cant")
	public Integer getEv2_cantidad() {
		return Ev2_cantidad;
	}
	public void setEv2_cantidad(Integer ev2_cantidad) {
		Ev2_cantidad = ev2_cantidad;
	}
	
	@Column(name = "Ev2_Tipo")
	public String getEv2_tipo() {
		return Ev2_tipo;
	}
	public void setEv2_tipo(String ev2_tipo) {
		Ev2_tipo = ev2_tipo;
	}
	
	@Column(name = "Ev3_Cant")
	public Integer getEv3_cantidad() {
		return Ev3_cantidad;
	}
	public void setEv3_cantidad(Integer ev3_cantidad) {
		Ev3_cantidad = ev3_cantidad;
	}
	
	@Column(name = "Ev3_Tipo")
	public String getEv3_tipo() {
		return Ev3_tipo;
	}
	public void setEv3_tipo(String ev3_tipo) {
		Ev3_tipo = ev3_tipo;
	}
	
	@Column(name = "Fecha")
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	@Column(name = "Activo")
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	@Column(name = "FK01_Empresa")
	public Integer getIdEmpresa() {
		return idEmpresa;
	}
	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

}
