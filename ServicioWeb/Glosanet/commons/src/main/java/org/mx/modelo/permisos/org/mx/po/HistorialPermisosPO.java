package org.mx.modelo.permisos.org.mx.po;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.mx.modelo.comun.po.ProductoPO;
import org.mx.modelo.comun.po.UsuariosPO;

@Entity
@Table(name="Historial_Permisos")
public class HistorialPermisosPO implements Serializable{

	/**
	 * 
	 */

	private static final long serialVersionUID = -2679708072475403260L;
		private int idHistorialPermisos;
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
		
		private PermisoHistorialCofeprisPO historialCofepris;
		
		
		
		public HistorialPermisosPO(){
			
		}
		
		/**
		 * 
		 * @return
		 */
		
		/**
		 * 
		 * @return
		 */
		@Column(name="Vigencia_Permiso")
		public Date getViegnciaPermiso() {
			return viegnciaPermiso;
		}
		/**
		 * 
		 * @param viegnciaPermiso
		 */
		public void setViegnciaPermiso(Date viegnciaPermiso) {
			this.viegnciaPermiso = viegnciaPermiso;
		}
		/**
		 * 
		 * @return
		 */
		@Column(name="Permiso")
		public String getPermiso() {
			return permiso;
		}
		/**
		 * 
		 * @param permiso
		 */
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
		
//		@Column(name="FK03_Usuario", nullable=false)
//		public int getFkUsuario() {
//			return fkUsuario;
//		}
//
//		public void setFkUsuario(int fkUsuario) {
//			this.fkUsuario = fkUsuario;
//		}
		
		@Column(name = "FK04_Empresa")
		public Integer getIdEmpresa() {
			return idEmpresa;
		}

		public void setIdEmpresa(Integer idEmpresa) {
			this.idEmpresa = idEmpresa;
		}

		
		@Id 
		@GeneratedValue
		@Column(name = "PK_Historial_Permisos", unique = true, nullable = false)
		public int getIdHistorialPermisos() {
			return idHistorialPermisos;
		}

		public void setIdHistorialPermisos(int idHistorialPermisos) {
			this.idHistorialPermisos = idHistorialPermisos;
		}
		
		@ManyToOne
		@JoinColumn(name = "FK05_historialCofepris")
		public PermisoHistorialCofeprisPO getHistorialCofepris() {
			return historialCofepris;
		}

		public void setHistorialCofepris(PermisoHistorialCofeprisPO historialCofepris) {
			this.historialCofepris = historialCofepris;
		}
		
		@Column(name="FechaFin")
		public Date getFechaFin() {
			return fechaFin;
		}

		public void setFechaFin(Date fechaFin) {
			this.fechaFin = fechaFin;
		}
		
		
	
}
