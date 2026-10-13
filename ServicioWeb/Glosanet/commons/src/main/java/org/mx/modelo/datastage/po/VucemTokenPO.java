package org.mx.modelo.datastage.po;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "GN_Vucem_Token")
public class VucemTokenPO implements Serializable {


    private static final long serialVersionUID = 425030895612331323L;
    private Long pkToken;
    private String token;
    private Long idCliente;
    private Timestamp lastUpdateDate;
    private boolean activo;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_Token", unique = true, nullable = false)
    public Long getPkToken() {
        return pkToken;
    }
    public void setPkToken(Long pkToken) {
        this.pkToken = pkToken;
    }

    @Basic
    @Column(name = "Token")
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    @Basic
    @Column(name = "FK01_Cliente")
    public Long getIdCliente() {
        return idCliente;
    }
    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    @Basic
    @Column(name = "LastUpdateDate")
    public Timestamp getLastUpdateDate() {
        return lastUpdateDate;
    }
    public void setLastUpdateDate(Timestamp lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Basic
    @Column(name = "Activo")
    public boolean isActivo() {
        return activo;
    }
    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VucemTokenPO that = (VucemTokenPO) o;
        return Objects.equals(pkToken, that.pkToken) &&
                token.equals(that.token) &&
                Objects.equals(idCliente, that.idCliente) &&
                Objects.equals(activo, that.activo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pkToken, token, idCliente, activo);
    }
}
