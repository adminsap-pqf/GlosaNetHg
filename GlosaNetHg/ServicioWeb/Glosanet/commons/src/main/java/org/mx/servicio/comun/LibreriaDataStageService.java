package org.mx.servicio.comun;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.ClientesBO;
import org.mx.modelo.comun.bo.EmpresaBO;
import org.mx.modelo.comun.bo.Parametros;
import org.mx.modelo.comun.bo.UsuarioBO;
import org.mx.modelo.datastage.bo.DatosGeneralesBO;

public interface LibreriaDataStageService {

	/***
	 * 
	 * @param parametros
	 * @param usuario
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DatosGeneralesBO> navegacionDataStage(Parametros parametros, UsuarioBO usuario) throws GlosaNetException;
	
	/***
	 * 
	 * @param cliente
	 * @return
	 * @throws GlosaNetException
	 */
	public DatosGeneralesBO periodoDatosPorCliente(ClientesBO cliente) throws GlosaNetException;
	
	/***
	 * Obtiene la fecha inicio y fecha fin histirica de los datasStage de la empresa
	 * @param empresa
	 * @return
	 * @throws GlosaNetException
	 */
	public DatosGeneralesBO obtenerPeriodoDatosPorEmpresa(EmpresaBO empresa) throws GlosaNetException;

	/***
	 * 
	 * Metodo que inserta las patentes que faltan en la tabla de agentes aduanales
	 * @throws GlosaNetException
	 */
	public void ingresarPatentesFaltantes() throws GlosaNetException;

	/***
	 * 
	 * @param parametros
	 * @return
	 * @throws Exception
	 */
	public String generarZip(Parametros parametros) throws Exception;

	/***
	 * 
	 * @param parametros
	 * @return
	 * @throws Exception
	 */
	public ByteArrayOutputStream obtenerArchivo(Parametros parametros) throws Exception;
}
