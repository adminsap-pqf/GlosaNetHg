package org.mx.modelo.reportes.bo;

import org.mx.modelo.reportes.po.DetalleContribucionesPO;

import java.sql.Timestamp;

public class DetalleContribucionesBO {
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

    public DetalleContribucionesBO() {
    }
    public DetalleContribucionesBO(Integer rowIndex, Timestamp fechaPagoReal, Integer idDatastage, String folio, String patente, String pedimento, String seccionAduanera, String tipoOperacion, String tipoPedimento, String claveDocumento, Double dta, Double prv, Double cnt, Double otros_imp, Double recargos, Double iva, Double igi, Double total) {
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
    public DetalleContribucionesBO(DetalleContribucionesPO detalle) {
        this.rowIndex = detalle.getRowIndex();
        this.fechaPagoReal = detalle.getFechaPagoReal();
        this.idDatastage = detalle.getIdDatastage();
        this.folio = detalle.getFolio();
        this.patente = detalle.getPatente();
        this.pedimento = detalle.getPedimento();
        this.seccionAduanera = detalle.getSeccionAduanera();
        this.tipoOperacion = detalle.getTipoOperacion();
        this.tipoPedimento = detalle.getTipoPedimento();
        this.claveDocumento = detalle.getClaveDocumento();
        this.dta = detalle.getDta();
        this.prv = detalle.getPrv();
        this.cnt = detalle.getCnt();
        this.otros_imp = detalle.getOtros_imp();
        this.recargos = detalle.getRecargos();
        this.iva = detalle.getIva();
        this.igi = detalle.getIgi();
        this.total = detalle.getTotal();
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

    public String getTipoOperacion() { return tipoOperacion; }
    public void setTipoOperacion(String tipoOperacion) { this.tipoOperacion = tipoOperacion; }

    public String getTipoPedimento() { return tipoPedimento; }
    public void setTipoPedimento(String tipoPedimento) { this.tipoPedimento = tipoPedimento; }

    public String getClaveDocumento() { return claveDocumento; }
    public void setClaveDocumento(String claveDocumento) { this.claveDocumento = claveDocumento; }

    public Double getDta() { return dta; }
    public void setDta(Double dta) { this.dta = dta; }

    public Double getPrv() { return prv; }
    public void setPrv(Double prv) { this.prv = prv; }

    public Double getCnt() { return cnt; }
    public void setCnt(Double cnt) { this.cnt = cnt; }

    public Double getOtros_imp() { return otros_imp; }
    public void setOtros_imp(Double otros_imp) { this.otros_imp = otros_imp; }

    public Double getRecargos() { return recargos; }
    public void setRecargos(Double recargos) { this.recargos = recargos; }

    public Double getIva() { return iva; }
    public void setIva(Double iva) { this.iva = iva; }

    public Double getIgi() { return igi; }
    public void setIgi(Double igi) { this.igi = igi; }

    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }
}
