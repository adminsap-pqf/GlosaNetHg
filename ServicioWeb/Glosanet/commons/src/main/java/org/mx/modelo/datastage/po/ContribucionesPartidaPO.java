package org.mx.modelo.datastage.po;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.mx.modelo.comun.po.DataStagePO;

/**
 * ContribucionesPartida557 entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Contribuciones_Partida_557")
public class ContribucionesPartidaPO implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -3843848471870223858L;
	private Integer pkContribucionesPartida;
	private PartidaPO partidas;
	private DataStagePO dataStage;
	private Integer claveContribucion;
	private Integer formaPago;
	private Double importePago;
	private Timestamp fechaPagoReal;

	// Constructors

	/** default constructor */
	public ContribucionesPartidaPO() {
	}

	/** minimal constructor */
	public ContribucionesPartidaPO(Integer pkContribucionesPartida,
			PartidaPO partidas, DataStagePO dataStage) {
		this.pkContribucionesPartida = pkContribucionesPartida;
		this.partidas = partidas;
		this.dataStage = dataStage;
	}

	/** full constructor */
	public ContribucionesPartidaPO(Integer pkContribucionesPartida,
			PartidaPO partidas, DataStagePO dataStage,
			Integer claveContribucion, Integer formaPago, Double importePago,
			Timestamp fechaPagoReal) {
		this.pkContribucionesPartida = pkContribucionesPartida;
		this.partidas = partidas;
		this.dataStage = dataStage;
		this.claveContribucion = claveContribucion;
		this.formaPago = formaPago;
		this.importePago = importePago;
		this.fechaPagoReal = fechaPagoReal;
	}

	// Property accessors
	@Id 
	@GeneratedValue
	@Column(name = "PK_Contribuciones_Partida", unique = true, nullable = false)
	public Integer getPkContribucionesPartida() {
		return this.pkContribucionesPartida;
	}

	public void setPkContribucionesPartida(Integer pkContribucionesPartida) {
		this.pkContribucionesPartida = pkContribucionesPartida;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "Patente", referencedColumnName = "Patente", nullable = false),
			@JoinColumn(name = "Pedimento", referencedColumnName = "Pedimento", nullable = false),
			@JoinColumn(name = "SeccionAduanera", referencedColumnName = "SeccionAduanera", nullable = false),
			@JoinColumn(name = "Fraccion", referencedColumnName = "Fraccion", nullable = false),
			@JoinColumn(name = "SecuenciaFraccion", referencedColumnName = "SecuenciaFraccion", nullable = false) })
	public PartidaPO getPartidas() {
		return this.partidas;
	}

	public void setPartidas(PartidaPO partidas) {
		this.partidas = partidas;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_DataStage", nullable = false)
	public DataStagePO getDataStage() {
		return this.dataStage;
	}

	public void setDataStage(DataStagePO dataStage) {
		this.dataStage = dataStage;
	}

	@Column(name = "ClaveContribucion")
	public Integer getClaveContribucion() {
		return this.claveContribucion;
	}

	public void setClaveContribucion(Integer claveContribucion) {
		this.claveContribucion = claveContribucion;
	}

	@Column(name = "FormaPago")
	public Integer getFormaPago() {
		return this.formaPago;
	}

	public void setFormaPago(Integer formaPago) {
		this.formaPago = formaPago;
	}

	@Column(name = "ImportePago", precision = 12)
	public Double getImportePago() {
		return this.importePago;
	}

	public void setImportePago(Double importePago) {
		this.importePago = importePago;
	}

	@Column(name = "FechaPagoReal", length = 23)
	public Timestamp getFechaPagoReal() {
		return this.fechaPagoReal;
	}

	public void setFechaPagoReal(Timestamp fechaPagoReal) {
		this.fechaPagoReal = fechaPagoReal;
	}

}