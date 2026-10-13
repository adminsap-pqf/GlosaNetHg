package org.mx.persistencia.comun.impl;

import org.mx.modelo.comun.bo.CambioBitacoraBO;
import org.mx.modelo.comun.po.CambioBitacoraPO;
import org.mx.persistencia.comun.CambioBitacoraDAO;
import org.mx.persistencia.comun.builder.BuilderCambioBitacora;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Repository("cambioBitacoraDAO")
public class CambioBitacoraDAOImpl implements CambioBitacoraDAO {

    private final static Logger LOGGER = Logger.getLogger(CambioBitacoraDAOImpl.class.getName());

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    public void customInit() {
		LOGGER.info("@customInit: Inicio -> CONSTRUI usuarioService...");
    }

    @Override
    public int addCambioBitacora(CambioBitacoraBO cambioBitacora) throws Exception {
        try {
            String sql;

            em.clear();

            sql = "INSERT INTO PF_CambiosBitacora (Fecha, Descripcion, FK01_Usuario) "
                    + " VALUES (:fecha, :descripcion, :usuario)";

            Query query = em.createNativeQuery(sql);
            query.setParameter("fecha", cambioBitacora.getFecha());
            query.setParameter("descripcion", cambioBitacora.getDescripcion());
            query.setParameter("usuario", cambioBitacora.getUsuario());

            query.executeUpdate();

            Query id = em.createNativeQuery("SELECT LAST_INSERT_ID()");
            Long l = ((BigInteger) id.getSingleResult()).longValue();
            int r = l != null ? l.intValue() : 0;

            return r;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CambioBitacoraBO> findCambiosBitacora() {
        try {

            String sql;
            em.clear();

            sql = "SELECT c FROM CambioBitacoraPO c WHERE Activo = 1 ";

            Query query = em.createQuery(sql, CambioBitacoraPO.class);
            List<CambioBitacoraPO> lstPO = query.getResultList();
            List<CambioBitacoraBO> lstBO = new ArrayList<CambioBitacoraBO>();
            for (CambioBitacoraPO po : lstPO) {
                CambioBitacoraBO r = new CambioBitacoraBO();
                r = BuilderCambioBitacora.builderBO(po);
                lstBO.add(r);
            }

            return lstBO;

        } catch (NoResultException e) {
            e.printStackTrace();
        }
        return null;
    }
}
