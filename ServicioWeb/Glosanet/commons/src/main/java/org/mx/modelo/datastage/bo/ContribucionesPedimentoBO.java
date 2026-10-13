/**
 * 
 */
package org.mx.modelo.datastage.bo;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ContribucionesPedimentoBO")
public class ContribucionesPedimentoBO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5907648876676821156L;
	private Integer idContribucionesPedimento;
	private DataStageBO dataStage;
	private String patente;
	private String pedimento;
	private String seccionAduanera;
	private Integer claveContribucion;
	private Integer formaPago;
	private Double importePago;
	private Integer tipoPedimento;
	private Date fechaPagoReal;
	
	/**
	 * @param idContribucionesPedimento
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
	public ContribucionesPedimentoBO(Integer idContribucionesPedimento,
			DataStageBO dataStage, String patente, String pedimento,
			String seccionAduanera, Integer claveContribucion,
			Integer formaPago, Double importePago, Integer tipoPedimento,
			Date fechaPagoReal) {
		super();
		this.idContribucionesPedimento = idContribucionesPedimento;
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
	 * 
	 */
	public ContribucionesPedimentoBO() {
		super();
	}
	/**
	 * @return the idContribucionesPedimento
	 */
	public Integer getIdContribucionesPedimento() {
		return idContribucionesPedimento;
	}
	/**
	 * @param idContribucionesPedimento the idContribucionesPedimento to set
	 */
	public void setIdContribucionesPedimento(Integer idContribucionesPedimento) {
		this.idContribucionesPedimento = idContribucionesPedimento;
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
	public Integer getClaveContribucion() {
		return claveContribucion;
	}
	/**
	 * @param claveContribucion the claveContribucion to set
	 */
	public void setClaveContribucion(Integer claveContribucion) {
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
	public Double getImportePago() {
		return importePago;
	}
	/**
	 * @param importePago the importePago to set
	 */
	public void setImportePago(Double importePago) {
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
