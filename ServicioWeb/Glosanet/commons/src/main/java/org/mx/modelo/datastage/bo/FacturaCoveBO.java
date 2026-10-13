package org.mx.modelo.datastage.bo;

public class FacturaCoveBO {

    private Integer idFactura;
    private Integer idCliente;
    private String pedimento;
    private String numeroFactura;

    public FacturaCoveBO() {
    }

    public Integer getIdFactura() {
        return idFactura;
    }
    public void setIdFactura(Integer idFactura) {
        this.idFactura = idFactura;
    }
    public Integer getIdCliente() { return idCliente; }
    public void setIdCliente(Integer idCliente) { this.idCliente = idCliente; }
    public String getNumeroFactura() {
        return numeroFactura;
    }
    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }
    public String getPedimento() {
        return pedimento;
    }
    public void setPedimento(String pedimento) {
        this.pedimento = pedimento;
    }
}
