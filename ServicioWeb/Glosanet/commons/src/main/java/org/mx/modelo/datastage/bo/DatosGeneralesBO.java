/**
 * 
 */
package org.mx.modelo.datastage.bo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "DatosGeneralesBO")
public class DatosGeneralesBO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6123801085841201637L;
	private Integer pkDatosGenerales;
	private DataStageBO dataStage;
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
	private Date fechaRecepcionPedimento;
	private Date fechaPagoReal;
	
	private Date finicio;
	private Date ffin;
	private ResumenBO resumen;
	
	private Integer fk01Empresa;

	private BigInteger totalArchivosAdicionales;

	
	/**
	 * 
	 */
	public DatosGeneralesBO() {
		super();
	}

	/**
	 * @param pkDatosGenerales
	 * @param dataStage
	 * @param patente
	 * @param pedimento
	 * @param seccionAduanera
	 * @param tipoOperacion
	 * @param claveDocumento
	 * @param seccionAduaneraEntrada
	 * @param curpContribuyente
	 * @param rfc
	 * @param curpAgenteA
	 * @param tipoCambio
	 * @param totalFletes
	 * @param totalSeguros
	 * @param totalEmbalajes
	 * @param totalIncrementables
	 * @param totalDeducibles
	 * @param pesoBrutoMercancia
	 * @param medioTransporteSalida
	 * @param medioTransporteArribo
	 * @param medioTransporteEntradaSalida
	 * @param destinoMercancia
	 * @param nombreContribuyente
	 * @param calleContribuyente
	 * @param numInteriorContribuyente
	 * @param numExteriorContribuyente
	 * @param cpcontribuyente
	 * @param municipioContribuyente
	 * @param entidadFedContribuyente
	 * @param paisContribuyente
	 * @param tipoPedimento
	 * @param fechaRecepcionPedimento
	 * @param fechaPagoReal
	 */
	public DatosGeneralesBO(Integer pkDatosGenerales, DataStageBO dataStage,
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
			Integer tipoPedimento, Date fechaRecepcionPedimento,
			Date fechaPagoReal) {
		super();
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

	/**
	 * @return the pkDatosGenerales
	 */
	public Integer getPkDatosGenerales() {
		return pkDatosGenerales;
	}

	/**
	 * @param pkDatosGenerales the pkDatosGenerales to set
	 */
	public void setPkDatosGenerales(Integer pkDatosGenerales) {
		this.pkDatosGenerales = pkDatosGenerales;
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
	 * @return the tipoOperacion
	 */
	public String getTipoOperacion() {
		return tipoOperacion;
	}

	/**
	 * @param tipoOperacion the tipoOperacion to set
	 */
	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	/**
	 * @return the claveDocumento
	 */
	public String getClaveDocumento() {
		return claveDocumento;
	}

	/**
	 * @param claveDocumento the claveDocumento to set
	 */
	public void setClaveDocumento(String claveDocumento) {
		this.claveDocumento = claveDocumento;
	}

	/**
	 * @return the seccionAduaneraEntrada
	 */
	public String getSeccionAduaneraEntrada() {
		return seccionAduaneraEntrada;
	}

	/**
	 * @param seccionAduaneraEntrada the seccionAduaneraEntrada to set
	 */
	public void setSeccionAduaneraEntrada(String seccionAduaneraEntrada) {
		this.seccionAduaneraEntrada = seccionAduaneraEntrada;
	}

	/**
	 * @return the curpContribuyente
	 */
	public String getCurpContribuyente() {
		return curpContribuyente;
	}

	/**
	 * @param curpContribuyente the curpContribuyente to set
	 */
	public void setCurpContribuyente(String curpContribuyente) {
		this.curpContribuyente = curpContribuyente;
	}

	/**
	 * @return the rfc
	 */
	public String getRfc() {
		return rfc;
	}

	/**
	 * @param rfc the rfc to set
	 */
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	/**
	 * @return the curpAgenteA
	 */
	public String getCurpAgenteA() {
		return curpAgenteA;
	}

	/**
	 * @param curpAgenteA the curpAgenteA to set
	 */
	public void setCurpAgenteA(String curpAgenteA) {
		this.curpAgenteA = curpAgenteA;
	}

	/**
	 * @return the tipoCambio
	 */
	public Double getTipoCambio() {
		return tipoCambio;
	}

	/**
	 * @param tipoCambio the tipoCambio to set
	 */
	public void setTipoCambio(Double tipoCambio) {
		this.tipoCambio = tipoCambio;
	}

	/**
	 * @return the totalFletes
	 */
	public Double getTotalFletes() {
		return totalFletes;
	}

	/**
	 * @param totalFletes the totalFletes to set
	 */
	public void setTotalFletes(Double totalFletes) {
		this.totalFletes = totalFletes;
	}

	/**
	 * @return the totalSeguros
	 */
	public Double getTotalSeguros() {
		return totalSeguros;
	}

	/**
	 * @param totalSeguros the totalSeguros to set
	 */
	public void setTotalSeguros(Double totalSeguros) {
		this.totalSeguros = totalSeguros;
	}

	/**
	 * @return the totalEmbalajes
	 */
	public Double getTotalEmbalajes() {
		return totalEmbalajes;
	}

	/**
	 * @param totalEmbalajes the totalEmbalajes to set
	 */
	public void setTotalEmbalajes(Double totalEmbalajes) {
		this.totalEmbalajes = totalEmbalajes;
	}

	/**
	 * @return the totalIncrementables
	 */
	public Double getTotalIncrementables() {
		return totalIncrementables;
	}

	/**
	 * @param totalIncrementables the totalIncrementables to set
	 */
	public void setTotalIncrementables(Double totalIncrementables) {
		this.totalIncrementables = totalIncrementables;
	}

	/**
	 * @return the totalDeducibles
	 */
	public Double getTotalDeducibles() {
		return totalDeducibles;
	}

	/**
	 * @param totalDeducibles the totalDeducibles to set
	 */
	public void setTotalDeducibles(Double totalDeducibles) {
		this.totalDeducibles = totalDeducibles;
	}

	/**
	 * @return the pesoBrutoMercancia
	 */
	public Double getPesoBrutoMercancia() {
		return pesoBrutoMercancia;
	}

	/**
	 * @param pesoBrutoMercancia the pesoBrutoMercancia to set
	 */
	public void setPesoBrutoMercancia(Double pesoBrutoMercancia) {
		this.pesoBrutoMercancia = pesoBrutoMercancia;
	}

	/**
	 * @return the medioTransporteSalida
	 */
	public Integer getMedioTransporteSalida() {
		return medioTransporteSalida;
	}

	/**
	 * @param medioTransporteSalida the medioTransporteSalida to set
	 */
	public void setMedioTransporteSalida(Integer medioTransporteSalida) {
		this.medioTransporteSalida = medioTransporteSalida;
	}

	/**
	 * @return the medioTransporteArribo
	 */
	public Integer getMedioTransporteArribo() {
		return medioTransporteArribo;
	}

	/**
	 * @param medioTransporteArribo the medioTransporteArribo to set
	 */
	public void setMedioTransporteArribo(Integer medioTransporteArribo) {
		this.medioTransporteArribo = medioTransporteArribo;
	}

	/**
	 * @return the medioTransporteEntradaSalida
	 */
	public Integer getMedioTransporteEntradaSalida() {
		return medioTransporteEntradaSalida;
	}

	/**
	 * @param medioTransporteEntradaSalida the medioTransporteEntradaSalida to set
	 */
	public void setMedioTransporteEntradaSalida(Integer medioTransporteEntradaSalida) {
		this.medioTransporteEntradaSalida = medioTransporteEntradaSalida;
	}

	/**
	 * @return the destinoMercancia
	 */
	public Integer getDestinoMercancia() {
		return destinoMercancia;
	}

	/**
	 * @param destinoMercancia the destinoMercancia to set
	 */
	public void setDestinoMercancia(Integer destinoMercancia) {
		this.destinoMercancia = destinoMercancia;
	}

	/**
	 * @return the nombreContribuyente
	 */
	public String getNombreContribuyente() {
		return nombreContribuyente;
	}

	/**
	 * @param nombreContribuyente the nombreContribuyente to set
	 */
	public void setNombreContribuyente(String nombreContribuyente) {
		this.nombreContribuyente = nombreContribuyente;
	}

	/**
	 * @return the calleContribuyente
	 */
	public String getCalleContribuyente() {
		return calleContribuyente;
	}

	/**
	 * @param calleContribuyente the calleContribuyente to set
	 */
	public void setCalleContribuyente(String calleContribuyente) {
		this.calleContribuyente = calleContribuyente;
	}

	/**
	 * @return the numInteriorContribuyente
	 */
	public String getNumInteriorContribuyente() {
		return numInteriorContribuyente;
	}

	/**
	 * @param numInteriorContribuyente the numInteriorContribuyente to set
	 */
	public void setNumInteriorContribuyente(String numInteriorContribuyente) {
		this.numInteriorContribuyente = numInteriorContribuyente;
	}

	/**
	 * @return the numExteriorContribuyente
	 */
	public String getNumExteriorContribuyente() {
		return numExteriorContribuyente;
	}

	/**
	 * @param numExteriorContribuyente the numExteriorContribuyente to set
	 */
	public void setNumExteriorContribuyente(String numExteriorContribuyente) {
		this.numExteriorContribuyente = numExteriorContribuyente;
	}

	/**
	 * @return the cpcontribuyente
	 */
	public String getCpcontribuyente() {
		return cpcontribuyente;
	}

	/**
	 * @param cpcontribuyente the cpcontribuyente to set
	 */
	public void setCpcontribuyente(String cpcontribuyente) {
		this.cpcontribuyente = cpcontribuyente;
	}

	/**
	 * @return the municipioContribuyente
	 */
	public String getMunicipioContribuyente() {
		return municipioContribuyente;
	}

	/**
	 * @param municipioContribuyente the municipioContribuyente to set
	 */
	public void setMunicipioContribuyente(String municipioContribuyente) {
		this.municipioContribuyente = municipioContribuyente;
	}

	/**
	 * @return the entidadFedContribuyente
	 */
	public String getEntidadFedContribuyente() {
		return entidadFedContribuyente;
	}

	/**
	 * @param entidadFedContribuyente the entidadFedContribuyente to set
	 */
	public void setEntidadFedContribuyente(String entidadFedContribuyente) {
		this.entidadFedContribuyente = entidadFedContribuyente;
	}

	/**
	 * @return the paisContribuyente
	 */
	public String getPaisContribuyente() {
		return paisContribuyente;
	}

	/**
	 * @param paisContribuyente the paisContribuyente to set
	 */
	public void setPaisContribuyente(String paisContribuyente) {
		this.paisContribuyente = paisContribuyente;
	}

	/**
	 * @return the tipoPedimento
	 */
	public Integer getTipoPedimento() {
		return tipoPedimento;
	}

	/**
	 * @param tipoPedimento the tipoPedimento to set
	 */
	public void setTipoPedimento(Integer tipoPedimento) {
		this.tipoPedimento = tipoPedimento;
	}

	/**
	 * @return the fechaRecepcionPedimento
	 */
	public Date getFechaRecepcionPedimento() {
		return fechaRecepcionPedimento;
	}

	/**
	 * @param fechaRecepcionPedimento the fechaRecepcionPedimento to set
	 */
	public void setFechaRecepcionPedimento(Date fechaRecepcionPedimento) {
		this.fechaRecepcionPedimento = fechaRecepcionPedimento;
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

	/**
	 * @return the finicio
	 */
	public Date getFinicio() {
		return finicio;
	}

	/**
	 * @param finicio the finicio to set
	 */
	public void setFinicio(Date finicio) {
		this.finicio = finicio;
	}

	/**
	 * @return the ffin
	 */
	public Date getFfin() {
		return ffin;
	}

	/**
	 * @param ffin the ffin to set
	 */
	public void setFfin(Date ffin) {
		this.ffin = ffin;
	}

	/**
	 * @return the resumen
	 */
	public ResumenBO getResumen() {
		return resumen;
	}

	/**
	 * @param resumen the resumen to set
	 */
	public void setResumen(ResumenBO resumen) {
		this.resumen = resumen;
	}

	/**
	 * @return the fk01Empresa
	 */
	public Integer getFk01Empresa() {
		return fk01Empresa;
	}

	/**
	 * @param fk01Empresa the fk01Empresa to set
	 */
	public void setFk01Empresa(Integer fk01Empresa) {
		this.fk01Empresa = fk01Empresa;
	}

	public BigInteger getTotalArchivosAdicionales() {
		return this.totalArchivosAdicionales;
	}

	public void setTotalArchivosAdicionales(BigInteger totalArchivosAdicionales) {
		this.totalArchivosAdicionales = totalArchivosAdicionales;
	}
}
