package org.mx.modelo.registros.bo;

import java.io.Serializable;
import java.sql.Timestamp;

public class HistorialProductoBO implements Serializable {

    private static final long serialVersionUID = -1959024028131421075L;
    private Integer idHistorialProducto;
    private Integer idRegistro;
    private String nombre;
    private String comentarios;
    private Timestamp fecha;

    public Integer getIdHistorialProducto() {
        return idHistorialProducto;
    }

    public void setIdHistorialProducto(Integer idHistorialProducto) {
        this.idHistorialProducto = idHistorialProducto;
    }

    public Integer getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(Integer idRegistro) {
        this.idRegistro = idRegistro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }
}
