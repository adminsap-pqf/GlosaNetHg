package org.mx.modelo.permisos.org.mx.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.mx.modelo.comun.bo.ProductoBO;
import org.mx.modelo.comun.bo.UsuarioBO;


@XmlRootElement(name = "HistorialPermisosBO")
public class HistorialPermisosBO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7438432812317920737L;
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
	
	// variables aux
		private int numEventos;
		private String eventoActual;
		private String estadoCofepris;
		private int cerrados;
	
	
	
	
	public HistorialPermisosBO(){
		eventos = new ArrayList<PermisoEventoBO>();
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


	public boolean isFlagProductoPartidas() {
		return flagProductoPartidas;
	}


	public void setFlagProductoPartidas(boolean flagProductoPartidas) {
		this.flagProductoPartidas = flagProductoPartidas;
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

	public String getTramiteCofepris() {
		return tramiteCofepris;
	}

	public void setTramiteCofepris(String tramiteCofepris) {
		this.tramiteCofepris = tramiteCofepris;
	}

	
	

	
	

	
}
