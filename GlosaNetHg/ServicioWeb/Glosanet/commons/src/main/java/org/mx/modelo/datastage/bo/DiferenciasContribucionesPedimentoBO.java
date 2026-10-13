/**
 * 
 */
package org.mx.modelo.datastage.bo;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "DiferenciasContribucionesPedimentoBO")
public class DiferenciasContribucionesPedimentoBO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2354769810799222265L;
	private Integer idDiferenciasContribucionesPedimento;
	private DataStageBO dataStage;
	private String patente;
	private String pedimento;
	private String seccionAduanera;
	private Double claveContribucion;
	private Integer formaPago;
	private Integer importePago;
	private Integer tipoPedimento;
	private Date fechaPagoReal;
	
	/**
	 * 
	 */
	public DiferenciasContribucionesPedimentoBO() {
		super();
	}

	/**
	 * @param idDiferenciasContribucionesPedimento
	 * @param dataStage
	 * @param patente
	 * @param pedimento
	 * @param seccionAduanera
	 * @param claveContribucion
	 * @param formaPago
	 * @param importePago
	 * @param tipoPedimento
	 * @param fechaPagoReal
	 */
	public DiferenciasContribucionesPedimentoBO(
			Integer idDiferenciasContribucionesPedimento,
			DataStageBO dataStage, String patente, String pedimento,
			String seccionAduanera, Double claveContribucion,
			Integer formaPago, Integer importePago, Integer tipoPedimento,
			Date fechaPagoReal) {
		super();
		this.idDiferenciasContribucionesPedimento = idDiferenciasContribucionesPedimento;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
		this.claveContribucion = claveContribucion;
		this.formaPago = formaPago;
		this.importePago = importePago;
		this.tipoPedimento = tipoPedimento;
		this.fechaPagoReal = fechaPagoReal;
	}

	/**
	 * @return the idDiferenciasContribucionesPedimento
	 */
	public Integer getIdDiferenciasContribucionesPedimento() {
		return idDiferenciasContribucionesPedimento;
	}

	/**
	 * @param idDiferenciasContribucionesPedimento the idDiferenciasContribucionesPedimento to set
	 */
	public void setIdDiferenciasContribucionesPedimento(
			Integer idDiferenciasContribucionesPedimento) {
		this.idDiferenciasContribucionesPedimento = idDiferenciasContribucionesPedimento;
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
	 * @return the claveContribucion
	 */
	public Double getClaveContribucion() {
		return claveContribucion;
	}

	/**
	 * @param claveContribucion the claveContribucion to set
	 */
	public void setClaveContribucion(Double claveContribucion) {
		this.claveContribucion = claveContribucion;
	}

	/**
	 * @return the formaPago
	 */
	public Integer getFormaPago() {
		return formaPago;
	}

	/**
	 * @param formaPago the formaPago to set
	 */
	public void setFormaPago(Integer formaPago) {
		this.formaPago = formaPago;
	}

	/**
	 * @return the importePago
	 */
	public Integer getImportePago() {
		return importePago;
	}

	/**
	 * @param importePago the importePago to set
	 */
	public void setImportePago(Integer importePago) {
		this.importePago = importePago;
	}

	/**
	 * @return the tipoPedimento
	 */
	public Integer getTipoPedimento() {
		return tipoPedimento;
	}

	/**
	 * @param tipoPedimento the tipoPedimento to set
	 */
	public void setTipoPedimento(Integer tipoPedimento) {
		this.tipoPedimento = tipoPedimento;
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
