/**
 * 
 */
package org.mx.modelo.comun.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author yahir.nunez
 *
 */
@Entity
@Table(name = "GN_Agencia_Aduanal")
public class AgenciaAduanalPO implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8392776530156346370L;
	private Integer pkAgencia;
	private String rfc;
	private String razonSocial;
	private String nombre;
	private String telefono;
	private String correoElectronico;
	private AgenteAduanalPO agente;
	private DireccionFiscalPO direccion;
	
	
	
	
	
	/**
	 * @param pkAgencia
	 * @param razonSocial
	 * @param nombre
	 * @param telefono
	 * @param correoElectronico
	 * @param agente
	 * @param direccion
	 */
	public AgenciaAduanalPO(Integer pkAgencia,String rfc, String razonSocial,
			String nombre, String telefono, String correoElectronico,
			AgenteAduanalPO agente, DireccionFiscalPO direccion) {
		super();
		this.pkAgencia = pkAgencia;
		this.rfc=rfc;
		this.razonSocial = razonSocial;
		this.nombre = nombre;
		this.telefono = telefono;
		this.correoElectronico = correoElectronico;
		this.agente = agente;
		this.direccion = direccion;
	}
	
	public AgenciaAduanalPO(){
		
	}
	/**
	 * @return the pkAgencia
	 */
	@Id
	@GeneratedValue
	@Column(name="PK_Agencia", unique = true, nullable = false)
	public Integer getPkAgencia() {
		return pkAgencia;
	}
	/**
	 * @param pkAgencia the pkAgencia to set
	 */
	public void setPkAgencia(Integer pkAgencia) {
		this.pkAgencia = pkAgencia;
	}
	/**
	 * @return the rfc
	 */
	@Column(name="Rfc")
	public String getRfc() {
		return rfc;
	}

	/**
	 * @param rfc the rfc to set
	 */
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	/**
	 * @return the razonSocial
	 */
	@Column(name="Razon_Social")
	public String getRazonSocial() {
		return razonSocial;
	}
	/**
	 * @param razonSocial the razonSocial to set
	 */
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	/**
	 * @return the nombre
	 */
	@Column(name="Nombre")
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return the telefono
	 */
	@Column(name="Telefono")
	public String getTelefono() {
		return telefono;
	}
	/**
	 * @param telefono the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	/**
	 * @return the correoElectronico
	 */
	@Column(name="Correo_Electronico")
	public String getCorreoElectronico() {
		return correoElectronico;
	}
	/**
	 * @param correoElectronico the correoElectronico to set
	 */
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}
	/**
	 * @return the agente
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FK02_Agente")
	public AgenteAduanalPO getAgente() {
		return agente;
	}
	/**
	 * @param agente the agente to set
	 */
	public void setAgente(AgenteAduanalPO agente) {
		this.agente = agente;
	}
	/**
	 * @return the direccion
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="FK01_Direccion", nullable = false)
	public DireccionFiscalPO getDireccion() {
		return direccion;
	}
	/**
	 * @param direccion the direccion to set
	 */
	public void setDireccion(DireccionFiscalPO direccion) {
		this.direccion = direccion;
	}

	
	
	
	

}
