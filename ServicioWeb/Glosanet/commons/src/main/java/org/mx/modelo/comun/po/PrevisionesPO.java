package org.mx.modelo.comun.po;



import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "Prevision")
public class PrevisionesPO implements Serializable {

    
	/**
	 * 
	 */
	private static final long serialVersionUID = -5968728518868453865L;
	private Integer idPrevision;
    private Integer vigencia;
    private Date fechaCreacion;
    private String tipoPrevision;
    private String folioPrevision;
    private EmpresaPO fK01Empresa;
    private Integer publicado;
    private Date citaCofepris;
    private UsuariosPO usuario;
    private Set<PEventoPO> eventos;
    
    private Date fechaCita;
    
    //private Set<PrevisionProductoPO> productos;


    public PrevisionesPO(Integer idPrevision, Integer vigencia, Date fechaCreacion, String estado, String tipoPrevision,
			String descripcion, Date fechaLimite, String folioPrevision, EmpresaPO fk01Empresa, Integer publicado) {
		super();
		this.idPrevision = idPrevision;
		this.vigencia = vigencia;
		this.fechaCreacion = fechaCreacion;
		this.tipoPrevision = tipoPrevision;
		this.folioPrevision = folioPrevision;
		this.fK01Empresa = fk01Empresa;
		this.publicado = publicado;
		
	}
    
	public PrevisionesPO() {
		super();
	}

	@Id
    @GeneratedValue
    @Column(name = "PK_Prevision", unique = true, nullable = false)
    public Integer getIdPrevision() {
        return this.idPrevision;
    }


    public void setIdPrevision(Integer idPrevision) {
        this.idPrevision = idPrevision;
    }


    @Column(name = "Vigencia")
    public Integer getVigencia() {
        return vigencia;
    }

    public void setVigencia(Integer vigencia) {
        this.vigencia = vigencia;
    }    
    
    @Column(name = "Fecha_Creacion")
    public Date getFechaCreacion() {
        return fechaCreacion;
    }


    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }


    @Column(name = "Tipo_Prevision")
    public String getTipoPrevision() {
        return tipoPrevision;
    }


    public void setTipoPrevision(String tipoPrevision) {
        this.tipoPrevision = tipoPrevision;
    }


    @Column(name = "Folio_Prevision")
    public String getFolioPrevision() {
        return folioPrevision;
    }


    public void setFolioPrevision(String folioPrevision) {
        this.folioPrevision = folioPrevision;
    }

    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="FK01_Empresa")
	public EmpresaPO getfK01Empresa() {
		return fK01Empresa;
	}

	public void setfK01Empresa(EmpresaPO fK01Empresa) {
		this.fK01Empresa = fK01Empresa;
	}

	
	@Column(name = "Publicado")
	public Integer getPublicado() {
		return publicado;
	}

	public void setPublicado(Integer publicado) {
		this.publicado = publicado;
	}

	

	
	@Column(name = "Cita_COFEPRIS")
	public Date getCitaCofepris() {
		return citaCofepris;
	}

	public void setCitaCofepris(Date citaCofepris) {
		this.citaCofepris = citaCofepris;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="FK02_Usuario")
	public UsuariosPO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuariosPO usuario) {
		this.usuario = usuario;
	}

	@OneToMany(fetch = FetchType.EAGER,  mappedBy = "prevision")
	@OrderBy("FechaRealizacion ASC")
	public Set<PEventoPO> getPEventos() {
		return eventos;
	}

	public void setPEventos(Set<PEventoPO> eventos) {
		this.eventos = eventos;
	}

	@Column(name = "Fecha_Cita")
	public Date getFechaCita() {
		return fechaCita;
	}

	public void setFechaCita(Date fechaCita) {
		this.fechaCita = fechaCita;
	}

//	@OneToMany(fetch = FetchType.EAGER,  mappedBy = "prevision")
//	public Set<PrevisionProductoPO> getProductos() {
//		return productos;
//	}
//
//	public void setProductos(Set<PrevisionProductoPO> productos) {
//		this.productos = productos;
//	}
    
	
	

}
