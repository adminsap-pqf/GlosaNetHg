/**
 * 
 */
package org.mx.modelo.datastage.bo;

import java.util.Date;

/**
 * @author yosimar.mendez
 *
 */
public class FechasPedimentoBO {

	private Integer idFechasPedimento;
	private DataStageBO dataStage;
	private String patente;
	private String pedimento;
	private String seccionAduanera;
	private Integer tipoFecha;
	private Date fechaOperacion;
	private Date fechaValidacionPagoR;
	
	/**
	 * 
	 */
	public FechasPedimentoBO() {
		super();
	}

	/**
	 * @param idFechasPedimento
	 * @param dataStage
	 * @param patente
	 * @param pedimento
	 * @param seccionAduanera
	 * @param tipoFecha
	 * @param fechaOperacion
	 * @param fechaValidacionPagoR
	 */
	public FechasPedimentoBO(Integer idFechasPedimento, DataStageBO dataStage,
			String patente, String pedimento, String seccionAduanera,
			Integer tipoFecha, Date fechaOperacion, Date fechaValidacionPagoR) {
		super();
		this.idFechasPedimento = idFechasPedimento;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
		this.tipoFecha = tipoFecha;
		this.fechaOperacion = fechaOperacion;
		this.fechaValidacionPagoR = fechaValidacionPagoR;
	}

	/**
	 * @return the idFechasPedimento
	 */
	public Integer getIdFechasPedimento() {
		return idFechasPedimento;
	}

	/**
	 * @param idFechasPedimento the idFechasPedimento to set
	 */
	public void setIdFechasPedimento(Integer idFechasPedimento) {
		this.idFechasPedimento = idFechasPedimento;
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
	 * @return the tipoFecha
	 */
	public Integer getTipoFecha() {
		return tipoFecha;
	}

	/**
	 * @param tipoFecha the tipoFecha to set
	 */
	public void setTipoFecha(Integer tipoFecha) {
		this.tipoFecha = tipoFecha;
	}

	/**
	 * @return the fechaOperacion
	 */
	public Date getFechaOperacion() {
		return fechaOperacion;
	}

	/**
	 * @param fechaOperacion the fechaOperacion to set
	 */
	public void setFechaOperacion(Date fechaOperacion) {
		this.fechaOperacion = fechaOperacion;
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
