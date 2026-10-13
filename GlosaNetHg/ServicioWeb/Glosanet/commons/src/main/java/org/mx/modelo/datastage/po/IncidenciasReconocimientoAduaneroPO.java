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
 * IncidenciasReconocimientoAduanero entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Incidencias_Reconocimiento_Aduanero")
public class IncidenciasReconocimientoAduaneroPO implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -2125220371181769895L;
	private Integer pkIncidenciasReconocimientoAduanero;
	private DataStagePO dataStage;
	private String patente;
	private String pedimento;
	private String seccionAduanera;
	private Integer consecutivoRemesa;
	private Integer numeroSeleccion;
	private Timestamp fechaInicioReconocimiento;
	private String horaInicioReconocimiento;
	private Timestamp fechaFinReconocimiento;
	private String horaFinReconocimiento;
	private String fraccion;
	private Integer secuenciaFraccion;
	private String claveDocumento;
	private String tipoOperacion;
	private String gradoIncidencia;
	private Timestamp fechaSeleccion;

	// Constructors

	/** default constructor */
	public IncidenciasReconocimientoAduaneroPO() {
	}

	/** minimal constructor */
	public IncidenciasReconocimientoAduaneroPO(
			Integer pkIncidenciasReconocimientoAduanero, DataStagePO dataStage,
			String patente, String pedimento, String seccionAduanera) {
		this.pkIncidenciasReconocimientoAduanero = pkIncidenciasReconocimientoAduanero;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
	}

	/** full constructor */
	public IncidenciasReconocimientoAduaneroPO(
			Integer pkIncidenciasReconocimientoAduanero, DataStagePO dataStage,
			String patente, String pedimento, String seccionAduanera,
			Integer consecutivoRemesa, Integer numeroSeleccion,
			Timestamp fechaInicioReconocimiento,
			String horaInicioReconocimiento, Timestamp fechaFinReconocimiento,
			String horaFinReconocimiento, String fraccion,
			Integer secuenciaFraccion, String claveDocumento,
			String tipoOperacion, String gradoIncidencia,
			Timestamp fechaSeleccion) {
		this.pkIncidenciasReconocimientoAduanero = pkIncidenciasReconocimientoAduanero;
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

	// Property accessors
	@Id 
	@GeneratedValue
	@Column(name = "PK_Incidencias_Reconocimiento_Aduanero", unique = true, nullable = false)
	public Integer getPkIncidenciasReconocimientoAduanero() {
		return this.pkIncidenciasReconocimientoAduanero;
	}

	public void setPkIncidenciasReconocimientoAduanero(
			Integer pkIncidenciasReconocimientoAduanero) {
		this.pkIncidenciasReconocimientoAduanero = pkIncidenciasReconocimientoAduanero;
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

	@Column(name = "ConsecutivoRemesa")
	public Integer getConsecutivoRemesa() {
		return this.consecutivoRemesa;
	}

	public void setConsecutivoRemesa(Integer consecutivoRemesa) {
		this.consecutivoRemesa = consecutivoRemesa;
	}

	@Column(name = "NumeroSeleccion")
	public Integer getNumeroSeleccion() {
		return this.numeroSeleccion;
	}

	public void setNumeroSeleccion(Integer numeroSeleccion) {
		this.numeroSeleccion = numeroSeleccion;
	}

	@Column(name = "FechaInicioReconocimiento", length = 23)
	public Timestamp getFechaInicioReconocimiento() {
		return this.fechaInicioReconocimiento;
	}

	public void setFechaInicioReconocimiento(Timestamp fechaInicioReconocimiento) {
		this.fechaInicioReconocimiento = fechaInicioReconocimiento;
	}

	@Column(name = "HoraInicioReconocimiento", length = 10)
	public String getHoraInicioReconocimiento() {
		return this.horaInicioReconocimiento;
	}

	public void setHoraInicioReconocimiento(String horaInicioReconocimiento) {
		this.horaInicioReconocimiento = horaInicioReconocimiento;
	}

	@Column(name = "FechaFinReconocimiento", length = 23)
	public Timestamp getFechaFinReconocimiento() {
		return this.fechaFinReconocimiento;
	}

	public void setFechaFinReconocimiento(Timestamp fechaFinReconocimiento) {
		this.fechaFinReconocimiento = fechaFinReconocimiento;
	}

	@Column(name = "HoraFinReconocimiento", length = 10)
	public String getHoraFinReconocimiento() {
		return this.horaFinReconocimiento;
	}

	public void setHoraFinReconocimiento(String horaFinReconocimiento) {
		this.horaFinReconocimiento = horaFinReconocimiento;
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

	@Column(name = "ClaveDocumento", length = 3)
	public String getClaveDocumento() {
		return this.claveDocumento;
	}

	public void setClaveDocumento(String claveDocumento) {
		this.claveDocumento = claveDocumento;
	}

	@Column(name = "TipoOperacion", length = 1)
	public String getTipoOperacion() {
		return this.tipoOperacion;
	}

	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	@Column(name = "GradoIncidencia", length = 2)
	public String getGradoIncidencia() {
		return this.gradoIncidencia;
	}

	public void setGradoIncidencia(String gradoIncidencia) {
		this.gradoIncidencia = gradoIncidencia;
	}

	@Column(name = "FechaSeleccion", length = 23)
	public Timestamp getFechaSeleccion() {
		return this.fechaSeleccion;
	}

	public void setFechaSeleccion(Timestamp fechaSeleccion) {
		this.fechaSeleccion = fechaSeleccion;
	}

}