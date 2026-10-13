package org.mx.modelo.catalogo.po;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "GN_Catalogo")
public class CatalogoPO implements Serializable {
    private static final long serialVersionUID = -2680617484179487485L;

    private Integer id;
    private String nombreCatalogo;
    private String descripcion;
    private boolean activo;
    private Timestamp FechaCreacion;
    private Timestamp FechaModificacion;
    private Timestamp FechaEliminacion;
    private List<CatalogoContenidoPO> contenido = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="PK_Catalogo")
    public Integer getId() { return id; }
    public void setId(Integer pk_Catalogo) { this.id = pk_Catalogo; }

    @Column(name = "NombreCatalogo")
    public String getNombreCatalogo() { return nombreCatalogo; }
    public void setNombreCatalogo(String nombreCatalogo) { this.nombreCatalogo = nombreCatalogo; }

    @Column(name = "Descripcion")
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    @Column(name = "Activo")
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    @Column(name = "FechaCreacion")
    public Timestamp getFechaCreacion() { return FechaCreacion; }
    public void setFechaCreacion(Timestamp fechaCreacion) { FechaCreacion = fechaCreacion; }

    @Column(name = "FechaModificacion")
    public Timestamp getFechaModificacion() { return FechaModificacion; }
    public void setFechaModificacion(Timestamp fechaModificacion) { FechaModificacion = fechaModificacion; }

    @Column(name = "FechaEliminacion")
    public Timestamp getFechaEliminacion() { return FechaEliminacion; }
    public void setFechaEliminacion(Timestamp fechaEliminacion) { FechaEliminacion = fechaEliminacion; }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "FK_Catalogo")
    public List<CatalogoContenidoPO> getContenido() { return contenido; }
    public void setContenido(List<CatalogoContenidoPO> contenido) { this.contenido = contenido; }
}
