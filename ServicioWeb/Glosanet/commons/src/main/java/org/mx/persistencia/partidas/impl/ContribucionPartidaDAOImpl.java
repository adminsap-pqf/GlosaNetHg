package org.mx.persistencia.partidas.impl;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.datastage.bo.ContribucionesPartidaBO;
import org.mx.modelo.datastage.po.ContribucionesPartidaPO;
import org.mx.modelo.util.archivos.Archivos;
import org.mx.persistencia.partidas.ContribucionPartidaDAO;
import org.mx.persistencia.partidas.builder.BuilderContribucionesPartida;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class ContribucionPartidaDAOImpl implements ContribucionPartidaDAO{
	private static final Logger log = Logger.getLogger(ContribucionPartidaDAOImpl.class.getName());

	@PersistenceContext
	private EntityManager em;

	//@Override
	protected ContribucionesPartidaBO builderBO(ContribucionesPartidaPO po) {
		return BuilderContribucionesPartida.builderBO(po);
	}

	//@Override
	protected ContribucionesPartidaPO builderPO(ContribucionesPartidaBO bo) {
		return BuilderContribucionesPartida.builderPO(bo);
	}

	@Override
	public boolean guardar(List<ContribucionesPartidaBO> list){

		for (ContribucionesPartidaBO entity : list) {
			try {
				save(entity);
			} catch (Exception e) {
				log.severe("Error " + e.getMessage()); e.printStackTrace();
			}
		}
		return true;
	}

	@Override
	public ContribucionesPartidaBO findTop1() throws GlosaNetException {

		try {
			if (!em.getTransaction().isActive())
				em.getTransaction().begin();
			StringBuilder sbQuery = new StringBuilder("FROM ContribucionesPartidaPO");
			Query query = em.createQuery(sbQuery.toString());
			ContribucionesPartidaPO po = null;
			if (!query.getResultList().isEmpty()){
				query.setFirstResult(0);
				query.setMaxResults(1);
				po = (ContribucionesPartidaPO) query.getSingleResult();
			}
			if (po != null)
				return builderBO(po);
		} catch (Exception e) {
			log.severe("Error " + e.getMessage()); e.printStackTrace();
		}
		return null;
	}

	public ContribucionesPartidaBO save(ContribucionesPartidaBO entity) {
		try {
			ContribucionesPartidaPO po = builderPO(entity);
			em.persist(po);

			entity = builderBO(po);
			return entity;
		} catch (RuntimeException re) {
			em.getTransaction().rollback();
			 
			throw re;
		}
	}

	@Override
	public boolean loadDataLocal(File file){

		try {
			log.info(ContribucionesPartidaPO.class.getSimpleName());

			StringBuilder sbQuery = new StringBuilder("LOAD DATA LOCAL INFILE '").append(file.getPath()).append("' \n");
			sbQuery.append("INTO TABLE ").append(Archivos.obtenerTabla(ContribucionesPartidaPO.class.getSimpleName())).append(" \n");
			sbQuery.append("FIELDS TERMINATED BY '|' \n");
			sbQuery.append("LINES TERMINATED BY '\\r\\n' \n");
			sbQuery.append("IGNORE 1 LINES \n");
			log.info(sbQuery.toString());
			Query query = em.createNativeQuery(sbQuery.toString());
			int salida = query.executeUpdate();

			log.info("Se cargó correctamente las contribuciones de partidas. Status: " + salida);
			//em.getTransaction().commit();

		} catch (Exception e) {
			log.severe("Error " + e.getMessage()); e.printStackTrace();
		}
		return false;
	}

}
