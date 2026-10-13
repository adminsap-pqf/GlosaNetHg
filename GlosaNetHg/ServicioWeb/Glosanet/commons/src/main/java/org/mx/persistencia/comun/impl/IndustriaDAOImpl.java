package org.mx.persistencia.comun.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.mx.modelo.comun.bo.IndustriaBO;
import org.mx.modelo.comun.po.IndustriaPO;
import org.mx.persistencia.comun.IndustriaDAO;
import org.mx.persistencia.comun.builder.BuilderIndustria;
import org.springframework.stereotype.Component;

@Component
public class IndustriaDAOImpl implements IndustriaDAO{

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public int addIndustria(IndustriaBO industria) throws Exception{
		try{
			String sql;

			em.clear();

			sql = "INSERT INTO Industrias (NombreES, NombreEN, DescripcionES, DescripcionEN, Activo) "
					+ " VALUES (:nombreEs, :nombreEn, :descripcionEs, :descripcionEn, :activo)";

			Query query = em.createNativeQuery(sql);
			query.setParameter("nombreEs", industria.getNombreEs());
			query.setParameter("nombreEn", industria.getNombreEn());
			query.setParameter("descripcionEs", industria.getDescripcionEs());
			query.setParameter("descripcionEn", industria.getDescripcionEn());
			query.setParameter("activo", industria.isActivo());

			query.executeUpdate();

			Query id = em.createNativeQuery("SELECT LAST_INSERT_ID()");
			Long l = ((BigInteger) id.getSingleResult()).longValue() ;
			int r = l != null ? l.intValue() : 0;

			return r;
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
	}

	@Override
	public boolean updateIndustria(IndustriaBO industria) throws Exception{
		try{
			String sql;

			em.clear();

			sql = "UPDATE Industrias SET NombreES = :nombreEs , NombreEN = :nombreEn, DescripcionES = :descripcionEs, "
					+ " DescripcionEN = :descripcionEn, Activo = :activo WHERE PK_Industria = :idIndustria";

			Query query = em.createNativeQuery(sql);
			query.setParameter("nombreEs", industria.getNombreEs());
			query.setParameter("nombreEn", industria.getNombreEn());
			query.setParameter("descripcionEs", industria.getDescripcionEs());
			query.setParameter("descripcionEn", industria.getDescripcionEn());
			query.setParameter("activo", industria.isActivo());
			query.setParameter("idIndustria", industria.getIdIndustria());

			query.executeUpdate();

			return true;

		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IndustriaBO> findIndustria(){
		try{

			String sql;
			em.clear();
			
			sql = "SELECT i FROM IndustriaPO i WHERE Activo = 1 ";
			
			Query query = em.createQuery(sql, IndustriaPO.class);
			List<IndustriaPO> lstPO = query.getResultList();
			List<IndustriaBO> lstBO = new ArrayList<IndustriaBO>();
			for(IndustriaPO po : lstPO){
				IndustriaBO r = new IndustriaBO();
				r = BuilderIndustria.builderBO(po);
				lstBO.add(r);
			}

			return lstBO;

		}catch (NoResultException e) {
			e.printStackTrace();
		}
		return null;
	}
}
