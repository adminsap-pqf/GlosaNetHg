package org.mx.persistencia.pedimento.impl;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.mx.modelo.datastage.bo.TasasPedimentoBO;
import org.mx.modelo.datastage.po.TasasPedimentoPO;
import org.mx.modelo.util.archivos.Archivos;
import org.mx.persistencia.pedimento.TasaPedimentoDAO;
import org.mx.persistencia.pedimento.builder.BuilderTasasPedimento;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class TasaPedimentoDAOImpl implements TasaPedimentoDAO{
	private static final Logger log = Logger.getLogger(TasaPedimentoDAOImpl.class.getName());

	@PersistenceContext
	private EntityManager em;

	@Override
	public boolean guardar(List<TasasPedimentoBO> list) {
		for (TasasPedimentoBO entity : list) {
			try {
				save(entity);
			} catch (Exception e) {
				log.severe("Error al guardar la lista de tasas de pedimento. " + e.getMessage());
				e.printStackTrace();
			}
		}
		return true;
	}

	//@Override
	protected TasasPedimentoBO builderBO(TasasPedimentoPO po) {
		return null;
	}

	//@Override
	protected TasasPedimentoPO builderPO(TasasPedimentoBO bo) {
		return BuilderTasasPedimento.builderPO(bo);
	}
	
	public TasasPedimentoBO save(TasasPedimentoBO entity) {
		try {
			TasasPedimentoPO po = builderPO(entity);
			em.persist(po);
			entity = builderBO(po);

			return entity;
		} catch (RuntimeException re) {
			log.severe("Error al guardar una tasa de pedimento. " + re.getMessage());
			em.getTransaction().rollback();
			throw re;
		}
	}

	@Override
	public boolean loadDataLocal(File file){
		try {
			StringBuilder sbQuery = new StringBuilder("LOAD DATA LOCAL INFILE '").append(file.getPath()).append("' \n");
			sbQuery.append("INTO TABLE ").append(Archivos.obtenerTabla(TasasPedimentoPO.class.getSimpleName())).append(" \n");
			sbQuery.append("FIELDS TERMINATED BY '|' \n");
			sbQuery.append("LINES TERMINATED BY '\\r\\n' \n");
			sbQuery.append("IGNORE 1 LINES \n");
			log.info("Query: " + sbQuery.toString());

			Query query = em.createNativeQuery(sbQuery.toString());
			int salida = query.executeUpdate();
			log.info("Se cargó correctamente el archivo de tasa de pedimento. Status: " + salida);
		} catch (Exception e) {
			log.severe("Error al cargar el archivo de tasas de pedimento. " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
}