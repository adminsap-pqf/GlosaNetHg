package org.mx.resource;


import org.apache.commons.codec.binary.Base64;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.jose4j.lang.JoseException;
import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.MediaContent;
import org.mx.modelo.comun.bo.*;
import org.mx.servicio.comun.AccessService;
import org.mx.servicio.comun.LibreriaDataStageService;
import org.mx.servicio.comun.LlaveTokenService;
import org.mx.servicio.comun.PrevisionesService;
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
@Path("previsiones")
@CrossOrigin
public class PrevisionesResource {
    private final static Logger log = Logger.getLogger(PrevisionesResource.class.getName());

    @Autowired
    LlaveTokenService llaveService;
    @Autowired
    PrevisionesService PrevisionesService;
    @Autowired
    LibreriaDataStageService libreriaDataStageService;
    @Autowired
    private AccessService accessService;

    @Path("/actualizarPrevisiones")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarPrevision(@HeaderParam("x-auth-token") String token, PrevisionesBO bo) throws Exception {
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
                boolean result = PrevisionesService.actualizarPrevisiones(bo);
                System.out.println("Resultado: " + result);
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

    @Path("/borrarPrevisiones")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response borrarPrevision(@HeaderParam("x-auth-token") String token, PrevisionesBO bo) throws Exception {
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
                boolean result = PrevisionesService.borrarPrevision(bo);
                System.out.println("Resultado: " + result);
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

    @Path("/modificarPrevision")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response modificarPrevision(@HeaderParam("x-auth-token") String token, PrevisionesBO bo) throws Exception {
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
                boolean result = PrevisionesService.modificarPrevision(bo);
                System.out.println("Resultado: " + result);
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


    @Path("/crearPrevisiones")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response agregaPrevisiones(@HeaderParam("x-auth-token") String token, PrevisionesBO bo) throws Exception {
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
                System.out.println("=====================");
                System.out.println(bo);
                System.out.println("=====================");
                int lstResult = PrevisionesService.agregarPrevisiones(bo);
                return Response.status(200).entity(lstResult + "").build();
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

    @Path("/obtenerPrevisiones")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPrevisiones(@HeaderParam("x-auth-token") String token, PrevisionesBO bo) throws GlosaNetException {
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
                System.out.println("Antes del Servicio");
                List<PrevisionesBO> lstTratado = PrevisionesService.obtenerPrevisiones(bo);
                System.out.println("Despues del Servicio" + lstTratado.size());
                GenericEntity<List<PrevisionesBO>> entity = new GenericEntity<List<PrevisionesBO>>(lstTratado) {
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

    @Path("/obtenerProductos")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerProductos(@HeaderParam("x-auth-token") String token, PrevisionesBO bo) throws GlosaNetException {
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
                System.out.println("Antes del Servicio");
                List<PrevisionesBO> lstTratado = PrevisionesService.obtenerProductos(bo);
                System.out.println("Despues del Servicio" + lstTratado.size());
                GenericEntity<List<PrevisionesBO>> entity = new GenericEntity<List<PrevisionesBO>>(lstTratado) {
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

    @Path("/crearPrevision")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response agregaPrevision(@HeaderParam("x-auth-token") String token, PrevisionesBO bo) throws Exception {
        System.out.println("***********");
        System.out.println(token);
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
                System.out.println("=====================");
                System.out.println(bo.getVigenciaString());
                System.out.println("=====================");
                PrevisionesBO result = PrevisionesService.agregarPrevision(bo);
                return Response.status(200).entity(result.getIdPrevision().toString()).build();
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

    @Path("/guardarPrevision")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response guardarPrevision(@HeaderParam("x-auth-token") String token, List<PrevisionesBO> bo) {
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
                System.out.println("Antes del Servicio");
                List<PrevisionesBO> lstTratado = PrevisionesService.guardarPrevision(bo);
                System.out.println("Despues del Servicio" + lstTratado.size());
                GenericEntity<List<PrevisionesBO>> entity = new GenericEntity<List<PrevisionesBO>>(lstTratado) {
                };
                return Response.ok(entity).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        StatusMessage statusMessage = new StatusMessage();
        statusMessage.setStatus(Status.FORBIDDEN.getStatusCode());
        statusMessage.setMessage(
                "Access Denied for this functionality !!!");
        return Response.status(Status.FORBIDDEN.getStatusCode())
                .entity(statusMessage).build();
    }

    @Path("/obtenerProductosGenerales")
    @POST
    @Produces(MediaType.APPLICATION_JSON)

    public Response obtenerHistorialImportaciones(@HeaderParam("x-auth-token") String token, Parametros p) throws Exception {
        StatusMessage statusMessage = new StatusMessage();
        if (token == null) {
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
                List<PrevisionesBO> lstResult = PrevisionesService.obtenerProductosgenerales(p);
                GenericEntity<List<PrevisionesBO>> entity = new GenericEntity<List<PrevisionesBO>>(lstResult) {
                };
                //return Response.status(Status.OK.getStatusCode()).entity(statusMessage).build();
                return Response.ok(entity).build();
            }
        } catch (JoseException e) {
            e.printStackTrace();
        }
        statusMessage.setStatus(Status.FORBIDDEN.getStatusCode());
        statusMessage.setMessage(
                "Access Denied for this functionality !!!");
        return Response.status(Status.FORBIDDEN.getStatusCode())
                .entity(statusMessage).build();
    }

    @Path("/obtenerArchivo")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerArchivo(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                ByteArrayOutputStream buffer = libreriaDataStageService.obtenerArchivo(parametros);

                if (buffer != null) {
                    ResponseBuilder response = Response.ok(Base64.encodeBase64(buffer.toByteArray()))
                            .header("Content-Disposition", "attachment; filename=" + parametros.getNombres()[0]);
                    Response responseBuilder = response.build();

                    return responseBuilder;
                } else {
                    StatusMessage statusMessage = new StatusMessage();
                    statusMessage.setStatus(Status.NO_CONTENT.getStatusCode());
                    statusMessage.setMessage("NO CONTENT");
                    return Response.status(Status.NO_CONTENT.getStatusCode())
                            .entity(statusMessage).build();
                }

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


    @Path("/registrarProducto")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarPermiso(@HeaderParam("x-auth-token") String token, ProductoBO bo) throws Exception {

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
                StatusMessage statusMessage = new StatusMessage();
                System.out.print("_--------__----__---_--___-__--__-_-_--______--_-");
                System.out.print("Dentro del metodo insertar Permiso importaciones");
                System.out.print("_--------__----__---_--___-__--__-_-_--______--_-");
                statusMessage.setMessage("Dentro del metodo obtener historial importaciones");
                boolean insercion = PrevisionesService.insertarProducto(bo);
                return Response.status(200).entity(insercion + "").build();
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


    @Path("/obtenerPrevision")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPrevision(@HeaderParam("x-auth-token") String token, Parametros parametro) throws GlosaNetException {
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
                PrevisionesBO lstTratado = PrevisionesService.obtenerPrevision(parametro.getId());
                GenericEntity<PrevisionesBO> entity = new GenericEntity<PrevisionesBO>(lstTratado) {
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


    @Path("/obtenerPrevisionActual")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPrevisionActual(@HeaderParam("x-auth-token") String token, Parametros parametro) {

        log.info("@obtenerPrevisionActual: Inicio");

        Response deniedAccessResponse = accessService.validateAccessToAPI(token);
        if (null != deniedAccessResponse)
            return deniedAccessResponse;

        String respuesta = null;
        try {
            respuesta = String.valueOf(PrevisionesService.obtenerPrevisionActual(parametro.getId()));

        } catch (GlosaNetException e) {
            e.printStackTrace();
            log.severe("@obtenerPrevisionActual: exception -> " + e.toString());
        }

        return Response.ok(new MediaContent("text", respuesta)).build();
    }


    @Path("/obtenerProductosCompletosPrevisionActual")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerProductosCompletosPrevisionActual(@HeaderParam("x-auth-token") String token, Parametros parametro) throws GlosaNetException {
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
                List<ProductoBO> lstBo = PrevisionesService.obtenerProductosCompletosPrevisionActual(parametro.getId());
                GenericEntity<List<ProductoBO>> entity = new GenericEntity<List<ProductoBO>>(lstBo) {
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


    @Path("/obtenerProductosPorPrevision")
    @POST
    @Produces(MediaType.APPLICATION_JSON)

    public Response obtenerProductosPorPrevision(@HeaderParam("x-auth-token") String token, Parametros p) throws Exception {
        StatusMessage statusMessage = new StatusMessage();
        if (token == null) {
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
                List<ProductoBO> lstResult = PrevisionesService.obtenerProductosPorPrevision(p.getId());
                GenericEntity<List<ProductoBO>> entity = new GenericEntity<List<ProductoBO>>(lstResult) {
                };
                //return Response.status(Status.OK.getStatusCode()).entity(statusMessage).build();
                return Response.ok(entity).build();
            }
        } catch (JoseException e) {
            e.printStackTrace();
        }
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
                PrevisionesService.eliminarNotificaciones(p.getCondicion());
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
                PrevisionesService.eliminarCorreos(p.getCondicion());
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


    @Path("/guardarListadoProducto")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response guardarProductos(@HeaderParam("x-auth-token") String token, List<ProductoBO> lstProductos) throws Exception {

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
                List<ProductoBO> lst = PrevisionesService.guardarProductos(lstProductos);
                GenericEntity<List<ProductoBO>> entity = new GenericEntity<List<ProductoBO>>(lst) {
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


    @Path("/eliminarPrevisionProductos")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarPrevisionProductos(@HeaderParam("x-auth-token") String token, Parametros p) throws Exception {
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
                PrevisionesService.eliminarPrevisionProducto(p.getCondicion());
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
                List<PHistorialEventoBO> result = PrevisionesService.guardarHistorialEvento(bo.getpHistorial());
                Parametros r = new Parametros();
                r.setpHistorial(result);
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
                List<PHistorialEventoBO> bo = PrevisionesService.obtenerHistorialEvento(p.getId());
                GenericEntity<List<PHistorialEventoBO>> entity = new GenericEntity<List<PHistorialEventoBO>>(bo) {
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
                PrevisionesService.eliminarHistorialEvento(p.getIdEmpresa(), p.getIdRegistro(), p.getIdEvento(), p.getIdHistorial());
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


    @Path("/subirArchivoHistorial/{idEmpresa}/{idPrevision}/{idEvento}/{idHistorial}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response subirArchivoHistorial(@HeaderParam("x-auth-token") String token,
                                          @FormDataParam("file") File file, @PathParam("idEmpresa") String idEmpresa,
                                          @PathParam("idPrevision") String idPrevision, @PathParam("idEvento") String idEvento,
                                          @PathParam("idHistorial") String idHistorial) throws Exception {

        log.info("@subirArchivoHistorial: Inicio");

        //Validamos acceso: si deniedAccessResponse != null => Acceso denegado
        Response deniedAccessResponse = accessService.validateAccessToAPI(token);
        if (null != deniedAccessResponse)
            return deniedAccessResponse;

        String respuesta = null;

        try {
                String path = idEmpresa + "/" + idPrevision + "/" + idEvento;
                respuesta = PrevisionesService.subirArchivoHistorial(file, "Doc_" + idHistorial, path);

        } catch (GlosaNetException e) {
            e.printStackTrace();
            log.severe("@subirArchivoHistorial: exception -> " + e.toString());
        }

        return Response.ok(new MediaContent("text", respuesta)).build();
    }


    @Path("/obtenerrArchivoHistorial/{idEmpresa}/{idPrevision}/{idEvento}/{idHistorial}")
    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response obtenerrArchivoHistorial(@HeaderParam("x-auth-token") String token,
                                             @PathParam("idEmpresa") String idEmpresa,
                                             @PathParam("idPrevision") String idPrevision, @PathParam("idEvento") String idEvento,
                                             @PathParam("idHistorial") String idHistorial) throws Exception {

        log.info("@obtenerrArchivoHistorial: Inicio");

        //Validamos acceso: si deniedAccessResponse != null => Acceso denegado
        Response deniedAccessResponse = accessService.validateAccessToAPI(token);
        if (null != deniedAccessResponse)
            return deniedAccessResponse;

        Response response = null;
        try {
            String path = idPrevision + "/" + idEmpresa + "/" + idEvento + "/Doc_" + idHistorial + ".pdf";
            ByteArrayOutputStream buffer = PrevisionesService.obtenerrArchivoHistorial(path);
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
                PrevisionesService.realizarEvento(p.getId(), p.getOrden());
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


    @Path("/actualizarFechaCita")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarFechaCita(@HeaderParam("x-auth-token") String token, Parametros parametro) throws Exception {
        System.out.println("***********");
        System.out.println(token);
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
                boolean result = PrevisionesService.actualizarFechaCita(parametro);
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


    @Path("/actualizarCitaCofeprisEvento")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarCitaCofeprisEvento(@HeaderParam("x-auth-token") String token, Parametros parametro) throws Exception {
        System.out.println("***********");
        System.out.println(token);
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
                boolean result = PrevisionesService.actualizarCitaCofeprisEvento(parametro);
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


    @Path("/guardarElementoMonitoreoPrevision")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response guardarElementoMonitoreoPrevision(@HeaderParam("x-auth-token") String token, Parametros p) throws Exception {
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
                 * p.getId() = idPrevision
                 * p.getTipo() = tipo de campo
                 * p.getCondicion() = valor string
                 * p.getFinicio() = valor date
                 */
                PrevisionesService.guardarElementoMonitoreoPrevision(p.getId(), Integer.parseInt(p.getTipo()), p.getCondicion(), p.getFinicio());
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


    @Path("/obtenerProductosLibreriaPrevision")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerProductosLibreriaPrevision(@HeaderParam("x-auth-token") String token, Parametros p) throws GlosaNetException {
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
                List<ProductoBO> lstTratado = PrevisionesService.obtenerPrevisiones(p.getId());
                GenericEntity<List<ProductoBO>> entity = new GenericEntity<List<ProductoBO>>(lstTratado) {
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
