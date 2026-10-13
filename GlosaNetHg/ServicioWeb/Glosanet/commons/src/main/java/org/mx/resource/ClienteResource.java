package org.mx.resource;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jose4j.lang.JoseException;
import org.mx.modelo.comun.bo.ClientesBO;
import org.mx.modelo.comun.bo.StatusMessage;
import org.mx.modelo.comun.bo.UsuarioBO;
import org.mx.servicio.comun.ClienteService;
import org.mx.servicio.comun.LlaveTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

@Component
@Path("cliente")
@CrossOrigin
public class ClienteResource {

	@Autowired
	LlaveTokenService llaveService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Path("/actualizarCliente/")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response actualizarCliente(@HeaderParam("x-auth-token") String token, ClientesBO cliente) throws Exception{
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
				if(clienteService.actualizarCliente(idUsuario, cliente)){
					return Response.status(200).entity(cliente).build();
				}else{
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
}
