package org.mx.modelo.datastage.bo;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.sql.Timestamp;

@XmlRootElement(name = "DatosGeneralesCustomBO")
public class DatosGeneralesCustomBO implements Serializable {

    private static final long serialVersionUID = 4426659878931582032L;
    private Integer idDatosGenerales;
    private String patente;
    private String pedimento;
    private String seccionAduanera;
    private String tipoOperacion;
    private String claveDocumento;
    private String seccionAduaneraEntrada;
    private Integer tipoPedimento;
    private Timestamp fechaRecepcionPedimento;
    private Timestamp fechaPagoReal;
    private String observaciones;
    private Integer recordIndex;
    private Integer idDatastage;

    public DatosGeneralesCustomBO() {
        super();
    }

    public Integer getIdDatosGenerales() {
        return idDatosGenerales;
    }

    public void setIdDatosGenerales(Integer idDatosGenerales) {
        this.idDatosGenerales = idDatosGenerales;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public String getPedimento() {
        return pedimento;
    }

    public void setPedimento(String pedimento) {
        this.pedimento = pedimento;
    }

    public String getSeccionAduanera() {
        return seccionAduanera;
    }

    public void setSeccionAduanera(String seccionAduanera) {
        this.seccionAduanera = seccionAduanera;
    }

    public String getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public String getClaveDocumento() {
        return claveDocumento;
    }

    public void setClaveDocumento(String claveDocumento) {
        this.claveDocumento = claveDocumento;
    }

    public String getSeccionAduaneraEntrada() {
        return seccionAduaneraEntrada;
    }

    public void setSeccionAduaneraEntrada(String seccionAduaneraEntrada) {
        this.seccionAduaneraEntrada = seccionAduaneraEntrada;
    }

    public Integer getTipoPedimento() {
        return tipoPedimento;
    }

    public void setTipoPedimento(Integer tipoPedimento) {
        this.tipoPedimento = tipoPedimento;
    }

    public Timestamp getFechaRecepcionPedimento() {
        return fechaRecepcionPedimento;
    }

    public void setFechaRecepcionPedimento(Timestamp fechaRecepcionPedimento) {
        this.fechaRecepcionPedimento = fechaRecepcionPedimento;
    }

    public Timestamp getFechaPagoReal() {
        return fechaPagoReal;
    }

    public void setFechaPagoReal(Timestamp fechaPagoReal) {
        this.fechaPagoReal = fechaPagoReal;
    }

    public Integer getIdDatastage() {
        return idDatastage;
    }

    public void setIdDatastage(Integer idDatastage) {
        this.idDatastage = idDatastage;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Integer getRecordIndex() {
        return recordIndex;
    }

    public void setRecordIndex(Integer recordIndex) {
        this.recordIndex = recordIndex;
    }
}
