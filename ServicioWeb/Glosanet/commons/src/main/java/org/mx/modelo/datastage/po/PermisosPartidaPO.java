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
 * PermisosPartida553 entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Permisos_Partida_553")
public class PermisosPartidaPO implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -329820201498062014L;
	private Integer pkPermisosPartida;
	private DataStagePO dataStage;
	private String patente;
	private String pedimento;
	private String seccionAduanera;
	private String fraccion;
	private Integer secuenciaFraccion;
	private String clavePermiso;
	private String firmaDescargo;
	private String numeroPermiso;
	private Double valorComercialDolares;
	private Double cantidadMumtarifa;
	private Timestamp fechaPagoReal;

	// Constructors

	/** default constructor */
	public PermisosPartidaPO() {
	}

	/** minimal constructor */
	public PermisosPartidaPO(Integer pkPermisosPartida, DataStagePO dataStage,
			String patente, String pedimento, String seccionAduanera) {
		this.pkPermisosPartida = pkPermisosPartida;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
	}

	/** full constructor */
	public PermisosPartidaPO(Integer pkPermisosPartida, DataStagePO dataStage,
			String patente, String pedimento, String seccionAduanera,
			String fraccion, Integer secuenciaFraccion, String clavePermiso,
			String firmaDescargo, String numeroPermiso,
			Double valorComercialDolares, Double cantidadMumtarifa,
			Timestamp fechaPagoReal) {
		this.pkPermisosPartida = pkPermisosPartida;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
		this.fraccion = fraccion;
		this.secuenciaFraccion = secuenciaFraccion;
		this.clavePermiso = clavePermiso;
		this.firmaDescargo = firmaDescargo;
		this.numeroPermiso = numeroPermiso;
		this.valorComercialDolares = valorComercialDolares;
		this.cantidadMumtarifa = cantidadMumtarifa;
		this.fechaPagoReal = fechaPagoReal;
	}

	// Property accessors
	@Id 
	@GeneratedValue
	@Column(name = "PK_Permisos_Partida", unique = true, nullable = false)
	public Integer getPkPermisosPartida() {
		return this.pkPermisosPartida;
	}

	public void setPkPermisosPartida(Integer pkPermisosPartida) {
		this.pkPermisosPartida = pkPermisosPartida;
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

	@Column(name = "ClavePermiso", length = 2)
	public String getClavePermiso() {
		return this.clavePermiso;
	}

	public void setClavePermiso(String clavePermiso) {
		this.clavePermiso = clavePermiso;
	}

	@Column(name = "FirmaDescargo", length = 17)
	public String getFirmaDescargo() {
		return this.firmaDescargo;
	}

	public void setFirmaDescargo(String firmaDescargo) {
		this.firmaDescargo = firmaDescargo;
	}

	@Column(name = "NumeroPermiso", length = 17)
	public String getNumeroPermiso() {
		return this.numeroPermiso;
	}

	public void setNumeroPermiso(String numeroPermiso) {
		this.numeroPermiso = numeroPermiso;
	}

	@Column(name = "ValorComercialDolares", precision = 14)
	public Double getValorComercialDolares() {
		return this.valorComercialDolares;
	}

	public void setValorComercialDolares(Double valorComercialDolares) {
		this.valorComercialDolares = valorComercialDolares;
	}

	@Column(name = "CantidadMUMTarifa", precision = 15, scale = 3)
	public Double getCantidadMumtarifa() {
		return this.cantidadMumtarifa;
	}

	public void setCantidadMumtarifa(Double cantidadMumtarifa) {
		this.cantidadMumtarifa = cantidadMumtarifa;
	}

	@Column(name = "FechaPagoReal", length = 23)
	public Timestamp getFechaPagoReal() {
		return this.fechaPagoReal;
	}

	public void setFechaPagoReal(Timestamp fechaPagoReal) {
		this.fechaPagoReal = fechaPagoReal;
	}

}