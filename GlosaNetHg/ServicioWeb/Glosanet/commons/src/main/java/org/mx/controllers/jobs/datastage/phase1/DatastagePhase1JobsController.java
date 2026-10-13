package org.mx.controllers.jobs.datastage.phase1;

import org.mx.servicio.jobs.datastage.loader.phase1.processing.DatastagePhase1JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ryndem.mx.glosanet.messagebroker.datastage.loader.phase1.persistence.pages.AllJobsPage;
import java.util.logging.Logger;

import static org.mx.controllers.jobs.datastage.phase1.DatastagePhase1JobsRequestOptions.DSP1J_ORDERBYVALUE_CREATIONDATE;
import static org.mx.controllers.jobs.datastage.phase1.DatastagePhase1JobsRequestOptions.DSP1J_ORDERBY_ASC;

@CrossOrigin
@Controller
public class DatastagePhase1JobsController {
    private final static Logger log = Logger.getLogger(DatastagePhase1JobsController.class.getName());
    private final static String URL = "/jobs/datastagePhase1";
    
    @Autowired
    private DatastagePhase1JobService datastagePhase1JobService;
    
    @RequestMapping(
            value = URL,
            method = RequestMethod.GET,
            produces = "application/json"
    )
    @ResponseBody
    public ResponseEntity findAllJobs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size,
            @RequestParam(defaultValue = "") String filterInitialDate,
            @RequestParam(defaultValue = "") String filterFinalDate,
            @RequestParam(defaultValue = "") String filterStatus,
            @RequestParam(defaultValue = "") String filterCompany,
            @RequestParam(defaultValue = DSP1J_ORDERBYVALUE_CREATIONDATE) String orderByValue,
            @RequestParam(defaultValue = DSP1J_ORDERBY_ASC) String orderBySymbol
    ) {
        ResponseEntity responseEntity;
        try {
            DatastagePhase1JobsRequestOptions param = new DatastagePhase1JobsRequestOptions
                    .DatastagePhase1JobsRequestOptionsBuilder(page, size)
                    .setFilterInitialDate(filterInitialDate)
                    .setFilterFinalDate(filterFinalDate)
                    .setFilterStatus(filterStatus)
                    .setFilterCompany(filterCompany)
                    .setOrderBy(orderByValue)
                    .setOrderBySymbol(orderBySymbol)
                    .build();

            AllJobsPage resultPage = datastagePhase1JobService.listPaginatedJobs(param);
            responseEntity = new ResponseEntity<>(resultPage, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @RequestMapping(
            value = URL + "/{GUID}/stacktrace",
            method = RequestMethod.GET,
            produces = "application/json"
    )
    @ResponseBody
    public ResponseEntity findJobStacktrace(
            @PathVariable String GUID
    ) {
        ResponseEntity responseEntity;
        try {
            String stackTrace = datastagePhase1JobService.findStacktraceOfGUID(GUID);
            responseEntity = new ResponseEntity<>(stackTrace, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}