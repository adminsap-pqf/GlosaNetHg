package org.mx.modelo.comun.po;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Producto")
public class ProductoNumRegPO implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -1364462390807676453L;

	private Integer numRegistros;
	private Integer numPermisos;
    private Integer idProducto;
	private Integer idEmpresa;
	private String nombre;
	private String origen;
	private boolean registro;
	private boolean permiso;
	private String codigo;
	private Integer tipoProducto;
	private String presentacion;
	private Integer recordIndex;

	public ProductoNumRegPO() {
		super();
	}


	public ProductoNumRegPO(Integer idProducto, Integer idEmpresa, String nombre,
                            String origen, boolean registro, boolean permiso) {
		super();
		this.idProducto = idProducto;
		this.idEmpresa = idEmpresa;
		this.nombre = nombre;
		this.origen = origen;
		this.registro = registro;
		this.permiso = permiso;
	}


	@Id 
	@GeneratedValue
	@Column(name = "PK_Producto", unique = true, nullable = false)
	public Integer getIdProducto() {
		return idProducto;
	}


	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}


//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "PK_Empresa", nullable = false)
	@Column(name = "FK01_Empresa")
	public Integer getIdEmpresa() {
		return idEmpresa;
	}


	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	@Column(name = "Nombre")
	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "Origen")
	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	@Column(name = "Registro")
	public boolean isRegistro() {
		return registro;
	}
	public void setRegistro(boolean registro) {
		this.registro = registro;
	}

	@Column(name = "Permiso")
	public boolean isPermiso() {
		return permiso;
	}
	public void setPermiso(boolean permiso) {
		this.permiso = permiso;
	}

	@Column(name = "Codigo")
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@Column(name = "Presentacion")
	public String getPresentacion() {
		return presentacion;
	}
	public void setPresentacion(String presentacion) {
		this.presentacion = presentacion;
	}

	@Column(name = "FK02_Tipo_Producto")
	public Integer getTipoProducto() {
		return tipoProducto;
	}
	public void setTipoProducto(Integer tipoProducto) {
		this.tipoProducto = tipoProducto;
	}

	@Column(name = "NumRegistros")
	public Integer getNumRegistros() { return numRegistros; }
	public void setNumRegistros(Integer numRegistros) { this.numRegistros = numRegistros; }

	@Column(name = "NumPermisos")
	public Integer getNumPermisos() { return numPermisos; }
	public void setNumPermisos(Integer numPermisos) { this.numPermisos = numPermisos; }

	@Column(name = "RecordIndex")
	public Integer getRecordIndex() { return recordIndex; }
	public void setRecordIndex(Integer recordIndex) { this.recordIndex = recordIndex; }
}
