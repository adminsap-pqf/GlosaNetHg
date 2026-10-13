package org.mx.persistencia.mercancia.impl;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.mx.modelo.datastage.bo.TransporteMercanciasBO;
import org.mx.modelo.datastage.po.TransporteMercanciasPO;
import org.mx.modelo.util.archivos.Archivos;
import org.mx.persistencia.mercancia.TransporteMercanciasDAO;
import org.mx.persistencia.mercancia.builder.BuilderTransporteMercancias;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class TransporteMercanciasDAOImpl implements TransporteMercanciasDAO{
	private static final Logger log = Logger.getLogger(TransporteMercanciasDAOImpl.class.getName());

	@PersistenceContext
	private EntityManager em;

	@Override
	public boolean guardar(List<TransporteMercanciasBO> list) {
		for(TransporteMercanciasBO entity : list){
			try{
				save(entity);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return true;
	}

	//@Override
	protected TransporteMercanciasBO builderBO(TransporteMercanciasPO po) {
		// TODO Auto-generated method stub
		return null;
	}

	//@Override
	protected TransporteMercanciasPO builderPO(TransporteMercanciasBO bo) {
		return BuilderTransporteMercancias.builderPO(bo);
	}
	
	public TransporteMercanciasBO save(TransporteMercanciasBO entity) {
		try {
			TransporteMercanciasPO po = builderPO(entity);
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
			StringBuilder sbQuery = new StringBuilder("LOAD DATA LOCAL INFILE '").append(file.getPath()).append("' \n");
			sbQuery.append("INTO TABLE ").append(Archivos.obtenerTabla(TransporteMercanciasPO.class.getSimpleName())).append(" \n");
			sbQuery.append("FIELDS TERMINATED BY '|' \n");
			sbQuery.append("LINES TERMINATED BY '\\r\\n' \n");
			sbQuery.append("IGNORE 1 LINES \n");
			log.info("Tabla: " + TransporteMercanciasPO.class.getSimpleName() + " Query: " + sbQuery.toString());

			Query query = em.createNativeQuery(sbQuery.toString());
			int salida = query.executeUpdate();
			log.info("Se cargó correctamente el archivo de transporte de mercancías. Status: " + salida);
		} catch (Exception e) {
			log.severe("Error al cargar el archivo de transporte de mercancías a la base de datos. " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
}