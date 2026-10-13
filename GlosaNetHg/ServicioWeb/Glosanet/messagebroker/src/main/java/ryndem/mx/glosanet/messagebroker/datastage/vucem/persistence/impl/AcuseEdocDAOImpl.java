package ryndem.mx.glosanet.messagebroker.datastage.vucem.persistence.impl;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ryndem.mx.glosanet.messagebroker.datastage.vucem.model.po.SolicitudAcuseEdocPO;
import ryndem.mx.glosanet.messagebroker.datastage.vucem.model.po.SolicitudEdocPO;
import ryndem.mx.glosanet.messagebroker.datastage.vucem.persistence.AcuseEdocDAO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import static ryndem.mx.glosanet.messagebroker.config.Protocols.*;

@Repository(VUCEM_ACUSE_EDOC_JOB_DAO_NAME)
@Transactional
public class AcuseEdocDAOImpl implements AcuseEdocDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<SolicitudAcuseEdocPO> saveAcuseEdoc(List<SolicitudAcuseEdocPO> list) {
        for (SolicitudAcuseEdocPO model : list) {
            try {
                em.persist(model);
            } catch (RuntimeException re) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                re.printStackTrace(pw);
                model.setStacktrace(sw.toString().substring(0,8000));
                model.setErrorMessage(re.getMessage());
                em.getTransaction().rollback();
                //throw re;
            }
        }
        return list;
    }
    @Override
    public SolicitudAcuseEdocPO saveAcuseEdoc(SolicitudAcuseEdocPO model) {
        try {
            em.persist(model);
        } catch (RuntimeException re) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            re.printStackTrace(pw);
            model.setStacktrace(sw.toString().substring(0,8000));
            model.setErrorMessage(re.getMessage());
            //em.getTransaction().rollback();
        }
        return model;
    }

    @Override
    public SolicitudAcuseEdocPO getRequestByCustomer(Long idCustomer) {
        try {
            Session session = em.unwrap(Session.class);
            Query query = session.createNativeQuery("SELECT * FROM GN_Vucem_AcuseEdoc WHERE idCustomer = :id");
            query.setParameter("id", idCustomer);
            return (SolicitudAcuseEdocPO) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public SolicitudAcuseEdocPO getRequestById(Long id) {
        try {
            Session session = em.unwrap(Session.class);
            return session.find(SolicitudAcuseEdocPO.class, id);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void resetStatus(Long id) {
        Session session = em.unwrap(Session.class);
        SolicitudAcuseEdocPO obj = session.find(SolicitudAcuseEdocPO.class, id);
        obj.setStatus(RABBITMQ_JOB_STATE_RECEIVED);

        em.persist(obj);
    }
}
