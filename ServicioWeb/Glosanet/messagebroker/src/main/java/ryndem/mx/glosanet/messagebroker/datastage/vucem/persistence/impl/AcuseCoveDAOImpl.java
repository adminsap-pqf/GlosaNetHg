package ryndem.mx.glosanet.messagebroker.datastage.vucem.persistence.impl;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ryndem.mx.glosanet.messagebroker.datastage.vucem.model.po.SolicitudAcuseFacturaPO;
import ryndem.mx.glosanet.messagebroker.datastage.vucem.persistence.AcuseCoveDAO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import static ryndem.mx.glosanet.messagebroker.config.Protocols.RABBITMQ_JOB_STATE_RECEIVED;
import static ryndem.mx.glosanet.messagebroker.config.Protocols.VUCEM_ACUSE_COVE_JOB_DAO_NAME;

@Repository(VUCEM_ACUSE_COVE_JOB_DAO_NAME)
@Transactional
public class AcuseCoveDAOImpl implements AcuseCoveDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public List<SolicitudAcuseFacturaPO> saveFactura(List<SolicitudAcuseFacturaPO> list) {
        for (SolicitudAcuseFacturaPO model : list) {
            try {
                em.persist(model);
            } catch (RuntimeException re) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                re.printStackTrace(pw);
                model.setStacktrace(sw.toString().substring(0,8000));
                model.setErrorMessage(re.getMessage());
                em.getTransaction().rollback();
                throw re;
            }
        }
        return list;
    }
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public SolicitudAcuseFacturaPO saveFactura(SolicitudAcuseFacturaPO model) {
        try {
            em.persist(model);
        } catch (RuntimeException re) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            re.printStackTrace(pw);
            model.setStacktrace(sw.toString().substring(0,8000));
            model.setErrorMessage(re.getMessage());
            em.getTransaction().rollback();
            throw re;
        }
        return model;
    }

    @Override
    public SolicitudAcuseFacturaPO getRequestByCustomer(Long idCustomer) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT o FROM SolicitudAcuseFacturaPO o where o.idCustomer = :id");
            query.setParameter("id", idCustomer);
            return (SolicitudAcuseFacturaPO) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public SolicitudAcuseFacturaPO getRequestById(Long idReceipt) {
        try {
            Session session = em.unwrap(Session.class);
            return session.find(SolicitudAcuseFacturaPO.class, idReceipt);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void resetStatus(Long id) {
        Session session = em.unwrap(Session.class);
        SolicitudAcuseFacturaPO obj = session.find(SolicitudAcuseFacturaPO.class, id);
        obj.setStatus(RABBITMQ_JOB_STATE_RECEIVED);

        em.persist(obj);
    }
}
