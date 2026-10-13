package org.mx.servicio.scheduler;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.logging.Logger;

public class NotificacionProgramada extends QuartzJobBean{

	private final static Logger LOGGER = Logger.getLogger(ProgramacionDICIPA.class.getName());

	private TareaGLOSANET tareaGLOSANET;

	public void setTareaGLOSANET(TareaGLOSANET tareaGLOSANET) {
		this.tareaGLOSANET = tareaGLOSANET;
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {

		LOGGER.info("Inicia envio de notificaciones");

		try {
			tareaGLOSANET.ejecutaProgramacionNotificaciones();
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}
}
