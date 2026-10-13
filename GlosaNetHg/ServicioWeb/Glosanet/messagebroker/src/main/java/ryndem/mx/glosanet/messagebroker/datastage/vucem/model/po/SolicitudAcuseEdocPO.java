package ryndem.mx.glosanet.messagebroker.datastage.vucem.model.po;

import javax.persistence.*;

import java.io.Serializable;
import java.sql.Timestamp;

import static ryndem.mx.glosanet.messagebroker.config.Protocols.VUCEM_ACUSE_EDOC_MESSAGES_TABLE;

@Entity
@Table(name = VUCEM_ACUSE_EDOC_MESSAGES_TABLE)
public class SolicitudAcuseEdocPO implements Serializable {
    private static final long serialVersionUID = 3437346270660230721L;

    private Long id;
    private String idDocument;
    private Long idCustomer;
    private Long idRequestCase;
    private Integer status;
    private Timestamp requestDate;
    private Timestamp lastUpdateDate;
    private String path;
    private String notes;
    private String errorMessage;
    private String stacktrace;

    public SolicitudAcuseEdocPO(Long id, String idDocument, Long idCustomer, Long idRequestCase, Integer status, Timestamp requestDate, Timestamp lastUpdateDate, String path, String notes, String errorMessage, String stacktrace) {
        this.id = id;
        this.idDocument = idDocument;
        this.idCustomer = idCustomer;
        this.status = status;
        this.requestDate = requestDate;
        this.lastUpdateDate = lastUpdateDate;
        this.path = path;
        this.notes = notes;
        this.errorMessage = errorMessage;
        this.stacktrace = stacktrace;
        this.idRequestCase = idRequestCase;
    }

    public SolicitudAcuseEdocPO(Long idRequestCase, String idDocument, Long idCustomer, Integer status, String path) {
        this.idDocument = idDocument;
        this.idCustomer = idCustomer;
        this.idRequestCase = idRequestCase;
        this.status = status;
        this.path = path;
        this.requestDate = new Timestamp(System.currentTimeMillis());
        this.lastUpdateDate = new Timestamp(System.currentTimeMillis());
    }

    public SolicitudAcuseEdocPO() {
    }

    //Se obtiene de PK_SolicitudAcuseEdoc
    @Id
    @Column(name = "PK_SolicitudAcuseEdoc")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public Long getIdRequestCase() {
        return idRequestCase;
    }
    public void setIdRequestCase(Long idCaseRequest) {
        this.idRequestCase = idCaseRequest;
    }

    @Override
    public String toString() {
        return "SolicitudAcuseEdocPO{" +
                "id=" + id +
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
