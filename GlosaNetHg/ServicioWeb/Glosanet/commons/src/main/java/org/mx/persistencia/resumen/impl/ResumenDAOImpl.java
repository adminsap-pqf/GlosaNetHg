package org.mx.persistencia.resumen.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.mx.modelo.datastage.bo.ResumenBO;
import org.mx.modelo.datastage.po.ResumenPO;
import org.mx.persistencia.resumen.ResumenDAO;
import org.mx.persistencia.resumen.builder.BuilderResumen;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class ResumenDAOImpl implements ResumenDAO{
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public boolean guardar(List<ResumenBO> list, int idDatastage){
		
		for (ResumenBO entity : list) {
			try {
				save(entity, idDatastage);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	protected ResumenBO builderBO(ResumenPO po) {
		return null;
	}

	protected ResumenPO builderPO(ResumenBO bo) {
		return BuilderResumen.builderResumen(bo);
	}
	
	public ResumenBO save(ResumenBO entity, int idDatastage) {
//		try {
//		System.out.println(idDatastage);
//			ResumenPO po = BuilderResumen.builderResumen(entity); 
//			po.getDataStage().setPkDataStage(idDatastage);
//			em.persist(po);
//
//			entity = builderBO(po);
//			return entity;
//		} catch (RuntimeException re) {
//			 
//			em.getTransaction().rollback();
//			
//			throw re;
//		}
		try{
			String sql;

			em.clear();
			
			sql = "INSERT INTO   Resumen  ( Folio ,  RFCoPatenteAduanal ,  Fecha_Inicial ,  Fecha_Final ,  Fecha_Ejecucion ,  Total_Fracciones ,  Total_Contribuciones ,  FK_DataStage ) "
					+ "VALUES (:folio ,  :rfc ,  :finicial ,  :ffin ,  :fejecucion ,  :tfracciones ,  :tcont ,  :ds )";

			Query query = em.createNativeQuery(sql);
			query.setParameter("folio", entity.getFolio());
			query.setParameter("rfc", entity.getRfcoPatenteAduanal());
			query.setParameter("finicial", entity.getFechaInicial());
			query.setParameter("ffin", entity.getFechaFinal());
			query.setParameter("fejecucion", entity.getFechaEjecucion());
			query.setParameter("tfracciones", entity.getTotalFracciones());
			query.setParameter("tcont", entity.getTotalContribuciones());
			query.setParameter("ds", idDatastage);
			
			query.executeUpdate();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
}








