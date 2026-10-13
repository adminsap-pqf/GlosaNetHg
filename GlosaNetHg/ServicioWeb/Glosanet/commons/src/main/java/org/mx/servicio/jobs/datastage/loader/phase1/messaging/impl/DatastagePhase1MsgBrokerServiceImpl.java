package org.mx.servicio.jobs.datastage.loader.phase1.messaging.impl;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.servicio.jobs.datastage.loader.phase1.messaging.DatastagePhase1MsgBrokerService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ryndem.mx.glosanet.messagebroker.Utils;
import ryndem.mx.glosanet.messagebroker.datastage.loader.phase1.config.RabbitConfig;
import ryndem.mx.glosanet.messagebroker.datastage.loader.phase1.document.JobMessage;
import ryndem.mx.glosanet.messagebroker.datastage.loader.phase1.model.bo.JobBO;
import ryndem.mx.glosanet.messagebroker.datastage.loader.phase1.persistence.JobDAO;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.logging.Logger;

import static ryndem.mx.glosanet.messagebroker.config.Protocols.*;

@Service
public class DatastagePhase1MsgBrokerServiceImpl implements DatastagePhase1MsgBrokerService {
    private final static Logger log = Logger.getLogger(DatastagePhase1MsgBrokerServiceImpl.class.getName());

    private JobDAO jobDAO;
    @Autowired
    @Qualifier(PHASE1_JOB_DAO_NAME)
    public void setJobDAO(JobDAO jobDAO) {
        this.jobDAO = jobDAO;
    }

    private RabbitConfig rabbitConfig;
    @Autowired
    @Qualifier(PHASE1_RABBIT_CONFIG_NAME)
    public void setRabbitConfig(RabbitConfig rabbitConfig) {
        this.rabbitConfig = rabbitConfig;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void sendMessage(Integer idUsuario, Integer idCliente, String rfc, String filePath, Integer idDownload)
            throws GlosaNetException {
        log.info("@sendMessage: Inicio");

        String guid = Utils.generateGUID();
        Instant instant = Instant.now();
        Timestamp timestamp = Timestamp.from(instant);
        JobBO businessObject = new JobBO(null, guid, RABBITMQ_JOB_STATE_RECEIVED, idDownload,
                timestamp, "");

        JobBO processedBusinessObject;
        try {
            log.info("@sendMessage: Storing in DB...");
            processedBusinessObject = jobDAO.save(businessObject);
        } catch (Exception e) {
            e.printStackTrace();
            log.severe("@sendMessage: Error storing in DB...");
            throw new GlosaNetException(e);
        }

        if (null == processedBusinessObject) return;
        log.info("@sendMessage: Sending message...");
        JobMessage jobMessage = new JobMessage(idUsuario, idCliente, processedBusinessObject.getGuid_job(), idDownload, rfc, filePath);

        String pretty_json_message = jobMessage.toPrettyJson();
        log.info("@sendMessage: Message sent <" + pretty_json_message + ">");

        String json_message = jobMessage.toRawJson();
        RabbitTemplate rabbitTemplate = rabbitConfig.fetchRabbitTemplate();
        rabbitTemplate.convertAndSend(json_message);

    }
}
