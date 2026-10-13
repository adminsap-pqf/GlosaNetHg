package org.mx.persistencia.aduaneras.impl;

import org.mx.modelo.datastage.bo.CuentasAduanerasGarantiaPedimentoBO;
import org.mx.modelo.datastage.po.CuentasAduanerasGarantiaPedimentoPO;
import org.mx.modelo.util.archivos.Archivos;
import org.mx.persistencia.aduaneras.CuentasAduanerasGarantiaPedimentoDAO;
import org.mx.persistencia.aduaneras.builder.BuilderCuentasAduanerasGarantiaPedimento;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.File;
import java.util.List;
import java.util.logging.Logger;

@Component
@Transactional
public class CuentasAduanerasGarantiaPedimentoDAOImpl implements CuentasAduanerasGarantiaPedimentoDAO {
    private final static Logger log = Logger.getLogger(CuentasAduanerasGarantiaPedimentoDAOImpl.class.getName());

    @PersistenceContext
    private EntityManager em;
//	public CuentasAduanerasGarantiaPedimentoDAOImpl() {
//		super(CuentasAduanerasGarantiaPedimentoPO.class);
//	}

    //@Override
    protected CuentasAduanerasGarantiaPedimentoBO builderBO(
            CuentasAduanerasGarantiaPedimentoPO po) {
        return null;
    }

    //@Override
    protected CuentasAduanerasGarantiaPedimentoPO builderPO(
            CuentasAduanerasGarantiaPedimentoBO bo) {
        return BuilderCuentasAduanerasGarantiaPedimento.builderPO(bo);
    }

    @Override
    public boolean guardar(List<CuentasAduanerasGarantiaPedimentoBO> list) {

        for (CuentasAduanerasGarantiaPedimentoBO entity : list) {
            try {
                save(entity);
            } catch (Exception e) {
                log.severe("Error al guardar la lista de cuentas aduaneras de garantía de pedimentos: " + e.getMessage());
                e.printStackTrace();
            }
        }
        log.info("Se guardó la lista de cuentas aduaneras de garantía de pedimento");
        return true;
    }

    public CuentasAduanerasGarantiaPedimentoBO save(CuentasAduanerasGarantiaPedimentoBO entity) {
        try {
            CuentasAduanerasGarantiaPedimentoPO po = builderPO(entity);
            em.persist(po);
            entity = builderBO(po);
            log.info("Se guardó la cuneta aduanera de garantía de pedimento");
            return entity;
        } catch (RuntimeException re) {
            log.severe("Error al guardar la cuenta aduanera de garantía de pedimento. " + re.getMessage());
            em.getTransaction().rollback();

            throw re;
        }
    }

    @Override
    public boolean loadDataLocal(File file) {

        try {
            StringBuilder sbQuery = new StringBuilder("LOAD DATA LOCAL INFILE '").append(file.getPath()).append("' \n");
            sbQuery.append("INTO TABLE ").append(Archivos.obtenerTabla(CuentasAduanerasGarantiaPedimentoPO.class.getSimpleName())).append(" \n");
            sbQuery.append("FIELDS TERMINATED BY '|' \n");
            sbQuery.append("LINES TERMINATED BY '\\r\\n' \n");
            sbQuery.append("IGNORE 1 LINES \n");
            log.info("Query de carga de cuentas aduaneras de garantía pedimento: " + sbQuery.toString());
            Query query = em.createNativeQuery(sbQuery.toString());
            int salida = query.executeUpdate();

            log.info("Resultado de la carga de cuentas aduaneras de garantía de pedimento: " + salida);
        } catch (Exception e) {
        	log.severe("Error al cargar la lista de cuentas aduaneras de garantía de pedimento: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}

