package org.mx.servicio.comun.impl;

import java.util.List;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.HistorialImportacionesBO;
import org.mx.persistencia.comun.HistorialImportacionesDAO;
import org.mx.servicio.comun.HistorialImportacionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;


@Service("historialImportacionesService")
public class HistorialImportacionesImpl implements HistorialImportacionesService {
	
	@Autowired
	HistorialImportacionesDAO historialImportacionesDAO;
	
	
	@Override
	public List<HistorialImportacionesBO> obtenerHistorialImportaciones(int idproducto,boolean flagProductoPartida) throws GlosaNetException {
		return historialImportacionesDAO.obteneHistorialImportaciones(idproducto, flagProductoPartida);
	}
	
	@Override
	@Transactional(rollbackFor={Exception.class})
   public boolean insertarHistorialImportacion(HistorialImportacionesBO bo) throws Exception{
		try{
			 return historialImportacionesDAO.insertarHistorialImportacion(bo);
		}catch(Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
			.setRollbackOnly();
			throw new Exception();
		}
	  
   }
	
//	@Override
//	public List<HistorialPermisosBO> obtenerPermisos(Parametros bo){
//		return historialImportacionesDAO.obtenerPermisos(bo);
//	}

	
	
}
