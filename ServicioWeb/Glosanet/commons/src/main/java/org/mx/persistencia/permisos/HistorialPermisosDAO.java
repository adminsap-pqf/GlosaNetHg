package org.mx.persistencia.permisos;

import java.util.Date;
import java.util.List;

import org.mx.modelo.comun.bo.Parametros;
import org.mx.modelo.comun.bo.ProductoBO;
import org.mx.modelo.permisos.org.mx.bo.HistorialPermisosBO;
import org.mx.modelo.permisos.org.mx.bo.PermisoBO;
import org.mx.modelo.permisos.org.mx.bo.PermisoCorreoEventoBO;
import org.mx.modelo.permisos.org.mx.bo.PermisoEventoBO;
import org.mx.modelo.permisos.org.mx.bo.PermisoHistorialCofeprisBO;
import org.mx.modelo.permisos.org.mx.bo.PermisoHistorialEventoBO;
import org.mx.modelo.permisos.org.mx.bo.PermisoNotificacionBO;

public interface HistorialPermisosDAO {
	
	/**
	 * 
	 * @return
	 */
	public List<PermisoBO> obtenerPermisos ();
	/**
	 * 
	 * @param numPermiso
	 * @return
	 */
	public int obtenerPermiso(int numPermiso);
	/**
	 * 
	 * @param bo
	 * @return
	 */
	public boolean insertarPermisos(HistorialPermisosBO bo) throws Exception;
	
	/**
	 * 
	 */
	public List<PermisoBO> buscarPermisosEmpresa(int idEmpresa);
	/**
	 * 
	 * @param tramite
	 * @param estado
	 * @return
	 */
	public int saveHistorialCofepris(String tramite, String estado);
	/**
	 * 
	 * @param tramite
	 * @return
	 */
	public List<PermisoHistorialCofeprisBO> findPermisoHistorialCofepris(String tramite);
	
	
	
	public int createPermiso(PermisoBO bo) throws Exception;
	
	public int saveNuevoProducto(ProductoBO p);
	
	public int saveNuevoEvento(PermisoEventoBO evento);
	
	public int saveNuevaNotificacion(PermisoNotificacionBO notificacion);
	
	public int saveNuevoCorreoNotificacion(PermisoCorreoEventoBO correo);
	
	public boolean updateEvento(PermisoEventoBO evento);
	
	public boolean updateNotificacion(PermisoNotificacionBO notificacion);
	
	public boolean updateCorreoNotificacion(PermisoCorreoEventoBO correo);
	
	public boolean deleteNotificaciones(String notificaciones);
	
	public boolean deleteCorreos(String correos);
	
	public PermisoBO getPermiso(int idPermiso);
	
	public boolean updatePermiso(PermisoBO permiso);
	
	public int saveNuevaHistoria(PermisoHistorialEventoBO historial);
	
	public boolean updateHistoria(PermisoHistorialEventoBO historial);
	
	public List<PermisoHistorialEventoBO> findHistorialEvento(int idEvento);
	
	public boolean deleteHistorial(int idHistorial);
	
	public boolean updaterealizarEvento(int idEvento, int cerrar);
	
	public boolean updateRegistroPorCampo(int idPermiso, String campo, String dato);
	
	public boolean updateRegistroPorCampoFecha(int idPermiso, String campo,
			Date dato);
	
	public int updateFechaCita(Parametros parametro) throws Exception;
	
	public int updateCitaCofeprisEvento(Parametros parametro) throws Exception;
	
	public List<PermisoBO> findPermisosActual(int year);
	
	public List<Parametros> findImportaciones(Parametros parametro);
}
