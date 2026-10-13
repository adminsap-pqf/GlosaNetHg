package org.mx.modelo.notificaciones.po;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "Registro_Evento")
public class NotificationEventoPO {
    private static final long serialVersionUID = 5979467972445007961L;
    private Integer idEvento;
    private String clave;
    private String producto;
    private Date fechaVencimiento;
    private Date fechaRealizacion;
    private Integer duracion;
    private String recurrencia;
    private String folioCofepris;
    private String evento;
    private String cliente;
    private String correo;

    public NotificationEventoPO(Integer idEvento, String clave, String producto, Date fechaVencimiento, Date fechaRealizacion, Integer duracion, String folioCofepris, String evento, String cliente, String correo, String recurrencia) {
        this.idEvento = idEvento;
        this.clave = clave;
        this.producto = producto;
        this.fechaVencimiento = fechaVencimiento;
        this.fechaRealizacion = fechaRealizacion;
        this.duracion = duracion;
        this.folioCofepris = folioCofepris;
        this.evento = evento;
        this.cliente = cliente;
        this.correo = correo;
        this.recurrencia = recurrencia;
    }
    public NotificationEventoPO(){ }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationEventoPO that = (NotificationEventoPO) o;
        return idEvento.equals(that.idEvento);
    }

    @Override
    public int hashCode() { return Objects.hash(idEvento); }

    @Id
    @GeneratedValue
    @Column(name = "IdEvento", unique = true, nullable = false)
    public Integer getIdEvento() { return idEvento; }
    public void setIdEvento(Integer idEvento) { this.idEvento = idEvento; }

    @Column(name = "Clave")
    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }

    @Column(name = "Producto")
    public String getProducto() { return producto; }
    public void setProducto(String producto) { this.producto = producto; }

    @Column(name = "FechaVencimiento")
    public Date getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(Date fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

    @Column(name = "FechaRealizacion")
    public Date getFechaRealizacion() { return fechaRealizacion; }
    public void setFechaRealizacion(Date fechaRealizacion) { this.fechaRealizacion = fechaRealizacion; }

    @Column(name = "Duracion")
    public Integer getDuracion() { return duracion; }
    public void setDuracion(Integer duracion) { this.duracion = duracion; }

    @Column(name = "FolioCofepris")
    public String getFolioCofepris() { return folioCofepris; }
    public void setFolioCofepris(String folioCofepris) { this.folioCofepris = folioCofepris; }

    @Column(name = "Evento")
    public String getEvento() { return evento; }
    public void setEvento(String evento) { this.evento = evento; }

    @Column(name = "Cliente")
    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }

    @Column(name = "Correo")
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    @Column(name = "Recurrencia")
    public String getRecurrencia() { return recurrencia; }
    public void setRecurrencia(String recurrencia) { this.recurrencia = recurrencia; }
}
