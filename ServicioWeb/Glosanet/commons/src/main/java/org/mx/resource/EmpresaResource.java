package org.mx.resource;

import org.apache.commons.codec.binary.Base64;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.jose4j.lang.JoseException;
import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.MediaContent;
import org.mx.modelo.comun.bo.ClientesBO;
import org.mx.modelo.comun.bo.EmpresaBO;
import org.mx.modelo.comun.bo.StatusMessage;
import org.mx.modelo.comun.bo.UsuarioBO;
import org.mx.servicio.comun.AccessService;
import org.mx.servicio.comun.EmpresaService;
import org.mx.servicio.comun.LlaveTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.util.logging.Logger;

@Component
@Path("empresa")
@CrossOrigin
public class EmpresaResource {

    private final static Logger LOGGER = Logger.getLogger(EmpresaResource.class.getName());

    @Autowired
    LlaveTokenService llaveService;

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private AccessService accessService;

    @Path("/actualizarVigencia/")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarCliente(@HeaderParam("x-auth-token") String token, ClientesBO cliente) throws Exception {
        if (token == null) {
            StatusMessage statusMessage = new StatusMessage();
            statusMessage.setStatus(Status.FORBIDDEN.getStatusCode());
            statusMessage.setMessage(
                    "Access Denied for this functionality !!!");
            return Response.status(Status.FORBIDDEN.getStatusCode())
                    .entity(statusMessage).build();
        }
        int idUsuario = 0;
        try {
            UsuarioBO u = llaveService.validacionToken(token);
            if (u != null) {
                idUsuario = u.getIdUsuario();
            }
            if (idUsuario > 0) {
                for (EmpresaBO e : cliente.getEmpresas()) {
                    empresaService.actualizarVigencia(e);
                }
                return Response.status(200).entity(cliente).build();

            }
        } catch (JoseException e) {
            e.printStackTrace();
        }

        StatusMessage statusMessage = new StatusMessage();
        statusMessage.setStatus(Status.FORBIDDEN.getStatusCode());
        statusMessage.setMessage(
                "Access Denied for this functionality !!!");
        return Response.status(Status.FORBIDDEN.getStatusCode())
                .entity(statusMessage).build();
    }

    @Path("/guardarMatriz/{rfc}/{idCliente}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response subirMatriz(@HeaderParam("x-auth-token") String token,
                                @FormDataParam("file") File file, @PathParam("idCliente") String idCliente,
                                @PathParam("rfc") String rfc) throws Exception {

        LOGGER.info("@subirMatriz: Inicio");

        Response deniedAccessResponse = accessService.validateAccessToAPI(token);
        if (null != deniedAccessResponse)
            return deniedAccessResponse;

        Boolean respuesta = false;
        try {
                byte[] array = Files.readAllBytes(file.toPath());
                respuesta = empresaService.guardarMatrizSeguridad(rfc, idCliente, array);

        } catch (GlosaNetException e) {
            e.printStackTrace();
            LOGGER.severe("@subirMatriz: exception -> " + e.toString());
        }

        return Response.ok(new MediaContent("text", respuesta.toString())).build();
    }

    @Path("/obtenerMatriz/{rfc}/{idCliente}/")
    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response obtenerMatriz(@HeaderParam("x-auth-token") String token,
                                  @PathParam("rfc") String rfc,
                                  @PathParam("idCliente") String idCliente) {

        LOGGER.info("@obtenerMatriz: Inicio");

        //Validamos acceso: si deniedAccessResponse != null => Acceso denegado
        Response deniedAccessResponse = accessService.validateAccessToAPI(token);
        if (null != deniedAccessResponse)
            return deniedAccessResponse;

        Response response = null;
        try {
            ByteArrayOutputStream buffer = empresaService.descargarMatrizSeguridad(rfc, idCliente);
            if (null == buffer)
                return Response.status(Status.NO_CONTENT.getStatusCode())
                        .entity("NO CONTENT").build();

            ResponseBuilder responseBuilder = Response.ok(Base64.encodeBase64(buffer.toByteArray()))
                    .header("Content-Disposition", "attachment; filename=Doc_" + rfc + ".pdf");
            response = responseBuilder.build();

        } catch (GlosaNetException e) {
            e.printStackTrace();
            LOGGER.severe("@obtenerMatriz: exception -> " + e.toString());
        }

        return response;
    }


    @Path("/guardarImagenEmpresa/{idCliente}/{idEmpresa}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response subirImagenEmpresa(@HeaderParam("x-auth-token") String token,
                                       @FormDataParam("file") File file, @PathParam("idCliente") String idCliente,
                                       @PathParam("idEmpresa") String idEmpresa) throws Exception {

        LOGGER.info("@subirImagenEmpresa: Inicio");

        //Validamos acceso: si deniedAccessResponse != null => Acceso denegado
        Response deniedAccessResponse = accessService.validateAccessToAPI(token);
        if (null != deniedAccessResponse)
            return deniedAccessResponse;

        Boolean respuesta = false;
        try {
                byte[] array = Files.readAllBytes(file.toPath());
                respuesta = empresaService.guardarImagenEmpresa(idEmpresa, idCliente, array);
                return Response.status(200).entity(respuesta).build();
        } catch (GlosaNetException e) {
            e.printStackTrace();
            LOGGER.severe("@subirImagenEmpresa: exception -> " + e.toString());
        }

        return Response.ok(new MediaContent("text", respuesta.toString())).build();
    }
}
