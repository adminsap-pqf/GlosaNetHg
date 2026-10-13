package org.mx.modelo.datastage.bo;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "PartidaCustomBO")
public class PartidaCustomBO implements Serializable {

    private static final long serialVersionUID = 5344040846828516986L;
    private Integer idPartida;
    private String patente;
    private String pedimento;
    private String fraccion;
    private Integer secuenciaFraccion;
    private Integer subdivisionFraccion;
    private String seccionAduanera;
    private String descripcionMercancia;
    private Double valorDolares;
    private String paisOrigenDestino;
    private String paisCompradorVendedor;
    private Integer recordIndex;

    public PartidaCustomBO() { }

    public Integer getIdPartida() {
        return idPartida;
    }
    public void setIdPartida(Integer idPartida) {
        this.idPartida = idPartida;
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
    public String getFraccion() { return fraccion; }
    public void setFraccion(String fraccion) { this.fraccion = fraccion; }
    public Integer getSecuenciaFraccion() { return secuenciaFraccion; }
    public void setSecuenciaFraccion(Integer secuenciaFraccion) { this.secuenciaFraccion = secuenciaFraccion; }
    public Integer getSubdivisionFraccion() { return subdivisionFraccion; }
    public void setSubdivisionFraccion(Integer subdivisionFraccion) { this.subdivisionFraccion = subdivisionFraccion; }
    public String getSeccionAduanera() {
        return seccionAduanera;
    }
    public void setSeccionAduanera(String seccionAduanera) {
        this.seccionAduanera = seccionAduanera;
    }
    public String getDescripcionMercancia() {
        return descripcionMercancia;
    }
    public void setDescripcionMercancia(String descripcionMercancia) { this.descripcionMercancia = descripcionMercancia; }
    public Double getValorDolares() {
        return valorDolares;
    }
    public void setValorDolares(Double valorDolares) {
        this.valorDolares = valorDolares;
    }
    public String getPaisOrigenDestino() {
        return paisOrigenDestino;
    }
    public void setPaisOrigenDestino(String paisOrigenDestino) {
        this.paisOrigenDestino = paisOrigenDestino;
    }
    public String getPaisCompradorVendedor() {
        return paisCompradorVendedor;
    }
    public void setPaisCompradorVendedor(String paisCompradorVendedor) { this.paisCompradorVendedor = paisCompradorVendedor; }
    public Integer getRecordIndex() {
        return recordIndex;
    }
    public void setRecordIndex(Integer recordIndex) {
        this.recordIndex = recordIndex;
    }
}
