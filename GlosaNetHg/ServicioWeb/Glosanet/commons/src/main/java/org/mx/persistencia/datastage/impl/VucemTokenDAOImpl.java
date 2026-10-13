package org.mx.persistencia.datastage.impl;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.datastage.bo.VucemTokenBO;
import org.mx.modelo.datastage.po.VucemTokenPO;
import org.mx.persistencia.datastage.VucemTokenDAO;
import org.mx.persistencia.datastage.builder.BuilderVucemToken;
import org.mx.persistencia.registros.impl.RegistrosDAOImpl;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Component
public class VucemTokenDAOImpl implements VucemTokenDAO {

    private final static Logger LOGGER = Logger.getLogger(RegistrosDAOImpl.class.getName());

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public VucemTokenBO saveNuevoVucemToken(VucemTokenBO entity) throws GlosaNetException {
        try {
            entity.setLastDate(new Timestamp(new Date().getTime()));
            VucemTokenPO po = BuilderVucemToken.builderPO(entity);
            em.persist(po);
            return BuilderVucemToken.builderBO(po);

        } catch (Exception e) {
            em.getTransaction().rollback();
            LOGGER.severe("@saveNuevoVucemToken: exception -> " + e.toString());
            throw new GlosaNetException(e);
        }
    }

    @Override
    public VucemTokenBO findVucemTokenPorIdCliente(Long idCliente) throws GlosaNetException {
        try {
            String sql;
            em.clear();

            sql = "FROM VucemTokenPO where idCliente = :idCliente";
            Query query = em.createQuery(sql);
            query.setParameter("idCliente", idCliente);
            List<VucemTokenPO> list = query.getResultList();
            if (!list.isEmpty()) {
                return BuilderVucemToken.builderBO(list.get(0));
            } else {
                return new VucemTokenBO();
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            LOGGER.severe("@findVucemTokenPorIdCliente: exception -> " + e.toString());
            throw new GlosaNetException(e);
        }
    }

    @Override
    @Transactional
    public VucemTokenBO updateVucemToken(VucemTokenBO token)
            throws GlosaNetException {
        LOGGER.severe("@updateVucemToken: inicio ");
        try {
            Timestamp lastUpdateData = new Timestamp(new Date().getTime());
            String sql;
            em.clear();
            sql = "UPDATE GN_Vucem_Token SET Token = :token, LastUpdateDate = :lastUpdateDate, Activo = :activo WHERE PK_Token = :idToken";

            Query query = em.createNativeQuery(sql);
            query.setParameter("idToken", token.getPkToken());
            query.setParameter("token", token.getToken());
            query.setParameter("lastUpdateDate", lastUpdateData);
            query.setParameter("activo", token.isActivo());
            query.executeUpdate();

            token.setLastDate(lastUpdateData);
            return token;

        } catch (Exception e) {
            em.getTransaction().rollback();
            LOGGER.severe("@updateVucemToken: exception -> " + e.toString());
            throw new GlosaNetException(e);
        }
    }

    @Override
    @Transactional
    public boolean deleteVucemToken(Long idToken)
            throws GlosaNetException {
        LOGGER.severe("@deleteVucemToken: inicio ");

        try {
            String sql;
            em.clear();

            sql = "DELETE FROM GN_Vucem_Token WHERE PK_Token = :idToken";
            Query query = em.createNativeQuery(sql);
            query.setParameter("idToken", idToken);
            query.executeUpdate();
            return true;

        } catch (Exception e) {
            em.getTransaction().rollback();
            LOGGER.severe("@deleteVucemToken: exception -> " + e.toString());
            throw new GlosaNetException(e);
        }
    }
}
