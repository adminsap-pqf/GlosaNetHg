package org.mx.modelo.permisos.org.mx.po;

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
@Table(name = "Permiso_CorreoEvento")
public class PermisoCorreoEventoPO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4859888615378528105L;
	private Integer idcorreo;
	private String correo;
	private DupPermisoEventoPO permiso;
	
	
	
	@Id 
	@GeneratedValue
	@Column(name = "PK_Correo", unique = true, nullable = false)
	public Integer getIdcorreo() {
		return idcorreo;
	}
	public void setIdcorreo(Integer idcorreo) {
		this.idcorreo = idcorreo;
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
	public DupPermisoEventoPO getPermiso() {
		return permiso;
	}
	public void setPermiso(DupPermisoEventoPO permiso) {
		this.permiso = permiso;
	}
	
	
	
	
}
