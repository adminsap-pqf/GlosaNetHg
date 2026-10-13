package org.mx.resource;

import org.glassfish.jersey.media.multipart.FormDataParam;
import org.mx.servicio.jobs.datastage.loader.phase1.messaging.DatastagePhase1MsgBrokerService;
import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.MediaContent;
import org.mx.modelo.comun.bo.Parametros;
import org.mx.modelo.util.Util;
import org.mx.servicio.comun.AccessService;
import org.mx.servicio.comun.LlaveTokenService;
import org.mx.servicio.descomprimir.DescomprimirService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.util.logging.Logger;

@Component
@Path("descomprimir")
@CrossOrigin
public class DescomprimirResource {

    private final static Logger LOGGER = Logger.getLogger(DescomprimirResource.class.getName());

    @Autowired
    private LlaveTokenService llaveService;
    @Autowired
    private DescomprimirService descomprimirService;
    @Autowired
    private AccessService accessService;

    //Bean de instancia de mensajero para cola de RabbitMQ
    @Autowired
    private DatastagePhase1MsgBrokerService datastagePhase1MsgBrokerService;


    @Path("/subirArchivoDataStage/{nombreFile}/{idUsuario}/{idCliente}/{rfcActual}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response subirArchivoDataStage(@HeaderParam("x-auth-token") String token,
                                          @FormDataParam("file") File file,
                                          @PathParam("nombreFile") String nombreFile,
                                          @PathParam("idUsuario") int idUsuario,
                                          @PathParam("idCliente") int idCliente,
                                          @PathParam("rfcActual") String rfcActual) {

        LOGGER.info("@subirArchivoDataStage: Inicio");

        Response deniedAccessResponse = accessService.validateAccessToAPI(token);
        if (null != deniedAccessResponse)
            return deniedAccessResponse;

        /*Subimos el archivo a carpeta temporal de datastages*/
        String idDescarga = null;
        try {
            idDescarga = descomprimirService.subirDataStage(file, nombreFile, idUsuario, idCliente, rfcActual);

        } catch (GlosaNetException e) {
            e.printStackTrace();
            LOGGER.severe("@subirArchivoDataStage: exception -> " + e.toString());
        }

        /*Construimos el mensaje de procesamiento para RabbitMQ*/
        if (null != idDescarga) {

            String filePath = Util.rutaTempDatastages + idCliente
                    + "/" + rfcActual + "/" + nombreFile;
            try {
                datastagePhase1MsgBrokerService.sendMessage(idUsuario, idCliente, rfcActual, filePath, Integer.valueOf(idDescarga));
            } catch (GlosaNetException e) {
                e.printStackTrace();
                LOGGER.severe("@subirArchivoDataStage: exception -> " + e.toString());
            }

        }

        return Response.ok(new MediaContent("text", idDescarga)).build();
    }

    @Path("/obtenerDataStageRespuesta")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerDataStageRespuesta(@HeaderParam("x-auth-token") String token,
                                              Parametros p) {

        LOGGER.info("@obtenerDataStageRespuesta: Inicio");

        Response deniedAccessResponse = accessService.validateAccessToAPI(token);
        if (null != deniedAccessResponse)
            return deniedAccessResponse;

        String respuesta = null;
        try {
            respuesta = descomprimirService.obtenerDataStageRespuesta(p.getId());

        } catch (GlosaNetException e) {
            e.printStackTrace();
            LOGGER.severe("@subirArchivoDataStage: exception -> " + e.toString());
        }

        return Response.ok(new MediaContent("text", respuesta)).build();
    }

}
