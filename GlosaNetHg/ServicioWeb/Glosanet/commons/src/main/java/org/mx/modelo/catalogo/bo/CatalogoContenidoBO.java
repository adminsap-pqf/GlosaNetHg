package org.mx.modelo.catalogo.bo;

import java.util.Objects;

public class CatalogoContenidoBO {
    private Integer Id;
    private Integer FK_Catalogo;
    private Integer Posicion;
    private String Clave;
    private String ClaveReporte;
    private String Nombre;
    private String DescripcionBreve;
    private String DescripcionExtensa;
    private String Tooltip;
    private boolean Predeterminado;

    public CatalogoContenidoBO() {
    }
    public CatalogoContenidoBO(Integer Id, Integer FK_Catalogo, Integer posicion, String clave, String claveReporte, String nombre, String descripcionBreve, String descripcionExtensa, String tooltip, boolean predeterminado) {
        this.Id = Id;
        this.FK_Catalogo = FK_Catalogo;
        Posicion = posicion;
        Clave = clave;
        ClaveReporte = claveReporte;
        Nombre = nombre;
        DescripcionBreve = descripcionBreve;
        DescripcionExtensa = descripcionExtensa;
        Tooltip = tooltip;
        Predeterminado = predeterminado;
    }

    public Integer getId() { return Id; }
    public void setId(Integer id) { this.Id = id; }

    public Integer getFK_Catalogo() { return FK_Catalogo; }
    public void setFK_Catalogo(Integer FK_Catalogo) { this.FK_Catalogo = FK_Catalogo; }

    public Integer getPosicion() { return Posicion; }
    public void setPosicion(Integer posicion) { Posicion = posicion; }

    public String getClave() { return Clave; }
    public void setClave(String clave) { Clave = clave; }

    public String getClaveReporte() { return ClaveReporte; }
    public void setClaveReporte(String claveReporte) { ClaveReporte = claveReporte; }

    public String getNombre() { return Nombre; }
    public void setNombre(String nombre) { Nombre = nombre; }

    public String getDescripcionBreve() { return DescripcionBreve; }
    public void setDescripcionBreve(String descripcionBreve) { DescripcionBreve = descripcionBreve; }

    public String getDescripcionExtensa() { return DescripcionExtensa; }
    public void setDescripcionExtensa(String descripcionExtensa) { DescripcionExtensa = descripcionExtensa; }

    public String getTooltip() { return Tooltip; }
    public void setTooltip(String tooltip) { Tooltip = tooltip; }

    public boolean isPredeterminado() { return Predeterminado; }
    public void setPredeterminado(boolean predeterminado) { Predeterminado = predeterminado; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CatalogoContenidoBO that = (CatalogoContenidoBO) o;
        return isPredeterminado() == that.isPredeterminado() &&
                Objects.equals(getId(), that.getId()) &&
                Objects.equals(getFK_Catalogo(), that.getFK_Catalogo()) &&
                Objects.equals(getPosicion(), that.getPosicion()) &&
                Objects.equals(getClave(), that.getClave()) &&
                Objects.equals(getClaveReporte(), that.getClaveReporte()) &&
                Objects.equals(getNombre(), that.getNombre()) &&
                Objects.equals(getDescripcionBreve(), that.getDescripcionBreve()) &&
                Objects.equals(getDescripcionExtensa(), that.getDescripcionExtensa()) &&
                Objects.equals(getTooltip(), that.getTooltip());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFK_Catalogo(), getPosicion(), getClave(), getClaveReporte(), getNombre(), getDescripcionBreve(), getDescripcionExtensa(), getTooltip(), isPredeterminado());
    }

    @Override
    public String toString() {
        return Nombre;
    }
}
