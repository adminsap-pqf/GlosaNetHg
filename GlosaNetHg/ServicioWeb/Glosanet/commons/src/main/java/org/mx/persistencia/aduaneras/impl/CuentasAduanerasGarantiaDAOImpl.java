package org.mx.persistencia.aduaneras.impl;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.mx.modelo.datastage.bo.CuentasAduanerasGarantiaBO;
import org.mx.modelo.datastage.po.CuentasAduanerasGarantiaPO;
import org.mx.modelo.util.archivos.Archivos;
import org.mx.persistencia.aduaneras.CuentasAduanerasGarantiaDAO;
import org.mx.persistencia.aduaneras.builder.BuilderCuentasAduanerasGarantia;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class CuentasAduanerasGarantiaDAOImpl implements CuentasAduanerasGarantiaDAO{
	private final static Logger log = Logger.getLogger(CuentasAduanerasGarantiaDAOImpl.class.getName());

	@PersistenceContext
	private EntityManager em;
//	public CuentasAduanerasGarantiaDAOImpl() {
//		super(CuentasAduanerasGarantiaPO.class);
//		// TODO Auto-generated constructor stub
//	}
	
	//@Override
	protected CuentasAduanerasGarantiaBO builderBO(CuentasAduanerasGarantiaPO po) {
		return null;
	}

	//@Override
	protected CuentasAduanerasGarantiaPO builderPO(CuentasAduanerasGarantiaBO bo) {
		return BuilderCuentasAduanerasGarantia.builderPO(bo);
	}
	
	@Override
	public boolean guardar(List<CuentasAduanerasGarantiaBO> list){
		for (CuentasAduanerasGarantiaBO entity : list) {
			try {
				save(entity);
			} catch (Exception e) {
				log.severe("Error al guardar la lista de cuentas aduaneras de garantía: " + e.getMessage());
				e.printStackTrace();
			}
		}
		log.info("Se guardó la lista de cuentas aduaneras de garantía");
		return true;
	}
	
	public CuentasAduanerasGarantiaBO save(CuentasAduanerasGarantiaBO entity) {
		try {
			CuentasAduanerasGarantiaPO po = builderPO(entity);
			em.persist(po);
			entity = builderBO(po);
			log.info("Se guardó la cuneta aduanera de garantía");
			return entity;
		} catch (RuntimeException re) {
			log.severe("Error al guardar la cuenta aduanera de garantía. " + re.getMessage());
			em.getTransaction().rollback();
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
			sbQuery.append("INTO TABLE ").append(Archivos.obtenerTabla(CuentasAduanerasGarantiaPO.class.getSimpleName())).append(" \n");
			sbQuery.append("FIELDS TERMINATED BY '|' \n");
			sbQuery.append("LINES TERMINATED BY '\\r\\n' \n");
			sbQuery.append("IGNORE 1 LINES \n");
			log.info("Carga de cuentas aduaneras: " + sbQuery.toString());

			Query query = em.createNativeQuery(sbQuery.toString());
			int salida = query.executeUpdate();
			log.info("Resultado de la carga de cuentas aduaneras a la BD: " + salida);
			//em.getTransaction().commit();

		} catch (Exception e) {
			log.severe("Error al cargar el archivo de cuentas aduaneras a la BD: " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
}
