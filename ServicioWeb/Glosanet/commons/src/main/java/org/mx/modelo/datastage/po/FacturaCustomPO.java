package org.mx.modelo.datastage.po;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "Facturas_505")
public class FacturaCustomPO implements Serializable {

    private static final long serialVersionUID = -6456793607521748364L;
    private Integer idFactura;
    private String patente;
    private String pedimento;
    private String seccionAduanera;
    private Timestamp fechaFacturacion;
    private String numeroFactura;
    private String terminoFacturacion; // INCONTERM
    private String proveedorMercancia;
    private String calleProveedor; // ProveedorMercanciaDetalle
    private Integer status;
    private String errorMessage;
    private Integer idSolicitudFactura;
    private String path;
    private Integer recordIndex;

    public FacturaCustomPO() { }

    @Id
    @GeneratedValue
    @Column(name = "PK_Facturas", unique = true, nullable = false)
    public Integer getIdFactura() {
        return idFactura;
    }
    public void setIdFactura(Integer idFactura) {
        this.idFactura = idFactura;
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
    @Column(name = "FechaFacturacion", length = 23)
    public Timestamp getFechaFacturacion() {
        return fechaFacturacion;
    }
    public void setFechaFacturacion(Timestamp fechaFacturacion) {
        this.fechaFacturacion = fechaFacturacion;
    }
    @Column(name = "NumeroFactura", length = 15)
    public String getNumeroFactura() {
        return numeroFactura;
    }
    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }
    @Column(name = "TerminoFacturacion", length = 3)
    public String getTerminoFacturacion() {
        return terminoFacturacion;
    }
    public void setTerminoFacturacion(String terminoFacturacion) {
        this.terminoFacturacion = terminoFacturacion;
    }
    @Column(name = "ProveedorMercancia")
    public String getProveedorMercancia() {
        return proveedorMercancia;
    }
    public void setProveedorMercancia(String proveedorMercancia) {
        this.proveedorMercancia = proveedorMercancia;
    }
    public String getCalleProveedor() {
        return calleProveedor;
    }
    @Column(name = "CalleProveedor")
    public void setCalleProveedor(String calleProveedor) {
        this.calleProveedor = calleProveedor;
    }
    @Column(name = "Status")
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    @Column(name = "ErrorMessage")
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
    @Column(name = "IdSolicitudFactura")
    public Integer getIdSolicitudFactura() { return idSolicitudFactura; }
    public void setIdSolicitudFactura(Integer idSolicitudFactura) { this.idSolicitudFactura = idSolicitudFactura; }
    @Column(name = "Path")
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    @Column(name = "RecordIndex")
    public Integer getRecordIndex() {
        return recordIndex;
    }
    public void setRecordIndex(Integer recordIndex) {
        this.recordIndex = recordIndex;
    }
}
