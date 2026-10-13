/**
 * 
 */
package org.mx.modelo.comun.po;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.mx.modelo.datastage.po.UsuarioPermisosPO;

/**
 * @author yosimar.mendez
 *
 */
@Entity
@Table(name = "GN_Usuarios")
public class UsuariosPO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9136687389410091848L;

	private Integer pkUsuario;
	private String usuario;
	private String password;
	private ClientesPO cliente;
	private String permisos;
	private boolean habilitado;
	private Timestamp fcreacion;
	
	private String nombre;
	private String apellido;
	
	
	private String email;
	private String telefono;
	private String ext;
	private List<UsuarioPermisosPO> lstPermisos; 
	public boolean consola;
	
	
	
	/**
	 * 
	 */
	public UsuariosPO() {
		super();
	}

	
	public UsuariosPO(Integer pkUsuario) {
		super();
		this.pkUsuario = pkUsuario;
	}
	
	/**
	 * @param pkUsuario
	 * @param usuario
	 * @param password
	 * @param cliente
	 * @param permisos
	 * @param habilitado
	 */
	public UsuariosPO(Integer pkUsuario, String usuario, String password,
					  ClientesPO cliente, UnusedPermisosPO permisos, boolean habilitado) {
		super();
		this.pkUsuario = pkUsuario;
		this.usuario = usuario;
		this.password = password;
		this.cliente = cliente;
//		this.permisos = permisos;
		this.habilitado = habilitado;
	}


	/**
	 * @return the pkUsuario
	 */
	@Id
	@GeneratedValue
	@Column(name = "PK_Usuario", unique = true, nullable = false)
	public Integer getPkUsuario() {
		return pkUsuario;
	}

	/**
	 * @param pkUsuario the pkUsuario to set
	 */
	public void setPkUsuario(Integer pkUsuario) {
		this.pkUsuario = pkUsuario;
	}

	/**
	 * @return the usuario
	 */
	@Column(name = "usuario", length = 65, nullable = false)
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the password
	 */
	@Column(name = "password", length = 18, nullable = false)
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the permisos
	 */
//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name = "FK02_Permiso", nullable = false)
//	public PermisosPO getPermisos() {
//		return permisos;
//	}
//
//	/**
//	 * @param permisos the permisos to set
//	 */
//	public void setPermisos(PermisosPO permisos) {
//		this.permisos = permisos;
//	}

	/**
	 * @return the habilitado
	 */
	@Column(name = "Habilitado", nullable = false)
	public boolean isHabilitado() {
		return habilitado;
	}

	/**
	 * @param habilitado the habilitado to set
	 */
	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}

	/**
	 * @return the cliente
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FK01_Cliente", nullable = false)
	public ClientesPO getCliente() {
		return cliente;
	}

	/**
	 * @param cliente the cliente to set
	 */
	public void setCliente(ClientesPO cliente) {
		this.cliente = cliente;
	}

	/**
	 * @return the fcreacion
	 */
	@Column(name = "fcreacion")
	public Timestamp getFcreacion() {
		return fcreacion;
	}

	/**
	 * @param fcreacion the fcreacion to set
	 */
	public void setFcreacion(Timestamp fcreacion) {
		this.fcreacion = fcreacion;
	}


	/**
	 * @return the nombre
	 */
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
	 * @return the apellido
	 */
	@Column(name = "Apellido")
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
	 * @return the email
	 */
	@Column(name = "Email")
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the telefono
	 */
	@Column(name = "Telefono")
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
	 * @return the ext
	 */
	@Column(name = "Ext")
	public String getExt() {
		return ext;
	}

	/**
	 * @param ext the ext to set
	 */
	public void setExt(String ext) {
		this.ext = ext;
	}

	/**
	 * @return the lstPermisos
	 */
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "idUsuario")
//	@JoinTable(name = "GN_Usuario_Permisos", 
//	joinColumns = {@JoinColumn(name = "FK01_Usuario", referencedColumnName = "PK_Usuario")})
	public List<UsuarioPermisosPO> getLstPermisos() {
		return lstPermisos;
	}

	/**
	 * @param lstPermisos the lstPermisos to set
	 */
	public void setLstPermisos(List<UsuarioPermisosPO> lstPermisos) {
		this.lstPermisos = lstPermisos;
	}




	/**
	 * @return the consola
	 */
	@Column(name = "Consola", nullable = false)
	public boolean isConsola() {
		return consola;
	}


	/**
	 * @param consol the consola to set
	 */
	public void setConsola(boolean consol) {
		consola = consol;
	}


	@Column(name = "Permiso", nullable = false)
	public String getPermisos() {
		return permisos;
	}


	public void setPermisos(String permisos) {
		this.permisos = permisos;
	}
	
	
	
	

}
