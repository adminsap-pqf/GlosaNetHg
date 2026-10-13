package org.mx.modelo.datastage.bo;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CasosPedimentoBO")
public class CasosPedimentoBO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8964562343167268176L;
	private Integer idCasosPedimento;
	private DataStageBO dataStage;
	private String patente;
	private String pedimento;
	private String seccionAduanera;
	private String claveCaso;
	private String identificadorCaso;
	private Integer tipoPedimento;
	private String complementoCaso;
	private Date fechaValidacionPagoR;
	
	/**
	 * 
	 */
	public CasosPedimentoBO() {
		super();
	}

	/**
	 * @param idCasosPedimento
	 * @param dataStage
	 * @param patente
	 * @param pedimento
	 * @param seccionAduanera
	 * @param claveCaso
	 * @param identificadorCaso
	 * @param tipoPedimento
	 * @param complementoCaso
	 * @param fechaValidacionPagoR
	 */
	public CasosPedimentoBO(Integer idCasosPedimento, DataStageBO dataStage,
			String patente, String pedimento, String seccionAduanera,
			String claveCaso, String identificadorCaso, Integer tipoPedimento,
			String complementoCaso, Date fechaValidacionPagoR) {
		super();
		this.idCasosPedimento = idCasosPedimento;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
		this.claveCaso = claveCaso;
		this.identificadorCaso = identificadorCaso;
		this.tipoPedimento = tipoPedimento;
		this.complementoCaso = complementoCaso;
		this.fechaValidacionPagoR = fechaValidacionPagoR;
	}

	/**
	 * @return the idCasosPedimento
	 */
	public Integer getIdCasosPedimento() {
		return idCasosPedimento;
	}

	/**
	 * @param idCasosPedimento the idCasosPedimento to set
	 */
	public void setIdCasosPedimento(Integer idCasosPedimento) {
		this.idCasosPedimento = idCasosPedimento;
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
	 * @return the claveCaso
	 */
	public String getClaveCaso() {
		return claveCaso;
	}

	/**
	 * @param claveCaso the claveCaso to set
	 */
	public void setClaveCaso(String claveCaso) {
		this.claveCaso = claveCaso;
	}

	/**
	 * @return the identificadorCaso
	 */
	public String getIdentificadorCaso() {
		return identificadorCaso;
	}

	/**
	 * @param identificadorCaso the identificadorCaso to set
	 */
	public void setIdentificadorCaso(String identificadorCaso) {
		this.identificadorCaso = identificadorCaso;
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
	 * @return the complementoCaso
	 */
	public String getComplementoCaso() {
		return complementoCaso;
	}

	/**
	 * @param complementoCaso the complementoCaso to set
	 */
	public void setComplementoCaso(String complementoCaso) {
		this.complementoCaso = complementoCaso;
	}

	/**
	 * @return the fechaValidacionPagoR
	 */
	public Date getFechaValidacionPagoR() {
		return fechaValidacionPagoR;
	}

	/**
	 * @param fechaValidacionPagoR the fechaValidacionPagoR to set
	 */
	public void setFechaValidacionPagoR(Date fechaValidacionPagoR) {
		this.fechaValidacionPagoR = fechaValidacionPagoR;
	}
}
