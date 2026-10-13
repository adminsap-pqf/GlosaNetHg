package org.mx.modelo.datastage.po;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class CasosPedimentoIDCasoPO {

    private static final long serialVersionUID = -5672333485987672265L;
    private Integer idCasosPedimento;
    private Integer idCliente;
    private String identificadorCaso;
    private String pedimento;

    public CasosPedimentoIDCasoPO() {
    }

    @Id
    @GeneratedValue
    @Column(name = "PK_Casos_Pedimento", unique = true, nullable = false)
    public Integer getIdCasosPedimento() {
        return idCasosPedimento;
    }
    public void setIdCasosPedimento(Integer idCasosPedimento) {
        this.idCasosPedimento = idCasosPedimento;
    }
    @Column(name = "IdentificadorCaso", length = 20)
    public String getIdentificadorCaso() {
        return identificadorCaso;
    }
    public void setIdentificadorCaso(String identificadorCaso) {
        this.identificadorCaso = identificadorCaso;
    }
    public Integer getIdCliente() { return idCliente; }
    public void setIdCliente(Integer idCliente) { this.idCliente = idCliente; }
    public String getPedimento() { return pedimento; }
    public void setPedimento(String pedimento) { this.pedimento = pedimento; }
}
