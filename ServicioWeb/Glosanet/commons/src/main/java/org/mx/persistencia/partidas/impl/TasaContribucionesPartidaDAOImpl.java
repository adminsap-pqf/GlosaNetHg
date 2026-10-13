package org.mx.persistencia.partidas.impl;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.mx.modelo.datastage.bo.TasasContribucionesPartidaBO;
import org.mx.modelo.datastage.po.TasasContribucionesPartidaPO;
import org.mx.modelo.util.archivos.Archivos;
import org.mx.persistencia.partidas.TasaContribucionesPartidaDAO;
import org.mx.persistencia.partidas.builder.BuilderTasaContribucionesPartida;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class TasaContribucionesPartidaDAOImpl implements TasaContribucionesPartidaDAO{
	private static final Logger log = Logger.getLogger(TasaContribucionesPartidaDAOImpl.class.getName());

	@PersistenceContext
	private EntityManager em;

	@Override
	public boolean guardar(List<TasasContribucionesPartidaBO> list) {
		for (TasasContribucionesPartidaBO entity : list) {
			try {
				save(entity);
			} catch (Exception e) {
				log.severe("Error al guardar la lista de tasas de contribuciones de partida. " + e.getMessage());
				e.printStackTrace();
			}
		}
		return true;
	}

	//@Override
	protected TasasContribucionesPartidaBO builderBO(
			TasasContribucionesPartidaPO po) {
		return null;
	}

	//@Override
	protected TasasContribucionesPartidaPO builderPO(
			TasasContribucionesPartidaBO bo) {
		return BuilderTasaContribucionesPartida.builderPO(bo);
	}
	
	public TasasContribucionesPartidaBO save(TasasContribucionesPartidaBO entity) {
		try {
			TasasContribucionesPartidaPO po = builderPO(entity);
			em.persist(po);

			entity = builderBO(po);
			return entity;
		} catch (RuntimeException re) {
			log.severe("Error al guardar una tasa de contribución de partida. " + re.getMessage());
			em.getTransaction().rollback();
			throw re;
		}
	}

	@Override
	public boolean loadDataLocal(File file){

		try {
			StringBuilder sbQuery = new StringBuilder("LOAD DATA LOCAL INFILE '").append(file.getPath()).append("' \n");
			sbQuery.append("INTO TABLE ").append(Archivos.obtenerTabla(TasasContribucionesPartidaPO.class.getSimpleName())).append(" \n");
			sbQuery.append("FIELDS TERMINATED BY '|' \n");
			sbQuery.append("LINES TERMINATED BY '\\r\\n' \n");
			sbQuery.append("IGNORE 1 LINES \n");
			log.info("Query: " + sbQuery.toString());

			Query query = em.createNativeQuery(sbQuery.toString());
			int salida = query.executeUpdate();
			log.info("Se cargó correctamente el archivo de tasas de contribuciones por partida. Status: " + salida);
		} catch (Exception e) {
			log.severe("Error al cargar el archivo de tasas de contribuciones por partida. " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	
}
