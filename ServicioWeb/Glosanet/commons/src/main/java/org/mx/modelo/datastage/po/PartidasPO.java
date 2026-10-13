package org.mx.modelo.datastage.po;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Partidas551 entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Partidas_551")
public class PartidasPO implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 6985068260813585978L;
	private PartidasPOId id;
	private Integer subdivisionFraccion;
	private String descripcionMercancia;
	private Double precioUnitario;
	private Double valorAduana;
	private Double valorComercial;
	private Double valorDolares;
	private Double cantidadUmcomercial;
	private Integer unidadMedidaComercial;
	private Double cantidadUmtarifa;
	private Integer unidadMedidaTarifa;
	private Double valorAgregado;
	private Integer claveVinculacion;
	private Integer metodoValorizacion;
	private String codigoMercanciaProducto;
	private String marcaMercanciaProducto;
	private String modeloMercanciaProducto;
	private String paisOrigenDestino;
	private String paisCompradorVendedor;
	private String entidadFedOrigen;
	private String entidadFedDestino;
	private String entidadFedComprador;
	private String entidadFedVendedor;
	private String tipoOperacion;
	private String claveDocumento;
	private Timestamp fechaPagoReal;
	
	private Set<ContribucionesPartidaPO> contribucionesPartida557s = new HashSet<ContribucionesPartidaPO>(
			0);

	// Constructors

	/** default constructor */
	public PartidasPO() {
	}

	/** minimal constructor */
	public PartidasPO(PartidasPOId id) {
		this.id = id;
	}

	/** full constructor */
	public PartidasPO(PartidasPOId id, Integer subdivisionFraccion,
			String descripcionMercancia, Double precioUnitario,
			Double valorAduana, Double valorComercial, Double valorDolares,
			Double cantidadUmcomercial, Integer unidadMedidaComercial,
			Double cantidadUmtarifa, Integer unidadMedidaTarifa,
			Double valorAgregado, Integer claveVinculacion,
			Integer metodoValorizacion, String codigoMercanciaProducto,
			String marcaMercanciaProducto, String modeloMercanciaProducto,
			String paisOrigenDestino, String paisCompradorVendedor,
			String entidadFedOrigen, String entidadFedDestino,
			String entidadFedComprador, String entidadFedVendedor,
			String tipoOperacion, String claveDocumento,
			Timestamp fechaPagoReal,
			Set<ContribucionesPartidaPO> contribucionesPartida557s) {
		this.id = id;
		this.subdivisionFraccion = subdivisionFraccion;
		this.descripcionMercancia = descripcionMercancia;
		this.precioUnitario = precioUnitario;
		this.valorAduana = valorAduana;
		this.valorComercial = valorComercial;
		this.valorDolares = valorDolares;
		this.cantidadUmcomercial = cantidadUmcomercial;
		this.unidadMedidaComercial = unidadMedidaComercial;
		this.cantidadUmtarifa = cantidadUmtarifa;
		this.unidadMedidaTarifa = unidadMedidaTarifa;
		this.valorAgregado = valorAgregado;
		this.claveVinculacion = claveVinculacion;
		this.metodoValorizacion = metodoValorizacion;
		this.codigoMercanciaProducto = codigoMercanciaProducto;
		this.marcaMercanciaProducto = marcaMercanciaProducto;
		this.modeloMercanciaProducto = modeloMercanciaProducto;
		this.paisOrigenDestino = paisOrigenDestino;
		this.paisCompradorVendedor = paisCompradorVendedor;
		this.entidadFedOrigen = entidadFedOrigen;
		this.entidadFedDestino = entidadFedDestino;
		this.entidadFedComprador = entidadFedComprador;
		this.entidadFedVendedor = entidadFedVendedor;
		this.tipoOperacion = tipoOperacion;
		this.claveDocumento = claveDocumento;
		this.fechaPagoReal = fechaPagoReal;
		this.contribucionesPartida557s = contribucionesPartida557s;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "patente", column = @Column(name = "Patente", nullable = false, length = 4)),
			@AttributeOverride(name = "pedimento", column = @Column(name = "Pedimento", nullable = false, length = 7)),
			@AttributeOverride(name = "seccionAduanera", column = @Column(name = "SeccionAduanera", nullable = false, length = 3)),
			@AttributeOverride(name = "fraccion", column = @Column(name = "Fraccion", nullable = false, length = 8)),
			@AttributeOverride(name = "secuenciaFraccion", column = @Column(name = "SecuenciaFraccion", nullable = false)) })
	public PartidasPOId getId() {
		return this.id;
	}

	public void setId(PartidasPOId id) {
		this.id = id;
	}

	@Column(name = "SubdivisionFraccion")
	public Integer getSubdivisionFraccion() {
		return this.subdivisionFraccion;
	}

	public void setSubdivisionFraccion(Integer subdivisionFraccion) {
		this.subdivisionFraccion = subdivisionFraccion;
	}

	@Column(name = "DescripcionMercancia")
	public String getDescripcionMercancia() {
		return this.descripcionMercancia;
	}

	public void setDescripcionMercancia(String descripcionMercancia) {
		this.descripcionMercancia = descripcionMercancia;
	}

	@Column(name = "PrecioUnitario", precision = 15, scale = 5)
	public Double getPrecioUnitario() {
		return this.precioUnitario;
	}

	public void setPrecioUnitario(Double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	@Column(name = "ValorAduana", precision = 12)
	public Double getValorAduana() {
		return this.valorAduana;
	}

	public void setValorAduana(Double valorAduana) {
		this.valorAduana = valorAduana;
	}

	@Column(name = "ValorComercial", precision = 12)
	public Double getValorComercial() {
		return this.valorComercial;
	}

	public void setValorComercial(Double valorComercial) {
		this.valorComercial = valorComercial;
	}

	@Column(name = "ValorDolares", precision = 14)
	public Double getValorDolares() {
		return this.valorDolares;
	}

	public void setValorDolares(Double valorDolares) {
		this.valorDolares = valorDolares;
	}

	@Column(name = "CantidadUMComercial", precision = 15, scale = 3)
	public Double getCantidadUmcomercial() {
		return this.cantidadUmcomercial;
	}

	public void setCantidadUmcomercial(Double cantidadUmcomercial) {
		this.cantidadUmcomercial = cantidadUmcomercial;
	}

	@Column(name = "UnidadMedidaComercial")
	public Integer getUnidadMedidaComercial() {
		return this.unidadMedidaComercial;
	}

	public void setUnidadMedidaComercial(Integer unidadMedidaComercial) {
		this.unidadMedidaComercial = unidadMedidaComercial;
	}

	@Column(name = "CantidadUMTarifa", precision = 15, scale = 3)
	public Double getCantidadUmtarifa() {
		return this.cantidadUmtarifa;
	}

	public void setCantidadUmtarifa(Double cantidadUmtarifa) {
		this.cantidadUmtarifa = cantidadUmtarifa;
	}

	@Column(name = "UnidadMedidaTarifa")
	public Integer getUnidadMedidaTarifa() {
		return this.unidadMedidaTarifa;
	}

	public void setUnidadMedidaTarifa(Integer unidadMedidaTarifa) {
		this.unidadMedidaTarifa = unidadMedidaTarifa;
	}

	@Column(name = "ValorAgregado", precision = 12)
	public Double getValorAgregado() {
		return this.valorAgregado;
	}

	public void setValorAgregado(Double valorAgregado) {
		this.valorAgregado = valorAgregado;
	}

	@Column(name = "ClaveVinculacion")
	public Integer getClaveVinculacion() {
		return this.claveVinculacion;
	}

	public void setClaveVinculacion(Integer claveVinculacion) {
		this.claveVinculacion = claveVinculacion;
	}

	@Column(name = "MetodoValorizacion")
	public Integer getMetodoValorizacion() {
		return this.metodoValorizacion;
	}

	public void setMetodoValorizacion(Integer metodoValorizacion) {
		this.metodoValorizacion = metodoValorizacion;
	}

	@Column(name = "CodigoMercanciaProducto", length = 20)
	public String getCodigoMercanciaProducto() {
		return this.codigoMercanciaProducto;
	}

	public void setCodigoMercanciaProducto(String codigoMercanciaProducto) {
		this.codigoMercanciaProducto = codigoMercanciaProducto;
	}

	@Column(name = "MarcaMercanciaProducto", length = 80)
	public String getMarcaMercanciaProducto() {
		return this.marcaMercanciaProducto;
	}

	public void setMarcaMercanciaProducto(String marcaMercanciaProducto) {
		this.marcaMercanciaProducto = marcaMercanciaProducto;
	}

	@Column(name = "ModeloMercanciaProducto", length = 80)
	public String getModeloMercanciaProducto() {
		return this.modeloMercanciaProducto;
	}

	public void setModeloMercanciaProducto(String modeloMercanciaProducto) {
		this.modeloMercanciaProducto = modeloMercanciaProducto;
	}

	@Column(name = "PaisOrigenDestino", length = 3)
	public String getPaisOrigenDestino() {
		return this.paisOrigenDestino;
	}

	public void setPaisOrigenDestino(String paisOrigenDestino) {
		this.paisOrigenDestino = paisOrigenDestino;
	}

	@Column(name = "PaisCompradorVendedor", length = 3)
	public String getPaisCompradorVendedor() {
		return this.paisCompradorVendedor;
	}

	public void setPaisCompradorVendedor(String paisCompradorVendedor) {
		this.paisCompradorVendedor = paisCompradorVendedor;
	}

	@Column(name = "EntidadFedOrigen", length = 3)
	public String getEntidadFedOrigen() {
		return this.entidadFedOrigen;
	}

	public void setEntidadFedOrigen(String entidadFedOrigen) {
		this.entidadFedOrigen = entidadFedOrigen;
	}

	@Column(name = "EntidadFedDestino", length = 3)
	public String getEntidadFedDestino() {
		return this.entidadFedDestino;
	}

	public void setEntidadFedDestino(String entidadFedDestino) {
		this.entidadFedDestino = entidadFedDestino;
	}

	@Column(name = "EntidadFedComprador", length = 3)
	public String getEntidadFedComprador() {
		return this.entidadFedComprador;
	}

	public void setEntidadFedComprador(String entidadFedComprador) {
		this.entidadFedComprador = entidadFedComprador;
	}

	@Column(name = "EntidadFedVendedor", length = 3)
	public String getEntidadFedVendedor() {
		return this.entidadFedVendedor;
	}

	public void setEntidadFedVendedor(String entidadFedVendedor) {
		this.entidadFedVendedor = entidadFedVendedor;
	}

	@Column(name = "TipoOperacion", length = 1)
	public String getTipoOperacion() {
		return this.tipoOperacion;
	}

	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	@Column(name = "ClaveDocumento", length = 3)
	public String getClaveDocumento() {
		return this.claveDocumento;
	}

	public void setClaveDocumento(String claveDocumento) {
		this.claveDocumento = claveDocumento;
	}

	@Column(name = "FechaPagoReal", length = 23)
	public Timestamp getFechaPagoReal() {
		return this.fechaPagoReal;
	}

	public void setFechaPagoReal(Timestamp fechaPagoReal) {
		this.fechaPagoReal = fechaPagoReal;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "partidas")
	public Set<ContribucionesPartidaPO> getContribucionesPartida557s() {
		return this.contribucionesPartida557s;
	}

	public void setContribucionesPartida557s(
			Set<ContribucionesPartidaPO> contribucionesPartida557s) {
		this.contribucionesPartida557s = contribucionesPartida557s;
	}

}