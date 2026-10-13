package org.mx.modelo.datastage.bo;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "CasosPedimentoCustomBO")
public class CasosPedimentoCustomBO implements Serializable {

    private static final long serialVersionUID = 1811463999883475387L;
    private Integer idCasosPedimento;
    private Integer idCliente;
    private String patente;
    private String pedimento;
    private String seccionAduanera;
    private String claveCaso;
    private String identificadorCaso;
    private Integer statusEdoc;
    private Integer statusAcuse;
    private String errorMessageEdoc;
    private String errorMessageAcuse;
    private Integer recordIndex;
    private Integer idSolicitudEdoc;
    private Integer idSolicitudAcuse;
    private String pathEdoc;
    private String pathAcuse;

    public CasosPedimentoCustomBO() { }

    public Integer getIdCasosPedimento() {
        return idCasosPedimento;
    }
    public void setIdCasosPedimento(Integer idCasosPedimento) {
        this.idCasosPedimento = idCasosPedimento;
    }
    public Integer getIdCliente() { return idCliente; }
    public void setIdCliente(Integer idCliente) { this.idCliente = idCliente; }
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
    public String getClaveCaso() {
        return claveCaso;
    }
    public void setClaveCaso(String claveCaso) {
        this.claveCaso = claveCaso;
    }
    public String getIdentificadorCaso() {
        return identificadorCaso;
    }
    public void setIdentificadorCaso(String identificadorCaso) {
        this.identificadorCaso = identificadorCaso;
    }
    public Integer getStatusEdoc() { return statusEdoc; }
    public void setStatusEdoc(Integer statusEdoc) { this.statusEdoc = statusEdoc; }
    public Integer getStatusAcuse() { return statusAcuse; }
    public void setStatusAcuse(Integer statusAcuse) { this.statusAcuse = statusAcuse; }
    public String getErrorMessageEdoc() { return errorMessageEdoc; }
    public void setErrorMessageEdoc(String errorMessageEdoc) { this.errorMessageEdoc = errorMessageEdoc; }
    public String getErrorMessageAcuse() { return errorMessageAcuse; }
    public void setErrorMessageAcuse(String errorMessageAcuse) { this.errorMessageAcuse = errorMessageAcuse; }
    public Integer getRecordIndex() {
        return recordIndex;
    }
    public void setRecordIndex(Integer recordIndex) {
        this.recordIndex = recordIndex;
    }
    public Integer getIdSolicitudEdoc() { return idSolicitudEdoc; }
    public void setIdSolicitudEdoc(Integer idSolicitudEdoc) { this.idSolicitudEdoc = idSolicitudEdoc; }
    public Integer getIdSolicitudAcuse() { return idSolicitudAcuse; }
    public void setIdSolicitudAcuse(Integer idSolicitudAcuse) { this.idSolicitudAcuse = idSolicitudAcuse; }
    public String getPathEdoc() {
        return pathEdoc;
    }
    public void setPathEdoc(String pathEdoc) {
        this.pathEdoc = pathEdoc;
    }
    public String getPathAcuse() {
        return pathAcuse;
    }
    public void setPathAcuse(String pathAcuse) {
        this.pathAcuse = pathAcuse;
    }
}
