package org.mx.modelo.datastage.bo;

public class CasosPedimentoIDCasoBO {

    private Integer idCasosPedimento;
    private String patente;
    private String pedimento;
    private String seccionAduanera;
    private String claveCaso;
    private String identificadorCaso;

    public CasosPedimentoIDCasoBO() {
    }

    public Integer getIdCasosPedimento() {
        return idCasosPedimento;
    }

    public void setIdCasosPedimento(Integer idCasosPedimento) {
        this.idCasosPedimento = idCasosPedimento;
    }

    public String getIdentificadorCaso() {
        return identificadorCaso;
    }

    public void setIdentificadorCaso(String identificadorCaso) {
        this.identificadorCaso = identificadorCaso;
    }
}
