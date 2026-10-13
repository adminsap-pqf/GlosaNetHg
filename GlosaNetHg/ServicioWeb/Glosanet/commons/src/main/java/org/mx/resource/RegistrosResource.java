package org.mx.resource;

import org.apache.commons.codec.binary.Base64;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.jose4j.lang.JoseException;
import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.MediaContent;
import org.mx.modelo.comun.bo.Parametros;
import org.mx.modelo.comun.bo.ProductoBO;
import org.mx.modelo.comun.bo.StatusMessage;
import org.mx.modelo.comun.bo.UsuarioBO;
import org.mx.modelo.registros.bo.*;
import org.mx.modelo.util.Util;
import org.mx.persistencia.Page;
import org.mx.servicio.comun.AccessService;
import org.mx.servicio.comun.LlaveTokenService;
import org.mx.servicio.comun.RegistrosService;
import org.mx.servicio.registros.RegistroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.logging.Logger;

@Component
@Path("registros")
@CrossOrigin
public class RegistrosResource {

    private final static Logger LOGGER = Logger.getLogger(RegistrosResource.class.getName());

    @Autowired
    RegistroService registroService;
    @Autowired
    LlaveTokenService llaveService;
    @Autowired
    private AccessService accessService;
    @Autowired
    RegistrosService registrosService;

    @Path("/guardarRegistro")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response guardarRegistro(@HeaderParam("x-auth-token") String token, RegistroBO bo) throws Exception {
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
                RegistroBO result = registroService.guardarRegistro(bo);
                Parametros response = new Parametros();
                response.setIdRegistro(result.getIdRegistro());
                response.setIdproducto(result.getProducto().getIdProducto());
                return Response.status(200).entity(response).build();
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

    @Path("/obtenerRegistrosEmpresa")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerRegistrosEmpresa(@HeaderParam("x-auth-token") String token, Parametros p) throws Exception {
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
                int idEmpresa = p.getId();
                List<RegistroBO> lstResult = registroService.obtenerRegistrosEmpresa(idEmpresa);
                GenericEntity<List<RegistroBO>> entity = new GenericEntity<List<RegistroBO>>(lstResult) {
                };
                return Response.ok(entity).build();
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

    @Path("/obtenerTiempoRealizacion")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerTiempoRealizacion(@HeaderParam("x-auth-token") String token, Parametros p) throws Exception {
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
                int idEmpresa = p.getId();
                TiempoRealizacionBO bo = registroService.obtenerTiempoRealizacion(idEmpresa);
                return Response.status(200).entity(bo).build();
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

    @Path("/obtenerRegistroPorId")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerRegistroPorId(@HeaderParam("x-auth-token") String token, Parametros p) throws Exception {
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
                int idRegistro = p.getId();
                RegistroBO bo = registroService.obtenerRegistroPorId(idRegistro);
                return Response.status(200).entity(bo).build();
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

    @Path("/obtenerHistorialEvento")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerHistorialEvento(@HeaderParam("x-auth-token") String token, Parametros p) throws Exception {
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
                List<HistorialEventoBO> bo = registroService.obtenerHistorialEvento(p.getId());
                GenericEntity<List<HistorialEventoBO>> entity = new GenericEntity<List<HistorialEventoBO>>(bo) {
                };
                return Response.ok(entity).build();
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


    @Path("/guardarHistorialEvento")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response guardarHistorialEvento(@HeaderParam("x-auth-token") String token, Parametros bo) throws Exception {
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
                List<HistorialEventoBO> result = registroService.guardarHistorialEvento(bo.getHistorial());
                Parametros r = new Parametros();
                r.setHistorial(result);
                return Response.status(200).entity(r).build();
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


    @Path("/subirArchivoHistorial/{idEmpresa}/{idRegistro}/{idEvento}/{idHistorial}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response subirArchivoHistorial(@HeaderParam("x-auth-token") String token,
                                          @FormDataParam("file") File file, @PathParam("idEmpresa") String idEmpresa,
                                          @PathParam("idRegistro") String idRegistro, @PathParam("idEvento") String idEvento,
                                          @PathParam("idHistorial") String idHistorial) throws Exception {

        LOGGER.info("@subirArchivoHistorial: Inicio");

        Response deniedAccessResponse = accessService.validateAccessToAPI(token);
        if (null != deniedAccessResponse)
            return deniedAccessResponse;

        String respuesta = null;
        try {

            String path = idEmpresa + "/" + idRegistro + "/" + idEvento;
            respuesta = registroService.subirArchivoHistorial(file, "Doc_" + idHistorial, path);

        } catch (GlosaNetException e) {
            e.printStackTrace();
            LOGGER.severe("@subirArchivoHistorial: exception -> " + e.toString());
        }

        return Response.ok(new MediaContent("text", respuesta)).build();
    }

    @Path("/obtenerrArchivoHistorial/{idEmpresa}/{idRegistro}/{idEvento}/{idHistorial}")
    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response obtenerrArchivoHistorial(@HeaderParam("x-auth-token") String token,
                                             @PathParam("idEmpresa") String idEmpresa,
                                             @PathParam("idRegistro") String idRegistro, @PathParam("idEvento") String idEvento,
                                             @PathParam("idHistorial") String idHistorial) {
        LOGGER.info("@obtenerrArchivoHistorial: Inicio");

        //Validamos acceso: si deniedAccessResponse != null => Acceso denegado
        Response deniedAccessResponse = accessService.validateAccessToAPI(token);
        if (null != deniedAccessResponse)
            return deniedAccessResponse;

        Response response = null;
        try {
            String path = idRegistro + "/" + idEmpresa + "/" + idEvento + "/Doc_" + idHistorial + ".pdf";
            ByteArrayOutputStream buffer = registroService.obtenerrArchivoHistorial(path);
            if (null == buffer)
                return Response.status(Status.NO_CONTENT.getStatusCode())
                        .entity("NO CONTENT").build();

            ResponseBuilder responseBuilder = Response.ok(Base64.encodeBase64(buffer.toByteArray()))
                    .header("Content-Disposition", "attachment; filename=Doc_" + idHistorial + ".pdf");
            response = responseBuilder.build();

        } catch (GlosaNetException e) {
            e.printStackTrace();
            LOGGER.severe("@obtenerrArchivoHistorial: exception -> " + e.toString());
        }

        return response;
    }


    @Path("/guardarElementoMonitoreoRegistro")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response guardarElementoMonitoreoRegistro(@HeaderParam("x-auth-token") String token, Parametros p) throws Exception {
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
                /**
                 * p.getId() = idRegistro
                 * p.getTipo() = tipo de campo
                 * p.getCondicion() = valor string
                 * p.getFinicio() = valor date
                 */
                registroService.guardarElementoMonitoreoRegistro(p.getId(), Integer.parseInt(p.getTipo()), p.getCondicion(), p.getFinicio());
                return Response.status(200).entity("true").build();
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

    @Path("/actualizaNotificaciones")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizaNotificaciones(@HeaderParam("x-auth-token") String token, Parametros p) throws Exception {

        LOGGER.info("@actualizaNotificaciones: Inicio");

        StatusMessage statusMessage = new StatusMessage();

        if (null == token) {
            statusMessage.setMessage(
                    "Recurso no autorizado");
            return Response.status(Status.UNAUTHORIZED.getStatusCode())
                    .entity(statusMessage).build();
        }

        try {
            UsuarioBO usuario = llaveService.validacionToken(token);
            if (null == usuario || usuario.getIdUsuario() <= 0) {
                statusMessage.setMessage(
                        "Prohibido");
                return Response.status(Status.FORBIDDEN.getStatusCode())
                        .entity(statusMessage).build();
            }

            registroService.actualizaNotificaciones(p.getNotificaciones());

        } catch (JoseException e) {
            e.printStackTrace();
            statusMessage.setMessage(
                    "Error de servidor");
            return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
                    .entity(statusMessage).build();
        }

        return Response.status(Status.OK.getStatusCode()).entity("true").build();
    }

    @Path("/realizarEvento")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response realizarEvento(@HeaderParam("x-auth-token") String token, Parametros p) throws Exception {
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
                /**
                 * p.getId() = idEvento
                 * p.getOrden() = cerrar (0,abierto) (1,cerrado)
                 */
                registroService.realizarEvento(p.getId(), p.getOrden());
                return Response.status(200).entity("true").build();
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

    @Path("/obtenerStatusTramiteCofepris")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerStatusTramiteCofepris(@HeaderParam("x-auth-token") String token,
                                                 Parametros p) throws Exception {

        LOGGER.info("@obtenerStatusTramiteCofepris: Inicio");

        Response deniedAccessResponse = accessService.validateAccessToAPI(token);
        if (null != deniedAccessResponse)
            return deniedAccessResponse;

        String respuesta = null;
        try {

            respuesta = registroService.obtenerStatusTramiteCofepris(p.getCondicion());
        } catch (GlosaNetException e) {
            e.printStackTrace();
            LOGGER.severe("@obtenerStatusTramiteCofepris: exception -> " + e.toString());
        }

        return Response.ok(new MediaContent("text", respuesta)).build();
    }


    @Path("/eliminarHistorialEvento")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarHistorialEvento(@HeaderParam("x-auth-token") String token, Parametros p) throws Exception {
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
                /**
                 *
                 * idEmpresa, idRegistro, idEvento, idHistorial
                 */
                registroService.eliminarHistorialEvento(p.getIdEmpresa(), p.getIdRegistro(), p.getIdEvento(), p.getIdHistorial());
                return Response.status(200).entity("true").build();
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


    @Path("/eliminarNotificaciones")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarNotificaciones(@HeaderParam("x-auth-token") String token, Parametros p) throws Exception {
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
                registroService.eliminarNotificaciones(p.getCondicion());
                return Response.status(200).entity("true").build();
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


    @Path("/eliminarCorreos")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarCorreos(@HeaderParam("x-auth-token") String token, Parametros p) throws Exception {
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
                registroService.eliminarCorreos(p.getCondicion());
                return Response.status(200).entity("true").build();
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

    @Path("/obtenerNotificacionesPorEnviar")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerNotificacionesPorEnviar(@HeaderParam("x-auth-token") String token) throws Exception {

        LOGGER.info("@obtenerNotificacionesPorEnviar: Inicio");

        StatusMessage statusMessage = new StatusMessage();

        if (null == token) {
            statusMessage.setMessage(
                    "Recurso no autorizado");
            return Response.status(Status.UNAUTHORIZED.getStatusCode())
                    .entity(statusMessage).build();
        }

        List<NotificacionBO> lstResult;

        try {
            UsuarioBO usuario = llaveService.validacionToken(token);
            if (null == usuario || usuario.getIdUsuario() <= 0) {
                statusMessage.setMessage(
                        "Prohibido");
                return Response.status(Status.FORBIDDEN.getStatusCode())
                        .entity(statusMessage).build();
            }

            lstResult = registroService.obtenNotificacionesPorEnviar(
                    LocalDateTime.now(ZoneId.systemDefault()));

        } catch (JoseException e) {
            e.printStackTrace();
            statusMessage.setMessage(
                    "Error de servidor");
            return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
                    .entity(statusMessage).build();
        }

        GenericEntity<List<NotificacionBO>> entity = new GenericEntity<List<NotificacionBO>>(lstResult) {
        };
        return Response.ok(entity).build();
    }


    @Path("/obtenerFeriadosOficiales")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerFeriadosOficiales(@HeaderParam("x-auth-token") String token) throws Exception {
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
                List<FeriadosOficialesBO> lstResult = registroService.obtenerFeriadosOficiales();
                GenericEntity<List<FeriadosOficialesBO>> entity = new GenericEntity<List<FeriadosOficialesBO>>(lstResult) {
                };
                return Response.ok(entity).build();
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


    @Path("/actualizarRegistro")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarRegistro(@HeaderParam("x-auth-token") String token, RegistroBO bo) throws Exception {
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
                boolean result = registroService.actualizarRegistro(bo);

                return Response.status(200).entity(result + "").build();
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


    @Path("/cargaRegistros")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response cargaRegistros(@HeaderParam("x-auth-token") String token, String archivo) throws Exception {
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
                boolean result = registroService.cargaRegistros(archivo);

                return Response.status(200).entity(result + "").build();
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

    @Path("/actualizarFechas")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarFechas(@HeaderParam("x-auth-token") String token) throws Exception {
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
                //int id = Integer.parseInt(idEmpresa);
                boolean result = registroService.actualizarFechas(10);

                return Response.status(200).entity(result + "").build();
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


    @Path("/guardarHistorialEvento")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response cuantosDiasHabiles(@HeaderParam("x-auth-token") String token, @PathParam("finicio") String finicio,
                                       @PathParam("ffin") String ffin) throws Exception {

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
                //int id = Integer.parseInt(idEmpresa);
                String result = registroService.cuantosDiasHabiles(finicio, ffin);

                return Response.status(200).entity(result + "").build();
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

    @Path("/guardarHistorialProducto")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response guardarHistorialProducto(@HeaderParam("x-auth-token") String token, Parametros parameter) {

        LOGGER.info("@guardarHistorialProducto: Inicio");

        Response deniedAccessResponse = accessService.validateAccessToAPI(token);
        if (null != deniedAccessResponse)
            return deniedAccessResponse;

        try {
            List<HistorialProductoBO> result = registroService.saveHistorialProducto(parameter.getHistorialProducto());
            Parametros response = new Parametros();
            response.setHistorialProducto(result);
            return Response.status(200).entity(response).build();
        } catch (GlosaNetException e) {
            e.printStackTrace();
            LOGGER.severe("@guardarHistorialProducto: exception -> " + e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
                    .entity(e).build();
        }
    }

    @Path("/subirArchivoHistorialProducto/{idEmpresa}/{idProducto}/{idRegistro}/{idHistorialProducto}/{fileName}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response subirArchivoHistorialProducto(@HeaderParam("x-auth-token") String token,
                                                  @FormDataParam("file") File file,
                                                  @PathParam("idEmpresa") String idEmpresa,
                                                  @PathParam("idProducto") String idProducto,
                                                  @PathParam("idRegistro") String idRegistro,
                                                  @PathParam("idHistorialProducto") Integer idHistorialProducto,
                                                  @PathParam("fileName") String fileName ) throws Exception {

        LOGGER.info("@subirArchivoHistorialProducto: Inicio");

        Response deniedAccessResponse = accessService.validateAccessToAPI(token);
        if (null != deniedAccessResponse)
            return deniedAccessResponse;

        String response = null;
        try {
            String path = idEmpresa + "/" + idProducto + "/" + idRegistro;
            response = registroService.subirArchivoHistorialProducto(file, fileName, path, idHistorialProducto);
        } catch (GlosaNetException e) {
            e.printStackTrace();
            LOGGER.severe("@subirArchivoHistorialProducto: exception -> " + e.toString());
        }

        return Response.ok(new MediaContent("text", response)).build();
    }

    @Path("/obtenerArchivosPorRegistro")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerArchivosPorRegistro(@HeaderParam("x-auth-token") String token, Parametros parameter) {

        LOGGER.info("@obtenerArchivosPorRegistro: Inicio");

        Response deniedAccessResponse = accessService.validateAccessToAPI(token);
        if (null != deniedAccessResponse)
            return deniedAccessResponse;

        try {
            int idRegistro = parameter.getId();
            List<HistorialProductoBO> result = registroService.getArchivosPorRegistro(idRegistro);
            Parametros response = new Parametros();
            response.setHistorialProducto(result);
            return Response.status(200).entity(response).build();
        } catch (GlosaNetException e) {
            e.printStackTrace();
            LOGGER.severe("@obtenerArchivosPorRegistro: exception -> " + e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
                    .entity(e).build();
        }
    }

    @Path("/obtenerHistorialProductoPDF/{idEmpresa}/{idProducto}/{idRegistro}/{fileName}")
    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response obtenerHistorialProductoPDF(@HeaderParam("x-auth-token") String token,
                                             @PathParam("idEmpresa") String idEmpresa,
                                             @PathParam("idProducto") String idProducto,
                                             @PathParam("idRegistro") String idRegistro,
                                             @PathParam("fileName") String fileName) {
        LOGGER.info("@obtenerHistorialProductoPDF: Inicio");

        //Validamos acceso: si deniedAccessResponse != null => Acceso denegado
        Response deniedAccessResponse = accessService.validateAccessToAPI(token);
        if (null != deniedAccessResponse)
            return deniedAccessResponse;

        Response response = null;
        try {
            String path = idEmpresa + "/" + idProducto + "/" + idRegistro + "/" + fileName;

            ByteArrayOutputStream buffer = registroService.obtenerHistorialProductoPDF(path);
            if (null == buffer)
                return Response.status(Status.NO_CONTENT.getStatusCode())
                        .entity("NO CONTENT").build();

            ResponseBuilder responseBuilder = Response.ok(Base64.encodeBase64(buffer.toByteArray()))
                    .header("Content-Disposition", "attachment; filename=" + fileName);
            response = responseBuilder.build();

        } catch (GlosaNetException e) {
            e.printStackTrace();
            LOGGER.severe("@obtenerHistorialProductoPDF: exception -> " + e.toString());
        }

        return response;
    }

    @Path("/obtenerRegistrosPorProducto")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerRegistrosPorProducto(@HeaderParam("x-auth-token") String token, Parametros parameter) throws Exception {

        LOGGER.info("@obtenerRegistrosPorProducto: Inicio");

        Response deniedAccessResponse = accessService.validateAccessToAPI(token);
        if (null != deniedAccessResponse)
            return deniedAccessResponse;

        try {
            int idEmpresa = parameter.getIdEmpresa();
            int idProducto = parameter.getIdproducto();
            List<RegistroBO> result = registroService.obtenerRegistrosPorProducto(idEmpresa, idProducto);
            GenericEntity<List<RegistroBO>> entity = new GenericEntity<List<RegistroBO>>(result) {};
            return Response.ok(entity).build();
        } catch (GlosaNetException e) {
            e.printStackTrace();
            LOGGER.severe("@obtenerRegistrosPorProducto: exception -> " + e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
                    .entity(e).build();
        }
    }

    @Path("/eliminarHistorialProducto")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarHistorialProducto(@HeaderParam("x-auth-token") String token, Parametros parameter) throws Exception {

        LOGGER.info("@eliminarHistorialProducto: Inicio");

        Response deniedAccessResponse = accessService.validateAccessToAPI(token);
        if (null != deniedAccessResponse)
            return deniedAccessResponse;

        try {
            registroService.eliminarHistorialProducto(parameter.getIdHistorial(), parameter.getPath());
            return Response.status(200).entity("true").build();
        } catch (GlosaNetException e) {
            e.printStackTrace();
            LOGGER.severe("@eliminarHistorialProducto: exception -> " + e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
                    .entity(e).build();
        }
    }
    @Path("/registrarLibreriaRegistros")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarLibreriaRegistros(){
        System.out.println("Entra el metodo!!");
        try{
            //registrosService.findProducts();
            registrosService.readFile();
            return Response.status(200).entity("true").build();
        }catch (Exception e){

            return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
                    .entity(e).build();
        }

    }

}
