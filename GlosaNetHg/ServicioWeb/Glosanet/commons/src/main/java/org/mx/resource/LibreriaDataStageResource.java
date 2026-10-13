package org.mx.resource;

import org.apache.commons.codec.binary.Base64;
import org.jose4j.lang.JoseException;
import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.MediaContent;
import org.mx.modelo.comun.bo.*;
import org.mx.modelo.datastage.bo.DatosGeneralesBO;
import org.mx.servicio.cargasAdicionales.CargasAdicionalesService;
import org.mx.servicio.comun.AccessService;
import org.mx.servicio.comun.LibreriaDataStageService;
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
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.logging.Logger;

@Component
@Path("libreriaDataStage")
@CrossOrigin
public class LibreriaDataStageResource {

    private final static Logger LOGGER = Logger.getLogger(LibreriaDataStageResource.class.getName());

    @Autowired
    LibreriaDataStageService libreriaDataStageService;
    @Autowired
    LlaveTokenService llaveService;
    @Autowired
    private AccessService accessService;
    @Autowired
    CargasAdicionalesService cargasAdicionalesService;

    @Path("/navegacionDataStage")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response navegacionDataStage(@HeaderParam("x-auth-token") String token, Parametros parametros) throws Exception {

        LOGGER.info("@navegacionDataStage: Inicio");

        Response deniedAccessResponse = accessService.validateAccessToAPI(token);
        if (null != deniedAccessResponse)
            return deniedAccessResponse;

        List<DatosGeneralesBO> lstDatosGenerales;
        try {
            lstDatosGenerales = libreriaDataStageService.navegacionDataStage(
                    parametros, parametros.getUsuario());
        } catch (GlosaNetException e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
                    .entity(e).build();
        }

        GenericEntity<List<DatosGeneralesBO>> entity =
                new GenericEntity<List<DatosGeneralesBO>>(lstDatosGenerales) {
                };
        return Response.ok(entity).build();
    }

    @Path("/periodoDatosPorCliente")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response periodoDatosPorCliente(@HeaderParam("x-auth-token") String token, ClientesBO cliente) throws Exception {
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
                DatosGeneralesBO datosGenerales = libreriaDataStageService.periodoDatosPorCliente(cliente);
                return Response.status(200).entity(datosGenerales).build();
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

    @Path("/obtenerPeriodoDatosPorEmpresa")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPeriodoDatosPorEmpresa(@HeaderParam("x-auth-token") String token, EmpresaBO empresa) throws Exception {
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
                DatosGeneralesBO datosGenerales = libreriaDataStageService.obtenerPeriodoDatosPorEmpresa(empresa);
                return Response.status(200).entity(datosGenerales).build();
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

    @Path("/generarZip")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response generarZip(@HeaderParam("x-auth-token") String token, Parametros parametros) {

        LOGGER.info("@generarZip: Inicio");

        Response deniedAccessResponse = accessService.validateAccessToAPI(token);
        if (null != deniedAccessResponse)
            return deniedAccessResponse;

        String respuesta = null;
        try {
            respuesta = libreriaDataStageService.generarZip(parametros);
            if (null != respuesta)
                LOGGER.info("@obtenerLigasDescargasReportes: ya obtuve la liga");

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.severe("@generarZip: exception -> " + e.toString());
        }

        return Response.ok(new MediaContent("text", respuesta)).build();
    }

    @Path("/obtenerArchivo")
    @POST
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response obtenerArchivo(@HeaderParam("x-auth-token") String token, Parametros parametros) {

        LOGGER.info("@obtenerArchivo: Inicio");
        LOGGER.info("@obtenerArchivo: "+parametros.isFlagAdicionales());


        Response deniedAccessResponse = accessService.validateAccessToAPI(token);
        if (null != deniedAccessResponse) {
            return deniedAccessResponse;
        }
        if (parametros.isFlagAdicionales()) {
            LOGGER.info("@obtenerArchivo: incluye adicionales");

            cargasAdicionalesService.comprimirArchivos(parametros.getArchivos()[0].replaceAll("[0-9]*_Adicionales\\.zip",""),parametros.getNombres()[0].replaceAll("_Adicionales\\.zip","") );
        }

        Response response = null;
        try {
            ByteArrayOutputStream buffer = libreriaDataStageService.obtenerArchivo(parametros);

            if (null == buffer)
                return Response.status(Status.NO_CONTENT.getStatusCode())
                        .entity("NO CONTENT").build();

            ResponseBuilder responseBuilder = Response.ok(Base64.encodeBase64(buffer.toByteArray()))
                    .header("Content-Disposition", "attachment; filename=" + parametros.getNombres()[0]);
            response = responseBuilder.build();


        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.severe("@obtenerMatriz: exception -> " + e.toString());
        }

        return response;
    }

    @Path("/CargasAdicionales/request-adicional")
    @POST
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM)
    public Response obtenerrArchivoHistorial(@HeaderParam("x-auth-token") String token,
                                             Parametros parametros
    ) {

       // log.info("@obtenerrArchivoHistorial: Inicio");

        //Validamos acceso: si deniedAccessResponse != null => Acceso denegado



        Response response = null;
        try {
           String path = parametros.getIdCliente() + "/" + parametros.getRfc() + "/CargasAdicionales/" + parametros.getFolioDataStage() + "/" + parametros.getIdPedimento() + "/"+parametros.getNombreArchivo();
            //String path ="C:\\Users\\Fernando Betanzos\\Downloads\\" +parametros.getIdCliente() + "\\" + parametros.getRfc() + "\\CargasAdicionales\\" + parametros.getFolioDataStage() + "\\" + parametros.getIdPedimento() + "\\"+parametros.getNombreArchivo();
            ByteArrayOutputStream buffer = cargasAdicionalesService.obtenerrArchivo(path);
            if (null == buffer)
                return Response.status(Response.Status.NO_CONTENT.getStatusCode())
                        .entity("NO CONTENT").build();

            Response.ResponseBuilder responseBuilder = Response.ok(Base64.encodeBase64(buffer.toByteArray()))
                    .header("Content-Disposition", "attachment; filename=" + parametros.getNombreArchivo());
            response = responseBuilder.build();

        } catch (GlosaNetException e) {
            e.printStackTrace();
            //log.severe("@obtenerrArchivoHistorial: exception -> " + e.toString());
        }

        return response;
    }

}
