package org.mx.controllers.datastage;

import org.mx.modelo.datastage.bo.CasosPedimentoCustomBO;
import org.mx.modelo.datastage.bo.FacturaCoveBO;
import ryndem.mx.glosanet.messagebroker.VucemDocuments;

public class CustomLoadRequestOptions  {
    private Integer idSolicitud;
    private Integer idFactura;
    private Integer idCasoPedimento;
    private String numeroFactura;
    private String casoPedimento;

    private FacturaCoveBO facturaBO;
    private CasosPedimentoCustomBO casosPedimentoBO;
    private boolean isDocumented;
    private boolean isRequested;
    private VucemDocuments documentType;

    public Integer getIdSolicitud() { return idSolicitud; }
    public void setIdSolicitud(Integer idSolicitud) { this.idSolicitud = idSolicitud; }
    public Integer getIdFactura() { return idFactura; }
    public void setIdFactura(Integer idFactura) { this.idFactura = idFactura; }
    public Integer getIdCasoPedimento() { return idCasoPedimento; }
    public void setIdCasoPedimento(Integer idCasoPedimento) { this.idCasoPedimento = idCasoPedimento; }
    public String getNumeroFactura() { return numeroFactura; }
    public void setNumeroFactura(String numeroFactura) { this.numeroFactura = numeroFactura; }
    public String getCasoPedimento() { return casoPedimento; }
    public void setCasoPedimento(String casoPedimento) { this.casoPedimento = casoPedimento; }
    public boolean isDocumented() { return isDocumented; }
    public void setDocumented(boolean documented) { isDocumented = documented; }
    public boolean isRequested() { return isRequested; }
    public void setRequested(boolean requested) { isRequested = requested; }
    public VucemDocuments getDocumentType() { return documentType; }
    public void setDocumentType(VucemDocuments documentType) { this.documentType = documentType; }
    public FacturaCoveBO getFacturaBO() { return facturaBO; }
    public void setFacturaPO(FacturaCoveBO facturaBO) {
        this.facturaBO = facturaBO;
    }
    public CasosPedimentoCustomBO getCasosPedimentoBO() { return casosPedimentoBO; }
    public void setCasosPedimentoBO(CasosPedimentoCustomBO casosPedimentoBO) { this.casosPedimentoBO = casosPedimentoBO; }
}
