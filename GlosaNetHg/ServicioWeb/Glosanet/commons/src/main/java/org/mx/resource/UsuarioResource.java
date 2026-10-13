package org.mx.resource;

import org.apache.commons.codec.binary.Base64;
import org.jose4j.lang.JoseException;
import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.*;
import org.mx.modelo.datastage.bo.UsuarioPermisosBO;
import org.mx.servicio.comun.AccessService;
import org.mx.servicio.comun.LlaveTokenService;
import org.mx.servicio.comun.UsuarioService;
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
import java.util.List;
import java.util.logging.Logger;

@Component
@Path("usuario")
@CrossOrigin
public class UsuarioResource {

    private final static Logger LOGGER = Logger.getLogger(UsuarioResource.class.getName());

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private LlaveTokenService llaveService;

    @Autowired
    private AccessService accessService;

    @Path("/agregarUsuario")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response agregarUsuario(@HeaderParam("x-auth-token") String token, UsuarioBO usuario) throws Exception {
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
                UsuarioBO usuarioBO = usuarioService.agregarUsuario(idUsuario, usuario);
                String respuesta = usuarioBO.getIdUsuario() + "";
                return Response.status(200).entity(respuesta).build();
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

    @Path("/actualizarUsuario/")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarUsuario(@HeaderParam("x-auth-token") String token, UsuarioBO usuario) throws Exception {
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
                if (usuarioService.actualizarUsuario(idUsuario, usuario)) {
                    return Response.status(200).entity(usuario).build();
                } else {
                    StatusMessage statusMessage = new StatusMessage();
                    statusMessage.setStatus(Status.FORBIDDEN.getStatusCode());
                    statusMessage.setMessage(
                            "ERROR !!!");
                    return Response.status(Status.FORBIDDEN.getStatusCode())
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

    @Path("/buscarUsuarios/")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarUsuarios(@HeaderParam("x-auth-token") String token, Parametros parametro) throws Exception {
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
                List<UsuarioBO> listUsuario = usuarioService.obtenerListaUsuarios(parametro.getTipo(), parametro.getYear());
                GenericEntity<List<UsuarioBO>> entity = new GenericEntity<List<UsuarioBO>>(listUsuario) {
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

    @Path("/buscarUsuariosRol/")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarUsuariosRol(@HeaderParam("x-auth-token") String token, Parametros parametro) throws Exception {
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
                List<UsuarioBO> listUsuario = usuarioService.buscarUsuariosRol(parametro.getTipo());
                GenericEntity<List<UsuarioBO>> entity = new GenericEntity<List<UsuarioBO>>(listUsuario) {
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

    @Path("/obtenerUsuarioActual/")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerUsuarioActual(@HeaderParam("x-auth-token") String token) throws Exception {
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
                RespuestaBO respuesta = usuarioService.obtenerUsuario("PK_Usuario", idUsuario);
                return Response.status(200).entity(respuesta).build();
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

    @Path("/obtenerUsuarioActualCompleto/")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerUsuarioActualCompleto(@HeaderParam("x-auth-token") String token) throws Exception {
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
                RespuestaBO respuesta = usuarioService.obtenerUsuarioCompleto("PK_Usuario", idUsuario);
                return Response.status(200).entity(respuesta).build();
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


    @Path("/buscarUsuariosPorEmpresa/")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarUsuariosPorEmpresa(@HeaderParam("x-auth-token") String token, Parametros parametro) {

        LOGGER.info("@buscarUsuariosPorEmpresa: Inicio");

        //Validamos acceso: si deniedAccessResponse != null => Acceso denegado
        Response deniedAccessResponse = accessService.validateAccessToAPI(token);
        if (null != deniedAccessResponse)
            return deniedAccessResponse;

        //Accedemos a datos
        List<UsuarioBO> listUser;
        try {
            listUser = usuarioService.obtenerUsuariosPorEmpresa(
                    parametro.getId());
        } catch (GlosaNetException e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
                    .entity(e).build();
        }

        //Devolvemos respuesta
        GenericEntity<List<UsuarioBO>> entity =
                new GenericEntity<List<UsuarioBO>>(listUser) {
                };
        return Response.ok(entity).build();
    }

    @Path("/buscarRoles/")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarRoles(@HeaderParam("x-auth-token") String token) throws Exception {
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
                List<RolBO> listRol = usuarioService.obtenerListaRoles();
                GenericEntity<List<RolBO>> entity = new GenericEntity<List<RolBO>>(listRol) {
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


    @Path("/actualizarPermisos/")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarPermisos(@HeaderParam("x-auth-token") String token, RespuestaBO respuesta) throws Exception {
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

                for (UsuarioBO e : respuesta.getAdministrador()) {
                    for (UsuarioPermisosBO p : e.getPermisos()) {
                        usuarioService.actualizarPermisos(p);
                    }
                }

                return Response.status(200).entity(respuesta).build();

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


    @Path("/obtenerLogo/{idCliente}/{idEmpresa}/")
    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response obtenerLogo(@HeaderParam("x-auth-token") String token,
                                @PathParam("idEmpresa") String idEmpresa,
                                @PathParam("idCliente") String idCliente) {

        LOGGER.info("@obtenerLogo: Inicio");

        //Validamos acceso: si deniedAccessResponse != null => Acceso denegado
        Response deniedAccessResponse = accessService.validateAccessToAPI(token);
        if (null != deniedAccessResponse)
            return deniedAccessResponse;

        Response response = null;
        try {
            ByteArrayOutputStream buffer = usuarioService.descargarLogo(idEmpresa, idCliente);
            if (null == buffer)
                return Response.status(Status.NO_CONTENT.getStatusCode())
                        .entity("NO CONTENT").build();

            ResponseBuilder responseBuilder = Response.ok(Base64.encodeBase64(buffer.toByteArray()))
                    .header("Content-Disposition", "attachment; filename=Logo_" + idEmpresa + ".jpg");
            response = responseBuilder.build();

        } catch (GlosaNetException e) {
            e.printStackTrace();
            LOGGER.severe("@obtenerLogo: exception -> " + e.toString());
        }

        return response;
    }

}
