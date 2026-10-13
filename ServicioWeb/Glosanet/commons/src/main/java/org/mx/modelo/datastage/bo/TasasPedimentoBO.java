/**
 * 
 */
package org.mx.modelo.datastage.bo;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "TasasPedimentoBO")
public class TasasPedimentoBO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6089385109434797313L;
	private Integer idTasasPedimento;
	private DataStageBO dataStage;
	private String patente;
	private String pedimento;
	private String seccionAduanera;
	private Integer claveContribucion;
	private Double tasaContribucion;
	private Integer tipoTasa;
	private Integer tipoPedimento;
	private Date fechaPagoReal;

	/**
	 * 
	 */
	public TasasPedimentoBO() {
		super();
	}

	/**
	 * @param idTasasPedimento
	 * @param dataStage
	 * @param patente
	 * @param pedimento
	 * @param seccionAduanera
	 * @param claveContribucion
	 * @param tasaContribucion
	 * @param tipoTasa
	 * @param tipoPedimento
	 * @param fechaPagoReal
	 */
	public TasasPedimentoBO(Integer idTasasPedimento, DataStageBO dataStage,
			String patente, String pedimento, String seccionAduanera,
			Integer claveContribucion, Double tasaContribucion,
			Integer tipoTasa, Integer tipoPedimento, Date fechaPagoReal) {
		super();
		this.idTasasPedimento = idTasasPedimento;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
		this.claveContribucion = claveContribucion;
		this.tasaContribucion = tasaContribucion;
		this.tipoTasa = tipoTasa;
		this.tipoPedimento = tipoPedimento;
		this.fechaPagoReal = fechaPagoReal;
	}

	/**
	 * @return the idTasasPedimento
	 */
	public Integer getIdTasasPedimento() {
		return idTasasPedimento;
	}

	/**
	 * @param idTasasPedimento the idTasasPedimento to set
	 */
	public void setIdTasasPedimento(Integer idTasasPedimento) {
		this.idTasasPedimento = idTasasPedimento;
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
	 * @return the tasaContribucion
	 */
	public Double getTasaContribucion() {
		return tasaContribucion;
	}

	/**
	 * @param tasaContribucion the tasaContribucion to set
	 */
	public void setTasaContribucion(Double tasaContribucion) {
		this.tasaContribucion = tasaContribucion;
	}

	/**
	 * @return the tipoTasa
	 */
	public Integer getTipoTasa() {
		return tipoTasa;
	}

	/**
	 * @param tipoTasa the tipoTasa to set
	 */
	public void setTipoTasa(Integer tipoTasa) {
		this.tipoTasa = tipoTasa;
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
