package org.mx.persistencia.comun;

import java.util.List;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.PCorreoEventoBO;
import org.mx.modelo.comun.bo.PEventoBO;
import org.mx.modelo.comun.bo.PHistorialEventoBO;
import org.mx.modelo.comun.bo.PNotificacionBO;
import org.mx.modelo.comun.bo.Parametros;
import org.mx.modelo.comun.bo.PrevisionProductoBO;
import org.mx.modelo.comun.bo.PrevisionesBO;
import org.mx.modelo.comun.bo.ProductoBO;
import org.mx.modelo.permisos.org.mx.bo.HistorialPermisosBO;

public interface PrevisionesDAO {
	 

	public boolean actualizarPrevision(PrevisionesBO bo);
	
	public boolean modificarPrevision(PrevisionesBO bo);
	
	public int crearPrevisiones(PrevisionesBO prevision) throws Exception;
	
	public List<PrevisionesBO> obtenerPrevisiones(PrevisionesBO bo) throws GlosaNetException;
	
	public List<PrevisionesBO> obtenerProductos(PrevisionesBO bo) throws GlosaNetException;
	
	public int crearPrevision(PrevisionesBO prevision) throws Exception;
	
	public boolean borrarPrevision(PrevisionesBO bo);
	
	boolean eliminarProductoPrevision(String in, Integer idPrevision) throws Exception;
	
	public List<PrevisionesBO>  obtenerProductosgenerales(Parametros p);
	
	public int insertarProducto(ProductoBO bo) throws Exception;
	
	public boolean insertarPermisos(HistorialPermisosBO bo) throws Exception;
	
	public PrevisionesBO obtenerPrevision(int id) throws GlosaNetException;

	
	public int saveNuevoEvento(PEventoBO evento);
	
	public int saveNuevaNotificacion(PNotificacionBO notificacion);
	
	public int saveNuevoCorreoNotificacion(PCorreoEventoBO correo);
	
	public boolean updateEvento(PEventoBO evento);
	
	public boolean updateNotificacion(PNotificacionBO notificacion);
	
	public boolean updateCorreoNotificacion(PCorreoEventoBO correo);
	
	public boolean deleteNotificaciones(String notificaciones);
	
	public boolean deleteCorreos(String correos);
	
	public List<PrevisionProductoBO> obtenerProductosPrevisionActual(int id) throws GlosaNetException;
	
	public int crearPrevisionProducto(PrevisionProductoBO prevision) throws Exception;
	
	public int updatePrevision(PrevisionesBO prevision) throws Exception;
	
	public boolean obtenerPrevisionProxima(int id) throws GlosaNetException;
	
	public List<ProductoBO> obtenerProductosPorPrevision(int id) throws GlosaNetException;
	
	public int saveNuevoProducto(ProductoBO producto);
	
	public boolean updateProducto(ProductoBO producto);
	
	public int savePrevisionProducto(ProductoBO p);
	
	public boolean updatePrevisionProducto(ProductoBO producto);
	
	public int obtenerIdPrevisionProxima(int id) throws GlosaNetException;
	
	public boolean deletePrevisionProducto(String previsionProductos);
	
	public int saveNuevaHistoria(PHistorialEventoBO historial);
	
	public boolean updateHistoria(PHistorialEventoBO historial);
	
	public List<PHistorialEventoBO> findHistorialEvento(int idEvento);
	
	public boolean deleteHistorial(int idHistorial);
	
	public boolean updaterealizarEvento(int idEvento, int cerrar);
	
	public int updateFechaCita(Parametros parametro) throws Exception;
	
	public int updateCitaCofeprisEvento(Parametros parametro) throws Exception;
	
	public int obtenerIdPrevisionActual(int id) throws GlosaNetException;
	
	public List<ProductoBO> obtenerProductosCompletosPrevisionActual(int id) throws GlosaNetException;
	
	public boolean updatePrevisionPorCampo(int idPrevision, String campo,
			String dato);
	
	public List<ProductoBO> obtenerListaProductosPrevisionActual(int idEmpresa) throws GlosaNetException;
}
