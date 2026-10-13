package org.mx.modelo.comun.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "GN_Pedimentos")
public class PedimentosPO implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7171600582454668490L;
	private Integer idPedimentos;
	private Integer idCliente;
	private String numeroPedimentos;
	private String periodo;
	
	@Id 
	@GeneratedValue
	@Column(name = "PK_Pedimentos", unique = true, nullable = false)
	public Integer getIdPedimentos() {
		return idPedimentos;
	}
	public void setIdPedimentos(Integer idPedimentos) {
		this.idPedimentos = idPedimentos;
	}
	
	@Column(name = "FK01_Cliente")
	public Integer getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}
	
	@Column(name = "NumeroPedimentos")
	public String getNumeroPedimentos() {
		return numeroPedimentos;
	}
	public void setNumeroPedimentos(String numeroPedimentos) {
		this.numeroPedimentos = numeroPedimentos;
	}
	
	@Column(name = "Periodo")
	public String getPeriodo() {
		return periodo;
	}
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	
	
}
