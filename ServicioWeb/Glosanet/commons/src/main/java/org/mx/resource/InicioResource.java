package org.mx.resource;

import org.jose4j.lang.JoseException;
import org.mx.modelo.comun.bo.Parametros;
import org.mx.modelo.comun.bo.StatusMessage;
import org.mx.modelo.comun.bo.UsuarioBO;
import org.mx.modelo.registros.bo.EventoBO;
import org.mx.servicio.comun.LlaveTokenService;
import org.mx.servicio.comun.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Logger;

@Component
@Path("inicio")
@CrossOrigin

public class InicioResource {

    private final static Logger LOGGER = Logger.getLogger(InicioResource.class.getName());

    private static class TokenAccess {

        Integer userId;
        String token;

        public TokenAccess() {
        }

        public Integer getUserId() {
            return userId;
        }
        public void setUserId(Integer userId) {
            this.userId = userId;
        }
        public String getToken() {
            return token;
        }
        public void setToken(String token) {
            this.token = token;
        }
    }

    private UsuarioService userService;
    private LlaveTokenService keyService;

    public UsuarioService getUserService() {
        return userService;
    }

    @Autowired
    public void setUserService(UsuarioService userService) {
        this.userService = userService;
    }

    public LlaveTokenService getKeyService() {
        return keyService;
    }

    @Autowired
    public void setKeyService(LlaveTokenService keyService) {
        this.keyService = keyService;
    }

    @Path("/status")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String returnVersion() {
        return "JwtSecurityExample Status is OK...";
    }

    @PermitAll
    @Path("/authenticate")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticateCredentials(UsuarioBO usuario)
            throws IOException, NoSuchAlgorithmException {

        LOGGER.info("@authenticateCredentials: Inicio");

        String username = usuario.getUsuario();
        String password = usuario.getPassword();

        if (null == username) {
            StatusMessage statusMessage = new StatusMessage();
            statusMessage.setStatus(
                    Status.PRECONDITION_FAILED.getStatusCode());
            statusMessage.setMessage("Falta Usuario");
            return Response.status(
                    Status.PRECONDITION_FAILED.getStatusCode())
                    .entity(statusMessage).build();
        }

        if (null == password) {
            StatusMessage statusMessage = new StatusMessage();
            statusMessage.setStatus(
                    Status.PRECONDITION_FAILED.getStatusCode());
            statusMessage.setMessage("Falta Contrasena");
            return Response.status(
                    Status.PRECONDITION_FAILED.getStatusCode())
                    .entity(statusMessage).build();
        }

        LOGGER.info("@authenticateCredentials: usuario: " + username);
        UsuarioBO usuarioValidado = userService.validarUsuario(username, password);

        if (null == usuarioValidado) {
            StatusMessage statusMessage = new StatusMessage();
            statusMessage.setStatus(Status.FORBIDDEN.getStatusCode());
            statusMessage.setMessage(
                    "Acceso denegado: user = null");
            return Response.status(Status.FORBIDDEN.getStatusCode())
                    .entity(statusMessage).build();
        }
        try {
            String jwt = keyService.generarToken(usuarioValidado);
            TokenAccess accesoToken = new TokenAccess();
            accesoToken.setUserId(usuarioValidado.getIdUsuario());
            accesoToken.setToken(jwt);
            return Response.ok(accesoToken).build();
        } catch (Exception e) {
            e.printStackTrace();
            StatusMessage statusMessage = new StatusMessage();
            statusMessage.setStatus(Status.FORBIDDEN.getStatusCode());
            statusMessage.setMessage(
                    "Acceso denegado");
            return Response.status(Status.FORBIDDEN.getStatusCode())
                    .entity(statusMessage).build();
        }
    }


    @Path("/obtenerUsuarios")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response findItemById(@HeaderParam("x-auth-token") String token,
                                 Parametros parametro) throws Exception {
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
            UsuarioBO u = keyService.validacionToken(token);
            if (u != null) {
                idUsuario = u.getIdUsuario();
            }
            if (idUsuario > 0) {
                List<UsuarioBO> usuarios = userService.obtenerListaUsuarios(parametro.getTipo(), parametro.getYear());//antes era en lugar de year id
                GenericEntity<List<UsuarioBO>> entity = new GenericEntity<List<UsuarioBO>>(usuarios) {
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


    @Path("/restablecerContrasenia")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response restablecerContrasenia(String email) throws Exception {
        try {
            if (email.contains("@") && !email.contains("<") && !email.equals("")) {
                userService.buscarEmail(email);
                String mensaje = "Ok";
                GenericEntity<String> respuesta = new GenericEntity<String>(mensaje) {
                };
                return Response.ok(respuesta).build();
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


    @Path("/guardarInfo")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response guardarInfo(@HeaderParam("x-auth-token") String token) throws Exception {
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
            UsuarioBO u = keyService.validacionToken(token);
            if (u != null) {
                idUsuario = u.getIdUsuario();
            }
            if (idUsuario > 0) {
                List<EventoBO> lstResult = userService.guardarInfo();
                GenericEntity<List<EventoBO>> entity = new GenericEntity<List<EventoBO>>(lstResult) {
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
