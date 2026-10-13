package org.mx.persistencia.partidas.impl;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.mx.modelo.datastage.bo.PermisosPartidaBO;
import org.mx.modelo.datastage.po.PermisosPartidaPO;
import org.mx.modelo.util.archivos.Archivos;
import org.mx.persistencia.partidas.PermisosPartidaDAO;
import org.mx.persistencia.partidas.builder.BuilderPermisosPartida;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class PermisosPartidaDAOImpl implements PermisosPartidaDAO {
	private static final Logger log = Logger.getLogger(PermisosPartidaDAOImpl.class.getName());

	@PersistenceContext
	private EntityManager em;

	protected PermisosPartidaBO builderBO(PermisosPartidaPO po) {
		return null;
	}

	protected PermisosPartidaPO builderPO(PermisosPartidaBO bo) {
		return BuilderPermisosPartida.builderPO(bo);
	}

	@Override
	public boolean guardar(List<PermisosPartidaBO> list){
		
		for (PermisosPartidaBO entity : list) {
			try {
				save(entity);
			} catch (Exception e) {
				log.severe("Error al guardar la lista de permisos de partida. " + e.getMessage());
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public PermisosPartidaBO save(PermisosPartidaBO entity) {
		try {
			PermisosPartidaPO po = builderPO(entity);
			em.persist(po);
			entity = builderBO(po);

			return entity;
		} catch (RuntimeException re) {
			log.severe("Erro al guardar un permiso a la partida. " + re.getMessage());
			em.getTransaction().rollback();
			throw re;
		}
	}

	@Override
	public boolean loadDataLocal(File file){
		try {
			StringBuilder sbQuery = new StringBuilder("LOAD DATA LOCAL INFILE '").append(file.getPath()).append("' \n");
			sbQuery.append("INTO TABLE ").append(Archivos.obtenerTabla(PermisosPartidaPO.class.getSimpleName())).append(" \n");
			sbQuery.append("FIELDS TERMINATED BY '|' \n");
			sbQuery.append("LINES TERMINATED BY '\\r\\n' \n");
			sbQuery.append("IGNORE 1 LINES \n");
			log.info("Query: " + sbQuery.toString());

			Query query = em.createNativeQuery(sbQuery.toString());
			int salida = query.executeUpdate();
			log.info("Se cargó correctamente el archivo de permisos en partidas. Status: " + salida);
		} catch (Exception e) {
			log.severe("Error al cargar el archivo de permisos en partidas. " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
}
