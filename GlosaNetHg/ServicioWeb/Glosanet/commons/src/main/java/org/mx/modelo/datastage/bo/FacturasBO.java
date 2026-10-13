/**
 * 
 */
package org.mx.modelo.datastage.bo;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "FacturasBO")
public class FacturasBO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4301984744462836559L;
	private Integer idFacturas;
	private DataStageBO dataStage;
	private String patente;
	private String pedimento;
	private String seccionAduanera;
	private Date fechaFacturacion;
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
	private Date fechaPagoReal;
	
	private String region;
	
	/**
	 * 
	 */
	public FacturasBO() {
		super();
	}

	/**
	 * @param idFacturas
	 * @param dataStage
	 * @param patente
	 * @param pedimento
	 * @param seccionAduanera
	 * @param fechaFacturacion
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
	 * @param fechaPagoReal
	 */
	public FacturasBO(Integer idFacturas, DataStageBO dataStage,
			String patente, String pedimento, String seccionAduanera,
			Date fechaFacturacion, String numeroFactura,
			String terminoFacturacion, String monedaFacturacion,
			Double valorDolares, Double valorMonedaExtranjera,
			String paisFacturacion, String entidadFedFacturacion,
			String indentFiscalProveedor, String proveedorMercancia,
			String calleProveedor, String numInteriorProveedor,
			String numExteriorProveedor, String cpProveedor,
			String municipioProveedor, Date fechaPagoReal) {
		super();
		this.idFacturas = idFacturas;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
		this.fechaFacturacion = fechaFacturacion;
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
		this.fechaPagoReal = fechaPagoReal;
	}

	/**
	 * @return the idFacturas
	 */
	public Integer getIdFacturas() {
		return idFacturas;
	}

	/**
	 * @param idFacturas the idFacturas to set
	 */
	public void setIdFacturas(Integer idFacturas) {
		this.idFacturas = idFacturas;
	}

	/**
	 * @return the dataStage
	 */
	public DataStageBO getDataStage() {
		return dataStage;
	}

	/**
	 * @param dataStage the dataStage to set
	 */
	public void setDataStage(DataStageBO dataStage) {
		this.dataStage = dataStage;
	}

	/**
	 * @return the patente
	 */
	public String getPatente() {
		return patente;
	}

	/**
	 * @param patente the patente to set
	 */
	public void setPatente(String patente) {
		this.patente = patente;
	}

	/**
	 * @return the pedimento
	 */
	public String getPedimento() {
		return pedimento;
	}

	/**
	 * @param pedimento the pedimento to set
	 */
	public void setPedimento(String pedimento) {
		this.pedimento = pedimento;
	}

	/**
	 * @return the seccionAduanera
	 */
	public String getSeccionAduanera() {
		return seccionAduanera;
	}

	/**
	 * @param seccionAduanera the seccionAduanera to set
	 */
	public void setSeccionAduanera(String seccionAduanera) {
		this.seccionAduanera = seccionAduanera;
	}

	/**
	 * @return the fechaFacturacion
	 */
	public Date getFechaFacturacion() {
		return fechaFacturacion;
	}

	/**
	 * @param fechaFacturacion the fechaFacturacion to set
	 */
	public void setFechaFacturacion(Date fechaFacturacion) {
		this.fechaFacturacion = fechaFacturacion;
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
	 * @return the fechaPagoReal
	 */
	public Date getFechaPagoReal() {
		return fechaPagoReal;
	}

	/**
	 * @param fechaPagoReal the fechaPagoReal to set
	 */
	public void setFechaPagoReal(Date fechaPagoReal) {
		this.fechaPagoReal = fechaPagoReal;
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
