package org.mx.modelo.datastage.bo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.mx.modelo.datastage.bo.PermisosBO;


@XmlRootElement(name = "usuarioPermisosBO")
public class UsuarioPermisosBO implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -257250384478596321L;
	private Integer idUsuarioPermiso;
	private Integer idUsuario;
	private PermisosBO permiso;
	private boolean activo;
	
	
	public Integer getIdUsuarioPermiso() {
		return idUsuarioPermiso;
	}
	public void setIdUsuarioPermiso(Integer idUsusarioPermiso) {
		this.idUsuarioPermiso = idUsusarioPermiso;
	}
	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	public PermisosBO getPermiso() {
		return permiso;
	}
	public void setPermiso(PermisosBO permiso) {
		this.permiso = permiso;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	

}
