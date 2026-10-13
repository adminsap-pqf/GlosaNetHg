package org.mx.modelo.comun.po;

import java.sql.Timestamp;

import javax.persistence.*;

/**
 * DataStage entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "GN_DataStage")
public class DataStagePO implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -4100608883132013299L;
	private Integer pkDataStage;
	private ClientesPO clientes;
	private UsuariosPO usuario;
	private String folio;
	private Timestamp fua;
	
	// Constructors

	/** default constructor */
	public DataStagePO() {
	}

	/** minimal constructor */
	public DataStagePO(Integer pkDataStage, ClientesPO clientes) {
		this.pkDataStage = pkDataStage;
		this.clientes = clientes;
	}

	/** full constructor */
	public DataStagePO(Integer pkDataStage, ClientesPO clientes, String folio) {
		this.pkDataStage = pkDataStage;
		this.clientes = clientes;
		this.folio = folio;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PK_DataStage", unique = true, nullable = false)
	public Integer getPkDataStage() {
		return this.pkDataStage;
	}

	public void setPkDataStage(Integer pkDataStage) {
		this.pkDataStage = pkDataStage;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FK01_Cliente", nullable = false)
	public ClientesPO getClientes() {
		return this.clientes;
	}

	public void setClientes(ClientesPO clientes) {
		this.clientes = clientes;
	}

	@Column(name = "Folio", length = 17)
	public String getFolio() {
		return this.folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	@Column(name = "fua", length = 23)
	public Timestamp getFua() {
		return fua;
	}

	public void setFua(Timestamp fua) {
		this.fua = fua;
	}

	/**
	 * @return the usuario
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FK02_Usuario")
	public UsuariosPO getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(UsuariosPO usuario) {
		this.usuario = usuario;
	}
}
