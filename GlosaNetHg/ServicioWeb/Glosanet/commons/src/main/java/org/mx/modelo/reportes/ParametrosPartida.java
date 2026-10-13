package org.mx.modelo.reportes;

import org.mx.modelo.comun.bo.UsuarioBO;

public class ParametrosPartida {
    private Integer idDatastage;
    private String patente;
    private String pedimento;
    private String seccionAduanera;


    public ParametrosPartida() {}
    public ParametrosPartida(Integer idDatastage, String patente, String pedimento, String seccionAduanera) {
        this.idDatastage = idDatastage;
        this.patente = patente;
        this.pedimento = pedimento;
        this.seccionAduanera = seccionAduanera;
    }

    public Integer getIdDatastage() { return idDatastage; }
    public void setIdDatastage(Integer idDatastage) { this.idDatastage = idDatastage; }

    public String getPatente() { return patente; }
    public void setPatente(String patente) { this.patente = patente; }

    public String getPedimento() { return pedimento; }
    public void setPedimento(String pedimento) { this.pedimento = pedimento; }

    public String getSeccionAduanera() { return seccionAduanera; }
    public void setSeccionAduanera(String seccionAduanera) { this.seccionAduanera = seccionAduanera; }
}
