package org.mx.modelo.reportes.po;

import org.mx.modelo.reportes.bo.DetallePartidasBO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class DetallePartidasPO {
    private static final long serialVersionUID = 1578425558442184071L;
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

    public DetallePartidasPO() {}
    public DetallePartidasPO(Integer rowIndex, Integer idDatastage, String folio, String patente, String pedimento, String seccionAduanera, Integer secuenciaFraccion, String paisCompradorVendedor, String indentFiscalProveedor, String proveedorMercancia, Double valorAduana, Double iva, Double igi, Timestamp fechaPagoReal) {
        this.rowIndex = rowIndex;
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
        this.fechaPagoReal = fechaPagoReal;
    }
    public DetallePartidasPO (DetallePartidasBO bo) {
        this.rowIndex = bo.getRowIndex();
        this.fechaPagoReal = bo.getFechaPagoReal();
        this.idDatastage = bo.getIdDatastage();
        this.folio = bo.getFolio();
        this.patente = bo.getPatente();
        this.pedimento = bo.getPedimento();
        this.seccionAduanera = bo.getSeccionAduanera();
        this.secuenciaFraccion = bo.getSecuenciaFraccion();
        this.paisCompradorVendedor = bo.getPaisCompradorVendedor();
        this.indentFiscalProveedor = bo.getIndentFiscalProveedor();
        this.proveedorMercancia = bo.getProveedorMercancia();
        this.valorAduana = bo.getValorAduana();
        this.iva = bo.getIva();
        this.igi = bo.getIgi();
    }

    @Id
    @Column(name = "RowIndex")
    public Integer getRowIndex() { return rowIndex; }
    public void setRowIndex(Integer rowIndex) { this.rowIndex = rowIndex; }

    @Column(name = "FechaPagoReal")
    public Timestamp getFechaPagoReal() { return fechaPagoReal; }
    public void setFechaPagoReal(Timestamp fechaPagoReal) { this.fechaPagoReal = fechaPagoReal; }

    @Column(name = "FK_Datastage")
    public Integer getIdDatastage() { return idDatastage; }
    public void setIdDatastage(Integer idDatastage) { this.idDatastage = idDatastage; }

    @Column(name = "Folio")
    public String getFolio() { return folio; }
    public void setFolio(String folio) { this.folio = folio; }

    @Column(name = "Patente")
    public String getPatente() { return patente; }
    public void setPatente(String patente) { this.patente = patente; }

    @Column(name = "Pedimento")
    public String getPedimento() { return pedimento; }
    public void setPedimento(String pedimento) { this.pedimento = pedimento; }

    @Column(name = "SeccionAduanera")
    public String getSeccionAduanera() { return seccionAduanera; }
    public void setSeccionAduanera(String seccionAduanera) { this.seccionAduanera = seccionAduanera; }

    @Column(name = "SecuenciaFraccion")
    public Integer getSecuenciaFraccion() { return secuenciaFraccion; }
    public void setSecuenciaFraccion(Integer secuenciaFraccion) { this.secuenciaFraccion = secuenciaFraccion; }

    @Column(name = "PaisCompradorVendedor")
    public String getPaisCompradorVendedor() { return paisCompradorVendedor; }
    public void setPaisCompradorVendedor(String paisCompradorVendedor) { this.paisCompradorVendedor = paisCompradorVendedor; }

    @Column(name = "IndentFiscalProveedor")
    public String getIndentFiscalProveedor() { return indentFiscalProveedor; }
    public void setIndentFiscalProveedor(String indentFiscalProveedor) { this.indentFiscalProveedor = indentFiscalProveedor; }

    @Column(name = "ProveedorMercancia")
    public String getProveedorMercancia() { return proveedorMercancia; }
    public void setProveedorMercancia(String proveedorMercancia) { this.proveedorMercancia = proveedorMercancia; }

    @Column(name = "ValorAduana")
    public Double getValorAduana() { return valorAduana; }
    public void setValorAduana(Double valorAduana) { this.valorAduana = valorAduana; }

    @Column(name = "IVA")
    public Double getIva() { return iva; }
    public void setIva(Double iva) { this.iva = iva; }

    @Column(name = "IGI")
    public Double getIgi() { return igi; }
    public void setIgi(Double igi) { this.igi = igi; }
}
