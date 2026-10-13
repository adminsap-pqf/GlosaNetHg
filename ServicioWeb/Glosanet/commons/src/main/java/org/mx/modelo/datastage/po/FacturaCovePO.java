package org.mx.modelo.datastage.po;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

//@Entity
//@Table(name = "Facturas_505")
public class FacturaCovePO implements Serializable {

    private static final long serialVersionUID = -2099429103018883953L;

    private Integer idFactura;
    private Integer idCliente;
    private String pedimento;
    private String numeroFactura;

    public FacturaCovePO() {
    }

    public Integer getIdFactura() {
        return idFactura;
    }
    public void setIdFactura(Integer idFactura) {
        this.idFactura = idFactura;
    }
    public String getPedimento() {
        return pedimento;
    }
    public void setPedimento(String pedimento) {
        this.pedimento = pedimento;
    }
    public String getNumeroFactura() {
        return numeroFactura;
    }
    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }
    public Integer getIdCliente() { return idCliente; }
    public void setIdCliente(Integer idCliente) { this.idCliente = idCliente; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FacturaCovePO that = (FacturaCovePO) o;
        return idFactura.equals(that.idFactura) &&
                Objects.equals(idCliente, that.idCliente) &&
                pedimento.equals(that.pedimento) &&
                Objects.equals(numeroFactura, that.numeroFactura);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idFactura, idCliente, pedimento, numeroFactura);
    }
}
