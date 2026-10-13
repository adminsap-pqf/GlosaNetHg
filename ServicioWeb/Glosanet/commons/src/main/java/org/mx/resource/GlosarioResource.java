package org.mx.resource;

import org.jose4j.lang.JoseException;
import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.*;
import org.mx.servicio.comun.AccessService;
import org.mx.servicio.comun.GlosarioService;
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
import java.util.List;
import java.util.logging.Logger;

@Component
@Path("glosario")
@CrossOrigin
public class GlosarioResource {

    private final static Logger LOGGER = Logger.getLogger(GlosarioResource.class.getName());

    @Autowired
    private LlaveTokenService llaveService;
    @Autowired
    private GlosarioService glosarioService;
    @Autowired
    private AccessService accessService;

    @Path("/obtenerApendices")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerApendices(@HeaderParam("x-auth-token") String token) {

		LOGGER.info("@obtenerApendices: Inicio");

        //Validamos acceso: si deniedAccessResponse != null => Acceso denegado
        Response deniedAccessResponse = accessService.validateAccessToAPI(token);
        if (null != deniedAccessResponse)
            return deniedAccessResponse;

        Response response = null;
        try {
            List<ApendiceBO> lstApendices = glosarioService.obtenerApendices();
            GenericEntity<List<ApendiceBO>> entity = new GenericEntity<List<ApendiceBO>>(lstApendices) {
            };
            response = Response.ok(entity).build();

        } catch (GlosaNetException e) {
            e.printStackTrace();
			LOGGER.severe("@obtenerApendices: exception -> " + e.toString());
        }

        return response;
    }

    @Path("/obtenerTratados")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerTratados(@HeaderParam("x-auth-token") String token) throws GlosaNetException {
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
                List<TratadoBO> lstTratados = glosarioService.obtenerTratados();
                GenericEntity<List<TratadoBO>> entity = new GenericEntity<List<TratadoBO>>(lstTratados) {
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

    @Path("/obtenerRegiones")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerRegiones(@HeaderParam("x-auth-token") String token) throws GlosaNetException {
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
                List<RegionesBO> lstRegiones = glosarioService.obtenerRegiones();
                GenericEntity<List<RegionesBO>> entity = new GenericEntity<List<RegionesBO>>(lstRegiones) {
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

    @Path("/obtenerApendiceContenido")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerApendiceContenido(@HeaderParam("x-auth-token") String token, Parametros p) throws GlosaNetException {
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
                List<ApendiceContenidoBO> lstApendices = glosarioService.obtenerApendiceContenido(p.getId(), p.getOrden());
                GenericEntity<List<ApendiceContenidoBO>> entity = new GenericEntity<List<ApendiceContenidoBO>>(lstApendices) {
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

    @Path("/obtenerTratadoContenido")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerTratadoContenido(@HeaderParam("x-auth-token") String token, Parametros p) throws GlosaNetException {
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
                List<TratadoContenidoBO> lstTratado = glosarioService.obtenerTratadoContenido(p.getId());
                GenericEntity<List<TratadoContenidoBO>> entity = new GenericEntity<List<TratadoContenidoBO>>(lstTratado) {
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


    @Path("/obtenerRegionContenido")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerRegionContenido(@HeaderParam("x-auth-token") String token, Parametros p) throws GlosaNetException {
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
                List<RegionContenidoBO> lstTratado = glosarioService.obtenerRegionContenido(p.getId());
                GenericEntity<List<RegionContenidoBO>> entity = new GenericEntity<List<RegionContenidoBO>>(lstTratado) {
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

    @Path("/obtenerPaises")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPaises(@HeaderParam("x-auth-token") String token) throws GlosaNetException {
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
                /***
                 * Se utiliza StatusMessage por que marcaba error:  MessageBodyWriter not found for media type=application/json, type=class java.util.ArrayList, genericType=java.util.List<java.lang.String>
                 */
                List<String> paises = glosarioService.obtenerPaises();
                statusMessage.setCurrent(paises);
                statusMessage.setStatus(Status.OK.getStatusCode());
                return Response.status(statusMessage.getStatus()).entity(statusMessage).build();
            }
        } catch (JoseException e) {
            e.printStackTrace();
        }

//		StatusMessage statusMessage = new StatusMessage();
        statusMessage.setStatus(Status.FORBIDDEN.getStatusCode());
        statusMessage.setMessage(
                "Access Denied for this functionality !!!");
        return Response.status(Status.FORBIDDEN.getStatusCode())
                .entity(statusMessage).build();
    }

}
