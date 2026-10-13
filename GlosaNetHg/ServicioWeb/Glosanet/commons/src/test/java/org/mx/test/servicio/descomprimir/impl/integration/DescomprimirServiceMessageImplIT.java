package org.mx.test.servicio.descomprimir.impl.integration;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.ClientesBO;
import org.mx.modelo.comun.bo.DescargaBO;
import org.mx.modelo.comun.bo.UsuarioBO;
import org.mx.modelo.util.Util;
import org.mx.persistencia.comun.DescargaDAO;
import org.mx.servicio.comun.UsuarioService;
import org.mx.servicio.descomprimir.DescomprimirService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ryndem.mx.glosanet.messagebroker.Utils;
import ryndem.mx.glosanet.messagebroker.datastage.loader.phase1.config.RabbitConfig;
import ryndem.mx.glosanet.messagebroker.datastage.loader.phase1.document.JobMessage;
import ryndem.mx.glosanet.messagebroker.datastage.loader.phase1.model.bo.JobBO;
import ryndem.mx.glosanet.messagebroker.datastage.loader.phase1.persistence.JobDAO;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static ryndem.mx.glosanet.messagebroker.config.Protocols.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:spring/applicationContext.xml"})
@WebAppConfiguration
public class DescomprimirServiceMessageImplIT {

    private UsuarioService usuarioService;
    @Autowired
    @Qualifier("usuarioService")
    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    private DescomprimirService descomprimirService;
    @Autowired
    @Qualifier("descomprimirService")
    public void setDescomprimirService(DescomprimirService descomprimirService) {
        this.descomprimirService = descomprimirService;
    }

    private DescargaDAO descargaDAO;
    @Autowired
    @Qualifier("descargaDAO")
    public void setDescargaDAO(DescargaDAO descargaDAO) {
        this.descargaDAO = descargaDAO;
    }

    private UsuarioBO usuario = null;

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

    @Before
    public void setup() throws Exception {

        //usuario = usuarioService.validarUsuario("comarket", "613c9504bcef2ba34ea97febf9624dedbd068726");
        usuario = usuarioService.validarUsuario("prueba", getPassword("cambiame"));
    }

    //region Helpers
    private String getPassword(String input) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.reset();
        md.update(input.getBytes("utf8"));
        return String.format("%040x", new BigInteger(1, md.digest()));
    }
    //endregion

    @Test
    public void testLogin() {

        Assert.assertNotNull(usuario);
    }

    @Test
    public void createJob() {
        ClientesBO clientesBO = usuario.getCliente();
        clientesBO.setRfc("DCP790511D36");
        clientesBO.setRfcActual("DCP790511D36");

        //Insertando en Base de datos
        Integer idCliente = clientesBO.getIdCliente();
        Integer idUsuario = usuario.getIdUsuario();
        String rfcActual = clientesBO.getRfcActual();
        String rutaDataStage = Util.rutaTestDatastages + idCliente
                + "/" + rfcActual + "/";
        String nombreFile = "700118_solicitudes.zip";

        DescargaBO peticion = new DescargaBO(rutaDataStage, nombreFile,
                idUsuario, "DataStage", rfcActual);

        Integer idDescarga = null;
        try {
            idDescarga = descargaDAO.insertPeticionDescarga(peticion);
        } catch (GlosaNetException e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(idDescarga);

        JobBO businessObject = new JobBO();
        String guid = Utils.generateGUID();
        businessObject.setGuid_job(guid);
        businessObject.setStatus(RABBITMQ_JOB_STATE_RECEIVED);
        businessObject.setIdDownload(idDescarga);
        businessObject.setStackTrace("");

        JobBO processedBusinessObject = null;
        try {
            System.out.println("Storing in DB...");
            processedBusinessObject = jobDAO.save(businessObject);
        } catch (Exception e) {
            System.out.println("Error storing in DB...");
            e.printStackTrace();
        }
        Assert.assertNotNull(processedBusinessObject);

        //Envío de mensaje
        System.out.println("Sending message...");
        String filePath = rutaDataStage + nombreFile;

        JobMessage jobMessage = new JobMessage(
                idUsuario, idCliente, processedBusinessObject.getGuid_job(), idDescarga, rfcActual, filePath);

        Assert.assertNotNull(jobMessage);

        String pretty_json_message = jobMessage.toPrettyJson();
        Assert.assertNotNull(pretty_json_message);
        System.out.println("Message sent <" + pretty_json_message + ">");

        String json_message = jobMessage.toRawJson();
        Assert.assertNotNull(pretty_json_message);
        RabbitTemplate rabbitTemplate = rabbitConfig.fetchRabbitTemplate();
        Assert.assertNotNull(rabbitTemplate);
        rabbitTemplate.convertAndSend(json_message);
    }

    @Test
    public void uploadFile() {

        ClientesBO clientesBO = usuario.getCliente();
        clientesBO.setRfc("DCP790511D36");
        clientesBO.setRfcActual("DCP790511D36");

        descomprimirService.setLogginCliente(clientesBO);
        descomprimirService.setUsuarioLoggin(usuario);
        descomprimirService.setRutaDataStageArchivos("/Users/sergio.zavala/Desktop/DataStages/");
        int salida = 0;
        try {
            salida = descomprimirService.uploadFile(
                    new File("/Users/sergio.zavala/Desktop/DataStages/COMARKET_355988_solicitudes_Enero17.zip"));
        } catch (GlosaNetException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(salida, 1);
    }
}
