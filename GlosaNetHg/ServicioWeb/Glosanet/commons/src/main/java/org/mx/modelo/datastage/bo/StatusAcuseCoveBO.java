package org.mx.modelo.datastage.bo;

import org.mx.modelo.datastage.po.StatusAcuseCovePO;

public class StatusAcuseCoveBO {

    private Integer idFactura;
    private Integer status;
    private String errorMessage;
    private String path;

    public StatusAcuseCoveBO() { }
    public StatusAcuseCoveBO(Integer idFactura, Integer status, String errorMessage, String path) {
        this.idFactura = idFactura;
        this.status = status;
        this.errorMessage = errorMessage;
        this.path = path;
    }
    public StatusAcuseCoveBO(StatusAcuseCovePO po) {
        this.idFactura = po.getIdReceipt();
        this.status = po.getStatus();
        this.errorMessage = po.getErrorMessage();
        this.path = po.getPath();
    }

    public Integer getIdFactura() { return idFactura; }
    public void setIdFactura(Integer idFactura) { this.idFactura = idFactura; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
}
