package ryndem.mx.glosanet.messagebroker.datastage.vucem.model.po;

import javax.persistence.*;

import java.io.Serializable;
import java.sql.Timestamp;

import static ryndem.mx.glosanet.messagebroker.config.Protocols.VUCEM_ACUSE_COVE_MESSAGES_TABLE;

@Entity
@Table(name = VUCEM_ACUSE_COVE_MESSAGES_TABLE)
public class SolicitudAcuseFacturaPO implements Serializable {
    private static final long serialVersionUID = -5052579498580033046L;

    private Long id;
    private Long idReceipt; // FK_Facturas a primary de facturas 505
    private String idDocument; //NumeroFactura en 505
    private Long idCustomer;
    private Integer status;
    private Timestamp requestDate;
    private Timestamp lastUpdateDate;
    private String path;
    private String notes;
    private String errorMessage;
    private String stacktrace;

    public SolicitudAcuseFacturaPO() {
    }

    public SolicitudAcuseFacturaPO(Long idReceipt, String idDocument, Long idCustomer, Integer status, String path) {
        this.idReceipt = idReceipt;
        this.idDocument = idDocument;
        this.idCustomer = idCustomer;
        this.status = status;
        this.path = path;
        this.requestDate = new Timestamp(System.currentTimeMillis());
        this.lastUpdateDate = new Timestamp(System.currentTimeMillis());
    }

    //Se obtiene de PK_SolicitudAcuseFactura
    @Id @Column(name = "PK_SolicitudAcuseFactura") @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getIdReceipt() {
        return idReceipt;
    }
    public void setIdReceipt(Long idRequestCase) {
        this.idReceipt = idRequestCase;
    }
    public String getIdDocument() {
        return idDocument;
    }
    public void setIdDocument(String idDocument) {
        this.idDocument = idDocument;
    }
    public Long getIdCustomer() {
        return idCustomer;
    }
    public void setIdCustomer(Long idCustomer) {
        this.idCustomer = idCustomer;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public Timestamp getRequestDate() {
        return requestDate;
    }
    public void setRequestDate(Timestamp requestDate) {
        this.requestDate = requestDate;
    }
    public Timestamp getLastUpdateDate() {
        return lastUpdateDate;
    }
    public void setLastUpdateDate(Timestamp lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public String getStacktrace() {
        return stacktrace;
    }
    public void setStacktrace(String stacktrace) {
        this.stacktrace = stacktrace;
    }

    @Override
    public String toString() {
        return "SolicitudAcuseFacturaPO{" +
                "id=" + id +
                ", idRequestCase=" + idReceipt +
                ", idDocument='" + idDocument + '\'' +
                ", idCustomer=" + idCustomer +
                ", status=" + status +
                ", requestDate=" + requestDate +
                ", lastUpdateDate=" + lastUpdateDate +
                ", path='" + path + '\'' +
                ", notes='" + notes + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
