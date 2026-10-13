/**
 * 
 */
package org.mx.modelo.comun.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

/**
 * @author viviana.romero
 *
 */
@Entity
@Table(name = "GN_Descargas")
public class DescargaPO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7690560341155772025L;

	private Integer idDescarga;
	private String sqlConsulta;
	private String locacionTemp;
	private Integer idUsuario;
	private String tipo;
	private Boolean descargado;
	private String encabezados;
	

	/**
	 * 
	 */
	public DescargaPO() {
	}
	
	/**
	 * @param idDescarga
	 * @param sqlConsulta
	 * @param locacionTemp
	 * @param idUsuario
	 */
	public DescargaPO(Integer idDescarga, String sqlConsulta,
			String locacionTemp, Integer idUsuario, String tipo) {
		super();
		this.idDescarga = idDescarga;
		this.sqlConsulta = sqlConsulta;
		this.locacionTemp = locacionTemp;
		this.idUsuario = idUsuario;
		this.tipo = tipo;
	}

	/**
	 * @param sqlConsulta
	 * @param idUsuario
	 */
	public DescargaPO(String sqlConsulta, Integer idUsuario) {
		super();
		this.sqlConsulta = sqlConsulta;
		this.idUsuario = idUsuario;
	}

	/**
	 * @return the idDescarga
	 */
	@Id
	@GeneratedValue
	@Column(name = "PK_Descargas", unique = true, nullable = false)
	public Integer getIdDescarga() {
		return this.idDescarga;
	}
	/**
	 * @param idDescarga the idDescarga to set
	 */
	public void setIdDescarga(Integer idDescarga) {
		this.idDescarga = idDescarga;
	}
	/**
	 * @return the sqlConsulta
	 */
	@Column(name = "SqlConsulta")
	@Type(type="text")
	public String getSqlConsulta() {
		return this.sqlConsulta;
	}
	/**
	 * @param sqlConsulta the sqlConsulta to set
	 */
	public void setSqlConsulta(String sqlConsulta) {
		this.sqlConsulta = sqlConsulta;
	}
	/**
	 * @return the locacionTemp
	 */
	@Column(name = "LocacionTemp")
	@Type(type="text")
	public String getLocacionTemp() {
		return this.locacionTemp;
	}
	/**
	 * @param locacionTemp the locacionTemp to set
	 */
	public void setLocacionTemp(String locacionTemp) {
		this.locacionTemp = locacionTemp;
	}
	/**
	 * @return the idUsuario
	 */
	@Column(name = "FK01_Usuario")
	public Integer getIdUsuario() {
		return this.idUsuario;
	}
	/**
	 * @param idUsuario the idUsuario to set
	 */
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	@Column(name = "Tipo")
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the descargado
	 */
	@Column(name = "Descargado")
	public Boolean getDescargado() {
		return descargado;
	}

	/**
	 * @param descargado the descargado to set
	 */
	public void setDescargado(Boolean descargado) {
		this.descargado = descargado;
	}

	/**
	 * @return the encabezados
	 */
	@Column(name = "Encabezados")
	public String getEncabezados() {
		return encabezados;
	}

	/**
	 * @param encabezados the encabezados to set
	 */
	public void setEncabezados(String encabezados) {
		this.encabezados = encabezados;
	}
	
}
