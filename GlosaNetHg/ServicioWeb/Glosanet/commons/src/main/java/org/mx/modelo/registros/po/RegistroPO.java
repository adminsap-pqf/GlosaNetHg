package org.mx.modelo.registros.po;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.mx.modelo.comun.po.ProductoPO;
import org.mx.modelo.comun.po.UsuariosPO;

@Entity
@Table(name = "Registro")
public class RegistroPO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2840225124069395943L;
	private Integer idRegistro;
//	private Integer idProducto;
	private Integer idEmpresa;
	private Date finicio;
	private Date ffin;
	private String folioRegistro;
	private String tramiteCofepris;
	private boolean prorroga;
	private String descripcion;
	private Set<EventoPO> eventos;
	private ProductoPO producto;
//	private Integer idUsuario;
	private UsuariosPO usuario;
	private Date finicioProrroga;
	private Date ffinProrroga;
	private boolean terminado;
	private Date lastUpdateDate;

	@Id 
	@GeneratedValue
	@Column(name = "PK_Registro", unique = true, nullable = false)
	public Integer getIdRegistro() {
		return idRegistro;
	}

	public void setIdRegistro(Integer idRegistro) {
		this.idRegistro = idRegistro;
	}
	
//	@OneToOne
//	@JoinColumn(name = "FK01_Producto", nullable = false)
//	@Column(name = "FK01_Producto")
//	public Integer getIdProducto() {
//		return idProducto;
//	}

//	public void setIdProducto(Integer idProducto) {
//		this.idProducto = idProducto;
//	}

	@Column(name = "FechaInicio")
	public Date getFinicio() {
		return finicio;
	}

	public void setFinicio(Date finicio) {
		this.finicio = finicio;
	}

	@Column(name = "FechaFin")
	public Date getFfin() {
		return ffin;
	}

	public void setFfin(Date ffin) {
		this.ffin = ffin;
	}

	@Column(name = "FolioRegistro")
	public String getFolioRegistro() {
		return folioRegistro;
	}

	public void setFolioRegistro(String folioRegistro) {
		this.folioRegistro = folioRegistro;
	}

	@Column(name = "TramiteCofepris")
	public String getTramiteCofepris() {
		return tramiteCofepris;
	}

	public void setTramiteCofepris(String tramiteCofepris) {
		this.tramiteCofepris = tramiteCofepris;
	}

	@Column(name = "Prorroga")
	public boolean isProrroga() {
		return prorroga;
	}

	public void setProrroga(boolean prorroga) {
		this.prorroga = prorroga;
	}

	@Column(name = "Descripcion")
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@Column(name = "FK02_Empresa")
	public Integer getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	@OneToMany(fetch = FetchType.EAGER,  mappedBy = "registro")
	@OrderBy("FechaRealizacion ASC")
	public Set<EventoPO> getEventos() {
		return eventos;
	}

	public void setEventos(Set<EventoPO> eventos) {
		this.eventos = eventos;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="FK01_Producto", nullable = false)
	public ProductoPO getProducto() {
		return producto;
	}

	public void setProducto(ProductoPO producto) {
		this.producto = producto;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="FK03_Usuario", nullable = false)
	public UsuariosPO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuariosPO usuario) {
		this.usuario = usuario;
	}
	

	@Column(name = "FechaInicioProrroga")
	public Date getFinicioProrroga() {
		return finicioProrroga;
	}

	public void setFinicioProrroga(Date finicioProrroga) {
		this.finicioProrroga = finicioProrroga;
	}

	@Column(name = "FechaFinProrroga")
	public Date getFfinProrroga() {
		return ffinProrroga;
	}

	public void setFfinProrroga(Date ffinProrroga) {
		this.ffinProrroga = ffinProrroga;
	}

	@Column(name = "Terminado")
	public boolean isTerminado() {
		return terminado;
	}

	public void setTerminado(boolean terminado) {
		this.terminado = terminado;
	}

	@Column(name = "LastUpdateDate")
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
}
