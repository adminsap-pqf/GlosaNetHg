package org.mx.test.servicio.datastage.impl.integration;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mx.controllers.datastage.CustomLoadRequestOptions;
import org.mx.modelo.comun.bo.UsuarioBO;
import org.mx.modelo.datastage.bo.FacturaCoveBO;
import org.mx.modelo.datastage.bo.VucemTokenBO;
import org.mx.persistencia.Page;
import org.mx.servicio.comun.UsuarioService;
import org.mx.servicio.datastage.DatastageService;
import org.mx.servicio.datastage.VucemTokenService;
import org.mx.servicio.jobs.datastage.vucem.messaging.DocRequestMsgBrokerService;
import org.mx.servicio.jobs.datastage.vucem.partitioning.DocRequestPartitioningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ryndem.mx.glosanet.messagebroker.VucemDocuments;
import ryndem.mx.glosanet.messagebroker.datastage.vucem.config.RabbitConfig;
import ryndem.mx.glosanet.messagebroker.datastage.vucem.model.po.SolicitudAcuseFacturaPO;
import ryndem.mx.glosanet.messagebroker.datastage.vucem.persistence.AcuseCoveDAO;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static ryndem.mx.glosanet.messagebroker.config.Protocols.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:spring/applicationContext.xml"})
@WebAppConfiguration
public class AcuseCoveRequestMsgBrokerMessageImplIT {

    @Autowired
    private DocRequestPartitioningService docRequestPartitioningService;

    @Autowired
    private DocRequestMsgBrokerService docRequestMsgBrokerService;

    private DatastageService datastageService;
    @Autowired
    @Qualifier("datastageService")
    public void setDatastageService(DatastageService datastageService) {
        this.datastageService = datastageService;
    }

    private UsuarioService usuarioService;
    @Autowired
    @Qualifier("usuarioService")
    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    private UsuarioBO usuario = null;
    private VucemTokenService vucemTokenService;
    @Autowired
    @Qualifier("vucemTokenService")
    public void setVucemTokenService(VucemTokenService vucemTokenService) {
        this.vucemTokenService = vucemTokenService;
    }

    private VucemTokenBO vucemTokenBO = null;
    private AcuseCoveDAO acuseCoveDAO;
    @Autowired
    @Qualifier(VUCEM_ACUSE_COVE_JOB_DAO_NAME)
    public void setAcuseCoveDAO(AcuseCoveDAO acuseCoveDAO) {
        this.acuseCoveDAO = acuseCoveDAO;
    }

    private RabbitConfig rabbitConfig;
    @Autowired
    @Qualifier(VUCEM_RABBIT_CONFIG_NAME)
    public void setRabbitConfig(ryndem.mx.glosanet.messagebroker.datastage.vucem.config.RabbitConfig rabbitConfig) {
        this.rabbitConfig = rabbitConfig;
    }

    @Before
    public void setup() throws Exception {
        /* USUARIO */
        //usuario = usuarioService.validarUsuario("comarket", "613c9504bcef2ba34ea97febf9624dedbd068726");
        usuario = usuarioService.validarUsuario("prueba", getPassword("cambiame"));
        /* 1) Traer un datastage; 2) acceder a Facturas 505 , 3) Traer un registro de factura para la prueba*/
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
    public void saveToken() throws Exception {
        /* Guardamos un TOKEN VUCEM */
        VucemTokenBO token = new VucemTokenBO();
        token.setIdCliente(Integer.toUnsignedLong(usuario.getCliente().getIdCliente()));
        token.setToken("vsljgASDFASDFdsafS/DSDC5dfasAldasfdd+ryFxCJgb3Wtwefd8hzoANsWSMOn");
        vucemTokenBO = vucemTokenService.guardaVucemToken(token);
        //assert
    }

    @Test
    public void saveSolicitudAcuseFactura() throws Exception {
        /* Guardamos una solicitud de documento ACUSE COVE */

        SolicitudAcuseFacturaPO solicitudAcuseFacturaPO = new SolicitudAcuseFacturaPO();
        solicitudAcuseFacturaPO.setIdDocument("");
        solicitudAcuseFacturaPO.setIdCustomer(Long.valueOf(usuario.getCliente().getIdCliente()));
        solicitudAcuseFacturaPO.setIdReceipt(0l); //TODO obtener el id de la tabla Facturas_505
        solicitudAcuseFacturaPO.setStatus(RABBITMQ_JOB_STATE_RECEIVED);
        SolicitudAcuseFacturaPO savedRequest = acuseCoveDAO.saveFactura(solicitudAcuseFacturaPO);

        //Comprobación de guardado
        Assert.assertNotNull(savedRequest);
        Assert.assertFalse(savedRequest.getId() == 0L);
    }

    //Inserción de mensaje de solicitud a AMPQ
    @Test
    public void sendMessageToAMQP() throws Exception {
        int idDataStageTest = 425;
        // 1. Obtengo total de facturas coves de datastage
        Integer totalFacturasCOVEs = datastageService.getTotalFacturasDocsDeDatastage(idDataStageTest);
        Assert.assertNotNull(totalFacturasCOVEs);

        // 2. Obtengo lista de particiones de FacturasCOVE
        List<Page<FacturaCoveBO>> listaParticiones = docRequestPartitioningService.creaParticionesFacturasCOVEsDePedimento(
                idDataStageTest, 10, totalFacturasCOVEs);

        //Comprobaciones de la lista de particiones
        Assert.assertNotNull(listaParticiones);
        Assert.assertTrue(listaParticiones.size() > 0);

        docRequestMsgBrokerService.sendMessageToAcuseCoveQueue(listaParticiones);
    }

    @Test
    public void sendBulkMessageToAMQP() throws Exception {
        //Ultimos cinco de proquifa y dicipa
        int[] arrDataStageTest = {420, 421, 422, 423, 424, 425, 381, 380, 379, 378, 359};
        // 1. Obtengo total de facturas coves de datastage
        for (int idDatastageTest: arrDataStageTest) {
            Integer totalFacturasCOVEs = datastageService.getTotalFacturasDocsDeDatastage(idDatastageTest);
            Assert.assertNotNull(totalFacturasCOVEs);

            // 2. Obtengo lista de particiones de FacturasCOVE
            List<Page<FacturaCoveBO>> listaParticiones = docRequestPartitioningService.creaParticionesFacturasCOVEsDePedimento(
                    idDatastageTest, 10, totalFacturasCOVEs);

            //Comprobaciones de la lista de particiones
            Assert.assertNotNull(listaParticiones);
            Assert.assertTrue(listaParticiones.size() > 0);
            docRequestMsgBrokerService.sendMessageToAcuseCoveQueue(listaParticiones);
        }
    }

    @Test
    public void createPartitions() throws Exception {
        // 1. Obtengo total de facturas coves de datastage; caso de prueba con datastage pk_datastage = 424
        Integer totalFacturasCOVEs = datastageService.getTotalFacturasDocsDeDatastage(424);
        Assert.assertNotNull(totalFacturasCOVEs);
        // 2. Obtengo lista de particiones de FacturasCOVE
        List<Page<FacturaCoveBO>> paginasFacturaCOVEs =
                docRequestPartitioningService.creaParticionesFacturasCOVEsDePedimento(
                        424, 10, totalFacturasCOVEs);

        Assert.assertNotNull(paginasFacturaCOVEs);
        Assert.assertTrue(paginasFacturaCOVEs.size() > 0);
    }

    @Test
    public void loadManually() throws Exception {
        CustomLoadRequestOptions solicitud = new CustomLoadRequestOptions();
        solicitud.setIdFactura(269667);
        solicitud.setDocumentType(VucemDocuments.AcuseCove);

        datastageService.sendCustomMessage(solicitud);
    }
}
