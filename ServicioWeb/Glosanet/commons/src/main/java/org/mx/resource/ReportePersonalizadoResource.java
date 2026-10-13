package org.mx.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jose4j.lang.JoseException;
import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.MediaContent;
import org.mx.modelo.comun.bo.Parametros;
import org.mx.modelo.comun.bo.StatusMessage;
import org.mx.modelo.comun.bo.UsuarioBO;
import org.mx.modelo.reportes.bo.CampoTablaBO;
import org.mx.modelo.reportes.bo.ReportePersonalizadoBO;
import org.mx.modelo.reportes.bo.TablaDataStageBO;
import org.mx.servicio.comun.AccessService;
import org.mx.servicio.comun.LlaveTokenService;
import org.mx.servicio.comun.impl.AccessServiceImpl;
import org.mx.servicio.reporte.PersonalizadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

@Component
@Path("reportePersonalizado")
@CrossOrigin
public class ReportePersonalizadoResource {

	private final static Logger LOGGER = Logger.getLogger(ReportePersonalizadoResource.class.getName());

	@Autowired
	private PersonalizadoService personalizadoService;
	@Autowired
	private LlaveTokenService llaveService;
	@Autowired
	private AccessService accessService;
	
	@Path("/obtenerTablasDataStage")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerTablasDataStage(@HeaderParam("x-auth-token") String token, Parametros p)throws Exception {
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
				List<TablaDataStageBO> lstTablaDataStage = personalizadoService.obtenerTablasDataStage(p.getIdioma(), p.getCondicion());
				GenericEntity<List<TablaDataStageBO>> entity = new GenericEntity<List<TablaDataStageBO>>(lstTablaDataStage){};
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
	
	@SuppressWarnings("unchecked")
	@Path("/obtenerResultadosDeReportePersonalizado")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerResultadosDeReportePersonalizado(@HeaderParam("x-auth-token") String token, Parametros p)throws Exception {
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
				List<CampoTablaBO> campos = (List<CampoTablaBO>)(Object)p.getLstObjects();
				List<String> lstRReportePersonalizado = personalizadoService.obtenerResultadosDeReportePersonalizado(campos, p);
				GenericEntity<List<String>> entity = new GenericEntity<List<String>>(lstRReportePersonalizado){};
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
	
	@Path("/peticionResultadosDeReportePersonalizado")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response peticionResultadosDeReportePersonalizado(@HeaderParam("x-auth-token") String token, Parametros p)throws Exception {
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
				List<CampoTablaBO> campos = p.getLstObjects();
				Integer respuesta = personalizadoService.peticionResultadosDeReportePersonalizado(campos, p);
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
	
	@Path("/obtenerLigasDescargasReportes")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerLigasDescargasReportes(@HeaderParam("x-auth-token") String token, Parametros p)
			{

		LOGGER.info("@obtenerLigasDescargasReportes: Inicio");

		Response deniedAccessResponse = accessService.validateAccessToAPI(token);
		if (null != deniedAccessResponse)
			return deniedAccessResponse;

		String respuesta = null;
		try {
				respuesta = personalizadoService.obtenerLigasDescargasReportes(p.getId());
				if (null != respuesta)
					LOGGER.info("@obtenerLigasDescargasReportes: ya obtuve la liga");

		} catch (GlosaNetException e) {
			e.printStackTrace();
			LOGGER.severe("@obtenerLigasDescargasReportes: exception -> " + e.toString());
		}

		return Response.ok(new MediaContent("text", respuesta)).build();
	}
	
	@Path("/guardarReporte")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response guardarReporte(@HeaderParam("x-auth-token") String token, ReportePersonalizadoBO rp)throws Exception {
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
			if(idUsuario > 0){
				Integer respuesta = personalizadoService.guardarReporte(rp);
				return Response.status(200).entity(respuesta + "").build();
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
	
	@Path("/borrarReporte")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response borrarReporte(@HeaderParam("x-auth-token") String token, ReportePersonalizadoBO rp)throws Exception {
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
			if(idUsuario > 0){
				Boolean respuesta = personalizadoService.borrarReporte(	rp);
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
	
	@Path("/obtenerReportes")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerReportes(@HeaderParam("x-auth-token") String token)throws Exception {
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
			if(idUsuario > 0){
				List<ReportePersonalizadoBO> respuesta = personalizadoService.obtenerReportes(idUsuario);
				if(respuesta == null){
					respuesta = new ArrayList<ReportePersonalizadoBO>();
				}
				GenericEntity<List<ReportePersonalizadoBO>> entity = new GenericEntity<List<ReportePersonalizadoBO>>(respuesta){};
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
	
	@Path("/obtenerReportePorId")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerReportePorId(@HeaderParam("x-auth-token") String token, ReportePersonalizadoBO rp)throws Exception {
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
			if(idUsuario > 0){
				ReportePersonalizadoBO respuesta = personalizadoService.obtenerReportePorId(rp.getIdReportePersonalizado());
				GenericEntity<ReportePersonalizadoBO> entity = new GenericEntity<ReportePersonalizadoBO>(respuesta){};
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
