package org.mx.modelo.comun.bo;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.mx.modelo.datastage.bo.DataStageBO;

@XmlRootElement(name = "RespuestaBO")
public class RespuestaBO {

	private UsuarioBO usuario;
	private ClientesBO cliente;
	private List<DataStageBO> lstDataStage;
	private List<UsuarioBO> administrador ;
	private Date dof;
	
	public UsuarioBO getUsuario() {
		return usuario;
	}
	
	public void setUsuario(UsuarioBO usuario) {
		this.usuario = usuario;
	}
	
	public ClientesBO getCliente() {
		return cliente;
	}
	
	public void setCliente(ClientesBO cliente) {
		this.cliente = cliente;
	}

	public List<DataStageBO> getLstDataStage() {
		return lstDataStage;
	}

	public void setLstDataStage(List<DataStageBO> lstDataStage) {
		this.lstDataStage = lstDataStage;
	}

	public List<UsuarioBO> getAdministrador() {
		return administrador;
	}

	public void setAdministrador(List<UsuarioBO> administrador) {
		this.administrador = administrador;
	}

	public Date getDof() {
		return dof;
	}

	public void setDof(Date dof) {
		this.dof = dof;
	}
	
}
