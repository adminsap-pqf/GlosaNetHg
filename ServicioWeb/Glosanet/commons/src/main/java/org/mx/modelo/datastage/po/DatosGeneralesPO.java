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
 * DatosGenerales501 entity. @author MyEclipse Persistence Tools
 */
//@NamedStoredProcedureQuery(name = "BulKInsert",
//procedureName = "BulKInsert",
//parameters = {
//@StoredProcedureParameter(mode = ParameterMode.IN, name = "File", type = String.class),
//@StoredProcedureParameter(mode = ParameterMode.IN, name = "Table", type = String.class)
//}
//)
@Entity
@Table(name = "Datos_Generales_501")
public class DatosGeneralesPO implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 2360245881853824905L;
	private Integer pkDatosGenerales;
	private DataStagePO dataStage;
	private String patente;
	private String pedimento;
	private String seccionAduanera;
	private String tipoOperacion;
	private String claveDocumento;
	private String seccionAduaneraEntrada;
	private String curpContribuyente;
	private String rfc;
	private String curpAgenteA;
	private Double tipoCambio;
	private Double totalFletes;
	private Double totalSeguros;
	private Double totalEmbalajes;
	private Double totalIncrementables;
	private Double totalDeducibles;
	private Double pesoBrutoMercancia;
	private Integer medioTransporteSalida;
	private Integer medioTransporteArribo;
	private Integer medioTransporteEntradaSalida;
	private Integer destinoMercancia;
	private String nombreContribuyente;
	private String calleContribuyente;
	private String numInteriorContribuyente;
	private String numExteriorContribuyente;
	private String cpcontribuyente;
	private String municipioContribuyente;
	private String entidadFedContribuyente;
	private String paisContribuyente;
	private Integer tipoPedimento;
	private Timestamp fechaRecepcionPedimento;
	private Timestamp fechaPagoReal;

	// Constructors

	/** default constructor */
	public DatosGeneralesPO() {
	}

	/** minimal constructor */
	public DatosGeneralesPO(Integer pkDatosGenerales, DataStagePO dataStage,
			String patente, String pedimento, String seccionAduanera) {
		this.pkDatosGenerales = pkDatosGenerales;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
	}

	/** full constructor */
	public DatosGeneralesPO(Integer pkDatosGenerales, DataStagePO dataStage,
			String patente, String pedimento, String seccionAduanera,
			String tipoOperacion, String claveDocumento,
			String seccionAduaneraEntrada, String curpContribuyente,
			String rfc, String curpAgenteA, Double tipoCambio,
			Double totalFletes, Double totalSeguros, Double totalEmbalajes,
			Double totalIncrementables, Double totalDeducibles,
			Double pesoBrutoMercancia, Integer medioTransporteSalida,
			Integer medioTransporteArribo,
			Integer medioTransporteEntradaSalida, Integer destinoMercancia,
			String nombreContribuyente, String calleContribuyente,
			String numInteriorContribuyente, String numExteriorContribuyente,
			String cpcontribuyente, String municipioContribuyente,
			String entidadFedContribuyente, String paisContribuyente,
			Integer tipoPedimento, Timestamp fechaRecepcionPedimento,
			Timestamp fechaPagoReal) {
		this.pkDatosGenerales = pkDatosGenerales;
		this.dataStage = dataStage;
		this.patente = patente;
		this.pedimento = pedimento;
		this.seccionAduanera = seccionAduanera;
		this.tipoOperacion = tipoOperacion;
		this.claveDocumento = claveDocumento;
		this.seccionAduaneraEntrada = seccionAduaneraEntrada;
		this.curpContribuyente = curpContribuyente;
		this.rfc = rfc;
		this.curpAgenteA = curpAgenteA;
		this.tipoCambio = tipoCambio;
		this.totalFletes = totalFletes;
		this.totalSeguros = totalSeguros;
		this.totalEmbalajes = totalEmbalajes;
		this.totalIncrementables = totalIncrementables;
		this.totalDeducibles = totalDeducibles;
		this.pesoBrutoMercancia = pesoBrutoMercancia;
		this.medioTransporteSalida = medioTransporteSalida;
		this.medioTransporteArribo = medioTransporteArribo;
		this.medioTransporteEntradaSalida = medioTransporteEntradaSalida;
		this.destinoMercancia = destinoMercancia;
		this.nombreContribuyente = nombreContribuyente;
		this.calleContribuyente = calleContribuyente;
		this.numInteriorContribuyente = numInteriorContribuyente;
		this.numExteriorContribuyente = numExteriorContribuyente;
		this.cpcontribuyente = cpcontribuyente;
		this.municipioContribuyente = municipioContribuyente;
		this.entidadFedContribuyente = entidadFedContribuyente;
		this.paisContribuyente = paisContribuyente;
		this.tipoPedimento = tipoPedimento;
		this.fechaRecepcionPedimento = fechaRecepcionPedimento;
		this.fechaPagoReal = fechaPagoReal;
	}

	// Property accessors
	@Id 
	@GeneratedValue
	@Column(name = "PK_Datos_Generales", unique = true, nullable = false)
	public Integer getPkDatosGenerales() {
		return this.pkDatosGenerales;
	}

	public void setPkDatosGenerales(Integer pkDatosGenerales) {
		this.pkDatosGenerales = pkDatosGenerales;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_DataStage",  nullable = false)
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

	@Column(name = "TipoOperacion", length = 1)
	public String getTipoOperacion() {
		return this.tipoOperacion;
	}

	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	@Column(name = "ClaveDocumento", length = 3)
	public String getClaveDocumento() {
		return this.claveDocumento;
	}

	public void setClaveDocumento(String claveDocumento) {
		this.claveDocumento = claveDocumento;
	}

	@Column(name = "SeccionAduaneraEntrada", length = 3)
	public String getSeccionAduaneraEntrada() {
		return this.seccionAduaneraEntrada;
	}

	public void setSeccionAduaneraEntrada(String seccionAduaneraEntrada) {
		this.seccionAduaneraEntrada = seccionAduaneraEntrada;
	}

	@Column(name = "CurpContribuyente", length = 18)
	public String getCurpContribuyente() {
		return this.curpContribuyente;
	}

	public void setCurpContribuyente(String curpContribuyente) {
		this.curpContribuyente = curpContribuyente;
	}

	@Column(name = "Rfc", length = 13)
	public String getRfc() {
		return this.rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	@Column(name = "CurpAgenteA", length = 18)
	public String getCurpAgenteA() {
		return this.curpAgenteA;
	}

	public void setCurpAgenteA(String curpAgenteA) {
		this.curpAgenteA = curpAgenteA;
	}

	@Column(name = "TipoCambio", precision = 10, scale = 5)
	public Double getTipoCambio() {
		return this.tipoCambio;
	}

	public void setTipoCambio(Double tipoCambio) {
		this.tipoCambio = tipoCambio;
	}

	@Column(name = "TotalFletes", precision = 12)
	public Double getTotalFletes() {
		return this.totalFletes;
	}

	public void setTotalFletes(Double totalFletes) {
		this.totalFletes = totalFletes;
	}

	@Column(name = "TotalSeguros", precision = 12)
	public Double getTotalSeguros() {
		return this.totalSeguros;
	}

	public void setTotalSeguros(Double totalSeguros) {
		this.totalSeguros = totalSeguros;
	}

	@Column(name = "TotalEmbalajes", precision = 12)
	public Double getTotalEmbalajes() {
		return this.totalEmbalajes;
	}

	public void setTotalEmbalajes(Double totalEmbalajes) {
		this.totalEmbalajes = totalEmbalajes;
	}

	@Column(name = "TotalIncrementables", precision = 12)
	public Double getTotalIncrementables() {
		return this.totalIncrementables;
	}

	public void setTotalIncrementables(Double totalIncrementables) {
		this.totalIncrementables = totalIncrementables;
	}

	@Column(name = "TotalDeducibles", precision = 12)
	public Double getTotalDeducibles() {
		return this.totalDeducibles;
	}

	public void setTotalDeducibles(Double totalDeducibles) {
		this.totalDeducibles = totalDeducibles;
	}

	@Column(name = "PesoBrutoMercancia", precision = 14, scale = 3)
	public Double getPesoBrutoMercancia() {
		return this.pesoBrutoMercancia;
	}

	public void setPesoBrutoMercancia(Double pesoBrutoMercancia) {
		this.pesoBrutoMercancia = pesoBrutoMercancia;
	}

	@Column(name = "MedioTransporteSalida")
	public Integer getMedioTransporteSalida() {
		return this.medioTransporteSalida;
	}

	public void setMedioTransporteSalida(Integer medioTransporteSalida) {
		this.medioTransporteSalida = medioTransporteSalida;
	}

	@Column(name = "MedioTransporteArribo")
	public Integer getMedioTransporteArribo() {
		return this.medioTransporteArribo;
	}

	public void setMedioTransporteArribo(Integer medioTransporteArribo) {
		this.medioTransporteArribo = medioTransporteArribo;
	}

	@Column(name = "MedioTransporteEntrada_Salida")
	public Integer getMedioTransporteEntradaSalida() {
		return this.medioTransporteEntradaSalida;
	}

	public void setMedioTransporteEntradaSalida(
			Integer medioTransporteEntradaSalida) {
		this.medioTransporteEntradaSalida = medioTransporteEntradaSalida;
	}

	@Column(name = "DestinoMercancia")
	public Integer getDestinoMercancia() {
		return this.destinoMercancia;
	}

	public void setDestinoMercancia(Integer destinoMercancia) {
		this.destinoMercancia = destinoMercancia;
	}

	@Column(name = "NombreContribuyente")
	public String getNombreContribuyente() {
		return this.nombreContribuyente;
	}

	public void setNombreContribuyente(String nombreContribuyente) {
		this.nombreContribuyente = nombreContribuyente;
	}

	@Column(name = "CalleContribuyente")
	public String getCalleContribuyente() {
		return this.calleContribuyente;
	}

	public void setCalleContribuyente(String calleContribuyente) {
		this.calleContribuyente = calleContribuyente;
	}

	@Column(name = "NumInteriorContribuyente", length = 10)
	public String getNumInteriorContribuyente() {
		return this.numInteriorContribuyente;
	}

	public void setNumInteriorContribuyente(String numInteriorContribuyente) {
		this.numInteriorContribuyente = numInteriorContribuyente;
	}

	@Column(name = "NumExteriorContribuyente", length = 10)
	public String getNumExteriorContribuyente() {
		return this.numExteriorContribuyente;
	}

	public void setNumExteriorContribuyente(String numExteriorContribuyente) {
		this.numExteriorContribuyente = numExteriorContribuyente;
	}

	@Column(name = "CPContribuyente", length = 10)
	public String getCpcontribuyente() {
		return this.cpcontribuyente;
	}

	public void setCpcontribuyente(String cpcontribuyente) {
		this.cpcontribuyente = cpcontribuyente;
	}

	@Column(name = "MunicipioContribuyente")
	public String getMunicipioContribuyente() {
		return this.municipioContribuyente;
	}

	public void setMunicipioContribuyente(String municipioContribuyente) {
		this.municipioContribuyente = municipioContribuyente;
	}

	@Column(name = "EntidadFedContribuyente", length = 3)
	public String getEntidadFedContribuyente() {
		return this.entidadFedContribuyente;
	}

	public void setEntidadFedContribuyente(String entidadFedContribuyente) {
		this.entidadFedContribuyente = entidadFedContribuyente;
	}

	@Column(name = "PaisContribuyente", length = 3)
	public String getPaisContribuyente() {
		return this.paisContribuyente;
	}

	public void setPaisContribuyente(String paisContribuyente) {
		this.paisContribuyente = paisContribuyente;
	}

	@Column(name = "TipoPedimento")
	public Integer getTipoPedimento() {
		return this.tipoPedimento;
	}

	public void setTipoPedimento(Integer tipoPedimento) {
		this.tipoPedimento = tipoPedimento;
	}

	@Column(name = "FechaRecepcionPedimento", length = 23)
	public Timestamp getFechaRecepcionPedimento() {
		return this.fechaRecepcionPedimento;
	}

	public void setFechaRecepcionPedimento(Timestamp fechaRecepcionPedimento) {
		this.fechaRecepcionPedimento = fechaRecepcionPedimento;
	}

	@Column(name = "FechaPagoReal", length = 23)
	public Timestamp getFechaPagoReal() {
		return this.fechaPagoReal;
	}

	public void setFechaPagoReal(Timestamp fechaPagoReal) {
		this.fechaPagoReal = fechaPagoReal;
	}

}