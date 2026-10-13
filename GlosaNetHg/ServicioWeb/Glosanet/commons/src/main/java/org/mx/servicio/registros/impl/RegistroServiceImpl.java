package org.mx.servicio.registros.impl;

import org.mx.controllers.registros.RegistrosRequestOptions;
import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.CorreoBO;
import org.mx.modelo.comun.bo.ProductoBO;
import org.mx.modelo.comun.bo.Responsables;
import org.mx.modelo.comun.bo.UsuarioBO;
import org.mx.modelo.notificaciones.po.NotificationEventoPO;
import org.mx.modelo.registros.bo.*;
import org.mx.modelo.util.Util;
import org.mx.modelo.util.UtilJavaTime;
import org.mx.persistencia.Page;
import org.mx.persistencia.registros.RegistrosDAO;
import org.mx.persistencia.registros_hibernate.RegistrosHibernateDAO;
import org.mx.servicio.registros.RegistroService;
import org.mx.servicio.scheduler.notificaciones.notificationBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service("registroService")
public class RegistroServiceImpl implements RegistroService {

    //Bitácora de depuración
    private final static Logger log = Logger.getLogger(RegistroServiceImpl.class.getName());

    //Tipos de recurrencia de las notificaciones
    private final static String RECURRENCE_DAILY = "Cada Día";
    private final static String RECURRENCE_WEEKLY = "Cada Semana";
    private final static String RECURRENCE_MONTHLY = "Cada Mes";
    private final static String RECURRENCE_YEARLY = "Cada Año";

    //Límite de días para envio de notificación de trámite
    private final static int COFEPRIS_EVENT_NOTIFICATION_LIMIT_DAYS = 90;

    @Autowired
    RegistrosDAO registrosDAO;

    @Autowired
    RegistrosHibernateDAO registrosHibernateDAO;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public RegistroBO guardarRegistro(RegistroBO registro)
            throws GlosaNetException {
        try {
            boolean nuevo = false;
            log.info("Guardar registro. Id de registro: " + registro.getFinicio());
            if (registro.getProducto() != null && registro.getIdRegistro() != null && registro.getIdRegistro() == 0) {
                if (registro.getProducto().getIdProducto() == 0) {
                    registro.getProducto().setIdProducto(registrosDAO.saveNuevoProducto(registro.getProducto()));
                }
                // Guardar los tiempo nuevos
                TiempoRealizacionBO tr = new TiempoRealizacionBO();
                tr.setIdEmpresa(registro.getIdEmpresa());
                for (EventoBO e : registro.getEventos()) {

                    boolean thereIsDossier = false;
                    boolean thereIsTecnovigilancia = false;
                    boolean thereIsSometimiento = false;

                    if (e.getNombre().equals("Dossier")) {
                        tr.setEv1_cantidad(e.getCantPeriodo());
                        tr.setEv1_tipo(e.getTipoPeriodo());
                        thereIsDossier = true;
                    }

                    if (e.getNombre().equals("Tecnovigilancia")) {
                        tr.setEv2_cantidad(e.getCantPeriodo());
                        tr.setEv2_tipo(e.getTipoPeriodo());
                        thereIsTecnovigilancia = true;
                    }

                    if (e.getNombre().equals("Sometimiento")) {
                        tr.setEv3_cantidad(e.getCantPeriodo());
                        tr.setEv3_tipo(e.getTipoPeriodo());
                        thereIsSometimiento = true;
                    }

                    /*
                     *  En caso de que no se tenga el evento, se guardaran las propiedades por default
                     *
                     */
                    if (!thereIsDossier) {
                        tr.setEv1_cantidad(300);
                        tr.setEv1_tipo("Días");
                    }

                    if (!thereIsTecnovigilancia) {
                        tr.setEv2_cantidad(365);
                        tr.setEv2_tipo("Días");
                    }

                    if (!thereIsSometimiento) {
                        tr.setEv3_cantidad(300);
                        tr.setEv3_tipo("Días");
                    }
                }

                registrosDAO.saveTiempoRealizacion(tr);

                nuevo = true;
                log.info("Se agregó un nuevo registro");
            }

            if (registro != null && registro.getIdRegistro() != null && registro.getIdRegistro() > 0) {
                registrosDAO.updateRegistro(registro);
            } else if (registro != null) {
                registro.setIdRegistro(registrosDAO.saveNuevoRegistro(registro));
            }

            log.info("Inicia el envío de notificaciones, eventos y correos.");
            if (registro != null && registro.getIdRegistro() > 0) {
                for (EventoBO e : registro.getEventos()) {

                    e.setRegistro(registro);
                    if (e.getIdEvento() != null && e.getIdEvento() > 0) {
                        registrosDAO.updateEvento(e);
                        log.info("Inicia actualización del evento");
                    } else {
                        e.setIdEvento(registrosDAO.saveNuevoEvento(e));
                    }

                    if (e.getIdEvento() != null && e.getIdEvento() > 0) {
                        for (NotificacionBO n : e.getNotificaciones()) {
                            n.setIdEvento(e.getIdEvento());
                            if (n.getIdNotificacion() != null && n.getIdNotificacion() > 0) {
                                registrosDAO.updateNotificacion(n);
                            } else {
                                n.setIdNotificacion(registrosDAO.saveNuevaNotificacion(n));
                            }
                        }

                        for (CorreoEventoBO c : e.getCorreos()) {
                            c.setIdEvento(e.getIdEvento());
                            c.setIdEvento(e.getIdEvento());
                            if (c.getIdCorreo() != null && c.getIdCorreo() > 0) {
                                registrosDAO.updateCorreoNotificacion(c);
                            } else {
                                c.setIdCorreo(registrosDAO.saveNuevoCorreoNotificacion(c));
                            }
                        }
                    }
                }

                /***
                 * Se inserta por default un evento Folio Registro
                 */
                if (nuevo) {
                    EventoBO f = new EventoBO();
                    f.setRegistro(registro);
                    f.setCantPeriodo(0);
                    f.setCorreos(null);
                    f.setDefinido(true);
                    f.setFrealizacion(registro.getFfin());
                    f.setNombre("Folio Registro");
                    f.setNotificaciones(null);
                    f.setTerminado(false);
                    f.setTipoPeriodo("Días");
                    f.setIdEvento(registrosDAO.saveNuevoEvento(f));
                }
            }
            log.info("Finaliza el envío de notificaciones, eventos y correos.");

            return registro;
        } catch (Exception e) {
            log.severe("Error durante el proceso de guardar registro. " + e.getMessage());
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus()
                    .setRollbackOnly();
            throw new GlosaNetException();
        }
    }

    @Override
    public List<RegistroBO> obtenerRegistrosEmpresa(int idEmpresa)
            throws GlosaNetException {
        List<RegistroBO> r = registrosDAO.findRegistrosEmpresa(idEmpresa);

        for (RegistroBO x : r) {
            for (EventoBO ev : x.getEventos()) {
                if (x.getEventoActual().equals(ev.getNombre())) {
                    x.setEstadoCofepris(obtenerStatusTramiteCofepris(ev.getTramiteCofepris()));
                }
            }
        }
        return r;
    }


    @Override
    public List<RegistroBO> obtenerRegistros()
            throws GlosaNetException {
        log.info("Inicia el proceso de obtención de registros de Cofepris");

        if (onlineTramiteCofepris()) {
            List<RegistroBO> r = registrosDAO.findRegistros();
            for (RegistroBO x : r) {
                for (EventoBO ev : x.getEventos()) {
                    if (x.getEventoActual().equals(ev.getNombre()) && ev.getTramiteCofepris() != null && !ev.getTramiteCofepris().equals("")) {

                        String resultado = "Trámite No Encontrado";

                        List<HistorialCofeprisBO> lstBO = registrosDAO.findHistorialCofepris(ev.getTramiteCofepris());
                        if (!lstBO.isEmpty()) {
                            if (lstBO.get(0).getEstado().equals("Trámite entregado.") || lstBO.get(0).getEstado().equals("Entregado al cliente.")) {
                                resultado = lstBO.get(0).getEstado() + "|" + lstBO.get(0).getFecha();
                                x.setEstadoCofepris(resultado);
                            } else {
                                x.setEstadoCofepris(guardarStatusTramiteCofepris(x, ev, lstBO.get(0).getEstado()));
                                log.info("Estatus de Cofepris: " + x.getEstadoCofepris());
                                String array[] = x.getEstadoCofepris().split("\\|");
                                for (String v : array) {
                                    log.info(v);
                                }
                            }
                        } else {
                            x.setEstadoCofepris(guardarStatusTramiteCofepris(x, ev, ""));
                            log.info("Estado de Cofepris: " + x.getEstadoCofepris());
                            String array[] = x.getEstadoCofepris().split("\\|");

                            for (String v : array) {
                                log.info(v);
                            }
                        }
                    }
                }
            }
            log.info("Finalizó el proceso de obtener registros de Cofepris");
            return r;
        }
        return null;
    }

    @Override
    public Page<ProductoBO> obtenerProductosDataStagePorEmpresa(String rfcActual, int idEmpresa, int page, int size, int orden, String busqueda, String condicion)
            throws GlosaNetException {
        Page<ProductoBO> lst = registrosDAO.getProductos(idEmpresa, page, size, orden, busqueda, condicion);

        return lst;
    }


    @Override
    public TiempoRealizacionBO obtenerTiempoRealizacion(int idEmpresa)
            throws GlosaNetException {
        return registrosDAO.getTiempoRealizacion(idEmpresa);
    }


    @Override
    public RegistroBO obtenerRegistroPorId(int idRegistro)
            throws GlosaNetException {
        RegistroBO r = registrosDAO.getRegistroPorId(idRegistro);

        //		if(r.getTramiteCofepris() != null && !r.getTramiteCofepris().equals("")){
        //			r.setEstadoCofepris(obtenerStatusTramiteCofepris(r.getTramiteCofepris()));
        //		}

        for (EventoBO e : r.getEventos()) {
            e.setTotalHistorial(registrosDAO.getTotaldeHistorialPorEvento(e.getIdEvento()));
        }

        return r;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class})
    public List<HistorialEventoBO> guardarHistorialEvento(List<HistorialEventoBO> lstHistorial)
            throws GlosaNetException {
        try {
            for (HistorialEventoBO historial : lstHistorial) {
                if (historial != null && historial.getIdHistorial() > 0) {
                    registrosDAO.updateHistoria(historial);
                }
                if (historial != null) {
                    historial.setIdHistorial(registrosDAO.saveNuevaHistoria(historial));
                }
            }
            return lstHistorial;
        } catch (Exception e) {
            log.severe("Error al guardar la cita en el historial. " + e.getMessage());
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus()
                    .setRollbackOnly();
            throw new GlosaNetException();
        }
    }


    @Override
    public List<HistorialEventoBO> obtenerHistorialEvento(int idEvento)
            throws GlosaNetException {
        return registrosDAO.findHistorialEvento(idEvento);
    }

    public String subirArchivoHistorial(File archivo, String nombreArchivo, String path)
            throws GlosaNetException {
        try {
            String dir = Util.rutaArchivos + "Registros/" + path;
            File file = new File(dir);
            File targetFile = new File(dir + "/" + nombreArchivo + ".pdf");
            if (!file.exists()) {
                file.mkdirs();
            }
            OutputStream outStream;
            outStream = new FileOutputStream(targetFile);
            byte[] array = Files.readAllBytes(archivo.toPath());
            outStream.write(array);
            outStream.close();
            return "Exito";
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlosaNetException();
        }
    }

    @Override
    public ByteArrayOutputStream obtenerrArchivoHistorial(String path) throws GlosaNetException {

        log.info("@obtenerrArchivoHistorial: Inicio");

        ByteArrayOutputStream buffer;
        try {
            String dir = Util.rutaArchivos + "Registros/" + path;

            log.info("Ruta para obtener archivo: " + dir);
            File file = new File(dir);

            if (null == file) {
                log.severe("No se encontró el archivo: " + dir);
                throw new GlosaNetException("Archivo no encontrado");
            }

            InputStream fileStream = new FileInputStream(file);
            byte[] data = new byte[1024];
            buffer = new ByteArrayOutputStream();

            int read = 0;
            while ((read = fileStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, read);
            }

            buffer.flush();
            fileStream.close();

        } catch (Exception e) {
            e.printStackTrace();
            log.severe("Error al obtener un archivo del historial. " + e.toString());
            throw new GlosaNetException(e);
        }

        return buffer;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean eliminarHistorialEvento(int idEmpresa, int idRegistro,
                                           int idEvento, int idHistorial) throws GlosaNetException {
        try {
            if (registrosDAO.deleteHistorial(idHistorial)) {
                String dir = Util.rutaArchivos + "Registros/" + idRegistro + "/" + idEmpresa + "/" + idEvento + "/Doc_" + idHistorial + ".pdf";
                File file = new File(dir);

                if (file.exists()) {
                    file.delete();
                }
                log.info("Se eliminó el archivo relacionado a una cita del historial: " + dir);
            }
            return true;
        } catch (Exception e) {
            log.severe("Error al eliminar un archivo relacionado a una cita del historial. " + e.getMessage());
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus()
                    .setRollbackOnly();
            throw new GlosaNetException();
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean guardarElementoMonitoreoRegistro(int idRegistro, int tipo,
                                                    String dato, Date fecha) throws GlosaNetException {
        try {
            String campo;

            switch (tipo) {
                case 1: // Cofepris
                    campo = "TramiteCofepris";
                    registrosDAO.updateRegistroPorCampo(idRegistro, campo, dato);
                    break;
                case 2: //Folio Registro
                    campo = "FolioRegistro";
                    registrosDAO.updateRegistroPorCampoRegistro(idRegistro, campo, dato);
                    break;
                case 3: // Finicio Registro
                    campo = "FechaInicio";
                    registrosDAO.updateRegistroPorCampoFecha(idRegistro, campo, fecha);
                    break;
                case 4: // FFin Registro
                    campo = "FechaFin";
                    registrosDAO.updateRegistroPorCampoFecha(idRegistro, campo, fecha);
                    break;
                case 5: // Finicio Prorroga
                    campo = "FechaInicioProrroga";
                    registrosDAO.updateRegistroPorCampoFecha(idRegistro, campo, fecha);
                    break;
                case 6: // FFin Prorroga
                    campo = "FechaFinProrroga";
                    registrosDAO.updateRegistroPorCampoFecha(idRegistro, campo, fecha);
                    break;
                case 7: // Cofepris
                    campo = "FechaTramite";
                    registrosDAO.updateRegistroPorCampoFechaTramite(idRegistro, campo, fecha);
                    break;
                default:
                    break;
            }
            return true;
        } catch (Exception e) {
            log.severe("Error al guardar el registro de monitoreo. " + e.getMessage());
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus()
                    .setRollbackOnly();
            throw new GlosaNetException();
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean realizarEvento(int idEvento, int cerrar) throws GlosaNetException {
        try {
            return registrosDAO.updaterealizarEvento(idEvento, cerrar);
        } catch (Exception e) {
            log.severe("Error al actualizar la realización del evento. " + e.getMessage());
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus()
                    .setRollbackOnly();
            throw new GlosaNetException();
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean actualizaNotificaciones(List<NotificacionBO> notificaciones) throws GlosaNetException {
        try {
            for (NotificacionBO notificacion : notificaciones) {
                registrosDAO.updateNotificacion(notificacion);
            }
            return true;

        } catch (Exception e) {
            log.severe("Error al actualizar las notificaciones. " + e.getMessage());
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus()
                    .setRollbackOnly();
            throw new GlosaNetException();
        }

    }

    @Override
    public String obtenerStatusTramiteCofepris(String tramite)
            throws GlosaNetException {
        log.info("@obtenerStatusTramiteCofepris: Inicio");
        String resultado = "Trámite No Encontrado";

        try {
            List<HistorialCofeprisBO> lstBO = registrosDAO.findHistorialCofepris(tramite);
            if (!lstBO.isEmpty()) {
                resultado = lstBO.get(0).getEstado() + "|" + lstBO.get(0).getFecha();
            }
        } catch (GlosaNetException e) {
            log.severe("Error al obtener el estatus del trámite de Cofepris. " + e.toString());
            e.printStackTrace();
            throw new GlosaNetException(e);
        }
        return resultado;
    }


    public boolean onlineTramiteCofepris()
            throws GlosaNetException {
        try {
            URL urlObj = new URL("http://tramiteselectronicos02.cofepris.gob.mx/EstadoTramite/Default.aspx");
            HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
            con.setRequestMethod("GET");
            // Set connection timeout
            con.setConnectTimeout(3000);
            con.connect();

            int code = con.getResponseCode();
            if (code == 200) {
                log.info("El servidor de Trámites Electrónicos de Cofepris está en línea");
                return true;
            } else
                log.info("El servidor de Trámites Electrónicos de Cofepris está fuera de línea");
        } catch (Exception e) {
            log.severe("El servidor de Trámites Electrónicos de Cofepris está fuera de línea. Error: " + e.getMessage());
            return false;
        }
        return false;
    }

    public String guardarStatusTramiteCofepris(RegistroBO registro, EventoBO evento, String estadoActual)
            throws GlosaNetException {
        URL url;
        InputStream is = null;
        BufferedReader br;
        String line, estado = "", resultado = "Trámite No Encontrado";
        String tramite = evento.getTramiteCofepris();
        log.info("Ruta: " + "http://tramiteselectronicos02.cofepris.gob.mx/EstadoTramite/Consulta.aspx?id=" + tramite);

        //183300301X0183
        try {

            url = new URL("http://tramiteselectronicos02.cofepris.gob.mx/EstadoTramite/Consulta.aspx?id=" + tramite);
            is = url.openStream();  // throws an IOException
            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            //Se terminará la conexión después de cierto tiempo
            boolean encontro = false;
            while ((line = br.readLine()) != null) {
                if (line.contains("MainContent_estatusTramiteLabel")) {
                    estado = line;
                    if (!line.contains("</span>")) {
                        encontro = true;
                    }

                } else if (encontro) {
                    if (line.contains("</span>")) {
                        encontro = false;
                    }
                    estado += line;
                }
            }
            //			System.out.println(estado);
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException ioe) {
                // nothing to see here
            }
        }
        //<span id="MainContent_estatusTramiteLabel">Estado del trámite: Trámite disponible<br /><br />Importe adicional: $0.00</span>]
        //'Trámite disponible  Importe adicional'
        boolean diferente = false;
        if (!estado.equals("")) {
            String array1[] = estado.split(": ");
            if (array1.length > 1) {
                array1[0] = array1[0].replaceAll("</span>", "");
                //				System.out.println("array[0] ************************************" + array1[0]);
                if (!array1[0].contains("Fase del trámite")) {
                    //					System.out.println("array1 ************************************" + array1[1]);
                    resultado = array1[1];
                    resultado = resultado.replaceAll("</span>", "");
                    //					resultado = resultado.replaceAll(".", ""); esto hace que la variable de vaya en blanco
                    resultado = resultado.replaceAll("<br />", " ");
                    resultado = resultado.replaceAll("  ", " - ");
                } else {
                    //					System.out.println("array2 ************************************" + array1[1] + " " + array1[2] );
                    resultado = "Fase del trámite: " + array1[1] + " " + array1[2];
                    resultado = resultado.replaceAll("</span>", "");
                    resultado = resultado.replaceAll("<br />", " ");
                    resultado = resultado.replaceAll("  ", " ");
                }
            }

            if (estadoActual.equals("")) {
                registrosDAO.saveHistorialCofepris(tramite, resultado);

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

                String codigo = registro.getProducto().getCodigo();
                String nombProducto = registro.getProducto().getNombre();
                String fechaLimite = formatter.format(registro.getFfin());
                String nombEvento = evento.getNombre();
                String fechaRealizacion = formatter.format(evento.getFrealizacion());
                String tramiteCofepris = evento.getTramiteCofepris();

                //Envío de correo de notificación de estado
                String correoHTML = Util.textoMensajeTramiteCofepris(codigo, nombProducto, fechaLimite, nombEvento, fechaRealizacion, tramiteCofepris, resultado, estadoActual);

                for (CorreoEventoBO c : evento.getCorreos()) {
                    CorreoBO enviarC = new CorreoBO();
                    enviarC.setCorreo(c.getCorreo());
                    log.info("Destinatarios de correo: " + c.getCorreo());
                    //enviarC.setCorreo("carlos.lozada@ryndem.mx");
                    enviarC.setAsunto("GlosaNet - Notificación");
                    enviarC.setCuerpoCorreo(correoHTML);
                    Util.enviarCorreo(enviarC);
                }
                resultado += "|" + new Date();
            }
            else if (!resultado.equals(estadoActual) && !resultado.equals(""))
            {
                registrosDAO.saveHistorialCofepris(tramite, resultado);
                diferente = true;

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

                String codigo = registro.getProducto().getCodigo();
                String nombProducto = registro.getProducto().getNombre();
                String fechaLimite = formatter.format(registro.getFfin());
                String nombEvento = evento.getNombre();
                String fechaRealizacion = formatter.format(evento.getFrealizacion());
                String tramiteCofepris = evento.getTramiteCofepris();

                //Envío de correo de notificación de estado
                String correoHTML = Util.textoMensajeTramiteCofepris(codigo, nombProducto, fechaLimite, nombEvento, fechaRealizacion, tramiteCofepris, resultado, estadoActual);

                for (CorreoEventoBO c : evento.getCorreos()) {
                    CorreoBO enviarC = new CorreoBO();
                    enviarC.setCorreo(c.getCorreo());
                    log.info("Destinatarios de correo: " + c.getCorreo());
                    //enviarC.setCorreo("carlos.lozada@ryndem.mx");
                    enviarC.setAsunto("GlosaNet - Notificación");
                    enviarC.setCuerpoCorreo(correoHTML);
                    Util.enviarCorreo(enviarC);
                }
                resultado += "|" + new Date();
            }
        }

        if (estado.contains("Trámite disponible") && diferente == true) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

            String codigo = registro.getProducto().getCodigo();
            String nombProducto = registro.getProducto().getNombre();
            String fechaLimite = formatter.format(registro.getFfin());
            String nombEvento = evento.getNombre();
            String fechaRealizacion = formatter.format(evento.getFrealizacion());
            String tramiteCofepris = evento.getTramiteCofepris();
            String correoHTML = Util.textoMensajeTramiteCofepris(codigo, nombProducto, fechaLimite, nombEvento, fechaRealizacion, tramiteCofepris, resultado, estadoActual);

			/*
			Ahora ya se envían los correos a todos los involucrados
			 */

            for (CorreoEventoBO c : evento.getCorreos()) {
                CorreoBO enviarC = new CorreoBO();
                enviarC.setCorreo(c.getCorreo());
                log.info("Destinatarios de correo: : " + c.getCorreo());
                //enviarC.setCorreo("carlos.lozada@ryndem.mx");
                enviarC.setAsunto("GlosaNet - Notificación");
                enviarC.setCuerpoCorreo(correoHTML);
                Util.enviarCorreo(enviarC);
            }
        }
        return resultado;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean eliminarNotificaciones(String notificaciones)
            throws GlosaNetException {
        try {
            if (notificaciones.substring(notificaciones.length() - 1).equals(",")) {
                notificaciones = notificaciones.substring(0, notificaciones.length() - 1);
            }

            return registrosDAO.deleteNotificaciones(notificaciones);
        } catch (Exception e) {
            log.severe("Error al eliminar las notificaciones. " + e.getMessage());
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus()
                    .setRollbackOnly();
            throw new GlosaNetException();
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean eliminarCorreos(String correos)
            throws GlosaNetException {
        try {
            if (correos.substring(correos.length() - 1).equals(","))
                correos = correos.substring(0, correos.length() - 1);

            return registrosDAO.deleteCorreos(correos);
        } catch (Exception e) {
            log.severe("Error al eliminar los correos. " + e.getMessage());
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus()
                    .setRollbackOnly();
            throw new GlosaNetException();
        }
    }

    @Override
    public List<NotificacionBO> obtenNotificacionesPorEnviar(LocalDateTime today)
            throws GlosaNetException {
        List<NotificacionBO> notifications = registrosDAO.findNotificacionesPorEnviar();
        int deliveriesCount = 0;

        for (NotificacionBO notification : notifications) {
            if (this.shouldDeliverNotification(notification, today)) {
                EventoBO evento = registrosDAO.getEventoPorId(notification.getIdEvento());

                //Imposible enviar notificacion con registros inexistentes
                if (null == evento || null == evento.getRegistro() || null == evento.getRegistro().getProducto())
                    continue;

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                String codigo = evento.getRegistro().getProducto().getCodigo(),
                        nombProducto = evento.getRegistro().getProducto().getNombre(),
                        fechaLimite = formatter.format(evento.getRegistro().getFfin()),
                        nombEvento = evento.getNombre(),
                        fechaRealizacion = formatter.format(evento.getFrealizacion()),
                        tramiteCofepris = evento.getTramiteCofepris();
                tramiteCofepris = null == tramiteCofepris ? "No encontrado" : tramiteCofepris;

                String correoHTML = Util.textoMensajeNotificacion(codigo, nombProducto, fechaLimite, nombEvento,
                        fechaRealizacion, tramiteCofepris);
                for (CorreoEventoBO c : evento.getCorreos()) {
                    CorreoBO enviarC = new CorreoBO();
                    //enviarC.setCorreo(c.getCorreo());
                    log.info("Destinatarios de correo : " + c.getCorreo());
                    enviarC.setCorreo("mauricio.zarate@ryndem.mx");
                    enviarC.setAsunto("GlosaNet - Recordatorio");
                    enviarC.setCuerpoCorreo(correoHTML);
                    Util.enviarCorreo(enviarC);
                }

                //TODO: Buscar la manera de hacer esto sin dormir la rutina, agendando otra llamada a quartz mejor
                deliveriesCount++;
                if (deliveriesCount > 500) {
                    String msg = "@obtenerNotificacionesPorEnviar: Se han alcanzado >= 500 notificaciones en "
                            + today.toString();
                    log.info(msg);
                    //deliveriesCount = 0;
                }
            }
        }

        String msg = "@obtenerNotificacionesPorEnviar: Total de notificaciones enviadas: "
                + deliveriesCount;
        log.info(msg);

        return notifications;
    }

    @Override
    public void getNotificacionesEventoHoy() {

        log.info("Entra al metodo de getNotificacionesEventoHoy");
        List<NotificationEventoPO> eventos = registrosDAO.getNotificacionesEventoHoy();

        Map<String, Map<String, List<NotificationEventoPO>>> customerList = eventos
                .stream()
                .collect(Collectors
                        .groupingBy(
                                NotificationEventoPO::getCliente,
                                Collectors.groupingBy(NotificationEventoPO::getEvento)));

        for (Map.Entry<String, Map<String, List<NotificationEventoPO>>> customer: customerList.entrySet()){
            notificationBody email = new notificationBody(customer.getKey());

            for (Map.Entry<String, List<NotificationEventoPO>> entry: customer.getValue().entrySet() ) {
                email.newEnvelope();
                email.setEventName(entry.getKey());

                for (NotificationEventoPO evento : entry.getValue()) {
                    email.appendEvent(
                            evento.getClave(),
                            evento.getProducto(),
                            evento.getFolioCofepris(),
                            evento.getFechaVencimiento(),
                            evento.getFechaRealizacion(),
                            evento.getDuracion());
                }
                String html = email.buildHTML();

                CorreoBO sender = new CorreoBO();
                sender.setCorreo(entry.getValue().get(0).getCorreo());
                 //sender.setCorreo("fernando.serdan@ryndem.mx; carlos.lozada@ryndem.mx; oscar.cardona@ryndem.mx"); //TEST
                log.info("Se envió un correo con " + entry.getValue().size() + " notificaciones de eventos de " + entry.getKey() + " a: " + entry.getValue().get(0).getCorreo());
                sender.setAsunto("GlosaNet - Recordatorio");
                sender.setCuerpoCorreo(html);

            //    Util.enviarCorreo(sender);
            }
        }
    }

    private boolean shouldDeliverNotification(NotificacionBO notification, LocalDateTime today) {

        log.info("@shouldDeliverNotification: Inicio");

        //fecha de registro de la notificacion
        Calendar initialDateCal = Calendar.getInstance(); //Fecha tipo Calendar del registro
        initialDateCal.setTime(notification.getFinicio());

        //Fecha tipo LocalDate del registro
        final LocalDateTime initialDateReg = LocalDateTime.of(
                initialDateCal.get(Calendar.YEAR),
                initialDateCal.get(Calendar.MONTH) + 1,
                initialDateCal.get(Calendar.DAY_OF_MONTH),
                initialDateCal.get(Calendar.HOUR),
                initialDateCal.get(Calendar.MINUTE),
                initialDateCal.get(Calendar.SECOND)
        );

        // Unidad a utilizar para cálculo de siguiente ocurrencia
        int timeUnitToUse = UtilJavaTime.TIME_UNIT_MONTH;

        //Si hoy es antes que la fecha de inicio del registro no hacer nada
        if (today.isBefore(initialDateReg)) return false;

        //Caso Mismo día de la creación del registro
        if (today.isEqual(initialDateReg)
                && (today.getDayOfWeek() == DayOfWeek.SUNDAY || today.getDayOfWeek() == DayOfWeek.SATURDAY))
            return false;

        //Obtenemos recurrence
        final String recurrence = notification.getRecurrencia();

        switch (recurrence) {

            case RECURRENCE_DAILY: {
                if (today.getDayOfWeek() == DayOfWeek.SUNDAY
                        || today.getDayOfWeek() == DayOfWeek.SATURDAY) return false;
                break;
            }

            case RECURRENCE_WEEKLY: {
                //TODO: Qué pasa con los registros que se hacen los fines de semana (sábado, domingo; son válidos?)
                if (initialDateReg.getDayOfWeek() != today.getDayOfWeek()) return false;
                break;
            }

            case RECURRENCE_MONTHLY:
            case RECURRENCE_YEARLY: {

                //Checamos cuál unidad usaremos
                if (recurrence == RECURRENCE_YEARLY) timeUnitToUse = UtilJavaTime.TIME_UNIT_YEAR;
                //TODO: La fecha inicial puede ser a futuro?

                try {
                    Long duration = UtilJavaTime.getDurationBetweenDates(
                            initialDateReg, today, timeUnitToUse);
                    if (duration == 0) return false;

                    // 1 o más meses/años han pasado; checar si cae el día indicado
                    LocalDate nextOccurrence = LocalDate.of(
                            initialDateReg.getYear(),
                            initialDateReg.getMonthValue(),
                            initialDateReg.getDayOfMonth());

                    // Sumamos el tiempo que ha pasado; consideraciones internas: leap years, meses cortos etc...
                    // pero no zonas horarias
                    nextOccurrence = timeUnitToUse == UtilJavaTime.TIME_UNIT_MONTH
                            ? nextOccurrence.plusMonths(duration)
                            : nextOccurrence.plusYears(duration);

                    if (nextOccurrence.compareTo(today.toLocalDate()) != 0) return false;
                    //TODO: Tomar en cuenta qué pasa si la notificación no se envió el día exacto;
                    //esperar todo un año o mes para enviarla?
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
                break;
            }

            default: {
                throw new IllegalStateException("Unexpected value: " + recurrence);
            }
        }

        //Salida
        String msg = "@shouldDeliverNotification: " +
                "Confirmación de envío con recurrence " + recurrence + ", Fecha inicial " +
                initialDateReg.toString() + ", Hoy " + today.toString();
        log.info(msg);
        //System.out.println(msg);

        return true;
    }


    @Override
    public void notificaTramitesPendientesCOFEPRIS(LocalDateTime today) throws GlosaNetException {

        log.info("@notificaTramitesPendientesCOFEPRIS: inicio");

        List<EventoBO> eventos = registrosDAO.findEventosTramite();
        int deliveriesCount = 0;

        for (EventoBO evento : eventos) {

            // Fecha de registro del evento del trámite
            Calendar initialDateCal = Calendar.getInstance(); //Fecha tipo Calendar del registro
            initialDateCal.setTime(evento.getFechaTramite());

            //Fecha tipo LocalDate del registro
            final LocalDateTime initialDateReg = LocalDateTime.of(
                    initialDateCal.get(Calendar.YEAR),
                    initialDateCal.get(Calendar.MONTH) + 1,
                    initialDateCal.get(Calendar.DAY_OF_MONTH),
                    initialDateCal.get(Calendar.HOUR),
                    initialDateCal.get(Calendar.MINUTE),
                    initialDateCal.get(Calendar.SECOND)
            );

            //Si hoy es antes que la fecha de inicio del registro no hacer nada
            if (today.isBefore(initialDateReg)) continue;

            try {
                Long duration = UtilJavaTime.getDurationBetweenDates(
                        initialDateReg, today, UtilJavaTime.TIME_UNIT_DAYS);

                // Exactamente 90 días han pasado desde la creación de la notificación
                if (duration == COFEPRIS_EVENT_NOTIFICATION_LIMIT_DAYS) {

                    RegistroBO registro = registrosDAO.getRegistroPorId(evento.getRegistro().getIdRegistro());
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    String codigo = registro.getProducto().getCodigo(),
                            nombProducto = registro.getProducto().getNombre(),
                            fechaLimite = formatter.format(registro.getFfin()),
                            nombEvento = evento.getNombre(),
                            tramiteCofepris = evento.getTramiteCofepris();
                    tramiteCofepris = null == tramiteCofepris ? "Trámite no Encontrado" : tramiteCofepris;

                    String correoHTML = Util.textoMensajeEventoTramite(codigo, nombProducto, fechaLimite, nombEvento,
                            tramiteCofepris);
                    CorreoBO enviarC = new CorreoBO();
                    enviarC.setCorreo(registro.getUsuario().getEmail());
                    log.info("@notificaTramitesPendientesCOFEPRIS: enviamos correo a : " + enviarC.getCorreo());
                    //enviarC.setCorreo("carlos.lozada@ryndem.mx");
                    enviarC.setAsunto("GlosaNet - Recordatorio");
                    enviarC.setCuerpoCorreo(correoHTML);
                    Util.enviarCorreo(enviarC);

                    deliveriesCount++;
                    if (deliveriesCount > 500) {
                        String msg = "@notificaTramitesPendientesCOFEPRIS: Se han alcanzado >= 500 notificaciones en "
                                + today.toString();
                        log.info(msg);
                        //System.out.println(msg);
                        //deliveriesCount = 0;
                    }

                }

            } catch (Exception ex) {
                ex.printStackTrace();
                continue;
            }
        }

        String msg = "@notificaTramitesPendientesCOFEPRIS: Total de notificaciones enviadas: "
                + deliveriesCount;
        log.info(msg);
        //System.out.println(msg);
    }


    @Override
    public List<FeriadosOficialesBO> obtenerFeriadosOficiales()
            throws GlosaNetException {
        return registrosDAO.findFeriadosOficiales();
    }


    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean actualizarRegistro(RegistroBO bo) throws GlosaNetException {
        try {

            registrosDAO.updateRegistroFFin(bo);
            registrosDAO.updateProducto(bo.getProducto());

            /* Actualizar fechas de realización de eventos y sus respectivas notificaciones */
            if (bo.getEventos().size() > 0) {
                for (EventoBO event : bo.getEventos()) {
                    if (event.getIdEvento() != null && event.getIdEvento() > 0) {
                        registrosDAO.updateEvento(event);
                    }

                    if (event.getIdEvento() != null && event.getIdEvento() > 0) {
                        for (NotificacionBO n : event.getNotificaciones()) {
                            n.setIdEvento(event.getIdEvento());
                            if (n.getIdNotificacion() != null && n.getIdNotificacion() > 0) {
                                registrosDAO.updateNotificacion(n);
                            }
                        }
                    }
                }
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus()
                    .setRollbackOnly();
            throw new GlosaNetException();
        }
    }


    @SuppressWarnings("resource")
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean cargaRegistros(String urlArhivo) throws GlosaNetException {
        log.info("Inicia la carga de registros en: " + urlArhivo);
        BufferedReader br = null;
        List<String[]> oneDdata = new ArrayList<String[]>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        ProductoBO p;
        RegistroBO r;
        EventoBO e;
        NotificacionBO n;
        CorreoEventoBO c;
        UsuarioBO u = new UsuarioBO();
        u.setIdUsuario(37);
        String[] correos;
        Calendar calendar = Calendar.getInstance();

        // El archivo debe ser csv, dividido por , (comas)
        // Los encabezados:
        // 		Codigo	Descripcion											-- Producto
        // 		FechaInicio	FechaFin	FolioRegistro	TramiteCofepris		-- Registro
        // 		CantPeriodo	TramiteCofepris									-- Tecnovigilancia
        // 		CantPeriodo	Recurrencia 									-- Notificaciones
        // 		correos 													-- Divididos por ;
        // 		CantPeriodo	TramiteCofepris -- Prorroga
        // 		CantPeriodo	Recurrencia 									-- Notificaciones
        // 		correos 													-- Divididos por ;

        try {
            File f = new File(Util.rutaArchivos + "RehistrosTodos.csv"); // sin esta linea marca error de que el archivo no existe
            br = new BufferedReader(new FileReader(Util.rutaArchivos + "RehistrosTodos.csv"));
            String line = br.readLine();
            while (null != line) {
                String[] fields = line.split(",");
                oneDdata.add(fields);
                line = br.readLine();
                //System.out.println("-");
            }

            for (String[] x : oneDdata) {
                log.info(Arrays.toString(x));

                p = new ProductoBO();
                p.setIdEmpresa(10);
                p.setOrigen("USA");
                p.setRegistro(true);
                p.setPermiso(false);
                p.setCodigo(x[0]);
                p.setDescripcion(x[1]);
                p.setNombre(p.getCodigo() + " " + p.getDescripcion());
                /*** Nuevo Producto ***/
                p.setIdProducto(registrosDAO.saveNuevoProducto(p));

                r = new RegistroBO();
                r.setProducto(p);
                r.setIdEmpresa(10);
                r.setFinicio(formatter.parse(x[2]));
                r.setFfin(formatter.parse(x[3]));
                r.setFolioRegistro(x[4]);
                r.setTramiteCofepris(x[5]);
                r.setProrroga(true);
                r.setUsuario(u);
                r.setFinicioProrroga(new Date());
                r.setFfinProrroga(new Date());
                r.setTerminado(false);
                r.setDescripcion(p.getDescripcion());

                /*** Nuevo Registro ***/
                r.setIdRegistro(registrosDAO.saveNuevoRegistro(r));

                e = new EventoBO();
                e.setRegistro(r);
                e.setNombre("Dossier");
                e.setDefinido(true);
                e.setCantPeriodo(0);
                e.setTipoPeriodo("Días");
                e.setTerminado(true);
                e.setFrealizacion(new Date());
                e.setTramite(false);
                e.setTramiteCofepris("");
                /*** Nuevo Evento Terminado ***/
                registrosDAO.saveNuevoEvento(e);

                e = new EventoBO();
                e.setRegistro(r);
                e.setNombre("Sometimiento");
                e.setDefinido(true);
                e.setCantPeriodo(0);
                e.setTipoPeriodo("Días");
                e.setTerminado(true);
                e.setFrealizacion(new Date());
                e.setTramite(false);
                e.setTramiteCofepris("");
                /*** Nuevo Evento Terminado ***/
                registrosDAO.saveNuevoEvento(e);

                e = new EventoBO();
                e.setRegistro(r);
                e.setNombre("Tecnovigilancia");
                e.setDefinido(true);
                e.setCantPeriodo(Integer.parseInt(x[6]));
                e.setTipoPeriodo("Días");

                calendar.setTime(r.getFfin());
                calendar.add(Calendar.DATE, -e.getCantPeriodo());
                e.setFrealizacion(calendar.getTime());
                e.setTerminado(false);

                if (x[7] != null && x[7].length() > 0) {
                    e.setTramite(true);
                    e.setTramiteCofepris(x[7]);
                } else {
                    e.setTramite(false);
                    e.setTramiteCofepris("");
                }
                /*** Nuevo Evento ***/
                e.setIdEvento(registrosDAO.saveNuevoEvento(e));

                n = new NotificacionBO();
                n.setIdEvento(e.getIdEvento());

                n.setCantPeriodo(Integer.parseInt(x[8]));
                n.setTipoPeriodo("Días");

                calendar.setTime(r.getFfin());
                calendar.add(Calendar.DATE, -(n.getCantPeriodo() + e.getCantPeriodo()));
                n.setFinicio(calendar.getTime());

                n.setTerminado(false);
                n.setRecurrencia(x[9]);
                /*** Nuevo Notificacion ***/
                registrosDAO.saveNuevaNotificacion(n);

                correos = x[10].split(";");
                for (int i = 0; i < correos.length; i++) {
                    c = new CorreoEventoBO();
                    c.setIdEvento(e.getIdEvento());
                    c.setCorreo(correos[i]);
                    /*** Nuevo Correo ***/
                    registrosDAO.saveNuevoCorreoNotificacion(c);
                }

                e = new EventoBO();
                e.setRegistro(r);
                e.setNombre("Prorroga");
                e.setDefinido(true);
                e.setCantPeriodo(Integer.parseInt(x[11]));
                e.setTipoPeriodo("Días");

                calendar.setTime(r.getFfin());
                calendar.add(Calendar.DATE, -e.getCantPeriodo());
                e.setFrealizacion(calendar.getTime());

                e.setTerminado(false);

                if (x[12] != null && x[12].length() > 0) {
                    e.setTramite(true);
                    e.setTramiteCofepris(x[12]);
                } else {
                    e.setTramite(false);
                    e.setTramiteCofepris("");
                }

                /*** Nuevo Evento ***/
                e.setIdEvento(registrosDAO.saveNuevoEvento(e));


                n = new NotificacionBO();
                n.setIdEvento(e.getIdEvento());
                n.setCantPeriodo(Integer.parseInt(x[13]));
                n.setTipoPeriodo("Días");

                calendar.setTime(r.getFfin());
                calendar.add(Calendar.DATE, -(n.getCantPeriodo() + e.getCantPeriodo()));
                n.setFinicio(calendar.getTime());

                n.setTerminado(false);
                n.setRecurrencia(x[14]);
                /*** Nuevo Notificacion ***/
                registrosDAO.saveNuevaNotificacion(n);


                correos = x[15].split(";");
                for (int i = 0; i < correos.length; i++) {
                    c = new CorreoEventoBO();
                    c.setIdEvento(e.getIdEvento());
                    c.setCorreo(correos[i]);
                    /*** Nuevo Correo ***/
                    registrosDAO.saveNuevoCorreoNotificacion(c);
                }
            }

        } catch (Exception ex) {
            log.severe("Error al cargar registros. " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
        return true;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean actualizarFechas(int idEmpresa) throws GlosaNetException {
        try {
            List<RegistroBO> registros = registrosDAO.findRegistrosEmpresa(idEmpresa);
            for (RegistroBO r : registros) {
                //			for(int i = 0; i < 30; i++) {
                //				RegistroBO r = registros.get(i);
                for (EventoBO e : r.getEventos()) {
                    if (r.getFfin() != null) {
                        log.info("Eventos Inicio: " + e.getIdEvento() + " - " + e.getFrealizacion());
                        e.setFrealizacion(Util.calcularFechaConDiasHabiles(r.getFfin(), e.getCantPeriodo()));
                        log.info("Eventos Fin: " + e.getIdEvento() + " - " + e.getFrealizacion());
                        registrosDAO.updateEventoFRealizacion(e.getIdEvento(), e.getFrealizacion());
                    }
                    for (NotificacionBO n : e.getNotificaciones()) {
                        if (e.getFrealizacion() != null) {
                            n.setFinicio(Util.calcularFechaConDiasHabiles(r.getFfin(), n.getCantPeriodo()));
                            log.info("Notificación: " + n.getIdNotificacion() + " - " + n.getFinicio());
                            registrosDAO.updateNotificacionFInicio(n.getIdNotificacion(), n.getFinicio());
                        }
                    }
                }
            }
            return true;
        } catch (Exception e) {
            log.severe("Error al actualizar fechas de registros. " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String cuantosDiasHabiles(String finicio, String ffin) throws GlosaNetException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date inicio = new Date();
            inicio = formatter.parse(finicio);
            Date fin = new Date();
            fin = formatter.parse(ffin);
            Date calcula = new Date();
            calcula = Util.calcularFechaConDiasHabiles(fin, 365);
            log.info("Fecha de finalización: " + calcula);
            long diff = fin.getTime() - inicio.getTime();
            log.info("Cálculo de días hábiles: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
            return formatter.format(calcula) + " - " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            log.severe("Error al calcular los días hábiles. " + e.getMessage());
            e.printStackTrace();
        }
        return "false";
    }

    @Override
    public Page<RegistroBO> listPaginatedRecords(RegistrosRequestOptions requestOptions)
            throws GlosaNetException {
        Page<RegistroBO> page = registrosHibernateDAO.listPaginatedRecords(requestOptions);
        for (RegistroBO x : page.getResults()) {
            for (EventoBO ev : x.getEventos()) {
                if (x.getEventoActual().equals(ev.getNombre())) {
                    x.setEstadoCofepris(obtenerStatusTramiteCofepris(ev.getTramiteCofepris()));
                }
            }
        }
        return page;
    }

    @Override
    public Responsables getManager(int idEmpresa)
            throws GlosaNetException {

        Responsables data = new Responsables();

        ArrayList<String> manager = registrosHibernateDAO.getManager(idEmpresa);
        ArrayList<String> managerEvent = registrosHibernateDAO.getManagerEvent(idEmpresa);

        data.setManager(manager);
        data.setManagerEvent(managerEvent);

        return data;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public List<HistorialProductoBO> saveHistorialProducto(List<HistorialProductoBO> listHistorialProducto)
            throws GlosaNetException {
        try {
            for (HistorialProductoBO historial : listHistorialProducto) {
                if (historial != null && historial.getIdHistorialProducto() > 0) {
                    registrosDAO.updateHistorialProducto(historial, "comentarios");
                }
                if (historial != null) {
                    historial.setIdHistorialProducto(registrosDAO.addHistorialProducto(historial));
                }
            }
            return listHistorialProducto;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus()
                    .setRollbackOnly();
            throw new GlosaNetException();
        }
    }

    @Override
    public String subirArchivoHistorialProducto(File file, String fileName, String path, Integer idHistorialProducto)
            throws GlosaNetException {
        try {
            // Renombramos el archivo, concatenandole el id del registro para diferenciar en caso de que se carguen archivos con el mismo nombre
            HistorialProductoBO history = new HistorialProductoBO();
            history.setIdHistorialProducto(idHistorialProducto);
            history.setNombre(fileName);
            registrosDAO.updateHistorialProducto( history,"nombre");
            // Guardamos el archivo en la ruta específica
            String dir = Util.rutaArchivos + "Producto/" + path;
            File currentFile = new File(dir);
            File targetFile = new File(dir + "/" + fileName);
            if (!currentFile.exists()) {
                currentFile.mkdirs();
            }
            OutputStream outStream;
            outStream = new FileOutputStream(targetFile);
            byte[] array = Files.readAllBytes(file.toPath());
            outStream.write(array);
            outStream.close();
            return "Exito";
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlosaNetException();
        }
    }

    @Override
    public List<HistorialProductoBO> getArchivosPorRegistro(int idRegistro)
            throws GlosaNetException {
        List<HistorialProductoBO> result = registrosDAO.findArchivosPorRegistro(idRegistro);
        return result;
    }

    @Override
    public ByteArrayOutputStream obtenerHistorialProductoPDF(String path) throws GlosaNetException {

        log.info("@obtenerHistorialProductoPDF: Inicio");

        ByteArrayOutputStream buffer;
        try {
            String dir = Util.rutaArchivos + "Producto/" + path;
            log.info("@obtenerHistorialProductoPDF: Ruta para obtener archivo: " + dir);
            File file = new File(dir);

            if (null == file) {
                log.severe("@obtenerHistorialProductoPDF: Archivo no encontrado");
                throw new GlosaNetException("Archivo no encontrado");
            }

            InputStream fileStream = new FileInputStream(file);
            byte[] data = new byte[1024];
            buffer = new ByteArrayOutputStream();

            int read = 0;
            while ((read = fileStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, read);
            }

            buffer.flush();
            fileStream.close();

        } catch (Exception e) {
            e.printStackTrace();
            log.severe("@obtenerHistorialProductoPDF: exception -> " + e.toString());
            throw new GlosaNetException(e);
        }

        return buffer;
    }

    @Override
    public List<RegistroBO> obtenerRegistrosPorProducto(int idEmpresa, int idProducto)
            throws GlosaNetException {
        List<RegistroBO> result = registrosDAO.findRegistrosPorProducto(idEmpresa, idProducto);

        for (RegistroBO record : result) {
            record.setArchivosProductos(registrosDAO.findArchivosPorRegistro(record.getIdRegistro()));
            for (EventoBO event : record.getEventos()) {
                event.setHistorialEvento(registrosDAO.findHistorialEvento(event.getIdEvento()));
            }
        }

        return result;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean eliminarHistorialProducto(int idHistorialProducto, String path) throws GlosaNetException {
        try {
            if (registrosDAO.deleteHistorialProducto(idHistorialProducto)) {
                String dir = Util.rutaArchivos + "Producto/" + path;
                File file = new File(dir);

                if (file.exists()) {
                    file.delete();
                }
            }
            log.info("Se ha eliminado el producto del historial");
            return true;
        } catch (Exception e) {
            log.severe("Error al eliminar un producto del historial. " + e.getMessage());
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus()
                    .setRollbackOnly();
            throw new GlosaNetException();
        }
    }
}
