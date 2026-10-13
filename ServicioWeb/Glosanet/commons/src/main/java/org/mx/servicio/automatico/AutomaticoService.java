package org.mx.servicio.automatico;

import java.util.Map;

import org.mx.modelo.Exception.GlosaNetException;

public interface AutomaticoService {

	/***
	 * 
	 * @return
	 * @throws GlosaNetException
	 */
	public Boolean borrarArchivosTemporales() throws GlosaNetException;

	/***
	 * 
	 * @return
	 * @throws GlosaNetException
	 */
	public Boolean crearReportesDescargas() throws GlosaNetException;
	
	/***
	 * 
	 * @param mensaje
	 * @param tipoMensaje
	 * @return
	 * @throws GlosaNetException
	 */
	public Boolean enviarMensajeVista(String mensaje, String tipoMensaje)
			throws GlosaNetException;

	/***
	 * 
	 * @return
	 * @throws GlosaNetException
	 */
	public Boolean crearReportes_DICIPA() throws GlosaNetException;

	/***
	 * 
	 * @param mensaje
	 * @param idDescarga
	 * @return
	 * @throws GlosaNetException
	 */
	public Boolean enviarMensajeVista(Map<String, Object> mensaje, int idDescarga)
			throws GlosaNetException;

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean verificarDataStage() throws Exception;
	
	public boolean enviarRecordatoriosRegistros() throws GlosaNetException;

}
