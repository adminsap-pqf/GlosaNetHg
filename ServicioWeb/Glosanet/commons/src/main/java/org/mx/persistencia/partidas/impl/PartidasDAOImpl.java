package org.mx.persistencia.partidas.impl;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.mx.modelo.datastage.bo.PartidaBO;
import org.mx.modelo.datastage.po.PartidasPO;
import org.mx.modelo.util.archivos.Archivos;
import org.mx.persistencia.partidas.PartidasDAO;
import org.mx.persistencia.partidas.builder.BuilderPartida;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class PartidasDAOImpl implements PartidasDAO{
	private static final Logger log = Logger.getLogger(PartidasDAOImpl.class.getName());

	@PersistenceContext
	private EntityManager em;

	protected PartidaBO builderBO(PartidasPO po) {
		return null;
	}

	protected PartidasPO builderPO(PartidaBO bo) {
		return BuilderPartida.builderPO(bo);
	}

	@Override
	public boolean guardar(List<PartidaBO> list){
		for (PartidaBO entity : list) {
			try {
				save(entity);
			} catch (Exception e) {
				log.severe("Error al guardar la lista de partidas. " + e.getMessage());
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public PartidaBO save(PartidaBO entity) {
		try {
			PartidasPO po = builderPO(entity);
			em.persist(po);
			entity = builderBO(po);

			return entity;
		} catch (RuntimeException re) {
			log.severe("Error al guardar una partida. " + entity.getPatente());
			em.getTransaction().rollback();
			throw re;
		}
	}

	@Override
	public boolean loadDataLocal(File file){
		try {
			StringBuilder sbQuery = new StringBuilder("LOAD DATA LOCAL INFILE '").append(file.getPath()).append("' \n");
			sbQuery.append("INTO TABLE ").append(Archivos.obtenerTabla(PartidasPO.class.getSimpleName())).append(" \n");
			sbQuery.append("FIELDS TERMINATED BY '|' \n");
			sbQuery.append("LINES TERMINATED BY '\\r\\n' \n");
			sbQuery.append("IGNORE 1 LINES \n");
			log.info("Query: " + sbQuery.toString());

			Query query = em.createNativeQuery(sbQuery.toString());
			int salida = query.executeUpdate();
			log.info("Se cargó correctamente el archivo de partidas. Status: " + salida);
		} catch (Exception e) {
			log.severe("Error al cargar el archivo de partidas. " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
}
