/**
 * 
 */
package org.mx.modelo.datastage.bo;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "IncidenciasReconocimientoAduaneroBO")
public class IncidenciasReconocimientoAduaneroBO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4440435020601518917L;
	private Integer idIncidenciasReconocimientoAduanero;
	private DataStageBO dataStage;
	private String patente;
	private String pedimento;
	private String seccionAduanera;
	private Integer consecutivoRemesa;
	private Integer numeroSeleccion;
	private Date fechaInicioReconocimiento;
	private String horaInicioReconocimiento;
	private Date fechaFinReconocimiento;
	private String horaFinReconocimiento;
	private String fraccion;
	private Integer secuenciaFraccion;
	private String claveDocumento;
	private String tipoOperacion;
	private String gradoIncidencia;
	private Date fechaSeleccion;
	
	/**
	 * 
	 */
	public IncidenciasReconocimientoAduaneroBO() {
		super();
	}

	/**
	 * @param idIncidenciasReconocimientoAduanero
	 * @param dataStage
	 * @param patente
	 * @param pedimento
	 * @param seccionAduanera
	 * @param consecutivoRemesa
	 * @param numeroSeleccion
	 * @param fechaInicioReconocimiento
	 * @param horaInicioReconocimiento
	 * @param fechaFinReconocimiento
	 * @param horaFinReconocimiento
	 * @param fraccion
	 * @param secuenciaFraccion
	 * @param claveDocumento
	 * @param tipoOperacion
	 * @param gradoIncidencia
	 * @param fechaSeleccion
	 */
	public IncidenciasReconocimientoAduaneroBO(
			Integer idIncidenciasReconocimientoAduanero, DataStageBO dataStage,
			String patente, String pedimento, String seccionAduanera,
			Integer consecutivoRemesa, Integer numeroSeleccion,
			Date fechaInicioReconocimiento, String horaInicioReconocimiento,
			Date fechaFinReconocimiento, String horaFinReconocimiento,
			String fraccion, Integer secuenciaFraccion, String claveDocumento,
			String tipoOperacion, String gradoIncidencia, Date fechaSeleccion) {
		super();
		this.idIncidenciasReconocimientoAduanero = idIncidenciasReconocimientoAduanero;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
		this.consecutivoRemesa = consecutivoRemesa;
		this.numeroSeleccion = numeroSeleccion;
		this.fechaInicioReconocimiento = fechaInicioReconocimiento;
		this.horaInicioReconocimiento = horaInicioReconocimiento;
		this.fechaFinReconocimiento = fechaFinReconocimiento;
		this.horaFinReconocimiento = horaFinReconocimiento;
		this.fraccion = fraccion;
		this.secuenciaFraccion = secuenciaFraccion;
		this.claveDocumento = claveDocumento;
		this.tipoOperacion = tipoOperacion;
		this.gradoIncidencia = gradoIncidencia;
		this.fechaSeleccion = fechaSeleccion;
	}

	/**
	 * @return the idIncidenciasReconocimientoAduanero
	 */
	public Integer getIdIncidenciasReconocimientoAduanero() {
		return idIncidenciasReconocimientoAduanero;
	}

	/**
	 * @param idIncidenciasReconocimientoAduanero the idIncidenciasReconocimientoAduanero to set
	 */
	public void setIdIncidenciasReconocimientoAduanero(
			Integer idIncidenciasReconocimientoAduanero) {
		this.idIncidenciasReconocimientoAduanero = idIncidenciasReconocimientoAduanero;
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
	 * @return the consecutivoRemesa
	 */
	public Integer getConsecutivoRemesa() {
		return consecutivoRemesa;
	}

	/**
	 * @param consecutivoRemesa the consecutivoRemesa to set
	 */
	public void setConsecutivoRemesa(Integer consecutivoRemesa) {
		this.consecutivoRemesa = consecutivoRemesa;
	}

	/**
	 * @return the numeroSeleccion
	 */
	public Integer getNumeroSeleccion() {
		return numeroSeleccion;
	}

	/**
	 * @param numeroSeleccion the numeroSeleccion to set
	 */
	public void setNumeroSeleccion(Integer numeroSeleccion) {
		this.numeroSeleccion = numeroSeleccion;
	}

	/**
	 * @return the fechaInicioReconocimiento
	 */
	public Date getFechaInicioReconocimiento() {
		return fechaInicioReconocimiento;
	}

	/**
	 * @param fechaInicioReconocimiento the fechaInicioReconocimiento to set
	 */
	public void setFechaInicioReconocimiento(Date fechaInicioReconocimiento) {
		this.fechaInicioReconocimiento = fechaInicioReconocimiento;
	}

	/**
	 * @return the horaInicioReconocimiento
	 */
	public String getHoraInicioReconocimiento() {
		return horaInicioReconocimiento;
	}

	/**
	 * @param horaInicioReconocimiento the horaInicioReconocimiento to set
	 */
	public void setHoraInicioReconocimiento(String horaInicioReconocimiento) {
		this.horaInicioReconocimiento = horaInicioReconocimiento;
	}

	/**
	 * @return the fechaFinReconocimiento
	 */
	public Date getFechaFinReconocimiento() {
		return fechaFinReconocimiento;
	}

	/**
	 * @param fechaFinReconocimiento the fechaFinReconocimiento to set
	 */
	public void setFechaFinReconocimiento(Date fechaFinReconocimiento) {
		this.fechaFinReconocimiento = fechaFinReconocimiento;
	}

	/**
	 * @return the horaFinReconocimiento
	 */
	public String getHoraFinReconocimiento() {
		return horaFinReconocimiento;
	}

	/**
	 * @param horaFinReconocimiento the horaFinReconocimiento to set
	 */
	public void setHoraFinReconocimiento(String horaFinReconocimiento) {
		this.horaFinReconocimiento = horaFinReconocimiento;
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
	 * @return the claveDocumento
	 */
	public String getClaveDocumento() {
		return claveDocumento;
	}

	/**
	 * @param claveDocumento the claveDocumento to set
	 */
	public void setClaveDocumento(String claveDocumento) {
		this.claveDocumento = claveDocumento;
	}

	/**
	 * @return the tipoOperacion
	 */
	public String getTipoOperacion() {
		return tipoOperacion;
	}

	/**
	 * @param tipoOperacion the tipoOperacion to set
	 */
	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	/**
	 * @return the gradoIncidencia
	 */
	public String getGradoIncidencia() {
		return gradoIncidencia;
	}

	/**
	 * @param gradoIncidencia the gradoIncidencia to set
	 */
	public void setGradoIncidencia(String gradoIncidencia) {
		this.gradoIncidencia = gradoIncidencia;
	}

	/**
	 * @return the fechaSeleccion
	 */
	public Date getFechaSeleccion() {
		return fechaSeleccion;
	}

	/**
	 * @param fechaSeleccion the fechaSeleccion to set
	 */
	public void setFechaSeleccion(Date fechaSeleccion) {
		this.fechaSeleccion = fechaSeleccion;
	}
}
