package org.mx.modelo.reportes.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class DetalleContribucionesPO {
    private static final long serialVersionUID = 8242413982690543694L;
    private Integer rowIndex;
    private Timestamp fechaPagoReal;
    private Integer idDatastage;
    private String folio;
    private String patente;
    private String pedimento;
    private String seccionAduanera;
    private String tipoOperacion;
    private String tipoPedimento;
    private String claveDocumento;
    private Double dta;
    private Double prv;
    private Double cnt;
    private Double otros_imp;
    private Double recargos;
    private Double iva;
    private Double igi;
    private Double total;

    public DetalleContribucionesPO() {
    }
    public DetalleContribucionesPO(Integer rowIndex, Timestamp fechaPagoReal, Integer idDatastage, String folio, String patente, String pedimento, String seccionAduanera, String tipoOperacion, String tipoPedimento, String claveDocumento, Double dta, Double prv, Double cnt, Double otros_imp, Double recargos, Double iva, Double igi, Double total) {
        this.rowIndex = rowIndex;
        this.fechaPagoReal = fechaPagoReal;
        this.idDatastage = idDatastage;
        this.folio = folio;
        this.patente = patente;
        this.pedimento = pedimento;
        this.seccionAduanera = seccionAduanera;
        this.tipoOperacion = tipoOperacion;
        this.tipoPedimento = tipoPedimento;
        this.claveDocumento = claveDocumento;
        this.dta = dta;
        this.prv = prv;
        this.cnt = cnt;
        this.otros_imp = otros_imp;
        this.recargos = recargos;
        this.iva = iva;
        this.igi = igi;
        this.total = total;
    }

    @Id
    @Column(name="RowIndex")
    public Integer getRowIndex() { return rowIndex; }
    public void setRowIndex(Integer rowIndex) { this.rowIndex = rowIndex; }

    @Column(name="FechaPagoReal")
    public Timestamp getFechaPagoReal() { return fechaPagoReal; }
    public void setFechaPagoReal(Timestamp fechaPagoReal) { this.fechaPagoReal = fechaPagoReal; }

    @Column(name="IdDatastage")
    public Integer getIdDatastage() { return idDatastage; }
    public void setIdDatastage(Integer idDatastage) { this.idDatastage = idDatastage; }

    @Column(name="Folio")
    public String getFolio() { return folio; }
    public void setFolio(String folio) { this.folio = folio; }

    @Column(name="Patente")
    public String getPatente() { return patente; }
    public void setPatente(String patente) { this.patente = patente; }

    @Column(name="Pedimento")
    public String getPedimento() { return pedimento; }
    public void setPedimento(String pedimento) { this.pedimento = pedimento; }

    @Column(name="SeccionAduanera")
    public String getSeccionAduanera() { return seccionAduanera; }
    public void setSeccionAduanera(String seccionAduanera) { this.seccionAduanera = seccionAduanera; }

    @Column(name="TipoOperacion")
    public String getTipoOperacion() { return tipoOperacion; }
    public void setTipoOperacion(String tipoOperacion) { this.tipoOperacion = tipoOperacion; }

    @Column(name="TipoPedimento")
    public String getTipoPedimento() { return tipoPedimento; }
    public void setTipoPedimento(String tipoPedimento) { this.tipoPedimento = tipoPedimento; }

    @Column(name="ClaveDocumento")
    public String getClaveDocumento() { return claveDocumento; }
    public void setClaveDocumento(String claveDocumento) { this.claveDocumento = claveDocumento; }

    @Column(name="DTA")
    public Double getDta() { return dta; }
    public void setDta(Double dta) { this.dta = dta; }

    @Column(name="PRV")
    public Double getPrv() { return prv; }
    public void setPrv(Double prv) { this.prv = prv; }

    @Column(name="CNT")
    public Double getCnt() { return cnt; }
    public void setCnt(Double cnt) { this.cnt = cnt; }

    @Column(name="OTROS")
    public Double getOtros_imp() { return otros_imp; }
    public void setOtros_imp(Double otros_imp) { this.otros_imp = otros_imp; }

    @Column(name="RECARGOS")
    public Double getRecargos() { return recargos; }
    public void setRecargos(Double recargos) { this.recargos = recargos; }

    @Column(name="IVA")
    public Double getIva() { return iva; }
    public void setIva(Double iva) { this.iva = iva; }

    @Column(name="IGI")
    public Double getIgi() { return igi; }
    public void setIgi(Double igi) { this.igi = igi; }

    @Column(name="TOTAL")
    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }
}