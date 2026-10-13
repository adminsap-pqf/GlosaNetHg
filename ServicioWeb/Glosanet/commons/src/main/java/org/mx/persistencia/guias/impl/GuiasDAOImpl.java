package org.mx.persistencia.guias.impl;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.mx.modelo.datastage.bo.GuiasBO;
import org.mx.modelo.datastage.po.GuiasPO;
import org.mx.modelo.util.archivos.Archivos;
import org.mx.persistencia.guias.GuiasDAO;
import org.mx.persistencia.guias.builder.BuilderGuias;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class GuiasDAOImpl implements GuiasDAO{
	private static final Logger log = Logger.getLogger(GuiasDAOImpl.class.getName());

	@PersistenceContext
	private EntityManager em;

	//@Override
	protected GuiasBO builderBO(GuiasPO po) {
		return null;
	}

	//@Override
	protected GuiasPO builderPO(GuiasBO bo) {
		return BuilderGuias.builderPO(bo);
	}

	@Override
	public boolean guardar(List<GuiasBO> list){
		for (GuiasBO entity : list) {
			try {
				save(entity);
			} catch (Exception e) {
				log.severe("Error al guardar la lista de guías. " + e.getMessage());
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public GuiasBO save(GuiasBO entity) {
		try {
			GuiasPO po = builderPO(entity);
			em.persist(po);
			entity = builderBO(po);

			return entity;
		} catch (RuntimeException re) {
			log.severe("Error al guardar la guía. " + re.getMessage());
			em.getTransaction().rollback();
			throw re;
		}
	}

	@Override
	public boolean loadDataLocal(File file){

		try {
			StringBuilder sbQuery = new StringBuilder("LOAD DATA LOCAL INFILE '").append(file.getPath()).append("' \n");
			sbQuery.append("INTO TABLE ").append(Archivos.obtenerTabla(GuiasPO.class.getSimpleName())).append(" \n");
			sbQuery.append("FIELDS TERMINATED BY '|' \n");
			sbQuery.append("LINES TERMINATED BY '\\r\\n' \n");
			sbQuery.append("IGNORE 1 LINES \n");
			log.info("Tabla: " + GuiasPO.class.getSimpleName() + " Query: " + sbQuery.toString());

			Query query = em.createNativeQuery(sbQuery.toString());
			int salida = query.executeUpdate();
			log.info("Se ha cargado el archivo correctamente a la tabla " + GuiasPO.class.getSimpleName() + " Status: " + salida);
		} catch (Exception e) {
			log.severe("Error al cargar el archivo a la tabla de Guías. " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
}