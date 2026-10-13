package org.mx.persistencia.comun;

import java.io.File;
import java.util.List;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.ClientesBO;
import org.mx.modelo.comun.bo.EmpresaBO;
import org.mx.modelo.comun.bo.UsuarioBO;
import org.mx.modelo.datastage.bo.DataStageBO;
import org.mx.modelo.datastage.bo.ResumenBO;

public interface DataStageDAO {

	/***
	 * 
	 * @param empresa
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DataStageBO> obtenerDatasStagePorEmpresa(EmpresaBO empresa)
			throws GlosaNetException;

	/**
	 * 
	 * @param resumen
	 * @param usuario
	 * @return
	 */
	public DataStageBO guardar(ResumenBO resumen, UsuarioBO usuario);

	/**
	 * 
	 * @param cliente
	 * @return
	 * @throws GlosaNetException
	 */
	public List<DataStageBO> obtenerDatasStagePorCliente(ClientesBO cliente)
			throws GlosaNetException;

	/**
	 * 
	 * @param file
	 * @return
	 */
	public boolean loadDataLocal(File file);

}
