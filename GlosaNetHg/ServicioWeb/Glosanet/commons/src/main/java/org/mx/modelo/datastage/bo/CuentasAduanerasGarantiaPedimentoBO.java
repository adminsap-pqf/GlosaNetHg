/**
 * 
 */
package org.mx.modelo.datastage.bo;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CuentasAduanerasGarantiaPedimentoBO")
public class CuentasAduanerasGarantiaPedimentoBO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5489375981692322452L;
	private Integer idCuentasAduanerasGarantiaPedimento;
	private DataStageBO dataStage;
	private String patente;
	private String pedimento;
	private String seccionAduanera;
	private Integer institucionEmisora;
	private Integer numeroCuenta;
	private String folioConstancia;
	private Date fechaConstancia;
	private Integer tipoCuenta;
	private Integer claveGarantia;
	private Double valorUnitarioTitulo;
	private Double totalGarantia;
	private Double cantidadUnidades;
	private Double titulosAsignados;
	private Date fechaPagoReal;
	
	/**
	 * 
	 */
	public CuentasAduanerasGarantiaPedimentoBO() {
		super();
	}

	/**
	 * @param idCuentasAduanerasGarantiaPedimento
	 * @param dataStage
	 * @param patente
	 * @param pedimento
	 * @param seccionAduanera
	 * @param institucionEmisora
	 * @param numeroCuenta
	 * @param folioConstancia
	 * @param fechaConstancia
	 * @param tipoCuenta
	 * @param claveGarantia
	 * @param valorUnitarioTitulo
	 * @param totalGarantia
	 * @param cantidadUnidades
	 * @param titulosAsignados
	 * @param fechaPagoReal
	 */
	public CuentasAduanerasGarantiaPedimentoBO(
			Integer idCuentasAduanerasGarantiaPedimento, DataStageBO dataStage,
			String patente, String pedimento, String seccionAduanera,
			Integer institucionEmisora, Integer numeroCuenta,
			String folioConstancia, Date fechaConstancia,
			Integer tipoCuenta, Integer claveGarantia,
			Double valorUnitarioTitulo, Double totalGarantia,
			Double cantidadUnidades, Double titulosAsignados, Date fechaPagoReal) {
		super();
		this.idCuentasAduanerasGarantiaPedimento = idCuentasAduanerasGarantiaPedimento;
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

	/**
	 * @return the idCuentasAduanerasGarantiaPedimento
	 */
	public Integer getIdCuentasAduanerasGarantiaPedimento() {
		return idCuentasAduanerasGarantiaPedimento;
	}

	/**
	 * @param idCuentasAduanerasGarantiaPedimento the idCuentasAduanerasGarantiaPedimento to set
	 */
	public void setIdCuentasAduanerasGarantiaPedimento(
			Integer idCuentasAduanerasGarantiaPedimento) {
		this.idCuentasAduanerasGarantiaPedimento = idCuentasAduanerasGarantiaPedimento;
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
	 * @return the institucionEmisora
	 */
	public Integer getInstitucionEmisora() {
		return institucionEmisora;
	}

	/**
	 * @param institucionEmisora the institucionEmisora to set
	 */
	public void setInstitucionEmisora(Integer institucionEmisora) {
		this.institucionEmisora = institucionEmisora;
	}

	/**
	 * @return the numeroCuenta
	 */
	public Integer getNumeroCuenta() {
		return numeroCuenta;
	}

	/**
	 * @param numeroCuenta the numeroCuenta to set
	 */
	public void setNumeroCuenta(Integer numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	/**
	 * @return the folioConstancia
	 */
	public String getFolioConstancia() {
		return folioConstancia;
	}

	/**
	 * @param folioConstancia the folioConstancia to set
	 */
	public void setFolioConstancia(String folioConstancia) {
		this.folioConstancia = folioConstancia;
	}

	/**
	 * @return the fechaConstancia
	 */
	public Date getFechaConstancia() {
		return fechaConstancia;
	}

	/**
	 * @param fechaConstancia the fechaConstancia to set
	 */
	public void setFechaConstancia(Date fechaConstancia) {
		this.fechaConstancia = fechaConstancia;
	}

	/**
	 * @return the tipoCuenta
	 */
	public Integer getTipoCuenta() {
		return tipoCuenta;
	}

	/**
	 * @param tipoCuenta the tipoCuenta to set
	 */
	public void setTipoCuenta(Integer tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	/**
	 * @return the claveGarantia
	 */
	public Integer getClaveGarantia() {
		return claveGarantia;
	}

	/**
	 * @param claveGarantia the claveGarantia to set
	 */
	public void setClaveGarantia(Integer claveGarantia) {
		this.claveGarantia = claveGarantia;
	}

	/**
	 * @return the valorUnitarioTitulo
	 */
	public Double getValorUnitarioTitulo() {
		return valorUnitarioTitulo;
	}

	/**
	 * @param valorUnitarioTitulo the valorUnitarioTitulo to set
	 */
	public void setValorUnitarioTitulo(Double valorUnitarioTitulo) {
		this.valorUnitarioTitulo = valorUnitarioTitulo;
	}

	/**
	 * @return the totalGarantia
	 */
	public Double getTotalGarantia() {
		return totalGarantia;
	}

	/**
	 * @param totalGarantia the totalGarantia to set
	 */
	public void setTotalGarantia(Double totalGarantia) {
		this.totalGarantia = totalGarantia;
	}

	/**
	 * @return the cantidadUnidades
	 */
	public Double getCantidadUnidades() {
		return cantidadUnidades;
	}

	/**
	 * @param cantidadUnidades the cantidadUnidades to set
	 */
	public void setCantidadUnidades(Double cantidadUnidades) {
		this.cantidadUnidades = cantidadUnidades;
	}

	/**
	 * @return the titulosAsignados
	 */
	public Double getTitulosAsignados() {
		return titulosAsignados;
	}

	/**
	 * @param titulosAsignados the titulosAsignados to set
	 */
	public void setTitulosAsignados(Double titulosAsignados) {
		this.titulosAsignados = titulosAsignados;
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
