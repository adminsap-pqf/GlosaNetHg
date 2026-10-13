package org.mx.modelo.permisos.org.mx.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.mx.modelo.comun.bo.PrevisionesBO;
import org.mx.modelo.comun.bo.ProductoBO;
import org.mx.modelo.comun.bo.UsuarioBO;

public class PermisoBO implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5245780420861445792L;
	private Integer idpermiso;
	private Integer idEmpresa;
	private String permiso;
	private String vigenciaPermiso;
	private Integer idproducto;
	private Integer fkProducto;
	private Integer fkPartidas;
	private Integer pkHistorialPermisos;
	private boolean flagProductoPartidas;
	private Date ffin;
	private  Date finicio;
	private String folioRegistro;
	private List<PermisoEventoBO> eventos;
	private ProductoBO producto;
	private UsuarioBO usuario;
	private String tramiteCofepris;
	private String cantidad;
	private String unidad;
	
	private Date citaCofepris;
	private boolean previsionExt;
	private PrevisionesBO prevision;
	private Date fechaCita;
	
	private boolean terminado;
	
	private Date fRealizacionFolio;
	
	private Date fecha;
	
	// variables aux
		private int numEventos;
		private String eventoActual;
		private String estadoCofepris;
		private int cerrados;
		
		
		public PermisoBO(){
			eventos = new ArrayList<PermisoEventoBO>();
		}
		
		public Integer getIdpermiso() {
			return idpermiso;
		}
		public void setIdpermiso(Integer idpermiso) {
			this.idpermiso = idpermiso;
		}
		public Integer getIdEmpresa() {
			return idEmpresa;
		}
		public void setIdEmpresa(Integer idEmpresa) {
			this.idEmpresa = idEmpresa;
		}
		public String getPermiso() {
			return permiso;
		}
		public void setPermiso(String permiso) {
			this.permiso = permiso;
		}
		public String getVigenciaPermiso() {
			return vigenciaPermiso;
		}
		public void setVigenciaPermiso(String vigenciaPermiso) {
			this.vigenciaPermiso = vigenciaPermiso;
		}
		public Integer getIdproducto() {
			return idproducto;
		}
		public void setIdproducto(Integer idproducto) {
			this.idproducto = idproducto;
		}
		public Integer getFkProducto() {
			return fkProducto;
		}
		public void setFkProducto(Integer fkProducto) {
			this.fkProducto = fkProducto;
		}
		public Integer getFkPartidas() {
			return fkPartidas;
		}
		public void setFkPartidas(Integer fkPartidas) {
			this.fkPartidas = fkPartidas;
		}
		public Integer getPkHistorialPermisos() {
			return pkHistorialPermisos;
		}
		public void setPkHistorialPermisos(Integer pkHistorialPermisos) {
			this.pkHistorialPermisos = pkHistorialPermisos;
		}
		public boolean isFlagProductoPartidas() {
			return flagProductoPartidas;
		}
		public void setFlagProductoPartidas(boolean flagProductoPartidas) {
			this.flagProductoPartidas = flagProductoPartidas;
		}
		public Date getFfin() {
			return ffin;
		}
		public void setFfin(Date ffin) {
			this.ffin = ffin;
		}
		public Date getFinicio() {
			return finicio;
		}
		public void setFinicio(Date finicio) {
			this.finicio = finicio;
		}
		public String getFolioRegistro() {
			return folioRegistro;
		}
		public void setFolioRegistro(String folioRegistro) {
			this.folioRegistro = folioRegistro;
		}
		public List<PermisoEventoBO> getEventos() {
			return eventos;
		}
		public void setEventos(List<PermisoEventoBO> eventos) {
			this.eventos = eventos;
		}
		public ProductoBO getProducto() {
			return producto;
		}
		public void setProducto(ProductoBO producto) {
			this.producto = producto;
		}
		public UsuarioBO getUsuario() {
			return usuario;
		}
		public void setUsuario(UsuarioBO usuario) {
			this.usuario = usuario;
		}
		public String getTramiteCofepris() {
			return tramiteCofepris;
		}
		public void setTramiteCofepris(String tramiteCofepris) {
			this.tramiteCofepris = tramiteCofepris;
		}
		public int getNumEventos() {
			return numEventos;
		}
		public void setNumEventos(int numEventos) {
			this.numEventos = numEventos;
		}
		public String getEventoActual() {
			return eventoActual;
		}
		public void setEventoActual(String eventoActual) {
			this.eventoActual = eventoActual;
		}
		public String getEstadoCofepris() {
			return estadoCofepris;
		}
		public void setEstadoCofepris(String estadoCofepris) {
			this.estadoCofepris = estadoCofepris;
		}
		public int getCerrados() {
			return cerrados;
		}
		public void setCerrados(int cerrados) {
			this.cerrados = cerrados;
		}
		public Date getCitaCofepris() {
			return citaCofepris;
		}
		public void setCitaCofepris(Date citaCofepris) {
			this.citaCofepris = citaCofepris;
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

		public PrevisionesBO getPrevision() {
			return prevision;
		}

		public void setPrevision(PrevisionesBO prevision) {
			this.prevision = prevision;
		}

		public boolean isPrevisionExt() {
			return previsionExt;
		}

		public void setPrevisionExt(boolean previsionExt) {
			this.previsionExt = previsionExt;
		}

		public Date getFechaCita() {
			return fechaCita;
		}

		public void setFechaCita(Date fechaCita) {
			this.fechaCita = fechaCita;
		}

		public Date getfRealizacionFolio() {
			return fRealizacionFolio;
		}

		public void setfRealizacionFolio(Date fRealizacionFolio) {
			this.fRealizacionFolio = fRealizacionFolio;
		}

		public Date getFecha() {
			return fecha;
		}

		public void setFecha(Date fecha) {
			this.fecha = fecha;
		}

		public boolean isTerminado() {
			return terminado;
		}

		public void setTerminado(boolean terminado) {
			this.terminado = terminado;
		}


		
		
		
		
}
