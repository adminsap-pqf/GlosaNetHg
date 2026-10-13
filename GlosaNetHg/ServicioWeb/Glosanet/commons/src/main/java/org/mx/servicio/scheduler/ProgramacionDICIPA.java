package org.mx.servicio.scheduler;

import org.mx.modelo.util.Util;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.logging.Logger;

public class ProgramacionDICIPA extends QuartzJobBean{

	private final static Logger LOGGER = Logger.getLogger(ProgramacionDICIPA.class.getName());

	private TareaGLOSANET tareaGLOSANET;

	public void setTareaGLOSANET(TareaGLOSANET tareaGLOSANET) {
		this.tareaGLOSANET = tareaGLOSANET;
	}

	@SuppressWarnings("unused")
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {

		LOGGER.info("@executeInternal: " + Util.termino);

		try {
			if(Util.termino){
				Util.termino = false;
				Util.termino = tareaGLOSANET.ejecutaProgramacionDICIPA();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
