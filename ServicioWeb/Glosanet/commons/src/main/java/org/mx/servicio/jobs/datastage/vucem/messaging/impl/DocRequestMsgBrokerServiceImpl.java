package org.mx.servicio.jobs.datastage.vucem.messaging.impl;

import org.mx.controllers.datastage.CustomLoadRequestOptions;
import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.datastage.bo.CasosPedimentoCustomBO;
import org.mx.modelo.datastage.bo.FacturaCoveBO;
import org.mx.persistencia.Page;
import org.mx.servicio.jobs.datastage.vucem.messaging.DocRequestMsgBrokerService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ryndem.mx.glosanet.messagebroker.VucemDocuments;
import ryndem.mx.glosanet.messagebroker.datastage.vucem.config.RabbitConfig;
import ryndem.mx.glosanet.messagebroker.datastage.vucem.document.VucemDocumentRequest;
import ryndem.mx.glosanet.messagebroker.datastage.vucem.document.VucemDocumentsRequestMessage;
import ryndem.mx.glosanet.messagebroker.datastage.vucem.model.po.SolicitudAcuseEdocPO;
import ryndem.mx.glosanet.messagebroker.datastage.vucem.model.po.SolicitudAcuseFacturaPO;
import ryndem.mx.glosanet.messagebroker.datastage.vucem.model.po.SolicitudEdocPO;
import ryndem.mx.glosanet.messagebroker.datastage.vucem.persistence.AcuseCoveDAO;
import ryndem.mx.glosanet.messagebroker.datastage.vucem.persistence.AcuseEdocDAO;
import ryndem.mx.glosanet.messagebroker.datastage.vucem.persistence.EdocDAO;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import static ryndem.mx.glosanet.messagebroker.config.Protocols.*;

@Service
public class DocRequestMsgBrokerServiceImpl implements DocRequestMsgBrokerService {
    private static final Logger log = Logger.getLogger(DocRequestMsgBrokerServiceImpl.class.getName());
    List<List<VucemDocumentRequest>> partitionsList;
    List<VucemDocumentRequest> receiptsList;
    VucemDocumentsRequestMessage messageList;
    private AcuseCoveDAO acuseCoveDAO;
    private EdocDAO edocDAO;
    private AcuseEdocDAO acuseEdocDAO;
    private RabbitConfig rabbitConfig;

    @Autowired
    @Qualifier(VUCEM_ACUSE_COVE_JOB_DAO_NAME)
    public void setAcuseCoveDAO(AcuseCoveDAO acuseCoveDAO) {
        this.acuseCoveDAO = acuseCoveDAO;
    }

    @Autowired
    @Qualifier(VUCEM_EDOC_JOB_DAO_NAME)
    public void setEdocDAO(EdocDAO edocDAO) {
        this.edocDAO = edocDAO;
    }

    @Autowired
    @Qualifier(VUCEM_ACUSE_EDOC_JOB_DAO_NAME)
    public void setAcuseEdocDAO(AcuseEdocDAO acuseEdocDAO) {
        this.acuseEdocDAO = acuseEdocDAO;
    }

    @Autowired
    @Qualifier(VUCEM_RABBIT_CONFIG_NAME)
    public void setRabbitConfig(ryndem.mx.glosanet.messagebroker.datastage.vucem.config.RabbitConfig rabbitConfig) {
        this.rabbitConfig = rabbitConfig;
    }

    @Override
    public boolean sendMessageToAcuseCoveQueue(List<Page<FacturaCoveBO>> list) throws GlosaNetException {
        for (Page<FacturaCoveBO> pages : list) {
            receiptsList = new LinkedList<>();
            for (FacturaCoveBO factura : pages.getResults()) {
                SolicitudAcuseFacturaPO solicitudAcuseFacturaPO = new SolicitudAcuseFacturaPO(
                        Long.valueOf(factura.getIdFactura()),
                        factura.getNumeroFactura(),
                        Long.valueOf(factura.getIdCliente()), RABBITMQ_JOB_STATE_RECEIVED, "");

                SolicitudAcuseFacturaPO savedRequest = acuseCoveDAO.saveFactura(solicitudAcuseFacturaPO);
                receiptsList.add(new VucemDocumentRequest(factura.getNumeroFactura(), Long.valueOf(savedRequest.getId()), savedRequest.getIdCustomer()));
            }
            messageList = new VucemDocumentsRequestMessage(receiptsList);
            String pretty_json_message = messageList.toPrettyJson();
            log.info("Mensaje enviado <" + pretty_json_message + ">");
            String json_message = messageList.toRawJson();
            RabbitTemplate rabbitTemplate = rabbitConfig.fetchRabbitTemplate(VucemDocuments.AcuseCove);
            rabbitTemplate.convertAndSend(json_message);
        }
        return true;
    }

    @Override
    public boolean sendMessageToEdocQueue(List<Page<CasosPedimentoCustomBO>> list) throws GlosaNetException {
        partitionsList = new LinkedList<>();

        for (Page<CasosPedimentoCustomBO> pages : list) {
            receiptsList = new LinkedList<>();
            for (CasosPedimentoCustomBO edoc : pages.getResults()) {
                SolicitudEdocPO solicitud = new SolicitudEdocPO(
                        Long.valueOf(edoc.getIdCasosPedimento()),
                        edoc.getIdentificadorCaso(),
                        Long.valueOf(edoc.getIdCliente()), RABBITMQ_JOB_STATE_RECEIVED, "");

                SolicitudEdocPO savedRequest = edocDAO.saveEdoc(solicitud);
                receiptsList.add(new VucemDocumentRequest(edoc.getIdentificadorCaso(), Long.valueOf(savedRequest.getId()), savedRequest.getIdCustomer()));
            }
            messageList = new VucemDocumentsRequestMessage(receiptsList);
            String pretty_json_message = messageList.toPrettyJson();
            log.info("Mensaje enviado <" + pretty_json_message + ">");
            String json_message = messageList.toRawJson();
            RabbitTemplate rabbitTemplate = rabbitConfig.fetchRabbitTemplate(VucemDocuments.Edoc);
            rabbitTemplate.convertAndSend(json_message);
        }
        return true;
    }

    public boolean sendMessageToAcuseEdocQueue(List<Page<CasosPedimentoCustomBO>> list) throws GlosaNetException {
        List<List<VucemDocumentRequest>> partitionsList = new LinkedList<>();
        List<VucemDocumentRequest> receiptsList;

        for (Page<CasosPedimentoCustomBO> pages : list) {
            receiptsList = new LinkedList<>();
            for (CasosPedimentoCustomBO acuseEdoc : pages.getResults()) {

                SolicitudAcuseEdocPO solicitud = new SolicitudAcuseEdocPO(
                        Long.valueOf(acuseEdoc.getIdCasosPedimento()),
                        acuseEdoc.getIdentificadorCaso(),
                        Long.valueOf(acuseEdoc.getIdCliente()), RABBITMQ_JOB_STATE_RECEIVED, "");

                SolicitudAcuseEdocPO savedRequest = acuseEdocDAO.saveAcuseEdoc(solicitud);
                receiptsList.add(new VucemDocumentRequest(acuseEdoc.getIdentificadorCaso(), Long.valueOf(savedRequest.getId()), savedRequest.getIdCustomer()));
            }
            messageList = new VucemDocumentsRequestMessage(receiptsList);
            String pretty_json_message = messageList.toPrettyJson();
            log.info("Mensaje enviado <" + pretty_json_message + ">");
            String json_message = messageList.toRawJson();
            RabbitTemplate rabbitTemplate = rabbitConfig.fetchRabbitTemplate(VucemDocuments.AcuseEdoc);
            rabbitTemplate.convertAndSend(json_message);
        }
        return true;
    }

    @Override
    public boolean sendCustomMessage(CustomLoadRequestOptions options) throws GlosaNetException {
        VucemDocumentRequest document;
        List<VucemDocumentRequest> receiptsList = new LinkedList<>();
        Long idCostumer = null;

        if (options.getFacturaBO() == null && options.getIdFactura() != null)
            throw new GlosaNetException("El id de factura no existe");

        switch (options.getDocumentType()) {
            case AcuseCove:
                if (options.getIdSolicitud() != null) {
                    SolicitudAcuseFacturaPO facturaPO = acuseCoveDAO.getRequestById(Long.valueOf(options.getIdSolicitud()));
                    options.setRequested(facturaPO != null);
                    options.setNumeroFactura(facturaPO.getIdDocument());
                    idCostumer = facturaPO.getIdCustomer();
                    acuseCoveDAO.resetStatus(Long.valueOf(options.getIdSolicitud()));
                } else {
                    options.setRequested(false);
                    options.setNumeroFactura(options.getFacturaBO().getNumeroFactura());
                }

                if (options.getIdSolicitud() == null && options.getIdFactura() != null && !options.isRequested()) {
                    SolicitudAcuseFacturaPO savedObj = acuseCoveDAO.saveFactura(new SolicitudAcuseFacturaPO(
                            Long.valueOf(options.getFacturaBO().getIdFactura()),
                            options.getFacturaBO().getNumeroFactura(),
                            Long.valueOf(options.getFacturaBO().getIdCliente()),
                            RABBITMQ_JOB_STATE_RECEIVED, ""));
                    options.setIdSolicitud(Math.toIntExact(savedObj.getId()));
                }
                break;
            case Edoc:
                if (options.getIdSolicitud() != null) {
                    SolicitudEdocPO edocPO = edocDAO.getRequestById(Long.valueOf(options.getIdSolicitud()));
                    options.setRequested(edocPO != null);
                    options.setCasoPedimento(edocPO.getIdDocument());
                    idCostumer = edocPO.getIdCustomer();
                    edocDAO.resetStatus(Long.valueOf(options.getIdSolicitud()));
                } else {
                    options.setRequested(false);
                    options.setCasoPedimento(options.getCasosPedimentoBO().getIdentificadorCaso());
                }

                if (options.getIdSolicitud() == null && options.getCasosPedimentoBO() != null && !options.isRequested()) {
                    SolicitudEdocPO savedObj = edocDAO.saveEdoc(new SolicitudEdocPO(
                            Long.valueOf(options.getCasosPedimentoBO().getIdCasosPedimento()),
                            options.getCasosPedimentoBO().getIdentificadorCaso(),
                            Long.valueOf(options.getCasosPedimentoBO().getIdCliente()),
                            RABBITMQ_JOB_STATE_RECEIVED, ""));
                    options.setIdSolicitud(Math.toIntExact(savedObj.getId()));
                }
                break;
            case AcuseEdoc:
                if (options.getIdSolicitud() != null) {
                    SolicitudAcuseEdocPO acuseEdocPO = acuseEdocDAO.getRequestById(Long.valueOf(options.getIdSolicitud()));
                    options.setRequested(acuseEdocPO != null);
                    options.setCasoPedimento(acuseEdocPO.getIdDocument());
                    idCostumer = acuseEdocPO.getIdCustomer();
                    acuseEdocDAO.resetStatus(Long.valueOf(options.getIdSolicitud()));
                } else {
                    options.setRequested(false);
                    options.setCasoPedimento(options.getCasosPedimentoBO().getIdentificadorCaso());
                }

                if (options.getIdSolicitud() == null && options.getCasosPedimentoBO() != null && !options.isRequested()) {
                    SolicitudAcuseEdocPO savedObj = acuseEdocDAO.saveAcuseEdoc(new SolicitudAcuseEdocPO(
                            Long.valueOf(options.getCasosPedimentoBO().getIdCasosPedimento()),
                            options.getCasosPedimentoBO().getIdentificadorCaso(),
                            Long.valueOf(options.getCasosPedimentoBO().getIdCliente()),
                            RABBITMQ_JOB_STATE_RECEIVED, ""));
                    options.setIdSolicitud(Math.toIntExact(savedObj.getId()));
                }
                break;
        }

        if (options.getIdSolicitud() == null) {
            if (options.getDocumentType().equals(VucemDocuments.AcuseCove))
                document = new VucemDocumentRequest(
                        options.getNumeroFactura(),
                        Long.valueOf(options.getIdSolicitud()),
                        Long.valueOf(options.getFacturaBO().getIdCliente())
                );
            else
                document = new VucemDocumentRequest(
                        options.getCasoPedimento(),
                        Long.valueOf(options.getIdSolicitud()),
                        Long.valueOf(options.getCasosPedimentoBO().getIdCliente())
                );
        } else {
            if (options.getDocumentType().equals(VucemDocuments.AcuseCove))
                document = new VucemDocumentRequest(
                        options.getNumeroFactura(),
                        Long.valueOf(options.getIdSolicitud()),
                        options.getFacturaBO() != null ? Long.valueOf(options.getFacturaBO().getIdCliente()) : idCostumer
                );
            else
                document = new VucemDocumentRequest(
                        options.getCasoPedimento(),
                        Long.valueOf(options.getIdSolicitud()),
                        options.getCasosPedimentoBO() != null ? Long.valueOf(options.getCasosPedimentoBO().getIdCliente()) : idCostumer
                );
        }

        receiptsList.add(document);
        messageList = new VucemDocumentsRequestMessage(receiptsList);
        String pretty_json_message = messageList.toPrettyJson();
        log.info("Mensaje enviado <" + pretty_json_message + ">");
        String json_message = messageList.toRawJson();
        RabbitTemplate rabbitTemplate = rabbitConfig.fetchRabbitTemplate(options.getDocumentType());
        rabbitTemplate.convertAndSend(json_message);

        return true;
    }
}
