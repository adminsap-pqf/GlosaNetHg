/**
 * 
 */
package org.mx.modelo.comun.bo;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "DofBO")
public class DofBO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8225476825966324345L;
	private int idDof;
	private Date dof;
	
	/**
	 * 
	 */
	public DofBO() {
		super();
	}

	/**
	 * @param idDof
	 * @param dof
	 */
	public DofBO(int idDof, Date dof) {
		super();
		this.idDof = idDof;
		this.dof = dof;
	}

	/**
	 * @return the idDof
	 */
	public int getIdDof() {
		return idDof;
	}

	/**
	 * @param idDof the idDof to set
	 */
	public void setIdDof(int idDof) {
		this.idDof = idDof;
	}

	/**
	 * @return the dof
	 */
	public Date getDof() {
		return dof;
	}

	/**
	 * @param dof the dof to set
	 */
	public void setDof(Date dof) {
		this.dof = dof;
	}
}
