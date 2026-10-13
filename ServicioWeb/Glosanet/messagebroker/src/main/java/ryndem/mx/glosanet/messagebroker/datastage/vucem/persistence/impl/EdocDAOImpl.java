package ryndem.mx.glosanet.messagebroker.datastage.vucem.persistence.impl;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ryndem.mx.glosanet.messagebroker.datastage.vucem.model.po.SolicitudAcuseEdocPO;
import ryndem.mx.glosanet.messagebroker.datastage.vucem.model.po.SolicitudEdocPO;
import ryndem.mx.glosanet.messagebroker.datastage.vucem.persistence.EdocDAO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import static ryndem.mx.glosanet.messagebroker.config.Protocols.RABBITMQ_JOB_STATE_RECEIVED;
import static ryndem.mx.glosanet.messagebroker.config.Protocols.VUCEM_EDOC_JOB_DAO_NAME;

@Repository(VUCEM_EDOC_JOB_DAO_NAME)
@Transactional
public class EdocDAOImpl implements EdocDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<SolicitudEdocPO> saveEdoc(List<SolicitudEdocPO> list) {
        for (SolicitudEdocPO model : list) {
            try {
                em.persist(model);
            } catch (RuntimeException re) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                re.printStackTrace(pw);
                model.setStacktrace(sw.toString().substring(0,8000));
                model.setErrorMessage(re.getMessage());
                //em.getTransaction().rollback();
                //throw re;
            }
        }
        return list;
    }
    @Override
    public SolicitudEdocPO saveEdoc(SolicitudEdocPO model) {
        try {
            em.persist(model);
        } catch (RuntimeException re) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            re.printStackTrace(pw);
            model.setStacktrace(sw.toString().substring(0,8000));
            model.setErrorMessage(re.getMessage());
            em.getTransaction().rollback();
        }
        return model;
    }

    @Override
    public SolicitudEdocPO getRequestByCustomer(Long idCustomer) {
        try {
            Session session = em.unwrap(Session.class);
            Query query = session.createQuery("SELECT o FROM SolicitudEdocPO o WHERE o.idCustomer = :id");
            query.setParameter("id", idCustomer);
            return (SolicitudEdocPO) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    @Override
    public SolicitudEdocPO getRequestById(Long idReceipt) {
        try {
            Session session = em.unwrap(Session.class);
            return session.find(SolicitudEdocPO.class, idReceipt);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void resetStatus(Long id) {
        Session session = em.unwrap(Session.class);
        SolicitudEdocPO obj = session.find(SolicitudEdocPO.class, id);
        obj.setStatus(RABBITMQ_JOB_STATE_RECEIVED);

        em.persist(obj);
    }
}
