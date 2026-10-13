package org.mx.modelo.datastage.bo;

import org.mx.modelo.datastage.po.StatusAcuseEdocPO;

public class StatusAcuseEdocBO {
    private Integer idCasosPedimento;
    private Integer statusAcuse;
    private String errorMessageAcuse;
    private String pathAcuse;

    public StatusAcuseEdocBO() { }
    public StatusAcuseEdocBO(Integer idCasosPedimento, Integer statusAcuse, String errorMessageAcuse, String pathAcuse) {
        this.idCasosPedimento = idCasosPedimento;
        this.statusAcuse = statusAcuse;
        this.errorMessageAcuse = errorMessageAcuse;
        this.pathAcuse = pathAcuse;
    }
    public StatusAcuseEdocBO(StatusAcuseEdocPO po) {
        this.idCasosPedimento = po.getIdRequestCase();
        this.statusAcuse = po.getStatus();
        this.errorMessageAcuse = po.getErrorMessage();
        this.pathAcuse = po.getPath();
    }

    public Integer getIdCasosPedimento() {
        return idCasosPedimento;
    }
    public void setIdCasosPedimento(Integer idCasosPedimento) { this.idCasosPedimento = idCasosPedimento; }
    public Integer getStatusAcuse() { return statusAcuse; }
    public void setStatusAcuse(Integer statusAcuse) { this.statusAcuse = statusAcuse; }
    public String getErrorMessageAcuse() { return errorMessageAcuse; }
    public void setErrorMessageAcuse(String errorMessageAcuse) { this.errorMessageAcuse = errorMessageAcuse; }
    public String getPathAcuse() {
        return pathAcuse;
    }
    public void setPathAcuse(String pathAcuse) {
        this.pathAcuse = pathAcuse;
    }
}

