package org.mx.persistencia.comun.builder;

import org.mx.modelo.comun.bo.ClientesBO;
import org.mx.modelo.comun.bo.EmpresaBO;
import org.mx.modelo.comun.bo.PaqueteBO;
import org.mx.modelo.comun.po.ClientesPO;
import org.mx.modelo.comun.po.EmpresaPO;

public class BuilderClientes {

	public static ClientesPO builderPO(ClientesBO bo){

		ClientesPO po = new ClientesPO();
		po.setPkCliente(bo.getIdCliente());
		po.setRepresentante(bo.getRepresentante());
		po.setNombre(bo.getNombre());

		for (EmpresaBO empresa : bo.getEmpresas()) {
			po.getEmpresas().add(BuilderEmpresa.builderPO(empresa));
		}

		return po;
	}


	public static ClientesBO builderBO(ClientesPO po){

		ClientesBO bo = new ClientesBO();
		bo.setIdCliente(po.getPkCliente());
		bo.setNombre(po.getNombre());
		bo.setRepresentante(po.getRepresentante());
		bo.setFcreacion(po.getFcreacion());
		bo.setFua(po.getFua());
		
		bo.setRfc(po.getRfc());
		bo.setRazonSocial(po.getRazonSocial());
		bo.setTelefono(po.getTelefono());
		bo.setFauditable(po.getfAuditbale());
		bo.setRegimenFiscal(po.getRegimenFiscal());
		bo.setUsoCFDI(po.getUsoCFDI());
		bo.setClaveProducto(po.getClaveProducto());
		
		if(po.getPaquete()!=null){
			PaqueteBO paquete = BuilderPaquete.builderBO(po.getPaquete());
			bo.setPaquete(paquete);
		}

		if(po.getEmpresas() != null){
			for (EmpresaPO empresa : po.getEmpresas()) {
				bo.getEmpresas().add(BuilderEmpresa.builderBO(empresa, true));
			}			
		}

		return bo;
	}

}
