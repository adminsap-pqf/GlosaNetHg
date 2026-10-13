/**
 * 
 */
package org.mx.modelo.comun.bo;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "DescargaBO")
public class DescargaBO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7836629588992201234L;
	private Integer idDescarga;
	private String sqlConsulta;
	private String encabezados;
	private Integer idUsuario;
	private String tipo;
	private List<String> locacionTemp;
	private Boolean descargado;
	
	private String locacion;
	
	/**
	 * @param idDescarga
	 * @param sqlConsulta
	 * @param locacionTemp
	 */
	public DescargaBO(Integer idDescarga, String sqlConsulta,
			List<String> locacionTemp, Integer idUsuario) {
		super();
		this.idDescarga = idDescarga;
		this.sqlConsulta = sqlConsulta;
		this.locacionTemp = locacionTemp;
		this.idUsuario = idUsuario;
	}
	
	public DescargaBO( ){
		super();
	}
	public DescargaBO(String sqlConsulta, String encabezados, Integer idUsuario, String tipo, String locacion ){
		super();
		this.sqlConsulta = sqlConsulta;
		this.encabezados = encabezados;
		this.idUsuario = idUsuario;
		this.tipo = tipo;
		this.locacion = locacion;
	}
	public DescargaBO(Integer idDescarga, List<String> locacionTemp, String encabezados){
		super();
		this.idDescarga = idDescarga;
		this.locacionTemp = locacionTemp;
		this.encabezados = encabezados;
	}
	
	/**
	 * @return the idDescarga
	 */
	public Integer getIdDescarga() {
		return idDescarga;
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
	public String getSqlConsulta() {
		return sqlConsulta;
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
	public List<String> getLocacionTemp() {
		return locacionTemp;
	}
	/**
	 * @param locacionTemp the locacionTemp to set
	 */
	public void setLocacionTemp(List<String> locacionTemp) {
		this.locacionTemp = locacionTemp;
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
	 * @return the encabezados
	 */
	public String getEncabezados() {
		return encabezados;
	}

	/**
	 * @param encabezados the encabezados to set
	 */
	public void setEncabezados(String encabezados) {
		this.encabezados = encabezados;
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
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the descargado
	 */
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
	 * @return the locacion
	 */
	public String getLocacion() {
		return locacion;
	}

	/**
	 * @param locacion the locacion to set
	 */
	public void setLocacion(String locacion) {
		this.locacion = locacion;
	}
	
	
	
}
