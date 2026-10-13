package org.mx.modelo.registros.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Registro_HistorialEvento")
public class HistorialEventoPO implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 315330571285858040L;
	private Integer idHistorial;
	private Integer idUsuario;
	private Integer idEvento;
	private Date fecha;
	private String comentarios;
	
	
	@Id 
	@GeneratedValue
	@Column(name = "PK_Historial", unique = true, nullable = false)
	public Integer getIdHistorial() {
		return idHistorial;
	}
	public void setIdHistorial(Integer idHistorial) {
		this.idHistorial = idHistorial;
	}
	
	@Column(name = "FK01_Usuario")
	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	@Column(name = "Fecha")
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	@Column(name = "Comentarios")
	public String getComentarios() {
		return comentarios;
	}
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
	
	@Column(name = "FK02_Evento")
	public Integer getIdEvento() {
		return idEvento;
	}
	public void setIdEvento(Integer idEvento) {
		this.idEvento = idEvento;
	}

}
