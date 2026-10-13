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

/**
 * @author yahir.nunez
 *
 */
@Entity
@Table(name = "GN_Paquete")
public class PaquetePO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3610888716316260422L;
	private Integer pkPaquete;
	private String nombre;
	private String tipo;
	private Integer pedimentos;
	private Integer usuarios;
	private Double costoAnual;
	private Double costoSemestral;
	private Double costoMensual;
	private Double costoConfiguracion;
	/**
	 * 
	 */
	public PaquetePO() {
		super();
	}
	/**
	 * @return the pkPaquete
	 */
	@Id
	@GeneratedValue
	@Column(name = "PK_Paquete", unique = true, nullable = false)
	public Integer getPkPaquete() {
		return pkPaquete;
	}
	/**
	 * @param pkPaquete the pkPaquete to set
	 */
	public void setPkPaquete(Integer pkPaquete) {
		this.pkPaquete = pkPaquete;
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
	 * @return the tipo
	 */
	@Column(name = "Tipo")
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
	 * @return the pedimentos
	 */
	@Column(name = "Pedimentos")
	public Integer getPedimentos() {
		return pedimentos;
	}
	/**
	 * @param pedimentos the pedimentos to set
	 */
	public void setPedimentos(Integer pedimentos) {
		this.pedimentos = pedimentos;
	}
	/**
	 * @return the usuarios
	 */
	@Column(name = "Usuarios")
	public Integer getUsuarios() {
		return usuarios;
	}
	/**
	 * @param usuarios the usuarios to set
	 */
	public void setUsuarios(Integer usuarios) {
		this.usuarios = usuarios;
	}
	/**
	 * @return the costoAnual
	 */
	@Column(name = "Costo_Anual")
	public Double getCostoAnual() {
		return costoAnual;
	}
	/**
	 * @param costoAnual the costoAnual to set
	 */
	public void setCostoAnual(Double costoAnual) {
		this.costoAnual = costoAnual;
	}
	/**
	 * @return the costoSemestral
	 */
	@Column(name = "Corto_Semestral")
	public Double getCostoSemestral() {
		return costoSemestral;
	}
	/**
	 * @param costoSemestral the costoSemestral to set
	 */
	public void setCostoSemestral(Double costoSemestral) {
		this.costoSemestral = costoSemestral;
	}
	/**
	 * @return the costoMensual
	 */
	@Column(name = "Costo_Mensual")
	public Double getCostoMensual() {
		return costoMensual;
	}
	/**
	 * @param costoMensual the costoMensual to set
	 */
	public void setCostoMensual(Double costoMensual) {
		this.costoMensual = costoMensual;
	}
	/**
	 * @return the costoConfiguracion
	 */
	@Column(name = "Costo_ConfiguracionInicial")
	public Double getCostoConfiguracion() {
		return costoConfiguracion;
	}
	/**
	 * @param costoConfiguracion the costoConfiguracion to set
	 */
	public void setCostoConfiguracion(Double costoConfiguracion) {
		this.costoConfiguracion = costoConfiguracion;
	}
	
	

}
