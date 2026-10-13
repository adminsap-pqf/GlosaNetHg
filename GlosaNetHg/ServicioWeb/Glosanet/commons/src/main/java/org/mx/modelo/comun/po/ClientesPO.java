package org.mx.modelo.comun.po;

import java.util.Date;
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
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * Clientes entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "GN_Cliente")
public class ClientesPO implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -4610792342044758412L;
	private Integer pkCliente;
	private String nombre;
	private String representante;
	private Date fcreacion;
	private Date fua;
	
	
	
	private String rfc;
	private String razonSocial;
	private Boolean habilitado;
	
	
	private PaquetePO paquete;
	private Set<EmpresaPO> empresas;
	private Set<UsuariosPO> usuarios;
	
	private String telefono;
	private Date fAuditbale;
	private String regimenFiscal;
	private String usoCFDI;
	private String  claveProducto;
	

	// Constructors

	/** default constructor */
	public ClientesPO() {
		empresas = new HashSet<EmpresaPO>();
		usuarios = new HashSet<UsuariosPO>();
	}

	/** minimal constructor */
	public ClientesPO(Integer pkCliente, String nombre, String representante) {
		this.pkCliente = pkCliente;
		this.nombre = nombre;
		this.representante = representante;
	}

	

	// Property accessors
	@Id 
	@GeneratedValue
	@Column(name = "PK_Cliente", unique = true, nullable = false)
	public Integer getPkCliente() {
		return this.pkCliente;
	}

	public void setPkCliente(Integer pkCliente) {
		this.pkCliente = pkCliente;
	}

	@Column(name = "Nombre", nullable = false)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return the empresas
	 */
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "cliente")
	@OrderBy("razonSocial ASC")
	public Set<EmpresaPO> getEmpresas() {
		return empresas;
	}

	/**
	 * @param empresas the empresas to set
	 */
	public void setEmpresas(Set<EmpresaPO> empresas) {
		this.empresas = empresas;
	}
	
	

	/**
	 * @return the usuarios
	 */
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "cliente")
	public Set<UsuariosPO> getUsuarios() {
		return usuarios;
	}

	/**
	 * @param usuarios the usuarios to set
	 */
	public void setUsuarios(Set<UsuariosPO> usuarios) {
		this.usuarios = usuarios;
	}

	/**
	 * @return the representante
	 */
	@Column(name = "Representante")
	public String getRepresentante() {
		return representante;
	}

	/**
	 * @param representante the representante to set
	 */
	public void setRepresentante(String representante) {
		this.representante = representante;
	}

	/**
	 * @return the fcreacion
	 */
	@Column(name = "FCreacion")
	public Date getFcreacion() {
		return fcreacion;
	}

	/**
	 * @param fcreacion the fcreacion to set
	 */
	public void setFcreacion(Date fcreacion) {
		this.fcreacion = fcreacion;
	}

	/**
	 * @return the fua
	 */
	@Column(name = "FUA")
	public Date getFua() {
		return fua;
	}

	/**
	 * @param fua the fua to set
	 */
	public void setFua(Date fua) {
		this.fua = fua;
	}


	

	/**
	 * @return the rfc
	 */
	@Column(name = "RFC")
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
	@Column(name = "RazonSocial")
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
	 * @return the habilitado
	 */
	@Column(name = "Habilitado")
	public Boolean getHabilitado() {
		return habilitado;
	}

	/**
	 * @param habilitado the habilitado to set
	 */
	public void setHabilitado(Boolean habilitado) {
		this.habilitado = habilitado;
	}



	/**
	 * @return the paquete
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="FK01_Paquete")
	public PaquetePO getPaquete() {
		return paquete;
	}

	/**
	 * @param paquete the paquete to set
	 */
	public void setPaquete(PaquetePO paquete) {
		this.paquete = paquete;
	}

	@Column(name = "Telefono")
	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@Column(name = "FAuditable")
	public Date getfAuditbale() {
		return fAuditbale;
	}

	public void setfAuditbale(Date fAuditbale) {
		this.fAuditbale = fAuditbale;
	}

	@Column(name = "RegimenFiscal")
	public String getRegimenFiscal() {
		return regimenFiscal;
	}

	public void setRegimenFiscal(String regimenFiscal) {
		this.regimenFiscal = regimenFiscal;
	}

	@Column(name = "UsoCFDI")
	public String getUsoCFDI() {
		return usoCFDI;
	}

	public void setUsoCFDI(String usoCFDI) {
		this.usoCFDI = usoCFDI;
	}

	@Column(name = "claveProducto")
	public String getClaveProducto() {
		return claveProducto;
	}

	public void setClaveProducto(String claveProducto) {
		this.claveProducto = claveProducto;
	}
	
	

}