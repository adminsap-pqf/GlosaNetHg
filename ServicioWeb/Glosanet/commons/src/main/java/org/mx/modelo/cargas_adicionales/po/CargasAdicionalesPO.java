package org.mx.modelo.cargas_adicionales.po;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Cargas_Adicionales", schema = "GlosaNet", catalog = "")
public class CargasAdicionalesPO {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "PK_Documento")
    private int pkDocumento;
    @Basic
    @Column(name = "nombreArchivo")
    private String nombreArchivo;
    @Basic
    @Column(name = "fechaCarga")
    private Timestamp fechaCarga;
    @Basic
    @Column(name = "FUA")
    private Timestamp fua;
    @Basic
    @Column(name = "Descripcion")
    private String descripcion;
    @Basic
    @Column(name = "FK01_Datos_Generales_501")
    private Integer fk01DatosGenerales501;
    @Basic
    @Column(name = "FK02_Usuario")
    private Integer fk02Usuario;

    public int getPkDocumento() {
        return this.pkDocumento;
    }

    public void setPkDocumento(int pkDocumento) {
        this.pkDocumento = pkDocumento;
    }

    public String getNombreArchivo() {
        return this.nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public Timestamp getFechaCarga() {
        return this.fechaCarga;
    }

    public void setFechaCarga(Timestamp fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    public Timestamp getFua() {
        return this.fua;
    }

    public void setFua(Timestamp fua) {
        this.fua = fua;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getFk01DatosGenerales551() {
        return this.fk01DatosGenerales501;
    }

    public void setFk01DatosGenerales551(Integer fk01DatosGenerales551) {
        this.fk01DatosGenerales501 = fk01DatosGenerales551;
    }

    public Integer getFk02Usuario() {
        return fk02Usuario;
    }

    public void setFk02Usuario(Integer fk02Usuario) {
        this.fk02Usuario = fk02Usuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CargasAdicionalesPO that = (CargasAdicionalesPO) o;

        if (pkDocumento != that.pkDocumento) return false;
        if (nombreArchivo != null ? !nombreArchivo.equals(that.nombreArchivo) : that.nombreArchivo != null)
            return false;
        if (fechaCarga != null ? !fechaCarga.equals(that.fechaCarga) : that.fechaCarga != null) return false;
        if (fua != null ? !fua.equals(that.fua) : that.fua != null) return false;
        if (descripcion != null ? !descripcion.equals(that.descripcion) : that.descripcion != null) return false;
        if (fk01DatosGenerales501 != null ? !fk01DatosGenerales501.equals(that.fk01DatosGenerales501) : that.fk01DatosGenerales501 != null)
            return false;
        if (fk02Usuario != null ? !fk02Usuario.equals(that.fk02Usuario) : that.fk02Usuario != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = pkDocumento;
        result = 31 * result + (nombreArchivo != null ? nombreArchivo.hashCode() : 0);
        result = 31 * result + (fechaCarga != null ? fechaCarga.hashCode() : 0);
        result = 31 * result + (fua != null ? fua.hashCode() : 0);
        result = 31 * result + (descripcion != null ? descripcion.hashCode() : 0);
        result = 31 * result + (fk01DatosGenerales501 != null ? fk01DatosGenerales501.hashCode() : 0);
        result = 31 * result + (fk02Usuario != null ? fk02Usuario.hashCode() : 0);
        return result;
    }
}
