package org.mx.modelo.datastage.po;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Partidas_551")
public class PartidaCustomPO implements Serializable {

    private static final long serialVersionUID = 5366449969845530797L;
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

    public PartidaCustomPO() { }

    @Id
    @GeneratedValue
    @Column(name = "PK_Partida", unique = true, nullable = false)
    public Integer getIdPartida() {
        return idPartida;
    }
    public void setIdPartida(Integer idPartida) {
        this.idPartida = idPartida;
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

    @Column(name = "Fraccion")
    public String getFraccion() { return fraccion; }
    public void setFraccion(String fraccion) { this.fraccion = fraccion; }

    @Column(name = "SecuenciaFraccion")
    public Integer getSecuenciaFraccion() { return secuenciaFraccion; }
    public void setSecuenciaFraccion(Integer secuenciaFraccion) { this.secuenciaFraccion = secuenciaFraccion; }

    @Column(name = "SubdivisionFraccion")
    public Integer getSubdivisionFraccion() { return subdivisionFraccion; }
    public void setSubdivisionFraccion(Integer subdivisionFraccion) { this.subdivisionFraccion = subdivisionFraccion; }

    @Column(name = "SeccionAduanera", nullable = false, length = 3)
    public String getSeccionAduanera() {
        return seccionAduanera;
    }
    public void setSeccionAduanera(String seccionAduanera) {
        this.seccionAduanera = seccionAduanera;
    }

    @Column(name = "DescripcionMercancia")
    public String getDescripcionMercancia() {
        return descripcionMercancia;
    }
    public void setDescripcionMercancia(String descripcionMercancia) { this.descripcionMercancia = descripcionMercancia; }

    @Column(name = "ValorDolares", precision = 14)
    public Double getValorDolares() {
        return valorDolares;
    }
    public void setValorDolares(Double valorDolares) {
        this.valorDolares = valorDolares;
    }

    @Column(name = "PaisOrigenDestino", length = 3)
    public String getPaisOrigenDestino() {
        return paisOrigenDestino;
    }
    public void setPaisOrigenDestino(String paisOrigenDestino) {
        this.paisOrigenDestino = paisOrigenDestino;
    }

    @Column(name = "PaisCompradorVendedor", length = 3)
    public String getPaisCompradorVendedor() {
        return paisCompradorVendedor;
    }
    public void setPaisCompradorVendedor(String paisCompradorVendedor) { this.paisCompradorVendedor = paisCompradorVendedor; }

    @Column(name = "RecordIndex")
    public Integer getRecordIndex() {
        return recordIndex;
    }
    public void setRecordIndex(Integer recordIndex) {
        this.recordIndex = recordIndex;
    }
}
