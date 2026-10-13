/**
 * 
 */
package org.mx.modelo.comun.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "AgenteAduanalBO")
public class AgenteAduanalBO implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7424274270853645914L;
	private Integer idAgente;
	private String numeroPatente;
	private String nombre;
	private String apellido;
	private String rfc;
	private String telefono;
	private String correoElectronico;
	private DireccionFiscalBO direccion;
	private Integer idCliente;
	private List<AgenciaAduanalBO> agencias; 
	
	
//	/**
//	 * @param idAgente
//	 * @param nombre
//	 * @param numeroPatente
//	 */
//	public AgenteAduanalBO(Integer idAgente, Integer numeroPatente, String nombre, String apellido, String rfc, String telefono, String correoElectronico,
//			DireccionFiscalBO direccion,List<AgenciaAduanalBO> agencias) {
//		super();
//		this.idAgente = idAgente;
//		this.numeroPatente = numeroPatente;
//		this.nombre = nombre;
//		this.apellido=apellido;
//		this.rfc=rfc;
//		this.telefono=telefono;
//		this.correoElectronico=correoElectronico;
//		this.direccion=direccion;
//		this.agencias=agencias;
//	}

	
	/**
	 * 
	 */
	public AgenteAduanalBO() {
		agencias=new ArrayList<AgenciaAduanalBO>();
	}


	/**
	 * @param idAgente
	 * @param numeroPatente
	 * @param nombre
	 * @param apellido
	 * @param rfc
	 * @param telefono
	 * @param correoElectronico
	 * @param direccion
	 * @param cliente
	 * @param agencias
	 */
	public AgenteAduanalBO(Integer idAgente, String numeroPatente,
			String nombre, String apellido, String rfc, String telefono,
			String correoElectronico, DireccionFiscalBO direccion,Integer idCliente,
			List<AgenciaAduanalBO> agencias) {
		super();
		this.idAgente = idAgente;
		this.numeroPatente = numeroPatente;
		this.nombre = nombre;
		this.apellido = apellido;
		this.rfc = rfc;
		this.telefono = telefono;
		this.correoElectronico = correoElectronico;
		this.direccion = direccion;
		this.idCliente=idCliente;
		this.agencias = agencias;
	}


	/**
	 * @return the cliente
	 */
	public Integer getIdCliente() {
		return idCliente;
	}


	/**
	 * @param cliente the cliente to set
	 */
	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}


	/**
	 * @return the idAgente
	 */
	public Integer getIdAgente() {
		return idAgente;
	}


	/**
	 * @param idAgente the idAgente to set
	 */
	public void setIdAgente(Integer idAgente) {
		this.idAgente = idAgente;
	}


	/**
	 * @return the nombre
	 */
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
	 * @return the numeroPatente
	 */
	public String getNumeroPatente() {
		return numeroPatente;
	}


	/**
	 * @param numeroPatente the numeroPatente to set
	 */
	public void setNumeroPatente(String numeroPatente) {
		this.numeroPatente = numeroPatente;
	}


	/**
	 * @return the apellido
	 */
	public String getApellido() {
		return apellido;
	}


	/**
	 * @param apellido the apellido to set
	 */
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}


	/**
	 * @return the rfc
	 */
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
	 * @return the telefono
	 */
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
	 * @return the direccion
	 */
	public DireccionFiscalBO getDireccion() {
		return direccion;
	}


	/**
	 * @param direccion the direccion to set
	 */
	public void setDireccion(DireccionFiscalBO direccion) {
		this.direccion = direccion;
	}


	/**
	 * @return the agencias
	 */
	public List<AgenciaAduanalBO> getAgencias() {
		return agencias;
	}


	/**
	 * @param agencias the agencias to set
	 */
	public void setAgencias(List<AgenciaAduanalBO> agencias) {
		this.agencias = agencias;
	}
	
	
	
	

}
