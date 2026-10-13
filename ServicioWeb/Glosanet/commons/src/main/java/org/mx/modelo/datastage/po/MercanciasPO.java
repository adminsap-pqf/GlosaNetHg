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
 * Mercancias552 entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Mercancias_552")
public class MercanciasPO implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -1412452493266459103L;
	private Integer pkMercancias;
	private DataStagePO dataStage;
	private String patente;
	private String pedimento;
	private String seccionAduanera;
	private String fraccion;
	private Integer secuenciaFraccion;
	private String vinNumeroSerie;
	private Integer kilometrajeVehiculo;
	private Timestamp fechaPagoReal;

	// Constructors

	/** default constructor */
	public MercanciasPO() {
	}

	/** minimal constructor */
	public MercanciasPO(Integer pkMercancias, DataStagePO dataStage,
			String patente, String pedimento, String seccionAduanera) {
		this.pkMercancias = pkMercancias;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
	}

	/** full constructor */
	public MercanciasPO(Integer pkMercancias, DataStagePO dataStage,
			String patente, String pedimento, String seccionAduanera,
			String fraccion, Integer secuenciaFraccion, String vinNumeroSerie,
			Integer kilometrajeVehiculo, Timestamp fechaPagoReal) {
		this.pkMercancias = pkMercancias;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
		this.fraccion = fraccion;
		this.secuenciaFraccion = secuenciaFraccion;
		this.vinNumeroSerie = vinNumeroSerie;
		this.kilometrajeVehiculo = kilometrajeVehiculo;
		this.fechaPagoReal = fechaPagoReal;
	}

	// Property accessors
	@Id 
	@GeneratedValue
	@Column(name = "PK_Mercancias", unique = true, nullable = false)
	public Integer getPkMercancias() {
		return this.pkMercancias;
	}

	public void setPkMercancias(Integer pkMercancias) {
		this.pkMercancias = pkMercancias;
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

	@Column(name = "Fraccion", length = 8)
	public String getFraccion() {
		return this.fraccion;
	}

	public void setFraccion(String fraccion) {
		this.fraccion = fraccion;
	}

	@Column(name = "SecuenciaFraccion")
	public Integer getSecuenciaFraccion() {
		return this.secuenciaFraccion;
	}

	public void setSecuenciaFraccion(Integer secuenciaFraccion) {
		this.secuenciaFraccion = secuenciaFraccion;
	}

	@Column(name = "VinNumeroSerie", length = 17)
	public String getVinNumeroSerie() {
		return this.vinNumeroSerie;
	}

	public void setVinNumeroSerie(String vinNumeroSerie) {
		this.vinNumeroSerie = vinNumeroSerie;
	}

	@Column(name = "KilometrajeVehiculo")
	public Integer getKilometrajeVehiculo() {
		return this.kilometrajeVehiculo;
	}

	public void setKilometrajeVehiculo(Integer kilometrajeVehiculo) {
		this.kilometrajeVehiculo = kilometrajeVehiculo;
	}

	@Column(name = "FechaPagoReal", length = 23)
	public Timestamp getFechaPagoReal() {
		return this.fechaPagoReal;
	}

	public void setFechaPagoReal(Timestamp fechaPagoReal) {
		this.fechaPagoReal = fechaPagoReal;
	}

}