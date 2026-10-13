package org.mx.modelo.datastage.po;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "Datos_Generales_501")
public class DatosGeneralesCustomPO implements Serializable {

    private static final long serialVersionUID = -4619561266135968557L;
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
    private Integer idDatastage;
    private Integer recordIndex;

    public DatosGeneralesCustomPO() { }

    @Id
    @GeneratedValue
    @Column(name = "PK_Datos_Generales", unique = true, nullable = false)
    public Integer getIdDatosGenerales() {
        return idDatosGenerales;
    }

    public void setIdDatosGenerales(Integer idDatosGenerales) {
        this.idDatosGenerales = idDatosGenerales;
    }

    @Column(name = "Patente", nullable = false, length = 4)
    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    @Column(name = "Pedimento", nullable = false, length = 7)
    public String getPedimento() {
        return pedimento;
    }

    public void setPedimento(String pedimento) {
        this.pedimento = pedimento;
    }

    @Column(name = "SeccionAduanera", nullable = false, length = 3)
    public String getSeccionAduanera() {
        return seccionAduanera;
    }

    public void setSeccionAduanera(String seccionAduanera) {
        this.seccionAduanera = seccionAduanera;
    }

    @Column(name = "TipoOperacion", length = 1)
    public String getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    @Column(name = "ClaveDocumento", length = 3)
    public String getClaveDocumento() {
        return claveDocumento;
    }

    public void setClaveDocumento(String claveDocumento) {
        this.claveDocumento = claveDocumento;
    }

    @Column(name = "SeccionAduaneraEntrada", length = 3)
    public String getSeccionAduaneraEntrada() {
        return seccionAduaneraEntrada;
    }

    public void setSeccionAduaneraEntrada(String seccionAduaneraEntrada) {
        this.seccionAduaneraEntrada = seccionAduaneraEntrada;
    }

    @Column(name = "TipoPedimento")
    public Integer getTipoPedimento() {
        return tipoPedimento;
    }

    public void setTipoPedimento(Integer tipoPedimento) {
        this.tipoPedimento = tipoPedimento;
    }

    @Column(name = "FechaRecepcionPedimento", length = 23)
    public Timestamp getFechaRecepcionPedimento() {
        return fechaRecepcionPedimento;
    }

    public void setFechaRecepcionPedimento(Timestamp fechaRecepcionPedimento) {
        this.fechaRecepcionPedimento = fechaRecepcionPedimento;
    }

    @Column(name = "FechaPagoReal", length = 23)
    public Timestamp getFechaPagoReal() {
        return fechaPagoReal;
    }

    public void setFechaPagoReal(Timestamp fechaPagoReal) {
        this.fechaPagoReal = fechaPagoReal;
    }

    @Column(name = "Observaciones")
    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Column(name = "PK_DataStage")
    public Integer getIdDatastage() {
        return idDatastage;
    }

    public void setIdDatastage(Integer idDatastage) {
        this.idDatastage = idDatastage;
    }

    @Column(name = "RecordIndex")
    public Integer getRecordIndex() {
        return recordIndex;
    }

    public void setRecordIndex(Integer recordIndex) {
        this.recordIndex = recordIndex;
    }
}
