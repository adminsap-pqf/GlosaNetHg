package org.mx.persistencia.pedimento.impl;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.mx.modelo.datastage.bo.ContribucionesPedimentoBO;
import org.mx.modelo.datastage.po.ContribucionesPedimentoPO;
import org.mx.modelo.util.archivos.Archivos;
import org.mx.persistencia.pedimento.ContribucionesPedimentoDAO;
import org.mx.persistencia.pedimento.builder.BuilderContribucionesPedimento;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class ContribucionesPedimentoDAOImpl implements ContribucionesPedimentoDAO{
	private static final Logger log = Logger.getLogger(ContribucionesPedimentoDAOImpl.class.getName());

	@PersistenceContext
	private EntityManager em;

	//@Override
	protected ContribucionesPedimentoBO builderBO(ContribucionesPedimentoPO po) {
		return null;
	}
	
	//@Override
	protected ContribucionesPedimentoPO builderPO(ContribucionesPedimentoBO bo) {
		return BuilderContribucionesPedimento.builderPO(bo);
	}
	
	@Override
	public boolean guardar(List<ContribucionesPedimentoBO> list){
		
		for (ContribucionesPedimentoBO entity : list) {
			try {
				save(entity);
			} catch (Exception e) {
				log.severe("Error al guardar la lista de contribuciones de pedimento. " + e.getMessage());
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public ContribucionesPedimentoBO save(ContribucionesPedimentoBO entity) {
		try {
			ContribucionesPedimentoPO po = builderPO(entity);
			em.persist(po);
			entity = builderBO(po);

			return entity;
		} catch (RuntimeException re) {
			log.severe("Error al guardar una contribución de pedimento. " + entity.getPatente());
			em.getTransaction().rollback();
			throw re;
		}
	}

	@Override
	public boolean loadDataLocal(File file){
		try {
			StringBuilder sbQuery = new StringBuilder("LOAD DATA LOCAL INFILE '").append(file.getPath()).append("' \n");
			sbQuery.append("INTO TABLE ").append(Archivos.obtenerTabla(ContribucionesPedimentoPO.class.getSimpleName())).append(" \n");
			sbQuery.append("FIELDS TERMINATED BY '|' \n");
			sbQuery.append("LINES TERMINATED BY '\\r\\n' \n");
			sbQuery.append("IGNORE 1 LINES \n");
			log.info("Query: " + sbQuery.toString());

			Query query = em.createNativeQuery(sbQuery.toString());
			int salida = query.executeUpdate();
			log.info("Se cargó correctamente el archivo de contribuciones de pedimento. Status: " + salida);
		} catch (Exception e) {
			log.severe("Error al cargar el archivo de contribuciones de pedimento. " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
}