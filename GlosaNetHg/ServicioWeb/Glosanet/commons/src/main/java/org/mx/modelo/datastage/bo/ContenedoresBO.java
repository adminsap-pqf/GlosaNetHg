/**
 * 
 */
package org.mx.modelo.datastage.bo;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "ContenedoresBO")
public class ContenedoresBO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2653550246826879771L;
	private Integer idContenedores;
	private DataStageBO dataStage;
	private String patente;
	private String pedimento;
	private String seccionAduanera;
	private String numContenedor;
	private String tipoContenedor;
	private Date fechaPagoReal;
	
	/**
	 * 
	 */
	public ContenedoresBO() {
		super();
	}

	/**
	 * @param idContenedores
	 * @param dataStage
	 * @param patente
	 * @param pedimento
	 * @param seccionAduanera
	 * @param numContenedor
	 * @param tipoContenedor
	 * @param fechaPagoReal
	 */
	public ContenedoresBO(Integer idContenedores, DataStageBO dataStage,
			String patente, String pedimento, String seccionAduanera,
			String numContenedor, String tipoContenedor, Date fechaPagoReal) {
		super();
		this.idContenedores = idContenedores;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
		this.numContenedor = numContenedor;
		this.tipoContenedor = tipoContenedor;
		this.fechaPagoReal = fechaPagoReal;
	}

	/**
	 * @return the idContenedores
	 */
	public Integer getIdContenedores() {
		return idContenedores;
	}

	/**
	 * @param idContenedores the idContenedores to set
	 */
	public void setIdContenedores(Integer idContenedores) {
		this.idContenedores = idContenedores;
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
	 * @return the numContenedor
	 */
	public String getNumContenedor() {
		return numContenedor;
	}

	/**
	 * @param numContenedor the numContenedor to set
	 */
	public void setNumContenedor(String numContenedor) {
		this.numContenedor = numContenedor;
	}

	/**
	 * @return the tipoContenedor
	 */
	public String getTipoContenedor() {
		return tipoContenedor;
	}

	/**
	 * @param tipoContenedor the tipoContenedor to set
	 */
	public void setTipoContenedor(String tipoContenedor) {
		this.tipoContenedor = tipoContenedor;
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
