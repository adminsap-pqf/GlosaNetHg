package ryndem.mx.glosanet.messagebroker.datastage.loader.phase1.persistence;

import ryndem.mx.glosanet.messagebroker.datastage.loader.phase1.model.bo.JobBO;
import ryndem.mx.glosanet.messagebroker.datastage.loader.phase1.persistence.pages.AllJobsPage;

import java.util.List;

public interface JobDAO {

    /**
     * @param list
     * @return
     */
    boolean save(List<JobBO> list);

    /**
     * @param entity
     * @return
     */
    JobBO save(JobBO entity);

    /**
     * @param query
     * @param page
     * @param size
     * @return
     */
    AllJobsPage findJobs(String query, int page, int size);


    /**
     * @param GUID
     * @return
     */
    String findJobStacktraceByGUID(String GUID);

    Integer getIdDatastage(Integer idCliente, String folio);
}
