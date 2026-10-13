package org.mx.modelo.datastage.bo;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import org.mx.modelo.comun.bo.ClientesBO;
import org.mx.modelo.comun.bo.UsuarioBO;

@XmlRootElement(name = "DataStageBO")
public class DataStageBO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8100653017579453623L;
	private Integer idDataStage;
	private ClientesBO clientes;
	private String folio;
	private Date fua;
	private UsuarioBO usuario;
	
	/**
	 * 
	 */
	public DataStageBO() {
		super();
	}

	/**
	 * @param idDataStage
	 * @param clientes
	 * @param folio
	 */
	public DataStageBO(Integer idDataStage, ClientesBO clientes, String folio) {
		super();
		this.idDataStage = idDataStage;
		this.clientes = clientes;
		this.folio = folio;
	}

	/**
	 * @return the idDataStage
	 */
	public Integer getIdDataStage() {
		return idDataStage;
	}

	/**
	 * @param idDataStage the idDataStage to set
	 */
	public void setIdDataStage(Integer idDataStage) {
		this.idDataStage = idDataStage;
	}

	/**
	 * @return the clientes
	 */
	public ClientesBO getClientes() {
		return clientes;
	}

	/**
	 * @param clientes the clientes to set
	 */
	public void setClientes(ClientesBO clientes) {
		this.clientes = clientes;
	}

	/**
	 * @return the folio
	 */
	public String getFolio() {
		return folio;
	}

	/**
	 * @param folio the folio to set
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}

	/**
	 * @return the fua
	 */
	public Date getFua() {
		return fua;
	}

	/**
	 * @param fua the fua to set
	 */
	public void setFua(Date fua) {
		this.fua = fua;
	}

	/**
	 * @return the usuario
	 */
	public UsuarioBO getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(UsuarioBO usuario) {
		this.usuario = usuario;
	}
	
	
}
