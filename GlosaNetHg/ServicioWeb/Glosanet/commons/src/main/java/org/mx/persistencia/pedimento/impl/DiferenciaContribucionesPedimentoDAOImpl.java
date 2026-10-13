package org.mx.persistencia.pedimento.impl;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.mx.modelo.datastage.bo.DiferenciasContribucionesPedimentoBO;
import org.mx.modelo.datastage.po.DiferenciasContribucionesPedimentoPO;
import org.mx.modelo.util.archivos.Archivos;
import org.mx.persistencia.pedimento.DiferenciaContribucionesPedimentoDAO;
import org.mx.persistencia.pedimento.builder.BuilderDiferenciasContribucionesPedimento;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class DiferenciaContribucionesPedimentoDAOImpl implements DiferenciaContribucionesPedimentoDAO{
	private static final Logger log = Logger.getLogger(DiferenciaContribucionesPedimentoDAOImpl.class.getName());
	@PersistenceContext
	private EntityManager em;

	protected DiferenciasContribucionesPedimentoBO builderBO(
			DiferenciasContribucionesPedimentoPO po) {
		return null;
	}

	protected DiferenciasContribucionesPedimentoPO builderPO(
			DiferenciasContribucionesPedimentoBO bo) {
		return BuilderDiferenciasContribucionesPedimento.builderPO(bo);
	}
	
	@Override
	public boolean guardar(List<DiferenciasContribucionesPedimentoBO> list){
		
		for (DiferenciasContribucionesPedimentoBO entity : list) {
			try {
				save(entity);
			} catch (Exception e) {
				log.severe("Error al guardar la lista de diferencia de contribuciones de pedimento. " + e.getMessage());
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public DiferenciasContribucionesPedimentoBO save(DiferenciasContribucionesPedimentoBO entity) {
		try {
			DiferenciasContribucionesPedimentoPO po = builderPO(entity);
			em.persist(po);
			entity = builderBO(po);
			return entity;
		} catch (RuntimeException re) {
			log.severe("Error al guardar una diferencia de contribución de pedimento. " + re.getMessage());
			em.getTransaction().rollback();
			throw re;
		}
	}

	@Override
	public boolean loadDataLocal(File file){
		try {
			StringBuilder sbQuery = new StringBuilder("LOAD DATA LOCAL INFILE '").append(file.getPath()).append("' \n");
			sbQuery.append("INTO TABLE ").append(Archivos.obtenerTabla(DiferenciasContribucionesPedimentoPO.class.getSimpleName())).append(" \n");
			sbQuery.append("FIELDS TERMINATED BY '|' \n");
			sbQuery.append("LINES TERMINATED BY '\\r\\n' \n");
			sbQuery.append("IGNORE 1 LINES \n");
			log.info("Query: " + sbQuery.toString());

			Query query = em.createNativeQuery(sbQuery.toString());
			int salida = query.executeUpdate();
			log.info("Se ha cargado el archivo de diferencia de contribuciones de pedimentos. Status: " + salida);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
