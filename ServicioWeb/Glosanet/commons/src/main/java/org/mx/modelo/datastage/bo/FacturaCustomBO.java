package org.mx.modelo.datastage.bo;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.sql.Timestamp;

@XmlRootElement(name = "FacturaCustomBO")
public class FacturaCustomBO implements Serializable {

    private static final long serialVersionUID = 7096438489529747603L;
    private Integer idFactura;
    private String patente;
    private String pedimento;
    private String seccionAduanera;
    private Timestamp fechaFacturacion;
    private String numeroFactura;
    private String terminoFacturacion; // INCOTERM
    private String proveedorMercancia;
    private String calleProveedor; // ProveedorMercanciaDetalle
    private Integer status;
    private String errorMessage;
    private Integer idSolicitudFactura;
    private String path;
    private Integer recordIndex;

    public FacturaCustomBO() { }

    public Integer getIdFactura() {
        return idFactura;
    }
    public void setIdFactura(Integer idFactura) {
        this.idFactura = idFactura;
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
    public Timestamp getFechaFacturacion() {
        return fechaFacturacion;
    }
    public void setFechaFacturacion(Timestamp fechaFacturacion) {
        this.fechaFacturacion = fechaFacturacion;
    }
    public String getNumeroFactura() {
        return numeroFactura;
    }
    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }
    public String getTerminoFacturacion() {
        return terminoFacturacion;
    }
    public void setTerminoFacturacion(String terminoFacturacion) {
        this.terminoFacturacion = terminoFacturacion;
    }
    public String getProveedorMercancia() {
        return proveedorMercancia;
    }
    public void setProveedorMercancia(String proveedorMercancia) {
        this.proveedorMercancia = proveedorMercancia;
    }
    public String getCalleProveedor() {
        return calleProveedor;
    }
    public void setCalleProveedor(String calleProveedor) {
        calleProveedor = calleProveedor;
    }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
    public Integer getIdSolicitudFactura() { return idSolicitudFactura; }
    public void setIdSolicitudFactura(Integer idSolicitudFactura) { this.idSolicitudFactura = idSolicitudFactura; }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public Integer getRecordIndex() {
        return recordIndex;
    }
    public void setRecordIndex(Integer recordIndex) {
        this.recordIndex = recordIndex;
    }

}
