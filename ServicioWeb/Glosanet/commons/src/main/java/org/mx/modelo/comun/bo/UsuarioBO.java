/**
 * 
 */
package org.mx.modelo.comun.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.mx.modelo.datastage.bo.UsuarioPermisosBO;


/**
 * @author yosimar.mendez
 *
 */
public class UsuarioBO {

	private Integer idUsuario;
	private String usuario;
	private String password;
	private ClientesBO cliente;
	private String permiso;
	private boolean habilitado;
	private Date fcreacion;
	
	private String nombre;
	private String apellido;
	
	private String email;
	private String telefono;
	private String ext;
	private List<UsuarioPermisosBO> permisos; 
	private byte[] imagenUsuario;
	private String extImagenUsuario;
	private boolean admin; 
	private boolean sinImagen;
	
	private boolean consola;
	
	private String contrasenaNueva;
	
	/**
	 * 
	 */
	public UsuarioBO() {
		super();
		permisos = new ArrayList<UsuarioPermisosBO>();
	}

	/**
	 * @param idUsuario
	 * @param usuario
	 * @param password
	 * @param empresa
	 * @param permiso
	 */
	public UsuarioBO(Integer idUsuario, String usuario, String password,
			ClientesBO empresa, String permiso) {
		super();
		this.idUsuario = idUsuario;
		this.usuario = usuario;
		this.password = password;
		this.cliente = empresa;
		this.permiso = permiso;
	}

	/**
	 * @return the idUsuario
	 */
	public Integer getIdUsuario() {
		return idUsuario;
	}

	/**
	 * @param idUsuario the idUsuario to set
	 */
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * @return the usuario
	 */
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
	 * @return the permiso
	 */
	public String getPermiso() {
		return permiso;
	}

	/**
	 * @param permiso the permiso to set
	 */
	public void setPermiso(String permiso) {
		this.permiso = permiso;
	}

	/**
	 * @return the habilitado
	 */
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
	public ClientesBO getCliente() {
		return cliente;
	}

	/**
	 * @param cliente the cliente to set
	 */
	public void setCliente(ClientesBO cliente) {
		this.cliente = cliente;
	}

	/**
	 * @return the fcreacion
	 */
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
	 * @return the email
	 */
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
	 * @return the permisos
	 */
	public List<UsuarioPermisosBO> getPermisos() {
		return permisos;
	}

	/**
	 * @param permisos the permisos to set
	 */
	public void setPermisos(List<UsuarioPermisosBO> permisos) {
		this.permisos = permisos;
	}

	/**
	 * @return the admin
	 */
	public boolean isAdmin() {
		return admin;
	}

	/**
	 * @param admin the admin to set
	 */
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	/**
	 * @return the imagenUsuario
	 */
	public byte[] getImagenUsuario() {
		return imagenUsuario;
	}

	/**
	 * @param imagenUsuario the imagenUsuario to set
	 */
	public void setImagenUsuario(byte[] imagenUsuario) {
		this.imagenUsuario = imagenUsuario;
	}

	/**
	 * @return the extImagenUsuario
	 */
	public String getExtImagenUsuario() {
		return extImagenUsuario;
	}

	/**
	 * @param extImagenUsuario the extImagenUsuario to set
	 */
	public void setExtImagenUsuario(String extImagenUsuario) {
		this.extImagenUsuario = extImagenUsuario;
	}

	/**
	 * @return the sinImagen
	 */
	public boolean isSinImagen() {
		return sinImagen;
	}

	/**
	 * @param sinImagen the sinImagen to set
	 */
	public void setSinImagen(boolean sinImagen) {
		this.sinImagen = sinImagen;
	}


	/**
	 * @return the consola
	 */
	public boolean isConsola() {
		return consola;
	}

	/**
	 * @param consola the consola to set
	 */
	public void setConsola(boolean consola) {
		this.consola = consola;
	}

	public String getContrasenaNueva() {
		return contrasenaNueva;
	}

	public void setContrasenaNueva(String contrasenaNueva) {
		this.contrasenaNueva = contrasenaNueva;
	}
	
	

}
