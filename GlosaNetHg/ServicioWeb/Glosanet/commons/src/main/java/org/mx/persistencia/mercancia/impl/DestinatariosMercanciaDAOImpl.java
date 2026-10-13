package org.mx.persistencia.mercancia.impl;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.mx.modelo.datastage.bo.DestinatariosMercanciaBO;
import org.mx.modelo.datastage.po.DestinatariosMercanciaPO;
import org.mx.modelo.util.archivos.Archivos;
import org.mx.persistencia.mercancia.DestinatariosMercanciaDAO;
import org.mx.persistencia.mercancia.builder.BuilderDestinatariosMercancia;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class DestinatariosMercanciaDAOImpl implements DestinatariosMercanciaDAO{
	private static final Logger log = Logger.getLogger(DestinatariosMercanciaDAOImpl.class.getName());

	@PersistenceContext
	private EntityManager em;
	//@Override
	protected DestinatariosMercanciaBO builderBO(DestinatariosMercanciaPO po) {
		return null;
	}

	//@Override
	protected DestinatariosMercanciaPO builderPO(DestinatariosMercanciaBO bo) {
		return BuilderDestinatariosMercancia.builderPO(bo);
	}
	
	@Override
	public boolean guardar(List<DestinatariosMercanciaBO> list){
		
		for (DestinatariosMercanciaBO entity : list) {
			try {
				save(entity);
			} catch (Exception e) {
				log.severe("Error al guardar la lista de destinatarios de mercancía. " + e.getMessage());
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public DestinatariosMercanciaBO save(DestinatariosMercanciaBO entity) {
		try {
			DestinatariosMercanciaPO po = builderPO(entity);
			em.persist(po);
			entity = builderBO(po);
			log.info("Se guardó correctamente un destinatario de mercancía");

			return entity;
		} catch (RuntimeException re) {
			log.severe("Error al guardar un destinatario de mercancía. " + re.getMessage());
			em.getTransaction().rollback();
			throw re;
		}
	}

	@Override
	public boolean loadDataLocal(File file){
		try {
			StringBuilder sbQuery = new StringBuilder("LOAD DATA LOCAL INFILE '").append(file.getPath()).append("' \n");
			sbQuery.append("INTO TABLE ").append(Archivos.obtenerTabla(DestinatariosMercanciaPO.class.getSimpleName())).append(" \n");
			sbQuery.append("FIELDS TERMINATED BY '|' \n");
			sbQuery.append("LINES TERMINATED BY '\\r\\n' \n");
			sbQuery.append("IGNORE 1 LINES \n");
			log.info("Tabla: " + DestinatariosMercanciaPO.class.getSimpleName() + " Query: " + sbQuery.toString());

			Query query = em.createNativeQuery(sbQuery.toString());
			int salida = query.executeUpdate();
			log.info("Se cargó correctamente el archivo de destinatarios de mercancías a la tabla. Status: " + salida );
		} catch (Exception e) {
			log.severe("Error al cargar el archivo de destinatarios de mercancía a la BD. " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
}