package org.mx.persistencia.comun.builder;

import org.mx.modelo.comun.bo.AgenciaAduanalBO;
import org.mx.modelo.comun.bo.AgenteAduanalBO;
import org.mx.modelo.comun.bo.DireccionFiscalBO;
import org.mx.modelo.comun.po.AgenciaAduanalPO;
import org.mx.modelo.comun.po.AgenteAduanalPO;
import org.mx.modelo.comun.po.DireccionFiscalPO;

public class BuilderAgencia {

	public static AgenciaAduanalBO builderBO(AgenciaAduanalPO po){
		AgenciaAduanalBO bo=new AgenciaAduanalBO();
		bo.setIdAgenciaAduanal(po.getPkAgencia());
		bo.setRfc(po.getRfc());
		bo.setRazonSocial(po.getRazonSocial());
		bo.setNombre(po.getNombre());
		bo.setTelefono(po.getTelefono());
		bo.setCorreoElectronico(po.getCorreoElectronico());
		DireccionFiscalBO direccion = BuilderDireccionFiscal.builderBO(po.getDireccion());
		bo.setDireccion(direccion);
		
		AgenteAduanalBO agente = BuilderAgenteAduanal.builderBO(po.getAgente());
		bo.setAgente(agente);
		
		return bo;
	}
	
	public static AgenciaAduanalBO builderBO(AgenciaAduanalPO po, boolean oneToMany){
		AgenciaAduanalBO bo=new AgenciaAduanalBO();
		bo.setIdAgenciaAduanal(po.getPkAgencia());
		bo.setRfc(po.getRfc());
		bo.setRazonSocial(po.getRazonSocial());
		bo.setNombre(po.getNombre());
		bo.setTelefono(po.getTelefono());
		bo.setCorreoElectronico(po.getCorreoElectronico());
		if(po.getDireccion()!=null){
			DireccionFiscalBO direccion = BuilderDireccionFiscal.builderBO(po.getDireccion());
			bo.setDireccion(direccion);
		}
		
		return bo;
	}
	
	public static AgenciaAduanalPO builderPO(AgenciaAduanalBO bo){
		AgenciaAduanalPO po=new AgenciaAduanalPO();
		po.setPkAgencia(bo.getIdAgenciaAduanal());
		po.setRfc(bo.getRfc());
		po.setRazonSocial(bo.getRazonSocial());
		po.setNombre(bo.getNombre());
		po.setTelefono(bo.getTelefono());
		po.setCorreoElectronico(bo.getCorreoElectronico());
		
		if(bo.getDireccion()!=null){
			DireccionFiscalPO direccion = BuilderDireccionFiscal.builderPO(bo.getDireccion());
			po.setDireccion(direccion);
		}
		if(bo.getAgente()!=null){
			AgenteAduanalPO agente = BuilderAgenteAduanal.builderPO(bo.getAgente());
			po.setAgente(agente);
			
		}
		return po;
	}
	
}
