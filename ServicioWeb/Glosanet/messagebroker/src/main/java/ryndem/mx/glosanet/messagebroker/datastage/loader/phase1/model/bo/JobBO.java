package ryndem.mx.glosanet.messagebroker.datastage.loader.phase1.model.bo;

import java.sql.Timestamp;

public class JobBO {

    private static final long serialVersionUID = 8964562343167223457L;
    private String id;
    private String guid_job;
    private Integer status;
    private Integer idDownload;
    private Timestamp creationDate;
    private String stackTrace;


    public JobBO() {
        super();
    }

    public JobBO(String id, String guid_job, Integer status, Integer idDownload, Timestamp creationDate, String stackTrace) {
        super();
        this.id = id;
        this.guid_job = guid_job;
        this.status = status;
        this.idDownload = idDownload;
        this.creationDate = creationDate;
        this.stackTrace = stackTrace;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGuid_job() {
        return guid_job;
    }

    public void setGuid_job(String guid_job) {
        this.guid_job = guid_job;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIdDownload() {
        return idDownload;
    }

    public void setIdDownload(Integer idDownload) {
        this.idDownload = idDownload;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }
}
