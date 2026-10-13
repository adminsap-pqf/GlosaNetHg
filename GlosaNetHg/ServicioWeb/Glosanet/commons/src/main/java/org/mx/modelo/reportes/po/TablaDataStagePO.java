package org.mx.modelo.reportes.po;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "GN_TablaDataStage")
public class TablaDataStagePO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6055145711651930357L;
	
	private Integer pk_Tabla;
	private String nombre;
	private String descripcion;
	private Integer numero; 
	private Boolean nivelPartida;
	private String descripcionEn;
	private String idioma;
	private Set<CampoTablaPO> campos;
	

	
	public TablaDataStagePO() {
		campos = new HashSet<CampoTablaPO>();
	}
	
	/**
	 * @param pk_Tabla
	 * @param nombre
	 * @param descripcion
	 * @param campos
	 */
	public TablaDataStagePO(Integer pk_Tabla, String nombre, String descripcionEn,
			String descripcion, Set<CampoTablaPO> campos) {
		super();
		this.pk_Tabla = pk_Tabla;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.descripcionEn = descripcionEn;
		this.campos = campos;
	}



	@Id 
	@GeneratedValue
	@Column(name = "PK_TablasDataStage", unique = false, nullable = false)
	public Integer getPk_Tabla() {
		return pk_Tabla;
	}
	/**
	 * @param pk_Tabla the pk_Tabla to set
	 */
	public void setPk_Tabla(Integer pk_Tabla) {
		this.pk_Tabla = pk_Tabla;
	}
	/**
	 * @return the nombre
	 */
	@Column(name = "Nombre_Tabla",  length = 55)
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
	 * @return the numero
	 */
	@Column(name = "Numero",  length = 5)
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
	@Column(name = "NivelPartida",  length = 1)
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
	 * @return the Campos
	 */
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "tabla")
	@OrderBy("pk_campo ASC")
	public Set<CampoTablaPO> getCampos() {
		return campos;
	}

	/**
	 * @param empresas the Campos to set
	 */
	public void setCampos(Set<CampoTablaPO> campos) {
		this.campos = campos;
	}



	/**
	 * @return the descripcionEn
	 */
	@Column(name = "Descripcion_En",  length = 55)
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
