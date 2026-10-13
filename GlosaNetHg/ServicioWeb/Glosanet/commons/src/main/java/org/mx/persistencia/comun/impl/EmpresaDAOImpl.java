package org.mx.persistencia.comun.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.mx.modelo.comun.bo.EmpresaBO;
import org.mx.persistencia.comun.EmpresaDAO;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class EmpresaDAOImpl implements EmpresaDAO{
	private static final Logger log = Logger.getLogger(EmpresaDAOImpl.class.getName());

	@PersistenceContext
	private EntityManager em;

	@Override
	public boolean updateVigencia(EmpresaBO empresa) throws Exception {
		try{
			String sql;
			em.clear();

			sql = "UPDATE GN_Empresa SET VigenciaMatriz = :vigenciaMatriz , VigenciaMatrizFin = :vigenciaMatrizFin "
					+ "WHERE PK_Empresa = :idEmpresa";
			log.info("Query: " + sql);
			Query query = em.createNativeQuery(sql);
			query.setParameter("vigenciaMatriz", empresa.getVigenciaMatriz());
			query.setParameter("vigenciaMatrizFin", empresa.getVigenciaMatrizFin());
			query.setParameter("idEmpresa", empresa.getIdEmpresa());

			query.executeUpdate();
			log.info("La vigencia de la matríz se ha actualizado correctamente");
			return true;

		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
	}
}
