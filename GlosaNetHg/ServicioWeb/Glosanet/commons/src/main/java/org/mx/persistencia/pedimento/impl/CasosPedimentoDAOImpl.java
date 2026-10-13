package org.mx.persistencia.pedimento.impl;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.mx.modelo.datastage.bo.CasosPedimentoBO;
import org.mx.modelo.datastage.po.CasosPedimentoPO;
import org.mx.modelo.util.archivos.Archivos;
import org.mx.persistencia.pedimento.CasosPedimentoDAO;
import org.mx.persistencia.pedimento.builder.BuilderCasosPedimento;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class CasosPedimentoDAOImpl implements CasosPedimentoDAO{
	private static final Logger log = Logger.getLogger(CasosPedimentoDAOImpl.class.getName());

	@PersistenceContext
	private EntityManager em;

	//@Override
	protected CasosPedimentoBO builderBO(CasosPedimentoPO po) {
		return null;
	}

	//@Override
	protected CasosPedimentoPO builderPO(CasosPedimentoBO bo) {
		return BuilderCasosPedimento.builderPO(bo);
	}
	
	@Override
	public boolean guardar(List<CasosPedimentoBO> list){
		for (CasosPedimentoBO entity : list) {
			try {
				save(entity);
			} catch (Exception e) {
				log.severe("Error al guardar la lista de casos de pedimento. " + e.getMessage());
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public CasosPedimentoBO save(CasosPedimentoBO entity) {
		try {
			CasosPedimentoPO po = builderPO(entity);
			em.persist(po);

			entity = builderBO(po);
			return entity;
		} catch (RuntimeException re) {
			log.severe("Error al guardar un caso de pedimento. " + re.getMessage());
			em.getTransaction().rollback();
			throw re;
		}
	}

	@Override
	public boolean loadDataLocal(File file){
		try {
			StringBuilder sbQuery = new StringBuilder("LOAD DATA LOCAL INFILE '").append(file.getPath()).append("' \n");
			sbQuery.append("INTO TABLE ").append(Archivos.obtenerTabla(CasosPedimentoPO.class.getSimpleName())).append(" \n");
			sbQuery.append("FIELDS TERMINATED BY '|' \n");
			sbQuery.append("LINES TERMINATED BY '\\r\\n' \n");
			sbQuery.append("IGNORE 1 LINES \n");
			log.info("Query: " + sbQuery.toString());

			Query query = em.createNativeQuery(sbQuery.toString());
			int salida = query.executeUpdate();

			log.info("Se ha cargado correctamente el archivo de casos de pedimento a la base de datos. Status: " + salida);
		} catch (Exception e) {
			log.severe("Error al cargar el archivo de casos de pedimento a la base de datos. " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
}