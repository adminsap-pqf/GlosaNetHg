package org.mx.modelo.datastage.bo;

import java.sql.Timestamp;

public class VucemTokenBO {

    private Long pkToken;
    private String token;
    private Long idCliente;
    private Timestamp lastUpdateDate;
    private boolean activo;

    public Long getPkToken() {
        return pkToken;
    }
    public void setPkToken(Long pkToken) {
        this.pkToken = pkToken;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public Long getIdCliente() {
        return idCliente;
    }
    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }
    public Timestamp getLastUpdateDate() {
        return lastUpdateDate;
    }
    public void setLastDate(Timestamp lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}
