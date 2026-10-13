package org.mx.modelo.comun.bo;

import java.util.Date;

public class CedulaBO {
	
	int contribucionesPedimento;
	int patente;
	int seccionAduanera;
	int claveContribucion;
	int formaPago;
	int tipoPedimento;
	int dataStage;
	String pedimento;
	String fraccion;
	String descripcionMercancia;
	String paisOrigenDestino;
	String paisCompradorVendedor;
	String tipo;
	String proveedorMercancia;
	String terminoFacturacion;
	String paisFacturacion;
	double valorComercial;
	double cantidadUMComercial;
	double total;
	double importePago;
	double valorDolares;
	double totalIncrementables;
	Date fechaPagoReal;
	
	public CedulaBO(){
		super();
	}

	public int getContribucionesPedimento() {
		return contribucionesPedimento;
	}

	public void setContribucionesPedimento(int contribucionesPedimento) {
		this.contribucionesPedimento = contribucionesPedimento;
	}

	public int getPatente() {
		return patente;
	}

	public void setPatente(int patente) {
		this.patente = patente;
	}

	public int getSeccionAduanera() {
		return seccionAduanera;
	}

	public void setSeccionAduanera(int seccionAduanera) {
		this.seccionAduanera = seccionAduanera;
	}

	public int getClaveContribucion() {
		return claveContribucion;
	}

	public void setClaveContribucion(int claveContribucion) {
		this.claveContribucion = claveContribucion;
	}

	public int getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(int formaPago) {
		this.formaPago = formaPago;
	}

	public int getTipoPedimento() {
		return tipoPedimento;
	}

	public void setTipoPedimento(int tipoPedimento) {
		this.tipoPedimento = tipoPedimento;
	}

	public int getDataStage() {
		return dataStage;
	}

	public void setDataStage(int dataStage) {
		this.dataStage = dataStage;
	}

	public String getPedimento() {
		return pedimento;
	}

	public void setPedimento(String pedimento) {
		this.pedimento = pedimento;
	}

	public String getFraccion() {
		return fraccion;
	}

	public void setFraccion(String fraccion) {
		this.fraccion = fraccion;
	}

	public String getDescripcionMercancia() {
		return descripcionMercancia;
	}

	public void setDescripcionMercancia(String descripcionMercancia) {
		this.descripcionMercancia = descripcionMercancia;
	}

	public String getPaisOrigenDestino() {
		return paisOrigenDestino;
	}

	public void setPaisOrigenDestino(String paisOrigenDestino) {
		this.paisOrigenDestino = paisOrigenDestino;
	}

	public String getPaisCompradorVendedor() {
		return paisCompradorVendedor;
	}

	public void setPaisCompradorVendedor(String paisCompradorVendedor) {
		this.paisCompradorVendedor = paisCompradorVendedor;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getProveedorMercancia() {
		return proveedorMercancia;
	}

	public void setProveedorMercancia(String proveedorMercancia) {
		this.proveedorMercancia = proveedorMercancia;
	}

	public String getTerminoFacturacion() {
		return terminoFacturacion;
	}

	public void setTerminoFacturacion(String terminoFacturacion) {
		this.terminoFacturacion = terminoFacturacion;
	}

	public String getPaisFacturacion() {
		return paisFacturacion;
	}

	public void setPaisFacturacion(String paisFacturacion) {
		this.paisFacturacion = paisFacturacion;
	}

	public double getValorComercial() {
		return valorComercial;
	}

	public void setValorComercial(double valorComercial) {
		this.valorComercial = valorComercial;
	}

	public double getCantidadUMComercial() {
		return cantidadUMComercial;
	}

	public void setCantidadUMComercial(double cantidadUMComercial) {
		this.cantidadUMComercial = cantidadUMComercial;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getImportePago() {
		return importePago;
	}

	public void setImportePago(double importePago) {
		this.importePago = importePago;
	}

	public double getValorDolares() {
		return valorDolares;
	}

	public void setValorDolares(double valorDolares) {
		this.valorDolares = valorDolares;
	}

	public double getTotalIncrementables() {
		return totalIncrementables;
	}

	public void setTotalIncrementables(double totalIncrementables) {
		this.totalIncrementables = totalIncrementables;
	}

	public Date getFechaPagoReal() {
		return fechaPagoReal;
	}

	public void setFechaPagoReal(Date fechaPagoReal) {
		this.fechaPagoReal = fechaPagoReal;
	}	

}
