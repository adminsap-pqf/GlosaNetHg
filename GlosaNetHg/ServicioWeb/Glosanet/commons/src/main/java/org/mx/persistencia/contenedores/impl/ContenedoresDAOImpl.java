package org.mx.persistencia.contenedores.impl;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.mx.modelo.datastage.bo.ContenedoresBO;
import org.mx.modelo.datastage.po.ContenedoresPO;
import org.mx.modelo.util.archivos.Archivos;
import org.mx.persistencia.contenedores.ContenedoresDAO;
import org.mx.persistencia.contenedores.builder.BuilderContenedores;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class ContenedoresDAOImpl implements ContenedoresDAO{
	private static final Logger log = Logger.getLogger(ContenedoresDAOImpl.class.getName());

	@PersistenceContext
	private EntityManager em;	

	protected ContenedoresBO builderBO(ContenedoresPO po) {
		return null;
	}
	protected ContenedoresPO builderPO(ContenedoresBO bo) {
		return BuilderContenedores.builderPO(bo);
	}
	
	@Override
	public boolean guardar(List<ContenedoresBO> list){
		
		for (ContenedoresBO entity : list) {
			try {
				save(entity);
			} catch (Exception e) {
				log.severe("Error al guardar la lista de contenedores. " + e.getMessage());
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public ContenedoresBO save(ContenedoresBO entity) {
		try {
			ContenedoresPO po = builderPO(entity);
			em.persist(po);
			entity = builderBO(po);

			return entity;
		} catch (RuntimeException re) {
			log.severe("Error al guardar el contenedor. " + re.getMessage());
			em.getTransaction().rollback();
			throw re;
		}
	}

	@Override
	public boolean loadDataLocal(File file){
		try {
			StringBuilder sbQuery = new StringBuilder("LOAD DATA LOCAL INFILE '").append(file.getPath()).append("' \n");
			sbQuery.append("INTO TABLE ").append(Archivos.obtenerTabla(ContenedoresPO.class.getSimpleName())).append(" \n");
			sbQuery.append("FIELDS TERMINATED BY '|' \n");
			sbQuery.append("LINES TERMINATED BY '\\r\\n' \n");
			sbQuery.append("IGNORE 1 LINES \n");
			log.info("Query: " + sbQuery.toString());

			Query query = em.createNativeQuery(sbQuery.toString());
			int salida = query.executeUpdate();
			log.info("Se cargó correctamente el archivo a la tabla de contenedores. Status: " + salida);
		} catch (Exception e) {
			log.severe("Error al cargar el archivo de contenedores a la BD. " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	
}
