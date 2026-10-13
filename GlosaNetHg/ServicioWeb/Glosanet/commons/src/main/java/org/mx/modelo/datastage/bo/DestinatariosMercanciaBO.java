/**
 * 
 */
package org.mx.modelo.datastage.bo;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "DestinatariosMercanciaBO")
public class DestinatariosMercanciaBO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2592606141607267134L;
	private Integer idDestinatariosMercancia;
	private DataStageBO dataStage;
	private String patente;
	private String pedimento;
	private String seccionAduanera;
	private String indentFiscalDestinatario;
	private String nombreDestinatarioMercancia;
	private String calleDestinatario;
	private String numInteriorDestinatario;
	private String numExteriorDestinatario;
	private String cpDestinatario;
	private String municpioDestinatario;
	private String paisDestinatario;
	private Date fechaPagoReal;
	
	/**
	 * 
	 */
	public DestinatariosMercanciaBO() {
		super();
	}

	/**
	 * @param idDestinatariosMercancia
	 * @param dataStage
	 * @param patente
	 * @param pedimento
	 * @param seccionAduanera
	 * @param indentFiscalDestinatario
	 * @param nombreDestinatarioMercancia
	 * @param calleDestinatario
	 * @param numInteriorDestinatario
	 * @param numExteriorDestinatario
	 * @param cpDestinatario
	 * @param municpioDestinatario
	 * @param paisDestinatario
	 * @param fechaPagoReal
	 */
	public DestinatariosMercanciaBO(Integer idDestinatariosMercancia,
			DataStageBO dataStage, String patente, String pedimento,
			String seccionAduanera, String indentFiscalDestinatario,
			String nombreDestinatarioMercancia, String calleDestinatario,
			String numInteriorDestinatario, String numExteriorDestinatario,
			String cpDestinatario, String municpioDestinatario,
			String paisDestinatario, Date fechaPagoReal) {
		super();
		this.idDestinatariosMercancia = idDestinatariosMercancia;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
		this.indentFiscalDestinatario = indentFiscalDestinatario;
		this.nombreDestinatarioMercancia = nombreDestinatarioMercancia;
		this.calleDestinatario = calleDestinatario;
		this.numInteriorDestinatario = numInteriorDestinatario;
		this.numExteriorDestinatario = numExteriorDestinatario;
		this.cpDestinatario = cpDestinatario;
		this.municpioDestinatario = municpioDestinatario;
		this.paisDestinatario = paisDestinatario;
		this.fechaPagoReal = fechaPagoReal;
	}

	/**
	 * @return the idDestinatariosMercancia
	 */
	public Integer getIdDestinatariosMercancia() {
		return idDestinatariosMercancia;
	}

	/**
	 * @param idDestinatariosMercancia the idDestinatariosMercancia to set
	 */
	public void setIdDestinatariosMercancia(Integer idDestinatariosMercancia) {
		this.idDestinatariosMercancia = idDestinatariosMercancia;
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
	 * @return the indentFiscalDestinatario
	 */
	public String getIndentFiscalDestinatario() {
		return indentFiscalDestinatario;
	}

	/**
	 * @param indentFiscalDestinatario the indentFiscalDestinatario to set
	 */
	public void setIndentFiscalDestinatario(String indentFiscalDestinatario) {
		this.indentFiscalDestinatario = indentFiscalDestinatario;
	}

	/**
	 * @return the nombreDestinatarioMercancia
	 */
	public String getNombreDestinatarioMercancia() {
		return nombreDestinatarioMercancia;
	}

	/**
	 * @param nombreDestinatarioMercancia the nombreDestinatarioMercancia to set
	 */
	public void setNombreDestinatarioMercancia(String nombreDestinatarioMercancia) {
		this.nombreDestinatarioMercancia = nombreDestinatarioMercancia;
	}

	/**
	 * @return the calleDestinatario
	 */
	public String getCalleDestinatario() {
		return calleDestinatario;
	}

	/**
	 * @param calleDestinatario the calleDestinatario to set
	 */
	public void setCalleDestinatario(String calleDestinatario) {
		this.calleDestinatario = calleDestinatario;
	}

	/**
	 * @return the numInteriorDestinatario
	 */
	public String getNumInteriorDestinatario() {
		return numInteriorDestinatario;
	}

	/**
	 * @param numInteriorDestinatario the numInteriorDestinatario to set
	 */
	public void setNumInteriorDestinatario(String numInteriorDestinatario) {
		this.numInteriorDestinatario = numInteriorDestinatario;
	}

	/**
	 * @return the numExteriorDestinatario
	 */
	public String getNumExteriorDestinatario() {
		return numExteriorDestinatario;
	}

	/**
	 * @param numExteriorDestinatario the numExteriorDestinatario to set
	 */
	public void setNumExteriorDestinatario(String numExteriorDestinatario) {
		this.numExteriorDestinatario = numExteriorDestinatario;
	}

	/**
	 * @return the cpDestinatario
	 */
	public String getCpDestinatario() {
		return cpDestinatario;
	}

	/**
	 * @param cpDestinatario the cpDestinatario to set
	 */
	public void setCpDestinatario(String cpDestinatario) {
		this.cpDestinatario = cpDestinatario;
	}

	/**
	 * @return the municpioDestinatario
	 */
	public String getMunicpioDestinatario() {
		return municpioDestinatario;
	}

	/**
	 * @param municpioDestinatario the municpioDestinatario to set
	 */
	public void setMunicpioDestinatario(String municpioDestinatario) {
		this.municpioDestinatario = municpioDestinatario;
	}

	/**
	 * @return the paisDestinatario
	 */
	public String getPaisDestinatario() {
		return paisDestinatario;
	}

	/**
	 * @param paisDestinatario the paisDestinatario to set
	 */
	public void setPaisDestinatario(String paisDestinatario) {
		this.paisDestinatario = paisDestinatario;
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
