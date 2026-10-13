package org.mx.resource;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.jose4j.lang.JoseException;
import org.mx.modelo.comun.bo.DashBoard;
import org.mx.modelo.comun.bo.Parametros;
import org.mx.modelo.comun.bo.StatusMessage;
import org.mx.modelo.comun.bo.UsuarioBO;
import org.mx.servicio.comun.AccessService;
import org.mx.servicio.comun.DashBoardService;
import org.mx.servicio.comun.LlaveTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Component
@Path("dashBoard")
@CrossOrigin
public class DashBoardResource {

    private final static Logger LOGGER = Logger.getLogger(DashBoardResource.class.getName());

    @Autowired
    private DashBoardService dashBoardService;
    @Autowired
    private LlaveTokenService llaveService;
    @Autowired
    private AccessService accessService;

    @Path("/generarReporte")
    @POST
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response generarReporte(@HeaderParam("x-auth-token") String token, String arguments) {

        LOGGER.info("@generarReporte: Inicio");

        //Validamos acceso: si deniedAccessResponse != null => Acceso denegado
        Response deniedAccessResponse = accessService.validateAccessToAPI(token);
        if (null != deniedAccessResponse)
            return deniedAccessResponse;

        Response response;
        try {

            // convert JSON string to Map
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map = mapper.readValue(arguments, new TypeReference<Map<String, Object>>() {
            });

            byte[] byteArray = dashBoardService.generaReporteDG501PDF(map);
            if (null == byteArray)
                return Response.status(Status.NO_CONTENT.getStatusCode())
                        .entity("NO CONTENT").build();

            ResponseBuilder responseBuilder = Response.ok(Base64.encodeBase64(byteArray))
                    .header("Content-Disposition", "attachment; filename=reporte.pdf");
            response = responseBuilder.build();

        } catch (Exception e) {

            return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
                    .entity(e).build();
        }

        return response;
    }

    @Path("/t702_ObtenerTotalPorFormaPago")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t702_ObtenerTotalPorFormaPago(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t702_ObtenerTotalPorFormaPago(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t702_ObtenerOperacionesPorAduana")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t702_ObtenerOperacionesPorAduana(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t702_ObtenerOperacionesPorAduana(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t702_ObtenerOperacionesPorPatente")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t702_ObtenerOperacionesPorPatente(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t702_ObtenerOperacionesPorPatente(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t701_ObtenerOperacionesPorAduana")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t701_ObtenerOperacionesPorAduana(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t701_ObtenerOperacionesPorAduana(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t701_ObtenerOperacionesPorPatente")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t701_ObtenerOperacionesPorPatente(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t701_ObtenerOperacionesPorPatente(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t701_ObtenerOperacionesPorClaveDocumento")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t701_ObtenerOperacionesPorClaveDocumento(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t701_ObtenerOperacionesPorClaveDocumento(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t556_ObtenerOperacionesTipoTasa")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t556_ObtenerOperacionesTipoTasa(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t556_ObtenerOperacionesTipoTasa(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t556_ObtenerOperacionesPorAgente")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t556_ObtenerOperacionesPorAgente(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t556_ObtenerOperacionesPorAgente(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t556_ObtenerOperacionesPorAduana")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t556_ObtenerOperacionesPorAduana(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t556_ObtenerOperacionesPorAduana(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t556_ObtenerOperacionesTasaContribucion")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t556_ObtenerOperacionesTasaContribucion(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t556_ObtenerOperacionesTasaContribucion(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t556_ObtenerOperacionesPorClave")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t556_ObtenerOperacionesPorClave(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t556_ObtenerOperacionesPorClave(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t554_ObtenerOperacionesPorAgente")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t554_ObtenerOperacionesPorAgente(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t554_ObtenerOperacionesPorAgente(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t554_ObtenerOperacionesPorClave")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t554_ObtenerOperacionesPorClave(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t554_ObtenerOperacionesPorClave(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t554_ObtenerOperacionesPorAduana")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t554_ObtenerOperacionesPorAduana(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t554_ObtenerOperacionesPorAduana(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t510_ObtenerOperacionesPorTipoPedimento")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t510_ObtenerOperacionesPorTipoPedimento(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t510_ObtenerOperacionesPorTipoPedimento(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t510_ObtenerOperacionesPorAgente")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t510_ObtenerOperacionesPorAgente(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t510_ObtenerOperacionesPorAgente(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t510_ObtenerOperacionesPorClave")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t510_ObtenerOperacionesPorClave(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t510_ObtenerOperacionesPorClave(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t510_ObtenerOperacionesPorContribucion")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t510_ObtenerOperacionesPorContribucion(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t510_ObtenerOperacionesPorContribucion(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t510_ObtenerOperacionesPorAduana")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t510_ObtenerOperacionesPorAduana(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t510_ObtenerOperacionesPorAduana(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t509_ObtenerPedimentoPorImporteTasa")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t509_ObtenerPedimentoPorImporteTasa(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t509_ObtenerPedimentoPorImporteTasa(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t509_ObtenerPedimentoPorAgente")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t509_ObtenerPedimentoPorAgente(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t509_ObtenerPedimentoPorAgente(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t509_ObtenerPedimentoPorTipoTasa")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t509_ObtenerPedimentoPorTipoTasa(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t509_ObtenerPedimentoPorTipoTasa(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t509_ObtenerPedimentoPorAduana")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t509_ObtenerPedimentoPorAduana(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t509_ObtenerPedimentoPorAduana(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t507_ObtenerPedimentoPorClave")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t507_ObtenerPedimentoPorClave(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t507_ObtenerPedimentoPorClave(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t507_ObtenerPedimentoPorAduana")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t507_ObtenerPedimentoPorAduana(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t507_ObtenerPedimentoPorAduana(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t506_ObtenerFechaPorAgente")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t506_ObtenerFechaPorAgente(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t506_ObtenerFechaPorAgente(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t506_ObtenerFechaPorAduana")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t506_ObtenerFechaPorAduana(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t506_ObtenerFechaPorAduana(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t505_ObtenerFacturaPorMoneda")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t505_ObtenerFacturaPorMoneda(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t505_ObtenerFacturaPorMoneda(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t505_ObtenerFacturaPorPais")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t505_ObtenerFacturaPorPais(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t505_ObtenerFacturaPorPais(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t504_ObtenerContenedorPorAgente")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t504_ObtenerContenedorPorAgente(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t504_ObtenerContenedorPorAgente(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t504_ObtenerContenedorPorTipo")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t504_ObtenerContenedorPorTipo(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t504_ObtenerContenedorPorTipo(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t504_ObtenerContenedorPorAduana")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t504_ObtenerContenedorPorAduana(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t504_ObtenerContenedorPorAduana(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t503_ObtenerGuiasPorAgente")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t503_ObtenerGuiasPorAgente(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t503_ObtenerGuiasPorAgente(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t503_ObtenerGuiasPorAduana")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t503_ObtenerGuiasPorAduana(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t503_ObtenerGuiasPorAduana(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t503_ObtenerGuiasPorTipo")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t503_ObtenerGuiasPorTipo(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t503_ObtenerGuiasPorTipo(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t501_obtenerOperacionesDelPeriodo")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t501_obtenerOperacionesDelPeriodo(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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


                List<DashBoard> lstResult = dashBoardService.t501_obtenerOperacionesDelPeriodo(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t501_obtenerOperacionesPorAduana")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t501_obtenerOperacionesPorAduana(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t501_obtenerOperacionesPorAduana(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t501_obtenerTipoCambioPeriodo")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t501_obtenerTipoCambioPeriodo(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t501_obtenerTipoCambioPeriodo(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t501_obtenerOperacionesPorMedioTransporteArribo")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t501_obtenerOperacionesPorMedioTransporteArribo(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t501_obtenerOperacionesPorMedioTransporteArribo(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t501_obtenerPesoBrutoPorMedioTransporteArribo")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t501_obtenerPesoBrutoPorMedioTransporteArribo(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t501_obtenerPesoBrutoPorMedioTransporteArribo(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t501_obtenerOperacionesPorTipoPedimento")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t501_obtenerOperacionesPorTipoPedimento(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t501_obtenerOperacionesPorTipoPedimento(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t501_obtenerOperacionesPorClavePedimento")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t501_obtenerOperacionesPorClavePedimento(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t501_obtenerOperacionesPorClavePedimento(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t502_obtenerOperacionesPorAduana")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t502_obtenerOperacionesPorAduana(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t502_obtenerOperacionesPorAduana(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t502_obtenerOperacionesPorAgenteAduanal")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t502_obtenerOperacionesPorAgenteAduanal(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t502_obtenerOperacionesPorAgenteAduanal(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t502_obtenerOperacionesPorPais")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t502_obtenerOperacionesPorPais(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t502_obtenerOperacionesPorPais(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t557_obtenerOperacionesPorFormaDePago")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t557_obtenerOperacionesPorFormaDePago(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t557_obtenerOperacionesPorFormaDePago(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t557_obtenerImportePagoPorTipoContribucion")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t557_obtenerImportePagoPorTipoContribucion(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t557_obtenerImportePagoPorTipoContribucion(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t557_obtenerOperacionesPorAgenteAduanal")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t557_obtenerOperacionesPorAgenteAduanal(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t557_obtenerOperacionesPorAgenteAduanal(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t557_obtenerOperacionesPorAduana")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t557_obtenerOperacionesPorAduana(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t557_obtenerOperacionesPorAduana(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t501_ObtenerOperacionesMedioTransporteSalida")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t501_ObtenerOperacionesMedioTransporteSalida(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t501_ObtenerOperacionesMedioTransporteSalida(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t501_ObtenerOperacionesMedioTransporteArriboSalida")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t501_ObtenerOperacionesMedioTransporteArriboSalida(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t501_ObtenerOperacionesMedioTransporteArriboSalida(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t501_ObtenerOperacionesPorIncrementablesYDeducibles")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t501_ObtenerOperacionesPorIncrementablesYDeducibles(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t501_ObtenerOperacionesPorIncrementablesYDeducibles(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t501_ObtenerOperacionesDestinoMercancia")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t501_ObtenerOperacionesDestinoMercancia(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t501_ObtenerOperacionesDestinoMercancia(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t501_ObtenerOperacionesPorPatente")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t501_ObtenerOperacionesPorPatente(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t501_ObtenerOperacionesPorPatente(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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

    @Path("/t501_ObtenerOperacionesPorTipoPedimento")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t501_ObtenerOperacionesPorTipoPedimento(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DashBoard> lstResult = dashBoardService.t501_ObtenerOperacionesPorTipoPedimento(parametros);
                GenericEntity<List<DashBoard>> entity = new GenericEntity<List<DashBoard>>(lstResult) {
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


    @Path("/t501_obtenerDatos")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t501_obtenerDatos(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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

                List<Parametros> lstG = new ArrayList<Parametros>();
                Parametros lst1 = new Parametros();
                lst1.setList(dashBoardService.t501_obtenerOperacionesDelPeriodo(parametros));
                lst1.setNombreDashBoard("t501_OperacionesDelPeriodo");
                Parametros lst2 = new Parametros();
                lst2.setList(dashBoardService.t501_obtenerTipoCambioPeriodo(parametros));
                lst2.setNombreDashBoard("t501_TipoCambioPeriodo");
                Parametros lst3 = new Parametros();
                lst3.setList(dashBoardService.t501_obtenerOperacionesPorAduana(parametros));
                lst3.setNombreDashBoard("t501_OperacionesPorAduana");
                Parametros lst4 = new Parametros();
                lst4.setList(dashBoardService.t501_obtenerOperacionesPorMedioTransporteArribo(parametros));
                lst4.setNombreDashBoard("t501_OperacionesPorMedioTransporteArribo");
                Parametros lst5 = new Parametros();
                lst5.setList(dashBoardService.t501_obtenerPesoBrutoPorMedioTransporteArribo(parametros));
                lst5.setNombreDashBoard("t501_PesoBrutoPorMedioTransporteArribo");
                Parametros lst6 = new Parametros();
                lst6.setList(dashBoardService.t501_obtenerOperacionesPorTipoPedimento(parametros));
                lst6.setNombreDashBoard("t501_operacionesPorTipoPedimento");
                Parametros lst7 = new Parametros();
                lst7.setList(dashBoardService.t501_obtenerOperacionesPorClavePedimento(parametros));
                lst7.setNombreDashBoard("t501_obtenerOperacionesPorClavePedimento");
                Parametros lst8 = new Parametros();
                lst8.setList(dashBoardService.t501_ObtenerOperacionesMedioTransporteSalida(parametros));
                lst8.setNombreDashBoard("t501_OperacionesMedioTransporteSalida");
                Parametros lst9 = new Parametros();
                lst9.setList(dashBoardService.t501_ObtenerOperacionesMedioTransporteArriboSalida(parametros));
                lst9.setNombreDashBoard("t501_OperacionesMedioTransporteArriboSalida");
                Parametros lst10 = new Parametros();
                lst10.setList(dashBoardService.t501_ObtenerOperacionesPorIncrementablesYDeducibles(parametros));
                lst10.setNombreDashBoard("t501_OperacionesPorIncrementablesYDeducibles");
                Parametros lst11 = new Parametros();
                lst11.setList(dashBoardService.t501_ObtenerOperacionesDestinoMercancia(parametros));
                lst11.setNombreDashBoard("t501_OperacionesDestinoMercancia");
                Parametros lst12 = new Parametros();
                lst12.setList(dashBoardService.t501_ObtenerOperacionesPorPatente(parametros));
                lst12.setNombreDashBoard("t501_OperacionesPorPatente");
                Parametros lst13 = new Parametros();
                lst13.setList(dashBoardService.t501_ObtenerOperacionesPorTipoPedimento(parametros));
                lst13.setNombreDashBoard("t501_OperacionesPorTipoPedimento");
                lstG.add(lst1);
                lstG.add(lst2);
                lstG.add(lst3);
                lstG.add(lst4);
                lstG.add(lst5);
                lstG.add(lst6);
                lstG.add(lst7);
                lstG.add(lst8);
                lstG.add(lst9);
                lstG.add(lst10);
                lstG.add(lst11);
                lstG.add(lst12);
                lstG.add(lst13);


                GenericEntity<List<Parametros>> entity = new GenericEntity<List<Parametros>>(lstG) {
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

    @Path("/t502_obtenerDatos")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t502_obtenerDatos(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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

                List<Parametros> lstG = new ArrayList<Parametros>();
                Parametros lst1 = new Parametros();
                lst1.setList(dashBoardService.t502_obtenerOperacionesPorAduana(parametros));
                lst1.setNombreDashBoard("t502_OperacionesPorAduana");
                Parametros lst2 = new Parametros();
                lst2.setList(dashBoardService.t502_obtenerOperacionesPorAgenteAduanal(parametros));
                lst2.setNombreDashBoard("t502_OperacionesPorAgenteAduanal");
                Parametros lst3 = new Parametros();
                lst3.setList(dashBoardService.t502_obtenerOperacionesPorPais(parametros));
                lst3.setNombreDashBoard("t502_OperacionesPorPais");
                lstG.add(lst1);
                lstG.add(lst2);
                lstG.add(lst3);


                GenericEntity<List<Parametros>> entity = new GenericEntity<List<Parametros>>(lstG) {
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

    @Path("/t503_obtenerDatos")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t503_obtenerDatos(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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

                List<Parametros> lstG = new ArrayList<Parametros>();
                Parametros lst1 = new Parametros();
                lst1.setList(dashBoardService.t503_ObtenerGuiasPorTipo(parametros));
                lst1.setNombreDashBoard("t503_GuiasPorTipo");
                Parametros lst2 = new Parametros();
                lst2.setList(dashBoardService.t503_ObtenerGuiasPorAduana(parametros));
                lst2.setNombreDashBoard("t503_GuiasPorAduana");
                Parametros lst3 = new Parametros();
                lst3.setList(dashBoardService.t503_ObtenerGuiasPorAgente(parametros));
                lst3.setNombreDashBoard("t503_GuiasPorAgente");
                lstG.add(lst1);
                lstG.add(lst2);
                lstG.add(lst3);


                GenericEntity<List<Parametros>> entity = new GenericEntity<List<Parametros>>(lstG) {
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


    @Path("/t504_obtenerDatos")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t504_obtenerDatos(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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

                List<Parametros> lstG = new ArrayList<Parametros>();
                Parametros lst1 = new Parametros();
                lst1.setList(dashBoardService.t504_ObtenerContenedorPorAduana(parametros));
                lst1.setNombreDashBoard("t504_ContenedorPorAduana");
                Parametros lst2 = new Parametros();
                lst2.setList(dashBoardService.t504_ObtenerContenedorPorTipo(parametros));
                lst2.setNombreDashBoard("t504_ContenedorPorTipo");
                Parametros lst3 = new Parametros();
                lst3.setList(dashBoardService.t504_ObtenerContenedorPorAgente(parametros));
                lst3.setNombreDashBoard("t504_ContenedorPorAgente");
                lstG.add(lst1);
                lstG.add(lst2);
                lstG.add(lst3);


                GenericEntity<List<Parametros>> entity = new GenericEntity<List<Parametros>>(lstG) {
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


    @Path("/t505_obtenerDatos")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t505_obtenerDatos(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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

                List<Parametros> lstG = new ArrayList<Parametros>();
                Parametros lst1 = new Parametros();
                lst1.setList(dashBoardService.t505_ObtenerFacturaPorPais(parametros));
                lst1.setNombreDashBoard("t505_FacturaPorPais");
                Parametros lst2 = new Parametros();
                lst2.setList(dashBoardService.t505_ObtenerFacturaPorMoneda(parametros));
                lst2.setNombreDashBoard("t505_FacturaPorMoneda");
                lstG.add(lst1);
                lstG.add(lst2);


                GenericEntity<List<Parametros>> entity = new GenericEntity<List<Parametros>>(lstG) {
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


    @Path("/t506_obtenerDatos")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t506_obtenerDatos(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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

                List<Parametros> lstG = new ArrayList<Parametros>();
                Parametros lst1 = new Parametros();
                lst1.setList(dashBoardService.t506_ObtenerFechaPorAduana(parametros));
                lst1.setNombreDashBoard("t506_FechaPorAduana");
                Parametros lst2 = new Parametros();
                lst2.setList(dashBoardService.t506_ObtenerFechaPorAgente(parametros));
                lst2.setNombreDashBoard("t506_FechaPorAgente");
                lstG.add(lst1);
                lstG.add(lst2);


                GenericEntity<List<Parametros>> entity = new GenericEntity<List<Parametros>>(lstG) {
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

    @Path("/t507_obtenerDatos")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t507_obtenerDatos(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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

                List<Parametros> lstG = new ArrayList<Parametros>();
                Parametros lst1 = new Parametros();
                lst1.setList(dashBoardService.t507_ObtenerPedimentoPorAduana(parametros));
                lst1.setNombreDashBoard("t507_PedimentoPorAduana");
                Parametros lst2 = new Parametros();
                lst2.setList(dashBoardService.t507_ObtenerPedimentoPorClave(parametros));
                lst2.setNombreDashBoard("t507_PedimentoPorClave");
                lstG.add(lst1);
                lstG.add(lst2);


                GenericEntity<List<Parametros>> entity = new GenericEntity<List<Parametros>>(lstG) {
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

    @Path("/t509_obtenerDatos")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t509_obtenerDatos(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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

                List<Parametros> lstG = new ArrayList<Parametros>();
                Parametros lst1 = new Parametros();
                lst1.setList(dashBoardService.t509_ObtenerPedimentoPorTipoTasa(parametros));
                lst1.setNombreDashBoard("t509_PedimentoPorTipoTasa");
                Parametros lst2 = new Parametros();
                lst2.setList(dashBoardService.t509_ObtenerPedimentoPorAduana(parametros));
                lst2.setNombreDashBoard("t509_PedimentoPorAduana");
                Parametros lst3 = new Parametros();
                lst3.setList(dashBoardService.t509_ObtenerPedimentoPorImporteTasa(parametros));
                lst3.setNombreDashBoard("t509_PedimentoPorImporteTasa");
                Parametros lst4 = new Parametros();
                lst4.setList(dashBoardService.t509_ObtenerPedimentoPorAgente(parametros));
                lst4.setNombreDashBoard("t509_PedimentoPorAgente");
                lstG.add(lst1);
                lstG.add(lst2);
                lstG.add(lst3);
                lstG.add(lst4);


                GenericEntity<List<Parametros>> entity = new GenericEntity<List<Parametros>>(lstG) {
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

    @Path("/t510_obtenerDatos")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t510_obtenerDatos(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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

                List<Parametros> lstG = new ArrayList<Parametros>();
                Parametros lst1 = new Parametros();
                lst1.setList(dashBoardService.t510_ObtenerOperacionesPorTipoPedimento(parametros));
                lst1.setNombreDashBoard("t510_OperacionesPorTipoPedimento");
                Parametros lst2 = new Parametros();
                lst2.setList(dashBoardService.t510_ObtenerOperacionesPorAduana(parametros));
                lst2.setNombreDashBoard("t510_OperacionesPorAduana");
                Parametros lst3 = new Parametros();
                lst3.setList(dashBoardService.t510_ObtenerOperacionesPorContribucion(parametros));
                lst3.setNombreDashBoard("t510_OperacionesPorContribucion");
                Parametros lst4 = new Parametros();
                lst4.setList(dashBoardService.t510_ObtenerOperacionesPorClave(parametros));
                lst4.setNombreDashBoard("t510_OperacionesPorClave");
                Parametros lst5 = new Parametros();
                lst5.setList(dashBoardService.t510_ObtenerOperacionesPorAgente(parametros));
                lst5.setNombreDashBoard("t510_OperacionesPorAgente");
                lstG.add(lst1);
                lstG.add(lst2);
                lstG.add(lst3);
                lstG.add(lst4);
                lstG.add(lst5);


                GenericEntity<List<Parametros>> entity = new GenericEntity<List<Parametros>>(lstG) {
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

    @Path("/t701_obtenerDatos")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t701_obtenerDatos(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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

                List<Parametros> lstG = new ArrayList<Parametros>();
                Parametros lst1 = new Parametros();
                lst1.setList(dashBoardService.t701_ObtenerOperacionesPorAduana(parametros));
                lst1.setNombreDashBoard("t701_OperacionesPorAduana");
                Parametros lst2 = new Parametros();
                lst2.setList(dashBoardService.t701_ObtenerOperacionesPorPatente(parametros));
                lst2.setNombreDashBoard("t701_OperacionesPorPatente");
                Parametros lst3 = new Parametros();
                lst3.setList(dashBoardService.t701_ObtenerOperacionesPorClaveDocumento(parametros));
                lst3.setNombreDashBoard("t701_OperacionesPorClaveDocumento");
                lstG.add(lst1);
                lstG.add(lst2);
                lstG.add(lst3);


                GenericEntity<List<Parametros>> entity = new GenericEntity<List<Parametros>>(lstG) {
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


    @Path("/t702_obtenerDatos")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t702_obtenerDatos(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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

                List<Parametros> lstG = new ArrayList<Parametros>();
                Parametros lst1 = new Parametros();
                lst1.setList(dashBoardService.t702_ObtenerOperacionesPorAduana(parametros));
                lst1.setNombreDashBoard("t702_OperacionesPorAduana");
                Parametros lst2 = new Parametros();
                lst2.setList(dashBoardService.t702_ObtenerOperacionesPorPatente(parametros));
                lst2.setNombreDashBoard("t702_OperacionesPorPatente");
                Parametros lst3 = new Parametros();
                lst3.setList(dashBoardService.t702_ObtenerTotalPorFormaPago(parametros));
                lst3.setNombreDashBoard("t702_TotalPorFormaPago");
                lstG.add(lst1);
                lstG.add(lst2);
                lstG.add(lst3);


                GenericEntity<List<Parametros>> entity = new GenericEntity<List<Parametros>>(lstG) {
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

    @Path("/t554_obtenerDatos")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t554_obtenerDatos(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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

                List<Parametros> lstG = new ArrayList<Parametros>();
                Parametros lst1 = new Parametros();
                lst1.setList(dashBoardService.t554_ObtenerOperacionesPorAduana(parametros));
                lst1.setNombreDashBoard("t554_OperacionesPorAduana");
                Parametros lst2 = new Parametros();
                lst2.setList(dashBoardService.t554_ObtenerOperacionesPorAgente(parametros));
                lst2.setNombreDashBoard("t554_OperacionesPorAgente");
                Parametros lst3 = new Parametros();
                lst3.setList(dashBoardService.t554_ObtenerOperacionesPorClave(parametros));
                lst3.setNombreDashBoard("t554_OperacionesPorClave");
                lstG.add(lst1);
                lstG.add(lst2);
                lstG.add(lst3);


                GenericEntity<List<Parametros>> entity = new GenericEntity<List<Parametros>>(lstG) {
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

    @Path("/t556_obtenerDatos")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t556_obtenerDatos(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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

                List<Parametros> lstG = new ArrayList<Parametros>();
                Parametros lst1 = new Parametros();
                lst1.setList(dashBoardService.t556_ObtenerOperacionesPorAduana(parametros));
                lst1.setNombreDashBoard("t556_OperacionesPorAduana");
                Parametros lst2 = new Parametros();
                lst2.setList(dashBoardService.t556_ObtenerOperacionesPorAgente(parametros));
                lst2.setNombreDashBoard("t556_OperacionesPorAgente");
                Parametros lst3 = new Parametros();
                lst3.setList(dashBoardService.t556_ObtenerOperacionesPorClave(parametros));
                lst3.setNombreDashBoard("t556_OperacionesPorClave");
                Parametros lst4 = new Parametros();
                lst4.setList(dashBoardService.t556_ObtenerOperacionesTasaContribucion(parametros));
                lst4.setNombreDashBoard("t556_OperacionesTasaContribucion");
                Parametros lst5 = new Parametros();
                lst5.setList(dashBoardService.t556_ObtenerOperacionesTipoTasa(parametros));
                lst5.setNombreDashBoard("t556_OperacionesTipoTasa");
                lstG.add(lst1);
                lstG.add(lst2);
                lstG.add(lst3);
                lstG.add(lst4);
                lstG.add(lst5);

                GenericEntity<List<Parametros>> entity = new GenericEntity<List<Parametros>>(lstG) {
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

    @Path("/t557_obtenerDatos")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response t557_obtenerDatos(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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

                List<Parametros> lstG = new ArrayList<Parametros>();
                Parametros lst1 = new Parametros();
                lst1.setList(dashBoardService.t557_obtenerOperacionesPorAduana(parametros));
                lst1.setNombreDashBoard("t557_OperacionesPorAduana");
                Parametros lst2 = new Parametros();
                lst2.setList(dashBoardService.t557_obtenerOperacionesPorAgenteAduanal(parametros));
                lst2.setNombreDashBoard("t557_OperacionesPorAgenteAduanal");
                Parametros lst3 = new Parametros();
                lst3.setList(dashBoardService.t557_obtenerImportePagoPorTipoContribucion(parametros));
                lst3.setNombreDashBoard("t557_ImportePagoPorTipoContribucion");
                Parametros lst4 = new Parametros();
                lst4.setList(dashBoardService.t557_obtenerOperacionesPorFormaDePago(parametros));
                lst4.setNombreDashBoard("t557_OperacionesPorFormaDePago");
                lstG.add(lst1);
                lstG.add(lst2);
                lstG.add(lst3);
                lstG.add(lst4);

                GenericEntity<List<Parametros>> entity = new GenericEntity<List<Parametros>>(lstG) {
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
