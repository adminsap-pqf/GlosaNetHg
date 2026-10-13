/**
 * 
 */
package org.mx.modelo.datastage.bo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "PermisosBO")
public class PermisosBO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 612915391109012067L;
	private Integer idPermiso;
	private String permiso;
	
	/**
	 * 
	 */
	public PermisosBO() {
		super();
	}

	/**
	 * @param idPermiso
	 * @param permiso
	 */
	public PermisosBO(Integer idPermiso, String permiso) {
		super();
		this.idPermiso = idPermiso;
		this.permiso = permiso;
	}

	/**
	 * @return the idPermiso
	 */
	public Integer getIdPermiso() {
		return idPermiso;
	}

	/**
	 * @param idPermiso the idPermiso to set
	 */
	public void setIdPermiso(Integer idPermiso) {
		this.idPermiso = idPermiso;
	}

	/**
	 * @return the permiso
	 */
	public String getPermiso() {
		return permiso;
	}

	/**
	 * @param permiso the permiso to set
	 */
	public void setPermiso(String permiso) {
		this.permiso = permiso;
	}


}
