package org.mx.servicio.comun;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Date;
import java.util.List;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.PHistorialEventoBO;
import org.mx.modelo.comun.bo.Parametros;
import org.mx.modelo.comun.bo.PrevisionesBO;
import org.mx.modelo.comun.bo.ProductoBO;


public interface PrevisionesService {


	public int agregarPrevisiones(PrevisionesBO bo);
	
	public PrevisionesBO agregarPrevision(PrevisionesBO prevision) throws Exception;

	public boolean actualizarPrevisiones(PrevisionesBO bo);

	public List<PrevisionesBO> obtenerPrevisiones(PrevisionesBO bo) throws GlosaNetException;
	
	public List<PrevisionesBO> obtenerProductos(PrevisionesBO bo) throws GlosaNetException;
	
	public boolean modificarPrevision(PrevisionesBO bo);

	public boolean borrarPrevision(PrevisionesBO bo);
	
	public List<PrevisionesBO> guardarPrevision(List<PrevisionesBO> bo) throws Exception;

	public List<PrevisionesBO>  obtenerProductosgenerales(Parametros p);
	
	public boolean insertarProducto(ProductoBO bo) throws Exception;
	
	
	public PrevisionesBO obtenerPrevision(int id) throws GlosaNetException;
	
	public List<ProductoBO> obtenerProductosPorPrevision(int id) throws GlosaNetException;
	
	public boolean eliminarNotificaciones(String notificaciones)
			throws GlosaNetException;
	
	public boolean eliminarCorreos(String correos)
			throws GlosaNetException;
	
	
	public List<ProductoBO> guardarProductos(List<ProductoBO> bo) throws Exception;
	
	public boolean eliminarPrevisionProducto(String previsionProducto)
			throws GlosaNetException;
	
	public List<PHistorialEventoBO> guardarHistorialEvento(List<PHistorialEventoBO> lstHistorial)
			throws GlosaNetException;
	
	public List<PHistorialEventoBO> obtenerHistorialEvento(int idEvento)
			throws GlosaNetException;
	
	public boolean eliminarHistorialEvento(int idEmpresa, int idRegistro,
			int idEvento, int idHistorial) throws GlosaNetException ;
	
	
	public String subirArchivoHistorial(File archivo, String nombreArchivo, String path)
			throws GlosaNetException;
	
	public ByteArrayOutputStream obtenerrArchivoHistorial(String path) throws GlosaNetException;
	
	public boolean realizarEvento(int idEvento, int cerrar) throws GlosaNetException ;
	
	public boolean actualizarFechaCita(Parametros parametro);
	
	public boolean actualizarCitaCofeprisEvento(Parametros parametro);
	
	public int obtenerPrevisionActual(int id) throws GlosaNetException;
	
	public List<ProductoBO> obtenerProductosCompletosPrevisionActual(int id) throws GlosaNetException;
	
	public boolean guardarElementoMonitoreoPrevision(int idPrevision, int tipo,
			String dato, Date fecha) throws GlosaNetException;
	
	
	public List<ProductoBO> obtenerPrevisiones(int idEmpresa) throws GlosaNetException;
}
