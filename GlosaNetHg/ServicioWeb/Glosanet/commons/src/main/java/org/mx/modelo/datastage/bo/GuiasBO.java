/**
 * 
 */
package org.mx.modelo.datastage.bo;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "GuiasBO")
public class GuiasBO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3005506090400541685L;
	private Integer idGuias;
	private DataStageBO dataStage;
	private String patente;
	private String pedimento;
	private String seccionAduanera;
	private String numeroGuia;
	private String tipoGuia;
	private Date fechaPagoReal;
	
	/**
	 * 
	 */
	public GuiasBO() {
		super();
	}

	/**
	 * @param idGuias
	 * @param dataStage
	 * @param patente
	 * @param pedimento
	 * @param seccionAduanera
	 * @param numeroGuia
	 * @param tipoGuia
	 * @param fechaPagoReal
	 */
	public GuiasBO(Integer idGuias, DataStageBO dataStage, String patente,
			String pedimento, String seccionAduanera, String numeroGuia,
			String tipoGuia, Date fechaPagoReal) {
		super();
		this.idGuias = idGuias;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
		this.numeroGuia = numeroGuia;
		this.tipoGuia = tipoGuia;
		this.fechaPagoReal = fechaPagoReal;
	}

	/**
	 * @return the idGuias
	 */
	public Integer getIdGuias() {
		return idGuias;
	}

	/**
	 * @param idGuias the idGuias to set
	 */
	public void setIdGuias(Integer idGuias) {
		this.idGuias = idGuias;
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
	 * @return the numeroGuia
	 */
	public String getNumeroGuia() {
		return numeroGuia;
	}

	/**
	 * @param numeroGuia the numeroGuia to set
	 */
	public void setNumeroGuia(String numeroGuia) {
		this.numeroGuia = numeroGuia;
	}

	/**
	 * @return the tipoGuia
	 */
	public String getTipoGuia() {
		return tipoGuia;
	}

	/**
	 * @param tipoGuia the tipoGuia to set
	 */
	public void setTipoGuia(String tipoGuia) {
		this.tipoGuia = tipoGuia;
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
