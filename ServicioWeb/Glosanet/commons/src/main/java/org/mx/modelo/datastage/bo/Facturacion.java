/**
 * 
 */
package org.mx.modelo.datastage.bo;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Facturacion")
public class Facturacion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8470830750306516433L;
	private Long patente;
	private Long pedimento;
	private Long seccionAduanera;
	private Date ffacturacion;
	private String numeroFactura;
	private String terminoFacturacion;
	private String monedaFacturacion;
	private Double valorDolares;
	private Double valorMonedaExtranjera;
	private String paisFacturacion;
	private String entidadFedFacturacion;
	private String indentFiscalProveedor;
	private String proveedorMercancia;
	private String calleProveedor;
	private String numInteriorProveedor;
	private String numExteriorProveedor;
	private String cpProveedor;
	private String municipioProveedor;
	private Date fpagoReal;
	
	private String region;
	
	/**
	 * 
	 */
	public Facturacion() {
		super();
	}
	/**
	 * @param patente
	 * @param pedimento
	 * @param seccionAduanera
	 * @param ffacturacion
	 * @param numeroFactura
	 * @param terminoFacturacion
	 * @param monedaFacturacion
	 * @param valorDolares
	 * @param valorMonedaExtranjera
	 * @param paisFacturacion
	 * @param entidadFedFacturacion
	 * @param indentFiscalProveedor
	 * @param proveedorMercancia
	 * @param calleProveedor
	 * @param numInteriorProveedor
	 * @param numExteriorProveedor
	 * @param cpProveedor
	 * @param municipioProveedor
	 * @param fpagoReal
	 */
	public Facturacion(Long patente, Long pedimento, Long seccionAduanera,
			Date ffacturacion, String numeroFactura, String terminoFacturacion,
			String monedaFacturacion, Double valorDolares,
			Double valorMonedaExtranjera, String paisFacturacion,
			String entidadFedFacturacion, String indentFiscalProveedor,
			String proveedorMercancia, String calleProveedor,
			String numInteriorProveedor, String numExteriorProveedor,
			String cpProveedor, String municipioProveedor, Date fpagoReal) {
		super();
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
		this.ffacturacion = ffacturacion;
		this.numeroFactura = numeroFactura;
		this.terminoFacturacion = terminoFacturacion;
		this.monedaFacturacion = monedaFacturacion;
		this.valorDolares = valorDolares;
		this.valorMonedaExtranjera = valorMonedaExtranjera;
		this.paisFacturacion = paisFacturacion;
		this.entidadFedFacturacion = entidadFedFacturacion;
		this.indentFiscalProveedor = indentFiscalProveedor;
		this.proveedorMercancia = proveedorMercancia;
		this.calleProveedor = calleProveedor;
		this.numInteriorProveedor = numInteriorProveedor;
		this.numExteriorProveedor = numExteriorProveedor;
		this.cpProveedor = cpProveedor;
		this.municipioProveedor = municipioProveedor;
		this.fpagoReal = fpagoReal;
	}
	/**
	 * @return the patente
	 */
	public Long getPatente() {
		return patente;
	}
	/**
	 * @param patente the patente to set
	 */
	public void setPatente(Long patente) {
		this.patente = patente;
	}
	/**
	 * @return the pedimento
	 */
	public Long getPedimento() {
		return pedimento;
	}
	/**
	 * @param pedimento the pedimento to set
	 */
	public void setPedimento(Long pedimento) {
		this.pedimento = pedimento;
	}
	/**
	 * @return the seccionAduanera
	 */
	public Long getSeccionAduanera() {
		return seccionAduanera;
	}
	/**
	 * @param seccionAduanera the seccionAduanera to set
	 */
	public void setSeccionAduanera(Long seccionAduanera) {
		this.seccionAduanera = seccionAduanera;
	}
	/**
	 * @return the ffacturacion
	 */
	public Date getFfacturacion() {
		return ffacturacion;
	}
	/**
	 * @param ffacturacion the ffacturacion to set
	 */
	public void setFfacturacion(Date ffacturacion) {
		this.ffacturacion = ffacturacion;
	}
	/**
	 * @return the numeroFactura
	 */
	public String getNumeroFactura() {
		return numeroFactura;
	}
	/**
	 * @param numeroFactura the numeroFactura to set
	 */
	public void setNumeroFactura(String numeroFactura) {
		this.numeroFactura = numeroFactura;
	}
	/**
	 * @return the terminoFacturacion
	 */
	public String getTerminoFacturacion() {
		return terminoFacturacion;
	}
	/**
	 * @param terminoFacturacion the terminoFacturacion to set
	 */
	public void setTerminoFacturacion(String terminoFacturacion) {
		this.terminoFacturacion = terminoFacturacion;
	}
	/**
	 * @return the monedaFacturacion
	 */
	public String getMonedaFacturacion() {
		return monedaFacturacion;
	}
	/**
	 * @param monedaFacturacion the monedaFacturacion to set
	 */
	public void setMonedaFacturacion(String monedaFacturacion) {
		this.monedaFacturacion = monedaFacturacion;
	}
	/**
	 * @return the valorDolares
	 */
	public Double getValorDolares() {
		return valorDolares;
	}
	/**
	 * @param valorDolares the valorDolares to set
	 */
	public void setValorDolares(Double valorDolares) {
		this.valorDolares = valorDolares;
	}
	/**
	 * @return the valorMonedaExtranjera
	 */
	public Double getValorMonedaExtranjera() {
		return valorMonedaExtranjera;
	}
	/**
	 * @param valorMonedaExtranjera the valorMonedaExtranjera to set
	 */
	public void setValorMonedaExtranjera(Double valorMonedaExtranjera) {
		this.valorMonedaExtranjera = valorMonedaExtranjera;
	}
	/**
	 * @return the paisFacturacion
	 */
	public String getPaisFacturacion() {
		return paisFacturacion;
	}
	/**
	 * @param paisFacturacion the paisFacturacion to set
	 */
	public void setPaisFacturacion(String paisFacturacion) {
		this.paisFacturacion = paisFacturacion;
	}
	/**
	 * @return the proveedorMercancia
	 */
	public String getProveedorMercancia() {
		return proveedorMercancia;
	}
	/**
	 * @param proveedorMercancia the proveedorMercancia to set
	 */
	public void setProveedorMercancia(String proveedorMercancia) {
		this.proveedorMercancia = proveedorMercancia;
	}
	/**
	 * @return the calleProveedor
	 */
	public String getCalleProveedor() {
		return calleProveedor;
	}
	/**
	 * @param calleProveedor the calleProveedor to set
	 */
	public void setCalleProveedor(String calleProveedor) {
		this.calleProveedor = calleProveedor;
	}
	/**
	 * @return the municipioProveedor
	 */
	public String getMunicipioProveedor() {
		return municipioProveedor;
	}
	/**
	 * @param municipioProveedor the municipioProveedor to set
	 */
	public void setMunicipioProveedor(String municipioProveedor) {
		this.municipioProveedor = municipioProveedor;
	}
	/**
	 * @return the fpagoReal
	 */
	public Date getFpagoReal() {
		return fpagoReal;
	}
	/**
	 * @param fpagoReal the fpagoReal to set
	 */
	public void setFpagoReal(Date fpagoReal) {
		this.fpagoReal = fpagoReal;
	}
	/**
	 * @return the numInteriorProveedor
	 */
	public String getNumInteriorProveedor() {
		return numInteriorProveedor;
	}
	/**
	 * @param numInteriorProveedor the numInteriorProveedor to set
	 */
	public void setNumInteriorProveedor(String numInteriorProveedor) {
		this.numInteriorProveedor = numInteriorProveedor;
	}
	/**
	 * @return the numExteriorProveedor
	 */
	public String getNumExteriorProveedor() {
		return numExteriorProveedor;
	}
	/**
	 * @param numExteriorProveedor the numExteriorProveedor to set
	 */
	public void setNumExteriorProveedor(String numExteriorProveedor) {
		this.numExteriorProveedor = numExteriorProveedor;
	}
	/**
	 * @return the cpProveedor
	 */
	public String getCpProveedor() {
		return cpProveedor;
	}
	/**
	 * @param cpProveedor the cpProveedor to set
	 */
	public void setCpProveedor(String cpProveedor) {
		this.cpProveedor = cpProveedor;
	}
	/**
	 * @return the entidadFedFacturacion
	 */
	public String getEntidadFedFacturacion() {
		return entidadFedFacturacion;
	}
	/**
	 * @param entidadFedFacturacion the entidadFedFacturacion to set
	 */
	public void setEntidadFedFacturacion(String entidadFedFacturacion) {
		this.entidadFedFacturacion = entidadFedFacturacion;
	}
	/**
	 * @return the indentFiscalProveedor
	 */
	public String getIndentFiscalProveedor() {
		return indentFiscalProveedor;
	}
	/**
	 * @param indentFiscalProveedor the indentFiscalProveedor to set
	 */
	public void setIndentFiscalProveedor(String indentFiscalProveedor) {
		this.indentFiscalProveedor = indentFiscalProveedor;
	}
	/**
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}
	/**
	 * @param region the region to set
	 */
	public void setRegion(String region) {
		this.region = region;
	}
	
	
}
