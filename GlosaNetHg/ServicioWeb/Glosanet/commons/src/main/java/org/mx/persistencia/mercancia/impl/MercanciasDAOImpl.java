package org.mx.persistencia.mercancia.impl;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.mx.modelo.datastage.bo.MercanciasBO;
import org.mx.modelo.datastage.po.MercanciasPO;
import org.mx.modelo.util.archivos.Archivos;
import org.mx.persistencia.mercancia.MercanciasDAO;
import org.mx.persistencia.mercancia.builder.BuilderMercancias;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class MercanciasDAOImpl implements MercanciasDAO {
	private static final Logger log = Logger.getLogger(MercanciasDAOImpl.class.getName());

	@PersistenceContext
	private EntityManager em;
	//@Override
	protected MercanciasBO builderBO(MercanciasPO po) {
		return null;
	}

	//@Override
	protected MercanciasPO builderPO(MercanciasBO bo) {
		return BuilderMercancias.builderPO(bo);
	}

	@Override
	public boolean guardar(List<MercanciasBO> list){
		log.info("Comienza el guardado de la lista de mercancías");
		for (MercanciasBO entity : list) {
			try {
				save(entity);
			} catch (Exception e) {
				log.severe("Error al guardar la lista de mercancías. " + e.getMessage());
				e.printStackTrace();
			}
		}
		log.info("Finalizó el guardado de la lista de mercancías");
		return true;
	}
	
	public MercanciasBO save(MercanciasBO entity) {
		try {
			MercanciasPO po = builderPO(entity);
			em.persist(po);
			entity = builderBO(po);
			log.info("Se guardó la mercancía. ");
			return entity;
		} catch (RuntimeException re) {
			log.severe("Error al guardar la mercancía. " + re.getMessage());
			em.getTransaction().rollback();
			 
			throw re;
		}
	}

	@Override
	public boolean loadDataLocal(File file){
		try {
			StringBuilder sbQuery = new StringBuilder("LOAD DATA LOCAL INFILE '").append(file.getPath()).append("' \n");
			sbQuery.append("INTO TABLE ").append(Archivos.obtenerTabla(MercanciasPO.class.getSimpleName())).append(" \n");
			sbQuery.append("FIELDS TERMINATED BY '|' \n");
			sbQuery.append("LINES TERMINATED BY '\\r\\n' \n");
			sbQuery.append("IGNORE 1 LINES \n");
			log.info("Tabla: " + MercanciasPO.class.getSimpleName() + " Query: " + sbQuery.toString());

			Query query = em.createNativeQuery(sbQuery.toString());
			int salida = query.executeUpdate();
			log.info("Se cargó correctamente el archivo de mercancías a la tabla. Status: " + salida);
		} catch (Exception e) {
			log.severe("Error al cargar el archivo de mercancías a la base de datos. " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
}