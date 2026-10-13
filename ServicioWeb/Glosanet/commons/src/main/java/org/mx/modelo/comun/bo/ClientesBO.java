package org.mx.modelo.comun.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.mx.modelo.datastage.bo.PedimentoBO;

@XmlRootElement(name = "ClientesBO")
public class ClientesBO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5582576094779094898L;
	private Integer idCliente;
	private String nombre;
	private String representante;
	private String rfcActual;
	private Integer idEmpresaActual;
	private List<EmpresaBO> empresas;
	private List<UsuarioBO> usuarios;
	private Date fcreacion;
	private Date fua;
	private Boolean habilitado;
	private PaqueteBO paquete;
	private String rfc;
	private String razonSocial;
	private List<PedimentoBO> pedimentosAnio;
	private Integer pedimentosTotales;
	
	private String telefono;
	private Date fauditable;
	private String regimenFiscal;
	private String usoCFDI;
	private String claveProducto;
	
	private String numeroPedimentos;

	/**
	 * 
	 */
	public ClientesBO() {
		super();
		empresas = new ArrayList<EmpresaBO>();
		usuarios = new ArrayList<UsuarioBO>();
		pedimentosTotales=0;
	}

	/**
	 * @param idCliente
	 * @param nombre
	 * @param rfcActual
	 * @param idEmpresaActual
	 * @param empresas
	 */
	public ClientesBO(Integer idCliente, String nombre, String representante, String rfcActual,
			Integer idEmpresaActual, List<EmpresaBO> empresas) {
		super();
		this.idCliente = idCliente;
		this.nombre = nombre;
		this.representante = representante;
		this.rfcActual = rfcActual;
		this.idEmpresaActual = idEmpresaActual;
		this.empresas = empresas;
	}

	public String getRfcActual() {
		return rfcActual;
	}
	public void setRfcActual(String rfcActual) {
		this.rfcActual = rfcActual;
	}
	public Integer getIdEmpresaActual() {
		return idEmpresaActual;
	}
	public void setIdEmpresaActual(Integer idEmpresaActual) {
		this.idEmpresaActual = idEmpresaActual;
	}
	public List<EmpresaBO> getEmpresas() {
		return empresas;
	}
	public void setEmpresas(List<EmpresaBO> empresas) {
		this.empresas = empresas;
	}
	public Integer getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getRepresentante() {
		return representante;
	}
	public void setRepresentante(String representante) {
		this.representante = representante;
	}
	public Date getFcreacion() {
		return fcreacion;
	}
	public void setFcreacion(Date fcreacion) {
		this.fcreacion = fcreacion;
	}
	public Date getFua() {
		return fua;
	}
	public void setFua(Date fua) {
		this.fua = fua;
	}
	public List<UsuarioBO> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(List<UsuarioBO> usuarios) {
		this.usuarios = usuarios;
	}
	public Boolean getHabilitado() {
		return habilitado;
	}
	public void setHabilitado(Boolean habilitado) {
		this.habilitado = habilitado;
	}
	/*public String getRfc() {
		return rfc;
	}*/
	/*public void setRfc(String rfc) {
		this.rfc = rfc;
	}*/
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public PaqueteBO getPaquete() {
		return paquete;
	}
	public void setPaquete(PaqueteBO paquete) {
		this.paquete = paquete;
	}
	public List<PedimentoBO> getPedimentosAnio() {
		return pedimentosAnio;
	}
	public void setPedimentosAnio(List<PedimentoBO> pedimentosAnio) {
		this.pedimentosAnio = pedimentosAnio;
	}
	public Integer getPedimentosTotales() {
		return pedimentosTotales;
	}
	public void setPedimentosTotales(Integer pedimentosTotales) {
		this.pedimentosTotales = pedimentosTotales;
	}
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public Date getFauditable() {
		return fauditable;
	}
	public void setFauditable(Date fauditable) {
		this.fauditable = fauditable;
	}
	public String getRegimenFiscal() {
		return regimenFiscal;
	}
	public void setRegimenFiscal(String regimenFiscal) {
		this.regimenFiscal = regimenFiscal;
	}
	public String getUsoCFDI() {
		return usoCFDI;
	}
	public void setUsoCFDI(String usoCFDI) {
		this.usoCFDI = usoCFDI;
	}
	public String getClaveProducto() {
		return claveProducto;
	}
	public void setClaveProducto(String claveProducto) {
		this.claveProducto = claveProducto;
	}
	public String getNumeroPedimentos() {
		return numeroPedimentos;
	}
	public void setNumeroPedimentos(String numeroPedimentos) {
		this.numeroPedimentos = numeroPedimentos;
	}
}
