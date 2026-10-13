package org.mx.persistencia.comun;

import java.io.File;
import java.util.List;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.ClientesBO;
import org.mx.modelo.comun.bo.EmpresaBO;
import org.mx.modelo.comun.bo.Parametros;
import org.mx.modelo.comun.bo.UsuarioBO;
import org.mx.modelo.datastage.bo.DatosGeneralesBO;

public interface LibreriaDataStageDAO extends DataBaseDAO<DatosGeneralesBO>{

	/***
	 * 
	 * @param list
	 * @return
	 */
//	public boolean guardar(List<DatosGeneralesBO> list);

	/***
	 * 
	 * @param file
	 * @return
	 */
	public boolean guardarArchivo(File file);

	/***
	 * 
	 * @param parametros
	 * @param usuario
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DatosGeneralesBO> navegacionDataStage(Parametros parametros, UsuarioBO usuario) throws GlosaNetException;

	/***
	 * Obtiene el periodo (finicio,ffin) que abarca la informaci??n ingresada en el sistema, de todas las empresas del cliente
	 * @param cliente
	 * @return
	 * @throws GlosaNetException
	 */
	public DatosGeneralesBO periodoDatosPorCliente(ClientesBO cliente) throws GlosaNetException;

	/***
	 * Obtiene el periodo (finicio,ffin) que abarca la informaci??n ingresada en el sistema, de una empresa
	 * @param cliente
	 * @return
	 * @throws GlosaNetException
	 */
	public DatosGeneralesBO getPeriodoDatosPorEmpresa(EmpresaBO empresa) throws GlosaNetException;

	/***
	 * Obtiene el periodo (finicio,ffin) que abarca la informaci??n ingresada en el sistema, de una empresa
	 * @param cliente
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DatosGeneralesBO> findPeriodoDatosPorCliente(ClientesBO cliente) throws GlosaNetException;

	/***
	 * 
	 * Metodo que inserta las patentes que faltan en la tabla de agentes aduanales
	 * @throws GlosaNetException
	 */
	public void insertPatentesFaltantes() throws GlosaNetException;
}
