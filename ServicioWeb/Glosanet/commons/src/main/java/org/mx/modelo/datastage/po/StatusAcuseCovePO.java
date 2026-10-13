package org.mx.modelo.datastage.po;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "GN_Vucem_AcuseCove")
public class StatusAcuseCovePO implements Serializable {
    private static final long serialVersionUID = -8126817390570503668L;

    private Integer idReceipt;
    private Integer status;
    private String errorMessage;
    private String path;

    public StatusAcuseCovePO() { }
    public StatusAcuseCovePO(Integer idReceipt, Integer status, String errorMessage, String path) {
        this.idReceipt = idReceipt;
        this.status = status;
        this.errorMessage = errorMessage;
        this.path = path;
    }

    @Id
    @GeneratedValue
    @Column(name = "IdReceipt", unique = true, nullable = false)
    public Integer getIdReceipt() { return idReceipt; }
    public void setIdReceipt(Integer idFactura) { this.idReceipt = idFactura; }

    @Column(name = "Status")
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    @Column(name = "ErrorMessage")
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

    @Column(name = "Path")
    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
}
