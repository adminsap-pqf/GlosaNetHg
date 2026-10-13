package org.mx.modelo.datastage.bo;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "PartidaBO")
public class PartidaBO implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7013017521737094322L;
	private String patente;
	private String pedimento;
	private String seccionAduanera;
	private String fraccion;
	private Integer secuenciaFraccion;
	
	private Integer subdivisionFraccion;
	private String descripcionMercancia;
	private Double precioUnitario;
	private Double valorAduana;
	private Double valorComercial;
	private Double valorDolares;
	private Double cantidadUmcomercial;
	private Integer unidadMedidaComercial;
	private Double cantidadUmtarifa;
	private Integer unidadMedidaTarifa;
	private Double valorAgregado;
	private Integer claveVinculacion;
	private Integer metodoValorizacion;
	private String codigoMercanciaProducto;
	private String marcaMercanciaProducto;
	private String modeloMercanciaProducto;
	private String paisOrigenDestino;
	private String paisCompradorVendedor;
	private String entidadFedOrigen;
	private String entidadFedDestino;
	private String entidadFedComprador;
	private String entidadFedVendedor;
	private String tipoOperacion;
	private String claveDocumento;
	private Date fechaPagoReal;
	
	
	public PartidaBO() {
		super();
	}


	public PartidaBO(String patente, String pedimento, String seccionAduanera,
			String fraccion, Integer secuenciaFraccion,
			Integer subdivisionFraccion, String descripcionMercancia,
			Double precioUnitario, Double valorAduana, Double valorComercial,
			Double valorDolares, Double cantidadUmcomercial,
			Integer unidadMedidaComercial, Double cantidadUmtarifa,
			Integer unidadMedidaTarifa, Double valorAgregado,
			Integer claveVinculacion, Integer metodoValorizacion,
			String codigoMercanciaProducto, String marcaMercanciaProducto,
			String modeloMercanciaProducto, String paisOrigenDestino,
			String paisCompradorVendedor, String entidadFedOrigen,
			String entidadFedDestino, String entidadFedComprador,
			String entidadFedVendedor, String tipoOperacion,
			String claveDocumento, Date fechaPagoReal) {
		super();
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
		this.fraccion = fraccion;
		this.secuenciaFraccion = secuenciaFraccion;
		this.subdivisionFraccion = subdivisionFraccion;
		this.descripcionMercancia = descripcionMercancia;
		this.precioUnitario = precioUnitario;
		this.valorAduana = valorAduana;
		this.valorComercial = valorComercial;
		this.valorDolares = valorDolares;
		this.cantidadUmcomercial = cantidadUmcomercial;
		this.unidadMedidaComercial = unidadMedidaComercial;
		this.cantidadUmtarifa = cantidadUmtarifa;
		this.unidadMedidaTarifa = unidadMedidaTarifa;
		this.valorAgregado = valorAgregado;
		this.claveVinculacion = claveVinculacion;
		this.metodoValorizacion = metodoValorizacion;
		this.codigoMercanciaProducto = codigoMercanciaProducto;
		this.marcaMercanciaProducto = marcaMercanciaProducto;
		this.modeloMercanciaProducto = modeloMercanciaProducto;
		this.paisOrigenDestino = paisOrigenDestino;
		this.paisCompradorVendedor = paisCompradorVendedor;
		this.entidadFedOrigen = entidadFedOrigen;
		this.entidadFedDestino = entidadFedDestino;
		this.entidadFedComprador = entidadFedComprador;
		this.entidadFedVendedor = entidadFedVendedor;
		this.tipoOperacion = tipoOperacion;
		this.claveDocumento = claveDocumento;
		this.fechaPagoReal = fechaPagoReal;
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
	 * @return the fraccion
	 */
	public String getFraccion() {
		return fraccion;
	}


	/**
	 * @param fraccion the fraccion to set
	 */
	public void setFraccion(String fraccion) {
		this.fraccion = fraccion;
	}


	/**
	 * @return the secuenciaFraccion
	 */
	public Integer getSecuenciaFraccion() {
		return secuenciaFraccion;
	}


	/**
	 * @param secuenciaFraccion the secuenciaFraccion to set
	 */
	public void setSecuenciaFraccion(Integer secuenciaFraccion) {
		this.secuenciaFraccion = secuenciaFraccion;
	}


	/**
	 * @return the subdivisionFraccion
	 */
	public Integer getSubdivisionFraccion() {
		return subdivisionFraccion;
	}


	/**
	 * @param subdivisionFraccion the subdivisionFraccion to set
	 */
	public void setSubdivisionFraccion(Integer subdivisionFraccion) {
		this.subdivisionFraccion = subdivisionFraccion;
	}


	/**
	 * @return the descripcionMercancia
	 */
	public String getDescripcionMercancia() {
		return descripcionMercancia;
	}


	/**
	 * @param descripcionMercancia the descripcionMercancia to set
	 */
	public void setDescripcionMercancia(String descripcionMercancia) {
		this.descripcionMercancia = descripcionMercancia;
	}


	/**
	 * @return the precioUnitario
	 */
	public Double getPrecioUnitario() {
		return precioUnitario;
	}


	/**
	 * @param precioUnitario the precioUnitario to set
	 */
	public void setPrecioUnitario(Double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}


	/**
	 * @return the valorAduana
	 */
	public Double getValorAduana() {
		return valorAduana;
	}


	/**
	 * @param valorAduana the valorAduana to set
	 */
	public void setValorAduana(Double valorAduana) {
		this.valorAduana = valorAduana;
	}


	/**
	 * @return the valorComercial
	 */
	public Double getValorComercial() {
		return valorComercial;
	}


	/**
	 * @param valorComercial the valorComercial to set
	 */
	public void setValorComercial(Double valorComercial) {
		this.valorComercial = valorComercial;
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
	 * @return the cantidadUmcomercial
	 */
	public Double getCantidadUmcomercial() {
		return cantidadUmcomercial;
	}


	/**
	 * @param cantidadUmcomercial the cantidadUmcomercial to set
	 */
	public void setCantidadUmcomercial(Double cantidadUmcomercial) {
		this.cantidadUmcomercial = cantidadUmcomercial;
	}


	/**
	 * @return the unidadMedidaComercial
	 */
	public Integer getUnidadMedidaComercial() {
		return unidadMedidaComercial;
	}


	/**
	 * @param unidadMedidaComercial the unidadMedidaComercial to set
	 */
	public void setUnidadMedidaComercial(Integer unidadMedidaComercial) {
		this.unidadMedidaComercial = unidadMedidaComercial;
	}


	/**
	 * @return the cantidadUmtarifa
	 */
	public Double getCantidadUmtarifa() {
		return cantidadUmtarifa;
	}


	/**
	 * @param cantidadUmtarifa the cantidadUmtarifa to set
	 */
	public void setCantidadUmtarifa(Double cantidadUmtarifa) {
		this.cantidadUmtarifa = cantidadUmtarifa;
	}


	/**
	 * @return the unidadMedidaTarifa
	 */
	public Integer getUnidadMedidaTarifa() {
		return unidadMedidaTarifa;
	}


	/**
	 * @param unidadMedidaTarifa the unidadMedidaTarifa to set
	 */
	public void setUnidadMedidaTarifa(Integer unidadMedidaTarifa) {
		this.unidadMedidaTarifa = unidadMedidaTarifa;
	}


	/**
	 * @return the valorAgregado
	 */
	public Double getValorAgregado() {
		return valorAgregado;
	}


	/**
	 * @param valorAgregado the valorAgregado to set
	 */
	public void setValorAgregado(Double valorAgregado) {
		this.valorAgregado = valorAgregado;
	}


	/**
	 * @return the claveVinculacion
	 */
	public Integer getClaveVinculacion() {
		return claveVinculacion;
	}


	/**
	 * @param claveVinculacion the claveVinculacion to set
	 */
	public void setClaveVinculacion(Integer claveVinculacion) {
		this.claveVinculacion = claveVinculacion;
	}


	/**
	 * @return the metodoValorizacion
	 */
	public Integer getMetodoValorizacion() {
		return metodoValorizacion;
	}


	/**
	 * @param metodoValorizacion the metodoValorizacion to set
	 */
	public void setMetodoValorizacion(Integer metodoValorizacion) {
		this.metodoValorizacion = metodoValorizacion;
	}


	/**
	 * @return the codigoMercanciaProducto
	 */
	public String getCodigoMercanciaProducto() {
		return codigoMercanciaProducto;
	}


	/**
	 * @param codigoMercanciaProducto the codigoMercanciaProducto to set
	 */
	public void setCodigoMercanciaProducto(String codigoMercanciaProducto) {
		this.codigoMercanciaProducto = codigoMercanciaProducto;
	}


	/**
	 * @return the marcaMercanciaProducto
	 */
	public String getMarcaMercanciaProducto() {
		return marcaMercanciaProducto;
	}


	/**
	 * @param marcaMercanciaProducto the marcaMercanciaProducto to set
	 */
	public void setMarcaMercanciaProducto(String marcaMercanciaProducto) {
		this.marcaMercanciaProducto = marcaMercanciaProducto;
	}


	/**
	 * @return the modeloMercanciaProducto
	 */
	public String getModeloMercanciaProducto() {
		return modeloMercanciaProducto;
	}


	/**
	 * @param modeloMercanciaProducto the modeloMercanciaProducto to set
	 */
	public void setModeloMercanciaProducto(String modeloMercanciaProducto) {
		this.modeloMercanciaProducto = modeloMercanciaProducto;
	}


	/**
	 * @return the paisOrigenDestino
	 */
	public String getPaisOrigenDestino() {
		return paisOrigenDestino;
	}


	/**
	 * @param paisOrigenDestino the paisOrigenDestino to set
	 */
	public void setPaisOrigenDestino(String paisOrigenDestino) {
		this.paisOrigenDestino = paisOrigenDestino;
	}


	/**
	 * @return the paisCompradorVendedor
	 */
	public String getPaisCompradorVendedor() {
		return paisCompradorVendedor;
	}


	/**
	 * @param paisCompradorVendedor the paisCompradorVendedor to set
	 */
	public void setPaisCompradorVendedor(String paisCompradorVendedor) {
		this.paisCompradorVendedor = paisCompradorVendedor;
	}


	/**
	 * @return the entidadFedOrigen
	 */
	public String getEntidadFedOrigen() {
		return entidadFedOrigen;
	}


	/**
	 * @param entidadFedOrigen the entidadFedOrigen to set
	 */
	public void setEntidadFedOrigen(String entidadFedOrigen) {
		this.entidadFedOrigen = entidadFedOrigen;
	}


	/**
	 * @return the entidadFedDestino
	 */
	public String getEntidadFedDestino() {
		return entidadFedDestino;
	}


	/**
	 * @param entidadFedDestino the entidadFedDestino to set
	 */
	public void setEntidadFedDestino(String entidadFedDestino) {
		this.entidadFedDestino = entidadFedDestino;
	}


	/**
	 * @return the entidadFedComprador
	 */
	public String getEntidadFedComprador() {
		return entidadFedComprador;
	}


	/**
	 * @param entidadFedComprador the entidadFedComprador to set
	 */
	public void setEntidadFedComprador(String entidadFedComprador) {
		this.entidadFedComprador = entidadFedComprador;
	}


	/**
	 * @return the entidadFedVendedor
	 */
	public String getEntidadFedVendedor() {
		return entidadFedVendedor;
	}


	/**
	 * @param entidadFedVendedor the entidadFedVendedor to set
	 */
	public void setEntidadFedVendedor(String entidadFedVendedor) {
		this.entidadFedVendedor = entidadFedVendedor;
	}


	/**
	 * @return the tipoOperacion
	 */
	public String getTipoOperacion() {
		return tipoOperacion;
	}


	/**
	 * @param tipoOperacion the tipoOperacion to set
	 */
	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}


	/**
	 * @return the claveDocumento
	 */
	public String getClaveDocumento() {
		return claveDocumento;
	}


	/**
	 * @param claveDocumento the claveDocumento to set
	 */
	public void setClaveDocumento(String claveDocumento) {
		this.claveDocumento = claveDocumento;
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
	
	
	
}
