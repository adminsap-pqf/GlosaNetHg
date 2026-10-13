/**
 * 
 */
package org.mx.modelo.datastage.bo;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CuentasAduanerasGarantiaBO")
public class CuentasAduanerasGarantiaBO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3448885561707112362L;
	private Integer idCuentasAduanerasGarantia;
	private DataStageBO dataStage;
	private String patente;
	private String pedimento;
	private String seccionAduanera;
	private String fraccion;
	private Integer secuenciaFraccion;
	private Integer institucionEmisora;
	private Integer numeroCuenta;
	private String folioConstancia;
	private Date fechaConstancia;
	private Integer claveGarantia;
	private Double valorUnitarioTitulo;
	private Double totalGarantia;
	private Double cantidadUnidadesMedida;
	private Double titulosAsignados;
	private Date fechaPagoReal;
	
	/**
	 * 
	 */
	public CuentasAduanerasGarantiaBO() {
		super();
	}

	/**
	 * @param idCuentasAduanerasGarantia
	 * @param dataStage
	 * @param patente
	 * @param pedimento
	 * @param seccionAduanera
	 * @param fraccion
	 * @param secuenciaFraccion
	 * @param institucionEmisora
	 * @param numeroCuenta
	 * @param folioConstancia
	 * @param fechaConstancia
	 * @param claveGarantia
	 * @param valorUnitarioTitulo
	 * @param totalGarantia
	 * @param cantidadUnidadesMedida
	 * @param titulosAsignados
	 * @param fechaPagoReal
	 */
	public CuentasAduanerasGarantiaBO(Integer idCuentasAduanerasGarantia,
			DataStageBO dataStage, String patente, String pedimento,
			String seccionAduanera, String fraccion, Integer secuenciaFraccion,
			Integer institucionEmisora, Integer numeroCuenta,
			String folioConstancia, Date fechaConstancia,
			Integer claveGarantia, Double valorUnitarioTitulo,
			Double totalGarantia, Double cantidadUnidadesMedida,
			Double titulosAsignados, Date fechaPagoReal) {
		super();
		this.idCuentasAduanerasGarantia = idCuentasAduanerasGarantia;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
		this.fraccion = fraccion;
		this.secuenciaFraccion = secuenciaFraccion;
		this.institucionEmisora = institucionEmisora;
		this.numeroCuenta = numeroCuenta;
		this.folioConstancia = folioConstancia;
		this.fechaConstancia = fechaConstancia;
		this.claveGarantia = claveGarantia;
		this.valorUnitarioTitulo = valorUnitarioTitulo;
		this.totalGarantia = totalGarantia;
		this.cantidadUnidadesMedida = cantidadUnidadesMedida;
		this.titulosAsignados = titulosAsignados;
		this.fechaPagoReal = fechaPagoReal;
	}

	/**
	 * @return the idCuentasAduanerasGarantia
	 */
	public Integer getIdCuentasAduanerasGarantia() {
		return idCuentasAduanerasGarantia;
	}

	/**
	 * @param idCuentasAduanerasGarantia the idCuentasAduanerasGarantia to set
	 */
	public void setIdCuentasAduanerasGarantia(Integer idCuentasAduanerasGarantia) {
		this.idCuentasAduanerasGarantia = idCuentasAduanerasGarantia;
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
	 * @return the cantidadUnidadesMedida
	 */
	public Double getCantidadUnidadesMedida() {
		return cantidadUnidadesMedida;
	}

	/**
	 * @param cantidadUnidadesMedida the cantidadUnidadesMedida to set
	 */
	public void setCantidadUnidadesMedida(Double cantidadUnidadesMedida) {
		this.cantidadUnidadesMedida = cantidadUnidadesMedida;
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
