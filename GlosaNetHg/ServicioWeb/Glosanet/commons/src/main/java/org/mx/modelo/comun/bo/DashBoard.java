/**
 * 
 */
package org.mx.modelo.comun.bo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "DashBoard")
public class DashBoard implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8918517353229136955L;
	private String tipo;
	private Integer clave;
	private String claveStr;
	
	private Integer totalI;
	private Double totalD;
	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	/**
	 * @return the totalI
	 */
	public Integer getTotalI() {
		return totalI;
	}
	/**
	 * @param totalI the totalI to set
	 */
	public void setTotalI(Integer totalI) {
		this.totalI = totalI;
	}
	/**
	 * @return the totalD
	 */
	public Double getTotalD() {
		return totalD;
	}
	/**
	 * @param totalD the totalD to set
	 */
	public void setTotalD(Double totalD) {
		this.totalD = totalD;
	}
	/**
	 * @return the clave
	 */
	public Integer getClave() {
		return clave;
	}
	/**
	 * @param clave the clave to set
	 */
	public void setClave(Integer clave) {
		this.clave = clave;
	}
	/**
	 * @return the claveStr
	 */
	public String getClaveStr() {
		return claveStr;
	}
	/**
	 * @param claveStr the claveStr to set
	 */
	public void setClaveStr(String claveStr) {
		this.claveStr = claveStr;
	}
	
	
	

	
}
