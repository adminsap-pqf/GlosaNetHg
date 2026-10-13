package ryndem.mx.glosanet.messagebroker.datastage.loader.phase1.persistence.impl;

import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ryndem.mx.glosanet.messagebroker.datastage.loader.phase1.model.bo.JobBO;
import ryndem.mx.glosanet.messagebroker.datastage.loader.phase1.model.dto.AllJobsDTO;
import ryndem.mx.glosanet.messagebroker.datastage.loader.phase1.model.po.JobPO;
import ryndem.mx.glosanet.messagebroker.datastage.loader.phase1.persistence.JobDAO;
import ryndem.mx.glosanet.messagebroker.datastage.loader.phase1.persistence.builder.BuilderJob;
import ryndem.mx.glosanet.messagebroker.datastage.loader.phase1.persistence.pages.AllJobsPage;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static ryndem.mx.glosanet.messagebroker.config.Protocols.PHASE1_JOB_DAO_NAME;

@Repository(PHASE1_JOB_DAO_NAME)
@Transactional
public class JobDAOImpl implements JobDAO {
    private static final Logger log = Logger.getLogger(JobDAOImpl.class.getName());

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean save(List<JobBO> list) {
        return false;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public JobBO save(JobBO entity) {
        try {
            JobPO persistenceObject = BuilderJob.builderPO(entity);
            em.persist(persistenceObject);
            entity = BuilderJob.builderBO(persistenceObject);
            return entity;
        } catch (RuntimeException re) {
            log.severe("Error al guardar el job. " + re.getMessage());
            em.getTransaction().rollback();
            throw re;
        }
    }

    @Override
    public AllJobsPage findJobs(String query, int page, int size) {

        try {
            Session session = em.unwrap(Session.class);
            log.info("Query obtenido: " + query);

            NativeQuery nativeQuery = session.createNativeQuery(query, AllJobsDTO.class);
            ScrollableResults resultScroll = nativeQuery.scroll(ScrollMode.SCROLL_INSENSITIVE);
            resultScroll.last();



            int totalResults = resultScroll.getRowNumber() + 1;
            resultScroll.first();
            int position = page * size;
            resultScroll.scroll(position < totalResults ? position : 0);

            List<AllJobsDTO> allJobsDTOList = new ArrayList<>();
            int i = 0;
            while (size > i++) {

                Object[] resultEntry = resultScroll.get();

                if (resultEntry != null)
                    allJobsDTOList.add((AllJobsDTO) resultEntry[0]);

                if (!resultScroll.next())
                    break;
            }

            AllJobsPage resultPage = new AllJobsPage();
            resultPage.setResults(allJobsDTOList);
            resultPage.setTotal(totalResults);
            log.info("Se encontraron " + totalResults + " jobs");

            return resultPage;
        } catch (Exception e) {
            log.severe("Error al buscar los jobs. " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public String findJobStacktraceByGUID(String GUID) {
        try {
            String sql;
            em.clear();

            sql = "SELECT stackTrace FROM JobPO where GUID = :GUID";
            Query query = em.createQuery(sql);
            query.setParameter("GUID", GUID);
            return (String) query.getSingleResult();
        } catch (Exception e) {
            log.severe("Error al obtener la traza del error por el id: " + GUID + " Mensaje del error: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Integer getIdDatastage(Integer idCliente, String folio) {
        Integer idDatastage;
        try {
            em.clear();
            Session session = em.unwrap(Session.class);

            Query query = session.createSQLQuery("SELECT PK_DataStage FROM GN_DataStage WHERE FK01_Cliente = :idCliente AND Folio = :folio");
            query.setParameter("idCliente", idCliente);
            query.setParameter("folio", folio);
            idDatastage = (Integer) query.getSingleResult();

            log.info("Id de DataStage: " + idDatastage);
        } catch (Exception ex) {
            return null;
        }
        return idDatastage;
    }
}
