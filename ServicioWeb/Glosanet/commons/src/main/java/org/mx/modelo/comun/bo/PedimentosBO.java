package org.mx.modelo.comun.bo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "PedimentosBO")
public class PedimentosBO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5343602459082671861L;
	private Integer idPedimentos;
	private Integer idCliente;
	private String numeroPedimentos;
	private String periodo;
	
	
	public Integer getIdPedimentos() {
		return idPedimentos;
	}
	public void setIdPedimentos(Integer idPedimentos) {
		this.idPedimentos = idPedimentos;
	}
	public Integer getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}
	public String getNumeroPedimentos() {
		return numeroPedimentos;
	}
	public void setNumeroPedimentos(String numeroPedimentos) {
		this.numeroPedimentos = numeroPedimentos;
	}
	public String getPeriodo() {
		return periodo;
	}
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	
	
}
