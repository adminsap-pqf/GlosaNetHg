package org.mx.persistencia.pedimento.impl;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.mx.modelo.datastage.bo.ObservacionesPedimentoBO;
import org.mx.modelo.datastage.po.ObservacionesPedimentoPO;
import org.mx.modelo.util.archivos.Archivos;
import org.mx.persistencia.pedimento.ObservacionesPedimentoDAO;
import org.mx.persistencia.pedimento.builder.BuilderObservacionesPedimento;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class ObservacionesPedimentoDAOImpl implements ObservacionesPedimentoDAO {
	private static final Logger log = Logger.getLogger(ObservacionesPedimentoDAOImpl.class.getName());

	@PersistenceContext
	private EntityManager em;

	protected ObservacionesPedimentoBO builderBO(ObservacionesPedimentoPO po) {
		return null;
	}

	protected ObservacionesPedimentoPO builderPO(ObservacionesPedimentoBO bo) {
		return BuilderObservacionesPedimento.builderPO(bo);
	}

	@Override
	public boolean guardar(List<ObservacionesPedimentoBO> list){
		for (ObservacionesPedimentoBO entity : list) {
			try {
				save(entity);
			} catch (Exception e) {
				log.severe("Error al guardar la lista de observaciones de pedimento. " + e.getMessage());
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public ObservacionesPedimentoBO save(ObservacionesPedimentoBO entity) {
		try {
			ObservacionesPedimentoPO po = builderPO(entity);
			em.persist(po);
			entity = builderBO(po);

			return entity;
		} catch (RuntimeException re) {
			log.severe("Error al guardar una observación de pedimento. " + re.getMessage());
			em.getTransaction().rollback();
			throw re;
		}
	}

	@Override
	public boolean loadDataLocal(File file){
		try {
			StringBuilder sbQuery = new StringBuilder("LOAD DATA LOCAL INFILE '").append(file.getPath()).append("' \n");
			sbQuery.append("INTO TABLE ").append(Archivos.obtenerTabla(ObservacionesPedimentoPO.class.getSimpleName())).append(" \n");
			sbQuery.append("FIELDS TERMINATED BY '|' \n");
			sbQuery.append("LINES TERMINATED BY '\\r\\n' \n");
			sbQuery.append("IGNORE 1 LINES \n");
			log.info("Query: " + sbQuery.toString());

			Query query = em.createNativeQuery(sbQuery.toString());
			int salida = query.executeUpdate();
			log.info("Se cargó correctamente el archivo de obsevaciones de pedimento. Status: " + salida);
		} catch (Exception e) {
			log.info("Error al cargar el archivo de observaciones de pedimento. " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
}