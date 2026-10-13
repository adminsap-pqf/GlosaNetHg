package org.mx.modelo.datastage.po;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.mx.modelo.comun.po.DataStagePO;

/**
 * TransporteMercancias502 entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Transporte_Mercancias_502")
public class TransporteMercanciasPO implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 4677413568088874758L;
	private Integer pkTransporteMercancias;
	private DataStagePO dataStage;
	private String patente;
	private String pedimento;
	private String seccionAduanera;
	private String rfcTransportista;
	private String curpTransportista;
	private String nombreTransportista;
	private String paisTransporte;
	private String identificadorTransporte;
	private Timestamp fechaPagoReal;

	// Constructors

	/** default constructor */
	public TransporteMercanciasPO() {
	}

	/** minimal constructor */
	public TransporteMercanciasPO(Integer pkTransporteMercancias,
			DataStagePO dataStage, String patente, String pedimento,
			String seccionAduanera) {
		this.pkTransporteMercancias = pkTransporteMercancias;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
	}

	/** full constructor */
	public TransporteMercanciasPO(Integer pkTransporteMercancias,
			DataStagePO dataStage, String patente, String pedimento,
			String seccionAduanera, String rfcTransportista,
			String curpTransportista, String nombreTransportista,
			String paisTransporte, String identificadorTransporte,
			Timestamp fechaPagoReal) {
		this.pkTransporteMercancias = pkTransporteMercancias;
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

	// Property accessors
	@Id 
	@GeneratedValue
	@Column(name = "PK_Transporte_Mercancias", unique = true, nullable = false)
	public Integer getPkTransporteMercancias() {
		return this.pkTransporteMercancias;
	}

	public void setPkTransporteMercancias(Integer pkTransporteMercancias) {
		this.pkTransporteMercancias = pkTransporteMercancias;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_DataStage", nullable = false)
	public DataStagePO getDataStage() {
		return this.dataStage;
	}

	public void setDataStage(DataStagePO dataStage) {
		this.dataStage = dataStage;
	}

	@Column(name = "Patente", nullable = false, length = 4)
	public String getPatente() {
		return this.patente;
	}

	public void setPatente(String patente) {
		this.patente = patente;
	}

	@Column(name = "Pedimento", nullable = false, length = 7)
	public String getPedimento() {
		return this.pedimento;
	}

	public void setPedimento(String pedimento) {
		this.pedimento = pedimento;
	}

	@Column(name = "SeccionAduanera", nullable = false, length = 3)
	public String getSeccionAduanera() {
		return this.seccionAduanera;
	}

	public void setSeccionAduanera(String seccionAduanera) {
		this.seccionAduanera = seccionAduanera;
	}

	@Column(name = "RfcTransportista", length = 13)
	public String getRfcTransportista() {
		return this.rfcTransportista;
	}

	public void setRfcTransportista(String rfcTransportista) {
		this.rfcTransportista = rfcTransportista;
	}

	@Column(name = "CurpTransportista", length = 18)
	public String getCurpTransportista() {
		return this.curpTransportista;
	}

	public void setCurpTransportista(String curpTransportista) {
		this.curpTransportista = curpTransportista;
	}

	@Column(name = "NombreTransportista")
	public String getNombreTransportista() {
		return this.nombreTransportista;
	}

	public void setNombreTransportista(String nombreTransportista) {
		this.nombreTransportista = nombreTransportista;
	}

	@Column(name = "PaisTransporte", length = 3)
	public String getPaisTransporte() {
		return this.paisTransporte;
	}

	public void setPaisTransporte(String paisTransporte) {
		this.paisTransporte = paisTransporte;
	}

	@Column(name = "IdentificadorTransporte", length = 17)
	public String getIdentificadorTransporte() {
		return this.identificadorTransporte;
	}

	public void setIdentificadorTransporte(String identificadorTransporte) {
		this.identificadorTransporte = identificadorTransporte;
	}

	@Column(name = "FechaPagoReal", length = 23)
	public Timestamp getFechaPagoReal() {
		return this.fechaPagoReal;
	}

	public void setFechaPagoReal(Timestamp fechaPagoReal) {
		this.fechaPagoReal = fechaPagoReal;
	}

}