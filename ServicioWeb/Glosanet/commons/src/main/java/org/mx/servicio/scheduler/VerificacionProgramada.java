package org.mx.servicio.scheduler;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.logging.Logger;

public class VerificacionProgramada extends QuartzJobBean{

	private final static Logger LOGGER = Logger.getLogger(ProgramacionDICIPA.class.getName());

	private TareaGLOSANET tareaGLOSANET;

	public void setTareaGLOSANET(TareaGLOSANET tareaGLOSANET) {
		this.tareaGLOSANET = tareaGLOSANET;
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		try {
			tareaGLOSANET.ejecutaVerificacion();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
