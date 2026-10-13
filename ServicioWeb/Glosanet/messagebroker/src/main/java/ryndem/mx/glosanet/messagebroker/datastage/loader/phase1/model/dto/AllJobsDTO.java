package ryndem.mx.glosanet.messagebroker.datastage.loader.phase1.model.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
public class AllJobsDTO implements Serializable {

    private static final long serialVersionUID = -4052579498580033049L;
    private String PK_Fase1RMQMensaje;
    private String GUID;
    private Integer status;
    private Timestamp creationDate;
    private String NombreArchivo;
    private String Nombre;
    private String Apellido;
    private Integer RecordIndex;
    private String Error;
    private String stackTrace;

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

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public String getNombreArchivo() {
        return NombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        NombreArchivo = nombreArchivo;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public Integer getRecordIndex() {
        return RecordIndex;
    }

    public void setRecordIndex(Integer recordIndex) {
        RecordIndex = recordIndex;
    }

    public String getError() {
        return Error;
    }

    public void setError(String error) {
        Error = error;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }
}
