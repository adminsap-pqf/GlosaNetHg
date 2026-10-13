package org.mx.persistencia.comun;

import java.util.Date;
import java.util.List;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.PedimentosBO;
import org.mx.modelo.comun.bo.RolBO;
import org.mx.modelo.comun.bo.UsuarioBO;
import org.mx.modelo.datastage.bo.UsuarioPermisosBO;
import org.mx.modelo.registros.bo.CorreoEventoBO;
import org.mx.modelo.registros.bo.EventoBO;


public interface UsuarioDAO {
	
	/***
	 * 
	 * @param usuario
	 * @return
	 */
	public UsuarioBO validateUsuario(String usuario);
	
	/***
	 * 
	 * @param idRol
	 * @return
	 */
	public String getNombreRol(int idRol);
	
	/***
	 * 
	 * @param tipo
	 * @param id
	 * @return
	 */
	public List<UsuarioBO> findUsuarios(String tipo, int id);
	
	/***
	 * 
	 * @return
	 */
	public List<UsuarioBO> findUsuariosCredito();
	
	/***
	 * 
	 * @return
	 */
	public int getNumUsuarios();
	
	/***
	 * 
	 * @return
	 */
	public List<RolBO> findRoles();
	
	/***
	 * 
	 * @param usuario
	 * @return
	 * @throws Exception
	 */
	public boolean updateCodigo(UsuarioBO usuario) throws Exception;
	
	/***
	 * 
	 * @param rol
	 * @return
	 */
	public RolBO getRol(String rol);

	/***
	 * 
	 * @param tipo
	 * @param id
	 * @return
	 */
	public List<UsuarioBO> findUsuariosActivos(String tipo, int id);
	
	/***
	 * 
	 * @return
	 * @throws Exception
	 */
	public int getTotalUsuarios() throws Exception;

	/***
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public UsuarioBO findUsuario(int id) throws Exception;

	/***
	 * 
	 * @param idEmpresa
	 * @return
	 * @throws GlosaNetException
	 */
	public Integer getNumeroDataStagePorEmpresa(Integer idEmpresa)
			throws GlosaNetException;

	/***
	 * 
	 * @param idCliente
	 * @return
	 * @throws GlosaNetException
	 */
	public List<UsuarioBO> obtenerUsuariosPorCliente(Integer idCliente)
			throws GlosaNetException;

	/***
	 * 
	 * @return
	 * @throws GlosaNetException
	 */
	public Date obtenerUltimaFecha() throws GlosaNetException;
	
	public boolean updateUsuario(UsuarioBO usuario) throws Exception;
	
	public boolean updatePermisos(UsuarioPermisosBO permisos) throws Exception;
	
	public PedimentosBO findPedimentos(int id, String periodo) throws Exception;
	
	
	
	
	
	public List<EventoBO> obtenerEventos(String tipo) throws Exception;
	
	public int saveNuevaNotificacion(int idEvento, Date finicio);
	
	public int saveNuevoCorreoNotificacion(int idEvento, String correo);
	
	public List<CorreoEventoBO> obtenerCorreos(int idEvento) throws Exception;

}
