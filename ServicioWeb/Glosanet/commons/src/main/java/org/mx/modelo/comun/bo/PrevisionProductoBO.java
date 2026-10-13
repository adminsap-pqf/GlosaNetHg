package org.mx.modelo.comun.bo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "PrevisionProductoBO")
public class PrevisionProductoBO implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2156737356967784208L;
	private Integer idPrevisionProducto;
	private Integer prevision;
	private Integer idProducto;
	private String cantidad;
	private String unidad;
	
	public PrevisionProductoBO() {
		super();
	}

	public Integer getIdPrevisionProducto() {
		return idPrevisionProducto;
	}

	public void setIdPrevisionProducto(Integer idPrevisionProducto) {
		this.idPrevisionProducto = idPrevisionProducto;
	}


	public Integer getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	public String getUnidad() {
		return unidad;
	}

	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}

	public Integer getPrevision() {
		return prevision;
	}

	public void setPrevision(Integer prevision) {
		this.prevision = prevision;
	}
	
	
}
