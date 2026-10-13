/**
 * 
 */
package org.mx.modelo.reportes.bo;

/**
 * @author viviana.romero
 *
 */
public class CampoTablaBO {
	
	private Integer idCampo;
	private String nombre;
	private String descripcion;
	private TablaDataStageBO tabla;
	
	
	public CampoTablaBO(){
		super();
	}
	
	/**
	 * @param idCampo
	 * @param nombre
	 * @param idTabla
	 */
	public CampoTablaBO(Integer idCampo, String nombre) {
		super();
		this.idCampo = idCampo;
		this.nombre = nombre;
	}


	/**
	 * @return the idCampo
	 */
	public Integer getIdCampo() {
		return idCampo;
	}


	/**
	 * @param idCampo the idCampo to set
	 */
	public void setIdCampo(Integer idCampo) {
		this.idCampo = idCampo;
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
	 * @return the tabla
	 */
	public TablaDataStageBO getTabla() {
		return tabla;
	}


	/**
	 * @param tabla the tabla to set
	 */
	public void setTabla(TablaDataStageBO tabla) {
		this.tabla = tabla;
	}

	
	

}
