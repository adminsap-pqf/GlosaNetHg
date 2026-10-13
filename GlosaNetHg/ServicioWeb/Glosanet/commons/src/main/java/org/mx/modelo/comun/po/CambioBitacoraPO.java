package org.mx.modelo.comun.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PF_CambiosBitacora")
public class CambioBitacoraPO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7413172848017212234L;

	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="PK_CambioBitacora")
	private int idCambioBitacora;
	
	@Column(name="Fecha")
	private Date fecha;
	
	@Column(name="DescripcionES")
	private String descripcion;
	
	@Column(name="FK01_Usuario") 
	private int usuario;
	
	
	public CambioBitacoraPO(){}

	public int getIdCambioBitacora() {
		return idCambioBitacora;
	}

	public void setIdCambioBitacora(int idCambioBitacora) {
		this.idCambioBitacora = idCambioBitacora;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public int getUsuario() {
		return usuario;
	}

	public void setUsuario(int usuario) {
		this.usuario = usuario;
	}
	
}
