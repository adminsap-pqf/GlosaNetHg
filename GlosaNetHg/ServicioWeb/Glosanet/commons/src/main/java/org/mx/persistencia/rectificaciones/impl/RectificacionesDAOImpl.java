package org.mx.persistencia.rectificaciones.impl;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.mx.modelo.datastage.bo.RectificacionesBO;
import org.mx.modelo.datastage.po.RectificacionesPO;
import org.mx.modelo.util.archivos.Archivos;
import org.mx.persistencia.rectificaciones.RectificacionesDAO;
import org.mx.persistencia.rectificaciones.builder.BuilderRectificaciones;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class RectificacionesDAOImpl implements RectificacionesDAO{
	private static final Logger log = Logger.getLogger(RectificacionesDAOImpl.class.getName());
	@PersistenceContext
	private EntityManager em;

	//@Override
	protected RectificacionesBO builderBO(RectificacionesPO po) {
		return null;
	}

	//@Override
	protected RectificacionesPO builderPO(RectificacionesBO bo) {
		return BuilderRectificaciones.builderPO(bo);
	}
	
	@Override
	public boolean guardar(List<RectificacionesBO> list){
		
		for (RectificacionesBO entity : list) {
			try {
				save(entity);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public RectificacionesBO save(RectificacionesBO entity) {
		try {
			RectificacionesPO po = builderPO(entity);
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
			System.out.println(RectificacionesPO.class.getSimpleName());

			StringBuilder sbQuery = new StringBuilder("LOAD DATA LOCAL INFILE '").append(file.getPath()).append("' \n");
			sbQuery.append("INTO TABLE ").append(Archivos.obtenerTabla(RectificacionesPO.class.getSimpleName())).append(" \n");
			sbQuery.append("FIELDS TERMINATED BY '|' \n");
			sbQuery.append("LINES TERMINATED BY '\\r\\n' \n");
			sbQuery.append("IGNORE 1 LINES \n");
			log.info("Query: " + sbQuery.toString());

			Query query = em.createNativeQuery(sbQuery.toString());
			int salida = query.executeUpdate();

			log.info("Se ha cargado el archivo de rectificaciones. Status: " + salida);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
