package org.mx.servicio.comun.impl;


import org.mx.modelo.comun.bo.ClientesBO;
import org.mx.persistencia.comun.ClienteDAO;
import org.mx.servicio.comun.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
public class ClienteServiceImpl implements ClienteService {
	
	@Autowired
	ClienteDAO clienteDAO;
	
	@Transactional(rollbackFor={Exception.class})
	public boolean actualizarCliente(int usuarioCreador, ClientesBO cliente) throws Exception {
		try{

			return clienteDAO.updateCliente(cliente);
		}catch(Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
			.setRollbackOnly();
			throw new Exception(); 
		}
		
	}

}
