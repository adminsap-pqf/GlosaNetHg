package org.mx.modelo.catalogo.po;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "GN_Catalogo_Contenido")
public class CatalogoContenidoPO implements Serializable {
    private static final long serialVersionUID = -3930208441126977929L;

    private Integer id;
    private Integer FK_Catalogo;
    private Integer Posicion;
    private String Clave;
    private String ClaveReporte;
    private String Nombre;
    private String DescripcionBreve;
    private String DescripcionExtensa;
    private String Tooltip;
    private boolean Predeterminado;
    private boolean Activo;
    private Timestamp FechaCreacion;
    private Timestamp FechaModificacion;
    private Timestamp FechaEliminacion;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="PK_Catalogo_Contenido")
    public Integer getId() { return id; }
    public void setId(Integer PK_Catalogo_Contenido) { this.id = PK_Catalogo_Contenido; }

    @JsonIgnore @Column(name = "FK_Catalogo")
    public Integer getFK_Catalogo() { return FK_Catalogo; }
    public void setFK_Catalogo(Integer FK_Catalogo) { this.FK_Catalogo = FK_Catalogo; }

    @Column(name = "Posicion")
    public Integer getPosicion() { return Posicion; }
    public void setPosicion(Integer posicion) { Posicion = posicion; }

    @Column(name = "Clave")
    public String getClave() { return Clave; }
    public void setClave(String clave) { Clave = clave; }

    @Column(name = "ClaveReporte")
    public String getClaveReporte() { return ClaveReporte; }
    public void setClaveReporte(String claveReporte) { ClaveReporte = claveReporte; }

    @Column(name = "Nombre")
    public String getNombre() { return Nombre; }
    public void setNombre(String nombre) { Nombre = nombre; }

    @Column(name = "DescripcionBreve")
    public String getDescripcionBreve() { return DescripcionBreve; }
    public void setDescripcionBreve(String descripcionBreve) { DescripcionBreve = descripcionBreve; }

    @Column(name = "DescripcionExtensa")
    public String getDescripcionExtensa() { return DescripcionExtensa; }
    public void setDescripcionExtensa(String descripcionExtensa) { DescripcionExtensa = descripcionExtensa; }

    @Column(name = "Tooltip")
    public String getTooltip() { return Tooltip; }
    public void setTooltip(String tooltip) { Tooltip = tooltip; }

    @Column(name = "Predeterminado")
    public boolean isPredeterminado() { return Predeterminado; }
    public void setPredeterminado(boolean predeterminado) { Predeterminado = predeterminado; }

    @Column(name = "Activo")
    public boolean isActivo() { return Activo; }
    public void setActivo(boolean activo) { Activo = activo; }

    @Column(name = "FechaCreacion")
    public Timestamp getFechaCreacion() { return FechaCreacion; }
    public void setFechaCreacion(Timestamp fechaCreacion) { FechaCreacion = fechaCreacion; }

    @Column(name = "FechaModificacion")
    public Timestamp getFechaModificacion() { return FechaModificacion; }
    public void setFechaModificacion(Timestamp fechaModificacion) { FechaModificacion = fechaModificacion; }

    @Column(name = "FechaEliminacion")
    public Timestamp getFechaEliminacion() { return FechaEliminacion; }
    public void setFechaEliminacion(Timestamp fechaEliminacion) { FechaEliminacion = fechaEliminacion; }
}
