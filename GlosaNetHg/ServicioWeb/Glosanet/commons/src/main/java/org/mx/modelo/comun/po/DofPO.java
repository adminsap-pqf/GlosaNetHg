/**
 * 
 */
package org.mx.modelo.comun.po;

import java.io.Serializable;
import java.sql.Timestamp;

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
@Table(name = "GN_DOF")
public class DofPO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7729916288802443013L;
	
	private Integer pkDof;
	private Timestamp dof;
	
	/**
	 * 
	 */
	public DofPO() {
		super();
	}

	/**
	 * @param pkDof
	 * @param dof
	 */
	public DofPO(Integer pkDof, Timestamp dof) {
		super();
		this.pkDof = pkDof;
		this.dof = dof;
	}

	/**
	 * @return the pkDof
	 */
	@Id
	@GeneratedValue
	@Column(name = "PK_DOF", unique = true, nullable = false)
	public Integer getPkDof() {
		return pkDof;
	}

	/**
	 * @param pkDof the pkDof to set
	 */
	public void setPkDof(Integer pkDof) {
		this.pkDof = pkDof;
	}

	/**
	 * @return the dof
	 */
	@Column(name = "DOF", length = 23)
	public Timestamp getDof() {
		return dof;
	}

	/**
	 * @param dof the dof to set
	 */
	public void setDof(Timestamp dof) {
		this.dof = dof;
	}
	
	

}
