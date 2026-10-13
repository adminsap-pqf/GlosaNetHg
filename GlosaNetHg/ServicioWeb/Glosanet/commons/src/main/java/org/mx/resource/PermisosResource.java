package org.mx.resource;

import org.apache.commons.codec.binary.Base64;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.jose4j.lang.JoseException;
import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.MediaContent;
import org.mx.modelo.comun.bo.Parametros;
import org.mx.modelo.comun.bo.StatusMessage;
import org.mx.modelo.comun.bo.UsuarioBO;
import org.mx.modelo.permisos.org.mx.bo.PermisoBO;
import org.mx.modelo.permisos.org.mx.bo.PermisoHistorialEventoBO;
import org.mx.servicio.comun.AccessService;
import org.mx.servicio.comun.LlaveTokenService;
import org.mx.servicio.permisos.HistorialPermisosService;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;
import java.util.logging.Logger;


@Component
@Path("historialPermisos")
@CrossOrigin
public class PermisosResource {
    private final static Logger log = Logger.getLogger(PermisosResource.class.getName());

    @Autowired
    HistorialPermisosService historialPermisosService;

    @Autowired
    LlaveTokenService llaveService;

    @Autowired
    private AccessService accessService;


    @Path("/obtenerPermisosEmpresa")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPermisosEmpresa(@HeaderParam("x-auth-token") String token, Parametros p) throws Exception {
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
                List<PermisoBO> lstResult = historialPermisosService.obtenerPermisosEmpresa(idEmpresa);
                GenericEntity<List<PermisoBO>> entity = new GenericEntity<List<PermisoBO>>(lstResult) {
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


    @Path("/crearPermiso")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response agregaPermiso(@HeaderParam("x-auth-token") String token, PermisoBO bo) throws Exception {
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
                PermisoBO result = historialPermisosService.agregarPermiso(bo);
                return Response.status(200).entity(result.getIdpermiso().toString()).build();

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


    @Path("/obtenerPermiso")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPermiso(@HeaderParam("x-auth-token") String token, Parametros p) throws Exception {
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
                int idPermiso = p.getId();
                PermisoBO bo = historialPermisosService.obtenerPermiso(idPermiso);
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
                historialPermisosService.eliminarNotificaciones(p.getCondicion());
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
                historialPermisosService.eliminarCorreos(p.getCondicion());
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
                List<PermisoHistorialEventoBO> result = historialPermisosService.guardarHistorialEvento(bo.getPermisoHistorial());
                Parametros r = new Parametros();
                r.setPermisoHistorial(result);
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
                List<PermisoHistorialEventoBO> bo = historialPermisosService.obtenerHistorialEvento(p.getId());
                GenericEntity<List<PermisoHistorialEventoBO>> entity = new GenericEntity<List<PermisoHistorialEventoBO>>(bo) {
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
                historialPermisosService.eliminarHistorialEvento(p.getIdEmpresa(), p.getIdRegistro(), p.getIdEvento(), p.getIdHistorial());
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


    @Path("/subirArchivoHistorial/{idEmpresa}/{idPermiso}/{idEvento}/{idHistorial}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response subirArchivoHistorial(@HeaderParam("x-auth-token") String token,
                                          @FormDataParam("file") File file, @PathParam("idEmpresa") String idEmpresa,
                                          @PathParam("idPermiso") String idPermiso, @PathParam("idEvento") String idEvento,
                                          @PathParam("idHistorial") String idHistorial) throws Exception {

        log.info("@subirArchivoHistorial: Inicio");

        //Validamos acceso: si deniedAccessResponse != null => Acceso denegado
        Response deniedAccessResponse = accessService.validateAccessToAPI(token);
        if (null != deniedAccessResponse)
            return deniedAccessResponse;

        String respuesta = null;
        try {

                String path = idEmpresa + "/" + idPermiso + "/" + idEvento;
                respuesta = historialPermisosService.subirArchivoHistorial(file, "Doc_" + idHistorial, path);

        } catch (GlosaNetException e) {
            e.printStackTrace();
            log.severe("@subirArchivoHistorial: exception -> " + e.toString());
        }

        return Response.ok(new MediaContent("text", respuesta)).build();
    }


    @Path("/obtenerrArchivoHistorial/{idEmpresa}/{idPermiso}/{idEvento}/{idHistorial}")
    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response obtenerrArchivoHistorial(@HeaderParam("x-auth-token") String token,
                                             @PathParam("idEmpresa") String idEmpresa,
                                             @PathParam("idPermiso") String idPermiso, @PathParam("idEvento") String idEvento,
                                             @PathParam("idHistorial") String idHistorial) {

        log.info("@obtenerrArchivoHistorial: Inicio");

        //Validamos acceso: si deniedAccessResponse != null => Acceso denegado
        Response deniedAccessResponse = accessService.validateAccessToAPI(token);
        if (null != deniedAccessResponse)
            return deniedAccessResponse;

        Response response = null;
        try {
            String path = idPermiso + "/" + idEmpresa + "/" + idEvento + "/Doc_" + idHistorial + ".pdf";
            ByteArrayOutputStream buffer = historialPermisosService.obtenerrArchivoHistorial(path);
            if (null == buffer)
                return Response.status(Status.NO_CONTENT.getStatusCode())
                        .entity("NO CONTENT").build();

            ResponseBuilder responseBuilder = Response.ok(Base64.encodeBase64(buffer.toByteArray()))
                    .header("Content-Disposition", "attachment; filename=Doc_" + idHistorial + ".pdf");
            response = responseBuilder.build();

        } catch (GlosaNetException e) {
            e.printStackTrace();
            log.severe("@obtenerrArchivoHistorial: exception -> " + e.toString());
        }

        return response;
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
                historialPermisosService.realizarEvento(p.getId(), p.getOrden());
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

    @Path("/guardarElementoMonitoreoPermiso")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response guardarElementoMonitoreoPermiso(@HeaderParam("x-auth-token") String token, Parametros p) throws Exception {
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
                 * p.getId() = idPermiso
                 * p.getTipo() = tipo de campo
                 * p.getCondicion() = valor string
                 * p.getFinicio() = valor date
                 */
                historialPermisosService.guardarElementoMonitoreoPermiso(p.getId(), Integer.parseInt(p.getTipo()), p.getCondicion(), p.getFinicio());
                return Response.status(200).entity("true").build();
            }
        } catch (JoseException e) {
            log.severe("Error al guardar el permiso del elemento de monitoreo. " + e.getMessage());
            e.printStackTrace();
        }

        StatusMessage statusMessage = new StatusMessage();
        statusMessage.setStatus(Status.FORBIDDEN.getStatusCode());
        statusMessage.setMessage(
                "Access Denied for this functionality !!!");
        return Response.status(Status.FORBIDDEN.getStatusCode())
                .entity(statusMessage).build();
    }


    @Path("/actualizarFechaCita")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarFechaCita(@HeaderParam("x-auth-token") String token, Parametros parametro) throws Exception {
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
                boolean result = historialPermisosService.actualizarFechaCita(parametro);
                return Response.status(200).entity("true").build();
            }
        } catch (JoseException e) {
            log.severe("Error al actualizar fecha de la cita. " + e.getMessage());
            e.printStackTrace();
        }

        StatusMessage statusMessage = new StatusMessage();
        statusMessage.setStatus(Status.FORBIDDEN.getStatusCode());
        statusMessage.setMessage(
                "Access Denied for this functionality !!!");
        return Response.status(Status.FORBIDDEN.getStatusCode())
                .entity(statusMessage).build();
    }


    @Path("/actualizarCitaCofeprisEvento")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarCitaCofeprisEvento(@HeaderParam("x-auth-token") String token, Parametros parametro) throws Exception {
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
                boolean result = historialPermisosService.actualizarCitaCofeprisEvento(parametro);
                return Response.status(200).entity("true").build();
            }
        } catch (JoseException e) {
            log.severe("Error al actualizar cita de Cofepris. " + e.getMessage());
            e.printStackTrace();
        }

        StatusMessage statusMessage = new StatusMessage();
        statusMessage.setStatus(Status.FORBIDDEN.getStatusCode());
        statusMessage.setMessage(
                "Access Denied for this functionality !!!");
        return Response.status(Status.FORBIDDEN.getStatusCode())
                .entity(statusMessage).build();
    }


    @Path("/obtenerPermisosActual")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPermisosActual(@HeaderParam("x-auth-token") String token, Parametros p) throws Exception {
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
                int idProducto = p.getId();
                List<PermisoBO> lstResult = historialPermisosService.obtenerPermisosActual(idProducto);
                GenericEntity<List<PermisoBO>> entity = new GenericEntity<List<PermisoBO>>(lstResult) {
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


    @Path("/obtenerImportaciones")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerImportaciones(@HeaderParam("x-auth-token") String token, Parametros p) throws Exception {
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
                List<Parametros> lstResult = historialPermisosService.obtenerImportaciones(p);
                GenericEntity<List<Parametros>> entity = new GenericEntity<List<Parametros>>(lstResult) {
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


}
