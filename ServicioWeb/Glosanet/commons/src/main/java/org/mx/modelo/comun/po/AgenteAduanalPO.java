/**
 * 
 */
package org.mx.modelo.comun.po;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;



/**
 * @author yahir.nunez
 *
 */
@Entity
@Table(name ="GN_Agente_Aduanal")
public class AgenteAduanalPO implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8166288827721189800L;
	private Integer pkAgente;
	private String numeroPatente;
	private String nombre;
	private String apellido;
	private String rfc;
	private String telefono;
	private String correoElectronico;
	private DireccionFiscalPO direccion;
	private Integer fkcliente;
	private Set<AgenciaAduanalPO> agencias;
	
	
	
	/**
	 * @param pkAgente
	 * @param nombre
	 * @param numeroPatente
	 */
	public AgenteAduanalPO(Integer pkAgente,String numeroPatente, String nombre,String apellido, String rfc, String telefono, String correoElectronico,
			DireccionFiscalPO direccion,Integer fkcliente,Set<AgenciaAduanalPO> agencias) {
		this.pkAgente = pkAgente;
		this.nombre = nombre;
		this.numeroPatente = numeroPatente;
		this.nombre = nombre;
		this.apellido=apellido;
		this.rfc=rfc;
		this.telefono=telefono;
		this.correoElectronico=correoElectronico;
		this.direccion=direccion;
		this.agencias=agencias;
		this.fkcliente=fkcliente;
	}
	
	public AgenteAduanalPO() {
		agencias = new HashSet<AgenciaAduanalPO>();
		
	}
	@Id 
	@GeneratedValue
	@Column(name = "PK_Agente", unique = true, nullable = false)
	public Integer getPkAgente() {
		return pkAgente;
	}
	/**
	 * @param pkAgente the pkAgente to set
	 */
	public void setPkAgente(Integer pkAgente) {
		this.pkAgente = pkAgente;
	}
	
	@Column(name = "Nombre")
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
	@Column(name="NumeroPatente", nullable=false)
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
	@Column(name="Apellido")
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

	/**
	 * @return the agencias
	 */
	@OneToMany(fetch = FetchType.EAGER,  mappedBy = "agente")
	public Set<AgenciaAduanalPO> getAgencias() {
		return agencias;
	}

	/**
	 * @param agencias the agencias to set
	 */
	public void setAgencias(Set<AgenciaAduanalPO> agencias) {
		this.agencias = agencias;
	}

	/**
	 * @return the fkcliente
	 */
	@Column(name="FK02_Cliente", nullable=false)
	public Integer getFkcliente() {
		return fkcliente;
	}

	/**
	 * @param fkcliente the fkcliente to set
	 */
	public void setFkcliente(Integer fkcliente) {
		this.fkcliente = fkcliente;
	}
	
	
	

}
