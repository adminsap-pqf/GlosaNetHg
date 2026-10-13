package ryndem.mx.glosanet.messagebroker.datastage.loader.phase1.document;

import ryndem.mx.glosanet.messagebroker.Message;

public class JobMessage extends Message {

    private Integer idUser;
    private Integer idClient;
    private String rfc;
    private String filePath;
    private String idJob;
    private Integer idDownload;

    public JobMessage() {
    }

    public JobMessage(Integer idUser, Integer idClient, String idJob, Integer idDownload, String rfc, String filePath) {
        this.idUser = idUser;
        this.idClient = idClient;
        this.rfc = rfc;
        this.filePath = filePath;
        this.idJob = idJob;
        this.idDownload = idDownload;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    public String getIdJob() {
        return idJob;
    }

    public void setIdJob(String idJob) {
        this.idJob = idJob;
    }

    public Integer getIdDownload() {
        return idDownload;
    }

    public void setIdDownload(Integer idDownload) {
        this.idDownload = idDownload;
    }
}
