package org.mx.servicio.jobs.datastage.loader.phase1.processing;

import org.mx.controllers.jobs.datastage.phase1.DatastagePhase1JobsRequestOptions;
import org.mx.modelo.Exception.GlosaNetException;
import ryndem.mx.glosanet.messagebroker.datastage.loader.phase1.persistence.pages.AllJobsPage;

import java.util.Map;

public interface DatastagePhase1JobService {

    /**
     * Obtiene página filtrada y ordenada de trabajos de procesamiento de datastages fase1
     *
     * @param requestOptions
     * @return
     * @throws GlosaNetException
     */
    AllJobsPage listPaginatedJobs(DatastagePhase1JobsRequestOptions requestOptions) throws GlosaNetException;

    /**
     * @return
     */
    Map<String, Object> process(Integer idUsuario, Integer idCliente, String rfc, String filePath, Integer idDescarga);


    /**
     * @param GUID
     * @return
     */
    String findStacktraceOfGUID(String GUID) throws GlosaNetException;

    /**
     * Obtiene el id de datastage a partir del id de cliente y el folio
     *
     * @param idCliente
     * @param folio
     * @return
     * @throws GlosaNetException
     */
    Integer getIdDatastage(Integer idCliente, String folio) throws GlosaNetException;
}
