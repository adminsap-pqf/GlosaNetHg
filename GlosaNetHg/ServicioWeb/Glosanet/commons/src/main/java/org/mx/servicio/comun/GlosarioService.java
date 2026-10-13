package org.mx.servicio.comun;

import java.util.List;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.ApendiceBO;
import org.mx.modelo.comun.bo.ApendiceContenidoBO;
import org.mx.modelo.comun.bo.RegionContenidoBO;
import org.mx.modelo.comun.bo.RegionesBO;
import org.mx.modelo.comun.bo.TratadoBO;
import org.mx.modelo.comun.bo.TratadoContenidoBO;

public interface GlosarioService {

	/***
	 * 
	 * @return
	 * @throws GlosaNetException
	 */
	public List<ApendiceBO> obtenerApendices() throws GlosaNetException;

	/***
	 * 
	 * @param idApendice
	 * @param orden
	 * @return
	 * @throws GlosaNetException
	 */
	public List<ApendiceContenidoBO> obtenerApendiceContenido(int idApendice, int orden)
			throws GlosaNetException;

	/**
	 * 
	 * @return
	 * @throws GlosaNetException
	 */
	public List<TratadoBO> obtenerTratados() throws GlosaNetException;

	/**
	 * 
	 * @param idApendice
	 * @return
	 * @throws GlosaNetException
	 */
	public List<TratadoContenidoBO> obtenerTratadoContenido(int idApendice) throws GlosaNetException;	
	/***
	 * 
	 * @return
	 * @throws GlosaNetException
	 */
	public List<RegionesBO> obtenerRegiones() throws GlosaNetException;
	/***
	 * 
	 * @param idApendice
	 * @return
	 * @throws GlosaNetException
	 */
	public List<RegionContenidoBO> obtenerRegionContenido(int idApendice) throws GlosaNetException;
	/***
	 * Obtiene todos los paises
	 * @return
	 * @throws GlosaNetException
	 */
	public List<String> obtenerPaises() throws GlosaNetException;

}
