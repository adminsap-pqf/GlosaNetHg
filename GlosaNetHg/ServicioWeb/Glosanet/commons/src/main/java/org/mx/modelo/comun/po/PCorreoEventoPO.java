package org.mx.modelo.comun.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.mx.modelo.comun.po.PEventoPO;

@Entity
@Table(name = "Prevision_CorreoEvento")
public class PCorreoEventoPO implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2743211289750022542L;
	private Integer idCorreo;
//	private Integer idEvento;
	private String correo;
	private PEventoPO evento;
	
	
	@Id 
	@GeneratedValue
	@Column(name = "PK_Correo", unique = true, nullable = false)
	public Integer getIdCorreo() {
		return idCorreo;
	}
	public void setIdCorreo(Integer idCorreo) {
		this.idCorreo = idCorreo;
	}
	
	@Column(name = "Correo")
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FK01_Evento")
	public PEventoPO getEvento() {
		return evento;
	}
	public void setEvento(PEventoPO evento) {
		this.evento = evento;
	}
}
