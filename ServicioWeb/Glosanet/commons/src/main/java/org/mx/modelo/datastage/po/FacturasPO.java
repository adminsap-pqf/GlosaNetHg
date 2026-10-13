package org.mx.modelo.datastage.po;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.mx.modelo.comun.po.DataStagePO;

/**
 * Facturas505 entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Facturas_505")
public class FacturasPO implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1403419090182951176L;
	private Integer pkFacturas;
	private DataStagePO dataStage;
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
	private Timestamp fechaPagoReal;

	// Constructors

	/** default constructor */
	public FacturasPO() {
	}

	/** minimal constructor */
	public FacturasPO(Integer pkFacturas, DataStagePO dataStage, String patente,
			String pedimento, String seccionAduanera) {
		this.pkFacturas = pkFacturas;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
	}

	/** full constructor */
	public FacturasPO(Integer pkFacturas, DataStagePO dataStage, String patente,
			String pedimento, String seccionAduanera,
			Timestamp fechaFacturacion, String numeroFactura,
			String terminoFacturacion, String monedaFacturacion,
			Double valorDolares, Double valorMonedaExtranjera,
			String paisFacturacion, String entidadFedFacturacion,
			String indentFiscalProveedor, String proveedorMercancia,
			String calleProveedor, String numInteriorProveedor,
			String numExteriorProveedor, String cpProveedor,
			String municipioProveedor, Timestamp fechaPagoReal) {
		this.pkFacturas = pkFacturas;
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

	// Property accessors
	@Id 
	@GeneratedValue
	@Column(name = "PK_Facturas", unique = true, nullable = false)
	public Integer getPkFacturas() {
		return this.pkFacturas;
	}

	public void setPkFacturas(Integer pkFacturas) {
		this.pkFacturas = pkFacturas;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_DataStage", nullable = false)
	public DataStagePO getDataStage() {
		return this.dataStage;
	}

	public void setDataStage(DataStagePO dataStage) {
		this.dataStage = dataStage;
	}

	@Column(name = "Patente", nullable = false, length = 4)
	public String getPatente() {
		return this.patente;
	}

	public void setPatente(String patente) {
		this.patente = patente;
	}

	@Column(name = "Pedimento", nullable = false, length = 7)
	public String getPedimento() {
		return this.pedimento;
	}

	public void setPedimento(String pedimento) {
		this.pedimento = pedimento;
	}

	@Column(name = "SeccionAduanera", nullable = false, length = 3)
	public String getSeccionAduanera() {
		return this.seccionAduanera;
	}

	public void setSeccionAduanera(String seccionAduanera) {
		this.seccionAduanera = seccionAduanera;
	}

	@Column(name = "FechaFacturacion", length = 23)
	public Date getFechaFacturacion() {
		return this.fechaFacturacion;
	}

	public void setFechaFacturacion(Date fechaFacturacion) {
		this.fechaFacturacion = fechaFacturacion;
	}

	@Column(name = "NumeroFactura", length = 15)
	public String getNumeroFactura() {
		return this.numeroFactura;
	}

	public void setNumeroFactura(String numeroFactura) {
		this.numeroFactura = numeroFactura;
	}

	@Column(name = "TerminoFacturacion", length = 3)
	public String getTerminoFacturacion() {
		return this.terminoFacturacion;
	}

	public void setTerminoFacturacion(String terminoFacturacion) {
		this.terminoFacturacion = terminoFacturacion;
	}

	@Column(name = "MonedaFacturacion", length = 3)
	public String getMonedaFacturacion() {
		return this.monedaFacturacion;
	}

	public void setMonedaFacturacion(String monedaFacturacion) {
		this.monedaFacturacion = monedaFacturacion;
	}

	@Column(name = "ValorDolares", precision = 14)
	public Double getValorDolares() {
		return this.valorDolares;
	}

	public void setValorDolares(Double valorDolares) {
		this.valorDolares = valorDolares;
	}

	@Column(name = "ValorMonedaExtranjera", precision = 14)
	public Double getValorMonedaExtranjera() {
		return this.valorMonedaExtranjera;
	}

	public void setValorMonedaExtranjera(Double valorMonedaExtranjera) {
		this.valorMonedaExtranjera = valorMonedaExtranjera;
	}

	@Column(name = "PaisFacturacion", length = 3)
	public String getPaisFacturacion() {
		return this.paisFacturacion;
	}

	public void setPaisFacturacion(String paisFacturacion) {
		this.paisFacturacion = paisFacturacion;
	}

	@Column(name = "EntidadFedFacturacion", length = 3)
	public String getEntidadFedFacturacion() {
		return this.entidadFedFacturacion;
	}

	public void setEntidadFedFacturacion(String entidadFedFacturacion) {
		this.entidadFedFacturacion = entidadFedFacturacion;
	}

	@Column(name = "IndentFiscalProveedor", length = 17)
	public String getIndentFiscalProveedor() {
		return this.indentFiscalProveedor;
	}

	public void setIndentFiscalProveedor(String indentFiscalProveedor) {
		this.indentFiscalProveedor = indentFiscalProveedor;
	}

	@Column(name = "ProveedorMercancia")
	public String getProveedorMercancia() {
		return this.proveedorMercancia;
	}

	public void setProveedorMercancia(String proveedorMercancia) {
		this.proveedorMercancia = proveedorMercancia;
	}

	@Column(name = "CalleProveedor")
	public String getCalleProveedor() {
		return this.calleProveedor;
	}

	public void setCalleProveedor(String calleProveedor) {
		this.calleProveedor = calleProveedor;
	}

	@Column(name = "NumInteriorProveedor", length = 10)
	public String getNumInteriorProveedor() {
		return this.numInteriorProveedor;
	}

	public void setNumInteriorProveedor(String numInteriorProveedor) {
		this.numInteriorProveedor = numInteriorProveedor;
	}

	@Column(name = "NumExteriorProveedor", length = 10)
	public String getNumExteriorProveedor() {
		return this.numExteriorProveedor;
	}

	public void setNumExteriorProveedor(String numExteriorProveedor) {
		this.numExteriorProveedor = numExteriorProveedor;
	}

	@Column(name = "CpProveedor", length = 10)
	public String getCpProveedor() {
		return this.cpProveedor;
	}

	public void setCpProveedor(String cpProveedor) {
		this.cpProveedor = cpProveedor;
	}

	@Column(name = "MunicipioProveedor")
	public String getMunicipioProveedor() {
		return this.municipioProveedor;
	}

	public void setMunicipioProveedor(String municipioProveedor) {
		this.municipioProveedor = municipioProveedor;
	}

	@Column(name = "FechaPagoReal", length = 23)
	public Timestamp getFechaPagoReal() {
		return this.fechaPagoReal;
	}

	public void setFechaPagoReal(Timestamp fechaPagoReal) {
		this.fechaPagoReal = fechaPagoReal;
	}

}