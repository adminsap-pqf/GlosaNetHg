/**
 * 
 */
package org.mx.modelo.datastage.bo;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ResumenBO")
public class ResumenBO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5128263904807137586L;
	private Integer idResumen;
	private String folio;
	private String rfcoPatenteAduanal;
	private Date fechaInicial;
	private Date fechaFinal;
	private Date fechaEjecucion;
	private String totalFracciones;
	private String totalContribuciones;
	private DataStageBO dataStage;
	
	/**
	 * 
	 */
	public ResumenBO() {
		super();
	}

	/**
	 * @param idResumen
	 * @param folio
	 * @param rfcoPatenteAduanal
	 * @param fechaInicial
	 * @param fechaFinal
	 * @param fechaEjecucion
	 * @param totalFracciones
	 * @param totalContribuciones
	 * @param dataStage
	 */
	public ResumenBO(Integer idResumen, String folio,
			String rfcoPatenteAduanal, Date fechaInicial, Date fechaFinal,
			Date fechaEjecucion, String totalFracciones,
			String totalContribuciones, DataStageBO dataStage) {
		super();
		this.idResumen = idResumen;
		this.folio = folio;
		this.rfcoPatenteAduanal = rfcoPatenteAduanal;
		this.fechaInicial = fechaInicial;
		this.fechaFinal = fechaFinal;
		this.fechaEjecucion = fechaEjecucion;
		this.totalFracciones = totalFracciones;
		this.totalContribuciones = totalContribuciones;
		this.dataStage = dataStage;
	}

	/**
	 * @return the idResumen
	 */
	public Integer getIdResumen() {
		return idResumen;
	}

	/**
	 * @param idResumen the idResumen to set
	 */
	public void setIdResumen(Integer idResumen) {
		this.idResumen = idResumen;
	}

	/**
	 * @return the folio
	 */
	public String getFolio() {
		return folio;
	}

	/**
	 * @param folio the folio to set
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}

	/**
	 * @return the rfcoPatenteAduanal
	 */
	public String getRfcoPatenteAduanal() {
		return rfcoPatenteAduanal;
	}

	/**
	 * @param rfcoPatenteAduanal the rfcoPatenteAduanal to set
	 */
	public void setRfcoPatenteAduanal(String rfcoPatenteAduanal) {
		this.rfcoPatenteAduanal = rfcoPatenteAduanal;
	}

	/**
	 * @return the fechaInicial
	 */
	public Date getFechaInicial() {
		return fechaInicial;
	}

	/**
	 * @param fechaInicial the fechaInicial to set
	 */
	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	/**
	 * @return the fechaFinal
	 */
	public Date getFechaFinal() {
		return fechaFinal;
	}

	/**
	 * @param fechaFinal the fechaFinal to set
	 */
	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	/**
	 * @return the fechaEjecucion
	 */
	public Date getFechaEjecucion() {
		return fechaEjecucion;
	}

	/**
	 * @param fechaEjecucion the fechaEjecucion to set
	 */
	public void setFechaEjecucion(Date fechaEjecucion) {
		this.fechaEjecucion = fechaEjecucion;
	}

	/**
	 * @return the totalFracciones
	 */
	public String getTotalFracciones() {
		return totalFracciones;
	}

	/**
	 * @param totalFracciones the totalFracciones to set
	 */
	public void setTotalFracciones(String totalFracciones) {
		this.totalFracciones = totalFracciones;
	}

	/**
	 * @return the totalContribuciones
	 */
	public String getTotalContribuciones() {
		return totalContribuciones;
	}

	/**
	 * @param totalContribuciones the totalContribuciones to set
	 */
	public void setTotalContribuciones(String totalContribuciones) {
		this.totalContribuciones = totalContribuciones;
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
}
