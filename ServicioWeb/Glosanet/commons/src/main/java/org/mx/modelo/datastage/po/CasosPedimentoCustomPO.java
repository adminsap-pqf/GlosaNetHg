package org.mx.modelo.datastage.po;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Casos_Pedimento_507")
public class CasosPedimentoCustomPO implements Serializable {

    private static final long serialVersionUID = -5672333485987672265L;
    private Integer idCasosPedimento;
    private String patente;
    private String pedimento;
    private String seccionAduanera;
    private String claveCaso;
    private String identificadorCaso;
    private Integer recordIndex;
    private Integer idCliente;
    private Integer statusEdoc;
    private Integer statusAcuse;
    private String errorMessageEdoc;
    private String errorMessageAcuse;
    private Integer idSolicitudAcuse;
    private Integer idSolicitudEdoc;
    private String pathEdoc;
    private String pathAcuse;

    public CasosPedimentoCustomPO() { }

    @Id
    @GeneratedValue
    @Column(name = "PK_Casos_Pedimento", unique = true, nullable = false)
    public Integer getIdCasosPedimento() {
        return idCasosPedimento;
    }
    public void setIdCasosPedimento(Integer idCasosPedimento) { this.idCasosPedimento = idCasosPedimento; }
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
    @Column(name = "ClaveCaso", length = 2)
    public String getClaveCaso() {
        return claveCaso;
    }
    public void setClaveCaso(String claveCaso) {
        this.claveCaso = claveCaso;
    }
    @Column(name = "IdentificadorCaso", length = 20)
    public String getIdentificadorCaso() {
        return identificadorCaso;
    }
    public void setIdentificadorCaso(String identificadorCaso) {
        this.identificadorCaso = identificadorCaso;
    }
    @Column(name = "StatusEdoc")
    public Integer getStatusEdoc() { return statusEdoc; }
    public void setStatusEdoc(Integer statusEdoc) { this.statusEdoc = statusEdoc; }
    @Column(name = "StatusAcuse")
    public Integer getStatusAcuse() { return statusAcuse; }
    public void setStatusAcuse(Integer statusAcuse) { this.statusAcuse = statusAcuse; }
    @Column(name = "ErrorMessageEdoc")
    public String getErrorMessageEdoc() { return errorMessageEdoc; }
    public void setErrorMessageEdoc(String errorMessageEdoc) { this.errorMessageEdoc = errorMessageEdoc; }
    @Column(name = "ErrorMessageAcuse")
    public String getErrorMessageAcuse() { return errorMessageAcuse; }
    public void setErrorMessageAcuse(String errorMessageAcuse) { this.errorMessageAcuse = errorMessageAcuse; }
    @Column(name = "RecordIndex")
    public Integer getRecordIndex() { return recordIndex; }
    public void setRecordIndex(Integer recordIndex) { this.recordIndex = recordIndex; }
    @Transient
    public Integer getIdCliente() { return idCliente; }
    public void setIdCliente(Integer idCliente) { this.idCliente = idCliente; }
    @Column(name = "IdSolicitudAcuse")
    public Integer getIdSolicitudAcuse() { return idSolicitudAcuse; }
    public void setIdSolicitudAcuse(Integer idSolicitudAcuse) { this.idSolicitudAcuse = idSolicitudAcuse; }
    @Column(name = "IdSolicitudEdoc")
    public Integer getIdSolicitudEdoc() { return idSolicitudEdoc; }
    public void setIdSolicitudEdoc(Integer idSolicitudEdoc) { this.idSolicitudEdoc = idSolicitudEdoc; }
    @Column(name = "PathEdoc")
    public String getPathEdoc() {
        return pathEdoc;
    }
    public void setPathEdoc(String pathEdoc) {
        this.pathEdoc = pathEdoc;
    }
    @Column(name = "PathAcuse")
    public String getPathAcuse() {
        return pathAcuse;
    }
    public void setPathAcuse(String pathAcuse) {
        this.pathAcuse = pathAcuse;
    }
}

