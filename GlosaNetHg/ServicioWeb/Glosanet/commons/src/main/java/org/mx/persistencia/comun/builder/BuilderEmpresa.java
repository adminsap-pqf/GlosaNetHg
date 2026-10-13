package org.mx.persistencia.comun.builder;

import java.sql.Timestamp;

import org.mx.modelo.comun.bo.ClientesBO;
import org.mx.modelo.comun.bo.EmpresaBO;
import org.mx.modelo.comun.po.EmpresaPO;

public class BuilderEmpresa {

	public static EmpresaBO builderBO(EmpresaPO po)
	{
		EmpresaBO bo = new EmpresaBO();
		bo.setCalle(po.getCalle());
		bo.setCp(po.getCp());
		bo.setDelegacion(po.getDelegacion());
		bo.setEmail(po.getEmail());
		bo.setEmpresarial(po.getEmpresarial());
		bo.setEstado(po.getEstado());
		bo.setIdEmpresa(po.getPkEmpresa());
		bo.setMseguridad(po.isMseguridad());
		bo.setMunicipio(po.getMunicipio());
		bo.setPais(po.getPais());
		bo.setRazonSocial(po.getRazonSocial());
		bo.setRfc(po.getRfc());
		bo.setTelefono(po.getTelefono());
		bo.setVigencia(po.getVigencia());
		bo.setVigenciaMatriz(po.getVigenciaMatriz());
		bo.setAuditable(po.getAuditable());
		bo.setRepresentanteLegal(po.getRepresentanteLegal());
		bo.setVigenciaMatrizFin(po.getVigenciaMatrizFin());
		
		ClientesBO cliente = BuilderClientes.builderBO(po.getCliente());
		bo.setCliente(cliente);

		return bo;
	}

	public static EmpresaBO builderBO(EmpresaPO po, boolean oneToMany)
	{
		EmpresaBO bo = new EmpresaBO();
		bo.setCalle(po.getCalle());
		bo.setCp(po.getCp());
		bo.setDelegacion(po.getDelegacion());
		bo.setEmail(po.getEmail());
		bo.setEmpresarial(po.getEmpresarial());
		bo.setEstado(po.getEstado());
		bo.setIdEmpresa(po.getPkEmpresa());
		bo.setMseguridad(po.isMseguridad());
		bo.setMunicipio(po.getMunicipio());
		bo.setPais(po.getPais());
		bo.setRazonSocial(po.getRazonSocial());
		bo.setRfc(po.getRfc());
		bo.setTelefono(po.getTelefono());
		bo.setVigencia(po.getVigencia());
		bo.setVigenciaMatriz(po.getVigenciaMatriz());
		bo.setVigenciaMatrizFin(po.getVigenciaMatrizFin());
		bo.setAuditable(po.getAuditable());
		bo.setRepresentanteLegal(po.getRepresentanteLegal());
		
		return bo;
	}
	
	public static EmpresaPO builderPO(EmpresaBO bo)
	{
		EmpresaPO po = new EmpresaPO();
		po.setCalle(bo.getCalle());
		po.setCp(bo.getCp());
		po.setDelegacion(bo.getDelegacion());
		po.setEmail(bo.getEmail());
		po.setEmpresarial(bo.getEmpresarial());
		po.setEstado(bo.getEstado());
		po.setPkEmpresa(bo.getIdEmpresa());
		po.setMseguridad(bo.isMseguridad());
		po.setMunicipio(bo.getMunicipio());
		po.setPais(bo.getPais());
		po.setRazonSocial(bo.getRazonSocial());
		po.setRfc(bo.getRfc());
		po.setTelefono(bo.getTelefono());
		po.setRepresentanteLegal(bo.getRepresentanteLegal());
		
		if (bo.getVigencia() != null)
			po.setVigencia(new Timestamp(bo.getVigencia().getTime()));
		
		if (bo.getVigenciaMatriz() != null)
			po.setVigenciaMatriz(new Timestamp(bo.getVigenciaMatriz().getTime()));
		
		if (bo.getVigenciaMatrizFin() != null)
			po.setVigenciaMatrizFin(new Timestamp(bo.getVigenciaMatrizFin().getTime()));
		
		if (bo.getAuditable() != null)
			po.setAuditable(new Timestamp(bo.getAuditable().getTime()));
		
		return po;
	}
}
