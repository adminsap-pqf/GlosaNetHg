package org.mx.servicio.scheduler;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.logging.Logger;

public class ProgramacionCOFEPRIS extends QuartzJobBean{

	private final static Logger LOGGER = Logger.getLogger(ProgramacionCOFEPRIS.class.getName());

	private TareaGLOSANET tareaGLOSANET;

	public void setTareaGLOSANET(TareaGLOSANET tareaGLOSANET) {
		this.tareaGLOSANET = tareaGLOSANET;
	}
	
	@SuppressWarnings("unused")
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {

		LOGGER.info("Inicia envio de notificaciones TRAMITE COFEPRIS");

		try {
			tareaGLOSANET.ejecutaProgramacionCOFEPRIS();
		} catch (Exception e) {
			e.printStackTrace();
		}

//		System.out.println("-------------------------------------------------- Day of week prueba --------------------------------------------------");
//		Calendar calendarF = Calendar.getInstance();
//		calendarF.setTime(new Date(2018, 5, 17));
//		Calendar calendar = new GregorianCalendar(2018,5,16);
//		System.out.println(calendar.get(Calendar.DAY_OF_WEEK));
//		System.out.println(calendar.getTime());

	}
}
