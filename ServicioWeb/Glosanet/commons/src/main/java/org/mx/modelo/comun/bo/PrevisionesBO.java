package org.mx.modelo.comun.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "PrevisionesBO")
public class PrevisionesBO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4024709905588395554L;
	
	private Integer idPrevision;
    private Integer vigencia;
    private String vigenciaString;
    private String nombre;
    private Date fechaCreacion;
    private String fechaCreacionString;
    private String estado;
    private String tipoPrevision;
    private String descripcion;
    private Date fechaLimite;
    private String fechaLimiteString;
    private String folioPrevision;
    private Double cantidad;
    private String unidad;
    private Integer productos;
    private Integer previsionProducto;
    private Integer partida;
    private EmpresaBO empresa;
    private Integer publicado;
    private Integer tipoEstado;
	private Integer fkPrevision;
	private Double total;
	private String codigo;
	
	private Date citaCofepris;
	private UsuarioBO usuario;
	private Date fechaCita;
	
	
	private List<PEventoBO> eventos;
	
	private List<PrevisionProductoBO> lstProductos;
	
	private boolean impLstProductos;
	
	private boolean proxPrevision;
	
	private Integer idProxPrevision;
	
	

	public PrevisionesBO() {
        super();
        eventos = new ArrayList<PEventoBO>();
        // TODO Auto-generated constructor stub
    }

	public PrevisionesBO(Integer idPrevision, Integer vigencia, String nombre, Date fechaCreacion, String estado,
			String tipoPrevision, String descripcion, Date fechaLimite, String folioPrevision, Double cantidad,
			String unidad, Integer productos, Integer previsionProducto, Integer partida, EmpresaBO empresa, Integer publicado) {
		super();
		this.idPrevision = idPrevision;
		this.vigencia = vigencia;
		this.nombre = nombre;
		this.fechaCreacion = fechaCreacion;
		this.estado = estado;
		this.tipoPrevision = tipoPrevision;
		this.descripcion = descripcion;
		this.fechaLimite = fechaLimite;
		this.folioPrevision = folioPrevision;
		this.cantidad = cantidad;
		this.unidad = unidad;
		this.productos = productos;
		this.previsionProducto = previsionProducto;
		this.partida = partida;
		this.empresa = empresa;
		this.publicado = publicado;
	}

	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Integer getFkPrevision() {
		return fkPrevision;
	}


	public void setFkPrevision(Integer fkPrevision) {
		this.fkPrevision = fkPrevision;
	}

	public Integer getIdPrevision() {
        return idPrevision;
    }

    public void setIdPrevision(Integer idPrevision) {
        this.idPrevision = idPrevision;
    }

    public Integer getVigencia() {
        return vigencia;
    }

    public void setVigencia(Integer vigencia) {
        this.vigencia = vigencia;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipoPrevision() {
        return tipoPrevision;
    }

    public void setTipoPrevision(String tipoPrevision) {
        this.tipoPrevision = tipoPrevision;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(Date fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public String getFolioPrevision() {
        return folioPrevision;
    }

    public void setFolioPrevision(String folioPrevision) {
        this.folioPrevision = folioPrevision;
    }
    
    public Double getCantidad() {
		return cantidad;
	}

	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}
	
	public String getUnidad() {
		return unidad;
	}

	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}

	public Integer getProductos() {
		return productos;
	}

	public void setProductos(Integer productos) {
		this.productos = productos;
	}

	public Integer getPrevisionProducto() {
		return previsionProducto;
	}

	public void setPrevisionProducto(Integer previsionProducto) {
		this.previsionProducto = previsionProducto;
	}

	public Integer getPartida() {
		return partida;
	}

	public void setPartida(Integer partida) {
		this.partida = partida;
	}

	public EmpresaBO getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaBO empresa) {
		this.empresa = empresa;
	}

	public String getVigenciaString() {
		return vigenciaString;
	}

	public void setVigenciaString(String vigenciaString) {
		this.vigenciaString = vigenciaString;
	}

	public String getFechaCreacionString() {
		return fechaCreacionString;
	}

	public void setFechaCreacionString(String fechaCreacionString) {
		this.fechaCreacionString = fechaCreacionString;
	}

	public String getFechaLimiteString() {
		return fechaLimiteString;
	}

	public void setFechaLimiteString(String fechaLimiteString) {
		this.fechaLimiteString = fechaLimiteString;
	}

	public Integer getPublicado() {
		return publicado;
	}

	public void setPublicado(Integer publicado) {
		this.publicado = publicado;
	}

	public Integer getTipoEstado() {
		return tipoEstado;
	}

	public void setTipoEstado(Integer tipoEstado) {
		this.tipoEstado = tipoEstado;
	}

	public Date getCitaCofepris() {
		return citaCofepris;
	}

	public void setCitaCofepris(Date citaCofepris) {
		this.citaCofepris = citaCofepris;
	}

	public UsuarioBO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioBO usuario) {
		this.usuario = usuario;
	}

	public List<PEventoBO> getEventos() {
		return eventos;
	}

	public void setEventos(List<PEventoBO> eventos) {
		this.eventos = eventos;
	}

	public List<PrevisionProductoBO> getLstProductos() {
		return lstProductos;
	}

	public void setLstProductos(List<PrevisionProductoBO> lstProductos) {
		this.lstProductos = lstProductos;
	}

	public boolean isImpLstProductos() {
		return impLstProductos;
	}

	public void setImpLstProductos(boolean impLstProductos) {
		this.impLstProductos = impLstProductos;
	}

	public boolean isProxPrevision() {
		return proxPrevision;
	}

	public void setProxPrevision(boolean proxPrevision) {
		this.proxPrevision = proxPrevision;
	}

	public Integer getIdProxPrevision() {
		return idProxPrevision;
	}

	public void setIdProxPrevision(Integer idProxPrevision) {
		this.idProxPrevision = idProxPrevision;
	}

	public Date getFechaCita() {
		return fechaCita;
	}

	public void setFechaCita(Date fechaCita) {
		this.fechaCita = fechaCita;
	}


	
}
