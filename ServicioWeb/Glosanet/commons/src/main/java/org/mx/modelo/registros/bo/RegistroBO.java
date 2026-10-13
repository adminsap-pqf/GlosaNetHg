package org.mx.modelo.registros.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.mx.modelo.comun.bo.ProductoBO;
import org.mx.modelo.comun.bo.UsuarioBO;

@XmlRootElement(name = "RegistroBO")
public class RegistroBO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4485069167115173729L;
	private Integer idRegistro;
//	private Integer idProducto;
	private Integer idEmpresa;
	private Date finicio;
	private Date ffin;
	private String folioRegistro;
	private String tramiteCofepris;
	private boolean prorroga;
	private String descripcion;
	private List<EventoBO> eventos;
	private ProductoBO producto;
	private UsuarioBO usuario;
//	private Integer idUsuario;
	private Date finicioProrroga;
	private Date ffinProrroga;
	private boolean terminado;
	private Date lastUpdateDate;
	
	// variables aux
	private int numEventos;
	private String eventoActual;
	private String estadoCofepris;
	private int cerrados;
	private int recordIndex;
	private List<HistorialProductoBO> archivosProductos;
	
	public RegistroBO() {
		super();
		eventos = new ArrayList<EventoBO>();
		// TODO Auto-generated constructor stub
	}

	public Date getFinicio() {
		return finicio;
	}

	public void setFinicio(Date finicio) {
		this.finicio = finicio;
	}

	public Date getFfin() {
		return ffin;
	}

	public void setFfin(Date ffin) {
		this.ffin = ffin;
	}

	public String getFolioRegistro() {
		return folioRegistro;
	}

	public void setFolioRegistro(String folioRegistro) {
		this.folioRegistro = folioRegistro;
	}

	public String getTramiteCofepris() {
		return tramiteCofepris;
	}

	public void setTramiteCofepris(String tramiteCofepris) {
		this.tramiteCofepris = tramiteCofepris;
	}

	public boolean isProrroga() {
		return prorroga;
	}

	public void setProrroga(boolean prorroga) {
		this.prorroga = prorroga;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getIdRegistro() {
		return idRegistro;
	}

	public void setIdRegistro(Integer idRegistro) {
		this.idRegistro = idRegistro;
	}

	public List<EventoBO> getEventos() {
		return eventos;
	}

	public void setEventos(List<EventoBO> eventos) {
		this.eventos = eventos;
	}

	public int getNumEventos() {
		return numEventos;
	}

	public void setNumEventos(int numEventos) {
		this.numEventos = numEventos;
	}

	public int getRecordIndex() {
		return recordIndex;
	}

	public void setRecordIndex(int recordIndex) {
		this.recordIndex = recordIndex;
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

	public Date getFinicioProrroga() {
		return finicioProrroga;
	}

	public void setFinicioProrroga(Date finicioProrroga) {
		this.finicioProrroga = finicioProrroga;
	}

	public Date getFfinProrroga() {
		return ffinProrroga;
	}

	public void setFfinProrroga(Date ffinProrroga) {
		this.ffinProrroga = ffinProrroga;
	}

	public boolean isTerminado() {
		return terminado;
	}

	public void setTerminado(boolean terminado) {
		this.terminado = terminado;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
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

	public UsuarioBO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioBO usuario) {
		this.usuario = usuario;
	}

	public List<HistorialProductoBO> getArchivosProductos() {
		return archivosProductos;
	}

	public void setArchivosProductos(List<HistorialProductoBO> archivosProductos) {
		this.archivosProductos = archivosProductos;
	}
}
