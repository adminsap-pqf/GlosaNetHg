package org.mx.modelo.reportes.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "GN_Columna_TablaDataStage")
public class CampoTablaPO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1091529648492313974L;
	private Integer pk_campo;
	private String nombre;
	private String descripcion;
	private String descripcionEn;
	private String idioma;
	private TablaDataStagePO tabla;
	
	public CampoTablaPO(){
		super();
	}
	
	/**
	 * @param pk_campo
	 * @param nombre
	 * @param descripcion
	 * @param fk01_tabla
	 * @param tabla
	 */
	public CampoTablaPO(Integer pk_campo, String nombre, String descripcion, String descripcionEn,
			 TablaDataStagePO tabla) {
		super();
		this.pk_campo = pk_campo;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.descripcionEn =(descripcionEn);
		this.tabla = tabla;
	}

	/**
	 * @return the pk_campo
	 */
	@Id 
	@GeneratedValue
	@Column(name = "PK_Columnas", unique = false, nullable = false)
	public Integer getPk_campo() {
		return pk_campo;
	}

	/**
	 * @param pk_campo the pk_campo to set
	 */
	public void setPk_campo(Integer pk_campo) {
		this.pk_campo = pk_campo;
	}

	/**
	 * @return the nombre
	 */
	@Column(name = "Nombre_Columna",  length = 55)
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
	@Column(name = "Descripcion",  length = 55)
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
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FK01_TablaDataStage", nullable = false)
	public TablaDataStagePO getTabla() {
		return tabla;
	}

	/**
	 * @param tabla the tabla to set
	 */
	public void setTabla(TablaDataStagePO tabla) {
		this.tabla = tabla;
	}

	/**
	 * @return the descripcionEn
	 */
	@Column(name="Descripcion_En")
	public String getDescripcionEn() {
		return descripcionEn;
	}

	/**
	 * @param descripcionEn the descripcionEn to set
	 */
	public void setDescripcionEn(String descripcionEn) {
		this.descripcionEn = descripcionEn;
	}

	/**
	 * @return the idioma
	 */
	@Transient
	public String getIdioma() {
		return idioma;
	}

	/**
	 * @param idioma the idioma to set
	 */
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

}
