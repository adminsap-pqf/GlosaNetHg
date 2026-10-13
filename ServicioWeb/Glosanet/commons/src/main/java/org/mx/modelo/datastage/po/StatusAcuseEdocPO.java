package org.mx.modelo.datastage.po;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "GN_Vucem_AcuseEdoc")
public class StatusAcuseEdocPO implements Serializable {
    private static final long serialVersionUID = -4105959175319893209L;

    private Integer idRequestCase;
    private Integer status;
    private String errorMessage;
    private String path;

    public StatusAcuseEdocPO() { }

    @Id
    @GeneratedValue
    @Column(name = "IdRequestCase", unique = true, nullable = false)
    public Integer getIdRequestCase() {
        return idRequestCase;
    }
    public void setIdRequestCase(Integer idCasosPedimento) { this.idRequestCase = idCasosPedimento; }
    @Column(name = "Status")
    public Integer getStatus() { return status; }
    public void setStatus(Integer statusAcuse) { this.status = statusAcuse; }
    @Column(name = "ErrorMessage")
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessageAcuse) { this.errorMessage = errorMessageAcuse; }
    @Column(name = "Path")
    public String getPath() {
        return path;
    }
    public void setPath(String pathAcuse) {
        this.path = pathAcuse;
    }
}

