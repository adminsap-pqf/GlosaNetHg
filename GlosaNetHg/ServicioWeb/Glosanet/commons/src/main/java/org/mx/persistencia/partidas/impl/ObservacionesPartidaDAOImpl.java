package org.mx.persistencia.partidas.impl;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.mx.modelo.datastage.bo.ObservacionesPartidaBO;
import org.mx.modelo.datastage.po.ObservacionesPartidaPO;
import org.mx.modelo.util.archivos.Archivos;
import org.mx.persistencia.partidas.ObservacionesPartidaDAO;
import org.mx.persistencia.partidas.builder.BuilderObservacionesPartida;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class ObservacionesPartidaDAOImpl implements ObservacionesPartidaDAO{
	private static final Logger log = Logger.getLogger(ObservacionesPartidaDAOImpl.class.getName());
	@PersistenceContext
	private EntityManager em;

	protected ObservacionesPartidaBO builderBO(ObservacionesPartidaPO po) {
		return null;
	}

	protected ObservacionesPartidaPO builderPO(ObservacionesPartidaBO bo) {
		return BuilderObservacionesPartida.builderPO(bo);
	}
	
	@Override
	public boolean guardar(List<ObservacionesPartidaBO> list){
		for (ObservacionesPartidaBO entity : list) {
			try {
				save(entity);
			} catch (Exception e) {
				log.severe("Error al guardar la lista de observaciones de partidas. " + e.getMessage());
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public ObservacionesPartidaBO save(ObservacionesPartidaBO entity) {
		try {
			ObservacionesPartidaPO po = builderPO(entity);
			em.persist(po);
			entity = builderBO(po);

			return entity;
		} catch (RuntimeException re) {
			log.severe("Error al guardar una observación de partida. " + re.getMessage());
			em.getTransaction().rollback();
			throw re;
		}
	}

	@Override
	public boolean loadDataLocal(File file){
		try {
			StringBuilder sbQuery = new StringBuilder("LOAD DATA LOCAL INFILE '").append(file.getPath()).append("' \n");
			sbQuery.append("INTO TABLE ").append(Archivos.obtenerTabla(ObservacionesPartidaPO.class.getSimpleName())).append(" \n");
			sbQuery.append("FIELDS TERMINATED BY '|' \n");
			sbQuery.append("LINES TERMINATED BY '\\r\\n' \n");
			sbQuery.append("IGNORE 1 LINES \n");
			log.info("Query: " + sbQuery.toString());

			Query query = em.createNativeQuery(sbQuery.toString());
			int salida = query.executeUpdate();
			log.info("Se cargó correctamente el archivo de observaciones de partidas. Status: " + salida);
		} catch (Exception e) {
			log.severe("Error al cargar el archivo de observaciones de partidas. " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
}