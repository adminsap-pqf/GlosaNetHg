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
import org.mx.modelo.comun.bo.Parametros;
import org.mx.modelo.comun.bo.StatusMessage;
import org.mx.modelo.comun.bo.UsuarioBO;
import org.mx.modelo.reportes.ReporteDICIPA_565;
import org.mx.servicio.automatico.AutomaticoService;
import org.mx.servicio.comun.LlaveTokenService;
import org.mx.servicio.reporte.AmedidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

@Component
@Path("reporteAmedida")
@CrossOrigin
public class ReporteAmedidaResource {
	@Autowired
	private LlaveTokenService llaveService;
	@Autowired
	private AmedidaService amedidaService;
	@Autowired
	private AutomaticoService automaticoService;
	
	@Path("/obtenerReporteMedidaMensualDICIPA_565")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerReporteMedidaMensualDICIPA_565(@HeaderParam("x-auth-token") String token, Parametros p)throws Exception {
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
				List<ReporteDICIPA_565> lstReporteAmedida = amedidaService.obtenerReporteMedidaMensualDICIPA_565(p);
				GenericEntity<List<ReporteDICIPA_565>> entity = new GenericEntity<List<ReporteDICIPA_565>>(lstReporteAmedida){};
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
	
	@Path("/peticionReporteMedidaMensualDICIPA_565")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response peticionReporteMedidaMensualDICIPA_565(@HeaderParam("x-auth-token") String token, Parametros p)throws Exception {
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
				Integer respuesta = amedidaService.peticionReporteMedidaMensualDICIPA_565(p);
				automaticoService.crearReportes_DICIPA();//genera el reporte de Dicipa 565
				return Response.status(200).entity(respuesta+"").build();
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
