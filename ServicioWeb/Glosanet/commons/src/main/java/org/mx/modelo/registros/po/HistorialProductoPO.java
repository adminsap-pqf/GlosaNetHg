package org.mx.modelo.registros.po;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "Registro_HistorialProducto")
public class HistorialProductoPO implements Serializable {

    private static final long serialVersionUID = -1935618264812025230L;
    private Integer idHistorialProducto;
    private Integer idRegistro;
    private String nombre;
    private String comentarios;
    private Timestamp fecha;

    @Id
    @GeneratedValue
    @Column(name = "PK_Historial_Producto", unique = true, nullable = false)
    public Integer getIdHistorialProducto() {
        return idHistorialProducto;
    }

    public void setIdHistorialProducto(Integer idHistorialProducto) {
        this.idHistorialProducto = idHistorialProducto;
    }

    @Column(name  = "FK01_Registro")
    public Integer getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(Integer idRegistro) {
        this.idRegistro = idRegistro;
    }

    @Column(name = "Nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Column(name = "Comentarios")
    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    @Column(name = "Fecha")
    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }
}
