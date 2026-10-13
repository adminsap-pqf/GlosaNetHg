package org.mx.persistencia.registros;

import java.util.Date;
import java.util.List;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.ProductoBO;
import org.mx.modelo.notificaciones.po.NotificationEventoPO;
import org.mx.modelo.registros.bo.CorreoEventoBO;
import org.mx.modelo.registros.bo.EventoBO;
import org.mx.modelo.registros.bo.FeriadosOficialesBO;
import org.mx.modelo.registros.bo.HistorialCofeprisBO;
import org.mx.modelo.registros.bo.HistorialEventoBO;
import org.mx.modelo.registros.bo.HistorialProductoBO;
import org.mx.modelo.registros.bo.NotificacionBO;
import org.mx.modelo.registros.bo.RegistroBO;
import org.mx.modelo.registros.bo.TiempoRealizacionBO;
import org.mx.persistencia.Page;

public interface RegistrosDAO {
	
	public int saveNuevoProducto(ProductoBO p);
	/***
	 * 
	 * @param registro
	 * @return
	 */
	public int saveNuevoRegistro(RegistroBO registro);
	/***
	 * 
	 * @param evento
	 * @return
	 */
	public int saveNuevoEvento(EventoBO evento);
	/***
	 * 
	 * @param notificacion
	 * @return
	 */
	public int saveNuevaNotificacion(NotificacionBO notificacion);
	/***
	 * 
	 * @param correo
	 * @return
	 */
	public int saveNuevoCorreoNotificacion(CorreoEventoBO correo);
	/***
	 * 
	 * @param registro
	 * @return
	 */
	public boolean updateRegistro(RegistroBO registro);
	/***
	 * 
	 * @param evento
	 * @return
	 */
	public boolean updateEvento(EventoBO evento);
	public boolean updateEventoFRealizacion(int idEvento, Date frealizacion);
	/***
	 * 
	 * @param notificacion
	 * @return
	 */
	public boolean updateNotificacion(NotificacionBO notificacion);
	public boolean updateNotificacionFInicio(int idNotificacion, Date finicio);
	/***
	 * 
	 * @param correo
	 * @return
	 */
	public boolean updateCorreoNotificacion(CorreoEventoBO correo);
	/***
	 * 
	 * @param idEmpresa
	 * @return
	 */
	public List<RegistroBO> findRegistrosEmpresa(int idEmpresa);
	/***
	 * 
	 * @param idRegistro
	 * @return
	 */
	public RegistroBO getRegistroPorId(int idRegistro);
	/***
	 * 
	 * @return
	 */
	public TiempoRealizacionBO getTiempoRealizacion(int idEmpresa);
	/***
	 * 
	 * @param tiempo
	 * @return
	 */
	public int saveTiempoRealizacion(TiempoRealizacionBO tiempo);
	/***
	 * 
	 * @param rfcActual
	 * @return
	 */
	public List<ProductoBO> findProductosDataStagePorEmpresa(String rfcActual);
	/***
	 * 
	 * @param historial
	 * @return
	 */
	public int saveNuevaHistoria(HistorialEventoBO historial);
	/***
	 * 
	 * @param historial
	 * @return
	 */
	public boolean updateHistoria(HistorialEventoBO historial);
	/***
	 * 
	 * @param idEvento
	 * @return
	 */
	public List<HistorialEventoBO> findHistorialEvento(int idEvento);
	/***
	 * 
	 * @param idHistorial
	 * @return
	 */
	public boolean deleteHistorial(int idHistorial);
	/**
	 * Actualiza los campos editables en monitoreo de registros
	 * @param idRegistro
	 * @param campo
	 * @param dato
	 * @return
	 */
	public boolean updateRegistroPorCampo(int idRegistro, String campo, String dato);
	public boolean updateRegistroPorCampoFecha(int idRegistro, String campo, Date dato);
	public boolean updaterealizarEvento(int idEvento, int cerrar);
	/***
	 * 
	 * @param notificaciones
	 * @return
	 */
	public boolean deleteNotificaciones(String notificaciones);
	/***
	 * 
	 * @param notificaciones
	 * @return
	 */
	public boolean deleteCorreos(String notificaciones);
	public List<NotificacionBO> findNotificacionesPorEnviar();
	public List<NotificationEventoPO> getNotificacionesEventoHoy();
	public int getTotaldeHistorialPorEvento (int idEvento);
	public int saveHistorialCofepris(String tramite, String estado);
	public List<HistorialCofeprisBO> findHistorialCofepris(String tramite) throws GlosaNetException;
	public EventoBO getEventoPorId(int idEvento);
	public int getTotalRegistrosProducto(int idProducto);
	public Page<ProductoBO> getProductos(int idEmpresa, int page, int size, int orden, String busqueda, String condicion);
	public List<FeriadosOficialesBO> findFeriadosOficiales();
	public boolean updateRegistroPorCampoRegistro(int idRegistro, String campo,
			String dato);
	public boolean updateRegistroPorCampoFechaTramite(int idEvento, String campo,
			Date dato);
	public List<EventoBO> findEventosTramite();
	public List<RegistroBO> findRegistros();
	public boolean updateRegistroFFin(RegistroBO registro);
	public boolean updateProducto(ProductoBO producto);
	/***
	 *
	 * @param historialProducto
	 * @return
	 */
	public int addHistorialProducto(HistorialProductoBO historialProducto);
	/***
	*
 	* @param historialProducto
 	* @return
 	*/
	public boolean updateHistorialProducto(HistorialProductoBO historialProducto, String field);
	/***
	 *
	 * @param idRegistro
	 * @return
	 */
	public List<HistorialProductoBO> findArchivosPorRegistro(int idRegistro);
	/***
	 *
	 * @param idEmpresa
	 * @param idProducto
	 * @return
	 */
	public List<RegistroBO> findRegistrosPorProducto(int idEmpresa, int idProducto);
	/***
	 *
	 * @param idHistorialProducto
	 * @return
	 */
	public boolean deleteHistorialProducto(int idHistorialProducto);


}
