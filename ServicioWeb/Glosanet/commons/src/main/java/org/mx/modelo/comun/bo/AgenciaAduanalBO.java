/**
 * 
 */
package org.mx.modelo.comun.bo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "AgenciaAduanalBO")
public class AgenciaAduanalBO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7879955818773801802L;
	private Integer idAgenciaAduanal;
	private String rfc;
	private String razonSocial;
	private String nombre;
	private String telefono;
	private String correoElectronico;
	private DireccionFiscalBO direccion;
	private AgenteAduanalBO agente;
	/**
	 * @param idAgenciaAduanal
	 * @param razonSocial
	 * @param nombre
	 * @param telefono
	 * @param correoElectronico
	 * @param direccion
	 */
	public AgenciaAduanalBO(Integer idAgenciaAduanal,String rfc, String razonSocial,
			String nombre, String telefono, String correoElectronico,
			DireccionFiscalBO direccion,AgenteAduanalBO agente) {
		super();
		this.idAgenciaAduanal = idAgenciaAduanal;
		this.rfc=rfc;
		this.razonSocial = razonSocial;
		this.nombre = nombre;
		this.telefono = telefono;
		this.correoElectronico = correoElectronico;
		this.direccion = direccion;
		this.agente=agente;
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

	public AgenciaAduanalBO(){
		
	}
	/**
	 * @return the agente
	 */
	public AgenteAduanalBO getAgente() {
		return agente;
	}
	/**
	 * @param agente the agente to set
	 */
	public void setAgente(AgenteAduanalBO agente) {
		this.agente = agente;
	}
	/**
	 * @return the idAgenciaAduanal
	 */
	public Integer getIdAgenciaAduanal() {
		return idAgenciaAduanal;
	}
	/**
	 * @param idAgenciaAduanal the idAgenciaAduanal to set
	 */
	public void setIdAgenciaAduanal(Integer idAgenciaAduanal) {
		this.idAgenciaAduanal = idAgenciaAduanal;
	}
	/**
	 * @return the razonSocial
	 */
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
	
	

}
