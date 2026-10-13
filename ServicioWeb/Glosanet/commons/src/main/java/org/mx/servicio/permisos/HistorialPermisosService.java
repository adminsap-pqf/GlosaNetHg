package org.mx.servicio.permisos;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Date;
import java.util.List;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.Parametros;
import org.mx.modelo.permisos.org.mx.bo.PermisoBO;
import org.mx.modelo.permisos.org.mx.bo.PermisoHistorialEventoBO;

public interface HistorialPermisosService {
	
	/**
	 * 
	 * @param idEmpresa
	 * @return
	 * @throws GlosaNetException
	 */
	
	public List<PermisoBO> obtenerPermisosEmpresa (int idEmpresa) throws GlosaNetException;
	
	/**
	 * 
	 * @param tramite
	 * @return
	 * @throws GlosaNetException
	 */
	public String obtenerStatusTramiteCofepris(String tramite) throws GlosaNetException;
	
	
	public PermisoBO agregarPermiso(PermisoBO bo);
	
	public PermisoBO obtenerPermiso(int idPermiso)
			throws GlosaNetException;
	
	public boolean eliminarNotificaciones(String notificaciones)
			throws GlosaNetException;
	
	public boolean eliminarCorreos(String correos)
			throws GlosaNetException;
	
	public List<PermisoHistorialEventoBO> guardarHistorialEvento(List<PermisoHistorialEventoBO> lstHistorial)
			throws GlosaNetException;
	
	public List<PermisoHistorialEventoBO> obtenerHistorialEvento(int idEvento)
			throws GlosaNetException;
	
	public boolean eliminarHistorialEvento(int idEmpresa, int idPermiso,
			int idEvento, int idHistorial) throws GlosaNetException;
	
	public String subirArchivoHistorial(File archivo, String nombreArchivo, String path)
			throws GlosaNetException;
	
	public ByteArrayOutputStream obtenerrArchivoHistorial(String path) throws GlosaNetException;
	
	public boolean realizarEvento(int idEvento, int cerrar) throws GlosaNetException;
	
	public boolean guardarElementoMonitoreoPermiso(int idPermiso, int tipo,
			String dato, Date fecha) throws GlosaNetException;
	
	public boolean actualizarFechaCita(Parametros parametro);
	
	public boolean actualizarCitaCofeprisEvento(Parametros parametro);
	
	public List<PermisoBO> obtenerPermisosActual(int year)
			throws GlosaNetException;
	
	public List<Parametros> obtenerImportaciones(Parametros parametros)
			throws GlosaNetException;
	
}
