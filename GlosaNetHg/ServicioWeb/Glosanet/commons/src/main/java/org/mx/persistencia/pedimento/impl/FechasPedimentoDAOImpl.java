package org.mx.persistencia.pedimento.impl;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.mx.modelo.datastage.bo.FechasPedimentoBO;
import org.mx.modelo.datastage.po.FechasPedimentoPO;
import org.mx.modelo.util.archivos.Archivos;
import org.mx.persistencia.pedimento.FechasPedimentoDAO;
import org.mx.persistencia.pedimento.builder.BuilderFechasPedimento;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class FechasPedimentoDAOImpl implements FechasPedimentoDAO {
	private static final Logger log = Logger.getLogger(FechasPedimentoDAOImpl.class.getName());

	@PersistenceContext
	private EntityManager em;

	//@Override
	protected FechasPedimentoBO builderBO(FechasPedimentoPO po) {
		return null;
	}

	//@Override
	protected FechasPedimentoPO builderPO(FechasPedimentoBO bo) {
		return BuilderFechasPedimento.builderPO(bo);
	}

	@Override
	public boolean guardar(List<FechasPedimentoBO> list){
		for (FechasPedimentoBO entity : list) {
			try {
				save(entity);
			} catch (Exception e) {
				log.severe("Error al guardar la lista de fechas de pedimento. " + e.getMessage());
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public FechasPedimentoBO save(FechasPedimentoBO entity) {
		try {
			FechasPedimentoPO po = builderPO(entity);
			em.persist(po);
			entity = builderBO(po);

			return entity;
		} catch (RuntimeException re) {
			log.severe("Error al guardar una fecha de pedimento. " + re.getMessage());
			em.getTransaction().rollback();
			throw re;
		}
	}

	@Override
	public boolean loadDataLocal(File file){
		try {
			StringBuilder sbQuery = new StringBuilder("LOAD DATA LOCAL INFILE '").append(file.getPath()).append("' \n");
			sbQuery.append("INTO TABLE ").append(Archivos.obtenerTabla(FechasPedimentoPO.class.getSimpleName())).append(" \n");
			sbQuery.append("FIELDS TERMINATED BY '|' \n");
			sbQuery.append("LINES TERMINATED BY '\\r\\n' \n");
			sbQuery.append("IGNORE 1 LINES \n");
			log.info("Query: " + sbQuery.toString());

			Query query = em.createNativeQuery(sbQuery.toString());
			int salida = query.executeUpdate();
			log.info("Se cargó correctamente el archivo de fechas de pedimento. Status: " + salida);
		} catch (Exception e) {
			log.severe("Error al cargar el archivo de fechas de pedimento. " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
}