package org.mx.modelo.catalogo.bo;


import java.util.List;

public class CatalogoBO {
    private Integer id;
    private String nombreCatalogo;
    private String descripcion;
    private List<CatalogoContenidoBO> contenido;

    public CatalogoBO() {
    }

    public CatalogoBO(Integer id, String nombreCatalogo, String descripcion, List<CatalogoContenidoBO> contenido) {
        this.id = id;
        this.nombreCatalogo = nombreCatalogo;
        this.descripcion = descripcion;
        this.contenido = contenido;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombreCatalogo() { return nombreCatalogo; }
    public void setNombreCatalogo(String nombreCatalogo) { this.nombreCatalogo = nombreCatalogo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public List<CatalogoContenidoBO> getContenido() { return contenido; }
    public void setContenido(List<CatalogoContenidoBO> contenido) { this.contenido = contenido; }

    @Override
    public String toString() {
        return nombreCatalogo;
    }
}
