/**
 * 
 */
package org.mx.modelo.reportes.bo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author viviana.romero
 *
 */
public class TablaDataStageBO {
	
	private Integer idTabla;
	private String nombre;
	private String descripcion;
	private Integer numero; 
	private Boolean nivelPartida;
	private List<CampoTablaBO> campos;

	
	public TablaDataStageBO(){
		super();
		campos = new ArrayList<CampoTablaBO>();
	}

	
	
	/**
	 * @param idTabla
	 * @param nombre
	 * @param descripcion
	 * @param campos
	 */
	public TablaDataStageBO(Integer idTabla, String nombre, String descripcion,
			List<CampoTablaBO> campos) {
		super();
		this.idTabla = idTabla;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.campos = campos;
	}



	/**
	 * @return the idTabla
	 */
	public Integer getIdTabla() {
		return idTabla;
	}

	/**
	 * @param idTabla the idTabla to set
	 */
	public void setIdTabla(Integer idTabla) {
		this.idTabla = idTabla;
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
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the numero
	 */
	public Integer getNumero() {
		return numero;
	}



	/**
	 * @param numero the numero to set
	 */
	public void setNumero(Integer numero) {
		this.numero = numero;
	}



	/**
	 * @return the nivelPartida
	 */
	public Boolean getNivelPartida() {
		return nivelPartida;
	}



	/**
	 * @param nivelPartida the nivelPartida to set
	 */
	public void setNivelPartida(Boolean nivelPartida) {
		this.nivelPartida = nivelPartida;
	}



	/**
	 * @return the campos
	 */
	public List<CampoTablaBO> getCampos() {
		return campos;
	}

	/**
	 * @param campos the campos to set
	 */
	public void setCampos(List<CampoTablaBO> campos) {
		this.campos = campos;
	}
	

}
