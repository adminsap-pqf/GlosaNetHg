package org.mx.persistencia.incidencias.impl;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.mx.modelo.datastage.bo.IncidenciasReconocimientoAduaneroBO;
import org.mx.modelo.datastage.po.IncidenciasReconocimientoAduaneroPO;
import org.mx.modelo.util.archivos.Archivos;
import org.mx.persistencia.incidencias.IncidenciasDAO;
import org.mx.persistencia.incidencias.builder.BuilderIncidencias;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class IncidenciasDAOImpl implements IncidenciasDAO {
	private static final Logger log = Logger.getLogger(IncidenciasDAOImpl.class.getName());

	@PersistenceContext
	private EntityManager em;

	//@Override
	protected IncidenciasReconocimientoAduaneroBO builderBO(
			IncidenciasReconocimientoAduaneroPO po) {
		return null;
	}

	//@Override
	protected IncidenciasReconocimientoAduaneroPO builderPO(
			IncidenciasReconocimientoAduaneroBO bo) {
		return BuilderIncidencias.builderPO(bo);
	}

	@Override
	public boolean guardar(List<IncidenciasReconocimientoAduaneroBO> list){
		for (IncidenciasReconocimientoAduaneroBO entity : list) {
			try {
				save(entity);
			} catch (Exception e) {
				log.severe("Error al guardar la lista de incidencias de reconocimiento aduanero. " + e.getMessage());
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public IncidenciasReconocimientoAduaneroBO save(IncidenciasReconocimientoAduaneroBO entity) {
		try {
			IncidenciasReconocimientoAduaneroPO po = builderPO(entity);
			em.persist(po);
			entity = builderBO(po);

			return entity;
		} catch (RuntimeException re) {
			log.severe("Error al guardar una incidencia de reconocimiento aduanero. " + re.getMessage());
			em.getTransaction().rollback();
			throw re;
		}
	}

	@Override
	public boolean loadDataLocal(File file){

		try {
			StringBuilder sbQuery = new StringBuilder("LOAD DATA LOCAL INFILE '").append(file.getPath()).append("' \n");
			sbQuery.append("INTO TABLE ").append(Archivos.obtenerTabla(IncidenciasReconocimientoAduaneroPO.class.getSimpleName())).append(" \n");
			sbQuery.append("FIELDS TERMINATED BY '|' \n");
			sbQuery.append("LINES TERMINATED BY '\\r\\n' \n");
			sbQuery.append("IGNORE 1 LINES \n");
			log.info("Tabla: " + IncidenciasReconocimientoAduaneroPO.class.getSimpleName() + " Query: " + sbQuery.toString());

			Query query = em.createNativeQuery(sbQuery.toString());
			int salida = query.executeUpdate();
			log.info("Se ha guardado correctamente el archivo de incidencias. Status: " + salida);
		} catch (Exception e) {
			log.severe("Error al guardar el archivo de incidencias en la base de datos. " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
}