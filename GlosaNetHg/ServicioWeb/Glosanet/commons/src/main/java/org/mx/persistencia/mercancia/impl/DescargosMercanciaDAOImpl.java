package org.mx.persistencia.mercancia.impl;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.mx.modelo.datastage.bo.DescargosMercanciaBO;
import org.mx.modelo.datastage.po.DescargosMercanciaPO;
import org.mx.modelo.util.archivos.Archivos;
import org.mx.persistencia.mercancia.DescargosMercanciaDAO;
import org.mx.persistencia.mercancia.builder.BuilderDescargosMercancias;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class DescargosMercanciaDAOImpl implements DescargosMercanciaDAO{
	private static final Logger log = Logger.getLogger(DescargosMercanciaDAOImpl.class.getName());

	@PersistenceContext
	private EntityManager em;
	//@Override
	protected DescargosMercanciaBO builderBO(DescargosMercanciaPO po) {
		return null;
	}

	//@Override
	protected DescargosMercanciaPO builderPO(DescargosMercanciaBO bo) {
		return BuilderDescargosMercancias.builderPO(bo);
	}

	
	@Override
	public boolean guardar(List<DescargosMercanciaBO> list){
		
		for (DescargosMercanciaBO entity : list) {
			try {
				save(entity);
			} catch (Exception e) {
				log.severe("Error al guardar la lista de descargo de mercancías. " + e.getMessage());
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public DescargosMercanciaBO save(DescargosMercanciaBO entity) {
		try {
			DescargosMercanciaPO po = builderPO(entity);
			em.persist(po);
			entity = builderBO(po);

			return entity;
		} catch (RuntimeException re) {
			log.severe("Error al guardar un descargo de mercancía. " + re.getMessage());
			em.getTransaction().rollback();
			throw re;
		}
	}

	@Override
	public boolean loadDataLocal(File file){
		try {
			StringBuilder sbQuery = new StringBuilder("LOAD DATA LOCAL INFILE '").append(file.getPath()).append("' \n");
			sbQuery.append("INTO TABLE ").append(Archivos.obtenerTabla(DescargosMercanciaPO.class.getSimpleName())).append(" \n");
			sbQuery.append("FIELDS TERMINATED BY '|' \n");
			sbQuery.append("LINES TERMINATED BY '\\r\\n' \n");
			sbQuery.append("IGNORE 1 LINES \n");
			log.info("Tabla: " + DescargosMercanciaPO.class.getSimpleName() + " Query: " + sbQuery.toString());

			Query query = em.createNativeQuery(sbQuery.toString());
			int salida = query.executeUpdate();
			log.info("Se insertó correctamente el archivo de descargos de mercancía a la tabla. Status: " + salida);
		} catch (Exception e) {
			log.severe("Error al cargar el archivo de descargos de mercancía a la base de datos. " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
}