/**
 * 
 */
package org.mx.modelo.datastage.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author yosimar.mendez
 *
 */
@Entity
@Table(name = "GN_Permisos")
public class PermisosPO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8283696218426192212L;
	private Integer pkPermiso;
	private String permiso;
	
	
	/**
	 * 
	 */
	public PermisosPO() {
		super();
	}

	/**
	 * @param pkPermiso
	 * @param permiso
	 */
	public PermisosPO(Integer pkPermiso, String permiso) {
		super();
		this.pkPermiso = pkPermiso;
		this.permiso = permiso;
	}

	/**
	 * @return the pkPermiso
	 */
	@Id
	@GeneratedValue
	@Column(name = "PK_Permiso", unique = true, nullable = false)
	public Integer getPkPermiso() {
		return pkPermiso;
	}

	/**
	 * @param pkPermiso the pkPermiso to set
	 */
	public void setPkPermiso(Integer pkPermiso) {
		this.pkPermiso = pkPermiso;
	}

	/**
	 * @return the permiso
	 */
	@Column(name = "Permiso", length = 75, nullable = false)
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
