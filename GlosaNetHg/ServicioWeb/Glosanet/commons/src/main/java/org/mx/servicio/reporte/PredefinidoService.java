package org.mx.servicio.reporte;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.Parametros;
import org.mx.modelo.reportes.ParametrosPartida;
import org.mx.modelo.reportes.bo.DetalleContribucionesBO;
import org.mx.modelo.reportes.bo.DetallePartidasBO;
import org.mx.modelo.reportes.bo.ResumenReporteBO;
import org.mx.persistencia.Page;

public interface PredefinidoService {

	List<DetalleContribucionesBO> reporteDetalleContribuciones(Parametros parametros);

	/***
	 * Obtiene los resultados de las graficas predefinidas en el sistema
	 * @param p
	 * @return
	 * @throws GlosaNetException
	 */
	List<ResumenReporteBO> obtenerResultadosReportesPredefinidos(Parametros p) throws GlosaNetException;
	
	/**
	 * Genera un Reporte en PDF
	 * @param arguments
	 * @throws GlosaNetException
	 */
	byte[] descargarReporte(Map<String, Object> arguments)
			throws GlosaNetException;

	/**
	 *
	 * @param parametros
	 * @return
	 * @throws IOException
	 */
	ByteArrayOutputStream reporteContribucionesExcel(Parametros parametros) throws IOException;

	ByteArrayOutputStream reportePartidasExcel(Parametros p) throws IOException, GlosaNetException;

	/**
	 *
	 * @param parametros
	 * @return
	 */
    List<DetallePartidasBO> reporteDetallePartidas(ParametrosPartida parametros) throws GlosaNetException;

	/**
	 *
	 * @param parametros
	 * @return
	 */
	Page<DetallePartidasBO> reporteDetallePartidas(Parametros parametros) throws GlosaNetException;

    /** Envia el pdf por correo
	 * @param arguments
	 * @throws GlosaNetException
	 */
	//public void enviarCorreo(Map<String, Object> arguments) throws GlosaNetException;
}
