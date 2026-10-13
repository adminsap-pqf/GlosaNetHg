package org.mx.servicio.registros;

import org.mx.controllers.registros.RegistrosRequestOptions;
import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.ProductoBO;
import org.mx.modelo.comun.bo.Responsables;
import org.mx.modelo.registros.bo.*;
import org.mx.persistencia.Page;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface RegistroService {

    //Nuevo tramite

    /***
     *
     * @param registro
     * @return
     * @throws GlosaNetException
     */
    public RegistroBO guardarRegistro(RegistroBO registro) throws GlosaNetException;

    /***
     * Obtiene la informacion basica del registro para mostrar en un listado general
     * @param idEmpresa
     * @return
     * @throws GlosaNetException
     */
    public List<RegistroBO> obtenerRegistrosEmpresa(int idEmpresa) throws GlosaNetException;

    /***
     *
     * @param idRegistro
     * @return
     * @throws GlosaNetException
     */
    public RegistroBO obtenerRegistroPorId(int idRegistro) throws GlosaNetException;

    /***
     * Obtiene la infomacion de los productos que estan en  el DataStage y no estan en la tabla de produ
     * @param idEmpresa
     * @param orden
     * @param busqueda
     * @param condicion
     * @return
     * @throws GlosaNetException
     */
    public Page<ProductoBO> obtenerProductosDataStagePorEmpresa(String rfcActual, int idEmpresa, int page, int size, int orden, String busqueda, String condicion) throws GlosaNetException;

    /***
     *
     * @param idEmpresa
     * @return
     * @throws GlosaNetException
     */
    public TiempoRealizacionBO obtenerTiempoRealizacion(int idEmpresa) throws GlosaNetException;

    /***
     *
     * @param historial
     * @return
     * @throws GlosaNetException
     */
    public List<HistorialEventoBO> guardarHistorialEvento(List<HistorialEventoBO> historial) throws GlosaNetException;

    /***
     *
     * @param idEvento
     * @return
     * @throws GlosaNetException
     */
    public List<HistorialEventoBO> obtenerHistorialEvento(int idEvento) throws GlosaNetException;

    /***
     *
     * @param archivo
     * @param nombreArchivo
     * @param path
     * @return
     * @throws GlosaNetException
     */
    public String subirArchivoHistorial(File archivo, String nombreArchivo, String path) throws GlosaNetException;

    /***
     *
     * @param path
     * @return
     * @throws GlosaNetException
     */
    public ByteArrayOutputStream obtenerrArchivoHistorial(String path) throws GlosaNetException;

    /***
     *
     * @param idHistorial
     * @return
     * @throws GlosaNetException
     */
    public boolean eliminarHistorialEvento(int idEmpresa, int idRegistro, int idEvento, int idHistorial) throws GlosaNetException;

    /***
     * Puedes guardar distintos elemento: tramitecofepris, folio registro,
     * fecha inicio registro, fecha fin registro, finicio prorroga y fecha fin prorroga
     * @param idRegistro
     * @param tipo
     * @param dato
     * @param fecha
     * @return
     * @throws GlosaNetException
     */
    public boolean guardarElementoMonitoreoRegistro(int idRegistro, int tipo, String dato, Date fecha) throws GlosaNetException;

    public boolean actualizaNotificaciones(List<NotificacionBO> notificaciones) throws GlosaNetException;

    public boolean realizarEvento(int idEvento, int cerrar) throws GlosaNetException;

    public String obtenerStatusTramiteCofepris(String tramite) throws GlosaNetException;

    /***
     * Recibe un string con varios idNotificacion separados por ',' para que solo se incluya en el sql
     * @param notificaciones
     * @return
     * @throws GlosaNetException
     */
    public boolean eliminarNotificaciones(String notificaciones) throws GlosaNetException;

    /***
     * Recibe un string con varios idCorreos separados por ',' para que solo se incluya en el sql
     * @param correos
     * @return
     * @throws GlosaNetException
     */
    public boolean eliminarCorreos(String correos) throws GlosaNetException;

    public List<NotificacionBO> obtenNotificacionesPorEnviar(LocalDateTime today) throws GlosaNetException;

    public void notificaTramitesPendientesCOFEPRIS(LocalDateTime today) throws GlosaNetException;

    public List<FeriadosOficialesBO> obtenerFeriadosOficiales() throws GlosaNetException;

    public List<RegistroBO> obtenerRegistros() throws GlosaNetException;

    public String guardarStatusTramiteCofepris(RegistroBO registro, EventoBO evento, String estadoActual) throws GlosaNetException;

    public boolean actualizarRegistro(RegistroBO bo) throws GlosaNetException;

    /***
     * Carga masiva de registros, Con eventos dosier y sometimiento cerrados y tecnovigilancia y prorroga abiertos
     * @param urlArhivo
     * @return
     * @throws GlosaNetException
     */
    public boolean cargaRegistros(String urlArhivo) throws GlosaNetException;

    /***
     * Actualiza las fechas: Fin de Evento, Inicio de Notificaciones
     * @param idEmpresa
     * @return
     * @throws GlosaNetException
     */
    public boolean actualizarFechas(int idEmpresa) throws GlosaNetException;

    /***
     * Obtener dias habiles entre dos fechas
     * @param finicio
     * @param ffin
     * @return
     * @throws GlosaNetException
     */
    public String cuantosDiasHabiles(String finicio, String ffin) throws GlosaNetException;

    /**
     * Obtiene la informacion basica del registro para mostrar de forma pagina y filtrada en un listado general
     *
     * @param requestOptions
     * @return
     * @throws GlosaNetException
     */
    public Page<RegistroBO> listPaginatedRecords(RegistrosRequestOptions requestOptions) throws GlosaNetException;

    public Responsables getManager(int idEmpresa) throws GlosaNetException;

    /***
     *
     * @param listHistorialProducto
     * @return
     * @throws GlosaNetException
     */
    public List<HistorialProductoBO> saveHistorialProducto(List<HistorialProductoBO> listHistorialProducto) throws GlosaNetException;

    public String subirArchivoHistorialProducto(File file, String fileName, String path, Integer idHistorialProducto) throws GlosaNetException;

    public List<HistorialProductoBO> getArchivosPorRegistro(int idRegistro) throws GlosaNetException;

    public ByteArrayOutputStream obtenerHistorialProductoPDF(String path) throws GlosaNetException;

    public List<RegistroBO> obtenerRegistrosPorProducto(int idEmpresa, int idProducto) throws  GlosaNetException;

    /***
     *
     * @param idHistorialProducto
     * @param path
     * @return
     * @throws GlosaNetException
     */
    public boolean eliminarHistorialProducto(int idHistorialProducto, String path) throws GlosaNetException;

    void getNotificacionesEventoHoy();
}
