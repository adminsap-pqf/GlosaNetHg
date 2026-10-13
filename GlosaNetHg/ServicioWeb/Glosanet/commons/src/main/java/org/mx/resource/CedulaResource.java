package org.mx.resource;

import java.util.List;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jose4j.lang.JoseException;
import org.mx.modelo.comun.bo.CedulaBO;
import org.mx.modelo.comun.bo.Parametros;
import org.mx.modelo.comun.bo.StatusMessage;
import org.mx.modelo.comun.bo.UsuarioBO;
import org.mx.servicio.comun.CedulaService;
import org.mx.servicio.comun.LlaveTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;


@Component
@Path("cedula")
@CrossOrigin
public class CedulaResource {
	@Autowired
	private CedulaService cedulaService;
	@Autowired
	private LlaveTokenService llaveService;
	
	@Path("/totalProductos")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response totalProductos(@HeaderParam("x-auth-token") String token, Parametros p)throws Exception {
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
			if(u != null){
				idUsuario = u.getIdUsuario();
			}
			if( idUsuario > 0){
				List<CedulaBO> result = cedulaService.totalProductos(p.getId(), p.getFechaInicio(), p.getFechaFin());
				GenericEntity<List<CedulaBO>> entity = new GenericEntity<List<CedulaBO>>(result){};
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
	
	@Path("/fraccionOrigen")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response fraccionOrigen(@HeaderParam("x-auth-token") String token, Parametros p)throws Exception {
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
			if(u != null){
				idUsuario = u.getIdUsuario();
			}
			if( idUsuario > 0){
				List<CedulaBO> result = cedulaService.fraccionOrigen(p.getId(), p.getFechaInicio(), p.getFechaFin());
				GenericEntity<List<CedulaBO>> entity = new GenericEntity<List<CedulaBO>>(result){};
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
	
	@Path("/totalContribuciones")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response totalContribuciones(@HeaderParam("x-auth-token") String token, Parametros p)throws Exception {
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
			if(u != null){
				idUsuario = u.getIdUsuario();
			}
			if( idUsuario > 0){
				List<CedulaBO> result = cedulaService.totalContribuciones(p.getId(), p.getFechaInicio(), p.getFechaFin());
				GenericEntity<List<CedulaBO>> entity = new GenericEntity<List<CedulaBO>>(result){};
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
	
	@Path("/resumenContribuciones")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response resumenContribuciones(@HeaderParam("x-auth-token") String token, Parametros p)throws Exception {
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
			if(u != null){
				idUsuario = u.getIdUsuario();
			}
			if( idUsuario > 0){
				List<CedulaBO> result = cedulaService.resumenContribuciones(p.getId(), p.getFechaInicio(), p.getFechaFin());
				GenericEntity<List<CedulaBO>> entity = new GenericEntity<List<CedulaBO>>(result){};
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
	
	@Path("/incoterms")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response incoterms(@HeaderParam("x-auth-token") String token, Parametros p)throws Exception {
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
			if(u != null){
				idUsuario = u.getIdUsuario();
			}
			if( idUsuario > 0){
				List<CedulaBO> result = cedulaService.incoterms(p.getId(), p.getFechaInicio(), p.getFechaFin());
				GenericEntity<List<CedulaBO>> entity = new GenericEntity<List<CedulaBO>>(result){};
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
	
	@Path("/facturacionExw")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response facturacionExw(@HeaderParam("x-auth-token") String token, Parametros p)throws Exception {
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
			if(u != null){
				idUsuario = u.getIdUsuario();
			}
			if( idUsuario > 0){
				List<CedulaBO> result = cedulaService.facturacionExw(p.getId(), p.getFechaInicio(), p.getFechaFin());
				GenericEntity<List<CedulaBO>> entity = new GenericEntity<List<CedulaBO>>(result){};
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
