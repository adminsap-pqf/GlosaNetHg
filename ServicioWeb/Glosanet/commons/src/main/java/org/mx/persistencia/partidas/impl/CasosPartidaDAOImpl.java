package org.mx.persistencia.partidas.impl;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.mx.modelo.datastage.bo.CasosPartidaBO;
import org.mx.modelo.datastage.po.CasosPartidaPO;
import org.mx.modelo.util.archivos.Archivos;
import org.mx.persistencia.partidas.CasosPartidaDAO;
import org.mx.persistencia.partidas.builder.BuilderCasosPartida;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class CasosPartidaDAOImpl implements CasosPartidaDAO{
	public static final Logger log = Logger.getLogger(CasosPartidaDAOImpl.class.getName());

	@PersistenceContext
	private EntityManager em;

	//@Override
	protected CasosPartidaBO builderBO(CasosPartidaPO po) {
		return null;
	}

	//@Override
	protected CasosPartidaPO builderPO(CasosPartidaBO bo) {
		return BuilderCasosPartida.builderPO(bo);
	}

	@Override
	public boolean guardar(List<CasosPartidaBO> lst) {

		for (CasosPartidaBO entity : lst) {
			try {
				save(entity);
			} catch (Exception e) {
				log.severe("@guardar -> Ocurrió un error: " + e.getMessage());
				e.printStackTrace();
			}
		}
		log.info("@guardar -> Se guardó correctamente los casos de partida");
		return true;
	}

	public CasosPartidaBO save(CasosPartidaBO entity) {
		try {
//			if (!em.getTransaction().isActive())
//				em.getTransaction().begin();
			CasosPartidaPO po = builderPO(entity);
			em.persist(po);
			 
//			em.flush();
			//em.getTransaction().commit();
			entity = builderBO(po);
			log.info("@save -> Se guardó correctamente los casos de partida");
			return entity;
		} catch (RuntimeException re) {
			em.getTransaction().rollback();
			log.severe("@save -> Error al guardar: " + re.getMessage());
			throw re;
		}
	}

	@Override
	public boolean loadDataLocal(File file){

		try {
//			if (!em.getTransaction().isActive()){
//				em.getTransaction().begin();
//			}
			//

			StringBuilder sbQuery = new StringBuilder("LOAD DATA LOCAL INFILE '").append(file.getPath()).append("' \n");
			sbQuery.append("INTO TABLE ").append(Archivos.obtenerTabla(CasosPartidaPO.class.getSimpleName())).append(" \n");
			sbQuery.append("FIELDS TERMINATED BY '|' \n");
			sbQuery.append("LINES TERMINATED BY '\\r\\n' \n");
			sbQuery.append("IGNORE 1 LINES \n");
			//System.out.println(sbQuery.toString());
			Query query = em.createNativeQuery(sbQuery.toString());
			int salida = query.executeUpdate();

			log.info("@loadDataLocal -> Resultado de la carga local: " + salida);
			//em.getTransaction().commit();

		} catch (Exception e) {
			log.severe("@loadDataLocal -> Error al caregar los datos locales: " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
}
