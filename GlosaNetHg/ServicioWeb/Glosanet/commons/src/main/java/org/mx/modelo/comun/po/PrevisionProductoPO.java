package org.mx.modelo.comun.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Prevision_Producto")
public class PrevisionProductoPO implements Serializable{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 4643874451061795070L;
	private Integer idPrevisionProducto;
	private Integer prevision;
	private Integer idProducto;
	private String cantidad;
	private String unidad;
	
	@Id
    @GeneratedValue
    @Column(name = "PK_Prevision_Producto", unique = true, nullable = false)
	public Integer getIdPrevisionProducto() {
		return idPrevisionProducto;
	}
	public void setIdPrevisionProducto(Integer idPrevisionProducto) {
		this.idPrevisionProducto = idPrevisionProducto;
	}
	
//	
//	public PrevisionesPO getPrevision() {
//		return prevision;
//	}
//	public void setPrevision(PrevisionesPO prevision) {
//		this.prevision = prevision;
//	}
	
	@Column(name = "FK01_Producto")
	public Integer getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}
	
	@Column(name = "Cantidad")
	public String getCantidad() {
		return cantidad;
	}
	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}
	
	@Column(name = "unidad")
	public String getUnidad() {
		return unidad;
	}
	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}
	
	@Column(name = "FK02_Prevision")
	public Integer getPrevision() {
		return prevision;
	}
	public void setPrevision(Integer prevision) {
		this.prevision = prevision;
	}
	
	
	
}
