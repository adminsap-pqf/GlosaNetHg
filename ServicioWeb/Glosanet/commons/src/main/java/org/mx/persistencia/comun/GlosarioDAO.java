package org.mx.persistencia.comun;

import java.util.List;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.ApendiceBO;
import org.mx.modelo.comun.bo.ApendiceContenidoBO;
import org.mx.modelo.comun.bo.TratadoBO;
import org.mx.modelo.comun.bo.TratadoContenidoBO;
import org.mx.modelo.comun.bo.RegionesBO;
import org.mx.modelo.comun.bo.RegionContenidoBO;

public interface GlosarioDAO {

	/**
	 * 
	 * @return
	 * @throws GlosaNetException
	 */
	public List<ApendiceBO> getApendices() throws GlosaNetException;

	/**
	 * 
	 * @param idApendice
	 * @return
	 * @throws GlosaNetException
	 */
	public List<ApendiceContenidoBO> getApendicesContenido(int idApendice, int orden)
			throws GlosaNetException;

	/**
	 * 
	 * @return
	 * @throws GlosaNetException
	 */
	public List<TratadoBO> getTratados() throws GlosaNetException;

	/**
	 * 
	 * @param idTratado
	 * @return
	 * @throws GlosaNetException
	 */
	public List<TratadoContenidoBO> getTratadoContenido(int idTratado)
			throws GlosaNetException;
	
	public List<RegionesBO> getRegiones() throws GlosaNetException;
	
	public List<RegionContenidoBO> getRegionContenido(int idRegion) throws GlosaNetException;
	
	
	public String getNombreClave(String columna, String columnaClave, String tabla, int apendice, String clave) throws GlosaNetException;

}
