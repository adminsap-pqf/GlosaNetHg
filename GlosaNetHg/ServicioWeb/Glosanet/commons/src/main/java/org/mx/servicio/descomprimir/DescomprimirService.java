package org.mx.servicio.descomprimir;

import java.io.File;
import java.util.Map;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.ClientesBO;
import org.mx.modelo.comun.bo.FileZip;
import org.mx.modelo.comun.bo.UsuarioBO;


public interface DescomprimirService {


	/**
	 * @param dataStage
	 * @param nombreFile
	 * @param idUsuario
	 * @param idCliente
	 * @param rfcActual
	 * @return
	 * @throws GlosaNetException
	 */
	public String subirDataStage(File dataStage, String nombreFile, int idUsuario, int idCliente, String rfcActual)
			throws GlosaNetException;

	/***
	 * 
	 * @param idPeticion
	 * @return
	 * @throws GlosaNetException
	 */
	public String obtenerDataStageRespuesta(Integer idPeticion)
			throws GlosaNetException;

	/***
	 * 
	 * @param fileZ
	 * @param usuario
	 * @return
	 * @throws GlosaNetException
	 */
	public Map<String, Object> extractFile(FileZip fileZ, UsuarioBO usuario)
			throws GlosaNetException;

	public String getRutaDataStageArchivos();
	public void setRutaDataStageArchivos(String rutaDataStageArchivos);
	public ClientesBO getLogginCliente();
	public void setLogginCliente(org.mx.modelo.comun.bo.ClientesBO logginCliente);
	public UsuarioBO getUsuarioLoggin();
	public void setUsuarioLoggin(UsuarioBO usuarioLoggin);
	public int uploadFile(File file) throws GlosaNetException;
}
