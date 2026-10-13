package org.mx.modelo.comun.bo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ProductoBO")
public class ProductoBO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8386643550393771172L;
	
	private Integer idProducto;
	private Integer idEmpresa;
	private String nombre;
	private String origen;
	private boolean registro;
	private boolean permiso;
	private String codigo;
	private String descripcion;
	
	private String permiso2;
	private String vigencia;
	private int tipoProducto;

	private int numRegistros;
	private int numPermisos;

	private String cantidad;
	private String unidad;
	private String totalProducto;
	
	private String presentacion;
	private Integer idPrevisionProducto;
	private Integer idPrevision;

	private Integer recordIndex;
	
	public ProductoBO() {
		super();
	}

	
	
	
	public int getTipoProducto() {
		return tipoProducto;
	}
	public void setTipoProducto(int tipoProducto) {
		this.tipoProducto = tipoProducto;
	}
	public String getPermiso2() {
		return permiso2;
	}
	public void setPermiso2(String permiso2) {
		this.permiso2 = permiso2;
	}
	public String getVigencia() {
		return vigencia;
	}
	public void setVigencia(String vigencia) {
		this.vigencia = vigencia;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Integer getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}
	public Integer getIdEmpresa() {
		return idEmpresa;
	}
	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getOrigen() {
		return origen;
	}
	public void setOrigen(String origen) {
		this.origen = origen;
	}
	public boolean isRegistro() {
		return registro;
	}
	public void setRegistro(boolean registro) {
		this.registro = registro;
	}
	public boolean isPermiso() {
		return permiso;
	}
	public void setPermiso(boolean permiso) {
		this.permiso = permiso;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public int getNumRegistros() {
		return numRegistros;
	}
	public void setNumRegistros(int numRegistros) {
		this.numRegistros = numRegistros;
	}
	public int getNumPermisos() { return numPermisos; }
	public void setNumPermisos(int numPermisos) { this.numPermisos = numPermisos; }
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
	public String getPresentacion() {
		return presentacion;
	}
	public void setPresentacion(String presentacion) {
		this.presentacion = presentacion;
	}
	public Integer getIdPrevisionProducto() {
		return idPrevisionProducto;
	}
	public void setIdPrevisionProducto(Integer idPrevisionProducto) {
		this.idPrevisionProducto = idPrevisionProducto;
	}
	public Integer getIdPrevision() {
		return idPrevision;
	}
	public void setIdPrevision(Integer idPrevision) {
		this.idPrevision = idPrevision;
	}
	public String getTotalProducto() {
		return totalProducto;
	}
	public void setTotalProducto(String totalProducto) {
		this.totalProducto = totalProducto;
	}

	public Integer getRecordIndex() { return recordIndex; }
	public void setRecordIndex(Integer recordIndex) { this.recordIndex = recordIndex; }
}
