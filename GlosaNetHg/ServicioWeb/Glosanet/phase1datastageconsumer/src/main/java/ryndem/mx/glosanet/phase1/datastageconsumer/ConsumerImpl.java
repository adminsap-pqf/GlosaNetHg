package ryndem.mx.glosanet.phase1.datastageconsumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mx.modelo.Exception.GlosaNetException;
import org.mx.servicio.jobs.datastage.loader.phase1.processing.DatastagePhase1JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ryndem.mx.glosanet.messagebroker.Consumer;
import ryndem.mx.glosanet.messagebroker.datastage.loader.phase1.document.JobMessage;
import ryndem.mx.glosanet.messagebroker.datastage.loader.phase1.model.po.JobPO;
import ryndem.mx.glosanet.messagebroker.datastage.loader.phase1.persistence.IJobDAO;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import static ryndem.mx.glosanet.messagebroker.config.Protocols.*;

@Component
public class ConsumerImpl implements Consumer {
    private static final Logger log = Logger.getLogger(ConsumerImpl.class.getName());

    @Autowired
    IJobDAO iJobDAO;

    @Autowired
    DatastagePhase1JobService datastagePhase1JobService;

    @Value("${server.glosanet.uri}") private String glosanetUri;
    @Value("${trigger.datastage.onfinish}") private String endpointWhenFinish;
    @Value("${trigger.datastage.defaultpagesize}") private Integer defaultPageSize;

    @Override
    public void consumeMessage(String message) {

        //Recepción de mensaje
        if (null == message) {
            log.info("Sin mensajes para procesar");
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        JobMessage jobMessage = null;

        //Conversión a tipo mensaje de tarea
        try {
            jobMessage = mapper.readValue(message, JobMessage.class);
        } catch (IOException e) {
            log.severe("Error al generar el json de JobMessage");
            e.printStackTrace();
            return;
        }

        String pretty_json_message = jobMessage.toPrettyJson();
        log.info("Mensaje recibido con json formateado <" + pretty_json_message + ">");

        //Obtenemos objeto del repositorio de Jobs y lo actualizamos para ponerlo en proceso (estado 1)
        Optional<JobPO> optionalJobPO = null;
        try {
            optionalJobPO = iJobDAO.findByGUID(jobMessage.getIdJob());
        } catch (Exception e) {
            log.severe("Error al consultar el job");
            e.printStackTrace();
        }
        if (!optionalJobPO.isPresent()) {
            log.info("Error al consultar el job");
            return;
        }

        JobPO jobPO = optionalJobPO.get();
        jobPO.setStatus(RABBITMQ_JOB_STATE_PROCESSING);
        try {
            jobPO = iJobDAO.save(jobPO);
        } catch (Exception e) {
            log.severe("Error al actualizar el job con initialState = 1");
            e.printStackTrace();
            return;
        }

        //Inicia procesamiento
        Map<String, Object> results = datastagePhase1JobService.process(jobMessage.getIdUser(),
                jobMessage.getIdClient(), jobMessage.getRfc(), jobMessage.getFilePath(), jobMessage.getIdDownload());

        if (null == results) {
            log.info("Sin resultados después de procesar el job");
            return;
        }

        jobPO.setStatus((Integer) results.get("consumerProcessResult") == 0 ? RABBITMQ_JOB_STATE_FAILURE : RABBITMQ_JOB_STATE_SUCCESS);
        String stackTrace = (String) results.get("stackTrace");
        jobPO.setStackTrace(stackTrace);

        try {
            iJobDAO.save(jobPO);
        } catch (Exception e) {
            log.severe("Error al actualizar el job");
            e.printStackTrace();
        }

        /*
        Finaliza carga de DataStages y comienza el envío de mensajes a la cola de RabbitMQ
         */
        final String uri = glosanetUri + String.format(endpointWhenFinish, defaultPageSize, results.get("idDatastage"));
        log.info("Realizando petición GET - " + uri);
        RestTemplate restTemplate = new RestTemplate();
        String result;

        try {
            result = (String) restTemplate.getForObject(uri, String.class);
            if (!result.equals("{\n" +
                    "  \"result\": \"ok\"\n" +
                    "}"))
                log.info("Exito al mandar la petición: GET - " + String.format(endpointWhenFinish, defaultPageSize, results.get("idDatastage")));
        } catch (Exception e) {
            log.severe("Error al lanzar petición: GET - " + String.format(endpointWhenFinish, defaultPageSize, results.get("idDatastage")));
            e.printStackTrace();
        }
    }
}
