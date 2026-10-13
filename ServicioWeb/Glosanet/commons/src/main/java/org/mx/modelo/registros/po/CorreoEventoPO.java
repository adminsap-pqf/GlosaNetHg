package org.mx.modelo.registros.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Registro_CorreoEvento")
public class CorreoEventoPO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4589299380964944401L;
	
	private Integer idCorreo;
//	private Integer idEvento;
	private String correo;
	private EventoPO evento;
	
	
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
	public EventoPO getEvento() {
		return evento;
	}
	public void setEvento(EventoPO evento) {
		this.evento = evento;
	}

}
