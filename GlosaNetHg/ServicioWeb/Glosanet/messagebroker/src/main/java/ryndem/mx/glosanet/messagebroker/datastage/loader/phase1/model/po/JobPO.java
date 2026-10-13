package ryndem.mx.glosanet.messagebroker.datastage.loader.phase1.model.po;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

import static ryndem.mx.glosanet.messagebroker.config.Protocols.PHASE1_MESSAGES_TABLE;

@Entity
@Table(name = PHASE1_MESSAGES_TABLE)
public class JobPO implements Serializable {

    private static final long serialVersionUID = -5052579498580033048L;
    private String PK_Fase1RMQMensaje;
    private String GUID;
    private Integer status;
    private Integer FK01_Descarga;
    private Timestamp creationDate;
    private String stackTrace;


    public JobPO() {
    }

    public JobPO(String id, String guid_job, Integer status, Integer idDescarga) {
        this.PK_Fase1RMQMensaje = id;
        this.GUID = guid_job;
        this.status = status;
        this.FK01_Descarga = idDescarga;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public String getPK_Fase1RMQMensaje() {
        return PK_Fase1RMQMensaje;
    }

    public void setPK_Fase1RMQMensaje(String PK_Fase1RMQMensaje) {
        this.PK_Fase1RMQMensaje = PK_Fase1RMQMensaje;
    }

    public String getGUID() {
        return GUID;
    }

    public void setGUID(String GUID) {
        this.GUID = GUID;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getFK01_Descarga() {
        return FK01_Descarga;
    }

    public void setFK01_Descarga(Integer FK01_Descarga) {
        this.FK01_Descarga = FK01_Descarga;
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
