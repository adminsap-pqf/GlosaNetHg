package org.mx.servicio.comun.impl;

import java.util.List;

import org.mx.modelo.comun.bo.IndustriaBO;
import org.mx.persistencia.comun.IndustriaDAO;
import org.mx.servicio.comun.IndustriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
public class IndustriaServiceImpl implements IndustriaService{
	
	@Autowired
	IndustriaDAO industriaDAO;
	
	@Transactional(rollbackFor={Exception.class})
	public IndustriaBO agregarIndustria(IndustriaBO industria) throws Exception{
		try{
			industria.setIdIndustria(industriaDAO.addIndustria(industria));	
			return industria;
		}catch(Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
			.setRollbackOnly();
			throw new Exception();
		}
	}

	@Transactional(rollbackFor={Exception.class})
	public boolean actualizarIndustria(IndustriaBO industria) throws Exception{
		try{
			return industriaDAO.updateIndustria(industria);
		}catch(Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
			.setRollbackOnly();
			throw new Exception();
		}
	}

	@Override
	public List<IndustriaBO> buscarIndustrias(){
		return industriaDAO.findIndustria();
	}

}
