package org.mx.resource;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.jose4j.lang.JoseException;
import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.Parametros;
import org.mx.modelo.comun.bo.StatusMessage;
import org.mx.modelo.comun.bo.UsuarioBO;
import org.mx.modelo.reportes.ParametrosPartida;
import org.mx.modelo.reportes.bo.DetalleContribucionesBO;
import org.mx.modelo.reportes.bo.DetallePartidasBO;
import org.mx.modelo.reportes.bo.ResumenReporteBO;
import org.mx.modelo.reportes.po.DetallePartidasPO;
import org.mx.persistencia.Page;
import org.mx.servicio.comun.AccessService;
import org.mx.servicio.comun.LlaveTokenService;
import org.mx.servicio.reporte.PredefinidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Component
@Path("reportePredefinido")
@CrossOrigin
public class ReportePredefinidoResource {

    private final static Logger LOGGER = Logger.getLogger(ReportePredefinidoResource.class.getName());

    @Autowired
    private PredefinidoService predefinidoSercvice;
    @Autowired
    private LlaveTokenService llaveService;
    @Autowired
    private AccessService accessService;

    @Path("/obtenerResultadosReportesPredefinidos")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerResultadosReportesPredefinidos(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<ResumenReporteBO> lstResumenReporte = predefinidoSercvice.obtenerResultadosReportesPredefinidos(parametros);
                GenericEntity<List<ResumenReporteBO>> entity = new GenericEntity<List<ResumenReporteBO>>(lstResumenReporte) {
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


    @Path("/generarReporte")
    @POST
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response generarReporte(@HeaderParam("x-auth-token") String token, String arguments) {

        LOGGER.info("@generarReporte: Inicio");

        //Validamos acceso: si deniedAccessResponse != null => Acceso denegado
        Response deniedAccessResponse = accessService.validateAccessToAPI(token);
        if (null != deniedAccessResponse)
            return deniedAccessResponse;

        Response response = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map;
            // convert JSON string to Map
            map = mapper.readValue(arguments, new TypeReference<Map<String, Object>>() {
            });

            byte[] byteArray = predefinidoSercvice.descargarReporte(map);
            if (null == byteArray)
                return Response.status(Status.NO_CONTENT.getStatusCode())
                        .entity("NO CONTENT").build();

            ResponseBuilder responseBuilder = Response.ok(Base64.encodeBase64(byteArray))
                    .header("Content-Disposition", "attachment; filename=reporte.pdf");
            response = responseBuilder.build();

        } catch (GlosaNetException | IOException e) {
            e.printStackTrace();
            LOGGER.severe("@generarReporte: exception -> " + e.toString());
        }

        return response;
    }

    @Path("/reporteContribuciones")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response reporteContribuciones(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                List<DetalleContribucionesBO> reporte = predefinidoSercvice.reporteDetalleContribuciones(parametros);
                GenericEntity<List<DetalleContribucionesBO>> entity = new GenericEntity<List<DetalleContribucionesBO>>(reporte) {
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

    @Path("/reporteContribucionesDescarga")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response reporteContribucionesDescarga(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                ByteArrayOutputStream reporte = predefinidoSercvice.reporteContribucionesExcel(parametros);

                if (null == reporte)
                    return Response.status(Status.NO_CONTENT.getStatusCode())
                            .entity("NO CONTENT").build();

                ResponseBuilder responseBuilder = Response.ok(Base64.encodeBase64(reporte.toByteArray()))
                        .header("Content-Disposition", "attachment; filename=reporte_" + parametros.getYear() + ".xls");

                return responseBuilder.build();
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

    @Path("/reportePartidas")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response reportePartidas(@HeaderParam("x-auth-token") String token, ParametrosPartida parametros) throws Exception {
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
                List<DetallePartidasBO> reporte = predefinidoSercvice.reporteDetallePartidas(parametros);
                GenericEntity<List<DetallePartidasBO>> entity = new GenericEntity<List<DetallePartidasBO>>(reporte) {
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

    @Path("/reportePartidasDescarga")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response reportePartidasDescarga(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {
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
                ByteArrayOutputStream reporte = predefinidoSercvice.reportePartidasExcel(parametros);

                if (null == reporte)
                    return Response.status(Status.NO_CONTENT.getStatusCode())
                            .entity("NO CONTENT").build();

                ResponseBuilder responseBuilder = Response.ok(Base64.encodeBase64(reporte.toByteArray()))
                        .header("Content-Disposition", "attachment; filename=reporte_" + parametros.getYear() + ".xls");

                return responseBuilder.build();
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
