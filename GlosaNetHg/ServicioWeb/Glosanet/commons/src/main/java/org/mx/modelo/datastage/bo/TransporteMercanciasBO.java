/**
 * 
 */
package org.mx.modelo.datastage.bo;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "TransporteMercanciasBO")
public class TransporteMercanciasBO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9179758050507160220L;
	private Integer idTransporteMercancias;
	private DataStageBO dataStage;
	private String patente;
	private String pedimento;
	private String seccionAduanera;
	private String rfcTransportista;
	private String curpTransportista;
	private String nombreTransportista;
	private String paisTransporte;
	private String identificadorTransporte;
	private Date fechaPagoReal;

	/**
	 * 
	 */
	public TransporteMercanciasBO() {
		super();
	}

	/**
	 * @param idTransporteMercancias
	 * @param dataStage
	 * @param patente
	 * @param pedimento
	 * @param seccionAduanera
	 * @param rfcTransportista
	 * @param curpTransportista
	 * @param nombreTransportista
	 * @param paisTransporte
	 * @param identificadorTransporte
	 * @param fechaPagoReal
	 */
	public TransporteMercanciasBO(Integer idTransporteMercancias,
			DataStageBO dataStage, String patente, String pedimento,
			String seccionAduanera, String rfcTransportista,
			String curpTransportista, String nombreTransportista,
			String paisTransporte, String identificadorTransporte,
			Date fechaPagoReal) {
		super();
		this.idTransporteMercancias = idTransporteMercancias;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
		this.rfcTransportista = rfcTransportista;
		this.curpTransportista = curpTransportista;
		this.nombreTransportista = nombreTransportista;
		this.paisTransporte = paisTransporte;
		this.identificadorTransporte = identificadorTransporte;
		this.fechaPagoReal = fechaPagoReal;
	}

	/**
	 * @return the idTransporteMercancias
	 */
	public Integer getIdTransporteMercancias() {
		return idTransporteMercancias;
	}

	/**
	 * @param idTransporteMercancias the idTransporteMercancias to set
	 */
	public void setIdTransporteMercancias(Integer idTransporteMercancias) {
		this.idTransporteMercancias = idTransporteMercancias;
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
	 * @return the rfcTransportista
	 */
	public String getRfcTransportista() {
		return rfcTransportista;
	}

	/**
	 * @param rfcTransportista the rfcTransportista to set
	 */
	public void setRfcTransportista(String rfcTransportista) {
		this.rfcTransportista = rfcTransportista;
	}

	/**
	 * @return the curpTransportista
	 */
	public String getCurpTransportista() {
		return curpTransportista;
	}

	/**
	 * @param curpTransportista the curpTransportista to set
	 */
	public void setCurpTransportista(String curpTransportista) {
		this.curpTransportista = curpTransportista;
	}

	/**
	 * @return the nombreTransportista
	 */
	public String getNombreTransportista() {
		return nombreTransportista;
	}

	/**
	 * @param nombreTransportista the nombreTransportista to set
	 */
	public void setNombreTransportista(String nombreTransportista) {
		this.nombreTransportista = nombreTransportista;
	}

	/**
	 * @return the paisTransporte
	 */
	public String getPaisTransporte() {
		return paisTransporte;
	}

	/**
	 * @param paisTransporte the paisTransporte to set
	 */
	public void setPaisTransporte(String paisTransporte) {
		this.paisTransporte = paisTransporte;
	}

	/**
	 * @return the identificadorTransporte
	 */
	public String getIdentificadorTransporte() {
		return identificadorTransporte;
	}

	/**
	 * @param identificadorTransporte the identificadorTransporte to set
	 */
	public void setIdentificadorTransporte(String identificadorTransporte) {
		this.identificadorTransporte = identificadorTransporte;
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
