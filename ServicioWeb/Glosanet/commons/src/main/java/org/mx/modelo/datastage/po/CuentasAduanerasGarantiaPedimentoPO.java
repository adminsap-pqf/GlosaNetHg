package org.mx.modelo.datastage.po;

import java.sql.Timestamp;

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
 * CuentasAduanerasGarantiaPedimento508 entity. @author MyEclipse Persistence
 * Tools
 */
@Entity
@Table(name = "Cuentas_Aduaneras_Garantia_Pedimento_508")
public class CuentasAduanerasGarantiaPedimentoPO implements
		java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -2655225465804682453L;
	private Integer pkCuentasAduanerasGarantiaPedimento;
	private DataStagePO dataStage;
	private String patente;
	private String pedimento;
	private String seccionAduanera;
	private Integer institucionEmisora;
	private Integer numeroCuenta;
	private String folioConstancia;
	private Timestamp fechaConstancia;
	private Integer tipoCuenta;
	private Integer claveGarantia;
	private Double valorUnitarioTitulo;
	private Double totalGarantia;
	private Double cantidadUnidades;
	private Double titulosAsignados;
	private Timestamp fechaPagoReal;

	// Constructors

	/** default constructor */
	public CuentasAduanerasGarantiaPedimentoPO() {
	}

	/** minimal constructor */
	public CuentasAduanerasGarantiaPedimentoPO(
			Integer pkCuentasAduanerasGarantiaPedimento, DataStagePO dataStage,
			String patente, String pedimento, String seccionAduanera) {
		this.pkCuentasAduanerasGarantiaPedimento = pkCuentasAduanerasGarantiaPedimento;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
	}

	/** full constructor */
	public CuentasAduanerasGarantiaPedimentoPO(
			Integer pkCuentasAduanerasGarantiaPedimento, DataStagePO dataStage,
			String patente, String pedimento, String seccionAduanera,
			Integer institucionEmisora, Integer numeroCuenta,
			String folioConstancia, Timestamp fechaConstancia,
			Integer tipoCuenta, Integer claveGarantia,
			Double valorUnitarioTitulo, Double totalGarantia,
			Double cantidadUnidades, Double titulosAsignados,
			Timestamp fechaPagoReal) {
		this.pkCuentasAduanerasGarantiaPedimento = pkCuentasAduanerasGarantiaPedimento;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
		this.institucionEmisora = institucionEmisora;
		this.numeroCuenta = numeroCuenta;
		this.folioConstancia = folioConstancia;
		this.fechaConstancia = fechaConstancia;
		this.tipoCuenta = tipoCuenta;
		this.claveGarantia = claveGarantia;
		this.valorUnitarioTitulo = valorUnitarioTitulo;
		this.totalGarantia = totalGarantia;
		this.cantidadUnidades = cantidadUnidades;
		this.titulosAsignados = titulosAsignados;
		this.fechaPagoReal = fechaPagoReal;
	}

	// Property accessors
	@Id 
	@GeneratedValue
	@Column(name = "PK_Cuentas_Aduaneras_Garantia_Pedimento", unique = true, nullable = false)
	public Integer getPkCuentasAduanerasGarantiaPedimento() {
		return this.pkCuentasAduanerasGarantiaPedimento;
	}

	public void setPkCuentasAduanerasGarantiaPedimento(
			Integer pkCuentasAduanerasGarantiaPedimento) {
		this.pkCuentasAduanerasGarantiaPedimento = pkCuentasAduanerasGarantiaPedimento;
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

	@Column(name = "InstitucionEmisora")
	public Integer getInstitucionEmisora() {
		return this.institucionEmisora;
	}

	public void setInstitucionEmisora(Integer institucionEmisora) {
		this.institucionEmisora = institucionEmisora;
	}

	@Column(name = "NumeroCuenta")
	public Integer getNumeroCuenta() {
		return this.numeroCuenta;
	}

	public void setNumeroCuenta(Integer numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	@Column(name = "FolioConstancia", length = 17)
	public String getFolioConstancia() {
		return this.folioConstancia;
	}

	public void setFolioConstancia(String folioConstancia) {
		this.folioConstancia = folioConstancia;
	}

	@Column(name = "FechaConstancia", length = 23)
	public Timestamp getFechaConstancia() {
		return this.fechaConstancia;
	}

	public void setFechaConstancia(Timestamp fechaConstancia) {
		this.fechaConstancia = fechaConstancia;
	}

	@Column(name = "TipoCuenta")
	public Integer getTipoCuenta() {
		return this.tipoCuenta;
	}

	public void setTipoCuenta(Integer tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	@Column(name = "ClaveGarantia")
	public Integer getClaveGarantia() {
		return this.claveGarantia;
	}

	public void setClaveGarantia(Integer claveGarantia) {
		this.claveGarantia = claveGarantia;
	}

	@Column(name = "ValorUnitarioTitulo", precision = 14, scale = 4)
	public Double getValorUnitarioTitulo() {
		return this.valorUnitarioTitulo;
	}

	public void setValorUnitarioTitulo(Double valorUnitarioTitulo) {
		this.valorUnitarioTitulo = valorUnitarioTitulo;
	}

	@Column(name = "TotalGarantia", precision = 14)
	public Double getTotalGarantia() {
		return this.totalGarantia;
	}

	public void setTotalGarantia(Double totalGarantia) {
		this.totalGarantia = totalGarantia;
	}

	@Column(name = "CantidadUnidades", precision = 14, scale = 4)
	public Double getCantidadUnidades() {
		return this.cantidadUnidades;
	}

	public void setCantidadUnidades(Double cantidadUnidades) {
		this.cantidadUnidades = cantidadUnidades;
	}

	@Column(name = "TitulosAsignados", precision = 11, scale = 4)
	public Double getTitulosAsignados() {
		return this.titulosAsignados;
	}

	public void setTitulosAsignados(Double titulosAsignados) {
		this.titulosAsignados = titulosAsignados;
	}

	@Column(name = "FechaPagoReal", length = 23)
	public Timestamp getFechaPagoReal() {
		return this.fechaPagoReal;
	}

	public void setFechaPagoReal(Timestamp fechaPagoReal) {
		this.fechaPagoReal = fechaPagoReal;
	}

}