/**
 * 
 */
package org.mx.modelo.datastage.bo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "PedimentoBO")
public class PedimentoBO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5765164420883010063L;
	private Integer anio;
	private Integer numeroPedimentos;
	/**
	 * 
	 */
	public PedimentoBO() {
		super();
	}
	/**
	 * @return the anio
	 */
	public Integer getAnio() {
		return anio;
	}
	/**
	 * @param anio the anio to set
	 */
	public void setAnio(Integer anio) {
		this.anio = anio;
	}
	/**
	 * @return the numeroPedimentos
	 */
	public Integer getNumeroPedimentos() {
		return numeroPedimentos;
	}
	/**
	 * @param numeroPedimentos the numeroPedimentos to set
	 */
	public void setNumeroPedimentos(Integer numeroPedimentos) {
		this.numeroPedimentos = numeroPedimentos;
	}
	
	

}
