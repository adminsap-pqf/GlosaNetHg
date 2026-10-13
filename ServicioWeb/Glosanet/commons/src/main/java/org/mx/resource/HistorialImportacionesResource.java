package org.mx.resource;

import org.jose4j.lang.JoseException;
import org.mx.modelo.comun.bo.HistorialImportacionesBO;
import org.mx.modelo.comun.bo.Parametros;
import org.mx.modelo.comun.bo.StatusMessage;
import org.mx.modelo.comun.bo.UsuarioBO;
import org.mx.servicio.comun.HistorialImportacionesService;
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
import javax.ws.rs.core.Response.Status;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@Component
@Path("historialImportaciones")
@CrossOrigin
public class HistorialImportacionesResource {

    @Autowired
    HistorialImportacionesService historialImportacionesService;

    @Autowired
    LlaveTokenService llaveService;

    StatusMessage statusMessage = new StatusMessage();

    @Path("/obtenerHistorialImportaciones")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerHistorialImportaciones(@HeaderParam("x-auth-token") String token, Parametros p) throws Exception {

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
                int id = p.getIdproducto();
                boolean flag = p.isFlagProductoPartidas();
                statusMessage.setMessage("Dentro del metodo obtener historial importaciones");
                List<HistorialImportacionesBO> lstResult = historialImportacionesService.obtenerHistorialImportaciones(id, flag);
                GenericEntity<List<HistorialImportacionesBO>> entity = new GenericEntity<List<HistorialImportacionesBO>>(lstResult) {
                };
                //return Response.status(Status.OK.getStatusCode()).entity(statusMessage).build();
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


    @Path("/registrarImportacion")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarImportacion(@HeaderParam("x-auth-token") String token, HistorialImportacionesBO bo) throws Exception {

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
                Calendar c1 = new GregorianCalendar();
                String dia1 = Integer.toString(c1.get(Calendar.DATE));
                String me2 = Integer.toString(c1.get(Calendar.MONTH) + 1);
                String annio = Integer.toString(c1.get(Calendar.YEAR));
                String fecha = annio + "-" + me2 + "-" + dia1;
                bo.setFecha(fecha);
                boolean insercion = historialImportacionesService.insertarHistorialImportacion(bo);
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


    //TODO: verificar por qué ya no se usa este endpoint
    /*@Path("/obtenerPermisos")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarImportacion(@HeaderParam("x-auth-token") String token, Parametros p) throws Exception {

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
                List<HistorialPermisosBO> lstResult = historialImportacionesService.obtenerPermisos(p);
                GenericEntity<List<HistorialPermisosBO>> entity = new GenericEntity<List<HistorialPermisosBO>>(lstResult) {
                };
                return Response.status(Status.OK.getStatusCode()).entity(statusMessage).build();
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
    }*/


}
