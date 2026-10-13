package org.mx.modelo.cargas_adicionales.bo;

import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.Date;

@XmlRootElement(name="CargasAdicionalesBO")
public class CargasAdicionalesBO implements Serializable {



    private static final long serialVersionUID = -6988346961140846769L;
    private Integer idDocumento;
    private String nombreArchivo;
    private Date fechaCarga;
    private Date FUA;
    private String descripcion;
    private Integer idDatosGenerales;
    private Integer idUsuario;

    public CargasAdicionalesBO() {
        super();
    }

    public Integer getIdDocumento() {
        return this.idDocumento;
    }

    public void setIdDocumento(Integer idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getNombreArchivo() {
        return this.nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public Date getFechaCarga() {
        return this.fechaCarga;
    }

    public void setFechaCarga(Date fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    public Date getFUA() {
        return this.FUA;
    }

    public void setFUA(Date FUA) {
        this.FUA = FUA;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getIdDatosGenerales() {
        return this.idDatosGenerales;
    }

    public void setIdDatosGenerales(Integer idDatosGenerales) {
        this.idDatosGenerales = idDatosGenerales;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
}
