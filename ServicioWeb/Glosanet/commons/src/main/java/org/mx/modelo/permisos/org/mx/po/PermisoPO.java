package org.mx.modelo.permisos.org.mx.po;

import org.mx.modelo.comun.po.PrevisionesPO;
import org.mx.modelo.comun.po.ProductoPO;
import org.mx.modelo.comun.po.UsuariosPO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name="Permisos")
public class PermisoPO implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1207439124217961356L;
	private int idPermisos;
	private Date viegnciaPermiso;
	private String permiso;
	private Date fechaInicio;
	private Date fechaFin;
	private String folioRegistro;
	private Integer cantidad;
	private String unidad;
	private String tramiteCofepris;
	private boolean terminado; 
	private Set<DupPermisoEventoPO> eventos;
	private UsuariosPO usuario;
	private ProductoPO producto;
	private int idEmpresa;
	private Date citaCofepris;
	private PrevisionesPO prevision;
	
	private Date fechaCita;
	private Date fecha;
	
	private PermisoHistorialCofeprisPO historialCofepris;
	
	public PermisoPO(){
		
	}
	
	@Id 
	@GeneratedValue
	@Column(name = "PK_Permisos", unique = true, nullable = false)
	public int getIdPermisos() {
		return idPermisos;
	}

	public void setIdPermisos(int idPermisos) {
		this.idPermisos = idPermisos;
	}

	@Column(name="Vigencia_Permiso")
	public Date getViegnciaPermiso() {
		return viegnciaPermiso;
	}

	public void setViegnciaPermiso(Date viegnciaPermiso) {
		this.viegnciaPermiso = viegnciaPermiso;
	}

	@Column(name="Permiso")
	public String getPermiso() {
		return permiso;
	}

	public void setPermiso(String permiso) {
		this.permiso = permiso;
	}

	@Column(name="FechaInicio")
	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	@Column(name="FechaFin")
	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	@Column(name="FolioRegistro")
	public String getFolioRegistro() {
		return folioRegistro;
	}

	public void setFolioRegistro(String folioRegistro) {
		this.folioRegistro = folioRegistro;
	}

	@Column(name="Cantidad")
	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	@Column(name="Unidad")
	public String getUnidad() {
		return unidad;
	}

	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}

	@Column(name="TramiteCofepris")
	public String getTramiteCofepris() {
		return tramiteCofepris;
	}

	public void setTramiteCofepris(String tramiteCofepris) {
		this.tramiteCofepris = tramiteCofepris;
	}

	@Column(name="Terminado")
	public boolean isTerminado() {
		return terminado;
	}

	public void setTerminado(boolean terminado) {
		this.terminado = terminado;
	}

	@OneToMany(fetch = FetchType.EAGER,  mappedBy = "permiso")
	@OrderBy("FechaRealizacion ASC")
	public Set<DupPermisoEventoPO> getEventos() {
		return eventos;
	}

	public void setEventos(Set<DupPermisoEventoPO> eventos) {
		this.eventos = eventos;
	}

	@ManyToOne
	@JoinColumn(name="FK03_Usuario", nullable = false)
	public UsuariosPO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuariosPO usuario) {
		this.usuario = usuario;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="FK01_Producto", nullable = false)
	public ProductoPO getProducto() {
		return producto;
	}

	public void setProducto(ProductoPO producto) {
		this.producto = producto;
	}

	@Column(name = "FK04_Empresa")
	public int getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(int idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	@ManyToOne
	@JoinColumn(name = "FK05_historialCofepris")
	public PermisoHistorialCofeprisPO getHistorialCofepris() {
		return historialCofepris;
	}

	public void setHistorialCofepris(PermisoHistorialCofeprisPO historialCofepris) {
		this.historialCofepris = historialCofepris;
	}

	@Column(name="Cita_COFEPRIS")
	public Date getCitaCofepris() {
		return citaCofepris;
	}

	public void setCitaCofepris(Date citaCofepris) {
		this.citaCofepris = citaCofepris;
	}

	@ManyToOne
	@JoinColumn(name="FK06_Prevision", nullable = false)
	public PrevisionesPO getPrevision() {
		return prevision;
	}

	public void setPrevision(PrevisionesPO prevision) {
		this.prevision = prevision;
	}

	@Column(name="Fecha_Cita")
	public Date getFechaCita() {
		return fechaCita;
	}

	public void setFechaCita(Date fechaCita) {
		this.fechaCita = fechaCita;
	}

	@Column(name="Fecha")
	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	
	
	
	
}
