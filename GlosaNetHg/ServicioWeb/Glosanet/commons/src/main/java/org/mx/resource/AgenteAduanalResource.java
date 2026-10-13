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
import org.mx.modelo.comun.bo.AgenciaAduanalBO;
import org.mx.modelo.comun.bo.AgenteAduanalBO;
import org.mx.modelo.comun.bo.Parametros;
import org.mx.modelo.comun.bo.StatusMessage;
import org.mx.modelo.comun.bo.UsuarioBO;
import org.mx.servicio.comun.AgenteAduanalService;
import org.mx.servicio.comun.LlaveTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

@Component
@Path("agenteAduanal")
@CrossOrigin
public class AgenteAduanalResource {
	@Autowired
	AgenteAduanalService agenteAduanalService;
	@Autowired
	LlaveTokenService llaveService;

	@Path("/actualizarDatosAgenteAduanal")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response actualizarDatosAgenteAduanal(@HeaderParam("x-auth-token") String token, AgenteAduanalBO bo)throws Exception {
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
				boolean result = agenteAduanalService.actualizarDatosAgenteAduanal(bo);
				GenericEntity<Boolean> entity = new GenericEntity<Boolean>(result){};
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

	@Path("/obtenerCantidadAgentesSinInformacion")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerCantidadAgentesSinInformacion(@HeaderParam("x-auth-token") String token, Parametros p)throws Exception {
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
				String result = agenteAduanalService.obtenerCantidadAgentesSinInformacion(p.getId()) +"";
				return  Response.status(200).entity(result).build();
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

	@Path("/obtenerAgentesYAgencias")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerAgentesYAgencias(@HeaderParam("x-auth-token") String token, Parametros p)throws Exception {
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
				int idCliente = p.getId();
				boolean informacion = p.isAuxBoolean();
				List<AgenteAduanalBO> lstResult = agenteAduanalService.obtenerAgentesYAgencias(idCliente, informacion);
				GenericEntity<List<AgenteAduanalBO>> entity = new GenericEntity<List<AgenteAduanalBO>>(lstResult){};
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

	@Path("/actualizarAgenteAduanal")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response actualizarAgenteAduanal(@HeaderParam("x-auth-token") String token, AgenteAduanalBO bo)throws Exception {
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
				boolean lstResult = agenteAduanalService.actualizarAgenteAduanal(bo);
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

	@Path("/agregarAgenciaAduanal")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response agregarAgenciaAduanal(@HeaderParam("x-auth-token") String token, AgenciaAduanalBO bo)throws Exception {
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
				boolean lstResult = agenteAduanalService.agregarAgenciaAduanal(bo);
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

	@Path("/actualizarAgenciaAduanal")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response actualizarAgenciaAduanal(@HeaderParam("x-auth-token") String token, AgenciaAduanalBO bo)throws Exception {
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
				boolean lstResult = agenteAduanalService.actualizarAgenciaAduanal(bo);
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
}
