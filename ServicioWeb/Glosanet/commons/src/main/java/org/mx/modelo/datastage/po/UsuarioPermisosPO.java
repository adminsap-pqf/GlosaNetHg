package org.mx.modelo.datastage.po;

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
@Table(name = "GN_Usuario_Permisos")
public class UsuarioPermisosPO implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7241429826467940149L;
	private Integer idUsuarioPermiso;
	private Integer idUsuario;
	private PermisosPO permiso;
	private boolean activo;
	
	@Id
	@GeneratedValue
	@Column(name = "PK_Usuario_Permiso", unique = true, nullable = false)
	public Integer getIdUsuarioPermiso() {
		return idUsuarioPermiso;
	}
	public void setIdUsuarioPermiso(Integer idUsusarioPermiso) {
		this.idUsuarioPermiso = idUsusarioPermiso;
	}
	
	@Column(name = "FK01_Usuario")
	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	@ManyToOne(fetch = FetchType.EAGER )
	@JoinColumn(name = "FK02_Permiso")
	public PermisosPO getPermiso() {
		return permiso;
	}
	public void setPermiso(PermisosPO permiso) {
		this.permiso = permiso;
	}
	
	@Column(name = "Activo")
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}

}
