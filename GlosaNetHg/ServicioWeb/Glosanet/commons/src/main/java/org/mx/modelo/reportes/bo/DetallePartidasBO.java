package org.mx.modelo.reportes.bo;

import org.mx.modelo.reportes.po.DetallePartidasPO;

import javax.persistence.Column;
import java.sql.Timestamp;

public class DetallePartidasBO {
    private Integer rowIndex;
    private Timestamp fechaPagoReal;
    private Integer idDatastage;
    private String folio;
    private String patente;
    private String pedimento;
    private String seccionAduanera;
    private Integer secuenciaFraccion;
    private String paisCompradorVendedor;
    private String indentFiscalProveedor;
    private String proveedorMercancia;
    private Double valorAduana;
    private Double iva;
    private Double igi;

    public DetallePartidasBO() {}
    public DetallePartidasBO(Integer rowIndex, Integer idDatastage, String folio, String patente, String pedimento, String seccionAduanera, Integer secuenciaFraccion, String paisCompradorVendedor, String indentFiscalProveedor, String proveedorMercancia, Double valorAduana, Double iva, Double igi, Timestamp fechaPagoReal) {
        this.rowIndex = rowIndex;
        this.fechaPagoReal = fechaPagoReal;
        this.idDatastage = idDatastage;
        this.folio = folio;
        this.patente = patente;
        this.pedimento = pedimento;
        this.seccionAduanera = seccionAduanera;
        this.secuenciaFraccion = secuenciaFraccion;
        this.paisCompradorVendedor = paisCompradorVendedor;
        this.indentFiscalProveedor = indentFiscalProveedor;
        this.proveedorMercancia = proveedorMercancia;
        this.valorAduana = valorAduana;
        this.iva = iva;
        this.igi = igi;
    }
    public DetallePartidasBO (DetallePartidasPO po) {
        this.rowIndex = po.getRowIndex();
        this.fechaPagoReal = po.getFechaPagoReal();
        this.idDatastage = po.getIdDatastage();
        this.folio = po.getFolio();
        this.patente = po.getPatente();
        this.pedimento = po.getPedimento();
        this.seccionAduanera = po.getSeccionAduanera();
        this.secuenciaFraccion = po.getSecuenciaFraccion();
        this.paisCompradorVendedor = po.getPaisCompradorVendedor();
        this.indentFiscalProveedor = po.getIndentFiscalProveedor();
        this.proveedorMercancia = po.getProveedorMercancia();
        this.valorAduana = po.getValorAduana();
        this.iva = po.getIva();
        this.igi = po.getIgi();
    }

    public Integer getRowIndex() { return rowIndex; }
    public void setRowIndex(Integer rowIndex) { this.rowIndex = rowIndex; }

    public Timestamp getFechaPagoReal() { return fechaPagoReal; }
    public void setFechaPagoReal(Timestamp fechaPagoReal) { this.fechaPagoReal = fechaPagoReal; }

    public Integer getIdDatastage() { return idDatastage; }
    public void setIdDatastage(Integer idDatastage) { this.idDatastage = idDatastage; }

    public String getFolio() { return folio; }
    public void setFolio(String folio) { this.folio = folio; }

    public String getPatente() { return patente; }
    public void setPatente(String patente) { this.patente = patente; }

    public String getPedimento() { return pedimento; }
    public void setPedimento(String pedimento) { this.pedimento = pedimento; }

    public String getSeccionAduanera() { return seccionAduanera; }
    public void setSeccionAduanera(String seccionAduanera) { this.seccionAduanera = seccionAduanera; }

    public Integer getSecuenciaFraccion() { return secuenciaFraccion; }
    public void setSecuenciaFraccion(Integer secuenciaFraccion) { this.secuenciaFraccion = secuenciaFraccion; }

    public String getPaisCompradorVendedor() { return paisCompradorVendedor; }
    public void setPaisCompradorVendedor(String paisCompradorVendedor) { this.paisCompradorVendedor = paisCompradorVendedor; }

    public String getIndentFiscalProveedor() { return indentFiscalProveedor; }
    public void setIndentFiscalProveedor(String indentFiscalProveedor) { this.indentFiscalProveedor = indentFiscalProveedor; }

    public String getProveedorMercancia() { return proveedorMercancia; }
    public void setProveedorMercancia(String proveedorMercancia) { this.proveedorMercancia = proveedorMercancia; }

    public Double getValorAduana() { return valorAduana; }
    public void setValorAduana(Double valorAduana) { this.valorAduana = valorAduana; }

    public Double getIva() { return iva; }
    public void setIva(Double iva) { this.iva = iva; }

    public Double getIgi() { return igi; }
    public void setIgi(Double igi) { this.igi = igi; }
}
